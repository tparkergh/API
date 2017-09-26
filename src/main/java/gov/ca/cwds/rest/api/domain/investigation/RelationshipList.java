package gov.ca.cwds.rest.api.domain.investigation;

import java.util.Set;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;

/**
 * 
 * @author CWDS API Team
 */
@JsonInclude(Include.ALWAYS)
public class RelationshipList implements Request, Response {

  private static final long serialVersionUID = 1L;

  @JsonProperty("relationships")
  private Set<Relationship> relationshipList;

  /**
   * 
   */
  public RelationshipList() {

  }

  /**
   * @param relationshipList - list of relationships of people for investigation
   */
  public RelationshipList(Set<Relationship> relationshipList) {
    this.relationshipList = relationshipList;
  }

  /**
   * @return - list of relationships of people for investigation
   */
  public Set<Relationship> getRelationship() {
    return relationshipList;
  }

  @Override
  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this, false);
  }

  @Override
  public boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj, false);
  }

}
