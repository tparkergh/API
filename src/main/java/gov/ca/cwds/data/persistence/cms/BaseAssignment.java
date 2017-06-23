package gov.ca.cwds.data.persistence.cms;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.DiscriminatorFormula;
import org.hibernate.annotations.DiscriminatorOptions;
import org.hibernate.annotations.Type;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import gov.ca.cwds.data.CmsSystemCodeDeserializer;
import gov.ca.cwds.data.SystemCodeSerializer;
import gov.ca.cwds.rest.api.domain.DomainChef;

/**
 * {@link CmsPersistentObject} representing an Assignment for either Case or Referral.
 * 
 * <p>
 * This entity bean wraps the Assignment table's "folded key" FK (virtual FK) on columns ESTBLISH_CD
 * and ESTBLSH_ID to either to Referral or Case, as dictated by ESTBLISH_CD.
 * </p>
 * 
 * @author CWDS API Team
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "ASGNM_T")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorFormula("case when ESTBLSH_CD = 'R' then 'REFERRAL' when ESTBLSH_CD = 'C' then 'CASES' end")
@DiscriminatorOptions(force = true)
public abstract class BaseAssignment extends CmsPersistentObject {

  @Id
  @Column(name = "IDENTIFIER", length = CMS_ID_LEN)
  private String id;

  @Column(name = "ESTBLSH_ID", length = CMS_ID_LEN, insertable = false, updatable = false)
  protected String establishedForId;

  @Column(name = "CNTY_SPFCD", length = 2)
  private String countySpecificCode;

  @Type(type = "date")
  @Column(name = "END_DT")
  private Date endDate;

  @Type(type = "time")
  @Column(name = "END_TM")
  private Date endTime;

  @Column(name = "ESTBLSH_CD", length = 1)
  protected String establishedForCode;

  @Column(name = "FKCASE_LDT", length = CMS_ID_LEN)
  private String fkCaseLoad;

  @Column(name = "FKOST_PTYT", length = CMS_ID_LEN)
  private String fkOutOfStateContactParty;

  @Column(name = "RESPL_DSC", length = 160)
  private String responsibilityDescription;

  @SystemCodeSerializer(logical = true, description = true)
  @JsonDeserialize(using = CmsSystemCodeDeserializer.class)
  @Type(type = "short")
  @Column(name = "ASSG_RLC")
  private Short secondaryAssignmentRoleType;

  @Type(type = "date")
  @Column(name = "START_DT")
  private Date startDate;

  @Type(type = "time")
  @Column(name = "START_TM")
  private Date startTime;

  @Column(name = "ASGNMNT_CD")
  private String typeOfAssignmentCode;

  // @Type(type = "decimal") // NO NO NO! pain ...
  @Column(name = "WGHTNG_NO", length = 5)
  private BigDecimal weightingNumber;

  /**
   * Default constructor. Required for Hibernate
   */
  public BaseAssignment() {
    super();
  }

  /**
   * Construct from all fields.
   * 
   * @param countySpecificCode - county code of case load with this assignment
   * @param endDate - end date of assignment
   * @param endTime - end time of assignment
   * @param establishedForCode - referral or case
   * @param establishedForId - referral or case Id
   * @param fkCaseLoad - foreign key to the case load
   * @param fkOutOfStateContactParty - foreign ky to the out of state contact party
   * @param responsibilityDescription - description
   * @param secondaryAssignmentRoleType - primary or secondary
   * @param startDate - start date of assignment
   * @param startTime - end date of assignment
   * @param typeOfAssignmentCode - primary, secondary, or read only
   * @param weightingNumber - weighting within case load
   */
  public BaseAssignment(String countySpecificCode, Date endDate, Date endTime,
      String establishedForCode, String establishedForId, String fkCaseLoad,
      String fkOutOfStateContactParty, String responsibilityDescription,
      Short secondaryAssignmentRoleType, Date startDate, Date startTime,
      String typeOfAssignmentCode, BigDecimal weightingNumber) {
    super();

    setEstablishedForId(establishedForId);

    this.countySpecificCode = countySpecificCode;
    this.endDate = endDate;
    this.endTime = endTime;
    this.establishedForCode = establishedForCode;
    this.fkCaseLoad = fkCaseLoad;
    this.fkOutOfStateContactParty = fkOutOfStateContactParty;
    this.responsibilityDescription = responsibilityDescription;
    this.secondaryAssignmentRoleType = secondaryAssignmentRoleType;
    this.startDate = startDate;
    this.startTime = startTime;
    this.typeOfAssignmentCode = typeOfAssignmentCode;
    this.weightingNumber = weightingNumber;
  }

  /**
   * Construct from domain class instance.
   * 
   * @param id - Assignment Id
   * @param pa - persisted Assignment object
   * @param lastUpdateId - staff person id
   */
  public BaseAssignment(String id, gov.ca.cwds.rest.api.domain.cms.Assignment pa,
      String lastUpdateId) {
    super(lastUpdateId);
    setEstablishedForId(pa.getEstablishedForId());
    this.id = id;
    this.countySpecificCode = pa.getCountySpecificCode();
    this.endDate = DomainChef.uncookDateString(pa.getEndDate());
    this.endTime = DomainChef.uncookTimeString(pa.getEndTime());
    this.establishedForCode = pa.getEstablishedForCode();
    this.fkCaseLoad = pa.getCaseLoadId();
    this.fkOutOfStateContactParty = pa.getOutOfStateContactId();
    this.responsibilityDescription = pa.getResponsibilityDescription();
    this.secondaryAssignmentRoleType = pa.getSecondaryAssignmentRoleType();
    this.startDate = DomainChef.uncookDateString(pa.getStartDate());
    this.startTime = DomainChef.uncookTimeString(pa.getStartTime());
    this.typeOfAssignmentCode = pa.getTypeOfAssignmentCode();
    this.weightingNumber = pa.getWeightingNumber();
  }

  // ===================
  // ACCESSORS:
  // ===================

  @SuppressWarnings("javadoc")
  public String getCountySpecificCode() {
    return countySpecificCode;
  }

  @SuppressWarnings("javadoc")
  public Date getEndDate() {
    return endDate;
  }

  @SuppressWarnings("javadoc")
  public Date getEndTime() {
    return endTime;
  }

  @SuppressWarnings("javadoc")
  public String getEstablishedForCode() {
    return establishedForCode;
  }

  @SuppressWarnings("javadoc")
  public String getFkCaseLoad() {
    return fkCaseLoad;
  }

  @SuppressWarnings("javadoc")
  public String getFkOutOfStateContactParty() {
    return fkOutOfStateContactParty;
  }

  @SuppressWarnings("javadoc")
  public String getId() {
    return id;
  }

  @SuppressWarnings("javadoc")
  public String getResponsibilityDescription() {
    return responsibilityDescription;
  }

  @SuppressWarnings("javadoc")
  public Short getSecondaryAssignmentRoleType() {
    return secondaryAssignmentRoleType;
  }

  @SuppressWarnings("javadoc")
  public Date getStartDate() {
    return startDate;
  }

  @SuppressWarnings("javadoc")
  public Date getStartTime() {
    return startTime;
  }

  @SuppressWarnings("javadoc")
  public String getTypeOfAssignmentCode() {
    return typeOfAssignmentCode;
  }

  @SuppressWarnings("javadoc")
  public BigDecimal getWeightingNumber() {
    return weightingNumber;
  }

  @SuppressWarnings("javadoc")
  public String getEstablishedForId() {
    return establishedForId;
  }

  @SuppressWarnings("javadoc")
  public void setEstablishedForId(String establishedForId) {
    this.establishedForId = establishedForId;
  }

  @Override
  public Serializable getPrimaryKey() {
    return getId();
  }

  @Override
  public final int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this, false);
  }

  @SuppressWarnings("javadoc")
  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }

  @SuppressWarnings("javadoc")
  public void setEndTime(Date endTime) {
    this.endTime = endTime;
  }

  @SuppressWarnings("javadoc")
  public void setFkCaseLoad(String fkCaseLoad) {
    this.fkCaseLoad = fkCaseLoad;
  }

  @SuppressWarnings("javadoc")
  public void setFkOutOfStateContactParty(String fkOutOfStateContactParty) {
    this.fkOutOfStateContactParty = fkOutOfStateContactParty;
  }

  @SuppressWarnings("javadoc")
  public void setResponsibilityDescription(String responsibilityDescription) {
    this.responsibilityDescription = responsibilityDescription;
  }

  @SuppressWarnings("javadoc")
  public void setSecondaryAssignmentRoleType(Short secondaryAssignmentRoleType) {
    this.secondaryAssignmentRoleType = secondaryAssignmentRoleType;
  }

  @SuppressWarnings("javadoc")
  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }

  @SuppressWarnings("javadoc")
  public void setStartTime(Date startTime) {
    this.startTime = startTime;
  }

  @SuppressWarnings("javadoc")
  public void setTypeOfAssignmentCode(String typeOfAssignmentCode) {
    this.typeOfAssignmentCode = typeOfAssignmentCode;
  }

  @SuppressWarnings("javadoc")
  public void setWeightingNumber(BigDecimal weightingNumber) {
    this.weightingNumber = weightingNumber;
  }

  // ===================
  // IDENTITY:
  // ===================

  @Override
  public final boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj, false);
  }

  @SuppressWarnings("javadoc")
  public void setId(String id) {
    this.id = id;
  }

  @SuppressWarnings("javadoc")
  public void setCountySpecificCode(String countySpecificCode) {
    this.countySpecificCode = countySpecificCode;
  }

}
