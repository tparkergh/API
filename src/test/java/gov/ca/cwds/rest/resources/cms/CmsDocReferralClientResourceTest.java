package gov.ca.cwds.rest.resources.cms;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import javax.ws.rs.core.MediaType;

import org.hamcrest.junit.ExpectedException;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mockito;

import gov.ca.cwds.rest.resources.ResourceDelegate;
import gov.ca.cwds.rest.resources.ServiceBackedResourceDelegate;
import io.dropwizard.testing.junit.ResourceTestRule;


/**
 * NOTE : The CWDS API Team has taken the pattern of delegating Resource functions to
 * {@link ServiceBackedResourceDelegate}. As such the tests in here reflect that assumption.
 * 
 * @author CWDS API Team
 */
public class CmsDocReferralClientResourceTest {

  private static final String FOUND_RESOURCE = "/cmsdocreferralclient/abc";

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  private final static ResourceDelegate resourceDelegate = mock(ResourceDelegate.class);

  @ClassRule
  public final static ResourceTestRule inMemoryResource = ResourceTestRule.builder()
      .addResource(new CmsDocReferralClientResource(resourceDelegate)).build();

  @Before
  public void setup() throws Exception {
    Mockito.reset(resourceDelegate);
  }

  /**
   * Get Tests
   * 
   * @throws Exception required for test
   */
  @Test
  public void getDelegatesToResourceDelegate() throws Exception {
    inMemoryResource.client().target(FOUND_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
        .get();
    verify(resourceDelegate).get("abc");
  }

  /**
   * Create Tests
   * <p>
   * <strong>NOT YET IMPLEMENTED</strong>
   * </p>
   */
  // @Test
  // public void createValidatesEntity() throws Exception {
  // CmsDocReferralClient serialized =
  // MAPPER.readValue(fixture("fixtures/domain/cms/CmsDocReferralClient/invalid/invalid.json"),
  // CmsDocReferralClient.class);
  //
  // final Response resp =
  // inMemoryResource.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
  // .post(Entity.entity(serialized, MediaType.APPLICATION_JSON));
  //
  // final int status = resp.getStatus();
  //
  // System.out.println("status=" + status);
  //
  // ValidationErrorMessage msg = resp.readEntity(ValidationErrorMessage.class);
  // if (msg != null) {
  // final List<String> errors = msg.getErrors();
  // if (errors != null && errors.size() > 0) {
  // for (String err : errors) {
  // System.out.println("ERROR: " + err);
  // }
  // }
  // }
  //
  // assertThat(status, is(422));
  // }
}
