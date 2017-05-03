package gov.ca.cwds.rest.api.domain;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.dropwizard.jackson.Jackson;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

public class CrossReportTest {

  private String agencyType = "Law enforcement";
  private String agencyName = "Sacramento County Sheriff Deparment";
  private String method = "electronic report";
  private String informDate = "2017-03-15";

  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

  /*
   * Serialization and de-serialization
   */
  @Test
  public void serializesToJSON() throws Exception {
    String expected = MAPPER.writeValueAsString(new CrossReport("Law enforcement",
        "Sacramento County Sheriff Deparment", "electronic report", "2017-03-15"));

    String serialized = MAPPER.writeValueAsString(MAPPER
        .readValue(fixture("fixtures/domain/CrossReport/valid/valid.json"), CrossReport.class));

    assertThat(serialized, is(expected));
  }

  @Test
  public void testDeserializesFromJSON() throws Exception {
    CrossReport expected = new CrossReport("Law enforcement", "Sacramento County Sheriff Deparment",
        "electronic report", "2017-03-15");

    CrossReport serialized = MAPPER
        .readValue(fixture("fixtures/domain/CrossReport/valid/valid.json"), CrossReport.class);
    assertThat(serialized, is(expected));

  }

  @Test
  public void equalsHashCodeWork() throws Exception {
      EqualsVerifier.forClass(CrossReport.class)
          .suppress(Warning.NONFINAL_FIELDS, Warning.NULL_FIELDS)
          .withIgnoredFields("messages")
          .verify();
  }

  @Test
  public void testDomainConstructorTest() throws Exception {
    CrossReport domain = new CrossReport(agencyType, agencyName, method, informDate);

    assertThat(domain.getAgencyType(), is(equalTo(agencyType)));
    assertThat(domain.getAgencyName(), is(equalTo(agencyName)));
    assertThat(domain.getMethod(), is(equalTo(method)));
    assertThat(domain.getInformDate(), is(equalTo(informDate)));

  }
}
