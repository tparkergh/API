package gov.ca.cwds.rest.api.domain.cms;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.squarespace.jersey2.guice.JerseyGuiceUtils;

import gov.ca.cwds.fixture.StaffPersonCaseLoadResourceBuilder;
import gov.ca.cwds.rest.resources.cms.JerseyGuiceRule;
import io.dropwizard.jackson.Jackson;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

/**
 * @author CWDS API Team
 */
public class StaffPersonCaseLoadTest {

  private Validator validator;

  @Before
  public void setup() {
    MAPPER.configure(SerializationFeature.INDENT_OUTPUT, true);
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
  }

  @SuppressWarnings("javadoc")
  @After
  public void ensureServiceLocatorPopulated() {
    JerseyGuiceUtils.reset();
  }

  @SuppressWarnings("javadoc")
  @ClassRule
  public static JerseyGuiceRule rule = new JerseyGuiceRule();

  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

  private final static DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

  @SuppressWarnings("javadoc")
  @Test
  public void persistentObjectConstructorTest() throws Exception {

    StaffPersonCaseLoad builder = new StaffPersonCaseLoadResourceBuilder().build();

    gov.ca.cwds.data.persistence.cms.StaffPersonCaseLoad persistent =
        new gov.ca.cwds.data.persistence.cms.StaffPersonCaseLoad(builder.getThirdId(), builder,
            "lastUpdatedId");
    StaffPersonCaseLoad domain = new StaffPersonCaseLoad(persistent);

    assertThat(domain.getCountyCode(), is(equalTo(persistent.getCountyCode())));
    assertThat(domain.getEndDate(),
        is(equalTo((persistent.getEndDate() == null ? null : df.format(persistent.getEndDate())))));
    assertThat(domain.getFkCaseLoad(), is(equalTo(persistent.getFkCaseLoad())));
    assertThat(domain.getFkStaffPerson(), is(equalTo(persistent.getFkStaffPerson())));
    assertThat(domain.getStartDate(), is(equalTo(df.format(persistent.getStartDate()))));
    assertThat(domain.getThirdId(), is(equalTo(persistent.getThirdId())));
  }

  @SuppressWarnings("javadoc")
  @Test
  public void testPersistentObjectConstructorSuccess() throws Exception {

    StaffPersonCaseLoad builder = new StaffPersonCaseLoadResourceBuilder().build();

    StaffPersonCaseLoad domain = new StaffPersonCaseLoad(builder.getCountyCode(),
        builder.getEndDate(), builder.getFkCaseLoad(), builder.getFkStaffPerson(),
        builder.getStartDate(), builder.getThirdId());

    assertThat(domain.getCountyCode(), is(equalTo(builder.getCountyCode())));
    assertThat(domain.getEndDate(), is(equalTo((builder.getEndDate()))));
    assertThat(domain.getFkCaseLoad(), is(equalTo(builder.getFkCaseLoad())));
    assertThat(domain.getFkStaffPerson(), is(equalTo(builder.getFkStaffPerson())));
    assertThat(domain.getStartDate(), is(equalTo(builder.getStartDate())));
    assertThat(domain.getThirdId(), is(equalTo(builder.getThirdId())));

  }

  @SuppressWarnings("javadoc")
  @Test
  public void equalsHashCodeWork() {
    EqualsVerifier.forClass(LongText.class).suppress(Warning.NONFINAL_FIELDS).verify();
  }

  @SuppressWarnings("javadoc")
  @Test
  public void testCountySpceficCodeNullFail() throws Exception {
    StaffPersonCaseLoad builder =
        new StaffPersonCaseLoadResourceBuilder().setCountySpecificCode(null).build();

    Set<ConstraintViolation<StaffPersonCaseLoad>> constraintViolations =
        validator.validate(builder);
    assertEquals(1, constraintViolations.size());
    assertEquals("may not be empty", constraintViolations.iterator().next().getMessage());

  }

  @SuppressWarnings("javadoc")
  @Test
  public void testCountySpceficCodeBlankFail() throws Exception {
    StaffPersonCaseLoad builder =
        new StaffPersonCaseLoadResourceBuilder().setCountySpecificCode("").build();

    Set<ConstraintViolation<StaffPersonCaseLoad>> constraintViolations =
        validator.validate(builder);
    assertEquals(2, constraintViolations.size());

    ArrayList<String> messages = new ArrayList<String>();
    for (ConstraintViolation<?> violation : constraintViolations) {
      messages.add(violation.getMessage());
    }
    assertEquals(messages.contains("may not be empty"), true);
    assertEquals(messages.contains("size must be between 1 and 2"), true);
  }

  @SuppressWarnings("javadoc")
  @Test
  public void testEndDateBlankSuccess() throws Exception {
    StaffPersonCaseLoad builder = new StaffPersonCaseLoadResourceBuilder().setEndDate("").build();

    Set<ConstraintViolation<StaffPersonCaseLoad>> constraintViolations =
        validator.validate(builder);

    assertEquals(0, constraintViolations.size());
  }

  @SuppressWarnings("javadoc")
  @Test
  public void testEndDateNullSuccess() throws Exception {
    StaffPersonCaseLoad builder = new StaffPersonCaseLoadResourceBuilder().setEndDate(null).build();

    Set<ConstraintViolation<StaffPersonCaseLoad>> constraintViolations =
        validator.validate(builder);

    assertEquals(0, constraintViolations.size());

  }

  @SuppressWarnings("javadoc")
  @Test
  public void testEndDateInvalidFormatFail() throws Exception {
    StaffPersonCaseLoad builder =
        new StaffPersonCaseLoadResourceBuilder().setEndDate("2017-Jan-20").build();
    Set<ConstraintViolation<StaffPersonCaseLoad>> constraintViolations =
        validator.validate(builder);
    assertEquals(1, constraintViolations.size());
    assertEquals("must be in the format of yyyy-MM-dd",
        constraintViolations.iterator().next().getMessage());
    // for (ConstraintViolation<?> violation : constraintViolations) {
    // System.out.println(violation.getMessage());
    // }
  }

