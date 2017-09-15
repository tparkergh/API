package gov.ca.cwds.rest.api.domain.investigation;

import static gov.ca.cwds.data.persistence.cms.CmsPersistentObject.CMS_ID_LEN;

import java.util.Set;

import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
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

  @JsonProperty("table_name")
  @ApiModelProperty(required = true, readOnly = false, value = "CWS/CMS table name",
      example = "ADDRS_T")
  private String tableName;

  @JsonProperty("id")
  @ApiModelProperty(required = true, readOnly = false, value = "Identifier", example = "1234567ABC")
  @Size(max = CMS_ID_LEN)
  private String id;

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
  private Integer state;

  @JsonProperty("zip")
  @ApiModelProperty(value = "Zip", example = "95757")
  @Size(min = 5, max = 5)
  private String zip;

  @JsonProperty("type")
  @ApiModelProperty(example = "32")
  @ValidSystemCodeId(required = true, category = SystemCodeCategoryId.ADDRESS_TYPE)
  private Integer type;

  @JsonProperty("phone")
  private Set<gov.ca.cwds.rest.api.domain.PhoneNumber> phone;

  /**
   * @param tableName - CWS/CMS table name
   * @param id - unique CWS/CMS identifier
   * @param streetAddress - street address of the investigation
   * @param city - city of the investigation
   * @param state - state code of the investigation
   * @param zip - zip code of the investigation
   * @param type - type code of the address
   */
  public InvestigationAddress(@JsonProperty("table_name") String tableName,
      @JsonProperty("id") String id, @JsonProperty("street_address") String streetAddress,
      @JsonProperty("city") String city, @JsonProperty("state") Integer state,
      @JsonProperty("zip") String zip, @JsonProperty("type") Integer type) {
    super();
    this.tableName = tableName;
    this.id = id;
    this.streetAddress = streetAddress;
    this.city = city;
    this.state = state;
    this.zip = zip;
    this.type = type;
  }


  /**
   * @param tableName - the CWS/CMS table name
   */
  public void setTableName(String tableName) {
    this.tableName = tableName;
  }

  /**
   * @return CWS/CMS table name
   */
  public String getTableName() {
    return tableName;
  }

  /**
   * @param id - unique CWS/CMS identifier
   */
  public void setId(String id) {
    this.id = id;
  }

  /**
   * @return unique CWS/CMS identifier
   */
  public String getId() {
    return id;
  }

  /**
   * @param streetAddress - street address
   */
  public void setStreetAddress(String streetAddress) {
    this.streetAddress = streetAddress;
  }

  /**
   * @return - street address
   */
  public String getStreetAddress() {
    return streetAddress;
  }

  /**
   * @param city - city of address
   */
  public void setCity(String city) {
    this.city = city;
  }

  /**
   * @return - city of address
   */
  public String getCity() {
    return city;
  }

  /**
   * @param state - the state code
   */
  public void setState(Integer state) {
    this.state = state;
  }

  /**
   * @return - state code
   */
  public Integer getState() {
    return state;
  }

  /**
   * @param zip - the zip code
   */
  public void setZip(String zip) {
    this.zip = zip;
  }

  /**
   * @return - zip code
   */
  public String getZip() {
    return zip;
  }

  /**
   * @param type - address type code
   */
  public void setType(Integer type) {
    this.type = type;
  }

  /**
   * @return - address type
   */
  public Integer getType() {
    return type;
  }


  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((city == null) ? 0 : city.hashCode());
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    result = prime * result + ((state == null) ? 0 : state.hashCode());
    result = prime * result + ((streetAddress == null) ? 0 : streetAddress.hashCode());
    result = prime * result + ((tableName == null) ? 0 : tableName.hashCode());
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
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
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
    if (tableName == null) {
      if (other.tableName != null)
        return false;
    } else if (!tableName.equals(other.tableName))
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
