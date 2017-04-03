package gov.ca.cwds.rest.api.domain.cms;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.data.SystemCodeSerializer;
import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.DomainChef;
import gov.ca.cwds.rest.api.domain.DomainObject;
import io.dropwizard.validation.OneOf;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * {@link DomainObject} representing a Client
 * 
 * @author CWDS API Team
 */
@ApiModel
public class Client extends DomainObject implements Request, Response {
  /**
   * Serialization version
   */
  private static final long serialVersionUID = 1L;

  @Size(max = 10)
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "ABC1234567")
  private String existingClientId;

  @ApiModelProperty(required = false, readOnly = false)
  private Boolean adjudicatedDelinquentIndicator;

  @NotEmpty
  @Size(min = 1, max = 1, message = "size must be 1")
  @OneOf(value = {"T", "P", "N", "A"}, ignoreCase = true, ignoreWhitespace = true)
  @ApiModelProperty(required = true, readOnly = false,
      value = "T=totally free - P=partially free - N=not free - A=not applicable", example = "N")
  private String adoptionStatusCode;

  @NotNull
  @Size(max = 12)
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "123456789012")
  private String alienRegistrationNumber;

  @NotNull
  @Size(max = 35)
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "Fresno")
  private String birthCity;

  @SystemCodeSerializer(logical = true, description = true)
  @NotNull
  @ApiModelProperty(required = true, readOnly = false, example = "1234")
  private Short birthCountryCodeType;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT)
  @JsonProperty(value = "birthDate")
  @gov.ca.cwds.rest.validation.Date(format = DATE_FORMAT, required = false)
  @ApiModelProperty(required = false, readOnly = false, value = "yyyy-MM-dd",
      example = "2016-01-01")
  private String birthDate;

  @NotNull
  @Size(max = 35)
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "facility name")
  private String birthFacilityName;

  @SystemCodeSerializer(logical = true, description = true)
  @NotNull
  @ApiModelProperty(required = false, readOnly = false, example = "1234")
  private Short birthStateCodeType;

  @NotNull
  @ApiModelProperty(required = false, readOnly = false)
  private Boolean birthplaceVerifiedIndicator;

  @NotNull
  @ApiModelProperty(required = true, readOnly = false)
  private Boolean childClientIndicatorVar;

  @Size(max = 12)
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "client index number")
  private String clientIndexNumber;

  @NotNull
  @Size(max = 120)
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "a description")
  private String commentDescription;

  @NotBlank
  @Size(min = 1, max = 20)
  @ApiModelProperty(required = true, readOnly = false, value = "first name of Client",
      example = "first name")
  private String commonFirstName;

  @NotBlank
  @Size(min = 1, max = 25)
  @ApiModelProperty(required = true, readOnly = false, value = "", example = "last name")
  private String commonLastName;

  @NotNull
  @Size(max = 20)
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "middle name")
  private String commonMiddleName;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT)
  @JsonProperty(value = "confidentialityActionDate")
  @gov.ca.cwds.rest.validation.Date(format = DATE_FORMAT, required = false)
  @ApiModelProperty(required = false, readOnly = false, value = "yyyy-MM-dd",
      example = "2000-01-01")
  private String confidentialityActionDate;

  @NotNull
  @ApiModelProperty(required = true, readOnly = false)
  private Boolean confidentialityInEffectIndicator;

  @NotNull
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT)
  @JsonProperty(value = "creationDate")
  @gov.ca.cwds.rest.validation.Date(format = DATE_FORMAT, required = true)
  @ApiModelProperty(required = true, readOnly = false, value = "yyyy-MM-dd", example = "2016-01-01")
  private String creationDate;

  @NotNull
  @ApiModelProperty(required = true, readOnly = false)
  private Boolean currCaChildrenServIndicator;

  @NotNull
  @Size(max = 25)
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "other description")
  private String currentlyOtherDescription;

  @NotNull
  @ApiModelProperty(required = true, readOnly = false)
  private Boolean currentlyRegionalCenterIndicator;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT)
  @JsonProperty(value = "deathDate")
  @gov.ca.cwds.rest.validation.Date(format = DATE_FORMAT, required = false)
  @ApiModelProperty(required = false, readOnly = false, value = "yyyy-MM-dd",
      example = "2016-01-01")
  private String deathDate;

  @NotNull
  @ApiModelProperty(required = true, readOnly = false)
  private Boolean deathDateVerifiedIndicator;

  @Size(max = 35)
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "some location")
  private String deathPlace;

  @Size(max = 10)
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "reason text")
  private String deathReasonText;

  @NotNull
  @Size(max = 20)
  @ApiModelProperty(required = false, readOnly = false,
      value = "driver license number of the CLIENT", example = "CA12345678901234567")
  private String driverLicenseNumber;

  @SystemCodeSerializer(logical = true, description = true)
  @NotNull
  @ApiModelProperty(required = false, readOnly = false, example = "1828")
  private Short driverLicenseStateCodeType;

  @Size(max = 50)
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "abc@def.com")
  private String emailAddress;

  @NotEmpty
  @Size(min = 1, max = 1, message = "size must be 1")
  @ApiModelProperty(required = true, readOnly = false, value = "", example = "Y")
  private String estimatedDobCode;

  @Size(max = 1)
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "K")
  private String ethUnableToDetReasonCode;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT)
  @JsonProperty(value = "fatherParentalRightTermDate")
  @gov.ca.cwds.rest.validation.Date(format = DATE_FORMAT, required = false)
  @ApiModelProperty(required = false, readOnly = false, value = "yyyy-MM-dd",
      example = "2016-01-01")
  private String fatherParentalRightTermDate;

  @NotEmpty
  @Size(min = 1, max = 1, message = "size must be 1")
  @ApiModelProperty(required = true, readOnly = false, value = "", example = "U")
  private String genderCode;

  @Size(max = 10)
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "summary text")
  private String healthSummaryText;

  @Size(max = 1)
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "A")
  private String hispUnableToDetReasonCode;

  @NotEmpty
  @Size(min = 1, max = 1, message = "size must be 1")
  @ApiModelProperty(required = true, readOnly = false, value = "", example = "X")
  private String hispanicOriginCode;

  @SystemCodeSerializer(logical = true, description = true)
  @NotNull
  @ApiModelProperty(required = false, readOnly = false, example = "1234")
  private Short immigrationCountryCodeType;

  @SystemCodeSerializer(logical = true, description = true)
  @NotNull
  @ApiModelProperty(required = false, readOnly = false, example = "1199")
  private Short immigrationStatusType;

  @NotEmpty
  @Size(min = 1, max = 2)
  @ApiModelProperty(required = true, readOnly = false, value = "", example = "U")
  private String incapacitatedParentCode;

  @NotNull
  @ApiModelProperty(required = true, readOnly = false)
  private Boolean individualHealthCarePlanIndicator;

  @NotNull
  @ApiModelProperty(required = true, readOnly = false)
  private Boolean limitationOnScpHealthIndicator;

  @NotEmpty
  @Size(min = 1, max = 1, message = "size must be 1")
  @ApiModelProperty(required = true, readOnly = false, value = "", example = "U")
  private String literateCode;

  @NotNull
  @ApiModelProperty(required = true, readOnly = false)
  private Boolean maritalCohabitatnHstryIndicatorVar;

  @SystemCodeSerializer(logical = true, description = true)
  @NotNull
  @ApiModelProperty(required = false, readOnly = false, example = "0")
  private Short maritalStatusType;

  @NotEmpty
  @Size(min = 1, max = 1, message = "size must be 1")
  @ApiModelProperty(required = true, readOnly = false, value = "", example = "N")
  private String militaryStatusCode;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT)
  @JsonProperty(value = "motherParentalRightTermDate")
  @gov.ca.cwds.rest.validation.Date(format = DATE_FORMAT, required = false)
  @ApiModelProperty(required = false, readOnly = false, value = "yyyy-MM-dd",
      example = "2016-01-01")
  private String motherParentalRightTermDate;

  @NotNull
  @Size(max = 6)
  @ApiModelProperty(required = false, readOnly = false, value = "", example = " ")
  private String namePrefixDescription;

  @SystemCodeSerializer(logical = true, description = true)
  @NotNull
  @ApiModelProperty(required = true, readOnly = false, example = "1234")
  private Short nameType;

  @NotNull
  @ApiModelProperty(required = true, readOnly = false)
  private Boolean outstandingWarrantIndicator;

  @NotNull
  @ApiModelProperty(required = true, readOnly = false)
  private Boolean prevCaChildrenServIndicator;

  @NotEmpty
  @Size(max = 25)
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "other description")
  private String prevOtherDescription;

  @NotNull
  @ApiModelProperty(required = true, readOnly = false)
  private Boolean prevRegionalCenterIndicator;

  @SystemCodeSerializer(logical = true, description = true)
  @NotNull
  @ApiModelProperty(required = false, readOnly = false, example = "839")
  private Short primaryEthnicityType;

  @SystemCodeSerializer(logical = true, description = true)
  @NotNull
  @ApiModelProperty(required = false, readOnly = false, example = "1253")
  private Short primaryLanguageType;

  @SystemCodeSerializer(logical = true, description = true)
  @NotNull
  @ApiModelProperty(required = false, readOnly = false, example = "1234")
  private Short religionType;

  @SystemCodeSerializer(logical = true, description = true)
  @NotNull
  @ApiModelProperty(required = false, readOnly = false, example = "1255")
  private Short secondaryLanguageType;

  @NotNull
  @ApiModelProperty(required = true, readOnly = false)
  private Boolean sensitiveHlthInfoOnFileIndicator;

  @NotEmpty
  @Size(min = 1, max = 1, message = "size must be 1")
  @ApiModelProperty(required = true, readOnly = false, value = "", example = "R")
  private String sensitivityIndicator;

  @NotEmpty
  @Size(min = 1, max = 1, message = "size must be 1")
  @ApiModelProperty(required = true, readOnly = false, value = "", example = "N")
  private String soc158PlacementCode;

  @NotNull
  @ApiModelProperty(required = true, readOnly = false)
  private Boolean soc158SealedClientIndicator;

  @NotNull
  @ApiModelProperty(required = true, readOnly = false)
  private String socialSecurityNumChangedCode;

  @NotEmpty
  @Size(max = 9)
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "123456789")
  private String socialSecurityNumber;

  @NotEmpty
  @Size(max = 4)
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "A1")
  private String suffixTitleDescription;

  @NotNull
  @ApiModelProperty(required = true, readOnly = false)
  private Boolean tribalAncestryClientIndicatorVar;

  @NotNull
  @ApiModelProperty(required = true, readOnly = false)
  private Boolean tribalMembrshpVerifctnIndicatorVar;

  @NotEmpty
  @Size(min = 1, max = 2)
  @ApiModelProperty(required = true, readOnly = false, value = "", example = "U")
  private String unemployedParentCode;

  @NotNull
  @ApiModelProperty(required = true, readOnly = false)
  private Boolean zippyCreatedIndicator;

  /**
   * @param existingClientId - existingClientId
   * @param adjudicatedDelinquentIndicator - adjudicatedDelinquentIndicator
   * @param adoptionStatusCode - adoptionStatusCode
   * @param alienRegistrationNumber - alienRegistrationNumber
   * @param birthCity - birthCity
   * @param birthCountryCodeType - birthCountryCodeType
   * @param birthDate - birthDate
   * @param birthFacilityName - birthFacilityName
   * @param birthStateCodeType - birthStateCodeType
   * @param birthplaceVerifiedIndicator - birthplaceVerifiedIndicator
   * @param childClientIndicatorVar - childClientIndicatorVar
   * @param clientIndexNumber - clientIndexNumber
   * @param commentDescription - commentDescription
   * @param commonFirstName - commonFirstName
   * @param commonLastName - commonLastName
   * @param commonMiddleName - commonMiddleName
   * @param confidentialityActionDate - confidentialityActionDate
   * @param confidentialityInEffectIndicator - confidentialityInEffectIndicator
   * @param creationDate - creationDate
   * @param currCaChildrenServIndicator - currCaChildrenServIndicator
   * @param currentlyOtherDescription - currentlyOtherDescription
   * @param currentlyRegionalCenterIndicator - currentlyRegionalCenterIndicator
   * @param deathDate - deathDate
   * @param deathDateVerifiedIndicator - deathDateVerifiedIndicator
   * @param deathPlace - deathPlace
   * @param deathReasonText - deathReasonText
   * @param driverLicenseNumber - driverLicenseNumber
   * @param driverLicenseStateCodeType - driverLicenseStateCodeType
   * @param emailAddress - emailAddress
   * @param estimatedDobCode - estimatedDobCode
   * @param ethUnableToDetReasonCode - ethUnableToDetReasonCode
   * @param fatherParentalRightTermDate - fatherParentalRightTermDate
   * @param genderCode - genderCode
   * @param healthSummaryText - healthSummaryText
   * @param hispUnableToDetReasonCode - hispUnableToDetReasonCode
   * @param hispanicOriginCode - hispanicOriginCode
   * @param immigrationCountryCodeType - immigrationCountryCodeType
   * @param immigrationStatusType - immigrationStatusType
   * @param incapacitatedParentCode - incapacitatedParentCode
   * @param individualHealthCarePlanIndicator - individualHealthCarePlanIndicator
   * @param limitationOnScpHealthIndicator - limitationOnScpHealthIndicator
   * @param literateCode - literateCode
   * @param maritalCohabitatnHstryIndicatorVar - maritalCohabitatnHstryIndicatorVar
   * @param maritalStatusType - maritalStatusType
   * @param militaryStatusCode - militaryStatusCode
   * @param motherParentalRightTermDate - motherParentalRightTermDate
   * @param namePrefixDescription - namePrefixDescription
   * @param nameType - nameType
   * @param outstandingWarrantIndicator - outstandingWarrantIndicator
   * @param prevCaChildrenServIndicator - prevCaChildrenServIndicator
   * @param prevOtherDescription - prevOtherDescription
   * @param prevRegionalCenterIndicator - prevRegionalCenterIndicator
   * @param primaryEthnicityType - primaryEthnicityType
   * @param primaryLanguageType - primaryLanguageType
   * @param religionType - religionType
   * @param secondaryLanguageType - secondaryLanguageType
   * @param sensitiveHlthInfoOnFileIndicator - sensitiveHlthInfoOnFileIndicator
   * @param sensitivityIndicator - sensitivityIndicator
   * @param soc158PlacementCode - soc158PlacementCode
   * @param soc158SealedClientIndicator - soc158SealedClientIndicator
   * @param socialSecurityNumChangedCode - socialSecurityNumChangedCode
   * @param socialSecurityNumber - socialSecurityNumber
   * @param suffixTitleDescription - suffixTitleDescription
   * @param tribalAncestryClientIndicatorVar - tribalAncestryClientIndicatorVar
   * @param tribalMembrshpVerifctnIndicatorVar - tribalMembrshpVerifctnIndicatorVar
   * @param unemployedParentCode - unemployedParentCode
   * @param zippyCreatedIndicator - zippyCreatedIndicator
   */
  @JsonCreator
  public Client(@JsonProperty("existingClientId") String existingClientId,
      @JsonProperty("adjudicatedDelinquentIndicator") Boolean adjudicatedDelinquentIndicator,
      @JsonProperty("adoptionStatusCode") String adoptionStatusCode,
      @JsonProperty("alienRegistrationNumber") String alienRegistrationNumber,
      @JsonProperty("birthCity") String birthCity,
      @JsonProperty("birthCountryCodeType") Short birthCountryCodeType,
      @JsonProperty("birthDate") String birthDate,
      @JsonProperty("birthFacilityName") String birthFacilityName,
      @JsonProperty("birthStateCodeType") Short birthStateCodeType,
      @JsonProperty("birthplaceVerifiedIndicator") Boolean birthplaceVerifiedIndicator,
      @JsonProperty("childClientIndicatorVar") Boolean childClientIndicatorVar,
      @JsonProperty("clientIndexNumber") String clientIndexNumber,
      @JsonProperty("commentDescription") String commentDescription,
      @JsonProperty("commonFirstName") String commonFirstName,
      @JsonProperty("commonLastName") String commonLastName,
      @JsonProperty("commonMiddleName") String commonMiddleName,
      @JsonProperty("confidentialityActionDate") String confidentialityActionDate,
      @JsonProperty("confidentialityInEffectIndicator") Boolean confidentialityInEffectIndicator,
      @JsonProperty("creationDate") String creationDate,
      @JsonProperty("currCaChildrenServIndicator") Boolean currCaChildrenServIndicator,
      @JsonProperty("currentlyOtherDescription") String currentlyOtherDescription,
      @JsonProperty("currentlyRegionalCenterIndicator") Boolean currentlyRegionalCenterIndicator,
      @JsonProperty("deathDate") String deathDate,
      @JsonProperty("deathDateVerifiedIndicator") Boolean deathDateVerifiedIndicator,
      @JsonProperty("deathPlace") String deathPlace,
      @JsonProperty("deathReasonText") String deathReasonText,
      @JsonProperty("driverLicenseNumber") String driverLicenseNumber,
      @JsonProperty("driverLicenseStateCodeType") Short driverLicenseStateCodeType,
      @JsonProperty("emailAddress") String emailAddress,
      @JsonProperty("estimatedDobCode") String estimatedDobCode,
      @JsonProperty("ethUnableToDetReasonCode") String ethUnableToDetReasonCode,
      @JsonProperty("fatherParentalRightTermDate") String fatherParentalRightTermDate,
      @JsonProperty("genderCode") String genderCode,
      @JsonProperty("healthSummaryText") String healthSummaryText,
      @JsonProperty("hispUnableToDetReasonCode") String hispUnableToDetReasonCode,
      @JsonProperty("hispanicOriginCode") String hispanicOriginCode,
      @JsonProperty("immigrationCountryCodeType") Short immigrationCountryCodeType,
      @JsonProperty("immigrationStatusType") Short immigrationStatusType,
      @JsonProperty("incapacitatedParentCode") String incapacitatedParentCode,
      @JsonProperty("individualHealthCarePlanIndicator") Boolean individualHealthCarePlanIndicator,
      @JsonProperty("limitationOnScpHealthIndicator") Boolean limitationOnScpHealthIndicator,
      @JsonProperty("literateCode") String literateCode,
      @JsonProperty("maritalCohabitatnHstryIndicatorVar") Boolean maritalCohabitatnHstryIndicatorVar,
      @JsonProperty("maritalStatusType") Short maritalStatusType,
      @JsonProperty("militaryStatusCode") String militaryStatusCode,
      @JsonProperty("motherParentalRightTermDate") String motherParentalRightTermDate,
      @JsonProperty("namePrefixDescription") String namePrefixDescription,
      @JsonProperty("nameType") Short nameType,
      @JsonProperty("outstandingWarrantIndicator") Boolean outstandingWarrantIndicator,
      @JsonProperty("prevCaChildrenServIndicator") Boolean prevCaChildrenServIndicator,
      @JsonProperty("prevOtherDescription") String prevOtherDescription,
      @JsonProperty("prevRegionalCenterIndicator") Boolean prevRegionalCenterIndicator,
      @JsonProperty("primaryEthnicityType") Short primaryEthnicityType,
      @JsonProperty("primaryLanguageType") Short primaryLanguageType,
      @JsonProperty("religionType") Short religionType,
      @JsonProperty("secondaryLanguageType") Short secondaryLanguageType,
      @JsonProperty("sensitiveHlthInfoOnFileIndicator") Boolean sensitiveHlthInfoOnFileIndicator,
      @JsonProperty("sensitivityIndicator") String sensitivityIndicator,
      @JsonProperty("soc158PlacementCode") String soc158PlacementCode,
      @JsonProperty("soc158SealedClientIndicator") Boolean soc158SealedClientIndicator,
      @JsonProperty("socialSecurityNumChangedCode") String socialSecurityNumChangedCode,
      @JsonProperty("socialSecurityNumber") String socialSecurityNumber,
      @JsonProperty("suffixTitleDescription") String suffixTitleDescription,
      @JsonProperty("tribalAncestryClientIndicatorVar") Boolean tribalAncestryClientIndicatorVar,
      @JsonProperty("tribalMembrshpVerifctnIndicatorVar") Boolean tribalMembrshpVerifctnIndicatorVar,
      @JsonProperty("unemployedParentCode") String unemployedParentCode,
      @JsonProperty("zippyCreatedIndicator") Boolean zippyCreatedIndicator) {
    super();
    this.existingClientId = existingClientId;
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
   * @param persistedClient - persistedClient object
   * @param isExist - ExistingId
   */
  public Client(gov.ca.cwds.data.persistence.cms.Client persistedClient, boolean isExist) {
    this.existingClientId = isExist ? persistedClient.getId() : "";
    this.adjudicatedDelinquentIndicator =
        DomainChef.uncookBooleanString(persistedClient.getAdjudicatedDelinquentIndicator());
    this.adoptionStatusCode = persistedClient.getAdoptionStatusCode();
    this.alienRegistrationNumber = persistedClient.getAlienRegistrationNumber();
    this.birthCity = persistedClient.getBirthCity();
    this.birthCountryCodeType = persistedClient.getBirthCountryCodeType();
    this.birthDate = DomainChef.cookDate(persistedClient.getBirthDate());
    this.birthFacilityName = persistedClient.getBirthFacilityName();
    this.birthStateCodeType = persistedClient.getBirthStateCodeType();
    this.birthplaceVerifiedIndicator =
        DomainChef.uncookBooleanString(persistedClient.getBirthplaceVerifiedIndicator());
    this.childClientIndicatorVar =
        DomainChef.uncookBooleanString(persistedClient.getChildClientIndicatorVar());
    this.clientIndexNumber = persistedClient.getClientIndexNumber();
    this.commentDescription = persistedClient.getCommentDescription();
    this.commonFirstName = persistedClient.getCommonFirstName();
    this.commonLastName = persistedClient.getCommonLastName();
    this.commonMiddleName = persistedClient.getCommonMiddleName();
    this.confidentialityActionDate =
        DomainChef.cookDate(persistedClient.getConfidentialityActionDate());
    this.confidentialityInEffectIndicator =
        DomainChef.uncookBooleanString(persistedClient.getConfidentialityInEffectIndicator());
    this.creationDate = DomainChef.cookDate(persistedClient.getCreationDate());
    this.currCaChildrenServIndicator =
        DomainChef.uncookBooleanString(persistedClient.getCurrCaChildrenServIndicator());
    this.currentlyOtherDescription = persistedClient.getCurrentlyOtherDescription();
    this.currentlyRegionalCenterIndicator =
        DomainChef.uncookBooleanString(persistedClient.getCurrentlyRegionalCenterIndicator());
    this.deathDate = DomainChef.cookDate(persistedClient.getDeathDate());
    this.deathDateVerifiedIndicator =
        DomainChef.uncookBooleanString(persistedClient.getDeathDateVerifiedIndicator());
    this.deathPlace = persistedClient.getDeathPlace();
    this.deathReasonText = persistedClient.getDeathReasonText();
    this.driverLicenseNumber = persistedClient.getDriverLicenseNumber();
    this.driverLicenseStateCodeType = persistedClient.getDriverLicenseStateCodeType();
    this.emailAddress = persistedClient.getEmailAddress();
    this.estimatedDobCode = persistedClient.getEstimatedDobCode();
    this.ethUnableToDetReasonCode = persistedClient.getEthUnableToDetReasonCode();
    this.fatherParentalRightTermDate =
        DomainChef.cookDate(persistedClient.getFatherParentalRightTermDate());
    this.genderCode = persistedClient.getGenderCode();
    this.healthSummaryText = persistedClient.getHealthSummaryText();
    this.hispUnableToDetReasonCode = persistedClient.getHispUnableToDetReasonCode();
    this.hispanicOriginCode = persistedClient.getHispanicOriginCode();
    this.immigrationCountryCodeType = persistedClient.getImmigrationCountryCodeType();
    this.immigrationStatusType = persistedClient.getImmigrationStatusType();
    this.incapacitatedParentCode = persistedClient.getIncapacitatedParentCode();
    this.individualHealthCarePlanIndicator =
        DomainChef.uncookBooleanString(persistedClient.getIndividualHealthCarePlanIndicator());
    this.limitationOnScpHealthIndicator =
        DomainChef.uncookBooleanString(persistedClient.getLimitationOnScpHealthIndicator());
    this.literateCode = persistedClient.getLiterateCode();
    this.maritalCohabitatnHstryIndicatorVar =
        DomainChef.uncookBooleanString(persistedClient.getMaritalCohabitatnHstryIndicatorVar());
    this.maritalStatusType = persistedClient.getMaritalStatusType();
    this.militaryStatusCode = persistedClient.getMilitaryStatusCode();
    this.motherParentalRightTermDate =
        DomainChef.cookDate(persistedClient.getMotherParentalRightTermDate());
    this.namePrefixDescription = persistedClient.getNamePrefixDescription();
    this.nameType = persistedClient.getNameType();
    this.outstandingWarrantIndicator =
        DomainChef.uncookBooleanString(persistedClient.getOutstandingWarrantIndicator());
    this.prevCaChildrenServIndicator =
        DomainChef.uncookBooleanString(persistedClient.getPrevCaChildrenServIndicator());
    this.prevOtherDescription = persistedClient.getPrevOtherDescription();
    this.prevRegionalCenterIndicator =
        DomainChef.uncookBooleanString(persistedClient.getPrevRegionalCenterIndicator());
    this.primaryEthnicityType = persistedClient.getPrimaryEthnicityType();
    this.primaryLanguageType = persistedClient.getPrimaryLanguageType();
    this.religionType = persistedClient.getReligionType();
    this.secondaryLanguageType = persistedClient.getSecondaryLanguageType();
    this.sensitiveHlthInfoOnFileIndicator =
        DomainChef.uncookBooleanString(persistedClient.getSensitiveHlthInfoOnFileIndicator());
    this.sensitivityIndicator = persistedClient.getSensitivityIndicator();
    this.soc158PlacementCode = persistedClient.getSoc158PlacementCode();
    this.soc158SealedClientIndicator =
        DomainChef.uncookBooleanString(persistedClient.getSoc158SealedClientIndicator());
    this.socialSecurityNumChangedCode = persistedClient.getSocialSecurityNumChangedCode();
    this.socialSecurityNumber = persistedClient.getSocialSecurityNumber();
    this.suffixTitleDescription = persistedClient.getSuffixTitleDescription();
    this.tribalAncestryClientIndicatorVar =
        DomainChef.uncookBooleanString(persistedClient.getTribalAncestryClientIndicatorVar());
    this.tribalMembrshpVerifctnIndicatorVar =
        DomainChef.uncookBooleanString(persistedClient.getTribalMembrshpVerifctnIndicatorVar());
    this.unemployedParentCode = persistedClient.getUnemployedParentCode();
    this.zippyCreatedIndicator =
        DomainChef.uncookBooleanString(persistedClient.getZippyCreatedIndicator());
  }

  /**
   * @return the existingClientId
   */
  public String getExistingClientId() {
    return existingClientId;
  }

  /**
   * @return the adjudicatedDelinquentIndicator
   */
  public Boolean getAdjudicatedDelinquentIndicator() {
    return adjudicatedDelinquentIndicator;
  }

  /**
   * @return the adoptionStatusCode
   */
  public String getAdoptionStatusCode() {
    return adoptionStatusCode;
  }

  /**
   * @return the alienRegistrationNumber
   */
  public String getAlienRegistrationNumber() {
    return alienRegistrationNumber;
  }

  /**
   * @return the birthCity
   */
  public String getBirthCity() {
    return birthCity;
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
  public String getBirthDate() {
    return birthDate;
  }

  /**
   * @return the birthFacilityName
   */
  public String getBirthFacilityName() {
    return birthFacilityName;
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
  public Boolean getBirthplaceVerifiedIndicator() {
    return birthplaceVerifiedIndicator;
  }

  /**
   * @return the childClientIndicatorVar
   */
  public Boolean getChildClientIndicatorVar() {
    return childClientIndicatorVar;
  }

  /**
   * @return the clientIndexNumber
   */
  public String getClientIndexNumber() {
    return clientIndexNumber;
  }

  /**
   * @return the commentDescription
   */
  public String getCommentDescription() {
    return commentDescription;
  }

  /**
   * @return the commonFirstName
   */
  public String getCommonFirstName() {
    return commonFirstName;
  }

  /**
   * @return the commonLastName
   */
  public String getCommonLastName() {
    return commonLastName;
  }

  /**
   * @return the commonMiddleName
   */
  public String getCommonMiddleName() {
    return commonMiddleName;
  }

  /**
   * @return the confidentialityActionDate
   */
  public String getConfidentialityActionDate() {
    return confidentialityActionDate;
  }

  /**
   * @return the confidentialityInEffectIndicator
   */
  public Boolean getConfidentialityInEffectIndicator() {
    return confidentialityInEffectIndicator;
  }

  /**
   * @return the creationDate
   */
  public String getCreationDate() {
    return creationDate;
  }

  /**
   * @return the currCaChildrenServIndicator
   */
  public Boolean getCurrCaChildrenServIndicator() {
    return currCaChildrenServIndicator;
  }

  /**
   * @return the currentlyOtherDescription
   */
  public String getCurrentlyOtherDescription() {
    return currentlyOtherDescription;
  }

  /**
   * @return the currentlyRegionalCenterIndicator
   */
  public Boolean getCurrentlyRegionalCenterIndicator() {
    return currentlyRegionalCenterIndicator;
  }

  /**
   * @return the deathDate
   */
  public String getDeathDate() {
    return deathDate;
  }

  /**
   * @return the deathDateVerifiedIndicator
   */
  public Boolean getDeathDateVerifiedIndicator() {
    return deathDateVerifiedIndicator;
  }

  /**
   * @return the deathPlace
   */
  public String getDeathPlace() {
    return deathPlace;
  }

  /**
   * @return the deathReasonText
   */
  public String getDeathReasonText() {
    return deathReasonText;
  }

  /**
   * @return the driverLicenseNumber
   */
  public String getDriverLicenseNumber() {
    return driverLicenseNumber;
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
    return emailAddress;
  }

  /**
   * @return the estimatedDobCode
   */
  public String getEstimatedDobCode() {
    return estimatedDobCode;
  }

  /**
   * @return the ethUnableToDetReasonCode
   */
  public String getEthUnableToDetReasonCode() {
    return ethUnableToDetReasonCode;
  }

  /**
   * @return the fatherParentalRightTermDate
   */
  public String getFatherParentalRightTermDate() {
    return fatherParentalRightTermDate;
  }

  /**
   * @return the genderCode
   */
  public String getGenderCode() {
    return genderCode;
  }

  /**
   * @return the healthSummaryText
   */
  public String getHealthSummaryText() {
    return healthSummaryText;
  }

  /**
   * @return the hispUnableToDetReasonCode
   */
  public String getHispUnableToDetReasonCode() {
    return hispUnableToDetReasonCode;
  }

  /**
   * @return the hispanicOriginCode
   */
  public String getHispanicOriginCode() {
    return hispanicOriginCode;
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
    return incapacitatedParentCode;
  }

  /**
   * @return the individualHealthCarePlanIndicator
   */
  public Boolean getIndividualHealthCarePlanIndicator() {
    return individualHealthCarePlanIndicator;
  }

  /**
   * @return the limitationOnScpHealthIndicator
   */
  public Boolean getLimitationOnScpHealthIndicator() {
    return limitationOnScpHealthIndicator;
  }

  /**
   * @return the literateCode
   */
  public String getLiterateCode() {
    return literateCode;
  }

  /**
   * @return the maritalCohabitatnHstryIndicatorVar
   */
  public Boolean getMaritalCohabitatnHstryIndicatorVar() {
    return maritalCohabitatnHstryIndicatorVar;
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
    return militaryStatusCode;
  }

  /**
   * @return the motherParentalRightTermDate
   */
  public String getMotherParentalRightTermDate() {
    return motherParentalRightTermDate;
  }

  /**
   * @return the namePrefixDescription
   */
  public String getNamePrefixDescription() {
    return namePrefixDescription;
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
  public Boolean getOutstandingWarrantIndicator() {
    return outstandingWarrantIndicator;
  }

  /**
   * @return the prevCaChildrenServIndicator
   */
  public Boolean getPrevCaChildrenServIndicator() {
    return prevCaChildrenServIndicator;
  }

  /**
   * @return the prevOtherDescription
   */
  public String getPrevOtherDescription() {
    return prevOtherDescription;
  }

  /**
   * @return the prevRegionalCenterIndicator
   */
  public Boolean getPrevRegionalCenterIndicator() {
    return prevRegionalCenterIndicator;
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
  public Boolean getSensitiveHlthInfoOnFileIndicator() {
    return sensitiveHlthInfoOnFileIndicator;
  }

  /**
   * @return the sensitivityIndicator
   */
  public String getSensitivityIndicator() {
    return sensitivityIndicator;
  }

  /**
   * @return the soc158PlacementCode
   */
  public String getSoc158PlacementCode() {
    return soc158PlacementCode;
  }

  /**
   * @return the soc158SealedClientIndicator
   */
  public Boolean getSoc158SealedClientIndicator() {
    return soc158SealedClientIndicator;
  }

  /**
   * @return the socialSecurityNumChangedCode
   */
  public String getSocialSecurityNumChangedCode() {
    return socialSecurityNumChangedCode;
  }

  /**
   * @return the socialSecurityNumber
   */
  public String getSocialSecurityNumber() {
    return socialSecurityNumber;
  }

  /**
   * @return the suffixTitleDescription
   */
  public String getSuffixTitleDescription() {
    return suffixTitleDescription;
  }

  /**
   * @return the tribalAncestryClientIndicatorVar
   */
  public Boolean getTribalAncestryClientIndicatorVar() {
    return tribalAncestryClientIndicatorVar;
  }

  /**
   * @return the tribalMembrshpVerifctnIndicatorVar
   */
  public Boolean getTribalMembrshpVerifctnIndicatorVar() {
    return tribalMembrshpVerifctnIndicatorVar;
  }

  /**
   * @return the unemployedParentCode
   */
  public String getUnemployedParentCode() {
    return unemployedParentCode;
  }

  /**
   * @return the zippyCreatedIndicator
   */
  public Boolean getZippyCreatedIndicator() {
    return zippyCreatedIndicator;
  }

  @Override
  public final int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((existingClientId == null) ? 0 : existingClientId.hashCode());
    result = prime * result + ((adjudicatedDelinquentIndicator == null) ? 0
        : adjudicatedDelinquentIndicator.hashCode());
    result = prime * result + ((adoptionStatusCode == null) ? 0 : adoptionStatusCode.hashCode());
    result = prime * result
        + ((alienRegistrationNumber == null) ? 0 : alienRegistrationNumber.hashCode());
    result = prime * result + ((birthCity == null) ? 0 : birthCity.hashCode());
    result =
        prime * result + ((birthCountryCodeType == null) ? 0 : birthCountryCodeType.hashCode());
    result = prime * result + ((birthDate == null) ? 0 : birthDate.hashCode());
    result = prime * result + ((birthFacilityName == null) ? 0 : birthFacilityName.hashCode());
    result = prime * result + ((birthStateCodeType == null) ? 0 : birthStateCodeType.hashCode());
    result = prime * result
        + ((birthplaceVerifiedIndicator == null) ? 0 : birthplaceVerifiedIndicator.hashCode());
    result = prime * result
        + ((childClientIndicatorVar == null) ? 0 : childClientIndicatorVar.hashCode());
    result = prime * result + ((clientIndexNumber == null) ? 0 : clientIndexNumber.hashCode());
    result = prime * result + ((commentDescription == null) ? 0 : commentDescription.hashCode());
    result = prime * result + ((commonFirstName == null) ? 0 : commonFirstName.hashCode());
    result = prime * result + ((commonLastName == null) ? 0 : commonLastName.hashCode());
    result = prime * result + ((commonMiddleName == null) ? 0 : commonMiddleName.hashCode());
    result = prime * result
        + ((confidentialityActionDate == null) ? 0 : confidentialityActionDate.hashCode());
    result = prime * result + ((confidentialityInEffectIndicator == null) ? 0
        : confidentialityInEffectIndicator.hashCode());
    result = prime * result + ((creationDate == null) ? 0 : creationDate.hashCode());
    result = prime * result
        + ((currCaChildrenServIndicator == null) ? 0 : currCaChildrenServIndicator.hashCode());
    result = prime * result
        + ((currentlyOtherDescription == null) ? 0 : currentlyOtherDescription.hashCode());
    result = prime * result + ((currentlyRegionalCenterIndicator == null) ? 0
        : currentlyRegionalCenterIndicator.hashCode());
    result = prime * result + ((deathDate == null) ? 0 : deathDate.hashCode());
    result = prime * result
        + ((deathDateVerifiedIndicator == null) ? 0 : deathDateVerifiedIndicator.hashCode());
    result = prime * result + ((deathPlace == null) ? 0 : deathPlace.hashCode());
    result = prime * result + ((deathReasonText == null) ? 0 : deathReasonText.hashCode());
    result = prime * result + ((driverLicenseNumber == null) ? 0 : driverLicenseNumber.hashCode());
    result = prime * result
        + ((driverLicenseStateCodeType == null) ? 0 : driverLicenseStateCodeType.hashCode());
    result = prime * result + ((emailAddress == null) ? 0 : emailAddress.hashCode());
    result = prime * result + ((estimatedDobCode == null) ? 0 : estimatedDobCode.hashCode());
    result = prime * result
        + ((ethUnableToDetReasonCode == null) ? 0 : ethUnableToDetReasonCode.hashCode());
    result = prime * result
        + ((fatherParentalRightTermDate == null) ? 0 : fatherParentalRightTermDate.hashCode());
    result = prime * result + ((genderCode == null) ? 0 : genderCode.hashCode());
    result = prime * result + ((healthSummaryText == null) ? 0 : healthSummaryText.hashCode());
    result = prime * result
        + ((hispUnableToDetReasonCode == null) ? 0 : hispUnableToDetReasonCode.hashCode());
    result = prime * result + ((hispanicOriginCode == null) ? 0 : hispanicOriginCode.hashCode());
    result = prime * result
        + ((immigrationCountryCodeType == null) ? 0 : immigrationCountryCodeType.hashCode());
    result =
        prime * result + ((immigrationStatusType == null) ? 0 : immigrationStatusType.hashCode());
    result = prime * result
        + ((incapacitatedParentCode == null) ? 0 : incapacitatedParentCode.hashCode());
    result = prime * result + ((individualHealthCarePlanIndicator == null) ? 0
        : individualHealthCarePlanIndicator.hashCode());
    result = prime * result + ((limitationOnScpHealthIndicator == null) ? 0
        : limitationOnScpHealthIndicator.hashCode());
    result = prime * result + ((literateCode == null) ? 0 : literateCode.hashCode());
    result = prime * result + ((maritalCohabitatnHstryIndicatorVar == null) ? 0
        : maritalCohabitatnHstryIndicatorVar.hashCode());
    result = prime * result + ((maritalStatusType == null) ? 0 : maritalStatusType.hashCode());
    result = prime * result + ((militaryStatusCode == null) ? 0 : militaryStatusCode.hashCode());
    result = prime * result
        + ((motherParentalRightTermDate == null) ? 0 : motherParentalRightTermDate.hashCode());
    result =
        prime * result + ((namePrefixDescription == null) ? 0 : namePrefixDescription.hashCode());
    result = prime * result + ((nameType == null) ? 0 : nameType.hashCode());
    result = prime * result
        + ((outstandingWarrantIndicator == null) ? 0 : outstandingWarrantIndicator.hashCode());
    result = prime * result
        + ((prevCaChildrenServIndicator == null) ? 0 : prevCaChildrenServIndicator.hashCode());
    result =
        prime * result + ((prevOtherDescription == null) ? 0 : prevOtherDescription.hashCode());
    result = prime * result
        + ((prevRegionalCenterIndicator == null) ? 0 : prevRegionalCenterIndicator.hashCode());
    result =
        prime * result + ((primaryEthnicityType == null) ? 0 : primaryEthnicityType.hashCode());
    result = prime * result + ((primaryLanguageType == null) ? 0 : primaryLanguageType.hashCode());
    result = prime * result + ((religionType == null) ? 0 : religionType.hashCode());
    result =
        prime * result + ((secondaryLanguageType == null) ? 0 : secondaryLanguageType.hashCode());
    result = prime * result + ((sensitiveHlthInfoOnFileIndicator == null) ? 0
        : sensitiveHlthInfoOnFileIndicator.hashCode());
    result =
        prime * result + ((sensitivityIndicator == null) ? 0 : sensitivityIndicator.hashCode());
    result = prime * result + ((soc158PlacementCode == null) ? 0 : soc158PlacementCode.hashCode());
    result = prime * result
        + ((soc158SealedClientIndicator == null) ? 0 : soc158SealedClientIndicator.hashCode());
    result = prime * result
        + ((socialSecurityNumChangedCode == null) ? 0 : socialSecurityNumChangedCode.hashCode());
    result =
        prime * result + ((socialSecurityNumber == null) ? 0 : socialSecurityNumber.hashCode());
    result =
        prime * result + ((suffixTitleDescription == null) ? 0 : suffixTitleDescription.hashCode());
    result = prime * result + ((tribalAncestryClientIndicatorVar == null) ? 0
        : tribalAncestryClientIndicatorVar.hashCode());
    result = prime * result + ((tribalMembrshpVerifctnIndicatorVar == null) ? 0
        : tribalMembrshpVerifctnIndicatorVar.hashCode());
    result =
        prime * result + ((unemployedParentCode == null) ? 0 : unemployedParentCode.hashCode());
    result =
        prime * result + ((zippyCreatedIndicator == null) ? 0 : zippyCreatedIndicator.hashCode());
    return result;
  }

  @Override
  public final boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (!(obj instanceof Client)) {
      return false;
    }
    Client other = (Client) obj;
    if (existingClientId == null) {
      if (other.existingClientId != null)
        return false;
    } else if (!existingClientId.equals(other.existingClientId))
      return false;
    if (adjudicatedDelinquentIndicator == null) {
      if (other.adjudicatedDelinquentIndicator != null)
        return false;
    } else if (!adjudicatedDelinquentIndicator.equals(other.adjudicatedDelinquentIndicator))
      return false;
    if (adoptionStatusCode == null) {
      if (other.adoptionStatusCode != null)
        return false;
    } else if (!adoptionStatusCode.equals(other.adoptionStatusCode))
      return false;
    if (alienRegistrationNumber == null) {
      if (other.alienRegistrationNumber != null)
        return false;
    } else if (!alienRegistrationNumber.equals(other.alienRegistrationNumber))
      return false;
    if (birthCity == null) {
      if (other.birthCity != null)
        return false;
    } else if (!birthCity.equals(other.birthCity))
      return false;
    if (birthCountryCodeType == null) {
      if (other.birthCountryCodeType != null)
        return false;
    } else if (!birthCountryCodeType.equals(other.birthCountryCodeType))
      return false;
    if (birthDate == null) {
      if (other.birthDate != null)
        return false;
    } else if (!birthDate.equals(other.birthDate))
      return false;
    if (birthFacilityName == null) {
      if (other.birthFacilityName != null)
        return false;
    } else if (!birthFacilityName.equals(other.birthFacilityName))
      return false;
    if (birthStateCodeType == null) {
      if (other.birthStateCodeType != null)
        return false;
    } else if (!birthStateCodeType.equals(other.birthStateCodeType))
      return false;
    if (birthplaceVerifiedIndicator == null) {
      if (other.birthplaceVerifiedIndicator != null)
        return false;
    } else if (!birthplaceVerifiedIndicator.equals(other.birthplaceVerifiedIndicator))
      return false;
    if (childClientIndicatorVar == null) {
      if (other.childClientIndicatorVar != null)
        return false;
    } else if (!childClientIndicatorVar.equals(other.childClientIndicatorVar))
      return false;
    if (clientIndexNumber == null) {
      if (other.clientIndexNumber != null)
        return false;
    } else if (!clientIndexNumber.equals(other.clientIndexNumber))
      return false;
    if (commentDescription == null) {
      if (other.commentDescription != null)
        return false;
    } else if (!commentDescription.equals(other.commentDescription))
      return false;
    if (commonFirstName == null) {
      if (other.commonFirstName != null)
        return false;
    } else if (!commonFirstName.equals(other.commonFirstName))
      return false;
    if (commonLastName == null) {
      if (other.commonLastName != null)
        return false;
    } else if (!commonLastName.equals(other.commonLastName))
      return false;
    if (commonMiddleName == null) {
      if (other.commonMiddleName != null)
        return false;
    } else if (!commonMiddleName.equals(other.commonMiddleName))
      return false;
    if (confidentialityActionDate == null) {
      if (other.confidentialityActionDate != null)
        return false;
    } else if (!confidentialityActionDate.equals(other.confidentialityActionDate))
      return false;
    if (confidentialityInEffectIndicator == null) {
      if (other.confidentialityInEffectIndicator != null)
        return false;
    } else if (!confidentialityInEffectIndicator.equals(other.confidentialityInEffectIndicator))
      return false;
    if (creationDate == null) {
      if (other.creationDate != null)
        return false;
    } else if (!creationDate.equals(other.creationDate))
      return false;
    if (currCaChildrenServIndicator == null) {
      if (other.currCaChildrenServIndicator != null)
        return false;
    } else if (!currCaChildrenServIndicator.equals(other.currCaChildrenServIndicator))
      return false;
    if (currentlyOtherDescription == null) {
      if (other.currentlyOtherDescription != null)
        return false;
    } else if (!currentlyOtherDescription.equals(other.currentlyOtherDescription))
      return false;
    if (currentlyRegionalCenterIndicator == null) {
      if (other.currentlyRegionalCenterIndicator != null)
        return false;
    } else if (!currentlyRegionalCenterIndicator.equals(other.currentlyRegionalCenterIndicator))
      return false;
    if (deathDate == null) {
      if (other.deathDate != null)
        return false;
    } else if (!deathDate.equals(other.deathDate))
      return false;
    if (deathDateVerifiedIndicator == null) {
      if (other.deathDateVerifiedIndicator != null)
        return false;
    } else if (!deathDateVerifiedIndicator.equals(other.deathDateVerifiedIndicator))
      return false;
    if (deathPlace == null) {
      if (other.deathPlace != null)
        return false;
    } else if (!deathPlace.equals(other.deathPlace))
      return false;
    if (deathReasonText == null) {
      if (other.deathReasonText != null)
        return false;
    } else if (!deathReasonText.equals(other.deathReasonText))
      return false;
    if (driverLicenseNumber == null) {
      if (other.driverLicenseNumber != null)
        return false;
    } else if (!driverLicenseNumber.equals(other.driverLicenseNumber))
      return false;
    if (driverLicenseStateCodeType == null) {
      if (other.driverLicenseStateCodeType != null)
        return false;
    } else if (!driverLicenseStateCodeType.equals(other.driverLicenseStateCodeType))
      return false;
    if (emailAddress == null) {
      if (other.emailAddress != null)
        return false;
    } else if (!emailAddress.equals(other.emailAddress))
      return false;
    if (estimatedDobCode == null) {
      if (other.estimatedDobCode != null)
        return false;
    } else if (!estimatedDobCode.equals(other.estimatedDobCode))
      return false;
    if (ethUnableToDetReasonCode == null) {
      if (other.ethUnableToDetReasonCode != null)
        return false;
    } else if (!ethUnableToDetReasonCode.equals(other.ethUnableToDetReasonCode))
      return false;
    if (fatherParentalRightTermDate == null) {
      if (other.fatherParentalRightTermDate != null)
        return false;
    } else if (!fatherParentalRightTermDate.equals(other.fatherParentalRightTermDate))
      return false;
    if (genderCode == null) {
      if (other.genderCode != null)
        return false;
    } else if (!genderCode.equals(other.genderCode))
      return false;
    if (healthSummaryText == null) {
      if (other.healthSummaryText != null)
        return false;
    } else if (!healthSummaryText.equals(other.healthSummaryText))
      return false;
    if (hispUnableToDetReasonCode == null) {
      if (other.hispUnableToDetReasonCode != null)
        return false;
    } else if (!hispUnableToDetReasonCode.equals(other.hispUnableToDetReasonCode))
      return false;
    if (hispanicOriginCode == null) {
      if (other.hispanicOriginCode != null)
        return false;
    } else if (!hispanicOriginCode.equals(other.hispanicOriginCode))
      return false;
    if (immigrationCountryCodeType == null) {
      if (other.immigrationCountryCodeType != null)
        return false;
    } else if (!immigrationCountryCodeType.equals(other.immigrationCountryCodeType))
      return false;
    if (immigrationStatusType == null) {
      if (other.immigrationStatusType != null)
        return false;
    } else if (!immigrationStatusType.equals(other.immigrationStatusType))
      return false;
    if (incapacitatedParentCode == null) {
      if (other.incapacitatedParentCode != null)
        return false;
    } else if (!incapacitatedParentCode.equals(other.incapacitatedParentCode))
      return false;
    if (individualHealthCarePlanIndicator == null) {
      if (other.individualHealthCarePlanIndicator != null)
        return false;
    } else if (!individualHealthCarePlanIndicator.equals(other.individualHealthCarePlanIndicator))
      return false;
    if (limitationOnScpHealthIndicator == null) {
      if (other.limitationOnScpHealthIndicator != null)
        return false;
    } else if (!limitationOnScpHealthIndicator.equals(other.limitationOnScpHealthIndicator))
      return false;
    if (literateCode == null) {
      if (other.literateCode != null)
        return false;
    } else if (!literateCode.equals(other.literateCode))
      return false;
    if (maritalCohabitatnHstryIndicatorVar == null) {
      if (other.maritalCohabitatnHstryIndicatorVar != null)
        return false;
    } else if (!maritalCohabitatnHstryIndicatorVar.equals(other.maritalCohabitatnHstryIndicatorVar))
      return false;
    if (maritalStatusType == null) {
      if (other.maritalStatusType != null)
        return false;
    } else if (!maritalStatusType.equals(other.maritalStatusType))
      return false;
    if (militaryStatusCode == null) {
      if (other.militaryStatusCode != null)
        return false;
    } else if (!militaryStatusCode.equals(other.militaryStatusCode))
      return false;
    if (motherParentalRightTermDate == null) {
      if (other.motherParentalRightTermDate != null)
        return false;
    } else if (!motherParentalRightTermDate.equals(other.motherParentalRightTermDate))
      return false;
    if (namePrefixDescription == null) {
      if (other.namePrefixDescription != null)
        return false;
    } else if (!namePrefixDescription.equals(other.namePrefixDescription))
      return false;
    if (nameType == null) {
      if (other.nameType != null)
        return false;
    } else if (!nameType.equals(other.nameType))
      return false;
    if (outstandingWarrantIndicator == null) {
      if (other.outstandingWarrantIndicator != null)
        return false;
    } else if (!outstandingWarrantIndicator.equals(other.outstandingWarrantIndicator))
      return false;
    if (prevCaChildrenServIndicator == null) {
      if (other.prevCaChildrenServIndicator != null)
        return false;
    } else if (!prevCaChildrenServIndicator.equals(other.prevCaChildrenServIndicator))
      return false;
    if (prevOtherDescription == null) {
      if (other.prevOtherDescription != null)
        return false;
    } else if (!prevOtherDescription.equals(other.prevOtherDescription))
      return false;
    if (prevRegionalCenterIndicator == null) {
      if (other.prevRegionalCenterIndicator != null)
        return false;
    } else if (!prevRegionalCenterIndicator.equals(other.prevRegionalCenterIndicator))
      return false;
    if (primaryEthnicityType == null) {
      if (other.primaryEthnicityType != null)
        return false;
    } else if (!primaryEthnicityType.equals(other.primaryEthnicityType))
      return false;
    if (primaryLanguageType == null) {
      if (other.primaryLanguageType != null)
        return false;
    } else if (!primaryLanguageType.equals(other.primaryLanguageType))
      return false;
    if (religionType == null) {
      if (other.religionType != null)
        return false;
    } else if (!religionType.equals(other.religionType))
      return false;
    if (secondaryLanguageType == null) {
      if (other.secondaryLanguageType != null)
        return false;
    } else if (!secondaryLanguageType.equals(other.secondaryLanguageType))
      return false;
    if (sensitiveHlthInfoOnFileIndicator == null) {
      if (other.sensitiveHlthInfoOnFileIndicator != null)
        return false;
    } else if (!sensitiveHlthInfoOnFileIndicator.equals(other.sensitiveHlthInfoOnFileIndicator))
      return false;
    if (sensitivityIndicator == null) {
      if (other.sensitivityIndicator != null)
        return false;
    } else if (!sensitivityIndicator.equals(other.sensitivityIndicator))
      return false;
    if (soc158PlacementCode == null) {
      if (other.soc158PlacementCode != null)
        return false;
    } else if (!soc158PlacementCode.equals(other.soc158PlacementCode))
      return false;
    if (soc158SealedClientIndicator == null) {
      if (other.soc158SealedClientIndicator != null)
        return false;
    } else if (!soc158SealedClientIndicator.equals(other.soc158SealedClientIndicator))
      return false;
    if (socialSecurityNumChangedCode == null) {
      if (other.socialSecurityNumChangedCode != null)
        return false;
    } else if (!socialSecurityNumChangedCode.equals(other.socialSecurityNumChangedCode))
      return false;
    if (socialSecurityNumber == null) {
      if (other.socialSecurityNumber != null)
        return false;
    } else if (!socialSecurityNumber.equals(other.socialSecurityNumber))
      return false;
    if (suffixTitleDescription == null) {
      if (other.suffixTitleDescription != null)
        return false;
    } else if (!suffixTitleDescription.equals(other.suffixTitleDescription))
      return false;
    if (tribalAncestryClientIndicatorVar == null) {
      if (other.tribalAncestryClientIndicatorVar != null)
        return false;
    } else if (!tribalAncestryClientIndicatorVar.equals(other.tribalAncestryClientIndicatorVar))
      return false;
    if (tribalMembrshpVerifctnIndicatorVar == null) {
      if (other.tribalMembrshpVerifctnIndicatorVar != null)
        return false;
    } else if (!tribalMembrshpVerifctnIndicatorVar.equals(other.tribalMembrshpVerifctnIndicatorVar))
      return false;
    if (unemployedParentCode == null) {
      if (other.unemployedParentCode != null)
        return false;
    } else if (!unemployedParentCode.equals(other.unemployedParentCode))
      return false;
    if (zippyCreatedIndicator == null) {
      if (other.zippyCreatedIndicator != null)
        return false;
    } else if (!zippyCreatedIndicator.equals(other.zippyCreatedIndicator))
      return false;
    return true;
  }

}
