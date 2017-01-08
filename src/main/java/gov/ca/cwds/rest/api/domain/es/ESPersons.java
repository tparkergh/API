package gov.ca.cwds.rest.api.domain.es;

import java.io.Serializable;

import gov.ca.cwds.rest.api.Response;

/**
 * API {@link Response} domain object, contains an array of ElasticSearch person results,
 * {@link ESPerson}.
 * 
 * @author CWDS API Team
 * @see ESPerson
 */
public class ESPersons implements Response, Serializable {

  /**
   * Default serial id.
   */
  private static final long serialVersionUID = 1L;

  private final ESPerson[] persons;

  /**
   * Disallow use of the default constructor.
   */
  @SuppressWarnings("unused")
  private ESPersons() {
    // Prohibit access to default constructor, since the person array must be populated.
    this.persons = new ESPerson[0];
  }

  /**
   * Constructor. Sets immutable array of {@link ESPerson}.
   * 
   * @param persons array of {@link ESPerson}.
   */
  public ESPersons(ESPerson[] persons) {
    this.persons = persons;
  }

  /**
   * Getter for immutable array of {@link ESPerson}.
   * 
   * @return immutable array of {@link ESPerson}
   */
  public final ESPerson[] getPersons() {
    return persons;
  }

}
