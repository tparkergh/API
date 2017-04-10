package gov.ca.cwds.rest.api.domain.cms;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.services.ServiceException;

/**
 * {@link Response} adding an id to the {@link StaffPerson}
 * 
 * @author CWDS API Team
 */
public class PostedReporter extends Reporter {
  /**
   * Serialization version
   */
  private static final long serialVersionUID = 1L;
  @JsonProperty("id")
  private String id;

  /**
   * Default Constructor
   */
  public PostedReporter() {
    /**
     * default constructor
     * 
     */
  }

  /**
   * Constructor
   * 
   * @param reporter The persisted reporter
   */
  public PostedReporter(gov.ca.cwds.data.persistence.cms.Reporter reporter) {
    super(reporter);
    if (StringUtils.isBlank(reporter.getReferralId())) {
      throw new ServiceException("Referral ID cannot be empty for Reporter");
    }

    this.id = reporter.getReferralId();
  }

  /**
   * @return the id
   */
  public String getId() {
    return id;
  }

}
