package gov.ca.cwds.rest.api.domain.investigation;

import static gov.ca.cwds.data.persistence.cms.CmsPersistentObject.CMS_ID_LEN;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.rest.api.domain.DomainObject;
import gov.ca.cwds.rest.validation.Date;
import io.swagger.annotations.ApiModelProperty;

/**
 * {@link DomainObject} representing a ClientRelationship
 * 
 * @author CWDS API Team
 */
public class RelationshipTo {

  /**
   * Serialization version
   */
  private static final long serialVersionUID = 1L;

  @JsonProperty("table_name")
  @ApiModelProperty(required = true, readOnly = false, value = "CWS/CMS table name",
      example = "CLN_RELT")
  private String tableName;

  @JsonProperty("id")
  @ApiModelProperty(required = true, readOnly = false, value = "CWS/CMS identifier",
      example = "ABC1234567")
  @Size(min = CMS_ID_LEN, max = CMS_ID_LEN)
  private String id;

  @NotEmpty
  @JsonProperty("last_updated_by")
  @ApiModelProperty(required = false, readOnly = false, value = "Staff person ID", example = "0X5")
  private String lastUpdatedBy;

  @NotEmpty
  @JsonProperty("last_update_at")
  @gov.ca.cwds.rest.validation.Date(format = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", required = true)
  @ApiModelProperty(required = true, readOnly = false, value = "Last updated date/time",
      example = "2010-04-28T23:30:14.000Z")
  String lastUpdatedAt;

  @JsonProperty("related_table_name")
  @ApiModelProperty(required = true, readOnly = false, value = "CWS/CMS table name",
      example = "CLIENT_T")
  private String relatedTableName;

  @JsonProperty("related_id")
  @ApiModelProperty(required = true, readOnly = false, value = "CWS/CMS identifier",
      example = "ABC1234567")
  @Size(min = CMS_ID_LEN, max = CMS_ID_LEN)
  private String relatedId;

  @JsonProperty("related_first_name")
  @ApiModelProperty(required = false, readOnly = false, value = "first name")
  @Size(min = 1, max = 20)
  private String relatedFirstName;

  @JsonProperty("related_middle_name")
  @Size(min = 0, max = 20)
  @ApiModelProperty(required = true, readOnly = false, value = "middle name", example = "")
  private String relatedMiddleName;

  @JsonProperty("related_last_name")
  @NotBlank
  @Size(min = 1, max = 25)
  @ApiModelProperty(required = true, readOnly = false, value = "last name", example = "last name")
  private String relatedLastName;

  @JsonProperty("related_suffix_title")
  @NotNull
  @Size(max = 4)
  @ApiModelProperty(required = false, readOnly = false, value = "Suffix Title Description",
      example = "phd")
  private String relatedSuffixTitle;

  @JsonProperty("related_prefix_title")
  @NotNull
  @Size(max = 6)
  @ApiModelProperty(required = false, readOnly = false, value = "Prefix Title Description",
      example = " ")
  private String relatedPrefixTitle;

  @JsonProperty("relationship_type")
  @NotNull
  @ApiModelProperty(required = true, readOnly = false, value = "Relationship type code",
      example = "179")
  private Short relationshipType;

  @JsonProperty("relationship")
  @ApiModelProperty(required = true, readOnly = false,
      value = "the first party in the relationship")
  private String relationship;

  @JsonProperty("relationship_to_person")
  @ApiModelProperty(required = true, readOnly = false,
      value = "the second party in the relationship")
  private String relationshipToPerson;

  public RelationshipTo() {
    super();


  }

  public RelationshipTo(String tableName, String id, String lastUpdatedBy,
      @Date(format = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", required = true) String lastUpdatedAt,
      String relatedTableName, String relatedId, String relatedFirstName, String relatedMiddleName,
      String relatedLastName, String relatedSuffixTitle, String relatedPrefixTitle,
      Short relationshipType, String relationship, String relationshipToPerson) {
    super();
    this.tableName = tableName;
    this.id = id;
    this.lastUpdatedBy = lastUpdatedBy;
    this.lastUpdatedAt = lastUpdatedAt;
    this.relatedTableName = relatedTableName;
    this.relatedId = relatedId;
    this.relatedFirstName = relatedFirstName;
    this.relatedMiddleName = relatedMiddleName;
    this.relatedLastName = relatedLastName;
    this.relatedSuffixTitle = relatedSuffixTitle;
    this.relatedPrefixTitle = relatedPrefixTitle;
    this.relationshipType = relationshipType;
    this.relationship = relationship;
    this.relationshipToPerson = relationshipToPerson;
  }

  public static long getSerialversionuid() {
    return serialVersionUID;
  }

  public String getTableName() {
    return tableName;
  }

  public String getId() {
    return id;
  }

  public String getLastUpdatedBy() {
    return lastUpdatedBy;
  }

  public String getLastUpdatedAt() {
    return lastUpdatedAt;
  }

  public String getRelatedTableName() {
    return relatedTableName;
  }

  public String getRelatedId() {
    return relatedId;
  }

  public String getRelatedFirstName() {
    return relatedFirstName;
  }

  public String getRelatedMiddleName() {
    return relatedMiddleName;
  }

  public String getRelatedLastName() {
    return relatedLastName;
  }

  public String getRelatedSuffixTitle() {
    return relatedSuffixTitle;
  }

  public String getRelatedPrefixTitle() {
    return relatedPrefixTitle;
  }

  public Short getRelationshipType() {
    return relationshipType;
  }

  public String getRelationship() {
    return relationship;
  }

  public String getRelationshipToPerson() {
    return relationshipToPerson;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    result = prime * result + ((lastUpdatedAt == null) ? 0 : lastUpdatedAt.hashCode());
    result = prime * result + ((lastUpdatedBy == null) ? 0 : lastUpdatedBy.hashCode());
    result = prime * result + ((relatedFirstName == null) ? 0 : relatedFirstName.hashCode());
    result = prime * result + ((relatedId == null) ? 0 : relatedId.hashCode());
    result = prime * result + ((relatedLastName == null) ? 0 : relatedLastName.hashCode());
    result = prime * result + ((relatedMiddleName == null) ? 0 : relatedMiddleName.hashCode());
    result = prime * result + ((relatedPrefixTitle == null) ? 0 : relatedPrefixTitle.hashCode());
    result = prime * result + ((relatedSuffixTitle == null) ? 0 : relatedSuffixTitle.hashCode());
    result = prime * result + ((relatedTableName == null) ? 0 : relatedTableName.hashCode());
    result = prime * result + ((relationship == null) ? 0 : relationship.hashCode());
    result =
        prime * result + ((relationshipToPerson == null) ? 0 : relationshipToPerson.hashCode());
    result = prime * result + ((relationshipType == null) ? 0 : relationshipType.hashCode());
    result = prime * result + ((tableName == null) ? 0 : tableName.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    RelationshipTo other = (RelationshipTo) obj;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    if (lastUpdatedAt == null) {
      if (other.lastUpdatedAt != null)
        return false;
    } else if (!lastUpdatedAt.equals(other.lastUpdatedAt))
      return false;
    if (lastUpdatedBy == null) {
      if (other.lastUpdatedBy != null)
        return false;
    } else if (!lastUpdatedBy.equals(other.lastUpdatedBy))
      return false;
    if (relatedFirstName == null) {
      if (other.relatedFirstName != null)
        return false;
    } else if (!relatedFirstName.equals(other.relatedFirstName))
      return false;
    if (relatedId == null) {
      if (other.relatedId != null)
        return false;
    } else if (!relatedId.equals(other.relatedId))
      return false;
    if (relatedLastName == null) {
      if (other.relatedLastName != null)
        return false;
    } else if (!relatedLastName.equals(other.relatedLastName))
      return false;
    if (relatedMiddleName == null) {
      if (other.relatedMiddleName != null)
        return false;
    } else if (!relatedMiddleName.equals(other.relatedMiddleName))
      return false;
    if (relatedPrefixTitle == null) {
      if (other.relatedPrefixTitle != null)
        return false;
    } else if (!relatedPrefixTitle.equals(other.relatedPrefixTitle))
      return false;
    if (relatedSuffixTitle == null) {
      if (other.relatedSuffixTitle != null)
        return false;
    } else if (!relatedSuffixTitle.equals(other.relatedSuffixTitle))
      return false;
    if (relatedTableName == null) {
      if (other.relatedTableName != null)
        return false;
    } else if (!relatedTableName.equals(other.relatedTableName))
      return false;
    if (relationship == null) {
      if (other.relationship != null)
        return false;
    } else if (!relationship.equals(other.relationship))
      return false;
    if (relationshipToPerson == null) {
      if (other.relationshipToPerson != null)
        return false;
    } else if (!relationshipToPerson.equals(other.relationshipToPerson))
      return false;
    if (relationshipType == null) {
      if (other.relationshipType != null)
        return false;
    } else if (!relationshipType.equals(other.relationshipType))
      return false;
    if (tableName == null) {
      if (other.tableName != null)
        return false;
    } else if (!tableName.equals(other.tableName))
      return false;
    return true;
  }

}
