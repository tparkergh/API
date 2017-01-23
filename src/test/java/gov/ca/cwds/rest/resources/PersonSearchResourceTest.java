package gov.ca.cwds.rest.resources;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

import org.hamcrest.junit.ExpectedException;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import gov.ca.cwds.rest.api.domain.es.ESPerson;
import gov.ca.cwds.rest.api.domain.es.ESPersonSearchRequest;
import gov.ca.cwds.rest.services.PersonService;
import gov.ca.cwds.rest.services.ServiceException;
import io.dropwizard.testing.junit.ResourceTestRule;

/**
 * NOTE : The CWDS API Team has taken the pattern of delegating Resource functions to
 * {@link ResourceDelegate}. As such the tests in here reflect that assumption.
 * 
 * @author CWDS API Team
 */
// TODO: class name should match ESPersonSearchRequest

// TODO: #136527227:
// 1) All test methods should throw Exception (fixed). Don't swallow exceptions.
// 2) Try a few more request params and different result sizes.
// 3) See comments on PersonSearchTest also.

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

  // @Test
  // public void testShowAllPersonsEmpty() throws Exception {
  // ESPerson[] hits = new ESPerson[0];
  // when(personService.fetchAllPersons()).thenReturn(hits);
  // javax.ws.rs.core.Response response = backedinMemoryResource.client().target(FOUND_RESOURCE)
  // .request().accept(MediaType.APPLICATION_JSON).post(null);
  // ESPerson[] result = response.readEntity(ESPerson[].class);
  // assertThat(result.length, is(0));
  // }

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

  @Test
  public void createReturns404() throws Exception {
    int receivedStatus = inMemoryResource.client().target(NO_FOUND_RESOURCE).request()
        .accept(MediaType.APPLICATION_JSON).post(null).getStatus();
    int expectedStatus = 404;
    assertThat(receivedStatus, is(expectedStatus));
  }

  /*
   * 501 for unimplemented delete
   */
  @Test
  public void deleteReturns501() throws Exception {
    int receivedStatus = inMemoryResource.client().target(DELETE_RESOURCE).request()
        .accept(MediaType.APPLICATION_JSON).delete().getStatus();
    int expectedStatus = 501;
    assertThat(receivedStatus, is(expectedStatus));
  }

  /*
   * 501 for unimplemented create
   */
  @Test
  public void createReturns501() throws Exception {
    int receivedStatus = inMemoryResource.client().target(DELETE_RESOURCE).request()
        .accept(MediaType.APPLICATION_JSON).post(null).getStatus();
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
  public void testShowAllPersons500() throws Exception {
    when(personService.fetchAllPersons()).thenThrow(new ServiceException());
    int receivedStatus = backedinMemoryResource.client().target(FOUND_RESOURCE).request()
        .accept(MediaType.APPLICATION_JSON).post(null).getStatus();
    assertThat(receivedStatus, is(500));
  }

  /*
   * 200 for showing the query person
   */
  @Test
  public void testQueryPersonOrTerm200() throws Exception {
    ESPerson[] hits = new ESPerson[2];
    when(personService.queryPersonOr("a", "b", "1992")).thenReturn(hits);
    ESPersonSearchRequest req = new ESPersonSearchRequest();
    req.setFirstName("a");
    req.setLastName("b");
    req.setBirthDate("1992");
    javax.ws.rs.core.Response response = backedinMemoryResource.client().target(QUERY_RESOURCE)
        .request().accept(MediaType.APPLICATION_JSON)
        .post(Entity.entity(req, MediaType.APPLICATION_JSON));
    ESPerson[] result = response.readEntity(ESPerson[].class);
    assertThat(result.length, is(2));
  }

  /*
   * query return empty or null
   */
  @Test
  public void testQueryPersonOrTermReturnsEmpty() throws Exception {
    ESPerson[] hits = new ESPerson[2];
    when(personService.queryPersonOr("a", "b", "1992")).thenReturn(hits);
    ESPersonSearchRequest req = new ESPersonSearchRequest();
    req.setFirstName("a");
    req.setLastName("b");
    req.setBirthDate("2000");
    javax.ws.rs.core.Response response = backedinMemoryResource.client().target(QUERY_RESOURCE)
        .request().accept(MediaType.APPLICATION_JSON)
        .post(Entity.entity(req, MediaType.APPLICATION_JSON));
    ESPerson[] result = response.readEntity(ESPerson[].class);
    Assert.assertNull(result);
  }

  /*
   * 404 when no person found
   */
  @Test
  public void testQueryPersonOrTerm404() throws Exception {
    ESPerson[] hits = null;
    when(personService.queryPersonOr("", "", "")).thenReturn(hits);
    ESPersonSearchRequest req = new ESPersonSearchRequest();
    javax.ws.rs.core.Response response = backedinMemoryResource.client().target(QUERY_RESOURCE)
        .request().accept(MediaType.APPLICATION_JSON)
        .post(Entity.entity(req, MediaType.APPLICATION_JSON));
    int receivedStatus = response.getStatus();
    assertThat(receivedStatus, is(404));
  }

  /*
   * 400 for invalid JSON
   */
  @Test
  public void testQueryPersonOrTerm400() throws Exception {
    ESPerson[] hits = new ESPerson[2];
    when(personService.queryPersonOr("", "", "")).thenReturn(hits);
    @SuppressWarnings("unused")
    ESPersonSearchRequest req = new ESPersonSearchRequest();
    javax.ws.rs.core.Response response = backedinMemoryResource.client().target(QUERY_RESOURCE)
        .request().accept(MediaType.APPLICATION_JSON)
        .post(Entity.entity("test", MediaType.APPLICATION_JSON));
    int receivedStatus = response.getStatus();
    assertThat(receivedStatus, is(400));

  }

  /*
   * 406 for invalid headers
   */
  @Test
  public void testQueryPersonOrTerm406() throws Exception {
    ESPerson[] hits = new ESPerson[2];
    when(personService.queryPersonOr("", "", "")).thenReturn(hits);
    @SuppressWarnings("unused")
    ESPersonSearchRequest req = new ESPersonSearchRequest();
    javax.ws.rs.core.Response response = backedinMemoryResource.client().target(QUERY_RESOURCE)
        .request().accept(MediaType.APPLICATION_FORM_URLENCODED)
        .post(Entity.entity("test", MediaType.APPLICATION_JSON));
    int receivedStatus = response.getStatus();
    assertThat(receivedStatus, is(406));
  }

  /*
   * 500 for server error
   */
  @Test
  public void testQueryPersonOrTerm500() throws Exception {
    when(personService.queryPersonOr("", "", "")).thenThrow(new Exception());
    ESPersonSearchRequest req = new ESPersonSearchRequest();
    int receivedStatus = backedinMemoryResource.client().target(QUERY_RESOURCE).request()
        .accept(MediaType.APPLICATION_JSON).post(Entity.entity(req, MediaType.APPLICATION_JSON))
        .getStatus();
    assertThat(receivedStatus, is(500));
  }

  /*
   * 500 when the request is null
   */
  @Test
  public void testQueryPersonOrTermNull() throws Exception {
    ESPerson[] hits = new ESPerson[2];
    when(personService.queryPersonOr("", "", "")).thenReturn(hits);
    @SuppressWarnings("unused")
    ESPersonSearchRequest req = new ESPersonSearchRequest();
    javax.ws.rs.core.Response response = backedinMemoryResource.client().target(QUERY_RESOURCE)
        .request().accept(MediaType.APPLICATION_JSON)
        .post(Entity.entity(null, MediaType.APPLICATION_JSON));
    int receivedStatus = response.getStatus();
    assertThat(receivedStatus, is(500));
  }

  /*
   * 500 when the request is empty
   */
  @Test
  public void testQueryPersonOrTermEmpty() throws Exception {
    ESPerson[] hits = new ESPerson[2];
    when(personService.queryPersonOr("", "", "")).thenReturn(hits);
    @SuppressWarnings("unused")
    ESPersonSearchRequest req = new ESPersonSearchRequest();
    javax.ws.rs.core.Response response = backedinMemoryResource.client().target(QUERY_RESOURCE)
        .request().accept(MediaType.APPLICATION_JSON)
        .post(Entity.entity(" ", MediaType.APPLICATION_JSON));
    int receivedStatus = response.getStatus();
    assertThat(receivedStatus, is(500));
  }

}
