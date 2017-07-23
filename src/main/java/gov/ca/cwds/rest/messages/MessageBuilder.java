package gov.ca.cwds.rest.messages;

import java.util.ArrayList;
import java.util.Set;

import java.util.logging.Logger;
import javax.validation.ConstraintViolation;

import gov.ca.cwds.rest.api.domain.DomainObject;
import gov.ca.cwds.rest.api.domain.error.ErrorMessage;
import gov.ca.cwds.rest.api.domain.error.ErrorMessage.ErrorType;
import org.slf4j.LoggerFactory;

/**
 * @author CWDS API Team
 *
 */
public class MessageBuilder {
  ArrayList<ErrorMessage> messages;

  /**
   * messageBuilder - messageBuilder
   */
  public MessageBuilder() {
    messages = new ArrayList<>();
  }

  /**
   * @param message - message
   */
  public void addError(String message) {
    messages.add(new ErrorMessage(ErrorMessage.ErrorType.VALIDATION, message, ""));
  }

  /**
   * @param message - message
   * @param type - type
   */
  public void addError(String message, ErrorType type) {
    messages.add(new ErrorMessage(type, message, ""));
  }

  /**
   * @param errors - errors
   */
  public void addDomainValidationError(Set<ConstraintViolation<DomainObject>> errors) {
    if (!errors.isEmpty()) {
      errors.forEach(error -> {
        final String message = error.getPropertyPath() + " " + error.getMessage();
        // " getRootBean: " + error.getRootBean() + " getLeafBean: "
        // + error.getLeafBean() + " getConstraintDescriptor: " + error.getConstraintDescriptor()
        // + " ERROR PROP PATH: " + error.getPropertyPath() + " " + error.getMessage();
        messages.add(new ErrorMessage(ErrorMessage.ErrorType.VALIDATION, message, ""));
      });
    }
  }

  /**
   * @return the error message
   */
  public ArrayList<ErrorMessage> getMessages() {
    return messages == null ? new ArrayList<>() : messages;
  }

  public void addMessageAndLog(String message, org.slf4j.Logger logger){
    addError(message);
    logger.error(message);
  }

  public void addMessageAndLog(String message, Exception exception, org.slf4j.Logger logger){
    addError(message);
    logger.error(message, exception.getMessage());
  }
}
