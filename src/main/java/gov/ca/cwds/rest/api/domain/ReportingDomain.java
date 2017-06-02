package gov.ca.cwds.rest.api.domain;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.error.ErrorMessage;

/**
 * @author CWDS API Team
 */
public abstract class ReportingDomain extends DomainObject implements Request, Response {

  /**
   * Serialization version
   */
  private static final long serialVersionUID = 1L;

  @JsonIgnore
  private Set<ErrorMessage> messages = new HashSet<>();

  @Override
  public Set<ErrorMessage> getMessages() {
    return messages;
  }

  /**
   * @param errorMessage message to add
   */
  public void addMessage(ErrorMessage errorMessage) {
    if (messages == null) {
      messages = new HashSet<>();
    }
    messages.add(errorMessage);
  }

  /**
   * @param messages Set of messages
   */
  public void setMessages(Set<ErrorMessage> messages) {
    this.messages = messages;
  }

  @Override
  public boolean hasMessages() {
    return messages != null && !messages.isEmpty();
  }
}
