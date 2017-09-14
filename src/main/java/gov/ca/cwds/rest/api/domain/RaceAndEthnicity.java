package gov.ca.cwds.rest.api.domain;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.validation.Valid;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import io.dropwizard.jackson.JsonSnakeCase;
import io.dropwizard.validation.OneOf;
import io.swagger.annotations.ApiModelProperty;

/**
 * {@link DomainObject} representing a RaceAndEthnicity
 * 
 * @author CWDS API Team
 */
@JsonSnakeCase
@JsonPropertyOrder({"raceCode", "unableToDetermineCode", "hispanicCode", "hispanicOriginCode",
    "hispanicUnableToDetermineCode"})
public class RaceAndEthnicity extends ReportingDomain implements Request, Response {

  /**
   * Serialization version.
   */
  private static final long serialVersionUID = 1L;

  @Valid
  @JsonProperty("race_codes")
  @ApiModelProperty(required = false, readOnly = false, value = "primary races",
      dataType = "java.util.List", example = "['839', '840']")
  // @ValidSystemCodeId(required = false, category = SystemCodeCategoryId.ETHNICITY)
  private Set<Short> raceCode;

  @JsonProperty("unable_to_determine_code")
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "A")
  @OneOf(value = {"A", "I", "K", ""}, ignoreCase = true, ignoreWhitespace = true)
  private String unableToDetermineCode;

  @Valid
  @JsonProperty("hispanic_codes")
  @ApiModelProperty(required = false, readOnly = false, value = "other(secondary) races",
      dataType = "java.util.List", example = "['3164']")
  // @ValidSystemCodeId(required = false, category = SystemCodeCategoryId.ETHNICITY)
  private Set<Short> hispanicCode;

  @OneOf(value = {"D", "N", "U", "X", "Y", "Z", ""}, ignoreCase = true, ignoreWhitespace = true)
  @JsonProperty("hispanic_origin_code")
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "X")
  private String hispanicOriginCode;

  @OneOf(value = {"A", "I", "K", ""}, ignoreCase = true, ignoreWhitespace = true)
  @JsonProperty("hispanic_unable_to_determine_code")
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "A")
  private String hispanicUnableToDetermineCode;

  /**
   * empty constructor
   */
  public RaceAndEthnicity() {
    super();
  }

  /**
   * @param raceCode The race code
   * @param unableToDetermineCode The unable to determine code
   * @param hispanicCode The hispanic code
   * @param hispanicOriginCode The hispanic origin code
   * @param hispanicUnableToDetermineCode The hispanic unable to determine code
   */
  @JsonCreator
  public RaceAndEthnicity(@JsonProperty("race_codes") LinkedHashSet<Short> raceCode,
      @JsonProperty("unable_to_determine_code") String unableToDetermineCode,
      @JsonProperty("hispanic_codes") LinkedHashSet<Short> hispanicCode,
      @JsonProperty("hispanic_orgin_code") String hispanicOriginCode,
      @JsonProperty("hispanic_unable_to_determine_code") String hispanicUnableToDetermineCode) {
    super();
    this.raceCode = raceCode;
    this.unableToDetermineCode = unableToDetermineCode;
    this.hispanicCode = hispanicCode;
    this.hispanicOriginCode = hispanicOriginCode;
    this.hispanicUnableToDetermineCode = hispanicUnableToDetermineCode;
  }

  /**
   * @return the raceCode
   */
  public Set<Short> getRaceCode() {
    return raceCode;
  }

  /**
   * @return the unableToDetermineCode
   */
  public String getUnableToDetermineCode() {
    return unableToDetermineCode;
  }

  /**
   * @return the hispanicCode
   */
  public Set<Short> getHispanicCode() {
    return hispanicCode;
  }

  /**
   * @return the hispanicOriginCode
   */
  public String getHispanicOriginCode() {
    return hispanicOriginCode;
  }

  /**
   * @return the hispanicUnableToDetermineCode
   */
  public String getHispanicUnableToDetermineCode() {
    return hispanicUnableToDetermineCode;
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
