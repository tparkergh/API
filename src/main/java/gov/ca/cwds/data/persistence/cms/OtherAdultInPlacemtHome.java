package gov.ca.cwds.data.persistence.cms;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import gov.ca.cwds.data.persistence.PersistentObject;

/**
 * {@link PersistentObject} representing a OtherAdultInPlacemtHome
 * 
 * @author CWDS API Team
 */
// @NamedQueries({
// @NamedQuery(name = "gov.ca.cwds.data.persistence.cms.OtherAdultInPlacemtHome.findAll",
// query = "FROM OtherAdultInPlacemtHome"),
// @NamedQuery(
// name = "gov.ca.cwds.data.persistence.cms.OtherAdultInPlacemtHome.findAllUpdatedAfter",
// query = "FROM OtherAdultInPlacemtHome WHERE lastUpdatedTime > :after")})
@Entity
@Table(name = "OTH_ADLT")
@JsonPropertyOrder(alphabetic = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class OtherAdultInPlacemtHome extends BaseOtherAdultInPlacemtHome {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

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
