package gov.ca.cwds.rest.api.domain.investigation;

import static gov.ca.cwds.data.persistence.cms.CmsPersistentObject.CMS_ID_LEN;

import java.util.Set;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.rest.api.domain.DomainObject;
import gov.ca.cwds.rest.api.domain.LegacyDescriptor;
import gov.ca.cwds.rest.validation.Date;
import io.swagger.annotations.ApiModelProperty;

/**
 * {@link DomainObject} representing a ClientRelationship
 * 
 * @author CWDS API Team
 */
public class Relationship {

  private static final long serialVersionUID = 1L;

  @JsonProperty("id")
  @ApiModelProperty(required = true, readOnly = false, value = "CWS/CMS identifier",
      example = "ABC1234567")
  @Size(min = CMS_ID_LEN, max = CMS_ID_LEN)
  private String id;

  @JsonProperty("date_of_birth")
  @gov.ca.cwds.rest.validation.Date(format = gov.ca.cwds.rest.api.domain.DomainObject.DATE_FORMAT,
      required = false)
  @ApiModelProperty(required = true, readOnly = false, value = "date of birth",
      example = "1999-10-01")
  private String dateOfBirth;

  @JsonProperty("first_name")
  @ApiModelProperty(required = false, readOnly = false, value = "first name")
  @Size(min = 1, max = 20)
  private String firstName;

  @JsonProperty("middle_name")
  @Size(min = 0, max = 20)
  @ApiModelProperty(required = true, readOnly = false, value = "middle name", example = "")
  private String middleName;

  @JsonProperty("last_name")
  @NotBlank
  @Size(min = 1, max = 25)
  @ApiModelProperty(required = true, readOnly = false, value = "last name", example = "last name")
  private String lastName;

  @JsonProperty("name_suffix")
  @NotNull
  @Size(max = 4)
  @ApiModelProperty(required = false, readOnly = false, value = "Suffix Title Description",
      example = "phd")
  private String suffixName;

  @JsonProperty("sensitive")
  @NotNull
  @ApiModelProperty(required = true, readOnly = false, value = "sensitive", example = "false")
  private Boolean sensitive;

  @JsonProperty("sealed")
  @NotNull
  @ApiModelProperty(required = true, readOnly = false, value = "sealed", example = "false")
  private Boolean sealed;

  @JsonProperty("legacy_descirptor")
  private LegacyDescriptor legacyDescriptor;

  @JsonProperty("relationship_to")
  private Set<RelationshipTo> relatedTo;

  /**
   * 
   */
  public Relationship() {
    super();
  }


  /**
   * @param id - id
   * @param dateOfBirth - date of birth
   * @param firstName - first anme
   * @param middleName - middle name
   * @param lastName - last name
   * @param suffixName - suffix
   * @param sensitive - sensitive data
   * @param sealed - sealed data
   * @param legacyDescriptor - CMS record description
   * @param relatedTo - people realated to this person
   */
  public Relationship(String id, @Date(format = "yyyy-MM-dd", required = false) String dateOfBirth,
      String firstName, String middleName, String lastName, String suffixName, Boolean sensitive,
      Boolean sealed, LegacyDescriptor legacyDescriptor, Set<RelationshipTo> relatedTo) {
    super();
    this.id = id;
    this.dateOfBirth = dateOfBirth;
    this.firstName = firstName;
    this.middleName = middleName;
    this.lastName = lastName;
    this.suffixName = suffixName;
    this.sensitive = sensitive;
    this.sealed = sealed;
    this.legacyDescriptor = legacyDescriptor;
    this.relatedTo = relatedTo;
  }


  public String getId() {
    return id;
  }

  public String getDateOfBirth() {
    return dateOfBirth;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getMiddleName() {
    return middleName;
  }

  public String getLastName() {
    return lastName;
  }

  public String getSuffixName() {
    return suffixName;
  }

  public Boolean getSensitive() {
    return sensitive;
  }

  public Boolean getSealed() {
    return sealed;
  }

  public LegacyDescriptor getLegacyDescriptor() {
    return legacyDescriptor;
  }

  public Set<RelationshipTo> getRelatedTo() {
    return relatedTo;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((dateOfBirth == null) ? 0 : dateOfBirth.hashCode());
    result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
    result = prime * result + ((legacyDescriptor == null) ? 0 : legacyDescriptor.hashCode());
    result = prime * result + ((middleName == null) ? 0 : middleName.hashCode());
    result = prime * result + ((relatedTo == null) ? 0 : relatedTo.hashCode());
    result = prime * result + ((sealed == null) ? 0 : sealed.hashCode());
    result = prime * result + ((sensitive == null) ? 0 : sensitive.hashCode());
    result = prime * result + ((suffixName == null) ? 0 : suffixName.hashCode());
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
    if (dateOfBirth == null) {
      if (other.dateOfBirth != null)
        return false;
    } else if (!dateOfBirth.equals(other.dateOfBirth))
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
    if (lastName == null) {
      if (other.lastName != null)
        return false;
    } else if (!lastName.equals(other.lastName))
      return false;
    if (legacyDescriptor == null) {
      if (other.legacyDescriptor != null)
        return false;
    } else if (!legacyDescriptor.equals(other.legacyDescriptor))
      return false;
    if (middleName == null) {
      if (other.middleName != null)
        return false;
    } else if (!middleName.equals(other.middleName))
      return false;
    if (relatedTo == null) {
      if (other.relatedTo != null)
        return false;
    } else if (!relatedTo.equals(other.relatedTo))
      return false;
    if (sealed == null) {
      if (other.sealed != null)
        return false;
    } else if (!sealed.equals(other.sealed))
      return false;
    if (sensitive == null) {
      if (other.sensitive != null)
        return false;
    } else if (!sensitive.equals(other.sensitive))
      return false;
    if (suffixName == null) {
      if (other.suffixName != null)
        return false;
    } else if (!suffixName.equals(other.suffixName))
      return false;
    return true;
  }


}
