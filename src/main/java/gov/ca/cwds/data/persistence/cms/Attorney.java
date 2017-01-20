package gov.ca.cwds.data.persistence.cms;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;
import org.hibernate.annotations.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import gov.ca.cwds.data.CmsSystemCodeDeserializer;
import gov.ca.cwds.data.IMultiplePhonesAware;
import gov.ca.cwds.data.IPersonAware;
import gov.ca.cwds.data.IPhoneAware;
import gov.ca.cwds.data.ReadablePhone;
import gov.ca.cwds.data.SystemCodeSerializer;
import gov.ca.cwds.data.persistence.PersistentObject;

/**
 * {@link PersistentObject} representing a Attorney
 * 
 * @author CWDS API Team
 */
@NamedQueries({
    @NamedQuery(name = "gov.ca.cwds.data.persistence.cms.Attorney.findAll",
        query = "FROM Attorney"),
    @NamedQuery(name = "gov.ca.cwds.data.persistence.cms.Attorney.findAllUpdatedAfter",
        query = "FROM Attorney WHERE lastUpdatedTime > :after")})
@Entity
@Table(schema = "CWSINT", name = "ATTRNY_T")
@JsonPropertyOrder(alphabetic = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Attorney extends CmsPersistentObject implements IPersonAware, IMultiplePhonesAware {

  private static final Logger LOGGER = LoggerFactory.getLogger(Attorney.class);

  /**
   * Base serialization value. Increment by version.
   */
  private static final long serialVersionUID = 1L;

  @Column(name = "ARCASS_IND")
  private String archiveAssociationIndicator;

  @Column(name = "BUSNSS_NM")
  private String businessName;

  @Column(name = "CITY_NM")
  private String cityName;

  @Column(name = "CWATRY_IND")
  private String cwsAttorneyIndicator;

  @Column(name = "EMAIL_ADDR")
  private String emailAddress;

  @Type(type = "date")
  @Column(name = "END_DT")
  private Date endDate;

  @Column(name = "FAX_NO")
  private BigDecimal faxNumber;

  @Column(name = "FIRST_NM")
  private String firstName;

  @SystemCodeSerializer(logical = true, description = true)
  @JsonDeserialize(using = CmsSystemCodeDeserializer.class)
  @Type(type = "short")
  @Column(name = "GVR_ENTC")
  private Short governmentEntityType;

  @Id
  @Column(name = "IDENTIFIER", length = CMS_ID_LEN)
  private String id;

  @SystemCodeSerializer(logical = true, description = true)
  @JsonDeserialize(using = CmsSystemCodeDeserializer.class)
  @Type(type = "short")
  @Column(name = "LANG_TPC")
  private Short languageType;

  @Column(name = "LAST_NM")
  private String lastName;

  @Type(type = "integer")
  @Column(name = "MSG_EXT_NO")
  private Integer messagePhoneExtensionNumber;

  @Column(name = "MSG_TEL_NO")
  private BigDecimal messagePhoneNumber;

  @Column(name = "MID_INI_NM")
  private String middleInitialName;

  @Column(name = "NMPRFX_DSC")
  private String namePrefixDescription;

  @Column(name = "POSTIL_DSC")
  private String positionTitleDescription;

  @Type(type = "integer")
  @Column(name = "PRM_EXT_NO")
  private Integer primaryPhoneExtensionNumber;

  @Column(name = "PRM_TEL_NO")
  private BigDecimal primaryPhoneNumber;

  @SystemCodeSerializer(logical = true, description = true)
  @JsonDeserialize(using = CmsSystemCodeDeserializer.class)
  @Type(type = "short")
  @Column(name = "STATE_C")
  private Short stateCodeType;

  @Column(name = "STREET_NM")
  private String streetName;

  @Column(name = "STREET_NO")
  private String streetNumber;

  @Column(name = "SUFX_TLDSC")
  private String suffixTitleDescription;

  @Type(type = "integer")
  @Column(name = "ZIP_NO")
  private Integer zipNumber;

  @Type(type = "short")
  @Column(name = "ZIP_SFX_NO")
  private Short zipSuffixNumber;

  /**
   * Default constructor
   * 
   * Required for Hibernate
   */
  public Attorney() {
    super();
  }

  /**
   * Construct from all fields.
   * 
   * @param archiveAssociationIndicator The archiveAssociationIndicator
   * @param businessName The businessName
   * @param cityName The cityName
   * @param cwsAttorneyIndicator The cwsAttorneyIndicator
   * @param emailAddress The cwsAttorneyIndicator
   * @param endDate The endDate
   * @param faxNumber The faxNumber
   * @param firstName The firstName
   * @param governmentEntityType The governmentEntityType
   * @param id The id
   * @param languageType The languageType
   * @param lastName The lastName
   * @param messagePhoneExtensionNumber The messagePhoneExtensionNumber
   * @param messagePhoneNumber The messagePhoneNumber
   * @param middleInitialName The middleInitialName
   * @param namePrefixDescription The namePrefixDescription
   * @param positionTitleDescription The positionTitleDescription
   * @param primaryPhoneExtensionNumber The primaryPhoneExtensionNumber
   * @param primaryPhoneNumber The primaryPhoneNumber
   * @param stateCodeType The stateCodeType
   * @param streetName The streetName
   * @param streetNumber The streetNumber
   * @param suffixTitleDescription The suffixTitleDescription
   * @param zipNumber The zipNumber
   * @param zipSuffixNumber The zipSuffixNumber
   */
  public Attorney(String archiveAssociationIndicator, String businessName, String cityName,
      String cwsAttorneyIndicator, String emailAddress, Date endDate, BigDecimal faxNumber,
      String firstName, Short governmentEntityType, String id, Short languageType, String lastName,
      Integer messagePhoneExtensionNumber, BigDecimal messagePhoneNumber, String middleInitialName,
      String namePrefixDescription, String positionTitleDescription,
      Integer primaryPhoneExtensionNumber, BigDecimal primaryPhoneNumber, Short stateCodeType,
      String streetName, String streetNumber, String suffixTitleDescription, Integer zipNumber,
      Short zipSuffixNumber) {
    super();
    this.archiveAssociationIndicator = archiveAssociationIndicator;
    this.businessName = businessName;
    this.cityName = cityName;
    this.cwsAttorneyIndicator = cwsAttorneyIndicator;
    this.emailAddress = emailAddress;
    this.endDate = endDate;
    this.faxNumber = faxNumber;
    this.firstName = firstName;
    this.governmentEntityType = governmentEntityType;
    this.id = id;
    this.languageType = languageType;
    this.lastName = lastName;
    this.messagePhoneExtensionNumber = messagePhoneExtensionNumber;
    this.messagePhoneNumber = messagePhoneNumber;
    this.middleInitialName = middleInitialName;
    this.namePrefixDescription = namePrefixDescription;
    this.positionTitleDescription = positionTitleDescription;
    this.primaryPhoneExtensionNumber = primaryPhoneExtensionNumber;
    this.primaryPhoneNumber = primaryPhoneNumber;
    this.stateCodeType = stateCodeType;
    this.streetName = streetName;
    this.streetNumber = streetNumber;
    this.suffixTitleDescription = suffixTitleDescription;
    this.zipNumber = zipNumber;
    this.zipSuffixNumber = zipSuffixNumber;
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.data.persistence.PersistentObject#getPrimaryKey()
   */
  @JsonIgnore
  @Override
  public String getPrimaryKey() {
    return getId();
  }

  /**
   * @return the archiveAssociationIndicator
   */
  public String getArchiveAssociationIndicator() {
    return StringUtils.trimToEmpty(archiveAssociationIndicator);
  }

  /**
   * @return the businessName
   */
  public String getBusinessName() {
    return StringUtils.trimToEmpty(businessName);
  }

  /**
   * @return the cityName
   */
  public String getCityName() {
    return StringUtils.trimToEmpty(cityName);
  }

  /**
   * @return the cwsAttorneyIndicator
   */
  public String getCwsAttorneyIndicator() {
    return StringUtils.trimToEmpty(cwsAttorneyIndicator);
  }

  /**
   * @return the emailAddress
   */
  public String getEmailAddress() {
    return StringUtils.trimToEmpty(emailAddress);
  }

  /**
   * @return the endDate
   */
  public Date getEndDate() {
    return endDate;
  }

  /**
   * @return the faxNumber
   */
  public BigDecimal getFaxNumber() {
    return faxNumber;
  }

  /**
   * @return the firstName
   */
  @Override
  public String getFirstName() {
    return StringUtils.trimToEmpty(firstName);
  }

  /**
   * @return the governmentEntityType
   */
  public Short getGovernmentEntityType() {
    return governmentEntityType;
  }

  /**
   * @return the id
   */
  public String getId() {
    return StringUtils.trimToEmpty(id);
  }

  /**
   * @return the languageType
   */
  public Short getLanguageType() {
    return languageType;
  }

  /**
   * @return the lastName
   */
  @Override
  public String getLastName() {
    return StringUtils.trimToEmpty(lastName);
  }

  /**
   * @return the messagePhoneExtensionNumber
   */
  public Integer getMessagePhoneExtensionNumber() {
    return messagePhoneExtensionNumber;
  }

  /**
   * @return the messagePhoneNumber
   */
  public BigDecimal getMessagePhoneNumber() {
    return messagePhoneNumber;
  }

  /**
   * @return the middleInitialName
   */
  public String getMiddleInitialName() {
    return StringUtils.trimToEmpty(middleInitialName);
  }

  /**
   * @return the namePrefixDescription
   */
  public String getNamePrefixDescription() {
    return StringUtils.trimToEmpty(namePrefixDescription);
  }

  /**
   * @return the positionTitleDescription
   */
  public String getPositionTitleDescription() {
    return StringUtils.trimToEmpty(positionTitleDescription);
  }

  /**
   * @return the primaryPhoneExtensionNumber
   */
  public Integer getPrimaryPhoneExtensionNumber() {
    return primaryPhoneExtensionNumber;
  }

  /**
   * @return the primaryPhoneNumber
   */
  public BigDecimal getPrimaryPhoneNumber() {
    return primaryPhoneNumber;
  }

  /**
   * @return the stateCodeType
   */
  public Short getStateCodeType() {
    return stateCodeType;
  }

  /**
   * @return the streetName
   */
  public String getStreetName() {
    return StringUtils.trimToEmpty(streetName);
  }

  /**
   * @return the streetNumber
   */
  public String getStreetNumber() {
    return StringUtils.trimToEmpty(streetNumber);
  }

  /**
   * @return the suffixTitleDescription
   */
  public String getSuffixTitleDescription() {
    return StringUtils.trimToEmpty(suffixTitleDescription);
  }

  /**
   * @return the zipNumber
   */
  public Integer getZipNumber() {
    return zipNumber;
  }

  /**
   * @return the zipSuffixNumber
   */
  public Short getZipSuffixNumber() {
    return zipSuffixNumber;
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
    result = prime * result
        + ((archiveAssociationIndicator == null) ? 0 : archiveAssociationIndicator.hashCode());
    result = prime * result + ((businessName == null) ? 0 : businessName.hashCode());
    result = prime * result + ((cityName == null) ? 0 : cityName.hashCode());
    result =
        prime * result + ((cwsAttorneyIndicator == null) ? 0 : cwsAttorneyIndicator.hashCode());
    result = prime * result + ((emailAddress == null) ? 0 : emailAddress.hashCode());
    result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
    result = prime * result + ((faxNumber == null) ? 0 : faxNumber.hashCode());
    result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
    result =
        prime * result + ((governmentEntityType == null) ? 0 : governmentEntityType.hashCode());
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    result = prime * result + ((languageType == null) ? 0 : languageType.hashCode());
    result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
    result = prime * result
        + ((messagePhoneExtensionNumber == null) ? 0 : messagePhoneExtensionNumber.hashCode());
    result = prime * result + ((messagePhoneNumber == null) ? 0 : messagePhoneNumber.hashCode());
    result = prime * result + ((middleInitialName == null) ? 0 : middleInitialName.hashCode());
    result =
        prime * result + ((namePrefixDescription == null) ? 0 : namePrefixDescription.hashCode());
    result = prime * result
        + ((positionTitleDescription == null) ? 0 : positionTitleDescription.hashCode());
    result = prime * result
        + ((primaryPhoneExtensionNumber == null) ? 0 : primaryPhoneExtensionNumber.hashCode());
    result = prime * result + ((primaryPhoneNumber == null) ? 0 : primaryPhoneNumber.hashCode());
    result = prime * result + ((stateCodeType == null) ? 0 : stateCodeType.hashCode());
    result = prime * result + ((streetName == null) ? 0 : streetName.hashCode());
    result = prime * result + ((streetNumber == null) ? 0 : streetNumber.hashCode());
    result =
        prime * result + ((suffixTitleDescription == null) ? 0 : suffixTitleDescription.hashCode());
    result = prime * result + ((zipNumber == null) ? 0 : zipNumber.hashCode());
    result = prime * result + ((zipSuffixNumber == null) ? 0 : zipSuffixNumber.hashCode());
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
    if (!(obj instanceof Attorney)) {
      return false;
    }
    Attorney other = (Attorney) obj;
    if (archiveAssociationIndicator == null) {
      if (other.archiveAssociationIndicator != null) {
        return false;
      }
    } else if (!archiveAssociationIndicator.equals(other.archiveAssociationIndicator)) {
      return false;
    }
    if (businessName == null) {
      if (other.businessName != null) {
        return false;
      }
    } else if (!businessName.equals(other.businessName)) {
      return false;
    }
    if (cityName == null) {
      if (other.cityName != null) {
        return false;
      }
    } else if (!cityName.equals(other.cityName)) {
      return false;
    }
    if (cwsAttorneyIndicator == null) {
      if (other.cwsAttorneyIndicator != null) {
        return false;
      }
    } else if (!cwsAttorneyIndicator.equals(other.cwsAttorneyIndicator)) {
      return false;
    }
    if (emailAddress == null) {
      if (other.emailAddress != null) {
        return false;
      }
    } else if (!emailAddress.equals(other.emailAddress)) {
      return false;
    }
    if (endDate == null) {
      if (other.endDate != null) {
        return false;
      }
    } else if (!endDate.equals(other.endDate)) {
      return false;
    }
    if (faxNumber == null) {
      if (other.faxNumber != null) {
        return false;
      }
    } else if (!faxNumber.equals(other.faxNumber)) {
      return false;
    }
    if (firstName == null) {
      if (other.firstName != null) {
        return false;
      }
    } else if (!firstName.equals(other.firstName)) {
      return false;
    }
    if (governmentEntityType == null) {
      if (other.governmentEntityType != null) {
        return false;
      }
    } else if (!governmentEntityType.equals(other.governmentEntityType)) {
      return false;
    }
    if (id == null) {
      if (other.id != null) {
        return false;
      }
    } else if (!id.equals(other.id)) {
      return false;
    }
    if (languageType == null) {
      if (other.languageType != null) {
        return false;
      }
    } else if (!languageType.equals(other.languageType)) {
      return false;
    }
    if (lastName == null) {
      if (other.lastName != null) {
        return false;
      }
    } else if (!lastName.equals(other.lastName)) {
      return false;
    }
    if (messagePhoneExtensionNumber == null) {
      if (other.messagePhoneExtensionNumber != null) {
        return false;
      }
    } else if (!messagePhoneExtensionNumber.equals(other.messagePhoneExtensionNumber)) {
      return false;
    }
    if (messagePhoneNumber == null) {
      if (other.messagePhoneNumber != null) {
        return false;
      }
    } else if (!messagePhoneNumber.equals(other.messagePhoneNumber)) {
      return false;
    }
    if (middleInitialName == null) {
      if (other.middleInitialName != null) {
        return false;
      }
    } else if (!middleInitialName.equals(other.middleInitialName)) {
      return false;
    }
    if (namePrefixDescription == null) {
      if (other.namePrefixDescription != null) {
        return false;
      }
    } else if (!namePrefixDescription.equals(other.namePrefixDescription)) {
      return false;
    }
    if (positionTitleDescription == null) {
      if (other.positionTitleDescription != null) {
        return false;
      }
    } else if (!positionTitleDescription.equals(other.positionTitleDescription)) {
      return false;
    }
    if (primaryPhoneExtensionNumber == null) {
      if (other.primaryPhoneExtensionNumber != null) {
        return false;
      }
    } else if (!primaryPhoneExtensionNumber.equals(other.primaryPhoneExtensionNumber)) {
      return false;
    }
    if (primaryPhoneNumber == null) {
      if (other.primaryPhoneNumber != null) {
        return false;
      }
    } else if (!primaryPhoneNumber.equals(other.primaryPhoneNumber)) {
      return false;
    }
    if (stateCodeType == null) {
      if (other.stateCodeType != null) {
        return false;
      }
    } else if (!stateCodeType.equals(other.stateCodeType)) {
      return false;
    }
    if (streetName == null) {
      if (other.streetName != null) {
        return false;
      }
    } else if (!streetName.equals(other.streetName)) {
      return false;
    }
    if (streetNumber == null) {
      if (other.streetNumber != null) {
        return false;
      }
    } else if (!streetNumber.equals(other.streetNumber)) {
      return false;
    }
    if (suffixTitleDescription == null) {
      if (other.suffixTitleDescription != null) {
        return false;
      }
    } else if (!suffixTitleDescription.equals(other.suffixTitleDescription)) {
      return false;
    }
    if (zipNumber == null) {
      if (other.zipNumber != null) {
        return false;
      }
    } else if (!zipNumber.equals(other.zipNumber)) {
      return false;
    }
    if (zipSuffixNumber == null) {
      if (other.zipSuffixNumber != null) {
        return false;
      }
    } else if (!zipSuffixNumber.equals(other.zipSuffixNumber)) {
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

  @JsonIgnore
  @Override
  public String getMiddleName() {
    return this.getMiddleInitialName();
  }

  @JsonIgnore
  @Override
  public String getGender() {
    return null;
  }

  @JsonIgnore
  @Override
  public Date getBirthDate() {
    return null;
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

  // =======================
  // IMultiplePhonesAware:
  // =======================

  @JsonIgnore
  @Override
  public IPhoneAware[] getPhones() {

    List<IPhoneAware> phones = new ArrayList<>();
    if (this.primaryPhoneNumber != null && !BigDecimal.ZERO.equals(this.primaryPhoneNumber)) {
      phones.add(new ReadablePhone(this.primaryPhoneNumber.toPlainString(),
          this.primaryPhoneExtensionNumber != null ? this.primaryPhoneExtensionNumber.toString()
              : null,
          null));
    }

    if (this.messagePhoneNumber != null && !BigDecimal.ZERO.equals(this.messagePhoneNumber)) {
      LOGGER.debug("add message phone");
      phones
          .add(new ReadablePhone(
              this.messagePhoneNumber.toPlainString(), this.messagePhoneExtensionNumber != null
                  ? this.messagePhoneExtensionNumber.toString() : null,
              IPhoneAware.PhoneType.Cell));
    }

    return phones.toArray(new IPhoneAware[0]);
  }

}
