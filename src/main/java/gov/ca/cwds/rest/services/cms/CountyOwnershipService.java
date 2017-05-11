package gov.ca.cwds.rest.services.cms;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.Dao;
import gov.ca.cwds.data.cms.CountyOwnershipDao;
import gov.ca.cwds.data.persistence.cms.Client;
import gov.ca.cwds.data.persistence.cms.CountyOwnership;
import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.services.CrudsService;

/**
 * Business layer object to work on {@link CountyOwnershipService}
 * 
 * @author CWDS API Team
 */
public class CountyOwnershipService implements CrudsService {

  private static final Logger LOGGER = LoggerFactory.getLogger(CountyOwnershipService.class);

  private CountyOwnershipDao countyOwnershipDao;

  /**
   * Constructor
   * 
   * @param countyOwnershipDao The {@link Dao} handling
   *        {@link gov.ca.cwds.data.persistence.cms.CountyOwnership} objects.
   */
  @Inject
  public CountyOwnershipService(CountyOwnershipDao countyOwnershipDao) {
    this.countyOwnershipDao = countyOwnershipDao;

    System.out.println("***************adarsh");

  }

  /**
   * @param client - client
   * @return the client
   */
  public boolean createCountyFromClient(Client client) {
    CountyOwnership county = new CountyOwnership();
    county.setEntityId(client.getId());
    county.setEntityCode("C");

    CountyOwnership postedCounty = countyOwnershipDao.create(county);
    if (postedCounty != null)
      return true;
    else
      return false;
  }

  @Override
  public Response find(Serializable arg0) {
    return null;
  }

  @Override
  public Response delete(Serializable arg0) {
    return null;
  }

  @Override
  public Response create(Request arg0) {
    return null;
  }

  @Override
  public Response update(Serializable arg0, Request arg1) {
    return null;
  }

}
