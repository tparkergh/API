package gov.ca.cwds.rest.services.cms;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.Dao;
import gov.ca.cwds.data.cms.ClientScpEthnicityDao;
import gov.ca.cwds.data.persistence.cms.CmsKeyIdGenerator;
import gov.ca.cwds.rest.api.domain.RaceAndEthnicity;
import gov.ca.cwds.rest.api.domain.cms.ClientScpEthnicity;
import gov.ca.cwds.rest.filters.RequestExecutionContext;
import gov.ca.cwds.rest.services.ServiceException;
import gov.ca.cwds.rest.services.TypedCrudsService;

/**
 * Business layer object to work on {@link gov.ca.cwds.data.persistence.cms.ClientScpEthnicity}
 * 
 * @author CWDS API Team
 */
public class ClientScpEthnicityService
    implements TypedCrudsService<String, ClientScpEthnicity, ClientScpEthnicity> {

  private static final Logger LOGGER = LoggerFactory.getLogger(ClientScpEthnicityService.class);

  private ClientScpEthnicityDao clientScpEthnicityDao;
  private String lastUpdatedId = RequestExecutionContext.instance().getStaffId();
  private Date lastUpdatedTime = RequestExecutionContext.instance().getRequestStartTime();

  /**
   * @param clientScpEthnicityDao The {@link Dao} handling
   *        {@link gov.ca.cwds.data.persistence.cms.ClientScpEthnicity} objects.
   */
  @Inject
  public ClientScpEthnicityService(ClientScpEthnicityDao clientScpEthnicityDao) {
    this.clientScpEthnicityDao = clientScpEthnicityDao;
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#create(gov.ca.cwds.rest.api.Request)
   */
  @Override
  public ClientScpEthnicity create(ClientScpEthnicity request) {
    ClientScpEthnicity clientScpEthnicity = request;

    try {
      gov.ca.cwds.data.persistence.cms.ClientScpEthnicity managed =
          new gov.ca.cwds.data.persistence.cms.ClientScpEthnicity(
              CmsKeyIdGenerator.generate(lastUpdatedId), clientScpEthnicity, lastUpdatedId,
              lastUpdatedTime);
      managed = clientScpEthnicityDao.create(managed);
      return new gov.ca.cwds.rest.api.domain.cms.ClientScpEthnicity(managed);
    } catch (EntityExistsException e) {
      LOGGER.info("clientScpEthnicity already exists : {}", clientScpEthnicity);
      throw new ServiceException(e);
    }
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#delete(java.io.Serializable)
   */
  @Override
  public ClientScpEthnicity delete(String primaryKey) {

    gov.ca.cwds.data.persistence.cms.ClientScpEthnicity persistedClientScpEthnicity =
        clientScpEthnicityDao.delete(primaryKey);
    if (persistedClientScpEthnicity != null) {
      return new gov.ca.cwds.rest.api.domain.cms.ClientScpEthnicity(persistedClientScpEthnicity);
    }
    return null;
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#find(java.io.Serializable)
   */
  @Override
  public ClientScpEthnicity find(String primaryKey) {

    gov.ca.cwds.data.persistence.cms.ClientScpEthnicity persistedClientScpEthnicity =
        clientScpEthnicityDao.find(primaryKey);
    if (persistedClientScpEthnicity != null) {
      return new gov.ca.cwds.rest.api.domain.cms.ClientScpEthnicity(persistedClientScpEthnicity);
    }
    return null;
  }

  @Override
  public ClientScpEthnicity update(String primaryKey, ClientScpEthnicity request) {

    ClientScpEthnicity clientScpEthnicity = request;

    try {
      gov.ca.cwds.data.persistence.cms.ClientScpEthnicity managed =
          new gov.ca.cwds.data.persistence.cms.ClientScpEthnicity(primaryKey, clientScpEthnicity,
              primaryKey, lastUpdatedTime);
      managed = clientScpEthnicityDao.update(managed);
      return new gov.ca.cwds.rest.api.domain.cms.ClientScpEthnicity(managed);
    } catch (EntityNotFoundException e) {
      LOGGER.info("clientScpEthnicity not found : {}", clientScpEthnicity);
      throw new ServiceException(e);
    }
  }

  /**
   * @param clientId
   * @param raceAndEthnicity
   */
  public void createOtherEthnicity(String clientId, RaceAndEthnicity raceAndEthnicity) {
    List<gov.ca.cwds.data.persistence.cms.ClientScpEthnicity> scpEthnicities = new ArrayList<>();
    if (!raceAndEthnicity.getRaceCode().isEmpty()) {
      Iterator<Short> it = raceAndEthnicity.getRaceCode().iterator();
      it.next();
      while (it.hasNext()) {
        gov.ca.cwds.data.persistence.cms.ClientScpEthnicity scpEthnicity =
            new gov.ca.cwds.data.persistence.cms.ClientScpEthnicity(
                CmsKeyIdGenerator.generate(lastUpdatedId), "C", clientId, it.next(), lastUpdatedId,
                lastUpdatedTime);
        scpEthnicities.add(scpEthnicity);
      }
    }

    if (!raceAndEthnicity.getHispanicCode().isEmpty()) {
      Iterator<Short> it1 = raceAndEthnicity.getHispanicCode().iterator();
      while (it1.hasNext()) {
        gov.ca.cwds.data.persistence.cms.ClientScpEthnicity scpEthnicity =
            new gov.ca.cwds.data.persistence.cms.ClientScpEthnicity(
                CmsKeyIdGenerator.generate(lastUpdatedId), "C", clientId, it1.next(), lastUpdatedId,
                lastUpdatedTime);
        scpEthnicities.add(scpEthnicity);
      }
    }

    if (!scpEthnicities.isEmpty()) {
      for (gov.ca.cwds.data.persistence.cms.ClientScpEthnicity entity : scpEthnicities) {
        clientScpEthnicityDao.create(entity);
      }
    }

  }

}
