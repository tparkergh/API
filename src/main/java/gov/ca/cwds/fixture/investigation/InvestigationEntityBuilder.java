package gov.ca.cwds.fixture.investigation;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import org.joda.time.DateTime;

import gov.ca.cwds.rest.api.domain.investigation.Allegation;
import gov.ca.cwds.rest.api.domain.investigation.Assignee;
import gov.ca.cwds.rest.api.domain.investigation.CmsRecordDescriptor;
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

  String tableName = "REFERL_T";

  String id = "1234567ABC";

  String lastUpdatedBy = "OX5";

  DateTime lastUpdatedAt = new DateTime("2016-08-03T01:00:00.000Z");

  String incidentCounty = "20";
  String incidentDate = "2017-08-20";
  String locationType = "Home";
  Short communicationMethod = 408;
  String name = "The test invetigation";
  String reportNarrative = "Summary of an investigation would appear here.";
  String reference = "REF-TEST";
  Short responseTime = 1518;
  String startedAt = "2017-08-10";
  String additionalInformation = "Additional information about the investigation.";
  Boolean sensitive = Boolean.FALSE;
  Boolean sealed = Boolean.FALSE;
  BigDecimal phone = new BigDecimal(4445555);
  Integer phoneExtension = 1122;
  DateTime now = new DateTime("2010-10-01T15:26:42.000-0700");
  Short phoneType = 1111;

  private CmsRecordDescriptor cmsRecordDescriptor =
      new CmsRecordDescriptor(id, "111-222-333-4444", now, tableName, "Referral");

  private Assignee assignee = new Assignee("CWS Staff", incidentCounty, "Madera CWS", "0X5");

  private Set<PhoneNumber> phoneNumbers = new HashSet<>();
  private PhoneNumber phoneNumber =
      new PhoneNumber(phone, phoneExtension, phoneType, cmsRecordDescriptor);

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

    return new Investigation(cmsRecordDescriptor, lastUpdatedBy, lastUpdatedAt, incidentCounty,
        incidentDate, locationType, communicationMethod, name, reportNarrative, reference,
        responseTime, startedAt, assignee, additionalInformation, Boolean.FALSE, Boolean.FALSE,
        phoneNumbers, address, screening, historyOfInvolvement, allegations, people, relationships);
  }

  public String getTableName() {
    return tableName;
  }

  public InvestigationEntityBuilder setTableName(String tableName) {
    this.tableName = tableName;
    return this;
  }

  public String getId() {
    return id;
  }

  public InvestigationEntityBuilder setId(String id) {
    this.id = id;
    return this;
  }

  public String getLastUpdatedBy() {
    return lastUpdatedBy;
  }

  public InvestigationEntityBuilder setLastUpdatedBy(String lastUpdatedBy) {
    this.lastUpdatedBy = lastUpdatedBy;
    return this;
  }

  public DateTime getLastUpdatedAt() {
    return lastUpdatedAt;
  }

  public InvestigationEntityBuilder setLastUpdatedAt(DateTime lastUpdatedAt) {
    this.lastUpdatedAt = lastUpdatedAt;
    return this;
  }

  public String getIncidentCounty() {
    return incidentCounty;
  }

  public InvestigationEntityBuilder setIncidentCounty(String incidentCounty) {
    this.incidentCounty = incidentCounty;
    return this;
  }

  public String getIncidentDate() {
    return incidentDate;
  }

  public InvestigationEntityBuilder setIncidentDate(String incidentDate) {
    this.incidentDate = incidentDate;
    return this;
  }

  public String getLocationType() {
    return locationType;
  }

  public InvestigationEntityBuilder setLocationType(String locationType) {
    this.locationType = locationType;
    return this;
  }

  public Short getCommunicationMethod() {
    return communicationMethod;
  }

  public InvestigationEntityBuilder setCommunicationMethod(Short communicationMethod) {
    this.communicationMethod = communicationMethod;
    return this;
  }

  public String getName() {
    return name;
  }

  public InvestigationEntityBuilder setName(String name) {
    this.name = name;
    return this;
  }

  public String getReportNarrative() {
    return reportNarrative;
  }

  public InvestigationEntityBuilder setReportNarrative(String reportNarrative) {
    this.reportNarrative = reportNarrative;
    return this;
  }

  public String getReference() {
    return reference;
  }

  public InvestigationEntityBuilder setReference(String reference) {
    this.reference = reference;
    return this;
  }

  public Short getResponseTime() {
    return responseTime;
  }

  public InvestigationEntityBuilder setResponseTime(Short responseTime) {
    this.responseTime = responseTime;
    return this;
  }

  public String getStartedAt() {
    return startedAt;
  }

  public InvestigationEntityBuilder setStartedAt(String startedAt) {
    this.startedAt = startedAt;
    return this;
  }

  public String getAdditionalInformation() {
    return additionalInformation;
  }

  public InvestigationEntityBuilder setAdditionalInformation(String additionalInformation) {
    this.additionalInformation = additionalInformation;
    return this;
  }

  public Boolean getSensitive() {
    return sensitive;
  }

  public InvestigationEntityBuilder setSensitive(Boolean sensitive) {
    this.sensitive = sensitive;
    return this;
  }

  public Boolean getSealed() {
    return sealed;
  }

  public InvestigationEntityBuilder setSealed(Boolean sealed) {
    this.sealed = sealed;
    return this;
  }

  public BigDecimal getPhone() {
    return phone;
  }

  public InvestigationEntityBuilder setPhone(BigDecimal phone) {
    this.phone = phone;
    return this;
  }

  public Integer getPhoneExtension() {
    return phoneExtension;
  }

  public InvestigationEntityBuilder setPhoneExtension(Integer phoneExtension) {
    this.phoneExtension = phoneExtension;
    return this;
  }

  public DateTime getNow() {
    return now;
  }

  public InvestigationEntityBuilder setNow(DateTime now) {
    this.now = now;
    return this;
  }

  public Short getPhoneType() {
    return phoneType;
  }

  public InvestigationEntityBuilder setPhoneType(Short phoneType) {
    this.phoneType = phoneType;
    return this;
  }

  public CmsRecordDescriptor getCmsRecordDescriptor() {
    return cmsRecordDescriptor;
  }

  public InvestigationEntityBuilder setCmsRecordDescriptor(
      CmsRecordDescriptor cmsRecordDescriptor) {
    this.cmsRecordDescriptor = cmsRecordDescriptor;
    return this;
  }

  public Assignee getAssignee() {
    return assignee;
  }

  public InvestigationEntityBuilder setAssignee(Assignee assignee) {
    this.assignee = assignee;
    return this;
  }

  public Set<PhoneNumber> getPhoneNumbers() {
    return phoneNumbers;
  }

  public InvestigationEntityBuilder setPhoneNumbers(Set<PhoneNumber> phoneNumbers) {
    this.phoneNumbers = phoneNumbers;
    return this;
  }

  public PhoneNumber getPhoneNumber() {
    return phoneNumber;
  }

  public InvestigationEntityBuilder setPhoneNumber(PhoneNumber phoneNumber) {
    this.phoneNumber = phoneNumber;
    return this;
  }

  public LimitedAccess getLimitedAccess() {
    return limitedAccess;
  }

  public InvestigationEntityBuilder setLimitedAccess(LimitedAccess limitedAccess) {
    this.limitedAccess = limitedAccess;
    return this;
  }

  public InvestigationAddress getAddress() {
    return address;
  }

  public InvestigationEntityBuilder setAddress(InvestigationAddress address) {
    this.address = address;
    return this;
  }

  public SimpleScreening getScreening() {
    return screening;
  }

  public InvestigationEntityBuilder setScreening(SimpleScreening screening) {
    this.screening = screening;
    return this;
  }

  public HistoryOfInvolvement getHistoryOfInvolvement() {
    return historyOfInvolvement;
  }

  public InvestigationEntityBuilder setHistoryOfInvolvement(
      HistoryOfInvolvement historyOfInvolvement) {
    this.historyOfInvolvement = historyOfInvolvement;
    return this;
  }

  public Allegation getAllegation() {
    return allegation;
  }

  public InvestigationEntityBuilder setAllegation(Allegation allegation) {
    this.allegation = allegation;
    return this;
  }

  public Set<Allegation> getAllegations() {
    return allegations;
  }

  public InvestigationEntityBuilder setAllegations(Set<Allegation> allegations) {
    this.allegations = allegations;
    return this;
  }

  public Person getPerson() {
    return person;
  }

  public InvestigationEntityBuilder setPerson(Person person) {
    this.person = person;
    return this;
  }

  public Set<Person> getPeople() {
    return people;
  }

  public InvestigationEntityBuilder setPeople(Set<Person> people) {
    this.people = people;
    return this;
  }

  public Relationship getRelationship() {
    return relationship;
  }

  public InvestigationEntityBuilder setRelationship(Relationship relationship) {
    this.relationship = relationship;
    return this;
  }

  public Set<Relationship> getRelationships() {
    return relationships;
  }

  public InvestigationEntityBuilder setRelationships(Set<Relationship> relationships) {
    this.relationships = relationships;
    return this;
  }

}
