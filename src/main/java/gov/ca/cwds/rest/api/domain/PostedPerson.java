package gov.ca.cwds.rest.api.domain;

import java.util.Set;

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

  @SuppressWarnings("javadoc")
  public PostedPerson(long id, String firstName, String lastName, String gender, String dateOfBirth,
      String ssn, Set<Address> address, Set<PhoneNumber> phoneNumber) {
    super(firstName, lastName, gender, dateOfBirth, ssn, address, phoneNumber);
    this.id = id;
  }

  @SuppressWarnings("javadoc")
  public PostedPerson(gov.ca.cwds.data.persistence.ns.Person person) {
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
