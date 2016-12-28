package gov.ca.cwds.rest.api.persistence.auth;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;
import org.hibernate.annotations.Type;

import gov.ca.cwds.rest.api.persistence.PersistentObject;
import gov.ca.cwds.rest.api.persistence.cms.CmsPersistentObject;

/**
 * {@link PersistentObject} representing a StaffPersonPrivilege
 * 
 * @author CWDS API Team
 */
@NamedQueries({
    @NamedQuery(name = "gov.ca.cwds.rest.api.persistence.auth.StaffAuthorityPrivilege.findAll",
        query = "FROM StaffAuthorityPrivilege"),
    @NamedQuery(name = "gov.ca.cwds.rest.api.persistence.auth.StaffAuthorityPrivilege.findByUser",
        query = "FROM StaffAuthorityPrivilege WHERE fkuseridT = :userId"),
    @NamedQuery(
        name = "gov.ca.cwds.rest.api.persistence.auth.StaffAuthorityPrivilege.findAllUpdatedAfter",
        query = "FROM StaffAuthorityPrivilege WHERE lastUpdatedTime > :after"),
    @NamedQuery(
        name = "gov.ca.cwds.rest.api.persistence.auth.StaffAuthorityPrivilege.checkForSocialWorker",
        query = "FROM StaffAuthorityPrivilege S WHERE S.fkuseridT = :userId AND "
            + "S.levelOfAuthPrivilegeType = '1468' AND "
            + "S.levelOfAuthPrivilegeCode = 'P' AND S.endDate is null")})
@Entity
@Table(schema = "CWSINT", name = "STF_PVLT")
public class StaffAuthorityPrivilege extends CmsPersistentObject {

  /**
   * Base serialization version. Increment per version of this class.
   */
  private static final long serialVersionUID = 1L;

  @Column(name = "CNTY_SPFCD")
  private String countySpecificCode;

  @Type(type = "date")
  @Column(name = "END_DT")
  private Date endDate;

  @Type(type = "time")
  @Column(name = "END_TIME")
  private Date endTime;

  @Column(name = "FKUSERID_T", length = CMS_ID_LEN)
  private String fkuseridT;

  @Id
  @Column(name = "IDENTIFIER", length = CMS_ID_LEN)
  private String id;

  @Column(name = "ATH_PRVC")
  private String levelOfAuthPrivilegeCode;

  @Type(type = "short")
  @Column(name = "LVL_PRVC")
  private Short levelOfAuthPrivilegeType;

  @Type(type = "date")
  @Column(name = "START_DT")
  private Date startDate;

  @Type(type = "time")
  @Column(name = "START_TIME")
  private Date startTime;

  /**
   * Default constructor.
   * 
   * Required for Hibernate
   */
  public StaffAuthorityPrivilege() {
    super();
  }

