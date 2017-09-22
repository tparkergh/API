package gov.ca.cwds.rest.api.domain;

import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import io.dropwizard.jackson.JsonSnakeCase;
import io.swagger.annotations.ApiModelProperty;

/**
 * {@link DomainObject} representing an ethnicity
 * 
 * @author CWDS API Team
 */
@JsonSnakeCase
public class Ethnicity extends ReportingDomain implements Request, Response {
  /**
   * Base serialization value. Increment by version
   */
  private static final long serialVersionUID = 1L;

  @JsonProperty("ethnicityType")
  @ApiModelProperty(example = "Unknown")
  @Size(max = 50)
  String ethnicityType;

  @JsonProperty("subEthnicity")
  @ApiModelProperty(example = "South American")
  @Size(max = 50)
  String subEthnicity;

  /**
   * Construct from persistence class
   * 
   * @param ethnicity persistence level ethnicity object
   */
  public Ethnicity(gov.ca.cwds.data.persistence.ns.Ethnicity ethnicity) {
    this.ethnicityType = ethnicity.getEthnicityType();
    this.subEthnicity = ethnicity.getSubEthnicity();
  }

  /**
   * @param ethnicityType - ethnicityType
   * @param subEthnicity - ethnicityType
   */
  @JsonCreator
  public Ethnicity(@JsonProperty("ethnicityType") String ethnicityType,
      @JsonProperty("subEthnicity") String subEthnicity) {
    super();
    this.ethnicityType = ethnicityType;
    this.subEthnicity = subEthnicity;
  }

  /**
   * @return the ethnicityType
   */
  public String getEthnicityType() {
    return ethnicityType;
  }

  /**
   * @return the subEthnicity
   */
  public String getSubEthnicity() {
    return subEthnicity;
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
