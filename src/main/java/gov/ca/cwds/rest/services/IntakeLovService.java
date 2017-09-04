package gov.ca.cwds.rest.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.google.inject.name.Named;

import gov.ca.cwds.data.ns.IntakeLovDao;
import gov.ca.cwds.rest.api.domain.IntakeLov;
import gov.ca.cwds.rest.api.domain.IntakeLovResponse;
import gov.ca.cwds.rest.resources.SimpleResourceService;

/**
 * Business service for Intake LOV listings.
 * 
 * @author CWDS API Team
 */
public class IntakeLovService extends SimpleResourceService<String, IntakeLov, IntakeLovResponse> {

  private static final Logger LOGGER = LoggerFactory.getLogger(IntakeLovService.class);

  private IntakeLovDao dao;

  /**
   * Constructor
   * 
   * @param dao main DAO
   */
  @Inject
  public IntakeLovService(@Named("people.index") IntakeLovDao dao) {
    this.dao = dao;
  }

  @Override
  protected IntakeLovResponse handleRequest(IntakeLov req) {
    // return callDao(searchTerm);
    return null;
  }

  @Override
  protected IntakeLovResponse handleFind(String searchForThis) {
    try {
      // return dao.findAll();
      return null;
    } catch (Exception e) {
      throw new ServiceException("Something went wrong ...", e);
    }
  }

}
