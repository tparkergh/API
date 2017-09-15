package gov.ca.cwds.rest.api.domain.investigation;

import static gov.ca.cwds.data.persistence.cms.CmsPersistentObject.CMS_ID_LEN;

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
import gov.ca.cwds.rest.api.domain.Allegation;
import gov.ca.cwds.rest.api.domain.Contact;
import gov.ca.cwds.rest.api.domain.CrossReport;
import gov.ca.cwds.rest.api.domain.DomainObject;
import gov.ca.cwds.rest.api.domain.ReportingDomain;
import gov.ca.cwds.rest.api.domain.Screening;
import gov.ca.cwds.rest.api.domain.SystemCodeCategoryId;
import gov.ca.cwds.rest.validation.Date;
import gov.ca.cwds.rest.validation.ValidLogicalId;
import gov.ca.cwds.rest.validation.ValidSystemCodeId;
import io.dropwizard.jackson.JsonSnakeCase;
import io.dropwizard.validation.OneOf;
import io.swagger.annotations.ApiModelProperty;

/**
 * {@link DomainObject} representing a Investigation
 * 
 * @author CWDS API Team
 */

@JsonSnakeCase
public class Investigation extends ReportingDomain implements Request, Response {
  private static final long serialVersionUID = 1L;

  @JsonProperty("table_name")
  @ApiModelProperty(required = true, readOnly = false, value = "CMS Table Name",
      example = "REFERL_T")
  private String tableName;

  @Size(max = CMS_ID_LEN)
  @NotNull
  @JsonProperty("id")
  @ApiModelProperty(required = true, readOnly = true, value = "", example = "ABC1234567")
  private String id;

  @JsonProperty("last_updated_by")
  @ApiModelProperty(required = false, readOnly = false, value = "staff person id")
  private String lastUpdatedBy;

  @ApiModelProperty(required = false, readOnly = false, value = "Last Updated Time",
      example = "2010-10-01T15:26:42.000-0700")
  @JsonProperty("last_updated_at")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
  private DateTime lastUpdated;

  @JsonProperty("approval_status")
  @ValidSystemCodeId(required = false, category = SystemCodeCategoryId.APPROVAL_STATUS_TYPE)
  @ApiModelProperty(required = false, readOnly = false, value = "Status of the approval",
      example = "118")
  private int approvalStatus;

