package gov.ca.cwds.fixture.investigation;

import gov.ca.cwds.rest.api.domain.investigation.SimplePerson;

@SuppressWarnings("javadoc")
public class SimplePersonEntityBuilder {

  private String lastName = "Smyth";
  private String firstName = "Susan";
  private String sensitivityIndicator = "N";

  public SimplePerson build() {
    return new SimplePerson(lastName, firstName, sensitivityIndicator);
  }

  public SimplePersonEntityBuilder setLastName(String lastName) {
    this.lastName = lastName;
    return this;
  }

  public SimplePersonEntityBuilder setFirstName(String firstName) {
    this.firstName = firstName;
    return this;
  }

  public SimplePersonEntityBuilder setSensitivityIndicator(String sensitivityIndicator) {
    this.sensitivityIndicator = sensitivityIndicator;
    return this;
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
