package gov.ca.cwds.fixture;

import gov.ca.cwds.rest.api.domain.cms.ReferralClient;

/**
 * 
 * @author CWDS API Team
 */
@SuppressWarnings("javadoc")
public class ReferralClientResourceBuilder {

  String approvalNumber = "123456789A";
  Short approvalStatusType = (short) 1234;
  Short dispositionClosureReasonType = (short) 7890;
  String dispositionCode = "S";
  String dispositionDate = "2000-01-01";
  Boolean selfReportedIndicator = false;
  Boolean staffPersonAddedIndicator = false;
  String referralId = "ABC1234567";
  String clientId = "1234567ABC";
  String dispositionClosureDescription = "Some type of Kili Kili descrption";
  Short ageNumber = (short) 25;
  String agePeriodCode = "D";
  String countySpecificCode = "99";
  Boolean mentalHealthIssuesIndicator = false;
  Boolean alcoholIndicator = false;
  Boolean drugIndicator = false;

  public ReferralClient buildReferralClient() {
    return new ReferralClient(approvalNumber, approvalStatusType, dispositionClosureReasonType,
        dispositionCode, dispositionDate, selfReportedIndicator, staffPersonAddedIndicator,
        referralId, clientId, dispositionClosureDescription, ageNumber, agePeriodCode,
        countySpecificCode, mentalHealthIssuesIndicator, alcoholIndicator, drugIndicator);
  }

  public String getApprovalNumber() {
    return approvalNumber;
  }

  public ReferralClientResourceBuilder setApprovalNumber(String approvalNumber) {
    this.approvalNumber = approvalNumber;
    return this;
  }

  public Short getApprovalStatusType() {
    return approvalStatusType;
  }

  public ReferralClientResourceBuilder setApprovalStatusType(Short approvalStatusType) {
    this.approvalStatusType = approvalStatusType;
    return this;
  }

  public Short getDispositionClosureReasonType() {
    return dispositionClosureReasonType;
  }

  public ReferralClientResourceBuilder setDispositionClosureReasonType(
      Short dispositionClosureReasonType) {
    this.dispositionClosureReasonType = dispositionClosureReasonType;
    return this;
  }

  public String getDispositionCode() {
    return dispositionCode;
  }

  public ReferralClientResourceBuilder setDispositionCode(String dispositionCode) {
    this.dispositionCode = dispositionCode;
    return this;
  }

  public String getDispositionDate() {
    return dispositionDate;
  }

  public ReferralClientResourceBuilder setDispositionDate(String dispositionDate) {
    this.dispositionDate = dispositionDate;
    return this;
  }

  public Boolean getSelfReportedIndicator() {
    return selfReportedIndicator;
  }

  public ReferralClientResourceBuilder setSelfReportedIndicator(Boolean selfReportedIndicator) {
    this.selfReportedIndicator = selfReportedIndicator;
    return this;
  }

  public Boolean getStaffPersonAddedIndicator() {
    return staffPersonAddedIndicator;
  }

  public ReferralClientResourceBuilder setStaffPersonAddedIndicator(
      Boolean staffPersonAddedIndicator) {
    this.staffPersonAddedIndicator = staffPersonAddedIndicator;
    return this;
  }

  public String getReferralId() {
    return referralId;
  }

  public ReferralClientResourceBuilder setReferralId(String referralId) {
    this.referralId = referralId;
    return this;
  }

  public String getClientId() {
    return clientId;
  }

  public ReferralClientResourceBuilder setClientId(String clientId) {
    this.clientId = clientId;
    return this;
  }

  public String getDispositionClosureDescription() {
    return dispositionClosureDescription;
  }

  public ReferralClientResourceBuilder setDispositionClosureDescription(
      String dispositionClosureDescription) {
    this.dispositionClosureDescription = dispositionClosureDescription;
    return this;
  }

  public Short getAgeNumber() {
    return ageNumber;
  }

  public ReferralClientResourceBuilder setAgeNumber(Short ageNumber) {
    this.ageNumber = ageNumber;
    return this;
  }

  public String getAgePeriodCode() {
    return agePeriodCode;
  }

  public ReferralClientResourceBuilder setAgePeriodCode(String agePeriodCode) {
    this.agePeriodCode = agePeriodCode;
    return this;
  }

  public String getCountySpecificCode() {
    return countySpecificCode;
  }

  public ReferralClientResourceBuilder setCountySpecificCode(String countySpecificCode) {
    this.countySpecificCode = countySpecificCode;
    return this;
  }

  public Boolean getMentalHealthIssuesIndicator() {
    return mentalHealthIssuesIndicator;
  }

  public ReferralClientResourceBuilder setMentalHealthIssuesIndicator(
      Boolean mentalHealthIssuesIndicator) {
    this.mentalHealthIssuesIndicator = mentalHealthIssuesIndicator;
    return this;
  }

  public Boolean getAlcoholIndicator() {
    return alcoholIndicator;
  }

  public ReferralClientResourceBuilder setAlcoholIndicator(Boolean alcoholIndicator) {
    this.alcoholIndicator = alcoholIndicator;
    return this;
  }

  public Boolean getDrugIndicator() {
    return drugIndicator;
  }

  public ReferralClientResourceBuilder setDrugIndicator(Boolean drugIndicator) {
    this.drugIndicator = drugIndicator;
    return this;
  }



}
