package gov.ca.cwds.rest.resources;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.mockito.Mockito;

import com.fasterxml.jackson.databind.ObjectMapper;

import gov.ca.cwds.rest.api.domain.Screening;
import gov.ca.cwds.rest.resources.cms.JerseyGuiceRule;
import io.dropwizard.testing.junit.ResourceTestRule;

/**
 * NOTE : The CWDS API Team has taken the pattern of delegating Resource functions to
 * {@link ServiceBackedResourceDelegate}. As such the tests in here reflect that assumption.
 * 
 * @author CWDS API Team
 */
public class ScreeningResourceTest {

  private static final String ROOT_RESOURCE = "/screenings/";

  private static final ResourceDelegate resourceDelegate = mock(ResourceDelegate.class);

  @ClassRule
  public static final JerseyGuiceRule rule = new JerseyGuiceRule();

  @ClassRule
  public static final ResourceTestRule inMemoryResource =
      ResourceTestRule.builder().addResource(new ScreeningResource(resourceDelegate)).build();

  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  @Before
  public void setup() throws Exception {
    Mockito.reset(resourceDelegate);
  }

  /*
   * Create Tests
   */
  @Test
  public void testCreate() throws Exception {
    Screening screening = new Screening("abc", "screening", "reference", "screeningDecision",
        "screeningDecisionDetail", "assignee", "2017-01-01");
    int actualStatus =
        inMemoryResource.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(screening, MediaType.APPLICATION_JSON)).getStatus();
    int expectedStatus = 204; // in real web interaction this would be 201
    assertThat(actualStatus, is(expectedStatus));
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
  @Test
  public void testUpdate() throws Exception {
    Screening screening = new Screening("abc", "screening", "reference", "screeningDecision",
        "screeningDecisionDetail", "assignee", "2017-01-01");

    String screeningJson = OBJECT_MAPPER.writeValueAsString(screening);

    int actualStatus = inMemoryResource.client().target(ROOT_RESOURCE + "abc").request()
        .accept(MediaType.APPLICATION_JSON)
        .put(Entity.entity(screeningJson, MediaType.APPLICATION_JSON)).getStatus();

    int expectedStatus = 204; // in real web interaction this would be 200
    assertThat(actualStatus, is(expectedStatus));
    verify(resourceDelegate).update(eq("abc"), eq(screening));
  }

  /*
   * Update Tests
   */
  @Test
  public void testUpdateInvalid() throws Exception {
    Screening screening = new Screening("", "", "", "", "", "", "");

    int actualStatus = inMemoryResource.client().target(ROOT_RESOURCE + "abc").request()
        .accept(MediaType.APPLICATION_JSON)
        .put(Entity.entity(screening, MediaType.APPLICATION_JSON)).getStatus();

    int expectedStatus = 422;
    assertThat(actualStatus, is(expectedStatus));
  }
}
