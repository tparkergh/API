package gov.ca.cwds.fixture;

import gov.ca.cwds.rest.api.domain.Participant;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ParticipantResourceBuilder {

  long id = 5432;
  String legacySourceTable = "";
  String legacyId = "";
  long personId = 12345;
  long screeningId = 12345;
  String firstName = "John";
  String lastName = "Smith";
  String gender = "male";
  String dateOfBirth = "2001-03-15";
  String ssn = "123456789";
  Set<String> roles;
  Set<gov.ca.cwds.rest.api.domain.Address> addresses;

  public ParticipantResourceBuilder setId(long id) {
    this.id = id;
    return this;
  }

  public ParticipantResourceBuilder setLegacySourceTable(String legacySourceTable) {
    this.legacySourceTable = legacySourceTable;
    return this;
  }

  public ParticipantResourceBuilder setLegacyId(String legacyId) {
    this.legacyId = legacyId;
    return this;
  }

  public ParticipantResourceBuilder setPersonId(long personId) {
    this.personId = personId;
    return this;
  }

  public ParticipantResourceBuilder setScreeningId(long screeningId) {
    this.screeningId = screeningId;
    return this;
  }

  public ParticipantResourceBuilder setFirstName(String firstName) {
    this.firstName = firstName;
    return this;
  }

  public ParticipantResourceBuilder setLastName(String lastName) {
    this.lastName = lastName;
    return this;
  }

  public ParticipantResourceBuilder setGender(String gender) {
    this.gender = gender;
    return this;
  }

  public ParticipantResourceBuilder setDateOfBirth(String dateOfBirth) {
    this.dateOfBirth = dateOfBirth;
    return this;
  }

  public ParticipantResourceBuilder setSsn(String ssn) {
    this.ssn = ssn;
    return this;
  }

  public ParticipantResourceBuilder setRoles(Set<String> roles) {
    this.roles = roles;
    return this;
  }

  public ParticipantResourceBuilder setAddresses(
      Set<gov.ca.cwds.rest.api.domain.Address> addresses) {
    this.addresses = addresses;
    return this;
  }

  public ParticipantResourceBuilder() {
    this.roles = new HashSet<String>(Arrays.asList("Victim"));

    gov.ca.cwds.rest.api.domain.Address address = new AddressResourceBuilder()
        .setStreetAddress("123 First St")
        .setCity("San Jose")
        .setZip(94321)
        .createAddress();
    this.addresses = new HashSet(Arrays.asList(address));

  }

  public Participant createVictimParticipant() {
    this.roles = new HashSet<String>(Arrays.asList("Victim"));
    return createParticipant();
  }

  public Participant createPerpParticipant() {
    this.roles = new HashSet<String>(Arrays.asList("Perpetrator"));
    return createParticipant();
  }

  public Participant createReporterParticipant() {
    this.roles = new HashSet<String>(Arrays.asList("Non-mandated Reporter"));
    return createParticipant();

  }
  public Participant createParticipant() {
    return new Participant(id, legacySourceTable, legacyId, firstName, lastName, gender, ssn,
        dateOfBirth, personId, screeningId, roles, addresses);
  }
}
