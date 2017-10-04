package gov.ca.cwds.fixture;

import java.math.BigDecimal;

import gov.ca.cwds.rest.api.domain.cms.Assignment;

/**
 * @author CWDS API Team
 *
 */
@SuppressWarnings("javadoc")
public class AssignmentResourceBuilder {
  private String countySpecificCode = "20";
  private String endDate = "2018-06-01";
  private String endTime = "12:01:00";
  private String establishedForCode = "R";
  private String establishedForId = "1234567ABC";
  private String caseLoadId = "2345678ABC";
  private String outOfStateContactId = "";
  private String id = "SlCAr46088";
  private String responsibilityDescription = "Assignment responsibility description";
  private Short secondaryAssignmentRoleType = 0;
  private String startDate = "2017-06-20";
  private String startTime = "16:41:49";
  private String typeOfAssignmentCode = "P";
  private BigDecimal weightingNumber = new BigDecimal("0.0");
  private String staffId = "0X5";

  /**
   * @return the Assignment
   */
  public Assignment buildAssignment() {
    return new Assignment(countySpecificCode, endDate, endTime, establishedForCode,
        establishedForId, caseLoadId, outOfStateContactId, responsibilityDescription,
        secondaryAssignmentRoleType, startDate, startTime, typeOfAssignmentCode, weightingNumber);
  }

  public String getCountySpecificCode() {
    return countySpecificCode;
  }

  public AssignmentResourceBuilder setCountySpecificCode(String countySpecificCode) {
    this.countySpecificCode = countySpecificCode;
    return this;
  }

  public String getEndDate() {
    return endDate;
  }

  public AssignmentResourceBuilder setEndDate(String endDate) {
    this.endDate = endDate;
    return this;
  }

  public String getEndTime() {
    return endTime;
  }

  public AssignmentResourceBuilder setEndTime(String endTime) {
    this.endTime = endTime;
    return this;
  }

  public String getEstablishedForCode() {
    return establishedForCode;
  }

  public AssignmentResourceBuilder setEstablishedForCode(String establishedForCode) {
    this.establishedForCode = establishedForCode;
    return this;
  }

  public String getEstablishedForId() {
    return establishedForId;
  }

  public AssignmentResourceBuilder setEstablishedForId(String establishedForId) {
    this.establishedForId = establishedForId;
    return this;
  }

  public String getCaseLoadId() {
    return caseLoadId;
  }

  public AssignmentResourceBuilder setCaseLoadId(String caseLoadId) {
    this.caseLoadId = caseLoadId;
    return this;
  }

  public String getOutOfStateContactId() {
    return outOfStateContactId;
  }

  public AssignmentResourceBuilder setOutOfStateContactId(String outOfStateContactId) {
    this.outOfStateContactId = outOfStateContactId;
    return this;
  }

  public String getId() {
    return id;
  }

  public AssignmentResourceBuilder setId(String id) {
    this.id = id;
    return this;
  }

  public String getResponsibilityDescription() {
    return responsibilityDescription;
  }

  public AssignmentResourceBuilder setResponsibilityDescription(String responsibilityDescription) {
    this.responsibilityDescription = responsibilityDescription;
    return this;
  }

  public Short getSecondaryAssignmentRoleType() {
    return secondaryAssignmentRoleType;
  }

  public AssignmentResourceBuilder setSecondaryAssignmentRoleType(
      Short secondaryAssignmentRoleType) {
    this.secondaryAssignmentRoleType = secondaryAssignmentRoleType;
    return this;
  }

  public String getStartDate() {
    return startDate;
  }

  public AssignmentResourceBuilder setStartDate(String startDate) {
    this.startDate = startDate;
    return this;
  }

  public String getStartTime() {
    return startTime;
  }

  public AssignmentResourceBuilder setStartTime(String startTime) {
    this.startTime = startTime;
    return this;
  }

  public String getTypeOfAssignmentCode() {
    return typeOfAssignmentCode;
  }

  public AssignmentResourceBuilder setTypeOfAssignmentCode(String typeOfAssignmentCode) {
    this.typeOfAssignmentCode = typeOfAssignmentCode;
    return this;
  }

  public BigDecimal getWeightingNumber() {
    return weightingNumber;
  }

  public AssignmentResourceBuilder setWeightingNumber(BigDecimal weightingNumber) {
    this.weightingNumber = weightingNumber;
    return this;
  }

  public String getStaffId() {
    return staffId;
  }

  public AssignmentResourceBuilder setStaffId(String staffId) {
    this.staffId = staffId;
    return this;
  }

}
