package gov.ca.cwds.rest.api.domain.cms;

import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.services.ServiceException;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * {@link Response} adding an id to the {@link ClientRelationship}
 * 
 * @author CWDS API Team
 */
public class PostedClientRelationship extends ClientRelationship {
  /**
   * Serialization version
   */
  private static final long serialVersionUID = 1L;

  @JsonProperty("id")
  private String id;

  /**
   * Constructor
   * 
   * @param clientRelationship The persisted Client Relationship
   * 
   */
  public PostedClientRelationship(
      gov.ca.cwds.data.persistence.cms.ClientRelationship clientRelationship) {
    super(clientRelationship);
    if (StringUtils.isBlank(clientRelationship.getId())) {
      throw new ServiceException("ClientRelationship ID cannot be empty");
    }

    this.id = clientRelationship.getId();
  }

  /**
   * Constructor
   * 
   * @param clientRelationship The domain Client Relationship
   * @param id The Unique Identifier
   * 
   */
  public PostedClientRelationship(ClientRelationship clientRelationship, String id) {
    super(clientRelationship.getAbsentParentCode(), clientRelationship.getClientRelationshipType(),
        clientRelationship.getEndDate(), clientRelationship.getSecondaryClientId(),
        clientRelationship.getPrimaryClientId(), clientRelationship.getSameHomeCode(),
        clientRelationship.getStartDate());
    if (StringUtils.isBlank(id)) {
      throw new ServiceException("ClientRelationship ID cannot be empty");
    }

    this.id = id;
  }

  /**
   * @return the id
   */
  public String getId() {
    return id;
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this, false);
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj, false);
  }

}
