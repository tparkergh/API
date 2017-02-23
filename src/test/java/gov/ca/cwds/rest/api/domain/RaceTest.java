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

/**
 * @author CWDS API Team
 *
 */
@SuppressWarnings("javadoc")
public class RaceTest {

  private String race = "White";
  private String subrace = "American";

  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

  /*
   * Serialization and de-serialization
   */
  @Test
  public void serializesToJSON() throws Exception {
    String expected = MAPPER.writeValueAsString(new Race("White", "American"));

    String serialized = MAPPER.writeValueAsString(
        MAPPER.readValue(fixture("fixtures/domain/race/valid/valid.json"), Race.class));

    assertThat(serialized, is(expected));
  }

  @Test
  public void testDeserializesFromJSON() throws Exception {
    Race expected = new Race("White", "American");

    Race serialized =
        MAPPER.readValue(fixture("fixtures/domain/race/valid/valid.json"), Race.class);
    assertThat(serialized, is(expected));

  }

  @Test
  public void equalsHashCodeWork() throws Exception {
    EqualsVerifier.forClass(Race.class).suppress(Warning.NONFINAL_FIELDS).verify();
  }

  @Test
  public void persistentObjectConstructorTest() throws Exception {
    Race domain = this.validRace();

    gov.ca.cwds.data.persistence.ns.Race persistent =
        new gov.ca.cwds.data.persistence.ns.Race(domain, "12345", "12345");

    Race totest = new Race(persistent);
    assertThat(totest.getRace(), is(equalTo(persistent.getRace())));
    assertThat(totest.getSubrace(), is(equalTo(persistent.getSubrace())));
  }

  @Test
  public void testJSONConstructorTest() throws Exception {
    Race domain = new Race(race, subrace);

    assertThat(domain.getRace(), is(equalTo(race)));
    assertThat(domain.getSubrace(), is(equalTo(subrace)));
  }

  private Race validRace() {

    try {
      Race validRace =
          MAPPER.readValue(fixture("fixtures/domain/race/valid/valid.json"), Race.class);

      return validRace;

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
