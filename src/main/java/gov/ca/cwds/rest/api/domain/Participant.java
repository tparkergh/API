package gov.ca.cwds.rest.api.domain;

import static gov.ca.cwds.data.persistence.cms.CmsPersistentObject.CMS_ID_LEN;

import java.util.HashSet;
import java.util.Set;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.services.ServiceException;
import gov.ca.cwds.rest.validation.Date;
import gov.ca.cwds.rest.validation.ParticipantValidator;
import gov.ca.cwds.rest.validation.ValidSystemCodeId;
import io.dropwizard.jackson.JsonSnakeCase;
import io.dropwizard.validation.OneOf;
import io.swagger.annotations.ApiModelProperty;

/**
 * {@link DomainObject} representing a Participant.
 * 
 * @author CWDS API Team
 */
@JsonSnakeCase
@JsonPropertyOrder({"id", "legacySourceTable", "legacyId", "firstName", "lastName", "gender", "ssn",
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
  @ApiModelProperty(required = true, readOnly = false, value = "Legacy Source Table",
      example = "CLIENT_T")
  private String legacySourceTable;

  @JsonProperty("legacy_id")
  @ApiModelProperty(required = true, readOnly = false, value = "Legacy Client Id",
      example = "ABC1234567")
  @Size(max = CMS_ID_LEN)
  private String legacyId;

  @ApiModelProperty(required = true, readOnly = false)
  @JsonProperty("legacy_descriptor")
  @Valid
  private LegacyDescriptor legacyDescriptor;

  @JsonProperty("first_name")
  @ApiModelProperty(required = false, readOnly = false, value = "First Name", example = "John")
  private String firstName;

  @JsonProperty("middle_name")
  @Size(min = 1, max = 20,
      message = "middleName size must be between 1 and 20 or assign the value to default Space")
  @ApiModelProperty(required = true, readOnly = false, value = "", example = "middle name")
  private String middleName;

  @JsonProperty("last_name")
  @ApiModelProperty(required = false, readOnly = false, value = "Last name", example = "Smith")
  private String lastName;

  @JsonProperty("name_suffix")
  @ApiModelProperty(required = false, readOnly = false, value = "name suffix", example = "Jr.")
  private String nameSuffix;

  @OneOf(value = {"M", "Male", "F", "Female", "O", "Other"}, ignoreCase = true,
      ignoreWhitespace = true)
  @JsonProperty("gender")
  @ApiModelProperty(required = false, readOnly = false, value = "Gender", example = "Male",
      allowableValues = "M, Male, F, Female, O, Other")
  private String gender;

  @JsonProperty("ssn")
  @Size(min = 1, max = 9,
      message = "ssn size must be between 1 and 9 or assign the value to defalut 0")
  @ApiModelProperty(required = true, readOnly = false, value = "", example = "123456789",
      notes = "Default value should be 0 can't be Empty or Null")
  private String ssn;

  @Date
  @JsonProperty("date_of_birth")
  @ApiModelProperty(required = false, readOnly = false, value = "Date of Birth",
      example = "2001-09-13")
  private String dateOfBirth;

  @JsonProperty("primary_language")
  @NotNull
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "1234",
      notes = "The code for primary Language")
  @ValidSystemCodeId(required = true, category = SystemCodeCategoryId.LANGUAGE_CODE)
  private Short primaryLanguage;

  @JsonProperty("secondary_language")
  @NotNull
>>>>>>> Stashed changes
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "1234",
      notes = "The code for secondary Language")
  @ValidSystemCodeId(required = true, category = SystemCodeCategoryId.LANGUAGE_CODE)
  private Short secondaryLanguage;

  //only used for reporter
  @JsonProperty("reporter_confidential_waiver")
  @ApiModelProperty(required = false, value = "Reporter Confidential Waiver", example = "N" )
  private boolean reporterConfidentialWaiver;

  //only used for reporter
  @JsonProperty("reporter_employer_name")
  @ApiModelProperty(required = false, value = "Reporter Employer Name", example = "Buisness Name, Inc" )
  private String reporterEmployerName;

  //only used for Client
  @JsonProperty("client_staff_person_added")
  @ApiModelProperty(required = false, value = "Staff Person Added", example = "N")
  private boolean clientStaffPersonAdded;

  @JsonProperty("screening_id")
  @ApiModelProperty(required = false, readOnly = false, value = "Screening Id", example = "12345")
  private long screeningId;

  @Valid
  @JsonProperty("roles")
  @ApiModelProperty(required = true, readOnly = false, value = "Role of participant",
      dataType = "java.util.List", example = "['Victim', 'Mandated Reporter']")
  private Set<String> roles;

  @Valid
  @ApiModelProperty(dataType = "List[gov.ca.cwds.rest.api.domain.Address]")
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
   * @param legacyDescriptor legacy descriptor
   * @param personId The person Id
   * @param screeningId The screening Id
   * @param firstName The first Name
   * @param middleName The middle Name
   * @param lastName The last Name
   * @param nameSuffix The participants suffix Name
   * @param gender The gender
   * @param dateOfBirth The date Of Birth
   * @param ssn The social security number
   * @param primaryLanguage primary language
   * @param secondaryLanguage secondary language
   * @param roles The roles of the participant
   * @param addresses The addresses of the participant
   * @throws ServiceException throw any exception
   */
  @JsonCreator
  public Participant(@JsonProperty("id") long id,
      @JsonProperty("legacy_source_table") String legacySourceTable,
      @JsonProperty("legacy_client_id") String clientId,
      @JsonProperty("legacy_descriptor") LegacyDescriptor legacyDescriptor,
      @JsonProperty("first_name") String firstName, @JsonProperty("middle_name") String middleName,
      @JsonProperty("last_name") String lastName, @JsonProperty("name_suffix") String nameSuffix,
      @JsonProperty("gender") String gender, @JsonProperty("ssn") String ssn,
      @JsonProperty("date_of_birth") String dateOfBirth,
      @JsonProperty("primary_language") Short primaryLanguage,
      @JsonProperty("secondary_language") Short secondaryLanguage,
      @JsonProperty("person_id") long personId, @JsonProperty("screening_id") long screeningId,
      @JsonProperty("roles") Set<String> roles, @JsonProperty("addresses") Set<Address> addresses)
      throws ServiceException {
    super();
    this.id = id;
    this.legacySourceTable = legacySourceTable;
    this.legacyId = clientId;
    this.legacyDescriptor = legacyDescriptor;
    this.screeningId = screeningId;
    this.firstName = firstName;
    this.middleName = middleName;
    this.lastName = lastName;
    this.nameSuffix = nameSuffix;
    this.gender = gender;
    this.dateOfBirth = dateOfBirth;
    this.ssn = ssn;
    this.primaryLanguage = primaryLanguage;
    this.secondaryLanguage = secondaryLanguage;
    this.roles = roles;
    this.addresses = addresses;

    try {
      victim = ParticipantValidator.hasVictimRole(this);
      reporter = ParticipantValidator.isReporterType(this);
      perpetrator = ParticipantValidator.isPerpetrator(this);
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
    this.screeningId = participant.getHotlineContactId();
    if (participant.getPerson() != null) {
      this.firstName = participant.getPerson().getFirstName();
      this.middleName = participant.getPerson().getMiddleName();
      this.lastName = participant.getPerson().getLastName();
      this.nameSuffix = "";
      this.gender = participant.getPerson().getGender();
      this.dateOfBirth = DomainChef.cookDate(participant.getPerson().getDateOfBirth());
      this.ssn = participant.getPerson().getSsn();
      // this.primaryLanguage = participant;
      // this.secondaryLanguage = secondaryLanguage;
      this.legacyDescriptor = new LegacyDescriptor();
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
    this.screeningId = participant.getHotlineContactId();
    if (person != null) {
      this.firstName = person.getFirstName();
      this.middleName = person.getMiddleName();
      this.lastName = person.getLastName();
      this.nameSuffix = person.getNameSuffix();
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
  public String getLegacyId() {
    return legacyId;
  }

  /**
   * @param clientId - the legacy Id
   */
  public void setLegacyId(String clientId) {
    this.legacyId = clientId;
  }

  public LegacyDescriptor getLegacyDescriptor() {
    if (legacyDescriptor == null) {
      legacyDescriptor = new LegacyDescriptor();
    }
    return legacyDescriptor;
  }

  public void setLegacyDescriptor(LegacyDescriptor legacyDescriptor) {
    this.legacyDescriptor = legacyDescriptor;
  }

  /**
   * @return the firstName
   */
  public String getFirstName() {
    return firstName;
  }

  /**
   * @return the middleName
   */
  public String getMiddleName() {

    return middleName;
  }

  /** * @return the lastName */
  public String getLastName() {
    return lastName;
  }

  /** * @return the name suffix */
  public String getNameSuffix() {
    return nameSuffix;
  }

  /**
   * @return the gender
   */
  public String getGender() {
    return gender;
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
   * @return the primaryLanguage
   */
  public Short getPrimaryLanguage() {
    return primaryLanguage;
  }

  /**
   * @return the secondaryLanguage
   */
  public Short getSecondaryLanguage() {
    return secondaryLanguage;
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
   * adds a set of addresses to current addresses.
   * 
   * @param addresses - domain addresses
   */
  public void addAddresses(Set<Address> addresses) {
    if (addresses == null) {
      return;
    }
    if (this.addresses == null) {
      this.addresses = new HashSet<>();
    }
    this.addresses.addAll(addresses);
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

  /**
   * {@inheritDoc}
   * 
   * @see java.lang.Object#hashCode()
   */
  @Override
  public final int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((addresses == null) ? 0 : addresses.hashCode());
    result = prime * result + ((dateOfBirth == null) ? 0 : dateOfBirth.hashCode());
    result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
    result = prime * result + ((gender == null) ? 0 : gender.hashCode());
    result = prime * result + (int) (id ^ (id >>> 32));
    result = prime * result + ((middleName == null) ? 0 : middleName.hashCode());
    result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
    result = prime * result + ((nameSuffix == null) ? 0 : nameSuffix.hashCode());
    result = prime * result + ((reporterEmployerName == null) ? 0 : reporterEmployerName.hashCode());
    result = prime * result + ((reporterConfidentialWaiver == true) ? 1 : 0);
    result = prime * result + ((clientStaffPersonAdded == true) ? 1 : 0);
    result = prime * result + ((primaryLanguage == null) ? 0 : primaryLanguage.hashCode());
    result = prime * result + ((secondaryLanguage == null) ? 0 : secondaryLanguage.hashCode());
    result = prime * result + ((legacyId == null) ? 0 : legacyId.hashCode());
    result = prime * result + ((legacySourceTable == null) ? 0 : legacySourceTable.hashCode());
    result = prime * result + ((legacyDescriptor == null) ? 0 : legacyDescriptor.hashCode());
    result = prime * result + ((roles == null) ? 0 : roles.hashCode());
    result = prime * result + (int) (screeningId ^ (screeningId >>> 32));
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
    if (!(obj instanceof Participant))
      return false;
    Participant other = (Participant) obj;
    if (addresses == null) {
      if (other.addresses != null)
        return false;
    } else if (!addresses.equals(other.addresses))
      return false;
    if (dateOfBirth == null) {
      if (other.dateOfBirth != null)
        return false;
    } else if (!dateOfBirth.equals(other.dateOfBirth))
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
    if (id != other.id)
      return false;
    if (lastName == null) {
      if (other.lastName != null)
        return false;
    } else if (!lastName.equals(other.lastName))
      return false;
    if (middleName == null) {
      if (other.middleName != null)
        return false;
    } else if (!middleName.equals(other.middleName))
      return false;
    if (nameSuffix == null) {
      if (other.nameSuffix != null)
        return false;
    } else if (!nameSuffix.equals(other.nameSuffix))
      return false;
     if (reporterEmployerName == null) {
      if (other.reporterEmployerName != null)
        return false;
    } else if (!reporterEmployerName.equals(other.reporterEmployerName))
      return false;
    if (reporterConfidentialWaiver != other.reporterConfidentialWaiver){
      return false;
    }
    if (clientStaffPersonAdded != other.clientStaffPersonAdded){
      return false;
    }
    if (primaryLanguage == null) {
      if (other.primaryLanguage != null)
        return false;
    } else if (!primaryLanguage.equals(other.primaryLanguage))
      return false;
    if (secondaryLanguage == null) {
      if (other.secondaryLanguage != null)
        return false;
    } else if (!secondaryLanguage.equals(other.secondaryLanguage))
      return false;
    if (legacyId == null) {
      if (other.legacyId != null)
        return false;
    } else if (!legacyId.equals(other.legacyId))
      return false;
    if (legacySourceTable == null) {
      if (other.legacySourceTable != null)
        return false;
    } else if (!legacySourceTable.equals(other.legacySourceTable))
      return false;
    if (roles == null) {
      if (other.roles != null)
        return false;
    } else if (!roles.equals(other.roles))
      return false;
    if (screeningId != other.screeningId)
      return false;
    if (ssn == null) {
      if (other.ssn != null)
        return false;
    } else if (!ssn.equals(other.ssn))
      return false;
    if (legacyDescriptor == null) {
      if (other.legacyDescriptor != null)
        return false;
    } else if (!legacyDescriptor.equals(other.legacyDescriptor))
      return false;

    return true;
  }

}
