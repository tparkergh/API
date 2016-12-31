package gov.ca.cwds.data.cms;

import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.persistence.cms.ServiceProvider;
import gov.ca.cwds.inject.CmsSessionFactory;

/**
 * DAO for {@link ServiceProvider}.
 * 
 * @author CWDS API Team
 */
public class ServiceProviderDao extends BaseDaoImpl<ServiceProvider> {

  /**
   * Constructor
   * 
   * @param sessionFactory The sessionFactory
   */
  @Inject
  public ServiceProviderDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }
}
