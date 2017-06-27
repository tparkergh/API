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
  /**
   * Serialization version
   */
  private static final long serialVersionUID = 1L;
  @JsonProperty("id")
  private long id;

  @SuppressWarnings("javadoc")
  public PostedPerson(long id, String firstName, String middleName, String lastName, String nameSuffix, String gender, String dateOfBirth,
      String ssn, Set<Address> address, Set<PhoneNumber> phoneNumber, Set<Language> language,
      Set<Race> race, Set<Ethnicity> ethnicity) {
    super(firstName, middleName, lastName, nameSuffix, gender, dateOfBirth, ssn, address, phoneNumber, language, race,
        ethnicity);
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
