package gov.ca.cwds.data.persistence.cms;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.NamedNativeQueries;
import org.hibernate.annotations.NamedNativeQuery;
import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import gov.ca.cwds.data.CmsSystemCodeDeserializer;
import gov.ca.cwds.data.IAddressAware;
import gov.ca.cwds.data.IPersonAware;
import gov.ca.cwds.data.SystemCodeSerializer;
import gov.ca.cwds.data.persistence.PersistentObject;


/**
 * {@link PersistentObject} representing a CollateralIndividual
 * 
 * @author CWDS API Team
 */
// @NamedQueries({
// @NamedQuery(name = "gov.ca.cwds.data.persistence.cms.CollateralIndividual.findAll",
// query = "FROM CollateralIndividual WHERE IDENTIFIER IN (SELECT collateralIndividualId from
// ClientCollateral "
// + "WHERE activeIndicator = 'Y' AND clientId IN "
// + "(SELECT id FROM Client WHERE sensitivityIndicator = 'N' AND soc158SealedClientIndicator =
// 'N'))"),
// @NamedQuery(name = "gov.ca.cwds.data.persistence.cms.CollateralIndividual.findAllUpdatedAfter",
// query = "FROM CollateralIndividual WHERE lastUpdatedTime > :after AND IDENTIFIER IN (SELECT
// collateralIndividualId from ClientCollateral "
// + "WHERE activeIndicator = 'Y' AND clientId IN "
// + "(SELECT id FROM Client WHERE sensitivityIndicator = 'N' AND soc158SealedClientIndicator =
// 'N'))")})
@NamedNativeQueries({@NamedNativeQuery(
    name = "gov.ca.cwds.data.persistence.cms.CollateralIndividual.findAllByBucket",
    query = "select z.IDENTIFIER, z.BADGE_NO, z.CITY_NM, z.EMPLYR_NM, z.FAX_NO, "
        + "z.FIRST_NM, z.FRG_ADRT_B, z.LAST_NM, z.MID_INI_NM, z.NMPRFX_DSC, "
        + "z.PRM_TEL_NO, z.PRM_EXT_NO, z.STATE_C, z.STREET_NM, z.STREET_NO, "
        + "z.SUFX_TLDSC, z.ZIP_NO, z.LST_UPD_ID, z.LST_UPD_TS, z.ZIP_SFX_NO, "
        + "z.COMNT_DSC, z.GENDER_CD, z.BIRTH_DT, z.MRTL_STC, z.EMAIL_ADDR, "
        + "z.ESTBLSH_CD, z.ESTBLSH_ID, z.RESOST_IND "
        + "from ( select mod(y.rn, :total_buckets) + 1 as bucket, y.* "
        + "from ( select row_number() over (order by 1) as rn, x.* "
        + "from ( select c.* from cwsint.COLTRL_T c "
        + ") x ) y ) z where z.bucket = :bucket_num for read only",
    resultClass = CollateralIndividual.class)})
