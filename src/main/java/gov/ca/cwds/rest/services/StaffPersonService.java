package gov.ca.cwds.rest.services;

import gov.ca.cwds.data.Dao;
import gov.ca.cwds.data.cms.StaffPersonDao;
import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.domain.PostedStaffPerson;
import gov.ca.cwds.rest.api.domain.StaffPerson;
import gov.ca.cwds.rest.services.cms.StaffPersonIdRetriever;

import java.io.Serializable;

import org.apache.commons.lang3.NotImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

/**
 * Business layer object to work on {@link StaffPerson}
 * 
 * @author CWDS API Team
 */
public class StaffPersonService implements CrudsService {

  private static final Logger LOGGER = LoggerFactory.getLogger(StaffPersonService.class);

  private StaffPersonDao staffPersonDao;
  private StaffPersonIdRetriever staffPersonIdRetriever;

  /**
   * Constructor
   * 
   * @param staffPersonDao The {@link Dao} handling
   *        {@link gov.ca.cwds.data.persistence.cms.StaffPerson} objects.
   * @param staffPersonIdRetriever the staffPersonIdRetriever
   */
  @Inject
  public StaffPersonService(StaffPersonDao staffPersonDao,
      StaffPersonIdRetriever staffPersonIdRetriever) {
    this.staffPersonDao = staffPersonDao;
    this.staffPersonIdRetriever = staffPersonIdRetriever;
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#find(java.io.Serializable)
   */
  @Override
  public gov.ca.cwds.rest.api.domain.PostedStaffPerson find(Serializable primaryKey) {
    assert primaryKey instanceof String;
    gov.ca.cwds.data.persistence.cms.StaffPerson persistedStaffPerson =
        staffPersonDao.find(primaryKey);
    if (persistedStaffPerson != null) {
      return new PostedStaffPerson(persistedStaffPerson);
    }
    return null;
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#delete(java.io.Serializable)
   */
  @Override
  public gov.ca.cwds.rest.api.domain.cms.StaffPerson delete(Serializable primaryKey) {
    throw new NotImplementedException("Delete is not implemented");
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#create(gov.ca.cwds.rest.api.Request)
   */
  @Override
  public PostedStaffPerson create(Request request) {
    throw new NotImplementedException("Create is not implemented");
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#update(java.io.Serializable,
   *      gov.ca.cwds.rest.api.Request)
   */
  @Override
  public StaffPerson update(Serializable primaryKey, Request request) {
    throw new NotImplementedException("Update is not implemented");
  }

}
