package gov.ca.cwds.rest.api.domain;

import javax.validation.constraints.Size;

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
public class Ethnicity extends DomainObject implements Request, Response {
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

  @Override
  public final int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((ethnicityType == null) ? 0 : ethnicityType.hashCode());
    result = prime * result + ((subEthnicity == null) ? 0 : subEthnicity.hashCode());
    return result;
  }

  @Override
  public final boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (!(getClass().isInstance(obj)))
      return false;
    Ethnicity other = (Ethnicity) obj;
    if (ethnicityType == null) {
      if (other.ethnicityType != null)
        return false;
    } else if (!ethnicityType.equals(other.ethnicityType))
      return false;
    if (subEthnicity == null) {
      if (other.subEthnicity != null)
        return false;
    } else if (!subEthnicity.equals(other.subEthnicity))
      return false;
    return true;
  }

}
