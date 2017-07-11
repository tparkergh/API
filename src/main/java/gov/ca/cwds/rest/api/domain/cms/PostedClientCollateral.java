package gov.ca.cwds.rest.api.domain.cms;

import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.services.ServiceException;

import org.apache.commons.lang3.StringUtils;

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
   * @return the id
   */
  public String getThirdId() {
    return thirdId;
  }


}
