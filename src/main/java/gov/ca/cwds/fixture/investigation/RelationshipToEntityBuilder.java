package gov.ca.cwds.fixture.investigation;

import org.joda.time.DateTime;

import gov.ca.cwds.rest.api.domain.investigation.CmsRecordDescriptor;
import gov.ca.cwds.rest.api.domain.investigation.RelationshipTo;

@SuppressWarnings("javadoc")
public class RelationshipToEntityBuilder {
  private String tableName = "CLIENT_T";
  private String id = "2345678ABC";
  private String relatedFirstName = "Steve";
  private String relatedLastName = "Briggs";
  private String relationship = "Brother";
  private String relationshipToPerson = "Sister";
  private String relationshipContext = "step";
  private DateTime now = new DateTime("2010-10-01T15:26:42.000-0700");

  private CmsRecordDescriptor cmsRecordDescriptor =
      new CmsRecordDescriptor(id, "111-222-333-4444", now, tableName, "Client");

  public RelationshipTo build() {
    return new RelationshipTo(relatedFirstName, relatedLastName, relationship, relationshipContext,
        relationshipToPerson, cmsRecordDescriptor);
  }

  public String getTableName() {
    return tableName;
  }

  public RelationshipToEntityBuilder setTableName(String tableName) {
    this.tableName = tableName;
    return this;
  }

  public String getId() {
    return id;
  }

  public RelationshipToEntityBuilder setId(String id) {
    this.id = id;
    return this;
  }

  public String getRelatedFirstName() {
    return relatedFirstName;
  }

  public RelationshipToEntityBuilder setRelatedFirstName(String relatedFirstName) {
    this.relatedFirstName = relatedFirstName;
    return this;
  }

  public String getRelatedLastName() {
    return relatedLastName;
  }

  public RelationshipToEntityBuilder setRelatedLastName(String relatedLastName) {
    this.relatedLastName = relatedLastName;
    return this;
  }

  public String getRelationship() {
    return relationship;
  }

  public RelationshipToEntityBuilder setRelationship(String relationship) {
    this.relationship = relationship;
    return this;
  }

  public String getRelationshipToPerson() {
    return relationshipToPerson;
  }

  public RelationshipToEntityBuilder setRelationshipToPerson(String relationshipToPerson) {
    this.relationshipToPerson = relationshipToPerson;
    return this;
  }

  public String getRelationshipContext() {
    return relationshipContext;
  }

  public RelationshipToEntityBuilder setRelationshipContext(String relationshipContext) {
    this.relationshipContext = relationshipContext;
    return this;
  }

  public DateTime getNow() {
    return now;
  }

  public RelationshipToEntityBuilder setNow(DateTime now) {
    this.now = now;
    return this;
  }

  public CmsRecordDescriptor getCmsRecordDescriptor() {
    return cmsRecordDescriptor;
  }

  public RelationshipToEntityBuilder setCmsRecordDescriptor(
      CmsRecordDescriptor cmsRecordDescriptor) {
    this.cmsRecordDescriptor = cmsRecordDescriptor;
    return this;
  }

}
