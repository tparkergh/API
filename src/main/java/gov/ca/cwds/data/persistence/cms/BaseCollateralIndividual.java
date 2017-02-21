package gov.ca.cwds.data.persistence.cms;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import gov.ca.cwds.data.CmsSystemCodeDeserializer;
import gov.ca.cwds.data.SystemCodeSerializer;
import gov.ca.cwds.data.std.ApiAddressAware;
import gov.ca.cwds.data.std.ApiPersonAware;

public abstract class BaseCollateralIndividual extends CmsPersistentObject
    implements ApiPersonAware, ApiAddressAware {

  private static final long serialVersionUID = 1L;

  @Column(name = "BADGE_NO")
  protected String badgeNumber;

  @Type(type = "date")
  @Column(name = "BIRTH_DT")
  protected Date birthDate;

  @Column(name = "CITY_NM")
  protected String cityName;

  @Column(name = "COMNT_DSC")
  protected String commentDescription;

  @Column(name = "EMAIL_ADDR")
  protected String emailAddress;

  @Column(name = "EMPLYR_NM")
  protected String employerName;

  @Column(name = "ESTBLSH_CD")
  protected String establishedForCode;

  @Column(name = "FAX_NO")
  protected BigDecimal faxNumber;

  @Column(name = "FIRST_NM")
  protected String firstName;

  @Column(name = "FRG_ADRT_B")
  protected String foreignAddressIndicatorVariable;

  @Column(name = "GENDER_CD")
  protected String genderCode;

  @Id
  @Column(name = "IDENTIFIER", length = CMS_ID_LEN)
  protected String id;

  @Column(name = "LAST_NM")
  protected String lastName;

  @SystemCodeSerializer(logical = true, description = true)
  @JsonDeserialize(using = CmsSystemCodeDeserializer.class)
  @Type(type = "short")
  @Column(name = "MRTL_STC")
  protected Short maritalStatusType;

  @Column(name = "MID_INI_NM")
  protected String middleInitialName;

  @Column(name = "NMPRFX_DSC")
  protected String namePrefixDescription;

  @Type(type = "integer")
  @Column(name = "PRM_EXT_NO")
  protected Integer primaryExtensionNumber;

  @Column(name = "PRM_TEL_NO")
  protected BigInteger primaryPhoneNo;

  @Column(name = "RESOST_IND")
  protected String residedOutOfStateIndicator;

  @SystemCodeSerializer(logical = true, description = true)
  @JsonDeserialize(using = CmsSystemCodeDeserializer.class)
  @Type(type = "short")
  @Column(name = "STATE_C")
  protected Short stateCode;

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

  /**
   * @return serialVersionUID
   */
  public static long getSerialversionuid() {
    return serialVersionUID;
  }

  public BaseCollateralIndividual() {
    super();
  }

  public BaseCollateralIndividual(String lastUpdatedId) {
    super(lastUpdatedId);
  }

  /**
   * @return badgeNumber
   */
  public String getBadgeNumber() {
    return StringUtils.trimToEmpty(badgeNumber);
  }

  /**
   * @return birthDate
   */
  @Override
  public Date getBirthDate() {
    return birthDate;
  }

  /**
   * @return cityName
   */
  public String getCityName() {
    return StringUtils.trimToEmpty(cityName);
  }

  /**
   * @return commentDescription
   */
  public String getCommentDescription() {
    return StringUtils.trimToEmpty(commentDescription);
  }

  /**
   * @return emailAddress
   */
  public String getEmailAddress() {
    return StringUtils.trimToEmpty(emailAddress);
  }

  /**
   * @return employerName
   */
  public String getEmployerName() {
    return StringUtils.trimToEmpty(employerName);
  }

  /**
   * @return establishedForCode
   */
  public String getEstablishedForCode() {
    return StringUtils.trimToEmpty(establishedForCode);
  }

  /**
   * @return faxNumber
   */
  public BigDecimal getFaxNumber() {
    return faxNumber;
  }

  /**
   * @return firstName
   */
  @Override
  public String getFirstName() {
    return StringUtils.trimToEmpty(firstName);
  }

  /**
   * @return foreignAddressIndicatorVariable
   */
  public String getForeignAddressIndicatorVariable() {
    return foreignAddressIndicatorVariable;
  }

  /**
   * @return genderCode
   */
  public String getGenderCode() {
    return StringUtils.trimToEmpty(genderCode);
  }

  /**
   * @return id
   */
  public String getId() {
    return id;
  }

  /**
   * @return lastName
   */
  @Override
  public String getLastName() {
    return StringUtils.trimToEmpty(lastName);
  }

  /**
   * @return maritalStatus
   */
  public Short getMaritalStatus() {
    return maritalStatusType;
  }

  /**
   * @return maritalStatus
   */
  public String getMiddleInitialName() {
    return StringUtils.trimToEmpty(middleInitialName);
  }

  /**
   * @return namePrefixDescription
   */
  public String getNamePrefixDescription() {
    return StringUtils.trimToEmpty(namePrefixDescription);
  }

  /**
   * @return primaryExtensionNumber
   */
  public Integer getPrimaryExtensionNumber() {
    return primaryExtensionNumber;
  }

  /**
   * @return primaryPhoneNo
   */
  public BigInteger getPrimaryPhoneNo() {
    return primaryPhoneNo;
  }

  /**
   * @return residedOutOfStateIndicator
   */
  public String getResidedOutOfStateIndicator() {
    return residedOutOfStateIndicator;
  }

  /**
   * @return stateCode
   */
  public Short getStateCode() {
    return stateCode;
  }

  /**
   * @return streetName
   */
  public String getStreetName() {
    return StringUtils.trimToEmpty(streetName);
  }

  /**
   * @return streetNumber
   */
  public String getStreetNumber() {
    return StringUtils.trimToEmpty(streetNumber);
  }

  /**
   * @return suffixTitleDescription
   */
  public String getSuffixTitleDescription() {
    return StringUtils.trimToEmpty(suffixTitleDescription);
  }

  /**
   * @return zipNumber
   */
  public Integer getZipNumber() {
    return zipNumber;
  }

  /**
   * @return zipSuffixNumber
   */
  public Short getZipSuffixNumber() {
    return zipSuffixNumber;
  }

  @JsonIgnore
  @Override
  public String getPrimaryKey() {
    return getId();
  }

  @JsonIgnore
  @Override
  public String getMiddleName() {
    return this.getMiddleInitialName();
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
    return this.getSuffixTitleDescription();
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
    return this.stateCode != null ? this.stateCode.toString() : null;
  }

  @JsonIgnore
  @Override
  public String getZip() {
    return this.getZipNumber() != null ? this.getZipNumber().toString() : null;
  }

  @JsonIgnore
  @Override
  public String getCounty() {
    return null;
  }

}
