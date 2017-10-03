package gov.ca.cwds.rest.api.domain.cms;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.fasterxml.jackson.annotation.JsonValue;

import gov.ca.cwds.data.std.ApiMarker;
import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import io.dropwizard.jackson.JsonSnakeCase;
import io.swagger.annotations.ApiModel;

/**
 * A domain API {@link Request} for cross report agency[Government Organizations]
 * 
 * @author CWDS API Team
 */
@ApiModel
@JsonSnakeCase
public class GovernmentOrganizationResponse implements Response, ApiMarker {

  /**
   * Base serialization version. Increment by class version.
   */
  private static final long serialVersionUID = 1L;

  @JsonUnwrapped
  private List<GovernmentOrganization> governmentOrganizations = new ArrayList<>();

  /**
   * Disallow use of default constructor.
   */
  @SuppressWarnings("unused")
  private GovernmentOrganizationResponse() {
    // Default, no-op.
  }

  /**
   * Preferred constructor. Build from Government Organization array.
   * 
   * @param governmentOrganizations array of {@link GovernmentOrganization}
   */
  public GovernmentOrganizationResponse(List<GovernmentOrganization> governmentOrganizations) {
    this.governmentOrganizations = governmentOrganizations;
  }

  /**
   * Getter for array of {@link GovernmentOrganization governmentOrganization's}.
   * 
   * @return the cross report agencies
   */
  @JsonValue
  public List<GovernmentOrganization> getGovernmentOrganizations() {
    return governmentOrganizations;
  }

  /**
   * Setter for array of {@link GovernmentOrganization governmentOrganizations}.
   * 
   * @param governmentOrganizations governmentOrganizations
   */
  public void setGovernmentOrganizations(List<GovernmentOrganization> governmentOrganizations) {
    this.governmentOrganizations = governmentOrganizations;
  }

  public void addGovernmentOrganizations(List<GovernmentOrganization> governmentOrganizations) {
    this.governmentOrganizations.addAll(governmentOrganizations);
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
