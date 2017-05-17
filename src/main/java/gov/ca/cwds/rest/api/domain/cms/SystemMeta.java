package gov.ca.cwds.rest.api.domain.cms;

import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.DomainObject;
import gov.ca.cwds.rest.api.domain.ReportingDomain;
import io.dropwizard.jackson.JsonSnakeCase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * {@link DomainObject} representing a record from System Meta Table
 * 
 * @author CWDS API Team
 */
@JsonSnakeCase
@ApiModel
public class SystemMeta extends ReportingDomain implements Response {
  /**
   * Serialization version
   */
  private static final long serialVersionUID = 1L;

  @JsonProperty("logical_table_dsd_name")
  @NotEmpty
  @Size(min = 1, max = 8)
  @ApiModelProperty(required = true, readOnly = false, value = "", example = "GVC_ENTC")
  private String logicalTableDsdName;

  @JsonProperty("user_table_name")
  @NotEmpty
  @Size(min = 1, max = 40)
  @ApiModelProperty(required = true, readOnly = false, value = "",
      example = "Government Entity Type")
  private String userTableName;

  @JsonProperty("short_description_name")
  @NotEmpty
  @Size(min = 1, max = 40)
  @ApiModelProperty(required = true, readOnly = false, value = "", example = "Government Entity")
  private String shortDescriptionName;


  /**
   * Construct from all fields.
   * 
   * @param logicalTableDsdName the Logical Table DsdName
   * @param userTableName the UserTableName
   * @param shortDescriptionName the Short Description Name
   */
  @JsonCreator
  public SystemMeta(@JsonProperty("logical_table_dsd_name") String logicalTableDsdName,
      @JsonProperty("user_table_name") String userTableName,
      @JsonProperty("short_description_name") String shortDescriptionName) {
    super();
    this.logicalTableDsdName = logicalTableDsdName;
    this.userTableName = userTableName;
    this.shortDescriptionName = shortDescriptionName;
  }


  @SuppressWarnings("javadoc")
  public SystemMeta(gov.ca.cwds.data.persistence.cms.SystemMeta persistedSystemMeta) {
    this.logicalTableDsdName = persistedSystemMeta.getLogicalTableDsdName();
    this.userTableName = persistedSystemMeta.getUserTableName();
    this.shortDescriptionName = persistedSystemMeta.getShortDescriptionName();
  }


  /**
   * @return the logicalTableDsdName
   */
  public String getLogicalTableDsdName() {
    return logicalTableDsdName;
  }


  /**
   * @return the userTableName
   */
  public String getUserTableName() {
    return userTableName;
  }


  /**
   * @return the shortDescriptionName
   */
  public String getShortDescriptionName() {
    return shortDescriptionName;
  }


  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((logicalTableDsdName == null) ? 0 : logicalTableDsdName.hashCode());
    result =
        prime * result + ((shortDescriptionName == null) ? 0 : shortDescriptionName.hashCode());
    result = prime * result + ((userTableName == null) ? 0 : userTableName.hashCode());
    return result;
  }


  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    SystemMeta other = (SystemMeta) obj;
    if (logicalTableDsdName == null) {
      if (other.logicalTableDsdName != null) {
        return false;
      }
    } else if (!logicalTableDsdName.equals(other.logicalTableDsdName)) {
      return false;
    }
    if (shortDescriptionName == null) {
      if (other.shortDescriptionName != null) {
        return false;
      }
    } else if (!shortDescriptionName.equals(other.shortDescriptionName)) {
      return false;
    }
    if (userTableName == null) {
      if (other.userTableName != null) {
        return false;
      }
    } else if (!userTableName.equals(other.userTableName)) {
      return false;
    }
    return true;
  }



}
