package gov.ca.cwds.fixture;

import gov.ca.cwds.rest.api.domain.cms.Address;
import gov.ca.cwds.rest.api.domain.cms.Allegation;
import gov.ca.cwds.rest.api.domain.cms.Client;
import gov.ca.cwds.rest.api.domain.cms.CrossReport;
import gov.ca.cwds.rest.api.domain.cms.Referral;
import gov.ca.cwds.rest.api.domain.cms.Reporter;
import java.util.HashSet;
import java.util.Set;

public class ReferralResourceBuilder {
       Boolean additionalInfoIncludedCode = false;
       Boolean anonymousReporterIndicator = false;
       Boolean applicationForPetitionIndicator = false;
       String approvalNumber = "";
       Short approvalStatusType = 0;
       Boolean caretakersPerpetratorCode = false;
       String closureDate = "";
       Short communicationMethodType = 0;
       String currentLocationOfChildren = "";
       String drmsAllegationDescriptionDoc = "";
       String drmsErReferralDoc = "";
       String drmsInvestigationDoc = "";
       Boolean filedSuspectedChildAbuseReporttoLawEnforcementIndicator = false;
       Boolean familyAwarenessIndicator = false;
       Short govtEntityType = 0;
       String legalDefinitionCode = "N";
       Boolean legalRightsNoticeIndicator = false;
       String limitedAccessCode = "N";
       String mandatedCrossReportReceivedDate = "";
       String referralName = "";
       String openAdequateCaseCode = "";
       String receivedDate = "";
       String receivedTime = "";
       Short referralResponseType = 0;
       Short referredToResourceType = 0;
       String responseDeterminationDate = "";
       String responseDeterminationTime = "";
       String responseRationaleText = "";
       String screenerNoteText = "";
       String specificsIncludedCode = "N";
       String sufficientInformationCode = "N";
       String unfoundedSeriesCode = "N";
       String linkToPrimaryReferralId = "";
       String allegesAbuseOccurredAtAddressId = "";
       String firstResponseDeterminedByStaffPersonId = "";
       String primaryContactStaffPersonId = "";
       String countySpecificCode = "";
       Boolean specialProjectReferralIndicator = false;
       Boolean zippyCreatedIndicator = false;
       Boolean homelessIndicator = false;
       Boolean familyRefusedServicesIndicator = false;
       String firstEvaluatedOutApprovalDate = "";
       String responsibleAgencyCode = "C";
       Short limitedAccessGovtAgencyType = 0;
       String limitedAccessDate = "";
       String limitedAccessDesc = "";
       String originalClosureDate = "";
       String uiIdentifier = "";
       Set<Address> address = new HashSet<>();
       Set<Reporter> reporter = new HashSet<>();
       Set<CrossReport> crossReport = new HashSet<>();
       Set<Allegation> allegation = new HashSet<>();
       Set<Client> victimClient = new HashSet<>();
       Set<Client> perpetratorClient = new HashSet<>();

  public Boolean getAdditionalInfoIncludedCode() {
    return additionalInfoIncludedCode;
  }

  public ReferralResourceBuilder setAdditionalInfoIncludedCode(Boolean additionalInfoIncludedCode) {
    this.additionalInfoIncludedCode = additionalInfoIncludedCode;
    return this;
  }

  public Boolean getAnonymousReporterIndicator() {
    return anonymousReporterIndicator;
  }

  public ReferralResourceBuilder setAnonymousReporterIndicator(Boolean anonymousReporterIndicator) {
    this.anonymousReporterIndicator = anonymousReporterIndicator;
    return this;
  }

  public Boolean getApplicationForPetitionIndicator() {
    return applicationForPetitionIndicator;
  }

  public ReferralResourceBuilder setApplicationForPetitionIndicator(Boolean applicationForPetitionIndicator) {
    this.applicationForPetitionIndicator = applicationForPetitionIndicator;
    return this;
  }

  public String getApprovalNumber() {
    return approvalNumber;
  }

  public ReferralResourceBuilder setApprovalNumber(String approvalNumber) {
    this.approvalNumber = approvalNumber;
    return this;
  }

  public Short getApprovalStatusType() {
    return approvalStatusType;
  }

  public ReferralResourceBuilder setApprovalStatusType(Short approvalStatusType) {
    this.approvalStatusType = approvalStatusType;
    return this;
  }

  public Boolean getCaretakersPerpetratorCode() {
    return caretakersPerpetratorCode;
  }

  public ReferralResourceBuilder setCaretakersPerpetratorCode(Boolean caretakersPerpetratorCode) {
    this.caretakersPerpetratorCode = caretakersPerpetratorCode;
    return this;
  }

  public String getClosureDate() {
    return closureDate;
  }

  public ReferralResourceBuilder setClosureDate(String closureDate) {
    this.closureDate = closureDate;
    return this;
  }

