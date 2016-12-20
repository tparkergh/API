package gov.ca.cwds.rest.api.elasticsearch;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.startsWith;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.elasticsearch.action.ListenableActionFuture;
import org.elasticsearch.action.WriteConsistencyLevel;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import gov.ca.cwds.rest.api.ApiException;
import gov.ca.cwds.rest.api.domain.es.ESSearchRequest;
import gov.ca.cwds.rest.elasticsearch.db.ElasticsearchDao;

/**
 * Class under test: {@link ElasticsearchDao}.
 * 
 * @author CWDS API Team
 */
public class ElasticsearchDaoTest {

  private static final String TEST_HOST = "localhost";
  private static final String TEST_PORT = "9300";
  private static final String TEST_CLUSTERNAME = "elasticsearch";
  private static final String TEST_INDEXNAME = "people";
  private static final String TEST_INDEXTYPE = "person";


  @InjectMocks
  private ElasticsearchDao cut = new ElasticsearchDao(TEST_HOST, TEST_PORT, TEST_CLUSTERNAME);

  @Mock
  private TransportClient.Builder clientBuilder;

  @Mock
  private TransportClient client;

  @Mock
  private SearchRequestBuilder srb;

  @Mock
  private IndexRequestBuilder irb;

  @Mock
  private ListenableActionFuture<SearchResponse> listenSearch;

  @Mock
  private ListenableActionFuture<IndexResponse> listenIndex;

  @Mock
  private SearchResponse respSearch;

  @Mock
  private IndexResponse respIndex;

  @Mock
  private SearchHits hits;

  @Mock
  private SearchHit hit;

  // @Spy
  // private TransportAddress dummyTransport = DummyTransportAddress.INSTANCE;

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Before
  public void setUp() throws Exception {
    mock(TransportClient.class);
    mock(SearchRequestBuilder.class);
    MockitoAnnotations.initMocks(this);

    // Execute queries against a fake transport and client.
    // cut.setTransportAddress(DummyTransportAddress.INSTANCE);

    // Mock up searches:
    when(client.prepareSearch(any())).thenReturn(srb);
    when(srb.setTypes(any())).thenReturn(srb);
    when(srb.setSearchType(any(SearchType.class))).thenReturn(srb);
    when(srb.setFrom(0)).thenReturn(srb);
    when(srb.setSize(any(Integer.class))).thenReturn(srb);
    when(srb.setExplain(any(Boolean.class))).thenReturn(srb);
    when(srb.execute()).thenReturn(listenSearch);
    when(srb.setQuery(any(QueryBuilder.class))).thenReturn(srb);
    when(listenSearch.actionGet()).thenReturn(respSearch);
    when(respSearch.getHits()).thenReturn(hits);
    when(hits.getHits()).thenReturn(new SearchHit[] {hit});

    // Mock up document creation:
    when(client.prepareIndex(any(String.class), any(String.class), any(String.class)))
        .thenReturn(irb);
    when(irb.setConsistencyLevel(WriteConsistencyLevel.DEFAULT)).thenReturn(irb);
    when(irb.setSource(any(String.class))).thenReturn(irb);
    when(irb.execute()).thenReturn(listenIndex);
    when(listenIndex.actionGet()).thenReturn(respIndex);
    when(respIndex.isCreated()).thenReturn(true);
  }

  @Test
  public void testIndexNameEmptyFails() throws Exception {
    thrown.expect(ApiException.class);
    thrown.expectMessage(startsWith("Elasticsearch Index Name must be provided"));
    cut.setIndexName("");
  }

  @Test
  public void testDocumentTypeEmptyFails() throws Exception {
    thrown.expect(ApiException.class);
    thrown.expectMessage(startsWith("Elasticsearch Index Type must be provided"));
    cut.setDocumentType("");
  }

  @Test
  public void testSettings() throws Exception {
    assertThat("host", TEST_HOST.equals(cut.getHost()));
    assertThat("port", TEST_PORT.equals(cut.getPort()));
    assertThat("cluster", TEST_CLUSTERNAME.equals(cut.getClusterName()));
  }

  @Test
  public void testSetIndexType() throws Exception {
    cut.setDocumentType(TEST_INDEXTYPE);
    assertThat("index type", TEST_INDEXTYPE.equals(cut.getDocumentType()));
  }

  @Test
  public void testSetIndexName() throws Exception {
    cut.setIndexName(TEST_INDEXNAME);
    assertThat("index name", TEST_INDEXNAME.equals(cut.getIndexName()));
  }

  @Test
  public void testIndexDoc() throws Exception {
    assertThat("index doc", cut.index("fred", "1234"));
  }

  @Test
  public void testFetchAllPerson() throws Exception {
    final SearchHit[] results = cut.fetchAllPerson();
    assertThat("hits", results != null && results.length > 0);
  }

  @Test
  public void testQueryPersonOrWithWildcard() throws Exception {
    ESSearchRequest req = new ESSearchRequest();
    req.getRoot().addElem(new ESSearchRequest.ESFieldSearchEntry("first_name", "bart*"));
    final SearchHit[] results = cut.queryPersonOr(req);
    assertThat("hits", results != null && results.length > 0);
  }

  @Test
  public void testQueryPersonOrNoWildcard() throws Exception {
    ESSearchRequest req = new ESSearchRequest();
    req.getRoot().addElem(new ESSearchRequest.ESFieldSearchEntry("first_name", "bart"));
    final SearchHit[] results = cut.queryPersonOr(req);
    assertThat("hits", results != null && results.length > 0);
  }


}
