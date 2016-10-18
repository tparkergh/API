package gov.ca.cwds.rest.api.domain;

import gov.ca.cwds.rest.api.Response;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * {@link Response} adding an id to the {@link Person}
 * 
 * @author CWDS API Team
 */
public class PersonCreated extends Person {
  @JsonProperty("id")
  private long id;

  public PersonCreated(long id, String first_name, String last_name, String gender,
      String date_of_birth, String ssn, Address address) {
    super(first_name, last_name, gender, date_of_birth, ssn, address);
    this.id = id;
  }

  public PersonCreated(gov.ca.cwds.rest.api.persistence.ns.Person person,
      gov.ca.cwds.rest.api.persistence.ns.Address address) {
    super(person, address);
    this.id = person.getId();
  }


}
