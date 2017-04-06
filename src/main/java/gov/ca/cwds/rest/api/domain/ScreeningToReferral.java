package gov.ca.cwds.rest.api.domain;

import java.util.Set;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.validation.Date;
import io.dropwizard.jackson.JsonSnakeCase;
import io.swagger.annotations.ApiModelProperty;

/**
 * {@link DomainObject} representing a screening
 * 
 * @author CWDS API Team
 */
@SuppressWarnings("serial")
@JsonSnakeCase
public class ScreeningToReferral extends DomainObject implements Request {

  @JsonProperty("id")
  @ApiModelProperty(example = "12345")
  private long id;

  @JsonProperty("decision_rationale")
  @ApiModelProperty(example = "WXTSKD")
  @Size(max = 50)
  private String decisionRational;

  @Date
  @JsonProperty("ended_at")
  @ApiModelProperty(example = "2015-12-14")
  private String endedAt;

  @JsonProperty("incident_county")
  @ApiModelProperty(example = "Sacramento")
  @Size(max = 50)
  private String incidentCounty;

  @Date
  @JsonProperty("incident_date")
  @ApiModelProperty(example = "2015-01-13")
  private String incidentDate;

  @JsonProperty("location_type")
  @ApiModelProperty(example = "foster home")
  @Size(max = 50)
  private String locationType;

  @JsonProperty("communication_method")
  @ApiModelProperty(example = "email")
  @Size(max = 50)
  private String communicationMethod;

  @JsonProperty("name")
  @ApiModelProperty(example = "a referral name")
  @Size(max = 35)
  private String name;

  @JsonProperty("report_narrative")
  @ApiModelProperty(example = "On the evening of...")
  @Size(max = 1500)
  private String reportNarrative;

  @JsonProperty("reference")
  @ApiModelProperty(example = "reference WXTSKD")
  @Size(max = 50)
  private String reference;

  @JsonProperty("response_time")
  @ApiModelProperty(example = "immediate")
  @Size(max = 50)
  private String responseTime;

  @Date
  @JsonProperty("started_at")
  @ApiModelProperty(example = "2015-11-15")
  private String startedAt;

  @JsonProperty("assignee")
  @ApiModelProperty(example = "Mich Bastow")
  @Size(max = 50)
  private String assignee;

  @JsonProperty("additional_info_included")
  @ApiModelProperty(example = "")
  @Size(max = 50)
  private String additionalInfoIncluded;

  @ApiModelProperty(required = true, readOnly = false)
  @Valid
  private Address address;

  @JsonProperty("screening_decision")
  @ApiModelProperty(example = "Response time")
  @Size(max = 50)
  private String screeningDecision;

  @JsonProperty("screening_decision_detail")
  @ApiModelProperty(example = "decision detail")
  @Size(max = 1500)
  private String screeningDecisionDetail;

  @NotNull
  @ApiModelProperty(required = true, readOnly = false)
  @Valid
  private Set<Participant> participants;

  @JsonProperty("cross_reports")
  private Set<CrossReport> crossReports;

  @JsonProperty("allegations")
  private Set<Allegation> allegations;

  /**
   * @return the id
   */
  public long getId() {
    return id;
  }

  /**
   * @return the decisionRational
   */
  public String getDecisionRational() {
    return decisionRational;
  }

  /**
   * @return the endedAt
   */
  public String getEndedAt() {
    return endedAt;
  }

  /**
   * @return the incidentCounty
   */
  public String getIncidentCounty() {
    return incidentCounty;
  }

  /**
   * @return the incidentDate
   */
  public String getIncidentDate() {
    return incidentDate;
  }

  /**
   * @return the locationType
   */
  public String getLocationType() {
    return locationType;
  }

  /**
   * @return the communicationMethod
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
   * @return the report_narrative
   */
  public String getReportNarrative() {
    return reportNarrative;
  }

  /**
   * @return the reference
   */
  public String getReference() {
    return reference;
  }

  /**
   * @return the responseTime
   */
  public String getResponseTime() {
    return responseTime;
  }

  /**
   * @return the startedAt
   */
  public String getStartedAt() {
    return startedAt;
  }

  /**
   * @return the assignee
   */
  public String getAssignee() {
    return assignee;
  }

  /**
   * @return the additionalInfoIncluded
   */
  public String getAdditionalInfoIncluded() {
    return additionalInfoIncluded;
  }

  /**
   * @return the address
   */
  public Address getAddress() {
    return address;
  }

  /**
   * @return the screeningDecision
   */
  public String getScreeningDecision() {
    return screeningDecision;
  }

  /**
   * @return the screeningDecisionDetail
   */
  public String getScreeningDecisionDetail() {
    return screeningDecisionDetail;
  }

  /**
   * @return the participants
   */
  public Set<Participant> getParticipants() {
    return participants;
  }

  /**
   * @return the crossReports
   */
  public Set<CrossReport> getCrossReports() {
    return crossReports;
  }

  /**
   * @return the allegations
   */
  public Set<Allegation> getAllegations() {
    return allegations;
  }

  /**
   * empty constructor
   */
  public ScreeningToReferral() {

  }

