package gov.ca.cwds.data.persistence.cms;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * {@link CmsPersistentObject} class representing a Case Load Entity
 * 
 * @author CWDS API Team
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "CASE_LDT")
@JsonPropertyOrder(alphabetic = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CaseLoad extends CmsPersistentObject {

  @Id
  @Column(name = "IDENTIFIER", length = CMS_ID_LEN)
  private String id;

  @NotNull
  @Size(max = 1)
  @Column(name = "ARCASS_IND")
  private String archiveAssociationIndicator;

  @NotNull
  @Size(min = 2, max = 2)
  @Column(name = "CNTY_SPFCD")
  private String countySpecificCode;

  @NotNull
  @Size(max = 1)
  @Column(name = "CASELD_B")
  private String caseLoadIndicatorVariable;

  @NotNull
  @Column(name = "FKASG_UNIT", length = CMS_ID_LEN)
  private String fkAssignmentUnit;

  @Type(type = "date")
  @Column(name = "START_DT")
  private Date startDate;

  @NotNull
  @Size(max = 1)
  @Column(name = "ON_HLD_IND")
  private String onHoldIndicator;

  @NotNull
  @Size(min = 1, max = 30)
  @Column(name = "IDENTFR_NM")
  private String identifierName;

  @Type(type = "date")
  @Column(name = "END_DT")
  private Date endDate;

  @NotNull
  @Column(name = "CLD_CEILNO")
  private BigDecimal ceilingNumber;

  @NotNull
  @Size(max = 1)
  @Column(name = "ASGDSK_IND")
  private String assignmentDeskCaseLoadIndicator;

  /**
   * Default constructor
   * 
   * Required for Hibernate
   */
  public CaseLoad() {
    super();
  }

  /**
   * @param id the Identifier
   * @param archiveAssociationIndicator the archive Association Indicator
   * @param countySpecificCode the county Specific Code
   * @param caseLoadIndicatorVariable the case Load Indicator Variable
   * @param fkAssignmentUnit the foreign key Assignment Unit
   * @param startDate the start Date
   * @param onHoldIndicator the on Hold Indicator
   * @param identifierName the identifier Name
   * @param endDate the endDate
   * @param ceilingNumber the ceiling Number
   * @param assignmentDeskCaseLoadIndicator the assignment Desk Case Load Indicator
   */
  public CaseLoad(String id, String archiveAssociationIndicator, String countySpecificCode,
      String caseLoadIndicatorVariable, String fkAssignmentUnit, Date startDate,
      String onHoldIndicator, String identifierName, Date endDate, BigDecimal ceilingNumber,
      String assignmentDeskCaseLoadIndicator) {
    super();
    this.id = id;
    this.archiveAssociationIndicator = archiveAssociationIndicator;
    this.countySpecificCode = countySpecificCode;
    this.caseLoadIndicatorVariable = caseLoadIndicatorVariable;
    this.fkAssignmentUnit = fkAssignmentUnit;
    this.startDate = startDate;
    this.onHoldIndicator = onHoldIndicator;
    this.identifierName = identifierName;
    this.endDate = endDate;
    this.ceilingNumber = ceilingNumber;
    this.assignmentDeskCaseLoadIndicator = assignmentDeskCaseLoadIndicator;
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.data.persistence.PersistentObject#getPrimaryKey()
   */
  @Override
  public Serializable getPrimaryKey() {
    return getId();
  }

  /**
   * @return the identifer
   */
  public String getId() {
    return id;
  }

  /**
   * @return the archiveAssociationIndicator
   */
  public String getArchiveAssociationIndicator() {
    return archiveAssociationIndicator;
  }

  /**
   * @return the countySpecificCode
   */
  public String getCountySpecificCode() {
    return countySpecificCode;
  }

  /**
   * @return the caseLoadIndicatorVariable
   */
  public String getCaseLoadIndicatorVariable() {
    return caseLoadIndicatorVariable;
  }

  /**
   * @return the fkAssignmentUnit
   */
  public String getFkAssignmentUnit() {
    return fkAssignmentUnit;
  }

  /**
   * @return the startDate
   */
  public Date getStartDate() {
    return startDate;
  }

  /**
   * @return the onHoldIndicator
   */
  public String getOnHoldIndicator() {
    return onHoldIndicator;
  }

  /**
   * @return the identifierName
   */
  public String getIdentifierName() {
    return identifierName;
  }

  /**
   * @return the endDate
   */
  public Date getEndDate() {
    return endDate;
  }

  /**
   * @return the ceilingNumber
   */
  public BigDecimal getCeilingNumber() {
    return ceilingNumber;
  }

  /**
   * @return the assignmentDeskCaseLoadIndicator
   */
  public String getAssignmentDeskCaseLoadIndicator() {
    return assignmentDeskCaseLoadIndicator;
  }

}
