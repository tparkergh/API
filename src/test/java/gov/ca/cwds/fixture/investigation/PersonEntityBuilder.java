package gov.ca.cwds.fixture.investigation;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import org.joda.time.DateTime;

import gov.ca.cwds.rest.api.domain.LegacyDescriptor;
import gov.ca.cwds.rest.api.domain.RaceAndEthnicity;
import gov.ca.cwds.rest.api.domain.investigation.InvestigationAddress;
import gov.ca.cwds.rest.api.domain.investigation.Person;
import gov.ca.cwds.rest.api.domain.investigation.PhoneNumber;

@SuppressWarnings("javadoc")
public class PersonEntityBuilder {

  private LegacyDescriptor legacyDescriptor;
  private String lastUpdatedBy = "0X5";
  private String lastUpdatedAt = "2016-04-27T23:30:14.000Z";
  private String firstName = "Art";
  private String middleName = "Mike";
  private String lastName = "Griswald";
  private String suffixTitle = "bs";
  private String gender = "M";
  private String birthDate = "1998-10-30";
  private String ssn = "999667777";
  private Set<Short> languages = new LinkedHashSet<Short>();

  private Short primaryLanguage = 1253;
  private Short secondaryLanguage = 1255;
  private RaceAndEthnicity raceAndEthnicity = new RaceAndEthnicityEntityBuilder().build();
  private Boolean sensitive = false;
  private Boolean sealed = false;
  private DateTime now = new DateTime();

  private BigDecimal phoneNumber = new BigDecimal(3219876);
  private LegacyDescriptor phoneLegacyDescriptor =
      new LegacyDescriptor("1234567ABC", "001-2000-3399-415790", now, "CLIENT_T", "Client");

  private PhoneNumber phone = new PhoneNumber(phoneNumber, 3322, "Home", phoneLegacyDescriptor);
  private Set<PhoneNumber> phoneNumbers = new LinkedHashSet<PhoneNumber>();
  private Set<String> roles = new HashSet();

  private InvestigationAddress address = new InvestigationAddressEntityBuilder().build();
  private Set<InvestigationAddress> addresses = new HashSet();

  public Person build() {
    roles.add("Mandated reporter");
    addresses.add(address);
    languages.add(primaryLanguage);
    languages.add(secondaryLanguage);
    legacyDescriptor =
        new LegacyDescriptor("1234567ABC", "111-222-333-4444", now, "CLIENT_T", "Client");
    phoneNumbers.add(phone);

    return new Person(legacyDescriptor, lastUpdatedBy, lastUpdatedAt, firstName, middleName,
        lastName, suffixTitle, gender, birthDate, ssn, languages, raceAndEthnicity, sensitive,
        sealed, phoneNumbers, roles, addresses);
  }

  public PersonEntityBuilder setLegacyDescriptor(LegacyDescriptor legacyDescriptor) {
    this.legacyDescriptor = legacyDescriptor;
    return this;
  }

  public PersonEntityBuilder setLastUpdatedBy(String lastUpdatedBy) {
    this.lastUpdatedBy = lastUpdatedBy;
    return this;
  }

  public PersonEntityBuilder setLastUpdatedAt(String lastUpdatedAt) {
    this.lastUpdatedAt = lastUpdatedAt;
    return this;
  }

  public PersonEntityBuilder setFirstName(String firstName) {
    this.firstName = firstName;
    return this;
  }

  public PersonEntityBuilder setMiddleName(String middleName) {
    this.middleName = middleName;
    return this;

  }

  public PersonEntityBuilder setLastName(String lastName) {
    this.lastName = lastName;
    return this;
  }

  public PersonEntityBuilder setSuffixTitle(String suffixTitle) {
    this.suffixTitle = suffixTitle;
    return this;
  }

  public PersonEntityBuilder setGender(String gender) {
    this.gender = gender;
    return this;
  }

  public PersonEntityBuilder setSnn(String setSnn) {
    this.ssn = ssn;
    return this;
  }

  public PersonEntityBuilder setBirthDate(String birthDate) {
    this.birthDate = birthDate;
    return this;
  }

  public PersonEntityBuilder setLanguages(Set<Short> languages) {
    this.languages = languages;
    return this;
  }

  public PersonEntityBuilder setRaceAndEthnicity(RaceAndEthnicity raceAndEthnicity) {
    this.raceAndEthnicity = raceAndEthnicity;
    return this;
  }


  public PersonEntityBuilder setSensitive(Boolean sensitive) {
    this.sensitive = sensitive;
    return this;
  }

  public PersonEntityBuilder setSealed(Boolean sealed) {
    this.sealed = sealed;
    return this;
  }

  public PersonEntityBuilder setPhoneNumbers(Set<PhoneNumber> phoneNumbers) {
    this.phoneNumbers = phoneNumbers;
    return this;
  }

  public PersonEntityBuilder setRoles(Set<String> roles) {
    this.roles = roles;
    return this;
  }

  public PersonEntityBuilder setAddresses(Set<InvestigationAddress> addresses) {
    this.addresses = addresses;
    return this;
  }

  public LegacyDescriptor getLegacyDescriptor() {
    return legacyDescriptor;
  }

  public String getLastUpdatedBy() {
    return lastUpdatedBy;
  }

  public String getLastUpdatedAt() {
    return lastUpdatedAt;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getMiddleName() {
    return middleName;
  }

  public String getLastName() {
    return lastName;
  }

  public String getSuffixTitle() {
    return suffixTitle;
  }

  public String getGender() {
    return gender;
  }

  public String getSnn() {
    return ssn;
  }

  public String getBirthDate() {
    return birthDate;
  }

  public Set<Short> getLanguages() {
    return languages;
  }

  public RaceAndEthnicity getRaceAndEthnicity() {
    return raceAndEthnicity;
  }

  public Boolean getSensitive() {
    return sensitive;
  }

  public Boolean getSealed() {
    return sealed;
  }

  public Set<PhoneNumber> getPhoneNumbers() {
    return phoneNumbers;
  }

  public Set<String> getRoles() {
    return roles;
  }

  public Set<InvestigationAddress> getAddresses() {
    return addresses;
  }

}
