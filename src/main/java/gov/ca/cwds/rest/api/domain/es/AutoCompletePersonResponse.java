package gov.ca.cwds.rest.api.domain.es;

import java.io.Serializable;

import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import io.dropwizard.jackson.JsonSnakeCase;
import io.swagger.annotations.ApiModel;

/**
 * A domain API {@link Request} for Intake Person Auto-complete feature to Elasticsearch.
 * 
 * <p>
 * The Intake Auto-complete for Person takes a single search term, which is used to query
 * Elasticsearch Person documents by ALL relevant fields. For example, search strings consisting of
 * only digits could be phone numbers, social security numbers, or street address numbers. Search
 * strings consisting only of letters could be last name, first name, city, state, language, and so
 * forth.
 * </p>
 * 
 * @author CWDS API Team
 */
@ApiModel
@JsonSnakeCase
public class AutoCompletePersonResponse implements Serializable, Response {

  /**
   * Base version. Increment by class version.
   */
  private static final long serialVersionUID = 1L;

  private AutoCompletePerson[] persons;

  /**
   * Disallow calls to default constructor.
   */
  @SuppressWarnings("unused")
  private AutoCompletePersonResponse() {
    // Default, no-op.
  }

  /**
   * Preferred constructor. Build from person array.
   * 
   * @param persons array of {@link AutoCompletePerson}
   */
  public AutoCompletePersonResponse(AutoCompletePerson[] persons) {
    this.persons = persons;
  }

  /**
   * Getter for array of {@link AutoCompletePerson persons}
   * 
   * @return persons objects suitable for Intake Auto-complete
   */
  public AutoCompletePerson[] getPersons() {
    return persons;
  }

  /**
   * Setter for array of {@link AutoCompletePerson persons}
   * 
   * @param persons person objects suitable for Intake Auto-complete
   */
  public void setPersons(AutoCompletePerson[] persons) {
    this.persons = persons;
  }

}
