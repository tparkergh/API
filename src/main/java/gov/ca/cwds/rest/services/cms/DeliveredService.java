package gov.ca.cwds.rest.services.cms;

import javax.persistence.EntityExistsException;

import org.apache.commons.lang3.NotImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.Dao;
import gov.ca.cwds.data.cms.DeliveredServiceDao;
import gov.ca.cwds.data.persistence.cms.CmsKeyIdGenerator;
import gov.ca.cwds.data.persistence.cms.DeliveredServiceEntity;
import gov.ca.cwds.rest.filters.RequestExecutionContext;
import gov.ca.cwds.rest.services.ServiceException;
import gov.ca.cwds.rest.services.TypedCrudsService;

/**
 * Business layer object to work on {@link DeliveredServiceEntity}
 * 
 * @author CWDS API Team
 */
public class DeliveredService implements
    TypedCrudsService<String, gov.ca.cwds.rest.api.domain.cms.DeliveredService, gov.ca.cwds.rest.api.domain.cms.DeliveredService> {

  private static final Logger LOGGER = LoggerFactory.getLogger(DeliveredService.class);

  private DeliveredServiceDao deliveredServiceDao;

  /**
   * @param deliveredServiceDao {@link Dao} handling
   *        {@link gov.ca.cwds.data.persistence.cms.DeliveredServiceEntity} objects
   */
  @Inject
  public DeliveredService(DeliveredServiceDao deliveredServiceDao) {
    super();
    this.deliveredServiceDao = deliveredServiceDao;
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#create(gov.ca.cwds.rest.api.Request)
   */
  @Override
  public gov.ca.cwds.rest.api.domain.cms.DeliveredService create(
      gov.ca.cwds.rest.api.domain.cms.DeliveredService request) {

    gov.ca.cwds.rest.api.domain.cms.DeliveredService deliveredServiceEntity = request;

    try {

      String lastUpdatedId = RequestExecutionContext.instance().getUserId();
      gov.ca.cwds.data.persistence.cms.DeliveredServiceEntity managed = new DeliveredServiceEntity(
          CmsKeyIdGenerator.generate(lastUpdatedId), deliveredServiceEntity, lastUpdatedId);
      managed = deliveredServiceDao.create(managed);
      return new gov.ca.cwds.rest.api.domain.cms.DeliveredService(managed);
    } catch (EntityExistsException e) {
      LOGGER.info("deliveredServiceEntity already exists : {}", deliveredServiceEntity);
      throw new ServiceException(e);
    }

  }

  @Override
  public gov.ca.cwds.rest.api.domain.cms.DeliveredService delete(String arg0) {
    throw new NotImplementedException("delete not implement");
  }

  @Override
  public gov.ca.cwds.rest.api.domain.cms.DeliveredService find(String arg0) {
    throw new NotImplementedException("find not implement");
  }

  @Override
  public gov.ca.cwds.rest.api.domain.cms.DeliveredService update(String arg0,
      gov.ca.cwds.rest.api.domain.cms.DeliveredService arg1) {
    throw new NotImplementedException("update not implement");
  }

}
