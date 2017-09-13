package gov.ca.cwds.fixture;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import gov.ca.cwds.rest.api.domain.LegacyDescriptor;
import gov.ca.cwds.rest.api.domain.Participant;
import gov.ca.cwds.rest.api.domain.RaceAndEthnicity;

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
  Short primaryLanguage = 1271;
  Short secondaryLanguage = 1253;
  boolean reporterConfidentialWaiver = false;
  String reporterEmployerName = "Employer Name";
  String sensitivityIndicator = "N";
  boolean clientStaffPersonAdded = false;
  Set<String> roles;
  Set<gov.ca.cwds.rest.api.domain.Address> addresses;
  RaceAndEthnicity raceAndEthnicity;

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
   * @return the ParticipantResourceBuilder
   */
  public ParticipantResourceBuilder setLegacySourceTable(String legacySourceTable) {
    this.legacySourceTable = legacySourceTable;
    return this;
  }

  /**
   * @param legacyId - legacyId
   * @return the ParticipantResourceBuilder
   */
  public ParticipantResourceBuilder setLegacyId(String legacyId) {
    this.legacyId = legacyId;
    return this;
  }

  /**
   * @param legacyDescriptor - legacyDescriptor
   * @return the ParticipantResourceBuilder
   */
  public ParticipantResourceBuilder setLegacyDescriptor(LegacyDescriptor legacyDescriptor) {
    this.legacyDescriptor = legacyDescriptor;
    return this;
  }

  /**
   * @param personId person id
   * @return the ParticipantResourceBuilder
   */
  public ParticipantResourceBuilder setPersonId(long personId) {
    this.personId = personId;
    return this;
  }

  /**
   * @param screeningId - screeningId
   * @return the ParticipantResourceBuilder
   */
  public ParticipantResourceBuilder setScreeningId(long screeningId) {
    this.screeningId = screeningId;
    return this;
  }

  /**
   * @param firstName - firstName
   * @return the ParticipantResourceBuilder
   */
  public ParticipantResourceBuilder setFirstName(String firstName) {
    this.firstName = firstName;
    return this;
  }

  /**
   * @param middleName - middleName
   * @return the ParticipantResourceBuilder
   */
  public ParticipantResourceBuilder setMiddleName(String middleName) {
    this.middleName = middleName;
    return this;
  }

  /**
   * @param lastName - lastName
   * @return the ParticipantResourceBuilder
   */
  public ParticipantResourceBuilder setLastName(String lastName) {
    this.lastName = lastName;
    return this;
  }

  /**
   * @param gender - gender
   * @return the ParticipantResourceBuilder
   */
  public ParticipantResourceBuilder setGender(String gender) {
    this.gender = gender;
    return this;
  }

  /**
   * @param dateOfBirth - dateOfBirth
   * @return the ParticipantResourceBuilder
   */
  public ParticipantResourceBuilder setDateOfBirth(String dateOfBirth) {
    this.dateOfBirth = dateOfBirth;
    return this;
  }

  /**
   * @param primaryLanguage - primaryLanguage
   * @return the ParticipantResourceBuilder
   */
  public ParticipantResourceBuilder setPrimaryLanguage(Short primaryLanguage) {
    this.primaryLanguage = primaryLanguage;
    return this;
  }

  /**
   * @param secondaryLanguage - secondaryLanguage
   * @return the ParticipantResourceBuilder
   */
  public ParticipantResourceBuilder setSecondaryLanguage(Short secondaryLanguage) {
    this.secondaryLanguage = secondaryLanguage;
    return this;
  }

  /**
   * @param ssn - ssn
   * @return the ParticipantResourceBuilder
   */
  public ParticipantResourceBuilder setSsn(String ssn) {
    this.ssn = ssn;
    return this;
  }

  /**
   * @param suffix - suffix
   * @return the ParticipantResourceBuilder
   */
  public ParticipantResourceBuilder setSuffix(String suffix) {
    this.suffix = suffix;
    return this;
  }

  /**
   * @param reporterConfidentialWaiver - reporterConfidentialWaiver
   * @return the ParticipantResourceBuilder
   */
  public ParticipantResourceBuilder setReporterConfidentialWaiver(
      boolean reporterConfidentialWaiver) {
    this.reporterConfidentialWaiver = reporterConfidentialWaiver;
    return this;
  }

  /**
   * @param reporterEmployerName - reporterEmployerName
   * @return the ParticipantResourceBuilder
   */
  public ParticipantResourceBuilder setReporterEmployerName(String reporterEmployerName) {
    this.reporterEmployerName = reporterEmployerName;
    return this;
  }

  /**
   * @param clientStaffPersonAdded - clientStaffPersonAdded
   * @return the ParticipantResourceBuilder
   */
  public ParticipantResourceBuilder setClientStaffPersonAdded(boolean clientStaffPersonAdded) {
    this.clientStaffPersonAdded = clientStaffPersonAdded;
    return this;
  }

  /**
   * @param sensitivityIndicator - sensitivityIndicator
   * @return the ParticipantResourceBuilder
   */
  public ParticipantResourceBuilder setSensitivityIndicator(String sensitivityIndicator) {
    this.sensitivityIndicator = sensitivityIndicator;
    return this;
  }


  /**
   * @param roles - roles
   * @return the ParticipantResourceBuilder
   */
  public ParticipantResourceBuilder setRoles(Set<String> roles) {
    this.roles = roles;
    return this;
  }

  /**
   * @param addresses - addresses
   * @return the ParticipantResourceBuilder
   */
  public ParticipantResourceBuilder setAddresses(
      Set<gov.ca.cwds.rest.api.domain.Address> addresses) {
    this.addresses = addresses;
    return this;
  }


  public ParticipantResourceBuilder() {
    this.roles = new HashSet<String>(Arrays.asList("Victim"));

    gov.ca.cwds.rest.api.domain.Address address = new AddressResourceBuilder()
        .setStreetAddress("123 First St").setCity("San Jose").setZip("94321").createAddress();
    this.addresses = new HashSet<>(Arrays.asList(address));

  }

  /**
   * @return the Victim Participant
   */
  public Participant createVictimParticipant() {
    this.roles = new HashSet<String>(Arrays.asList("Victim"));
    return createParticipant();
  }

  /**
   * @return the Perp Participant
   */
  public Participant createPerpParticipant() {
    this.roles = new HashSet<String>(Arrays.asList("Perpetrator"));
    return createParticipant();
  }

  /**
   * @return the Reporter Participant
   */
  public Participant createReporterParticipant() {
    // reporter only allows first initial
    this.middleName = middleName.substring(0, 1);
    this.roles = new HashSet<String>(Arrays.asList("Non-mandated Reporter"));
    return createParticipant();
  }

  /**
   * @return teh raceAndEthnicity
   */
  public RaceAndEthnicity getRaceAndEthnicity() {
    return raceAndEthnicity;
  }

  /**
   * @param raceAndEthnicity the race and ethnicity
   * @return teh raceAndEthnicity
   */
  public ParticipantResourceBuilder setRaceAndEthnicity(RaceAndEthnicity raceAndEthnicity) {
    this.raceAndEthnicity = raceAndEthnicity;
    return this;
  }

  /**
   * @return the Participant
   */
  public Participant createParticipant() {
    return new Participant(id, legacySourceTable, legacyId, legacyDescriptor, firstName, middleName,
        lastName, suffix, gender, ssn, dateOfBirth, primaryLanguage, secondaryLanguage, personId,
        screeningId, reporterConfidentialWaiver, reporterEmployerName, clientStaffPersonAdded,
        sensitivityIndicator, roles, addresses, raceAndEthnicity);
  }
}
