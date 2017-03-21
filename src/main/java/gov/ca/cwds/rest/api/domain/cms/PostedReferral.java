package gov.ca.cwds.rest.api.domain.cms;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.services.ServiceException;

/**
 * {@link Response} adding an id to the {@link Referral}
 * 
 * @author CWDS API Team
 */
public class PostedReferral extends Referral {
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  @JsonProperty("id")
  private String id;

  /**
   * Constructor
   * 
   * @param referral The persisted referral
   */
  public PostedReferral(gov.ca.cwds.data.persistence.cms.Referral referral) {

    super(referral);

    if (StringUtils.isBlank(referral.getId())) {
      throw new ServiceException("Referral ID cannot be empty");
    }

    this.id = referral.getId();
  }

  /**
   * @return the id
   */
  public String getId() {
    return id;
  }


}
