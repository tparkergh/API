package gov.ca.cwds.inject;

import gov.ca.cwds.rest.ApiConfiguration;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.hibernate.UnitOfWorkAwareProxyFactory;

/**
 * 
 * @author CWDS API Team
 */
public class UnitOfWorkModule {

  private static UnitOfWorkAwareProxyFactory proxyFactory;

  private UnitOfWorkModule() {

  }

  @SuppressWarnings({"javadoc", "unchecked"})
  public static UnitOfWorkAwareProxyFactory getUnitOfWorkProxyFactory(
      HibernateBundle<ApiConfiguration>... bundles) {
    if (proxyFactory == null) {
      proxyFactory = new UnitOfWorkAwareProxyFactory(bundles);
    }
    return proxyFactory;
  }

}
