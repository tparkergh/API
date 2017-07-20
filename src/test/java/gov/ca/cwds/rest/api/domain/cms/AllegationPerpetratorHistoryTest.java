package gov.ca.cwds.rest.api.domain.cms;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertTrue;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.validation.Validation;
import javax.validation.Validator;

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.squarespace.jersey2.guice.JerseyGuiceUtils;

import gov.ca.cwds.fixture.AllegationPerpetratorHistoryResourceBuilder;
import gov.ca.cwds.rest.api.domain.DomainChef;
import gov.ca.cwds.rest.api.domain.error.ErrorMessage;
import gov.ca.cwds.rest.messages.MessageBuilder;
import gov.ca.cwds.rest.resources.cms.JerseyGuiceRule;
import io.dropwizard.jackson.Jackson;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

@SuppressWarnings("javadoc")
public class AllegationPerpetratorHistoryTest {

  @ClassRule
  public static JerseyGuiceRule rule = new JerseyGuiceRule();

  // @ClassRule
  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

  private final static DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
  private String id = "1234567ABC";
  private String countySpecificCode = "99";
  private String allegationId = "2345678ABC";
  private String perpetratorClientId = "3456789ABC";
  private Date perpetratorUpdateDate;

  private MessageBuilder messageBuilder;
  private Validator validator;

  @Before
  public void setup() {
    perpetratorUpdateDate = new Date();
    messageBuilder = new MessageBuilder();
  }

  @After
  public void ensureServiceLocatorPopulated() {
    JerseyGuiceUtils.reset();
  }

  @Test
  public void equalsHashCodeWork() {
    EqualsVerifier.forClass(AllegationPerpetratorHistory.class).suppress(Warning.NONFINAL_FIELDS)
        .verify();
  }

  @Test
  public void testConstructorSuccess() throws Exception {
    AllegationPerpetratorHistory toTest = new AllegationPerpetratorHistory(countySpecificCode,
        perpetratorClientId, allegationId, df.format(perpetratorUpdateDate));
    assertThat(toTest.getAllegationId(), is(equalTo(allegationId)));
    assertThat(toTest.getCountySpecificCode(), is(equalTo(countySpecificCode)));
    assertThat(toTest.getPerpetratorClientId(), is(equalTo(perpetratorClientId)));
    assertThat(toTest.getPerpetratorUpdateDate(), is(equalTo(df.format(perpetratorUpdateDate))));
  }

  @Test
  public void testConstructorFromPersistentObjectSucess() throws Exception {
    gov.ca.cwds.data.persistence.cms.AllegationPerpetratorHistory persistentAllegationPerpetratorHistory =
        new gov.ca.cwds.data.persistence.cms.AllegationPerpetratorHistory(id, countySpecificCode,
            perpetratorClientId, allegationId, perpetratorUpdateDate);

    AllegationPerpetratorHistory toTest =
        new AllegationPerpetratorHistory(persistentAllegationPerpetratorHistory);

    assertThat(toTest.getAllegationId(),
        is(equalTo(persistentAllegationPerpetratorHistory.getAllegationId())));
    assertThat(toTest.getCountySpecificCode(),
        is(equalTo(persistentAllegationPerpetratorHistory.getCountySpecificCode())));
    assertThat(toTest.getPerpetratorClientId(),
        is(equalTo(persistentAllegationPerpetratorHistory.getPerpetratorClientId())));
    assertThat(toTest.getPerpetratorUpdateDate(), is(equalTo(
        DomainChef.cookDate(persistentAllegationPerpetratorHistory.getPerpetratorUpdateDate()))));

  }