  /**
   * @param countySpecificCode The countySpecificCode
   * @param endDate The endDate
   * @param endTime The endDate
   * @param fkuseridT FK to user id table
   * @param id The id
   * @param levelOfAuthPrivilegeCode The levelOfAuthPrivilegeCode
   * @param levelOfAuthPrivilegeType The levelOfAuthPrivilegeType
   * @param startDate The startDate
   * @param startTime The startTime
   */
  public StaffAuthorityPrivilege(String countySpecificCode, Date endDate, Date endTime,
      String fkuseridT, String id, String levelOfAuthPrivilegeCode, Short levelOfAuthPrivilegeType,
      Date startDate, Date startTime) {
    super();
    this.countySpecificCode = countySpecificCode;
    this.endDate = endDate;
    this.endTime = endTime;
    this.fkuseridT = fkuseridT;
    this.id = id;
    this.levelOfAuthPrivilegeCode = levelOfAuthPrivilegeCode;
    this.levelOfAuthPrivilegeType = levelOfAuthPrivilegeType;
    this.startDate = startDate;
    this.startTime = startTime;
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.api.persistence.PersistentObject#getPrimaryKey()
   */
  @Override
  public String getPrimaryKey() {
    return getId();
  }

  /**
   * @return the countySpecificCode
   */
  public String getCountySpecificCode() {
    return StringUtils.trimToEmpty(countySpecificCode);
  }

  /**
   * @return the endDate
   */
  public Date getEndDate() {
    return endDate;
  }

  /**
   * @return the endTime
   */
  public Date getEndTime() {
    return endTime;
  }

  /**
   * @return the fkuseridT
   */
  public String getFkuseridT() {
    return StringUtils.trimToEmpty(fkuseridT);
  }

  /**
   * @return the id
   */
  public String getId() {
    return StringUtils.trimToEmpty(id);
  }

  /**
   * @return the levelOfAuthPrivilegeCode
   */
  public String getLevelOfAuthPrivilegeCode() {
    return StringUtils.trimToEmpty(levelOfAuthPrivilegeCode);
  }

  /**
   * @return the levelOfAuthPrivilegeType
   */
  public Short getLevelOfAuthPrivilegeType() {
    return levelOfAuthPrivilegeType;
  }

  /**
   * @return the startDate
   */
  public Date getStartDate() {
    return startDate;
  }

  /**
   * @return the startTime
   */
  public Date getStartTime() {
    return startTime;
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((countySpecificCode == null) ? 0 : countySpecificCode.hashCode());
    result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
    result = prime * result + ((endTime == null) ? 0 : endTime.hashCode());
    result = prime * result + ((fkuseridT == null) ? 0 : fkuseridT.hashCode());
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    result = prime * result
        + ((levelOfAuthPrivilegeCode == null) ? 0 : levelOfAuthPrivilegeCode.hashCode());
    result = prime * result
        + ((levelOfAuthPrivilegeType == null) ? 0 : levelOfAuthPrivilegeType.hashCode());
    result = prime * result + ((startDate == null) ? 0 : startDate.hashCode());
    result = prime * result + ((startTime == null) ? 0 : startTime.hashCode());
    return result;
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (!(obj instanceof StaffAuthorityPrivilege)) {
      return false;
    }
    StaffAuthorityPrivilege other = (StaffAuthorityPrivilege) obj;
    if (countySpecificCode == null) {
      if (other.countySpecificCode != null) {
        return false;
      }
    } else if (!countySpecificCode.equals(other.countySpecificCode)) {
      return false;
    }
    if (endDate == null) {
      if (other.endDate != null) {
        return false;
      }
    } else if (!endDate.equals(other.endDate)) {
      return false;
    }
    if (endTime == null) {
      if (other.endTime != null) {
        return false;
      }
    } else if (!endTime.equals(other.endTime)) {
      return false;
    }
    if (fkuseridT == null) {
      if (other.fkuseridT != null) {
        return false;
      }
    } else if (!fkuseridT.equals(other.fkuseridT)) {
      return false;
    }
    if (id == null) {
      if (other.id != null) {
        return false;
      }
    } else if (!id.equals(other.id)) {
      return false;
    }
    if (levelOfAuthPrivilegeCode == null) {
      if (other.levelOfAuthPrivilegeCode != null) {
        return false;
      }
    } else if (!levelOfAuthPrivilegeCode.equals(other.levelOfAuthPrivilegeCode)) {
      return false;
    }
    if (levelOfAuthPrivilegeType == null) {
      if (other.levelOfAuthPrivilegeType != null) {
        return false;
      }
    } else if (!levelOfAuthPrivilegeType.equals(other.levelOfAuthPrivilegeType)) {
      return false;
    }
    if (startDate == null) {
      if (other.startDate != null) {
        return false;
      }
    } else if (!startDate.equals(other.startDate)) {
      return false;
    }
    if (startTime == null) {
      if (other.startTime != null) {
        return false;
      }
    } else if (!startTime.equals(other.startTime)) {
      return false;
    }
    return true;
  }

}

