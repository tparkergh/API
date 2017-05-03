package gov.ca.cwds.rest.api.domain;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import io.dropwizard.jackson.JsonSnakeCase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * {@link DomainObject} representing an allegation
 * 
 * @author CWDS API Team
 */
@JsonSnakeCase
@ApiModel("nsAllegation")
public class Allegation extends ReportingDomain implements Request, Response {

  /**
   * Serialization version
   */
  private static final long serialVersionUID = 1L;

  @JsonProperty("victim_person_id")
  @ApiModelProperty(required = true, value = "id of victim", example = "12345")
  private long victimPersonId;

  @JsonProperty("perpetrator_person_id")
  @ApiModelProperty(required = true, value = "id of perpatrator", example = "12345")
  private long perpetratorPersonId;

  @JsonProperty("type")
  @ApiModelProperty(required = true, value = "type of allegation", example = "mental abuse")
  @NotEmpty
  @Size(max = 75)
  private String type;

  @JsonProperty("county")
  @ApiModelProperty(example = "Sacramento")
  private String county;

  /**
   * @param victimPersonId - Person Id of victim
   * @param perpetratorPersonId - Person Id of perpetrator
   * @param type - Injury Harm type
   * @param county = County
   */
  public Allegation(@JsonProperty("victim_person_id") long victimPersonId,
      @JsonProperty("perpetrator_person_id") long perpetratorPersonId,
      @JsonProperty("type") String type, @JsonProperty("county") String county) {
    super();
    this.victimPersonId = victimPersonId;
    this.perpetratorPersonId = perpetratorPersonId;
    this.type = type;
    this.county = county;
  }

  /**
   * @return victimPersonId
   */
  public long getVictimPersonId() {
    return victimPersonId;
  }

  /**
   * @return perpetratorPersonId
   */
  public long getPerpetratorPersonId() {
    return perpetratorPersonId;
  }

  /**
   * @return type
   */
  public String getType() {
    return type;
  }

  /**
   * @return county
   */
  public String getCounty() {
    return county;
  }

  @Override
  public final int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((county == null) ? 0 : county.hashCode());
    result = prime * result + (int) (perpetratorPersonId ^ (perpetratorPersonId >>> 32));
    result = prime * result + ((type == null) ? 0 : type.hashCode());
    result = prime * result + (int) (victimPersonId ^ (victimPersonId >>> 32));
    return result;
  }

  @Override
  public final boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Allegation other = (Allegation) obj;
    if (county == null) {
      if (other.county != null)
        return false;
    } else if (!county.equals(other.county))
      return false;
    if (perpetratorPersonId != other.perpetratorPersonId)
      return false;
    if (type == null) {
      if (other.type != null)
        return false;
    } else if (!type.equals(other.type))
      return false;
    if (victimPersonId != other.victimPersonId)
      return false;
    return true;
  }

}
