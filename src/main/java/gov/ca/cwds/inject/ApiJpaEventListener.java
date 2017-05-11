package gov.ca.cwds.inject;

import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.persistence.cms.Client;
import gov.ca.cwds.data.persistence.cms.ReferralClient;
import gov.ca.cwds.rest.services.cms.CountyOwnershipService;

/**
 * 
 * @author CWDS API Team
 *
 */
public class ApiJpaEventListener {

  private static final Logger LOGGER = LoggerFactory.getLogger(ApiJpaEventListener.class);

  private CountyOwnershipService countyService;

  /**
   * default constructor
   */
  public ApiJpaEventListener() {
    super();
  }

  /**
   * @param countyService - countyService
   */
  @Inject
  public ApiJpaEventListener(CountyOwnershipService countyService) {
    this.countyService = countyService;
  }

  /**
   * Method for the before post
   * 
   * @param ob object instance
   */
  @PrePersist
  public void userPrePersist(Object ob) {
    if (ob instanceof ReferralClient) {
      LOGGER.info("Listening User Pre Persist : " + ((ReferralClient) ob).getClientId());
    }

  }

  /**
   * Method for the after post
   * 
   * @param ob object instance
   */
  @PostPersist
  public void userPostPersist(Object ob) {
    LOGGER.info("Listening User Post Persist : " + ((Client) ob).getId());
    // countyService = Listner.getCountyService();
    if (ob != null) {
      if (ob instanceof Client) {
        Client client = (Client) ob;
        LOGGER.info("aaaaaaaaaaaaaaa" + client);
        countyService.createCountyFromClient(client);
      }

    }

  }

  /**
   * @param ob object instance
   */
  @PostLoad
  public void userPostLoad(Object ob) {
    LOGGER.info("Listening User Post Load Adarsh : " + ((ReferralClient) ob).getClientId());
  }

  /**
   * @param ob object instance
   */
  @PreUpdate
  public void userPreUpdate(ReferralClient ob) {
    LOGGER.info("Listening User Pre Update : " + ob.getClientId());
  }

  /**
   * @param ob object instance
   */
  @PostUpdate
  public void userPostUpdate(ReferralClient ob) {
    LOGGER.info("Listening User Post Update : " + ob.getClientId());
  }

  /**
   * @param ob object instance
   */
  @PreRemove
  public void userPreRemove(ReferralClient ob) {
    LOGGER.info("Listening User Pre Remove : " + ob.getClientId());
  }

  /**
   * @param ob object instance
   */
  @PostRemove
  public void userPostRemove(ReferralClient ob) {
    LOGGER.info("Listening User Post Remove : " + ob.getClientId());
  }
}
