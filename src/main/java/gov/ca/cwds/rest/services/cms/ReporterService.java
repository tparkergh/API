package gov.ca.cwds.rest.services.cms;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.Dao;
import gov.ca.cwds.data.cms.ReporterDao;
import gov.ca.cwds.data.persistence.cms.Reporter;
import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.domain.cms.PostedReporter;
import gov.ca.cwds.rest.services.CrudsService;
import gov.ca.cwds.rest.services.ServiceException;
import gov.ca.cwds.rest.services.referentialintegrity.RIReporter;

/**
 * Business layer object to work on {@link Reporter}
 * 
 * @author CWDS API Team
 */
public class ReporterService implements CrudsService {
  private static final Logger LOGGER = LoggerFactory.getLogger(ReporterService.class);

  private ReporterDao reporterDao;
  private StaffPersonIdRetriever staffPersonIdRetriever;
  private RIReporter riReporter;

  /**
   * Constructor
   * 
   * @param reporterDao The {@link Dao} handling {@link gov.ca.cwds.data.persistence.cms.Reporter}
   *        objects.
   * @param staffPersonIdRetriever the staffPersonIdRetriever
   * @param riReporter see {@link RIReporter}
   */
  @Inject
  public ReporterService(ReporterDao reporterDao, StaffPersonIdRetriever staffPersonIdRetriever,
      RIReporter riReporter) {
    this.reporterDao = reporterDao;
    this.staffPersonIdRetriever = staffPersonIdRetriever;
    this.riReporter = riReporter;
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#find(java.io.Serializable)
   */
  @Override
  public gov.ca.cwds.rest.api.domain.cms.Reporter find(Serializable primaryKey) {
    assert primaryKey instanceof String;

    gov.ca.cwds.data.persistence.cms.Reporter persistedReporter = reporterDao.find(primaryKey);
    if (persistedReporter != null) {
      return new gov.ca.cwds.rest.api.domain.cms.Reporter(persistedReporter);
    }
    return null;
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#delete(java.io.Serializable)
   */
  @Override
  public gov.ca.cwds.rest.api.domain.cms.Reporter delete(Serializable primaryKey) {
    assert primaryKey instanceof String;
    gov.ca.cwds.data.persistence.cms.Reporter persistedReporter = reporterDao.delete(primaryKey);
    if (persistedReporter != null) {
      return new gov.ca.cwds.rest.api.domain.cms.Reporter(persistedReporter);
    }
    return null;
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#create(gov.ca.cwds.rest.api.Request)
   */
  @Override
  public PostedReporter create(Request request) {
    assert request instanceof gov.ca.cwds.rest.api.domain.cms.Reporter;

    gov.ca.cwds.rest.api.domain.cms.Reporter reporter =
        (gov.ca.cwds.rest.api.domain.cms.Reporter) request;
    return create(reporter, null);

  }

  /**
   * This createWithSingleTimestamp is used for the referrals to maintian the same timestamp for the
   * whole transaction
   * 
   * @param request - request
   * @param timestamp - timestamp
   * @return the single timestamp
   */
  public PostedReporter createWithSingleTimestamp(Request request, Date timestamp) {
    assert request instanceof gov.ca.cwds.rest.api.domain.cms.Reporter;

    gov.ca.cwds.rest.api.domain.cms.Reporter reporter =
        (gov.ca.cwds.rest.api.domain.cms.Reporter) request;
    return create(reporter, timestamp);
  }

  /**
   * This private method is created to handle to single reporter and referrals with single timestamp
   * 
   */
  private PostedReporter create(gov.ca.cwds.rest.api.domain.cms.Reporter reporter, Date timestamp) {
    try {
      String lastUpdatedId = staffPersonIdRetriever.getStaffPersonId();
      Reporter managed;
      if (timestamp == null) {
        managed = new Reporter(reporter, lastUpdatedId);
      } else {
        managed = new Reporter(reporter, lastUpdatedId, timestamp);
      }
      managed = reporterDao.create(managed);
      return new PostedReporter(managed);
    } catch (EntityExistsException e) {
      LOGGER.info("Reporter already exists : {}", reporter);
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
  public gov.ca.cwds.rest.api.domain.cms.Reporter update(Serializable primaryKey, Request request) {
    assert primaryKey instanceof String;
    assert request instanceof gov.ca.cwds.rest.api.domain.cms.Reporter;
    gov.ca.cwds.rest.api.domain.cms.Reporter reporter =
        (gov.ca.cwds.rest.api.domain.cms.Reporter) request;

    try {
      String lastUpdatedId = staffPersonIdRetriever.getStaffPersonId();
      Reporter managed = new Reporter(reporter, lastUpdatedId);
      managed = reporterDao.update(managed);
      return new gov.ca.cwds.rest.api.domain.cms.Reporter(managed);
    } catch (EntityNotFoundException e) {
      LOGGER.info("Reporter not found : {}", reporter);
      throw new ServiceException(e);
    }
  }

}
