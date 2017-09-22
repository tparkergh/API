package gov.ca.cwds.fixture;

import java.math.BigDecimal;

import gov.ca.cwds.rest.api.domain.cms.Reporter;
import org.joda.time.DateTime;

/**
 * @author CWDS API Team
 *
 */
@SuppressWarnings("javadoc")
public class CmsReporterResourceBuilder {

  DateTime lastUpdatedTime = new DateTime();
  String badgeNumber = "      ";
  String cityName = "City";
  Short colltrClientRptrReltnshpType = 591;
  Short communicationMethodType = 0;
  Boolean confidentialWaiverIndicator = false;
  String drmsMandatedRprtrFeedback = null;
  String employerName = "";
  String feedbackDate = null;
  Boolean feedbackRequiredIndicator = false;
  String firstName = "Fred";
  String lastName = "Reporter";
  Boolean mandatedReporterIndicator = false;
  Integer messagePhoneExtensionNumber = 0;
  BigDecimal messagePhoneNumber = new BigDecimal(0);
  String middleInitialName = " ";
  String namePrefixDescription = " ";
  BigDecimal primaryPhoneNumber = new BigDecimal(0);
  Integer primaryPhoneExtensionNumber = 0;
  Short stateCodeType = 1828;
  String streetName = "Street";
  String streetNumber = "12345";
  String suffixTitleDescription = "";
  String zipcode = "95845";
  String referralId = "AbiQCgu0Hj";
  String lawEnforcementId = null;
  Short zipSuffixNumber = 0;
  String countySpecificCode = "51";


  public Reporter build() {
    return new Reporter(lastUpdatedTime, badgeNumber, cityName, colltrClientRptrReltnshpType,
        communicationMethodType, confidentialWaiverIndicator, drmsMandatedRprtrFeedback,
        employerName, feedbackDate, feedbackRequiredIndicator, firstName, lastName,
        mandatedReporterIndicator, messagePhoneExtensionNumber, messagePhoneNumber,
        middleInitialName, namePrefixDescription, primaryPhoneNumber, primaryPhoneExtensionNumber,
        stateCodeType, streetName, streetNumber, suffixTitleDescription, zipcode, referralId,
        lawEnforcementId, zipSuffixNumber, countySpecificCode);
  }


  public DateTime getLastUpdatedTime() {return lastUpdatedTime;}

  public CmsReporterResourceBuilder setLastUpdatedTime(DateTime updatedTime){
    this.lastUpdatedTime = updatedTime;
    return this;
  }

  public String getBadgeNumber() {
    return badgeNumber;
  }


  public CmsReporterResourceBuilder setBadgeNumber(String badgeNumber) {
    this.badgeNumber = badgeNumber;
    return this;
  }


  public String getCityName() {
    return cityName;
  }


  public CmsReporterResourceBuilder setCityName(String cityName) {
    this.cityName = cityName;
    return this;
  }


  public Short getColltrClientRptrReltnshpType() {
    return colltrClientRptrReltnshpType;
  }


  public CmsReporterResourceBuilder setColltrClientRptrReltnshpType(
      Short colltrClientRptrReltnshpType) {
    this.colltrClientRptrReltnshpType = colltrClientRptrReltnshpType;
    return this;
  }


  public Short getCommunicationMethodType() {
    return communicationMethodType;
  }


  public CmsReporterResourceBuilder setCommunicationMethodType(Short communicationMethodType) {
    this.communicationMethodType = communicationMethodType;
    return this;
  }


  public Boolean getConfidentialWaiverIndicator() {
    return confidentialWaiverIndicator;
  }


  public CmsReporterResourceBuilder setConfidentialWaiverIndicator(
      Boolean confidentialWaiverIndicator) {
    this.confidentialWaiverIndicator = confidentialWaiverIndicator;
    return this;
  }


  public String getDrmsMandatedRprtrFeedback() {
    return drmsMandatedRprtrFeedback;
  }


  public CmsReporterResourceBuilder setDrmsMandatedRprtrFeedback(String drmsMandatedRprtrFeedback) {
    this.drmsMandatedRprtrFeedback = drmsMandatedRprtrFeedback;
    return this;
  }


  public String getEmployerName() {
    return employerName;
  }


  public CmsReporterResourceBuilder setEmployerName(String employerName) {
    this.employerName = employerName;
    return this;
  }


  public String getFeedbackDate() {
    return feedbackDate;
  }


  public CmsReporterResourceBuilder setFeedbackDate(String feedbackDate) {
    this.feedbackDate = feedbackDate;
    return this;
  }


  public Boolean getFeedbackRequiredIndicator() {
    return feedbackRequiredIndicator;
  }


