package gov.ca.cwds.rest.services.cms;

import java.io.Serializable;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.domain.cms.PostedAllegation;
import gov.ca.cwds.rest.api.persistence.cms.Allegation;
import gov.ca.cwds.rest.jdbi.Dao;
import gov.ca.cwds.rest.jdbi.cms.AllegationDao;
import gov.ca.cwds.rest.services.CrudsService;
import gov.ca.cwds.rest.services.ServiceException;
import gov.ca.cwds.rest.util.IdGenerator;

/**
 * Business layer object to work on {@link Allegation}
 * 
 * @author CWDS API Team
 */
public class AllegationService implements CrudsService {

  private static final Logger LOGGER = LoggerFactory.getLogger(AllegationService.class);

  private AllegationDao allegationDao;

  /**
   * Constructor
   * 
   * @param allegationDao The {@link Dao} handling
   *        {@link gov.ca.cwds.rest.api.persistence.cms.Allegation} objects.
   */
  @Inject
  public AllegationService(AllegationDao allegationDao) {
    this.allegationDao = allegationDao;
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#find(java.io.Serializable)
   */
  @Override
  public gov.ca.cwds.rest.api.domain.cms.Allegation find(Serializable primaryKey) {
    assert primaryKey instanceof String;

    gov.ca.cwds.rest.api.persistence.cms.Allegation persistedAllegation =
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
  public gov.ca.cwds.rest.api.domain.cms.Allegation delete(Serializable primaryKey) {
    assert primaryKey instanceof String;
    gov.ca.cwds.rest.api.persistence.cms.Allegation persistedAllegation =
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
  public PostedAllegation create(Request request) {
    assert request instanceof gov.ca.cwds.rest.api.domain.cms.Allegation;

    gov.ca.cwds.rest.api.domain.cms.Allegation allegation =
        (gov.ca.cwds.rest.api.domain.cms.Allegation) request;

    try {
      // TODO : refactor to actually determine who is updating. 'q1p' for now
      Allegation managed = new Allegation(IdGenerator.randomString(10), allegation, "q1p");

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
  public gov.ca.cwds.rest.api.domain.cms.Allegation update(Serializable primaryKey,
      Request request) {
    assert primaryKey instanceof String;
    assert request instanceof gov.ca.cwds.rest.api.domain.cms.Allegation;
    gov.ca.cwds.rest.api.domain.cms.Allegation allegation =
        (gov.ca.cwds.rest.api.domain.cms.Allegation) request;

    try {
      Allegation managed = new Allegation((String) primaryKey, allegation, "q1p");

      managed = allegationDao.update(managed);
      return new gov.ca.cwds.rest.api.domain.cms.Allegation(managed);
    } catch (EntityNotFoundException e) {
      LOGGER.info("Allegation not found : {}", allegation);
      throw new ServiceException(e);
    }
  }

}

