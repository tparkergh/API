package gov.ca.cwds.fixture;

import com.fasterxml.jackson.annotation.JsonProperty;
import gov.ca.cwds.rest.api.domain.cms.CrossReport;
import java.math.BigDecimal;

public class CrossReportEntityBuilder {
      String thirdId = "";
      Short crossReportMethodType = 0;
      Boolean filedOutOfStateIndicator = false;
      Boolean governmentOrgCrossRptIndicatorVar = false;
      String informTime = "";
      String recipientBadgeNumber = "";
      Integer recipientPhoneExtensionNumber = 0;
      BigDecimal recipientPhoneNumber = new BigDecimal(0);
      String informDate = "";
      String recipientPositionTitleDesc = "";
      String referenceNumber = "";
      String referralId = "";
      String lawEnforcementId = "";
      String staffPersonId = "";
      String description = "";
      String recipientName = "";
      String outStateLawEnforcementAddr = "";
      String countySpecificCode = "";
      Boolean lawEnforcementIndicator = false;
      Boolean outStateLawEnforcementIndicator = false;
      Boolean satisfyCrossReportIndicator = false;

  public CrossReportEntityBuilder setThirdId(String thirdId) {
    this.thirdId = thirdId;
    return this;
  }

  public CrossReportEntityBuilder setCrossReportMethodType(Short crossReportMethodType) {
    this.crossReportMethodType = crossReportMethodType;
    return this;
  }

  public CrossReportEntityBuilder setFiledOutOfStateIndicator(Boolean filedOutOfStateIndicator) {
    this.filedOutOfStateIndicator = filedOutOfStateIndicator;
    return this;
  }

  public CrossReportEntityBuilder setGovernmentOrgCrossRptIndicatorVar(
      Boolean governmentOrgCrossRptIndicatorVar) {
    this.governmentOrgCrossRptIndicatorVar = governmentOrgCrossRptIndicatorVar;
    return this;
  }

  public CrossReportEntityBuilder setInformTime(String informTime) {
    this.informTime = informTime;
    return this;
  }

  public CrossReportEntityBuilder setRecipientBadgeNumber(String recipientBadgeNumber) {
    this.recipientBadgeNumber = recipientBadgeNumber;
    return this;
  }

  public CrossReportEntityBuilder setRecipientPhoneExtensionNumber(Integer recipientPhoneExtensionNumber) {
    this.recipientPhoneExtensionNumber = recipientPhoneExtensionNumber;
    return this;
  }

  public CrossReportEntityBuilder setRecipientPhoneNumber(BigDecimal recipientPhoneNumber) {
    this.recipientPhoneNumber = recipientPhoneNumber;
    return this;
  }

  public CrossReportEntityBuilder setInformDate(String informDate) {
    this.informDate = informDate;
    return this;
  }

  public CrossReportEntityBuilder setRecipientPositionTitleDesc(String recipientPositionTitleDesc) {
    this.recipientPositionTitleDesc = recipientPositionTitleDesc;
    return this;
  }

  public CrossReportEntityBuilder setReferenceNumber(String referenceNumber) {
    this.referenceNumber = referenceNumber;
    return this;
  }

  public CrossReportEntityBuilder setReferralId(String referralId) {
    this.referralId = referralId;
    return this;
  }

  public CrossReportEntityBuilder setLawEnforcementId(String lawEnforcementId) {
    this.lawEnforcementId = lawEnforcementId;
    return this;
  }

  public CrossReportEntityBuilder setStaffPersonId(String staffPersonId) {
    this.staffPersonId = staffPersonId;
    return this;
  }

  public CrossReportEntityBuilder setDescription(String description) {
    this.description = description;
    return this;
  }

  public CrossReportEntityBuilder setRecipientName(String recipientName) {
    this.recipientName = recipientName;
    return this;
  }

  public CrossReportEntityBuilder setOutStateLawEnforcementAddr(String outStateLawEnforcementAddr) {
    this.outStateLawEnforcementAddr = outStateLawEnforcementAddr;
    return this;
  }

  public CrossReportEntityBuilder setCountySpecificCode(String countySpecificCode) {
    this.countySpecificCode = countySpecificCode;
    return this;
  }

  public CrossReportEntityBuilder setLawEnforcementIndicator(Boolean lawEnforcementIndicator) {
    this.lawEnforcementIndicator = lawEnforcementIndicator;
    return this;
  }

  public CrossReportEntityBuilder setOutStateLawEnforcementIndicator(Boolean outStateLawEnforcementIndicator) {
    this.outStateLawEnforcementIndicator = outStateLawEnforcementIndicator;
    return this;
  }

  public CrossReportEntityBuilder setSatisfyCrossReportIndicator(Boolean satisfyCrossReportIndicator) {
    this.satisfyCrossReportIndicator = satisfyCrossReportIndicator;
    return this;
  }

  public CrossReport build() {
      return new CrossReport(
          thirdId,
          crossReportMethodType,
          filedOutOfStateIndicator,
          governmentOrgCrossRptIndicatorVar,
          informTime,
          recipientBadgeNumber,
          recipientPhoneExtensionNumber,
          recipientPhoneNumber,
          informDate,
          recipientPositionTitleDesc,
          referenceNumber,
          referralId,
          lawEnforcementId,
          staffPersonId,
          description,
          recipientName,
          outStateLawEnforcementAddr,
          countySpecificCode,
          lawEnforcementIndicator,
          outStateLawEnforcementIndicator,
          satisfyCrossReportIndicator);
    }

}
