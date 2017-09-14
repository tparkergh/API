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
 * {@link DomainObject} representing a Limited Access (Access Limitation)
 * 
 * @author CWDS API Team
 */
@JsonSnakeCase
@JsonPropertyOrder({"limited_access_government_entity_id", "limited_access_code"})
public class LimitedAccess extends ReportingDomain implements Response {

  private static final long serialVersionUID = 1L;

  @JsonProperty("limited_access_government_entity_id")
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "0")
  private String limitedAccessGovernmentEntityId;

  @JsonProperty("limited_access_code")
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "N")
  private String limitedAccessCode;


  /**
   * Constructor
   * 
   * @param limitedAccessGovernmentEntityId the limited access government entity id
   * @param limitedAccessCode limited access code
   */
  public LimitedAccess(
      @JsonProperty("limited_access_government_entity_id") String limitedAccessGovernmentEntityId,
      @JsonProperty("limited_access_code") String limitedAccessCode) {
    super();
    this.limitedAccessGovernmentEntityId = limitedAccessGovernmentEntityId;
    this.limitedAccessCode = limitedAccessCode;
  }



  /**
   * @return the limitedAccessGovernmentEntityId
   */
  public String getLimitedAccessGovernmentEntityId() {
    return limitedAccessGovernmentEntityId;
  }



  /**
   * @return the limitedAccessCode
   */
  public String getLimitedAccessCode() {
    return limitedAccessCode;
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
