package gov.ca.cwds.fixture;

import gov.ca.cwds.rest.api.domain.Person;
import java.util.HashSet;
import java.util.Set;

public class PersonResourceBuilder {
  String firstName = "John";
  String middleName = "";
  String lastName = "Smitties";
  String nameSuffix = "";
  String gender = "M";
  String birthDate = "2012-04-01";
  String ssn = "222331111";
  Set address = new HashSet<>();
  Set phoneNumber = new HashSet<>();
  Set language = new HashSet<>();
  Set ethnicity = new HashSet<>();
  Set race = new HashSet<>();

  public String getFirstName() {
    return firstName;
  }

  public PersonResourceBuilder setFirstName(String firstName) {
    this.firstName = firstName;
    return this;
  }

  public String getMiddleName() {
    return middleName;
  }

  public PersonResourceBuilder setMiddleName(String middleName) {
    this.middleName = middleName;
    return this;
  }

  public String getLastName() {
    return lastName;
  }

  public PersonResourceBuilder setLastName(String lastName) {
    this.lastName = lastName;
    return this;
  }

  public String getNameSuffix() {
    return nameSuffix;
  }

  public PersonResourceBuilder setNameSuffix(String nameSuffix) {
    this.nameSuffix = nameSuffix;
    return this;
  }

  public String getGender() {
    return gender;
  }

  public PersonResourceBuilder setGender(String gender) {
    this.gender = gender;
    return this;
  }

  public String getBirthDate() {
    return birthDate;
  }

  public PersonResourceBuilder setBirthDate(String birthDate) {
    this.birthDate = birthDate;
    return this;
  }

  public String getSsn() {
    return ssn;
  }

  public PersonResourceBuilder setSsn(String ssn) {
    this.ssn = ssn;
    return this;
  }

  public Set getAddress() {
    return address;
  }

  public PersonResourceBuilder setAddress(Set address) {
    this.address = address;
    return this;
  }

  public Set getPhoneNumber() {
    return phoneNumber;
  }

  public PersonResourceBuilder setPhoneNumber(Set phoneNumber) {
    this.phoneNumber = phoneNumber;
    return this;
  }

  public Set getLanguage() {
    return language;
  }

  public PersonResourceBuilder setLanguage(Set language) {
    this.language = language;
    return this;
  }

  public Set getEthnicity() {
    return ethnicity;
  }

  public PersonResourceBuilder setEthnicity(Set ethnicity) {
    this.ethnicity = ethnicity;
    return this;
  }

  public Set getRace() {
    return race;
  }

  public PersonResourceBuilder setRace(Set race) {
    this.race = race;
    return this;
  }

  public Person build(){
    return new Person(firstName,middleName, lastName, nameSuffix, gender, birthDate, ssn,
        address, phoneNumber, language, race, ethnicity);
  }

}
