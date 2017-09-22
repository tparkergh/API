package gov.ca.cwds.rest.api.domain.investigation;

import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
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
