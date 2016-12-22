package gov.ca.cwds.rest.api.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.rest.api.Response;

/**
 * {@link Response} adding an id to the {@link Person}.
 * 
 * @author CWDS API Team
 */
public class PostedPerson extends Person {

  @JsonProperty("id")
  private long id;

  public PostedPerson(long id, String first_name, String last_name, String gender,
      String date_of_birth, String ssn, Address address) {
    super(first_name, last_name, gender, date_of_birth, ssn, address);
    this.id = id;
  }

  public PostedPerson(gov.ca.cwds.rest.api.persistence.ns.Person person) {
    super(person);
    this.id = person.getId();
  }

  /**
   * @return the id
   */
  public long getId() {
    return id;
  }

}
