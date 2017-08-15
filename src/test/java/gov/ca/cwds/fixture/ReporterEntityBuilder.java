package gov.ca.cwds.fixture;

import gov.ca.cwds.rest.api.domain.cms.Reporter;
import java.math.BigDecimal;

public class ReporterEntityBuilder {
  String badgeNumber = "";
   String cityName = "";
   Short colltrClientRptrReltnshpType = 0;
   Short communicationMethodType = 0;
   Boolean confidentialWaiverIndicator = false;
   String drmsMandatedRprtrFeedback = "";
   String employerName = "";
   String feedbackDate = "";
   Boolean feedbackRequiredIndicator = false;
   String firstName = "Frank";
   String lastName = "Johnson";
   Boolean mandatedReporterIndicator = false;
   Integer messagePhoneExtensionNumber = 123;
   BigDecimal messagePhoneNumber = new BigDecimal(1235555);
   String middleInitialName = "";
   String namePrefixDescription = "";
   BigDecimal primaryPhoneNumber = new BigDecimal(2223333);
   Integer primaryPhoneExtensionNumber = 123;
   Short stateCodeType = 0;
   String streetName = "";
   String streetNumber = "";
   String suffixTitleDescription = "";
   String zipcode = "";
   String referralId = "";
   String lawEnforcementId = "";
   Short zipSuffixNumber = 0;
   String countySpecificCode = "99";

  public ReporterEntityBuilder setBadgeNumber(String badgeNumber) {
    this.badgeNumber = badgeNumber;
    return this;
  }

  public ReporterEntityBuilder setCityName(String cityName) {
    this.cityName = cityName;
    return this;
  }

  public ReporterEntityBuilder setColltrClientRptrReltnshpType(Short colltrClientRptrReltnshpType) {
    this.colltrClientRptrReltnshpType = colltrClientRptrReltnshpType;
    return this;
  }

  public ReporterEntityBuilder setCommunicationMethodType(Short communicationMethodType) {
    this.communicationMethodType = communicationMethodType;
    return this;
  }

  public ReporterEntityBuilder setConfidentialWaiverIndicator(Boolean confidentialWaiverIndicator) {
    this.confidentialWaiverIndicator = confidentialWaiverIndicator;
    return this;
  }

  public ReporterEntityBuilder setDrmsMandatedRprtrFeedback(String drmsMandatedRprtrFeedback) {
    this.drmsMandatedRprtrFeedback = drmsMandatedRprtrFeedback;
    return this;
  }

  public ReporterEntityBuilder setEmployerName(String employerName) {
    this.employerName = employerName;
    return this;
  }

  public ReporterEntityBuilder setFeedbackDate(String feedbackDate) {
    this.feedbackDate = feedbackDate;
    return this;
  }

  public ReporterEntityBuilder setFeedbackRequiredIndicator(Boolean feedbackRequiredIndicator) {
    this.feedbackRequiredIndicator = feedbackRequiredIndicator;
    return this;
  }

  public ReporterEntityBuilder setFirstName(String firstName) {
    this.firstName = firstName;
    return this;
  }

  public ReporterEntityBuilder setLastName(String lastName) {
    this.lastName = lastName;
    return this;
  }

  public ReporterEntityBuilder setMandatedReporterIndicator(Boolean mandatedReporterIndicator) {
    this.mandatedReporterIndicator = mandatedReporterIndicator;
    return this;
  }

  public ReporterEntityBuilder setMessagePhoneExtensionNumber(Integer messagePhoneExtensionNumber) {
    this.messagePhoneExtensionNumber = messagePhoneExtensionNumber;
    return this;
  }

  public ReporterEntityBuilder setMessagePhoneNumber(BigDecimal messagePhoneNumber) {
    this.messagePhoneNumber = messagePhoneNumber;
    return this;
  }

  public ReporterEntityBuilder setMiddleInitialName(String middleInitialName) {
    this.middleInitialName = middleInitialName;
    return this;
  }

  public ReporterEntityBuilder setNamePrefixDescription(String namePrefixDescription) {
    this.namePrefixDescription = namePrefixDescription;
    return this;
  }

  public ReporterEntityBuilder setPrimaryPhoneNumber(BigDecimal primaryPhoneNumber) {
    this.primaryPhoneNumber = primaryPhoneNumber;
    return this;
  }

  public ReporterEntityBuilder setPrimaryPhoneExtensionNumber(Integer primaryPhoneExtensionNumber) {
    this.primaryPhoneExtensionNumber = primaryPhoneExtensionNumber;
    return this;
  }

  public ReporterEntityBuilder setStateCodeType(Short stateCodeType) {
    this.stateCodeType = stateCodeType;
    return this;
  }

  public ReporterEntityBuilder setStreetName(String streetName) {
    this.streetName = streetName;
    return this;
  }

  public ReporterEntityBuilder setStreetNumber(String streetNumber) {
    this.streetNumber = streetNumber;
    return this;
  }

  public ReporterEntityBuilder setSuffixTitleDescription(String suffixTitleDescription) {
    this.suffixTitleDescription = suffixTitleDescription;
    return this;
  }

  public ReporterEntityBuilder setZipcode(String zipcode) {
    this.zipcode = zipcode;
    return this;
  }

  public ReporterEntityBuilder setReferralId(String referralId) {
    this.referralId = referralId;
    return this;
  }

  public ReporterEntityBuilder setLawEnforcementId(String lawEnforcementId) {
    this.lawEnforcementId = lawEnforcementId;
    return this;
  }

  public ReporterEntityBuilder setZipSuffixNumber(Short zipSuffixNumber) {
    this.zipSuffixNumber = zipSuffixNumber;
    return this;
  }

  public ReporterEntityBuilder setCountySpecificCode(String countySpecificCode) {
    this.countySpecificCode = countySpecificCode;
    return this;
  }

  public Reporter build(){
    return new Reporter(badgeNumber, cityName, colltrClientRptrReltnshpType, communicationMethodType, confidentialWaiverIndicator,
        drmsMandatedRprtrFeedback, employerName, feedbackDate, feedbackRequiredIndicator, firstName,
        lastName, mandatedReporterIndicator, messagePhoneExtensionNumber, messagePhoneNumber, middleInitialName,
        namePrefixDescription, primaryPhoneNumber, primaryPhoneExtensionNumber, stateCodeType, streetName, streetNumber, suffixTitleDescription,
        zipcode, referralId, lawEnforcementId, zipSuffixNumber, countySpecificCode);

  }
}
