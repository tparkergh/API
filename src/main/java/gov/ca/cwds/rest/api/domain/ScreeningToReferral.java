package gov.ca.cwds.rest.api.domain;

import static gov.ca.cwds.data.persistence.cms.CmsPersistentObject.CMS_ID_LEN;

import gov.ca.cwds.rest.validation.ValidLogicalId;
import io.dropwizard.validation.OneOf;
import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder.In;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.validation.Date;
import gov.ca.cwds.rest.validation.ValidSystemCodeId;
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
  @ApiModelProperty(required = false, readOnly = false, value = "Legacy Table",
      example = "REFERL_T")
  private String legacySourceTable;

  @JsonProperty("legacy_id")
  @ApiModelProperty(required = true, readOnly = false, value = "Legacy Referral Id",
      example = "ABC1234567")
  @Size(max = CMS_ID_LEN)
  private String referralId;

  @JsonProperty("ended_at")
  @ApiModelProperty(required = false, readOnly = false, value = "Screening end date/time",
      example = "2016-08-03T01:00:00.000Z")
  private String endedAt;

  @JsonProperty("incident_county")
  @NotEmpty
  @ApiModelProperty(required = true, readOnly = false, value = "County where incident occurred",
      example = "Sacramento")
  @ValidLogicalId(required = true, category = SystemCodeCategoryId.COUNTY_CODE)
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
  @NotNull
  @ApiModelProperty(required = true, readOnly = false, value = "Communication Method",
      example = "409")
  @ValidSystemCodeId(required = true, category = SystemCodeCategoryId.COMMUNICATION_METHOD)
  private Short communicationMethod;

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
  @NotNull
  @ApiModelProperty(required = true, readOnly = false, value = "Referral Response Type",
      example = "1520")
  @ValidSystemCodeId(required = true, category = SystemCodeCategoryId.REFERRAL_RESPONSE)
  private Short responseTime;

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

  @JsonProperty("approval_status")
  @ApiModelProperty(required = false, readOnly = false, value = "Status of the approval",
      example = "118")
  private String approvalStatus;

  @JsonProperty("family_awareness")
  @ApiModelProperty(required = false, readOnly = false, value = "Family is aware of referral",
      example = "true" )
  private boolean familyAwareness;

  @JsonProperty("filed_with_law_enforcement")
  @ApiModelProperty(required = false, readOnly = false, value = "Filed Cross Report with Law Enforcement",
      example = "N")
  private boolean filedWithLawEnforcement;

  @JsonProperty("responsible_agency")
  @ApiModelProperty(required = false, readOnly = false, value = "Responsible Agency Code",
      example = "C", allowableValues = "C, P, O, A, S, I, K, M")
  @OneOf(value = {"C", "P", "O", "A", "S", "I", "K", "M"})
  @Size(max = 1)
  private String responsibleAgency;

  @NotNull
  @ApiModelProperty(required = true, readOnly = false)
  @Valid
  private Address address;

  @NotEmpty
  @ApiModelProperty(required = true, readOnly = false)
  @Valid
  private Set<Participant> participants;

  @JsonProperty("cross_reports")
  @ApiModelProperty(required = true, readOnly = false)
  @NotEmpty
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
      Short communicationMethod, String name, String reportNarrative, String reference,
      Short responseTime, @Date String startedAt, String assignee, String additionalInformation,
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
  public Short getCommunicationMethod() {
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
  public Short getResponseTime() {
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
