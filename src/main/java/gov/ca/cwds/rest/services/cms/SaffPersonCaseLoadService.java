package gov.ca.cwds.rest.services.cms;

import java.io.Serializable;

import com.google.inject.Inject;

import gov.ca.cwds.data.Dao;
import gov.ca.cwds.data.cms.StaffPersonCaseLoadDao;
import gov.ca.cwds.data.persistence.cms.StaffPersonCaseLoad;
import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.services.CrudsService;

/**
 * Business layer object to work on {@link StaffPersonCaseLoad}
 * 
 * @author CWDS API Team
 */
public class SaffPersonCaseLoadService implements CrudsService {

  private StaffPersonCaseLoadDao staffPersonCaseLoadDao;

  /**
   * 
   * @param staffPersonCaseLoadDao The {@link Dao} handling
   *        {@link gov.ca.cwds.data.persistence.cms.StaffPersonCaseLoad} objects.
   */
  @Inject
  public SaffPersonCaseLoadService(StaffPersonCaseLoadDao staffPersonCaseLoadDao) {
    this.staffPersonCaseLoadDao = staffPersonCaseLoadDao;
  }

  @Override
  public Response create(Request arg0) {
    return null;
  }

  @Override
  public Response delete(Serializable arg0) {
    return null;
  }

  @Override
  public Response find(Serializable primaryKey) {
    assert primaryKey instanceof String;
    gov.ca.cwds.data.persistence.cms.StaffPersonCaseLoad persistedStaffPersonCaseLoad =
        staffPersonCaseLoadDao.find(primaryKey);

    if (persistedStaffPersonCaseLoad != null) {
      return new gov.ca.cwds.rest.api.domain.cms.StaffPersonCaseLoad(persistedStaffPersonCaseLoad);
    }
    return null;
  }

  @Override
  public Response update(Serializable arg0, Request arg1) {
    return null;
  }

}
