package gov.ca.cwds.inject;

import com.google.inject.AbstractModule;

import gov.ca.cwds.data.CmsSystemCodeSerializer;
import gov.ca.cwds.data.persistence.cms.CmsSystemCodeCacheService;
import gov.ca.cwds.data.persistence.cms.ApiSystemCodeCache;
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
import gov.ca.cwds.rest.services.es.AutoCompletePersonService;

/**
 * Identifies all CWDS API business layer (aka, service) classes available for dependency injection
 * (aka, DI) by Google Guice.
 * 
 * @author CWDS API Team
 */
public class ServicesModule extends AbstractModule {

  /**
   * Default, no-op constructor.
   */
  public ServicesModule() {
    // Default, no-op.
  }

  @Override
  protected void configure() {
    bind(AddressService.class);
    bind(PersonService.class);
    bind(ScreeningService.class);

    bind(AllegationService.class);
    bind(CmsDocReferralClientService.class);
    bind(CmsDocumentService.class);
    bind(CmsReferralService.class);
    bind(ReferralClientService.class);
    bind(ReferralService.class);
    bind(ReporterService.class);
    bind(StaffPersonService.class);
    bind(AddressValidationService.class);
    bind(AutoCompletePersonService.class);
    bind(CrossReportService.class);

    // Register CMS system code translator.
    bind(ApiSystemCodeCache.class).to(CmsSystemCodeCacheService.class).asEagerSingleton();
    bind(CmsSystemCodeSerializer.class).asEagerSingleton();
  }

}
