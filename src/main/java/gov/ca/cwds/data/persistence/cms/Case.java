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
 * {@link CmsPersistentObject} Class representing a Child Client.
 * 
 * @author CWDS API Team
 */
@Entity
@Table(name = "CASE_T")
@JsonPropertyOrder(alphabetic = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Case extends CmsPersistentObject {

  /**
   * Serialization version.
   */
  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "IDENTIFIER", length = CMS_ID_LEN)
  protected String id;

  @Column(name = "ALERT_TXT")
  protected String alertText;

  @Column(name = "APRVL_NO")
  protected String approvalNumber;

  @Column(name = "APV_STC")
  protected String approvalStatusType;

  @Type(type = "short")
  @Column(name = "CLS_RSNC")
  protected Short caseClosureReasonType;

  @Column(name = "CSPL_DET_B")
  protected String caseplanChildrenDetailIndVar;

  @Column(name = "CL_STM_TXT")
  protected String closureStatementText;

  @Type(type = "short")
  @Column(name = "CNTRY_C")
  protected Short countryCodeType;

  @Column(name = "CNTY_SPFCD")
  protected String countySpecificCode;

  @Column(name = "NOTES_DOC")
  protected String drmsNotesDoc;

  @Type(type = "date")
  @Column(name = "EMANCPN_DT")
  protected String emancipationDate;

  @Type(type = "date")
  @Column(name = "END_DT")
  protected Date endDate;

  @Column(name = "FKCHLD_CLT")
  protected String fkchldClt;

  @Column(name = "FKREFERL_T")
  protected String fkreferlt;

  @Column(name = "FKSTFPERST")
  protected String fkstfperst;

  @Type(type = "short")
  @Column(name = "GVR_ENTC")
  protected Short governmentEntityType;

  @Column(name = "ICPCSTAT_B")
  protected String icpcOutgngPlcmtStatusIndVar;

  @Column(name = "ICPC_RQT_B")
  protected String icpcOutgoingRequestIndVar;

  @Column(name = "LMT_ACSSCD")
  protected String limitedAccessCode;

  @Type(type = "date")
  @Column(name = "LMT_ACS_DT")
  protected Date limitedAccessDate;

  @Column(name = "LMT_ACSDSC")
  protected String limitedAccessDesc;

  @Type(type = "short")
  @Column(name = "L_GVR_ENTC")
  protected Short limitedAccessGovernmentEntityType;

  @Column(name = "CASE_NM")
  protected String caseName;

  @Column(name = "NXT_TILPDT")
  protected String nextTilpDueDate;

  @Type(type = "date")
  @Column(name = "PRJ_END_DT")
  protected Date projectedEndDate;

  @Column(name = "RSP_AGY_CD")
  protected String responsibleAgencyCode;

  @Column(name = "SPRJ_CST_B")
  protected String specialProjectCaseIndVar;

  @Type(type = "date")
  @Column(name = "START_DT")
  protected Date startDate;

  @Type(type = "short")
  @Column(name = "STATE_C")
  protected Short stateCodeType;

  @Type(type = "short")
  @Column(name = "SRV_CMPC")
  protected Short activeServiceComponentType;

  @Type(type = "date")
  @Column(name = "SRV_CMPDT")
  protected Date activeSvcComponentStartDate;

  @Column(name = "TICKLE_T_B")
  protected String tickleIndVar;

  /**
   * Default constructor.
   */
  public Case() {

  }

  @Override
  public Serializable getPrimaryKey() {
    return id;
  }

}