  public CmsReporterResourceBuilder setFeedbackRequiredIndicator(
      Boolean feedbackRequiredIndicator) {
    this.feedbackRequiredIndicator = feedbackRequiredIndicator;
    return this;
  }


  public String getFirstName() {
    return firstName;
  }


  public CmsReporterResourceBuilder setFirstName(String firstName) {
    this.firstName = firstName;
    return this;
  }


  public String getLastName() {
    return lastName;
  }


  public CmsReporterResourceBuilder setLastName(String lastName) {
    this.lastName = lastName;
    return this;
  }


  public Boolean getMandatedReporterIndicator() {
    return mandatedReporterIndicator;
  }


  public CmsReporterResourceBuilder setMandatedReporterIndicator(
      Boolean mandatedReporterIndicator) {
    this.mandatedReporterIndicator = mandatedReporterIndicator;
    return this;
  }


  public Integer getMessagePhoneExtensionNumber() {
    return messagePhoneExtensionNumber;
  }


  public CmsReporterResourceBuilder setMessagePhoneExtensionNumber(
      Integer messagePhoneExtensionNumber) {
    this.messagePhoneExtensionNumber = messagePhoneExtensionNumber;
    return this;
  }


  public BigDecimal getMessagePhoneNumber() {
    return messagePhoneNumber;
  }


  public CmsReporterResourceBuilder setMessagePhoneNumber(BigDecimal messagePhoneNumber) {
    this.messagePhoneNumber = messagePhoneNumber;
    return this;
  }


  public String getMiddleInitialName() {
    return middleInitialName;
  }


  public CmsReporterResourceBuilder setMiddleInitialName(String middleInitialName) {
    this.middleInitialName = middleInitialName;
    return this;
  }


  public String getNamePrefixDescription() {
    return namePrefixDescription;
  }


  public CmsReporterResourceBuilder setNamePrefixDescription(String namePrefixDescription) {
    this.namePrefixDescription = namePrefixDescription;
    return this;
  }


  public BigDecimal getPrimaryPhoneNumber() {
    return primaryPhoneNumber;
  }


  public CmsReporterResourceBuilder setPrimaryPhoneNumber(BigDecimal primaryPhoneNumber) {
    this.primaryPhoneNumber = primaryPhoneNumber;
    return this;
  }


  public Integer getPrimaryPhoneExtensionNumber() {
    return primaryPhoneExtensionNumber;
  }


  public CmsReporterResourceBuilder setPrimaryPhoneExtensionNumber(
      Integer primaryPhoneExtensionNumber) {
    this.primaryPhoneExtensionNumber = primaryPhoneExtensionNumber;
    return this;
  }


  public Short getStateCodeType() {
    return stateCodeType;
  }


  public CmsReporterResourceBuilder setStateCodeType(Short stateCodeType) {
    this.stateCodeType = stateCodeType;
    return this;
  }


  public String getStreetName() {
    return streetName;
  }


  public CmsReporterResourceBuilder setStreetName(String streetName) {
    this.streetName = streetName;
    return this;
  }


  public String getStreetNumber() {
    return streetNumber;
  }


  public CmsReporterResourceBuilder setStreetNumber(String streetNumber) {
    this.streetNumber = streetNumber;
    return this;
  }


  public String getSuffixTitleDescription() {
    return suffixTitleDescription;
  }


  public CmsReporterResourceBuilder setSuffixTitleDescription(String suffixTitleDescription) {
    this.suffixTitleDescription = suffixTitleDescription;
    return this;
  }


  public String getZipcode() {
    return zipcode;
  }


  public CmsReporterResourceBuilder setZipcode(String zipcode) {
    this.zipcode = zipcode;
    return this;
  }


  public String getReferralId() {
    return referralId;
  }


  public CmsReporterResourceBuilder setReferralId(String referralId) {
    this.referralId = referralId;
    return this;
  }


  public String getLawEnforcementId() {
    return lawEnforcementId;
  }


  public CmsReporterResourceBuilder setLawEnforcementId(String lawEnforcementId) {
    this.lawEnforcementId = lawEnforcementId;
    return this;
  }


  public Short getZipSuffixNumber() {
    return zipSuffixNumber;
  }


  public CmsReporterResourceBuilder setZipSuffixNumber(Short zipSuffixNumber) {
    this.zipSuffixNumber = zipSuffixNumber;
    return this;
  }


  public String getCountySpecificCode() {
    return countySpecificCode;
  }


  public CmsReporterResourceBuilder setCountySpecificCode(String countySpecificCode) {
    this.countySpecificCode = countySpecificCode;
    return this;
  }

}
