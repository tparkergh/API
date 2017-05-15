package gov.ca.cwds.rest.services.cms;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.Dao;
import gov.ca.cwds.data.cms.CountyOwnershipDao;
import gov.ca.cwds.data.cms.ReferralClientDao;
import gov.ca.cwds.data.persistence.cms.CountyOwnership;
import gov.ca.cwds.data.persistence.cms.ReferralClient;
import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.services.CrudsService;
import gov.ca.cwds.rest.services.ServiceException;
import gov.ca.cwds.rest.util.ServiceUtils;

/**
 * Business layer object to work on {@link ReferralClient}
 * 
 * @author CWDS API Team
 */
public class ReferralClientService implements CrudsService {

  private static final Logger LOGGER = LoggerFactory.getLogger(ReferralClientService.class);

  private static final String KEY_REFERRAL_ID = "referralId";
  private static final String KEY_CLIENT_ID = "clientId";

  private ReferralClientDao referralClientDao;
  private CountyOwnershipDao countyOwnershipDao;

  /**
   * Constructor
   * 
   * @param referralClientDao The {@link Dao} handling
   *        {@link gov.ca.cwds.data.persistence.cms.ReferralClient} objects.
   * @param countyOwnershipDao countyOwenshipDao The {@link Dao} handling {@link CountyOwnership}
   *        objects
   */
  @Inject
  public ReferralClientService(ReferralClientDao referralClientDao,
      CountyOwnershipDao countyOwnershipDao) {
    this.referralClientDao = referralClientDao;
    this.countyOwnershipDao = countyOwnershipDao;
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#find(java.io.Serializable)
   */
  @Override
  public gov.ca.cwds.rest.api.domain.cms.ReferralClient find(Serializable primaryKey) {
    ReferralClient.PrimaryKey primaryKeyObject = extractPrimaryKey(primaryKey);
    gov.ca.cwds.data.persistence.cms.ReferralClient persistedReferralClient =
        referralClientDao.find(primaryKeyObject);
    if (persistedReferralClient != null) {
      return new gov.ca.cwds.rest.api.domain.cms.ReferralClient(persistedReferralClient);
    }
    return null;
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#delete(java.io.Serializable)
   */
  @Override
  public gov.ca.cwds.rest.api.domain.cms.ReferralClient delete(Serializable primaryKey) {
    ReferralClient.PrimaryKey primaryKeyObject = extractPrimaryKey(primaryKey);
    gov.ca.cwds.data.persistence.cms.ReferralClient persistedReferralClient =
        referralClientDao.delete(primaryKeyObject);

    if (persistedReferralClient != null) {
      return new gov.ca.cwds.rest.api.domain.cms.ReferralClient(persistedReferralClient);
    }

    return null;
  }

  @Override
  public gov.ca.cwds.rest.api.domain.cms.ReferralClient create(Request request) {
    assert request instanceof gov.ca.cwds.rest.api.domain.cms.ReferralClient;

    gov.ca.cwds.rest.api.domain.cms.ReferralClient referralClient =
        (gov.ca.cwds.rest.api.domain.cms.ReferralClient) request;

    try {
      // TODO : refactor to actually determine who is updating. 'q1p' for now - #136737071 - Tech
      // Debt: Legacy Service classes must use Staff ID for last update ID value

      ReferralClient managed = new ReferralClient(referralClient, "q1p");
      CountyOwnership countyOwnership = countyOwnershipDao.find(managed.getClientId());
      String methodName = "setCounty" + managed.getCountySpecificCode() + "Flag";
      Method method = null;
      try {
        method = countyOwnership.getClass().getMethod(methodName, String.class);
        method.invoke(countyOwnership, "Y");
      } catch (EntityNotFoundException | NoSuchMethodException | SecurityException
          | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
        LOGGER.error(e.getMessage(), e);
      }
      countyOwnershipDao.update(countyOwnership);

      managed = referralClientDao.create(managed);
      return new gov.ca.cwds.rest.api.domain.cms.ReferralClient(managed);
    } catch (EntityExistsException e) {
      LOGGER.info("Referral Client already exists : {}", referralClient);
      throw new ServiceException(e);
    }
  }

  @Override
  public gov.ca.cwds.rest.api.domain.cms.ReferralClient update(Serializable primaryKeyObject,
      Request request) {
    assert request instanceof gov.ca.cwds.rest.api.domain.cms.ReferralClient;
    gov.ca.cwds.rest.api.domain.cms.ReferralClient referralClient =
        (gov.ca.cwds.rest.api.domain.cms.ReferralClient) request;

    try {
      ReferralClient managed = new ReferralClient(referralClient, "q1p");
      CountyOwnership countyOwnership = countyOwnershipDao.find(managed.getClientId());
      String methodName = "setCounty" + managed.getCountySpecificCode() + "Flag";
      Method method;

      method = countyOwnership.getClass().getMethod(methodName, String.class);
      method.invoke(countyOwnership, "Y");

      managed = referralClientDao.update(managed);
      return new gov.ca.cwds.rest.api.domain.cms.ReferralClient(managed);
    } catch (EntityNotFoundException | NoSuchMethodException | SecurityException
        | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
      LOGGER.info("Referral not found or CountyOwenship exception : {}", referralClient);
      LOGGER.error(e.getMessage(), e);
      throw new ServiceException(e);
    }
  }

  private static ReferralClient.PrimaryKey extractPrimaryKey(Serializable primaryKey) {
    Map<String, String> nameValuePairs = ServiceUtils.extractKeyValuePairs(primaryKey);
    String referralId = nameValuePairs.get(KEY_REFERRAL_ID);
    String clientId = nameValuePairs.get(KEY_CLIENT_ID);
    return new ReferralClient.PrimaryKey(referralId, clientId);
  }
}
