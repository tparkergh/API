package gov.ca.cwds.rest.api.domain;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.squarespace.jersey2.guice.JerseyGuiceUtils;

import gov.ca.cwds.data.persistence.junit.template.PersistentTestTemplate;
import gov.ca.cwds.rest.core.Api;
import gov.ca.cwds.rest.resources.ParticipantResource;
import gov.ca.cwds.rest.resources.cms.JerseyGuiceRule;
import io.dropwizard.jackson.Jackson;
import io.dropwizard.testing.junit.ResourceTestRule;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

/**
 * @author CWDS API Team
 *
 */
@SuppressWarnings("javadoc")
public class ParticipantTest implements PersistentTestTemplate {

  private long id = 5432;
  private String clientId = "1234567ABC";
  private long personId = 12345;
  private long screeningId = 12345;
  private String firstName = "John";
  private String lastName = "Smith";
  private String gender = "male";
  private String dateOfBirth = "2001-03-15";
  private String ssn = "123456789";
  private Set<String> roles = new HashSet<String>();
  private Set<Address> addresses = new HashSet<Address>();


  private static final String ROOT_RESOURCE = "/" + Api.RESOURCE_PARTICIPANTS + "/";;
  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

  private static final ParticipantResource mockedParticipantResource =
      mock(ParticipantResource.class);

  @After
  public void ensureServiceLocatorPopulated() {
    JerseyGuiceUtils.reset();
  }

  @ClassRule
  public static JerseyGuiceRule rule = new JerseyGuiceRule();

  @ClassRule
  public static final ResourceTestRule resources =
      ResourceTestRule.builder().addResource(mockedParticipantResource).build();

  @Before
  public void setup() {
    Participant validParticipant = this.validParticipant();
    roles.add("victim");
    Address address = new Address("123 First St", "San Jose", "CA", 94321, "Home");
    addresses.add(address);


    when(mockedParticipantResource.create(eq(validParticipant)))
        .thenReturn(Response.status(Response.Status.NO_CONTENT).entity(null).build());

  }

  /*
   * Serialization and de-serialization
   */
  @Test
  public void serializesToJSON() throws Exception {
    String expected = MAPPER.writeValueAsString(validParticipant());

    String serialized = MAPPER.writeValueAsString(MAPPER
        .readValue(fixture("fixtures/domain/participant/valid/valid.json"), Participant.class));

    assertThat(serialized, is(expected));
  }

  @Test
  public void deserializesFromJSON() throws Exception {
    Participant expected = this.validParticipant();

    Participant serialized = MAPPER
        .readValue(fixture("fixtures/domain/participant/valid/valid.json"), Participant.class);
    assertThat(serialized, is(expected));
  }

  @Override
  @Test
  public void testEqualsHashCodeWorks() {
    EqualsVerifier.forClass(Participant.class).suppress(Warning.NONFINAL_FIELDS)
        .withIgnoredFields("messages").verify();
  }


  @Override
  @Test
  public void testEmptyConstructor() throws Exception {
    Participant empty = new Participant();
    assertThat(empty.getClass(), is(Participant.class));
  }


  @Override
  public void testPersistentConstructor() throws Exception {
    // no persistent constructor yet

  }

  @Override
  @Test
  public void testConstructorUsingDomain() throws Exception {

    Participant domain = new Participant(id, clientId, firstName, lastName, gender, ssn,
        dateOfBirth, personId, screeningId, roles, addresses);

    assertThat(domain.getId(), is(equalTo(id)));
    assertThat(domain.getClientId(), is(equalTo(clientId)));
    assertThat(domain.getPersonId(), is(equalTo(personId)));
    assertThat(domain.getScreeningId(), is(equalTo(screeningId)));
    assertThat(domain.getFirstName(), is(equalTo(firstName)));
    assertThat(domain.getLastName(), is(equalTo(lastName)));
    assertThat(domain.getGender(), is(equalTo(gender)));
    assertThat(domain.getDateOfBirth(), is(equalTo(dateOfBirth)));
    assertThat(domain.getSsn(), is(equalTo(ssn)));
    assertThat(domain.getRoles(), is(equalTo(roles)));
    assertThat(domain.getAddresses(), is(equalTo(addresses)));
  }

  @Test
  public void testWithEmptyClientIdSuccess() throws Exception {

    Participant toCreate = MAPPER.readValue(
        fixture("fixtures/domain/participant/valid/validEmptyClientId.json"), Participant.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));

  }

  @Test
  public void testWithNullClientIdFail() throws Exception {
    Participant toCreate = MAPPER.readValue(
        fixture("fixtures/domain/participant/invalid/nullClientId.json"), Participant.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));

  }

  @Test
  public void testWithMissingClientIdFail() throws Exception {
    Participant toCreate = MAPPER.readValue(
        fixture("fixtures/domain/participant/invalid/missingClientId.json"), Participant.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));

  }

  private Participant validParticipant() {
    Participant validParticipant = new Participant(id, clientId, firstName, lastName, gender, ssn,
        dateOfBirth, personId, screeningId, roles, addresses);
    return validParticipant;
  }
}

