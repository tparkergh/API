package gov.ca.cwds.rest.services.cms;

import java.io.Serializable;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.Dao;
import gov.ca.cwds.data.cms.AllegationPerpetratorHistoryDao;
import gov.ca.cwds.data.persistence.cms.AllegationPerpetratorHistory;
import gov.ca.cwds.data.persistence.cms.CmsKeyIdGenerator;
import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.domain.cms.PostedAllegationPerpetratorHistory;
import gov.ca.cwds.rest.services.CrudsService;
import gov.ca.cwds.rest.services.ServiceException;

/**
 * Business layer object to work on {@link AllegationPerpetratorHistory}
 * 
 * @author CWDS API Team
 */
public class AllegationPerpetratorHistoryService implements CrudsService {

  private static final Logger LOGGER =
      LoggerFactory.getLogger(AllegationPerpetratorHistoryService.class);

  private AllegationPerpetratorHistoryDao allegationPerpetratorHistoryDao;

  /**
   * Constructor
   * 
   * @param allegationPerpetratorHistoryDao The {@link Dao} handling
   *        {@link gov.ca.cwds.data.persistence.cms.AllegationPerpetratorHistory} objects.
   */
  @Inject
  public AllegationPerpetratorHistoryService(
      AllegationPerpetratorHistoryDao allegationPerpetratorHistoryDao) {
    this.allegationPerpetratorHistoryDao = allegationPerpetratorHistoryDao;
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#find(java.io.Serializable)
   */
  @Override
  public gov.ca.cwds.rest.api.domain.cms.AllegationPerpetratorHistory find(
      Serializable primaryKey) {
    assert primaryKey instanceof String;

    gov.ca.cwds.data.persistence.cms.AllegationPerpetratorHistory persistedAllegationPerpetratorHistory =
        allegationPerpetratorHistoryDao.find(primaryKey);
    if (persistedAllegationPerpetratorHistory != null) {
      return new gov.ca.cwds.rest.api.domain.cms.AllegationPerpetratorHistory(
          persistedAllegationPerpetratorHistory);
    }
    return null;
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#delete(java.io.Serializable)
   */
  @Override
  public gov.ca.cwds.rest.api.domain.cms.AllegationPerpetratorHistory delete(
      Serializable primaryKey) {
    assert primaryKey instanceof String;
    gov.ca.cwds.data.persistence.cms.AllegationPerpetratorHistory persistedAllegationPerpetratorHistory =
        allegationPerpetratorHistoryDao.delete(primaryKey);
    if (persistedAllegationPerpetratorHistory != null) {
      return new gov.ca.cwds.rest.api.domain.cms.AllegationPerpetratorHistory(
          persistedAllegationPerpetratorHistory);
    }
    return null;
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#create(gov.ca.cwds.rest.api.Request)
   */
  @Override
  public PostedAllegationPerpetratorHistory create(Request request) {
    assert request instanceof gov.ca.cwds.rest.api.domain.cms.AllegationPerpetratorHistory;

    gov.ca.cwds.rest.api.domain.cms.AllegationPerpetratorHistory allegationPerpetratorHistory =
        (gov.ca.cwds.rest.api.domain.cms.AllegationPerpetratorHistory) request;

    try {
      // TODO : refactor to actually determine who is updating. 'q1p' for now - #136737071 - Tech
      // Debt: Legacy Service classes must use Staff ID for last update ID value
      AllegationPerpetratorHistory managed = new AllegationPerpetratorHistory(
          CmsKeyIdGenerator.cmsIdGenertor(null), allegationPerpetratorHistory, "q1p");
      managed = allegationPerpetratorHistoryDao.create(managed);
      return new PostedAllegationPerpetratorHistory(managed);
    } catch (EntityExistsException e) {
      LOGGER.info("AllegationPerpetratorHistory already exists : {}", allegationPerpetratorHistory);
      throw new ServiceException(e);
    }
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#update(java.io.Serializable,
   *      gov.ca.cwds.rest.api.Request)
   */
  @Override
  public gov.ca.cwds.rest.api.domain.cms.AllegationPerpetratorHistory update(
      Serializable primaryKey, Request request) {
    assert primaryKey instanceof String;
    assert request instanceof gov.ca.cwds.rest.api.domain.cms.AllegationPerpetratorHistory;
    gov.ca.cwds.rest.api.domain.cms.AllegationPerpetratorHistory allegationPerpetratorHistory =
        (gov.ca.cwds.rest.api.domain.cms.AllegationPerpetratorHistory) request;

    try {
      AllegationPerpetratorHistory managed = new AllegationPerpetratorHistory((String) primaryKey,
          allegationPerpetratorHistory, "q1p");
      managed = allegationPerpetratorHistoryDao.update(managed);
      return new gov.ca.cwds.rest.api.domain.cms.AllegationPerpetratorHistory(managed);
    } catch (EntityNotFoundException e) {
      LOGGER.info("AllegationPerpetratorHistory not found : {}", allegationPerpetratorHistory);
      throw new ServiceException(e);
    }
  }

}
