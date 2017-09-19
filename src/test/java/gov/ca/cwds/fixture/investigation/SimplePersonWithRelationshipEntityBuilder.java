package gov.ca.cwds.fixture.investigation;

import gov.ca.cwds.rest.api.domain.investigation.SimplePersonWithRelationship;

@SuppressWarnings("javadoc")
public class SimplePersonWithRelationshipEntityBuilder {
  private String relationship = "Father/Son";
  private String lastName = "Plimsoul";
  private String firstName = "Paul";
  private String sensitivityIndicator = "N";

  public SimplePersonWithRelationship build() {
    return new SimplePersonWithRelationship(relationship, lastName, firstName,
        sensitivityIndicator);
  }

  public SimplePersonWithRelationshipEntityBuilder setRelationship(String relationship) {
    this.relationship = relationship;
    return this;
  }

  public SimplePersonWithRelationshipEntityBuilder setLastName(String lastName) {
    this.lastName = lastName;
    return this;
  }

  public SimplePersonWithRelationshipEntityBuilder setFirstName(String firstName) {
    this.firstName = firstName;
    return this;
  }

  public SimplePersonWithRelationshipEntityBuilder setSensitivityIndicator(
      String sensitivityIndicator) {
    this.sensitivityIndicator = sensitivityIndicator;
    return this;
  }

  public String getRelationship() {
    return relationship;
  }

  public String getLastName() {
    return lastName;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getSensitivityIndicator() {
    return sensitivityIndicator;
  }


}
