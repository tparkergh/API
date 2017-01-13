package gov.ca.cwds.rest.api.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.validation.Date;
import io.dropwizard.jackson.JsonSnakeCase;
import io.swagger.annotations.ApiModelProperty;

/**
 * {@link DomainObject} representing a Participant
 * 
 * @author CWDS API Team
 */
@JsonSnakeCase
public class Participant extends DomainObject implements Request, Response {

  @JsonProperty("person_id")
  @ApiModelProperty(example = "12345")
  private long personId;

  @JsonProperty("screening_id")
  @ApiModelProperty(example = "12345")
  private long screeningId;

  @JsonProperty("first_name")
  @ApiModelProperty(example = "John")
  private String firstName;

  @JsonProperty("last_name")
  @ApiModelProperty(example = "Smith")
  private String lastName;

  @JsonProperty("gender")
  @ApiModelProperty(example = "Male")
  private String gender;

  @Date
  @JsonProperty("date_of_birth")
  @ApiModelProperty(example = "12/13/2010")
  private String dateOfBirth;

  @JsonProperty("ssn")
  @ApiModelProperty(example = "111-22-3333")
  private String ssn;

  /**
   * @param personId The person Id
   * @param screeningId The screening Id
   * @param firstName The first Name
   * @param lastName The last Name
   * @param gender The gender
   * @param dateOfBirth The date Of Birth
   * @param ssn The social security number
   */
  public Participant(long personId, long screeningId, String firstName, String lastName,
      String gender, String dateOfBirth, String ssn) {
    super();
    this.personId = personId;
    this.screeningId = screeningId;
    this.firstName = firstName;
    this.lastName = lastName;
    this.gender = gender;
    this.dateOfBirth = dateOfBirth;
    this.ssn = ssn;
  }


  /**
   * @return the personId
   */
  public long getPersonId() {
    return personId;
  }

  /**
   * @return the screeningId
   */
  public long getScreeningId() {
    return screeningId;
  }

  /**
   * @return the firstName
   */
  public String getFirstName() {
    return firstName;
  }

  /**
   * @return the lastName
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
   * @return the dateOfBirth
   */
  public String getDateOfBirth() {
    return dateOfBirth;
  }

  /**
   * @return the ssn
   */
  public String getSsn() {
    return ssn;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((dateOfBirth == null) ? 0 : dateOfBirth.hashCode());
    result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
    result = prime * result + ((gender == null) ? 0 : gender.hashCode());
    result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
    result = prime * result + (int) (personId ^ (personId >>> 32));
    result = prime * result + (int) (screeningId ^ (screeningId >>> 32));
    result = prime * result + ((ssn == null) ? 0 : ssn.hashCode());
    return result;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    Participant other = (Participant) obj;
    if (dateOfBirth == null) {
      if (other.dateOfBirth != null) {
        return false;
      }
    } else if (!dateOfBirth.equals(other.dateOfBirth)) {
      return false;
    }
    if (firstName == null) {
      if (other.firstName != null) {
        return false;
      }
    } else if (!firstName.equals(other.firstName)) {
      return false;
    }
    if (gender == null) {
      if (other.gender != null) {
        return false;
      }
    } else if (!gender.equals(other.gender)) {
      return false;
    }
    if (lastName == null) {
      if (other.lastName != null) {
        return false;
      }
    } else if (!lastName.equals(other.lastName)) {
      return false;
    }
    if (personId != other.personId) {
      return false;
    }
    if (screeningId != other.screeningId) {
      return false;
    }
    if (ssn == null) {
      if (other.ssn != null) {
        return false;
      }
    } else if (!ssn.equals(other.ssn)) {
      return false;
    }
    return true;
  }


}
