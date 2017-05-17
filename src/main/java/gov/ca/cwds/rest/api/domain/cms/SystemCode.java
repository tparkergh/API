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
 * {@link DomainObject} representing a SystemCode
 * 
 * @author CWDS API Team
 */
@JsonSnakeCase
@ApiModel
public class SystemCode extends ReportingDomain implements Response {
  /**
   * Serialization version
   */
  private static final long serialVersionUID = 1L;

  @JsonProperty("system_id")
  @NotEmpty
  @Size(min = 1, max = 20)
  @ApiModelProperty(required = true, readOnly = false, value = "", example = "123")
  private Short systemId;

  @JsonProperty("category_id")
  @ApiModelProperty(required = false, readOnly = false, example = "0")
  private Short categoryId;

  @JsonProperty("inactive_indicator")
  @ApiModelProperty(required = false, readOnly = false, example = "N")
  private String inactiveIndicator;

  @JsonProperty("other_cd")
  @ApiModelProperty(required = false, readOnly = false, example = "A")
  private String otherCd;

  @JsonProperty("short_description")
  @ApiModelProperty(required = false, readOnly = false, example = "Amador")
  private String shortDescription;

  @JsonProperty("logical_id")
  @ApiModelProperty(required = false, readOnly = false, example = "03")
  private String logicalId;

  @JsonProperty("third_id")
  @ApiModelProperty(required = false, readOnly = false, example = "0000")
  private String thirdId;

  @JsonProperty("foreign_key_meta_table")
  @NotEmpty
  @Size(min = 1, max = 20)
  @ApiModelProperty(required = true, readOnly = false, value = "", example = "GVR_ENTC")
  private String foreignKeyMetaTable;

  @JsonProperty("long_description")
  private String longDescription;



  /**
   * Construct from all fields.
   * 
   * @param systemId - The System Id
   * @param categoryId - the category id
   * @param inactiveIndicator - inactive indicator
   * @param otherCd - other CD
   * @param shortDescription - the short description
   * @param logicalId - the logical Id
   * @param thirdId - third Id
   * @param foreignKeyMetaTable - - foreign key to system meta table (S_META_T)
   * @param longDescription - long description
   */
  @JsonCreator
  public SystemCode(@JsonProperty("system_id") Short systemId,
      @JsonProperty("category_id") Short categoryId,
      @JsonProperty("inactive_indicator") String inactiveIndicator,
      @JsonProperty("other_cd") String otherCd,
      @JsonProperty("short_description") String shortDescription,
      @JsonProperty("logical_id") String logicalId, @JsonProperty("third_id") String thirdId,
      @JsonProperty("foreign_key_meta_table") String foreignKeyMetaTable,
      @JsonProperty("long_description") String longDescription) {
    super();
    this.systemId = systemId;
    this.categoryId = categoryId;
    this.inactiveIndicator = inactiveIndicator;
    this.otherCd = otherCd;
    this.shortDescription = shortDescription;
    this.logicalId = logicalId;
    this.thirdId = thirdId;
    this.foreignKeyMetaTable = foreignKeyMetaTable;
    this.longDescription = longDescription;
  }

  @SuppressWarnings("javadoc")
  public SystemCode(gov.ca.cwds.data.persistence.cms.SystemCode persistedSystemCode) {
    this.systemId = persistedSystemCode.getSystemCode();
    this.categoryId = persistedSystemCode.getCategoryId();
    this.inactiveIndicator = persistedSystemCode.getInactiveIndicator();
    this.otherCd = persistedSystemCode.getOtherCd();
    this.shortDescription = persistedSystemCode.getShortDescription();
    this.logicalId = persistedSystemCode.getLogicalId();
    this.thirdId = persistedSystemCode.getThirdId();
    this.foreignKeyMetaTable = persistedSystemCode.getForeignKeyMetaTable();
    this.longDescription = persistedSystemCode.getLongDescription();
  }


  /**
   * @return the systemId
   */
  public Short getSystemId() {
    return systemId;
  }

  /**
   * @return the categoryId
   */
  public Short getCategoryId() {
    return categoryId;
  }

  /**
   * @return the inactiveIndicator
   */
  public String getInactiveIndicator() {
    return inactiveIndicator;
  }

  /**
   * @return the otherCd
   */
  public String getOtherCd() {
    return otherCd;
  }

  /**
   * @return the shortDescription
   */
  public String getShortDescription() {
    return shortDescription;
  }

  /**
   * @return the logicalId
   */
  public String getLogicalId() {
    return logicalId;
  }

  /**
   * @return the thirdId
   */
  public String getThirdId() {
    return thirdId;
  }

  /**
   * @return the foreignKeyMetaTable
   */
  public String getForeignKeyMetaTable() {
    return foreignKeyMetaTable;
  }

  /**
   * @return the longDescription
   */
  public String getLongDescription() {
    return longDescription;
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
    result = prime * result + ((categoryId == null) ? 0 : categoryId.hashCode());
    result = prime * result + ((foreignKeyMetaTable == null) ? 0 : foreignKeyMetaTable.hashCode());
    result = prime * result + ((inactiveIndicator == null) ? 0 : inactiveIndicator.hashCode());
    result = prime * result + ((logicalId == null) ? 0 : logicalId.hashCode());
    result = prime * result + ((longDescription == null) ? 0 : longDescription.hashCode());
    result = prime * result + ((otherCd == null) ? 0 : otherCd.hashCode());
    result = prime * result + ((shortDescription == null) ? 0 : shortDescription.hashCode());
    result = prime * result + ((systemId == null) ? 0 : systemId.hashCode());
    result = prime * result + ((thirdId == null) ? 0 : thirdId.hashCode());
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
    SystemCode other = (SystemCode) obj;
    if (categoryId == null) {
      if (other.categoryId != null) {
        return false;
      }
    } else if (!categoryId.equals(other.categoryId)) {
      return false;
    }
    if (foreignKeyMetaTable == null) {
      if (other.foreignKeyMetaTable != null) {
        return false;
      }
    } else if (!foreignKeyMetaTable.equals(other.foreignKeyMetaTable)) {
      return false;
    }
    if (inactiveIndicator == null) {
      if (other.inactiveIndicator != null) {
        return false;
      }
    } else if (!inactiveIndicator.equals(other.inactiveIndicator)) {
      return false;
    }
    if (logicalId == null) {
      if (other.logicalId != null) {
        return false;
      }
    } else if (!logicalId.equals(other.logicalId)) {
      return false;
    }
    if (longDescription == null) {
      if (other.longDescription != null) {
        return false;
      }
    } else if (!longDescription.equals(other.longDescription)) {
      return false;
    }
    if (otherCd == null) {
      if (other.otherCd != null) {
        return false;
      }
    } else if (!otherCd.equals(other.otherCd)) {
      return false;
    }
    if (shortDescription == null) {
      if (other.shortDescription != null) {
        return false;
      }
    } else if (!shortDescription.equals(other.shortDescription)) {
      return false;
    }
    if (systemId == null) {
      if (other.systemId != null) {
        return false;
      }
    } else if (!systemId.equals(other.systemId)) {
      return false;
    }
    if (thirdId == null) {
      if (other.thirdId != null) {
        return false;
      }
    } else if (!thirdId.equals(other.thirdId)) {
      return false;
    }
    return true;
  }



}
