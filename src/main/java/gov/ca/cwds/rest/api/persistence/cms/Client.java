package gov.ca.cwds.rest.api.persistence.cms;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PersistenceException;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;
import org.hibernate.annotations.Type;

import gov.ca.cwds.rest.api.ApiException;
import gov.ca.cwds.rest.api.domain.DomainObject;
import gov.ca.cwds.rest.api.persistence.PersistentObject;

/**
 * {@link PersistentObject} representing a Client
 * 
 * @author CWDS API Team
 */
@NamedQueries({
    @NamedQuery(name = "gov.ca.cwds.rest.api.persistence.cms.Client.findAll",
        query = "FROM Client"),
    @NamedQuery(name = "gov.ca.cwds.rest.api.persistence.cms.Client.findAllUpdatedAfter",
        query = "FROM Client WHERE lastUpdatedTime > :after")})
@Entity
@Table(name = "CLIENT_T")
public class Client extends CmsPersistentObject {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  @Column(name = "ADJDEL_IND")
  private String adjudicatedDelinquentIndicator;

  @Column(name = "ADPTN_STCD")
  private String adoptionStatusCode;

  @Column(name = "ALN_REG_NO")
  private String alienRegistrationNumber;

  @Column(name = "BIRTH_CITY")
  private String birthCity;

  @Type(type = "short")
  @Column(name = "B_CNTRY_C")
  private Short birthCountryCodeType;

  @Type(type = "date")
  @Column(name = "BIRTH_DT")
  private Date birthDate;

  @Column(name = "BR_FAC_NM")
  private String birthFacilityName;

  @Type(type = "short")
  @Column(name = "B_STATE_C")
  private Short birthStateCodeType;

  @Column(name = "BP_VER_IND")
  private String birthplaceVerifiedIndicator;

  @Column(name = "CHLD_CLT_B")
  private String childClientIndicatorVar;

  @Column(name = "CL_INDX_NO")
  private String clientIndexNumber;

  @Column(name = "COMMNT_DSC")
  private String commentDescription;

  @Column(name = "COM_FST_NM")
  private String commonFirstName;

  @Column(name = "COM_LST_NM")
  private String commonLastName;

  @Column(name = "COM_MID_NM")
  private String commonMiddleName;

  @Type(type = "date")
  @Column(name = "CONF_ACTDT")
  private Date confidentialityActionDate;

  @Column(name = "CONF_EFIND")
  private String confidentialityInEffectIndicator;

  @Type(type = "date")
  @Column(name = "CREATN_DT")
  private Date creationDate;

  @Column(name = "CURRCA_IND")
  private String currCaChildrenServIndicator;

  @Column(name = "COTH_DESC")
  private String currentlyOtherDescription;

  @Column(name = "CURREG_IND")
  private String currentlyRegionalCenterIndicator;

  @Type(type = "date")
  @Column(name = "DEATH_DT")
  private Date deathDate;

  @Column(name = "DTH_DT_IND")
  private String deathDateVerifiedIndicator;

  @Column(name = "DEATH_PLC")
  private String deathPlace;

  @Column(name = "DTH_RN_TXT")
  private String deathReasonText;

  @Column(name = "DRV_LIC_NO")
  private String driverLicenseNumber;

  @Type(type = "short")
  @Column(name = "D_STATE_C")
  private Short driverLicenseStateCodeType;

  @Column(name = "EMAIL_ADDR")
  private String emailAddress;

  @Column(name = "EST_DOB_CD")
  private String estimatedDobCode;

  @Column(name = "ETH_UD_CD")
  private String ethUnableToDetReasonCode;

  @Type(type = "date")
  @Column(name = "FTERM_DT")
  private Date fatherParentalRightTermDate;

  @Column(name = "GENDER_CD")
  private String genderCode;

  @Column(name = "HEALTH_TXT")
  private String healthSummaryText;

  @Column(name = "HISP_UD_CD")
  private String hispUnableToDetReasonCode;

  @Column(name = "HISP_CD")
  private String hispanicOriginCode;

