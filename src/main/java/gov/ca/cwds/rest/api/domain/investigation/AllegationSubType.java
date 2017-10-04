package gov.ca.cwds.rest.api.domain.investigation;

import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.DomainObject;
import gov.ca.cwds.rest.api.domain.ReportingDomain;
import gov.ca.cwds.rest.api.domain.SystemCodeCategoryId;
import gov.ca.cwds.rest.validation.ValidSystemCodeId;
import io.dropwizard.jackson.JsonSnakeCase;
import io.swagger.annotations.ApiModelProperty;

/**
 * {@link DomainObject} representing an Allegation
 * 
 * @author CWDS API Team
 */
@JsonSnakeCase
public class AllegationSubType extends ReportingDomain implements Response {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  /**
   * empty constructor
   */
  public AllegationSubType() {
    super();
  }

  /**
   * @param injuryHarmType - Lov value of injury harm type
   * @param injuryHarmSubType - Lov value of body part
   */
  public AllegationSubType(Short injuryHarmType, Short injuryHarmSubType) {
    super();
    this.injuryHarmType = injuryHarmType;
    this.injuryHarmSubType = injuryHarmSubType;
  }

  @JsonProperty("injury_harm_type")
  @ApiModelProperty(required = false, readOnly = false, value = "Injury/Harm Type",
      example = "1372")
  @ValidSystemCodeId(required = true, category = SystemCodeCategoryId.INJURY_HARM_TYPE)
  private Short injuryHarmType;

  @JsonProperty("injury_harm_sub_type")
  @ApiModelProperty(required = false, readOnly = false, value = "Injury/Harm sub-typ")
  @ValidSystemCodeId(required = true, category = SystemCodeCategoryId.ALLEGATION_TYPE)
  private Short injuryHarmSubType;

  /**
   * @return - the injury harm type code
   */
  public Short getInjuryHarmType() {
    return injuryHarmType;
  }

  /**
   * @return - the injury harm sub type
   */
  public Short getInjuryHarmSubType() {
    return injuryHarmSubType;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((injuryHarmSubType == null) ? 0 : injuryHarmSubType.hashCode());
    result = prime * result + ((injuryHarmType == null) ? 0 : injuryHarmType.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    AllegationSubType other = (AllegationSubType) obj;
    if (injuryHarmSubType == null) {
      if (other.injuryHarmSubType != null)
        return false;
    } else if (!injuryHarmSubType.equals(other.injuryHarmSubType))
      return false;
    if (injuryHarmType == null) {
      if (other.injuryHarmType != null)
        return false;
    } else if (!injuryHarmType.equals(other.injuryHarmType))
      return false;
    return true;
  }



}
