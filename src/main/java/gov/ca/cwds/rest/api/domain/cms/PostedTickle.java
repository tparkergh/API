package gov.ca.cwds.rest.api.domain.cms;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.services.ServiceException;

/**
 * {@link Response} adding an id to the {@link Tickle}
 * 
 * @author CWDS API Team
 */
public class PostedTickle extends Tickle {

  /**
   * Serialization version
   */
  private static final long serialVersionUID = 1L;
  @JsonProperty("id")
  private String id;

  /**
   * Constructor
   * 
   * @param tickle The persisted tickle
   */
  public PostedTickle(gov.ca.cwds.data.persistence.cms.Tickle tickle) {
    super(tickle);
    if (StringUtils.isBlank(tickle.getId())) {
      throw new ServiceException("tickle ID cannot be blank");
    }

    this.id = tickle.getId();
  }

  /**
   * @return the id
   */
  public String getId() {
    return id;
  }

}
