package gov.ca.cwds.rest.api.domain;

import static gov.ca.cwds.data.persistence.cms.CmsPersistentObject.CMS_ID_LEN;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.validation.Date;
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
  @ApiModelProperty(required = true, value = "communication method", example = "phone")
  @NotEmpty
  private String method;

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
   * @param method - reporting method
   * @param informDate - reported date
   */
  public CrossReport(@JsonProperty("id") String id,
      @JsonProperty("legacy_source_table") String legacySourceTable,
      @JsonProperty("legacy_id") String legacyId, @JsonProperty("agency_type") String agencyType,
      @JsonProperty("agency_name") String agencyName, @JsonProperty("method") String method,
      @JsonProperty("inform_date") String informDate) {
    super();
    this.id = id;
    this.legacySourceTable = legacySourceTable;
    this.legacyId = legacyId;
    this.agencyType = agencyType;
    this.agencyName = agencyName;
    this.method = method;
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
  public String getMethod() {
    return method;
  }

  /**
   * @return informDate
   */
  public String getInformDate() {
    return informDate;
  }

  @Override
  public final int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((agencyName == null) ? 0 : agencyName.hashCode());
    result = prime * result + ((agencyType == null) ? 0 : agencyType.hashCode());
    result = prime * result + ((informDate == null) ? 0 : informDate.hashCode());
    result = prime * result + ((legacySourceTable == null) ? 0 : legacySourceTable.hashCode());
    result = prime * result + ((legacyId == null) ? 0 : legacyId.hashCode());
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    result = prime * result + ((method == null) ? 0 : method.hashCode());
    return result;
  }

  @Override
  public final boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (!(o instanceof CrossReport)) {
      return false;
    }

    CrossReport that = (CrossReport) o;

    if (id != null ? !id.equals(that.id) : that.id != null) {
      return false;
    }
    if (legacySourceTable != null ? !legacySourceTable.equals(that.legacySourceTable)
        : that.legacySourceTable != null) {
      return false;
    }
    if (legacyId != null ? !legacyId.equals(that.legacyId) : that.legacyId != null) {
      return false;
    }
    if (agencyType != null ? !agencyType.equals(that.agencyType) : that.agencyType != null) {
      return false;
    }
    if (agencyName != null ? !agencyName.equals(that.agencyName) : that.agencyName != null) {
      return false;
    }
    if (method != null ? !method.equals(that.method) : that.method != null) {
      return false;
    }
    return !(informDate != null ? !informDate.equals(that.informDate) : that.informDate != null);
  }

}
