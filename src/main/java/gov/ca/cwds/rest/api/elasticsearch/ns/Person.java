package gov.ca.cwds.rest.api.elasticsearch.ns;


/**
 * {@link NsElasticsearchObject} representing a Person.
 * 
 * @author CWDS API Team
 */
public class Person extends NsElasticsearchObject {

  private String id;
  private String first_name;
  private String last_name;
  private String gender;
  private String date_of_birth;
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
    this.first_name = firstName;
    this.last_name = lastName;
    this.gender = gender;
    this.date_of_birth = dateOfBirth;
    this.ssn = ssn;
    this.type = type;
    this.source = source;
  }

  /**
   * Constructor
   * 
   * @param person The domain object to construct this object from
   * @param updatedAt The time of the last update of this object
   */
  // public Person(gov.ca.cwds.rest.api.domain.Person person, String updatedAt) {
  // super(updatedAt);
  // this.first_name = person.getFirst_name();
  // this.last_name = person.getLast_name();
  // this.gender = person.getGender();
  // this.date_of_birth = person.getDate_of_birth();
  // this.ssn = person.getSsn();
  // }

  /**
   * @return the id
   */
  public String getId() {
    return id;
  }

  /**
   * @return the first_name
   */
  public String getFirst_name() {
    return first_name;
  }

  /**
   * @return the last_name
   */
  public String getLast_name() {
    return last_name;
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
  public String getDate_of_birth() {
    return date_of_birth;
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
