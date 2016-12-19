package gov.ca.cwds.rest.elasticsearch.db;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.WriteConsistencyLevel;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import gov.ca.cwds.rest.ElasticsearchConfiguration;
import gov.ca.cwds.rest.api.ApiException;
import gov.ca.cwds.rest.api.domain.es.ESSearchRequest;
import gov.ca.cwds.rest.api.domain.es.ESSearchRequest.ESFieldSearchEntry;
import gov.ca.cwds.rest.api.domain.es.ESSearchRequest.ESSearchElement;
import gov.ca.cwds.rest.api.domain.es.ESSearchRequest.ElementType;

/**
 * A DAO for Elasticsearch.
 * 
 * @author CWDS API Team
 */
public class ElasticsearchDao {

  private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(ElasticsearchDao.class);

  private static final int DEFAULT_MAX_RESULTS = 60;

  private Client client;
  private final String host;
  private final String port;
  private final String clusterName;
  private String indexName;
  private String indexType;
  private TransportAddress transportAddress;
  private TransportClient.Builder clientBuilder;

  /**
   * Constructor
   * 
   * @param host The host
   * @param port The port
   * @param clusterName The cluster name
   */
  public ElasticsearchDao(String host, String port, String clusterName) {
    this.host = host;
    this.port = port;
    this.clusterName = clusterName;
  }

  /**
   * Constructor
   * 
   * @param elasticsearchConfiguration The configuration
   */
  @Inject
  public ElasticsearchDao(ElasticsearchConfiguration elasticsearchConfiguration) {
    this.host = elasticsearchConfiguration.getElasticsearchHost();
    this.clusterName = elasticsearchConfiguration.getElasticsearchCluster();
    this.port = elasticsearchConfiguration.getElasticsearchPort();
  }

  /**
   * Initialize ElasticSearch client once. Synchronized to prevent race conditions and multiple
   * connections.
   * 
   * @throws UnknownHostException if host not found
   */
  protected synchronized void init() throws UnknownHostException {
    if (this.client == null) {
      Settings settings = Settings.settingsBuilder().put("cluster.name", clusterName).build();

      TransportAddress transport = buildTransportAddress();
      setTransportAddress(transport);

      this.client = this.clientBuilder != null ? clientBuilder.build()
          : TransportClient.builder().settings(settings).build()
              .addTransportAddress(getTransportAddress());
    }
  }

  /**
   * Start the ElasticSearch client, if not started already.
   * 
   * @throws Exception I/O error or unknown host
   * @see #init()
   */
  protected void start() throws Exception {
    LOGGER.info("ElasticSearchDao.start()");

    // Only enter synchronized method if client is not initialized.
    if (this.client == null) {
      init();
    }
  }

  /**
   * Only stop the ElasticSearch client, when the container stops or if the connection becomes
   * unhealthy.
   * 
   * @throws Exception if ElasticSearch client fails to close properly.
   */
  protected void stop() throws Exception {
    LOGGER.info("ElasticSearchDao.stop()");
    this.client.close();
    setTransportAddress(null);
    setClientBuilder(null);
  }

  /**
   * Create an ElasticSearch document with the given index and document type.
   * 
   * @param document JSON of document
   * @param id the unique identifier
   * @return true if document is indexed false if updated
   * @throws Exception exception on create document
   */
  public boolean index(String document, String id) throws Exception {
    LOGGER.info("ElasticSearchDao.createDocument(): " + document);
    start();
    IndexResponse response = client.prepareIndex(indexName, indexType, id)
        .setConsistencyLevel(WriteConsistencyLevel.DEFAULT).setSource(document).execute()
        .actionGet();

    LOGGER.info("Created document:\nindex: " + response.getIndex() + "\ndoc type: "
        + response.getType() + "\nid: " + response.getId() + "\nversion: " + response.getVersion()
        + "\ncreated: " + response.isCreated());

    return response.isCreated();
  }

