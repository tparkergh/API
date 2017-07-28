package gov.ca.cwds.rest.resources;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

import org.hamcrest.junit.ExpectedException;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mockito;

import com.squarespace.jersey2.guice.JerseyGuiceUtils;

import gov.ca.cwds.rest.api.domain.Address;
import gov.ca.cwds.rest.resources.cms.JerseyGuiceRule;
import io.dropwizard.testing.junit.ResourceTestRule;

/**
 * NOTE : The CWDS API Team has taken the pattern of delegating Resource functions to
 * {@link ServiceBackedResourceDelegate}. As such the tests in here reflect that assumption.
 * 
 * @author CWDS API Team
 */
public class AddressResourceTest {

  private static final String ROOT_RESOURCE = "/addresses/";
  private static final String FOUND_RESOURCE = "/addresses/1";
  private static final String NOT_FOUND_RESOURCE = "/addresses/X";

  @SuppressWarnings("javadoc")
  @After
  public void ensureServiceLocatorPopulated() {
    JerseyGuiceUtils.reset();
  }

  @SuppressWarnings("javadoc")
  @ClassRule
  public static JerseyGuiceRule rule = new JerseyGuiceRule();

  @SuppressWarnings("javadoc")
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  private final static ResourceDelegate resourceDelegate = mock(ResourceDelegate.class);

  @SuppressWarnings("javadoc")
  @ClassRule
  public final static ResourceTestRule inMemoryResource =
      ResourceTestRule.builder().addResource(new AddressResource(resourceDelegate)).build();

  @SuppressWarnings("javadoc")
  @Before
  public void setup() throws Exception {
    Mockito.reset(resourceDelegate);
  }

  /*
   * Get Tests
   */
  @SuppressWarnings("javadoc")
  @Test
  public void testGetDelegatesToResourceDelegate() throws Exception {
    inMemoryResource.client().target(FOUND_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
        .get().getStatus();
    verify(resourceDelegate, atLeastOnce()).get(1L);

  }

  @SuppressWarnings("javadoc")
  @Test
  public void testGet204NoContentSuccess() throws Exception {

    int status = inMemoryResource.client().target(FOUND_RESOURCE).request()
        .accept(MediaType.APPLICATION_JSON).get().getStatus();

    assertThat(status, is(204));

  }

  @SuppressWarnings("javadoc")
  @Test
  public void testGet404NotFoundError() throws Exception {
    int status = inMemoryResource.client().target(NOT_FOUND_RESOURCE).request()
        .accept(MediaType.APPLICATION_JSON).get().getStatus();

    assertThat(status, is(404));

  }

  /*
   * Create Tests
   */
  @SuppressWarnings("javadoc")
  @Test
  public void testPostDelegatesToResourceDelegate() throws Exception {
    Address address = new Address("", "", "742 Evergreen Terrace", "Springfield", "WA", 98700, 32);
    inMemoryResource.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
        .post(Entity.entity(address, MediaType.APPLICATION_JSON)).getStatus();
    verify(resourceDelegate, atLeastOnce()).create(eq(address));
  }

  @SuppressWarnings("javadoc")
  @Test
  public void testPostValidatesEntity() throws Exception {
    Address address = new Address("", "", "123456789012345678901234567890123456789012345678901",
        "Springfield", "WA", 98700, 32);
    int status =
        inMemoryResource.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(address, MediaType.APPLICATION_JSON)).getStatus();
    assertThat(status, is(422));

  }

  @SuppressWarnings("javadoc")
  @Test
  public void testPost200ResourceSuccess() throws Exception {
    Address address = new Address("", "", "742 Evergreen Terrace", "Springfield", "WA", 98700, 32);
    int status =
        inMemoryResource.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(address, MediaType.APPLICATION_JSON)).getStatus();
    assertThat(status, is(204));


  }

  /*
   * Delete Tests
   */
  @SuppressWarnings("javadoc")
  @Test
  public void testDelete501NotImplemented() throws Exception {
    int receivedStatus = inMemoryResource.client().target(FOUND_RESOURCE).request()
        .accept(MediaType.APPLICATION_JSON).delete().getStatus();
    int expectedStatus = 501;
    assertThat(receivedStatus, is(expectedStatus));

  }

  /*
   * Update Tests
   */
  @SuppressWarnings("javadoc")
  @Test
  public void testUpdate501NotImplemented() throws Exception {
    Address address = new Address("", "", "742 Evergreen Terrace", "Springfield", "WA", 98700, 32);
    int status =
        inMemoryResource.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .put(Entity.entity(address, MediaType.APPLICATION_JSON)).getStatus();
    assertThat(status, is(501));
  }

}
