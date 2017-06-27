package gov.ca.cwds.rest.api.domain.cms;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.validation.Validation;
import javax.validation.Validator;

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.squarespace.jersey2.guice.JerseyGuiceUtils;

import gov.ca.cwds.rest.api.domain.error.ErrorMessage;
import gov.ca.cwds.rest.core.Api;
import gov.ca.cwds.rest.messages.MessageBuilder;
import gov.ca.cwds.rest.resources.cms.JerseyGuiceRule;
import io.dropwizard.jackson.Jackson;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

/**
 * @author CWDS API Team
 *
 */
public class AssignmentTest {

  private static final String ROOT_RESOURCE = "/" + Api.RESOURCE_ASSIGNMENT + "/";
  // private static final AssignmentResource mockedAssignmentResource =
  // mock(AssignmentResource.class);

  @SuppressWarnings("javadoc")
  @After
  public void ensureServiceLocatorPopulated() {
    JerseyGuiceUtils.reset();
  }

  @SuppressWarnings("javadoc")
  @ClassRule
  public static JerseyGuiceRule rule = new JerseyGuiceRule();

  /**
   * 
   */
  // @ClassRule
  // public static final ResourceTestRule resources =
  // ResourceTestRule.builder().addResource(mockedAssignmentResource).build();

  private final static DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
  private final static DateFormat timeOnlyFormat = new SimpleDateFormat("HH:mm:ss");
  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

  private String countySpecificCode = "20";
  private String endDate = "2018-06-01";
  private String endTime = "12:01:00";
  private String establishedForCode = "R";
  private String establishedForId = "1234567ABC";
  private String caseLoadId = "2345678ABC";
  private String outOfStatePartyContactId = "";
  private String id = "3456789ABC";
  private String responsiblityDescription = "Assignment responsibility description";
  private Short secondaryAssignmentRoleType = 0;
  private String startDate = "2017-06-20";
  private String startTime = "16:41:49";
  private String typeOfAssignmentCode = "P";
  private BigDecimal weightingNumber = new BigDecimal("0.0");
  private String staffId = "0X5";

  private Assignment validAssignment = validAssignment();

  private MessageBuilder messageBuilder;
  private Validator validator;

  /**
   * 
   */
  @Before
  public void setup() {
    messageBuilder = new MessageBuilder();
    // when(mockedAssignmentResource.create(eq(validAssignment)))
    // .thenReturn(Response.status(Response.Status.NO_CONTENT).entity(null).build());
  }

  @SuppressWarnings("javadoc")
  @Test
  public void equalsHashCodeWorks() {
    EqualsVerifier.forClass(Assignment.class).suppress(Warning.NONFINAL_FIELDS)
        .withIgnoredFields("messages").verify();

  }

  @SuppressWarnings("javadoc")
  @Test
  public void testdeserializesFromJson() throws Exception {
    assertThat(MAPPER.readValue(fixture("fixtures/domain/legacy/Assignment/valid/valid.json"),
        Assignment.class), is(equalTo(validAssignment())));
  }

  @SuppressWarnings("javadoc")
  @Test
  public void testWithValidSuccess() throws Exception {
    Assignment toCreate = MAPPER
        .readValue(fixture("fixtures/domain/legacy/Assignment/valid/valid.json"), Assignment.class);
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(toCreate));

