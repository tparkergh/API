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
import org.elasticsearch.common.transport.DummyTransportAddress;
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

  private TransportAddress transportAddress;

  private final String host;
  private final String port;
  private final String clusterName;
  private String indexName;
  private String documentType;

  /**
   * Constructor. Construct from required fields.
   * 
   * @param host The ES host
   * @param port The port that the ES host is listening on
   * @param clusterName The ES cluster name
   */
  public ElasticsearchDao(String host, String port, String clusterName) {
    this.host = host;
    this.port = port;
    this.clusterName = clusterName;
  }

  /**
   * Constructor. Construct from YAML configuration.
   * 
   * @param config The ES configuration
   */
  @Inject
  public ElasticsearchDao(ElasticsearchConfiguration config) {
    this.host = config.getElasticsearchHost();
    this.clusterName = config.getElasticsearchCluster();
    this.port = config.getElasticsearchPort();
    this.indexName = config.getPeopleIndexName();
    this.documentType = config.getPeopleIndexType();
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
      this.client = TransportClient.builder().settings(settings).build()
          .addTransportAddress(this.transportAddress != null ? this.transportAddress
              : new InetSocketTransportAddress(InetAddress.getByName(host),
                  Integer.parseInt(port)));
    }
  }

  /**
   * Start the ElasticSearch client, if not started already.
   * 
   * <p>
   * This method calls the synchronized method, {@link #init()}, only if the underlying
   * {@link #client} is not initialized.
   * </p>
   * 
   * @throws Exception I/O error or unknown host
   * @see #init()
   */
  protected void start() throws Exception {
    LOGGER.debug("ElasticSearchDao.start()");

    // Enter synchronized init method ONLY if client is not initialized.
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
    LOGGER.debug("ElasticSearchDao.stop()");
    this.client.close();
    setClient(null);
    setTransportAddress(null);
  }

  /**
   * Stops and starts the ES client. If the client passed in is null, then {@link #init()} will
   * instantiate a new client with {@link InetSocketTransportAddress}.
   * 
   * @param client custom client, used for testing
   * @throws Exception I/O error or unknown host
   */
  public void reset(Client client) throws Exception {
    LOGGER.debug("ElasticSearchDao.stop()");
    stop();
    setClient(client);
    setTransportAddress(null);
    start();
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
    IndexResponse response = client.prepareIndex(indexName, documentType, id)
        .setConsistencyLevel(WriteConsistencyLevel.DEFAULT).setSource(document).execute()
        .actionGet();

    LOGGER.info("Created document:\nindex: " + response.getIndex() + "\ndoc type: "
        + response.getType() + "\nid: " + response.getId() + "\nversion: " + response.getVersion()
        + "\ncreated: " + response.isCreated());

    return response.isCreated();
  }

  /**
   * Returns ALL Person documents in the target ElasticSearch index, up the maximum number of rows
   * defined by {@link #DEFAULT_MAX_RESULTS}. Initializes and starts ElasticSearch client, if not
   * started.
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
    start();

    return client.prepareSearch(indexName).setTypes(documentType)
        .setSearchType(SearchType.QUERY_AND_FETCH).setFrom(0).setSize(DEFAULT_MAX_RESULTS)
        .setExplain(true).execute().actionGet().getHits().getHits();
  }

  /**
   * Generic OR query for Person. Initializes and starts ElasticSearch client, if not started.
   * 
   * @param req boolean hierarchy search request
   * @return array of raw ElasticSearch hits
   * @throws Exception unable to connect, disconnect, bad hair day, etc.
   */
  public SearchHit[] queryPersonOr(ESSearchRequest req) throws Exception {
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

    return client.prepareSearch(indexName).setTypes(documentType)
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
   * Set the default index (document) type.
   * 
   * @param docType the indexType to set
   */
  public void setDocumentType(String docType) {
    if (StringUtils.isNotBlank(docType)) {
      this.documentType = docType;
    } else {
      throw new ApiException("Elasticsearch Index Type must be provided");
    }
  }

  /**
   * Default index name
   * 
   * @return index name
   */
  public String getIndexName() {
    return indexName;
  }

  /**
   * Default index (document) type
   * 
   * @return default document type
   */
  public String getDocumentType() {
    return documentType;
  }

  /**
   * Used for testing but can be used to set a custom ES client.
   * 
   * @param client ElasticSearch client, typically a {@link TransportClient}
   */
  public void setClient(Client client) {
    this.client = client;
  }

  /**
   * Get the {@link TransportAddress}, primarily used for testing. See
   * {@link DummyTransportAddress}.
   * 
   * @return current TransportAddress, if any, such {@link DummyTransportAddress}.
   */
  public TransportAddress getTransportAddress() {
    return transportAddress;
  }

  /**
   * Set the {@link TransportAddress}, primarily used for testing. See
   * {@link DummyTransportAddress}.
   * 
   * @param transportAddress TransportAddress, if any, such {@link DummyTransportAddress}.
   */
  public void setTransportAddress(TransportAddress transportAddress) {
    this.transportAddress = transportAddress;
  }

}
