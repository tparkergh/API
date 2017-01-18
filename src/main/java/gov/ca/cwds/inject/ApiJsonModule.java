package gov.ca.cwds.inject;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;

import gov.ca.cwds.data.CmsSystemCodeSerializer;
import gov.ca.cwds.data.persistence.cms.ISystemCodeCache;

/**
 * Identifies all CWDS API business layer (aka, service) classes available for dependency injection
 * by Guice.
 * 
 * @author CWDS API Team
 */
public class ApiJsonModule extends AbstractModule {

  /**
   * Default, no-op constructor.
   */
  public ApiJsonModule() {
    // Default, no-op.
  }

  @Override
  protected void configure() {
    // Register system code translator with Jackson.
    SimpleModule module =
        new SimpleModule("SystemCodeModule", new Version(0, 1, 0, "a", "alpha", ""));
    module.addSerializer(Short.class,
        new CmsSystemCodeSerializer(Guice.createInjector().getInstance(ISystemCodeCache.class)));
    Guice.createInjector().getInstance(ObjectMapper.class).registerModule(module);
  }

}
