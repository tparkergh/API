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

  private static final long serialVersionUID = 1183360430821640119L;

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
   * @return the id
   */
  public String getId() {
    return id;
  }

  /**
   * @return the uiId
   */
  public String getUiId() {
    return uiId;
  }

  /**
   * @return the lastUpdated
   */
  public String getLastUpdated() {
    return lastUpdated;
  }

  /**
   * @return the tableName
   */
  public String getTableName() {
    return tableName;
  }

  /**
   * @return the tableDescription
   */
  public String getTableDescription() {
    return tableDescription;
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