    assertThat(messageBuilder.getMessages().isEmpty(), is(true));
  }

  @SuppressWarnings("javadoc")
  @Test
  public void testCountySpceficCodeBlankFail() throws Exception {
    Assignment toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Assignment/invalid/countySpecificCodeBlank.json"),
        Assignment.class);
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(toCreate));
    Boolean theErrorDetected = false;

    ArrayList<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
      if (message.getMessage().equals("countySpecificCode size must be between 1 and 2")) {
        theErrorDetected = true;
      }
    }

    assertThat(theErrorDetected, is(true));
  }

  @SuppressWarnings("javadoc")
  @Test
  public void testCountySpceficCodeNullFail() throws Exception {
    Assignment toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Assignment/invalid/countySpecificCodeBlank.json"),
        Assignment.class);
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(toCreate));
    Boolean theErrorDetected = false;

    ArrayList<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
      if (message.getMessage().equals("countySpecificCode may not be empty")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  @SuppressWarnings("javadoc")
  @Test
  public void testCountySpceficCodeNullMissing() throws Exception {
    Assignment toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Assignment/invalid/countySpecificCodeMissing.json"),
        Assignment.class);
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(toCreate));
    Boolean theErrorDetected = false;

    ArrayList<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
      // System.out.println(message.getMessage());
      if (message.getMessage().equals("countySpecificCode may not be empty")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  @SuppressWarnings("javadoc")
  @Test
  public void testEndDateBlankSuccess() throws Exception {
    Assignment toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Assignment/valid/endDateBlank.json"), Assignment.class);
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(toCreate));
    assertThat(messageBuilder.getMessages().isEmpty(), is(true));
  }

  @SuppressWarnings("javadoc")
  @Test
  public void testEndDateNullSuccess() throws Exception {
    Assignment toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Assignment/valid/endDateNull.json"), Assignment.class);
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(toCreate));
    assertThat(messageBuilder.getMessages().isEmpty(), is(true));
  }

  @SuppressWarnings("javadoc")
  @Test
  public void testEndDateMissingSuccess() throws Exception {
    Assignment toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Assignment/valid/endDateNull.json"), Assignment.class);
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(toCreate));
    assertThat(messageBuilder.getMessages().isEmpty(), is(true));
  }

  @SuppressWarnings("javadoc")
  @Test
  public void testEndDateInvlidFormatFails() throws Exception {
    Assignment toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Assignment/invalid/endDateInvalidFormat.json"),
        Assignment.class);
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(toCreate));
    Boolean theErrorDetected = false;

    ArrayList<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
      // System.out.println(message.getMessage());
      if (message.getMessage().equals("endDate must be in the format of yyyy-MM-dd")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }


  @SuppressWarnings("javadoc")
  @Test
  public void testEndTimeBlankSuccess() throws Exception {
    Assignment toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Assignment/valid/endTimeBlank.json"), Assignment.class);
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(toCreate));
    assertThat(messageBuilder.getMessages().isEmpty(), is(true));

  }

  @SuppressWarnings("javadoc")
  @Test
  public void testEndTimeNullSuccess() throws Exception {
    Assignment toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Assignment/valid/endTimeNull.json"), Assignment.class);
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(toCreate));
    assertThat(messageBuilder.getMessages().isEmpty(), is(true));

  }

  @SuppressWarnings("javadoc")
  @Test
  public void testEndTimeMissingSuccess() throws Exception {
    Assignment toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Assignment/valid/endTimeMissing.json"), Assignment.class);
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(toCreate));
    assertThat(messageBuilder.getMessages().isEmpty(), is(true));

  }

  @SuppressWarnings("javadoc")
  @Test
  public void testEndTimeInvalidFormatFails() throws Exception {
    Assignment toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Assignment/invalid/endTimeInvalidFormat.json"),
        Assignment.class);
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(toCreate));
    Boolean theErrorDetected = false;

    ArrayList<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
      System.out.println(message.getMessage());
      if (message.getMessage().equals("endTime must be in the format of HH:mm:ss")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  @Test
  public void testEstablishedForCodeMissingFails() throws Exception {
    Assignment toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Assignment/invalid/establishedForCodeMissing.json"),
        Assignment.class);
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(toCreate));
    Boolean theErrorDetected = false;

    ArrayList<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
      System.out.println(message.getMessage());
      if (message.getMessage().equals("establishedForCode may not be empty")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  @Test
  public void testEstablishedForCodeInvalidFails() throws Exception {
    Assignment toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Assignment/invalid/establishedForCodeInvalid.json"),
        Assignment.class);
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(toCreate));
    Boolean theErrorDetected = false;

    ArrayList<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
      // System.out.println(message.getMessage());
      if (message.getMessage().equals("establishedForCode must be one of [R, C]")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  @Test
  public void testEstablishedForCodeNullFails() throws Exception {
    Assignment toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Assignment/invalid/establishedForCodeNull.json"),
        Assignment.class);
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(toCreate));
    Boolean theErrorDetected = false;

    ArrayList<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
      // System.out.println(message.getMessage());
      if (message.getMessage().equals("establishedForCode may not be empty")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  @SuppressWarnings("javadoc")
  @Test
  public void testEstablishedForIdNullFails() throws Exception {
    Assignment toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Assignment/invalid/establishedForIdCodeNull.json"),
        Assignment.class);
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(toCreate));
    Boolean theErrorDetected = false;

    ArrayList<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
      // System.out.println(message.getMessage());
      if (message.getMessage().equals("establishedForId may not be empty")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  @SuppressWarnings("javadoc")
  @Test
  public void testEstablishedForIdMissingFails() throws Exception {
    Assignment toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Assignment/invalid/establishedForIdCodeNull.json"),
        Assignment.class);
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(toCreate));
    Boolean theErrorDetected = false;

    ArrayList<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
      // System.out.println(message.getMessage());
      if (message.getMessage().equals("establishedForId may not be empty")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));

  }

  @SuppressWarnings("javadoc")
  @Test
  public void testEstablishedForIdInvalidFails() throws Exception {
    Assignment toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Assignment/invalid/establishedForIdInvalid.json"),
        Assignment.class);
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(toCreate));
    Boolean theErrorDetected = false;

    ArrayList<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
      // System.out.println(message.getMessage());
      if (message.getMessage().equals("establishedForId size must be between 10 and 10")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  @SuppressWarnings("javadoc")
  @Test
  public void testFkCaseLoadMissingSuccess() throws Exception {
    Assignment toCreate =
        MAPPER.readValue(fixture("fixtures/domain/legacy/Assignment/valid/fkCaseLoadMissing.json"),
            Assignment.class);
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(toCreate));
    assertThat(messageBuilder.getMessages().isEmpty(), is(true));

  }

  @SuppressWarnings("javadoc")
  @Test
  public void testFkCaseLoadBlankSuccess() throws Exception {
    Assignment toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Assignment/valid/fkCaseLoadBlank.json"), Assignment.class);
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(toCreate));
    assertThat(messageBuilder.getMessages().isEmpty(), is(true));

  }

  @SuppressWarnings("javadoc")
  @Test
  public void testFkCaseLInvalidFormatFails() throws Exception {
    Assignment toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Assignment/invalid/fkCaseLdtInvalidFormat.json"),
        Assignment.class);
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(toCreate));
    Boolean theErrorDetected = false;

    ArrayList<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
      // System.out.println(message.getMessage());
      if (message.getMessage().equals("caseLoadId size must be between 0 and 10")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  @SuppressWarnings("javadoc")
  @Test
  public void testSecondaryAssignmentRoleTypeMissingSuccess() throws Exception {
    Assignment toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Assignment/valid/secondaryAssignmentRoleTypeMissing.json"),
        Assignment.class);
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(toCreate));
    assertThat(messageBuilder.getMessages().isEmpty(), is(true));

  }

  @SuppressWarnings("javadoc")
  @Test
  public void testStartDateEmptyFails() throws Exception {
    Assignment toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Assignment/invalid/startDateEmpty.json"), Assignment.class);
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(toCreate));
    Boolean theErrorDetected = false;

    ArrayList<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
      // System.out.println(message.getMessage());
      if (message.getMessage().equals("startDate may not be empty")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  @Test
  public void testStartDateMissingFails() throws Exception {
    Assignment toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Assignment/invalid/startDateEmptyMissing.json"),
        Assignment.class);
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(toCreate));
    Boolean theErrorDetected = false;

    ArrayList<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
      // System.out.println(message.getMessage());
      if (message.getMessage().equals("startDate may not be empty")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  @SuppressWarnings("javadoc")
  @Test
  public void testStartDateNullFails() throws Exception {
    Assignment toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Assignment/invalid/startDateEmptyNull.json"),
        Assignment.class);
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(toCreate));
    Boolean theErrorDetected = false;

    ArrayList<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
      // System.out.println(message.getMessage());
      if (message.getMessage().equals("startDate may not be empty")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  @Test
  public void testStartDateInvalidFormat() throws Exception {
    Assignment toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Assignment/invalid/startDateInvalidFormat.json"),
        Assignment.class);
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(toCreate));
    Boolean theErrorDetected = false;

    ArrayList<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
      // System.out.println(message.getMessage());
      if (message.getMessage().equals("startDate must be in the format of yyyy-MM-dd")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));

  }

  @Test
  public void testStartTimeEmptyFails() throws Exception {
    Assignment toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Assignment/invalid/startTimeEmpty.json"), Assignment.class);
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(toCreate));
    Boolean theErrorDetected = false;

    ArrayList<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
      // System.out.println(message.getMessage());
      if (message.getMessage().equals("startTime must be in the format of HH:mm:ss")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));

  }

  @Test
  public void testStartTimeMissingFails() throws Exception {
    Assignment toCreate =
        MAPPER.readValue(fixture("fixtures/domain/legacy/Assignment/invalid/startTimeMissing.json"),
            Assignment.class);
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(toCreate));
    Boolean theErrorDetected = false;

    ArrayList<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
      // System.out.println(message.getMessage());
      if (message.getMessage().equals("startTime must be in the format of HH:mm:ss")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));

  }

  @Test
  public void testStartTimeNullFails() throws Exception {
    Assignment toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Assignment/invalid/startTimeNull.json"), Assignment.class);
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(toCreate));
    Boolean theErrorDetected = false;

    ArrayList<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
      // System.out.println(message.getMessage());
      if (message.getMessage().equals("startTime must be in the format of HH:mm:ss")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));

  }

  @Test
  public void testStartTimeInvalidFormat() throws Exception {
    Assignment toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Assignment/invalid/startTimeInvalidFormat.json"),
        Assignment.class);
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(toCreate));
    Boolean theErrorDetected = false;

    ArrayList<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
      // System.out.println(message.getMessage());
      if (message.getMessage().equals("startTime must be in the format of HH:mm:ss")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  @SuppressWarnings("javadoc")
  @Test
  public void testDomainConstructorSuccess() throws Exception {

    Assignment da = validAssignment();

    assertThat(da.getCountySpecificCode(), is(equalTo(countySpecificCode)));
    assertThat(da.getEndDate(), is(equalTo(endDate)));
    assertThat(da.getEndTime(), is(equalTo(endTime)));
    assertThat(da.getEstablishedForCode(), is(equalTo(establishedForCode)));
    assertThat(da.getEstablishedForId(), is(equalTo(establishedForId)));
    assertThat(da.getCaseLoadId(), is(equalTo(caseLoadId)));
    assertThat(da.getOutOfStateContactId(), is(equalTo(outOfStatePartyContactId)));
    assertThat(da.getResponsibilityDescription(), is(equalTo(responsiblityDescription)));
    assertThat(da.getSecondaryAssignmentRoleType(), is(equalTo(secondaryAssignmentRoleType)));
    assertThat(da.getStartDate(), is(equalTo(startDate)));
    assertThat(da.getStartTime(), is(equalTo(startTime)));
    assertThat(da.getTypeOfAssignmentCode(), is(equalTo(typeOfAssignmentCode)));
    assertThat(da.getWeightingNumber(), is(equalTo(weightingNumber)));

  }

  @SuppressWarnings("javadoc")
  @Test
  public void testPersistentObjectConstructorSuccess() throws Exception {

    Assignment vda = validAssignment();

    gov.ca.cwds.data.persistence.cms.Assignment pa =
        new gov.ca.cwds.data.persistence.cms.Assignment(id, vda, staffId);

    Assignment pc = new Assignment(pa);

    assertThat(pc.getCountySpecificCode(), is(equalTo(pa.getCountySpecificCode())));
    assertThat(pc.getEndDate(), is(equalTo(df.format(pa.getEndDate()))));
    assertThat(pc.getEndTime(), is(equalTo(timeOnlyFormat.format(pa.getEndTime()))));
    assertThat(pc.getEstablishedForCode(), is(equalTo(pa.getEstablishedForCode())));
    assertThat(pc.getEstablishedForId(), is(equalTo(pa.getEstablishedForId())));
    assertThat(pc.getCaseLoadId(), is(equalTo(pa.getFkCaseLoad())));
    assertThat(pc.getOutOfStateContactId(), is(equalTo(pa.getFkOutOfStateContactParty())));
    assertThat(pc.getResponsibilityDescription(), is(equalTo(pa.getResponsibilityDescription())));
    assertThat(pc.getSecondaryAssignmentRoleType(),
        is(equalTo(pa.getSecondaryAssignmentRoleType())));
    assertThat(pc.getStartDate(), is(equalTo(df.format(pa.getStartDate()))));
    assertThat(pc.getStartTime(), is(equalTo(timeOnlyFormat.format(pa.getStartTime()))));
  }

  private Assignment validAssignment() {
    Assignment validAssignment = new Assignment(countySpecificCode, endDate, endTime,
        establishedForCode, establishedForId, caseLoadId, outOfStatePartyContactId,
        responsiblityDescription, secondaryAssignmentRoleType, startDate, startTime,
        typeOfAssignmentCode, weightingNumber);
    return validAssignment;
  }

}
