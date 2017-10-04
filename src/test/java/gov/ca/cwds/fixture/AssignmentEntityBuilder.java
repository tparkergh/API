package gov.ca.cwds.fixture;

import java.math.BigDecimal;
import java.util.Date;

import gov.ca.cwds.data.persistence.cms.Assignment;

/**
 * @author CWDS API Team
 *
 */
@SuppressWarnings("javadoc")
public class AssignmentEntityBuilder {

  String id = "SlCAr46088";
  String countySpecificCode = "99";
  Date endDate = new Date();
  Date endTime = new Date();
  String establishedForCode = "C";
  String establishedForId = "0iiVVuE088";
  String fkCaseLoad = "Aci1N2I00E";
  String fkOutOfStateContactParty = null;
  String responsibilityDescription = "some kind of descrption";
  Short secondaryAssignmentRoleType = (short) 0;
  Date startDate = new Date();
  Date startTime = new Date();
  String typeOfAssignmentCode = "P";
  BigDecimal weightingNumber = new BigDecimal(0.00);

  public Assignment build() {
    return new Assignment(id, countySpecificCode, endDate, endTime, establishedForCode,
        establishedForId, fkCaseLoad, fkOutOfStateContactParty, responsibilityDescription,
        secondaryAssignmentRoleType, startDate, startTime, typeOfAssignmentCode, weightingNumber);
  }

  public String getId() {
    return id;
  }

  public AssignmentEntityBuilder setId(String id) {
    this.id = id;
    return this;
  }

  public String getCountySpecificCode() {
    return countySpecificCode;
  }

  public AssignmentEntityBuilder setCountySpecificCode(String countySpecificCode) {
    this.countySpecificCode = countySpecificCode;
    return this;
  }

  public Date getEndDate() {
    return endDate;
  }

  public AssignmentEntityBuilder setEndDate(Date endDate) {
    this.endDate = endDate;
    return this;
  }

  public Date getEndTime() {
    return endTime;
  }

  public AssignmentEntityBuilder setEndTime(Date endTime) {
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

  public String getFkCaseLoad() {
    return fkCaseLoad;
  }

  public AssignmentEntityBuilder setFkCaseLoad(String fkCaseLoad) {
    this.fkCaseLoad = fkCaseLoad;
    return this;
  }

  public String getFkOutOfStateContactParty() {
    return fkOutOfStateContactParty;
  }

  public AssignmentEntityBuilder setFkOutOfStateContactParty(String fkOutOfStateContactParty) {
    this.fkOutOfStateContactParty = fkOutOfStateContactParty;
    return this;
  }

  public String getResponsibilityDescription() {
    return responsibilityDescription;
  }

  public AssignmentEntityBuilder setResponsibilityDescription(String responsibilityDescription) {
    this.responsibilityDescription = responsibilityDescription;
    return this;
  }

  public Short getSecondaryAssignmentRoleType() {
    return secondaryAssignmentRoleType;
  }

  public AssignmentEntityBuilder setSecondaryAssignmentRoleType(Short secondaryAssignmentRoleType) {
    this.secondaryAssignmentRoleType = secondaryAssignmentRoleType;
    return this;
  }

  public Date getStartDate() {
    return startDate;
  }

  public AssignmentEntityBuilder setStartDate(Date startDate) {
    this.startDate = startDate;
    return this;
  }

  public Date getStartTime() {
    return startTime;
  }

  public AssignmentEntityBuilder setStartTime(Date startTime) {
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

}
