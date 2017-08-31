package gov.ca.cwds.fixture;

import gov.ca.cwds.data.persistence.cms.Client;
import gov.ca.cwds.data.persistence.cms.ClientAddress;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class ClientEntityBuilder {

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

  public ClientEntityBuilder setAdjudicatedDelinquentIndicator(String adjudicatedDelinquentIndicator) {
    this.adjudicatedDelinquentIndicator = adjudicatedDelinquentIndicator;
    return this;
  }

  public ClientEntityBuilder setAdoptionStatusCode(String adoptionStatusCode) {
    this.adoptionStatusCode = adoptionStatusCode;
    return this;
  }

  public ClientEntityBuilder setAlienRegistrationNumber(String alienRegistrationNumber) {
    this.alienRegistrationNumber = alienRegistrationNumber;
    return this;
  }

  public ClientEntityBuilder setBirthCity(String birthCity) {
    this.birthCity = birthCity;
    return this;
  }

  public ClientEntityBuilder setBirthCountryCodeType(Short birthCountryCodeType) {
    this.birthCountryCodeType = birthCountryCodeType;
    return this;
  }

  public ClientEntityBuilder setBirthDate(Date birthDate) {
    this.birthDate = birthDate;
    return this;
  }

  public ClientEntityBuilder setBirthFacilityName(String birthFacilityName) {
    this.birthFacilityName = birthFacilityName;
    return this;
  }

  public ClientEntityBuilder setBirthStateCodeType(Short birthStateCodeType) {
    this.birthStateCodeType = birthStateCodeType;
    return this;
  }

  public ClientEntityBuilder setBirthplaceVerifiedIndicator(String birthplaceVerifiedIndicator) {
    this.birthplaceVerifiedIndicator = birthplaceVerifiedIndicator;
    return this;
  }

  public ClientEntityBuilder setChildClientIndicatorVar(String childClientIndicatorVar) {
    this.childClientIndicatorVar = childClientIndicatorVar;
    return this;
  }

  public ClientEntityBuilder setClientIndexNumber(String clientIndexNumber) {
    this.clientIndexNumber = clientIndexNumber;
    return this;
  }

  public ClientEntityBuilder setCommentDescription(String commentDescription) {
    this.commentDescription = commentDescription;
    return this;
  }

  public ClientEntityBuilder setCommonFirstName(String commonFirstName) {
    this.commonFirstName = commonFirstName;
    return this;
  }

  public ClientEntityBuilder setCommonLastName(String commonLastName) {
    this.commonLastName = commonLastName;
    return this;
  }

  public ClientEntityBuilder setCommonMiddleName(String commonMiddleName) {
    this.commonMiddleName = commonMiddleName;
    return this;
  }

  public ClientEntityBuilder setConfidentialityActionDate(Date confidentialityActionDate) {
    this.confidentialityActionDate = confidentialityActionDate;
    return this;
  }

  public ClientEntityBuilder setConfidentialityInEffectIndicator(String confidentialityInEffectIndicator) {
    this.confidentialityInEffectIndicator = confidentialityInEffectIndicator;
    return this;
  }

  public ClientEntityBuilder setCreationDate(Date creationDate) {
    this.creationDate = creationDate;
    return this;
  }

  public ClientEntityBuilder setCurrCaChildrenServIndicator(String currCaChildrenServIndicator) {
    this.currCaChildrenServIndicator = currCaChildrenServIndicator;
    return this;
  }

  public ClientEntityBuilder setCurrentlyOtherDescription(String currentlyOtherDescription) {
    this.currentlyOtherDescription = currentlyOtherDescription;
    return this;
  }

  public ClientEntityBuilder setCurrentlyRegionalCenterIndicator(String currentlyRegionalCenterIndicator) {
    this.currentlyRegionalCenterIndicator = currentlyRegionalCenterIndicator;
    return this;
  }

  public ClientEntityBuilder setDeathDate(Date deathDate) {
    this.deathDate = deathDate;
    return this;
  }

  public ClientEntityBuilder setDeathDateVerifiedIndicator(String deathDateVerifiedIndicator) {
    this.deathDateVerifiedIndicator = deathDateVerifiedIndicator;
    return this;
  }

  public ClientEntityBuilder setDeathPlace(String deathPlace) {
    this.deathPlace = deathPlace;
    return this;
  }

  public ClientEntityBuilder setDeathReasonText(String deathReasonText) {
    this.deathReasonText = deathReasonText;
    return this;
  }

  public ClientEntityBuilder setDriverLicenseNumber(String driverLicenseNumber) {
    this.driverLicenseNumber = driverLicenseNumber;
    return this;
  }

  public ClientEntityBuilder setDriverLicenseStateCodeType(Short driverLicenseStateCodeType) {
    this.driverLicenseStateCodeType = driverLicenseStateCodeType;
    return this;
  }

  public ClientEntityBuilder setEmailAddress(String emailAddress) {
    this.emailAddress = emailAddress;
    return this;
  }

  public ClientEntityBuilder setEstimatedDobCode(String estimatedDobCode) {
    this.estimatedDobCode = estimatedDobCode;
    return this;
  }

  public ClientEntityBuilder setEthUnableToDetReasonCode(String ethUnableToDetReasonCode) {
    this.ethUnableToDetReasonCode = ethUnableToDetReasonCode;
    return this;
  }

  public ClientEntityBuilder setFatherParentalRightTermDate(Date fatherParentalRightTermDate) {
    this.fatherParentalRightTermDate = fatherParentalRightTermDate;
    return this;
  }

  public ClientEntityBuilder setGenderCode(String genderCode) {
    this.genderCode = genderCode;
    return this;
  }

  public ClientEntityBuilder setHealthSummaryText(String healthSummaryText) {
    this.healthSummaryText = healthSummaryText;
    return this;
  }

  public ClientEntityBuilder setHispUnableToDetReasonCode(String hispUnableToDetReasonCode) {
    this.hispUnableToDetReasonCode = hispUnableToDetReasonCode;
    return this;
  }

  public ClientEntityBuilder setHispanicOriginCode(String hispanicOriginCode) {
    this.hispanicOriginCode = hispanicOriginCode;
    return this;
  }

  public ClientEntityBuilder setId(String id) {
    this.id = id;
    return this;
  }

  public ClientEntityBuilder setImmigrationCountryCodeType(Short immigrationCountryCodeType) {
    this.immigrationCountryCodeType = immigrationCountryCodeType;
    return this;
  }

  public ClientEntityBuilder setImmigrationStatusType(Short immigrationStatusType) {
    this.immigrationStatusType = immigrationStatusType;
    return this;
  }

  public ClientEntityBuilder setIncapacitatedParentCode(String incapacitatedParentCode) {
    this.incapacitatedParentCode = incapacitatedParentCode;
    return this;
  }

  public ClientEntityBuilder setIndividualHealthCarePlanIndicator(
      String individualHealthCarePlanIndicator) {
    this.individualHealthCarePlanIndicator = individualHealthCarePlanIndicator;
    return this;
  }

  public ClientEntityBuilder setLimitationOnScpHealthIndicator(String limitationOnScpHealthIndicator) {
    this.limitationOnScpHealthIndicator = limitationOnScpHealthIndicator;
    return this;
  }

  public ClientEntityBuilder setLiterateCode(String literateCode) {
    this.literateCode = literateCode;
    return this;
  }

  public ClientEntityBuilder setMaritalCohabitatnHstryIndicatorVar(
      String maritalCohabitatnHstryIndicatorVar) {
    this.maritalCohabitatnHstryIndicatorVar = maritalCohabitatnHstryIndicatorVar;
    return this;
  }

  public ClientEntityBuilder setMaritalStatusType(Short maritalStatusType) {
    this.maritalStatusType = maritalStatusType;
    return this;
  }

  public ClientEntityBuilder setMilitaryStatusCode(String militaryStatusCode) {
    this.militaryStatusCode = militaryStatusCode;
    return this;
  }

  public ClientEntityBuilder setMotherParentalRightTermDate(Date motherParentalRightTermDate) {
    this.motherParentalRightTermDate = motherParentalRightTermDate;
    return this;
  }

  public ClientEntityBuilder setNamePrefixDescription(String namePrefixDescription) {
    this.namePrefixDescription = namePrefixDescription;
    return this;
  }

  public ClientEntityBuilder setNameType(Short nameType) {
    this.nameType = nameType;
    return this;
  }

  public ClientEntityBuilder setOutstandingWarrantIndicator(String outstandingWarrantIndicator) {
    this.outstandingWarrantIndicator = outstandingWarrantIndicator;
    return this;
  }

  public ClientEntityBuilder setPrevCaChildrenServIndicator(String prevCaChildrenServIndicator) {
    this.prevCaChildrenServIndicator = prevCaChildrenServIndicator;
    return this;
  }

  public ClientEntityBuilder setPrevOtherDescription(String prevOtherDescription) {
    this.prevOtherDescription = prevOtherDescription;
    return this;
  }

  public ClientEntityBuilder setPrevRegionalCenterIndicator(String prevRegionalCenterIndicator) {
    this.prevRegionalCenterIndicator = prevRegionalCenterIndicator;
    return this;
  }

  public ClientEntityBuilder setPrimaryEthnicityType(Short primaryEthnicityType) {
    this.primaryEthnicityType = primaryEthnicityType;
    return this;
  }

  public ClientEntityBuilder setPrimaryLanguageType(Short primaryLanguageType) {
    this.primaryLanguageType = primaryLanguageType;
    return this;
  }

  public ClientEntityBuilder setReligionType(Short religionType) {
    this.religionType = religionType;
    return this;
  }

  public ClientEntityBuilder setSecondaryLanguageType(Short secondaryLanguageType) {
    this.secondaryLanguageType = secondaryLanguageType;
    return this;
  }

  public ClientEntityBuilder setSensitiveHlthInfoOnFileIndicator(String sensitiveHlthInfoOnFileIndicator) {
    this.sensitiveHlthInfoOnFileIndicator = sensitiveHlthInfoOnFileIndicator;
    return this;
  }

  public ClientEntityBuilder setSensitivityIndicator(String sensitivityIndicator) {
    this.sensitivityIndicator = sensitivityIndicator;
    return this;
  }

  public ClientEntityBuilder setSoc158PlacementCode(String soc158PlacementCode) {
    this.soc158PlacementCode = soc158PlacementCode;
    return this;
  }

  public ClientEntityBuilder setSoc158SealedClientIndicator(String soc158SealedClientIndicator) {
    this.soc158SealedClientIndicator = soc158SealedClientIndicator;
    return this;
  }

  public ClientEntityBuilder setSocialSecurityNumChangedCode(String socialSecurityNumChangedCode) {
    this.socialSecurityNumChangedCode = socialSecurityNumChangedCode;
    return this;
  }

  public ClientEntityBuilder setSocialSecurityNumber(String socialSecurityNumber) {
    this.socialSecurityNumber = socialSecurityNumber;
    return this;
  }

  public ClientEntityBuilder setSuffixTitleDescription(String suffixTitleDescription) {
    this.suffixTitleDescription = suffixTitleDescription;
    return this;
  }

  public ClientEntityBuilder setTribalAncestryClientIndicatorVar(String tribalAncestryClientIndicatorVar) {
    this.tribalAncestryClientIndicatorVar = tribalAncestryClientIndicatorVar;
    return this;
  }

  public ClientEntityBuilder setTribalMembrshpVerifctnIndicatorVar(
      String tribalMembrshpVerifctnIndicatorVar) {
    this.tribalMembrshpVerifctnIndicatorVar = tribalMembrshpVerifctnIndicatorVar;
    return this;
  }

  public ClientEntityBuilder setUnemployedParentCode(String unemployedParentCode) {
    this.unemployedParentCode = unemployedParentCode;
    return this;
  }

  public ClientEntityBuilder setZippyCreatedIndicator(String zippyCreatedIndicator) {
    this.zippyCreatedIndicator = zippyCreatedIndicator;
    return this;
  }

  public ClientEntityBuilder setClientAddress(
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
