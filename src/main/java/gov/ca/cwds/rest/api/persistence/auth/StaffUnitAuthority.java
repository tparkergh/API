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

import gov.ca.cwds.rest.api.persistence.cms.CmsPersistentObject;

/**
 * {@link CmsPersistentObject} representing a StaffPersonUnitAuthority.
 * 
 * @author CWDS API Team
 */
@NamedQueries({
    @NamedQuery(name = "gov.ca.cwds.rest.api.persistence.auth.StaffUnitAuthority.findAll",
        query = "FROM StaffUnitAuthority"),
    @NamedQuery(
        name = "gov.ca.cwds.rest.api.persistence.auth.StaffUnitAuthority.findAllUpdatedAfter",
        query = "FROM SubstituteCareProvider WHERE lastUpdatedTime > :after")})
@Entity
@Table(schema = "CWSINT", name = "STFUATHT")
public class StaffUnitAuthority extends CmsPersistentObject {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  @Column(name = "UNTAUTH_CD")
  private String authorityCode;

  @Column(name = "CNTY_SPFCD")
  private String countySpecificCode;

  @Type(type = "date")
  @Column(name = "END_DT")
  private Date endDate;

  @Column(name = "FKASG_UNIT")
  private String fkasgUnit;

  @Column(name = "FKSTFPERST")
  private String staffPersonId;

  @Type(type = "date")
  @Column(name = "START_DT")
  private Date startDate;

  @Id
  @Column(name = "THIRD_ID")
  private String thirdId;

  /**
   * Default constructor
   * 
   * Required for Hibernate
   */
  public StaffUnitAuthority() {
    super();
  }

  /**
   * @param authorityCode The authorityCode
   * @param countySpecificCode The countySpecificCode
   * @param endDate The endDate
   * @param fkasgUnit The fkasgUnit
   * @param staffPersonId The staffPersonId
   * @param startDate The startDate
   * @param thirdId The thirdId
   */
  public StaffUnitAuthority(String authorityCode, String countySpecificCode, Date endDate,
      String fkasgUnit, String staffPersonId, Date startDate, String thirdId) {
    super();
    this.authorityCode = authorityCode;
    this.countySpecificCode = countySpecificCode;
    this.endDate = endDate;
    this.fkasgUnit = fkasgUnit;
    this.staffPersonId = staffPersonId;
    this.startDate = startDate;
    this.thirdId = thirdId;
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.api.persistence.PersistentObject#getPrimaryKey()
   */
  @Override
  public String getPrimaryKey() {
    return getThirdId();
  }

  /**
   * @return the authorityCode
   */
  public String getAuthorityCode() {
    return StringUtils.trimToEmpty(authorityCode);
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
   * @return the fkasgUnit
   */
  public String getFkasgUnit() {
    return StringUtils.trimToEmpty(fkasgUnit);
  }

  /**
   * @return the staffPersonId
   */
  public String getStaffPersonId() {
    return StringUtils.trimToEmpty(staffPersonId);
  }

  /**
   * @return the startDate
   */
  public Date getStartDate() {
    return startDate;
  }

  /**
   * @return the thirdId
   */
  public String getThirdId() {
    return StringUtils.trimToEmpty(thirdId);
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
    result = prime * result + ((authorityCode == null) ? 0 : authorityCode.hashCode());
    result = prime * result + ((countySpecificCode == null) ? 0 : countySpecificCode.hashCode());
    result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
    result = prime * result + ((fkasgUnit == null) ? 0 : fkasgUnit.hashCode());
    result = prime * result + ((staffPersonId == null) ? 0 : staffPersonId.hashCode());
    result = prime * result + ((startDate == null) ? 0 : startDate.hashCode());
    result = prime * result + ((thirdId == null) ? 0 : thirdId.hashCode());
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
    if (!(obj instanceof StaffUnitAuthority)) {
      return false;
    }
    StaffUnitAuthority other = (StaffUnitAuthority) obj;
    if (authorityCode == null) {
      if (other.authorityCode != null) {
        return false;
      }
    } else if (!authorityCode.equals(other.authorityCode)) {
      return false;
    }
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
    if (fkasgUnit == null) {
      if (other.fkasgUnit != null) {
        return false;
      }
    } else if (!fkasgUnit.equals(other.fkasgUnit)) {
      return false;
    }
    if (staffPersonId == null) {
      if (other.staffPersonId != null) {
        return false;
      }
    } else if (!staffPersonId.equals(other.staffPersonId)) {
      return false;
    }
    if (startDate == null) {
      if (other.startDate != null) {
        return false;
      }
    } else if (!startDate.equals(other.startDate)) {
      return false;
    }
    if (thirdId == null) {
      if (other.thirdId != null) {
        return false;
      }
    } else if (!thirdId.equals(other.thirdId)) {
      return false;
    }
    return true;
  }

}