  @Id
  @Column(name = "IDENTIFIER", length = CMS_ID_LEN)
  private String id;

  @Type(type = "short")
  @Column(name = "I_CNTRY_C")
  private Short immigrationCountryCodeType;

  @Type(type = "short")
  @Column(name = "IMGT_STC")
  private Short immigrationStatusType;

  @Column(name = "INCAPC_CD")
  private String incapacitatedParentCode;

  @Column(name = "HCARE_IND")
  private String individualHealthCarePlanIndicator;

  @Column(name = "LIMIT_IND")
  private String limitationOnScpHealthIndicator;

  @Column(name = "LITRATE_CD")
  private String literateCode;

  @Column(name = "MAR_HIST_B")
  private String maritalCohabitatnHstryIndicatorVar;

  @Type(type = "short")
  @Column(name = "MRTL_STC")
  private Short maritalStatusType;

  @Column(name = "MILT_STACD")
  private String militaryStatusCode;

  @Type(type = "date")
  @Column(name = "MTERM_DT")
  private Date motherParentalRightTermDate;

  @Column(name = "NMPRFX_DSC")
  private String namePrefixDescription;

  @Type(type = "short")
  @Column(name = "NAME_TPC")
  private Short nameType;

  @Column(name = "OUTWRT_IND")
  private String outstandingWarrantIndicator;

  @Column(name = "PREVCA_IND")
  private String prevCaChildrenServIndicator;

  @Column(name = "POTH_DESC")
  private String prevOtherDescription;

  @Column(name = "PREREG_IND")
  private String prevRegionalCenterIndicator;

  @Type(type = "short")
  @Column(name = "P_ETHNCTYC")
  private Short primaryEthnicityType;

  @Type(type = "short")
  @Column(name = "P_LANG_TPC")
  private Short primaryLanguageType;

  @Type(type = "short")
  @Column(name = "RLGN_TPC")
  private Short religionType;

  @Type(type = "short")
  @Column(name = "S_LANG_TC")
  private Short secondaryLanguageType;

  @Column(name = "SNTV_HLIND")
  private String sensitiveHlthInfoOnFileIndicator;

  @Column(name = "SENSTV_IND")
  private String sensitivityIndicator;

  @Column(name = "SOCPLC_CD")
  private String soc158PlacementCode;

  @Column(name = "SOC158_IND")
  private String soc158SealedClientIndicator;

  @Column(name = "SSN_CHG_CD")
  private String socialSecurityNumChangedCode;

  @Column(name = "SS_NO")
  private String socialSecurityNumber;

  @Column(name = "SUFX_TLDSC")
  private String suffixTitleDescription;

  @Column(name = "TRBA_CLT_B")
  private String tribalAncestryClientIndicatorVar;

  @Column(name = "TR_MBVRT_B")
  private String tribalMembrshpVerifctnIndicatorVar;

  @Column(name = "UNEMPLY_CD")
  private String unemployedParentCode;

  @Column(name = "ZIPPY_IND")
  private String zippyCreatedIndicator;

  /**
   * Default constructor
   * 
   * Required for Hibernate
   */
  public Client() {
    super();
  }