  @SuppressWarnings("javadoc")
  @Test
  public void testEndDateValidSuccess() throws Exception {
    StaffPersonCaseLoad builder =
        new StaffPersonCaseLoadResourceBuilder().setEndDate("2017-08-30").build();

    Set<ConstraintViolation<StaffPersonCaseLoad>> constraintViolations =
        validator.validate(builder);

    assertEquals(0, constraintViolations.size());

  }

  @SuppressWarnings("javadoc")
  @Test
  public void testFkCaseLoadEmptyFails() throws Exception {

    StaffPersonCaseLoad builder =
        new StaffPersonCaseLoadResourceBuilder().setFkCaseLoad("").build();

    Set<ConstraintViolation<StaffPersonCaseLoad>> constraintViolations =
        validator.validate(builder);

    assertEquals(2, constraintViolations.size());
    ArrayList<String> messages = new ArrayList<String>();
    for (ConstraintViolation<?> violation : constraintViolations) {
      messages.add(violation.getMessage());
    }
    assertEquals(messages.contains("size must be between 10 and 10"), true);
    assertEquals(messages.contains("may not be empty"), true);
  }

  @SuppressWarnings("javadoc")
  @Test
  public void testFkCaseLoadNullFails() throws Exception {

    StaffPersonCaseLoad builder =
        new StaffPersonCaseLoadResourceBuilder().setFkCaseLoad(null).build();

    Set<ConstraintViolation<StaffPersonCaseLoad>> constraintViolations =
        validator.validate(builder);

    assertEquals(1, constraintViolations.size());
    assertEquals("may not be empty", constraintViolations.iterator().next().getMessage());

  }

  @SuppressWarnings("javadoc")
  @Test
  public void testFkCaseLoadInvalidFormatFails() throws Exception {

    StaffPersonCaseLoad builder =
        new StaffPersonCaseLoadResourceBuilder().setFkCaseLoad("123456789").build();

    Set<ConstraintViolation<StaffPersonCaseLoad>> constraintViolations =
        validator.validate(builder);

    assertEquals(1, constraintViolations.size());
    assertEquals("size must be between 10 and 10",
        constraintViolations.iterator().next().getMessage());

  }

  @SuppressWarnings("javadoc")
  @Test
  public void testFkStaffPersonEmptyFails() throws Exception {
    StaffPersonCaseLoad builder =
        new StaffPersonCaseLoadResourceBuilder().setFkStaffPerson("").build();

    Set<ConstraintViolation<StaffPersonCaseLoad>> constraintViolations =
        validator.validate(builder);
    ArrayList<String> messages = new ArrayList<String>();
    for (ConstraintViolation<?> violation : constraintViolations) {
      messages.add(violation.getMessage());
    }

    assertEquals(2, constraintViolations.size());
    assertEquals(messages.contains("may not be empty"), true);
    assertEquals(messages.contains("size must be between 3 and 3"), true);
  }

  @SuppressWarnings("javadoc")
  @Test
  public void testFkStaffPersonNullFails() throws Exception {

    StaffPersonCaseLoad builder =
        new StaffPersonCaseLoadResourceBuilder().setFkStaffPerson(null).build();

    Set<ConstraintViolation<StaffPersonCaseLoad>> constraintViolations =
        validator.validate(builder);

    assertEquals(1, constraintViolations.size());
    assertEquals("may not be empty", constraintViolations.iterator().next().getMessage());

  }

  @SuppressWarnings("javadoc")
  @Test
  public void testFkStaffPersonInvalidFormat() throws Exception {
    StaffPersonCaseLoad builder =
        new StaffPersonCaseLoadResourceBuilder().setFkStaffPerson("ABCD").build();

    Set<ConstraintViolation<StaffPersonCaseLoad>> constraintViolations =
        validator.validate(builder);

    assertEquals(1, constraintViolations.size());
    assertEquals("size must be between 3 and 3",
        constraintViolations.iterator().next().getMessage());

  }

  @SuppressWarnings("javadoc")
  @Test
  public void testStartDateBlankFails() throws Exception {
    StaffPersonCaseLoad builder = new StaffPersonCaseLoadResourceBuilder().setStartDate("").build();

    Set<ConstraintViolation<StaffPersonCaseLoad>> constraintViolations =
        validator.validate(builder);

    assertEquals(1, constraintViolations.size());
    assertEquals("may not be empty", constraintViolations.iterator().next().getMessage());
  }

  @SuppressWarnings("javadoc")
  @Test
  public void testStartDateNullFails() throws Exception {
    StaffPersonCaseLoad builder =
        new StaffPersonCaseLoadResourceBuilder().setStartDate(null).build();

    Set<ConstraintViolation<StaffPersonCaseLoad>> constraintViolations =
        validator.validate(builder);
    assertEquals(1, constraintViolations.size());
    assertEquals("may not be empty", constraintViolations.iterator().next().getMessage());

  }

  @SuppressWarnings("javadoc")
  @Test
  public void testStartDateInvalidFormatFails() throws Exception {
    StaffPersonCaseLoad builder =
        new StaffPersonCaseLoadResourceBuilder().setStartDate("31-Jan-2017").build();

    Set<ConstraintViolation<StaffPersonCaseLoad>> constraintViolations =
        validator.validate(builder);
    assertEquals(1, constraintViolations.size());
    assertEquals("must be in the format of yyyy-MM-dd",
        constraintViolations.iterator().next().getMessage());
  }
}
