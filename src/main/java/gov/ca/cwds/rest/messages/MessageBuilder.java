package gov.ca.cwds.rest.messages;

import gov.ca.cwds.rest.api.domain.DomainObject;
import gov.ca.cwds.rest.api.domain.error.ErrorMessage;
import gov.ca.cwds.rest.api.domain.error.ErrorMessage.ErrorType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import javax.validation.ConstraintViolation;

public class MessageBuilder {
  ArrayList<ErrorMessage> messages;

  public MessageBuilder(){
    messages = new ArrayList<>();
  }

  public void addError(String message) {
    messages.add(new ErrorMessage(ErrorMessage.ErrorType.VALIDATION, message, ""));
  }

  public void addError(String message, ErrorType type) {
    messages.add(new ErrorMessage(type, message, ""));
  }

  public void addDomainValidationError( Set<ConstraintViolation<DomainObject>> errors) {
    if (!errors.isEmpty()) {
      errors.forEach(error -> {
        final String message = error.getMessage();
//            " getRootBean: " + error.getRootBean() + " getLeafBean: "
//            + error.getLeafBean() + " getConstraintDescriptor: " + error.getConstraintDescriptor()
//            + " ERROR PROP PATH: " + error.getPropertyPath() + " " + error.getMessage();
        messages.add(new ErrorMessage(ErrorMessage.ErrorType.VALIDATION, message, ""));
      });
    }
  }

  public ArrayList<ErrorMessage> getMessages(){
    return messages == null ? new ArrayList() : messages;
  }
}
