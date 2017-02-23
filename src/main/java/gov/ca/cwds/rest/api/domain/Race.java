package gov.ca.cwds.rest.api.domain;

import javax.validation.constraints.Size;

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
   * Base serialization value. Increment by version
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

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#hashCode()
   */
  @Override
  public final int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((race == null) ? 0 : race.hashCode());
    result = prime * result + ((subrace == null) ? 0 : subrace.hashCode());
    return result;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public final boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (!(getClass().isInstance(obj))) {
      return false;
    }
    Race other = (Race) obj;
    if (race == null) {
      if (other.race != null) {
        return false;
      }
    } else if (!race.equals(other.race)) {
      return false;
    }
    if (subrace == null) {
      if (other.subrace != null) {
        return false;
      }
    } else if (!subrace.equals(other.subrace)) {
      return false;
    }
    return true;
  }

}
