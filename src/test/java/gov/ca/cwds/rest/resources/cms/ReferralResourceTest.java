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

import gov.ca.cwds.rest.api.domain.cms.Referral;
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
public class ReferralResourceTest {

  private static final String ROOT_RESOURCE = "/_referrals/";
  private static final String FOUND_RESOURCE = "/_referrals/abc";

  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

  @SuppressWarnings("javadoc")
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  private final static ResourceDelegate resourceDelegate = mock(ResourceDelegate.class);

  @SuppressWarnings("javadoc")
  @ClassRule
  public final static ResourceTestRule inMemoryResource =
      ResourceTestRule.builder().addResource(new ReferralResource(resourceDelegate)).build();

  @SuppressWarnings("javadoc")
  @Before
  public void setup() throws Exception {}

  /*
   * Get Tests
   */
  @SuppressWarnings("javadoc")
  @Test
  public void getDelegatesToResourceDelegate() throws Exception {
    inMemoryResource.client().target(FOUND_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
        .get();
    verify(resourceDelegate).get("abc");
  }

  /*
   * Create Tests
   */
  @SuppressWarnings("javadoc")
  @Test
  public void createDelegatesToResourceDelegate() throws Exception {
    Referral serialized = MAPPER
        .readValue(fixture("fixtures/domain/legacy/Referral/valid/valid.json"), Referral.class);

    inMemoryResource.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
        .post(Entity.entity(serialized, MediaType.APPLICATION_JSON));
    verify(resourceDelegate).create(eq(serialized));
  }

  @SuppressWarnings("javadoc")
  @Test
  public void testPost422ValidationError() throws Exception {
    Referral serialized = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Referral/invalid/closureDateWrongFormat.json"),
        Referral.class);

    int status =
        inMemoryResource.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(serialized, MediaType.APPLICATION_JSON)).getStatus();
    assertThat(status, is(422));
  }

  @SuppressWarnings("javadoc")
  @Test
  public void testPost204ResourceSuccess() throws Exception {
    Referral serialized = MAPPER
        .readValue(fixture("fixtures/domain/legacy/Referral/valid/valid.json"), Referral.class);
    Integer status = inMemoryResource.client().target(FOUND_RESOURCE).request()
        .accept(MediaType.APPLICATION_JSON)
        .put(Entity.entity(serialized, MediaType.APPLICATION_JSON)).getStatus();
    assertThat(status, is(204));

  }

  /*
   * Delete Tests
   */
  @SuppressWarnings("javadoc")
  @Test
  public void deleteDelegatesToResourceDelegate() throws Exception {
    inMemoryResource.client().target(FOUND_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
        .delete();
    verify(resourceDelegate).delete("abc");
  }

  /*
   * Update Tests
   */
  // @SuppressWarnings("javadoc")
  // @Test
  // public void udpateDelegatesToResourceDelegate() throws Exception {
  // Referral serialized = MAPPER
  // .readValue(fixture("fixtures/domain/legacy/Referral/valid/valid.json"), Referral.class);
  //
  // inMemoryResource.client().target(FOUND_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
  // .put(Entity.entity(serialized, MediaType.APPLICATION_JSON));
  // verify(resourceDelegate).update(eq("abc"), eq(serialized));
  // }

  @SuppressWarnings("javadoc")
  @Test
  public void testUpdate422ValidationError() throws Exception {
    Referral serialized = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Referral/invalid/closureDateWrongFormat.json"),
        Referral.class);

    int status = inMemoryResource.client().target(FOUND_RESOURCE).request()
        .accept(MediaType.APPLICATION_JSON)
        .put(Entity.entity(serialized, MediaType.APPLICATION_JSON)).getStatus();
    assertThat(status, is(422));
  }

  @SuppressWarnings("javadoc")
  @Test
  public void testUpdate204ResourceSuccess() throws Exception {
    Referral serialized = MAPPER
        .readValue(fixture("fixtures/domain/legacy/Referral/valid/valid.json"), Referral.class);
    Integer status = inMemoryResource.client().target(FOUND_RESOURCE).request()
        .accept(MediaType.APPLICATION_JSON)
        .put(Entity.entity(serialized, MediaType.APPLICATION_JSON)).getStatus();
    assertThat(status, is(204));
  }
}
