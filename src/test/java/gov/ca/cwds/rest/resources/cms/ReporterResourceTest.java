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
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mockito;

import com.fasterxml.jackson.databind.ObjectMapper;

import gov.ca.cwds.rest.api.domain.cms.Reporter;
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
public class ReporterResourceTest {

  private static final String ROOT_RESOURCE = "/_reporters/";
  private static final String FOUND_RESOURCE = "/_reporters/referralId=AbiQCgu0Hj";

  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  private final static ResourceDelegate resourceDelegate = mock(ResourceDelegate.class);

  @ClassRule
  public final static ResourceTestRule inMemoryResource =
      ResourceTestRule.builder().addResource(new ReporterResource(resourceDelegate)).build();

  @Before
  public void setup() throws Exception {
    Mockito.reset(resourceDelegate);
  }

  /*
   * Get Tests
   */
  @Test
  public void getDelegatesToResourceDelegate() throws Exception {
    inMemoryResource.client().target(FOUND_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
        .get();
    verify(resourceDelegate).get("AbiQCgu0Hj");
  }

  /*
   * Create Tests
   */
  @Test
  public void createDelegatesToResourceDelegate() throws Exception {
    Reporter serialized = MAPPER
        .readValue(fixture("fixtures/domain/legacy/Reporter/valid/valid.json"), Reporter.class);

    inMemoryResource.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
        .post(Entity.entity(serialized, MediaType.APPLICATION_JSON));
    verify(resourceDelegate).create(eq(serialized));
  }

  @Test
  public void createValidatesEntity() throws Exception {
    Reporter serialized = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Reporter/invalid/feedbackDateWrongFormat.json"),
        Reporter.class);

    int status =
        inMemoryResource.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(serialized, MediaType.APPLICATION_JSON)).getStatus();
    assertThat(status, is(422));
  }

  /*
   * Delete Tests
   */
  @Test
  public void deleteDelegatesToResourceDelegate() throws Exception {
    inMemoryResource.client().target(FOUND_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
        .delete();
    verify(resourceDelegate).delete("AbiQCgu0Hj");
  }

  /*
   * Update Tests
   */
  @Test
  public void udpateDelegatesToResourceDelegate() throws Exception {
    Reporter serialized = MAPPER
        .readValue(fixture("fixtures/domain/legacy/Reporter/valid/valid.json"), Reporter.class);

    inMemoryResource.client().target(FOUND_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
        .put(Entity.entity(serialized, MediaType.APPLICATION_JSON));
    verify(resourceDelegate).update(eq("AbiQCgu0Hj"), eq(serialized));
  }

  @Test
  public void udpateValidatesEntity() throws Exception {
    Reporter serialized = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Reporter/invalid/feedbackDateWrongFormat.json"),
        Reporter.class);

    int status = inMemoryResource.client().target(FOUND_RESOURCE).request()
        .accept(MediaType.APPLICATION_JSON)
        .put(Entity.entity(serialized, MediaType.APPLICATION_JSON)).getStatus();
    assertThat(status, is(422));
  }
}
