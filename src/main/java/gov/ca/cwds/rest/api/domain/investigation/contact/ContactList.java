package gov.ca.cwds.rest.api.domain.investigation.contact;

import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.DomainObject;
import gov.ca.cwds.rest.api.domain.ReportingDomain;
import io.dropwizard.jackson.JsonSnakeCase;
import io.swagger.annotations.ApiModel;

import java.util.Set;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * {@link DomainObject} representing a Contact List response
 * 
 * @author CWDS API Team
 */
@ApiModel
@JsonSnakeCase
public class ContactList extends ReportingDomain implements Request, Response {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  @JsonUnwrapped
  private Set<Contact> contacts;

  /**
   * 
   */
  public ContactList() {
    // default
  }


  public ContactList(Set<Contact> contacts) {
    this.contacts = contacts;
  }

  /**
   * @return the contacts
   */
  @JsonValue
  public Set<Contact> getContacts() {
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
