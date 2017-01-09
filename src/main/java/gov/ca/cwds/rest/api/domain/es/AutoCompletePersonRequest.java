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
 * <p>
 * By the nature of "auto-complete", wildcard searches ("*" or "?") are not allowed.
 * </p>
 * 
 * @author CWDS API Team
 */
@ApiModel
@JsonSnakeCase
public final class AutoCompletePersonRequest implements Serializable, Request {

  /**
   * Base version. Increment by class version.
   */
  private static final long serialVersionUID = 1L;

  @ApiModelProperty(required = true, readOnly = false, example = "john")
  @JsonProperty("search_criteria")
  private String searchCriteria;

  /**
   * JSON DropWizard Constructor. Takes solitary search term.
   * 
   * @param searchCriteria String search term.
   */
  @JsonCreator
  public AutoCompletePersonRequest(
      @NotNull @JsonProperty("search_criteria") String searchCriteria) {
    this.searchCriteria = searchCriteria;
  }

  /**
   * Getter for auto-complete search term.
   * 
   * @return search term
   */
  public String getSearchCriteria() {
    return searchCriteria;
  }

  /**
   * Setter for auto-complete search term.
   * 
   * @param searchCriteria search term
   */
  public void setSearchCriteria(String searchCriteria) {
    this.searchCriteria = searchCriteria;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((searchCriteria == null) ? 0 : searchCriteria.hashCode());
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
    if (searchCriteria == null) {
      if (other.searchCriteria != null)
        return false;
    } else if (!searchCriteria.equals(other.searchCriteria))
      return false;
    return true;
  }

}