  public Short getCommunicationMethodType() {
    return communicationMethodType;
  }

  public ReferralResourceBuilder setCommunicationMethodType(Short communicationMethodType) {
    this.communicationMethodType = communicationMethodType;
    return this;
  }

  public String getCurrentLocationOfChildren() {
    return currentLocationOfChildren;
  }

  public ReferralResourceBuilder setCurrentLocationOfChildren(String currentLocationOfChildren) {
    this.currentLocationOfChildren = currentLocationOfChildren;
    return this;
  }

  public String getDrmsAllegationDescriptionDoc() {
    return drmsAllegationDescriptionDoc;
  }

  public ReferralResourceBuilder setDrmsAllegationDescriptionDoc(String drmsAllegationDescriptionDoc) {
    this.drmsAllegationDescriptionDoc = drmsAllegationDescriptionDoc;
    return this;
  }

  public String getDrmsErReferralDoc() {
    return drmsErReferralDoc;
  }

  public ReferralResourceBuilder setDrmsErReferralDoc(String drmsErReferralDoc) {
    this.drmsErReferralDoc = drmsErReferralDoc;
    return this;
  }

  public String getDrmsInvestigationDoc() {
    return drmsInvestigationDoc;
  }

  public ReferralResourceBuilder setDrmsInvestigationDoc(String drmsInvestigationDoc) {
    this.drmsInvestigationDoc = drmsInvestigationDoc;
    return this;
  }

  public Boolean getFiledSuspectedChildAbuseReporttoLawEnforcementIndicator() {
    return filedSuspectedChildAbuseReporttoLawEnforcementIndicator;
  }

  public ReferralResourceBuilder setFiledSuspectedChildAbuseReporttoLawEnforcementIndicator(
      Boolean filedSuspectedChildAbuseReporttoLawEnforcementIndicator) {
    this.filedSuspectedChildAbuseReporttoLawEnforcementIndicator = filedSuspectedChildAbuseReporttoLawEnforcementIndicator;
    return this;
  }

  public Boolean getFamilyAwarenessIndicator() {
    return familyAwarenessIndicator;
  }

  public ReferralResourceBuilder setFamilyAwarenessIndicator(Boolean familyAwarenessIndicator) {
    this.familyAwarenessIndicator = familyAwarenessIndicator;
    return this;
  }

  public Short getGovtEntityType() {
    return govtEntityType;
  }

  public ReferralResourceBuilder setGovtEntityType(Short govtEntityType) {
    this.govtEntityType = govtEntityType;
    return this;
  }

  public String getLegalDefinitionCode() {
    return legalDefinitionCode;
  }

  public ReferralResourceBuilder setLegalDefinitionCode(String legalDefinitionCode) {
    this.legalDefinitionCode = legalDefinitionCode;
    return this;
  }

  public Boolean getLegalRightsNoticeIndicator() {
    return legalRightsNoticeIndicator;
  }

  public ReferralResourceBuilder setLegalRightsNoticeIndicator(Boolean legalRightsNoticeIndicator) {
    this.legalRightsNoticeIndicator = legalRightsNoticeIndicator;
    return this;
  }

  public String getLimitedAccessCode() {
    return limitedAccessCode;
  }

  public ReferralResourceBuilder setLimitedAccessCode(String limitedAccessCode) {
    this.limitedAccessCode = limitedAccessCode;
    return this;
  }

  public String getMandatedCrossReportReceivedDate() {
    return mandatedCrossReportReceivedDate;
  }

  public ReferralResourceBuilder setMandatedCrossReportReceivedDate(String mandatedCrossReportReceivedDate) {
    this.mandatedCrossReportReceivedDate = mandatedCrossReportReceivedDate;
    return this;
  }

  public String getReferralName() {
    return referralName;
  }

  public ReferralResourceBuilder setReferralName(String referralName) {
    this.referralName = referralName;
    return this;
  }

  public String getOpenAdequateCaseCode() {
    return openAdequateCaseCode;
  }

  public ReferralResourceBuilder setOpenAdequateCaseCode(String openAdequateCaseCode) {
    this.openAdequateCaseCode = openAdequateCaseCode;
    return this;
  }

  public String getReceivedDate() {
    return receivedDate;
  }

  public ReferralResourceBuilder setReceivedDate(String receivedDate) {
    this.receivedDate = receivedDate;
    return this;
  }

  public String getReceivedTime() {
    return receivedTime;
  }

  public ReferralResourceBuilder setReceivedTime(String receivedTime) {
    this.receivedTime = receivedTime;
    return this;
  }

  public Short getReferralResponseType() {
    return referralResponseType;
  }

  public ReferralResourceBuilder setReferralResponseType(Short referralResponseType) {
    this.referralResponseType = referralResponseType;
    return this;
  }

