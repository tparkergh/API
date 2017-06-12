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
 * A domain API {@link Request} for converting a 10 character legacy key to a 19 digit UI
 * identifier.
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

  @ApiModelProperty(required = true, readOnly = false, example = "JJaIiuJ0Fk")
  @JsonProperty("key")
  private String key;

  /**
   * JSON DropWizard Constructor. Takes solitary search term.
   * 
   * @param key String search term.
   */
  @JsonCreator
  public LegacyKeyRequest(@Valid @NotNull @JsonProperty("key") String key) {
    this.key = key;
  }

  /**
   * Getter for auto-complete search term(s).
   * 
   * @return search term
   */
  public String getLegacyKey() {
    return key;
  }

  /**
   * Setter for auto-complete search term.
   * 
   * @param key legacy 10-char key
   */
  public void setLegacyKey(String key) {
    this.key = key;
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
