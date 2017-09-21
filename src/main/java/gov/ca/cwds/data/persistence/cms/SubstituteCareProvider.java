package gov.ca.cwds.data.persistence.cms;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.NamedNativeQueries;
import org.hibernate.annotations.NamedNativeQuery;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import gov.ca.cwds.data.persistence.PersistentObject;

/**
 * {@link PersistentObject} representing a SubstituteCareProvider.
 * 
 * @author CWDS API Team
 */
@NamedQueries({@NamedQuery(name = "gov.ca.cwds.data.persistence.cms.SubstituteCareProvider.findAll",
    query = "FROM SubstituteCareProvider")})
@NamedNativeQueries({@NamedNativeQuery(
    name = "gov.ca.cwds.data.persistence.cms.SubstituteCareProvider.findAllByBucket",
    query = "select z.IDENTIFIER, z.ADD_TEL_NO, z.ADD_EXT_NO, z.YR_INC_AMT, "
        + "z.BIRTH_DT, z.CA_DLIC_NO, z.CITY_NM, z.EDUCATION, z.EMAIL_ADDR, "
        + "z.EMPLYR_NM, z.EMPL_STAT, z.ETH_UD_CD, z.FIRST_NM, z.FRG_ADRT_B, "
        + "z.GENDER_IND, z.HISP_UD_CD, z.HISP_CD, z.IND_TRBC, z.LAST_NM, "
        + "z.LISOWNIND, z.LIS_PER_ID, z.MRTL_STC, z.MID_INI_NM, z.NMPRFX_DSC, "
        + "z.PASSBC_CD, z.PRIM_INC, z.RESOST_IND, z.SEC_INC, z.SS_NO, "
        + "z.STATE_C, z.STREET_NM, z.STREET_NO, z.SUFX_TLDSC, z.ZIP_NO, "
        + "z.ZIP_SFX_NO, z.LST_UPD_ID, z.LST_UPD_TS "
        + "from ( select mod(y.rn, CAST(:total_buckets AS INTEGER)) + 1 as bucket, y.* "
        + "from ( select row_number() over (order by 1) as rn, x.* from {h-schema}SB_PVDRT x "
        + " ) y ) z where z.bucket = :bucket_num for read only",
    resultClass = SubstituteCareProvider.class)})
@Entity
@Table(name = "SB_PVDRT")
@JsonPropertyOrder(alphabetic = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SubstituteCareProvider extends BaseSubstituteCareProvider {

  /**
   * Default serialization.
   */
  private static final long serialVersionUID = 1L;

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
   * @see java.lang.Object#hashCode()
   */
  @Override
  public final int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this, false);
  }

  /**
   * {@inheritDoc}
   *
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public final boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj, false);
  }

}
