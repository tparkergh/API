package gov.ca.cwds.fixture;

import gov.ca.cwds.rest.api.domain.cms.Address;
import gov.ca.cwds.rest.api.domain.cms.Client;
import java.util.Set;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

public class ClientResourceBuilder {
  String existingClientId = "";
  private DateTime lastUpdatedTime = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ").parseDateTime("2004-03-31T09:45:58.000-0800");
  Boolean adjudicatedDelinquentIndicator = false;
  String adoptionStatusCode = Client.DEFAULT_ADOPTION_STATUS_CODE;
  String alienRegistrationNumber = "";
  String birthCity = "";
  Short birthCountryCodeType = Client.DEFAULT_CODE;
  String birthDate = "1966-12-01";
  String birthFacilityName = "";
  Short birthStateCodeType = Client.DEFAULT_CODE;
  Boolean birthplaceVerifiedIndicator = false;
  Boolean childClientIndicatorVar = Client.DEFAULT_CHILD_CLIENT_INDICATOR;
  String clientIndexNumber = "";
  String commentDescription = "";
  String commonFirstName = "Billy";
  String commonMiddleName = "J";
  String commonLastName = "Smith";
  String confidentialityActionDate = "";
  Boolean confidentialityInEffectIndicator = false;
  String creationDate = "2017-03-15";
  Boolean currCaChildrenServIndicator = false;
  String currentlyOtherDescription = "";
  Boolean currentlyRegionalCenterIndicator = false;
  String deathDate = "";
  Boolean deathDateVerifiedIndicator = false;
  String deathPlace = "";
  String deathReasonText = "";
  String driverLicenseNumber = "";
  Short driverLicenseStateCodeType = Client.DEFAULT_CODE;
  String emailAddress = "";
  String estimatedDobCode = Client.DEFAULT_ESTIMATED_DOB_CODE;
  String ethUnableToDetReasonCode = Client.DEFAULT_UNABLE_TO_DETAIN_CODE;
  String fatherParentalRightTermDate = "";
  String genderCode = "M";
  String healthSummaryText = "";
  String hispUnableToDetReasonCode = "";
  String hispanicOriginCode = Client.DEFAULT_HISPANIC_ORIGIN_CODE;
  Short immigrationCountryCodeType = Client.DEFAULT_CODE;
  Short immigrationStatusType = Client.DEFAULT_CODE;
  String incapacitatedParentCode = Client.DEFAULT_INCAPCITATED_PARENT_CODE;
  Boolean individualHealthCarePlanIndicator = false;
  Boolean limitationOnScpHealthIndicator = false;
  String literateCode = Client.DEFAULT_LITERATE_CODE;
  Boolean maritalCohabitatnHstryIndicatorVar = false;
  Short maritalStatusType = Client.DEFAULT_CODE;
  String militaryStatusCode = Client.DEFAULT_MILITARY_STATUS_CODE;
  String motherParentalRightTermDate = "";
  String namePrefixDescription = "";
  Short nameType = Client.DEFAULT_NAME_TYPE;
  Boolean outstandingWarrantIndicator = false;
  Boolean prevCaChildrenServIndicator = false;
  String prevOtherDescription = "";
  Boolean prevRegionalCenterIndicator = false;
  Short primaryEthnicityType = Client.DEFAULT_CODE;
  Short primaryLanguageType = Client.DEFAULT_CODE;
  Short religionType = Client.DEFAULT_CODE;
  Short secondaryLanguageType = Client.DEFAULT_SECONDARY_LANGUAGE_TYPE;
  Boolean sensitiveHlthInfoOnFileIndicator = false;
  String sensitivityIndicator = Client.DEFAULT_SENSITIVITY_INDICATOR;
  String soc158PlacementCode = Client.DEFAULT_SOC158_PLACEMENT_CODE;
  Boolean soc158SealedClientIndicator = false;
  String socialSecurityNumChangedCode = Client.DEFAULT_SOCIAL_SECURITY_NUM_CHANGE_CODE;
  String socialSecurityNumber = "123442334";
  String suffixTitleDescription = "";
  Boolean tribalAncestryClientIndicatorVar = false;
  Boolean tribalMembrshpVerifctnIndicatorVar = false;
  String unemployedParentCode = Client.DEFAULT_UNEMPLOYED_PARENT_CODE;
  Boolean zippyCreatedIndicator = false;
  Set<Address> address = null;

