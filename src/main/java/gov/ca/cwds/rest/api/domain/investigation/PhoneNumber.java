package gov.ca.cwds.rest.api.domain.investigation;

import java.math.BigDecimal;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.ReportingDomain;
import io.dropwizard.jackson.JsonSnakeCase;
import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * @author CWDS API Team
 */
@JsonSnakeCase
@JsonPropertyOrder({"number", "extension", "type", "legacy_descriptor"})
public class PhoneNumber extends ReportingDomain implements Request, Response {

  private static final long serialVersionUID = 1L;

  @JsonProperty("number")
  @ApiModelProperty(required = true, readOnly = false, value = "phone number", example = "4569939")
  private BigDecimal phoneNumber;

  @JsonProperty("extension")
  @ApiModelProperty(required = true, readOnly = false, value = "extension", example = "2334")
  private Integer phoneExtension;

  @JsonProperty("type")
  @ApiModelProperty(required = true, readOnly = false, value = "phone number type",
      example = "1111")
  private Short phoneType;

  @JsonProperty("legacy_descriptor")
  private CmsRecordDescriptor cmsRecordDescriptor;

  /**
   * empty constructor
   */
  public PhoneNumber() {
    super();
  }

  /**
   * @param phoneNumber - phone number
   * @param phoneExtension - extension
   * @param phoneType -type
   * @param cmsRecordDescriptor - CMS record description
   */
  public PhoneNumber(@JsonProperty("number") BigDecimal phoneNumber,
      @JsonProperty("extension") Integer phoneExtension, @JsonProperty("type") Short phoneType,
      @JsonProperty("legacy_descriptor") CmsRecordDescriptor cmsRecordDescriptor) {
    super();
    this.phoneNumber = phoneNumber;
    this.phoneExtension = phoneExtension;
    this.phoneType = phoneType;
    this.cmsRecordDescriptor = cmsRecordDescriptor;
  }

  /**
   * @return - phone number
   */
  public BigDecimal getPhoneNumber() {
    return phoneNumber;
  }

  /**
   * @return - phone extension
   */
  public Integer getPhoneExtension() {
    return phoneExtension;
  }

  /**
   * @return - phone type
   */
  public Short getPhoneType() {
    return phoneType;
  }

  /**
   * @return - CMS record description
   */
  public CmsRecordDescriptor getCmsRecordDescriptor() {
    return cmsRecordDescriptor;
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
