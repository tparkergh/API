package gov.ca.cwds.rest.services.cms;

import java.util.Date;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.Dao;
import gov.ca.cwds.data.cms.CrossReportDao;
import gov.ca.cwds.data.persistence.cms.CmsKeyIdGenerator;
import gov.ca.cwds.data.persistence.cms.CrossReport;
import gov.ca.cwds.rest.services.ServiceException;
import gov.ca.cwds.rest.services.TypedCrudsService;
import gov.ca.cwds.rest.services.referentialintegrity.RICrossReport;

/**
 * Business layer object to work on {@link CrossReport}
 * 
 * @author CWDS API Team
 */
public class CrossReportService implements
    TypedCrudsService<String, gov.ca.cwds.rest.api.domain.cms.CrossReport, gov.ca.cwds.rest.api.domain.cms.CrossReport> {

  private static final Logger LOGGER = LoggerFactory.getLogger(CrossReportService.class);

  private CrossReportDao crossReportDao;
  private StaffPersonIdRetriever staffPersonIdRetriever;
  private RICrossReport riCrossReport;

  /**
   * Constructor
   * 
   * @param crossReportDao The {@link Dao} handling
   *        {@link gov.ca.cwds.data.persistence.cms.CrossReport} objects.
   * @param staffPersonIdRetriever the staffPersonIdRetriever
   * @param riCrossReport the ri for cross report
   */
  @Inject
  public CrossReportService(CrossReportDao crossReportDao,
      StaffPersonIdRetriever staffPersonIdRetriever, RICrossReport riCrossReport) {
    this.crossReportDao = crossReportDao;
    this.staffPersonIdRetriever = staffPersonIdRetriever;
    this.riCrossReport = riCrossReport;
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#find(java.io.Serializable)
   */
  @Override
  public gov.ca.cwds.rest.api.domain.cms.CrossReport find(String primaryKey) {

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
  public gov.ca.cwds.rest.api.domain.cms.CrossReport delete(String primaryKey) {
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
  public gov.ca.cwds.rest.api.domain.cms.CrossReport create(
      gov.ca.cwds.rest.api.domain.cms.CrossReport request) {

    gov.ca.cwds.rest.api.domain.cms.CrossReport crossReport = request;
    return create(crossReport, null);
  }

  /**
   * This createWithSingleTimestamp is used for the referrals to maintian the same timestamp for the
   * whole transaction
   * 
   * @param request - request
   * @param timestamp - timestamp
   * @return the single timestamp
   */
  public gov.ca.cwds.rest.api.domain.cms.CrossReport createWithSingleTimestamp(
      gov.ca.cwds.rest.api.domain.cms.CrossReport request, Date timestamp) {

    gov.ca.cwds.rest.api.domain.cms.CrossReport crossReport = request;
    return create(crossReport, timestamp);
  }

  /**
   * This private method is created to handle to single crossReport and referrals with single
   * timestamp
   * 
   */
  private gov.ca.cwds.rest.api.domain.cms.CrossReport create(
      gov.ca.cwds.rest.api.domain.cms.CrossReport crossReport, Date timestamp) {
    try {
      String lastUpdatedId = staffPersonIdRetriever.getStaffPersonId();
      CrossReport managed;
      if (timestamp == null) {
        managed =
            new CrossReport(CmsKeyIdGenerator.generate(lastUpdatedId), crossReport, lastUpdatedId);
      } else {
        managed = new CrossReport(CmsKeyIdGenerator.generate(lastUpdatedId), crossReport,
            lastUpdatedId, timestamp);
      }
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
  public gov.ca.cwds.rest.api.domain.cms.CrossReport update(String primaryKey,
      gov.ca.cwds.rest.api.domain.cms.CrossReport request) {
    gov.ca.cwds.rest.api.domain.cms.CrossReport crossReport = request;

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
