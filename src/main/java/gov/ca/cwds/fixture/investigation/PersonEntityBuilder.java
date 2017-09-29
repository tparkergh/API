package gov.ca.cwds.fixture.investigation;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import org.joda.time.DateTime;

import gov.ca.cwds.rest.api.domain.RaceAndEthnicity;
import gov.ca.cwds.rest.api.domain.investigation.CmsRecordDescriptor;
import gov.ca.cwds.rest.api.domain.investigation.InvestigationAddress;
import gov.ca.cwds.rest.api.domain.investigation.Person;
import gov.ca.cwds.rest.api.domain.investigation.PhoneNumber;

@SuppressWarnings("javadoc")
public class PersonEntityBuilder {

  private CmsRecordDescriptor cmsRecordDescriptor;
  protected String lastUpdatedBy = "0X5";
  protected String lastUpdatedAt = "2016-04-27T23:30:14.000Z";
  protected String firstName = "Art";
  protected String middleName = "Mike";
  protected String lastName = "Griswald";
  protected String suffixTitle = "bs";
  protected String gender = "M";
  protected String birthDate = "1998-10-30";
  protected String ssn = "999667777";
  private Set<Short> languages = new LinkedHashSet<>();

  protected Short primaryLanguage = 1253;
  protected Short secondaryLanguage = 1255;
  private RaceAndEthnicity raceAndEthnicity = new RaceAndEthnicityEntityBuilder().build();
  protected Boolean sensitive = Boolean.FALSE;
  protected Boolean sealed = Boolean.FALSE;
  private DateTime now = new DateTime("2010-10-01T15:26:42.000-0700");
  protected Short phoneType = 1111;

  private BigDecimal phoneNumber = new BigDecimal(3219876);
  private CmsRecordDescriptor phoneCmsRecordDescriptor =
      new CmsRecordDescriptor("1234567ABC", "001-2000-3399-415790", now, "CLIENT_T", "Client");

  private PhoneNumber phone =
      new PhoneNumber(phoneNumber, 3322, phoneType, phoneCmsRecordDescriptor);
  private Set<PhoneNumber> phoneNumbers = new HashSet<>();
  private Set<String> roles = new HashSet<>();

  private InvestigationAddress address = new InvestigationAddressEntityBuilder().build();
  private Set<InvestigationAddress> addresses = new HashSet<>();

  public Person build() {
    roles.add("Mandated reporter");
    addresses.add(address);
    languages.add(primaryLanguage);
    languages.add(secondaryLanguage);
    cmsRecordDescriptor =
        new CmsRecordDescriptor("1234567ABC", "111-222-333-4444", now, "CLIENT_T", "Client");
    phoneNumbers.add(phone);

    return new Person(cmsRecordDescriptor, lastUpdatedBy, lastUpdatedAt, firstName, middleName,
        lastName, suffixTitle, gender, birthDate, ssn, languages, raceAndEthnicity, sensitive,
        sealed, phoneNumbers, roles, addresses);
  }

  public CmsRecordDescriptor getCmsRecordDescriptor() {
    return cmsRecordDescriptor;
  }

  public PersonEntityBuilder setCmsRecordDescriptor(CmsRecordDescriptor cmsRecordDescriptor) {
    this.cmsRecordDescriptor = cmsRecordDescriptor;
    return this;
  }

  public String getLastUpdatedBy() {
    return lastUpdatedBy;
  }

  public PersonEntityBuilder setLastUpdatedBy(String lastUpdatedBy) {
    this.lastUpdatedBy = lastUpdatedBy;
    return this;
  }

  public String getLastUpdatedAt() {
    return lastUpdatedAt;
  }

  public PersonEntityBuilder setLastUpdatedAt(String lastUpdatedAt) {
    this.lastUpdatedAt = lastUpdatedAt;
    return this;
  }

  public String getFirstName() {
    return firstName;
  }

  public PersonEntityBuilder setFirstName(String firstName) {
    this.firstName = firstName;
    return this;
  }

  public String getMiddleName() {
    return middleName;
  }

