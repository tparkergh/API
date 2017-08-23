package gov.ca.cwds.rest.services.cms;

import java.util.Date;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.Dao;
import gov.ca.cwds.data.cms.AllegationDao;
import gov.ca.cwds.data.persistence.cms.Allegation;
import gov.ca.cwds.data.persistence.cms.CmsKeyIdGenerator;
import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.domain.cms.PostedAllegation;
import gov.ca.cwds.rest.services.ServiceException;
import gov.ca.cwds.rest.services.TypedCrudsService;
import gov.ca.cwds.rest.services.referentialintegrity.RIAllegation;

/**
 * Business layer object to work on {@link Allegation}
 * 
 * @author CWDS API Team
 */
public class AllegationService implements
    TypedCrudsService<String, gov.ca.cwds.rest.api.domain.cms.Allegation, gov.ca.cwds.rest.api.domain.cms.Allegation> {

  private static final Logger LOGGER = LoggerFactory.getLogger(AllegationService.class);

  private AllegationDao allegationDao;
  private StaffPersonIdRetriever staffPersonIdRetriever;
  private RIAllegation riAllegation;

  /**
   * Constructor
   * 
   * @param allegationDao The {@link Dao} handling
   *        {@link gov.ca.cwds.data.persistence.cms.Allegation} objects.
   * @param staffPersonIdRetriever the staffPersonIdRetriever
   * @param riAllegation the ri for allegation
   */
  @Inject
  public AllegationService(AllegationDao allegationDao,
      StaffPersonIdRetriever staffPersonIdRetriever, RIAllegation riAllegation) {
    this.allegationDao = allegationDao;
    this.staffPersonIdRetriever = staffPersonIdRetriever;
    this.riAllegation = riAllegation;
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#find(java.io.Serializable)
   */
  @Override
  public gov.ca.cwds.rest.api.domain.cms.Allegation find(String primaryKey) {

    gov.ca.cwds.data.persistence.cms.Allegation persistedAllegation =
        allegationDao.find(primaryKey);
    if (persistedAllegation != null) {
      return new gov.ca.cwds.rest.api.domain.cms.Allegation(persistedAllegation);
    }
    return null;
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#delete(java.io.Serializable)
   */
  @Override
  public gov.ca.cwds.rest.api.domain.cms.Allegation delete(String primaryKey) {

    gov.ca.cwds.data.persistence.cms.Allegation persistedAllegation =
        allegationDao.delete(primaryKey);
    if (persistedAllegation != null) {
      return new gov.ca.cwds.rest.api.domain.cms.Allegation(persistedAllegation);
    }
    return null;
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#create(gov.ca.cwds.rest.api.Request)
   */
  @Override
  public PostedAllegation create(gov.ca.cwds.rest.api.domain.cms.Allegation request) {

    gov.ca.cwds.rest.api.domain.cms.Allegation allegation = request;
    return create(allegation, null);

  }

  /**
   * This createWithSingleTimestamp is used for the referrals to maintian the same timestamp for the
   * whole transaction
   * 
   * @param request - request
   * @param timestamp - timestamp
   * @return the single timestamp
   */
  public PostedAllegation createWithSingleTimestamp(Request request, Date timestamp) {

    gov.ca.cwds.rest.api.domain.cms.Allegation allegation =
        (gov.ca.cwds.rest.api.domain.cms.Allegation) request;
    return create(allegation, timestamp);

  }

  /**
   * This private method is created to handle to single allegation and referrals with single
   * timestamp
   * 
   */
  private PostedAllegation create(gov.ca.cwds.rest.api.domain.cms.Allegation allegation,
      Date timestamp) {
    try {
      String lastUpdatedId = staffPersonIdRetriever.getStaffPersonId();
      Allegation managed;
      if (timestamp == null) {
        managed =
            new Allegation(CmsKeyIdGenerator.generate(lastUpdatedId), allegation, lastUpdatedId);
      } else {
        managed = new Allegation(CmsKeyIdGenerator.generate(lastUpdatedId), allegation,
            lastUpdatedId, timestamp);
      }
      managed = allegationDao.create(managed);
      return new PostedAllegation(managed);
    } catch (EntityExistsException e) {
      LOGGER.info("Allegation already exists : {}", allegation);
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
  public gov.ca.cwds.rest.api.domain.cms.Allegation update(String primaryKey,
      gov.ca.cwds.rest.api.domain.cms.Allegation request) {
    gov.ca.cwds.rest.api.domain.cms.Allegation allegation = request;

    try {
      String lastUpdatedId = staffPersonIdRetriever.getStaffPersonId();
      Allegation managed = new Allegation(primaryKey, allegation, lastUpdatedId);
      managed = allegationDao.update(managed);
      return new gov.ca.cwds.rest.api.domain.cms.Allegation(managed);
    } catch (EntityNotFoundException e) {
      LOGGER.info("Allegation not found : {}", allegation);
      throw new ServiceException(e);
    }
  }


}
