package gov.ca.cwds.rest.api.domain;

import java.util.Set;

import javax.validation.Valid;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.validation.Date;
import gov.ca.cwds.rest.validation.ParticipantValidator;
import io.dropwizard.jackson.JsonSnakeCase;
import io.dropwizard.validation.OneOf;
import io.swagger.annotations.ApiModelProperty;

/**
 * {@link DomainObject} representing a Participant
 * 
 * @author CWDS API Team
 */
@JsonSnakeCase
@JsonPropertyOrder({"id", "legacySourceTable", "clientId", "firstName", "lastName", "gender", "ssn",
    "dateOfBirth", "roles", "addresses"})
public class Participant extends ReportingDomain implements Request, Response {
  /**
   * Serialization version
   */
  private static final long serialVersionUID = 1L;

  @JsonProperty("id")
  @ApiModelProperty(required = true, readOnly = false, value = "Participant Id", example = "12345")
  private long id;

  @JsonProperty("legacy_source_table")
  @ApiModelProperty(required = true, readOnly = false, value = "Legacy Source Table", example = "")
  private String legacySourceTable;

  @JsonProperty("legacy_id")
  @ApiModelProperty(required = true, readOnly = false, value = "Legacy Client Id", example = "")
  @Size(max = 10)
  private String clientId;

  @JsonProperty("first_name")
  @ApiModelProperty(required = false, readOnly = false, value = "First Name", example = "John")
  private String firstName;

  @JsonProperty("last_name")
  @ApiModelProperty(required = false, readOnly = false, value = "Last name", example = "Smith")
  private String lastName;

  @OneOf(value = {"M", "Male", "F", "Female", "O", "Other"}, ignoreCase = true,
      ignoreWhitespace = true)
  @JsonProperty("gender")
  @ApiModelProperty(required = false, readOnly = false, value = "Gender", example = "Male",
      allowableValues = "M, Male, F, Female, O, Other")
  private String gender;

  @JsonProperty("ssn")
  @Size(max = 9)
  @ApiModelProperty(required = false, readOnly = false, value = "Social Security Number",
      example = "111223333")
  private String ssn;

  @Date
  @JsonProperty("date_of_birth")
  @ApiModelProperty(required = false, readOnly = false, value = "Date of Birth",
      example = "2001-09-13")
  private String dateOfBirth;

  @JsonProperty("person_id")
  @ApiModelProperty(required = false, readOnly = false, value = "Person Id", example = "12345")
  private long personId;

  @JsonProperty("screening_id")
  @ApiModelProperty(required = false, readOnly = false, value = "Screening Id", example = "12345")
  private long screeningId;

  @Valid
  @JsonProperty("roles")
  @ApiModelProperty(required = true, readOnly = false, value = "Role of participant", example = "")
  private Set<String> roles;

  @Valid
  @JsonProperty("addresses")
  private Set<Address> addresses;



  @JsonIgnore
  private boolean perpetrator;

  @JsonIgnore
  private boolean victim;

  @JsonIgnore
  private boolean reporter;

  /**
   * empty constructor
   */
  public Participant() {
    super();
  }

  /**
   * Constructor
   * 
   * @param id The id of the Participant
   * @param legacySourceTable - legacy source table name
   * @param clientId - the legacy clientId
   * @param personId The person Id
   * @param screeningId The screening Id
   * @param firstName The first Name
   * @param lastName The last Name
   * @param gender The gender
   * @param dateOfBirth The date Of Birth
   * @param ssn The social security number
   * @param roles The roles of the participant
   * @param addresses The addresses of the participant
   * @throws Exception
   */
  @JsonCreator
  public Participant(@JsonProperty("id") long id,
      @JsonProperty("legacy_source_table") String legacySourceTable,
      @JsonProperty("legacy_client_id") String clientId,
      @JsonProperty("first_name") String firstName, @JsonProperty("last_name") String lastName,
      @JsonProperty("gender") String gender, @JsonProperty("ssn") String ssn,
      @JsonProperty("date_of_birth") String dateOfBirth, @JsonProperty("person_id") long personId,
      @JsonProperty("screening_id") long screeningId, @JsonProperty("roles") Set<String> roles,
      @JsonProperty("addresses") Set<Address> addresses) throws Exception {
    super();
    this.id = id;
    this.legacySourceTable = legacySourceTable;
    this.clientId = clientId;
    this.personId = personId;
    this.screeningId = screeningId;
    this.firstName = firstName;
    this.lastName = lastName;
    this.gender = gender;
    this.dateOfBirth = dateOfBirth;
    this.ssn = ssn;
    this.roles = roles;
    this.addresses = addresses;

    try {
      victim = ParticipantValidator.hasVictimRole(this);
      reporter = ParticipantValidator.isReporterType(this);
      perpetrator = ParticipantValidator.isPerpatrator(this);
    } catch (Exception e) {
      throw e;
    }
  }

