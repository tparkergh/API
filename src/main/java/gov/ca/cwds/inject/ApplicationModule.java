package gov.ca.cwds.inject;

import com.google.inject.AbstractModule;

import gov.ca.cwds.rest.ApiApplication;
import gov.ca.cwds.rest.ApiConfiguration;
import gov.ca.cwds.rest.WebSecurityConfiguration;
import io.dropwizard.setup.Bootstrap;

/**
 * Bootstraps and configures the CWDS RESTful API application.
 * 
 * @author CWDS API Team
 * @see ApiApplication
 */
public class ApplicationModule extends AbstractModule {

  private Bootstrap<ApiConfiguration> bootstrap;

  /**
   * Constructor. {@link AbstractModule#AbstractModule()}
   * 
   * @param bootstrap API configuration
   */
  public ApplicationModule(Bootstrap<ApiConfiguration> bootstrap) {
    super();
    this.bootstrap = bootstrap;
  }

  /**
   * Configure and initialize API components, including services, resources, data access objects
   * (DAO), web service filters, and auditing.
   * 
   * {@inheritDoc}
   */
  @Override
  protected void configure() {
    install(new DataAccessModule(bootstrap));
    install(new ServicesModule());
    install(new ResourcesModule());
    install(new FiltersModule<ApiConfiguration>());
    install(new AuditingModule());
  }

  /**
   * Provides WebSecurityConfiguration
   * 
   * @param configuration The configuration for application
   * @return An object of WebSecurityConfiguration
   */
  public WebSecurityConfiguration provideWebSecurityConfiguration(ApiConfiguration configuration) {
    return configuration.getWebSecurityConfiguration();
  }
}
