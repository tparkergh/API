package gov.ca.cwds.rest.services.cms;

import java.io.Serializable;
import java.util.Date;

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
  private StaffPersonIdRetriever staffPersonIdRetriever;


  /**
   * Constructor
   * 
   * @param allegationPerpetratorHistoryDao The {@link Dao} handling
   *        {@link gov.ca.cwds.data.persistence.cms.AllegationPerpetratorHistory} objects.
   * @param staffPersonIdRetriever the staffPersonIdRetriever
   */
  @Inject
  public AllegationPerpetratorHistoryService(
      AllegationPerpetratorHistoryDao allegationPerpetratorHistoryDao,
      StaffPersonIdRetriever staffPersonIdRetriever) {
    this.allegationPerpetratorHistoryDao = allegationPerpetratorHistoryDao;
    this.staffPersonIdRetriever = staffPersonIdRetriever;

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

    gov.ca.cwds.rest.api.domain.cms.AllegationPerpetratorHistory allegationPerpetratoryHistory =
        (gov.ca.cwds.rest.api.domain.cms.AllegationPerpetratorHistory) request;
    return create(allegationPerpetratoryHistory, null);

  }

  /**
   * This createWithSingleTimestamp is used for the referrals to maintian the same timestamp for the
   * whole transaction
   * 
   * @param request - request
   * @param timestamp - timestamp
   * @return the Posted Allegation Perpetrator History
   */
  public PostedAllegationPerpetratorHistory createWithSingleTimestamp(Request request,
      Date timestamp) {
    assert request instanceof gov.ca.cwds.rest.api.domain.cms.AllegationPerpetratorHistory;

    gov.ca.cwds.rest.api.domain.cms.AllegationPerpetratorHistory allegationPerpetratoryHistory =
        (gov.ca.cwds.rest.api.domain.cms.AllegationPerpetratorHistory) request;
    return create(allegationPerpetratoryHistory, timestamp);

  }

  /**
   * 
   * This private method is used by createWithSingleTimestamp()
   */
  private PostedAllegationPerpetratorHistory create(
      gov.ca.cwds.rest.api.domain.cms.AllegationPerpetratorHistory allegationPerpetratorHistory,
      Date timestamp) {

    try {
      String lastUpdatedId = staffPersonIdRetriever.getStaffPersonId();
      AllegationPerpetratorHistory managed;
      if (timestamp == null) {
        managed = new AllegationPerpetratorHistory(CmsKeyIdGenerator.generate(lastUpdatedId),
            allegationPerpetratorHistory, lastUpdatedId);

      } else {
        managed = new AllegationPerpetratorHistory(CmsKeyIdGenerator.generate(lastUpdatedId),
            allegationPerpetratorHistory, lastUpdatedId, timestamp);
      }
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
      String lastUpdatedId = staffPersonIdRetriever.getStaffPersonId();
      AllegationPerpetratorHistory managed = new AllegationPerpetratorHistory((String) primaryKey,
          allegationPerpetratorHistory, lastUpdatedId);
      managed = allegationPerpetratorHistoryDao.update(managed);
      return new gov.ca.cwds.rest.api.domain.cms.AllegationPerpetratorHistory(managed);
    } catch (EntityNotFoundException e) {
      LOGGER.info("AllegationPerpetratorHistory not found : {}", allegationPerpetratorHistory);
      throw new ServiceException(e);
    }
  }

}
