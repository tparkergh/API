package gov.ca.cwds.rest.api.domain;

import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import io.dropwizard.jackson.JsonSnakeCase;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * {@link DomainObject} representing an address that is validated or standardized via SmartyStreets
 * 
 * @author CWDS API Team
 */
@JsonSnakeCase
public class ValidatedAddress extends DomainObject implements Request, Response {

  @JsonProperty("street_address")
  @ApiModelProperty(example = "9500 Kiefer Blvd")
  @Size(max = 50)
  private String streetAddress;

  @JsonProperty("city")
  @ApiModelProperty(example = "Sacramento")
  @Size(max = 50)
  private String city;

  @JsonProperty("state")
  @ApiModelProperty(example = "CA")
  @Size(max = 50)
  private String state;

  @JsonProperty("zip")
  @ApiModelProperty(example = "95827")
  private Integer zip;

  @JsonProperty("longitude")
  @ApiModelProperty(example = "-121.34332")
  private Double longitude;

  @JsonProperty("lattitude")
  @ApiModelProperty(example = "38.5445")
  private Double lattitude;

  @JsonProperty("deliverable")
  @ApiModelProperty(example = "true")
  private Boolean deliverable;


  /**
   * Constructor
   * 
   * @param streetAddress The validated street address
   * @param city The validated city
   * @param state The validated state
   * @param zip The validated zip
   * @param longitude The longitude
   * @param lattitude The lattitude
   * @param deliverable The smarty street deliverable status
   */
  public ValidatedAddress(String streetAddress, String city, String state, Integer zip,
      Double longitude, Double lattitude, Boolean deliverable) {
    super();
    this.streetAddress = streetAddress;
    this.city = city;
    this.state = state;
    this.zip = zip;
    this.longitude = longitude;
    this.lattitude = lattitude;
    this.deliverable = deliverable;
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
    result = prime * result + ((city == null) ? 0 : city.hashCode());
    result = prime * result + ((deliverable == null) ? 0 : deliverable.hashCode());
    result = prime * result + ((lattitude == null) ? 0 : lattitude.hashCode());
    result = prime * result + ((longitude == null) ? 0 : longitude.hashCode());
    result = prime * result + ((state == null) ? 0 : state.hashCode());
    result = prime * result + ((streetAddress == null) ? 0 : streetAddress.hashCode());
    result = prime * result + ((zip == null) ? 0 : zip.hashCode());
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
    if (getClass() != obj.getClass()) {
      return false;
    }
    ValidatedAddress other = (ValidatedAddress) obj;
    if (city == null) {
      if (other.city != null) {
        return false;
      }
    } else if (!city.equals(other.city)) {
      return false;
    }
    if (deliverable == null) {
      if (other.deliverable != null) {
        return false;
      }
    } else if (!deliverable.equals(other.deliverable)) {
      return false;
    }
    if (lattitude == null) {
      if (other.lattitude != null) {
        return false;
      }
    } else if (!lattitude.equals(other.lattitude)) {
      return false;
    }
    if (longitude == null) {
      if (other.longitude != null) {
        return false;
      }
    } else if (!longitude.equals(other.longitude)) {
      return false;
    }
    if (state == null) {
      if (other.state != null) {
        return false;
      }
    } else if (!state.equals(other.state)) {
      return false;
    }
    if (streetAddress == null) {
      if (other.streetAddress != null) {
        return false;
      }
    } else if (!streetAddress.equals(other.streetAddress)) {
      return false;
    }
    if (zip == null) {
      if (other.zip != null) {
        return false;
      }
    } else if (!zip.equals(other.zip)) {
      return false;
    }
    return true;
  }

}