  public Short getReferredToResourceType() {
    return referredToResourceType;
  }

  public ReferralResourceBuilder setReferredToResourceType(Short referredToResourceType) {
    this.referredToResourceType = referredToResourceType;
    return this;
  }

  public String getResponseDeterminationDate() {
    return responseDeterminationDate;
  }

  public ReferralResourceBuilder setResponseDeterminationDate(String responseDeterminationDate) {
    this.responseDeterminationDate = responseDeterminationDate;
    return this;
  }

  public String getResponseDeterminationTime() {
    return responseDeterminationTime;
  }

  public ReferralResourceBuilder setResponseDeterminationTime(String responseDeterminationTime) {
    this.responseDeterminationTime = responseDeterminationTime;
    return this;
  }

  public String getResponseRationaleText() {
    return responseRationaleText;
  }

  public ReferralResourceBuilder setResponseRationaleText(String responseRationaleText) {
    this.responseRationaleText = responseRationaleText;
    return this;
  }

  public String getScreenerNoteText() {
    return screenerNoteText;
  }

  public ReferralResourceBuilder setScreenerNoteText(String screenerNoteText) {
    this.screenerNoteText = screenerNoteText;
    return this;
  }

  public String getSpecificsIncludedCode() {
    return specificsIncludedCode;
  }

  public ReferralResourceBuilder setSpecificsIncludedCode(String specificsIncludedCode) {
    this.specificsIncludedCode = specificsIncludedCode;
    return this;
  }

  public String getSufficientInformationCode() {
    return sufficientInformationCode;
  }

  public ReferralResourceBuilder setSufficientInformationCode(String sufficientInformationCode) {
    this.sufficientInformationCode = sufficientInformationCode;
    return this;
  }

  public String getUnfoundedSeriesCode() {
    return unfoundedSeriesCode;
  }

  public ReferralResourceBuilder setUnfoundedSeriesCode(String unfoundedSeriesCode) {
    this.unfoundedSeriesCode = unfoundedSeriesCode;
    return this;
  }

  public String getLinkToPrimaryReferralId() {
    return linkToPrimaryReferralId;
  }

  public ReferralResourceBuilder setLinkToPrimaryReferralId(String linkToPrimaryReferralId) {
    this.linkToPrimaryReferralId = linkToPrimaryReferralId;
    return this;
  }

  public String getAllegesAbuseOccurredAtAddressId() {
    return allegesAbuseOccurredAtAddressId;
  }

  public ReferralResourceBuilder setAllegesAbuseOccurredAtAddressId(String allegesAbuseOccurredAtAddressId) {
    this.allegesAbuseOccurredAtAddressId = allegesAbuseOccurredAtAddressId;
    return this;
  }

  public String getFirstResponseDeterminedByStaffPersonId() {
    return firstResponseDeterminedByStaffPersonId;
  }

  public ReferralResourceBuilder setFirstResponseDeterminedByStaffPersonId(
      String firstResponseDeterminedByStaffPersonId) {
    this.firstResponseDeterminedByStaffPersonId = firstResponseDeterminedByStaffPersonId;
    return this;
  }

  public String getPrimaryContactStaffPersonId() {
    return primaryContactStaffPersonId;
  }

  public ReferralResourceBuilder setPrimaryContactStaffPersonId(String primaryContactStaffPersonId) {
    this.primaryContactStaffPersonId = primaryContactStaffPersonId;
    return this;
  }

  public String getCountySpecificCode() {
    return countySpecificCode;
  }

  public ReferralResourceBuilder setCountySpecificCode(String countySpecificCode) {
    this.countySpecificCode = countySpecificCode;
    return this;
  }

  public Boolean getSpecialProjectReferralIndicator() {
    return specialProjectReferralIndicator;
  }

  public ReferralResourceBuilder setSpecialProjectReferralIndicator(Boolean specialProjectReferralIndicator) {
    this.specialProjectReferralIndicator = specialProjectReferralIndicator;
    return this;
  }

  public Boolean getZippyCreatedIndicator() {
    return zippyCreatedIndicator;
  }

  public ReferralResourceBuilder setZippyCreatedIndicator(Boolean zippyCreatedIndicator) {
    this.zippyCreatedIndicator = zippyCreatedIndicator;
    return this;
  }

  public Boolean getHomelessIndicator() {
    return homelessIndicator;
  }

  public ReferralResourceBuilder setHomelessIndicator(Boolean homelessIndicator) {
    this.homelessIndicator = homelessIndicator;
    return this;
  }

  public Boolean getFamilyRefusedServicesIndicator() {
    return familyRefusedServicesIndicator;
  }

  public ReferralResourceBuilder setFamilyRefusedServicesIndicator(Boolean familyRefusedServicesIndicator) {
    this.familyRefusedServicesIndicator = familyRefusedServicesIndicator;
    return this;
  }

