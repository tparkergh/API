package gov.ca.cwds.data.cms;

import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.CrudsDaoImpl;
import gov.ca.cwds.data.persistence.cms.CountyTrigger;
import gov.ca.cwds.inject.CmsSessionFactory;

/**
 * DAO for {@link CountyTrigger}.
 * 
 * @author CWDS API Team
 */
public class CountyTriggerDao extends CrudsDaoImpl<CountyTrigger> {

  /**
   * Constructor
   * 
   * @param sessionFactory The session factory
   */
  @Inject
  public CountyTriggerDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }


}
