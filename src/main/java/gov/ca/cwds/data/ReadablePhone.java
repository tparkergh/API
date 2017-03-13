package gov.ca.cwds.data;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.data.std.ApiPhoneAware;

/**
 * Simple, concrete implementation of interface {@link ApiPhoneAware}.
 * 
 * @author CWDS API Team
 */
public final class ReadablePhone implements ApiPhoneAware, Serializable {

  /**
   * Base serialization version. Increment by class version.
   */
  private static final long serialVersionUID = 1L;

  private final String phoneId;
  private final String phoneNumber;
  private final String phoneNumberExtension;
  private final PhoneType phoneType;

  /**
   * Construct a readable phone from all required values.
   * 
   * @param phoneId phone identifier (primary key), if any
   * @param phoneNumber concatenated phone number. Not atomic fields.
   * @param phoneNumberExtension phone extension
   * @param phoneType phone type
   */
  @JsonCreator
  public ReadablePhone(@JsonProperty("phoneId") String phoneId,
      @JsonProperty("phoneNumber") String phoneNumber,
      @JsonProperty("phoneNumberExtension") String phoneNumberExtension,
      @JsonProperty("phoneType") PhoneType phoneType) {
    this.phoneId = phoneId;
    this.phoneNumber = phoneNumber;
    this.phoneNumberExtension = phoneNumberExtension;
    this.phoneType = phoneType;
  }

  @Override
  public String getPhoneId() {
    return phoneId;
  }

  @Override
  public PhoneType getPhoneType() {
    return this.phoneType;
  }

  @Override
  public String getPhoneNumber() {
    return this.phoneNumber;
  }

  @Override
  public String getPhoneNumberExtension() {
    return this.phoneNumberExtension;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((phoneId == null) ? 0 : phoneId.hashCode());
    result = prime * result + ((phoneNumber == null) ? 0 : phoneNumber.hashCode());
    result =
        prime * result + ((phoneNumberExtension == null) ? 0 : phoneNumberExtension.hashCode());
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
    ReadablePhone other = (ReadablePhone) obj;

    if (phoneId == null) {
      if (other.phoneId != null)
        return false;
    } else if (!phoneId.equals(other.phoneId))
      return false;

    if (phoneNumber == null) {
      if (other.phoneNumber != null)
        return false;
    } else if (!phoneNumber.equals(other.phoneNumber))
      return false;

    if (phoneNumberExtension == null) {
      if (other.phoneNumberExtension != null)
        return false;
    } else if (!phoneNumberExtension.equals(other.phoneNumberExtension))
      return false;

    if (phoneType != other.phoneType)
      return false;

    return true;
  }

}
