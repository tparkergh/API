package gov.ca.cwds.rest.api.domain;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.dropwizard.jackson.Jackson;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

public class CrossReportTest {

  private String agencyType = "Law enforcement";
  private String agencyName = "Sacramento County Sheriff Deparment";
  private Integer method = 2095; // "electronic report"
  private String informDate = "2017-03-15";
  private String legacySourceTable = "CRSS_RPT";
  private String legacyId = "1234567ABC";
  private String id = "1234567ABC";

  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();
  private CrossReport crossReport;

  @Before
  public void setup() {
    crossReport = new CrossReport("", "", "", "Law enforcement",
        "Sacramento County Sheriff Deparment", 2095, "2017-03-15");

  }

  /*
   * Serialization and de-serialization
   */
  @Test
  public void serializesToJSON() throws Exception {
    String expected = MAPPER.writeValueAsString(crossReport);

    String serialized = MAPPER.writeValueAsString(MAPPER
        .readValue(fixture("fixtures/domain/CrossReport/valid/valid.json"), CrossReport.class));

    assertThat(serialized, is(expected));
  }

  @Test
  public void testDeserializesFromJSON() throws Exception {
    CrossReport expected = crossReport;

    CrossReport serialized = MAPPER
        .readValue(fixture("fixtures/domain/CrossReport/valid/valid.json"), CrossReport.class);
    assertThat(serialized, is(expected));

  }

  @Test
  public void equalsHashCodeWork() throws Exception {
    EqualsVerifier.forClass(CrossReport.class)
        .suppress(Warning.NONFINAL_FIELDS, Warning.NULL_FIELDS).withIgnoredFields("messages")
        .verify();
  }

  @Test
  public void testEquals() {
    CrossReport thisCrossReport = new CrossReport(id, legacySourceTable, legacyId, agencyType,
        agencyName, method, informDate);
    CrossReport thatCrossReport = new CrossReport(id, legacySourceTable, legacyId, agencyType,
        agencyName, method, informDate);
    assertEquals("Should be equal", thisCrossReport, thatCrossReport);

  }

  @Test
  public void testDomainConstructorTest() throws Exception {
    CrossReport domain = new CrossReport(id, legacySourceTable, legacyId, agencyType, agencyName,
        method, informDate);

    assertThat(domain.getAgencyType(), is(equalTo(agencyType)));
    assertThat(domain.getAgencyName(), is(equalTo(agencyName)));
    assertThat(domain.getMethod(), is(equalTo(method)));
    assertThat(domain.getInformDate(), is(equalTo(informDate)));
    assertThat(domain.getLegacySourceTable(), is(equalTo(legacySourceTable)));
    assertThat(domain.getLegacyId(), is(equalTo(legacyId)));
    assertThat(domain.getId(), is(equalTo(id)));
  }

  @Test
  public void testValidCrossReportMethod() throws Exception {
    Integer validCrossReportMethod = 2095;
    CrossReport crossReport = new CrossReport(id, legacySourceTable, legacyId, agencyType,
        agencyName, validCrossReportMethod, informDate);

    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();

    Set<ConstraintViolation<CrossReport>> constraintViolations = validator.validate(crossReport);
    assertEquals(0, constraintViolations.size());
  }

  @Test
  public void testInvalidCrossReportMethod() throws Exception {
    Integer invalidCrossReportMethod = 9999;
    CrossReport crossReport = new CrossReport(id, legacySourceTable, legacyId, agencyType,
        agencyName, invalidCrossReportMethod, informDate);

    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();

    Set<ConstraintViolation<CrossReport>> constraintViolations = validator.validate(crossReport);
    assertEquals(1, constraintViolations.size());
  }
}
