package gov.ca.cwds.rest.resources.cms;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

import org.hamcrest.junit.ExpectedException;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.mockito.Mockito;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.squarespace.jersey2.guice.JerseyGuiceUtils;

import gov.ca.cwds.rest.api.domain.cms.ChildClient;
import gov.ca.cwds.rest.resources.ResourceDelegate;
import gov.ca.cwds.rest.resources.ServiceBackedResourceDelegate;
import io.dropwizard.jackson.Jackson;
import io.dropwizard.testing.junit.ResourceTestRule;

/**
 * NOTE : The CWDS API Team has taken the pattern of delegating Resource functions to
 * {@link ServiceBackedResourceDelegate}. As such the tests in here reflect that assumption.
 * 
 * @author CWDS API Team
 */
@SuppressWarnings("javadoc")
public class ChildClientResourceTest {

  private static final String ROOT_RESOURCE = "/_childClients/";
  private static final String FOUND_RESOURCE = "/_childClients/victimId=AbiOD9Y0Hj";

  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

  @After
  public void ensureServiceLocatorPopulated() {
    JerseyGuiceUtils.reset();
  }

  @ClassRule
  public static JerseyGuiceRule rule = new JerseyGuiceRule();

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  private final static ResourceDelegate resourceDelegate = mock(ResourceDelegate.class);

  @ClassRule
  public final static ResourceTestRule inMemoryResource =
      ResourceTestRule.builder().addResource(new ChildClientResource(resourceDelegate)).build();

  @Before
  public void setup() throws Exception {
    Mockito.reset(resourceDelegate);
  }

  /*
   * Get Tests
   */
  // @Test
  public void getDelegatesToResourceDelegate() throws Exception {
    inMemoryResource.client().target(FOUND_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
        .get();
    verify(resourceDelegate).get("AbiOD9Y0Hj");
  }

  /*
   * Create Tests
   */
  // @Test
  public void createDelegatesToResourceDelegate() throws Exception {
    ChildClient serialized = MAPPER.readValue(
        fixture("fixtures/domain/legacy/ChildClient/valid/valid.json"), ChildClient.class);

    inMemoryResource.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
        .post(Entity.entity(serialized, MediaType.APPLICATION_JSON));
    verify(resourceDelegate).create(eq(serialized));
  }

  // @Test
  public void createValidatesEntity() throws Exception {
    ChildClient serialized = MAPPER.readValue(
        fixture(
            "fixtures/domain/legacy/ChildClient/invalid/tribalCustomaryAdoptionDate/invalidFormat.json"),
        ChildClient.class);

    int status =
        inMemoryResource.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(serialized, MediaType.APPLICATION_JSON)).getStatus();
    assertThat(status, is(422));
  }

  /*
   * Delete Tests
   */
  // @Test
  public void deleteDelegatesToResourceDelegate() throws Exception {
    inMemoryResource.client().target(FOUND_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
        .delete();
    verify(resourceDelegate).delete("AbiOD9Y0Hj");
  }

  /*
   * Update Tests
   */
  // @Test
  public void updateDelegatesToResourceDelegate() throws Exception {
    ChildClient serialized = MAPPER.readValue(
        fixture("fixtures/domain/legacy/ChildClient/valid/valid.json"), ChildClient.class);

    inMemoryResource.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
        .put(Entity.entity(serialized, MediaType.APPLICATION_JSON));
    verify(resourceDelegate).update(eq(null), eq(serialized));
  }

  // @Test
  public void updateValidatesEntity() throws Exception {
    ChildClient serialized = MAPPER.readValue(
        fixture(
            "fixtures/domain/legacy/ChildClient/invalid/tribalCustomaryAdoptionDate/invalidFormat.json"),
        ChildClient.class);

    int status =
        inMemoryResource.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .put(Entity.entity(serialized, MediaType.APPLICATION_JSON)).getStatus();
    assertThat(status, is(422));
  }

}
