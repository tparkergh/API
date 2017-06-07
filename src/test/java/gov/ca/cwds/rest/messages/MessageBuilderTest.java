package gov.ca.cwds.rest.messages;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import javax.validation.Validation;
import javax.validation.Validator;

import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.rest.api.domain.cms.Address;
import gov.ca.cwds.rest.api.domain.error.ErrorMessage;
import gov.ca.cwds.rest.api.domain.error.ErrorMessage.ErrorType;

public class MessageBuilderTest {


  MessageBuilder builder;

  @Before
  public void setup() {
    builder = new MessageBuilder();
  }

  @Test
  public void shouldReturnEmptyListOfErrorsWhenNoErrorsAdded() {
    assertTrue("Expected an empty list", builder.getMessages().isEmpty());
  }

  @Test
  public void shouldRetrieveErrorMessagesAfterStoringIt() {
    String message = "my message";
    builder.addError(message);
    ErrorMessage savedMessage = builder.getMessages().get(0);
    assertEquals("Expected to find stored message in messages", message, savedMessage.getMessage());
  }

  @Test
  public void shouldContainTheTypeOfErrorPassedIn() {
    ErrorType type = ErrorType.BUSINESS;
    builder.addError("My Message", type);
    ErrorMessage savedMessage = builder.getMessages().get(0);
    assertEquals("Expected to find message type", type, savedMessage.getType());
  }

  @Test
  public void shouldContainThreeErrorMessagesWhenThreeErrorMessagesAreAdded() {
    builder.addError("My 1st Message", ErrorType.VALIDATION);
    builder.addError("My 2nd Message", ErrorType.BUSINESS);
    builder.addError("My 3rd Message", ErrorType.DATA_ACCESS);
    ArrayList<ErrorMessage> messages = builder.getMessages();
    assertEquals("Expected to find message type", "My 1st Message", messages.get(0).getMessage());
    assertEquals("Expected to find message type", "My 2nd Message", messages.get(1).getMessage());
    assertEquals("Expected to find message type", "My 3rd Message", messages.get(2).getMessage());
  }

  @Test
  public void shouldContainValidationErrorMessageForDomainObject() {
    String longWelshCityName = "Llanfairpwllgwyngyllgogerychwyrndrobwllllantysiliogogogoch";

    Address address = Address.createWithDefaults(new gov.ca.cwds.rest.api.domain.Address(null, null,
        "1 main", longWelshCityName, null, 123435, "type"), new Short("1234"));
    Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    builder.addDomainValidationError(validator.validate(address));
    ErrorMessage message = builder.getMessages().get(0);
    assertEquals("Expected Error to be formated with Resource Error message",
        "city size must be between 0 and 20", message.getMessage());
    assertEquals("Expected Error to have default validation type ", ErrorType.VALIDATION,
        message.getType());
  }
}
