package gov.ca.cwds.rest.api.domain.es;

import gov.ca.cwds.data.es.NsElasticsearchObject;

/**
 * {@link NsElasticsearchObject} representing a Person.
 * 
 * @author CWDS API Team
 */
public class Person extends NsElasticsearchObject {

  private String id;
  private String firstName;
  private String lastName;
  private String gender;
  private String dateOfBirth;
  private String ssn;
  private String type;
  private String source;

  /**
   * Default constructor
   */
  public Person() {
    super();
  }

  /**
   * Constructor
   * 
   * @param id The id
   * @param firstName The first name
   * @param lastName The last name
   * @param gender The gender
   * @param dateOfBirth The date of birth
   * @param ssn the social security number
   * @param type the type of the person
   * @param source the JSON of the original object
   */
  public Person(String id, String firstName, String lastName, String gender, String dateOfBirth,
      String ssn, String type, String source) {
    super();
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.gender = gender;
    this.dateOfBirth = dateOfBirth;
    this.ssn = ssn;
    this.type = type;
    this.source = source;
  }

  /**
   * @return the unique identifier
   */
  public String getId() {
    return id;
  }

  /**
   * @return the first_name
   */
  public String getFirstName() {
    return firstName;
  }

  /**
   * @return the last_name
   */
  public String getLastName() {
    return lastName;
  }

  /**
   * @return the gender
   */
  public String getGender() {
    return gender;
  }

  /**
   * @return the date_of_birth
   */
  public String getDateOfBirth() {
    return dateOfBirth;
  }

  /**
   * @return the ssn
   */
  public String getSsn() {
    return ssn;
  }

  /**
   * @return the type
   */
  public String getType() {
    return type;
  }

  /**
   * @return the source
   */
  public String getSource() {
    return source;
  }

}
