package gov.ca.cwds.rest.api.domain.cms;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.services.ServiceException;

/**
 * {@link Response} adding an id to the {@link Allegation}
 * 
 * @author CWDS API Team
 */
public class PostedAllegation extends Allegation {
  /**
   * Serialization version
   */
  private static final long serialVersionUID = 1L;
  @JsonProperty("id")
  private String id;

  /**
   * Constructor
   * 
   * @param allegation The persisted allegation
   */
  public PostedAllegation(gov.ca.cwds.data.persistence.cms.Allegation allegation) {
    super(allegation);
    if (StringUtils.isBlank(allegation.getId())) {
      throw new ServiceException("Allegation ID cannot be blank");
    }

    this.id = allegation.getId();
  }

  /**
   * @return the id
   */
  public String getId() {
    return id;
  }

}
