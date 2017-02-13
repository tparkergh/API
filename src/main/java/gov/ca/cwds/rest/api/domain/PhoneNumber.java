package gov.ca.cwds.rest.api.domain;

import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.dropwizard.jackson.JsonSnakeCase;
import io.swagger.annotations.ApiModelProperty;

/**
 * {@link DomainObject} representing an phone number
 * 
 * @author CWDS API Team
 */
@JsonSnakeCase
public class PhoneNumber {

  @JsonProperty("phone_number")
  @ApiModelProperty(example = "(408) 345-5678")
  @Size(max = 50)
  String phoneNumber;

  @JsonProperty("phone_type")
  @ApiModelProperty(example = "Cell")
  @Size(max = 50)
  String phoneType;

  /**
   * Construct from persistence class
   * 
   * @param phoneNumber persistence level address object
   */
  public PhoneNumber(gov.ca.cwds.data.persistence.ns.PhoneNumber phoneNumber) {
    this.phoneNumber = phoneNumber.getNumber();
    this.phoneType = phoneNumber.getType();
  }


  /**
   * @param phoneNumber - phone number
   * @param phoneType - phone number type
   */
  public PhoneNumber(@JsonProperty("number") String phoneNumber,
      @JsonProperty("type") String phoneType) {
    super();
    this.phoneNumber = phoneNumber;
    this.phoneType = phoneType;
  }

  /**
   * @return - phone number
   */
  public String getPhoneNumber() {
    return phoneNumber;
  }

  /**
   * @return - phone type
   */
  public String getPhoneType() {
    return phoneType;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((phoneNumber == null) ? 0 : phoneNumber.hashCode());
    result = prime * result + ((phoneType == null) ? 0 : phoneType.hashCode());
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
    PhoneNumber other = (PhoneNumber) obj;
    if (phoneNumber == null) {
      if (other.phoneNumber != null)
        return false;
    } else if (!phoneNumber.equals(other.phoneNumber))
      return false;
    if (phoneType == null) {
      if (other.phoneType != null)
        return false;
    } else if (!phoneType.equals(other.phoneType))
      return false;
    return true;
  }
}
