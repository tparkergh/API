package gov.ca.cwds.rest.services.cms;

import gov.ca.cwds.data.Dao;
import gov.ca.cwds.data.cms.ChildClientDao;
import gov.ca.cwds.data.persistence.cms.ChildClient;
import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.services.CrudsService;
import gov.ca.cwds.rest.services.ServiceException;

import java.io.Serializable;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

/**
 * Business layer object to work on {@link ChildClient}
 * 
 * @author CWDS API Team
 */
public class ChildClientService implements CrudsService {

  private static final Logger LOGGER = LoggerFactory.getLogger(ChildClientService.class);

  private ChildClientDao childClientDao;
  private StaffPersonIdRetriever staffPersonIdRetriever;
  private RIChildClient riChildClient;

  /**
   * Constructor
   * 
   * @param childClientDao The {@link Dao} handling
   *        {@link gov.ca.cwds.data.persistence.cms.ChildClient} objects.
   * @param staffPersonIdRetriever the staffPersonIdRetriever
   * @param riChildClient referential integrity checker
   */
  @Inject
  public ChildClientService(ChildClientDao childClientDao,
      StaffPersonIdRetriever staffPersonIdRetriever, RIChildClient riChildClient) {
    this.childClientDao = childClientDao;
    this.staffPersonIdRetriever = staffPersonIdRetriever;
    this.riChildClient = riChildClient;
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#create(gov.ca.cwds.rest.api.Request)
   */
  @Override
  public gov.ca.cwds.rest.api.domain.cms.ChildClient create(Request request) {
    assert request instanceof gov.ca.cwds.rest.api.domain.cms.ChildClient;

    gov.ca.cwds.rest.api.domain.cms.ChildClient childClient =
        (gov.ca.cwds.rest.api.domain.cms.ChildClient) request;

    if (childClient.getVictimClientId() == null) {
      LOGGER.info("ChildClient cannot be created with null or empty VictimClientId");
      throw new ServiceException("ChildClient cannot be created with null or empty VictimClientId");
    }

    try {
      String lastUpdatedId = staffPersonIdRetriever.getStaffPersonId();
      ChildClient managed =
          new ChildClient(childClient.getVictimClientId(), childClient, lastUpdatedId);
      managed = childClientDao.create(managed);
      return new gov.ca.cwds.rest.api.domain.cms.ChildClient(managed);
    } catch (EntityExistsException e) {
      LOGGER.info("ChildClient already exists : {}", childClient);
      throw new ServiceException("ChildClient already exists : {}" + childClient, e);
    }
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#find(java.io.Serializable)
   */
  @Override
  public gov.ca.cwds.rest.api.domain.cms.ChildClient find(Serializable primaryKey) {
    assert primaryKey instanceof String;

    gov.ca.cwds.data.persistence.cms.ChildClient persistedChildClient =
        childClientDao.find(primaryKey);
    if (persistedChildClient != null) {
      return new gov.ca.cwds.rest.api.domain.cms.ChildClient(persistedChildClient);
    }
    return null;
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#delete(java.io.Serializable)
   */
  @Override
  public gov.ca.cwds.rest.api.domain.cms.ChildClient delete(Serializable primaryKey) {
    assert primaryKey instanceof String;
    gov.ca.cwds.data.persistence.cms.ChildClient persistedChildClient =
        childClientDao.delete(primaryKey);
    if (persistedChildClient != null) {
      return new gov.ca.cwds.rest.api.domain.cms.ChildClient(persistedChildClient);
    }
    return null;
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#update(java.io.Serializable,
   *      gov.ca.cwds.rest.api.Request)
   */
  @Override
  public gov.ca.cwds.rest.api.domain.cms.ChildClient update(Serializable primaryKey, Request request) {
    assert request instanceof gov.ca.cwds.rest.api.domain.cms.ChildClient;
    gov.ca.cwds.rest.api.domain.cms.ChildClient childClient =
        (gov.ca.cwds.rest.api.domain.cms.ChildClient) request;

    try {
      String lastUpdatedId = staffPersonIdRetriever.getStaffPersonId();
      ChildClient managed =
          new ChildClient(childClient.getVictimClientId(), childClient, lastUpdatedId);
      managed = childClientDao.update(managed);
      return new gov.ca.cwds.rest.api.domain.cms.ChildClient(managed);
    } catch (EntityNotFoundException e) {
      LOGGER.info("childClient not found : {}", childClient);
      throw new ServiceException(e);
    }
  }

}
