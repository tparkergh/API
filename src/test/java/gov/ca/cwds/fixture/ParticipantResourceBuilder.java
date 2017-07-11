package gov.ca.cwds.fixture;

import gov.ca.cwds.rest.api.domain.LegacyDescriptor;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import gov.ca.cwds.rest.api.domain.Participant;

/**
 * 
 * @author CWDS API Team
 */
public class ParticipantResourceBuilder {

  long id = 5432;
  String legacySourceTable = "";
  String legacyId = "";
  LegacyDescriptor legacyDescriptor = null;
  long personId = 12345;
  long screeningId = 12345;
  String firstName = "John";
  String middleName = "S";
  String lastName = "Smith";
  String suffix = "Jr.";
  String gender = "male";
  String dateOfBirth = "2001-03-15";
  String ssn = "123456789";
  Set<String> roles;
  Set<gov.ca.cwds.rest.api.domain.Address> addresses;

  /**
   * @param id - id
   * @return the id
   */
  public ParticipantResourceBuilder setId(long id) {
    this.id = id;
    return this;
  }

  /**
   * @param legacySourceTable - legacySourceTable
   * @return the legacySourceTable
   */
  public ParticipantResourceBuilder setLegacySourceTable(String legacySourceTable) {
    this.legacySourceTable = legacySourceTable;
    return this;
  }

  /**
   * @param legacyId - legacyId
   * @return the legacyId
   */
  public ParticipantResourceBuilder setLegacyId(String legacyId) {
    this.legacyId = legacyId;
    return this;
  }

  /**
   * @param legacyDescriptor - legacyDescriptor
   * @return the legacyDescriptor
   */
  public ParticipantResourceBuilder setLegacyDescriptor(LegacyDescriptor legacyDescriptor) {
    this.legacyDescriptor = legacyDescriptor;
    return this;
  }

  /**
   * @param personId person id
   * @return the person id
   */
  public ParticipantResourceBuilder setPersonId(long personId) {
    this.personId = personId;
    return this;
  }

  /**
   * @param screeningId - screeningId
   * @return the screeningId
   */
  public ParticipantResourceBuilder setScreeningId(long screeningId) {
    this.screeningId = screeningId;
    return this;
  }

  /**
   * @param firstName - firstName
   * @return the firstName
   */
  public ParticipantResourceBuilder setFirstName(String firstName) {
    this.firstName = firstName;
    return this;
  }

  /**
   * @param middleName - middleName
   * @return the middleName
   */
  public ParticipantResourceBuilder setMiddleName(String middleName) {
    this.middleName = middleName;
    return this;
  }

  /**
   * @param lastName - lastName
   * @return the lastName
   */
  public ParticipantResourceBuilder setLastName(String lastName) {
    this.lastName = lastName;
    return this;
  }

  /**
   * @param gender - gender
   * @return the gender
   */
  public ParticipantResourceBuilder setGender(String gender) {
    this.gender = gender;
    return this;
  }

  /**
   * @param dateOfBirth - dateOfBirth
   * @return the dateOfBirth
   */
  public ParticipantResourceBuilder setDateOfBirth(String dateOfBirth) {
    this.dateOfBirth = dateOfBirth;
    return this;
  }

  /**
   * @param ssn - ssn
   * @return the ssn
   */
  public ParticipantResourceBuilder setSsn(String ssn) {
    this.ssn = ssn;
    return this;
  }

  /**
   * @param roles - roles
   * @return the roles
   */
  public ParticipantResourceBuilder setRoles(Set<String> roles) {
    this.roles = roles;
    return this;
  }

  /**
   * @param addresses - addresses
   * @return the addresses
   */
  public ParticipantResourceBuilder setAddresses(
      Set<gov.ca.cwds.rest.api.domain.Address> addresses) {
    this.addresses = addresses;
    return this;
  }

  /**
   * 
   */
  public ParticipantResourceBuilder() {
    this.roles = new HashSet<String>(Arrays.asList("Victim"));

    gov.ca.cwds.rest.api.domain.Address address = new AddressResourceBuilder()
        .setStreetAddress("123 First St").setCity("San Jose").setZip(94321).createAddress();
    this.addresses = new HashSet<>(Arrays.asList(address));

  }

  /**
   * @return the createVictimParticipant
   */
  public Participant createVictimParticipant() {
    this.roles = new HashSet<String>(Arrays.asList("Victim"));
    return createParticipant();
  }

  /**
   * @return the createPerpParticipant
   */
  public Participant createPerpParticipant() {
    this.roles = new HashSet<String>(Arrays.asList("Perpetrator"));
    return createParticipant();
  }

  /**
   * @return the createReporterParticipant
   */
  public Participant createReporterParticipant() {
    //reporter only allows first initial
    this.middleName = middleName.substring(0,1);
    this.roles = new HashSet<String>(Arrays.asList("Non-mandated Reporter"));
    return createParticipant();

  }

  /**
   * @return the createParticipant
   */
  public Participant createParticipant() {
    return new Participant(id, legacySourceTable, legacyId, firstName, middleName, lastName, suffix, gender, ssn,
        dateOfBirth, personId, screeningId, roles, addresses);
  }
}
