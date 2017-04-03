package gov.ca.cwds.rest.api.domain;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Response;

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squarespace.jersey2.guice.JerseyGuiceUtils;

import gov.ca.cwds.data.persistence.junit.template.PersistentTestTemplate;
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
public class ParticipantTest implements PersistentTestTemplate {

  private long id = 5432;
  private long personId = 12345;
  private long screeningId = 12345;
  private String firstName = "John";
  private String lastName = "Smith";
  private String gender = "male";
  private String dateOfBirth = "2001-03-15";
  private String ssn = "123456789";
  private Set<String> roles = new HashSet<String>();
  private Set<Address> addresses = new HashSet<Address>();


  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

  private static final ParticipantResource mockedParticipantResource =
      mock(ParticipantResource.class);

  @After
  public void ensureServiceLocatorPopulated() {
    JerseyGuiceUtils.reset();
  }

  @ClassRule
  public static JerseyGuiceRule rule = new JerseyGuiceRule();

  @SuppressWarnings("javadoc")
  @ClassRule
  public static final ResourceTestRule resources =
      ResourceTestRule.builder().addResource(mockedParticipantResource).build();

  @SuppressWarnings("javadoc")
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
  @SuppressWarnings("javadoc")
  @Test
  public void serializesToJSON() throws Exception {
    String expected = MAPPER.writeValueAsString(validParticipant());

    String serialized = MAPPER.writeValueAsString(MAPPER
        .readValue(fixture("fixtures/domain/participant/valid/valid.json"), Participant.class));

    assertThat(serialized, is(expected));
  }

  @SuppressWarnings("javadoc")
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
    EqualsVerifier.forClass(Participant.class).suppress(Warning.NONFINAL_FIELDS).verify();
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

    Participant domain = new Participant(id, firstName, lastName, gender, ssn, dateOfBirth,
        personId, screeningId, roles, addresses);

    assertThat(domain.getId(), is(equalTo(id)));
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

  private Participant validParticipant() {

    try {
      Participant validParticipant = MAPPER
          .readValue(fixture("fixtures/domain/participant/valid/valid.json"), Participant.class);

      return validParticipant;

    } catch (JsonParseException e) {
      e.printStackTrace();
      return null;
    } catch (JsonMappingException e) {
      e.printStackTrace();
      return null;
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

}