  @Test
  public void testWithValidValuesSuccess() throws Exception {

    AllegationPerpetratorHistory toTest =
        new AllegationPerpetratorHistoryResourceBuilder().createAllegationPerpetratorHistory();

    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(toTest));
    ArrayList<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
      System.out.println(message.getMessage());
    }
    assertThat(messageBuilder.getMessages().isEmpty(), is(true));
  }

  @Test
  public void testCountySpecificCodeTooLongFails() throws Exception {
    String errorMessage = "countySpecificCode size must be between 2 and 2";
    Boolean containsError = false;
    AllegationPerpetratorHistory toTest = new AllegationPerpetratorHistoryResourceBuilder()
        .setCountySpecificCode("999").createAllegationPerpetratorHistory();

    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(toTest));
    ArrayList<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
      if (message.getMessage().equals(errorMessage)) {
        containsError = true;
      }
    }
    assertTrue(containsError);
  }

  @Test
  public void testCountySpecificCodeTooShortFails() throws Exception {
    String errorMessage = "countySpecificCode size must be between 2 and 2";
    Boolean containsError = false;
    AllegationPerpetratorHistory toTest = new AllegationPerpetratorHistoryResourceBuilder()
        .setCountySpecificCode("999").createAllegationPerpetratorHistory();

    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(toTest));
    ArrayList<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
      if (message.getMessage().equals(errorMessage)) {
        containsError = true;
      }
    }
    assertTrue(containsError);
  }

  @Test
  public void testAllegationIdBlankFails() throws Exception {
    String errorMessage = "allegationId size must be between 10 and 10";
    Boolean containsError = false;
    AllegationPerpetratorHistory toTest = new AllegationPerpetratorHistoryResourceBuilder()
        .setAllegationId("").createAllegationPerpetratorHistory();

    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(toTest));
    ArrayList<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
      if (message.getMessage().equals(errorMessage)) {
        containsError = true;
      }
    }
    assertTrue(containsError);
  }

  @Test
  public void testAllegationIdTooLongFails() throws Exception {
    String errorMessage = "allegationId size must be between 10 and 10";
    Boolean containsError = false;
    AllegationPerpetratorHistory toTest = new AllegationPerpetratorHistoryResourceBuilder()
        .setAllegationId("").createAllegationPerpetratorHistory();

    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(toTest));
    ArrayList<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
      if (message.getMessage().equals(errorMessage)) {
        containsError = true;
      }
    }
    assertTrue(containsError);
  }

  @Test
  public void testAllegationIdTooShortFails() throws Exception {
    String errorMessage = "allegationId size must be between 10 and 10";
    Boolean containsError = false;
    AllegationPerpetratorHistory toTest = new AllegationPerpetratorHistoryResourceBuilder()
        .setAllegationId("").createAllegationPerpetratorHistory();

    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(toTest));
    ArrayList<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
      if (message.getMessage().equals(errorMessage)) {
        containsError = true;
      }
    }
    assertTrue(containsError);
  }

  @Test
  public void testPerpetratorClientIdBlankSuccess() throws Exception {
    AllegationPerpetratorHistory toTest = new AllegationPerpetratorHistoryResourceBuilder()
        .setPerpertratorClientId("").createAllegationPerpetratorHistory();

    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(toTest));
    ArrayList<ErrorMessage> validationErrors = messageBuilder.getMessages();
    assertTrue("No validation error detected", validationErrors.isEmpty());
  }

  @Test
  public void testPerpetratorClientIdTooLongFails() throws Exception {
    String errorMessage = "perpetratorClientId size must be between 0 and 10";
    Boolean containsError = false;
    AllegationPerpetratorHistory toTest = new AllegationPerpetratorHistoryResourceBuilder()
        .setPerpertratorClientId("12345678ABC").createAllegationPerpetratorHistory();

    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(toTest));
    ArrayList<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
      if (message.getMessage().equals(errorMessage)) {
        containsError = true;
      }
    }
    assertTrue(containsError);
  }

  @Test
  public void testPerpetrationUpdateDateWrongFormatFails() throws Exception {
    String errorMessage = "perpetratorUpdateDate must be in the format of yyyy-MM-dd";
    Boolean containsError = false;
    AllegationPerpetratorHistory toTest = new AllegationPerpetratorHistoryResourceBuilder()
        .setPerpetratorUpdateDate("2017/07/19").createAllegationPerpetratorHistory();

    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(toTest));
    ArrayList<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
      if (message.getMessage().equals(errorMessage)) {
        containsError = true;
      }
    }
    assertTrue(containsError);
  }
}
