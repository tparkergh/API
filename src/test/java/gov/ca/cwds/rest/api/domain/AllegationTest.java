package gov.ca.cwds.rest.api.domain;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.io.IOException;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.dropwizard.jackson.Jackson;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

public class AllegationTest {

  private long victimPersonId = 1;
  private long perpetratorPersonId = 2;
  private String type = "physical abuse";
  private String county = "Sacramento";


  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

  /*
   * Serialization and de-serialization
   */
  @Test
  public void serializesToJSON() throws Exception {
    String expected =
        MAPPER.writeValueAsString(new Allegation(5432, 2, "physical abuse", "Sacramento"));

    String serialized = MAPPER.writeValueAsString(
        MAPPER.readValue(fixture("fixtures/domain/Allegation/valid/valid.json"), Allegation.class));

    assertThat(serialized, is(expected));
  }


  @Test
  public void testDeserializesFromJSON() throws Exception {
    Allegation expected = new Allegation(5432, 2, "physical abuse", "Sacramento");

    Allegation serialized =
        MAPPER.readValue(fixture("fixtures/domain/Allegation/valid/valid.json"), Allegation.class);
    assertThat(serialized, is(expected));

  }

  @Test
  public void equalsHashCodeWork() throws Exception {
    EqualsVerifier.forClass(Address.class).suppress(Warning.NONFINAL_FIELDS, Warning.NULL_FIELDS)
        .verify();
  }

  @Test
  public void testDomainConstructorTest() throws Exception {
    Allegation domain = new Allegation(victimPersonId, perpetratorPersonId, type, county);

    assertThat(domain.getVictimPersonId(), is(equalTo(victimPersonId)));
    assertThat(domain.getPerpetratorPersonId(), is(equalTo(perpetratorPersonId)));
    assertThat(domain.getType(), is(equalTo(type)));
    assertThat(domain.getCounty(), is(equalTo(county)));
  }

  private Allegation validAllegation() {

    try {
      Allegation validAllegation = MAPPER
          .readValue(fixture("fixtures/domain/Allegation/valid/valid.json"), Allegation.class);

      return validAllegation;

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


