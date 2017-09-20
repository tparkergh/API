package gov.ca.cwds.rest.api.domain.investigation;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.LegacyDescriptor;
import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * @author CWDS API Team
 */
public class PhoneNumber implements Request, Response {
  private static final long serialVersionUID = 1L;

  @JsonProperty("number")
  @ApiModelProperty(required = true, readOnly = false, value = "phone number")
  private BigDecimal phoneNumber;

  @JsonProperty("extension")
  @ApiModelProperty(required = true, readOnly = false, value = "extension")
  private Integer phoneExtension;

  @JsonProperty("type")
  @ApiModelProperty(required = true, readOnly = false, value = "phone number type")
  private String phoneType;

  @JsonProperty("legacy_descriptor")
  private LegacyDescriptor legacyDescriptor;

  /**
   * @param phoneNumber - phone number
   * @param phoneExtension - extension
   * @param phoneType -type
   * @param legacyDescriptor - CMS record description
   */
  public PhoneNumber(BigDecimal phoneNumber, Integer phoneExtension, String phoneType,
      LegacyDescriptor legacyDescriptor) {
    super();
    this.phoneNumber = phoneNumber;
    this.phoneExtension = phoneExtension;
    this.phoneType = phoneType;
    this.legacyDescriptor = legacyDescriptor;
  }

  public BigDecimal getPhoneNumber() {
    return phoneNumber;
  }

  public Integer getPhoneExtension() {
    return phoneExtension;
  }

  public String getPhoneType() {
    return phoneType;
  }

  public LegacyDescriptor getLegacyDescriptor() {
    return legacyDescriptor;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((legacyDescriptor == null) ? 0 : legacyDescriptor.hashCode());
    result = prime * result + ((phoneExtension == null) ? 0 : phoneExtension.hashCode());
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
    if (legacyDescriptor == null) {
      if (other.legacyDescriptor != null)
        return false;
    } else if (!legacyDescriptor.equals(other.legacyDescriptor))
      return false;
    if (phoneExtension == null) {
      if (other.phoneExtension != null)
        return false;
    } else if (!phoneExtension.equals(other.phoneExtension))
      return false;
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
