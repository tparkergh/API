package gov.ca.cwds.rest.resources;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import javax.ws.rs.core.MediaType;

import org.hamcrest.junit.ExpectedException;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import io.dropwizard.testing.junit.ResourceTestRule;

/**
 * NOTE : The CWDS API Team has taken the pattern of delegating Resource functions to
 * {@link ResourceDelegate}. As such the tests in here reflect that assumption.
 * 
 * @author CWDS API Team
 */
public class PersonResourceTest {
  private static final String FOUND_RESOURCE = "/people/1";

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  private static ResourceDelegate resourceDelegate = mock(ResourceDelegate.class);

  @ClassRule
  public static final ResourceTestRule inMemoryResource =
      ResourceTestRule.builder().addResource(new PersonResource(resourceDelegate)).build();

  /**
   * Get Tests
   * 
   * @throws Exception required for test compilation
   */
  @Test
  public void getDelegatesToCrudsResource() throws Exception {
    inMemoryResource.client().target(FOUND_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
        .get().getStatus();
    verify(resourceDelegate).get(1L);
  }

  /**
   * Create Tests
   * 
   * @throws Exception required for test compilation
   */
  @Test
  public void createDelegatesToCrudsResource() throws Exception {
    // TODO: broken test.
    // Person person = new Person("firstname", "last", "M", "1990-11-22", "000000000", null);
    //
    // final Response resp = inMemoryResource.client().target(ROOT_RESOURCE).request()
    // .accept(MediaType.APPLICATION_JSON).post(Entity.entity(person, MediaType.APPLICATION_JSON));
    //
    // ValidationErrorMessage msg = resp.readEntity(ValidationErrorMessage.class);
    // if (msg != null) {
    // final List<String> errors = msg.getErrors();
    // if (errors != null && errors.size() > 0) {
    // for (String err : errors) {
    // System.out.println("ERROR: " + err);
    // }
    // }
    // }
    //
    // verify(resourceDelegate).create(eq(person));
  }

  /**
   * Delete Tests
   * 
   * @throws Exception required for test compilation
   */
  @Test
  public void deleteReturns501() throws Exception {
    int receivedStatus = inMemoryResource.client().target(FOUND_RESOURCE).request()
        .accept(MediaType.APPLICATION_JSON).delete().getStatus();
    int expectedStatus = 501;
    assertThat(receivedStatus, is(expectedStatus));
  }

  /**
   * Update Tests
   * 
   * @throws Exception required for test compilation
   */
  @Test
  public void udpateReturns501() throws Exception {
    // TODO: broken test.
    // Person person = new Person("firstname", "last", "M", "1973-11-22", "000000000", null);
    // int status =
    // inMemoryResource.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
    // .put(Entity.entity(person, MediaType.APPLICATION_JSON)).getStatus();
    // assertThat(status, is(501));
  }
}
