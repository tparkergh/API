package gov.ca.cwds.rest.api.domain;

import java.beans.Transient;
import java.util.List;

import javax.validation.constraints.Size;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.data.std.ApiMarker;
import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.error.ErrorMessage;
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
@ApiModel("nsIntakeLovEntry")
public class IntakeLovEntry implements Request, Response, ApiMarker {

  /**
   * Default serialization.
   */
  private static final long serialVersionUID = 1L;

  @JsonIgnore
  @ApiModelProperty(value = "Legacy system code ID", example = "1828")
  private Long legacySystemCodeId;

  @JsonIgnore
  // @JsonProperty("legacy_logical_code")
  @ApiModelProperty(example = "AK")
  @Size(max = 5)
  private String legacyLogicalCode;

  @JsonIgnore
  // @JsonProperty("legacy_meta")
  @ApiModelProperty(example = "ACTV_RNC")
  @Size(max = 50)
  private String legacyMeta;

  /**
   * code: whatever code/id we should pass back to the system
   */
  // @JsonProperty("code")
  @JsonIgnore
  @ApiModelProperty(example = "2167")
  @Size(max = 50)
  private String intakeCode;

  /**
   * value: the string to display to the user
   */
  // @JsonProperty("value")
  @JsonIgnore
  @ApiModelProperty(example = "Dangerous puppy on premises")
  @Size(max = 50)
  private String intakeValue;

  /**
   * type: the group this LOV belongs to (ie, states, counties, allegation_types)
   */
  // @JsonProperty("type")
  @JsonIgnore
  @ApiModelProperty(example = "DEADLY_BABY_ANIMALS")
  @Size(max = 50)
  private String intakeType;

  @JsonIgnore
  @ApiModelProperty(example = "SAFETY_ALERTS")
  @Size(max = 50)
  private String parentIntakeType;

  /**
   * use logical: whether to use the logical id, not the system code id
   */
  @JsonIgnore
  // @JsonProperty("use_logical")
  @ApiModelProperty(example = "false")
  private boolean useLogical;

  /**
   * sort_order: index position of sort order for this LOV object
   */
  @JsonIgnore
  // @JsonProperty("sort_order")
  @ApiModelProperty(example = "2")
  private int sortOrder;

  /**
   * Default constructor.
   */
  public IntakeLovEntry() {
    // Default, no-op.
  }

  /**
   * Constructor
   * 
   * @param legacySystemCodeId legacy lov id
   * @param legacyLogicalCode legacy logical code
   * @param legacyMeta legacy category
   * @param intakeType Intake category
   * @param intakeCode Intake LOV code
   * @param intakeDisplay Intake display value
   * @param useLogical use the logical id, not the system id
   */
  @JsonCreator
  public IntakeLovEntry(@JsonProperty("legacy_system_code_id") String legacySystemCodeId,
      @JsonProperty("legacy_logical_code") String legacyLogicalCode,
      @JsonProperty("legacy_meta") String legacyMeta,
      @JsonProperty("intake_type") String intakeType,
      @JsonProperty("intake_code") String intakeCode,
      @JsonProperty("intake_display") String intakeDisplay,
      @JsonProperty("use_logical") boolean useLogical) {
    super();
    this.legacySystemCodeId = Long.parseLong(legacySystemCodeId);
    this.legacyMeta = legacyMeta;
    this.legacyLogicalCode = legacyLogicalCode;
    this.intakeType = intakeType;
    this.intakeCode = intakeCode;
    this.intakeValue = intakeDisplay;
    this.useLogical = useLogical;
  }

  /**
   * Construct from persistence class.
   * 
   * @param lov persistence Intake LOV record
   */
  public IntakeLovEntry(gov.ca.cwds.data.persistence.ns.IntakeLov lov) {
    this.legacyMeta = lov.getLegacyMeta();
    this.legacySystemCodeId = lov.getLegacySystemCodeId();
    this.legacyLogicalCode = lov.getLegacyLogicalCode();
    this.intakeCode = lov.getIntakeCode();
    this.intakeType = lov.getIntakeType();
    this.parentIntakeType = lov.getParentIntakeType();
    this.intakeValue = StringUtils.isNotBlank(lov.getIntakeDisplay()) ? lov.getIntakeDisplay()
        : lov.getLegacyShortDescription().replaceAll("\\*", "");
    this.useLogical = lov.isUseLogical();
  }

  @JsonIgnore
  @Transient
  public Long getLegacySystemCodeId() {
    return legacySystemCodeId;
  }

  public void setLegacySystemCodeId(Long legacySystemCodeId) {
    this.legacySystemCodeId = legacySystemCodeId;
  }

  @JsonIgnore
  @Transient
  public String getLegacyMeta() {
    return legacyMeta;
  }

  public void setLegacyMeta(String legacyMeta) {
    this.legacyMeta = legacyMeta;
  }

  @JsonProperty("code")
  public String getIntakeCode() {
    return intakeCode;
  }

  public void setIntakeCode(String intakeCode) {
    this.intakeCode = intakeCode;
  }

  @JsonProperty("display")
  public String getIntakeValue() {
    return intakeValue;
  }

  public void setIntakeValue(String intakeValue) {
    this.intakeValue = intakeValue;
  }

  @JsonProperty("type")
  public String getIntakeType() {
    return intakeType;
  }

  public void setIntakeType(String intakeType) {
    this.intakeType = intakeType;
  }

  @JsonIgnore
  @Transient
  public int getSortOrder() {
    return sortOrder;
  }

  public void setSortOrder(int sortOrder) {
    this.sortOrder = sortOrder;
  }

  public static long getSerialversionuid() {
    return serialVersionUID;
  }

  @JsonIgnore
  public boolean isUseLogical() {
    return useLogical;
  }

  public void setUseLogical(boolean useLogical) {
    this.useLogical = useLogical;
  }

  @JsonIgnore
  public String getLegacyLogicalCode() {
    return legacyLogicalCode;
  }

  public void setLegacyLogicalCode(String legacyLogicalCode) {
    this.legacyLogicalCode = legacyLogicalCode;
  }

  @JsonIgnore
  public String getParentIntakeType() {
    return parentIntakeType;
  }

  public void setParentIntakeType(String parentIntakeType) {
    this.parentIntakeType = parentIntakeType;
  }

  @Override
  public boolean hasMessages() {
    return false;
  }

  @JsonIgnore
  @Transient
  @Override
  public List<ErrorMessage> getMessages() {
    return Response.super.getMessages();
  }

}
