package gov.ca.cwds.inject;

import gov.ca.cwds.rest.ApiConfiguration;
import gov.ca.cwds.rest.SwaggerConfiguration;
import gov.ca.cwds.rest.api.domain.cms.CmsDocument;
import gov.ca.cwds.rest.resources.AddressResource;
import gov.ca.cwds.rest.resources.AddressValidationResource;
import gov.ca.cwds.rest.resources.ApplicationResource;
import gov.ca.cwds.rest.resources.PersonResource;
import gov.ca.cwds.rest.resources.PersonSearchResource;
import gov.ca.cwds.rest.resources.ResourceDelegate;
import gov.ca.cwds.rest.resources.ScreeningResource;
import gov.ca.cwds.rest.resources.ServiceBackedResourceDelegate;
import gov.ca.cwds.rest.resources.SwaggerResource;
import gov.ca.cwds.rest.resources.TypedResourceDelegate;
import gov.ca.cwds.rest.resources.TypedServiceBackedResourceDelegate;
import gov.ca.cwds.rest.resources.cms.AllegationResource;
import gov.ca.cwds.rest.resources.cms.CmsDocReferralClientResource;
import gov.ca.cwds.rest.resources.cms.CmsDocumentResource;
import gov.ca.cwds.rest.resources.cms.CmsReferralResource;
import gov.ca.cwds.rest.resources.cms.ReferralClientResource;
import gov.ca.cwds.rest.resources.cms.ReferralResource;
import gov.ca.cwds.rest.resources.cms.ReporterResource;
import gov.ca.cwds.rest.resources.cms.StaffPersonResource;
import gov.ca.cwds.rest.services.AddressService;
import gov.ca.cwds.rest.services.AddressValidationService;
import gov.ca.cwds.rest.services.PersonService;
import gov.ca.cwds.rest.services.ScreeningService;
import gov.ca.cwds.rest.services.cms.AllegationService;
import gov.ca.cwds.rest.services.cms.CmsDocReferralClientService;
import gov.ca.cwds.rest.services.cms.CmsDocumentService;
import gov.ca.cwds.rest.services.cms.CmsReferralService;
import gov.ca.cwds.rest.services.cms.CrossReportService;
import gov.ca.cwds.rest.services.cms.ReferralClientService;
import gov.ca.cwds.rest.services.cms.ReferralService;
import gov.ca.cwds.rest.services.cms.ReporterService;
import gov.ca.cwds.rest.services.cms.StaffPersonService;

import com.google.inject.AbstractModule;
import com.google.inject.Injector;
import com.google.inject.Provides;
import com.google.inject.name.Named;

public class ResourcesModule extends AbstractModule {

  public ResourcesModule() {}

  @Override
  protected void configure() {
    bind(ApplicationResource.class);
    bind(SwaggerResource.class);
    bind(AddressResource.class);
    bind(PersonResource.class);
    bind(ScreeningResource.class);
    bind(AllegationResource.class);
    // bind(ClientResource.class);
    bind(CmsDocReferralClientResource.class);
    bind(CmsDocumentResource.class);
    bind(CmsReferralResource.class);
    bind(ReferralClientResource.class);
    bind(ReferralResource.class);
    bind(ReporterResource.class);
    bind(StaffPersonResource.class);
    bind(PersonSearchResource.class);
    bind(AddressValidationResource.class);
  }

  @Provides
  public SwaggerConfiguration swaggerConfiguration(ApiConfiguration apiConfiguration) {
    return apiConfiguration.getSwaggerConfiguration();
  }

  @Provides
  @Named("app.name")
  public String appName(ApiConfiguration apiConfiguration) {
    return apiConfiguration.getApplicationName();
  }

  @Provides
  @Named("app.version")
  public String appVersion(ApiConfiguration apiConfiguration) {
    return apiConfiguration.getVersion();
  }

  @Provides
  @AddressServiceBackedResource
  public ResourceDelegate addressServiceBackedResource(Injector injector) {
    return new ServiceBackedResourceDelegate(injector.getInstance(AddressService.class));
  }

  @Provides
  @PersonServiceBackedResource
  public ResourceDelegate personServiceBackedResource(Injector injector) {
    return new ServiceBackedResourceDelegate(injector.getInstance(PersonService.class));
  }

  @Provides
  @ScreeningServiceBackedResource
  public ResourceDelegate screeningServiceBackedResource(Injector injector) {
    return new ServiceBackedResourceDelegate(injector.getInstance(ScreeningService.class));
  }

  @Provides
  @AllegationServieBackedResource
  public ResourceDelegate allegationServieBackedResource(Injector injector) {
    return new ServiceBackedResourceDelegate(injector.getInstance(AllegationService.class));
  }

  // @Provides
  // @ClientServiceBackedResource
  // public ResourceDelegate clientServiceBackedResource(Injector injector) {
  // return new ServiceBackedResourceDelegate(injector.getInstance(ClientServ.class));
  // }

  @Provides
  @CmsDocumentReferralClientServiceBackedResource
  public ResourceDelegate cmsDocumentReferralClientServiceBackedResource(Injector injector) {
    return new ServiceBackedResourceDelegate(
        injector.getInstance(CmsDocReferralClientService.class));
  }

  @Provides
  @CmsDocumentBackedResource
  public TypedResourceDelegate<String, CmsDocument> cmsDocumentBackedResource(Injector injector) {
    return new TypedServiceBackedResourceDelegate<String, CmsDocument, CmsDocument>(
        injector.getInstance(CmsDocumentService.class));
  }

  @Provides
  @CmsReferralServiceBackedResource
  public ResourceDelegate cmsReferralServiceBackedResource(Injector injector) {
    return new ServiceBackedResourceDelegate(injector.getInstance(CmsReferralService.class));
  }

  @Provides
  @CrossReportServiceBackedResource
  public ResourceDelegate crossReportServiceBackedResource(Injector injector) {
    return new ServiceBackedResourceDelegate(injector.getInstance(CrossReportService.class));
  }

  @Provides
  @ReferralClientServiceBackedResource
  public ResourceDelegate referralClientServiceBackedResource(Injector injector) {
    return new ServiceBackedResourceDelegate(injector.getInstance(ReferralClientService.class));
  }

  @Provides
  @ReferralServiceBackedResource
  public ResourceDelegate referralServiceBackedResource(Injector injector) {
    return new ServiceBackedResourceDelegate(injector.getInstance(ReferralService.class));
  }

  @Provides
  @ReporterServiceBackedResource
  public ResourceDelegate reporterServiceBackedResource(Injector injector) {
    return new ServiceBackedResourceDelegate(injector.getInstance(ReporterService.class));
  }

  @Provides
  @StaffPersonServiceBackedResource
  public ResourceDelegate staffPersonServiceBackedResource(Injector injector) {
    return new ServiceBackedResourceDelegate(injector.getInstance(StaffPersonService.class));
  }

  @Provides
  @AddressValidationServiceBackedResource
  public ResourceDelegate addressValidationServiceBackedResource(Injector injector) {
    return new ServiceBackedResourceDelegate(injector.getInstance(AddressValidationService.class));
  }
}