  public String getExistingClientId() {
    return existingClientId;
  }

  public ClientResourceBuilder setExistingClientId(String existingClientId) {
    this.existingClientId = existingClientId;
    return this;
  }

  public DateTime getLastUpdatedTime(){
    return lastUpdatedTime;
  }

  public ClientResourceBuilder setLastUpdateTime(DateTime lastUpdatedTime){
    this.lastUpdatedTime = lastUpdatedTime;
    return this;
  }

  public boolean getAdjudicatedDelinquentIndicator() {
    return adjudicatedDelinquentIndicator;
  }

  public ClientResourceBuilder setAdjudicatedDelinquentIndicator(Boolean adjudicatedDelinquentIndicator) {
    this.adjudicatedDelinquentIndicator = adjudicatedDelinquentIndicator;
    return this;
  }

  public String getAdoptionStatusCode() {
    return adoptionStatusCode;
  }

  public ClientResourceBuilder setAdoptionStatusCode(String adoptionStatusCode) {
    this.adoptionStatusCode = adoptionStatusCode;
    return this;
  }

  public String getAlienRegistrationNumber() {
    return alienRegistrationNumber;
  }

  public ClientResourceBuilder setAlienRegistrationNumber(String alienRegistrationNumber) {
    this.alienRegistrationNumber = alienRegistrationNumber;
    return this;
  }

  public String getBirthCity() {
    return birthCity;
  }

  public ClientResourceBuilder setBirthCity(String birthCity) {
    this.birthCity = birthCity;
    return this;
  }

  public Short getBirthCountryCodeType() {
    return birthCountryCodeType;
  }

  public ClientResourceBuilder setBirthCountryCodeType(Short birthCountryCodeType) {
    this.birthCountryCodeType = birthCountryCodeType;
    return this;
  }

  public String getBirthDate() {
    return birthDate;
  }

  public ClientResourceBuilder setBirthDate(String birthDate) {
    this.birthDate = birthDate;
    return this;
  }

  public String getBirthFacilityName() {
    return birthFacilityName;
  }

  public ClientResourceBuilder setBirthFacilityName(String birthFacilityName) {
    this.birthFacilityName = birthFacilityName;
    return this;
  }

  public Short getBirthStateCodeType() {
    return birthStateCodeType;
  }

  public ClientResourceBuilder setBirthStateCodeType(Short birthStateCodeType) {
    this.birthStateCodeType = birthStateCodeType;
    return this;
  }

  public Boolean getBirthplaceVerifiedIndicator() {
    return birthplaceVerifiedIndicator;
  }

  public ClientResourceBuilder setBirthplaceVerifiedIndicator(Boolean birthplaceVerifiedIndicator) {
    this.birthplaceVerifiedIndicator = birthplaceVerifiedIndicator;
    return this;
  }

  public Boolean getChildClientIndicatorVar() {
    return childClientIndicatorVar;
  }

  public ClientResourceBuilder setChildClientIndicatorVar(Boolean childClientIndicatorVar) {
    this.childClientIndicatorVar = childClientIndicatorVar;
    return this;
  }

  public String getClientIndexNumber() {
    return clientIndexNumber;
  }

  public ClientResourceBuilder setClientIndexNumber(String clientIndexNumber) {
    this.clientIndexNumber = clientIndexNumber;
    return this;
  }

  public String getCommentDescription() {
    return commentDescription;
  }

  public ClientResourceBuilder setCommentDescription(String commentDescription) {
    this.commentDescription = commentDescription;
    return this;
  }

  public String getCommonFirstName() {
    return commonFirstName;
  }

  public ClientResourceBuilder setCommonFirstName(String commonFirstName) {
    this.commonFirstName = commonFirstName;
    return this;
  }

  public String getCommonMiddleName() {
    return commonMiddleName;
  }

