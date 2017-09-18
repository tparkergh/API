package gov.ca.cwds.fixture;

import java.util.Date;
import java.util.Set;

import gov.ca.cwds.data.persistence.cms.Address;
import gov.ca.cwds.data.persistence.cms.Allegation;
import gov.ca.cwds.data.persistence.cms.CrossReport;
import gov.ca.cwds.data.persistence.cms.Referral;
import gov.ca.cwds.data.persistence.cms.Reporter;

public class ReferralEntityBuilder {
  String id;
  String additionalInfoIncludedCode;
  String anonymousReporterIndicator;
  String applicationForPetitionIndicator;
  String approvalNumber;
  Short approvalStatusType;
  String caretakersPerpetratorCode;
  Date closureDate;
  Short communicationMethodType;
  String currentLocationOfChildren;
  String drmsAllegationDescriptionDoc;
  String drmsErReferralDoc;
  String drmsInvestigationDoc;
  String filedSuspectedChildAbuseReporttoLawEnforcementIndicator;
  String familyAwarenessIndicator;
  Short govtEntityType;
  String legalDefinitionCode;
  String legalRightsNoticeIndicator;
  String limitedAccessCode;
  Date mandatedCrossReportReceivedDate;
  String referralName;
  String openAdequateCaseCode;
  Date receivedDate;
  Date receivedTime;
  Short referralResponseType;
  Short referredToResourceType;
  Date responseDeterminationDate;
  Date responseDeterminationTime;
  String responseRationaleText;
  String screenerNoteText;
  String specificsIncludedCode;
  String sufficientInformationCode;
  String unfoundedSeriesCode;
  String linkToPrimaryReferralId;
  String allegesAbuseOccurredAtAddressId;
  String firstResponseDeterminedByStaffPersonId;
  String primaryContactStaffPersonId;
  String countySpecificCode;
  String specialProjectReferralIndicator;
  String zippyCreatedIndicator;
  String homelessIndicator;
  String familyRefusedServicesIndicator;
  Date firstEvaluatedOutApprovalDate;
  String responsibleAgencyCode;
  Short limitedAccessGovtAgencyType;
  Date limitedAccessDate;
  String limitedAccessDesc;
  Date originalClosureDate;
  Address addresses;
  Set<Allegation> allegations;
  Set<CrossReport> crossReports;
  Set<Reporter> reporters;

  public ReferralEntityBuilder setId(String id) {
    this.id = id;
    return this;
  }

  public ReferralEntityBuilder setAdditionalInfoIncludedCode(String additionalInfoIncludedCode) {
    this.additionalInfoIncludedCode = additionalInfoIncludedCode;
    return this;
  }

  public ReferralEntityBuilder setAnonymousReporterIndicator(String anonymousReporterIndicator) {
    this.anonymousReporterIndicator = anonymousReporterIndicator;
    return this;
  }

  public ReferralEntityBuilder setApplicationForPetitionIndicator(
      String applicationForPetitionIndicator) {
    this.applicationForPetitionIndicator = applicationForPetitionIndicator;
    return this;
  }

  public ReferralEntityBuilder setApprovalNumber(String approvalNumber) {
    this.approvalNumber = approvalNumber;
    return this;
  }

  public ReferralEntityBuilder setApprovalStatusType(Short approvalStatusType) {
    this.approvalStatusType = approvalStatusType;
    return this;
  }

  public ReferralEntityBuilder setCaretakersPerpetratorCode(String caretakersPerpetratorCode) {
    this.caretakersPerpetratorCode = caretakersPerpetratorCode;
    return this;
  }

  public ReferralEntityBuilder setClosureDate(Date closureDate) {
    this.closureDate = closureDate;
    return this;
  }

  public ReferralEntityBuilder setCommunicationMethodType(Short communicationMethodType) {
    this.communicationMethodType = communicationMethodType;
    return this;
  }

  public ReferralEntityBuilder setCurrentLocationOfChildren(String currentLocationOfChildren) {
    this.currentLocationOfChildren = currentLocationOfChildren;
    return this;
  }

  public ReferralEntityBuilder setDrmsAllegationDescriptionDoc(
      String drmsAllegationDescriptionDoc) {
    this.drmsAllegationDescriptionDoc = drmsAllegationDescriptionDoc;
    return this;
  }

  public ReferralEntityBuilder setDrmsErReferralDoc(String drmsErReferralDoc) {
    this.drmsErReferralDoc = drmsErReferralDoc;
    return this;
  }

