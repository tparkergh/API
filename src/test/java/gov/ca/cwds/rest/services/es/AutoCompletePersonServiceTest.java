package gov.ca.cwds.rest.services.es;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.elasticsearch.search.SearchHits;
import org.hamcrest.junit.ExpectedException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import com.fasterxml.jackson.databind.ObjectMapper;

import gov.ca.cwds.data.es.ElasticSearchPerson;
import gov.ca.cwds.data.es.ElasticsearchDao;
import gov.ca.cwds.rest.api.domain.es.AutoCompletePerson;
import gov.ca.cwds.rest.api.domain.es.AutoCompletePersonRequest;
import gov.ca.cwds.rest.api.domain.es.AutoCompletePersonResponse;
import gov.ca.cwds.rest.services.ServiceException;
import io.dropwizard.jackson.Jackson;

/**
 * @author CWDS API Team
 *
 */
@SuppressWarnings("javadoc")
public class AutoCompletePersonServiceTest {

  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Mock
  private ElasticsearchDao dao;

  @Mock
  private SearchHits hits;

  @Mock
  private AutoCompletePersonRequest req;

  @Spy
  @InjectMocks
  private AutoCompletePersonService target; // "Class Under Test"

  @Before
  public void initMocks() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void type() throws Exception {
    assertThat(AutoCompletePersonService.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    assertThat(target, notNullValue());
  }

  @Test(expected = ServiceException.class)
  public void testHandleRequest_Args$AutoCompletePersonRequest_T$NPE() throws Exception {
    final AutoCompletePersonResponse actual = target.handleRequest(req);
    AutoCompletePersonResponse expected = new AutoCompletePersonResponse(new ArrayList<>());
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void testHandleRequest_Args$AutoCompletePersonRequest() throws Exception {

    // Issues with spy + injection.
    // AutoCompletePersonService espion = spy(new AutoCompletePersonService(dao));
    // final String arg = "hello";
    // final AutoCompletePersonResponse spyResult = target.find(arg);
    // when(target.find(any())).thenReturn(new AutoCompletePersonResponse(new ArrayList<>()));

    when(req.getSearchTerm()).thenReturn("fred");
    final AutoCompletePersonResponse actual = target.handleRequest(req);

    final AutoCompletePersonResponse expected = new AutoCompletePersonResponse(new ArrayList<>());
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void testHandleRequest_Args$req_wildcard() throws Exception {
    when(req.getSearchTerm()).thenReturn("fred*");
    final AutoCompletePersonResponse actual = target.handleRequest(req);
    final AutoCompletePersonResponse expected = new AutoCompletePersonResponse(new ArrayList<>());
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void testHandleRequest_Args$req_double_wildcard() throws Exception {
    when(req.getSearchTerm()).thenReturn("fred**");
    final AutoCompletePersonResponse actual = target.handleRequest(req);
    final AutoCompletePersonResponse expected = new AutoCompletePersonResponse(new ArrayList<>());
    assertThat(actual, is(equalTo(expected)));
  }

  @Test(expected = ServiceException.class)
  public void testHandleFind_Args$String_Throw$NPE() throws Exception {
    String arg0 = null;
    AutoCompletePersonResponse actual = target.handleFind(arg0);
    AutoCompletePersonResponse expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void testFind_Args$String_Found() throws Exception {
    final String findThis = "nuttin";
    final ElasticSearchPerson[] empty = new ElasticSearchPerson[0];
    when(dao.searchPerson(findThis)).thenReturn(empty);
    AutoCompletePersonResponse actual = target.handleFind(findThis);
    AutoCompletePersonResponse expected =
        new AutoCompletePersonResponse(new ArrayList<AutoCompletePerson>());
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void testLoadFromJson1() throws Exception {
    final AutoCompletePerson[] results =
        MAPPER.readValue(fixture("es/person_search/expected.json"), AutoCompletePerson[].class);
    for (AutoCompletePerson p : results) {
      System.out.println(p);
    }
  }

  @Test
  public void testLoadFromJson2() throws Exception {
    final AutoCompletePerson[] results =
        MAPPER.readValue(fixture("es/person_search/actual.json"), AutoCompletePerson[].class);
    for (AutoCompletePerson p : results) {
      System.out.println(p);
    }
  }

}
