package gov.ca.cwds.rest.api.domain;

import java.io.Serializable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

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
  @ApiModelProperty(required = false, readOnly = false, value = "Legacy Id", example = "12345")
  private String id;

  @ApiModelProperty(required = false, readOnly = false, value = "Legacy UI Id", example = "998765")
  @JsonProperty("legacy_ui_id")
  private String uiId;

  @ApiModelProperty(required = false, readOnly = false, value = "Legacy Last Updated Time",
      example = "2010-10-01T15:26:42.000-0700")
  @JsonProperty("legacy_last_updated")
  private String lastUpdated;

  @JsonProperty("legacy_table_name")
  @ApiModelProperty(required = false, readOnly = false, value = "Legacy Table Name",
      example = "client_t")
  private String tableName;

  @JsonProperty("legacy_table_description")
  @ApiModelProperty(required = false, readOnly = false, value = "Legacy Table Description",
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
  public LegacyDescriptor(String id, String uiId, String lastUpdated, String tableName,
      String tableDescription) {
    this.id = id;
    this.uiId = uiId;
    this.lastUpdated = lastUpdated;
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
  public String getLastUpdated() {
    return lastUpdated;
  }

  /**
   *
   * @param lastUpdated set the last updated time as a string
   */
  public void setLastUpdated(String lastUpdated) {
    this.lastUpdated = lastUpdated;
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