  public ReferralEntityBuilder setDrmsInvestigationDoc(String drmsInvestigationDoc) {
    this.drmsInvestigationDoc = drmsInvestigationDoc;
    return this;
  }

  public ReferralEntityBuilder setFiledSuspectedChildAbuseReporttoLawEnforcementIndicator(
      String filedSuspectedChildAbuseReporttoLawEnforcementIndicator) {
    this.filedSuspectedChildAbuseReporttoLawEnforcementIndicator =
        filedSuspectedChildAbuseReporttoLawEnforcementIndicator;
    return this;
  }

  public ReferralEntityBuilder setFamilyAwarenessIndicator(String familyAwarenessIndicator) {
    this.familyAwarenessIndicator = familyAwarenessIndicator;
    return this;
  }

  public ReferralEntityBuilder setGovtEntityType(Short govtEntityType) {
    this.govtEntityType = govtEntityType;
    return this;
  }

  public ReferralEntityBuilder setLegalDefinitionCode(String legalDefinitionCode) {
    this.legalDefinitionCode = legalDefinitionCode;
    return this;
  }

  public ReferralEntityBuilder setLegalRightsNoticeIndicator(String legalRightsNoticeIndicator) {
    this.legalRightsNoticeIndicator = legalRightsNoticeIndicator;
    return this;
  }

  public ReferralEntityBuilder setLimitedAccessCode(String limitedAccessCode) {
    this.limitedAccessCode = limitedAccessCode;
    return this;
  }

  public ReferralEntityBuilder setMandatedCrossReportReceivedDate(
      Date mandatedCrossReportReceivedDate) {
    this.mandatedCrossReportReceivedDate = mandatedCrossReportReceivedDate;
    return this;
  }

  public ReferralEntityBuilder setReferralName(String referralName) {
    this.referralName = referralName;
    return this;
  }

  public ReferralEntityBuilder setOpenAdequateCaseCode(String openAdequateCaseCode) {
    this.openAdequateCaseCode = openAdequateCaseCode;
    return this;
  }

  public ReferralEntityBuilder setReceivedDate(Date receivedDate) {
    this.receivedDate = receivedDate;
    return this;
  }

  public ReferralEntityBuilder setReceivedTime(Date receivedTime) {
    this.receivedTime = receivedTime;
    return this;
  }

  public ReferralEntityBuilder setReferralResponseType(Short referralResponseType) {
    this.referralResponseType = referralResponseType;
    return this;
  }

  public ReferralEntityBuilder setReferredToResourceType(Short referredToResourceType) {
    this.referredToResourceType = referredToResourceType;
    return this;
  }

  public ReferralEntityBuilder setResponseDeterminationDate(Date responseDeterminationDate) {
    this.responseDeterminationDate = responseDeterminationDate;
    return this;
  }

  public ReferralEntityBuilder setResponseDeterminationTime(Date responseDeterminationTime) {
    this.responseDeterminationTime = responseDeterminationTime;
    return this;
  }

  public ReferralEntityBuilder setResponseRationaleText(String responseRationaleText) {
    this.responseRationaleText = responseRationaleText;
    return this;
  }

  public ReferralEntityBuilder setScreenerNoteText(String screenerNoteText) {
    this.screenerNoteText = screenerNoteText;
    return this;
  }

  public ReferralEntityBuilder setSpecificsIncludedCode(String specificsIncludedCode) {
    this.specificsIncludedCode = specificsIncludedCode;
    return this;
  }

  public ReferralEntityBuilder setSufficientInformationCode(String sufficientInformationCode) {
    this.sufficientInformationCode = sufficientInformationCode;
    return this;
  }

  public ReferralEntityBuilder setUnfoundedSeriesCode(String unfoundedSeriesCode) {
    this.unfoundedSeriesCode = unfoundedSeriesCode;
    return this;
  }

  public ReferralEntityBuilder setLinkToPrimaryReferralId(String linkToPrimaryReferralId) {
    this.linkToPrimaryReferralId = linkToPrimaryReferralId;
    return this;
  }

  public ReferralEntityBuilder setAllegesAbuseOccurredAtAddressId(
      String allegesAbuseOccurredAtAddressId) {
    this.allegesAbuseOccurredAtAddressId = allegesAbuseOccurredAtAddressId;
    return this;
  }

  public ReferralEntityBuilder setFirstResponseDeterminedByStaffPersonId(
      String firstResponseDeterminedByStaffPersonId) {
    this.firstResponseDeterminedByStaffPersonId = firstResponseDeterminedByStaffPersonId;
    return this;
  }

