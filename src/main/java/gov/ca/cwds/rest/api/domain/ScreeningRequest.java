package gov.ca.cwds.rest.api.domain;

import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.rest.api.Request;
import io.dropwizard.jackson.JsonSnakeCase;

/**
 * {@link DomainObject} representing a screening request.
 * 
 * @author CWDS API Team
 */
@JsonSnakeCase
public class ScreeningRequest extends Screening implements Request {
  @JsonProperty("address")
  private Address address;

  @JsonProperty("participant_ids")
  private List<Long> participantIds;

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
   * @param participantIds The {@link List}
   */
  @JsonCreator
  public ScreeningRequest(@JsonProperty("reference") String reference,
      @JsonProperty("ended_at") String endedAt,
      @JsonProperty("incident_county") String incidentCounty,
      @JsonProperty("incident_date") String incidentDate,
      @JsonProperty("location_type") String locationType,
      @JsonProperty("communication_method") String communicationMethod,
      @JsonProperty("name") String name, @JsonProperty("response_time") String responseTime,
      @JsonProperty("screening_decision") String screeningDecision,
      @JsonProperty("started_at") String startedAt, @JsonProperty("narrative") String narrative,
      @JsonProperty("address") Address address,
      @JsonProperty("participant_ids") List<Long> participantIds) {
    super(reference, endedAt, incidentCounty, incidentDate, locationType, communicationMethod, name,
        responseTime, screeningDecision, startedAt, narrative);
    this.address = address;
    this.participantIds = participantIds;
  }

  /**
   * @return the address
   */
  public Address getAddress() {
    return address;
  }

  /**
   * @return the participant_ids
   */
  public List<Long> getParticipantIds() {
    return participantIds;
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.lang.Object#hashCode()
   */
  @Override
  public final int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((address == null) ? 0 : address.hashCode());
    result = prime * result + ((participantIds == null) ? 0 : participantIds.hashCode());
    return result;
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public final boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (!(getClass().isInstance(obj)))
      return false;
    ScreeningRequest other = (ScreeningRequest) obj;
    if (address == null) {
      if (other.address != null)
        return false;
    } else if (!address.equals(other.address))
      return false;
    if (participantIds == null) {
      if (other.participantIds != null)
        return false;
    } else if (!Arrays.equals(participantIds.toArray(), other.participantIds.toArray())) {
      return false;
    }

    return true;
  }
}
