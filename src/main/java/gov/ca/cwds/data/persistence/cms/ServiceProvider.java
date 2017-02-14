package gov.ca.cwds.data.persistence.cms;

import java.math.BigDecimal;
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
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import gov.ca.cwds.data.CmsSystemCodeDeserializer;
import gov.ca.cwds.data.IAddressAware;
import gov.ca.cwds.data.IPersonAware;
import gov.ca.cwds.data.IPhoneAware;
import gov.ca.cwds.data.SystemCodeSerializer;
import gov.ca.cwds.data.persistence.PersistentObject;


/**
 * {@link PersistentObject} representing a ServiceProvider
 * 
 * @author CWDS API Team
 */
@NamedQueries({
    @NamedQuery(name = "gov.ca.cwds.data.persistence.cms.ServiceProvider.findAll",
        query = "FROM ServiceProvider"),
    @NamedQuery(name = "gov.ca.cwds.data.persistence.cms.ServiceProvider.findAllUpdatedAfter",
        query = "FROM ServiceProvider WHERE lastUpdatedTime > :after")})
@NamedNativeQueries({
    @NamedNativeQuery(name = "gov.ca.cwds.data.persistence.cms.ServiceProvider.findAllByBucket",
        query = "select z.IDENTIFIER, z.AGENCY_NM, z.CITY_NM, z.FAX_NO, z.FIRST_NM, z.LAST_NM, "
            + "z.NMPRFX_DSC, z.PHONE_NO, z.TEL_EXT_NO, z.PSTITL_DSC, z.SVCPVDRC, z.STATE_C, "
            + "z.STREET_NM, z.STREET_NO, z.SUFX_TLDSC, z.ZIP_NM, z.LST_UPD_ID, z.LST_UPD_TS, "
            + "z.ZIP_SFX_NO, z.ARCASS_IND, z.EMAIL_ADDR "
            + "from ( select mod(y.rn, CAST(:total_buckets AS INTEGER)) + 1 as bucket, y.* "
            + "from ( select row_number() over (order by 1) as rn, x.* "
            + "from ( select c.* from {h-schema}SVC_PVRT c "
            + ") x ) y ) z where z.bucket = :bucket_num for read only",
        resultClass = ServiceProvider.class)})
