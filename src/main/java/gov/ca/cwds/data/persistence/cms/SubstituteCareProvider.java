package gov.ca.cwds.data.persistence.cms;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;
import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import gov.ca.cwds.data.CmsSystemCodeDeserializer;
import gov.ca.cwds.data.SystemCodeSerializer;
import gov.ca.cwds.data.persistence.PersistentObject;


/**
 * {@link PersistentObject} representing a SubstituteCareProvider
 * 
 * @author CWDS API Team
 */
@NamedQueries({
    @NamedQuery(name = "gov.ca.cwds.data.persistence.cms.SubstituteCareProvider.findAll",
        query = "FROM SubstituteCareProvider"),
    @NamedQuery(
        name = "gov.ca.cwds.data.persistence.cms.SubstituteCareProvider.findAllUpdatedAfter",
        query = "FROM SubstituteCareProvider WHERE lastUpdatedTime > :after")})
@Entity
@Table(schema = "CWSINT", name = "SB_PVDRT")
@JsonPropertyOrder(alphabetic = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SubstituteCareProvider extends CmsPersistentObject {

  /**
   * Base serialization version. Increment per change.
   */
  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "IDENTIFIER", length = CMS_ID_LEN)
  private String id;

  @Column(name = "ADD_TEL_NO")
  private BigDecimal additionalPhoneNumber;

  @Type(type = "integer")
  @Column(name = "ADD_EXT_NO")
  private Integer additionlPhoneExtensionNumber;

  @Column(name = "YR_INC_AMT")
  private BigDecimal annualIncomeAmount;

  @Type(type = "date")
  @Column(name = "BIRTH_DT")
  private Date birthDate;

  @Column(name = "CA_DLIC_NO")
  private String caDriverLicenseNumber;

  @Column(name = "CITY_NM")
  private String cityName;

  @SystemCodeSerializer(logical = true, description = true)
  @JsonDeserialize(using = CmsSystemCodeDeserializer.class)
  @Type(type = "short")
  @Column(name = "EDUCATION")
  private Short educationType;

  @Column(name = "EMAIL_ADDR")
  private String emailAddress;

  @Column(name = "EMPLYR_NM")
  private String employerName;

  @SystemCodeSerializer(logical = true, description = true)
  @JsonDeserialize(using = CmsSystemCodeDeserializer.class)
  @Type(type = "short")
  @Column(name = "EMPL_STAT")
  private Short employmentStatusType;

  @Column(name = "ETH_UD_CD")
  private String ethUnableToDetReasonCode;

  @Column(name = "FIRST_NM")
  private String firstName;

  @Column(name = "FRG_ADRT_B")
  private String foreignAddressIndicatorVar;

  @Column(name = "GENDER_IND")
  private String genderIndicator;

  @Column(name = "HISP_UD_CD")
  private String hispUnableToDetReasonCode;

  @Column(name = "HISP_CD")
  private String hispanicOriginCode;

  @SystemCodeSerializer(logical = true, description = true)
  @JsonDeserialize(using = CmsSystemCodeDeserializer.class)
  @Type(type = "short")
  @Column(name = "IND_TRBC")
  private Short indianTribeType;

  @Column(name = "LAST_NM")
  private String lastName;

  @Column(name = "LISOWNIND")
  private String lisOwnershipIndicator;

  @Column(name = "LIS_PER_ID")
  private String lisPersonId;

  @SystemCodeSerializer(logical = true, description = true)
  @JsonDeserialize(using = CmsSystemCodeDeserializer.class)
  @Type(type = "short")
  @Column(name = "MRTL_STC")
  private Short maritalStatusType;

  @Column(name = "MID_INI_NM")
  private String middleInitialName;

  @Column(name = "NMPRFX_DSC")
  private String namePrefixDescription;

  @Column(name = "PASSBC_CD")
  private String passedBackgroundCheckCode;

  @SystemCodeSerializer(logical = true, description = true)
  @JsonDeserialize(using = CmsSystemCodeDeserializer.class)
  @Type(type = "short")
  @Column(name = "PRIM_INC")
  private Short primaryIncomeType;

  @Column(name = "RESOST_IND")
  private String residedOutOfStateIndicator;

  @SystemCodeSerializer(logical = true, description = true)
  @JsonDeserialize(using = CmsSystemCodeDeserializer.class)
  @Type(type = "short")
  @Column(name = "SEC_INC")
  private Short secondaryIncomeType;

  @Column(name = "SS_NO")
  private String socialSecurityNumber;

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
  public SubstituteCareProvider() {
    super();
  }

  /**
   * @param id The id
   * @param additionalPhoneNumber The additionalPhoneNumber
   * @param additionlPhoneExtensionNumber The additionlPhoneExtensionNumber
   * @param annualIncomeAmount The annualIncomeAmount
   * @param birthDate The birthDate
   * @param caDriverLicenseNumber The caDriverLicenseNumber
   * @param cityName The cityName
   * @param educationType The educationType
   * @param emailAddress The emailAddress
   * @param employerName The employerName
   * @param employmentStatusType The employmentStatusType
   * @param ethUnableToDetReasonCode The ethUnableToDetReasonCode
   * @param firstName The firstName
   * @param foreignAddressIndicatorVar The foreignAddressIndicatorVar
   * @param genderIndicator The genderIndicator
   * @param hispUnableToDetReasonCode The hispUnableToDetReasonCode
   * @param hispanicOriginCode The hispanicOriginCode
   * @param indianTribeType The indianTribeType
   * @param lastName The lastName
   * @param lisOwnershipIndicator The lisOwnershipIndicator
   * @param lisPersonId The lisPersonId
   * @param maritalStatusType The maritalStatusType
   * @param middleInitialName The middleInitialName
   * @param namePrefixDescription The namePrefixDescription
   * @param passedBackgroundCheckCode The passedBackgroundCheckCode
   * @param primaryIncomeType The primaryIncomeType
   * @param residedOutOfStateIndicator The residedOutOfStateIndicator
   * @param secondaryIncomeType The secondaryIncomeType
   * @param socialSecurityNumber The socialSecurityNumber
   * @param stateCodeType The stateCodeType
   * @param streetName The streetName
   * @param streetNumber The streetNumber
   * @param suffixTitleDescription The suffixTitleDescription
   * @param zipNumber The zipNumber
   * @param zipSuffixNumber The zipSuffixNumber
   */
  public SubstituteCareProvider(String id, BigDecimal additionalPhoneNumber,
      Integer additionlPhoneExtensionNumber, BigDecimal annualIncomeAmount, Date birthDate,
      String caDriverLicenseNumber, String cityName, Short educationType, String emailAddress,
      String employerName, Short employmentStatusType, String ethUnableToDetReasonCode,
      String firstName, String foreignAddressIndicatorVar, String genderIndicator,
      String hispUnableToDetReasonCode, String hispanicOriginCode, Short indianTribeType,
      String lastName, String lisOwnershipIndicator, String lisPersonId, Short maritalStatusType,
      String middleInitialName, String namePrefixDescription, String passedBackgroundCheckCode,
      Short primaryIncomeType, String residedOutOfStateIndicator, Short secondaryIncomeType,
      String socialSecurityNumber, Short stateCodeType, String streetName, String streetNumber,
      String suffixTitleDescription, Integer zipNumber, short zipSuffixNumber) {
    super();
    this.id = id;
    this.additionalPhoneNumber = additionalPhoneNumber;
    this.additionlPhoneExtensionNumber = additionlPhoneExtensionNumber;
    this.annualIncomeAmount = annualIncomeAmount;
    this.birthDate = birthDate;
    this.caDriverLicenseNumber = caDriverLicenseNumber;
    this.cityName = cityName;
    this.educationType = educationType;
    this.emailAddress = emailAddress;
    this.employerName = employerName;
    this.employmentStatusType = employmentStatusType;
    this.ethUnableToDetReasonCode = ethUnableToDetReasonCode;
    this.firstName = firstName;
    this.foreignAddressIndicatorVar = foreignAddressIndicatorVar;
    this.genderIndicator = genderIndicator;
    this.hispUnableToDetReasonCode = hispUnableToDetReasonCode;
    this.hispanicOriginCode = hispanicOriginCode;
    this.indianTribeType = indianTribeType;
    this.lastName = lastName;
    this.lisOwnershipIndicator = lisOwnershipIndicator;
    this.lisPersonId = lisPersonId;
    this.maritalStatusType = maritalStatusType;
    this.middleInitialName = middleInitialName;
    this.namePrefixDescription = namePrefixDescription;
    this.passedBackgroundCheckCode = passedBackgroundCheckCode;
    this.primaryIncomeType = primaryIncomeType;
    this.residedOutOfStateIndicator = residedOutOfStateIndicator;
    this.secondaryIncomeType = secondaryIncomeType;
    this.socialSecurityNumber = socialSecurityNumber;
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
  @Override
  public String getPrimaryKey() {
    return getId();
  }

  /**
   * @return the id
   */
  public String getId() {
    return StringUtils.trimToEmpty(id);
  }

  /**
   * @return the additionalPhoneNumber
   */
  public BigDecimal getAdditionalPhoneNumber() {
    return additionalPhoneNumber;
  }

  /**
   * @return the additionlPhoneExtensionNumber
   */
  public Integer getAdditionlPhoneExtensionNumber() {
    return additionlPhoneExtensionNumber;
  }

  /**
   * @return the annualIncomeAmount
   */
  public BigDecimal getAnnualIncomeAmount() {
    return annualIncomeAmount;
  }

  /**
   * @return the birthDate
   */
  public Date getBirthDate() {
    return birthDate;
  }

  /**
   * @return the caDriverLicenseNumber
   */
  public String getCaDriverLicenseNumber() {
    return StringUtils.trimToEmpty(caDriverLicenseNumber);
  }

  /**
   * @return the cityName
   */
  public String getCityName() {
    return StringUtils.trimToEmpty(cityName);
  }

  /**
   * @return the educationType
   */
  public Short getEducationType() {
    return educationType;
  }

  /**
   * @return the emailAddress
   */
  public String getEmailAddress() {
    return StringUtils.trimToEmpty(emailAddress);
  }

  /**
   * @return the employerName
   */
  public String getEmployerName() {
    return StringUtils.trimToEmpty(employerName);
  }

  /**
   * @return the employmentStatusType
   */
  public Short getEmploymentStatusType() {
    return employmentStatusType;
  }

  /**
   * @return the ethUnableToDetReasonCode
   */
  public String getEthUnableToDetReasonCode() {
    return StringUtils.trimToEmpty(ethUnableToDetReasonCode);
  }

  /**
   * @return the firstName
   */
  public String getFirstName() {
    return StringUtils.trimToEmpty(firstName);
  }

  /**
   * @return the foreignAddressIndicatorVar
   */
  public String getForeignAddressIndicatorVar() {
    return StringUtils.trimToEmpty(foreignAddressIndicatorVar);
  }

  /**
   * @return the genderIndicator
   */
  public String getGenderIndicator() {
    return StringUtils.trimToEmpty(genderIndicator);
  }

  /**
   * @return the hispUnableToDetReasonCode
   */
  public String getHispUnableToDetReasonCode() {
    return StringUtils.trimToEmpty(hispUnableToDetReasonCode);
  }

  /**
   * @return the hispanicOriginCode
   */
  public String getHispanicOriginCode() {
    return StringUtils.trimToEmpty(hispanicOriginCode);
  }

  /**
   * @return the indianTribeType
   */
  public Short getIndianTribeType() {
    return indianTribeType;
  }

  /**
   * @return the lastName
   */
  public String getLastName() {
    return StringUtils.trimToEmpty(lastName);
  }

  /**
   * @return the lisOwnershipIndicator
   */
  public String getLisOwnershipIndicator() {
    return StringUtils.trimToEmpty(lisOwnershipIndicator);
  }

  /**
   * @return the lisPersonId
   */
  public String getLisPersonId() {
    return StringUtils.trimToEmpty(lisPersonId);
  }

  /**
   * @return the maritalStatusType
   */
  public Short getMaritalStatusType() {
    return maritalStatusType;
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
   * @return the passedBackgroundCheckCode
   */
  public String getPassedBackgroundCheckCode() {
    return StringUtils.trimToEmpty(passedBackgroundCheckCode);
  }

  /**
   * @return the primaryIncomeType
   */
  public Short getPrimaryIncomeType() {
    return primaryIncomeType;
  }

  /**
   * @return the residedOutOfStateIndicator
   */
  public String getResidedOutOfStateIndicator() {
    return StringUtils.trimToEmpty(residedOutOfStateIndicator);
  }

  /**
   * @return the secondaryIncomeType
   */
  public Short getSecondaryIncomeType() {
    return secondaryIncomeType;
  }

  /**
   * @return the socialSecurityNumber
   */
  public String getSocialSecurityNumber() {
    return StringUtils.trimToEmpty(socialSecurityNumber);
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
    result =
        prime * result + ((additionalPhoneNumber == null) ? 0 : additionalPhoneNumber.hashCode());
    result = prime * result
        + ((additionlPhoneExtensionNumber == null) ? 0 : additionlPhoneExtensionNumber.hashCode());
    result = prime * result + ((annualIncomeAmount == null) ? 0 : annualIncomeAmount.hashCode());
    result = prime * result + ((birthDate == null) ? 0 : birthDate.hashCode());
    result =
        prime * result + ((caDriverLicenseNumber == null) ? 0 : caDriverLicenseNumber.hashCode());
    result = prime * result + ((cityName == null) ? 0 : cityName.hashCode());
    result = prime * result + ((educationType == null) ? 0 : educationType.hashCode());
    result = prime * result + ((emailAddress == null) ? 0 : emailAddress.hashCode());
    result = prime * result + ((employerName == null) ? 0 : employerName.hashCode());
    result =
        prime * result + ((employmentStatusType == null) ? 0 : employmentStatusType.hashCode());
    result = prime * result
        + ((ethUnableToDetReasonCode == null) ? 0 : ethUnableToDetReasonCode.hashCode());
    result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
    result = prime * result
        + ((foreignAddressIndicatorVar == null) ? 0 : foreignAddressIndicatorVar.hashCode());
    result = prime * result + ((genderIndicator == null) ? 0 : genderIndicator.hashCode());
    result = prime * result
        + ((hispUnableToDetReasonCode == null) ? 0 : hispUnableToDetReasonCode.hashCode());
    result = prime * result + ((hispanicOriginCode == null) ? 0 : hispanicOriginCode.hashCode());
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    result = prime * result + ((indianTribeType == null) ? 0 : indianTribeType.hashCode());
    result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
    result =
        prime * result + ((lisOwnershipIndicator == null) ? 0 : lisOwnershipIndicator.hashCode());
    result = prime * result + ((lisPersonId == null) ? 0 : lisPersonId.hashCode());
    result = prime * result + ((maritalStatusType == null) ? 0 : maritalStatusType.hashCode());
    result = prime * result + ((middleInitialName == null) ? 0 : middleInitialName.hashCode());
    result =
        prime * result + ((namePrefixDescription == null) ? 0 : namePrefixDescription.hashCode());
    result = prime * result
        + ((passedBackgroundCheckCode == null) ? 0 : passedBackgroundCheckCode.hashCode());
    result = prime * result + ((primaryIncomeType == null) ? 0 : primaryIncomeType.hashCode());
    result = prime * result
        + ((residedOutOfStateIndicator == null) ? 0 : residedOutOfStateIndicator.hashCode());
    result = prime * result + ((secondaryIncomeType == null) ? 0 : secondaryIncomeType.hashCode());
    result =
        prime * result + ((socialSecurityNumber == null) ? 0 : socialSecurityNumber.hashCode());
    result = prime * result + ((stateCodeType == null) ? 0 : stateCodeType.hashCode());
    result = prime * result + ((streetName == null) ? 0 : streetName.hashCode());
    result = prime * result + ((streetNumber == null) ? 0 : streetNumber.hashCode());
    result =
        prime * result + ((suffixTitleDescription == null) ? 0 : suffixTitleDescription.hashCode());
    result = prime * result + ((zipNumber == null) ? 0 : zipNumber.hashCode());
    result = prime * result + ((zipSuffixNumber == null) ? 0 : zipSuffixNumber.hashCode());
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
    if (!(obj instanceof SubstituteCareProvider)) {
      return false;
    }
    SubstituteCareProvider other = (SubstituteCareProvider) obj;
    if (additionalPhoneNumber == null) {
      if (other.additionalPhoneNumber != null) {
        return false;
      }
    } else if (!additionalPhoneNumber.equals(other.additionalPhoneNumber)) {
      return false;
    }
    if (additionlPhoneExtensionNumber == null) {
      if (other.additionlPhoneExtensionNumber != null) {
        return false;
      }
    } else if (!additionlPhoneExtensionNumber.equals(other.additionlPhoneExtensionNumber)) {
      return false;
    }
    if (annualIncomeAmount == null) {
      if (other.annualIncomeAmount != null) {
        return false;
      }
    } else if (!annualIncomeAmount.equals(other.annualIncomeAmount)) {
      return false;
    }
    if (birthDate == null) {
      if (other.birthDate != null) {
        return false;
      }
    } else if (!birthDate.equals(other.birthDate)) {
      return false;
    }
    if (caDriverLicenseNumber == null) {
      if (other.caDriverLicenseNumber != null) {
        return false;
      }
    } else if (!caDriverLicenseNumber.equals(other.caDriverLicenseNumber)) {
      return false;
    }
    if (cityName == null) {
      if (other.cityName != null) {
        return false;
      }
    } else if (!cityName.equals(other.cityName)) {
      return false;
    }
    if (educationType == null) {
      if (other.educationType != null) {
        return false;
      }
    } else if (!educationType.equals(other.educationType)) {
      return false;
    }
    if (emailAddress == null) {
      if (other.emailAddress != null) {
        return false;
      }
    } else if (!emailAddress.equals(other.emailAddress)) {
      return false;
    }
    if (employerName == null) {
      if (other.employerName != null) {
        return false;
      }
    } else if (!employerName.equals(other.employerName)) {
      return false;
    }
    if (employmentStatusType == null) {
      if (other.employmentStatusType != null) {
        return false;
      }
    } else if (!employmentStatusType.equals(other.employmentStatusType)) {
      return false;
    }
    if (ethUnableToDetReasonCode == null) {
      if (other.ethUnableToDetReasonCode != null) {
        return false;
      }
    } else if (!ethUnableToDetReasonCode.equals(other.ethUnableToDetReasonCode)) {
      return false;
    }
    if (firstName == null) {
      if (other.firstName != null) {
        return false;
      }
    } else if (!firstName.equals(other.firstName)) {
      return false;
    }
    if (foreignAddressIndicatorVar == null) {
      if (other.foreignAddressIndicatorVar != null) {
        return false;
      }
    } else if (!foreignAddressIndicatorVar.equals(other.foreignAddressIndicatorVar)) {
      return false;
    }
    if (genderIndicator == null) {
      if (other.genderIndicator != null) {
        return false;
      }
    } else if (!genderIndicator.equals(other.genderIndicator)) {
      return false;
    }
    if (hispUnableToDetReasonCode == null) {
      if (other.hispUnableToDetReasonCode != null) {
        return false;
      }
    } else if (!hispUnableToDetReasonCode.equals(other.hispUnableToDetReasonCode)) {
      return false;
    }
    if (hispanicOriginCode == null) {
      if (other.hispanicOriginCode != null) {
        return false;
      }
    } else if (!hispanicOriginCode.equals(other.hispanicOriginCode)) {
      return false;
    }
    if (id == null) {
      if (other.id != null) {
        return false;
      }
    } else if (!id.equals(other.id)) {
      return false;
    }
    if (indianTribeType == null) {
      if (other.indianTribeType != null) {
        return false;
      }
    } else if (!indianTribeType.equals(other.indianTribeType)) {
      return false;
    }
    if (lastName == null) {
      if (other.lastName != null) {
        return false;
      }
    } else if (!lastName.equals(other.lastName)) {
      return false;
    }
    if (lisOwnershipIndicator == null) {
      if (other.lisOwnershipIndicator != null) {
        return false;
      }
    } else if (!lisOwnershipIndicator.equals(other.lisOwnershipIndicator)) {
      return false;
    }
    if (lisPersonId == null) {
      if (other.lisPersonId != null) {
        return false;
      }
    } else if (!lisPersonId.equals(other.lisPersonId)) {
      return false;
    }
    if (maritalStatusType == null) {
      if (other.maritalStatusType != null) {
        return false;
      }
    } else if (!maritalStatusType.equals(other.maritalStatusType)) {
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
    if (passedBackgroundCheckCode == null) {
      if (other.passedBackgroundCheckCode != null) {
        return false;
      }
    } else if (!passedBackgroundCheckCode.equals(other.passedBackgroundCheckCode)) {
      return false;
    }
    if (primaryIncomeType == null) {
      if (other.primaryIncomeType != null) {
        return false;
      }
    } else if (!primaryIncomeType.equals(other.primaryIncomeType)) {
      return false;
    }
    if (residedOutOfStateIndicator == null) {
      if (other.residedOutOfStateIndicator != null) {
        return false;
      }
    } else if (!residedOutOfStateIndicator.equals(other.residedOutOfStateIndicator)) {
      return false;
    }
    if (secondaryIncomeType == null) {
      if (other.secondaryIncomeType != null) {
        return false;
      }
    } else if (!secondaryIncomeType.equals(other.secondaryIncomeType)) {
      return false;
    }
    if (socialSecurityNumber == null) {
      if (other.socialSecurityNumber != null) {
        return false;
      }
    } else if (!socialSecurityNumber.equals(other.socialSecurityNumber)) {
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