  public ClientResourceBuilder setCommonMiddleName(String commonMiddleName) {
    this.commonMiddleName = commonMiddleName;
    return this;
  }

  public String getCommonLastName() {
    return commonLastName;
  }

  public ClientResourceBuilder setCommonLastName(String commonLastName) {
    this.commonLastName = commonLastName;
    return this;
  }

  public String getConfidentialityActionDate() {
    return confidentialityActionDate;
  }

  public ClientResourceBuilder setConfidentialityActionDate(String confidentialityActionDate) {
    this.confidentialityActionDate = confidentialityActionDate;
    return this;
  }

  public Boolean getConfidentialityInEffectIndicator() {
    return confidentialityInEffectIndicator;
  }

  public ClientResourceBuilder setConfidentialityInEffectIndicator(
      Boolean confidentialityInEffectIndicator) {
    this.confidentialityInEffectIndicator = confidentialityInEffectIndicator;
    return this;
  }

  public String getCreationDate() {
    return creationDate;
  }

  public ClientResourceBuilder setCreationDate(String creationDate) {
    this.creationDate = creationDate;
    return this;
  }

  public Boolean getCurrCaChildrenServIndicator() {
    return currCaChildrenServIndicator;
  }

  public ClientResourceBuilder setCurrCaChildrenServIndicator(Boolean currCaChildrenServIndicator) {
    this.currCaChildrenServIndicator = currCaChildrenServIndicator;
    return this;
  }

  public String getCurrentlyOtherDescription() {
    return currentlyOtherDescription;
  }

  public ClientResourceBuilder setCurrentlyOtherDescription(String currentlyOtherDescription) {
    this.currentlyOtherDescription = currentlyOtherDescription;
    return this;
  }

  public Boolean getCurrentlyRegionalCenterIndicator() {
    return currentlyRegionalCenterIndicator;
  }

  public ClientResourceBuilder setCurrentlyRegionalCenterIndicator(
      Boolean currentlyRegionalCenterIndicator) {
    this.currentlyRegionalCenterIndicator = currentlyRegionalCenterIndicator;
    return this;
  }

  public String getDeathDate() {
    return deathDate;
  }

  public ClientResourceBuilder setDeathDate(String deathDate) {
    this.deathDate = deathDate;
    return this;
  }

  public Boolean getDeathDateVerifiedIndicator() {
    return deathDateVerifiedIndicator;
  }

  public ClientResourceBuilder setDeathDateVerifiedIndicator(Boolean deathDateVerifiedIndicator) {
    this.deathDateVerifiedIndicator = deathDateVerifiedIndicator;
    return this;
  }

  public String getDeathPlace() {
    return deathPlace;
  }

  public ClientResourceBuilder setDeathPlace(String deathPlace) {
    this.deathPlace = deathPlace;
    return this;
  }

  public String getDeathReasonText() {
    return deathReasonText;
  }

  public ClientResourceBuilder setDeathReasonText(String deathReasonText) {
    this.deathReasonText = deathReasonText;
    return this;
  }

  public String getDriverLicenseNumber() {
    return driverLicenseNumber;
  }

  public ClientResourceBuilder setDriverLicenseNumber(String driverLicenseNumber) {
    this.driverLicenseNumber = driverLicenseNumber;
    return this;
  }

  public Short getDriverLicenseStateCodeType() {
    return driverLicenseStateCodeType;
  }

  public ClientResourceBuilder setDriverLicenseStateCodeType(Short driverLicenseStateCodeType) {
    this.driverLicenseStateCodeType = driverLicenseStateCodeType;
    return this;
  }

  public String getEmailAddress() {
    return emailAddress;
  }

  public ClientResourceBuilder setEmailAddress(String emailAddress) {
    this.emailAddress = emailAddress;
    return this;
  }

  public String getEstimatedDobCode() {
    return estimatedDobCode;
  }

  public ClientResourceBuilder setEstimatedDobCode(String estimatedDobCode) {
    this.estimatedDobCode = estimatedDobCode;
    return this;
  }

  public String getEthUnableToDetReasonCode() {
    return ethUnableToDetReasonCode;
  }

