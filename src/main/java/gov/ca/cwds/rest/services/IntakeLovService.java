package gov.ca.cwds.rest.services;

import java.util.stream.Collectors;

import com.google.inject.Inject;

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

  private IntakeLovDao dao;

  /**
   * Constructor
   * 
   * @param dao main DAO
   */
  @Inject
  public IntakeLovService(IntakeLovDao dao) {
    this.dao = dao;
  }

  @Override
  protected IntakeLovResponse handleRequest(IntakeLov req) {
    return new IntakeLovResponse(
        dao.findAll().stream().map(IntakeLov::new).collect(Collectors.toList()));
  }

  @Override
  protected IntakeLovResponse handleFind(String searchForThis) {
    try {
      return new IntakeLovResponse(
          dao.findAll().stream().map(IntakeLov::new).collect(Collectors.toList()));
    } catch (Exception e) {
      throw new ServiceException("Something went wrong ...", e);
    }
  }

}
