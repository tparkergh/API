package gov.ca.cwds.rest.api.domain.cms;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.services.ServiceException;

/**
 * {@link Response} adding an id to the {@link Client}
 * 
 * @author CWDS API Team
 */
public class PostedAddress extends Address {
  /**
   * Serialization version
   */
  private static final long serialVersionUID = 1L;
  @JsonProperty("id")
  private String id;


  /*
   * Constuctor
   */
  /**
   * @param address - persistent Address object
   * @param isExist - ID already exist
   */
  public PostedAddress(gov.ca.cwds.data.persistence.cms.Address address, boolean isExist) {

    super(address, isExist);

    if (StringUtils.isBlank(address.getId())) {
      throw new ServiceException("Address ID cannot be empty");
    }

    this.id = address.getId();

  }
}
