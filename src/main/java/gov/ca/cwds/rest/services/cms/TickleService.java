package gov.ca.cwds.rest.services.cms;

import java.util.Date;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.Dao;
import gov.ca.cwds.data.cms.TickleDao;
import gov.ca.cwds.data.persistence.cms.CmsKeyIdGenerator;
import gov.ca.cwds.data.persistence.cms.Tickle;
import gov.ca.cwds.rest.filters.RequestExecutionContext;
import gov.ca.cwds.rest.services.ServiceException;
import gov.ca.cwds.rest.services.TypedCrudsService;

/**
 * Business layer object to work on {@link Tickle}
 * 
 * @author CWDS API Team
 */
public class TickleService implements
    TypedCrudsService<String, gov.ca.cwds.rest.api.domain.cms.Tickle, gov.ca.cwds.rest.api.domain.cms.Tickle> {

  private static final Logger LOGGER = LoggerFactory.getLogger(TickleService.class);

  private TickleDao tickleDao;
  private String lastUpdatedId = RequestExecutionContext.instance().getStaffId();
  private Date lastUpdatedTime = RequestExecutionContext.instance().getRequestStartTime();


  /**
   * @param tickleDao The {@link Dao} handling {@link gov.ca.cwds.data.persistence.cms.Tickle}
   *        objects.
   */
  @Inject
  public TickleService(TickleDao tickleDao) {
    this.tickleDao = tickleDao;
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#create(gov.ca.cwds.rest.api.Request)
   */
  @Override
  public gov.ca.cwds.rest.api.domain.cms.Tickle create(
      gov.ca.cwds.rest.api.domain.cms.Tickle request) {

    gov.ca.cwds.rest.api.domain.cms.Tickle tickle = request;

    try {

      gov.ca.cwds.data.persistence.cms.Tickle managed = new gov.ca.cwds.data.persistence.cms.Tickle(
          CmsKeyIdGenerator.generate(lastUpdatedId), tickle, lastUpdatedId, lastUpdatedTime);
      managed = tickleDao.create(managed);
      return new gov.ca.cwds.rest.api.domain.cms.Tickle(managed);
    } catch (EntityExistsException e) {
      LOGGER.info("tickle already exists : {}", tickle);
      throw new ServiceException(e);
    }
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#delete(java.io.Serializable)
   */
  @Override
  public gov.ca.cwds.rest.api.domain.cms.Tickle delete(String primaryKey) {

    gov.ca.cwds.data.persistence.cms.Tickle persistedTickle = tickleDao.delete(primaryKey);
    if (persistedTickle != null) {
      return new gov.ca.cwds.rest.api.domain.cms.Tickle(persistedTickle);
    }
    return null;
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#find(java.io.Serializable)
   */
  @Override
  public gov.ca.cwds.rest.api.domain.cms.Tickle find(String primaryKey) {

    gov.ca.cwds.data.persistence.cms.Tickle persistedTickle = tickleDao.find(primaryKey);
    if (persistedTickle != null) {
      return new gov.ca.cwds.rest.api.domain.cms.Tickle(persistedTickle);
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
  public gov.ca.cwds.rest.api.domain.cms.Tickle update(String primaryKey,
      gov.ca.cwds.rest.api.domain.cms.Tickle request) {

    gov.ca.cwds.rest.api.domain.cms.Tickle tickle = request;

    try {
      Tickle managed = new Tickle(primaryKey, tickle, lastUpdatedId, lastUpdatedTime);
      managed = tickleDao.update(managed);
      return new gov.ca.cwds.rest.api.domain.cms.Tickle(managed);
    } catch (EntityNotFoundException e) {
      LOGGER.info("tickle not found : {}", tickle);
      throw new ServiceException(e);
    }
  }

}
