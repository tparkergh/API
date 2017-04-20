package gov.ca.cwds.rest.api.domain;

import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import io.dropwizard.jackson.JsonSnakeCase;
import io.swagger.annotations.ApiModelProperty;

/**
 * {@link DomainObject} representing an race
 * 
 * @author CWDS API Team
 */
@JsonSnakeCase
public class Race extends DomainObject implements Request, Response {

  /**
   * Serialization version
   */
  private static final long serialVersionUID = 1L;

  @JsonProperty("race")
  @ApiModelProperty(example = "White")
  @Size(max = 50)
  String race;

  @JsonProperty("subrace")
  @ApiModelProperty(example = "European")
  @Size(max = 50)
  String subrace;

  /**
   * Construct from persistence class
   * 
   * @param race persistence level race object
   */
  public Race(gov.ca.cwds.data.persistence.ns.Race race) {
    this.race = race.getRace();
    this.subrace = race.getSubrace();
  }

  /**
   * @param race - race
   * @param subRace - sub race
   */
  public Race(@JsonProperty("race") String race, @JsonProperty("sub_race") String subRace) {
    super();
    this.race = race;
    this.subrace = subRace;
  }

  /**
   * @return the race
   */
  public String getRace() {
    return race;
  }

  /**
   * @param race the race to set
   */
  public void setRace(String race) {
    this.race = race;
  }

  /**
   * @return the subrace
   */
  public String getSubrace() {
    return subrace;
  }

  /**
   * @param subrace the subrace to set
   */
  public void setSubrace(String subrace) {
    this.subrace = subrace;
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
