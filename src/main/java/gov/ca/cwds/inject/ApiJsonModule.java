package gov.ca.cwds.inject;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;

import gov.ca.cwds.data.CmsSystemCodeSerializer;
import gov.ca.cwds.rest.ApiConfiguration;
import io.dropwizard.setup.Bootstrap;

/**
 * Identifies all CWDS API business layer (aka, service) classes available for dependency injection
 * by Guice.
 * 
 * @author CWDS API Team
 */
public class ApiJsonModule extends AbstractModule {

  private Bootstrap<ApiConfiguration> bootstrap;

  /**
   * Default, no-op constructor.
   * 
   * @param bootstrap API live configuration
   */
  public ApiJsonModule(Bootstrap<ApiConfiguration> bootstrap) {
    this.bootstrap = bootstrap;
  }

  @Override
  protected void configure() {
    // Register system code translator with Jackson.
    SimpleModule module =
        new SimpleModule("SystemCodeModule", new Version(0, 1, 0, "a", "alpha", ""));
    module.addSerializer(Short.class, new CmsSystemCodeSerializer(
        Guice.createInjector().getInstance(gov.ca.cwds.rest.api.domain.cms.SystemCodeCache.class)));
    bootstrap.getObjectMapper().registerModule(module);
  }

}
