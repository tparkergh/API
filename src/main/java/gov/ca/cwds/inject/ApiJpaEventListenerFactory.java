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

import gov.ca.cwds.data.persistence.cms.ReferralClient;
import gov.ca.cwds.rest.BaseApiApplication;

public class ApiJpaEventListenerFactory {

  private static final Logger LOGGER = LoggerFactory.getLogger(ApiJpaEventListenerFactory.class);

  private ApiJpaEventListener listener;

  protected synchronized ApiJpaEventListener initListener() {
    if (listener == null) {
      // Assumes that Guice initializes before the JPA event listener ...
      listener = BaseApiApplication.getInjector().getInstance(ApiJpaEventListener.class);
    }
    return listener;
  }

  public ApiJpaEventListenerFactory() {
    // Default, no-op. Wait till runtime to initialize the event listener.
  }

  /**
   * Method for the before post
   * 
   * @param ob object instance
   */
  @PrePersist
  public void userPrePersist(Object ob) {
    initListener().userPrePersist(ob);
  }

  /**
   * Method for the after post
   * 
   * @param ob object instance
   */
  @PostPersist
  public void userPostPersist(Object ob) {
    initListener().userPostPersist(ob);
  }

  /**
   * @param ob object instance
   */
  @PostLoad
  public void userPostLoad(Object ob) {
    initListener().userPostLoad(ob);
  }

  /**
   * @param ob object instance
   */
  @PreUpdate
  public void userPreUpdate(ReferralClient ob) {
    initListener().userPreUpdate(ob);
  }

  /**
   * @param ob object instance
   */
  @PostUpdate
  public void userPostUpdate(ReferralClient ob) {
    initListener().userPostUpdate(ob);
  }

  /**
   * @param ob object instance
   */
  @PreRemove
  public void userPreRemove(ReferralClient ob) {
    initListener().userPreRemove(ob);
  }

  /**
   * @param ob object instance
   */
  @PostRemove
  public void userPostRemove(ReferralClient ob) {
    initListener().userPostRemove(ob);
  }

}
