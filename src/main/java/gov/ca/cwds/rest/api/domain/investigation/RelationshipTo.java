package gov.ca.cwds.rest.api.domain.investigation;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.rest.api.domain.DomainObject;
import gov.ca.cwds.rest.api.domain.LegacyDescriptor;
import io.swagger.annotations.ApiModelProperty;

/**
 * {@link DomainObject} representing a ClientRelationship
 * 
 * @author CWDS API Team
 */
public final class RelationshipTo {

  /**
   * Serialization version
   */
  private static final long serialVersionUID = 1L;

  @JsonProperty("related_person_first_name")
  @ApiModelProperty(required = false, readOnly = false, value = "first name")
  @Size(min = 1, max = 20)
  private String relatedFirstName;

  @JsonProperty("related_person_last_name")
  @NotBlank
  @Size(min = 1, max = 25)
  @ApiModelProperty(required = true, readOnly = false, value = "last name", example = "last name")
  private String relatedLastName;

  @JsonProperty("indexed_person_relationship")
  @ApiModelProperty(required = true, readOnly = false, value = "relationship to the person")
  private String relationshipToPerson;

  @JsonProperty("relationship_context")
  @ApiModelProperty(required = true, readOnly = false, value = "context of the relationship")
  private String relationshipContext;

  @JsonProperty("related_person_relationship")
  @ApiModelProperty(required = true, readOnly = false, value = "persons relationship to")
  private String relatedPersonRelationship;

  @JsonProperty("legacy_descriptor")
  private LegacyDescriptor legacyDescriptor;

  /**
   * empty constructor
   */
  public RelationshipTo() {
    super();

  }

  /**
   * @param relatedFirstName - related persons first name
   * @param relatedLastName - related persons last name
   * @param relationshipToPerson - relation of owning person
   * @param relationshipContext - context information
   * @param relatedPersonRelationship - relation to owning person
   * @param legacyDescriptor - CMS record description
   */
  public RelationshipTo(String relatedFirstName, String relatedLastName,
      String relationshipToPerson, String relationshipContext, String relatedPersonRelationship,
      LegacyDescriptor legacyDescriptor) {
    super();
    this.relatedFirstName = relatedFirstName;
    this.relatedLastName = relatedLastName;
    this.relationshipToPerson = relationshipToPerson;
    this.relationshipContext = relationshipContext;
    this.relatedPersonRelationship = relatedPersonRelationship;
    this.legacyDescriptor = legacyDescriptor;
  }


  public String getRelatedFirstName() {
    return relatedFirstName;
  }


  public String getRelatedLastName() {
    return relatedLastName;
  }


  public String getRelationshipToPerson() {
    return relationshipToPerson;
  }


  public String getRelationshipContext() {
    return relationshipContext;
  }


  public String getRelatedPersonRelationship() {
    return relatedPersonRelationship;
  }


  public LegacyDescriptor getLegacyDescriptor() {
    return legacyDescriptor;
  }


  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((legacyDescriptor == null) ? 0 : legacyDescriptor.hashCode());
    result = prime * result + ((relatedFirstName == null) ? 0 : relatedFirstName.hashCode());
    result = prime * result + ((relatedLastName == null) ? 0 : relatedLastName.hashCode());
    result = prime * result
        + ((relatedPersonRelationship == null) ? 0 : relatedPersonRelationship.hashCode());
    result = prime * result + ((relationshipContext == null) ? 0 : relationshipContext.hashCode());
    result =
        prime * result + ((relationshipToPerson == null) ? 0 : relationshipToPerson.hashCode());
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
    if (legacyDescriptor == null) {
      if (other.legacyDescriptor != null)
        return false;
    } else if (!legacyDescriptor.equals(other.legacyDescriptor))
      return false;
    if (relatedFirstName == null) {
      if (other.relatedFirstName != null)
        return false;
    } else if (!relatedFirstName.equals(other.relatedFirstName))
      return false;
    if (relatedLastName == null) {
      if (other.relatedLastName != null)
        return false;
    } else if (!relatedLastName.equals(other.relatedLastName))
      return false;
    if (relatedPersonRelationship == null) {
      if (other.relatedPersonRelationship != null)
        return false;
    } else if (!relatedPersonRelationship.equals(other.relatedPersonRelationship))
      return false;
    if (relationshipContext == null) {
      if (other.relationshipContext != null)
        return false;
    } else if (!relationshipContext.equals(other.relationshipContext))
      return false;
    if (relationshipToPerson == null) {
      if (other.relationshipToPerson != null)
        return false;
    } else if (!relationshipToPerson.equals(other.relationshipToPerson))
      return false;
    return true;
  }

}
