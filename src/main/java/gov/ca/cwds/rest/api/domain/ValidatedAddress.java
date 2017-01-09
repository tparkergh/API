package gov.ca.cwds.rest.api.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.rest.api.Response;
import io.dropwizard.jackson.JsonSnakeCase;

/**
 * {@link Response} adding deliverable longitude and latitude to the {@link Address}
 * 
 * @author CWDS API Team
 */
@JsonSnakeCase
public class ValidatedAddress extends Address {

  @JsonProperty("longitude")
  private Double longitude;

  @JsonProperty("lattitude")
  private Double lattitude;

  @JsonProperty("deliverable")
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
    super(streetAddress, city, state, zip);
    this.longitude = longitude;
    this.lattitude = lattitude;
    this.deliverable = deliverable;
  }

  /**
   * 
   * @param address The address
   * @param longitude The longitude
   * @param lattitude The lattitude
   * @param deliverable The smarty street deliverable status
   */
  public ValidatedAddress(Address address, Double longitude, Double lattitude,
      Boolean deliverable) {
    super(address.getStreet_address(), address.getCity(), address.getState(), address.getZip());
    this.longitude = longitude;
    this.lattitude = lattitude;
    this.deliverable = deliverable;
  }

}
