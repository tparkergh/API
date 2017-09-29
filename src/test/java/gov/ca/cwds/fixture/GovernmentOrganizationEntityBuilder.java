package gov.ca.cwds.fixture;

import java.math.BigDecimal;

import gov.ca.cwds.data.persistence.cms.GovernmentOrganizationEntity;

/**
 * @author CWDS API Team
 *
 */
@SuppressWarnings("javadoc")
public class GovernmentOrganizationEntityBuilder {

  String id = "ABc1234567";
  String archiveAssociationInd = "N";
  String cityName = "Sacramento";
  String contactPersonName = "contact";
  BigDecimal contactPhoneNumber = new BigDecimal(2094688989);
  Integer contactPhoneExtNumber = 0;
  String contactPositionTitleDescription = "Asst Dist Atty";
  String emailAddress = "abc@123.com";
  BigDecimal faxNumber = new BigDecimal(0);
  String federalInd = "N";
  Short governmentEntityType = 1071;
  Short governmentOrganizationType = 5316;
  String governmentOrganizationName = "State of California";
  Short stateCodeType = 1828;
  String streetName = "W River Dr";
  String streetNumber = "1234";
  Integer zipNumber = 95833;
  Short zipSuffixNumber = 0;

  public GovernmentOrganizationEntity build() {
    return new GovernmentOrganizationEntity(id, archiveAssociationInd, cityName, contactPersonName,
        contactPhoneNumber, contactPhoneExtNumber, contactPositionTitleDescription, emailAddress,
        faxNumber, federalInd, governmentEntityType, governmentOrganizationType,
        governmentOrganizationName, stateCodeType, streetName, streetNumber, zipNumber,
        zipSuffixNumber);
  }

  public String getId() {
    return id;
  }

  public GovernmentOrganizationEntityBuilder setId(String id) {
    this.id = id;
    return this;
  }

  public String getArchiveAssociationInd() {
    return archiveAssociationInd;
  }

  public GovernmentOrganizationEntityBuilder setArchiveAssociationInd(
      String archiveAssociationInd) {
    this.archiveAssociationInd = archiveAssociationInd;
    return this;
  }

  public String getCityName() {
    return cityName;
  }

  public GovernmentOrganizationEntityBuilder setCityName(String cityName) {
    this.cityName = cityName;
    return this;
  }

  public String getContactPersonName() {
    return contactPersonName;
  }

  public GovernmentOrganizationEntityBuilder setContactPersonName(String contactPersonName) {
    this.contactPersonName = contactPersonName;
    return this;
  }

  public BigDecimal getContactPhoneNumber() {
    return contactPhoneNumber;
  }

  public GovernmentOrganizationEntityBuilder setContactPhoneNumber(BigDecimal contactPhoneNumber) {
    this.contactPhoneNumber = contactPhoneNumber;
    return this;
  }

  public Integer getContactPhoneExtNumber() {
    return contactPhoneExtNumber;
  }

  public GovernmentOrganizationEntityBuilder setContactPhoneExtNumber(
      Integer contactPhoneExtNumber) {
    this.contactPhoneExtNumber = contactPhoneExtNumber;
    return this;
  }

  public String getContactPositionTitleDescription() {
    return contactPositionTitleDescription;
  }

  public GovernmentOrganizationEntityBuilder setContactPositionTitleDescription(
      String contactPositionTitleDescription) {
    this.contactPositionTitleDescription = contactPositionTitleDescription;
    return this;
  }

  public String getEmailAddress() {
    return emailAddress;
  }

  public GovernmentOrganizationEntityBuilder setEmailAddress(String emailAddress) {
    this.emailAddress = emailAddress;
    return this;
  }

  public BigDecimal getFaxNumber() {
    return faxNumber;
  }

  public GovernmentOrganizationEntityBuilder setFaxNumber(BigDecimal faxNumber) {
    this.faxNumber = faxNumber;
    return this;
  }

  public String getFederalInd() {
    return federalInd;
  }

  public GovernmentOrganizationEntityBuilder setFederalInd(String federalInd) {
    this.federalInd = federalInd;
    return this;
  }

  public Short getGovernmentEntityType() {
    return governmentEntityType;
  }

  public GovernmentOrganizationEntityBuilder setGovernmentEntityType(Short governmentEntityType) {
    this.governmentEntityType = governmentEntityType;
    return this;
  }

  public Short getGovernmentOrganizationType() {
    return governmentOrganizationType;
  }

  public GovernmentOrganizationEntityBuilder setGovernmentOrganizationType(
      Short governmentOrganizationType) {
    this.governmentOrganizationType = governmentOrganizationType;
    return this;
  }

  public String getGovernmentOrganizationName() {
    return governmentOrganizationName;
  }

  public GovernmentOrganizationEntityBuilder setGovernmentOrganizationName(
      String governmentOrganizationName) {
    this.governmentOrganizationName = governmentOrganizationName;
    return this;
  }

  public Short getStateCodeType() {
    return stateCodeType;
  }

  public GovernmentOrganizationEntityBuilder setStateCodeType(Short stateCodeType) {
    this.stateCodeType = stateCodeType;
    return this;
  }

  public String getStreetName() {
    return streetName;
  }

  public GovernmentOrganizationEntityBuilder setStreetName(String streetName) {
    this.streetName = streetName;
    return this;
  }

  public String getStreetNumber() {
    return streetNumber;
  }

  public GovernmentOrganizationEntityBuilder setStreetNumber(String streetNumber) {
    this.streetNumber = streetNumber;
    return this;
  }

  public Integer getZipNumber() {
    return zipNumber;
  }

  public GovernmentOrganizationEntityBuilder setZipNumber(Integer zipNumber) {
    this.zipNumber = zipNumber;
    return this;
  }

  public Short getZipSuffixNumber() {
    return zipSuffixNumber;
  }

  public GovernmentOrganizationEntityBuilder setZipSuffixNumber(Short zipSuffixNumber) {
    this.zipSuffixNumber = zipSuffixNumber;
    return this;
  }

}