  @JsonProperty("family_awareness")
  @ApiModelProperty(required = false, readOnly = false, value = "Family is aware of referral",
      example = "true")
  private boolean familyAwareness;

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
      example = "CWS Staff")
  @Size(max = 50)
  private String assignee;

  @JsonProperty("additional_information")
  @ApiModelProperty(required = false, readOnly = false, value = "Additional Information",
      example = "more information about this investigation")
  @Size(max = 50)
  private String additionalInformation;

  @JsonProperty("limited_access_code")
  @Size(max = 1)
  @OneOf(value = {"R", "S", "N"})
  @ApiModelProperty(required = true, readOnly = false, value = "limited access code", example = "N")
  private String limitedAccessCode;

  @JsonProperty("limited_access_description")
  @Size(max = 254)
  @ApiModelProperty(required = false, readOnly = false, value = "limited access description",
      example = "Some text describing the limited access")
  private String limitedAccessDescription;

  @JsonProperty("limited_access_agency")
  @ApiModelProperty(required = false, readOnly = false, value = "40",
      example = "Government Entity Type ")
  @ValidLogicalId(required = false, category = SystemCodeCategoryId.COUNTY_CODE, ignoreable = true,
      ignoredValue = "")
  private String limitedAccessAgency;

  @JsonProperty("limited_access_date")
  @ApiModelProperty(required = false, readOnly = false, value = "yyyy-MM-dd",
      example = "2017-09-01")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
  private java.util.Date limitedAccessDate;

  @NotNull
  @ApiModelProperty(required = true, readOnly = false)
  @Valid
  private InvestigationAddress address;

  @JsonProperty("screenings")
  @ApiModelProperty(required = false, readOnly = false)
  @Valid
  private Set<Screening> screenings;

  // @JsonProperty("history_of_involvement")
  // @ApiModelProperty(required = false, readOnly = false)
  // @Valid
  // private Set<HistoryOfInvolvement> history_of_involvement;


  @JsonProperty("allegations")
  @ApiModelProperty(required = false, readOnly = false)
  @Valid
  private Set<Allegation> allegations;

  // @JsonProperty("salfety_alerts")
  // @ApiModelProperty(required = false, readOnly = false)
  // @Valid
  // private Set<SafetyAlerts> saftetyAlerts;


  @JsonProperty("cross_reports")
  @ApiModelProperty(required = false, readOnly = false)
  @Valid
  private Set<CrossReport> crossReports;

  @JsonProperty("contacts")
  @ApiModelProperty(required = false, readOnly = false)
  @Valid
  private Set<Contact> contacts;

  @JsonProperty("people")
  @ApiModelProperty(required = false, readOnly = false)
  @Valid
  private Set<InvestigationPerson> people;

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
   * @param tableName - CWS table name
   * @param id - CWS ID
   * @param lastUpdatedBy - last updated by CWS Staff person ID
   * @param lastUpdated - last updated date/time
   * @param approvalStatus - approval status code
   * @param familyAwareness - family awareness indicator
   * @param incidentCounty - county code
   * @param incidentDate - date of incident
   * @param locationType - location type text
   * @param communicationMethod - communication method code
   * @param name - name of investigation
   * @param reportNarrative - report narrative
   * @param reference - reference text
   * @param responseTime - response code
   * @param startedAt - started at date/time
   * @param assignee - assigned staff
   * @param additionalInformation - additional information text
   * @param limitedAccessCode - limited access code
   * @param limitedAccessDescription - limited access description
   * @param limitedAccessAgency - limited access agency
   * @param limitedAccessDate - date limited access applied
   * @param address
   * @param screenings
   * @param allegations
   * @param crossReports
   * @param contacts
   * @param people
   * @param relationships
   */
  public Investigation(String tableName, String id, String lastUpdatedBy, DateTime lastUpdated,
      int approvalStatus, boolean familyAwareness, String incidentCounty, String incidentDate,
      String locationType, Short communicationMethod, String name, String reportNarrative,
      String reference, Short responseTime, String startedAt, String assignee,
      String additionalInformation, String limitedAccessCode, String limitedAccessDescription,
      String limitedAccessAgency, java.util.Date limitedAccessDate, InvestigationAddress address,
      Set<Screening> screenings, Set<Allegation> allegations, Set<CrossReport> crossReports,
      Set<Contact> contacts, Set<InvestigationPerson> people, Set<Relationship> relationships) {
    super();
    this.tableName = tableName;
    this.id = id;
    this.lastUpdatedBy = lastUpdatedBy;
    this.lastUpdated = lastUpdated;
    this.approvalStatus = approvalStatus;
    this.familyAwareness = familyAwareness;
    this.incidentCounty = incidentCounty;
    this.locationType = locationType;
    this.communicationMethod = communicationMethod;
    this.name = name;
    this.reportNarrative = reportNarrative;
    this.reference = reference;
    this.responseTime = responseTime;
    this.startedAt = startedAt;
    this.assignee = assignee;
    this.additionalInformation = additionalInformation;
    this.address = address;
    this.limitedAccessCode = limitedAccessCode;
    this.limitedAccessDescription = limitedAccessDescription;
    this.limitedAccessAgency = limitedAccessAgency;
    this.limitedAccessDate = limitedAccessDate;
    this.address = address;
    this.screenings = screenings;
    this.allegations = allegations;
    this.crossReports = crossReports;
    this.contacts = contacts;
    this.people = people;
    this.relationships = relationships;

  }

  /**
   * @return CWS table name
   */
  public String getTableName() {
    return tableName;
  }

  /**
   * @return CWS Identifier
   */
  public String getId() {
    return id;
  }

  /**
   * @return staff person code of last update
   */
  public String getLastUpdatedBy() {
    return lastUpdatedBy;
  }

  /**
   * @return date/time of last of update
   */
  public DateTime getLastUpdated() {
    return lastUpdated;
  }

  /**
   * @return approval status code
   */
  public int getApprovalStatus() {
    return approvalStatus;
  }

  /**
   * @return family awareness indicator
   */
  public boolean isFamilyAwareness() {
    return familyAwareness;
  }

  /**
   * @return incident county code
   */
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

  public String getAssignee() {
    return assignee;
  }

  public String getAdditionalInformation() {
    return additionalInformation;
  }

  public String getLimitedAccessCode() {
    return limitedAccessCode;
  }

  public String getLimitedAccessDescription() {
    return limitedAccessDescription;
  }

  public String getLimitedAccessAgency() {
    return limitedAccessAgency;
  }

  public java.util.Date getLimitedAccessDate() {
    return limitedAccessDate;
  }

  public InvestigationAddress getAddress() {
    return address;
  }

  public Set<Screening> getScreenings() {
    return screenings;
  }

  public Set<Allegation> getAllegations() {
    return allegations;
  }

  public Set<CrossReport> getCrossReports() {
    return crossReports;
  }

  public Set<Contact> getContacts() {
    return contacts;
  }

  public Set<InvestigationPerson> getPeople() {
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
    result = prime * result + approvalStatus;
    result = prime * result + ((assignee == null) ? 0 : assignee.hashCode());
    result = prime * result + ((communicationMethod == null) ? 0 : communicationMethod.hashCode());
    result = prime * result + ((contacts == null) ? 0 : contacts.hashCode());
    result = prime * result + ((crossReports == null) ? 0 : crossReports.hashCode());
    result = prime * result + (familyAwareness ? 1231 : 1237);
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    result = prime * result + ((incidentCounty == null) ? 0 : incidentCounty.hashCode());
    result = prime * result + ((incidentDate == null) ? 0 : incidentDate.hashCode());
    result = prime * result + ((lastUpdated == null) ? 0 : lastUpdated.hashCode());
    result = prime * result + ((lastUpdatedBy == null) ? 0 : lastUpdatedBy.hashCode());
    result = prime * result + ((limitedAccessAgency == null) ? 0 : limitedAccessAgency.hashCode());
    result = prime * result + ((limitedAccessCode == null) ? 0 : limitedAccessCode.hashCode());
    result = prime * result + ((limitedAccessDate == null) ? 0 : limitedAccessDate.hashCode());
    result = prime * result
        + ((limitedAccessDescription == null) ? 0 : limitedAccessDescription.hashCode());
    result = prime * result + ((locationType == null) ? 0 : locationType.hashCode());
    result = prime * result + ((name == null) ? 0 : name.hashCode());
    result = prime * result + ((reference == null) ? 0 : reference.hashCode());
    result = prime * result + ((reportNarrative == null) ? 0 : reportNarrative.hashCode());
    result = prime * result + ((responseTime == null) ? 0 : responseTime.hashCode());
    result = prime * result + ((screenings == null) ? 0 : screenings.hashCode());
    result = prime * result + ((startedAt == null) ? 0 : startedAt.hashCode());
    result = prime * result + ((tableName == null) ? 0 : tableName.hashCode());
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
    if (approvalStatus != other.approvalStatus)
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
    if (contacts == null) {
      if (other.contacts != null)
        return false;
    } else if (!contacts.equals(other.contacts))
      return false;
    if (crossReports == null) {
      if (other.crossReports != null)
        return false;
    } else if (!crossReports.equals(other.crossReports))
      return false;
    if (familyAwareness != other.familyAwareness)
      return false;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
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
    if (lastUpdated == null) {
      if (other.lastUpdated != null)
        return false;
    } else if (!lastUpdated.equals(other.lastUpdated))
      return false;
    if (lastUpdatedBy == null) {
      if (other.lastUpdatedBy != null)
        return false;
    } else if (!lastUpdatedBy.equals(other.lastUpdatedBy))
      return false;
    if (limitedAccessAgency == null) {
      if (other.limitedAccessAgency != null)
        return false;
    } else if (!limitedAccessAgency.equals(other.limitedAccessAgency))
      return false;
    if (limitedAccessCode == null) {
      if (other.limitedAccessCode != null)
        return false;
    } else if (!limitedAccessCode.equals(other.limitedAccessCode))
      return false;
    if (limitedAccessDate == null) {
      if (other.limitedAccessDate != null)
        return false;
    } else if (!limitedAccessDate.equals(other.limitedAccessDate))
      return false;
    if (limitedAccessDescription == null) {
      if (other.limitedAccessDescription != null)
        return false;
    } else if (!limitedAccessDescription.equals(other.limitedAccessDescription))
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
    if (screenings == null) {
      if (other.screenings != null)
        return false;
    } else if (!screenings.equals(other.screenings))
      return false;
    if (startedAt == null) {
      if (other.startedAt != null)
        return false;
    } else if (!startedAt.equals(other.startedAt))
      return false;
    if (tableName == null) {
      if (other.tableName != null)
        return false;
    } else if (!tableName.equals(other.tableName))
      return false;
    return true;
  }

}
