package gov.ca.cwds.rest.business.rules;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.cms.CountyOwnershipDao;
import gov.ca.cwds.data.persistence.cms.Client;
import gov.ca.cwds.data.persistence.cms.CountyOwnership;
import gov.ca.cwds.data.persistence.cms.ReferralClient;
import gov.ca.cwds.data.rules.TriggerTableException;

/**
 * Business layer object to work on Non LA Triggers
 * 
 * <p>
 * If the staffPerson is from the LA County, it will trigger the countyOwnership table with the
 * associated foreign key and updates the trigger table if the record is existing
 * <p>
 *
 * @author CWDS API Team
 */
public class NonLACountyTriggers {

  private static final String CLIENT_ENTITY_CODE = "C";
  private static final String SET_FLAG = "Y";
  private static final String FLAG = "Flag";
  private static final String SET_COUNTY = "setCounty";

  private static final Logger LOGGER = LoggerFactory.getLogger(NonLACountyTriggers.class);

  private CountyOwnershipDao countyOwnershipDao;

  /**
   * @param countyOwnershipDao - countyOwnershipDao
   */
  @Inject
  public NonLACountyTriggers(CountyOwnershipDao countyOwnershipDao) {
    this.countyOwnershipDao = countyOwnershipDao;
  }

  /**
   * @param managed Client creates the countyOwnership with the associated staffPerson
   */
  public void createClientCountyTrigger(Client managed) {
    CountyOwnership countyOwnership = new CountyOwnership();
    countyOwnership.setEntityId(managed.getPrimaryKey());
    countyOwnership.setEntityCode(CLIENT_ENTITY_CODE);
    countyOwnershipDao.create(countyOwnership);

  }

  /**
   * @param managed referralClient creates or updates the countyOwnership with the associated
   *        staffPerson
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
    Method method = null;
    try {
      method = countyOwnership.getClass().getMethod(methodName, String.class);
      method.invoke(countyOwnership, SET_FLAG);
    } catch (NoSuchMethodException | SecurityException | IllegalAccessException
        | IllegalArgumentException | InvocationTargetException e) {
      LOGGER.info("CountyOwnership Unable to Trigger : {}", countyOwnership);
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
