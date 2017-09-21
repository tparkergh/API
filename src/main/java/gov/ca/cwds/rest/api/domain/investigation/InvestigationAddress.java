package gov.ca.cwds.rest.api.domain.investigation;

import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

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
   * empty constructor
   */
  public InvestigationAddress() {
    super();
  }

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
