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
public class PostedClientCollateral extends ClientCollateral {
  /**
   * Serialization version
   */
  private static final long serialVersionUID = 1L;

  @JsonProperty("thirdId")
  private String thirdId;

  /**
   * Constructor
   * 
   * @param clientCollateral The persisted Client Collateral
   * 
   */
  public PostedClientCollateral(gov.ca.cwds.data.persistence.cms.ClientCollateral clientCollateral) {
    super(clientCollateral);
    if (StringUtils.isBlank(clientCollateral.getThirdId())) {
      throw new ServiceException("ClientCollateral ID cannot be empty");
    }

    this.thirdId = clientCollateral.getThirdId();
  }

  /**
   * 
   * @param clientCollateral The domain Client Collateral
   * @param thirdId The Unique Identifier
   */
  public PostedClientCollateral(ClientCollateral clientCollateral, String thirdId) {
    super(clientCollateral.getActiveIndicator(), clientCollateral
        .getCollateralClientReporterRelationshipType(), clientCollateral.getCommentDescription(),
        clientCollateral.getClientId(), clientCollateral.getCollateralIndividualId());
    this.thirdId = thirdId;
  }

  /**
   * @return the id
   */
  public String getThirdId() {
    return thirdId;
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
