package gov.ca.cwds.rest.api.domain;

import static gov.ca.cwds.data.persistence.cms.CmsPersistentObject.CMS_ID_LEN;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.validation.Date;
import gov.ca.cwds.rest.validation.ValidSystemCodeId;
import io.dropwizard.jackson.JsonSnakeCase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * {@link DomainObject} representing an cross_report
 * 
 * @author CWDS API Team
 */
@SuppressWarnings("serial")
@JsonSnakeCase
@ApiModel("NsCrossReport")
public class CrossReport extends ReportingDomain implements Request, Response {

  @JsonProperty("id")
  @ApiModelProperty(required = false, value = "", example = "1234")
  private String id;

  @JsonProperty("legacy_source_table")
  @ApiModelProperty(required = false, value = "Legacy Source Table", example = "CRSS_RPT")
  private String legacySourceTable;

  @JsonProperty("legacy_id")
  @ApiModelProperty(required = false, value = "legacy Id", example = "ABC1234567")
  @Size(max = CMS_ID_LEN)
  private String legacyId;

  @JsonProperty("agency_type")
  @ApiModelProperty(required = true, value = "Cross Report to", example = "Law Enforcement")
  @NotEmpty
  private String agencyType;

  @JsonProperty("agency_name")
  @ApiModelProperty(required = true, value = "Agency Name", example = "Sheriff Department")
  @Size(max = 120)
  @NotEmpty
  private String agencyName;

  @JsonProperty("method")
  @ApiModelProperty(required = true,
      value = "Communication method system code ID e.g) 2097 -> Telephone Report", example = "2097")
  @NotNull
  @ValidSystemCodeId(required = true, category = SystemCodeCategoryId.CROSS_REPORT_METHOD)
  private Integer method;

  @JsonProperty("filed_out_of_state")
  @ApiModelProperty(required = false, value = "Cross Report was filed out of state", example = "N")

  private boolean filedOutOfState;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT)
  @Date
  @JsonProperty("inform_date")
  @ApiModelProperty(required = true, value = "yyyy-MM-dd", example = "2001-09-13")
  private String informDate;

  /**
   * Construct from all fields.
   * 
   * @param id primary key identifier
   * @param legacySourceTable - legacy source table name
   * @param legacyId - legacy Id
   * @param agencyType - Agency Type
   * @param agencyName - Agency Name
   * @param filedOutOfState - filedOutOfState
   * @param method - reporting method
   * @param informDate - reported date
   */
  public CrossReport(@JsonProperty("id") String id,
      @JsonProperty("legacy_source_table") String legacySourceTable,
      @JsonProperty("legacy_id") String legacyId, @JsonProperty("agency_type") String agencyType,
      @JsonProperty("agency_name") String agencyName,
      @JsonProperty("filed_out_of_state") boolean filedOutOfState,
      @JsonProperty("method") Integer method, @JsonProperty("inform_date") String informDate) {
    super();
    this.id = id;
    this.legacySourceTable = legacySourceTable;
    this.legacyId = legacyId;
    this.agencyType = agencyType;
    this.agencyName = agencyName;
    this.method = method;
    this.filedOutOfState = filedOutOfState;
    this.informDate = informDate;
  }

  /**
   * @return legacy source table name
   */
  public String getLegacySourceTable() {
    return legacySourceTable;
  }

  /**
   * @param legacySourceTable - the legacy source table name
   */
  public void setLegacySourceTable(String legacySourceTable) {
    this.legacySourceTable = legacySourceTable;
  }

  /**
   * @return legacy Id
   */
  public String getLegacyId() {
    return legacyId;
  }

  /**
   * @param legacyId - the legacy Id
   */
  public void setLegacyId(String legacyId) {
    this.legacyId = legacyId;
  }

  /**
   * @return id
   */
  public String getId() {
    return id;
  }

  /**
   * @param id - the id
   */
  public void setId(String id) {
    this.id = id;
  }

  /**
   * @return agencyType
   */
  public String getAgencyType() {
    return agencyType;
  }

  /**
   * @return agencyName
   */
  public String getAgencyName() {
    return agencyName;
  }

  /**
   * @return method
   */
  public Integer getMethod() {
    return method;
  }

  /**
   * @return filedOutOfState
   */
  public boolean isFiledOutOfState() {
    return filedOutOfState;
  }

  /**
   * @return informDate
   */
  public String getInformDate() {
    return informDate;
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