  /**
   * @param adjudicatedDelinquentIndicator The adjudicatedDelinquentIndicator
   * @param adoptionStatusCode The adoptionStatusCode
   * @param alienRegistrationNumber The alienRegistrationNumber
   * @param birthCity The birthCity
   * @param birthCountryCodeType The birthCountryCodeType
   * @param birthDate The birthDate
   * @param birthFacilityName The birthFacilityName
   * @param birthStateCodeType The birthStateCodeType
   * @param birthplaceVerifiedIndicator The birthplaceVerifiedIndicator
   * @param childClientIndicatorVar The childClientIndicatorVar
   * @param clientIndexNumber The clientIndexNumber
   * @param commentDescription The commentDescription
   * @param commonFirstName The commonFirstName
   * @param commonLastName The commonLastName
   * @param commonMiddleName The commonMiddleName
   * @param confidentialityActionDate The confidentialityActionDate
   * @param confidentialityInEffectIndicator The confidentialityInEffectIndicator
   * @param creationDate The creationDate
   * @param currCaChildrenServIndicator The currCaChildrenServIndicator
   * @param currentlyOtherDescription The currentlyOtherDescription
   * @param currentlyRegionalCenterIndicator The currentlyRegionalCenterIndicator
   * @param deathDate The deathDate
   * @param deathDateVerifiedIndicator The deathDateVerifiedIndicator
   * @param deathPlace The deathPlace
   * @param deathReasonText The deathReasonText
   * @param driverLicenseNumber The driverLicenseNumber
   * @param driverLicenseStateCodeType The driverLicenseStateCodeType
   * @param emailAddress The emailAddress
   * @param estimatedDobCode The estimatedDobCode
   * @param ethUnableToDetReasonCode The ethUnableToDetReasonCode
   * @param fatherParentalRightTermDate The fatherParentalRightTermDate
   * @param genderCode The genderCode
   * @param healthSummaryText The healthSummaryText
   * @param hispUnableToDetReasonCode The hispUnableToDetReasonCode
   * @param hispanicOriginCode The hispanicOriginCode
   * @param id The id
   * @param immigrationCountryCodeType The immigrationCountryCodeType
   * @param immigrationStatusType The immigrationStatusType
   * @param incapacitatedParentCode The incapacitatedParentCode
   * @param individualHealthCarePlanIndicator The individualHealthCarePlanIndicator
   * @param limitationOnScpHealthIndicator The limitationOnScpHealthIndicator
   * @param literateCode The literateCode
   * @param maritalCohabitatnHstryIndicatorVar The maritalCohabitatnHstryIndicatorVar
   * @param maritalStatusType The maritalStatusType
   * @param militaryStatusCode The militaryStatusCode
   * @param motherParentalRightTermDate The motherParentalRightTermDate
   * @param namePrefixDescription The namePrefixDescription
   * @param nameType The nameType
   * @param outstandingWarrantIndicator The outstandingWarrantIndicator
   * @param prevCaChildrenServIndicator The prevCaChildrenServIndicator
   * @param prevOtherDescription The prevOtherDescription
   * @param prevRegionalCenterIndicator The prevRegionalCenterIndicator
   * @param primaryEthnicityType The primaryEthnicityType
   * @param primaryLanguageType The primaryLanguageType
   * @param religionType The religionType
   * @param secondaryLanguageType The secondaryLanguageType
   * @param sensitiveHlthInfoOnFileIndicator The sensitiveHlthInfoOnFileIndicator
   * @param sensitivityIndicator The sensitivityIndicator
   * @param soc158PlacementCode The soc158PlacementCode
   * @param soc158SealedClientIndicator The soc158SealedClientIndicator
   * @param socialSecurityNumChangedCode The socialSecurityNumChangedCode
   * @param socialSecurityNumber The socialSecurityNumber
   * @param suffixTitleDescription The suffixTitleDescription
   * @param tribalAncestryClientIndicatorVar The tribalAncestryClientIndicatorVar
   * @param tribalMembrshpVerifctnIndicatorVar The tribalMembrshpVerifctnIndicatorVar
   * @param unemployedParentCode The unemployedParentCode
   * @param zippyCreatedIndicator The zippyCreatedIndicator
   */
  public Client(String adjudicatedDelinquentIndicator, String adoptionStatusCode,
      String alienRegistrationNumber, String birthCity, Short birthCountryCodeType, Date birthDate,
      String birthFacilityName, Short birthStateCodeType, String birthplaceVerifiedIndicator,
      String childClientIndicatorVar, String clientIndexNumber, String commentDescription,
      String commonFirstName, String commonLastName, String commonMiddleName,
      Date confidentialityActionDate, String confidentialityInEffectIndicator, Date creationDate,
      String currCaChildrenServIndicator, String currentlyOtherDescription,
      String currentlyRegionalCenterIndicator, Date deathDate, String deathDateVerifiedIndicator,
      String deathPlace, String deathReasonText, String driverLicenseNumber,
      Short driverLicenseStateCodeType, String emailAddress, String estimatedDobCode,
      String ethUnableToDetReasonCode, Date fatherParentalRightTermDate, String genderCode,
      String healthSummaryText, String hispUnableToDetReasonCode, String hispanicOriginCode,
      String id, Short immigrationCountryCodeType, Short immigrationStatusType,
      String incapacitatedParentCode, String individualHealthCarePlanIndicator,
      String limitationOnScpHealthIndicator, String literateCode,
      String maritalCohabitatnHstryIndicatorVar, Short maritalStatusType, String militaryStatusCode,
      Date motherParentalRightTermDate, String namePrefixDescription, Short nameType,
      String outstandingWarrantIndicator, String prevCaChildrenServIndicator,
      String prevOtherDescription, String prevRegionalCenterIndicator, Short primaryEthnicityType,
      Short primaryLanguageType, Short religionType, Short secondaryLanguageType,
      String sensitiveHlthInfoOnFileIndicator, String sensitivityIndicator,
      String soc158PlacementCode, String soc158SealedClientIndicator,
      String socialSecurityNumChangedCode, String socialSecurityNumber,
      String suffixTitleDescription, String tribalAncestryClientIndicatorVar,
      String tribalMembrshpVerifctnIndicatorVar, String unemployedParentCode,
      String zippyCreatedIndicator) {
    super();
    this.adjudicatedDelinquentIndicator = adjudicatedDelinquentIndicator;
    this.adoptionStatusCode = adoptionStatusCode;
    this.alienRegistrationNumber = alienRegistrationNumber;
    this.birthCity = birthCity;
    this.birthCountryCodeType = birthCountryCodeType;
    this.birthDate = birthDate;
    this.birthFacilityName = birthFacilityName;
    this.birthStateCodeType = birthStateCodeType;
    this.birthplaceVerifiedIndicator = birthplaceVerifiedIndicator;
    this.childClientIndicatorVar = childClientIndicatorVar;
    this.clientIndexNumber = clientIndexNumber;
    this.commentDescription = commentDescription;
    this.commonFirstName = commonFirstName;
    this.commonLastName = commonLastName;
    this.commonMiddleName = commonMiddleName;
    this.confidentialityActionDate = confidentialityActionDate;
    this.confidentialityInEffectIndicator = confidentialityInEffectIndicator;
    this.creationDate = creationDate;
    this.currCaChildrenServIndicator = currCaChildrenServIndicator;
    this.currentlyOtherDescription = currentlyOtherDescription;
    this.currentlyRegionalCenterIndicator = currentlyRegionalCenterIndicator;
    this.deathDate = deathDate;
    this.deathDateVerifiedIndicator = deathDateVerifiedIndicator;
    this.deathPlace = deathPlace;
    this.deathReasonText = deathReasonText;
    this.driverLicenseNumber = driverLicenseNumber;
    this.driverLicenseStateCodeType = driverLicenseStateCodeType;
    this.emailAddress = emailAddress;
    this.estimatedDobCode = estimatedDobCode;
    this.ethUnableToDetReasonCode = ethUnableToDetReasonCode;
    this.fatherParentalRightTermDate = fatherParentalRightTermDate;
    this.genderCode = genderCode;
    this.healthSummaryText = healthSummaryText;
    this.hispUnableToDetReasonCode = hispUnableToDetReasonCode;
    this.hispanicOriginCode = hispanicOriginCode;
    this.id = id;
    this.immigrationCountryCodeType = immigrationCountryCodeType;
    this.immigrationStatusType = immigrationStatusType;
    this.incapacitatedParentCode = incapacitatedParentCode;
    this.individualHealthCarePlanIndicator = individualHealthCarePlanIndicator;
    this.limitationOnScpHealthIndicator = limitationOnScpHealthIndicator;
    this.literateCode = literateCode;
    this.maritalCohabitatnHstryIndicatorVar = maritalCohabitatnHstryIndicatorVar;
    this.maritalStatusType = maritalStatusType;
    this.militaryStatusCode = militaryStatusCode;
    this.motherParentalRightTermDate = motherParentalRightTermDate;
    this.namePrefixDescription = namePrefixDescription;
    this.nameType = nameType;
    this.outstandingWarrantIndicator = outstandingWarrantIndicator;
    this.prevCaChildrenServIndicator = prevCaChildrenServIndicator;
    this.prevOtherDescription = prevOtherDescription;
    this.prevRegionalCenterIndicator = prevRegionalCenterIndicator;
    this.primaryEthnicityType = primaryEthnicityType;
    this.primaryLanguageType = primaryLanguageType;
    this.religionType = religionType;
    this.secondaryLanguageType = secondaryLanguageType;
    this.sensitiveHlthInfoOnFileIndicator = sensitiveHlthInfoOnFileIndicator;
    this.sensitivityIndicator = sensitivityIndicator;
    this.soc158PlacementCode = soc158PlacementCode;
    this.soc158SealedClientIndicator = soc158SealedClientIndicator;
    this.socialSecurityNumChangedCode = socialSecurityNumChangedCode;
    this.socialSecurityNumber = socialSecurityNumber;
    this.suffixTitleDescription = suffixTitleDescription;
    this.tribalAncestryClientIndicatorVar = tribalAncestryClientIndicatorVar;
    this.tribalMembrshpVerifctnIndicatorVar = tribalMembrshpVerifctnIndicatorVar;
    this.unemployedParentCode = unemployedParentCode;
    this.zippyCreatedIndicator = zippyCreatedIndicator;
  }

