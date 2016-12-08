package gov.ca.cwds.rest.api.persistence.cms;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;
import org.hibernate.annotations.Type;

import gov.ca.cwds.rest.api.persistence.PersistentObject;

/**
 * {@link PersistentObject} representing an OtherClientName
 * 
 * @author CWDS API Team
 */
@SuppressWarnings("serial")
@NamedQueries({
    @NamedQuery(name = "gov.ca.cwds.rest.api.persistence.cms.OtherClientName.findAll",
        query = "FROM OtherClientName"),
    @NamedQuery(name = "gov.ca.cwds.rest.api.persistence.cms.OtherClientName.findAllUpdatedAfter",
        query = "FROM OtherClientName WHERE lastUpdatedTime > :after")})
@Entity
@Table(schema = "CWSINT", name = "OCL_NM_T")
public class OtherClientName extends CmsPersistentObject {
  @Column(name = "FKCLIENT_T")
  private String clientId;

  @Column(name = "FIRST_NM")
  private String firstName;

  @Column(name = "LAST_NM")
  private String lastName;

  @Column(name = "MIDDLE_NM")
  private String middleName;

  @Column(name = "NMPRFX_DSC")
  private String namePrefixDescription;

  @Type(type = "short")
  @Column(name = "NAME_TPC")
  private Short nameType;

  @Column(name = "SUFX_TLDSC")
  private String suffixTitleDescription;

  @Id
  @Column(name = "THIRD_ID")
  private String thirdId;


  /**
   * Default constructor
   * 
   * Required for Hibernate
   */
  public OtherClientName() {
    super();
  }

  public OtherClientName(String clientId, String firstName, String lastName, String middleName,
      String namePrefixDescription, Short nameType, String suffixTitleDescription, String thirdId) {
    super();
    this.clientId = clientId;
    this.firstName = firstName;
    this.lastName = lastName;
    this.middleName = middleName;
    this.namePrefixDescription = namePrefixDescription;
    this.nameType = nameType;
    this.suffixTitleDescription = suffixTitleDescription;
    this.thirdId = thirdId;

  }

  /*
   * (non-Javadoc)
   * 
   * @see gov.ca.cwds.rest.api.persistence.PersistentObject#getPrimaryKey()
   */
  @Override
  public String getPrimaryKey() {
    return getThirdId();
  }

  /**
   * @return the firstName
   */
  public String getFirstName() {
    return StringUtils.trimToEmpty(firstName);
  }

  /**
   * @return the clientId
   */
  public String getClientId() {
    return StringUtils.trimToEmpty(clientId);
  }

  /**
   * @return the lastName
   */
  public String getLastName() {
    return StringUtils.trimToEmpty(lastName);
  }

  /**
   * @return the middleName
   */
  public String getMiddleName() {
    return StringUtils.trimToEmpty(middleName);
  }

  /**
   * @return the namePrefixDescription
   */
  public String getNamePrefixDescription() {
    return StringUtils.trimToEmpty(namePrefixDescription);
  }

  /**
   * @return the nameType
   */
  public Short getNameType() {
    return nameType;
  }

  /**
   * @return the suffixTitleDescription
   */
  public String getSuffixTitleDescription() {
    return StringUtils.trimToEmpty(suffixTitleDescription);
  }

  /**
   * @return the thirdId
   */
  public String getThirdId() {
    return StringUtils.trimToEmpty(thirdId);
  }

  @Override
  public final int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((clientId == null) ? 0 : clientId.hashCode());
    result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
    result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
    result = prime * result + ((middleName == null) ? 0 : middleName.hashCode());
    result =
        prime * result + ((namePrefixDescription == null) ? 0 : namePrefixDescription.hashCode());
    result = prime * result + ((nameType == null) ? 0 : nameType.hashCode());
    result =
        prime * result + ((suffixTitleDescription == null) ? 0 : suffixTitleDescription.hashCode());
    result = prime * result + ((thirdId == null) ? 0 : thirdId.hashCode());
    result = prime * result
        + ((super.getLastUpdatedId() == null) ? 0 : super.getLastUpdatedId().hashCode());
    result = prime * result
        + ((super.getLastUpdatedTime() == null) ? 0 : super.getLastUpdatedTime().hashCode());

    return result;
  }

  @Override
  public final boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (!(obj instanceof OtherClientName)) {
      return false;
    }
    OtherClientName other = (OtherClientName) obj;
    if (clientId == null) {
      if (other.clientId != null) {
        return false;
      }
    } else if (!clientId.equals(other.clientId)) {
      return false;
    }
    if (firstName == null) {
      if (other.firstName != null) {
        return false;
      }
    } else if (!firstName.equals(other.firstName)) {
      return false;
    }
    if (lastName == null) {
      if (other.lastName != null) {
        return false;
      }
    } else if (!lastName.equals(other.lastName)) {
      return false;
    }
    if (middleName == null) {
      if (other.middleName != null) {
        return false;
      }
    } else if (!middleName.equals(other.middleName)) {
      return false;
    }
    if (namePrefixDescription == null) {
      if (other.namePrefixDescription != null) {
        return false;
      }
    } else if (!namePrefixDescription.equals(other.namePrefixDescription)) {
      return false;
    }
    if (nameType == null) {
      if (other.nameType != null) {
        return false;
      }
    } else if (!nameType.equals(other.nameType)) {
      return false;
    }
    if (suffixTitleDescription == null) {
      if (other.suffixTitleDescription != null) {
        return false;
      }
    } else if (!suffixTitleDescription.equals(other.suffixTitleDescription)) {
      return false;
    }
    if (thirdId == null) {
      if (other.thirdId != null) {
        return false;
      }
    } else if (!thirdId.equals(other.thirdId)) {
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