  public ClientResourceBuilder setEthUnableToDetReasonCode(String ethUnableToDetReasonCode) {
    this.ethUnableToDetReasonCode = ethUnableToDetReasonCode;
    return this;
  }

  public String getFatherParentalRightTermDate() {
    return fatherParentalRightTermDate;
  }

  public ClientResourceBuilder setFatherParentalRightTermDate(String fatherParentalRightTermDate) {
    this.fatherParentalRightTermDate = fatherParentalRightTermDate;
    return this;
  }

  public String getGenderCode() {
    return genderCode;
  }

  public ClientResourceBuilder setGenderCode(String genderCode) {
    this.genderCode = genderCode;
    return this;
  }

  public String getHealthSummaryText() {
    return healthSummaryText;
  }

  public ClientResourceBuilder setHealthSummaryText(String healthSummaryText) {
    this.healthSummaryText = healthSummaryText;
    return this;
  }

  public String getHispUnableToDetReasonCode() {
    return hispUnableToDetReasonCode;
  }

  public ClientResourceBuilder setHispUnableToDetReasonCode(String hispUnableToDetReasonCode) {
    this.hispUnableToDetReasonCode = hispUnableToDetReasonCode;
    return this;
  }

  public String getHispanicOriginCode() {
    return hispanicOriginCode;
  }

  public ClientResourceBuilder setHispanicOriginCode(String hispanicOriginCode) {
    this.hispanicOriginCode = hispanicOriginCode;
    return this;
  }

  public Short getImmigrationCountryCodeType() {
    return immigrationCountryCodeType;
  }

  public ClientResourceBuilder setImmigrationCountryCodeType(Short immigrationCountryCodeType) {
    this.immigrationCountryCodeType = immigrationCountryCodeType;
    return this;
  }

  public Short getImmigrationStatusType() {
    return immigrationStatusType;
  }

  public ClientResourceBuilder setImmigrationStatusType(Short immigrationStatusType) {
    this.immigrationStatusType = immigrationStatusType;
    return this;
  }

  public String getIncapacitatedParentCode() {
    return incapacitatedParentCode;
  }

  public ClientResourceBuilder setIncapacitatedParentCode(String incapacitatedParentCode) {
    this.incapacitatedParentCode = incapacitatedParentCode;
    return this;
  }

  public Boolean getIndividualHealthCarePlanIndicator() {
    return individualHealthCarePlanIndicator;
  }

  public ClientResourceBuilder setIndividualHealthCarePlanIndicator(
      Boolean individualHealthCarePlanIndicator) {
    this.individualHealthCarePlanIndicator = individualHealthCarePlanIndicator;
    return this;
  }

  public Boolean getLimitationOnScpHealthIndicator() {
    return limitationOnScpHealthIndicator;
  }

  public ClientResourceBuilder setLimitationOnScpHealthIndicator(Boolean limitationOnScpHealthIndicator) {
    this.limitationOnScpHealthIndicator = limitationOnScpHealthIndicator;
    return this;
  }

  public String getLiterateCode() {
    return literateCode;
  }

  public ClientResourceBuilder setLiterateCode(String literateCode) {
    this.literateCode = literateCode;
    return this;
  }

  public Boolean getMaritalCohabitatnHstryIndicatorVar() {
    return maritalCohabitatnHstryIndicatorVar;
  }

  public ClientResourceBuilder setMaritalCohabitatnHstryIndicatorVar(
      Boolean maritalCohabitatnHstryIndicatorVar) {
    this.maritalCohabitatnHstryIndicatorVar = maritalCohabitatnHstryIndicatorVar;
    return this;
  }

  public Short getMaritalStatusType() {
    return maritalStatusType;
  }

  public ClientResourceBuilder setMaritalStatusType(Short maritalStatusType) {
    this.maritalStatusType = maritalStatusType;
    return this;
  }

  public String getMilitaryStatusCode() {
    return militaryStatusCode;
  }

  public ClientResourceBuilder setMilitaryStatusCode(String militaryStatusCode) {
    this.militaryStatusCode = militaryStatusCode;
    return this;
  }

  public String getMotherParentalRightTermDate() {
    return motherParentalRightTermDate;
  }

