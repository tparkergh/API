package gov.ca.cwds.rest.api.domain.investigation;

import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.LegacyDescriptor;
import gov.ca.cwds.rest.api.domain.SystemCodeCategoryId;
import gov.ca.cwds.rest.validation.ValidSystemCodeId;
import io.dropwizard.jackson.JsonSnakeCase;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author CWDS API Team
 *
 */
@JsonSnakeCase

public class InvestigationAddress implements Request, Response {

  @JsonProperty("legacy_descriptor")
  @ApiModelProperty(required = true, readOnly = false, value = "CMS record description")
  private LegacyDescriptor legacyDescriptor;

  @JsonProperty("street_address")
  @ApiModelProperty(example = "742 Evergreen Terrace")
  @Size(max = 50)
  private String streetAddress;

  @JsonProperty("city")
  @ApiModelProperty(value = "City", example = "Springfield")
  @Size(max = 50)
  private String city;

  @JsonProperty("state")
  @ApiModelProperty(value = "State Code", example = "1828")
  @ValidSystemCodeId(required = true, category = SystemCodeCategoryId.STATE_CODE)
  private Short state;

  @JsonProperty("zip")
  @ApiModelProperty(value = "Zip", example = "95757")
  @Size(min = 5, max = 5)
  private String zip;

  @JsonProperty("type")
  @ApiModelProperty(example = "32")
  @ValidSystemCodeId(required = true, category = SystemCodeCategoryId.ADDRESS_TYPE)
  private Short type;


  /**
   * @param legacyDescriptor - CMS record description
   * @param streetAddress - street address
   * @param city - city
   * @param state - state code
   * @param zip - zip code
   * @param type - address type code
   */
  public InvestigationAddress(LegacyDescriptor legacyDescriptor, String streetAddress, String city,
      @ValidSystemCodeId(required = true, category = "STATE_C") Short state, String zip,
      @ValidSystemCodeId(required = true, category = "ADDR_TPC") Short type) {
    super();
    this.legacyDescriptor = legacyDescriptor;
    this.streetAddress = streetAddress;
    this.city = city;
    this.state = state;
    this.zip = zip;
    this.type = type;
  }


  public LegacyDescriptor getLegacyDescriptor() {
    return legacyDescriptor;
  }


  public String getStreetAddress() {
    return streetAddress;
  }


  public String getCity() {
    return city;
  }


  public Short getState() {
    return state;
  }


  public String getZip() {
    return zip;
  }


  public Short getType() {
    return type;
  }


  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((city == null) ? 0 : city.hashCode());
    result = prime * result + ((legacyDescriptor == null) ? 0 : legacyDescriptor.hashCode());
    result = prime * result + ((state == null) ? 0 : state.hashCode());
    result = prime * result + ((streetAddress == null) ? 0 : streetAddress.hashCode());
    result = prime * result + ((type == null) ? 0 : type.hashCode());
    result = prime * result + ((zip == null) ? 0 : zip.hashCode());
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
    InvestigationAddress other = (InvestigationAddress) obj;
    if (city == null) {
      if (other.city != null)
        return false;
    } else if (!city.equals(other.city))
      return false;
    if (legacyDescriptor == null) {
      if (other.legacyDescriptor != null)
        return false;
    } else if (!legacyDescriptor.equals(other.legacyDescriptor))
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
    if (type == null) {
      if (other.type != null)
        return false;
    } else if (!type.equals(other.type))
      return false;
    if (zip == null) {
      if (other.zip != null)
        return false;
    } else if (!zip.equals(other.zip))
      return false;
    return true;
  }

}
