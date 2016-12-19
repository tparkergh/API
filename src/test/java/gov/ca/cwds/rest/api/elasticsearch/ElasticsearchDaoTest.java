package gov.ca.cwds.rest.api.elasticsearch;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.startsWith;
import static org.mockito.Mockito.mock;

import org.elasticsearch.client.transport.TransportClient;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import gov.ca.cwds.rest.api.ApiException;
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
  // private static final String TEST_INDEXNAME = "people";
  // private static final String TEST_INDEXTYPE = "person";


  @InjectMocks
  private ElasticsearchDao cut = new ElasticsearchDao(TEST_HOST, TEST_PORT, TEST_CLUSTERNAME);

  @Mock
  private TransportClient.Builder clientBuilder;

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Before
  public void setUp() throws Exception {
    mock(TransportClient.class);
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void testIndexNameEmptyFails() throws Exception {
    thrown.expect(ApiException.class);
    thrown.expectMessage(startsWith("Elasticsearch Index Name must be provided"));
    cut.setIndexName("");
  }

  @Test
  public void testIndexTypeEmptyFails() throws Exception {
    thrown.expect(ApiException.class);
    thrown.expectMessage(startsWith("Elasticsearch Index Type must be provided"));
    cut.setIndexType("");
  }

  @Test
  public void testSettings() throws Exception {
    assertThat("host", TEST_HOST.equals(cut.getHost()));
    assertThat("port", TEST_PORT.equals(cut.getPort()));
    assertThat("cluster", TEST_CLUSTERNAME.equals(cut.getClusterName()));
  }

  // TODO: save for integration test?
  // @Test
  // public void testfetchAllPerson() throws Exception {
  // // return client.prepareSearch(indexName).setTypes(indexType)
  // // .setSearchType(SearchType.QUERY_AND_FETCH).setFrom(0).setSize(DEFAULT_MAX_RESULTS)
  // // .setExplain(true).execute().actionGet().getHits().getHits();
  //
  //
  // // when(TransportClient.class).
  //
  // // final SearchHit[] hits = cut.fetchAllPerson();
  // }



}
