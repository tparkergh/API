package gov.ca.cwds.fixture;

import gov.ca.cwds.rest.api.domain.cms.Reporter;
import java.math.BigDecimal;
import org.joda.time.DateTime;

public class ReporterResourceBuilder {
  DateTime lastUpdatedTime = new DateTime();
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

  public DateTime getLastUpdatedTime() {return lastUpdatedTime;}

  public ReporterResourceBuilder setLastUpdatedTime(DateTime updatedTime){
    this.lastUpdatedTime = updatedTime;
    return this;
  }


  public ReporterResourceBuilder setBadgeNumber(String badgeNumber) {
    this.badgeNumber = badgeNumber;
    return this;
  }

  public ReporterResourceBuilder setCityName(String cityName) {
    this.cityName = cityName;
    return this;
  }

  public ReporterResourceBuilder setColltrClientRptrReltnshpType(Short colltrClientRptrReltnshpType) {
    this.colltrClientRptrReltnshpType = colltrClientRptrReltnshpType;
    return this;
  }

  public ReporterResourceBuilder setCommunicationMethodType(Short communicationMethodType) {
    this.communicationMethodType = communicationMethodType;
    return this;
  }

  public ReporterResourceBuilder setConfidentialWaiverIndicator(Boolean confidentialWaiverIndicator) {
    this.confidentialWaiverIndicator = confidentialWaiverIndicator;
    return this;
  }

  public ReporterResourceBuilder setDrmsMandatedRprtrFeedback(String drmsMandatedRprtrFeedback) {
    this.drmsMandatedRprtrFeedback = drmsMandatedRprtrFeedback;
    return this;
  }

  public ReporterResourceBuilder setEmployerName(String employerName) {
    this.employerName = employerName;
    return this;
  }

  public ReporterResourceBuilder setFeedbackDate(String feedbackDate) {
    this.feedbackDate = feedbackDate;
    return this;
  }

  public ReporterResourceBuilder setFeedbackRequiredIndicator(Boolean feedbackRequiredIndicator) {
    this.feedbackRequiredIndicator = feedbackRequiredIndicator;
    return this;
  }

  public ReporterResourceBuilder setFirstName(String firstName) {
    this.firstName = firstName;
    return this;
  }

  public ReporterResourceBuilder setLastName(String lastName) {
    this.lastName = lastName;
    return this;
  }

  public ReporterResourceBuilder setMandatedReporterIndicator(Boolean mandatedReporterIndicator) {
    this.mandatedReporterIndicator = mandatedReporterIndicator;
    return this;
  }

  public ReporterResourceBuilder setMessagePhoneExtensionNumber(Integer messagePhoneExtensionNumber) {
    this.messagePhoneExtensionNumber = messagePhoneExtensionNumber;
    return this;
  }

  public ReporterResourceBuilder setMessagePhoneNumber(BigDecimal messagePhoneNumber) {
    this.messagePhoneNumber = messagePhoneNumber;
    return this;
  }

  public ReporterResourceBuilder setMiddleInitialName(String middleInitialName) {
    this.middleInitialName = middleInitialName;
    return this;
  }

  public ReporterResourceBuilder setNamePrefixDescription(String namePrefixDescription) {
    this.namePrefixDescription = namePrefixDescription;
    return this;
  }

  public ReporterResourceBuilder setPrimaryPhoneNumber(BigDecimal primaryPhoneNumber) {
    this.primaryPhoneNumber = primaryPhoneNumber;
    return this;
  }

  public ReporterResourceBuilder setPrimaryPhoneExtensionNumber(Integer primaryPhoneExtensionNumber) {
    this.primaryPhoneExtensionNumber = primaryPhoneExtensionNumber;
    return this;
  }

  public ReporterResourceBuilder setStateCodeType(Short stateCodeType) {
    this.stateCodeType = stateCodeType;
    return this;
  }

  public ReporterResourceBuilder setStreetName(String streetName) {
    this.streetName = streetName;
    return this;
  }

  public ReporterResourceBuilder setStreetNumber(String streetNumber) {
    this.streetNumber = streetNumber;
    return this;
  }

  public ReporterResourceBuilder setSuffixTitleDescription(String suffixTitleDescription) {
    this.suffixTitleDescription = suffixTitleDescription;
    return this;
  }

  public ReporterResourceBuilder setZipcode(String zipcode) {
    this.zipcode = zipcode;
    return this;
  }

  public ReporterResourceBuilder setReferralId(String referralId) {
    this.referralId = referralId;
    return this;
  }

  public ReporterResourceBuilder setLawEnforcementId(String lawEnforcementId) {
    this.lawEnforcementId = lawEnforcementId;
    return this;
  }

  public ReporterResourceBuilder setZipSuffixNumber(Short zipSuffixNumber) {
    this.zipSuffixNumber = zipSuffixNumber;
    return this;
  }

  public ReporterResourceBuilder setCountySpecificCode(String countySpecificCode) {
    this.countySpecificCode = countySpecificCode;
    return this;
  }

  public Reporter build(){
    return new Reporter(lastUpdatedTime,badgeNumber, cityName, colltrClientRptrReltnshpType,
        communicationMethodType, confidentialWaiverIndicator,
        drmsMandatedRprtrFeedback, employerName, feedbackDate, feedbackRequiredIndicator, firstName,
        lastName, mandatedReporterIndicator, messagePhoneExtensionNumber, messagePhoneNumber, middleInitialName,
        namePrefixDescription, primaryPhoneNumber, primaryPhoneExtensionNumber, stateCodeType, streetName, streetNumber, suffixTitleDescription,
        zipcode, referralId, lawEnforcementId, zipSuffixNumber, countySpecificCode);

  }
}
