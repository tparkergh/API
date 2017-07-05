package gov.ca.cwds.data.persistence.cms;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import gov.ca.cwds.data.CmsSystemCodeSerializer;
import gov.ca.cwds.data.cms.TestSystemCodeCache;
import io.dropwizard.jackson.Jackson;

public class SystemCodeTestHarness {

  public static final ObjectMapper MAPPER;

  /**
   * Auto-magically translate CMS system codes when serializing JSON.
   */
  static {
    // Inject system code cache.
    ObjectMapper mapper = Jackson.newObjectMapper();
    SimpleModule module = new SimpleModule("SystemCodeModule",
        new Version(1, 0, 24, "alpha", "ca.gov.data.persistence.cms", "syscode"));
    module.addSerializer(Short.class, new CmsSystemCodeSerializer(new TestSystemCodeCache()));
    mapper.registerModule(module);
    MAPPER = mapper;
  }

}
