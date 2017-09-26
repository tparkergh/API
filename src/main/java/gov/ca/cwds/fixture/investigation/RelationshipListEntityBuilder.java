package gov.ca.cwds.fixture.investigation;

import java.util.HashSet;
import java.util.Set;

import gov.ca.cwds.rest.api.domain.investigation.Relationship;
import gov.ca.cwds.rest.api.domain.investigation.RelationshipList;

@SuppressWarnings("javadoc")
public class RelationshipListEntityBuilder {

  private Relationship relationship = new RelationshipEntityBuilder().build();
  private Set<Relationship> relationships = new HashSet<>();

  public RelationshipList build() {
    relationships.add(relationship);
    return new RelationshipList(relationships);
  }

  public RelationshipListEntityBuilder setRelationships(Set<Relationship> relationships) {
    this.relationships = relationships;
    return this;
  }

  public Set<Relationship> getRelationships() {
    return relationships;
  }
}
