package gov.ca.cwds.rest.api.domain.investigation;

import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.DomainObject;
import gov.ca.cwds.rest.api.domain.ReportingDomain;
import io.dropwizard.jackson.JsonSnakeCase;
import io.swagger.annotations.ApiModelProperty;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * {@link DomainObject} representing a Simple Legacy Descriptor
 * 
 * @author CWDS API Team
 */
@JsonSnakeCase
@JsonPropertyOrder({"legacy_ui_id"})
public class SimpleLegacyDescriptor extends ReportingDomain implements Response {

  private static final long serialVersionUID = 1L;

  @JsonProperty("legacy_ui_id")
  @ApiModelProperty(required = false, readOnly = false, value = "",
      example = "0426-8977-4115-4000591")
  private String legacyUiId;


  /**
   * @param legacyUiId the legacy ui id
   */
  public SimpleLegacyDescriptor(@JsonProperty("legacy_ui_id") String legacyUiId) {
    super();
    this.legacyUiId = legacyUiId;
  }


  /**
   * @return the legacyUiId
   */
  public String getLegacyUiId() {
    return legacyUiId;
  }


  /**
   * {@inheritDoc}
   *
   * @see java.lang.Object#hashCode()
   */
  @Override
  public final int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this, false);
  }

  /**
   * {@inheritDoc}
   *
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public final boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj, false);
  }

}
