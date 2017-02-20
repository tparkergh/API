package gov.ca.cwds.data.persistence.cms;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.NamedNativeQueries;
import org.hibernate.annotations.NamedNativeQuery;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;
import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import gov.ca.cwds.data.persistence.PersistentObject;
import gov.ca.cwds.data.std.ApiPersonAware;


/**
 * {@link PersistentObject} representing a OtherAdultInPlacemtHome
 * 
 * @author CWDS API Team
 */
@NamedQueries({
    @NamedQuery(name = "gov.ca.cwds.data.persistence.cms.OtherAdultInPlacemtHome.findAll",
        query = "FROM OtherAdultInPlacemtHome"),
    @NamedQuery(
        name = "gov.ca.cwds.data.persistence.cms.OtherAdultInPlacemtHome.findAllUpdatedAfter",
        query = "FROM OtherAdultInPlacemtHome WHERE lastUpdatedTime > :after")})
@NamedNativeQueries({@NamedNativeQuery(
    name = "gov.ca.cwds.data.persistence.cms.OtherAdultInPlacemtHome.findAllByBucket",
    query = "select z.IDENTIFIER, z.BIRTH_DT, z.END_DT, z.GENDER_CD, z.OTH_ADLTNM, "
        + "z.START_DT, z.LST_UPD_ID, z.LST_UPD_TS, z.FKPLC_HM_T, z.COMNT_DSC, "
        + "z.OTH_ADL_CD, z.IDENTFD_DT, z.RESOST_IND, z.PASSBC_CD "
        + "from ( select mod(y.rn, CAST(:total_buckets AS INTEGER)) + 1 as bucket, y.* "
        + "from ( select row_number() over (order by 1) as rn, x.* "
        + "from ( select c.* from {h-schema}OTH_ADLT c "
        + ") x ) y ) z where z.bucket = :bucket_num for read only",
    resultClass = OtherAdultInPlacemtHome.class)})
