package gov.ca.cwds.rest.api.domain.investigation;

import java.util.Set;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.ReportingDomain;

/**
 *
 * 
 * @author CWDS API Team
 */
@JsonInclude(Include.ALWAYS)
public final class People extends ReportingDomain implements Request, Response {

  private static final long serialVersionUID = 1L;

  @JsonProperty("people")
  private Set<Person> persons;

  /**
   * empty consturctor
   */
  public People() {

  }

  /**
   * @param people - set of Person
   */
  public People(Set<Person> people) {
    this.persons = people;
  }

  /**
   * @return - list of people associated with the investigationS
   */
  @JsonValue
  public Set<Person> getPersons() {
    return persons;
  }

  @Override
  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this, false);
  }

  @Override
  public boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj, false);
  }

}
