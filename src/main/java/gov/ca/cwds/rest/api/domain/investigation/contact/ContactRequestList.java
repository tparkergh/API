package gov.ca.cwds.rest.api.domain.investigation.contact;

import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.domain.DomainObject;

import java.util.Set;

import javax.validation.Valid;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * {@link DomainObject} representing a Contact List request
 * 
 * @author CWDS API Team
 */
@JsonInclude(Include.ALWAYS)
public class ContactRequestList implements Request {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  @Valid
  @JsonProperty("contacts")
  private Set<ContactRequest> contacts;

  /**
   * 
   */
  public ContactRequestList() {
    // default
  }


  public ContactRequestList(Set<ContactRequest> contacts) {
    this.contacts = contacts;
  }

  /**
   * @return the contacts
   */
  public Set<ContactRequest> getContacts() {
    return contacts;
  }


  /**
   * {@inheritDoc}
   *
   * @see java.lang.Object#hashCode()
   */
  @Override
  public final int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this, false);
  }

  /**
   * {@inheritDoc}
   *
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public final boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj, false);
  }


}
