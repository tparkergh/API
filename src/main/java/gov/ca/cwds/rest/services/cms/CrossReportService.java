package gov.ca.cwds.rest.services.cms;

import gov.ca.cwds.data.Dao;
import gov.ca.cwds.data.cms.CrossReportDao;
import gov.ca.cwds.data.persistence.cms.CmsKeyIdGenerator;
import gov.ca.cwds.data.persistence.cms.CrossReport;
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
 * Business layer object to work on {@link CrossReport}
 * 
 * @author CWDS API Team
 */
public class CrossReportService implements CrudsService {

  private static final Logger LOGGER = LoggerFactory.getLogger(CrossReportService.class);

  private CrossReportDao crossReportDao;
  private StaffPersonIdRetriever staffPersonIdRetriever;

  /**
   * Constructor
   * 
   * @param crossReportDao The {@link Dao} handling
   *        {@link gov.ca.cwds.data.persistence.cms.CrossReport} objects.
   * @param staffPersonIdRetriever the staffPersonIdRetriever
   */
  @Inject
  public CrossReportService(CrossReportDao crossReportDao,
      StaffPersonIdRetriever staffPersonIdRetriever) {
    this.crossReportDao = crossReportDao;
    this.staffPersonIdRetriever = staffPersonIdRetriever;
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#find(java.io.Serializable)
   */
  @Override
  public gov.ca.cwds.rest.api.domain.cms.CrossReport find(Serializable primaryKey) {
    assert primaryKey instanceof String;

    gov.ca.cwds.data.persistence.cms.CrossReport persistedCrossReport =
        crossReportDao.find(primaryKey);
    if (persistedCrossReport != null) {
      return new gov.ca.cwds.rest.api.domain.cms.CrossReport(persistedCrossReport);
    }
    return null;
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#delete(java.io.Serializable)
   */
  @Override
  public gov.ca.cwds.rest.api.domain.cms.CrossReport delete(Serializable primaryKey) {
    assert primaryKey instanceof String;
    gov.ca.cwds.data.persistence.cms.CrossReport persistedCrossReport =
        crossReportDao.delete(primaryKey);
    if (persistedCrossReport != null) {
      return new gov.ca.cwds.rest.api.domain.cms.CrossReport(persistedCrossReport);
    }
    return null;
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#create(gov.ca.cwds.rest.api.Request)
   */
  @Override
  public gov.ca.cwds.rest.api.domain.cms.CrossReport create(Request request) {
    assert request instanceof gov.ca.cwds.rest.api.domain.cms.CrossReport;

    gov.ca.cwds.rest.api.domain.cms.CrossReport crossReport =
        (gov.ca.cwds.rest.api.domain.cms.CrossReport) request;

    try {
      String lastUpdatedId = staffPersonIdRetriever.getStaffPersonId();
      CrossReport managed =
          new CrossReport(CmsKeyIdGenerator.generate(lastUpdatedId), crossReport,
              lastUpdatedId);
      managed = crossReportDao.create(managed);
      return new gov.ca.cwds.rest.api.domain.cms.CrossReport(managed);
    } catch (EntityExistsException e) {
      LOGGER.info("CrossReport already exists : {}", crossReport);
      throw new ServiceException("CrossReport already exists : {}" + crossReport, e);
    }
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#update(java.io.Serializable,
   *      gov.ca.cwds.rest.api.Request)
   */
  @Override
  public gov.ca.cwds.rest.api.domain.cms.CrossReport update(Serializable primaryKey, Request request) {
    assert request instanceof gov.ca.cwds.rest.api.domain.cms.CrossReport;
    gov.ca.cwds.rest.api.domain.cms.CrossReport crossReport =
        (gov.ca.cwds.rest.api.domain.cms.CrossReport) request;

    try {
      String lastUpdatedId = staffPersonIdRetriever.getStaffPersonId();
      CrossReport managed = new CrossReport(crossReport.getThirdId(), crossReport, lastUpdatedId);
      managed = crossReportDao.update(managed);
      return new gov.ca.cwds.rest.api.domain.cms.CrossReport(managed);
    } catch (EntityNotFoundException e) {
      LOGGER.info("CrossReport not found : {}", crossReport);
      throw new ServiceException(e);
    }
  }

}
