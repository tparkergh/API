package gov.ca.cwds.rest.jdbi.cms;

import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import gov.ca.cwds.inject.CmsSessionFactory;
import gov.ca.cwds.rest.api.persistence.cms.ServiceProvider;
import gov.ca.cwds.rest.jdbi.BaseDaoImpl;

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
