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
public class EthnicityTest {

  private String ethnicityType = "Unknown";
  private String subEthnicity = "South American";

  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

  /*
   * Serialization and de-serialization
   */
  @Test
  public void serializesToJSON() throws Exception {
    String expected = MAPPER.writeValueAsString(new Ethnicity("Unknown", "South American"));

    String serialized = MAPPER.writeValueAsString(
        MAPPER.readValue(fixture("fixtures/domain/ethnicity/valid/valid.json"), Ethnicity.class));

    assertThat(serialized, is(expected));
  }

  @Test
  public void testDeserializesFromJSON() throws Exception {
    Ethnicity expected = new Ethnicity("Unknown", "South American");

    Ethnicity serialized =
        MAPPER.readValue(fixture("fixtures/domain/ethnicity/valid/valid.json"), Ethnicity.class);
    assertThat(serialized, is(expected));

  }

  @Test
  public void equalsHashCodeWork() throws Exception {
    EqualsVerifier.forClass(Ethnicity.class)
            .suppress(Warning.NONFINAL_FIELDS)
            .withIgnoredFields("messages")
            .verify();
  }

  @Test
  public void persistentObjectConstructorTest() throws Exception {
    Ethnicity domain = this.validEthnicity();

    gov.ca.cwds.data.persistence.ns.Ethnicity persistent =
        new gov.ca.cwds.data.persistence.ns.Ethnicity(domain, "lastUpdatedId", "createUserId");

    Ethnicity totest = new Ethnicity(persistent);

    assertThat(totest.getEthnicityType(), is(equalTo(persistent.getEthnicityType())));
    assertThat(totest.getSubEthnicity(), is(equalTo(persistent.getSubEthnicity())));
  }

  @Test
  public void testJSONConstructorTest() throws Exception {
    Ethnicity domain = new Ethnicity(ethnicityType, subEthnicity);

    assertThat(domain.getEthnicityType(), is(equalTo(ethnicityType)));
    assertThat(domain.getSubEthnicity(), is(equalTo(subEthnicity)));
  }

  private Ethnicity validEthnicity() {

    try {
      Ethnicity validEthnicity =
          MAPPER.readValue(fixture("fixtures/domain/ethnicity/valid/valid.json"), Ethnicity.class);

      return validEthnicity;

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
