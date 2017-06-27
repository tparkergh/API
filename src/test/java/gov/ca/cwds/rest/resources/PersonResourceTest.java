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
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mockito;

import gov.ca.cwds.rest.api.domain.Person;
import io.dropwizard.testing.junit.ResourceTestRule;

/**
 * NOTE : The CWDS API Team has taken the pattern of delegating Resource functions to
 * {@link ResourceDelegate}. As such the tests in here reflect that assumption.
 * 
 * @author CWDS API Team
 */
public class PersonResourceTest {
  private static final String ROOT_RESOURCE = "/people/";
  private static final String FOUND_RESOURCE = "/people/1";

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  private static ResourceDelegate resourceDelegate = mock(ResourceDelegate.class);

  @ClassRule
  public static final ResourceTestRule inMemoryResource =
      ResourceTestRule.builder().addResource(new PersonResource(resourceDelegate)).build();

  @SuppressWarnings("javadoc")
  @Before
  public void setup() throws Exception {
    Mockito.reset(resourceDelegate);
  }

  /**
   * Get Tests
   * 
   * @throws Exception required for test compilation
   */
  @Test
  public void getDelegatesToCrudsResource() throws Exception {
    inMemoryResource.client().target(FOUND_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
        .get().getStatus();
    verify(resourceDelegate).get(1L);
  }

  /**
   * Create Tests
   * 
   * @throws Exception required for test compilation
   */
  @Test
  public void createDelegatesToCrudsResource() throws Exception {
    Person person = new Person("firstname", "middle", "last", "","M", "1990-11-22", "000000000", null, null,
        null, null, null);
    inMemoryResource.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
        .post(Entity.entity(person, MediaType.APPLICATION_JSON));
    verify(resourceDelegate, atLeastOnce()).create(eq(person));
  }

  /**
   * Delete Tests
   * 
   * @throws Exception required for test compilation
   */
  @Test
  public void deleteReturns501() throws Exception {
    int receivedStatus = inMemoryResource.client().target(FOUND_RESOURCE).request()
        .accept(MediaType.APPLICATION_JSON).delete().getStatus();
    int expectedStatus = 501;
    assertThat(receivedStatus, is(expectedStatus));
  }

  /**
   * Update Tests
   * 
   * @throws Exception required for test compilation
   */
  @Test
  public void udpateReturns501() throws Exception {
    Person person = new Person("firstname", "middle", "last", "","M", "1973-11-22", "000000000", null, null,
        null, null, null);
    int status = inMemoryResource.client().target(FOUND_RESOURCE).request()
        .accept(MediaType.APPLICATION_JSON).put(Entity.entity(person, MediaType.APPLICATION_JSON))
        .getStatus();
    assertThat(status, is(501));
  }
}
