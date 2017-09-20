package gov.ca.cwds.rest.api.domain.investigation;

import java.util.Set;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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

  @JsonProperty("legacy_descriptor")
  private LegacyDescriptor legacyDescriptor;

  @JsonProperty("last_updated_by")
  @ApiModelProperty(required = false, readOnly = false, value = "staff person id")
  private String lastUpdatedBy;

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
  private Assignee assignee;

  @JsonProperty("additional_information")
  @ApiModelProperty(required = false, readOnly = false, value = "Additional Information",
      example = "more information about this investigation")
  @Size(max = 50)
  private String additionalInformation;


  @JsonProperty("sensitive")
  @ApiModelProperty(required = true, readOnly = false, value = "contains sensitive information")
  private Boolean sensitive;

  @JsonProperty("sealed")
  @ApiModelProperty(required = true, readOnly = false, value = "contains sealed information")
  private Boolean sealed;

  @JsonProperty("phone")
  private Set<PhoneNumber> phoneNumbers;

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

  public Assignee getAssignedd() {
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
    result =
        prime * result + ((historyOfInvolvement == null) ? 0 : historyOfInvolvement.hashCode());
    result = prime * result + ((incidentCounty == null) ? 0 : incidentCounty.hashCode());
    result = prime * result + ((incidentDate == null) ? 0 : incidentDate.hashCode());
    result = prime * result + ((lastUpdatedAt == null) ? 0 : lastUpdatedAt.hashCode());
    result = prime * result + ((lastUpdatedBy == null) ? 0 : lastUpdatedBy.hashCode());
    result = prime * result + ((legacyDescriptor == null) ? 0 : legacyDescriptor.hashCode());
    result = prime * result + ((locationType == null) ? 0 : locationType.hashCode());
    result = prime * result + ((name == null) ? 0 : name.hashCode());
    result = prime * result + ((people == null) ? 0 : people.hashCode());
    result = prime * result + ((phoneNumbers == null) ? 0 : phoneNumbers.hashCode());
    result = prime * result + ((reference == null) ? 0 : reference.hashCode());
    result = prime * result + ((relationships == null) ? 0 : relationships.hashCode());
    result = prime * result + ((reportNarrative == null) ? 0 : reportNarrative.hashCode());
    result = prime * result + ((responseTime == null) ? 0 : responseTime.hashCode());
    result = prime * result + ((screening == null) ? 0 : screening.hashCode());
    result = prime * result + ((sealed == null) ? 0 : sealed.hashCode());
    result = prime * result + ((sensitive == null) ? 0 : sensitive.hashCode());
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
    Investigation other = (Investigation) obj;
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
    if (historyOfInvolvement == null) {
      if (other.historyOfInvolvement != null)
        return false;
    } else if (!historyOfInvolvement.equals(other.historyOfInvolvement))
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
    if (lastUpdatedAt == null) {
      if (other.lastUpdatedAt != null)
        return false;
    } else if (!lastUpdatedAt.equals(other.lastUpdatedAt))
      return false;
    if (lastUpdatedBy == null) {
      if (other.lastUpdatedBy != null)
        return false;
    } else if (!lastUpdatedBy.equals(other.lastUpdatedBy))
      return false;
    if (legacyDescriptor == null) {
      if (other.legacyDescriptor != null)
        return false;
    } else if (!legacyDescriptor.equals(other.legacyDescriptor))
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
    if (people == null) {
      if (other.people != null)
        return false;
    } else if (!people.equals(other.people))
      return false;
    if (phoneNumbers == null) {
      if (other.phoneNumbers != null)
        return false;
    } else if (!phoneNumbers.equals(other.phoneNumbers))
      return false;
    if (reference == null) {
      if (other.reference != null)
        return false;
    } else if (!reference.equals(other.reference))
      return false;
    if (relationships == null) {
      if (other.relationships != null)
        return false;
    } else if (!relationships.equals(other.relationships))
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
    if (screening == null) {
      if (other.screening != null)
        return false;
    } else if (!screening.equals(other.screening))
      return false;
    if (sealed == null) {
      if (other.sealed != null)
        return false;
    } else if (!sealed.equals(other.sealed))
      return false;
    if (sensitive == null) {
      if (other.sensitive != null)
        return false;
    } else if (!sensitive.equals(other.sensitive))
      return false;
    if (startedAt == null) {
      if (other.startedAt != null)
        return false;
    } else if (!startedAt.equals(other.startedAt))
      return false;
    return true;
  }


}
