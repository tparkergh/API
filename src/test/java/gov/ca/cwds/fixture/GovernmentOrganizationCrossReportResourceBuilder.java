package gov.ca.cwds.fixture;

import gov.ca.cwds.rest.api.domain.cms.GovernmentOrganizationCrossReport;

/**
 * @author CWDS API Team
 *
 */
@SuppressWarnings("javadoc")
public class GovernmentOrganizationCrossReportResourceBuilder {

  String thirdId = "AbalBln0Ki";
  String countySpecificCode = "99";
  String crossReportThirdId = "LikGcFD0Ki";
  String referralId = "RI1Wuve0Ki";
  String governmentOrganizationId = "CS90ZBR01c";
  String organizationTypeInd = "D";

  public GovernmentOrganizationCrossReport build() {
    return new GovernmentOrganizationCrossReport(thirdId, countySpecificCode, crossReportThirdId,
        referralId, governmentOrganizationId, organizationTypeInd);
  }

  public String getThirdId() {
    return thirdId;
  }

  public GovernmentOrganizationCrossReportResourceBuilder setThirdId(String thirdId) {
    this.thirdId = thirdId;
    return this;
  }

  public String getCountySpecificCode() {
    return countySpecificCode;
  }

  public GovernmentOrganizationCrossReportResourceBuilder setCountySpecificCode(
      String countySpecificCode) {
    this.countySpecificCode = countySpecificCode;
    return this;
  }

  public String getCrossReportThirdId() {
    return crossReportThirdId;
  }

  public GovernmentOrganizationCrossReportResourceBuilder setCrossReportThirdId(
      String crossReportThirdId) {
    this.crossReportThirdId = crossReportThirdId;
    return this;
  }

  public String getReferralId() {
    return referralId;
  }

  public GovernmentOrganizationCrossReportResourceBuilder setReferralId(String referralId) {
    this.referralId = referralId;
    return this;
  }

  public String getGovernmentOrganizationId() {
    return governmentOrganizationId;
  }

  public GovernmentOrganizationCrossReportResourceBuilder setGovernmentOrganizationId(
      String governmentOrganizationId) {
    this.governmentOrganizationId = governmentOrganizationId;
    return this;
  }

  public String getOrganizationTypeInd() {
    return organizationTypeInd;
  }

  public GovernmentOrganizationCrossReportResourceBuilder setOrganizationTypeInd(
      String organizationTypeInd) {
    this.organizationTypeInd = organizationTypeInd;
    return this;
  }

}
