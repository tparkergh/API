package gov.ca.cwds.rest.business.rules;

import java.util.Date;

import javax.persistence.Table;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.cms.CountyTriggerDao;
import gov.ca.cwds.data.persistence.cms.ClientAddress;
import gov.ca.cwds.data.persistence.cms.CountyTrigger;
import gov.ca.cwds.data.persistence.cms.Referral;
import gov.ca.cwds.data.persistence.cms.ReferralClient;
import gov.ca.cwds.rest.filters.RequestExecutionContext;

/**
 * Business layer object to work on LA County Trigger
 * 
 * <p>
 * If the staffPerson is from the LA County, it will trigger the countyTrigger table with the
 * associated foreign key and the table will be cleared after the batch jobs run.
 * <p>
 * 
 * @author CWDS API Team
 */
public class LACountyTrigger {

  private static final String LA_COUNTY_SPECIFIC_CODE = "19";

  private static final String CLIENT_COUNTYOWNERSHIP = "C";

  private static final String ADDRESS_COUNTYOWNERSHIP = "A";

  private Date integratorTimeStamp = RequestExecutionContext.instance().getRequestStartTime();

  private static final Logger LOGGER = LoggerFactory.getLogger(LACountyTrigger.class);

  private CountyTriggerDao countyTriggerDao;

  /**
   * @param countyTriggerDao - countyTriggerDao
   */
  @Inject
  public LACountyTrigger(CountyTriggerDao countyTriggerDao) {
    this.countyTriggerDao = countyTriggerDao;
  }

  /**
   * @param object The object creates the county Trigger for the Referral and referralClient
   * @return the countyTrigger created
   */
  public boolean createCountyTrigger(Object object) {
    Referral referral;
    ReferralClient referralClient;
    if (object instanceof Referral) {
      referral = (Referral) object;

      if (referral.getAllegesAbuseOccurredAtAddressId() != ""
          && referral.getAllegesAbuseOccurredAtAddressId() != null) {
        CountyTrigger countyTrigger = new CountyTrigger(referral.getCountySpecificCode(),
            ADDRESS_COUNTYOWNERSHIP, referral.getAllegesAbuseOccurredAtAddressId(),
            Referral.class.getDeclaredAnnotation(Table.class).name(), integratorTimeStamp);

        countyTriggerDao.create(countyTrigger);
        LOGGER.info("LA county referral address triggered");
      }
    }

    if (object instanceof ReferralClient) {
      referralClient = (ReferralClient) object;

      if (referralClient.getClientId() != "" && referralClient.getClientId() != null) {
        CountyTrigger countyTrigger = new CountyTrigger(referralClient.getCountySpecificCode(),
            CLIENT_COUNTYOWNERSHIP, referralClient.getClientId(),
            ReferralClient.class.getDeclaredAnnotation(Table.class).name(), integratorTimeStamp);

        countyTriggerDao.create(countyTrigger);
        LOGGER.info("LA county referralClient triggered");
      }
    }

    return true;
  }

  /**
   * @param object The object creates the county Trigger for the clientAddress
   * @return the CountyTrigger created
   */
  public boolean createClientAddressCountyTrigger(Object object) {
    ClientAddress clientAddress;

    if (object instanceof ClientAddress) {
      clientAddress = (ClientAddress) object;

      if (clientAddress.getFkClient() != "" && clientAddress.getFkClient() != null
          && clientAddress.getFkAddress() != "" && clientAddress.getFkAddress() != null) {
        CountyTrigger countyTrigger1 = new CountyTrigger(LA_COUNTY_SPECIFIC_CODE,
            CLIENT_COUNTYOWNERSHIP, clientAddress.getFkClient(),
            ClientAddress.class.getDeclaredAnnotation(Table.class).name(), integratorTimeStamp);

        CountyTrigger countyTrigger2 = new CountyTrigger(LA_COUNTY_SPECIFIC_CODE,
            ADDRESS_COUNTYOWNERSHIP, clientAddress.getFkAddress(),
            ClientAddress.class.getDeclaredAnnotation(Table.class).name(), integratorTimeStamp);

        countyTriggerDao.create(countyTrigger1);
        countyTriggerDao.create(countyTrigger2);
        LOGGER.info("LA county clientAddress triggered");
      }
    }
    return true;
  }

}