  /**
   * Constructor
   * 
   * @param client The domain object to construct this object from
   * @param lastUpdatedId the id of the last person to update this object
   */
  public Client(gov.ca.cwds.rest.api.domain.cms.Client client, String lastUpdatedId) {
    super(lastUpdatedId);
    try {
      this.adjudicatedDelinquentIndicator =
          DomainObject.cookBoolean(client.getAdjudicatedDelinquentIndicator());
      this.adoptionStatusCode = client.getAdoptionStatusCode();
      this.alienRegistrationNumber = client.getAlienRegistrationNumber();
      this.birthCity = client.getBirthCity();
      this.birthCountryCodeType = client.getBirthCountryCodeType();
      this.birthDate = DomainObject.uncookDateString(client.getBirthDate());
      this.birthFacilityName = client.getBirthFacilityName();
      this.birthStateCodeType = client.getBirthStateCodeType();
      this.birthplaceVerifiedIndicator =
          DomainObject.cookBoolean(client.getBirthplaceVerifiedIndicator());
      this.childClientIndicatorVar = DomainObject.cookBoolean(client.getChildClientIndicatorVar());
      this.clientIndexNumber = client.getClientIndexNumber();
      this.commentDescription = client.getCommentDescription();
      this.commonFirstName = client.getCommonFirstName();
      this.commonLastName = client.getCommonLastName();
      this.commonMiddleName = client.getCommonMiddleName();
      this.confidentialityActionDate =
          DomainObject.uncookDateString(client.getConfidentialityActionDate());
      this.confidentialityInEffectIndicator =
          DomainObject.cookBoolean(client.getConfidentialityInEffectIndicator());
      this.creationDate = DomainObject.uncookDateString(client.getCreationDate());
      this.currCaChildrenServIndicator =
          DomainObject.cookBoolean(client.getCurrCaChildrenServIndicator());
      this.currentlyOtherDescription = client.getCurrentlyOtherDescription();
      this.currentlyRegionalCenterIndicator =
          DomainObject.cookBoolean(client.getCurrentlyRegionalCenterIndicator());
      this.deathDate = DomainObject.uncookDateString(client.getDeathDate());
      this.deathDateVerifiedIndicator =
          DomainObject.cookBoolean(client.getDeathDateVerifiedIndicator());
      this.deathPlace = client.getDeathPlace();
      this.deathReasonText = client.getDeathReasonText();
      this.driverLicenseNumber = client.getDriverLicenseNumber();
      this.driverLicenseStateCodeType = client.getDriverLicenseStateCodeType();
      this.emailAddress = client.getEmailAddress();
      this.estimatedDobCode = client.getEstimatedDobCode();
      this.ethUnableToDetReasonCode = client.getEthUnableToDetReasonCode();
      this.fatherParentalRightTermDate =
          DomainObject.uncookDateString(client.getFatherParentalRightTermDate());
      this.genderCode = client.getGenderCode();
      this.healthSummaryText = client.getHealthSummaryText();
      this.hispUnableToDetReasonCode = client.getHispUnableToDetReasonCode();
      this.hispanicOriginCode = client.getHispanicOriginCode();
      this.id = client.getId();
      this.immigrationCountryCodeType = client.getImmigrationCountryCodeType();
      this.immigrationStatusType = client.getImmigrationStatusType();
      this.incapacitatedParentCode = client.getIncapacitatedParentCode();
      this.individualHealthCarePlanIndicator =
          DomainObject.cookBoolean(client.getIndividualHealthCarePlanIndicator());
      this.limitationOnScpHealthIndicator =
          DomainObject.cookBoolean(client.getLimitationOnScpHealthIndicator());
      this.literateCode = client.getLiterateCode();
      this.maritalCohabitatnHstryIndicatorVar =
          DomainObject.cookBoolean(client.getMaritalCohabitatnHstryIndicatorVar());
      this.maritalStatusType = client.getMaritalStatusType();
      this.militaryStatusCode = client.getMilitaryStatusCode();
      this.motherParentalRightTermDate =
          DomainObject.uncookDateString(client.getMotherParentalRightTermDate());
      this.namePrefixDescription = client.getNamePrefixDescription();
      this.nameType = client.getNameType();
      this.outstandingWarrantIndicator =
          DomainObject.cookBoolean(client.getOutstandingWarrantIndicator());
      this.prevCaChildrenServIndicator =
          DomainObject.cookBoolean(client.getPrevCaChildrenServIndicator());
      this.prevOtherDescription = client.getPrevOtherDescription();
      this.prevRegionalCenterIndicator =
          DomainObject.cookBoolean(client.getPrevRegionalCenterIndicator());
      this.primaryEthnicityType = client.getPrimaryEthnicityType();
      this.primaryLanguageType = client.getPrimaryLanguageType();
      this.religionType = client.getReligionType();
      this.secondaryLanguageType = client.getSecondaryLanguageType();
      this.sensitiveHlthInfoOnFileIndicator =
          DomainObject.cookBoolean(client.getSensitiveHlthInfoOnFileIndicator());
      this.sensitivityIndicator = client.getSensitivityIndicator();
      this.soc158PlacementCode = client.getSoc158PlacementCode();
      this.soc158SealedClientIndicator =
          DomainObject.cookBoolean(client.getSoc158SealedClientIndicator());
      this.socialSecurityNumChangedCode = client.getSocialSecurityNumChangedCode();
      this.socialSecurityNumber = client.getSocialSecurityNumber();
      this.suffixTitleDescription = client.getSuffixTitleDescription();
      this.tribalAncestryClientIndicatorVar =
          DomainObject.cookBoolean(client.getTribalAncestryClientIndicatorVar());
      this.tribalMembrshpVerifctnIndicatorVar =
          DomainObject.cookBoolean(client.getTribalMembrshpVerifctnIndicatorVar());
      this.unemployedParentCode = client.getUnemployedParentCode();
      this.zippyCreatedIndicator = DomainObject.cookBoolean(client.getZippyCreatedIndicator());

    } catch (ApiException e) {
      throw new PersistenceException(e);
    }
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.api.persistence.PersistentObject#getPrimaryKey()
   */
  @Override
  public String getPrimaryKey() {
    return getId();
  }

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


  // <HashCode>
  // <Equals>
}
