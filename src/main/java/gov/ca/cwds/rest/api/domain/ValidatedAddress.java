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
 * {@link DomainObject} representing an address that is validated or standardized via SmartyStreets
 * 
 * @author CWDS API Team
 */
@JsonSnakeCase
public class ValidatedAddress extends ReportingDomain implements Request, Response {
  /**
   * Serialization version
   */
  private static final long serialVersionUID = 1L;

  @JsonProperty("zip")
  @ApiModelProperty(example = "95827")
  private Integer zip;

  @JsonProperty("city")
  @ApiModelProperty(example = "Sacramento")
  @Size(max = 50)
  private String city;

  @JsonProperty("street_address")
  @ApiModelProperty(example = "9500 Kiefer Blvd")
  @Size(max = 50)
  private String streetAddress;


  @JsonProperty("state")
  @ApiModelProperty(example = "CA")
  private String state;

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
  @JsonCreator
  public ValidatedAddress(@JsonProperty("street_address") String streetAddress,
      @JsonProperty("city") String city, @JsonProperty("state") String state,
      @JsonProperty("zip") Integer zip, @JsonProperty("longitude") Double longitude,
      @JsonProperty("lattitude") Double lattitude, @JsonProperty("delivery") Boolean deliverable) {
    super();
    this.streetAddress = streetAddress;
    this.city = city;
    this.state = state;
    this.zip = zip;
    this.longitude = longitude;
    this.lattitude = lattitude;
    this.deliverable = deliverable;
  }

  /**
   * @return the streetAddress
   */
  public String getStreetAddress() {
    return streetAddress;
  }


  /**
   * @return the city
   */
  public String getCity() {
    return city;
  }


  /**
   * @return the state
   */
  public String getState() {
    return state;
  }


  /**
   * @return the zip
   */
  public Integer getZip() {
    return zip;
  }


  /**
   * @return the longitude
   */
  public Double getLongitude() {
    return longitude;
  }


  /**
   * @return the lattitude
   */
  public Double getLattitude() {
    return lattitude;
  }


  /**
   * @return the deliverable
   */
  public Boolean getDeliverable() {
    return deliverable;
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
