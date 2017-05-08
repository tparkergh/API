package gov.ca.cwds.rest.api.domain;

import java.util.Set;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.validation.Date;
import io.dropwizard.jackson.JsonSnakeCase;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author CWDS API Team
 */
@JsonSnakeCase
@JsonPropertyOrder({"id", "legacySourceTable", "referralId", "endedAt", "incidentCounty",
    "incidentDate", "locationType", "communicationMethod", "email", "name", "reportNarrative",
    "reference", "responseTime", "startedAt", "assignee", "additionalInformation",
    "screeningDecision", "screeningDecisionDetail", "address", "participants", "crossReports",
    "allegations"})
public class ScreeningToReferral extends ReportingDomain implements Request {

  /**
   * Serialization version
   */
  private static final long serialVersionUID = 1L;

  @JsonProperty("id")
  @Min(1)
  @NotNull
  @ApiModelProperty(required = true, readOnly = false, value = "Screening ID", example = "12345")
  private long id;

  @JsonProperty("legacy_source_table")
  @ApiModelProperty(required = false, readOnly = false, value = "Legacy Table", example = "REFERL")
  @NotNull
  private String legacySourceTable;

  @JsonProperty("legacy_id")
  @ApiModelProperty(required = true, readOnly = false, value = "Legacy Referral Id",
      example = "2345678ABC")
  @Size(max = 10)
  @NotNull
  private String referralId;

  @JsonProperty("ended_at")
  @ApiModelProperty(required = false, readOnly = false, value = "Screening end date/time",
      example = "2016-08-03T01:00:00.000Z")
  private String endedAt;

  @JsonProperty("incident_county")
  @NotEmpty
  @ApiModelProperty(required = true, readOnly = false, value = "County where incident occurred",
      example = "Sacramento")
  @Size(max = 50)
  private String incidentCounty;