  public String getFirstEvaluatedOutApprovalDate() {
    return firstEvaluatedOutApprovalDate;
  }

  public ReferralResourceBuilder setFirstEvaluatedOutApprovalDate(String firstEvaluatedOutApprovalDate) {
    this.firstEvaluatedOutApprovalDate = firstEvaluatedOutApprovalDate;
    return this;
  }

  public String getResponsibleAgencyCode() {
    return responsibleAgencyCode;
  }

  public ReferralResourceBuilder setResponsibleAgencyCode(String responsibleAgencyCode) {
    this.responsibleAgencyCode = responsibleAgencyCode;
    return this;
  }

  public Short getLimitedAccessGovtAgencyType() {
    return limitedAccessGovtAgencyType;
  }

  public ReferralResourceBuilder setLimitedAccessGovtAgencyType(Short limitedAccessGovtAgencyType) {
    this.limitedAccessGovtAgencyType = limitedAccessGovtAgencyType;
    return this;
  }

  public String getLimitedAccessDate() {
    return limitedAccessDate;
  }

  public ReferralResourceBuilder setLimitedAccessDate(String limitedAccessDate) {
    this.limitedAccessDate = limitedAccessDate;
    return this;
  }

  public String getLimitedAccessDesc() {
    return limitedAccessDesc;
  }

  public ReferralResourceBuilder setLimitedAccessDesc(String limitedAccessDesc) {
    this.limitedAccessDesc = limitedAccessDesc;
    return this;
  }

  public String getOriginalClosureDate() {
    return originalClosureDate;
  }

  public ReferralResourceBuilder setOriginalClosureDate(String originalClosureDate) {
    this.originalClosureDate = originalClosureDate;
    return this;
  }

  public String getUiIdentifier() {
    return uiIdentifier;
  }

  public ReferralResourceBuilder setUiIdentifier(String uiIdentifier) {
    this.uiIdentifier = uiIdentifier;
    return this;
  }

  public Set<Address> getAddress() {
    return address;
  }

  public ReferralResourceBuilder setAddress(Set<Address> address) {
    this.address = address;
    return this;
  }

  public Set<Reporter> getReporter() {
    return reporter;
  }

  public ReferralResourceBuilder setReporter(Set<Reporter> reporter) {
    this.reporter = reporter;
    return this;
  }

  public Set<CrossReport> getCrossReport() {
    return crossReport;
  }

  public ReferralResourceBuilder setCrossReport(Set<CrossReport> crossReport) {
    this.crossReport = crossReport;
    return this;
  }

  public Set<Allegation> getAllegation() {
    return allegation;
  }

  public ReferralResourceBuilder setAllegation(Set<Allegation> allegation) {
    this.allegation = allegation;
    return this;
  }

  public Set<Client> getVictimClient() {
    return victimClient;
  }

  public ReferralResourceBuilder setVictimClient(Set<Client> victimClient) {
    this.victimClient = victimClient;
    return this;
  }

  public Set<Client> getPerpetratorClient() {
    return perpetratorClient;
  }

  public ReferralResourceBuilder setPerpetratorClient(
      Set<Client> perpetratorClient) {
    this.perpetratorClient = perpetratorClient;
    return this;
  }

  public Referral build(){
    return new Referral(additionalInfoIncludedCode,anonymousReporterIndicator,applicationForPetitionIndicator,
        approvalNumber, approvalStatusType, caretakersPerpetratorCode, closureDate, communicationMethodType,
        currentLocationOfChildren, drmsAllegationDescriptionDoc, drmsErReferralDoc, drmsInvestigationDoc,
        filedSuspectedChildAbuseReporttoLawEnforcementIndicator, familyAwarenessIndicator, govtEntityType,
        legalDefinitionCode, legalRightsNoticeIndicator,limitedAccessCode, mandatedCrossReportReceivedDate,
        referralName, openAdequateCaseCode, receivedDate, receivedTime, referralResponseType, referredToResourceType,
        responseDeterminationDate, responseDeterminationTime, responseRationaleText, screenerNoteText,
        specificsIncludedCode, sufficientInformationCode,unfoundedSeriesCode, linkToPrimaryReferralId,
        allegesAbuseOccurredAtAddressId, firstResponseDeterminedByStaffPersonId, primaryContactStaffPersonId,
        countySpecificCode, specialProjectReferralIndicator, zippyCreatedIndicator, homelessIndicator,
        familyRefusedServicesIndicator, firstEvaluatedOutApprovalDate, responsibleAgencyCode, limitedAccessGovtAgencyType,
        limitedAccessDate,limitedAccessDesc, originalClosureDate, uiIdentifier, address, reporter,
        crossReport, allegation, victimClient, perpetratorClient
      );
  }
}
