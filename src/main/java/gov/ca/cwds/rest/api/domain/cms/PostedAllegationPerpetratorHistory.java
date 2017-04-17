package gov.ca.cwds.rest.api.domain.cms;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.services.ServiceException;

/**
 * {@link Response} adding an id to the {@link AllegationPerpetratorHistory}
 * 
 * @author CWDS API Team
 */
public class PostedAllegationPerpetratorHistory extends AllegationPerpetratorHistory {
  /**
   * Serialization version
   */
  private static final long serialVersionUID = 1L;
  @JsonProperty("id")
  private String id;

  /**
   * Constructor
   * 
   * @param allegationPerpetratorHistory The persisted allegationPerpetratorHistory
   */
  public PostedAllegationPerpetratorHistory(
      gov.ca.cwds.data.persistence.cms.AllegationPerpetratorHistory allegationPerpetratorHistory) {
    super(allegationPerpetratorHistory);
    if (StringUtils.isBlank(allegationPerpetratorHistory.getId())) {
      throw new ServiceException("AllegationPerpetratorHistory ID cannot be blank");
    }

    this.id = allegationPerpetratorHistory.getId();
  }

  /**
   * @return the id
   */
  public String getId() {
    return id;
  }

}
