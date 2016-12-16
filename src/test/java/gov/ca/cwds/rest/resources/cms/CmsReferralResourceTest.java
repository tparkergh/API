package gov.ca.cwds.rest.resources.cms;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

import org.hamcrest.junit.ExpectedException;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import gov.ca.cwds.rest.api.domain.cms.CmsReferral;
import gov.ca.cwds.rest.resources.ServiceBackedResourceDelegate;
import io.dropwizard.jackson.Jackson;
import io.dropwizard.testing.junit.ResourceTestRule;

/**
 * NOTE : The CWDS API Team has taken the pattern of delegating Resource functions to
 * {@link ServiceBackedResourceDelegate}. As such the tests in here reflect that assumption.
 * 
 * @author CWDS API Team
 * 
 */

public class CmsReferralResourceTest {

  private static final String ROOT_RESOURCE = "/_cmsreferrals/";

  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  // private final static ResourceDelegate resourceDelegate = mock(ResourceDelegate.class);
  private static final CmsReferralResource mockedCmsReferralResource =
      mock(CmsReferralResource.class);

  @ClassRule
  public final static ResourceTestRule inMemoryResource =
      ResourceTestRule.builder().addResource(mockedCmsReferralResource).build();

  @Before
  public void setup() throws Exception {}

  /*
   * Create Tests
   */
  // @Test
  // public void createDelegatesToResourceDelegate() throws Exception {
  //
  // CmsReferral serialized = MAPPER.readValue(
  // fixture("fixtures/domain/cms/CmsReferral/valid/cmsReferral.json"), CmsReferral.class);
  //
  // inMemoryResource.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
  // .post(Entity.entity(serialized, MediaType.APPLICATION_JSON));
  //
  // verify(mockedCmsReferralResource).post(eq(serialized));
  //
  // }

  @Test
  public void createValidatesEntity() throws Exception {
    CmsReferral serialized =
        MAPPER.readValue(fixture("fixtures/domain/cms/CmsReferral/invalid/cmsReferralInvalid.json"),
            CmsReferral.class);

    int status =
        inMemoryResource.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(serialized, MediaType.APPLICATION_JSON)).getStatus();
    assertThat(status, is(422));
  }

}
