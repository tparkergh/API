package gov.ca.cwds.fixture;

import java.math.BigDecimal;

import gov.ca.cwds.data.persistence.cms.LawEnforcement;

/**
 * @author CWDS API Team
 *
 */
@SuppressWarnings("javadoc")
public class LawEnforcementEntityBuilder {

  String archiveAssociationInd = "N";
  String cityName = "Sacramento";
  Integer contactPhoneExtensionNumber = 0;
  String contactPersonName = "Joe";
  BigDecimal contactPhoneNumber = new BigDecimal(0);
  String contactPositionTitleDescription = "Contact Descrption";
  String emailAddress = "123@abc.com";
  BigDecimal faxNumber = new BigDecimal(0);
  Short governmentEntityType = 1101;
  String id = "FDIp2i900E";
  String lawEnforcementName = "Sacramento City Police";
  Short referenceNumber = 1828;
  String stationName = "Station#3";
  String streetName = "G";
  String streetNumber = "914";
  Integer zipNumber = 95833;
  Short zipSuffixNumber = 0;

  public LawEnforcement build() {
    return new LawEnforcement(archiveAssociationInd, cityName, contactPhoneExtensionNumber,
        contactPersonName, contactPhoneNumber, contactPositionTitleDescription, emailAddress,
        faxNumber, governmentEntityType, id, lawEnforcementName, referenceNumber, stationName,
        streetName, streetNumber, zipNumber, zipSuffixNumber);
  }

  public String getArchiveAssociationInd() {
    return archiveAssociationInd;
  }

  public LawEnforcementEntityBuilder setArchiveAssociationInd(String archiveAssociationInd) {
    this.archiveAssociationInd = archiveAssociationInd;
    return this;
  }

  public String getCityName() {
    return cityName;
  }

  public LawEnforcementEntityBuilder setCityName(String cityName) {
    this.cityName = cityName;
    return this;
  }

  public Integer getContactPhoneExtensionNumber() {
    return contactPhoneExtensionNumber;
  }

  public LawEnforcementEntityBuilder setContactPhoneExtensionNumber(
      Integer contactPhoneExtensionNumber) {
    this.contactPhoneExtensionNumber = contactPhoneExtensionNumber;
    return this;
  }

  public String getContactPersonName() {
    return contactPersonName;
  }

  public LawEnforcementEntityBuilder setContactPersonName(String contactPersonName) {
    this.contactPersonName = contactPersonName;
    return this;
  }

  public BigDecimal getContactPhoneNumber() {
    return contactPhoneNumber;
  }

  public LawEnforcementEntityBuilder setContactPhoneNumber(BigDecimal contactPhoneNumber) {
    this.contactPhoneNumber = contactPhoneNumber;
    return this;
  }

  public String getContactPositionTitleDescription() {
    return contactPositionTitleDescription;
  }

  public LawEnforcementEntityBuilder setContactPositionTitleDescription(
      String contactPositionTitleDescription) {
    this.contactPositionTitleDescription = contactPositionTitleDescription;
    return this;
  }

  public String getEmailAddress() {
    return emailAddress;
  }

  public LawEnforcementEntityBuilder setEmailAddress(String emailAddress) {
    this.emailAddress = emailAddress;
    return this;
  }

  public BigDecimal getFaxNumber() {
    return faxNumber;
  }

  public LawEnforcementEntityBuilder setFaxNumber(BigDecimal faxNumber) {
    this.faxNumber = faxNumber;
    return this;
  }

  public Short getGovernmentEntityType() {
    return governmentEntityType;
  }

  public LawEnforcementEntityBuilder setGovernmentEntityType(Short governmentEntityType) {
    this.governmentEntityType = governmentEntityType;
    return this;
  }

  public String getId() {
    return id;
  }

  public LawEnforcementEntityBuilder setId(String id) {
    this.id = id;
    return this;
  }

  public String getLawEnforcementName() {
    return lawEnforcementName;
  }

  public LawEnforcementEntityBuilder setLawEnforcementName(String lawEnforcementName) {
    this.lawEnforcementName = lawEnforcementName;
    return this;
  }

  public Short getReferenceNumber() {
    return referenceNumber;
  }

  public LawEnforcementEntityBuilder setReferenceNumber(Short referenceNumber) {
    this.referenceNumber = referenceNumber;
    return this;
  }

  public String getStationName() {
    return stationName;
  }

  public LawEnforcementEntityBuilder setStationName(String stationName) {
    this.stationName = stationName;
    return this;
  }

  public String getStreetName() {
    return streetName;
  }

  public LawEnforcementEntityBuilder setStreetName(String streetName) {
    this.streetName = streetName;
    return this;
  }

  public String getStreetNumber() {
    return streetNumber;
  }

  public LawEnforcementEntityBuilder setStreetNumber(String streetNumber) {
    this.streetNumber = streetNumber;
    return this;
  }

  public Integer getZipNumber() {
    return zipNumber;
  }

  public LawEnforcementEntityBuilder setZipNumber(Integer zipNumber) {
    this.zipNumber = zipNumber;
    return this;
  }

  public Short getZipSuffixNumber() {
    return zipSuffixNumber;
  }

  public LawEnforcementEntityBuilder setZipSuffixNumber(Short zipSuffixNumber) {
    this.zipSuffixNumber = zipSuffixNumber;
    return this;
  }

}
