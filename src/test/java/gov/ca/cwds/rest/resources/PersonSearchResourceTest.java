package gov.ca.cwds.rest.resources;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

import org.hamcrest.junit.ExpectedException;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import gov.ca.cwds.rest.api.domain.es.ESPerson;
import gov.ca.cwds.rest.api.domain.es.ESPersonSearchRequest;
import gov.ca.cwds.rest.services.PersonService;
import io.dropwizard.testing.junit.ResourceTestRule;

/**
 * NOTE : The CWDS API Team has taken the pattern of delegating Resource functions to
 * {@link ResourceDelegate}. As such the tests in here reflect that assumption.
 * 
 * @author CWDS API Team
 */
// TODO: class name should match ESPersonSearchRequest

public class PersonSearchResourceTest {
  private static final String ROOT_RESOURCE = "/search_persons/";
  private static final String NO_FOUND_RESOURCE = "/search_persons/1";
  private static final String DELETE_RESOURCE = "/search_person/1";
  private static final String FOUND_RESOURCE = "/search_person/all";
  private static final String QUERY_RESOURCE = "/search_person/query_or";

  ESPersonSearchRequest personSearchRequest =
      new ESPersonSearchRequest("Bart", "Simpson", "2000-01-01");

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  private static PersonService personService = mock(PersonService.class);
  private static ResourceDelegate resourceDelegate = mock(ResourceDelegate.class);
  private static ResourceDelegate backedResourceDelegate =
      new ServiceBackedResourceDelegate(personService);


  @ClassRule
  public static final ResourceTestRule inMemoryResource =
      ResourceTestRule.builder().addResource(new PersonSearchResource(resourceDelegate)).build();

  @ClassRule
  public static final ResourceTestRule backedinMemoryResource = ResourceTestRule.builder()
      .addResource(new PersonSearchResource(backedResourceDelegate)).build();


  /*
   * 404 test for unimplemented methods
   */
  @Test
  public void deleteReturns404() throws Exception {
    int receivedStatus = inMemoryResource.client().target(NO_FOUND_RESOURCE).request()
        .accept(MediaType.APPLICATION_JSON).delete().getStatus();
    int expectedStatus = 404;
    assertThat(receivedStatus, is(expectedStatus));
  }

  @Test
  public void getReturns404() throws Exception {
    int receivedStatus = inMemoryResource.client().target(NO_FOUND_RESOURCE).request()
        .accept(MediaType.APPLICATION_JSON).get().getStatus();
    int expectedStatus = 404;
    assertThat(receivedStatus, is(expectedStatus));

  }

  /*
   * 501 for Non Implemented Delete
   */
  @Test
  public void deleteReturns501() throws Exception {
    int receivedStatus = inMemoryResource.client().target(DELETE_RESOURCE).request()
        .accept(MediaType.APPLICATION_JSON).delete().getStatus();
    int expectedStatus = 501;
    assertThat(receivedStatus, is(expectedStatus));

  }

  /*
   * 200 for Showing all the persons
   */
  @Test
  public void testShowAllPersons200() throws Exception {
    ESPerson[] hits = new ESPerson[2];
    when(personService.fetchAllPersons()).thenReturn(hits);
    javax.ws.rs.core.Response response = backedinMemoryResource.client().target(FOUND_RESOURCE)
        .request().accept(MediaType.APPLICATION_JSON).post(null);
    ESPerson[] result = response.readEntity(ESPerson[].class);
    assertThat(result.length, is(2));
  }

  /*
   * 404 for No user found
   */
  @Test
  public void testShowAllPersons404() throws Exception {
    when(personService.fetchAllPersons()).thenReturn(null);
    javax.ws.rs.core.Response response = backedinMemoryResource.client().target(FOUND_RESOURCE)
        .request().accept(MediaType.APPLICATION_JSON).post(null);
    int receivedStatus = response.getStatus();
    assertThat(receivedStatus, is(404));
  }

  /*
   * 500 for throwing the Service error
   */
  @Test
  public void testShowAllPersons500() {
    try {
      when(personService.fetchAllPersons()).thenThrow(new Exception());
    } catch (Exception e) {
      e.printStackTrace();
    }

    int receivedStatus = backedinMemoryResource.client().target(FOUND_RESOURCE).request()
        .accept(MediaType.APPLICATION_JSON).post(null).getStatus();
    assertThat(receivedStatus, is(500));
  }

  /*
   * 200 for showing the query person
   */
  @Test
  public void testqueryPersonOrTerm200() throws Exception {
    ESPerson[] hits = new ESPerson[2];
    when(personService.queryPersonOr("", "", "")).thenReturn(hits);
    ESPersonSearchRequest req = new ESPersonSearchRequest();
    javax.ws.rs.core.Response response = backedinMemoryResource.client().target(QUERY_RESOURCE)
        .request().accept(MediaType.APPLICATION_JSON)
        .post(Entity.entity(req, MediaType.APPLICATION_JSON));
    ESPerson[] result = response.readEntity(ESPerson[].class);
    assertThat(result.length, is(2));
  }

}
