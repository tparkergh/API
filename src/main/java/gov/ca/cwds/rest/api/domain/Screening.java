package gov.ca.cwds.rest.api.domain;

import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.validation.Date;
import io.swagger.annotations.ApiModelProperty;

/**
 * {@link DomainObject} representing a screening
 * 
 * @author CWDS API Team
 */
public class Screening extends DomainObject implements Request {
  @JsonProperty("reference")
  @ApiModelProperty(example = "WXTSKD")
  @Size(max = 50)
  private String reference;

  @Date
  @JsonProperty("ended_at")
  @ApiModelProperty(example = "12/01/2015")
  private String ended_at;

  @JsonProperty("incident_county")
  @ApiModelProperty(example = "Sacramento")
  @Size(max = 50)
  private String incident_county;

  @Date
  @JsonProperty("incident_date")
  @ApiModelProperty(example = "11/01/2015")
  private String incident_date;

  @JsonProperty("location_type")
  @ApiModelProperty(example = "home")
  @Size(max = 50)
  private String location_type;

  @JsonProperty("communication_method")
  @ApiModelProperty(example = "email")
  @Size(max = 50)
  private String communication_method;

  @JsonProperty("name")
  @ApiModelProperty(example = "Some Screening name")
  @Size(max = 50)
  private String name;

  @JsonProperty("response_time")
  @ApiModelProperty(example = "FYI : We aren't storing this???")
  private String response_time;

  @JsonProperty("screening_decision")
  @ApiModelProperty(example = "Decision")
  @Size(max = 50)
  private String screening_decision;

  @Date
  @JsonProperty("started_at")
  @ApiModelProperty(example = "10/01/2015")
  private String started_at;

  @JsonProperty("narrative")
  @ApiModelProperty(example = "On the evening of...")
  @Size(max = 1500)
  private String narrative;

  public Screening() {
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
   */
  @JsonCreator
  public Screening(@JsonProperty("reference") String reference,
      @JsonProperty("ended_at") String ended_at,
      @JsonProperty("incident_county") String incident_county,
      @JsonProperty("incident_date") String incident_date,
      @JsonProperty("location_type") String location_type,
      @JsonProperty("communication_method") String communication_method,
      @JsonProperty("name") String name, @JsonProperty("response_time") String response_time,
      @JsonProperty("screening_decision") String screening_decision,
      @JsonProperty("started_at") String started_at, @JsonProperty("narrative") String narrative) {
    super();
    this.reference = reference;
    this.ended_at = ended_at;
    this.incident_county = incident_county;
    this.incident_date = incident_date;
    this.location_type = location_type;
    this.communication_method = communication_method;
    this.name = name;
    this.response_time = response_time;
    this.screening_decision = screening_decision;
    this.started_at = started_at;
    this.narrative = narrative;
  }

  /**
   * Constructor
   * 
   * @param reference the reference for this screening
   */
  public Screening(String reference) {
    super();
    this.reference = reference;
  }

  /**
   * Construct from persistence level object.
   * 
   * @param screening persistence level screening object
   */
  public Screening(gov.ca.cwds.rest.api.persistence.ns.Screening screening) {
    this.reference = screening.getReference();
    this.ended_at = DomainObject.cookDate(screening.getEndedAt());
    this.incident_county = screening.getIncidentCounty();
    this.incident_date = DomainObject.cookDate(screening.getIncidentDate());
    this.location_type = screening.getLocationType();
    this.communication_method = screening.getCommunicationMethod();
    this.name = screening.getName();
    this.screening_decision = screening.getScreeningDecision();
    this.started_at = DomainObject.cookDate(screening.getStartedAt());
    this.narrative = screening.getNarrative();
  }

  /**
   * @return the reference
   */
  public String getReference() {
    return reference;
  }

  /**
   * @return the ended_at
   */
  public String getEnded_at() {
    return ended_at;
  }

  /**
   * @return the incident_county
   */
  public String getIncident_county() {
    return incident_county;
  }

  /**
   * @return the incident_date
   */
  public String getIncident_date() {
    return incident_date;
  }

  /**
   * @return the location_type
   */
  public String getLocation_type() {
    return location_type;
  }

  /**
   * @return the communication_method
   */
  public String getCommunication_method() {
    return communication_method;
  }

  /**
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * @return the response_time
   */
  public String getResponse_time() {
    return response_time;
  }

  /**
   * @return the screening_decision
   */
  public String getScreening_decision() {
    return screening_decision;
  }

  /**
   * @return the started_at
   */
  public String getStarted_at() {
    return started_at;
  }

  /**
   * @return the narrative
   */
  public String getNarrative() {
    return narrative;
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
    result =
        prime * result + ((communication_method == null) ? 0 : communication_method.hashCode());
    result = prime * result + ((ended_at == null) ? 0 : ended_at.hashCode());
    result = prime * result + ((incident_county == null) ? 0 : incident_county.hashCode());
    result = prime * result + ((incident_date == null) ? 0 : incident_date.hashCode());
    result = prime * result + ((location_type == null) ? 0 : location_type.hashCode());
    result = prime * result + ((name == null) ? 0 : name.hashCode());
    result = prime * result + ((narrative == null) ? 0 : narrative.hashCode());
    result = prime * result + ((reference == null) ? 0 : reference.hashCode());
    result = prime * result + ((response_time == null) ? 0 : response_time.hashCode());
    result = prime * result + ((screening_decision == null) ? 0 : screening_decision.hashCode());
    result = prime * result + ((started_at == null) ? 0 : started_at.hashCode());
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

    if (!(obj instanceof Screening)) {
      return false;
    }

    Screening other = (Screening) obj;
    if (communication_method == null) {
      if (other.communication_method != null)
        return false;
    } else if (!communication_method.equals(other.communication_method))
      return false;
    if (ended_at == null) {
      if (other.ended_at != null)
        return false;
    } else if (!ended_at.equals(other.ended_at))
      return false;
    if (incident_county == null) {
      if (other.incident_county != null)
        return false;
    } else if (!incident_county.equals(other.incident_county))
      return false;
    if (incident_date == null) {
      if (other.incident_date != null)
        return false;
    } else if (!incident_date.equals(other.incident_date))
      return false;
    if (location_type == null) {
      if (other.location_type != null)
        return false;
    } else if (!location_type.equals(other.location_type))
      return false;
    if (name == null) {
      if (other.name != null)
        return false;
    } else if (!name.equals(other.name))
      return false;
    if (narrative == null) {
      if (other.narrative != null)
        return false;
    } else if (!narrative.equals(other.narrative))
      return false;
    if (reference == null) {
      if (other.reference != null)
        return false;
    } else if (!reference.equals(other.reference))
      return false;
    if (response_time == null) {
      if (other.response_time != null)
        return false;
    } else if (!response_time.equals(other.response_time))
      return false;
    if (screening_decision == null) {
      if (other.screening_decision != null)
        return false;
    } else if (!screening_decision.equals(other.screening_decision))
      return false;
    if (started_at == null) {
      if (other.started_at != null)
        return false;
    } else if (!started_at.equals(other.started_at))
      return false;
    return true;
  }
}
