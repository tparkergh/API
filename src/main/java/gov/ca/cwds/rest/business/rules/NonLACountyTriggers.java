package gov.ca.cwds.rest.business.rules;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.cms.CountyOwnershipDao;
import gov.ca.cwds.data.cms.StaffPersonDao;
import gov.ca.cwds.data.persistence.cms.CountyOwnership;
import gov.ca.cwds.data.persistence.cms.ReferralClient;
import gov.ca.cwds.data.persistence.cms.StaffPerson;
import gov.ca.cwds.rest.services.ServiceException;

/**
 * Business layer object to work on Non LA Triggers
 *
 * @author CWDS API Team
 */
public class NonLACountyTriggers {

  private static final String CLIENT_ENTITY_CODE = "C";
  private static final String SET_FLAG = "Y";
  private static final String FLAG = "Flag";
  private static final String SET_COUNTY = "setCounty";

  private static final Logger LOGGER = LoggerFactory.getLogger(NonLACountyTriggers.class);

  private StaffPersonDao staffpersonDao;
  private CountyOwnershipDao countyOwnershipDao;

  /**
   * @param staffpersonDao - staffpersonDao
   * @param countyOwnershipDao - countyOwnershipDao
   */
  @Inject
  public NonLACountyTriggers(StaffPersonDao staffpersonDao, CountyOwnershipDao countyOwnershipDao) {
    this.staffpersonDao = staffpersonDao;
    this.countyOwnershipDao = countyOwnershipDao;
  }

  /**
   * 
   * @param managed referralClient This method triggers the CountyOwnership table with the
   *        associated lastUpdatedId
   * 
   */
  public void createAndUpdateCoutyOwnership(ReferralClient managed) {
    Boolean countyExists = true;
    StaffPerson staffperson = staffpersonDao.find(managed.getLastUpdatedId());
    if (staffperson != null && !("19".equals(staffperson.getCountyCode()))) {
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
        throw new ServiceException(e);
      }

      if (countyExists) {
        countyOwnershipDao.update(countyOwnership);
      } else {
        countyOwnershipDao.create(countyOwnership);
      }

    }
  }

}
