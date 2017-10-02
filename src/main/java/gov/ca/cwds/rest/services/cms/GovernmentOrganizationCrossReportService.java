package gov.ca.cwds.rest.services.cms;

import java.util.Date;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.Dao;
import gov.ca.cwds.data.cms.GovernmentOrganizationCrossReportDao;
import gov.ca.cwds.data.persistence.cms.CmsKeyIdGenerator;
import gov.ca.cwds.rest.api.domain.cms.GovernmentOrganizationCrossReport;
import gov.ca.cwds.rest.filters.RequestExecutionContext;
import gov.ca.cwds.rest.services.ServiceException;
import gov.ca.cwds.rest.services.TypedCrudsService;

/**
 * Business layer object to work on
 * {@link gov.ca.cwds.data.persistence.cms.GovernmentOrganizationCrossReport}
 * 
 * @author CWDS API Team
 */
public class GovernmentOrganizationCrossReportService implements
    TypedCrudsService<String, GovernmentOrganizationCrossReport, GovernmentOrganizationCrossReport> {

  private static final Logger LOGGER =
      LoggerFactory.getLogger(GovernmentOrganizationCrossReportService.class);

  private GovernmentOrganizationCrossReportDao governmentOrganizationCrossReportDao;
  private String lastUpdatedId = RequestExecutionContext.instance().getStaffId();
  private Date lastUpdatedTime = RequestExecutionContext.instance().getRequestStartTime();

  /**
   * @param governmentOrganizationCrossReportDao The {@link Dao} handling
   *        {@link gov.ca.cwds.data.persistence.cms.GovernmentOrganizationCrossReport} objects.
   */
  @Inject
  public GovernmentOrganizationCrossReportService(
      GovernmentOrganizationCrossReportDao governmentOrganizationCrossReportDao) {
    this.governmentOrganizationCrossReportDao = governmentOrganizationCrossReportDao;
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#create(gov.ca.cwds.rest.api.Request)
   */
  @Override
  public GovernmentOrganizationCrossReport create(GovernmentOrganizationCrossReport request) {

    GovernmentOrganizationCrossReport governmentOrganizationCrossReport = request;

    try {
      gov.ca.cwds.data.persistence.cms.GovernmentOrganizationCrossReport managed =
          new gov.ca.cwds.data.persistence.cms.GovernmentOrganizationCrossReport(
              CmsKeyIdGenerator.generate(lastUpdatedId), governmentOrganizationCrossReport,
              lastUpdatedId, lastUpdatedTime);
      managed = governmentOrganizationCrossReportDao.create(managed);
      return new gov.ca.cwds.rest.api.domain.cms.GovernmentOrganizationCrossReport(managed);
    } catch (EntityExistsException e) {
      LOGGER.info("governmentOrganizationCrossReport already exists : {}",
          governmentOrganizationCrossReport);
      throw new ServiceException(e);
    }
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#delete(java.io.Serializable)
   */
  @Override
  public GovernmentOrganizationCrossReport delete(String primaryKey) {

    gov.ca.cwds.data.persistence.cms.GovernmentOrganizationCrossReport persistedGovernmentOrganizationCrossReport =
        governmentOrganizationCrossReportDao.delete(primaryKey);
    if (persistedGovernmentOrganizationCrossReport != null) {
      return new gov.ca.cwds.rest.api.domain.cms.GovernmentOrganizationCrossReport(
          persistedGovernmentOrganizationCrossReport);
    }
    return null;
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#find(java.io.Serializable)
   */
  @Override
  public GovernmentOrganizationCrossReport find(String primaryKey) {

    gov.ca.cwds.data.persistence.cms.GovernmentOrganizationCrossReport persistedGovernmentOrganizationCrossReport =
        governmentOrganizationCrossReportDao.find(primaryKey);
    if (persistedGovernmentOrganizationCrossReport != null) {
      return new gov.ca.cwds.rest.api.domain.cms.GovernmentOrganizationCrossReport(
          persistedGovernmentOrganizationCrossReport);
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
  public GovernmentOrganizationCrossReport update(String primaryKey,
      GovernmentOrganizationCrossReport request) {

    GovernmentOrganizationCrossReport governmentOrganizationCrossReport = request;

    try {
      gov.ca.cwds.data.persistence.cms.GovernmentOrganizationCrossReport managed =
          new gov.ca.cwds.data.persistence.cms.GovernmentOrganizationCrossReport(primaryKey,
              governmentOrganizationCrossReport, lastUpdatedId, lastUpdatedTime);
      managed = governmentOrganizationCrossReportDao.update(managed);
      return new gov.ca.cwds.rest.api.domain.cms.GovernmentOrganizationCrossReport(managed);
    } catch (EntityNotFoundException e) {
      LOGGER.info("governmentOrganizationCrossReport not found : {}",
          governmentOrganizationCrossReport);
      throw new ServiceException(e);
    }
  }

}
