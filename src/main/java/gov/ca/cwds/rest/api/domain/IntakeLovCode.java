package gov.ca.cwds.rest.api.domain;

import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.validation.ValidSystemCodeId;
import io.dropwizard.jackson.JsonSnakeCase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * {@link DomainObject} representing an Intake LOV code entry.
 * 
 * @author CWDS API Team
 */
@JsonSnakeCase
@ApiModel("nsIntakeLovCode")
public class IntakeLovCode implements Request, Response {

  /**
   * Default serialization.
   */
  private static final long serialVersionUID = 1L;

  @ApiModelProperty(value = "State Code", example = "1828")
  @ValidSystemCodeId(required = true, category = SystemCodeCategoryId.STATE_CODE)
  private Long legacySystemCodeId;

  @JsonProperty("legacy_meta")
  @ApiModelProperty(example = "742 Evergreen Terrace")
  @Size(max = 50)
  private String legacyMeta;

  @JsonProperty("legacy_short_description")
  @ApiModelProperty(example = "742 Evergreen Terrace")
  @Size(max = 50)
  private String legacyShortDescription;

  @JsonProperty("legacy_logical_id")
  @ApiModelProperty(example = "742 Evergreen Terrace")
  @Size(max = 50)
  private String legacyLogicalId;

  @JsonProperty("legacy_inactive")
  @ApiModelProperty(example = "742 Evergreen Terrace")
  @Size(max = 50)
  private String legacyInactive;

  @JsonProperty("legacy_category_id")
  @ApiModelProperty(example = "742 Evergreen Terrace")
  @Size(max = 50)
  private String legacyCategoryId;

  @JsonProperty("legacy_other_code")
  @ApiModelProperty(example = "742 Evergreen Terrace")
  @Size(max = 50)
  private String legacyOtherCode;

  @JsonProperty("legacy_long_description")
  @ApiModelProperty(example = "742 Evergreen Terrace")
  @Size(max = 50)
  private String legacyLongDescription;

  @JsonProperty("intake_type")
  @ApiModelProperty(example = "742 Evergreen Terrace")
  @Size(max = 50)
  private String intakeType;

  @JsonProperty("intake_code")
  @ApiModelProperty(example = "742 Evergreen Terrace")
  @Size(max = 50)
  private String intakeCode;

  @JsonProperty("intake_display")
  @ApiModelProperty(example = "742 Evergreen Terrace")
  @Size(max = 50)
  private String intakeDisplay;

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
  public IntakeLovCode(@JsonProperty("legacySystemCodeId") String legacySystemCodeId,
      @JsonProperty("legacyMeta") String legacyMeta,
      @JsonProperty("legacyShortDescription") String legacyShortDescription,
      @JsonProperty("legacyLogicalId") String legacyLogicalId,
      @JsonProperty("legacyInactive") String legacyInactive,
      @JsonProperty("legacyCategoryId") String legacyCategoryId,
      @JsonProperty("legacyOtherCode") String legacyOtherCode,
      @JsonProperty("legacyLongDescription") String legacyLongDescription,
      @JsonProperty("intakeType") String intakeType, @JsonProperty("intakeCode") String intakeCode,
      @JsonProperty("intakeDisplay") String intakeDisplay) {
    super();
    this.legacySystemCodeId = Long.parseLong(legacySystemCodeId);
    this.legacyMeta = legacyMeta;
    this.legacyShortDescription = legacyShortDescription;
    this.legacyLogicalId = legacyLogicalId;
    this.legacyInactive = legacyInactive;
    this.legacyCategoryId = legacyCategoryId;
    this.legacyOtherCode = legacyOtherCode;
    this.legacyLongDescription = legacyLongDescription;
    this.intakeType = intakeType;
    this.intakeCode = intakeCode;
    this.intakeDisplay = intakeDisplay;
  }

  /**
   * Construct from persistence class.
   * 
   * @param lov persistence Intake LOV record
   */
  public IntakeLovCode(gov.ca.cwds.data.persistence.ns.IntakeLovCode lov) {
    this.legacySystemCodeId = lov.getLegacySystemCodeId();
    this.legacyMeta = lov.getLegacyMeta();
    this.legacyShortDescription = lov.getLegacyShortDescription();
    this.legacyLogicalId = lov.getLegacyLogicalId();
    this.legacyInactive = lov.getLegacyInactive();
    this.legacyCategoryId = lov.getLegacyCategoryId();
    this.legacyOtherCode = lov.getLegacyOtherCode();
    this.legacyLongDescription = lov.getLegacyLongDescription();
    this.intakeType = lov.getIntakeType();
    this.intakeCode = lov.getIntakeCode();
    this.intakeDisplay = lov.getIntakeDisplay();
  }

}