  /**
   * @param id The id of the Screening
   * @param decisionRational Decision Rational
   * @param endedAt Ended At Date/time
   * @param incidentCounty County
   * @param incidentDate Date of incident
   * @param locationType location type
   * @param communicationMethod communication method
   * @param name name of screening
   * @param reportNarrative narrative
   * @param reference screening reference
   * @param responseTime screening repsonse time
   * @param startedAt started at
   * @param assignee assignee
   * @param additionalInfoIncluded additional iformation included
   * @param address address of incident
   * @param screeningDecision screening decision
   * @param screeningDecisionDetail screening decision detail
   * @param participants participants of screening
   * @param crossReports cross reports of screening
   * @param allegations allegations of screening
   */
  public ScreeningToReferral(@JsonProperty("id") long id,
      @JsonProperty("decision_rational") String decisionRational,
      @JsonProperty("ended_at") String endedAt,
      @JsonProperty("incident_county") String incidentCounty,
      @JsonProperty("incident_date") String incidentDate,
      @JsonProperty("location_type") String locationType,
      @JsonProperty("communication_method") String communicationMethod,
      @JsonProperty("name") String name, @JsonProperty("report_narrative") String reportNarrative,
      @JsonProperty("reference") String reference,
      @JsonProperty("response_time") String responseTime,
      @JsonProperty("started_at") String startedAt, @JsonProperty("assignee") String assignee,
      @JsonProperty("additional_info_included") String additionalInfoIncluded,
      @JsonProperty("address") Address address,
      @JsonProperty("screening_decision") String screeningDecision,
      @JsonProperty("screening_decision_detail") String screeningDecisionDetail,
      @JsonProperty("participants") Set<Participant> participants,
      @JsonProperty("cross_reports") Set<CrossReport> crossReports,
      @JsonProperty("allegations") Set<Allegation> allegations) {
    super();
    this.id = id;
    this.decisionRational = decisionRational;
    this.endedAt = endedAt;
    this.incidentCounty = incidentCounty;
    this.incidentDate = incidentDate;
    this.locationType = locationType;
    this.communicationMethod = communicationMethod;
    this.name = name;
    this.reportNarrative = reportNarrative;
    this.reference = reference;
    this.responseTime = responseTime;
    this.startedAt = startedAt;
    this.assignee = assignee;
    this.additionalInfoIncluded = additionalInfoIncluded;
    this.address = address;
    this.screeningDecision = screeningDecision;
    this.screeningDecisionDetail = screeningDecisionDetail;
    this.participants = participants;
    this.crossReports = crossReports;
    this.allegations = allegations;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    ScreeningToReferral other = (ScreeningToReferral) obj;
    if (additionalInfoIncluded == null) {
      if (other.additionalInfoIncluded != null)
        return false;
    } else if (!additionalInfoIncluded.equals(other.additionalInfoIncluded))
      return false;
    if (address == null) {
      if (other.address != null)
        return false;
    } else if (!address.equals(other.address))
      return false;
    if (allegations == null) {
      if (other.allegations != null)
        return false;
    } else if (!allegations.equals(other.allegations))
      return false;
    if (assignee == null) {
      if (other.assignee != null)
        return false;
    } else if (!assignee.equals(other.assignee))
      return false;
    if (communicationMethod == null) {
      if (other.communicationMethod != null)
        return false;
    } else if (!communicationMethod.equals(other.communicationMethod))
      return false;
    if (crossReports == null) {
      if (other.crossReports != null)
        return false;
    } else if (!crossReports.equals(other.crossReports))
      return false;
    if (decisionRational == null) {
      if (other.decisionRational != null)
        return false;
    } else if (!decisionRational.equals(other.decisionRational))
      return false;
    if (endedAt == null) {
      if (other.endedAt != null)
        return false;
    } else if (!endedAt.equals(other.endedAt))
      return false;
    if (id != other.id)
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
    if (participants == null) {
      if (other.participants != null)
        return false;
    } else if (!participants.equals(other.participants))
      return false;
    if (reference == null) {
      if (other.reference != null)
        return false;
    } else if (!reference.equals(other.reference))
      return false;
    if (reportNarrative == null) {
      if (other.reportNarrative != null)
        return false;
    } else if (!reportNarrative.equals(other.reportNarrative))
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
    if (screeningDecisionDetail == null) {
      if (other.screeningDecisionDetail != null)
        return false;
    } else if (!screeningDecisionDetail.equals(other.screeningDecisionDetail))
      return false;
    if (startedAt == null) {
      if (other.startedAt != null)
        return false;
    } else if (!startedAt.equals(other.startedAt))
      return false;
    return true;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result =
        prime * result + ((additionalInfoIncluded == null) ? 0 : additionalInfoIncluded.hashCode());
    result = prime * result + ((address == null) ? 0 : address.hashCode());
    result = prime * result + ((allegations == null) ? 0 : allegations.hashCode());
    result = prime * result + ((assignee == null) ? 0 : assignee.hashCode());
    result = prime * result + ((communicationMethod == null) ? 0 : communicationMethod.hashCode());
    result = prime * result + ((crossReports == null) ? 0 : crossReports.hashCode());
    result = prime * result + ((decisionRational == null) ? 0 : decisionRational.hashCode());
    result = prime * result + ((endedAt == null) ? 0 : endedAt.hashCode());
    result = prime * result + (int) (id ^ (id >>> 32));
    result = prime * result + ((incidentCounty == null) ? 0 : incidentCounty.hashCode());
    result = prime * result + ((incidentDate == null) ? 0 : incidentDate.hashCode());
    result = prime * result + ((locationType == null) ? 0 : locationType.hashCode());
    result = prime * result + ((name == null) ? 0 : name.hashCode());
    result = prime * result + ((participants == null) ? 0 : participants.hashCode());
    result = prime * result + ((reference == null) ? 0 : reference.hashCode());
    result = prime * result + ((reportNarrative == null) ? 0 : reportNarrative.hashCode());
    result = prime * result + ((responseTime == null) ? 0 : responseTime.hashCode());
    result = prime * result + ((screeningDecision == null) ? 0 : screeningDecision.hashCode());
    result = prime * result
        + ((screeningDecisionDetail == null) ? 0 : screeningDecisionDetail.hashCode());
    result = prime * result + ((startedAt == null) ? 0 : startedAt.hashCode());
    return result;
  }

}
