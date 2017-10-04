package gov.ca.cwds.fixture;

import java.math.BigDecimal;

import gov.ca.cwds.data.persistence.cms.Address;

/**
 * @author CWDS API Team
 *
 */
@SuppressWarnings("javadoc")
public class AddressEntityBuilder {
  String id = "ABc1r90Pg6";
  String city = "Sacramento";
  java.math.BigDecimal emergencyNumber = BigDecimal.ZERO;
  Integer emergencyExtension = 0;
  String frgAdrtB = "N";
  Short governmentEntityCd = 0;
  BigDecimal messageNumber = new BigDecimal(0);
  Integer messageExtension = 0;
  String headerAddress = "";
  BigDecimal primaryNumber = new BigDecimal(0);
  Integer primaryExtension = 0;
  Short state = 1828;
  String streetName = "First Street";
  String streetNumber = "1";
  String zip = "98765";
  String addressDescription = "test CWS address";
  Short zip4 = 0;
  String postDirCd = "";
  String preDirCd = "";
  Short streetSuffixCd = 1;
  Short unitDesignationCd = 1;
  String unitNumber = "";

  public Address build() {
    return new Address(id, city, emergencyNumber, emergencyExtension, frgAdrtB, governmentEntityCd,
        messageNumber, messageExtension, headerAddress, primaryNumber, primaryExtension, state,
        streetName, streetNumber, zip, addressDescription, zip4, postDirCd, preDirCd,
        streetSuffixCd, unitDesignationCd, unitNumber);
  }

  public String getId() {
    return id;
  }

  public AddressEntityBuilder setId(String id) {
    this.id = id;
    return this;
  }

  public String getCity() {
    return city;
  }

  public AddressEntityBuilder setCity(String city) {
    this.city = city;
    return this;
  }

  public BigDecimal getEmergencyNumber() {
    return emergencyNumber;
  }

  public AddressEntityBuilder setEmergencyNumber(BigDecimal emergencyNumber) {
    this.emergencyNumber = emergencyNumber;
    return this;
  }

  public Integer getEmergencyExtension() {
    return emergencyExtension;
  }

  public AddressEntityBuilder setEmergencyExtension(Integer emergencyExtension) {
    this.emergencyExtension = emergencyExtension;
    return this;
  }

  public String getFrgAdrtB() {
    return frgAdrtB;
  }

  public AddressEntityBuilder setFrgAdrtB(String frgAdrtB) {
    this.frgAdrtB = frgAdrtB;
    return this;
  }

  public Short getGovernmentEntityCd() {
    return governmentEntityCd;
  }

  public AddressEntityBuilder setGovernmentEntityCd(Short governmentEntityCd) {
    this.governmentEntityCd = governmentEntityCd;
    return this;
  }

  public BigDecimal getMessageNumber() {
    return messageNumber;
  }

  public AddressEntityBuilder setMessageNumber(BigDecimal messageNumber) {
    this.messageNumber = messageNumber;
    return this;
  }

  public Integer getMessageExtension() {
    return messageExtension;
  }

  public AddressEntityBuilder setMessageExtension(Integer messageExtension) {
    this.messageExtension = messageExtension;
    return this;
  }

  public String getHeaderAddress() {
    return headerAddress;
  }

  public AddressEntityBuilder setHeaderAddress(String headerAddress) {
    this.headerAddress = headerAddress;
    return this;
  }

  public BigDecimal getPrimaryNumber() {
    return primaryNumber;
  }

  public AddressEntityBuilder setPrimaryNumber(BigDecimal primaryNumber) {
    this.primaryNumber = primaryNumber;
    return this;
  }

  public Integer getPrimaryExtension() {
    return primaryExtension;
  }

  public AddressEntityBuilder setPrimaryExtension(Integer primaryExtension) {
    this.primaryExtension = primaryExtension;
    return this;
  }

  public Short getState() {
    return state;
  }

  public AddressEntityBuilder setState(Short state) {
    this.state = state;
    return this;
  }

  public String getStreetName() {
    return streetName;
  }

  public AddressEntityBuilder setStreetName(String streetName) {
    this.streetName = streetName;
    return this;
  }

  public String getStreetNumber() {
    return streetNumber;
  }

  public AddressEntityBuilder setStreetNumber(String streetNumber) {
    this.streetNumber = streetNumber;
    return this;
  }

  public String getZip() {
    return zip;
  }

  public AddressEntityBuilder setZip(String zip) {
    this.zip = zip;
    return this;
  }

  public String getAddressDescription() {
    return addressDescription;
  }

  public AddressEntityBuilder setAddressDescription(String addressDescription) {
    this.addressDescription = addressDescription;
    return this;
  }

  public Short getZip4() {
    return zip4;
  }

  public AddressEntityBuilder setZip4(Short zip4) {
    this.zip4 = zip4;
    return this;
  }

  public String getPostDirCd() {
    return postDirCd;
  }

  public AddressEntityBuilder setPostDirCd(String postDirCd) {
    this.postDirCd = postDirCd;
    return this;
  }

  public String getPreDirCd() {
    return preDirCd;
  }

  public AddressEntityBuilder setPreDirCd(String preDirCd) {
    this.preDirCd = preDirCd;
    return this;
  }

  public Short getStreetSuffixCd() {
    return streetSuffixCd;
  }

  public AddressEntityBuilder setStreetSuffixCd(Short streetSuffixCd) {
    this.streetSuffixCd = streetSuffixCd;
    return this;
  }

  public Short getUnitDesignationCd() {
    return unitDesignationCd;
  }

  public AddressEntityBuilder setUnitDesignationCd(Short unitDesignationCd) {
    this.unitDesignationCd = unitDesignationCd;
    return this;
  }

  public String getUnitNumber() {
    return unitNumber;
  }

  public AddressEntityBuilder setUnitNumber(String unitNumber) {
    this.unitNumber = unitNumber;
    return this;
  }
}
