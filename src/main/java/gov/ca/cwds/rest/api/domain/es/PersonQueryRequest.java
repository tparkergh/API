package gov.ca.cwds.rest.api.domain.es;

import gov.ca.cwds.rest.api.Request;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A domain API {@link Request} for Intake Person Query feature to Elasticsearch.
 * 
 * <p>
 * The Intake Person Query for Person takes a single json as string, which is used to query
 * Elasticsearch Person documents by ALL relevant fields that are specified in the query.
 * </p>
 *
 * @author CWDS API Team
 */
@ApiModel
public class PersonQueryRequest implements Serializable, Request {

  /**
   * Base serialization version. Increment by class version.
   */
  private static final long serialVersionUID = 1L;

  @ApiModelProperty(required = true, readOnly = false, example = "a valid elasticsearch query json")
  @JsonProperty("query")
  private Object query;

  /**
   * JSON DropWizard Constructor. Takes query.
   * 
   * @param query the elasticsearch query
   */
  @JsonCreator
  public PersonQueryRequest(@Valid @NotNull @JsonProperty("query") Object query) {
    this.query = query;
  }

  /**
   * Getter for query.
   * 
   * @return query the elasticsearch query
   */
  public Object getQuery() {
    return query;
  }

  /**
   * Setter for query.
   * 
   * @param query the elasticsearch query
   */
  public void setQuery(Object query) {
    this.query = query;
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
