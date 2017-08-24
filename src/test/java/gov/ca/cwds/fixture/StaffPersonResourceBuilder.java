package gov.ca.cwds.fixture;

import java.math.BigDecimal;

import gov.ca.cwds.rest.api.domain.StaffPerson;

/**
 * @author CWDS API Team
 *
 */
@SuppressWarnings("javadoc")
public class StaffPersonResourceBuilder {

  String endDate = "2016-10-31";
  String firstName = "b";
  String jobTitle = "c";
  String lastName = "d";
  String middleInitial = "e";
  String namePrefix = "f";
  BigDecimal phoneNumber = new BigDecimal(1);
  Integer phoneExt = 2;
  String startDate = "2016-10-31";
  String nameSuffix = "g";
  Boolean telecommuterIndicator = Boolean.TRUE;
  String cwsOffice = "h";
  String availabilityAndLocationDescription = "i";
  String ssrsLicensingWorkerId = "j";
  String countyCode = "k";
  Boolean dutyWorkerIndicator = Boolean.FALSE;
  String cwsOfficeAddress = "l";
  String emailAddress = "m";

  public StaffPerson build() {
    return new StaffPerson(endDate, firstName, jobTitle, lastName, middleInitial, namePrefix,
        phoneNumber, phoneExt, startDate, nameSuffix, telecommuterIndicator, cwsOffice,
        availabilityAndLocationDescription, ssrsLicensingWorkerId, countyCode, dutyWorkerIndicator,
        cwsOfficeAddress, emailAddress);
  }

  public String getEndDate() {
    return endDate;
  }

  public StaffPersonResourceBuilder setEndDate(String endDate) {
    this.endDate = endDate;
    return this;
  }

  public String getFirstName() {
    return firstName;
  }

  public StaffPersonResourceBuilder setFirstName(String firstName) {
    this.firstName = firstName;
    return this;
  }

  public String getJobTitle() {
    return jobTitle;
  }

  public StaffPersonResourceBuilder setJobTitle(String jobTitle) {
    this.jobTitle = jobTitle;
    return this;
  }

  public String getLastName() {
    return lastName;
  }

  public StaffPersonResourceBuilder setLastName(String lastName) {
    this.lastName = lastName;
    return this;
  }

  public String getMiddleInitial() {
    return middleInitial;
  }

  public StaffPersonResourceBuilder setMiddleInitial(String middleInitial) {
    this.middleInitial = middleInitial;
    return this;
  }

  public String getNamePrefix() {
    return namePrefix;
  }

  public StaffPersonResourceBuilder setNamePrefix(String namePrefix) {
    this.namePrefix = namePrefix;
    return this;
  }

  public BigDecimal getPhoneNumber() {
    return phoneNumber;
  }

  public StaffPersonResourceBuilder setPhoneNumber(BigDecimal phoneNumber) {
    this.phoneNumber = phoneNumber;
    return this;
  }

  public Integer getPhoneExt() {
    return phoneExt;
  }

  public StaffPersonResourceBuilder setPhoneExt(Integer phoneExt) {
    this.phoneExt = phoneExt;
    return this;
  }

  public String getStartDate() {
    return startDate;
  }

  public StaffPersonResourceBuilder setStartDate(String startDate) {
    this.startDate = startDate;
    return this;
  }

  public String getNameSuffix() {
    return nameSuffix;
  }

  public StaffPersonResourceBuilder setNameSuffix(String nameSuffix) {
    this.nameSuffix = nameSuffix;
    return this;
  }

  public Boolean getTelecommuterIndicator() {
    return telecommuterIndicator;
  }

  public StaffPersonResourceBuilder setTelecommuterIndicator(Boolean telecommuterIndicator) {
    this.telecommuterIndicator = telecommuterIndicator;
    return this;
  }

  public String getCwsOffice() {
    return cwsOffice;
  }

  public StaffPersonResourceBuilder setCwsOffice(String cwsOffice) {
    this.cwsOffice = cwsOffice;
    return this;
  }

  public String getAvailabilityAndLocationDescription() {
    return availabilityAndLocationDescription;
  }

  public StaffPersonResourceBuilder setAvailabilityAndLocationDescription(
      String availabilityAndLocationDescription) {
    this.availabilityAndLocationDescription = availabilityAndLocationDescription;
    return this;
  }

  public String getSsrsLicensingWorkerId() {
    return ssrsLicensingWorkerId;
  }

  public StaffPersonResourceBuilder setSsrsLicensingWorkerId(String ssrsLicensingWorkerId) {
    this.ssrsLicensingWorkerId = ssrsLicensingWorkerId;
    return this;
  }

  public String getCountyCode() {
    return countyCode;
  }

  public StaffPersonResourceBuilder setCountyCode(String countyCode) {
    this.countyCode = countyCode;
    return this;
  }

  public Boolean getDutyWorkerIndicator() {
    return dutyWorkerIndicator;
  }

  public StaffPersonResourceBuilder setDutyWorkerIndicator(Boolean dutyWorkerIndicator) {
    this.dutyWorkerIndicator = dutyWorkerIndicator;
    return this;
  }

  public String getCwsOfficeAddress() {
    return cwsOfficeAddress;
  }

  public StaffPersonResourceBuilder setCwsOfficeAddress(String cwsOfficeAddress) {
    this.cwsOfficeAddress = cwsOfficeAddress;
    return this;
  }

  public String getEmailAddress() {
    return emailAddress;
  }

  public StaffPersonResourceBuilder setEmailAddress(String emailAddress) {
    this.emailAddress = emailAddress;
    return this;
  }

}
