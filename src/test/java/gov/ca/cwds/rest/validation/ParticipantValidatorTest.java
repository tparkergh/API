package gov.ca.cwds.rest.validation;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import gov.ca.cwds.rest.api.domain.ScreeningToReferral;
import io.dropwizard.jackson.Jackson;

@SuppressWarnings("javadoc")
public class ParticipantValidatorTest {
  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();
  private Validator validator;

  @Before
  public void setup() {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();

    MAPPER.configure(SerializationFeature.INDENT_OUTPUT, true);
  }

  @Test
  public void testHasValidParticipantsSuccess() throws Exception {
    ScreeningToReferral toValidate = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/valid.json"), ScreeningToReferral.class);
    assertThat(ParticipantValidator.hasValidParticipants(toValidate), equalTo(true));
  }

  @Test
  public void testHasMultipleVictimsFail() throws Exception {
    ScreeningToReferral toValidate = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/invalid/moreThanOneVictim.json"),
        ScreeningToReferral.class);
    assertThat(ParticipantValidator.hasValidParticipants(toValidate), equalTo(false));
  }

  @Test
  public void testHasMultipleReporterFail() throws Exception {
    ScreeningToReferral toValidate = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/invalid/moreThanOneReporter.json"),
        ScreeningToReferral.class);
    assertThat(ParticipantValidator.hasValidParticipants(toValidate), equalTo(false));
  }

  @Test
  public void testHasMultipleSelfReportedFail() throws Exception {
    ScreeningToReferral toValidate = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/invalid/moreThanOneSelfReported.json"),
        ScreeningToReferral.class);
    assertThat(ParticipantValidator.hasValidParticipants(toValidate), equalTo(false));
  }

  @Test
  public void testHasMultipleAllegationsSuccess() throws Exception {
    ScreeningToReferral toValidate = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/multipleAllegations.json"),
        ScreeningToReferral.class);
    assertThat(ParticipantValidator.hasValidParticipants(toValidate), equalTo(true));
  }

  @Test
  public void testNoVictimFail() throws Exception {
    ScreeningToReferral toValidate =
        MAPPER.readValue(fixture("fixtures/domain/ScreeningToReferral/invalid/noVictim.json"),
            ScreeningToReferral.class);
    assertThat(ParticipantValidator.hasValidParticipants(toValidate), equalTo(false));
  }

  @Test
  public void testAllegationVictimPersonIdNotParticipantVictimFail() throws Exception {
    ScreeningToReferral toValidate = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/valid.json"), ScreeningToReferral.class);
    assertThat(ParticipantValidator.isVictimParticipant(toValidate, 9999), equalTo(false));
  }

  @Test
  public void testAllegationVictimPersonIdIsParticipantVictimSuccess() throws Exception {
    ScreeningToReferral toValidate = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/valid.json"), ScreeningToReferral.class);
    assertThat(ParticipantValidator.isVictimParticipant(toValidate, 5432), equalTo(true));
  }

  @Test
  public void testAllegationPerpetratorPersonIdNotParticipantPerpetratorFail() throws Exception {
    ScreeningToReferral toValidate = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/valid.json"), ScreeningToReferral.class);
    assertThat(ParticipantValidator.isPerpetratorParticipant(toValidate, 9999), equalTo(false));
  }

  @Test
  public void testAllegationPerpetratorPersonIdIsParticipantPerpetratorSuccess() throws Exception {
    ScreeningToReferral toValidate = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/valid.json"), ScreeningToReferral.class);
    assertThat(ParticipantValidator.isPerpetratorParticipant(toValidate, 1234), equalTo(true));
  }

  @Test
  public void testRoleIsVictimSuccess() throws Exception {
    assertThat(ParticipantValidator.roleIsVictim("Victim"), equalTo(true));
  }

  @Test
  public void testRoleIsNotVictimSuccess() throws Exception {
    assertThat(ParticipantValidator.roleIsVictim("Perpetrator"), equalTo(false));
  }

  @Test
  public void testRoleIsNotVictimWhenNullSuccess() throws Exception {
    assertThat(ParticipantValidator.roleIsVictim(null), equalTo(false));
  }

  @Test
  public void testRoleIsPerpetratorSuccess() throws Exception {
    assertThat(ParticipantValidator.roleIsPerpetrator("Perpetrator"), equalTo(true));
  }

  @Test
  public void testRoleIsNotPerpetratorSuccess() throws Exception {
    assertThat(ParticipantValidator.roleIsPerpetrator("Victim"), equalTo(false));
  }

  @Test
  public void testRoleIsNotPerpetratorWhenNullSuccess() throws Exception {
    assertThat(ParticipantValidator.roleIsPerpetrator(null), equalTo(false));
  }

  @Test
  public void testRoleIsAnonymousReporterSuccess() throws Exception {
    assertThat(ParticipantValidator.roleIsAnonymousReporter("Anonymous Reporter"), equalTo(true));
  }

  @Test
  public void testRoleIsNotAnonymousReporterSuccess() throws Exception {
    assertThat(ParticipantValidator.roleIsAnonymousReporter("Perpetrator"), equalTo(false));
  }

  @Test
  public void testRoleIsNotAnonymousReporterWhenNullSuccess() throws Exception {
    assertThat(ParticipantValidator.roleIsAnonymousReporter(null), equalTo(false));
  }

  @Test
  public void testRoleIsReporterTypeWhenMandatedReporterSuccess() throws Exception {
    assertThat(ParticipantValidator.roleIsReporterType("Mandated Reporter"), equalTo(true));
  }

  @Test
  public void testRoleIsReporterTypeWhenNonMandatedReporterSuccess() throws Exception {
    assertThat(ParticipantValidator.roleIsReporterType("Non-mandated Reporter"), equalTo(true));
  }

  @Test
  public void testRoleIsNotReporterTypeSuccess() throws Exception {
    assertThat(ParticipantValidator.roleIsReporterType("Anonymous Reporter"), equalTo(false));
  }

  @Test
  public void testRoleIsNotReporterTypeWhenNullSuccess() throws Exception {
    assertThat(ParticipantValidator.roleIsReporterType(null), equalTo(false));
  }

  @Test
  public void testRoleIsMandatedReporterSuccess() throws Exception {
    assertThat(ParticipantValidator.roleIsMandatedReporter("Mandated Reporter"), equalTo(true));
  }

  @Test
  public void testRoleIsNotMandatedReporterSuccess() throws Exception {
    assertThat(ParticipantValidator.roleIsMandatedReporter("Perpetrator"), equalTo(false));
  }

  @Test
  public void testRoleIsNotMandatedReporterWhenNullSuccess() throws Exception {
    assertThat(ParticipantValidator.roleIsMandatedReporter(null), equalTo(false));
  }

}
