package gov.ca.cwds.rest.resources;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;

import javax.ws.rs.core.MediaType;

import org.hamcrest.junit.ExpectedException;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import gov.ca.cwds.rest.api.domain.es.PersonSearchRequest;
import io.dropwizard.testing.junit.ResourceTestRule;

/**
 * NOTE : The CWDS API Team has taken the pattern of delegating Resource functions to
 * {@link ResourceDelegate}. As such the tests in here reflect that assumption.
 * 
 * @author CWDS API Team
 */
public class PersonSearchResourceTest {
  private static final String ROOT_RESOURCE = "/search_persons/";
  private static final String FOUND_RESOURCE = "/search_persons/1";

  PersonSearchRequest personSearchRequest =
      new PersonSearchRequest("Bart", "Simpson", "2000-01-01");

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  private static ResourceDelegate resourceDelegate = mock(ResourceDelegate.class);

  @ClassRule
  public static final ResourceTestRule inMemoryResource =
      ResourceTestRule.builder().addResource(new PersonSearchResource(resourceDelegate)).build();

  /*
   * 404 test for unimplemented methods
   */
  @Test
  public void deleteReturns404() throws Exception {
    int receivedStatus = inMemoryResource.client().target(FOUND_RESOURCE).request()
        .accept(MediaType.APPLICATION_JSON).delete().getStatus();
    int expectedStatus = 404;
    assertThat(receivedStatus, is(expectedStatus));
  }

  @Test
  public void getReturns404() throws Exception {
    int receivedStatus = inMemoryResource.client().target(FOUND_RESOURCE).request()
        .accept(MediaType.APPLICATION_JSON).get().getStatus();
    int expectedStatus = 404;
    assertThat(receivedStatus, is(expectedStatus));

  }
}
