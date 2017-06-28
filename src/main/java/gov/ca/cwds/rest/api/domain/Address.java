package gov.ca.cwds.rest.api.domain;

import static gov.ca.cwds.data.persistence.cms.CmsPersistentObject.CMS_ID_LEN;

import io.swagger.annotations.ApiModel;
import javax.validation.constraints.Max;
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
@ApiModel("nsAddress")
public class Address extends ReportingDomain implements Request, Response {

  /**
   * Base serialization value. Increment by version
   */
  private static final long serialVersionUID = 1L;

  @JsonProperty("legacy_source_table")
  @ApiModelProperty(required = true, readOnly = false, value = "Legacy Source Table", example = "")
  private String legacySourceTable;

  @JsonProperty("legacy_id")
  @ApiModelProperty(required = true, readOnly = false, value = "Legacy Id", example = "ABC1234567")
  @Size(max = CMS_ID_LEN)
  private String legacyId;

  @JsonProperty("street_address")
  @ApiModelProperty(example = "742 Evergreen Terrace")
  @Size(max = 50)
  private String streetAddress;

  @JsonProperty("city")
  @ApiModelProperty(value = "City", example = "Springfield")
  @Size(max = 50)
  private String city;

  @JsonProperty("state")
  @ApiModelProperty(value = "State Code", example = "CA")
  @Size(max = 50)
  private String state;

  @JsonProperty("zip")
  @ApiModelProperty(value = "Zip", example = "95757")
  @Max(99999)
  private Integer zip;

  @JsonProperty("type")
  @ApiModelProperty(example = "Home")
  private String type;

  /**
   * Constructor
   * 
   * @param legacySourceTable - the legacy source table name
   * @param addressId - legacy Id
   * @param streetAddress - street address
   * @param city - city
   * @param state - state
   * @param zip - zip code
   * @param type the address type
   */
  @JsonCreator
  public Address(@JsonProperty("legacy_source_table") String legacySourceTable,
      @JsonProperty("legacy_id") String addressId,
      @JsonProperty("street_address") String streetAddress, @JsonProperty("city") String city,
      @JsonProperty("state") String state, @JsonProperty("zip") Integer zip,
      @JsonProperty("type") String type) {
    super();
    this.legacySourceTable = legacySourceTable;
    this.legacyId = addressId;
    this.streetAddress = streetAddress;
    this.city = city;
    this.state = state;
    this.zip = zip;
    this.type = type;
  }

  /**
   * Construct from persistence class
   * 
   * @param address persistence level address object
   */
  public Address(gov.ca.cwds.data.persistence.ns.Address address) {
    this.legacySourceTable = "";
    this.legacyId = "";
    this.streetAddress = address.getStreetAddress();
    this.city = address.getCity();
    this.state = address.getState();
    this.zip = address.getZip();
    this.type = address.getType();

  }

  /**
   * @return legacy source table
   */
  public String getLegacySourceTable() {
    return legacySourceTable;
  }

  /**
   * @param legacySourceTable - the legacy source table name
   */
  public void setLegacySourceTable(String legacySourceTable) {
    this.legacySourceTable = legacySourceTable;
  }

  /**
   * @return addressId
   */
  public String getLegacyId() {
    return legacyId;
  }

  /**
   * @param addressId - the legacy Id
   */
  public void setLegacyId(String addressId) {
    this.legacyId = addressId;
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
    return type;
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
    result = prime * result + ((legacySourceTable == null) ? 0 : legacySourceTable.hashCode());
    result = prime * result + ((legacyId == null) ? 0 : legacyId.hashCode());
    result = prime * result + ((city == null) ? 0 : city.hashCode());
    result = prime * result + ((state == null) ? 0 : state.hashCode());
    result = prime * result + ((streetAddress == null) ? 0 : streetAddress.hashCode());
    result = prime * result + ((type == null) ? 0 : type.hashCode());
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
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (!(obj instanceof Address)) {
      return false;
    }
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
    if (legacySourceTable == null) {
      if (other.legacySourceTable != null)
        return false;
    } else if (!legacySourceTable.equals(other.legacySourceTable))
      return false;
    if (legacyId == null) {
      if (other.legacyId != null)
        return false;
    } else if (!legacyId.equals(other.legacyId))
      return false;
    return true;

  }



}
