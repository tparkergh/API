package gov.ca.cwds.data.persistence.cms;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * {@link CmsPersistentObject} Class representing a Case.
 * 
 * @author CWDS API Team
 */
@Entity
@Table(name = "CASE_T")
@JsonPropertyOrder(alphabetic = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CmsCase extends CmsPersistentObject {

  /**
   * Default serialization.
   */
  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "IDENTIFIER", length = CMS_ID_LEN)
  private String id;

  @Column(name = "ALERT_TXT")
  private String alertText;

  @Column(name = "APRVL_NO")
  private String approvalNumber;

  @Column(name = "APV_STC")
  private String approvalStatusType;

  @Type(type = "short")
  @Column(name = "CLS_RSNC")
  private Short caseClosureReasonType;

  @Column(name = "CSPL_DET_B")
  private String caseplanChildrenDetailIndVar;

  @Column(name = "CL_STM_TXT")
  private String closureStatementText;

  @Type(type = "short")
  @Column(name = "CNTRY_C")
  private Short countryCodeType;

  @Column(name = "CNTY_SPFCD")
  private String countySpecificCode;

  @Column(name = "NOTES_DOC")
  private String drmsNotesDoc;

  @Type(type = "date")
  @Column(name = "EMANCPN_DT")
  private String emancipationDate;

  @Type(type = "date")
  @Column(name = "END_DT")
  private Date endDate;

  @Column(name = "FKCHLD_CLT")
  private String fkchldClt;

  @Column(name = "FKREFERL_T")
  private String fkreferlt;

  @Column(name = "FKSTFPERST")
  private String fkstfperst;

  @Type(type = "short")
  @Column(name = "GVR_ENTC")
  private Short governmentEntityType;

  @Column(name = "ICPCSTAT_B")
  private String icpcOutgngPlcmtStatusIndVar;

  @Column(name = "ICPC_RQT_B")
  private String icpcOutgoingRequestIndVar;

  @Column(name = "LMT_ACSSCD")
  private String limitedAccessCode;

  @Type(type = "date")
  @Column(name = "LMT_ACS_DT")
  private Date limitedAccessDate;

  @Column(name = "LMT_ACSDSC")
  private String limitedAccessDesc;

  @Type(type = "short")
  @Column(name = "L_GVR_ENTC")
  private Short limitedAccessGovernmentEntityType;

  @Column(name = "CASE_NM")
  private String caseName;

  @Column(name = "NXT_TILPDT")
  private String nextTilpDueDate;

  @Type(type = "date")
  @Column(name = "PRJ_END_DT")
  private Date projectedEndDate;

  @Column(name = "RSP_AGY_CD")
  private String responsibleAgencyCode;

  @Column(name = "SPRJ_CST_B")
  private String specialProjectCaseIndVar;

  @Type(type = "date")
  @Column(name = "START_DT")
  private Date startDate;

  @Type(type = "short")
  @Column(name = "STATE_C")
  private Short stateCodeType;

  @Type(type = "short")
  @Column(name = "SRV_CMPC")
  private Short activeServiceComponentType;

  @Type(type = "date")
  @Column(name = "SRV_CMPDT")
  private Date activeSvcComponentStartDate;

  @Column(name = "TICKLE_T_B")
  private String tickleIndVar;

  /**
   * Default constructor.
   */
  public CmsCase() {
    super();
  }

  @Override
  public Serializable getPrimaryKey() {
    return getId();
  }

  public String getId() {
    return this.id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getAlertText() {
    return alertText;
  }

  public void setAlertText(String alertText) {
    this.alertText = alertText;
  }

  public String getApprovalNumber() {
    return approvalNumber;
  }

  public void setApprovalNumber(String approvalNumber) {
    this.approvalNumber = approvalNumber;
  }

  public String getApprovalStatusType() {
    return approvalStatusType;
  }

  public void setApprovalStatusType(String approvalStatusType) {
    this.approvalStatusType = approvalStatusType;
  }

  public Short getCaseClosureReasonType() {
    return caseClosureReasonType;
  }

  public void setCaseClosureReasonType(Short caseClosureReasonType) {
    this.caseClosureReasonType = caseClosureReasonType;
  }

  public String getCaseplanChildrenDetailIndVar() {
    return caseplanChildrenDetailIndVar;
  }

  public void setCaseplanChildrenDetailIndVar(String caseplanChildrenDetailIndVar) {
    this.caseplanChildrenDetailIndVar = caseplanChildrenDetailIndVar;
  }

  public String getClosureStatementText() {
    return closureStatementText;
  }

  public void setClosureStatementText(String closureStatementText) {
    this.closureStatementText = closureStatementText;
  }

  public Short getCountryCodeType() {
    return countryCodeType;
  }

  public void setCountryCodeType(Short countryCodeType) {
    this.countryCodeType = countryCodeType;
  }

  public String getCountySpecificCode() {
    return countySpecificCode;
  }

  public void setCountySpecificCode(String countySpecificCode) {
    this.countySpecificCode = countySpecificCode;
  }

  public String getDrmsNotesDoc() {
    return drmsNotesDoc;
  }

  public void setDrmsNotesDoc(String drmsNotesDoc) {
    this.drmsNotesDoc = drmsNotesDoc;
  }

  public String getEmancipationDate() {
    return emancipationDate;
  }

  public void setEmancipationDate(String emancipationDate) {
    this.emancipationDate = emancipationDate;
  }

  public Date getEndDate() {
    return endDate;
  }

  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }

  public String getFkchldClt() {
    return fkchldClt;
  }

  public void setFkchldClt(String fkchldClt) {
    this.fkchldClt = fkchldClt;
  }

  public String getFkreferlt() {
    return fkreferlt;
  }

  public void setFkreferlt(String fkreferlt) {
    this.fkreferlt = fkreferlt;
  }

  public String getFkstfperst() {
    return fkstfperst;
  }

  public void setFkstfperst(String fkstfperst) {
    this.fkstfperst = fkstfperst;
  }

  public Short getGovernmentEntityType() {
    return governmentEntityType;
  }

  public void setGovernmentEntityType(Short governmentEntityType) {
    this.governmentEntityType = governmentEntityType;
  }

  public String getIcpcOutgngPlcmtStatusIndVar() {
    return icpcOutgngPlcmtStatusIndVar;
  }

  public void setIcpcOutgngPlcmtStatusIndVar(String icpcOutgngPlcmtStatusIndVar) {
    this.icpcOutgngPlcmtStatusIndVar = icpcOutgngPlcmtStatusIndVar;
  }

  public String getIcpcOutgoingRequestIndVar() {
    return icpcOutgoingRequestIndVar;
  }

  public void setIcpcOutgoingRequestIndVar(String icpcOutgoingRequestIndVar) {
    this.icpcOutgoingRequestIndVar = icpcOutgoingRequestIndVar;
  }

  public String getLimitedAccessCode() {
    return limitedAccessCode;
  }

  public void setLimitedAccessCode(String limitedAccessCode) {
    this.limitedAccessCode = limitedAccessCode;
  }

  public Date getLimitedAccessDate() {
    return limitedAccessDate;
  }

  public void setLimitedAccessDate(Date limitedAccessDate) {
    this.limitedAccessDate = limitedAccessDate;
  }

  public String getLimitedAccessDesc() {
    return limitedAccessDesc;
  }

  public void setLimitedAccessDesc(String limitedAccessDesc) {
    this.limitedAccessDesc = limitedAccessDesc;
  }

  public Short getLimitedAccessGovernmentEntityType() {
    return limitedAccessGovernmentEntityType;
  }

  public void setLimitedAccessGovernmentEntityType(Short limitedAccessGovernmentEntityType) {
    this.limitedAccessGovernmentEntityType = limitedAccessGovernmentEntityType;
  }

  public String getCaseName() {
    return caseName;
  }

  public void setCaseName(String caseName) {
    this.caseName = caseName;
  }

  public String getNextTilpDueDate() {
    return nextTilpDueDate;
  }

  public void setNextTilpDueDate(String nextTilpDueDate) {
    this.nextTilpDueDate = nextTilpDueDate;
  }

  public Date getProjectedEndDate() {
    return projectedEndDate;
  }

  public void setProjectedEndDate(Date projectedEndDate) {
    this.projectedEndDate = projectedEndDate;
  }

  public String getResponsibleAgencyCode() {
    return responsibleAgencyCode;
  }

  public void setResponsibleAgencyCode(String responsibleAgencyCode) {
    this.responsibleAgencyCode = responsibleAgencyCode;
  }

  public String getSpecialProjectCaseIndVar() {
    return specialProjectCaseIndVar;
  }

  public void setSpecialProjectCaseIndVar(String specialProjectCaseIndVar) {
    this.specialProjectCaseIndVar = specialProjectCaseIndVar;
  }

  public Date getStartDate() {
    return startDate;
  }

  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }

  public Short getStateCodeType() {
    return stateCodeType;
  }

  public void setStateCodeType(Short stateCodeType) {
    this.stateCodeType = stateCodeType;
  }

  public Short getActiveServiceComponentType() {
    return activeServiceComponentType;
  }

  public void setActiveServiceComponentType(Short activeServiceComponentType) {
    this.activeServiceComponentType = activeServiceComponentType;
  }

  public Date getActiveSvcComponentStartDate() {
    return activeSvcComponentStartDate;
  }

  public void setActiveSvcComponentStartDate(Date activeSvcComponentStartDate) {
    this.activeSvcComponentStartDate = activeSvcComponentStartDate;
  }

  public String getTickleIndVar() {
    return tickleIndVar;
  }

  public void setTickleIndVar(String tickleIndVar) {
    this.tickleIndVar = tickleIndVar;
  }

}
