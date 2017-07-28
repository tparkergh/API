package gov.ca.cwds.rest.business.rules;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.cms.CountyOwnershipDao;
import gov.ca.cwds.data.cms.ReferralClientDao;
import gov.ca.cwds.data.cms.ReferralDao;
import gov.ca.cwds.data.persistence.cms.Assignment;
import gov.ca.cwds.data.persistence.cms.Client;
import gov.ca.cwds.data.persistence.cms.ClientAddress;
import gov.ca.cwds.data.persistence.cms.CountyOwnership;
import gov.ca.cwds.data.persistence.cms.Referral;
import gov.ca.cwds.data.persistence.cms.ReferralClient;
import gov.ca.cwds.data.persistence.cms.ReferralClient.PrimaryKey;
import gov.ca.cwds.data.rules.TriggerTableException;


/**
 * Business layer object to work on Non LA Triggers
 * 
 * <p>
 * If the staffPerson is from the Non-LA County, it will trigger the countyOwnership table with the
 * associated foreign key and updates the trigger table if the record is existing. This
 * countyOwnerhip table helps to check how many county useres can access a single record.
 * <p>
 *
 * @author CWDS API Team
 */
public class NonLACountyTriggers {

  private static final String COUNTY_SPECIFIC_CODE_DEFAULT_CODE = "99";
  private static final String COUNTY_OWNERSHIP_UNABLE_TO_TRIGGER =
      "CountyOwnership Unable to Trigger : {}";
  private static final String CLIENT_ENTITY_CODE = "C";
  private static final String ADDRESS_ENTITY_CODE = "A";
  private static final String REFERRAL_ENTITY_CODE = "R";
  private static final String SET_FLAG = "Y";
  private static final String FLAG = "Flag";
  private static final String SET_COUNTY = "setCounty";
  private static final String TYPE_OF_ASSIGNMENT_CODE = "P";
  private static final String REFERRAL_ESTABLISHED_CODE = "R";

  private static final Logger LOGGER = LoggerFactory.getLogger(NonLACountyTriggers.class);

  private CountyOwnershipDao countyOwnershipDao;
  private ReferralDao referralDao;
  private ReferralClientDao referralClientDao;

  /**
   * @param countyOwnershipDao - countyOwnershipDao
   * @param referralDao - referralDao
   * @param referralClientDao - referralClientDao
   */
  @Inject
  public NonLACountyTriggers(CountyOwnershipDao countyOwnershipDao, ReferralDao referralDao,
      ReferralClientDao referralClientDao) {
    this.countyOwnershipDao = countyOwnershipDao;
    this.referralDao = referralDao;
    this.referralClientDao = referralClientDao;
  }

  /**
   * @param managed Client creates the countyOwnership with the client foreign key
   */
  public void createClientCountyTrigger(Client managed) {
    CountyOwnership countyOwnership = new CountyOwnership();
    countyOwnership.setEntityId(managed.getPrimaryKey());
    countyOwnership.setEntityCode(CLIENT_ENTITY_CODE);
    countyOwnershipDao.create(countyOwnership);
  }

  /**
   * @param managed referralClient creates or updates the countyOwnership with the client foreign
   *        key
   */
  public void createAndUpdateReferralClientCoutyOwnership(ReferralClient managed) {
    Boolean countyExists = true;
    CountyOwnership countyOwnership = countyOwnershipDao.find(managed.getClientId());
    if (countyOwnership == null) {
      countyExists = false;
      countyOwnership = new CountyOwnership();
      countyOwnership.setEntityId(managed.getClientId());
      countyOwnership.setEntityCode(CLIENT_ENTITY_CODE);
    }
    String methodName = SET_COUNTY + managed.getCountySpecificCode() + FLAG;
    createOrUpdateCountyOwnership(countyOwnership, methodName, countyExists);
  }

  /**
   * @param managedClientAddress clientAddress creates or updates the countyOwnership with the
   *        Address foreign key
   */
  public void createAndUpdateClientAddressCoutyOwnership(ClientAddress managedClientAddress) {
    Boolean countyExists = true;
    CountyOwnership countyOwnership = countyOwnershipDao.find(managedClientAddress.getFkAddress());
    if (countyOwnership == null) {
      countyExists = false;
      countyOwnership = new CountyOwnership();
      countyOwnership.setEntityId(managedClientAddress.getFkAddress());
      countyOwnership.setEntityCode(ADDRESS_ENTITY_CODE);
    }
    ReferralClient referralClient = referralClientDao.find(
        new PrimaryKey(managedClientAddress.getFkReferral(), managedClientAddress.getFkClient()));
    String methodName = SET_COUNTY + referralClient.getCountySpecificCode() + FLAG;
    createOrUpdateCountyOwnership(countyOwnership, methodName, countyExists);
  }

  /**
   * @param managed referral creates or updates the countyOwnership bases on the Assignment
   *        establishedCode with teh referral foreign key
   */
  public void createAndUpdateReferralCoutyOwnership(Assignment managed) {
    Boolean countyExists = true;
    if (managed.getTypeOfAssignmentCode() != TYPE_OF_ASSIGNMENT_CODE
        || managed.getEstablishedForCode() != REFERRAL_ESTABLISHED_CODE) {
      return;
    }

    CountyOwnership countyOwnership = countyOwnershipDao.find(managed.getEstablishedForId());
    if (countyOwnership == null) {
      countyExists = false;
      countyOwnership = new CountyOwnership();
      countyOwnership.setEntityId(managed.getEstablishedForId());
      countyOwnership.setEntityCode(REFERRAL_ENTITY_CODE);
    }
    Referral referral = referralDao.find(managed.getEstablishedForId());
    String methodName = SET_COUNTY + referral.getCountySpecificCode() + FLAG;
    createOrUpdateCountyOwnership(countyOwnership, methodName, countyExists);
  }

  /**
   * 
   */
  private void createOrUpdateCountyOwnership(CountyOwnership countyOwnership, String methodName,
      Boolean countyExists) {
    Method method = null;
    try {
      if (!methodName.equals(SET_COUNTY + COUNTY_SPECIFIC_CODE_DEFAULT_CODE + FLAG)) {
        method = countyOwnership.getClass().getMethod(methodName, String.class);
        method.invoke(countyOwnership, SET_FLAG);
      }
    } catch (NoSuchMethodException | SecurityException | IllegalAccessException
        | IllegalArgumentException | InvocationTargetException e) {
      LOGGER.info(COUNTY_OWNERSHIP_UNABLE_TO_TRIGGER, countyOwnership);
      LOGGER.error(e.getMessage(), e);
      throw new TriggerTableException();
    }

    if (countyExists) {
      countyOwnershipDao.update(countyOwnership);
    } else {
      countyOwnershipDao.create(countyOwnership);
    }
  }

}
