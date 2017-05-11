package gov.ca.cwds.inject;

import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

import com.google.inject.Inject;

import gov.ca.cwds.data.persistence.cms.Client;
import gov.ca.cwds.data.persistence.cms.ReferralClient;
import gov.ca.cwds.rest.services.cms.CountyOwnershipService;

/**
 * 
 * @author CWDS API Team
 *
 */
public class EventListener {

  private CountyOwnershipService countyService;

  /**
   * default constructor
   */
  public EventListener() {
    super();
  }

  /**
   * @param countyService - countyService
   */
  @Inject
  public EventListener(CountyOwnershipService countyService) {
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
      System.out.println("Listening User Pre Persist : " + ((ReferralClient) ob).getClientId());
    }

  }

  /**
   * Method for the after post
   * 
   * @param ob object instance
   */
  @PostPersist
  public void userPostPersist(Object ob) {
    System.out.println("Listening User Post Persist : " + ((Client) ob).getId());
    // countyService = Listner.getCountyService();
    if (ob != null) {
      if (ob instanceof Client) {
        Client client = (Client) ob;
        System.out.println("aaaaaaaaaaaaaaa" + client);
        countyService.createCountyFromClient(client);
      }

    }

  }

  /**
   * @param ob object instance
   */
  @PostLoad
  public void userPostLoad(Object ob) {
    System.out.println("Listening User Post Load Adarsh : " + ((ReferralClient) ob).getClientId());
  }

  /**
   * @param ob object instance
   */
  @PreUpdate
  public void userPreUpdate(ReferralClient ob) {
    System.out.println("Listening User Pre Update : " + ob.getClientId());
  }

  /**
   * @param ob object instance
   */
  @PostUpdate
  public void userPostUpdate(ReferralClient ob) {
    System.out.println("Listening User Post Update : " + ob.getClientId());
  }

  /**
   * @param ob object instance
   */
  @PreRemove
  public void userPreRemove(ReferralClient ob) {
    System.out.println("Listening User Pre Remove : " + ob.getClientId());
  }

  /**
   * @param ob object instance
   */
  @PostRemove
  public void userPostRemove(ReferralClient ob) {
    System.out.println("Listening User Post Remove : " + ob.getClientId());
  }
}
