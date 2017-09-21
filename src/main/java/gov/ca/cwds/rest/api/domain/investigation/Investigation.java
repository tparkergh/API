package gov.ca.cwds.rest.api.domain.investigation;

import java.util.Set;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.DomainObject;
import gov.ca.cwds.rest.api.domain.LegacyDescriptor;
import gov.ca.cwds.rest.api.domain.ReportingDomain;
import gov.ca.cwds.rest.api.domain.SystemCodeCategoryId;
import gov.ca.cwds.rest.validation.Date;
import gov.ca.cwds.rest.validation.ValidLogicalId;
import gov.ca.cwds.rest.validation.ValidSystemCodeId;
import io.dropwizard.jackson.JsonSnakeCase;
import io.swagger.annotations.ApiModelProperty;

/**
 * {@link DomainObject} representing a Investigation
 * 
 * @author CWDS API Team
 */
@SuppressWarnings("javadoc")

@JsonSnakeCase
public class Investigation extends ReportingDomain implements Request, Response {
  private static final long serialVersionUID = 1L;

  @NotNull
  @JsonProperty("legacy_descriptor")
  private LegacyDescriptor legacyDescriptor;

  @NotNull
  @JsonProperty("last_updated_by")
  @ApiModelProperty(required = false, readOnly = false, value = "staff person id")
  private String lastUpdatedBy;

  @NotNull
  @ApiModelProperty(required = false, readOnly = false, value = "Last Updated Time",
      example = "2010-10-01T15:26:42.000-0700")
  @JsonProperty("last_updated_at")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
  private DateTime lastUpdatedAt;

  @JsonProperty("incident_county")
  @NotEmpty
  @ApiModelProperty(required = true, readOnly = false,
      value = "County with primary assignment responsibility for Referral/Case", example = "34")
  @ValidLogicalId(required = true, category = SystemCodeCategoryId.COUNTY_CODE)
  private String incidentCounty;

