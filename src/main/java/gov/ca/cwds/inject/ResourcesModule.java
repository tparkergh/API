package gov.ca.cwds.inject;

import com.google.inject.AbstractModule;
import com.google.inject.Injector;
import com.google.inject.Provides;
import com.google.inject.name.Named;

import gov.ca.cwds.rest.ApiConfiguration;
import gov.ca.cwds.rest.SwaggerConfiguration;
import gov.ca.cwds.rest.api.domain.cms.CmsDocument;
import gov.ca.cwds.rest.api.domain.es.AutoCompletePersonRequest;
import gov.ca.cwds.rest.api.domain.es.AutoCompletePersonResponse;
import gov.ca.cwds.rest.api.domain.es.IndexQueryRequest;
import gov.ca.cwds.rest.api.domain.es.IndexQueryResponse;
import gov.ca.cwds.rest.resources.AddressResource;
import gov.ca.cwds.rest.resources.AddressValidationResource;
import gov.ca.cwds.rest.resources.ApplicationResource;
import gov.ca.cwds.rest.resources.ParticipantResource;
import gov.ca.cwds.rest.resources.PersonResource;
import gov.ca.cwds.rest.resources.ResourceDelegate;
import gov.ca.cwds.rest.resources.ScreeningResource;
import gov.ca.cwds.rest.resources.ScreeningToReferralResource;
import gov.ca.cwds.rest.resources.ServiceBackedResourceDelegate;
import gov.ca.cwds.rest.resources.SimpleResourceDelegate;
import gov.ca.cwds.rest.resources.SwaggerResource;
import gov.ca.cwds.rest.resources.TypedResourceDelegate;
import gov.ca.cwds.rest.resources.TypedServiceBackedResourceDelegate;
import gov.ca.cwds.rest.resources.cms.AllegationPerpetratorHistoryResource;
import gov.ca.cwds.rest.resources.cms.AllegationResource;
import gov.ca.cwds.rest.resources.cms.ChildClientResource;
import gov.ca.cwds.rest.resources.cms.ClientResource;
import gov.ca.cwds.rest.resources.cms.CmsDocReferralClientResource;
import gov.ca.cwds.rest.resources.cms.CmsDocumentResource;
import gov.ca.cwds.rest.resources.cms.CmsNSReferralResource;
import gov.ca.cwds.rest.resources.cms.CmsReferralResource;
import gov.ca.cwds.rest.resources.cms.CrossReportResource;
import gov.ca.cwds.rest.resources.cms.LongTextResource;
import gov.ca.cwds.rest.resources.cms.ReferralClientResource;
import gov.ca.cwds.rest.resources.cms.ReferralResource;
import gov.ca.cwds.rest.resources.cms.ReporterResource;
import gov.ca.cwds.rest.resources.cms.StaffPersonResource;
import gov.ca.cwds.rest.services.AddressService;
import gov.ca.cwds.rest.services.AddressValidationService;
import gov.ca.cwds.rest.services.ParticipantService;
import gov.ca.cwds.rest.services.PersonService;
import gov.ca.cwds.rest.services.ScreeningService;
import gov.ca.cwds.rest.services.ScreeningToReferralService;
import gov.ca.cwds.rest.services.cms.AllegationPerpetratorHistoryService;
import gov.ca.cwds.rest.services.cms.AllegationService;
import gov.ca.cwds.rest.services.cms.ChildClientService;
import gov.ca.cwds.rest.services.cms.ClientService;
import gov.ca.cwds.rest.services.cms.CmsDocReferralClientService;
import gov.ca.cwds.rest.services.cms.CmsDocumentService;
import gov.ca.cwds.rest.services.cms.CmsNSReferralService;
import gov.ca.cwds.rest.services.cms.CmsReferralService;
import gov.ca.cwds.rest.services.cms.CrossReportService;
import gov.ca.cwds.rest.services.cms.LongTextService;
import gov.ca.cwds.rest.services.cms.ReferralClientService;
import gov.ca.cwds.rest.services.cms.ReferralService;
import gov.ca.cwds.rest.services.cms.ReporterService;
import gov.ca.cwds.rest.services.cms.StaffPersonService;
import gov.ca.cwds.rest.services.es.AutoCompletePersonService;
import gov.ca.cwds.rest.services.es.IndexQueryService;


/**
 * Identifies all CWDS API domain resource classes available for dependency injection by Guice.
 * 
 * @author CWDS API Team
 */
@SuppressWarnings("javadoc")
public class ResourcesModule extends AbstractModule {

