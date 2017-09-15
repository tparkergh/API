package gov.ca.cwds.rest.api.domain.investigation;

import static gov.ca.cwds.data.persistence.cms.CmsPersistentObject.CMS_ID_LEN;

import java.util.Set;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.rest.api.domain.DomainObject;
import io.swagger.annotations.ApiModelProperty;

/**
 * {@link DomainObject} representing a ClientRelationship
 * 
 * @author CWDS API Team
 */
public class Relationship {

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

  @JsonProperty("first_name")
  @ApiModelProperty(required = false, readOnly = false, value = "first name")
  @Size(min = 1, max = 20)
  private String firstName;

  @JsonProperty("middle_name")
  @Size(min = 0, max = 20)
  @ApiModelProperty(required = true, readOnly = false, value = "middle name", example = "")
  private String MiddleName;

  @JsonProperty("last_name")
  @NotBlank
  @Size(min = 1, max = 25)
  @ApiModelProperty(required = true, readOnly = false, value = "last name", example = "last name")
  private String LastName;

  @JsonProperty("suffix_title")
  @NotNull
  @Size(max = 4)
  @ApiModelProperty(required = false, readOnly = false, value = "Suffix Title Description",
      example = "phd")
  private String SuffixTitle;

  @JsonProperty("prefix_title")
  @NotNull
  @Size(max = 6)
  @ApiModelProperty(required = false, readOnly = false, value = "Prefix Title Description",
      example = " ")
  private String prefixTitle;

  @JsonProperty("related_to")
  private Set<RelationshipTo> relatedTo;

  public Relationship() {
    super();
  }

  /**
   * @param tableName - CWS table name
   * @param id - CWS identifier
   * @param firstName - first name
   * @param middleName - middle name
   * @param lastName - last name
   * @param suffixTitle - suffix
   * @param prefixTitle - prefix
   * @param relatedTo - people related to this person
   */
  public Relationship(String tableName, String id, String firstName, String middleName,
      String lastName, String suffixTitle, String prefixTitle, Set<RelationshipTo> relatedTo) {
    super();
    this.tableName = tableName;
    this.id = id;
    this.firstName = firstName;
    MiddleName = middleName;
    LastName = lastName;
    SuffixTitle = suffixTitle;
    this.prefixTitle = prefixTitle;
    this.relatedTo = relatedTo;
  }

  /**
   * @return CWS table name
   */
  public String getTableName() {
    return tableName;
  }

  public String getId() {
    return id;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getMiddleName() {
    return MiddleName;
  }

  public String getLastName() {
    return LastName;
  }

  public String getSuffixTitle() {
    return SuffixTitle;
  }

  public String getPrefixTitle() {
    return prefixTitle;
  }

  public Set<RelationshipTo> getRelatedTo() {
    return relatedTo;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((LastName == null) ? 0 : LastName.hashCode());
    result = prime * result + ((MiddleName == null) ? 0 : MiddleName.hashCode());
    result = prime * result + ((SuffixTitle == null) ? 0 : SuffixTitle.hashCode());
    result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    result = prime * result + ((prefixTitle == null) ? 0 : prefixTitle.hashCode());
    result = prime * result + ((relatedTo == null) ? 0 : relatedTo.hashCode());
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
    Relationship other = (Relationship) obj;
    if (LastName == null) {
      if (other.LastName != null)
        return false;
    } else if (!LastName.equals(other.LastName))
      return false;
    if (MiddleName == null) {
      if (other.MiddleName != null)
        return false;
    } else if (!MiddleName.equals(other.MiddleName))
      return false;
    if (SuffixTitle == null) {
      if (other.SuffixTitle != null)
        return false;
    } else if (!SuffixTitle.equals(other.SuffixTitle))
      return false;
    if (firstName == null) {
      if (other.firstName != null)
        return false;
    } else if (!firstName.equals(other.firstName))
      return false;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    if (prefixTitle == null) {
      if (other.prefixTitle != null)
        return false;
    } else if (!prefixTitle.equals(other.prefixTitle))
      return false;
    if (relatedTo == null) {
      if (other.relatedTo != null)
        return false;
    } else if (!relatedTo.equals(other.relatedTo))
      return false;
    if (tableName == null) {
      if (other.tableName != null)
        return false;
    } else if (!tableName.equals(other.tableName))
      return false;
    return true;
  }


}
