package gov.ca.cwds.rest.api.domain;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonValue;

import gov.ca.cwds.data.std.ApiMarker;
import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import io.dropwizard.jackson.JsonSnakeCase;
import io.swagger.annotations.ApiModel;

/**
 * A domain API {@link Request} for Intake LOV listings.
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
public class IntakeLovResponse implements Response, ApiMarker {

  /**
   * Base serialization version. Increment by class version.
   */
  private static final long serialVersionUID = 1L;

  @JsonIgnore
  private List<IntakeLov> lovs = new ArrayList<>();

  /**
   * Disallow use of default constructor.
   */
  @SuppressWarnings("unused")
  private IntakeLovResponse() {
    // Default, no-op.
  }

  /**
   * Preferred constructor. Build from person array.
   * 
   * @param lovs array of {@link IntakeLov}
   */
  public IntakeLovResponse(List<IntakeLov> lovs) {
    this.lovs = lovs;
  }

  /**
   * Getter for array of {@link IntakeLov lovs}
   * 
   * @return lovs objects suitable for Intake Auto-complete
   */
  @JsonValue
  public List<IntakeLov> getPersons() {
    return lovs;
  }

  /**
   * Setter for array of {@link IntakeLov lovs}.
   * 
   * @param lovs person objects suitable for Intake LOV
   */
  public void setPersons(List<IntakeLov> lovs) {
    this.lovs = lovs;
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
