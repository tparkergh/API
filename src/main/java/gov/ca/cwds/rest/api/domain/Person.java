package gov.ca.cwds.rest.api.domain;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.data.persistence.ns.PersonAddress;
import gov.ca.cwds.data.persistence.ns.PersonLanguage;
import gov.ca.cwds.data.persistence.ns.PersonPhone;
import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.validation.Date;
import io.swagger.annotations.ApiModelProperty;

/**
 * {@link DomainObject} representing a person.
 * 
 * @author CWDS API Team
 */
public class Person extends DomainObject implements Request, Response {

  /**
   * Defalut
   */
  private static final long serialVersionUID = 1L;

  @JsonProperty("first_name")
  @ApiModelProperty(example = "bart")
  @Size(max = 50)
  private String firstName;

  @JsonProperty("last_name")
  @ApiModelProperty(example = "simpson")
  @Size(max = 50)
  private String lastName;

  @JsonProperty("gender")
  @ApiModelProperty(example = "M")
  @Size(max = 10)
  // @Pattern(message = "must be one of [M, F, O]", regexp = "[M|F|O]")
  private String gender;

  @Date
  // @PastDate()
  @JsonProperty("birth_date")
  @ApiModelProperty(example = "2012-04-01")
  private String birthDate;

  @JsonProperty("ssn")
  @ApiModelProperty(example = "999551111")
  @Size(min = 9, max = 9) // SSN is fixed width.
  private String ssn;

  @JsonProperty("address")
  private Set<Address> address;

  @JsonProperty("phone")
  private Set<PhoneNumber> phoneNumber;

  @JsonProperty("language")
  private Set<Language> language;


  /**
   * Constructor
   * 
   * @param firstName The first name
   * @param lastName The last name
   * @param gender The gender
   * @param birthDate The date of birth
   * @param ssn The ssn
   * @param address The address
   * @param phoneNumber The phoneNumber
   * @param language The language
   */
  @JsonCreator
  public Person(@JsonProperty("first_name") String firstName,
      @JsonProperty("last_name") String lastName, @JsonProperty("gender") String gender,
      @JsonProperty("birth_date") String birthDate, @JsonProperty("ssn") String ssn,
      @JsonProperty("address") Set<Address> address,
      @JsonProperty("phone") Set<PhoneNumber> phoneNumber,
      @JsonProperty("language") Set<Language> language) {
    super();
    this.firstName = firstName;
    this.lastName = lastName;
    this.gender = gender;
    this.birthDate = birthDate;
    this.ssn = ssn;
    this.address = address;
    this.phoneNumber = phoneNumber;
    this.language = language;
  }

  /**
   * Construct from persistence {@link gov.ca.cwds.data.persistence.ns.Person} object.
   * 
   * @param person persistence layer person
   */
  public Person(gov.ca.cwds.data.persistence.ns.Person person) {
    this.firstName = person.getFirstName();
    this.lastName = person.getLastName();
    this.gender = person.getGender();
    this.birthDate = DomainChef.cookDate(person.getDateOfBirth());
    this.ssn = person.getSsn();
    if (person.getPersonAddress() != null && !person.getPersonAddress().isEmpty()) {
      for (PersonAddress personAddress : person.getPersonAddress()) {
        this.address = new HashSet<>();
        this.address.add(new Address(personAddress.getAddress()));
      }
    }
    if (person.getPersonPhone() != null && !person.getPersonPhone().isEmpty()) {
      for (PersonPhone personPhone : person.getPersonPhone()) {
        this.phoneNumber = new HashSet<>();
        this.phoneNumber.add(new PhoneNumber(personPhone.getPhoneNumber()));
      }
    }
    if (person.getPersonLanguage() != null && !person.getPersonLanguage().isEmpty()) {
      for (PersonLanguage personLanguage : person.getPersonLanguage()) {
        this.language = new HashSet<>();
        this.language.add(new Language(personLanguage.getLanguage()));
      }
    }
  }

  /**
   * @return the first_name
   */
  public String getFirstName() {
    return firstName;
  }

  /**
   * @return the last_name
   */
  public String getLastName() {
    return lastName;
  }

  /**
   * @return the gender
   */
  public String getGender() {
    return gender;
  }

  /**
   * @return the birth_date
   */
  public String getBirthDate() {
    return birthDate;
  }

  /**
   * @return the ssn
   */
  public String getSsn() {
    return ssn;
  }

  /**
   * @return the address
   */
  public Set<Address> getAddress() {
    return address;
  }

  /**
   * @return the phoneNumber
   */
  public Set<PhoneNumber> getPhoneNumber() {
    return phoneNumber;
  }

  /**
   * @return the language
   */
  public Set<Language> getLanguage() {
    return language;
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.lang.Object#hashCode()
   */
  @Override
  public final int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((address == null) ? 0 : address.hashCode());
    result = prime * result + ((phoneNumber == null) ? 0 : phoneNumber.hashCode());
    result = prime * result + ((language == null) ? 0 : language.hashCode());
    result = prime * result + ((birthDate == null) ? 0 : birthDate.hashCode());
    result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
    result = prime * result + ((gender == null) ? 0 : gender.hashCode());
    result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
    result = prime * result + ((ssn == null) ? 0 : ssn.hashCode());
    return result;
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public final boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (!(getClass().isInstance(obj)))
      return false;
    Person other = (Person) obj;
    if (address == null) {
      if (other.address != null)
        return false;
    } else if (!address.equals(other.address))
      return false;
    if (phoneNumber == null) {
      if (other.phoneNumber != null)
        return false;
    } else if (!phoneNumber.equals(other.phoneNumber))
      return false;
    if (language == null) {
      if (other.language != null)
        return false;
    } else if (!language.equals(other.language))
      return false;
    if (birthDate == null) {
      if (other.birthDate != null)
        return false;
    } else if (!birthDate.equals(other.birthDate))
      return false;
    if (firstName == null) {
      if (other.firstName != null)
        return false;
    } else if (!firstName.equals(other.firstName))
      return false;
    if (gender == null) {
      if (other.gender != null)
        return false;
    } else if (!gender.equals(other.gender))
      return false;
    if (lastName == null) {
      if (other.lastName != null)
        return false;
    } else if (!lastName.equals(other.lastName))
      return false;
    if (ssn == null) {
      if (other.ssn != null)
        return false;
    } else if (!ssn.equals(other.ssn))
      return false;
    return true;
  }
}
