package gov.ca.cwds.fixture.investigation;

import org.joda.time.DateTime;

import gov.ca.cwds.rest.api.domain.LegacyDescriptor;
import gov.ca.cwds.rest.api.domain.investigation.RelationshipTo;

@SuppressWarnings("javadoc")
public class RelationshipToEntityBuilder {
  private String tableName = "CLIENT_T";
  private String id = "2345678ABC";
  private String relatedFirstName = "Steve";
  private String relatedLastName = "Briggs";
  private String relationship = "Brother";
  private String relationshipToPerson = "Sister";
  private String relationshipContext = "";
  private DateTime now = new DateTime();

  private LegacyDescriptor legacyDescriptor =
      new LegacyDescriptor(id, "111-222-333-4444", now, tableName, "Client");

  public RelationshipTo build() {
    return new RelationshipTo(relatedFirstName, relatedLastName, relationship, relationshipContext,
        relationshipToPerson, legacyDescriptor);
  }

  public RelationshipToEntityBuilder setRelatedFirstName(String relatedFirstName) {
    this.relatedFirstName = relatedFirstName;
    return this;
  }

  public RelationshipToEntityBuilder setRelatedLastName(String relatedLastName) {
    this.relatedLastName = relatedLastName;
    return this;
  }

  public RelationshipToEntityBuilder setrelationshipToPerson(String relationshipToPerson) {
    this.relationshipToPerson = relationshipToPerson;
    return this;
  }

  public RelationshipToEntityBuilder setRelationshipContext(String relationshipContext) {
    this.relationshipContext = relationshipContext;
    return this;
  }

  public RelationshipToEntityBuilder setRelationshipToPerson(String relationshipToPerson) {
    this.relationshipToPerson = relationshipToPerson;
    return this;
  }

  public RelationshipToEntityBuilder setLegacyDescriptor(LegacyDescriptor legacyDescriptor) {
    this.legacyDescriptor = legacyDescriptor;
    return this;
  }

  public String getRelatedFirstName() {
    return relatedFirstName;
  }

  public String getRelatedLastName() {
    return relatedLastName;
  }

  public String getRelationshipToPerson() {
    return relationshipToPerson;
  }

  public String getRelationshipContext() {
    return relationshipContext;
  }

  public LegacyDescriptor getLegacyDescriptor() {
    return legacyDescriptor;
  }


}