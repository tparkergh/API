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
public class PostedStaffPerson extends StaffPerson {
  /**
   * Serialization version
   */
  private static final long serialVersionUID = 1L;
  @JsonProperty("id")
  private String id;

  /**
   * Constructor
   * 
   * @param staffPerson The persisted staffPerson
   */
  public PostedStaffPerson(gov.ca.cwds.data.persistence.cms.StaffPerson staffPerson) {
    super(staffPerson);

    if (StringUtils.isBlank(staffPerson.getId())) {
      throw new ServiceException("StaffPerson ID cannot be empty");
    }

    this.id = staffPerson.getId();
  }

  /**
   * @return the id
   */
  public String getId() {
    return id;
  }

}