  /**
   * Returns ALL Person documents in the target ElasticSearch index, up the maximum number of rows
   * defined by {@link #DEFAULT_MAX_RESULTS}.
   * 
   * <p>
   * This method intentionally returns raw ElasticSearch {@link SearchHit}. Calling services should
   * convert data to appropriate classes.
   * </p>
   * 
   * <p>
   * SAMPLE HIT:
   * </p>
   * 
   * <blockquote>{updated_at=2016-11-23-09.09.15.930, gender=Male, birth_date=1990-04-01,
   * created_at=2016-11-23-09.09.15.953, last_name=Simpson, id=100, first_name=Bart, ssn=999551111}
   * </blockquote>
   * 
   * @return array of generic ElasticSearch {@link SearchHit}
   * @throws Exception unable to connect to ElasticSearch or disconnects midstream, etc.
   */
  public SearchHit[] fetchAllPerson() throws Exception {
    // Initialize and start ElasticSearch client, if not started.
    start();

    return client.prepareSearch(indexName).setTypes(indexType)
        .setSearchType(SearchType.QUERY_AND_FETCH).setFrom(0).setSize(DEFAULT_MAX_RESULTS)
        .setExplain(true).execute().actionGet().getHits().getHits();
  }

  /**
   * Generic OR query for Person.
   * 
   * @param req boolean hierarchy search request
   * @return array of raw ElasticSearch hits
   * @throws Exception unable to connect, disconnect, bad hair day, etc.
   */
  public SearchHit[] queryPersonOr(ESSearchRequest req) throws Exception {
    // Initialize and start ElasticSearch client, if not started.
    start();

    String field = "";
    String value = "";
    for (ESSearchElement elem : req.getRoot().getElems()) {
      if (elem.getElementType() == ElementType.FIELD_TERM) {
        ESFieldSearchEntry fieldSearch = (ESFieldSearchEntry) elem;
        field = fieldSearch.getField();
        value = fieldSearch.getValue();
      }
    }

    QueryBuilder qb = null;
    if ((value.contains("*") || value.contains("?"))
        && (!value.startsWith("?") && !value.startsWith("*"))) {
      qb = QueryBuilders.wildcardQuery(field, value);
    } else {
      qb = QueryBuilders.matchQuery(field, value);
    }

    return client.prepareSearch(indexName).setTypes(indexType)
        .setSearchType(SearchType.QUERY_AND_FETCH).setQuery(qb).setFrom(0)
        .setSize(DEFAULT_MAX_RESULTS).setExplain(true).execute().actionGet().getHits().getHits();
  }

  // ===================
  // ACCESSORS:
  // ===================

  /**
   * @return the client
   */
  public Client getClient() {
    return client;
  }

  /**
   * @return the host
   */
  public String getHost() {
    return host;
  }

  /**
   * @return the port
   */
  public String getPort() {
    return port;
  }

  /**
   * @return the clusterName
   */
  public String getClusterName() {
    return clusterName;
  }

  /**
   * @param indexName the indexName to set
   */
  public void setIndexName(String indexName) {
    if (StringUtils.isNotBlank(indexName)) {
      this.indexName = indexName;
    } else {
      throw new ApiException("Elasticsearch Index Name must be provided");
    }
  }

  /**
   * @param indexType the indexType to set
   */
  public void setIndexType(String indexType) {
    if (StringUtils.isNotBlank(indexType)) {
      this.indexType = indexType;
    } else {
      throw new ApiException("Elasticsearch Index Type must be provided");
    }
  }

  protected TransportAddress buildTransportAddress() throws UnknownHostException {
    return transportAddress != null ? transportAddress
        : new InetSocketTransportAddress(InetAddress.getByName(host), Integer.parseInt(port));
  }

  public TransportAddress getTransportAddress() throws UnknownHostException {
    return transportAddress;
  }

  public void setTransportAddress(TransportAddress transport) {
    this.transportAddress = transport;
  }

  public TransportClient.Builder getClientBuilder() {
    return clientBuilder;
  }

  public void setClientBuilder(TransportClient.Builder clientBuilder) {
    this.clientBuilder = clientBuilder;
  }

}
