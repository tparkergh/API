package gov.ca.cwds.rest.api.domain;

import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

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

  @JsonProperty("first_name")
  @ApiModelProperty(example = "bart")
  @Size(max = 50)
  private String first_name;

  @JsonProperty("last_name")
  @ApiModelProperty(example = "simpson")
  @Size(max = 50)
  private String last_name;

  @JsonProperty("gender")
  @ApiModelProperty(example = "M")
  @Size(max = 10)
  // @Pattern(message = "must be one of [M, F, O]", regexp = "[M|F|O]")
  private String gender;

  @Date
  // @PastDate()
  @JsonProperty("birth_date")
  @ApiModelProperty(example = "2012-04-01")
  private String birth_date;

  @JsonProperty("ssn")
  @ApiModelProperty(example = "999551111")
  @Size(min = 9, max = 9) // SSN is fixed width.
  private String ssn;

  @JsonProperty("address")
  private Address address;

  /**
   * Constructor
   * 
   * @param first_name The first name
   * @param last_name The last name
   * @param gender The gender
   * @param birth_date The date of birth
   * @param ssn The ssn
   * @param address The address
   */
  @JsonCreator
  public Person(@JsonProperty("first_name") String first_name,
      @JsonProperty("last_name") String last_name, @JsonProperty("gender") String gender,
      @JsonProperty("birth_date") String birth_date, @JsonProperty("ssn") String ssn,
      @JsonProperty("address") Address address) {
    super();
    this.first_name = first_name;
    this.last_name = last_name;
    this.gender = gender;
    this.birth_date = birth_date;
    this.ssn = ssn;
    this.address = address;
  }

  /**
   * Construct from persistence {@link gov.ca.cwds.rest.api.persistence.ns.Person} object.
   * 
   * @param person persistence layer person
   */
  public Person(gov.ca.cwds.rest.api.persistence.ns.Person person) {
    this.first_name = person.getFirstName();
    this.last_name = person.getLastName();
    this.gender = person.getGender();
    this.birth_date = DomainObject.cookDate(person.getDateOfBirth());
    this.ssn = person.getSsn();
    if (person.getAddress() != null) {
      this.address = new Address(person.getAddress());
    }
  }

  /**
   * @return the first_name
   */
  public String getFirst_name() {
    return first_name;
  }

  /**
   * @return the last_name
   */
  public String getLast_name() {
    return last_name;
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
  public String getDate_of_birth() {
    return birth_date;
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
  public Address getAddress() {
    return address;
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
    result = prime * result + ((birth_date == null) ? 0 : birth_date.hashCode());
    result = prime * result + ((first_name == null) ? 0 : first_name.hashCode());
    result = prime * result + ((gender == null) ? 0 : gender.hashCode());
    result = prime * result + ((last_name == null) ? 0 : last_name.hashCode());
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
    if (birth_date == null) {
      if (other.birth_date != null)
        return false;
    } else if (!birth_date.equals(other.birth_date))
      return false;
    if (first_name == null) {
      if (other.first_name != null)
        return false;
    } else if (!first_name.equals(other.first_name))
      return false;
    if (gender == null) {
      if (other.gender != null)
        return false;
    } else if (!gender.equals(other.gender))
      return false;
    if (last_name == null) {
      if (other.last_name != null)
        return false;
    } else if (!last_name.equals(other.last_name))
      return false;
    if (ssn == null) {
      if (other.ssn != null)
        return false;
    } else if (!ssn.equals(other.ssn))
      return false;
    return true;
  }
}