@Entity
@Table(name = "SVC_PVRT")
@JsonPropertyOrder(alphabetic = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ServiceProvider extends CmsPersistentObject
    implements IPersonAware, IAddressAware, IPhoneAware {

  /**
   * Base serialization value. Increment by class version.
   */
  private static final long serialVersionUID = 1L;

  @Column(name = "AGENCY_NM", length = 35, nullable = false)
  private String agencyName;

  @Column(name = "ARCASS_IND", length = 1, nullable = false)
  private String archiveAssociationIndicator;

  @Column(name = "CITY_NM", length = 20, nullable = false)
  private String cityName;

  @Column(name = "EMAIL_ADDR", length = 50)
  private String emailAddress;

  @Column(name = "FAX_NO")
  private BigDecimal faxNumber;

  @Column(name = "FIRST_NM", length = 20, nullable = false)
  private String firstName;

  @Id
  @Column(name = "IDENTIFIER", length = CMS_ID_LEN)
  private String id;

  @Column(name = "LAST_NM", length = 20, nullable = false)
  private String lastName;

  @Column(name = "NMPRFX_DSC", length = 6, nullable = false)
  private String namePrefixDescription;

  @Type(type = "integer")
  @Column(name = "TEL_EXT_NO", nullable = false)
  private Integer phoneExtensionNumber;

  @Column(name = "PHONE_NO")
  private BigDecimal phoneNumber;

  @Column(name = "PSTITL_DSC", length = 30, nullable = false)
  private String positionTitleDescription;

  @Type(type = "short")
  @Column(name = "SVCPVDRC", nullable = false)
  private Short serviceProviderType;

  @SystemCodeSerializer(logical = true, description = true)
  @JsonDeserialize(using = CmsSystemCodeDeserializer.class)
  @Type(type = "short")
  @Column(name = "STATE_C", nullable = false)
  private Short stateCodeType;

  @Column(name = "STREET_NM", length = 40, nullable = false)
  private String streetName;

  @Column(name = "STREET_NO", length = 10, nullable = false)
  private String streetNumber;

  @Column(name = "SUFX_TLDSC", length = 4, nullable = false)
  private String suffixTitleDescription;

  @Type(type = "integer")
  @Column(name = "ZIP_NM", nullable = false)
  private Integer zipNumber;

  @Type(type = "short")
  @Column(name = "ZIP_SFX_NO", nullable = false)
  private Short zipSuffixNumber;

  /**
   * Default constructor
   * 
   * Required for Hibernate
   */
  public ServiceProvider() {
    super();
  }

  /**
   * @param agencyName The agencyName
   * @param archiveAssociationIndicator The archiveAssociationIndicator
   * @param cityName The cityName
   * @param emailAddress The emailAddress
   * @param faxNumber The faxNumber
   * @param firstName The firstName
   * @param id The id
   * @param lastName The lastName
   * @param namePrefixDescription The namePrefixDescription
   * @param phoneExtensionNumber The phoneExtensionNumber
   * @param phoneNumber The phoneNumber
   * @param positionTitleDescription The positionTitleDescription
   * @param serviceProviderType The serviceProviderType
   * @param stateCodeType The stateCodeType
   * @param streetName The streetName
   * @param streetNumber The streetNumber
   * @param suffixTitleDescription The suffixTitleDescription
   * @param zipNumber The zipNumber
   * @param zipSuffixNumber The zipSuffixNumber
   */
  public ServiceProvider(String agencyName, String archiveAssociationIndicator, String cityName,
      String emailAddress, BigDecimal faxNumber, String firstName, String id, String lastName,
      String namePrefixDescription, Integer phoneExtensionNumber, BigDecimal phoneNumber,
      String positionTitleDescription, Short serviceProviderType, Short stateCodeType,
      String streetName, String streetNumber, String suffixTitleDescription, Integer zipNumber,
      Short zipSuffixNumber) {
    super();
    this.agencyName = agencyName;
    this.archiveAssociationIndicator = archiveAssociationIndicator;
    this.cityName = cityName;
    this.emailAddress = emailAddress;
    this.faxNumber = faxNumber;
    this.firstName = firstName;
    this.id = id;
    this.lastName = lastName;
    this.namePrefixDescription = namePrefixDescription;
    this.phoneExtensionNumber = phoneExtensionNumber;
    this.phoneNumber = phoneNumber;
    this.positionTitleDescription = positionTitleDescription;
    this.serviceProviderType = serviceProviderType;
    this.stateCodeType = stateCodeType;
    this.streetName = streetName;
    this.streetNumber = streetNumber;
    this.suffixTitleDescription = suffixTitleDescription;
    this.zipNumber = zipNumber;
    this.zipSuffixNumber = zipSuffixNumber;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getPrimaryKey() {
    return getId();
  }

  /**
   * @return the agencyName
   */
  public String getAgencyName() {
    return StringUtils.trimToEmpty(agencyName);
  }

  /**
   * @return the archiveAssociationIndicator
   */
  public String getArchiveAssociationIndicator() {
    return StringUtils.trimToEmpty(archiveAssociationIndicator);
  }

  /**
   * @return the cityName
   */
  public String getCityName() {
    return StringUtils.trimToEmpty(cityName);
  }

  /**
   * @return the emailAddress
   */
  public String getEmailAddress() {
    return StringUtils.trimToEmpty(emailAddress);
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
   * @return the id
   */
  public String getId() {
    return StringUtils.trimToEmpty(id);
  }

  /**
   * @return the lastName
   */
  @Override
  public String getLastName() {
    return StringUtils.trimToEmpty(lastName);
  }

  /**
   * @return the namePrefixDescription
   */
  public String getNamePrefixDescription() {
    return StringUtils.trimToEmpty(namePrefixDescription);
  }

  /**
   * @return the phoneExtensionNumber
   */
  public Integer getPhoneExtensionNumber() {
    return phoneExtensionNumber;
  }

  /**
   * @return the phoneNumber
   */
  public BigDecimal getPhoneNumberAsDecimal() {
    return phoneNumber;
  }

  /**
   * @return the phoneNumber
   */
  @Override
  public String getPhoneNumber() {
    return phoneNumber != null ? phoneNumber.toPlainString() : null;
  }

  /**
   * @return the positionTitleDescription
   */
  public String getPositionTitleDescription() {
    return StringUtils.trimToEmpty(positionTitleDescription);
  }

  /**
   * @return the serviceProviderType
   */
  public Short getServiceProviderType() {
    return serviceProviderType;
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

  @JsonIgnore
  @Override
  public String getStreetAddress() {
    return this.getStreetNumber() + " " + this.getStreetName();
  }

  @JsonIgnore
  @Override
  public String getCity() {
    return this.getCityName();
  }

  @JsonIgnore
  @Override
  public String getState() {
    return this.getStateCodeType() != null ? this.getStateCodeType().toString() : null;
  }

  @JsonIgnore
  @Override
  public String getZip() {
    StringBuilder buf = new StringBuilder();

    if (this.getZipNumber() != null) {
      buf.append(getZipNumber());
    }
    if (this.getZipSuffixNumber() != null) {
      buf.append('-').append(getZipSuffixNumber());
    }

    return buf.toString();
  }

  @JsonIgnore
  @Override
  public String getCounty() {
    return null;
  }

  @JsonIgnore
  @Override
  public String getMiddleName() {
    return null;
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

  @JsonIgnore
  @Override
  public String getPhoneNumberExtension() {
    return this.getPhoneExtensionNumber() != null ? this.getPhoneExtensionNumber().toString()
        : null;
  }

  @JsonIgnore
  @Override
  public PhoneType getPhoneType() {
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
    result = prime * result + ((agencyName == null) ? 0 : agencyName.hashCode());
    result = prime * result
        + ((archiveAssociationIndicator == null) ? 0 : archiveAssociationIndicator.hashCode());
    result = prime * result + ((cityName == null) ? 0 : cityName.hashCode());
    result = prime * result + ((emailAddress == null) ? 0 : emailAddress.hashCode());
    result = prime * result + ((faxNumber == null) ? 0 : faxNumber.hashCode());
    result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
    result =
        prime * result + ((namePrefixDescription == null) ? 0 : namePrefixDescription.hashCode());
    result =
        prime * result + ((phoneExtensionNumber == null) ? 0 : phoneExtensionNumber.hashCode());
    result = prime * result + ((phoneNumber == null) ? 0 : phoneNumber.hashCode());
    result = prime * result
        + ((positionTitleDescription == null) ? 0 : positionTitleDescription.hashCode());
    result = prime * result + ((serviceProviderType == null) ? 0 : serviceProviderType.hashCode());
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
    if (!(obj instanceof ServiceProvider)) {
      return false;
    }
    ServiceProvider other = (ServiceProvider) obj;
    if (agencyName == null) {
      if (other.agencyName != null) {
        return false;
      }
    } else if (!agencyName.equals(other.agencyName)) {
      return false;
    }
    if (archiveAssociationIndicator == null) {
      if (other.archiveAssociationIndicator != null) {
        return false;
      }
    } else if (!archiveAssociationIndicator.equals(other.archiveAssociationIndicator)) {
      return false;
    }
    if (cityName == null) {
      if (other.cityName != null) {
        return false;
      }
    } else if (!cityName.equals(other.cityName)) {
      return false;
    }
    if (emailAddress == null) {
      if (other.emailAddress != null) {
        return false;
      }
    } else if (!emailAddress.equals(other.emailAddress)) {
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
    if (id == null) {
      if (other.id != null) {
        return false;
      }
    } else if (!id.equals(other.id)) {
      return false;
    }
    if (lastName == null) {
      if (other.lastName != null) {
        return false;
      }
    } else if (!lastName.equals(other.lastName)) {
      return false;
    }
    if (namePrefixDescription == null) {
      if (other.namePrefixDescription != null) {
        return false;
      }
    } else if (!namePrefixDescription.equals(other.namePrefixDescription)) {
      return false;
    }
    if (phoneExtensionNumber == null) {
      if (other.phoneExtensionNumber != null) {
        return false;
      }
    } else if (!phoneExtensionNumber.equals(other.phoneExtensionNumber)) {
      return false;
    }
    if (phoneNumber == null) {
      if (other.phoneNumber != null) {
        return false;
      }
    } else if (!phoneNumber.equals(other.phoneNumber)) {
      return false;
    }
    if (positionTitleDescription == null) {
      if (other.positionTitleDescription != null) {
        return false;
      }
    } else if (!positionTitleDescription.equals(other.positionTitleDescription)) {
      return false;
    }
    if (serviceProviderType == null) {
      if (other.serviceProviderType != null) {
        return false;
      }
    } else if (!serviceProviderType.equals(other.serviceProviderType)) {
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

}
