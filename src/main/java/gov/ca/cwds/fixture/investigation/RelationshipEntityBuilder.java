package gov.ca.cwds.fixture.investigation;

import java.util.HashSet;
import java.util.Set;

import org.joda.time.DateTime;

import gov.ca.cwds.rest.api.domain.LegacyDescriptor;
import gov.ca.cwds.rest.api.domain.investigation.Relationship;
import gov.ca.cwds.rest.api.domain.investigation.RelationshipTo;

@SuppressWarnings("javadoc")
public class RelationshipEntityBuilder {

  protected String tableName = "CLIENT_T";
  protected String id = "1234567ABC";
  private DateTime now = new DateTime("2010-10-01T15:26:42.000-0700");

  private LegacyDescriptor legacyDescriptor =
      new LegacyDescriptor(id, "111-222-333-4444", now, tableName, "Client");
  protected String firstName = "Jackson";
  protected String middleName = "R";
  protected String lastName = "Greene";
  protected String dateOfBirth = "2000-10-01";
  protected String suffixTitle = "";
  protected Boolean sensitive = Boolean.FALSE;
  protected Boolean sealed = Boolean.FALSE;
  private RelationshipTo relationshipTo = new RelationshipToEntityBuilder().build();
  private Set<RelationshipTo> relationshipsTo = new HashSet<>();

  public Relationship build() {
    relationshipsTo.add(relationshipTo);
    return new Relationship(id, dateOfBirth, firstName, middleName, lastName, suffixTitle,
        sensitive, sealed, legacyDescriptor, relationshipsTo);

  }

  public RelationshipEntityBuilder setTableName(String tableName) {
    this.tableName = tableName;
    return this;
  }

  public RelationshipEntityBuilder setId(String id) {
    this.id = id;
    return this;
  }

  public RelationshipEntityBuilder setDateOfBirth(String dateOfBirth) {
    this.dateOfBirth = dateOfBirth;
    return this;
  }

  public RelationshipEntityBuilder setFirstName(String firstName) {
    this.firstName = firstName;
    return this;
  }

  public RelationshipEntityBuilder setMiddleName(String middleName) {
    this.middleName = middleName;
    return this;
  }

  public RelationshipEntityBuilder setLastName(String lastName) {
    this.lastName = lastName;
    return this;
  }

  public RelationshipEntityBuilder setSuffixTitle(String suffixTitle) {
    this.suffixTitle = suffixTitle;
    return this;
  }

  public RelationshipEntityBuilder setSensitive(Boolean sensitive) {
    this.sensitive = sensitive;
    return this;
  }

  public RelationshipEntityBuilder setSealed(Boolean sealed) {
    this.sealed = sealed;
    return this;
  }

  public RelationshipEntityBuilder setRelationShipsTo(Set<RelationshipTo> relationshipsTo) {
    this.relationshipsTo = relationshipsTo;
    return this;
  }

  public String getTableName() {
    return tableName;
  }

  public String getId() {
    return id;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getMiddleName() {
    return middleName;
  }

  public String getLastName() {
    return lastName;
  }

  public String getSuffixTitle() {
    return suffixTitle;
  }

  public Set<RelationshipTo> getRelationshipsTo() {
    return relationshipsTo;
  }



}
