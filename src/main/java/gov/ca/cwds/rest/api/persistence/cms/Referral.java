package gov.ca.cwds.rest.api.persistence.cms;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PersistenceException;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import gov.ca.cwds.rest.api.ApiException;
import gov.ca.cwds.rest.api.domain.DomainObject;
import gov.ca.cwds.rest.api.persistence.ns.NsPersistentObject;

/**
 * {@link NsPersistentObject} representing a Referral
 * 
 * @author CWDS API Team
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "REFERL_T")
public class Referral extends CmsPersistentObject {

  @Id
  @Column(name = "IDENTIFIER", length = CMS_ID_LEN)
  private String id;

  @Column(name = "ADD_INF_CD")
  private String additionalInfoIncludedCode;

  @Column(name = "ANRPTR_IND")
  private String anonymousReporterIndicator;

  @Column(name = "PETAPL_IND")
  private String applicationForPetitionIndicator;

  @Column(name = "APRVL_NO")
  private String approvalNumber;

  @Type(type = "short")
  @Column(name = "APV_STC")
  private Short approvalStatusType;

  @Column(name = "CR_PERP_CD")
  private String caretakersPerpetratorCode;

  @Type(type = "date")
  @Column(name = "REFCLSR_DT")
  private Date closureDate;

  @Type(type = "short")
  @Column(name = "CMM_MTHC")
  private Short communicationMethodType;

  @Column(name = "CHILOC_TXT")
  private String currentLocationOfChildren;

  @Column(name = "ALGDSC_DOC")
  private String drmsAllegationDescriptionDoc;

  @Column(name = "ER_REF_DOC")
  private String drmsErReferralDoc;

  @Column(name = "INVSTG_DOC")
  private String drmsInvestigationDoc;

  @Column(name = "XRPT_LWIND")
  private String filedSuspectedChildAbuseReporttoLawEnforcementIndicator;

  @Column(name = "FMY_AW_IND")
  private String familyAwarenessIndicator;

  @Type(type = "short")
  @Column(name = "GVR_ENTC")
  private Short govtEntityType;

  @Column(name = "LGL_DEF_CD")
  private String legalDefinitionCode;

  @Column(name = "LGLRGT_IND")
  private String legalRightsNoticeIndicator;

  @Column(name = "LMT_ACSSCD")
  private String limitedAccessCode;

  @Type(type = "date")
  @Column(name = "XRPT_RCVDT")
  private Date mandatedCrossReportReceivedDate;

  @Column(name = "REFERRL_NM")
  private String referralName;

  @Column(name = "ADQT_CS_CD")
  private String openAdequateCaseCode;

  @Type(type = "date")
  @Column(name = "REF_RCV_DT")
  private Date receivedDate;

  @Type(type = "time")
  @Column(name = "REF_RCV_TM")
  private Date receivedTime;

  @Type(type = "short")
  @Column(name = "RFR_RSPC")
  private Short referralResponseType;

  @Type(type = "short")
  @Column(name = "RFRD_RSC")
  private Short referredToResourceType;

  @Type(type = "date")
  @Column(name = "RSP_DTR_DT")
  private Date responseDeterminationDate;

  @Type(type = "time")
  @Column(name = "RSP_DTR_TM")
  private Date responseDeterminationTime;

  @Column(name = "RSP_RTNTXT")
  private String responseRationaleText;

  @Column(name = "SCN_NT_TXT")
  private String screenerNoteText;

  @Column(name = "SP_INCL_CD")
  private String specificsIncludedCode;

  @Column(name = "SFC_INF_CD")
  private String sufficientInformationCode;

  @Column(name = "UNFD_SR_CD")
  private String unfoundedSeriesCode;

  @Column(name = "FKREFERL_T")
  private String linkToPrimaryReferralId;

  @Column(name = "FKADDRS_T")
  private String allegesAbuseOccurredAtAddressId;

  @Column(name = "FKSTFPERS0")
  private String firstResponseDeterminedByStaffPersonId;

  @Column(name = "FKSTFPERST")
  private String primaryContactStaffPersonId;

  @Column(name = "CNTY_SPFCD")
  private String countySpecificCode;

  @Column(name = "SPRJRF_IND")
  private String specialProjectReferralIndicator;

  @Column(name = "ZIPPY_IND")
  private String zippyCreatedIndicator;

  @Column(name = "HOMLES_IND")
  private String homelessIndicator;

  @Column(name = "FAMREF_IND")
  private String familyRefusedServicesIndicator;

  @Type(type = "date")
  @Column(name = "FIRST_EODT")
  private Date firstEvaluatedOutApprovalDate;

  @Column(name = "RSP_AGY_CD")
  private String responsibleAgencyCode;

  @Type(type = "short")
  @Column(name = "L_GVR_ENTC")
  private Short limitedAccessGovtAgencyType;

  @Type(type = "date")
  @Column(name = "LMT_ACS_DT")
  private Date limitedAccessDate;

  @Column(name = "LMT_ACSDSC")
  private String limitedAccessDesc;

  @Type(type = "date")
  @Column(name = "ORIGCLS_DT")
  private Date originalClosureDate;

  /**
   * Default constructor
   * 
   * Required for Hibernate
   */
  public Referral() {
    super();
  }

  /**
   * @param id
   * @param additionalInfoIncludedCode
   * @param anonymousReporterIndicator
   * @param applicationForPetitionIndicator
   * @param approvalNumber
   * @param approvalStatusType
   * @param caretakersPerpetratorCode
   * @param closureDate
   * @param communicationMethodType
   * @param currentLocationOfChildren
   * @param drmsAllegationDescriptionDoc
   * @param drmsErReferralDoc
   * @param drmsInvestigationDoc
   * @param filedSuspectedChildAbuseReporttoLawEnforcementIndicator
   * @param familyAwarenessIndicator
   * @param govtEntityType
   * @param legalDefinitionCode
   * @param legalRightsNoticeIndicator
   * @param limitedAccessCode
   * @param mandatedCrossReportReceivedDate
   * @param referralName
   * @param openAdequateCaseCode
   * @param receivedDate
   * @param receivedTime
   * @param referralResponseType
   * @param referredToResourceType
   * @param responseDeterminationDate
   * @param responseDeterminationTime
   * @param responseRationaleText
   * @param screenerNoteText
   * @param specificsIncludedCode
   * @param sufficientInformationCode
   * @param unfoundedSeriesCode
   * @param linkToPrimaryReferralId
   * @param allegesAbuseOccurredAtAddressId
   * @param firstResponseDeterminedByStaffPersonId
   * @param primaryContactStaffPersonId
   * @param countySpecificCode
   * @param specialProjectReferralIndicator
   * @param zippyCreatedIndicator
   * @param homelessIndicator
   * @param familyRefusedServicesIndicator
   * @param firstEvaluatedOutApprovalDate
   * @param responsibleAgencyCode
   * @param limitedAccessGovtAgencyType
   * @param limitedAccessDate
   * @param limitedAccessDesc
   * @param originalClosureDate
   */
  public Referral(String id, String additionalInfoIncludedCode, String anonymousReporterIndicator,
      String applicationForPetitionIndicator, String approvalNumber, Short approvalStatusType,
      String caretakersPerpetratorCode, Date closureDate, Short communicationMethodType,
      String currentLocationOfChildren, String drmsAllegationDescriptionDoc,
      String drmsErReferralDoc, String drmsInvestigationDoc,
      String filedSuspectedChildAbuseReporttoLawEnforcementIndicator,
      String familyAwarenessIndicator, Short govtEntityType, String legalDefinitionCode,
      String legalRightsNoticeIndicator, String limitedAccessCode,
      Date mandatedCrossReportReceivedDate, String referralName, String openAdequateCaseCode,
      Date receivedDate, Date receivedTime, Short referralResponseType,
      Short referredToResourceType, Date responseDeterminationDate, Date responseDeterminationTime,
      String responseRationaleText, String screenerNoteText, String specificsIncludedCode,
      String sufficientInformationCode, String unfoundedSeriesCode, String linkToPrimaryReferralId,
      String allegesAbuseOccurredAtAddressId, String firstResponseDeterminedByStaffPersonId,
      String primaryContactStaffPersonId, String countySpecificCode,
      String specialProjectReferralIndicator, String zippyCreatedIndicator,
      String homelessIndicator, String familyRefusedServicesIndicator,
      Date firstEvaluatedOutApprovalDate, String responsibleAgencyCode,
      Short limitedAccessGovtAgencyType, Date limitedAccessDate, String limitedAccessDesc,
      Date originalClosureDate) {
    super();
    this.id = id;
    this.additionalInfoIncludedCode = additionalInfoIncludedCode;
    this.anonymousReporterIndicator = anonymousReporterIndicator;
    this.applicationForPetitionIndicator = applicationForPetitionIndicator;
    this.approvalNumber = approvalNumber;
    this.approvalStatusType = approvalStatusType;
    this.caretakersPerpetratorCode = caretakersPerpetratorCode;
    this.closureDate = closureDate;
    this.communicationMethodType = communicationMethodType;
    this.currentLocationOfChildren = currentLocationOfChildren;
    this.drmsAllegationDescriptionDoc = drmsAllegationDescriptionDoc;
    this.drmsErReferralDoc = drmsErReferralDoc;
    this.drmsInvestigationDoc = drmsInvestigationDoc;
    this.filedSuspectedChildAbuseReporttoLawEnforcementIndicator =
        filedSuspectedChildAbuseReporttoLawEnforcementIndicator;
    this.familyAwarenessIndicator = familyAwarenessIndicator;
    this.govtEntityType = govtEntityType;
    this.legalDefinitionCode = legalDefinitionCode;
    this.legalRightsNoticeIndicator = legalRightsNoticeIndicator;
    this.limitedAccessCode = limitedAccessCode;
    this.mandatedCrossReportReceivedDate = mandatedCrossReportReceivedDate;
    this.referralName = referralName;
    this.openAdequateCaseCode = openAdequateCaseCode;
    this.receivedDate = receivedDate;
    this.receivedTime = receivedTime;
    this.referralResponseType = referralResponseType;
    this.referredToResourceType = referredToResourceType;
    this.responseDeterminationDate = responseDeterminationDate;
    this.responseDeterminationTime = responseDeterminationTime;
    this.responseRationaleText = responseRationaleText;
    this.screenerNoteText = screenerNoteText;
    this.specificsIncludedCode = specificsIncludedCode;
    this.sufficientInformationCode = sufficientInformationCode;
    this.unfoundedSeriesCode = unfoundedSeriesCode;
    this.linkToPrimaryReferralId = linkToPrimaryReferralId;
    this.allegesAbuseOccurredAtAddressId = allegesAbuseOccurredAtAddressId;
    this.firstResponseDeterminedByStaffPersonId = firstResponseDeterminedByStaffPersonId;
    this.primaryContactStaffPersonId = primaryContactStaffPersonId;
    this.countySpecificCode = countySpecificCode;
    this.specialProjectReferralIndicator = specialProjectReferralIndicator;
    this.zippyCreatedIndicator = zippyCreatedIndicator;
    this.homelessIndicator = homelessIndicator;
    this.familyRefusedServicesIndicator = familyRefusedServicesIndicator;
    this.firstEvaluatedOutApprovalDate = firstEvaluatedOutApprovalDate;
    this.responsibleAgencyCode = responsibleAgencyCode;
    this.limitedAccessGovtAgencyType = limitedAccessGovtAgencyType;
    this.limitedAccessDate = limitedAccessDate;
    this.limitedAccessDesc = limitedAccessDesc;
    this.originalClosureDate = originalClosureDate;
  }

  /**
   * Constructor
   * 
   * @param id The id
   * @param referral The domain object to construct this object from
   * @param lastUpdatedId the id of the last person to update this object
   */
  public Referral(String id, gov.ca.cwds.rest.api.domain.cms.Referral referral,
      String lastUpdatedId) {
    super(lastUpdatedId);

    try {

      this.id = id;
      this.additionalInfoIncludedCode =
          DomainObject.cookBoolean(referral.getAdditionalInfoIncludedCode());
      this.anonymousReporterIndicator =
          DomainObject.cookBoolean(referral.getAnonymousReporterIndicator());
      this.applicationForPetitionIndicator =
          DomainObject.cookBoolean(referral.getApplicationForPetitionIndicator());
      this.approvalNumber = referral.getApprovalNumber();
      this.approvalStatusType = referral.getApprovalStatusType();
      this.caretakersPerpetratorCode =
          DomainObject.cookBoolean(referral.getCaretakersPerpetratorCode());
      this.closureDate = DomainObject.uncookDateString(referral.getClosureDate());
      this.communicationMethodType = referral.getCommunicationMethodType();
      this.currentLocationOfChildren = referral.getCurrentLocationOfChildren();
      this.drmsAllegationDescriptionDoc = referral.getDrmsAllegationDescriptionDoc();
      this.drmsErReferralDoc = referral.getDrmsErReferralDoc();
      this.drmsInvestigationDoc = referral.getDrmsInvestigationDoc();
      this.filedSuspectedChildAbuseReporttoLawEnforcementIndicator = DomainObject
          .cookBoolean(referral.getFiledSuspectedChildAbuseReporttoLawEnforcementIndicator());
      this.familyAwarenessIndicator =
          DomainObject.cookBoolean(referral.getFamilyAwarenessIndicator());
      this.govtEntityType = referral.getGovtEntityType();
      this.legalDefinitionCode = referral.getLegalDefinitionCode();
      this.legalRightsNoticeIndicator =
          DomainObject.cookBoolean(referral.getLegalRightsNoticeIndicator());
      this.limitedAccessCode = referral.getLimitedAccessCode();
      this.mandatedCrossReportReceivedDate =
          DomainObject.uncookDateString(referral.getMandatedCrossReportReceivedDate());
      this.referralName = referral.getReferralName();
      this.openAdequateCaseCode = referral.getOpenAdequateCaseCode();
      this.receivedDate = DomainObject.uncookDateString(referral.getReceivedDate());
      this.receivedTime = DomainObject.uncookTimeString(referral.getReceivedTime());
      this.referralResponseType = referral.getReferralResponseType();
      this.referredToResourceType = referral.getReferredToResourceType();
      this.responseDeterminationDate =
          DomainObject.uncookDateString(referral.getResponseDeterminationDate());
      this.responseDeterminationTime =
          DomainObject.uncookTimeString(referral.getResponseDeterminationTime());
      this.responseRationaleText = referral.getResponseRationaleText();
      this.screenerNoteText = referral.getScreenerNoteText();
      this.specificsIncludedCode = referral.getSpecificsIncludedCode();
      this.sufficientInformationCode = referral.getSufficientInformationCode();
      this.unfoundedSeriesCode = referral.getUnfoundedSeriesCode();
      this.linkToPrimaryReferralId = referral.getLinkToPrimaryReferralId();
      this.allegesAbuseOccurredAtAddressId = referral.getAllegesAbuseOccurredAtAddressId();
      this.firstResponseDeterminedByStaffPersonId =
          referral.getFirstResponseDeterminedByStaffPersonId();
      this.primaryContactStaffPersonId = referral.getPrimaryContactStaffPersonId();
      this.countySpecificCode = referral.getCountySpecificCode();
      this.specialProjectReferralIndicator =
          DomainObject.cookBoolean(referral.getSpecialProjectReferralIndicator());
      this.zippyCreatedIndicator = DomainObject.cookBoolean(referral.getZippyCreatedIndicator());
      this.homelessIndicator = DomainObject.cookBoolean(referral.getHomelessIndicator());
      this.familyRefusedServicesIndicator =
          DomainObject.cookBoolean(referral.getFamilyRefusedServicesIndicator());
      this.firstEvaluatedOutApprovalDate =
          DomainObject.uncookDateString(referral.getFirstEvaluatedOutApprovalDate());
      this.responsibleAgencyCode = referral.getResponsibleAgencyCode();
      this.limitedAccessGovtAgencyType = referral.getLimitedAccessGovtAgencyType();
      this.limitedAccessDate = DomainObject.uncookDateString(referral.getLimitedAccessDate());
      this.limitedAccessDesc = referral.getLimitedAccessDesc();
      this.originalClosureDate = DomainObject.uncookDateString(referral.getOriginalClosureDate());
    } catch (ApiException e) {
      throw new PersistenceException(e);
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see gov.ca.cwds.rest.api.persistence.PersistentObject#getPrimaryKey()
   */
  @Override
  public String getPrimaryKey() {
    return getId();
  }

  /**
   * @return the id
   */
  public String getId() {
    return id;
  }

  /**
   * @return the additionalInfoIncludedCode
   */
  public String getAdditionalInfoIncludedCode() {
    return additionalInfoIncludedCode;
  }

  /**
   * @return the anonymousReporterIndicator
   */
  public String getAnonymousReporterIndicator() {
    return anonymousReporterIndicator;
  }

  /**
   * @return the applicationForPetitionIndicator
   */
  public String getApplicationForPetitionIndicator() {
    return applicationForPetitionIndicator;
  }

  /**
   * @return the approvalNumber
   */
  public String getApprovalNumber() {
    return approvalNumber;
  }

  /**
   * @return the approvalStatusType
   */
  public Short getApprovalStatusType() {
    return approvalStatusType;
  }

  /**
   * @return the caretakersPerpetratorCode
   */
  public String getCaretakersPerpetratorCode() {
    return caretakersPerpetratorCode;
  }

  /**
   * @return the closureDate
   */
  public Date getClosureDate() {
    return closureDate;
  }

  /**
   * @return the communicationMethodType
   */
  public Short getCommunicationMethodType() {
    return communicationMethodType;
  }

  /**
   * @return the currentLocationOfChildren
   */
  public String getCurrentLocationOfChildren() {
    return currentLocationOfChildren;
  }

  /**
   * @return the drmsAllegationDescriptionDoc
   */
  public String getDrmsAllegationDescriptionDoc() {
    return drmsAllegationDescriptionDoc;
  }

  /**
   * @return the drmsErReferralDoc
   */
  public String getDrmsErReferralDoc() {
    return drmsErReferralDoc;
  }

  /**
   * @return the drmsInvestigationDoc
   */
  public String getDrmsInvestigationDoc() {
    return drmsInvestigationDoc;
  }

  /**
   * @return the filedSuspectedChildAbuseReporttoLawEnforcementIndicator
   */
  public String getFiledSuspectedChildAbuseReporttoLawEnforcementIndicator() {
    return filedSuspectedChildAbuseReporttoLawEnforcementIndicator;
  }

  /**
   * @return the familyAwarenessIndicator
   */
  public String getFamilyAwarenessIndicator() {
    return familyAwarenessIndicator;
  }

  /**
   * @return the govtEntityType
   */
  public Short getGovtEntityType() {
    return govtEntityType;
  }

  /**
   * @return the legalDefinitionCode
   */
  public String getLegalDefinitionCode() {
    return legalDefinitionCode;
  }

  /**
   * @return the legalRightsNoticeIndicator
   */
  public String getLegalRightsNoticeIndicator() {
    return legalRightsNoticeIndicator;
  }

  /**
   * @return the limitedAccessCode
   */
  public String getLimitedAccessCode() {
    return limitedAccessCode;
  }

  /**
   * @return the mandatedCrossReportReceivedDate
   */
  public Date getMandatedCrossReportReceivedDate() {
    return mandatedCrossReportReceivedDate;
  }

  /**
   * @return the referralName
   */
  public String getReferralName() {
    return referralName;
  }

  /**
   * @return the openAdequateCaseCode
   */
  public String getOpenAdequateCaseCode() {
    return openAdequateCaseCode;
  }

  /**
   * @return the receivedDate
   */
  public Date getReceivedDate() {
    return receivedDate;
  }

  /**
   * @return the receivedTime
   */
  public Date getReceivedTime() {
    return receivedTime;
  }

  /**
   * @return the referralResponseType
   */
  public Short getReferralResponseType() {
    return referralResponseType;
  }

  /**
   * @return the referredToResourceType
   */
  public Short getReferredToResourceType() {
    return referredToResourceType;
  }

  /**
   * @return the responseDeterminationDate
   */
  public Date getResponseDeterminationDate() {
    return responseDeterminationDate;
  }

  /**
   * @return the responseDeterminationTime
   */
  public Date getResponseDeterminationTime() {
    return responseDeterminationTime;
  }

  /**
   * @return the responseRationaleText
   */
  public String getResponseRationaleText() {
    return responseRationaleText;
  }

  /**
   * @return the screenerNoteText
   */
  public String getScreenerNoteText() {
    return screenerNoteText;
  }

  /**
   * @return the specificsIncludedCode
   */
  public String getSpecificsIncludedCode() {
    return specificsIncludedCode;
  }

  /**
   * @return the sufficientInformationCode
   */
  public String getSufficientInformationCode() {
    return sufficientInformationCode;
  }

  /**
   * @return the unfoundedSeriesCode
   */
  public String getUnfoundedSeriesCode() {
    return unfoundedSeriesCode;
  }

  /**
   * @return the linkToPrimaryReferralId
   */
  public String getLinkToPrimaryReferralId() {
    return linkToPrimaryReferralId;
  }

  /**
   * @return the allegesAbuseOccurredAtAddressId
   */
  public String getAllegesAbuseOccurredAtAddressId() {
    return allegesAbuseOccurredAtAddressId;
  }

  /**
   * @return the firstResponseDeterminedByStaffPersonId
   */
  public String getFirstResponseDeterminedByStaffPersonId() {
    return firstResponseDeterminedByStaffPersonId;
  }

  /**
   * @return the primaryContactStaffPersonId
   */
  public String getPrimaryContactStaffPersonId() {
    return primaryContactStaffPersonId;
  }

  /**
   * @return the countySpecificCode
   */
  public String getCountySpecificCode() {
    return countySpecificCode;
  }

  /**
   * @return the specialProjectReferralIndicator
   */
  public String getSpecialProjectReferralIndicator() {
    return specialProjectReferralIndicator;
  }

  /**
   * @return the zippyCreatedIndicator
   */
  public String getZippyCreatedIndicator() {
    return zippyCreatedIndicator;
  }

  /**
   * @return the homelessIndicator
   */
  public String getHomelessIndicator() {
    return homelessIndicator;
  }

  /**
   * @return the familyRefusedServicesIndicator
   */
  public String getFamilyRefusedServicesIndicator() {
    return familyRefusedServicesIndicator;
  }

  /**
   * @return the firstEvaluatedOutApprovalDate
   */
  public Date getFirstEvaluatedOutApprovalDate() {
    return firstEvaluatedOutApprovalDate;
  }

  /**
   * @return the responsibleAgencyCode
   */
  public String getResponsibleAgencyCode() {
    return responsibleAgencyCode;
  }

  /**
   * @return the limitedAccessGovtAgencyType
   */
  public Short getLimitedAccessGovtAgencyType() {
    return limitedAccessGovtAgencyType;
  }

  /**
   * @return the limitedAccessDate
   */
  public Date getLimitedAccessDate() {
    return limitedAccessDate;
  }

  /**
   * @return the limitedAccessDesc
   */
  public String getLimitedAccessDesc() {
    return limitedAccessDesc;
  }

  /**
   * @return the originalClosureDate
   */
  public Date getOriginalClosureDate() {
    return originalClosureDate;
  }
}
