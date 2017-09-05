package gov.ca.cwds.fixture;

import java.math.BigDecimal;
import java.util.Date;

import gov.ca.cwds.data.persistence.cms.CaseLoad;

/**
 * @author CWDS API Team
 *
 */
@SuppressWarnings("javadoc")
public class CaseLoadEntityBuilder {

  String id = "ABC1234567";
  String archiveAssociationIndicator = "N";
  String countySpecificCode = "99";
  String caseLoadIndicatorVariable = "N";
  String fkAssignmentUnit = "1234567ABC";
  Date startDate = new Date();
  String onHoldIndicator = "N";
  String identifierName = "In-Box CaseLoad";
  Date endDate = new Date();
  BigDecimal ceilingNumber = new BigDecimal("0.0");
  String assignmentDeskCaseLoadIndicator = "N";

  public CaseLoad build() {
    return new CaseLoad(id, archiveAssociationIndicator, countySpecificCode,
        caseLoadIndicatorVariable, fkAssignmentUnit, startDate, onHoldIndicator, identifierName,
        endDate, ceilingNumber, assignmentDeskCaseLoadIndicator);
  }

  public String getId() {
    return id;
  }

  public CaseLoadEntityBuilder setId(String id) {
    this.id = id;
    return this;
  }

  public String getArchiveAssociationIndicator() {
    return archiveAssociationIndicator;
  }

  public CaseLoadEntityBuilder setArchiveAssociationIndicator(String archiveAssociationIndicator) {
    this.archiveAssociationIndicator = archiveAssociationIndicator;
    return this;
  }

  public String getCountySpecificCode() {
    return countySpecificCode;
  }

  public CaseLoadEntityBuilder setCountySpecificCode(String countySpecificCode) {
    this.countySpecificCode = countySpecificCode;
    return this;
  }

  public String getCaseLoadIndicatorVariable() {
    return caseLoadIndicatorVariable;
  }

  public CaseLoadEntityBuilder setCaseLoadIndicatorVariable(String caseLoadIndicatorVariable) {
    this.caseLoadIndicatorVariable = caseLoadIndicatorVariable;
    return this;
  }

  public String getFkAssignmentUnit() {
    return fkAssignmentUnit;
  }

  public CaseLoadEntityBuilder setFkAssignmentUnit(String fkAssignmentUnit) {
    this.fkAssignmentUnit = fkAssignmentUnit;
    return this;
  }

  public Date getStartDate() {
    return startDate;
  }

  public CaseLoadEntityBuilder setStartDate(Date startDate) {
    this.startDate = startDate;
    return this;
  }

  public String getOnHoldIndicator() {
    return onHoldIndicator;
  }

  public CaseLoadEntityBuilder setOnHoldIndicator(String onHoldIndicator) {
    this.onHoldIndicator = onHoldIndicator;
    return this;
  }

  public String getIdentifierName() {
    return identifierName;
  }

  public CaseLoadEntityBuilder setIdentifierName(String identifierName) {
    this.identifierName = identifierName;
    return this;
  }

  public Date getEndDate() {
    return endDate;
  }

  public CaseLoadEntityBuilder setEndDate(Date endDate) {
    this.endDate = endDate;
    return this;
  }

  public BigDecimal getCeilingNumber() {
    return ceilingNumber;
  }

  public CaseLoadEntityBuilder setCeilingNumber(BigDecimal ceilingNumber) {
    this.ceilingNumber = ceilingNumber;
    return this;
  }

  public String getAssignmentDeskCaseLoadIndicator() {
    return assignmentDeskCaseLoadIndicator;
  }

  public CaseLoadEntityBuilder setAssignmentDeskCaseLoadIndicator(
      String assignmentDeskCaseLoadIndicator) {
    this.assignmentDeskCaseLoadIndicator = assignmentDeskCaseLoadIndicator;
    return this;
  }

}
