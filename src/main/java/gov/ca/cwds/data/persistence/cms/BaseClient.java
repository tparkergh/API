package gov.ca.cwds.data.persistence.cms;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import gov.ca.cwds.data.CmsSystemCodeDeserializer;
import gov.ca.cwds.data.SystemCodeSerializer;
import gov.ca.cwds.data.std.ApiLanguageAware;
import gov.ca.cwds.data.std.ApiMultipleLanguagesAware;
import gov.ca.cwds.data.std.ApiPersonAware;

/**
 * @author CDWS API Team
 *
 */
@MappedSuperclass
public abstract class BaseClient extends CmsPersistentObject
    implements ApiPersonAware, ApiMultipleLanguagesAware {

  /**
   * Base serialization version. Increment by class version.
   */
  private static final long serialVersionUID = 1L;

  private static final Logger LOGGER = LoggerFactory.getLogger(BaseClient.class);

  @Column(name = "ADJDEL_IND")
  protected String adjudicatedDelinquentIndicator;

  @Column(name = "ADPTN_STCD")
  protected String adoptionStatusCode;

  @Column(name = "ALN_REG_NO")
  protected String alienRegistrationNumber;

  @Column(name = "BIRTH_CITY")
  protected String birthCity;

  @SystemCodeSerializer(logical = true, description = true)
  @JsonDeserialize(using = CmsSystemCodeDeserializer.class)
  @Type(type = "short")
  @Column(name = "B_CNTRY_C")
  protected Short birthCountryCodeType;

  @Type(type = "date")
  @Column(name = "BIRTH_DT")
  protected Date birthDate;

  @Column(name = "BR_FAC_NM")
  protected String birthFacilityName;

  @SystemCodeSerializer(logical = true, description = true)
  @JsonDeserialize(using = CmsSystemCodeDeserializer.class)
  @Type(type = "short")
  @Column(name = "B_STATE_C")
  protected Short birthStateCodeType;

  @Column(name = "BP_VER_IND")
  protected String birthplaceVerifiedIndicator;

  @Column(name = "CHLD_CLT_B")
  protected String childClientIndicatorVar;

  @Column(name = "CL_INDX_NO")
  protected String clientIndexNumber;

  @Column(name = "COMMNT_DSC")
  protected String commentDescription;

  @Column(name = "COM_FST_NM")
  protected String commonFirstName;

  @Column(name = "COM_LST_NM")
  protected String commonLastName;

  @Column(name = "COM_MID_NM")
  protected String commonMiddleName;

  @Type(type = "date")
  @Column(name = "CONF_ACTDT")
  protected Date confidentialityActionDate;

  @Column(name = "CONF_EFIND")
  protected String confidentialityInEffectIndicator;

  @Type(type = "date")
  @Column(name = "CREATN_DT")
  protected Date creationDate;

  @Column(name = "CURRCA_IND")
  protected String currCaChildrenServIndicator;

  @Column(name = "COTH_DESC")
  protected String currentlyOtherDescription;

  @Column(name = "CURREG_IND")
  protected String currentlyRegionalCenterIndicator;

  @Type(type = "date")
  @Column(name = "DEATH_DT")
  protected Date deathDate;

  @Column(name = "DTH_DT_IND")
  protected String deathDateVerifiedIndicator;

  @Column(name = "DEATH_PLC")
  protected String deathPlace;

  @Column(name = "DTH_RN_TXT")
  protected String deathReasonText;

  @Column(name = "DRV_LIC_NO")
  protected String driverLicenseNumber;

  @SystemCodeSerializer(logical = true, description = true)
  @JsonDeserialize(using = CmsSystemCodeDeserializer.class)
  @Type(type = "short")
  @Column(name = "D_STATE_C")
  protected Short driverLicenseStateCodeType;

  @Column(name = "EMAIL_ADDR")
  protected String emailAddress;

  @Column(name = "EST_DOB_CD")
  protected String estimatedDobCode;

  @Column(name = "ETH_UD_CD")
  protected String ethUnableToDetReasonCode;

  @Type(type = "date")
  @Column(name = "FTERM_DT")
  protected Date fatherParentalRightTermDate;

  @Column(name = "GENDER_CD")
  protected String genderCode;

  @Column(name = "HEALTH_TXT")
  protected String healthSummaryText;

  @Column(name = "HISP_UD_CD")
  protected String hispUnableToDetReasonCode;

  @Column(name = "HISP_CD")
  protected String hispanicOriginCode;

  @Id
  @Column(name = "IDENTIFIER", length = CMS_ID_LEN)
  protected String id;

  @SystemCodeSerializer(logical = true, description = true)
  @JsonDeserialize(using = CmsSystemCodeDeserializer.class)
  @Type(type = "short")
  @Column(name = "I_CNTRY_C")
  protected Short immigrationCountryCodeType;

  @SystemCodeSerializer(logical = true, description = true)
  @JsonDeserialize(using = CmsSystemCodeDeserializer.class)
  @Type(type = "short")
  @Column(name = "IMGT_STC")
  protected Short immigrationStatusType;

  @Column(name = "INCAPC_CD")
  protected String incapacitatedParentCode;

  @Column(name = "HCARE_IND")
  protected String individualHealthCarePlanIndicator;

  @Column(name = "LIMIT_IND")
  protected String limitationOnScpHealthIndicator;

  @Column(name = "LITRATE_CD")
  protected String literateCode;

  @Column(name = "MAR_HIST_B")
  protected String maritalCohabitatnHstryIndicatorVar;

  @SystemCodeSerializer(logical = true, description = true)
  @JsonDeserialize(using = CmsSystemCodeDeserializer.class)
  @Type(type = "short")
  @Column(name = "MRTL_STC")
  protected Short maritalStatusType;

  @Column(name = "MILT_STACD")
  protected String militaryStatusCode;

  @Type(type = "date")
  @Column(name = "MTERM_DT")
  protected Date motherParentalRightTermDate;

  @Column(name = "NMPRFX_DSC")
  protected String namePrefixDescription;

  @SystemCodeSerializer(logical = true, description = true)
  @JsonDeserialize(using = CmsSystemCodeDeserializer.class)
  @Type(type = "short")
  @Column(name = "NAME_TPC")
  protected Short nameType;

  @Column(name = "OUTWRT_IND")
  protected String outstandingWarrantIndicator;

  @Column(name = "PREVCA_IND")
  protected String prevCaChildrenServIndicator;

  @Column(name = "POTH_DESC")
  protected String prevOtherDescription;

  @Column(name = "PREREG_IND")
  protected String prevRegionalCenterIndicator;

  @SystemCodeSerializer(logical = true, description = true)
  @JsonDeserialize(using = CmsSystemCodeDeserializer.class)
  @Type(type = "short")
  @Column(name = "P_ETHNCTYC")
  protected Short primaryEthnicityType;

  @SystemCodeSerializer(logical = true, description = true)
  @JsonDeserialize(using = CmsSystemCodeDeserializer.class)
  @Type(type = "short")
  @Column(name = "P_LANG_TPC")
  protected Short primaryLanguageType;

  @SystemCodeSerializer(logical = true, description = true)
  @JsonDeserialize(using = CmsSystemCodeDeserializer.class)
  @Type(type = "short")
  @Column(name = "RLGN_TPC")
  protected Short religionType;

  @SystemCodeSerializer(logical = true, description = true)
  @JsonDeserialize(using = CmsSystemCodeDeserializer.class)
  @Type(type = "short")
  @Column(name = "S_LANG_TC")
  protected Short secondaryLanguageType;

  @Column(name = "SNTV_HLIND")
  protected String sensitiveHlthInfoOnFileIndicator;

  @Column(name = "SENSTV_IND")
  protected String sensitivityIndicator;

  @Column(name = "SOCPLC_CD")
  protected String soc158PlacementCode;

  @Column(name = "SOC158_IND")
  protected String soc158SealedClientIndicator;

  @Column(name = "SSN_CHG_CD")
  protected String socialSecurityNumChangedCode;

  @Column(name = "SS_NO")
  protected String socialSecurityNumber;

  @Column(name = "SUFX_TLDSC")
  protected String suffixTitleDescription;

  @Column(name = "TRBA_CLT_B")
  protected String tribalAncestryClientIndicatorVar;

  @Column(name = "TR_MBVRT_B")
  protected String tribalMembrshpVerifctnIndicatorVar;

  @Column(name = "UNEMPLY_CD")
  protected String unemployedParentCode;

  @Column(name = "ZIPPY_IND")
  protected String zippyCreatedIndicator;

  /**
   * Default constructor
   */
  public BaseClient() {
    super();
  }

  /**
   * @param lastUpdatedId the last updated Id
   */
  public BaseClient(String lastUpdatedId) {
    super(lastUpdatedId);
  }

  // ==================
  // IDENTIFIERS:
  // ==================

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.data.persistence.PersistentObject#getPrimaryKey()
   */
  @Override
  public String getPrimaryKey() {
    return getId();
  }

  @Override
  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this, false);
  }

  @Override
  public boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj, false);
  }

  // ==================
  // ACCESSORS:
  // ==================

  /**
   * @return the adjudicatedDelinquentIndicator
   */
  public String getAdjudicatedDelinquentIndicator() {
    return StringUtils.trimToEmpty(adjudicatedDelinquentIndicator);
  }

  /**
   * @return the adoptionStatusCode
   */
  public String getAdoptionStatusCode() {
    return StringUtils.trimToEmpty(adoptionStatusCode);
  }

  /**
   * @return the alienRegistrationNumber
   */
  public String getAlienRegistrationNumber() {
    return StringUtils.trimToEmpty(alienRegistrationNumber);
  }

  /**
   * @return the birthCity
   */
  public String getBirthCity() {
    return StringUtils.trimToEmpty(birthCity);
  }

  /**
   * @return the birthCountryCodeType
   */
  public Short getBirthCountryCodeType() {
    return birthCountryCodeType;
  }

  /**
   * @return the birthDate
   */
  @Override
  public Date getBirthDate() {
    return birthDate;
  }

  /**
   * @return the birthFacilityName
   */
  public String getBirthFacilityName() {
    return StringUtils.trimToEmpty(birthFacilityName);
  }

  /**
   * @return the birthStateCodeType
   */
  public Short getBirthStateCodeType() {
    return birthStateCodeType;
  }

  /**
   * @return the birthplaceVerifiedIndicator
   */
  public String getBirthplaceVerifiedIndicator() {
    return StringUtils.trimToEmpty(birthplaceVerifiedIndicator);
  }

  /**
   * @return the childClientIndicatorVar
   */
  public String getChildClientIndicatorVar() {
    return StringUtils.trimToEmpty(childClientIndicatorVar);
  }

  /**
   * @return the clientIndexNumber
   */
  public String getClientIndexNumber() {
    return StringUtils.trimToEmpty(clientIndexNumber);
  }

  /**
   * @return the commentDescription
   */
  public String getCommentDescription() {
    return StringUtils.trimToEmpty(commentDescription);
  }

  /**
   * @return the commonFirstName
   */
  public String getCommonFirstName() {
    return StringUtils.trimToEmpty(commonFirstName);
  }

  /**
   * @return the commonLastName
   */
  public String getCommonLastName() {
    return StringUtils.trimToEmpty(commonLastName);
  }

  /**
   * @return the commonMiddleName
   */
  public String getCommonMiddleName() {
    return StringUtils.trimToEmpty(commonMiddleName);
  }

  /**
   * @return the confidentialityActionDate
   */
  public Date getConfidentialityActionDate() {
    return confidentialityActionDate;
  }

  /**
   * @return the confidentialityInEffectIndicator
   */
  public String getConfidentialityInEffectIndicator() {
    return StringUtils.trimToEmpty(confidentialityInEffectIndicator);
  }

  /**
   * @return the creationDate
   */
  public Date getCreationDate() {
    return creationDate;
  }

  /**
   * @return the currCaChildrenServIndicator
   */
  public String getCurrCaChildrenServIndicator() {
    return StringUtils.trimToEmpty(currCaChildrenServIndicator);
  }

  /**
   * @return the currentlyOtherDescription
   */
  public String getCurrentlyOtherDescription() {
    return StringUtils.trimToEmpty(currentlyOtherDescription);
  }

  /**
   * @return the currentlyRegionalCenterIndicator
   */
  public String getCurrentlyRegionalCenterIndicator() {
    return StringUtils.trimToEmpty(currentlyRegionalCenterIndicator);
  }

  /**
   * @return the deathDate
   */
  public Date getDeathDate() {
    return deathDate;
  }

  /**
   * @return the deathDateVerifiedIndicator
   */
  public String getDeathDateVerifiedIndicator() {
    return StringUtils.trimToEmpty(deathDateVerifiedIndicator);
  }

  /**
   * @return the deathPlace
   */
  public String getDeathPlace() {
    return StringUtils.trimToEmpty(deathPlace);
  }

  /**
   * @return the deathReasonText
   */
  public String getDeathReasonText() {
    return StringUtils.trimToEmpty(deathReasonText);
  }

  /**
   * @return the driverLicenseNumber
   */
  public String getDriverLicenseNumber() {
    return StringUtils.trimToEmpty(driverLicenseNumber);
  }

  /**
   * @return the driverLicenseStateCodeType
   */
  public Short getDriverLicenseStateCodeType() {
    return driverLicenseStateCodeType;
  }

  /**
   * @return the emailAddress
   */
  public String getEmailAddress() {
    return StringUtils.trimToEmpty(emailAddress);
  }

  /**
   * @return the estimatedDobCode
   */
  public String getEstimatedDobCode() {
    return StringUtils.trimToEmpty(estimatedDobCode);
  }

  /**
   * @return the ethUnableToDetReasonCode
   */
  public String getEthUnableToDetReasonCode() {
    return StringUtils.trimToEmpty(ethUnableToDetReasonCode);
  }

  /**
   * @return the fatherParentalRightTermDate
   */
  public Date getFatherParentalRightTermDate() {
    return fatherParentalRightTermDate;
  }

  /**
   * @return the genderCode
   */
  public String getGenderCode() {
    return StringUtils.trimToEmpty(genderCode);
  }

  /**
   * @return the healthSummaryText
   */
  public String getHealthSummaryText() {
    return StringUtils.trimToEmpty(healthSummaryText);
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
   * @return the id
   */
  public String getId() {
    return StringUtils.trimToEmpty(id);
  }

  /**
   * @return the immigrationCountryCodeType
   */
  public Short getImmigrationCountryCodeType() {
    return immigrationCountryCodeType;
  }

  /**
   * @return the immigrationStatusType
   */
  public Short getImmigrationStatusType() {
    return immigrationStatusType;
  }

  /**
   * @return the incapacitatedParentCode
   */
  public String getIncapacitatedParentCode() {
    return StringUtils.trimToEmpty(incapacitatedParentCode);
  }

  /**
   * @return the individualHealthCarePlanIndicator
   */
  public String getIndividualHealthCarePlanIndicator() {
    return StringUtils.trimToEmpty(individualHealthCarePlanIndicator);
  }

  /**
   * @return the limitationOnScpHealthIndicator
   */
  public String getLimitationOnScpHealthIndicator() {
    return StringUtils.trimToEmpty(limitationOnScpHealthIndicator);
  }

  /**
   * @return the literateCode
   */
  public String getLiterateCode() {
    return StringUtils.trimToEmpty(literateCode);
  }

  /**
   * @return the maritalCohabitatnHstryIndicatorVar
   */
  public String getMaritalCohabitatnHstryIndicatorVar() {
    return StringUtils.trimToEmpty(maritalCohabitatnHstryIndicatorVar);
  }

  /**
   * @return the maritalStatusType
   */
  public Short getMaritalStatusType() {
    return maritalStatusType;
  }

  /**
   * @return the militaryStatusCode
   */
  public String getMilitaryStatusCode() {
    return StringUtils.trimToEmpty(militaryStatusCode);
  }

  /**
   * @return the motherParentalRightTermDate
   */
  public Date getMotherParentalRightTermDate() {
    return motherParentalRightTermDate;
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
   * @return the outstandingWarrantIndicator
   */
  public String getOutstandingWarrantIndicator() {
    return StringUtils.trimToEmpty(outstandingWarrantIndicator);
  }

  /**
   * @return the prevCaChildrenServIndicator
   */
  public String getPrevCaChildrenServIndicator() {
    return StringUtils.trimToEmpty(prevCaChildrenServIndicator);
  }

  /**
   * @return the prevOtherDescription
   */
  public String getPrevOtherDescription() {
    return StringUtils.trimToEmpty(prevOtherDescription);
  }

  /**
   * @return the prevRegionalCenterIndicator
   */
  public String getPrevRegionalCenterIndicator() {
    return StringUtils.trimToEmpty(prevRegionalCenterIndicator);
  }

  /**
   * @return the primaryEthnicityType
   */
  public Short getPrimaryEthnicityType() {
    return primaryEthnicityType;
  }

  /**
   * @return the primaryLanguageType
   */
  public Short getPrimaryLanguageType() {
    return primaryLanguageType;
  }

  /**
   * @return the religionType
   */
  public Short getReligionType() {
    return religionType;
  }

  /**
   * @return the secondaryLanguageType
   */
  public Short getSecondaryLanguageType() {
    return secondaryLanguageType;
  }

  /**
   * @return the sensitiveHlthInfoOnFileIndicator
   */
  public String getSensitiveHlthInfoOnFileIndicator() {
    return StringUtils.trimToEmpty(sensitiveHlthInfoOnFileIndicator);
  }

  /**
   * @return the sensitivityIndicator
   */
  public String getSensitivityIndicator() {
    return StringUtils.trimToEmpty(sensitivityIndicator);
  }

  /**
   * @return the soc158PlacementCode
   */
  public String getSoc158PlacementCode() {
    return StringUtils.trimToEmpty(soc158PlacementCode);
  }

  /**
   * @return the soc158SealedClientIndicator
   */
  public String getSoc158SealedClientIndicator() {
    return StringUtils.trimToEmpty(soc158SealedClientIndicator);
  }

  /**
   * @return the socialSecurityNumChangedCode
   */
  public String getSocialSecurityNumChangedCode() {
    return StringUtils.trimToEmpty(socialSecurityNumChangedCode);
  }

  /**
   * @return the socialSecurityNumber
   */
  public String getSocialSecurityNumber() {
    return StringUtils.trimToEmpty(socialSecurityNumber);
  }

  /**
   * @return the suffixTitleDescription
   */
  public String getSuffixTitleDescription() {
    return StringUtils.trimToEmpty(suffixTitleDescription);
  }

  /**
   * @return the tribalAncestryClientIndicatorVar
   */
  public String getTribalAncestryClientIndicatorVar() {
    return StringUtils.trimToEmpty(tribalAncestryClientIndicatorVar);
  }

  /**
   * @return the tribalMembrshpVerifctnIndicatorVar
   */
  public String getTribalMembrshpVerifctnIndicatorVar() {
    return StringUtils.trimToEmpty(tribalMembrshpVerifctnIndicatorVar);
  }

  /**
   * @return the unemployedParentCode
   */
  public String getUnemployedParentCode() {
    return StringUtils.trimToEmpty(unemployedParentCode);
  }

  /**
   * @return the zippyCreatedIndicator
   */
  public String getZippyCreatedIndicator() {
    return StringUtils.trimToEmpty(zippyCreatedIndicator);
  }

  // =============================
  // ApiPersonAware:
  // =============================

  @JsonIgnore
  @Override
  public String getMiddleName() {
    return this.commonMiddleName;
  }

  @JsonIgnore
  @Override
  public String getFirstName() {
    return this.commonFirstName;
  }

  @JsonIgnore
  @Override
  public String getLastName() {
    return this.commonLastName;
  }

  @JsonIgnore
  @Override
  public String getGender() {
    return this.genderCode;
  }

  @JsonIgnore
  @Override
  public String getSsn() {
    return this.socialSecurityNumber;
  }

  @JsonIgnore
  @Override
  public String getNameSuffix() {
    return this.suffixTitleDescription;
  }

  // =============================
  // ApiMultipleLanguagesAware:
  // =============================

  @Override
  @JsonIgnore
  public ApiLanguageAware[] getLanguages() {

    List<ApiLanguageAware> ret = new ArrayList<>();
    if (this.primaryLanguageType != null && this.primaryLanguageType != 0) {
      ret.add(new ApiLanguageAware() {
        @Override
        public Integer getLanguageSysId() {
          return primaryLanguageType.intValue();
        }
      });
    }

    if (this.secondaryLanguageType != null && this.secondaryLanguageType != 0) {
      ret.add(new ApiLanguageAware() {
        @Override
        public Integer getLanguageSysId() {
          return secondaryLanguageType.intValue();
        }
      });
    }

    return ret.toArray(new ApiLanguageAware[0]);
  }

  // @Override
  // public ApiPhoneAware[] getPhones() {
  // List<ApiPhoneAware> phones = new ArrayList<>();
  // if (this.primaryPhoneNumber != null && !BigDecimal.ZERO.equals(this.primaryPhoneNumber)) {
  // phones.add(new ReadablePhone(this.primaryPhoneNumber.toPlainString(),
  // this.primaryPhoneExtensionNumber != null ? this.primaryPhoneExtensionNumber.toString()
  // : null,
  // null));
  // }
  //
  // if (this.messagePhoneNumber != null && !BigDecimal.ZERO.equals(this.messagePhoneNumber)) {
  // phones
  // .add(new ReadablePhone(
  // this.messagePhoneNumber.toPlainString(), this.messagePhoneExtensionNumber != null
  // ? this.messagePhoneExtensionNumber.toString() : null,
  // ApiPhoneAware.PhoneType.Cell));
  // }
  //
  // return phones.toArray(new ApiPhoneAware[0]);
  // }

}
