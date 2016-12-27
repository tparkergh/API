package gov.ca.cwds.rest.api.domain;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableSet;

import gov.ca.cwds.rest.api.Response;

/**
 * {@link DomainObject} representing a screening response
 * 
 * @author CWDS API Team
 */
@JsonInclude(Include.ALWAYS)
public class ScreeningResponse extends Screening implements Response {
  @JsonProperty("address")
  private Address address;

  @JsonProperty("participants")
  private Set<Person> participants;

  /**
   * 
   */
  public ScreeningResponse() {
    super();
  }

  /**
   * Constructor
   * 
   * @param reference The reference
   * @param ended_at The ended at
   * @param incident_county The incident county
   * @param incident_date The incident date
   * @param location_type The location type
   * @param communication_method The communication method
   * @param name The name
   * @param response_time The response time
   * @param screening_decision The screening decision
   * @param started_at The started at
   * @param narrative The narrative
   * @param address The {@link Address}
   * @param participants The {@link Set}
   */
  public ScreeningResponse(String reference, String ended_at, String incident_county,
      String incident_date, String location_type, String communication_method, String name,
      String response_time, String screening_decision, String started_at, String narrative,
      Address address, Set<Person> participants) {
    super(reference, ended_at, incident_county, incident_date, location_type, communication_method,
        name, response_time, screening_decision, started_at, narrative);
    this.address = address;
    this.participants = participants;
  }

  /**
   * Constructor
   * 
   * @param screening The persistent version of a screening
   * @param participants The list of participants for this screening
   */
  public ScreeningResponse(gov.ca.cwds.rest.api.persistence.ns.Screening screening,
      Set<gov.ca.cwds.rest.api.persistence.ns.Person> participants) {
    super(screening);
    if (screening.getContactAddress() != null) {
      this.address = new Address(screening.getContactAddress());
    }
    ImmutableSet.Builder<Person> participantSetBuilder = ImmutableSet.builder();
    if (participants != null) {
      for (gov.ca.cwds.rest.api.persistence.ns.Person person : participants) {
        participantSetBuilder.add(new Person(person));
      }
    }
    this.participants = participantSetBuilder.build();
  }

  /**
   * Constructor
   * 
   * @param reference the screening reference
   */
  public ScreeningResponse(String reference) {
    super(reference);
  }

  /**
   * @return the address
   */
  public Address getAddress() {
    return address;
  }

  /**
   * @return the participants
   */
  public Set<Person> getParticipants() {
    return participants;
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((address == null) ? 0 : address.hashCode());
    result = prime * result + ((participants == null) ? 0 : participants.hashCode());
    return result;
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;

    if (!(obj instanceof ScreeningResponse)) {
      return false;
    }

    ScreeningResponse other = (ScreeningResponse) obj;
    if (address == null) {
      if (other.address != null)
        return false;
    } else if (!address.equals(other.address))
      return false;
    if (participants == null) {
      if (other.participants != null)
        return false;
    } else if (!participants.equals(other.participants))
      return false;

    return true;
  }

}
