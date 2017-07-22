package gov.ca.cwds.rest.resources;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

import org.hamcrest.junit.ExpectedException;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mockito;

import gov.ca.cwds.rest.api.domain.Screening;
import io.dropwizard.testing.junit.ResourceTestRule;

/**
 * NOTE : The CWDS API Team has taken the pattern of delegating Resource functions to
 * {@link ServiceBackedResourceDelegate}. As such the tests in here reflect that assumption.
 * 
 * @author CWDS API Team
 */
public class ScreeningResourceTest {
  private static final String ROOT_RESOURCE = "/screenings/";
  private static final String FOUND_RESOURCE = "/screenings/1";

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  private static final ResourceDelegate resourceDelegate = mock(ResourceDelegate.class);

  @ClassRule
  public static final ResourceTestRule inMemoryResource =
      ResourceTestRule.builder().addResource(new ScreeningResource(resourceDelegate)).build();

  @Before
  public void setup() throws Exception {
    Mockito.reset(resourceDelegate);
  }

  /*
   * Get Tests
   */

  @Test
  public void testGetNotImplemented() throws Exception {
    int receivedStatus = inMemoryResource.client().target("/screenings/fetch/abc").request()
        .accept(MediaType.APPLICATION_JSON).get().getStatus();
    int expectedStatus = 501;
    assertThat(receivedStatus, is(expectedStatus));
  }

  /*
   * Delete Tests
   */
  @Test
  public void testDeletedNotNotImplemented() throws Exception {
    int receivedStatus = inMemoryResource.client().target(FOUND_RESOURCE).request()
        .accept(MediaType.APPLICATION_JSON).delete().getStatus();
    int expectedStatus = 501;
    assertThat(receivedStatus, is(expectedStatus));
  }

  /*
   * Create Tests
   */
  @Test
  public void testCreate() throws Exception {
    Screening screening = new Screening("abc", "screening", "reference", "screeningDecision",
        "screeningDecisionDetail", "assignee", "2017-01-01");
    inMemoryResource.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
        .post(Entity.entity(screening, MediaType.APPLICATION_JSON));
    verify(resourceDelegate).create(eq(screening));
  }

  /*
   * Create Tests
   */
  @Test
  public void testCreateInvalid() throws Exception {
    Screening screening = new Screening("", "", "", "", "", "", "");
    int actualStatus =
        inMemoryResource.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(screening, MediaType.APPLICATION_JSON)).getStatus();
    int expectedStatus = 422;
    assertThat(actualStatus, is(expectedStatus));
  }

  /*
   * Update Tests
   */
  // @Test
  public void testUpdate() throws Exception {
    Screening screening = new Screening("", "", "", "", "", "", "");
    inMemoryResource.client().target("screenings/abc").request().accept(MediaType.APPLICATION_JSON)
        .put(Entity.entity(screening, MediaType.APPLICATION_JSON));
    verify(resourceDelegate).update(eq("abc"), eq(screening));
  }

  /*
   * Update Tests
   */
  // @Test
  public void testUpdateInvalid() throws Exception {
    Screening screening = new Screening("", "", "", "screeningDecision", "screeningDecisionDetail",
        "assignee", "2017-01-01");
    int actualStatus = inMemoryResource.client().target("screenings/abc").request()
        .accept(MediaType.APPLICATION_JSON)
        .put(Entity.entity(screening, MediaType.APPLICATION_JSON)).getStatus();
    int expectedStatus = 422;
    assertThat(actualStatus, is(expectedStatus));
  }
}
