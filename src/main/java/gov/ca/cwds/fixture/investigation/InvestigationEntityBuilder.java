package gov.ca.cwds.fixture.investigation;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import org.joda.time.DateTime;

import gov.ca.cwds.rest.api.domain.LegacyDescriptor;
import gov.ca.cwds.rest.api.domain.investigation.Allegation;
import gov.ca.cwds.rest.api.domain.investigation.Assignee;
import gov.ca.cwds.rest.api.domain.investigation.HistoryOfInvolvement;
import gov.ca.cwds.rest.api.domain.investigation.Investigation;
import gov.ca.cwds.rest.api.domain.investigation.InvestigationAddress;
import gov.ca.cwds.rest.api.domain.investigation.LimitedAccess;
import gov.ca.cwds.rest.api.domain.investigation.Person;
import gov.ca.cwds.rest.api.domain.investigation.PhoneNumber;
import gov.ca.cwds.rest.api.domain.investigation.Relationship;
import gov.ca.cwds.rest.api.domain.investigation.SimpleScreening;

@SuppressWarnings("javadoc")
public class InvestigationEntityBuilder {

  private String tableName = "REFERL_T";

  private String id = "1234567ABC";

  private String lastUpdatedBy = "OX5";

  private DateTime lastUpdatedAt = new DateTime("2016-08-03T01:00:00.000Z");

  private String incidentCounty = "20";
  private String incidentDate = "2017-08-20";
  private String locationType = "Home";
  private Short communicationMethod = 408;
  private String name = "The test invetigation";
  private String reportNarrative = "Summary of an investigation would appear here.";
  private String reference = "REF-TEST";
  private Short responseTime = 1518;
  private String startedAt = "2017-08-10";
  private String additionalInformation = "Additional information about the investigation.";
  private Boolean sensitive = Boolean.FALSE;
  private Boolean sealed = Boolean.FALSE;
  private BigDecimal phone = new BigDecimal(4445555);
  private Integer phoneExtension = 1122;
  private DateTime now = new DateTime("2010-10-01T15:26:42.000-0700");

  private LegacyDescriptor legacyDescriptor =
      new LegacyDescriptor(id, "111-222-333-4444", now, tableName, "Referral");

  private Assignee assignee = new Assignee("CWS Staff", incidentCounty, "Madera CWS", "0X5");

  private Set<PhoneNumber> phoneNumbers = new HashSet<>();
  private PhoneNumber phoneNumber =
      new PhoneNumber(phone, phoneExtension, "Home", legacyDescriptor);

  private LimitedAccess limitedAccess = new LimitedAccess("N", "20");

  private InvestigationAddress address = new InvestigationAddressEntityBuilder().build();

  private SimpleScreening screening = new SimpleScreeningEntityBuilder().build();

  private HistoryOfInvolvement historyOfInvolvement =
      new HistoryOfInvolvementEntityBuilder().build();

  private Allegation allegation = new AllegationEntityBuilder().build();
  private Set<Allegation> allegations = new HashSet<>();

  private Person person = new PersonEntityBuilder().build();
  private Set<Person> people = new HashSet<>();

  private Relationship relationship = new RelationshipEntityBuilder().build();
  private Set<Relationship> relationships = new HashSet<>();

  public Investigation build() {
    allegations.add(allegation);
    people.add(person);
    phoneNumbers.add(phoneNumber);
    relationships.add(relationship);

    return new Investigation(legacyDescriptor, lastUpdatedBy, lastUpdatedAt, incidentCounty,
        incidentDate, locationType, communicationMethod, name, reportNarrative, reference,
        responseTime, startedAt, assignee, additionalInformation, Boolean.FALSE, Boolean.FALSE,
        phoneNumbers, address, screening, historyOfInvolvement, allegations, people, relationships);
  }

  public InvestigationEntityBuilder setLegacyDescriptor(LegacyDescriptor legacyDescriptor) {
    this.legacyDescriptor = legacyDescriptor;
    return this;
  }

  public InvestigationEntityBuilder setLastUpdatedBy(String lastUpdatedBy) {
    this.lastUpdatedBy = lastUpdatedBy;
    return this;
  }

  public InvestigationEntityBuilder setLastUpdatedAt(DateTime lastUpdatedAt) {
    this.lastUpdatedAt = lastUpdatedAt;
    return this;
  }

