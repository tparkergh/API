package gov.ca.cwds.rest.api.domain.cms;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.services.ServiceException;

/**
 * {@link Response} adding an id to the {@link LongText}
 * 
 * @author CWDS API Team
 */
public class PostedLongText extends LongText {

  /**
   * Serialization version
   */
  private static final long serialVersionUID = 1L;
  @JsonProperty("id")
  private String id;

  /**
   * Constructor
   * 
   * @param longText The persisted longText
   */
  public PostedLongText(gov.ca.cwds.data.persistence.cms.LongText longText) {
    super(longText);
    if (StringUtils.isBlank(longText.getId())) {
      throw new ServiceException("LongText ID cannot be blank");
    }

    this.id = longText.getId();
  }

  /**
   * @return the id
   */
  public String getId() {
    return id;
  }

}
