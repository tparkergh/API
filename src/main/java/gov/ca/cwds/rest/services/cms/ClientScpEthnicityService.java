package gov.ca.cwds.rest.services.cms;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

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

  private static final String CLIENT_ESTABLISHED_CODE = "C";

  private static final Logger LOGGER = LoggerFactory.getLogger(ClientScpEthnicityService.class);
  private Set<Short> raceCodes = new HashSet<>();
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
   * @param raceAndEthnicity - raceAndEthnicity
   * @param messageBuilder - messageBuilder
   * @return the race And Ethnicity codes
   */
  public Short getRaceCode(RaceAndEthnicity raceAndEthnicity) {

    Short raceCode = 0;
    if (raceAndEthnicity != null && !raceAndEthnicity.getRaceCode().isEmpty()) {

      Iterator<Short> raceCodeIterator = raceAndEthnicity.getRaceCode().iterator();
      raceCode = raceCodeIterator.next();
      while (raceCodeIterator.hasNext()) {
        raceCodes.add(raceCodeIterator.next());
      }
    }
    if (raceAndEthnicity != null && !raceAndEthnicity.getHispanicCode().isEmpty()) {
      Iterator<Short> hispanicCodeIterator = raceAndEthnicity.getHispanicCode().iterator();
      if (raceCode == 0) {
        raceCode = hispanicCodeIterator.next();
      }
      while (hispanicCodeIterator.hasNext()) {
        raceCodes.add(hispanicCodeIterator.next());
      }

    }
    return raceCode;
  }

  /**
   * If the client has more than one raceCode or hispanicCode it create a new record as secondary
   * ethnicity in the clientScpEthnicity table.
   * 
   * @param clientId - clientId
   * @param raceAndEthnicity - race and ethnicity
   */
  public void createOtherEthnicity(String clientId, RaceAndEthnicity raceAndEthnicity) {
    List<gov.ca.cwds.data.persistence.cms.ClientScpEthnicity> clientScpEthnicities =
        new ArrayList<>();
    if (!raceCodes.isEmpty()) {
      for (Short code : raceCodes) {
        gov.ca.cwds.data.persistence.cms.ClientScpEthnicity clientScpEthnicity =
            new gov.ca.cwds.data.persistence.cms.ClientScpEthnicity(
                CmsKeyIdGenerator.generate(lastUpdatedId), CLIENT_ESTABLISHED_CODE, clientId, code,
                lastUpdatedId, lastUpdatedTime);
        clientScpEthnicities.add(clientScpEthnicity);
      }
    }
    if (!clientScpEthnicities.isEmpty()) {
      for (gov.ca.cwds.data.persistence.cms.ClientScpEthnicity entity : clientScpEthnicities) {
        clientScpEthnicityDao.create(entity);
      }
    }

  }

}