  /**
   * Construct from persistence class
   * 
   * @param participant persistence level participant object
   */
  public Participant(gov.ca.cwds.data.persistence.ns.Participant participant) {
    this.personId = participant.getPersonId();
    this.screeningId = participant.getHotlineContactId();
    if (participant.getPerson() != null) {
      this.firstName = participant.getPerson().getFirstName();
      this.lastName = participant.getPerson().getLastName();
      this.gender = participant.getPerson().getGender();
      this.dateOfBirth = DomainChef.cookDate(participant.getPerson().getDateOfBirth());
      this.ssn = participant.getPerson().getSsn();
    }
  }

  /**
   * Construct from persistence class and person
   * 
   * @param participant persistence level participant object
   * @param person domain person object
   * 
   */
  public Participant(gov.ca.cwds.data.persistence.ns.Participant participant, Person person) {
    this.personId = participant.getPersonId();
    this.screeningId = participant.getHotlineContactId();
    if (person != null) {
      this.firstName = person.getFirstName();
      this.lastName = person.getLastName();
      this.gender = person.getGender();
      this.dateOfBirth = person.getBirthDate();
      this.ssn = person.getSsn();
    }
  }


  /**
   * @return id
   */
  public long getId() {
    return id;
  }

  /**
   * @return the legacy source table name
   */
  public String getLegacySourceTable() {
    return legacySourceTable;
  }

  /**
   * @param legacySourceTable - the legacy source table name
   */
  public void setLegacySourceTable(String legacySourceTable) {
    this.legacySourceTable = legacySourceTable;
  }

  /**
   * @return the legacy clientId
   */
  public String getClientId() {
    return clientId;
  }

  /**
   * @param clientId - the legacy Id
   */
  public void setClientId(String clientId) {
    this.clientId = clientId;
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
   * @return the person_id
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
   * @return the roles
   */
  public Set<String> getRoles() {
    return this.roles;
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

  /**
   * @return addresses
   */
  public Set<Address> getAddresses() {
    return addresses;
  }


  /**
   * @param addresses - domain addresses
   */
  public void setAddresses(Set<Address> addresses) {
    this.addresses = addresses;
  }

  /**
   * @return boolean
   */
  public boolean isPerpetrator() {
    return perpetrator;
  }

  /**
   * @return boolean
   */
  public boolean isVictim() {
    return victim;
  }

  /**
   * @return boolean
   */
  public boolean isReporter() {
    return reporter;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#hashCode()
   */
  @Override
  public final int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + (int) (id ^ (id >>> 32));
    result = prime * result + ((dateOfBirth == null) ? 0 : dateOfBirth.hashCode());
    result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
    result = prime * result + ((gender == null) ? 0 : gender.hashCode());
    result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
    result = prime * result + (int) (personId ^ (personId >>> 32));
    result = prime * result + (int) (screeningId ^ (screeningId >>> 32));
    result = prime * result + ((ssn == null) ? 0 : ssn.hashCode());
    result = prime * result + ((roles == null) ? 0 : roles.hashCode());
    result = prime * result + ((addresses == null) ? 0 : addresses.hashCode());
    result = prime * result + ((legacySourceTable == null) ? 0 : legacySourceTable.hashCode());
    result = prime * result + ((clientId == null) ? 0 : clientId.hashCode());
    return result;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public final boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (!(getClass().isInstance(obj))) {
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
    if (id != other.id) {
      return false;
    }
    if (legacySourceTable == null) {
      if (other.legacySourceTable != null) {
        return false;
      }
    } else if (!legacySourceTable.equals(other.legacySourceTable)) {
      return false;
    }
    if (clientId == null) {
      if (other.clientId != null) {
        return false;
      }
    } else if (!clientId.equals(other.clientId)) {
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
    if (roles == null) {
      if (other.roles != null) {
        return false;
      }
    } else if (!roles.equals(other.roles)) {
      return false;
    }
    if (addresses == null) {
      if (other.addresses != null) {
        return false;
      }
    } else if (!addresses.equals(other.addresses)) {
      return false;
    }

    return true;
  }


}
