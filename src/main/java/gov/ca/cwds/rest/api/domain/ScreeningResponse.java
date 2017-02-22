package gov.ca.cwds.rest.api.domain;

import gov.ca.cwds.rest.api.Response;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableSet;

/**
 * {@link DomainObject} representing a screening response
 * 
 * @author CWDS API Team
 */
@JsonInclude(Include.ALWAYS)
public class ScreeningResponse extends Screening implements Response {

  /**
   * Default
   */
  private static final long serialVersionUID = 1L;

  @JsonProperty("address")
  private Address address;

  @JsonProperty("participants")
  private Set<Participant> participants;

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
   * @param endedAt The ended at
   * @param incidentCounty The incident county
   * @param incidentDate The incident date
   * @param locationType The location type
   * @param communicationMethod The communication method
   * @param name The name
   * @param responseTime The response time
   * @param screeningDecision The screening decision
   * @param startedAt The started at
   * @param narrative The narrative
   * @param address The {@link Address}
   * @param participants The {@link Set}
   */
  public ScreeningResponse(String reference, String endedAt, String incidentCounty,
      String incidentDate, String locationType, String communicationMethod, String name,
      String responseTime, String screeningDecision, String startedAt, String narrative,
      Address address, Set<Participant> participants) {
    super(reference, endedAt, incidentCounty, incidentDate, locationType, communicationMethod,
        name, responseTime, screeningDecision, startedAt, narrative);
    this.address = address;
    this.participants = participants;
  }

  /**
   * Constructor
   * 
   * @param screening The persistent version of a screening
   * @param participants The list of participants for this screening
   */
  public ScreeningResponse(gov.ca.cwds.data.persistence.ns.Screening screening,
      Set<gov.ca.cwds.data.persistence.ns.Participant> participants) {
    super(screening);
    if (screening.getContactAddress() != null) {
      this.address = new Address(screening.getContactAddress());
    }
    ImmutableSet.Builder<Participant> participantSetBuilder = ImmutableSet.builder();
    if (participants != null) {
      for (gov.ca.cwds.data.persistence.ns.Participant participant : participants) {
        participantSetBuilder.add(new Participant(participant));
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
  public Set<Participant> getParticipants() {
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
