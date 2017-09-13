package gov.ca.cwds.rest.api.domain.cms;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.DomainObject;
import gov.ca.cwds.rest.api.domain.ReportingDomain;
import io.swagger.annotations.ApiModelProperty;

/**
 * {@link DomainObject} representing an ClientScpEthnicity
 * 
 * @author CWDS API Team
 */
public class ClientScpEthnicity extends ReportingDomain implements Request, Response {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  @NotNull
  @Size(min = 10, max = 10)
  @ApiModelProperty(required = true, readOnly = false, value = "ABC1234567", example = "ABC1234567")
  private String id;

  @NotNull
  @ApiModelProperty(required = true, readOnly = false, value = "C", example = "C")
  private String establishedForCode;

  @NotNull
  @Size(min = 1, max = 10)
  @ApiModelProperty(required = true, readOnly = false, value = "ABC1234567", example = "ABC1234567")
  private String establishedId;

  @NotNull
  @ApiModelProperty(required = true, readOnly = false, example = "839")
  private Short ethnicity;

  /**
   * @param id The id
   * @param establishedForCode The establishedForCode
   * @param establishedId The establishedId
   * @param ethnicity The ethnicity
   */
  public ClientScpEthnicity(@JsonProperty("id") String id,
      @JsonProperty("establishedForCode") String establishedForCode,
      @JsonProperty("establishedId") String establishedId,
      @JsonProperty("ethnicity") Short ethnicity) {
    super();
    this.id = id;
    this.establishedForCode = establishedForCode;
    this.establishedId = establishedId;
    this.ethnicity = ethnicity;
  }

  /**
   * Construct from persistence layer CMS ClientScpEthnicity.
   * 
   * @param persistenceClientScpEthnicity - persisted ClientScpEthnicity object
   */
  public ClientScpEthnicity(
      gov.ca.cwds.data.persistence.cms.ClientScpEthnicity persistenceClientScpEthnicity) {
    this.id = persistenceClientScpEthnicity.getId();
    this.establishedForCode = persistenceClientScpEthnicity.getEstablishedForCode();
    this.establishedId = persistenceClientScpEthnicity.getEstablishedId();
    this.ethnicity = persistenceClientScpEthnicity.getEthnicity();
  }

  /**
   * @return the id
   */
  public String getId() {
    return id;
  }

  /**
   * @return the establishedForCode
   */
  public String getEstablishedForCode() {
    return establishedForCode;
  }

  /**
   * @return the establishedId
   */
  public String getEstablishedId() {
    return establishedId;
  }

  /**
   * @return the ethnicity
   */
  public Short getEthnicity() {
    return ethnicity;
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
