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
import gov.ca.cwds.data.cms.StaffPersonDao;
import gov.ca.cwds.data.persistence.cms.CountyOwnership;
import gov.ca.cwds.data.persistence.cms.ReferralClient;
import gov.ca.cwds.data.persistence.cms.StaffPerson;
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
  private StaffPersonDao staffpersonDao;

  /**
   * Constructor
   * 
   * @param referralClientDao The {@link Dao} handling
   *        {@link gov.ca.cwds.data.persistence.cms.ReferralClient} objects.
   * @param countyOwnershipDao countyOwenshipDao The {@link Dao} handling {@link CountyOwnership}
   *        objects
   * @param staffpersonDao staffpersonDao The {@link Dao} handling {@link StaffPerson} objects
   */
  @Inject
  public ReferralClientService(ReferralClientDao referralClientDao,
      CountyOwnershipDao countyOwnershipDao, StaffPersonDao staffpersonDao) {
    this.referralClientDao = referralClientDao;
    this.countyOwnershipDao = countyOwnershipDao;
    this.staffpersonDao = staffpersonDao;
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

      ReferralClient managed = new ReferralClient(referralClient, "BTr");
      createAndUpdateCoutyOwnership(managed);
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
      createAndUpdateCoutyOwnership(managed);
      return new gov.ca.cwds.rest.api.domain.cms.ReferralClient(managed);
    } catch (EntityNotFoundException e) {
      LOGGER.info("Referral not found : {}", referralClient);
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

  /**
   * 
   * @param managed referralClient This method triggers the CountyOwnership table with the
   *        associated User
   * 
   */
  private void createAndUpdateCoutyOwnership(ReferralClient managed) {
    StaffPerson staffperson = staffpersonDao.find(managed.getLastUpdatedId());
    if (staffperson != null && !("19".equals(staffperson.getCountyCode()))) {
      CountyOwnership countyOwnership = countyOwnershipDao.find(managed.getClientId());
      if (countyOwnership == null) {
        countyOwnership = new CountyOwnership();
        countyOwnership.setEntityId(managed.getClientId());
        countyOwnership.setEntityCode("C");
      }
      String methodName = "setCounty" + managed.getCountySpecificCode() + "Flag";
      Method method = null;
      try {
        method = countyOwnership.getClass().getMethod(methodName, String.class);
        method.invoke(countyOwnership, "Y");
      } catch (NoSuchMethodException | SecurityException | IllegalAccessException
          | IllegalArgumentException | InvocationTargetException e) {
        LOGGER.info("CountyOwnership Unable to Trigger : {}", countyOwnership);
        LOGGER.error(e.getMessage(), e);
        throw new ServiceException(e);
      }

      countyOwnershipDao.update(countyOwnership);

    }
  }

}
