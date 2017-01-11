package gov.ca.cwds.rest.api.domain.es;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonValue;

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
   * Base serialization version. Increment by class version.
   */
  private static final long serialVersionUID = 1L;

  @JsonIgnore
  private List<AutoCompletePerson> persons = new ArrayList<>();

  /**
   * Disallow use of default constructor.
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
  public AutoCompletePersonResponse(List<AutoCompletePerson> persons) {
    this.persons = persons;
  }

  /**
   * Getter for array of {@link AutoCompletePerson persons}
   * 
   * @return persons objects suitable for Intake Auto-complete
   */
  @JsonValue
  public List<AutoCompletePerson> getPersons() {
    return persons;
  }

  /**
   * Setter for array of {@link AutoCompletePerson persons}
   * 
   * @param persons person objects suitable for Intake Auto-complete
   */
  public void setPersons(List<AutoCompletePerson> persons) {
    this.persons = persons;
  }

  @Override
  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this, false);
  }

  @Override
  public boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj, false);
  }

}
