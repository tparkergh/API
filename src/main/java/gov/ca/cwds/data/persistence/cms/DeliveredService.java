package gov.ca.cwds.data.persistence.cms;

import java.sql.Time;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import io.dropwizard.validation.OneOf;

/**
 * {@link CmsPersistentObject} class representing a External Interface Table
 * 
 * @author CWDS API Team
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "DL_SVC_T")
@JsonPropertyOrder(alphabetic = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class DeliveredService extends CmsPersistentObject {

  @Type(type = "short")
  @Column(name = "CFTLAGYC")
  private Short cftLeadAgencyType;

  @Column(name = "CORESVCIND")
  private String coreServiceIndicator;

  @Type(type = "short")
  @Column(name = "CMM_MTHC")
  private Short communicationMethodType;

  @Type(type = "short")
  @Column(name = "CNT_LOC")
  private Short contactLocationType;

  @NotEmpty
  @OneOf(value = {"C", "N", "V"}, ignoreCase = true, ignoreWhitespace = true)
  @Column(name = "CNT_VST_CD")
  private String contactVisitCode;

  @NotEmpty
  @Column(name = "CNTY_SPFCD")
  private String countySpecificCode;

  @Column(name = "DETAIL_TXT")
  private String detailText;

  @NotEmpty
  @Size(min = 1, max = 1)
  @OneOf(value = {"N", "U", "Y"}, ignoreCase = true, ignoreWhitespace = true)
  @Column(name = "DOC_FL_CD")
  private String hardCopyDocumentOnFileCode;

  @Column(name = "DTL2_TXT")
  private String detailTextContinuation;

  @Column(name = "END_DT")
  private Date endDate;

  @Column(name = "END_TM")
  private Time endTime;

  @Column(name = "FKDL_SVC_T")
  private String primaryDeliveryServiceId;

  @Id
  @Column(name = "IDENTIFIER", length = CMS_ID_LEN)
  private String id;

  @Column(name = "OTHPTC_DSC")
  private String otherParticipantsDesc;

  @Column(name = "PROVD_BYCD")
  private String providedByCode;

  @Column(name = "PROVD_BYID")
  private String providedById;

  @Type(type = "date")
  @Column(name = "START_DT")
  private Date startDate;

  @Type(type = "time")
  @Column(name = "START_TM")
  private Date startTime;

  @NotEmpty
  @Size(min = 1, max = 1)
  @OneOf(value = {"A", "C", "S"}, ignoreCase = true, ignoreWhitespace = true)
  @Column(name = "STATUS_CD")
  private String statusCode;

  @Column(name = "SUPRVSN_CD")
  private String supervisionCode;

  @Column(name = "SVC_CNTC")
  private String serviceContactType;

  @Column(name = "WRAPSVCIND")
  private String wraparoundServiceIndicator;


  /**
   * Default constructor
   * 
   * Required for Hibernate
   */
  public DeliveredService() {
    super();
  }



  /**
   * @param cftLeadAgencyType the CFT lead agency type
   * @param coreServiceIndicator the core service indicator
   * @param communicationMethodType the communication method type
   * @param contactLocationType the contact location type
   * @param contactVisitCode the contact visit code
   * @param countySpecificCode the county specific code
   * @param detailText the detail text
   * @param hardCopyDocumentOnFileCode the hardcopy document on file
   * @param detailTextContinuation the detail text continuation
   * @param endDate the ends date
   * @param endTime the end time
   * @param primaryDeliveryServiceId the delivered service Id
   * @param id the identifier
   * @param otherParticipantsDesc the other participant description
   * @param providedByCode the provided by code
   * @param providedById the provided by id
   * @param startDate the start date
   * @param startTime the start time
   * @param statusCode the status code
   * @param supervisionCode the supervision code
   * @param serviceContactType the service contact type
   * @param wraparoundServiceIndicator the wraparound Service Indicator
   */
  public DeliveredService(Short cftLeadAgencyType, String coreServiceIndicator,
      Short communicationMethodType, Short contactLocationType,
      @OneOf(value = {"C", "N", "V"}, ignoreCase = true,
          ignoreWhitespace = true) String contactVisitCode,
      String countySpecificCode, String detailText,
      @OneOf(value = {"N", "U", "Y"}, ignoreCase = true,
          ignoreWhitespace = true) String hardCopyDocumentOnFileCode,
      String detailTextContinuation, Date endDate, Time endTime, String primaryDeliveryServiceId,
      String id, String otherParticipantsDesc, String providedByCode, String providedById,
      Date startDate, Date startTime,
      @OneOf(value = {"A", "C", "S"}, ignoreCase = true, ignoreWhitespace = true) String statusCode,
      String supervisionCode, String serviceContactType, String wraparoundServiceIndicator) {
    super();
    this.cftLeadAgencyType = cftLeadAgencyType;
    this.coreServiceIndicator = coreServiceIndicator;
    this.communicationMethodType = communicationMethodType;
    this.contactLocationType = contactLocationType;
    this.contactVisitCode = contactVisitCode;
    this.countySpecificCode = countySpecificCode;
    this.detailText = detailText;
    this.hardCopyDocumentOnFileCode = hardCopyDocumentOnFileCode;
    this.detailTextContinuation = detailTextContinuation;
    this.endDate = endDate;
    this.endTime = endTime;
    this.primaryDeliveryServiceId = primaryDeliveryServiceId;
    this.id = id;
    this.otherParticipantsDesc = otherParticipantsDesc;
    this.providedByCode = providedByCode;
    this.providedById = providedById;
    this.startDate = startDate;
    this.startTime = startTime;
    this.statusCode = statusCode;
    this.supervisionCode = supervisionCode;
    this.serviceContactType = serviceContactType;
    this.wraparoundServiceIndicator = wraparoundServiceIndicator;
  }

  /**
   * @return the cftLeadAgencyType
   */
  public Short getCftLeadAgencyType() {
    return cftLeadAgencyType;
  }

  /**
   * @param cftLeadAgencyType the cftLeadAgencyType to set
   */
  public void setCftLeadAgencyType(Short cftLeadAgencyType) {
    this.cftLeadAgencyType = cftLeadAgencyType;
  }

  /**
   * @return the coreServiceIndicator
   */
  public String getCoreServiceIndicator() {
    return coreServiceIndicator;
  }

  /**
   * @param coreServiceIndicator the coreServiceIndicator to set
   */
  public void setCoreServiceIndicator(String coreServiceIndicator) {
    this.coreServiceIndicator = coreServiceIndicator;
  }

  /**
   * @return the communicationMethodType
   */
  public Short getCommunicationMethodType() {
    return communicationMethodType;
  }

  /**
   * @param communicationMethodType the communicationMethodType to set
   */
  public void setCommunicationMethodType(Short communicationMethodType) {
    this.communicationMethodType = communicationMethodType;
  }

  /**
   * @return the contactLocationType
   */
  public Short getContactLocationType() {
    return contactLocationType;
  }



  /**
   * @param contactLocationType the contactLocationType to set
   */
  public void setContactLocationType(Short contactLocationType) {
    this.contactLocationType = contactLocationType;
  }



  /**
   * @return the contactVisitCode
   */
  public String getContactVisitCode() {
    return contactVisitCode;
  }



  /**
   * @param contactVisitCode the contactVisitCode to set
   */
  public void setContactVisitCode(String contactVisitCode) {
    this.contactVisitCode = contactVisitCode;
  }



  /**
   * @return the countySpecificCode
   */
  public String getCountySpecificCode() {
    return countySpecificCode;
  }



  /**
   * @param countySpecificCode the countySpecificCode to set
   */
  public void setCountySpecificCode(String countySpecificCode) {
    this.countySpecificCode = countySpecificCode;
  }



  /**
   * @return the detailText
   */
  public String getDetailText() {
    return detailText;
  }



  /**
   * @param detailText the detailText to set
   */
  public void setDetailText(String detailText) {
    this.detailText = detailText;
  }



  /**
   * @return the hardCopyDocumentOnFileCode
   */
  public String getHardCopyDocumentOnFileCode() {
    return hardCopyDocumentOnFileCode;
  }



  /**
   * @param hardCopyDocumentOnFileCode the hardCopyDocumentOnFileCode to set
   */
  public void setHardCopyDocumentOnFileCode(String hardCopyDocumentOnFileCode) {
    this.hardCopyDocumentOnFileCode = hardCopyDocumentOnFileCode;
  }



  /**
   * @return the detailTextContinuation
   */
  public String getDetailTextContinuation() {
    return detailTextContinuation;
  }



  /**
   * @param detailTextContinuation the detailTextContinuation to set
   */
  public void setDetailTextContinuation(String detailTextContinuation) {
    this.detailTextContinuation = detailTextContinuation;
  }



  /**
   * @return the endDate
   */
  public Date getEndDate() {
    return endDate;
  }



  /**
   * @param endDate the endDate to set
   */
  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }



  /**
   * @return the endTime
   */
  public Time getEndTime() {
    return endTime;
  }



  /**
   * @param endTime the endTime to set
   */
  public void setEndTime(Time endTime) {
    this.endTime = endTime;
  }



  /**
   * @return the primaryDeliveryServiceId
   */
  public String getPrimaryDeliveryServiceId() {
    return primaryDeliveryServiceId;
  }



  /**
   * @param primaryDeliveryServiceId the primaryDeliveryServiceId to set
   */
  public void setPrimaryDeliveryServiceId(String primaryDeliveryServiceId) {
    this.primaryDeliveryServiceId = primaryDeliveryServiceId;
  }



  /**
   * @return the id
   */
  public String getId() {
    return id;
  }



  /**
   * @param id the id to set
   */
  public void setId(String id) {
    this.id = id;
  }



  /**
   * @return the otherParticipantsDesc
   */
  public String getOtherParticipantsDesc() {
    return otherParticipantsDesc;
  }



  /**
   * @param otherParticipantsDesc the otherParticipantsDesc to set
   */
  public void setOtherParticipantsDesc(String otherParticipantsDesc) {
    this.otherParticipantsDesc = otherParticipantsDesc;
  }



  /**
   * @return the providedByCode
   */
  public String getProvidedByCode() {
    return providedByCode;
  }



  /**
   * @param providedByCode the providedByCode to set
   */
  public void setProvidedByCode(String providedByCode) {
    this.providedByCode = providedByCode;
  }



  /**
   * @return the providedById
   */
  public String getProvidedById() {
    return providedById;
  }



  /**
   * @param providedById the providedById to set
   */
  public void setProvidedById(String providedById) {
    this.providedById = providedById;
  }



  /**
   * @return the startDate
   */
  public Date getStartDate() {
    return startDate;
  }



  /**
   * @param startDate the startDate to set
   */
  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }



  /**
   * @return the startTime
   */
  public Date getStartTime() {
    return startTime;
  }



  /**
   * @param startTime the startTime to set
   */
  public void setStartTime(Date startTime) {
    this.startTime = startTime;
  }



  /**
   * @return the statusCode
   */
  public String getStatusCode() {
    return statusCode;
  }



  /**
   * @param statusCode the statusCode to set
   */
  public void setStatusCode(String statusCode) {
    this.statusCode = statusCode;
  }



  /**
   * @return the supervisionCode
   */
  public String getSupervisionCode() {
    return supervisionCode;
  }



  /**
   * @param supervisionCode the supervisionCode to set
   */
  public void setSupervisionCode(String supervisionCode) {
    this.supervisionCode = supervisionCode;
  }



  /**
   * @return the serviceContactType
   */
  public String getServiceContactType() {
    return serviceContactType;
  }



  /**
   * @param serviceContactType the serviceContactType to set
   */
  public void setServiceContactType(String serviceContactType) {
    this.serviceContactType = serviceContactType;
  }



  /**
   * @return the wraparoundServiceIndicator
   */
  public String getWraparoundServiceIndicator() {
    return wraparoundServiceIndicator;
  }



  /**
   * @param wraparoundServiceIndicator the wraparoundServiceIndicator to set
   */
  public void setWraparoundServiceIndicator(String wraparoundServiceIndicator) {
    this.wraparoundServiceIndicator = wraparoundServiceIndicator;
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

  @Override
  public String getPrimaryKey() {
    return getId();
  }

}
