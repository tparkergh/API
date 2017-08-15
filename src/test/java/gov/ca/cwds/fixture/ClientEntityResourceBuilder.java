package gov.ca.cwds.fixture;

import gov.ca.cwds.data.persistence.cms.Client;
import gov.ca.cwds.data.persistence.cms.ClientAddress;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class ClientEntityResourceBuilder {

  protected String adjudicatedDelinquentIndicator = "";
  protected String adoptionStatusCode  = "";
  protected String alienRegistrationNumber  = "";
  protected String birthCity  = "";
  protected Short birthCountryCodeType  = 0;
  protected Date birthDate  = new Date();
  protected String birthFacilityName  = "";
  protected Short birthStateCodeType  = 0;
  protected String birthplaceVerifiedIndicator  = "";
  protected String childClientIndicatorVar  = "";
  protected String clientIndexNumber  = "";
  protected String commentDescription  = "";
  protected String commonFirstName  = "";
  protected String commonLastName  = "";
  protected String commonMiddleName  = "";
  protected Date confidentialityActionDate  = new Date();
  protected String confidentialityInEffectIndicator  = "";
  protected Date creationDate  = new Date();
  protected String currCaChildrenServIndicator  = "";
  protected String currentlyOtherDescription  = "";
  protected String currentlyRegionalCenterIndicator  = "";
  protected Date deathDate  = new Date();
  protected String deathDateVerifiedIndicator  = "";
  protected String deathPlace  = "";
  protected String deathReasonText  = "";
  protected String driverLicenseNumber  = "";
  protected Short driverLicenseStateCodeType  = 0;
  protected String emailAddress  = "";
  protected String estimatedDobCode  = "";
  protected String ethUnableToDetReasonCode  = "";
  protected Date fatherParentalRightTermDate  = new Date();
  protected String genderCode  = "";
  protected String healthSummaryText  = "";
  protected String hispUnableToDetReasonCode  = "";
  protected String hispanicOriginCode  = "";
  protected String id  = "";
  protected Short immigrationCountryCodeType  = 0;
  protected Short immigrationStatusType  = 0;
  protected String incapacitatedParentCode  = "";
  protected String individualHealthCarePlanIndicator  = "";
  protected String limitationOnScpHealthIndicator  = "";
  protected String literateCode  = "";
  protected String maritalCohabitatnHstryIndicatorVar  = "";
  protected Short maritalStatusType  = 0;
  protected String militaryStatusCode  = "";
  protected Date motherParentalRightTermDate  = new Date();
  protected String namePrefixDescription  = "";
  protected Short nameType  = 0;
  protected String outstandingWarrantIndicator  = "";
  protected String prevCaChildrenServIndicator  = "";
  protected String prevOtherDescription  = "";
  protected String prevRegionalCenterIndicator  = "";
  protected Short primaryEthnicityType  = 0;
  protected Short primaryLanguageType  = 0;
  protected Short religionType  = 0;
  protected Short secondaryLanguageType  = 0;
  protected String sensitiveHlthInfoOnFileIndicator  = "";
  protected String sensitivityIndicator  = "";
  protected String soc158PlacementCode  = "";
  protected String soc158SealedClientIndicator  = "";
  protected String socialSecurityNumChangedCode  = "";
  protected String socialSecurityNumber  = "";
  protected String suffixTitleDescription  = "";
  protected String tribalAncestryClientIndicatorVar  = "";
  protected String tribalMembrshpVerifctnIndicatorVar  = "";
  protected String unemployedParentCode  = "";
  protected String zippyCreatedIndicator  = "";
  private Set<ClientAddress> clientAddress = new HashSet<>();

  public ClientEntityResourceBuilder setAdjudicatedDelinquentIndicator(String adjudicatedDelinquentIndicator) {
    this.adjudicatedDelinquentIndicator = adjudicatedDelinquentIndicator;
    return this;
  }

  public ClientEntityResourceBuilder setAdoptionStatusCode(String adoptionStatusCode) {
    this.adoptionStatusCode = adoptionStatusCode;
    return this;
  }

  public ClientEntityResourceBuilder setAlienRegistrationNumber(String alienRegistrationNumber) {
    this.alienRegistrationNumber = alienRegistrationNumber;
    return this;
  }

  public ClientEntityResourceBuilder setBirthCity(String birthCity) {
    this.birthCity = birthCity;
    return this;
  }

  public ClientEntityResourceBuilder setBirthCountryCodeType(Short birthCountryCodeType) {
    this.birthCountryCodeType = birthCountryCodeType;
    return this;
  }

  public ClientEntityResourceBuilder setBirthDate(Date birthDate) {
    this.birthDate = birthDate;
    return this;
  }

  public ClientEntityResourceBuilder setBirthFacilityName(String birthFacilityName) {
    this.birthFacilityName = birthFacilityName;
    return this;
  }

  public ClientEntityResourceBuilder setBirthStateCodeType(Short birthStateCodeType) {
    this.birthStateCodeType = birthStateCodeType;
    return this;
  }

  public ClientEntityResourceBuilder setBirthplaceVerifiedIndicator(String birthplaceVerifiedIndicator) {
    this.birthplaceVerifiedIndicator = birthplaceVerifiedIndicator;
    return this;
  }

  public ClientEntityResourceBuilder setChildClientIndicatorVar(String childClientIndicatorVar) {
    this.childClientIndicatorVar = childClientIndicatorVar;
    return this;
  }

  public ClientEntityResourceBuilder setClientIndexNumber(String clientIndexNumber) {
    this.clientIndexNumber = clientIndexNumber;
    return this;
  }

  public ClientEntityResourceBuilder setCommentDescription(String commentDescription) {
    this.commentDescription = commentDescription;
    return this;
  }

  public ClientEntityResourceBuilder setCommonFirstName(String commonFirstName) {
    this.commonFirstName = commonFirstName;
    return this;
  }

  public ClientEntityResourceBuilder setCommonLastName(String commonLastName) {
    this.commonLastName = commonLastName;
    return this;
  }

  public ClientEntityResourceBuilder setCommonMiddleName(String commonMiddleName) {
    this.commonMiddleName = commonMiddleName;
    return this;
  }

  public ClientEntityResourceBuilder setConfidentialityActionDate(Date confidentialityActionDate) {
    this.confidentialityActionDate = confidentialityActionDate;
    return this;
  }

  public ClientEntityResourceBuilder setConfidentialityInEffectIndicator(String confidentialityInEffectIndicator) {
    this.confidentialityInEffectIndicator = confidentialityInEffectIndicator;
    return this;
  }

  public ClientEntityResourceBuilder setCreationDate(Date creationDate) {
    this.creationDate = creationDate;
    return this;
  }

  public ClientEntityResourceBuilder setCurrCaChildrenServIndicator(String currCaChildrenServIndicator) {
    this.currCaChildrenServIndicator = currCaChildrenServIndicator;
    return this;
  }

  public ClientEntityResourceBuilder setCurrentlyOtherDescription(String currentlyOtherDescription) {
    this.currentlyOtherDescription = currentlyOtherDescription;
    return this;
  }

  public ClientEntityResourceBuilder setCurrentlyRegionalCenterIndicator(String currentlyRegionalCenterIndicator) {
    this.currentlyRegionalCenterIndicator = currentlyRegionalCenterIndicator;
    return this;
  }

  public ClientEntityResourceBuilder setDeathDate(Date deathDate) {
    this.deathDate = deathDate;
    return this;
  }

  public ClientEntityResourceBuilder setDeathDateVerifiedIndicator(String deathDateVerifiedIndicator) {
    this.deathDateVerifiedIndicator = deathDateVerifiedIndicator;
    return this;
  }

  public ClientEntityResourceBuilder setDeathPlace(String deathPlace) {
    this.deathPlace = deathPlace;
    return this;
  }

  public ClientEntityResourceBuilder setDeathReasonText(String deathReasonText) {
    this.deathReasonText = deathReasonText;
    return this;
  }

  public ClientEntityResourceBuilder setDriverLicenseNumber(String driverLicenseNumber) {
    this.driverLicenseNumber = driverLicenseNumber;
    return this;
  }

  public ClientEntityResourceBuilder setDriverLicenseStateCodeType(Short driverLicenseStateCodeType) {
    this.driverLicenseStateCodeType = driverLicenseStateCodeType;
    return this;
  }

  public ClientEntityResourceBuilder setEmailAddress(String emailAddress) {
    this.emailAddress = emailAddress;
    return this;
  }

  public ClientEntityResourceBuilder setEstimatedDobCode(String estimatedDobCode) {
    this.estimatedDobCode = estimatedDobCode;
    return this;
  }

  public ClientEntityResourceBuilder setEthUnableToDetReasonCode(String ethUnableToDetReasonCode) {
    this.ethUnableToDetReasonCode = ethUnableToDetReasonCode;
    return this;
  }

  public ClientEntityResourceBuilder setFatherParentalRightTermDate(Date fatherParentalRightTermDate) {
    this.fatherParentalRightTermDate = fatherParentalRightTermDate;
    return this;
  }

  public ClientEntityResourceBuilder setGenderCode(String genderCode) {
    this.genderCode = genderCode;
    return this;
  }

  public ClientEntityResourceBuilder setHealthSummaryText(String healthSummaryText) {
    this.healthSummaryText = healthSummaryText;
    return this;
  }

  public ClientEntityResourceBuilder setHispUnableToDetReasonCode(String hispUnableToDetReasonCode) {
    this.hispUnableToDetReasonCode = hispUnableToDetReasonCode;
    return this;
  }

  public ClientEntityResourceBuilder setHispanicOriginCode(String hispanicOriginCode) {
    this.hispanicOriginCode = hispanicOriginCode;
    return this;
  }

  public ClientEntityResourceBuilder setId(String id) {
    this.id = id;
    return this;
  }

  public ClientEntityResourceBuilder setImmigrationCountryCodeType(Short immigrationCountryCodeType) {
    this.immigrationCountryCodeType = immigrationCountryCodeType;
    return this;
  }

  public ClientEntityResourceBuilder setImmigrationStatusType(Short immigrationStatusType) {
    this.immigrationStatusType = immigrationStatusType;
    return this;
  }

  public ClientEntityResourceBuilder setIncapacitatedParentCode(String incapacitatedParentCode) {
    this.incapacitatedParentCode = incapacitatedParentCode;
    return this;
  }

  public ClientEntityResourceBuilder setIndividualHealthCarePlanIndicator(
      String individualHealthCarePlanIndicator) {
    this.individualHealthCarePlanIndicator = individualHealthCarePlanIndicator;
    return this;
  }

  public ClientEntityResourceBuilder setLimitationOnScpHealthIndicator(String limitationOnScpHealthIndicator) {
    this.limitationOnScpHealthIndicator = limitationOnScpHealthIndicator;
    return this;
  }

  public ClientEntityResourceBuilder setLiterateCode(String literateCode) {
    this.literateCode = literateCode;
    return this;
  }

  public ClientEntityResourceBuilder setMaritalCohabitatnHstryIndicatorVar(
      String maritalCohabitatnHstryIndicatorVar) {
    this.maritalCohabitatnHstryIndicatorVar = maritalCohabitatnHstryIndicatorVar;
    return this;
  }

  public ClientEntityResourceBuilder setMaritalStatusType(Short maritalStatusType) {
    this.maritalStatusType = maritalStatusType;
    return this;
  }

  public ClientEntityResourceBuilder setMilitaryStatusCode(String militaryStatusCode) {
    this.militaryStatusCode = militaryStatusCode;
    return this;
  }

  public ClientEntityResourceBuilder setMotherParentalRightTermDate(Date motherParentalRightTermDate) {
    this.motherParentalRightTermDate = motherParentalRightTermDate;
    return this;
  }

  public ClientEntityResourceBuilder setNamePrefixDescription(String namePrefixDescription) {
    this.namePrefixDescription = namePrefixDescription;
    return this;
  }

  public ClientEntityResourceBuilder setNameType(Short nameType) {
    this.nameType = nameType;
    return this;
  }

  public ClientEntityResourceBuilder setOutstandingWarrantIndicator(String outstandingWarrantIndicator) {
    this.outstandingWarrantIndicator = outstandingWarrantIndicator;
    return this;
  }

  public ClientEntityResourceBuilder setPrevCaChildrenServIndicator(String prevCaChildrenServIndicator) {
    this.prevCaChildrenServIndicator = prevCaChildrenServIndicator;
    return this;
  }

  public ClientEntityResourceBuilder setPrevOtherDescription(String prevOtherDescription) {
    this.prevOtherDescription = prevOtherDescription;
    return this;
  }

  public ClientEntityResourceBuilder setPrevRegionalCenterIndicator(String prevRegionalCenterIndicator) {
    this.prevRegionalCenterIndicator = prevRegionalCenterIndicator;
    return this;
  }

  public ClientEntityResourceBuilder setPrimaryEthnicityType(Short primaryEthnicityType) {
    this.primaryEthnicityType = primaryEthnicityType;
    return this;
  }

  public ClientEntityResourceBuilder setPrimaryLanguageType(Short primaryLanguageType) {
    this.primaryLanguageType = primaryLanguageType;
    return this;
  }

  public ClientEntityResourceBuilder setReligionType(Short religionType) {
    this.religionType = religionType;
    return this;
  }

  public ClientEntityResourceBuilder setSecondaryLanguageType(Short secondaryLanguageType) {
    this.secondaryLanguageType = secondaryLanguageType;
    return this;
  }

  public ClientEntityResourceBuilder setSensitiveHlthInfoOnFileIndicator(String sensitiveHlthInfoOnFileIndicator) {
    this.sensitiveHlthInfoOnFileIndicator = sensitiveHlthInfoOnFileIndicator;
    return this;
  }

  public ClientEntityResourceBuilder setSensitivityIndicator(String sensitivityIndicator) {
    this.sensitivityIndicator = sensitivityIndicator;
    return this;
  }

  public ClientEntityResourceBuilder setSoc158PlacementCode(String soc158PlacementCode) {
    this.soc158PlacementCode = soc158PlacementCode;
    return this;
  }

  public ClientEntityResourceBuilder setSoc158SealedClientIndicator(String soc158SealedClientIndicator) {
    this.soc158SealedClientIndicator = soc158SealedClientIndicator;
    return this;
  }

  public ClientEntityResourceBuilder setSocialSecurityNumChangedCode(String socialSecurityNumChangedCode) {
    this.socialSecurityNumChangedCode = socialSecurityNumChangedCode;
    return this;
  }

  public ClientEntityResourceBuilder setSocialSecurityNumber(String socialSecurityNumber) {
    this.socialSecurityNumber = socialSecurityNumber;
    return this;
  }

  public ClientEntityResourceBuilder setSuffixTitleDescription(String suffixTitleDescription) {
    this.suffixTitleDescription = suffixTitleDescription;
    return this;
  }

  public ClientEntityResourceBuilder setTribalAncestryClientIndicatorVar(String tribalAncestryClientIndicatorVar) {
    this.tribalAncestryClientIndicatorVar = tribalAncestryClientIndicatorVar;
    return this;
  }

  public ClientEntityResourceBuilder setTribalMembrshpVerifctnIndicatorVar(
      String tribalMembrshpVerifctnIndicatorVar) {
    this.tribalMembrshpVerifctnIndicatorVar = tribalMembrshpVerifctnIndicatorVar;
    return this;
  }

  public ClientEntityResourceBuilder setUnemployedParentCode(String unemployedParentCode) {
    this.unemployedParentCode = unemployedParentCode;
    return this;
  }

  public ClientEntityResourceBuilder setZippyCreatedIndicator(String zippyCreatedIndicator) {
    this.zippyCreatedIndicator = zippyCreatedIndicator;
    return this;
  }

  public ClientEntityResourceBuilder setClientAddress(
      Set<ClientAddress> clientAddress) {
    this.clientAddress = clientAddress;
    return this;
  }

  public Client build(){
    return new Client(adjudicatedDelinquentIndicator,
    adoptionStatusCode ,
    alienRegistrationNumber ,
    birthCity ,
    birthCountryCodeType ,
    birthDate ,
    birthFacilityName ,
    birthStateCodeType ,
    birthplaceVerifiedIndicator ,
    childClientIndicatorVar ,
    clientIndexNumber ,
    commentDescription ,
    commonFirstName ,
    commonMiddleName ,
    commonLastName ,
    confidentialityActionDate ,
    confidentialityInEffectIndicator ,
    creationDate ,
    currCaChildrenServIndicator ,
    currentlyOtherDescription ,
    currentlyRegionalCenterIndicator ,
    deathDate ,
    deathDateVerifiedIndicator ,
    deathPlace ,
    deathReasonText ,
    driverLicenseNumber ,
    driverLicenseStateCodeType ,
    emailAddress ,
    estimatedDobCode ,
    ethUnableToDetReasonCode ,
    fatherParentalRightTermDate ,
    genderCode ,
    healthSummaryText ,
    hispUnableToDetReasonCode ,
    hispanicOriginCode ,
    id ,
    immigrationCountryCodeType ,
    immigrationStatusType ,
    incapacitatedParentCode ,
    individualHealthCarePlanIndicator ,
    limitationOnScpHealthIndicator ,
    literateCode ,
    maritalCohabitatnHstryIndicatorVar ,
    maritalStatusType ,
    militaryStatusCode ,
    motherParentalRightTermDate ,
    namePrefixDescription ,
    nameType ,
    outstandingWarrantIndicator ,
    prevCaChildrenServIndicator ,
    prevOtherDescription ,
    prevRegionalCenterIndicator ,
    primaryEthnicityType ,
    primaryLanguageType ,
    religionType ,
    secondaryLanguageType ,
    sensitiveHlthInfoOnFileIndicator ,
    sensitivityIndicator ,
    soc158PlacementCode ,
    soc158SealedClientIndicator ,
    socialSecurityNumChangedCode ,
    socialSecurityNumber ,
    suffixTitleDescription ,
    tribalAncestryClientIndicatorVar ,
    tribalMembrshpVerifctnIndicatorVar ,
    unemployedParentCode ,
    zippyCreatedIndicator ,
    clientAddress );
  }
}