  /**
   * Default, no-op constructor.
   */
  public ResourcesModule() {
    // Default, no-op.
  }

  @Override
  protected void configure() {
    bind(ApplicationResource.class);
    bind(SwaggerResource.class);
    bind(AddressResource.class);
    bind(ParticipantResource.class);
    bind(PersonResource.class);
    bind(ScreeningResource.class);
    bind(AllegationResource.class);
    bind(ClientResource.class);
    bind(CmsDocReferralClientResource.class);
    bind(CmsDocumentResource.class);
    bind(CmsNSReferralResource.class);
    bind(CmsReferralResource.class);
    bind(ReferralClientResource.class);
    bind(ReferralResource.class);
    bind(ReporterResource.class);
    bind(StaffPersonResource.class);
    bind(AddressValidationResource.class);
    bind(ScreeningToReferralResource.class);
    bind(LongTextResource.class);
    bind(AllegationPerpetratorHistoryResource.class);
    bind(CrossReportResource.class);
    bind(ChildClientResource.class);
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

  @Provides
  @LongTextServiceBackedResource
  public ResourceDelegate longTextServiceBackedResource(Injector injector) {
    return new ServiceBackedResourceDelegate(injector.getInstance(LongTextService.class));
  }

  @Provides
  @ChildClientServiceBackedResource
  public ResourceDelegate ChildClientServiceBackedResource(Injector injector) {
    return new ServiceBackedResourceDelegate(injector.getInstance(ChildClientService.class));
  }

  @Provides
  @AllegationPerpetratorHistoryServiceBackedResource
  public ResourceDelegate allegationPerpetratorHistoryServiceBackedResource(Injector injector) {
    return new ServiceBackedResourceDelegate(
        injector.getInstance(AllegationPerpetratorHistoryService.class));
  }

  @Provides
  @CmsDocumentReferralClientServiceBackedResource
  public ResourceDelegate cmsDocumentReferralClientServiceBackedResource(Injector injector) {
    return new ServiceBackedResourceDelegate(
        injector.getInstance(CmsDocReferralClientService.class));
  }

  @Provides
  @CmsDocumentBackedResource
  public TypedResourceDelegate<String, CmsDocument> cmsDocumentBackedResource(Injector injector) {
    return new TypedServiceBackedResourceDelegate<>(injector.getInstance(CmsDocumentService.class));
  }

  @Provides
  @ClientServiceBackedResource
  public ResourceDelegate clientServiceBackedResource(Injector injector) {
    return new ServiceBackedResourceDelegate(injector.getInstance(ClientService.class));
  }

  @Provides
  @CmsNSReferralServiceBackedResource
  public ResourceDelegate cmsNSReferralServiceBackedResource(Injector injector) {
    return new ServiceBackedResourceDelegate(injector.getInstance(CmsNSReferralService.class));
  }

  @Provides
  @CmsReferralServiceBackedResource
  public ResourceDelegate cmsReferralServiceBackedResource(Injector injector) {
    return new ServiceBackedResourceDelegate(injector.getInstance(CmsReferralService.class));
  }

  @Provides
  @ScreeningToReferralServiceBackedResource
  public ResourceDelegate screeningToReferralBackedResource(Injector injector) {
    return new ServiceBackedResourceDelegate(
        injector.getInstance(ScreeningToReferralService.class));
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

  @Provides
  @CrossReportServiceBackedResource
  public ResourceDelegate crossReportServiceBackedResource(Injector injector) {
    return new ServiceBackedResourceDelegate(injector.getInstance(CrossReportService.class));
  }

  @Provides
  @ParticipantServiceBackedResource
  public ResourceDelegate participantServiceBackedResource(Injector injector) {
    return new ServiceBackedResourceDelegate(injector.getInstance(ParticipantService.class));
  }

  @Provides
  @IntakePersonAutoCompleteServiceResource
  public SimpleResourceDelegate<String, AutoCompletePersonRequest, AutoCompletePersonResponse, AutoCompletePersonService> intakeAutoCompletePersonResource(
      Injector injector) {
    return new SimpleResourceDelegate<>(injector.getInstance(AutoCompletePersonService.class));
  }

  @Provides
  @IntakeIndexQueryServiceResource
  public SimpleResourceDelegate<String, IndexQueryRequest, IndexQueryResponse, IndexQueryService> intakeIndexQueryResource(
      Injector injector) {
    return new SimpleResourceDelegate<>(injector.getInstance(IndexQueryService.class));
  }


}
