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

import com.fasterxml.jackson.databind.ObjectMapper;

import gov.ca.cwds.rest.api.domain.cms.CrossReport;
import gov.ca.cwds.rest.resources.ResourceDelegate;
import gov.ca.cwds.rest.resources.ServiceBackedResourceDelegate;
import io.dropwizard.jackson.Jackson;
import io.dropwizard.testing.junit.ResourceTestRule;

/**
 * {@link ServiceBackedResourceDelegate}. As such the tests in here reflect that assumption.
 * 
 * @author CWDS API Team
 */
public class CrossReportResourceTest {

  private static final String ROOT_RESOURCE = "/_crossReports/";
  private static final String FOUND_RESOURCE = "/_crossReports/thirdId=ABC1234567";

  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  private final static ResourceDelegate resourceDelegate = mock(ResourceDelegate.class);

  @ClassRule
  public final static ResourceTestRule inMemoryResource =
      ResourceTestRule.builder().addResource(new CrossReportResource(resourceDelegate)).build();

  @Before
  public void setup() throws Exception {}

  /*
   * Get Tests
   */
  @Test
  public void getDelegatesToResourceDelegate() throws Exception {
    inMemoryResource.client().target(FOUND_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
        .get();
    verify(resourceDelegate).get("ABC1234567");
  }

  /*
   * Create Tests
   */
  @Test
  public void createDelegatesToResourceDelegate() throws Exception {
    CrossReport serialized = MAPPER.readValue(
        fixture("fixtures/domain/legacy/CrossReport/valid/valid.json"), CrossReport.class);

    inMemoryResource.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
        .post(Entity.entity(serialized, MediaType.APPLICATION_JSON));
    verify(resourceDelegate).create(eq(serialized));
  }

  @Test
  public void createValidatesEntity() throws Exception {
    CrossReport serialized = MAPPER.readValue(
        fixture("fixtures/domain/legacy/CrossReport/invalid/informDate/invalidFormat.json"),
        CrossReport.class);

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
    verify(resourceDelegate).delete("ABC1234567");
  }

  /*
   * Update Tests
   */
  @Test
  public void udpateDelegatesToResourceDelegate() throws Exception {
    CrossReport serialized = MAPPER.readValue(
        fixture("fixtures/domain/legacy/CrossReport/valid/valid.json"), CrossReport.class);

    inMemoryResource.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
        .put(Entity.entity(serialized, MediaType.APPLICATION_JSON));
    verify(resourceDelegate).update(eq(null), eq(serialized));
  }

  @Test
  public void udpateValidatesEntity() throws Exception {
    CrossReport serialized = MAPPER.readValue(
        fixture("fixtures/domain/legacy/CrossReport/invalid/informDate/invalidFormat.json"),
        CrossReport.class);

    int status =
        inMemoryResource.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .put(Entity.entity(serialized, MediaType.APPLICATION_JSON)).getStatus();
    assertThat(status, is(422));
  }
}
