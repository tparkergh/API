package gov.ca.cwds.rest.api.domain;

import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import io.dropwizard.jackson.JsonSnakeCase;
import io.swagger.annotations.ApiModelProperty;

/**
 * {@link DomainObject} representing an phone number
 * 
 * @author CWDS API Team
 */
@JsonSnakeCase
public class PhoneNumber extends DomainObject implements Request, Response {

  @JsonProperty("number")
  @ApiModelProperty(example = "(408) 345-5678")
  @Size(max = 50)
  String number;

  @JsonProperty("type")
  @ApiModelProperty(example = "Cell")
  @Size(max = 50)
  String type;

  /**
   * Construct from persistence class
   * 
   * @param phoneNumber persistence level address object
   */
  public PhoneNumber(gov.ca.cwds.data.persistence.ns.PhoneNumber phoneNumber) {
    this.number = phoneNumber.getNumber();
    this.type = phoneNumber.getType();
  }

  /**
   * @param phoneNumber - phone number
   * @param phoneType - phone number type
   */
  public PhoneNumber(@JsonProperty("number") String phoneNumber,
      @JsonProperty("phone_number_type_id") String phoneType) {
    super();
    this.number = phoneNumber;
    this.type = phoneType;
  }

  /**
   * @return - phone number
   */
  public String getPhoneNumber() {
    return number;
  }

  /**
   * @return - phone type
   */
  public String getPhoneType() {
    return type;
  }

  @Override
  public final int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((number == null) ? 0 : number.hashCode());
    result = prime * result + ((type == null) ? 0 : type.hashCode());
    return result;
  }

  @Override
  public final boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    PhoneNumber other = (PhoneNumber) obj;
    if (number == null) {
      if (other.number != null)
        return false;
    } else if (!number.equals(other.number))
      return false;
    if (type == null) {
      if (other.type != null)
        return false;
    } else if (!type.equals(other.type))
      return false;
    return true;
  }
}
