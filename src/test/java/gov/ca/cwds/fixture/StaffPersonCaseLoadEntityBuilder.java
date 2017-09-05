package gov.ca.cwds.fixture;

import java.util.Date;

import gov.ca.cwds.data.persistence.cms.StaffPersonCaseLoad;

/**
 * @author CWDS API Team
 *
 */
@SuppressWarnings("javadoc")
public class StaffPersonCaseLoadEntityBuilder {

  String countyCode = "99";
  Date endDate = new Date();
  String fkCaseLoad = "ABC1234567";
  String fkStaffPerson = "q1p";
  Date startDate = new Date();
  String thirdId = "MLazRFR00E";

  public StaffPersonCaseLoad build() {
    return new StaffPersonCaseLoad(countyCode, endDate, fkCaseLoad, fkStaffPerson, startDate,
        thirdId);
  }

  public String getCountyCode() {
    return countyCode;
  }

  public StaffPersonCaseLoadEntityBuilder setCountyCode(String countyCode) {
    this.countyCode = countyCode;
    return this;
  }

  public Date getEndDate() {
    return endDate;
  }

  public StaffPersonCaseLoadEntityBuilder setEndDate(Date endDate) {
    this.endDate = endDate;
    return this;
  }

  public String getFkCaseLoad() {
    return fkCaseLoad;
  }

  public StaffPersonCaseLoadEntityBuilder setFkCaseLoad(String fkCaseLoad) {
    this.fkCaseLoad = fkCaseLoad;
    return this;
  }

  public String getFkStaffPerson() {
    return fkStaffPerson;
  }

  public StaffPersonCaseLoadEntityBuilder setFkStaffPerson(String fkStaffPerson) {
    this.fkStaffPerson = fkStaffPerson;
    return this;
  }

  public Date getStartDate() {
    return startDate;
  }

  public StaffPersonCaseLoadEntityBuilder setStartDate(Date startDate) {
    this.startDate = startDate;
    return this;
  }

  public String getThirdId() {
    return thirdId;
  }

  public StaffPersonCaseLoadEntityBuilder setThirdId(String thirdId) {
    this.thirdId = thirdId;
    return this;
  }

}
