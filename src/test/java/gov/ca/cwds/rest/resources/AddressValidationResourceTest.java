package gov.ca.cwds.rest.resources;

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

import gov.ca.cwds.rest.api.domain.Address;
import gov.ca.cwds.rest.api.domain.ValidatedAddress;
import io.dropwizard.testing.junit.ResourceTestRule;

/**
 * NOTE : The CWDS API Team has taken the pattern of delegating Resource functions to
 * {@link ServiceBackedResourceDelegate}. As such the tests in here reflect that assumption.
 * 
 * @author CWDS API Team
 */
public class AddressValidationResourceTest {

  private static final String ROOT_RESOURCE = "/address_validation/";
  private static final String FOUND_RESOURCE = "/address_validation/1";


  @Rule
  public ExpectedException thrown = ExpectedException.none();

  private final static ResourceDelegate resourceDelegate = mock(ResourceDelegate.class);

  @ClassRule
  public final static ResourceTestRule inMemoryResource =
      ResourceTestRule.builder().addResource(new AddressResource(resourceDelegate)).build();

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
  @Test
  public void createDelegatesToResourceDelegate() throws Exception {
    Address address = new Address("742 Evergreen Terrace", "Springfield", "WA", 98700);
    ValidatedAddress[] validatedaddress = new ValidatedAddress[1];
    validatedaddress[0] = new ValidatedAddress(null, null, null, null, null, null, false);
    inMemoryResource.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
        .post(Entity.entity(address, MediaType.APPLICATION_JSON)).getStatus();
    System.out.println("resource del " + resourceDelegate.create(address));
    verify(resourceDelegate).create(eq(address));
  }

}
