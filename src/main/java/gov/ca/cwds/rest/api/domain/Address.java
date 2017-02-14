package gov.ca.cwds.rest.api.domain;

import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import io.dropwizard.jackson.JsonSnakeCase;
import io.swagger.annotations.ApiModelProperty;

/**
 * {@link DomainObject} representing an address
 * 
 * @author CWDS API Team
 */
@JsonSnakeCase
public class Address extends DomainObject implements Request, Response {

  @JsonProperty("street_address")
  @ApiModelProperty(example = "742 Evergreen Terrace")
  @Size(max = 50)
  private String streetAddress;

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

  @JsonProperty("address_type_id")
  @ApiModelProperty(example = "Home")
  private String addressTypeId;

  /**
   * Constructor
   * 
   * @param streetAddress - street address
   * @param city - city
   * @param state - state
   * @param zip - zip code
   * @param address_type_id the address type
   */
  @JsonCreator
  public Address(@JsonProperty("street_address") String streetAddress,
      @JsonProperty("city") String city, @JsonProperty("state") String state,
      @JsonProperty("zip") Integer zip, @JsonProperty("type") String address_type_id) {
    super();
    this.streetAddress = streetAddress;
    this.city = city;
    this.state = state;
    this.zip = zip;
    this.addressTypeId = address_type_id;
  }

  /**
   * Construct from persistence class
   * 
   * @param address persistence level address object
   */
  public Address(gov.ca.cwds.data.persistence.ns.Address address) {
    this.streetAddress = address.getStreetAddress();
    this.city = address.getCity();
    this.state = address.getState();
    this.zip = address.getZip();
    this.addressTypeId = address.getType();

  }

  /**
   * @return street address
   */
  public String getStreetAddress() {
    return streetAddress;
  }

  /**
   * @return city
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
   * @return zip code
   */
  public Integer getZip() {
    return zip;
  }

  /**
   * @return address type
   */
  public String getType() {
    return addressTypeId;
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
    result = prime * result + ((streetAddress == null) ? 0 : streetAddress.hashCode());
    result = prime * result + ((zip == null) ? 0 : zip.hashCode());
    result = prime * result + ((addressTypeId == null) ? 0 : addressTypeId.hashCode());
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
    if (streetAddress == null) {
      if (other.streetAddress != null)
        return false;
    } else if (!streetAddress.equals(other.streetAddress))
      return false;
    if (zip == null) {
      if (other.zip != null)
        return false;
    } else if (!zip.equals(other.zip))
      return false;
    if (addressTypeId == null) {
      if (other.addressTypeId != null)
        return false;
    } else if (!addressTypeId.equals(other.addressTypeId))
      return false;
    return true;
  }

}
