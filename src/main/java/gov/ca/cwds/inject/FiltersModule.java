package gov.ca.cwds.inject;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;

import gov.ca.cwds.rest.ApiApplication;
import gov.ca.cwds.rest.ApiConfiguration;
import gov.ca.cwds.rest.WebSecurityConfiguration;
import gov.ca.cwds.rest.filters.RequestExecutionContextFilter;
import gov.ca.cwds.rest.filters.RequestResponseLoggingFilter;
import gov.ca.cwds.rest.filters.WebSecurityFilter;

/**
 * Dependency injection (DI) for Filter classes.
 * 
 * <p>
 * Register filters her with Guice and configure them in {@link ApiApplication}, method
 * registerFilters.
 * </p>
 * 
 * @author CWDS API Team
 */
public class FiltersModule extends AbstractModule {

  @Override
  protected void configure() {
    bind(RequestExecutionContextFilter.class);
    bind(RequestResponseLoggingFilter.class);
    bind(WebSecurityFilter.class);
  }

  @Provides
  public WebSecurityConfiguration provideWebSecurityConfiguration(ApiConfiguration configuration) {
    return configuration.getWebSecurityConfiguration();
  }

}
