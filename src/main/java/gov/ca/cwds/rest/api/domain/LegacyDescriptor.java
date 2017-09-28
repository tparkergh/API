package gov.ca.cwds.rest.api.domain;

import java.io.Serializable;

import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.validator.constraints.NotBlank;
import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.data.persistence.cms.CmsPersistentObject;
import io.dropwizard.jackson.JsonSnakeCase;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author CWDS API Team
 *
 */
@JsonSnakeCase
public class LegacyDescriptor implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  @JsonProperty("legacy_id")
  @Size(min = CmsPersistentObject.CMS_ID_LEN, max = CmsPersistentObject.CMS_ID_LEN)
  @ApiModelProperty(required = true, readOnly = false, value = "CWS/CMS Id", example = "1234567ABC")
  private String id;

  @ApiModelProperty(required = false, readOnly = false, value = "CWS/CMS user interface Id",
      example = "1111-2222-3333-4444555")
  @JsonProperty("legacy_ui_id")
  @Size(max = 19)
  private String uiId;

  @ApiModelProperty(required = false, readOnly = false, value = "CWS/CMS Last Updated Time",
      example = "2010-10-01T15:26:42.000-0700")
  @JsonProperty("legacy_last_updated")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
  private DateTime lastUpdatedAt;

  @JsonProperty("legacy_table_name")
  @NotBlank
  @ApiModelProperty(required = true, readOnly = false, value = "CWS/CMS Table Name",
      example = "CLIENT_T")
  private String tableName;

  @JsonProperty("legacy_table_description")
  @ApiModelProperty(required = false, readOnly = false, value = "CWS/CMS Table Description",
      example = "Client")
  private String tableDescription;

  /**
   * Default constructor
   */
  public LegacyDescriptor() {
    // no-opt
  }

  /**
   * @param id teh id
   * @param uiId the UiId
   * @param lastUpdated the lastupdatedTime
   * @param tableName the tableName
   * @param tableDescription the tableDescrption
   */
  public LegacyDescriptor(String id, String uiId, DateTime lastUpdated, String tableName,
      String tableDescription) {
    this.id = id;
    this.uiId = uiId;
    this.lastUpdatedAt = lastUpdated;
    this.tableName = tableName;
    this.tableDescription = tableDescription;
  }

  /**
   *
   * @return the Legacy Id
   */
  public String getId() {
    return id;
  }

  /**
   *
   * @param id Set the legacy Id
   */
  public void setId(String id) {
    this.id = id;
  }

  /**
   *
   * @return the Ui Id
   */
  public String getUiId() {
    return uiId;
  }

  /**
   *
   * @param uiId set the uiId
   */
  public void setUiId(String uiId) {
    this.uiId = uiId;
  }

  /**
   *
   * @return return the last updated time as a string
   */
  public DateTime getLastUpdated() {
    return lastUpdatedAt;
  }

  /**
   *
   * @param lastUpdated set the last updated time as a string
   */
  public void setLastUpdated(DateTime lastUpdated) {
    this.lastUpdatedAt = lastUpdated;
  }

  /**
   *
   * @return get legacy table name
   */
  public String getTableName() {
    return tableName;
  }

  /**
   *
   * @param tableName set legacy table name
   */
  public void setTableName(String tableName) {
    this.tableName = tableName;
  }

  /**
   *
   * @return get legacy table description
   */
  public String getTableDescription() {
    return tableDescription;
  }

  /**
   *
   * @param tableDescription set the legacy table description
   */
  public void setTableDescription(String tableDescription) {
    this.tableDescription = tableDescription;
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
