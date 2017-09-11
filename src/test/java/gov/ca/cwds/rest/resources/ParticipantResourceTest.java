package gov.ca.cwds.rest.resources;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

import org.hamcrest.junit.ExpectedException;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mockito;

import gov.ca.cwds.rest.api.domain.Address;
import gov.ca.cwds.rest.api.domain.LegacyDescriptor;
import gov.ca.cwds.rest.api.domain.Participant;
import io.dropwizard.testing.junit.ResourceTestRule;

/**
 * NOTE : The CWDS API Team has taken the pattern of delegating Resource functions to
 * {@link ServiceBackedResourceDelegate}. As such the tests in here reflect that assumption.
 * 
 * @author CWDS API Team
 */
@SuppressWarnings("javadoc")
public class ParticipantResourceTest {

  private static final String ROOT_RESOURCE = "/participants/";
  private static final String FOUND_RESOURCE = "/participants/1";
  private final Short primaryLanguage = 1253;
  private final Short secondaryLanguage = 1271;
  private boolean reporterConfidentialWaiver = true;
  private String reporterEmployerName = "Employer Name";
  private boolean clientStaffPersonAdded = true;
  private String sensitivityIndicator = "R";
  private Set<String> roles = new HashSet<String>();
  private Set<Address> addresses = new HashSet<Address>();


  @Rule
  public ExpectedException thrown = ExpectedException.none();

  private final static ResourceDelegate resourceDelegate = mock(ResourceDelegate.class);

  @ClassRule
  public final static ResourceTestRule inMemoryResource =
      ResourceTestRule.builder().addResource(new ParticipantResource(resourceDelegate)).build();

  @Before
  public void setup() throws Exception {
    Mockito.reset(resourceDelegate);
  }

  @Test
  public void testGet404NotFoundError() throws Exception {
    int receivedStatus = inMemoryResource.client().target(FOUND_RESOURCE).request()
        .accept(MediaType.APPLICATION_JSON).get().getStatus();
    int expectedStatus = 404;
    assertThat(receivedStatus, is(expectedStatus));
  }

  // @Test
  public void testPostDelegatesToResourceDelegate() throws Exception {
    // roles.add("victim");
    // Address address = new Address("", "", "123 First St", "San Jose", "CA", 94321, "Home");
    // addresses.add(address);
    // Participant participant = new Participant(1, "", "", "Marge", "Simpson", "Female",
    // "11122333",
    // "11-01-2017", 123, 456, roles, addresses);
    //
    // inMemoryResource.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
    // .post(Entity.entity(participant, MediaType.APPLICATION_JSON));
    //
    // verify(resourceDelegate, atLeastOnce()).create(eq(participant));
  }

  @Test
  public void testPostValidatesEntity() throws Exception {
    roles.add("victim");
    Address address = new Address("", "", "123 First St", "San Jose", 1828, "94321", 32);
    addresses.add(address);
    Participant participant = new Participant(1, "", "", new LegacyDescriptor(), "Marge", "J",
        "Simpson", "F", "", "11122333", "11-01-2017", primaryLanguage, secondaryLanguage, 123, 456,
        reporterConfidentialWaiver, reporterEmployerName, clientStaffPersonAdded,
        sensitivityIndicator, roles, addresses);

    int status =
        inMemoryResource.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(participant, MediaType.APPLICATION_JSON)).getStatus();
    assertThat(status, is(422));
  }

  @Test
  public void testDelete200ResourceSuccess() throws Exception {
    roles.add("victim");
    Address address = new Address("", "", "123 First St", "San Jose", 1828, "94321", 32);
    addresses.add(address);
    Participant participant = new Participant(1, "", "", new LegacyDescriptor(), "Marge", "J",
        "Simpson", "", "F", "111223333", "2017-01-23", primaryLanguage, secondaryLanguage, 123, 456,
        reporterConfidentialWaiver, reporterEmployerName, clientStaffPersonAdded,
        sensitivityIndicator, roles, addresses);

    int status =
        inMemoryResource.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(participant, MediaType.APPLICATION_JSON)).getStatus();
    assertThat(status, is(204));

  }

  @Test
  public void testDelete404NotFoundError() throws Exception {
    int receivedStatus = inMemoryResource.client().target(FOUND_RESOURCE).request()
        .accept(MediaType.APPLICATION_JSON).delete().getStatus();
    int expectedStatus = 404;
    assertThat(receivedStatus, is(expectedStatus));

  }

  @Test
  public void testUpdate404NotFoundError() throws Exception {
    roles.add("victim");
    Address address = new Address("", "", "123 First St", "San Jose", 1828, "94321", 32);
    addresses.add(address);
    Participant participant = new Participant(1, "", "", new LegacyDescriptor(), "Marge", "J",
        "Simpson", "", "Female", "111223333", "2017-01-11", primaryLanguage, secondaryLanguage, 123,
        456, reporterConfidentialWaiver, reporterEmployerName, clientStaffPersonAdded,
        sensitivityIndicator, roles, addresses);
    int receivedStatus =
        inMemoryResource.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .put(Entity.entity(participant, MediaType.APPLICATION_JSON)).getStatus();
    int expectedStatus = 405;
    assertThat(receivedStatus, is(expectedStatus));
  }
}