@Entity
@Table(name = "OTH_ADLT")
@JsonPropertyOrder(alphabetic = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class OtherAdultInPlacemtHome extends CmsPersistentObject implements ApiPersonAware {

  /**
   * Base serialization version. Increment by class version.
   */
  private static final long serialVersionUID = 1L;

  @Type(type = "date")
  @Column(name = "BIRTH_DT")
  private Date birthDate;

  @Column(name = "COMNT_DSC")
  private String commentDescription;

  @Type(type = "date")
  @Column(name = "END_DT")
  private Date endDate;

  @Column(name = "FKPLC_HM_T", length = CMS_ID_LEN)
  private String fkplcHmT;

  @Column(name = "GENDER_CD")
  private String genderCode;

  @Id
  @Column(name = "IDENTIFIER", length = CMS_ID_LEN)
  private String id;

  @Type(type = "date")
  @Column(name = "IDENTFD_DT")
  private Date identifiedDate;

  @Column(name = "OTH_ADLTNM")
  private String name;

  @Column(name = "OTH_ADL_CD")
  private String otherAdultCode;

  @Column(name = "PASSBC_CD")
  private String passedBackgroundCheckCode;

  @Column(name = "RESOST_IND")
  private String residedOutOfStateIndicator;

  @Type(type = "date")
  @Column(name = "START_DT")
  private Date startDate;

  /**
   * Default constructor
   * 
   * Required for Hibernate
   */
  public OtherAdultInPlacemtHome() {
    super();
  }

  /**
   * @param birthDate The birthDate
   * @param commentDescription The commentDescription
   * @param endDate The endDate
   * @param fkplcHmT The fkplcHmT
   * @param genderCode The genderCode
   * @param id The id
   * @param identifiedDate The identifiedDate
   * @param name The name
   * @param otherAdultCode The otherAdultCode
   * @param passedBackgroundCheckCode The passedBackgroundCheckCode
   * @param residedOutOfStateIndicator The residedOutOfStateIndicator
   * @param startDate The startDate
   */
  public OtherAdultInPlacemtHome(Date birthDate, String commentDescription, Date endDate,
      String fkplcHmT, String genderCode, String id, Date identifiedDate, String name,
      String otherAdultCode, String passedBackgroundCheckCode, String residedOutOfStateIndicator,
      Date startDate) {
    super();
    this.birthDate = birthDate;
    this.commentDescription = commentDescription;
    this.endDate = endDate;
    this.fkplcHmT = fkplcHmT;
    this.genderCode = genderCode;
    this.id = id;
    this.identifiedDate = identifiedDate;
    this.name = name;
    this.otherAdultCode = otherAdultCode;
    this.passedBackgroundCheckCode = passedBackgroundCheckCode;
    this.residedOutOfStateIndicator = residedOutOfStateIndicator;
    this.startDate = startDate;
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.data.persistence.PersistentObject#getPrimaryKey()
   */
  @Override
  public String getPrimaryKey() {
    return getId();
  }

  /**
   * @return the birthDate
   */
  @Override
  public Date getBirthDate() {
    return birthDate;
  }

  /**
   * @return the commentDescription
   */
  public String getCommentDescription() {
    return StringUtils.trimToEmpty(commentDescription);
  }

  /**
   * @return the endDate
   */
  public Date getEndDate() {
    return endDate;
  }

  /**
   * @return the fkplcHmT
   */
  public String getFkplcHmT() {
    return StringUtils.trimToEmpty(fkplcHmT);
  }

  /**
   * @return the genderCode
   */
  public String getGenderCode() {
    return StringUtils.trimToEmpty(genderCode);
  }

  /**
   * @return the id
   */
  public String getId() {
    return StringUtils.trimToEmpty(id);
  }

  /**
   * @return the identifiedDate
   */
  public Date getIdentifiedDate() {
    return identifiedDate;
  }

  /**
   * @return the name
   */
  public String getName() {
    return StringUtils.trimToEmpty(name);
  }

  /**
   * @return the otherAdultCode
   */
  public String getOtherAdultCode() {
    return StringUtils.trimToEmpty(otherAdultCode);
  }

  /**
   * @return the passedBackgroundCheckCode
   */
  public String getPassedBackgroundCheckCode() {
    return StringUtils.trimToEmpty(passedBackgroundCheckCode);
  }

  /**
   * @return the residedOutOfStateIndicator
   */
  public String getResidedOutOfStateIndicator() {
    return StringUtils.trimToEmpty(residedOutOfStateIndicator);
  }

  /**
   * @return the startDate
   */
  public Date getStartDate() {
    return startDate;
  }

  @JsonIgnore
  @Override
  public String getFirstName() {
    return null;
  }

  @JsonIgnore
  @Override
  public String getMiddleName() {
    return null;
  }

  @JsonIgnore
  @Override
  public String getLastName() {
    return this.getName();
  }

  @JsonIgnore
  @Override
  public String getGender() {
    return this.getGenderCode();
  }

  @JsonIgnore
  @Override
  public String getSsn() {
    return null;
  }

  @JsonIgnore
  @Override
  public String getNameSuffix() {
    return null;
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.lang.Object#hashCode()
   */
  @Override
  public final int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((birthDate == null) ? 0 : birthDate.hashCode());
    result = prime * result + ((commentDescription == null) ? 0 : commentDescription.hashCode());
    result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
    result = prime * result + ((fkplcHmT == null) ? 0 : fkplcHmT.hashCode());
    result = prime * result + ((genderCode == null) ? 0 : genderCode.hashCode());
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    result = prime * result + ((identifiedDate == null) ? 0 : identifiedDate.hashCode());
    result = prime * result + ((name == null) ? 0 : name.hashCode());
    result = prime * result + ((otherAdultCode == null) ? 0 : otherAdultCode.hashCode());
    result = prime * result
        + ((passedBackgroundCheckCode == null) ? 0 : passedBackgroundCheckCode.hashCode());
    result = prime * result
        + ((residedOutOfStateIndicator == null) ? 0 : residedOutOfStateIndicator.hashCode());
    result = prime * result + ((startDate == null) ? 0 : startDate.hashCode());
    result = prime * result
        + ((super.getLastUpdatedId() == null) ? 0 : super.getLastUpdatedId().hashCode());
    result = prime * result
        + ((super.getLastUpdatedTime() == null) ? 0 : super.getLastUpdatedTime().hashCode());
    return result;
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public final boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (!(obj instanceof OtherAdultInPlacemtHome)) {
      return false;
    }
    OtherAdultInPlacemtHome other = (OtherAdultInPlacemtHome) obj;
    if (birthDate == null) {
      if (other.birthDate != null) {
        return false;
      }
    } else if (!birthDate.equals(other.birthDate)) {
      return false;
    }
    if (commentDescription == null) {
      if (other.commentDescription != null) {
        return false;
      }
    } else if (!commentDescription.equals(other.commentDescription)) {
      return false;
    }
    if (endDate == null) {
      if (other.endDate != null) {
        return false;
      }
    } else if (!endDate.equals(other.endDate)) {
      return false;
    }
    if (fkplcHmT == null) {
      if (other.fkplcHmT != null) {
        return false;
      }
    } else if (!fkplcHmT.equals(other.fkplcHmT)) {
      return false;
    }
    if (genderCode == null) {
      if (other.genderCode != null) {
        return false;
      }
    } else if (!genderCode.equals(other.genderCode)) {
      return false;
    }
    if (id == null) {
      if (other.id != null) {
        return false;
      }
    } else if (!id.equals(other.id)) {
      return false;
    }
    if (identifiedDate == null) {
      if (other.identifiedDate != null) {
        return false;
      }
    } else if (!identifiedDate.equals(other.identifiedDate)) {
      return false;
    }
    if (name == null) {
      if (other.name != null) {
        return false;
      }
    } else if (!name.equals(other.name)) {
      return false;
    }
    if (otherAdultCode == null) {
      if (other.otherAdultCode != null) {
        return false;
      }
    } else if (!otherAdultCode.equals(other.otherAdultCode)) {
      return false;
    }
    if (passedBackgroundCheckCode == null) {
      if (other.passedBackgroundCheckCode != null) {
        return false;
      }
    } else if (!passedBackgroundCheckCode.equals(other.passedBackgroundCheckCode)) {
      return false;
    }
    if (residedOutOfStateIndicator == null) {
      if (other.residedOutOfStateIndicator != null) {
        return false;
      }
    } else if (!residedOutOfStateIndicator.equals(other.residedOutOfStateIndicator)) {
      return false;
    }
    if (startDate == null) {
      if (other.startDate != null) {
        return false;
      }
    } else if (!startDate.equals(other.startDate)) {
      return false;
    }
    if (super.getLastUpdatedId() == null) {
      if (other.getLastUpdatedId() != null) {
        return false;
      }
    } else if (!super.getLastUpdatedId().equals(other.getLastUpdatedId())) {
      return false;
    }
    if (super.getLastUpdatedTime() == null) {
      if (other.getLastUpdatedTime() != null) {
        return false;
      }
    } else if (!super.getLastUpdatedTime().equals(other.getLastUpdatedTime())) {
      return false;
    }
    return true;
  }

}
