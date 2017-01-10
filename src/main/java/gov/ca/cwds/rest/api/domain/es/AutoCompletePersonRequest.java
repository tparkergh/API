package gov.ca.cwds.rest.api.domain.es;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.rest.api.Request;
import io.dropwizard.jackson.JsonSnakeCase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * A domain API {@link Request} for Intake Person Auto-complete feature to Elasticsearch.
 * 
 * <p>
 * The Intake Auto-complete for Person takes a single search term, which is used to query
 * Elasticsearch Person documents by ALL relevant fields.
 * </p>
 * 
 * <p>
 * For example, search strings consisting of only digits could be phone numbers, social security
 * numbers, or street address numbers. Search strings consisting of only letters could be last name,
 * first name, city, state, language, and so forth.
 * </p>
 * 
 * @author CWDS API Team
 */
@ApiModel
@JsonSnakeCase
public final class AutoCompletePersonRequest implements Serializable, Request {

  /**
   * Base serialization version. Increment by class version.
   */
  private static final long serialVersionUID = 1L;

  @ApiModelProperty(required = true, readOnly = false, example = "john")
  @JsonProperty("search_term")
  private String searchTerm;

  /**
   * JSON DropWizard Constructor. Takes solitary search term.
   * 
   * @param searchTerm String search term.
   */
  @JsonCreator
  public AutoCompletePersonRequest(@NotNull @JsonProperty("search_term") String searchTerm) {
    this.searchTerm = searchTerm;
  }

  /**
   * Getter for auto-complete search term(s).
   * 
   * @return search term
   */
  public String getSearchTerm() {
    return searchTerm;
  }

  /**
   * Setter for auto-complete search term.
   * 
   * @param searchTerm search term
   */
  public void setSearchTerm(String searchTerm) {
    this.searchTerm = searchTerm;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((searchTerm == null) ? 0 : searchTerm.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    AutoCompletePersonRequest other = (AutoCompletePersonRequest) obj;
    if (searchTerm == null) {
      if (other.searchTerm != null)
        return false;
    } else if (!searchTerm.equals(other.searchTerm))
      return false;
    return true;
  }

}
