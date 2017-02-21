package gov.ca.cwds.data.persistence.cms;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import gov.ca.cwds.data.CmsSystemCodeDeserializer;
import gov.ca.cwds.data.SystemCodeSerializer;
import gov.ca.cwds.data.std.ApiPersonAware;

@MappedSuperclass
public abstract class BaseSubstituteCareProvider extends CmsPersistentObject
    implements ApiPersonAware {

  /**
   * Base serialization version. Increment per change.
   */
  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "IDENTIFIER", length = CMS_ID_LEN)
  protected String id;

  @Column(name = "ADD_TEL_NO")
  protected BigDecimal additionalPhoneNumber;

  @Type(type = "integer")
  @Column(name = "ADD_EXT_NO")
  protected Integer additionlPhoneExtensionNumber;

  @Column(name = "YR_INC_AMT")
  protected BigDecimal annualIncomeAmount;

  @Type(type = "date")
  @Column(name = "BIRTH_DT")
  protected Date birthDate;

  @Column(name = "CA_DLIC_NO")
  protected String caDriverLicenseNumber;

  @Column(name = "CITY_NM")
  protected String cityName;

  @SystemCodeSerializer(logical = true, description = true)
  @JsonDeserialize(using = CmsSystemCodeDeserializer.class)
  @Type(type = "short")
  @Column(name = "EDUCATION")
  protected Short educationType;

  @Column(name = "EMAIL_ADDR")
  protected String emailAddress;

  @Column(name = "EMPLYR_NM")
  protected String employerName;

  @SystemCodeSerializer(logical = true, description = true)
  @JsonDeserialize(using = CmsSystemCodeDeserializer.class)
  @Type(type = "short")
  @Column(name = "EMPL_STAT")
  protected Short employmentStatusType;

  @Column(name = "ETH_UD_CD")
  protected String ethUnableToDetReasonCode;

  @Column(name = "FIRST_NM")
  protected String firstName;

  @Column(name = "FRG_ADRT_B")
  protected String foreignAddressIndicatorVar;

  @Column(name = "GENDER_IND")
  protected String genderIndicator;

  @Column(name = "HISP_UD_CD")
  protected String hispUnableToDetReasonCode;

  @Column(name = "HISP_CD")
  protected String hispanicOriginCode;

  @SystemCodeSerializer(logical = true, description = true)
  @JsonDeserialize(using = CmsSystemCodeDeserializer.class)
  @Type(type = "short")
  @Column(name = "IND_TRBC")
  protected Short indianTribeType;

  @Column(name = "LAST_NM")
  protected String lastName;

  @Column(name = "LISOWNIND")
  protected String lisOwnershipIndicator;

  @Column(name = "LIS_PER_ID")
  protected String lisPersonId;

  @SystemCodeSerializer(logical = true, description = true)
  @JsonDeserialize(using = CmsSystemCodeDeserializer.class)
  @Type(type = "short")
  @Column(name = "MRTL_STC")
  protected Short maritalStatusType;

  @Column(name = "MID_INI_NM")
  protected String middleInitialName;

  @Column(name = "NMPRFX_DSC")
  protected String namePrefixDescription;

  @Column(name = "PASSBC_CD")
  protected String passedBackgroundCheckCode;

  @SystemCodeSerializer(logical = true, description = true)
  @JsonDeserialize(using = CmsSystemCodeDeserializer.class)
  @Type(type = "short")
  @Column(name = "PRIM_INC")
  protected Short primaryIncomeType;

  @Column(name = "RESOST_IND")
  protected String residedOutOfStateIndicator;

  @SystemCodeSerializer(logical = true, description = true)
  @JsonDeserialize(using = CmsSystemCodeDeserializer.class)
  @Type(type = "short")
  @Column(name = "SEC_INC")
  protected Short secondaryIncomeType;

  @Column(name = "SS_NO")
  protected String socialSecurityNumber;

  @SystemCodeSerializer(logical = true, description = true)
  @JsonDeserialize(using = CmsSystemCodeDeserializer.class)
  @Type(type = "short")
  @Column(name = "STATE_C")
  protected Short stateCodeType;

  @Column(name = "STREET_NM")
  protected String streetName;

  @Column(name = "STREET_NO")
  protected String streetNumber;

  @Column(name = "SUFX_TLDSC")
  protected String suffixTitleDescription;

  @Type(type = "integer")
  @Column(name = "ZIP_NO")
  protected Integer zipNumber;

  @Type(type = "short")
  @Column(name = "ZIP_SFX_NO")
  protected Short zipSuffixNumber;

  public BaseSubstituteCareProvider() {
    super();
  }

  public BaseSubstituteCareProvider(String lastUpdatedId) {
    super(lastUpdatedId);
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
  @Override
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
  @Override
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
  @Override
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

  @JsonIgnore
  @Override
  public String getMiddleName() {
    return this.getMiddleInitialName();
  }

  @JsonIgnore
  @Override
  public String getGender() {
    return this.getGenderIndicator();
  }

  @JsonIgnore
  @Override
  public String getSsn() {
    return this.getSocialSecurityNumber();
  }

  @JsonIgnore
  @Override
  public String getNameSuffix() {
    return this.getSuffixTitleDescription();
  }

}
