package gov.ca.cwds.inject;

import com.google.inject.AbstractModule;
import com.google.inject.Injector;
import com.google.inject.Provides;

import gov.ca.cwds.rest.api.domain.es.IndexQueryRequest;
import gov.ca.cwds.rest.api.domain.es.IndexQueryResponse;
import gov.ca.cwds.rest.resources.ResourceDelegate;
import gov.ca.cwds.rest.resources.ServiceBackedResourceDelegate;
import gov.ca.cwds.rest.resources.SimpleResourceDelegate;
import gov.ca.cwds.rest.resources.cms.AllegationResource;
import gov.ca.cwds.rest.services.AddressService;
import gov.ca.cwds.rest.services.es.IndexQueryService;

/**
 * Registers CWDS API referential integrity classes with Guice for dependency injection.
 * 
 * @author CWDS API Team
 */
@SuppressWarnings("javadoc")
public class ReferentialIntegrityModule extends AbstractModule {

  /**
   * Default, no-op constructor.
   */
  public ReferentialIntegrityModule() {
    // Default, no-op.
  }

  @Override
  protected void configure() {
    bind(AllegationResource.class);
  }

  @Provides
  @AddressServiceBackedResource
  public ResourceDelegate addressServiceBackedResource(Injector injector) {
    return new ServiceBackedResourceDelegate(injector.getInstance(AddressService.class));
  }

  @Provides
  @IntakeIndexQueryServiceResource
  public SimpleResourceDelegate<String, IndexQueryRequest, IndexQueryResponse, IndexQueryService> intakeIndexQueryResource(
      Injector injector) {
    return new SimpleResourceDelegate<>(injector.getInstance(IndexQueryService.class));
  }

}