  @NotNull
  @Date
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT)
  @JsonProperty("incident_date")
  @ApiModelProperty(required = false, readOnly = false, value = "Incident date",
      example = "2017-01-13")
  private String incidentDate;

  @JsonProperty("location_type")
  @NotNull
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
  @ApiModelProperty(required = true, readOnly = false, value = "Title/Name of Referral/Case",
      example = "Test Case #1")
  @Size(max = 35)
  private String name;

  @JsonProperty("investigation_summary")
  @ApiModelProperty(required = false, readOnly = false, value = "summary of investigation",
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
  @NotNull
  @Valid
  private Assignee assignee;

  @JsonProperty("additional_information")
  @ApiModelProperty(required = false, readOnly = false, value = "Additional Information",
      example = "more information about this investigation")
  @Size(max = 50)
  private String additionalInformation;

  @NotEmpty
  @JsonProperty("sensitive")
  @ApiModelProperty(required = true, readOnly = false, value = "contains sensitive information")
  private Boolean sensitive;

  @NotEmpty
  @JsonProperty("sealed")
  @ApiModelProperty(required = true, readOnly = false, value = "contains sealed information")
  private Boolean sealed;

  @JsonProperty("phone")
  private Set<PhoneNumber> phoneNumbers;

  @JsonProperty("incident_phone_number")
  @NotNull
  @ApiModelProperty(required = true, readOnly = false)
  @Valid
  private InvestigationAddress address;

  @JsonProperty("screening")
  @ApiModelProperty(required = false, readOnly = false)
  @Valid
  private SimpleScreening screening;

  @JsonProperty("history_of_involvement")
  @ApiModelProperty(required = false, readOnly = false)
  @Valid
  private HistoryOfInvolvement historyOfInvolvement;

  @JsonProperty("allegations")
  @ApiModelProperty(required = false, readOnly = false)
  @Valid
  private Set<Allegation> allegations;

  // @JsonProperty("salfety_alerts")
  // @ApiModelProperty(required = false, readOnly = false)
  // @Valid
  // private Set<SafetyAlerts> saftetyAlerts;


  // @JsonProperty("cross_reports")
  // @ApiModelProperty(required = false, readOnly = false)
  // @Valid
  // private Set<CrossReport> crossReports;
  //
  // @JsonProperty("contacts")
  // @ApiModelProperty(required = false, readOnly = false)
  // @Valid
  // private Set<Contact> contacts;
  //
  @JsonProperty("people")
  @ApiModelProperty(required = false, readOnly = false)
  @Valid
  private Set<Person> people;

  @JsonProperty("relationships")
  @ApiModelProperty(required = false, readOnly = false)
  @Valid
  private Set<Relationship> relationships;


  /**
   * empty constructor
   */
  public Investigation() {
    super();
  }

  /**
   * @param legacyDescriptor - CMS record description
   * @param lastUpdatedBy - updated by staff id
   * @param lastUpdatedAt - updated date/time
   * @param incidentCounty - county code
   * @param incidentDate - incident date
   * @param locationType - location type code
   * @param communicationMethod - communication method code
   * @param name - name
   * @param reportNarrative - report narrative
   * @param reference - referrence
   * @param responseTime - response time
   * @param startedAt - started at
   * @param assignee - asignment info
   * @param additionalInformation - additional information
   * @param sensitive - contains senstive information
   * @param sealed - contains sealed information
   * @param phoneNumbers - phone numbers
   * @param address - address
   * @param screening - screening information
   * @param historyOfInvolvement - history of involvement
   * @param allegations - allegations
   * @param people - people of investigation
   * @param relationships - relationships of people
   */
  public Investigation(LegacyDescriptor legacyDescriptor, String lastUpdatedBy,
      DateTime lastUpdatedAt,
      @ValidLogicalId(required = true, category = "GVR_ENTC") String incidentCounty,
      @Date String incidentDate, String locationType,
      @ValidSystemCodeId(required = true, category = "CMM_MTHC") Short communicationMethod,
      String name, String reportNarrative, String reference,
      @ValidSystemCodeId(required = true, category = "RFR_RSPC") Short responseTime,
      String startedAt, Assignee assignee, String additionalInformation, Boolean sensitive,
      Boolean sealed, Set<PhoneNumber> phoneNumbers, InvestigationAddress address,
      SimpleScreening screening, HistoryOfInvolvement historyOfInvolvement,
      Set<Allegation> allegations, Set<Person> people, Set<Relationship> relationships) {
    super();
    this.legacyDescriptor = legacyDescriptor;
    this.lastUpdatedBy = lastUpdatedBy;
    this.lastUpdatedAt = lastUpdatedAt;
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
    this.sensitive = sensitive;
    this.sealed = sealed;
    this.phoneNumbers = phoneNumbers;
    this.address = address;
    this.screening = screening;
    this.historyOfInvolvement = historyOfInvolvement;
    this.allegations = allegations;
    this.people = people;
    this.relationships = relationships;
  }

  public LegacyDescriptor getLegacyDescriptor() {
    return legacyDescriptor;
  }

  public String getLastUpdatedBy() {
    return lastUpdatedBy;
  }

  public DateTime getLastUpdatedAt() {
    return lastUpdatedAt;
  }

  public String getIncidentCounty() {
    return incidentCounty;
  }

  public String getIncidentDate() {
    return incidentDate;
  }

  public String getLocationType() {
    return locationType;
  }

  public Short getCommunicationMethod() {
    return communicationMethod;
  }

  public String getName() {
    return name;
  }

  public String getReportNarrative() {
    return reportNarrative;
  }

  public String getReference() {
    return reference;
  }

  public Short getResponseTime() {
    return responseTime;
  }

  public String getStartedAt() {
    return startedAt;
  }

  public Assignee getAssignee() {
    return assignee;
  }

  public String getAdditionalInformation() {
    return additionalInformation;
  }

  public Boolean getSensitive() {
    return sensitive;
  }

  public Boolean getSealed() {
    return sealed;
  }

  public Set<PhoneNumber> getPhoneNumbers() {
    return phoneNumbers;
  }

  public InvestigationAddress getAddress() {
    return address;
  }

  public SimpleScreening getScreening() {
    return screening;
  }

  public HistoryOfInvolvement getHistoryOfInvolvement() {
    return historyOfInvolvement;
  }

  public Set<Allegation> getAllegations() {
    return allegations;
  }

  public Set<Person> getPeople() {
    return people;
  }

  public Set<Relationship> getRelationships() {
    return relationships;
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