  public ClientResourceBuilder setMotherParentalRightTermDate(String motherParentalRightTermDate) {
    this.motherParentalRightTermDate = motherParentalRightTermDate;
    return this;
  }

  public String getNamePrefixDescription() {
    return namePrefixDescription;
  }

  public ClientResourceBuilder setNamePrefixDescription(String namePrefixDescription) {
    this.namePrefixDescription = namePrefixDescription;
    return this;
  }

  public Short getNameType() {
    return nameType;
  }

  public ClientResourceBuilder setNameType(Short nameType) {
    this.nameType = nameType;
    return this;
  }

  public Boolean getOutstandingWarrantIndicator() {
    return outstandingWarrantIndicator;
  }

  public ClientResourceBuilder setOutstandingWarrantIndicator(Boolean outstandingWarrantIndicator) {
    this.outstandingWarrantIndicator = outstandingWarrantIndicator;
    return this;
  }

  public Boolean getPrevCaChildrenServIndicator() {
    return prevCaChildrenServIndicator;
  }

  public ClientResourceBuilder setPrevCaChildrenServIndicator(Boolean prevCaChildrenServIndicator) {
    this.prevCaChildrenServIndicator = prevCaChildrenServIndicator;
    return this;
  }

  public String getPrevOtherDescription() {
    return prevOtherDescription;
  }

  public ClientResourceBuilder setPrevOtherDescription(String prevOtherDescription) {
    this.prevOtherDescription = prevOtherDescription;
    return this;
  }

  public Boolean getPrevRegionalCenterIndicator() {
    return prevRegionalCenterIndicator;
  }

  public ClientResourceBuilder setPrevRegionalCenterIndicator(Boolean prevRegionalCenterIndicator) {
    this.prevRegionalCenterIndicator = prevRegionalCenterIndicator;
    return this;
  }

  public Short getPrimaryEthnicityType() {
    return primaryEthnicityType;
  }

  public ClientResourceBuilder setPrimaryEthnicityType(Short primaryEthnicityType) {
    this.primaryEthnicityType = primaryEthnicityType;
    return this;
  }

  public Short getPrimaryLanguageType() {
    return primaryLanguageType;
  }

  public ClientResourceBuilder setPrimaryLanguageType(Short primaryLanguageType) {
    this.primaryLanguageType = primaryLanguageType;
    return this;
  }

  public Short getReligionType() {
    return religionType;
  }

  public ClientResourceBuilder setReligionType(Short religionType) {
    this.religionType = religionType;
    return this;
  }

  public Short getSecondaryLanguageType() {
    return secondaryLanguageType;
  }

  public ClientResourceBuilder setSecondaryLanguageType(Short secondaryLanguageType) {
    this.secondaryLanguageType = secondaryLanguageType;
    return this;
  }

  public Boolean getSensitiveHlthInfoOnFileIndicator() {
    return sensitiveHlthInfoOnFileIndicator;
  }

  public ClientResourceBuilder setSensitiveHlthInfoOnFileIndicator(
      Boolean sensitiveHlthInfoOnFileIndicator) {
    this.sensitiveHlthInfoOnFileIndicator = sensitiveHlthInfoOnFileIndicator;
    return this;
  }

  public String getSensitivityIndicator() {
    return sensitivityIndicator;
  }

  public ClientResourceBuilder setSensitivityIndicator(String sensitivityIndicator) {
    this.sensitivityIndicator = sensitivityIndicator;
    return this;
  }

  public String getSoc158PlacementCode() {
    return soc158PlacementCode;
  }

  public ClientResourceBuilder setSoc158PlacementCode(String soc158PlacementCode) {
    this.soc158PlacementCode = soc158PlacementCode;
    return this;
  }

  public Boolean getSoc158SealedClientIndicator() {
    return soc158SealedClientIndicator;
  }

  public ClientResourceBuilder setSoc158SealedClientIndicator(Boolean soc158SealedClientIndicator) {
    this.soc158SealedClientIndicator = soc158SealedClientIndicator;
    return this;
  }

  public String getSocialSecurityNumChangedCode() {
    return socialSecurityNumChangedCode;
  }