  public ReferralEntityBuilder setPrimaryContactStaffPersonId(String primaryContactStaffPersonId) {
    this.primaryContactStaffPersonId = primaryContactStaffPersonId;
    return this;
  }

  public ReferralEntityBuilder setCountySpecificCode(String countySpecificCode) {
    this.countySpecificCode = countySpecificCode;
    return this;
  }

  public ReferralEntityBuilder setSpecialProjectReferralIndicator(
      String specialProjectReferralIndicator) {
    this.specialProjectReferralIndicator = specialProjectReferralIndicator;
    return this;
  }

  public ReferralEntityBuilder setZippyCreatedIndicator(String zippyCreatedIndicator) {
    this.zippyCreatedIndicator = zippyCreatedIndicator;
    return this;
  }

  public ReferralEntityBuilder setHomelessIndicator(String homelessIndicator) {
    this.homelessIndicator = homelessIndicator;
    return this;
  }

  public ReferralEntityBuilder setFamilyRefusedServicesIndicator(
      String familyRefusedServicesIndicator) {
    this.familyRefusedServicesIndicator = familyRefusedServicesIndicator;
    return this;
  }

  public ReferralEntityBuilder setFirstEvaluatedOutApprovalDate(
      Date firstEvaluatedOutApprovalDate) {
    this.firstEvaluatedOutApprovalDate = firstEvaluatedOutApprovalDate;
    return this;
  }

  public ReferralEntityBuilder setResponsibleAgencyCode(String responsibleAgencyCode) {
    this.responsibleAgencyCode = responsibleAgencyCode;
    return this;
  }

  public ReferralEntityBuilder setLimitedAccessGovtAgencyType(Short limitedAccessGovtAgencyType) {
    this.limitedAccessGovtAgencyType = limitedAccessGovtAgencyType;
    return this;
  }

  public ReferralEntityBuilder setLimitedAccessDate(Date limitedAccessDate) {
    this.limitedAccessDate = limitedAccessDate;
    return this;
  }

  public ReferralEntityBuilder setLimitedAccessDesc(String limitedAccessDesc) {
    this.limitedAccessDesc = limitedAccessDesc;
    return this;
  }

  public ReferralEntityBuilder setOriginalClosureDate(Date originalClosureDate) {
    this.originalClosureDate = originalClosureDate;
    return this;
  }

  public ReferralEntityBuilder setAddresses(Address addresses) {
    this.addresses = addresses;
    return this;
  }

  public ReferralEntityBuilder setAllegations(Set<Allegation> allegations) {
    this.allegations = allegations;
    return this;
  }

  public ReferralEntityBuilder setCrossReports(Set<CrossReport> crossReports) {
    this.crossReports = crossReports;
    return this;
  }

  public ReferralEntityBuilder setReporters(Set<Reporter> reporters) {
    this.reporters = reporters;
    return this;
  }

  public Referral build() {
    return new Referral(id, additionalInfoIncludedCode, anonymousReporterIndicator,
        applicationForPetitionIndicator, approvalNumber, approvalStatusType,
        caretakersPerpetratorCode, closureDate, communicationMethodType, currentLocationOfChildren,
        drmsAllegationDescriptionDoc, drmsErReferralDoc, drmsInvestigationDoc,
        filedSuspectedChildAbuseReporttoLawEnforcementIndicator, familyAwarenessIndicator,
        govtEntityType, legalDefinitionCode, legalRightsNoticeIndicator, limitedAccessCode,
        mandatedCrossReportReceivedDate, referralName, openAdequateCaseCode, receivedDate,
        receivedTime, referralResponseType, referredToResourceType, responseDeterminationDate,
        responseDeterminationTime, responseRationaleText, screenerNoteText, specificsIncludedCode,
        sufficientInformationCode, unfoundedSeriesCode, linkToPrimaryReferralId,
        allegesAbuseOccurredAtAddressId, firstResponseDeterminedByStaffPersonId,
        primaryContactStaffPersonId, countySpecificCode, specialProjectReferralIndicator,
        zippyCreatedIndicator, homelessIndicator, familyRefusedServicesIndicator,
        firstEvaluatedOutApprovalDate, responsibleAgencyCode, limitedAccessGovtAgencyType,
        limitedAccessDate, limitedAccessDesc, originalClosureDate, addresses, allegations,
        crossReports, reporters);
  }
}
