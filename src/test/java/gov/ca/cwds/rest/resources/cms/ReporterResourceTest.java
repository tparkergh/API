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
import gov.ca.cwds.rest.resource.junit.template.ResourceTestTemplate;
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
public class ReporterResourceTest implements ResourceTestTemplate {

  private static final String ROOT_RESOURCE = "/_reporters/";
  private static final String FOUND_RESOURCE = "/_reporters/referralId=AbiQCgu0Hj";

  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

  @SuppressWarnings("javadoc")
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  private final static ResourceDelegate resourceDelegate = mock(ResourceDelegate.class);

  @SuppressWarnings("javadoc")
  @ClassRule
  public final static ResourceTestRule inMemoryResource =
      ResourceTestRule.builder().addResource(new ReporterResource(resourceDelegate)).build();

  @SuppressWarnings("javadoc")
  @Before
  public void setup() throws Exception {
    Mockito.reset(resourceDelegate);
  }

  /*
   * Get Tests
   */
  @Override
  @Test
  public void testGetDelegatesToResourceDelegate() throws Exception {
    inMemoryResource.client().target(FOUND_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
        .get();
    verify(resourceDelegate).get("AbiQCgu0Hj");
  }

  @Override
  public void testGet201ResourceSuccess() throws Exception {
    // TODO Auto-generated method stub

  }

  @Override
  public void testGet404NotFoundError() throws Exception {
    // TODO Auto-generated method stub

  }

  @Override
  public void testGet501NotImplemented() throws Exception {
    // TODO Auto-generated method stub

  }

  /*
   * Create Tests
   */
  @Override
  @Test
  public void testPostDelegatesToResourceDelegate() throws Exception {
    Reporter serialized = MAPPER
        .readValue(fixture("fixtures/domain/legacy/Reporter/valid/valid.json"), Reporter.class);

    inMemoryResource.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
        .post(Entity.entity(serialized, MediaType.APPLICATION_JSON));
    verify(resourceDelegate).create(eq(serialized));
  }

  @Override
  @Test
  public void testPostValidatesEntity() throws Exception {
    Reporter serialized = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Reporter/invalid/feedbackDateWrongFormat.json"),
        Reporter.class);

    int status =
        inMemoryResource.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(serialized, MediaType.APPLICATION_JSON)).getStatus();
    assertThat(status, is(422));
  }

  @Override
  public void testPost400JSONError() throws Exception {
    // TODO Auto-generated method stub

  }

  @Override
  public void testPost406NotSupportedError() throws Exception {
    // TODO Auto-generated method stub

  }

  @Override
  public void testPost409AlreadyExistsError() throws Exception {
    // TODO Auto-generated method stub

  }

  @Override
  public void testPost422ValidationError() throws Exception {
    // TODO Auto-generated method stub

  }

  @Override
  @Test
  public void testPost200ResourceSuccess() throws Exception {
    Reporter serialized = MAPPER
        .readValue(fixture("fixtures/domain/legacy/Reporter/valid/valid.json"), Reporter.class);

    int status =
        inMemoryResource.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(serialized, MediaType.APPLICATION_JSON)).getStatus();
    assertThat(status, is(204));

  }

  @Override
  public void testPost501NotImplemented() throws Exception {
    //

  }

  /*
   * Delete Tests
   */
  @Override
  @Test
  public void testDeleteDelegatesToResource() throws Exception {
    inMemoryResource.client().target(FOUND_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
        .delete();
    verify(resourceDelegate).delete("AbiQCgu0Hj");
  }

  @Override
  @Test
  public void testDelete200ResourceSuccess() throws Exception {
    int status = inMemoryResource.client().target(ROOT_RESOURCE).request()
        .accept(MediaType.APPLICATION_JSON).delete().getStatus();
    assertThat(status, is(204));


  }

  @Override
  public void testDelete404NotFoundError() throws Exception {
    // TODO Auto-generated method stub

  }

  @Override
  public void testDelete501NotImplemented() throws Exception {
    //

  }

  /*
   * Update Tests
   */
  @Override
  @Test
  public void testUpdateDelegatesToResourceDelegate() throws Exception {
    Reporter serialized = MAPPER
        .readValue(fixture("fixtures/domain/legacy/Reporter/valid/valid.json"), Reporter.class);

    inMemoryResource.client().target(FOUND_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
        .put(Entity.entity(serialized, MediaType.APPLICATION_JSON));
    verify(resourceDelegate).update(eq("AbiQCgu0Hj"), eq(serialized));
  }

  @Override
  @Test
  public void testUpdate422ValidationError() throws Exception {
    Reporter serialized = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Reporter/invalid/feedbackDateWrongFormat.json"),
        Reporter.class);

    int status = inMemoryResource.client().target(FOUND_RESOURCE).request()
        .accept(MediaType.APPLICATION_JSON)
        .put(Entity.entity(serialized, MediaType.APPLICATION_JSON)).getStatus();
    assertThat(status, is(422));
  }

  @Override
  public void testUpdate200ResourceSuccess() throws Exception {
    // TODO Auto-generated method stub

  }

  @Override
  public void testUpdate400JSONError() throws Exception {
    // TODO Auto-generated method stub

  }

  @Override
  public void testUpdate404NotFoundError() throws Exception {
    // TODO Auto-generated method stub

  }

  @Override
  public void testUpdate406NotSupportedError() throws Exception {
    // TODO Auto-generated method stub

  }

  @Override
  public void testUpdate501NotImplemented() throws Exception {
    //

  }
}
