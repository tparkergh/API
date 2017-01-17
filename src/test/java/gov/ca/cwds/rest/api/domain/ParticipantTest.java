package gov.ca.cwds.rest.api.domain;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

import javax.ws.rs.core.Response;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import gov.ca.cwds.rest.resources.ParticipantResource;
import io.dropwizard.jackson.Jackson;
import io.dropwizard.testing.junit.ResourceTestRule;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

public class ParticipantTest {

  private long personId = 12345;
  private long screeningId = 12345;
  private String firstName = "firstname";
  private String lastName = "lastename";
  private String gender = "male";
  private String dateOfBirth = "2001-09-13";
  private String ssn = "123456789";

  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

  private static final ParticipantResource mockedParticipantResource =
      mock(ParticipantResource.class);

  @ClassRule
  public static final ResourceTestRule resources =
      ResourceTestRule.builder().addResource(mockedParticipantResource).build();

  @Before
  public void setup() {
    Participant validParticipant = this.validParticipant();

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

  @Test
  public void equalsHashCodeWork() {
    EqualsVerifier.forClass(Participant.class).suppress(Warning.NONFINAL_FIELDS).verify();
  }


  @Test
  public void jsonCreatorConstructorTest() throws Exception {

    Participant domain =
        new Participant(personId, screeningId, firstName, lastName, gender, dateOfBirth, ssn);

    assertThat(domain.getPersonId(), is(equalTo(personId)));
    assertThat(domain.getScreeningId(), is(equalTo(screeningId)));
    assertThat(domain.getFirstName(), is(equalTo(firstName)));
    assertThat(domain.getLastName(), is(equalTo(lastName)));
    assertThat(domain.getGender(), is(equalTo(gender)));
    assertThat(domain.getDateOfBirth(), is(equalTo(dateOfBirth)));
    assertThat(domain.getSsn(), is(equalTo(ssn)));

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

