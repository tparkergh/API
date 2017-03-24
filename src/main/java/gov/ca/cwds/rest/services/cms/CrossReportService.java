package gov.ca.cwds.rest.services.cms;

import java.io.Serializable;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.Dao;
import gov.ca.cwds.data.cms.CrossReportDao;
import gov.ca.cwds.data.persistence.cms.CrossReport;
import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.services.CrudsService;
import gov.ca.cwds.rest.services.ServiceException;

/**
 * Business layer object to work on {@link CrossReport}
 * 
 * @author CWDS API Team
 */
public class CrossReportService implements CrudsService {

  private static final Logger LOGGER = LoggerFactory.getLogger(CrossReportService.class);

  private CrossReportDao crossReportDao;

  /**
   * Constructor
   * 
   * @param crossReportDao The {@link Dao} handling
   *        {@link gov.ca.cwds.data.persistence.cms.CrossReport} objects.
   */
  @Inject
  public CrossReportService(CrossReportDao crossReportDao) {
    this.crossReportDao = crossReportDao;
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
      // TODO : refactor to actually determine who is updating. 'q1p' for now - #136737071 - Tech
      // Debt: Legacy Service classes must use Staff ID for last update ID value

      // CrossReport managed = new CrossReport(IdGenerator.randomString(10), crossReport, "q1p");
      CrossReport managed = new CrossReport(crossReport.getThirdId(), crossReport, "q1p");
      managed = crossReportDao.create(managed);
      return new gov.ca.cwds.rest.api.domain.cms.CrossReport(managed);
    } catch (EntityExistsException e) {
      LOGGER.info("CrossReport already exists : {}", crossReport);
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
  public gov.ca.cwds.rest.api.domain.cms.CrossReport update(Serializable primaryKey,
      Request request) {
    assert request instanceof gov.ca.cwds.rest.api.domain.cms.CrossReport;
    gov.ca.cwds.rest.api.domain.cms.CrossReport crossReport =
        (gov.ca.cwds.rest.api.domain.cms.CrossReport) request;

    try {
      CrossReport managed = new CrossReport(crossReport.getThirdId(), crossReport, "q1p");
      managed = crossReportDao.update(managed);
      return new gov.ca.cwds.rest.api.domain.cms.CrossReport(managed);
    } catch (EntityNotFoundException e) {
      LOGGER.info("CrossReport not found : {}", crossReport);
      throw new ServiceException(e);
    }
  }

}