  public InvestigationEntityBuilder setIncidentCounty(String incidentCounty) {
    this.incidentCounty = incidentCounty;
    return this;
  }

  public InvestigationEntityBuilder setIncidentDate(String incidentDate) {
    this.incidentDate = incidentDate;
    return this;
  }

  public InvestigationEntityBuilder setLocationType(String locationType) {
    this.locationType = locationType;
    return this;
  }

  public InvestigationEntityBuilder setCommunicationMethod(Short communicationMethod) {
    this.communicationMethod = communicationMethod;
    return this;
  }

  public InvestigationEntityBuilder setName(String name) {
    this.name = name;
    return this;
  }

  public InvestigationEntityBuilder setReportNarrative(String reportNarrative) {
    this.reportNarrative = reportNarrative;
    return this;
  }

  public InvestigationEntityBuilder setReference(String reference) {
    this.reference = reference;
    return this;
  }

  public InvestigationEntityBuilder setResponseTime(Short responseTime) {
    this.responseTime = responseTime;
    return this;
  }

  public InvestigationEntityBuilder setStartedAt(String startedAt) {
    this.startedAt = startedAt;
    return this;
  }

  public InvestigationEntityBuilder setAssignee(Assignee assignee) {
    this.assignee = assignee;
    return this;
  }


  public InvestigationEntityBuilder setAdditionalInformation(String additionalInformatioin) {
    this.additionalInformation = additionalInformatioin;
    return this;
  }

  public InvestigationEntityBuilder setSensitive(Boolean sensitive) {
    this.sensitive = sensitive;
    return this;
  }

  public InvestigationEntityBuilder setSealed(Boolean sealed) {
    this.sealed = sealed;
    return this;
  }

  public InvestigationEntityBuilder setPhoneNumbers(Set<PhoneNumber> phoneNumbers) {
    this.phoneNumbers = phoneNumbers;
    return this;
  }

  public InvestigationEntityBuilder setAddress(InvestigationAddress address) {
    this.address = address;
    return this;
  }

  public InvestigationEntityBuilder setScreening(SimpleScreening screening) {
    this.screening = screening;
    return this;
  }

  public InvestigationEntityBuilder setHistoryOfInvolvement(
      HistoryOfInvolvement historyOfInvolvement) {
    this.historyOfInvolvement = historyOfInvolvement;
    return this;
  }

  public InvestigationEntityBuilder setAllegations(Set<Allegation> allegations) {

    this.allegations = allegations;
    return this;
  }

  public InvestigationEntityBuilder setPeople(Set<Person> people) {
    this.people = people;
    return this;
  }

  public InvestigationEntityBuilder setRelaitonships(Set<Relationship> relationships) {
    this.relationships = relationships;
    return this;
  }

  public String getLastUpdatedBy() {
    return lastUpdatedBy;
  }

  public DateTime getLastUpdatedAt() {
    return lastUpdatedAt;
  }

  public String getIncidentCounty() {
    return incidentCounty;
  }

  public String getIncidentDate() {
    return incidentDate;
  }

  public String getLocationType() {
    return locationType;
  }

  public Short getCommunicationMethod() {
    return communicationMethod;
  }

  public String getName() {
    return name;
  }

  public String getReportNarrative() {
    return reportNarrative;
  }

  public String getReference() {
    return reference;
  }

  public Short getResponseTime() {
    return responseTime;
  }

  public String getStartedAt() {
    return startedAt;
  }

  public String getAdditionalInformation() {
    return additionalInformation;
  }

  public Boolean getSensitive() {
    return sensitive;
  }

  public Boolean getSealed() {
    return sealed;
  }

  public LegacyDescriptor getLegacyDescriptor() {
    return legacyDescriptor;
  }

  public Assignee getAssignee() {
    return assignee;
  }

  public Set<PhoneNumber> getPhoneNumbers() {
    return phoneNumbers;
  }

  public InvestigationAddress getAddress() {
    return address;
  }

  public SimpleScreening getScreening() {
    return screening;
  }

  public HistoryOfInvolvement getHistoryOfInvolvement() {
    return historyOfInvolvement;
  }

  public Set<Allegation> getAllegations() {
    return allegations;
  }

  public Set<Person> getPeople() {
    return people;
  }

  public Set<Relationship> getRelationships() {
    return relationships;
  }
}
