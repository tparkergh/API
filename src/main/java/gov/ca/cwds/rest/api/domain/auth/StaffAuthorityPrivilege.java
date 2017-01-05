package gov.ca.cwds.rest.api.domain.auth;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.dropwizard.jackson.JsonSnakeCase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Class representing a Staff Person Authority Privilege.
 * 
 * @author CWDS API Team
 */
@ApiModel
@JsonSnakeCase
public class StaffAuthorityPrivilege {

  @ApiModelProperty(example = "Sensitive Persons")
  @JsonProperty("auth_privilege_type")
  @NotNull
  private String authPrivilegeType;

  @ApiModelProperty(example = "P")
  @JsonProperty("auth_privilege_code")
  @NotNull
  private String authPrivilegeCode;

  @ApiModelProperty(example = "Fresno")
  @JsonProperty("county")
  private String county;

  @ApiModelProperty(example = "CWS Case Management System")
  @JsonProperty("description")
  private String description;

  // /**
  // * JSON Constructor
  // *
  // * @param authPrivilegeType the authority privilege type
  // * @param authPrivilegeCode the authority privilege code
  // * @param county the county
  // */
  // public StaffAuthorityPrivilege(@JsonProperty("auth_privilege_type") String authPrivilegeType,
  // @JsonProperty("auth_privilege_code") String authPrivilegeCode,
  // @JsonProperty("county") String county) {
  // super();
  // this.authPrivilegeType = authPrivilegeType;
  // this.authPrivilegeCode = authPrivilegeCode;
  // this.county = county;
  // }

  /**
   * JSON Constructor
   * 
   * @param authPrivilegeType the authority privilege type
   * @param authPrivilegeCode the authority privilege code
   * @param county the county
   * @param description description of this privilege
   */
  public StaffAuthorityPrivilege(@JsonProperty("auth_privilege_type") String authPrivilegeType,
      @JsonProperty("auth_privilege_code") String authPrivilegeCode,
      @JsonProperty("county") String county, @JsonProperty("description") String description) {
    super();
    this.authPrivilegeType = authPrivilegeType;
    this.authPrivilegeCode = authPrivilegeCode;
    this.county = county;
    this.description = description;
  }

  /**
   * @return the authPrivilegeType
   */
  public String getAuthPrivilegeType() {
    return authPrivilegeType;
  }

  /**
   * @return the authPrivilegeCode
   */
  public String getAuthPrivilegeCode() {
    return authPrivilegeCode;
  }

  /**
   * @return the county
   */
  public String getCounty() {
    return county;
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((authPrivilegeCode == null) ? 0 : authPrivilegeCode.hashCode());
    result = prime * result + ((authPrivilegeType == null) ? 0 : authPrivilegeType.hashCode());
    result = prime * result + ((county == null) ? 0 : county.hashCode());
    result = prime * result + ((description == null) ? 0 : description.hashCode());
    return result;
  }

  /**
   * {@inheritDoc}
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

    StaffAuthorityPrivilege other = (StaffAuthorityPrivilege) obj;
    if (authPrivilegeCode == null) {
      if (other.authPrivilegeCode != null) {
        return false;
      }
    } else if (!authPrivilegeCode.equals(other.authPrivilegeCode)) {
      return false;
    }

    if (authPrivilegeType == null) {
      if (other.authPrivilegeType != null) {
        return false;
      }
    } else if (!authPrivilegeType.equals(other.authPrivilegeType)) {
      return false;
    }

    if (county == null) {
      if (other.county != null) {
        return false;
      }
    } else if (!county.equals(other.county)) {
      return false;
    }

    if (description == null) {
      if (other.description != null) {
        return false;
      }
    } else if (!description.equals(other.description)) {
      return false;
    }

    return true;
  }

  /**
   * Get Human readable description of privilege.
   * 
   * @return privilege description
   */
  public String getDescription() {
    return description;
  }

  /**
   * Set Human readable description of privilege.
   * 
   * @param description privilege description
   */
  public void setDescription(String description) {
    this.description = description;
  }

}
