package gov.ca.cwds.rest.api.domain.cms;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import io.dropwizard.jackson.JsonSnakeCase;
import io.swagger.annotations.ApiModel;

/**
 * A domain API {@link Request} for Intake Person Auto-complete feature to Elasticsearch.
 * 
 * @author CWDS API Team
 */
@ApiModel
@JsonSnakeCase
public class LegacyKeyResponse implements Response {

  /**
   * Base serialization version. Increment by class version.
   */
  private static final long serialVersionUID = 1L;

  @JsonIgnore
  @JsonProperty("ui_identifier")
  private String uiIdentifier;

  /**
   * Disallow use of default constructor.
   */
  @SuppressWarnings("unused")
  private LegacyKeyResponse() {
    // Default, no-op.
  }

  /**
   * Preferred constructor. Build from UI identifier.
   * 
   * @param uiIdentifier String of UI identifier
   */
  public LegacyKeyResponse(String uiIdentifier) {
    this.uiIdentifier = uiIdentifier;
  }

  /**
   * Getter for UI identifier.
   * 
   * @return formatted UI identifier
   */
  public String getUiIdentifier() {
    return uiIdentifier;
  }

  /**
   * Setter for UI identifier.
   * 
   * @param uiIdentifier formatted UI identifier
   */
  public void setUiIdentifier(String uiIdentifier) {
    this.uiIdentifier = uiIdentifier;
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
