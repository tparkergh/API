package gov.ca.cwds.fixture;

import gov.ca.cwds.rest.api.domain.cms.StaffPersonCaseLoad;

/**
 * @author CWDS API Team
 *
 */
public class StaffPersonCaseLoadResourceBuilder {
  String countySpecificCode = "20";
  String endDate = "";
  String fkCaseLoad = "1234567ABC";
  String fkStaffPerson = "ABC";
  String startDate = "2017-08-29";
  String thirdId = "2345678ABC";

  @SuppressWarnings("javadoc")
  public StaffPersonCaseLoad build() {
    return new StaffPersonCaseLoad(countySpecificCode, endDate, fkCaseLoad, fkStaffPerson,
        startDate, thirdId);
  }

  @SuppressWarnings("javadoc")
  public String getCountySpecificCode() {
    return this.countySpecificCode;
  }

  @SuppressWarnings("javadoc")
  public StaffPersonCaseLoadResourceBuilder setCountySpecificCode(String countySpecificCode) {
    this.countySpecificCode = countySpecificCode;
    return this;
  }

  @SuppressWarnings("javadoc")
  public String getEndDate() {
    return this.endDate;
  }

  @SuppressWarnings("javadoc")
  public StaffPersonCaseLoadResourceBuilder setEndDate(String endDate) {
    this.endDate = endDate;
    return this;
  }

  @SuppressWarnings("javadoc")
  public String getFkCaseLoad() {
    return this.fkCaseLoad;
  }

  @SuppressWarnings("javadoc")
  public StaffPersonCaseLoadResourceBuilder setFkCaseLoad(String fkCaseLoad) {
    this.fkCaseLoad = fkCaseLoad;
    return this;
  }

  @SuppressWarnings("javadoc")
  public String getFkStaffPerson() {
    return this.fkStaffPerson;
  }

  @SuppressWarnings("javadoc")
  public StaffPersonCaseLoadResourceBuilder setStartDate(String startDate) {
    this.startDate = startDate;
    return this;
  }

  @SuppressWarnings("javadoc")
  public String getStartDate() {
    return this.startDate;
  }

  @SuppressWarnings("javadoc")
  public StaffPersonCaseLoadResourceBuilder setFkStaffPerson(String fkStaffPerson) {
    this.fkStaffPerson = fkStaffPerson;
    return this;
  }

  @SuppressWarnings("javadoc")
  public String getThirdId() {
    return this.thirdId;
  }

  @SuppressWarnings("javadoc")
  public StaffPersonCaseLoadResourceBuilder setThirdId(String thirdId) {
    this.thirdId = thirdId;
    return this;
  }
}
