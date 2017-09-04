package gov.ca.cwds.rest.api.domain;

import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.data.std.ApiMarker;
import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import io.dropwizard.jackson.JsonSnakeCase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * {@link DomainObject} representing an Intake LOV code entry.
 * 
 * <p>
 * Catch: the choice to send either logical id or system code id depends on the given REST API. It's
 * not an inherent property of an LOV entry.
 * </p>
 * 
 * @author CWDS API Team
 */
@JsonSnakeCase
@ApiModel("nsIntakeLov")
public class IntakeLov implements Request, Response, ApiMarker {

  /**
   * Default serialization.
   */
  private static final long serialVersionUID = 1L;

  @ApiModelProperty(value = "Legacy system code ID", example = "1828")
  private Long legacySystemCodeId;

  @JsonProperty("legacy_meta")
  @ApiModelProperty(example = "ACTV_RNC")
  @Size(max = 50)
  private String legacyMeta;

  /**
   * code: whatever code/id we should pass back to the system
   */
  @JsonProperty("intake_code")
  @ApiModelProperty(example = "2167")
  @Size(max = 50)
  private String intakeCode;

  /**
   * value: the string to display to the user
   */
  @JsonProperty("intake_value")
  @ApiModelProperty(example = "2167")
  @Size(max = 50)
  private String intakeValue;

  /**
   * type: the group this LOV belongs to (ie, states, counties, allegation_types)
   */
  @JsonProperty("intake_type")
  @ApiModelProperty(example = "SAFETY_ALERTS")
  @Size(max = 50)
  private String intakeType;

  /**
   * sort_order: index position of sort order for this LOV object
   */
  @JsonProperty("sort_order")
  @ApiModelProperty(example = "2")
  private int sortOrder;

  /**
   * Default constructor.
   */
  public IntakeLov() {
    // Default, no-op.
  }

  /**
   * Constructor
   * 
   * @param legacySystemCodeId legacy lov id
   * @param legacyMeta legacy category
   * @param legacyShortDescription legacy short description
   * @param legacyLogicalId legacy logical code, if used
   * @param legacyInactive legacy inactive flag
   * @param legacyCategoryId legacy sub-category id
   * @param legacyOtherCode legacy other code
   * @param legacyLongDescription legacy long description, if used
   * @param intakeType Intake category
   * @param intakeCode Intake LOV code
   * @param intakeDisplay Intake display
   */
  @JsonCreator
  public IntakeLov(@JsonProperty("legacy_system_code_id") String legacySystemCodeId,
      @JsonProperty("legacy_meta") String legacyMeta,
      @JsonProperty("legacy_short_description") String legacyShortDescription,
      @JsonProperty("legacy_logical_id") String legacyLogicalId,
      @JsonProperty("legacy_inactive") String legacyInactive,
      @JsonProperty("legacy_category_id") String legacyCategoryId,
      @JsonProperty("legacy_other_code") String legacyOtherCode,
      @JsonProperty("legacy_long_description") String legacyLongDescription,
      @JsonProperty("intake_type") String intakeType,
      @JsonProperty("intake_code") String intakeCode,
      @JsonProperty("intake_display") String intakeDisplay) {
    super();
    this.legacySystemCodeId = Long.parseLong(legacySystemCodeId);
    this.legacyMeta = legacyMeta;
  }

  /**
   * Construct from persistence class.
   * 
   * @param lov persistence Intake LOV record
   */
  public IntakeLov(gov.ca.cwds.data.persistence.ns.IntakeLov lov) {
    this.legacySystemCodeId = lov.getLegacySystemCodeId();
    this.legacyMeta = lov.getLegacyMeta();
    this.intakeCode = lov.getIntakeCode();
    this.intakeType = lov.getIntakeType();
    this.intakeValue = lov.getIntakeDisplay();
  }

  public Long getLegacySystemCodeId() {
    return legacySystemCodeId;
  }

  public void setLegacySystemCodeId(Long legacySystemCodeId) {
    this.legacySystemCodeId = legacySystemCodeId;
  }

  public String getLegacyMeta() {
    return legacyMeta;
  }

  public void setLegacyMeta(String legacyMeta) {
    this.legacyMeta = legacyMeta;
  }

  public String getIntakeCode() {
    return intakeCode;
  }

  public void setIntakeCode(String intakeCode) {
    this.intakeCode = intakeCode;
  }

  public String getIntakeValue() {
    return intakeValue;
  }

  public void setIntakeValue(String intakeValue) {
    this.intakeValue = intakeValue;
  }

  public String getIntakeType() {
    return intakeType;
  }

  public void setIntakeType(String intakeType) {
    this.intakeType = intakeType;
  }

  public int getSortOrder() {
    return sortOrder;
  }

  public void setSortOrder(int sortOrder) {
    this.sortOrder = sortOrder;
  }

  public static long getSerialversionuid() {
    return serialVersionUID;
  }

}