  public ClientResourceBuilder setSocialSecurityNumChangedCode(String socialSecurityNumChangedCode) {
    this.socialSecurityNumChangedCode = socialSecurityNumChangedCode;
    return this;
  }

  public String getSocialSecurityNumber() {
    return socialSecurityNumber;
  }

  public ClientResourceBuilder setSocialSecurityNumber(String socialSecurityNumber) {
    this.socialSecurityNumber = socialSecurityNumber;
    return this;
  }

  public String getSuffixTitleDescription() {
    return suffixTitleDescription;
  }

  public ClientResourceBuilder setSuffixTitleDescription(String suffixTitleDescription) {
    this.suffixTitleDescription = suffixTitleDescription;
    return this;
  }

  public Boolean getTribalAncestryClientIndicatorVar() {
    return tribalAncestryClientIndicatorVar;
  }

  public ClientResourceBuilder setTribalAncestryClientIndicatorVar(
      Boolean tribalAncestryClientIndicatorVar) {
    this.tribalAncestryClientIndicatorVar = tribalAncestryClientIndicatorVar;
    return this;
  }

  public Boolean getTribalMembrshpVerifctnIndicatorVar() {
    return tribalMembrshpVerifctnIndicatorVar;
  }

  public ClientResourceBuilder setTribalMembrshpVerifctnIndicatorVar(
      Boolean tribalMembrshpVerifctnIndicatorVar) {
    this.tribalMembrshpVerifctnIndicatorVar = tribalMembrshpVerifctnIndicatorVar;
    return this;
  }

  public String getUnemployedParentCode() {
    return unemployedParentCode;
  }

  public ClientResourceBuilder setUnemployedParentCode(String unemployedParentCode) {
    this.unemployedParentCode = unemployedParentCode;
    return this;
  }

  public Boolean getZippyCreatedIndicator() {
    return zippyCreatedIndicator;
  }

  public ClientResourceBuilder setZippyCreatedIndicator(Boolean zippyCreatedIndicator) {
    this.zippyCreatedIndicator = zippyCreatedIndicator;
    return this;
  }

  public Set<Address> getAddress() {
    return address;
  }

  public ClientResourceBuilder setAddress(Set<Address> address) {
    this.address = address;
    return this;
  }

  public Client build(){
    return new Client(existingClientId, lastUpdatedTime, adjudicatedDelinquentIndicator, adoptionStatusCode, alienRegistrationNumber, birthCity, birthCountryCodeType,
        birthDate, birthFacilityName, birthStateCodeType, birthplaceVerifiedIndicator, childClientIndicatorVar, clientIndexNumber,
        commentDescription, commonFirstName, commonMiddleName, commonLastName, confidentialityActionDate,
        confidentialityInEffectIndicator, creationDate, currCaChildrenServIndicator, currentlyOtherDescription, currentlyRegionalCenterIndicator, deathDate, deathDateVerifiedIndicator, deathPlace, deathReasonText, driverLicenseNumber, driverLicenseStateCodeType, emailAddress,
        estimatedDobCode, ethUnableToDetReasonCode, fatherParentalRightTermDate, genderCode, healthSummaryText, hispUnableToDetReasonCode,
        hispanicOriginCode, immigrationCountryCodeType, immigrationStatusType, incapacitatedParentCode,
        individualHealthCarePlanIndicator, limitationOnScpHealthIndicator, literateCode, maritalCohabitatnHstryIndicatorVar, maritalStatusType, militaryStatusCode, motherParentalRightTermDate,
        namePrefixDescription, nameType, outstandingWarrantIndicator, prevCaChildrenServIndicator, prevOtherDescription, prevRegionalCenterIndicator, primaryEthnicityType, primaryLanguageType, religionType,
        secondaryLanguageType, sensitiveHlthInfoOnFileIndicator, sensitivityIndicator,
        soc158PlacementCode, soc158SealedClientIndicator, socialSecurityNumChangedCode,
        socialSecurityNumber, suffixTitleDescription, tribalAncestryClientIndicatorVar, tribalMembrshpVerifctnIndicatorVar,
        unemployedParentCode, zippyCreatedIndicator, address);
  }
}