@Entity
@Table(name = "COLTRL_T")
@JsonPropertyOrder(alphabetic = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CollateralIndividual extends CmsPersistentObject
    implements IPersonAware, IAddressAware {

  private static final long serialVersionUID = 1L;

  @Column(name = "BADGE_NO")
  private String badgeNumber;

  @Type(type = "date")
  @Column(name = "BIRTH_DT")
  private Date birthDate;

  @Column(name = "CITY_NM")
  private String cityName;

  @Column(name = "COMNT_DSC")
  private String commentDescription;

  @Column(name = "EMAIL_ADDR")
  private String emailAddress;

  @Column(name = "EMPLYR_NM")
  private String employerName;

  @Column(name = "ESTBLSH_CD")
  private String establishedForCode;

  @Column(name = "FAX_NO")
  private BigDecimal faxNumber;

  @Column(name = "FIRST_NM")
  private String firstName;

  @Column(name = "FRG_ADRT_B")
  private String foreignAddressIndicatorVariable;

  @Column(name = "GENDER_CD")
  private String genderCode;

  @Id
  @Column(name = "IDENTIFIER", length = CMS_ID_LEN)
  private String id;

  @Column(name = "LAST_NM")
  private String lastName;

  @SystemCodeSerializer(logical = true, description = true)
  @JsonDeserialize(using = CmsSystemCodeDeserializer.class)
  @Type(type = "short")
  @Column(name = "MRTL_STC")
  private Short maritalStatusType;

  @Column(name = "MID_INI_NM")
  private String middleInitialName;

  @Column(name = "NMPRFX_DSC")
  private String namePrefixDescription;

  @Type(type = "integer")
  @Column(name = "PRM_EXT_NO")
  private Integer primaryExtensionNumber;

  @Column(name = "PRM_TEL_NO")
  private BigInteger primaryPhoneNo;

  @Column(name = "RESOST_IND")
  private String residedOutOfStateIndicator;

  @SystemCodeSerializer(logical = true, description = true)
  @JsonDeserialize(using = CmsSystemCodeDeserializer.class)
  @Type(type = "short")
  @Column(name = "STATE_C")
  private Short stateCode;

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
  public CollateralIndividual() {
    super();
  }

  /**
   * Constructor
   * 
   * @param badgeNumber The unique badge number
   * @param birthDate Date of birth
   * @param cityName The name of the city
   * @param commentDescription A brief description of any unusual circumstances
   * @param emailAddress The e-mail address
   * @param employerName The employer name
   * @param establishedForCode Defines each type of recipient entity
   * @param faxNumber The FAX number
   * @param firstName The first name
   * @param foreignAddressIndicatorVariable Indicates occurrences of foreign addresses
   * @param genderCode Indicates the gender
   * @param id The unique identifier
   * @param lastName The last name
   * @param maritalStatus The Martial Status type
   * @param middleInitialName The middle initial
   * @param namePrefixDescription The salutation form
   * @param primaryExtensionNumber The primary phone extension number
   * @param primaryPhoneNo The primary phone number
   * @param residedOutOfStateIndicator Indicates if reside out of state
   * @param stateCode The State Code Type
   * @param streetName The street name
   * @param streetNumber The street number
   * @param suffixTitleDescription The suffix name
   * @param zipNumber The zip code
   * @param zipSuffixNumber The zip suffix number
   */
  public CollateralIndividual(String badgeNumber, Date birthDate, String cityName,
      String commentDescription, String emailAddress, String employerName,
      String establishedForCode, BigDecimal faxNumber, String firstName,
      String foreignAddressIndicatorVariable, String genderCode, String id, String lastName,
      Short maritalStatus, String middleInitialName, String namePrefixDescription,
      Integer primaryExtensionNumber, BigInteger primaryPhoneNo, String residedOutOfStateIndicator,
      Short stateCode, String streetName, String streetNumber, String suffixTitleDescription,
      Integer zipNumber, Short zipSuffixNumber) {
    super();
    this.badgeNumber = badgeNumber;
    this.birthDate = birthDate;
    this.cityName = cityName;
    this.commentDescription = commentDescription;
    this.emailAddress = emailAddress;
    this.employerName = employerName;
    this.establishedForCode = establishedForCode;
    this.faxNumber = faxNumber;
    this.firstName = firstName;
    this.foreignAddressIndicatorVariable = foreignAddressIndicatorVariable;
    this.genderCode = genderCode;
    this.id = id;
    this.lastName = lastName;
    this.maritalStatusType = maritalStatus;
    this.middleInitialName = middleInitialName;
    this.namePrefixDescription = namePrefixDescription;
    this.primaryExtensionNumber = primaryExtensionNumber;
    this.primaryPhoneNo = primaryPhoneNo;
    this.residedOutOfStateIndicator = residedOutOfStateIndicator;
    this.stateCode = stateCode;
    this.streetName = streetName;
    this.streetNumber = streetNumber;
    this.suffixTitleDescription = suffixTitleDescription;
    this.zipNumber = zipNumber;
    this.zipSuffixNumber = zipSuffixNumber;
  }

  /**
   * @return serialVersionUID
   */
  public static long getSerialversionuid() {
    return serialVersionUID;
  }

  /**
   * @return badgeNumber
   */
  public String getBadgeNumber() {
    return badgeNumber;
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
    return cityName;
  }

  /**
   * @return commentDescription
   */
  public String getCommentDescription() {
    return commentDescription;
  }

  /**
   * @return emailAddress
   */
  public String getEmailAddress() {
    return emailAddress;
  }

  /**
   * @return employerName
   */
  public String getEmployerName() {
    return employerName;
  }

  /**
   * @return establishedForCode
   */
  public String getEstablishedForCode() {
    return establishedForCode;
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
    return firstName;
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
    return genderCode;
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
    return lastName;
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
    return middleInitialName;
  }

  /**
   * @return namePrefixDescription
   */
  public String getNamePrefixDescription() {
    return namePrefixDescription;
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
    return streetName;
  }

  /**
   * @return streetNumber
   */
  public String getStreetNumber() {
    return streetNumber;
  }

  /**
   * @return suffixTitleDescription
   */
  public String getSuffixTitleDescription() {
    return suffixTitleDescription;
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

  @Override
  public final int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((badgeNumber == null) ? 0 : badgeNumber.hashCode());
    result = prime * result + ((birthDate == null) ? 0 : birthDate.hashCode());
    result = prime * result + ((cityName == null) ? 0 : cityName.hashCode());
    result = prime * result + ((commentDescription == null) ? 0 : commentDescription.hashCode());
    result = prime * result + ((emailAddress == null) ? 0 : emailAddress.hashCode());
    result = prime * result + ((employerName == null) ? 0 : employerName.hashCode());
    result = prime * result + ((establishedForCode == null) ? 0 : establishedForCode.hashCode());
    result = prime * result + ((faxNumber == null) ? 0 : faxNumber.hashCode());
    result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
    result = prime * result + ((foreignAddressIndicatorVariable == null) ? 0
        : foreignAddressIndicatorVariable.hashCode());
    result = prime * result + ((genderCode == null) ? 0 : genderCode.hashCode());
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
    result = prime * result + ((maritalStatusType == null) ? 0 : maritalStatusType.hashCode());
    result = prime * result + ((middleInitialName == null) ? 0 : middleInitialName.hashCode());
    result =
        prime * result + ((namePrefixDescription == null) ? 0 : namePrefixDescription.hashCode());
    result =
        prime * result + ((primaryExtensionNumber == null) ? 0 : primaryExtensionNumber.hashCode());
    result = prime * result + ((primaryPhoneNo == null) ? 0 : primaryPhoneNo.hashCode());
    result = prime * result
        + ((residedOutOfStateIndicator == null) ? 0 : residedOutOfStateIndicator.hashCode());
    result = prime * result + ((stateCode == null) ? 0 : stateCode.hashCode());
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

  @Override
  public final boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (!(obj instanceof CollateralIndividual)) {
      return false;
    }
    CollateralIndividual other = (CollateralIndividual) obj;
    if (badgeNumber == null) {
      if (other.badgeNumber != null)
        return false;
    } else if (!badgeNumber.equals(other.badgeNumber))
      return false;
    if (birthDate == null) {
      if (other.birthDate != null)
        return false;
    } else if (!birthDate.equals(other.birthDate))
      return false;
    if (cityName == null) {
      if (other.cityName != null)
        return false;
    } else if (!cityName.equals(other.cityName))
      return false;
    if (commentDescription == null) {
      if (other.commentDescription != null)
        return false;
    } else if (!commentDescription.equals(other.commentDescription))
      return false;
    if (emailAddress == null) {
      if (other.emailAddress != null)
        return false;
    } else if (!emailAddress.equals(other.emailAddress))
      return false;
    if (employerName == null) {
      if (other.employerName != null)
        return false;
    } else if (!employerName.equals(other.employerName))
      return false;
    if (establishedForCode == null) {
      if (other.establishedForCode != null)
        return false;
    } else if (!establishedForCode.equals(other.establishedForCode))
      return false;
    if (faxNumber == null) {
      if (other.faxNumber != null)
        return false;
    } else if (!faxNumber.equals(other.faxNumber))
      return false;
    if (firstName == null) {
      if (other.firstName != null)
        return false;
    } else if (!firstName.equals(other.firstName))
      return false;
    if (foreignAddressIndicatorVariable == null) {
      if (other.foreignAddressIndicatorVariable != null)
        return false;
    } else if (!foreignAddressIndicatorVariable.equals(other.foreignAddressIndicatorVariable))
      return false;
    if (genderCode == null) {
      if (other.genderCode != null)
        return false;
    } else if (!genderCode.equals(other.genderCode))
      return false;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    if (lastName == null) {
      if (other.lastName != null)
        return false;
    } else if (!lastName.equals(other.lastName))
      return false;
    if (maritalStatusType == null) {
      if (other.maritalStatusType != null)
        return false;
    } else if (!maritalStatusType.equals(other.maritalStatusType))
      return false;
    if (middleInitialName == null) {
      if (other.middleInitialName != null)
        return false;
    } else if (!middleInitialName.equals(other.middleInitialName))
      return false;
    if (namePrefixDescription == null) {
      if (other.namePrefixDescription != null)
        return false;
    } else if (!namePrefixDescription.equals(other.namePrefixDescription))
      return false;
    if (primaryExtensionNumber == null) {
      if (other.primaryExtensionNumber != null)
        return false;
    } else if (!primaryExtensionNumber.equals(other.primaryExtensionNumber))
      return false;
    if (primaryPhoneNo == null) {
      if (other.primaryPhoneNo != null)
        return false;
    } else if (!primaryPhoneNo.equals(other.primaryPhoneNo))
      return false;
    if (residedOutOfStateIndicator == null) {
      if (other.residedOutOfStateIndicator != null)
        return false;
    } else if (!residedOutOfStateIndicator.equals(other.residedOutOfStateIndicator))
      return false;
    if (stateCode == null) {
      if (other.stateCode != null)
        return false;
    } else if (!stateCode.equals(other.stateCode))
      return false;
    if (streetName == null) {
      if (other.streetName != null)
        return false;
    } else if (!streetName.equals(other.streetName))
      return false;
    if (streetNumber == null) {
      if (other.streetNumber != null)
        return false;
    } else if (!streetNumber.equals(other.streetNumber))
      return false;
    if (suffixTitleDescription == null) {
      if (other.suffixTitleDescription != null)
        return false;
    } else if (!suffixTitleDescription.equals(other.suffixTitleDescription))
      return false;
    if (zipNumber == null) {
      if (other.zipNumber != null)
        return false;
    } else if (!zipNumber.equals(other.zipNumber))
      return false;
    if (zipSuffixNumber == null) {
      if (other.zipSuffixNumber != null)
        return false;
    } else if (!zipSuffixNumber.equals(other.zipSuffixNumber))
      return false;
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
