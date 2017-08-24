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

import gov.ca.cwds.rest.api.domain.cms.ReferralClient;
import gov.ca.cwds.rest.resources.ServiceBackedResourceDelegate;
import gov.ca.cwds.rest.resources.TypedResourceDelegate;
import io.dropwizard.jackson.Jackson;
import io.dropwizard.testing.junit.ResourceTestRule;

/**
 * NOTE : The CWDS API Team has taken the pattern of delegating Resource functions to
 * {@link ServiceBackedResourceDelegate}. As such the tests in here reflect that assumption.
 * 
 * @author CWDS API Team
 **/

public class ReferralClientResourceTest {

  private static final String ROOT_RESOURCE = "/_referralClients/";
  private static final String FOUND_RESOURCE =
      "/_referralClients/referralId=ABC1234567,clientId=1234567ABC";

  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @SuppressWarnings("unchecked")
  private final static TypedResourceDelegate<String, ReferralClient> typedResourceDelegate =
      mock(TypedResourceDelegate.class);

  @ClassRule
  public final static ResourceTestRule inMemoryResource = ResourceTestRule.builder()
      .addResource(new ReferralClientResource(typedResourceDelegate)).build();

  @Before
  public void setup() throws Exception {
    Mockito.reset(typedResourceDelegate);
  }

  @Test
  public void getDelegatesToResourceDelegate() throws Exception {
    inMemoryResource.client().target(FOUND_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
        .get();
    verify(typedResourceDelegate).get("referralId=ABC1234567,clientId=1234567ABC");
  }

  /*
   * Create Tests
   */
  @Test
  public void createDelegatesToResourceDelegate() throws Exception {
    ReferralClient serialized = MAPPER.readValue(
        fixture("fixtures/domain/legacy/ReferralClient/valid/valid.json"), ReferralClient.class);

    inMemoryResource.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
        .post(Entity.entity(serialized, MediaType.APPLICATION_JSON));
    verify(typedResourceDelegate).create(eq(serialized));
  }

  @Test
  public void createValidatesEntity() throws Exception {
    ReferralClient serialized = MAPPER.readValue(
        fixture("fixtures/domain/legacy/ReferralClient/invalid/clientIdMissing.json"),
        ReferralClient.class);

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
    verify(typedResourceDelegate).delete("referralId=ABC1234567,clientId=1234567ABC");
  }

  /*
   * Update Tests
   */
  @Test
  public void udpateDelegatesToResourceDelegate() throws Exception {
    ReferralClient serialized = MAPPER.readValue(
        fixture("fixtures/domain/legacy/ReferralClient/valid/valid.json"), ReferralClient.class);

    inMemoryResource.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
        .put(Entity.entity(serialized, MediaType.APPLICATION_JSON));
    verify(typedResourceDelegate).update(eq(null), eq(serialized));
  }

  @Test
  public void udpateValidatesEntity() throws Exception {
    ReferralClient serialized = MAPPER.readValue(
        fixture("fixtures/domain/legacy/ReferralClient/invalid/clientIdMissing.json"),
        ReferralClient.class);

    int status =
        inMemoryResource.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .put(Entity.entity(serialized, MediaType.APPLICATION_JSON)).getStatus();
    assertThat(status, is(422));
  }

}
