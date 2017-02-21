package gov.ca.cwds.rest.resources;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;

import javax.ws.rs.core.MediaType;

import org.hamcrest.junit.ExpectedException;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mockito;

import com.squarespace.jersey2.guice.JerseyGuiceUtils;

import gov.ca.cwds.rest.resources.cms.JerseyGuiceRule;
import io.dropwizard.testing.junit.ResourceTestRule;

/**
 * NOTE : The CWDS API Team has taken the pattern of delegating Resource functions to
 * {@link ServiceBackedResourceDelegate}. As such the tests in here reflect that assumption.
 * 
 * @author CWDS API Team
 */
@SuppressWarnings("javadoc")
public class AddressValidationResourceTest {

  private static final String ROOT_RESOURCE = "/address_validation/";
  private static final String FOUND_RESOURCE = "/address_validation/1";

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
  public final static ResourceTestRule inMemoryResource = ResourceTestRule.builder()
      .addResource(new AddressValidationResource(resourceDelegate)).build();

  @Before
  public void setup() throws Exception {
    Mockito.reset(resourceDelegate);
  }

  /*
   * 404 test for unimplemented methods
   */
  @Test
  public void deleteReturns404() throws Exception {
    int receivedStatus = inMemoryResource.client().target(FOUND_RESOURCE).request()
        .accept(MediaType.APPLICATION_JSON).delete().getStatus();
    int expectedStatus = 404;
    assertThat(receivedStatus, is(expectedStatus));
  }

  @Test
  public void getReturns404() throws Exception {
    int receivedStatus = inMemoryResource.client().target(FOUND_RESOURCE).request()
        .accept(MediaType.APPLICATION_JSON).get().getStatus();
    int expectedStatus = 404;
    assertThat(receivedStatus, is(expectedStatus));

  }

  @Test
  public void createReturns404() throws Exception {
    int receivedStatus = inMemoryResource.client().target(FOUND_RESOURCE).request()
        .accept(MediaType.APPLICATION_JSON).post(null).getStatus();
    int expectedStatus = 404;
    assertThat(receivedStatus, is(expectedStatus));
  }


  /*
   * Create Tests
   */
  // @Test
  // public void createDelegatesToResourceDelegate() throws Exception {
  // Address address = new Address("742 Evergreen Terrace", "Springfield", "WA", 98700, "Home");
  // ValidatedAddress[] validatedaddress = new ValidatedAddress[1];
  // validatedaddress[0] = new ValidatedAddress(null, null, null, null, null, null, false);
  // int status =
  // inMemoryResource.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
  // .post(Entity.entity(address, MediaType.APPLICATION_JSON)).getStatus();
  // System.out.println("Status = " + status);
  // System.out.println("resource del " + resourceDelegate.create(address));
  // verify(resourceDelegate).create(eq(address));
  // }

}
