package gov.ca.cwds.rest.api.domain.investigation;

import java.math.BigDecimal;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

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