  public PersonEntityBuilder setMiddleName(String middleName) {
    this.middleName = middleName;
    return this;
  }

  public String getLastName() {
    return lastName;
  }

  public PersonEntityBuilder setLastName(String lastName) {
    this.lastName = lastName;
    return this;
  }

  public String getSuffixTitle() {
    return suffixTitle;
  }

  public PersonEntityBuilder setSuffixTitle(String suffixTitle) {
    this.suffixTitle = suffixTitle;
    return this;
  }

  public String getGender() {
    return gender;
  }

  public PersonEntityBuilder setGender(String gender) {
    this.gender = gender;
    return this;
  }

  public String getBirthDate() {
    return birthDate;
  }

  public PersonEntityBuilder setBirthDate(String birthDate) {
    this.birthDate = birthDate;
    return this;
  }

  public String getSsn() {
    return ssn;
  }

  public PersonEntityBuilder setSsn(String ssn) {
    this.ssn = ssn;
    return this;
  }

  public Set<Short> getLanguages() {
    return languages;
  }

  public PersonEntityBuilder setLanguages(Set<Short> languages) {
    this.languages = languages;
    return this;
  }

  public Short getPrimaryLanguage() {
    return primaryLanguage;
  }

  public PersonEntityBuilder setPrimaryLanguage(Short primaryLanguage) {
    this.primaryLanguage = primaryLanguage;
    return this;
  }

  public Short getSecondaryLanguage() {
    return secondaryLanguage;
  }

  public PersonEntityBuilder setSecondaryLanguage(Short secondaryLanguage) {
    this.secondaryLanguage = secondaryLanguage;
    return this;
  }

  public RaceAndEthnicity getRaceAndEthnicity() {
    return raceAndEthnicity;
  }

  public PersonEntityBuilder setRaceAndEthnicity(RaceAndEthnicity raceAndEthnicity) {
    this.raceAndEthnicity = raceAndEthnicity;
    return this;
  }

  public Boolean getSensitive() {
    return sensitive;
  }

  public PersonEntityBuilder setSensitive(Boolean sensitive) {
    this.sensitive = sensitive;
    return this;
  }

  public Boolean getSealed() {
    return sealed;
  }

  public PersonEntityBuilder setSealed(Boolean sealed) {
    this.sealed = sealed;
    return this;
  }

  public DateTime getNow() {
    return now;
  }

  public PersonEntityBuilder setNow(DateTime now) {
    this.now = now;
    return this;
  }

  public BigDecimal getPhoneNumber() {
    return phoneNumber;
  }

  public PersonEntityBuilder setPhoneNumber(BigDecimal phoneNumber) {
    this.phoneNumber = phoneNumber;
    return this;
  }

  public CmsRecordDescriptor getPhoneCmsRecordDescriptor() {
    return phoneCmsRecordDescriptor;
  }

  public PersonEntityBuilder setPhoneCmsRecordDescriptor(
      CmsRecordDescriptor phoneCmsRecordDescriptor) {
    this.phoneCmsRecordDescriptor = phoneCmsRecordDescriptor;
    return this;
  }

  public PhoneNumber getPhone() {
    return phone;
  }

  public PersonEntityBuilder setPhone(PhoneNumber phone) {
    this.phone = phone;
    return this;
  }

  public Set<PhoneNumber> getPhoneNumbers() {
    return phoneNumbers;
  }

  public PersonEntityBuilder setPhoneNumbers(Set<PhoneNumber> phoneNumbers) {
    this.phoneNumbers = phoneNumbers;
    return this;
  }

  public Set<String> getRoles() {
    return roles;
  }

  public PersonEntityBuilder setRoles(Set<String> roles) {
    this.roles = roles;
    return this;
  }

  public InvestigationAddress getAddress() {
    return address;
  }

  public PersonEntityBuilder setAddress(InvestigationAddress address) {
    this.address = address;
    return this;
  }

  public Set<InvestigationAddress> getAddresses() {
    return addresses;
  }

  public PersonEntityBuilder setAddresses(Set<InvestigationAddress> addresses) {
    this.addresses = addresses;
    return this;
  }

}
