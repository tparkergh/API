package gov.ca.cwds.rest.api.domain.es;

import java.io.Serializable;

import gov.ca.cwds.rest.api.Response;

public class ESPersons implements Response, Serializable {

  private final ESPerson[] persons;

  private ESPersons() {
    // Prohibit access to default constructor, since the person array must be populated.
    this.persons = new ESPerson[0];
  }

  public ESPersons(ESPerson[] persons) {
    this.persons = persons;
  }

  public final ESPerson[] getPersons() {
    return persons;
  }

}
