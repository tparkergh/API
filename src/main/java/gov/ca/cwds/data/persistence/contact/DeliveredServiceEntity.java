package gov.ca.cwds.data.persistence.contact;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PersistenceException;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import gov.ca.cwds.data.persistence.cms.CmsPersistentObject;
import gov.ca.cwds.rest.api.ApiException;
import gov.ca.cwds.rest.api.domain.DomainChef;

/**
 * {@link CmsPersistentObject} class representing a Delivered Service Entity
 * 
 * @author CWDS API Team
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "DL_SVC_T")
@JsonPropertyOrder(alphabetic = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class DeliveredServiceEntity extends CmsPersistentObject {

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
  @Column(name = "CNT_VST_CD")
  private String contactVisitCode;

  @NotEmpty
  @Column(name = "CNTY_SPFCD")
  private String countySpecificCode;

  @Column(name = "DETAIL_TXT")
  private String detailText;

  @NotEmpty
  @Size(min = 1, max = 1)
  @Column(name = "DOC_FL_CD")
  private String hardCopyDocumentOnFileCode;

  @Column(name = "DTL2_TXT")
  private String detailTextContinuation;

  @Type(type = "date")
  @Column(name = "END_DT")
  private Date endDate;

  @Type(type = "time")
  @Column(name = "END_TM")
  private Date endTime;

  @Column(name = "FKDL_SVC_T")
  private String primaryDeliveredServiceId;

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
  @Column(name = "STATUS_CD")
  private String statusCode;

  @Column(name = "SUPRVSN_CD")
  private String supervisionCode;

  @Column(name = "SVC_CNTC")
  private Short serviceContactType;

  @Column(name = "WRAPSVCIND")
  private String wraparoundServiceIndicator;

  /**
   * Default constructor
   * 
   * Required for Hibernate
   */
  public DeliveredServiceEntity() {
    super();
  }

  /**
   * Constructor
   * 
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
   * @param primaryDeliveredServiceId the delivered service Id
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
  public DeliveredServiceEntity(Short cftLeadAgencyType, String coreServiceIndicator,
      Short communicationMethodType, Short contactLocationType, String contactVisitCode,
      String countySpecificCode, String detailText, String hardCopyDocumentOnFileCode,
      String detailTextContinuation, Date endDate, Date endTime, String primaryDeliveredServiceId,
      String id, String otherParticipantsDesc, String providedByCode, String providedById,
      Date startDate, Date startTime, String statusCode, String supervisionCode,
      Short serviceContactType, String wraparoundServiceIndicator) {
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
    this.primaryDeliveredServiceId = primaryDeliveredServiceId;
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
   * Constructor using domain
   * 
   * @param id The id
   * @param deliveredServiceDomain The domain object to construct this object from
   * @param lastUpdatedId The id of the last person to update this object
   */
  public DeliveredServiceEntity(String id,
      gov.ca.cwds.rest.api.contact.DeliveredServiceDomain deliveredServiceDomain,
      String lastUpdatedId) {
    super(lastUpdatedId);
    try {
      this.cftLeadAgencyType = deliveredServiceDomain.getCftLeadAgencyType();
      this.coreServiceIndicator =
          DomainChef.cookBoolean(deliveredServiceDomain.getCoreServiceIndicator());
      this.communicationMethodType =
          deliveredServiceDomain.getCommunicationMethodType().shortValue();
      this.contactLocationType = deliveredServiceDomain.getContactLocationType().shortValue();
      this.contactVisitCode = deliveredServiceDomain.getContactVisitCode();
      this.countySpecificCode = deliveredServiceDomain.getCountySpecificCode();
      this.detailText = deliveredServiceDomain.getDetailText();
      this.hardCopyDocumentOnFileCode = deliveredServiceDomain.getHardCopyDocumentOnFileCode();
      this.detailTextContinuation = deliveredServiceDomain.getDetailTextContinuation();
      this.endDate = DomainChef.uncookDateString(deliveredServiceDomain.getEndDate());
      this.endTime = DomainChef.uncookTimeString(deliveredServiceDomain.getEndTime());
      this.primaryDeliveredServiceId = deliveredServiceDomain.getPrimaryDeliveredServiceId();
      this.id = id;
      this.otherParticipantsDesc = deliveredServiceDomain.getOtherParticipantsDesc();
      this.providedByCode = deliveredServiceDomain.getProvidedByCode();
      this.providedById = deliveredServiceDomain.getProvidedById();
      this.startDate = DomainChef.uncookDateString(deliveredServiceDomain.getStartDate());
      this.startTime = DomainChef.uncookTimeString(deliveredServiceDomain.getStartTime());
      this.statusCode = deliveredServiceDomain.getStatusCode();
      this.supervisionCode = deliveredServiceDomain.getSupervisionCode();
      this.serviceContactType = deliveredServiceDomain.getServiceContactType().shortValue();
      this.wraparoundServiceIndicator =
          DomainChef.cookBoolean(deliveredServiceDomain.getWraparoundServiceIndicator());
    } catch (ApiException e) {
      throw new PersistenceException(e);
    }
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.data.persistence.PersistentObject#getPrimaryKey()
   */
  @Override
  public String getPrimaryKey() {
    return getId();
  }

  /**
   * @return the cftLeadAgencyType
   */
  public Short getCftLeadAgencyType() {
    return cftLeadAgencyType;
  }

  /**
   * @return the coreServiceIndicator
   */
  public String getCoreServiceIndicator() {
    return coreServiceIndicator;
  }

  /**
   * @return the communicationMethodType
   */
  public Short getCommunicationMethodType() {
    return communicationMethodType;
  }

  /**
   * @return the contactLocationType
   */
  public Short getContactLocationType() {
    return contactLocationType;
  }

  /**
   * @return the contactVisitCode
   */
  public String getContactVisitCode() {
    return contactVisitCode;
  }

  /**
   * @return the countySpecificCode
   */
  public String getCountySpecificCode() {
    return countySpecificCode;
  }

  /**
   * @return the detailText
   */
  public String getDetailText() {
    return detailText;
  }

  /**
   * @return the hardCopyDocumentOnFileCode
   */
  public String getHardCopyDocumentOnFileCode() {
    return hardCopyDocumentOnFileCode;
  }

  /**
   * @return the detailTextContinuation
   */
  public String getDetailTextContinuation() {
    return detailTextContinuation;
  }

  /**
   * @return the endDate
   */
  public Date getEndDate() {
    return endDate;
  }

  /**
   * @return the endTime
   */
  public Date getEndTime() {
    return endTime;
  }

  /**
   * @return the primaryDeliveryServiceId
   */
  public String getPrimaryDeliveredServiceId() {
    return primaryDeliveredServiceId;
  }

  /**
   * @return the primary key
   */
  public String getId() {
    return id;
  }

  /**
   * @return the otherParticipantsDesc
   */
  public String getOtherParticipantsDesc() {
    return otherParticipantsDesc;
  }

  /**
   * @return the providedByCode
   */
  public String getProvidedByCode() {
    return providedByCode;
  }

  /**
   * @return the providedById
   */
  public String getProvidedById() {
    return providedById;
  }

  /**
   * @return the startDate
   */
  public Date getStartDate() {
    return startDate;
  }

  /**
   * @return the startTime
   */
  public Date getStartTime() {
    return startTime;
  }

  /**
   * @return the statusCode
   */
  public String getStatusCode() {
    return statusCode;
  }

  /**
   * @return the supervisionCode
   */
  public String getSupervisionCode() {
    return supervisionCode;
  }

  /**
   * @return the serviceContactType
   */
  public Short getServiceContactType() {
    return serviceContactType;
  }

  /**
   * @return the wraparoundServiceIndicator
   */
  public String getWraparoundServiceIndicator() {
    return wraparoundServiceIndicator;
  }

}