  @Date
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT)
  @JsonProperty("incident_date")
  @NotEmpty
  @ApiModelProperty(required = true, readOnly = false, value = "Incident date",
      example = "2015-01-13")
  private String incidentDate;

  @JsonProperty("location_type")
  @NotEmpty
  @ApiModelProperty(required = true, readOnly = false, value = "Location Type",
      example = "foster home")
  @Size(max = 75)
  private String locationType;

  @JsonProperty("communication_method")
  @NotEmpty
  @ApiModelProperty(required = true, readOnly = false, value = "Communication Method",
      example = "email")
  @Size(max = 50)
  private String communicationMethod;

  @JsonProperty("name")
  @NotEmpty
  @ApiModelProperty(required = true, readOnly = false, value = "Title/Name of referral",
      example = "a referral name")
  @Size(max = 35)
  private String name;

  @JsonProperty("report_narrative")
  @ApiModelProperty(required = false, readOnly = false, value = "Report Narrative",
      example = "On the evening of...")
  @Size(max = 254)
  private String reportNarrative;

  @JsonProperty("reference")
  @ApiModelProperty(required = false, readOnly = false, value = "Referrence",
      example = "reference WXTSKD")
  @Size(max = 50)
  private String reference;

  @JsonProperty("response_time")
  @NotEmpty
  @ApiModelProperty(required = true, readOnly = false, value = "Response time",
      example = "immediate")
  @Size(max = 50)
  private String responseTime;

  @JsonProperty("started_at")
  @NotEmpty
  @ApiModelProperty(required = true, readOnly = false, value = "Date/time incident started",
      example = "2016-08-03T01:00:00.000Z")
  private String startedAt;

  @JsonProperty("assignee")
  @NotEmpty
  @ApiModelProperty(required = true, readOnly = false, value = "Assigned social worker",
      example = "Mich Bastow")
  @Size(max = 50)
  private String assignee;

  @JsonProperty("additional_information")
  @ApiModelProperty(required = false, readOnly = false, value = "Additional Information",
      example = "more information about this referral")
  @Size(max = 50)
  private String additionalInformation;

  @JsonProperty("screening_decision")
  @NotEmpty
  @ApiModelProperty(required = true, readOnly = false, value = "Screening Decision",
      example = "Response time")
  @Size(max = 50)
  private String screeningDecision;

  @JsonProperty("screening_decision_detail")
  @ApiModelProperty(required = false, readOnly = false, value = "Screening decision detail",
      example = "decision detail")
  @Size(max = 1500)
  private String screeningDecisionDetail;

  @NotNull
  @ApiModelProperty(required = true, readOnly = false)
  @Valid
  private Address address;

  @NotEmpty
  @ApiModelProperty(required = true, readOnly = false)
  @Valid
  private Set<Participant> participants;

  @JsonProperty("cross_reports")
  @ApiModelProperty(required = false, readOnly = false)
  @Valid
  private Set<CrossReport> crossReports;

  @JsonProperty("allegations")
  @NotEmpty
  @ApiModelProperty(required = true, readOnly = false)
  @Valid
  private Set<Allegation> allegations;

  @SuppressWarnings("javadoc")
  public ScreeningToReferral() {
    super();
  }

  /**
   * @param id - the screening Id
   * @param legacySourceTable - legacy source table
   * @param referralId - the referral Id
   * @param endedAt - screening ended at date
   * @param incidentCounty - county of incident
   * @param incidentDate - date of incident
   * @param locationType - location type
   * @param communicationMethod - communication method
   * @param name - screening name
   * @param reportNarrative - narrative
   * @param reference - referrence
   * @param responseTime - response time
   * @param startedAt - response started at time
   * @param assignee - staff person assigned
   * @param additionalInformation = additional information
   * @param screeningDecision - screening decesion
   * @param screeningDecisionDetail - screening decision detail
   * @param address - address associated with participants
   * @param participants - participants associcated with this screening
   * @param crossReports - Cross Reort
   * @param allegations - Allegtions
   */
  public ScreeningToReferral(long id, String legacySourceTable, String referralId,
      @Date String endedAt, String incidentCounty, @Date String incidentDate, String locationType,
      String communicationMethod, String name, String reportNarrative, String reference,
      String responseTime, @Date String startedAt, String assignee, String additionalInformation,
      String screeningDecision, String screeningDecisionDetail, Address address,
      Set<Participant> participants, Set<CrossReport> crossReports, Set<Allegation> allegations) {
    super();
    this.id = id;
    this.referralId = referralId;
    this.legacySourceTable = legacySourceTable;
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
    this.additionalInformation = additionalInformation;
    this.screeningDecision = screeningDecision;
    this.screeningDecisionDetail = screeningDecisionDetail;
    this.address = address;
    this.participants = participants;
    this.crossReports = crossReports;
    this.allegations = allegations;
  }

  /**
   * @return id
   */
  public long getId() {
    return id;
  }

  /**
   * @return legacySourceTable
   */
  public String getLegacySourceTable() {
    return legacySourceTable;
  }

  /**
   * @return referralId
   */
  public String getReferralId() {
    return referralId;
  }

  /**
   * @return ended date
   */
  public String getEndedAt() {
    return endedAt;
  }

  /**
   * @return incident county
   */
  public String getIncidentCounty() {
    return incidentCounty;
  }

  /**
   * @return incident Date
   */
  public String getIncidentDate() {
    return incidentDate;
  }

  /**
   * @return location type
   */
  public String getLocationType() {
    return locationType;
  }

  /**
   * @return communicaion method
   */
  public String getCommunicationMethod() {
    return communicationMethod;
  }

  /**
   * @return name
   */
  public String getName() {
    return name;
  }

  /**
   * @return report narrative
   */
  public String getReportNarrative() {
    return reportNarrative;
  }

  /**
   * @return referrence
   */
  public String getReference() {
    return reference;
  }

  /**
   * @return response time
   */
  public String getResponseTime() {
    return responseTime;
  }

  /**
   * @return = started at
   */
  public String getStartedAt() {
    return startedAt;
  }

  /**
   * @return assignee
   */
  public String getAssignee() {
    return assignee;
  }

  /**
   * @return addtional infoma
   */
  public String getAdditionalInformation() {
    return additionalInformation;
  }

  /**
   * @return - screening decio
   */
  public String getScreeningDecision() {
    return screeningDecision;
  }

  /**
   * @return screening decision detail
   */
  public String getScreeningDecisionDetail() {
    return screeningDecisionDetail;
  }

  @SuppressWarnings("javadoc")
  public Address getAddress() {
    return address;
  }

  /**
   * @param address - domain address
   */
  public void setAddress(Address address) {
    this.address = address;
  }

  @SuppressWarnings("javadoc")
  public Set<Participant> getParticipants() {
    return participants;
  }

  @SuppressWarnings("javadoc")
  public Set<Allegation> getAllegations() {
    return allegations;
  }

  @SuppressWarnings("javadoc")
  public Set<CrossReport> getCrossReports() {
    return crossReports;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result =
        prime * result + ((additionalInformation == null) ? 0 : additionalInformation.hashCode());
    result = prime * result + ((address == null) ? 0 : address.hashCode());
    result = prime * result + ((allegations == null) ? 0 : allegations.hashCode());
    result = prime * result + ((assignee == null) ? 0 : assignee.hashCode());
    result = prime * result + ((communicationMethod == null) ? 0 : communicationMethod.hashCode());
    result = prime * result + ((crossReports == null) ? 0 : crossReports.hashCode());
    result = prime * result + ((endedAt == null) ? 0 : endedAt.hashCode());
    result = prime * result + (int) (id ^ (id >>> 32));
    result = prime * result + ((incidentCounty == null) ? 0 : incidentCounty.hashCode());
    result = prime * result + ((incidentDate == null) ? 0 : incidentDate.hashCode());
    result = prime * result + ((locationType == null) ? 0 : locationType.hashCode());
    result = prime * result + ((name == null) ? 0 : name.hashCode());
    result = prime * result + ((participants == null) ? 0 : participants.hashCode());
    result = prime * result + ((reference == null) ? 0 : reference.hashCode());
    result = prime * result + ((legacySourceTable == null) ? 0 : legacySourceTable.hashCode());
    result = prime * result + ((referralId == null) ? 0 : referralId.hashCode());
    result = prime * result + ((reportNarrative == null) ? 0 : reportNarrative.hashCode());
    result = prime * result + ((responseTime == null) ? 0 : responseTime.hashCode());
    result = prime * result + ((screeningDecision == null) ? 0 : screeningDecision.hashCode());
    result = prime * result
        + ((screeningDecisionDetail == null) ? 0 : screeningDecisionDetail.hashCode());
    result = prime * result + ((startedAt == null) ? 0 : startedAt.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    ScreeningToReferral other = (ScreeningToReferral) obj;
    if (additionalInformation == null) {
      if (other.additionalInformation != null)
        return false;
    } else if (!additionalInformation.equals(other.additionalInformation))
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
    if (legacySourceTable == null) {
      if (other.legacySourceTable != null)
        return false;
    } else if (!legacySourceTable.equals(other.legacySourceTable))
      return false;
    if (referralId == null) {
      if (other.referralId != null)
        return false;
    } else if (!referralId.equals(other.referralId))
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
}
