package gov.ca.cwds.rest.api.domain;

import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * {@link DomainObject} representing an address
 * 
 * @author CWDS API Team
 */
@ApiModel
public class Address extends DomainObject implements Request, Response {

  @JsonProperty("street_address")
  @ApiModelProperty(example = "742 Evergreen Terrace")
  @Size(max = 50)
  private String street_address;

  @JsonProperty("city")
  @ApiModelProperty(example = "Springfield")
  @Size(max = 50)
  private String city;

  @JsonProperty("state")
  @ApiModelProperty(example = "WA")
  @Size(max = 50)
  private String state;

  @JsonProperty("zip")
  @ApiModelProperty(example = "6525")
  private Integer zip;

  /**
   * Constructor
   * 
   * @param street_address The street address
   * @param city The city
   * @param state The state
   * @param zip The zip
   */
  @JsonCreator
  public Address(@JsonProperty("street_address") String street_address,
      @JsonProperty("city") String city, @JsonProperty("state") String state,
      @JsonProperty("zip") Integer zip) {
    super();
    this.street_address = street_address;
    this.city = city;
    this.state = state;
    this.zip = zip;
  }

  /**
   * Construct from persistence class
   * 
   * @param address persistence level address object
   */
  public Address(gov.ca.cwds.data.persistence.ns.Address address) {
    this.street_address = address.getStreetAddress();
    this.city = address.getCity();
    this.state = address.getState();
    this.zip = address.getZip();
  }

  /**
   * @return the street_address
   */
  public String getStreet_address() {
    return street_address;
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
   * {@inheritDoc}
   * 
   * @see java.lang.Object#hashCode()
   */
  @Override
  public final int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((city == null) ? 0 : city.hashCode());
    result = prime * result + ((state == null) ? 0 : state.hashCode());
    result = prime * result + ((street_address == null) ? 0 : street_address.hashCode());
    result = prime * result + ((zip == null) ? 0 : zip.hashCode());
    return result;
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public final boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (!(getClass().isInstance(obj)))
      return false;
    Address other = (Address) obj;
    if (city == null) {
      if (other.city != null)
        return false;
    } else if (!city.equals(other.city))
      return false;
    if (state == null) {
      if (other.state != null)
        return false;
    } else if (!state.equals(other.state))
      return false;
    if (street_address == null) {
      if (other.street_address != null)
        return false;
    } else if (!street_address.equals(other.street_address))
      return false;
    if (zip == null) {
      if (other.zip != null)
        return false;
    } else if (!zip.equals(other.zip))
      return false;
    return true;
  }

}
