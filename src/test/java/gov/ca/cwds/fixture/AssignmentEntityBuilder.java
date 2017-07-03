package gov.ca.cwds.fixture;

import java.math.BigDecimal;

import gov.ca.cwds.data.persistence.cms.Assignment;
import gov.ca.cwds.rest.api.domain.DomainChef;

@SuppressWarnings("javadoc")
public class AssignmentEntityBuilder {
  private String countySpecificCode = "20";
  private String endDate = "2018-06-01";
  private String endTime = "12:01:00";
  private String establishedForCode = "R";
  private String establishedForId = "1234567ABC";
  private String caseLoadId = "2345678ABC";
  private String outOfStatePartyContactId = "";
  private String id = "3456789ABC";
  private String responsiblityDescription = "Assignment responsibility description";
  private Short secondaryAssignmentRoleType = 0;
  private String startDate = "2017-06-20";
  private String startTime = "16:41:49";
  private String typeOfAssignmentCode = "P";
  private BigDecimal weightingNumber = new BigDecimal("0.0");
  private String staffId = "0X5";

  public Assignment create() {
    return new Assignment(id, countySpecificCode, DomainChef.uncookDateString(endDate),
        DomainChef.uncookTimeString(endTime), establishedForCode, establishedForId, caseLoadId,
        outOfStatePartyContactId, responsiblityDescription, secondaryAssignmentRoleType,
        DomainChef.uncookDateString(startDate), DomainChef.uncookTimeString(startTime),
        typeOfAssignmentCode, weightingNumber);
  }

  public String getCountySpecificCode() {
    return countySpecificCode;
  }

  public AssignmentEntityBuilder setCountySpecificCode(String countySpecificCode) {
    this.countySpecificCode = countySpecificCode;
    return this;
  }

  public String getEndDate() {
    return endDate;
  }

  public AssignmentEntityBuilder setEndDate(String endDate) {
    this.endDate = endDate;
    return this;
  }

  public String getEndTime() {
    return endTime;
  }

  public AssignmentEntityBuilder setEndTime(String endTime) {
    this.endTime = endTime;
    return this;
  }

  public String getEstablishedForCode() {
    return establishedForCode;
  }

  public AssignmentEntityBuilder setEstablishedForCode(String establishedForCode) {
    this.establishedForCode = establishedForCode;
    return this;
  }

  public String getEstablishedForId() {
    return establishedForId;
  }

  public AssignmentEntityBuilder setEstablishedForId(String establishedForId) {
    this.establishedForId = establishedForId;
    return this;
  }

  public String getCaseLoadId() {
    return caseLoadId;
  }

  public AssignmentEntityBuilder setCaseLoadId(String caseLoadId) {
    this.caseLoadId = caseLoadId;
    return this;
  }

  public String getOutOfStatePartyContactId() {
    return outOfStatePartyContactId;
  }

  public AssignmentEntityBuilder setOutOfStatePartyContactId(String outOfStatePartyContactId) {
    this.outOfStatePartyContactId = outOfStatePartyContactId;
    return this;
  }

  public String getId() {
    return id;
  }

  public AssignmentEntityBuilder setId(String id) {
    this.id = id;
    return this;
  }

  public String getResponsiblityDescription() {
    return responsiblityDescription;
  }

  public AssignmentEntityBuilder setResponsiblityDescription(String responsiblityDescription) {
    this.responsiblityDescription = responsiblityDescription;
    return this;
  }

  public Short getSecondaryAssignmentRoleType() {
    return secondaryAssignmentRoleType;
  }

  public AssignmentEntityBuilder setSecondaryAssignmentRoleType(Short secondaryAssignmentRoleType) {
    this.secondaryAssignmentRoleType = secondaryAssignmentRoleType;
    return this;
  }

  public String getStartDate() {
    return startDate;
  }

  public AssignmentEntityBuilder setStartDate(String startDate) {
    this.startDate = startDate;
    return this;
  }

  public String getStartTime() {
    return startTime;
  }

  public AssignmentEntityBuilder setStartTime(String startTime) {
    this.startTime = startTime;
    return this;
  }

  public String getTypeOfAssignmentCode() {
    return typeOfAssignmentCode;
  }

  public AssignmentEntityBuilder setTypeOfAssignmentCode(String typeOfAssignmentCode) {
    this.typeOfAssignmentCode = typeOfAssignmentCode;
    return this;
  }

  public BigDecimal getWeightingNumber() {
    return weightingNumber;
  }

  public AssignmentEntityBuilder setWeightingNumber(BigDecimal weightingNumber) {
    this.weightingNumber = weightingNumber;
    return this;
  }

  public String getStaffId() {
    return staffId;
  }

  public AssignmentEntityBuilder setStaffId(String staffId) {
    this.staffId = staffId;
    return this;
  }

}
