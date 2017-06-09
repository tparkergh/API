package gov.ca.cwds.rest.api.domain.cms;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

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
public class LegacyKeyRequest implements Request {

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
  public LegacyKeyRequest(@Valid @NotNull @JsonProperty("search_term") String searchTerm) {
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
  public final int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this, false);
  }

  @Override
  public final boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj, false);
  }

}
