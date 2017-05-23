package gov.ca.cwds.rest.business.rules;

import javax.persistence.Table;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.cms.CountyTriggerDao;
import gov.ca.cwds.data.cms.StaffPersonDao;
import gov.ca.cwds.data.persistence.cms.CountyTrigger;
import gov.ca.cwds.data.persistence.cms.Referral;
import gov.ca.cwds.data.persistence.cms.ReferralClient;
import gov.ca.cwds.data.persistence.cms.StaffPerson;
import gov.ca.cwds.data.rules.TriggerTablesDao;

/**
 * Business layer object to work on LA Trigger
 *
 * @author CWDS API Team
 */
public class LACountyTrigger {

  private static final String CLIENT_COUNTYOWNERSHIP = "C";

  private static final String ADDRESS_COUNTYOWNERSHIP = "A";

  private static final Logger LOGGER = LoggerFactory.getLogger(LACountyTrigger.class);

  private StaffPersonDao staffpersonDao;
  private CountyTriggerDao countyTriggerDao;
  private TriggerTablesDao triggerTablesDao;

  /**
   * @param staffpersonDao - staffpersonDao
   * @param countyTriggerDao - countyTriggerDao
   * @param triggerTablesDao - triggerTablesDao
   */
  @Inject
  public LACountyTrigger(StaffPersonDao staffpersonDao, CountyTriggerDao countyTriggerDao,
      TriggerTablesDao triggerTablesDao) {
    this.staffpersonDao = staffpersonDao;
    this.countyTriggerDao = countyTriggerDao;
    this.triggerTablesDao = triggerTablesDao;
  }


  /**
   * @param object The object
   * @return the countyTrigger
   */
  public boolean createCountyTrigger(Object object) {
    Referral referral;
    ReferralClient referralClient;
    if (object instanceof Referral) {
      referral = (Referral) object;

      boolean countyTriggerExist = false;
      if (countyTriggerDao.find(referral.getAllegesAbuseOccurredAtAddressId()) != null) {
        countyTriggerExist = true;
      }

      StaffPerson staffperson = staffpersonDao.find(referral.getLastUpdatedId());
      if (staffperson != null
          && (triggerTablesDao.getLaCountySpecificCode().equals(staffperson.getCountyCode()))) {

        if (referral.getAllegesAbuseOccurredAtAddressId() != ""
            && referral.getAllegesAbuseOccurredAtAddressId() != null) {
          CountyTrigger countyTrigger =
              new CountyTrigger(referral.getAllegesAbuseOccurredAtAddressId(),
                  referral.getCountySpecificCode(), ADDRESS_COUNTYOWNERSHIP, null,
                  Referral.class.getDeclaredAnnotation(Table.class).name());
          if (countyTriggerExist) {
            countyTriggerDao.update(countyTrigger);
          } else {
            countyTriggerDao.create(countyTrigger);
          }
        }
      }
    }

    if (object instanceof ReferralClient) {
      referralClient = (ReferralClient) object;

      boolean countyTriggerExist = false;
      if (countyTriggerDao.find(referralClient.getClientId()) != null) {
        countyTriggerExist = true;
      }

      StaffPerson staffperson = staffpersonDao.find(referralClient.getLastUpdatedId());
      if (staffperson != null
          && (triggerTablesDao.getLaCountySpecificCode().equals(staffperson.getCountyCode()))) {

        if (referralClient.getClientId() != "" && referralClient.getClientId() != null) {
          CountyTrigger countyTrigger = new CountyTrigger(referralClient.getClientId(),
              referralClient.getCountySpecificCode(), CLIENT_COUNTYOWNERSHIP, null,
              ReferralClient.class.getDeclaredAnnotation(Table.class).name());
          if (countyTriggerExist) {
            countyTriggerDao.update(countyTrigger);
          } else {
            countyTriggerDao.create(countyTrigger);
          }
        }
      }
    }

    return true;
  }

}
