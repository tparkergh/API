package gov.ca.cwds.rest.api.domain;

import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.validation.Date;
import io.dropwizard.jackson.JsonSnakeCase;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * {@link DomainObject} representing a screening
 * 
 * @author CWDS API Team
 */
@JsonSnakeCase
public class Screening extends DomainObject implements Request {
  @JsonProperty("reference")
  @ApiModelProperty(example = "WXTSKD")
  @Size(max = 50)
  private String reference;

  @Date
  @JsonProperty("ended_at")
  @ApiModelProperty(example = "12/01/2015")
  private String endedAt;

  @JsonProperty("incident_county")
  @ApiModelProperty(example = "Sacramento")
  @Size(max = 50)
  private String incidentCounty;

  @Date
  @JsonProperty("incident_date")
  @ApiModelProperty(example = "11/01/2015")
  private String incidentDate;

  @JsonProperty("location_type")
  @ApiModelProperty(example = "home")
  @Size(max = 50)
  private String locationType;

  @JsonProperty("communication_method")
  @ApiModelProperty(example = "email")
  @Size(max = 50)
  private String communicationMethod;

  @JsonProperty("name")
  @ApiModelProperty(example = "Some Screening name")
  @Size(max = 50)
  private String name;

  @JsonProperty("response_time")
  @ApiModelProperty(example = "FYI : We aren't storing this???")
  private String responseTime;

  @JsonProperty("screening_decision")
  @ApiModelProperty(example = "Decision")
  @Size(max = 50)
  private String screeningDecision;

  @Date
  @JsonProperty("started_at")
  @ApiModelProperty(example = "10/01/2015")
  private String startedAt;

  @JsonProperty("narrative")
  @ApiModelProperty(example = "On the evening of...")
  @Size(max = 1500)
  private String narrative;

  /**
   * 
   */
  public Screening() {
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
   */
  @JsonCreator
  public Screening(@JsonProperty("reference") String reference,
      @JsonProperty("ended_at") String endedAt,
      @JsonProperty("incident_county") String incidentCounty,
      @JsonProperty("incident_date") String incidentDate,
      @JsonProperty("location_type") String locationType,
      @JsonProperty("communication_method") String communicationMethod,
      @JsonProperty("name") String name, @JsonProperty("response_time") String responseTime,
      @JsonProperty("screening_decision") String screeningDecision,
      @JsonProperty("started_at") String startedAt, @JsonProperty("narrative") String narrative) {
    super();
    this.reference = reference;
    this.endedAt = endedAt;
    this.incidentCounty = incidentCounty;
    this.incidentDate = incidentDate;
    this.locationType = locationType;
    this.communicationMethod = communicationMethod;
    this.name = name;
    this.responseTime = responseTime;
    this.screeningDecision = screeningDecision;
    this.startedAt = startedAt;
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
  public Screening(gov.ca.cwds.data.persistence.ns.Screening screening) {
    this.reference = screening.getReference();
    this.endedAt = DomainChef.cookDate(screening.getEndedAt());
    this.incidentCounty = screening.getIncidentCounty();
    this.incidentDate = DomainChef.cookDate(screening.getIncidentDate());
    this.locationType = screening.getLocationType();
    this.communicationMethod = screening.getCommunicationMethod();
    this.name = screening.getName();
    this.screeningDecision = screening.getScreeningDecision();
    this.startedAt = DomainChef.cookDate(screening.getStartedAt());
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
  public String getEndedAt() {
    return endedAt;
  }

  /**
   * @return the incident_county
   */
  public String getIncidentCounty() {
    return incidentCounty;
  }

  /**
   * @return the incident_date
   */
  public String getIncidentDate() {
    return incidentDate;
  }

  /**
   * @return the location_type
   */
  public String getLocationType() {
    return locationType;
  }

  /**
   * @return the communication_method
   */
  public String getCommunicationMethod() {
    return communicationMethod;
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
  public String getResponseTime() {
    return responseTime;
  }

  /**
   * @return the screening_decision
   */
  public String getScreeningDecision() {
    return screeningDecision;
  }

  /**
   * @return the started_at
   */
  public String getStartedAt() {
    return startedAt;
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
    result = prime * result + ((communicationMethod == null) ? 0 : communicationMethod.hashCode());
    result = prime * result + ((endedAt == null) ? 0 : endedAt.hashCode());
    result = prime * result + ((incidentCounty == null) ? 0 : incidentCounty.hashCode());
    result = prime * result + ((incidentDate == null) ? 0 : incidentDate.hashCode());
    result = prime * result + ((locationType == null) ? 0 : locationType.hashCode());
    result = prime * result + ((name == null) ? 0 : name.hashCode());
    result = prime * result + ((narrative == null) ? 0 : narrative.hashCode());
    result = prime * result + ((reference == null) ? 0 : reference.hashCode());
    result = prime * result + ((responseTime == null) ? 0 : responseTime.hashCode());
    result = prime * result + ((screeningDecision == null) ? 0 : screeningDecision.hashCode());
    result = prime * result + ((startedAt == null) ? 0 : startedAt.hashCode());
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
    if (communicationMethod == null) {
      if (other.communicationMethod != null)
        return false;
    } else if (!communicationMethod.equals(other.communicationMethod))
      return false;
    if (endedAt == null) {
      if (other.endedAt != null)
        return false;
    } else if (!endedAt.equals(other.endedAt))
      return false;
    if (incidentCounty == null) {
      if (other.incidentCounty != null)
        return false;
    } else if (!incidentCounty.equals(other.incidentCounty))
      return false;
    if (incidentDate == null) {
      if (other.incidentDate != null)
        return false;
    } else if (!incidentDate.equals(other.incidentDate))
      return false;
    if (locationType == null) {
      if (other.locationType != null)
        return false;
    } else if (!locationType.equals(other.locationType))
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
    if (responseTime == null) {
      if (other.responseTime != null)
        return false;
    } else if (!responseTime.equals(other.responseTime))
      return false;
    if (screeningDecision == null) {
      if (other.screeningDecision != null)
        return false;
    } else if (!screeningDecision.equals(other.screeningDecision))
      return false;
    if (startedAt == null) {
      if (other.startedAt != null)
        return false;
    } else if (!startedAt.equals(other.startedAt))
      return false;
    return true;
  }
}
