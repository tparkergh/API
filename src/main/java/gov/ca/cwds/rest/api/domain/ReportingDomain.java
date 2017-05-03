package gov.ca.cwds.rest.api.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.error.ErrorMessage;

import java.util.HashSet;
import java.util.Set;

public abstract class ReportingDomain extends DomainObject implements Request, Response {
     @JsonIgnore
    private Set<ErrorMessage> messages = new HashSet();

    public Set<ErrorMessage> getMessages() {
        return messages;
    }

    public void addMessage(ErrorMessage errorMessage) {
        if (messages == null){
            messages = new HashSet();
        }
        messages.add(errorMessage);
    }

    public void setMessages(Set<ErrorMessage> messages) {
        this.messages = messages;
    }

    public boolean hasMessages(){
        return messages != null && ! messages.isEmpty();
    }
}
