package gov.ca.cwds.data.persistence.cms;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.Type;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import gov.ca.cwds.data.CmsSystemCodeDeserializer;
import gov.ca.cwds.data.SystemCodeSerializer;

/**
 * {@link CmsPersistentObject} representing a StaffPerson.
 * 
 * @author CWDS API Team
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "ASGNM_T")
public class Assignment extends CmsPersistentObject {

  @Column(name = "CNTY_SPFCD", length = 2)
  private String countySpecificCode;

  @Type(type = "date")
  @Column(name = "END_DT")
  private Date endDate;

  @Type(type = "time")
  @Column(name = "END_TM")
  private Date endTime;

  @Column(name = "ESTBLSH_CD", length = 1)
  private String establishedForCode;

  @Column(name = "ESTBLSH_ID", length = CMS_ID_LEN)
  private String establishedForId;

  @Column(name = "FKCASE_LDT", length = CMS_ID_LEN)
  private String fkCaseLoad;

  @Column(name = "FKOST_PTYT", length = CMS_ID_LEN)
  private String fkOutOfStateContactParty;

  @Id
  @Column(name = "IDENTIFIER", length = CMS_ID_LEN)
  private String id;

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

  @Type(type = "decimal")
  @Column(name = "WGHTNG_NO", length = 5)
  private String weightingNumber;


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
  public String getEstablishedForId() {
    return establishedForId;
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
  public String getWeightingNumber() {
    return weightingNumber;
  }


  @Override
  public Serializable getPrimaryKey() {
    return getId();
  }

  /**
   * Default constructor
   * 
   * Required for Hibernate
   */
  public Assignment() {
    super();
  }

  /**
   * @param countySpecificCode - county code of case load with this assignment
   * @param endDate - end date of assignment
   * @param endTime - end time of assignment
   * @param establishedForCode - referral or case
   * @param establishedForId - referral or case Id
   * @param fkCaseLoad - foriegn key to the case load
   * @param fkOutOfStateContactParty - foriegn ky to the out of state contact party
   * @param responsibilityDescription - description
   * @param secondaryAssignmentRoleType - primary or secondary
   * @param startDate - start date of assignment
   * @param startTime - end date of assignment
   * @param typeOfAssignmentCode - primary, secondary, or read only
   * @param weightingNumber - weighting within case load
   */
  public Assignment(String countySpecificCode, Date endDate, Date endTime,
      String establishedForCode, String establishedForId, String fkCaseLoad,
      String fkOutOfStateContactParty, String responsibilityDescription,
      Short secondaryAssignmentRoleType, Date startDate, Date startTime,
      String typeOfAssignmentCode, String weightingNumber) {
    super();
    this.countySpecificCode = countySpecificCode;
    this.endDate = endDate;
    this.endTime = endTime;
    this.establishedForCode = establishedForCode;
    this.establishedForId = establishedForId;
    this.fkCaseLoad = fkCaseLoad;
    this.fkOutOfStateContactParty = fkOutOfStateContactParty;
    this.responsibilityDescription = responsibilityDescription;
    this.secondaryAssignmentRoleType = secondaryAssignmentRoleType;
    this.startDate = startDate;
    this.startTime = startTime;
    this.typeOfAssignmentCode = typeOfAssignmentCode;
    this.weightingNumber = weightingNumber;
  }


  @Override
  public final int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this, false);
  }


  @Override
  public final boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj, false);
  }

}
