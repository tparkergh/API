package gov.ca.cwds.rest.api.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.jackson.JsonSnakeCase;
import io.swagger.annotations.ApiModelProperty;

@JsonSnakeCase
public class LegacyDescriptor {
  /**
   * Serialization version
   */
  private static final long serialVersionUID = 1L;

  @JsonProperty("legacy_id")
  @ApiModelProperty(required = false, readOnly = false, value = "Legacy Id", example = "12345")
  private String id;

  @ApiModelProperty(required = false, readOnly = false, value = "Legacy UI Id", example = "998765")
  @JsonProperty("legacy_ui_id")
  private String uiId;

  @ApiModelProperty(required = false, readOnly = false, value = "Legacy Last Updated Time", example = "2010-10-01T15:26:42.000-0700")
  @JsonProperty("legacy_last_updated")
  private String lastUpdated;

  @JsonProperty("legacy_table_name")
  @ApiModelProperty(required = false, readOnly = false, value = "Legacy Table Name", example = "client_t")
  private String tableName;

  @JsonProperty("legacy_table_description")
  @ApiModelProperty(required = false, readOnly = false, value = "Legacy Table Description", example = "Client")
  private String tableDescription;

  public LegacyDescriptor(){ }

  public LegacyDescriptor(String id, String uiId, String lastUpdated, String tableName,
      String tableDescription) {
    this.id = id;
    this.uiId = uiId;
    this.lastUpdated = lastUpdated;
    this.tableName = tableName;
    this.tableDescription = tableDescription;
  }

  public String getId() {
    return id;
  }

  public String getUiId() {
    return uiId;
  }

  public String getLastUpdated() {
    return lastUpdated;
  }

  public String getTableName() {
    return tableName;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    LegacyDescriptor that = (LegacyDescriptor) o;

    if (id != null ? !id.equals(that.id) : that.id != null) {
      return false;
    }
    if (uiId != null ? !uiId.equals(that.uiId) : that.uiId != null) {
      return false;
    }
    if (lastUpdated != null ? !lastUpdated.equals(that.lastUpdated) : that.lastUpdated != null) {
      return false;
    }
    if (tableName != null ? !tableName.equals(that.tableName) : that.tableName != null) {
      return false;
    }
    return !(tableDescription != null ? !tableDescription.equals(that.tableDescription)
        : that.tableDescription != null);

  }

  @Override
  public int hashCode() {
    int result = id != null ? id.hashCode() : 0;
    result = 31 * result + (uiId != null ? uiId.hashCode() : 0);
    result = 31 * result + (lastUpdated != null ? lastUpdated.hashCode() : 0);
    result = 31 * result + (tableName != null ? tableName.hashCode() : 0);
    result = 31 * result + (tableDescription != null ? tableDescription.hashCode() : 0);
    return result;
  }

  public String getTableDescription() {
    return tableDescription;
  }

}
