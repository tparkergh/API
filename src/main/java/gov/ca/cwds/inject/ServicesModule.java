package gov.ca.cwds.inject;

import gov.ca.cwds.data.CmsSystemCodeSerializer;
import gov.ca.cwds.data.cms.SystemCodeDao;
import gov.ca.cwds.data.cms.SystemMetaDao;
import gov.ca.cwds.rest.ApiConfiguration;
import gov.ca.cwds.rest.api.domain.ScreeningToReferral;
import gov.ca.cwds.rest.api.domain.cms.SystemCodeCache;
import gov.ca.cwds.rest.messages.MessageBuilder;
import gov.ca.cwds.rest.services.AddressService;
import gov.ca.cwds.rest.services.AddressValidationService;
import gov.ca.cwds.rest.services.PersonService;
import gov.ca.cwds.rest.services.ScreeningService;
import gov.ca.cwds.rest.services.cms.AllegationService;
import gov.ca.cwds.rest.services.cms.AssignmentService;
import gov.ca.cwds.rest.services.cms.CachingSystemCodeService;
import gov.ca.cwds.rest.services.cms.ClientCollateralService;
import gov.ca.cwds.rest.services.cms.ClientRelationshipService;
import gov.ca.cwds.rest.services.cms.CmsDocReferralClientService;
import gov.ca.cwds.rest.services.cms.CmsDocumentService;
import gov.ca.cwds.rest.services.cms.CmsNSReferralService;
import gov.ca.cwds.rest.services.cms.CmsReferralService;
import gov.ca.cwds.rest.services.cms.CrossReportService;
import gov.ca.cwds.rest.services.cms.DrmsDocumentService;
import gov.ca.cwds.rest.services.cms.LegacyKeyService;
import gov.ca.cwds.rest.services.cms.ReferralClientService;
import gov.ca.cwds.rest.services.cms.ReferralService;
import gov.ca.cwds.rest.services.cms.ReporterService;
import gov.ca.cwds.rest.services.cms.StaffPersonIdRetriever;
import gov.ca.cwds.rest.services.cms.StaffPersonService;
import gov.ca.cwds.rest.services.cms.SystemCodeService;
import gov.ca.cwds.rest.services.cms.TickleService;
import gov.ca.cwds.rest.services.es.AutoCompletePersonService;
import gov.ca.cwds.rest.services.es.IndexQueryService;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.hibernate.UnitOfWorkAspect;
import io.dropwizard.hibernate.UnitOfWorkAwareProxyFactory;

import javax.validation.Validation;
import javax.validation.Validator;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Provides;
import com.google.inject.matcher.Matchers;

/**
 * Identifies all CWDS API business layer (aka, service) classes available for dependency injection
 * (aka, DI) by Google Guice.
 * 
 * @author CWDS API Team
 */
public class ServicesModule extends AbstractModule {

  /**
   * @author CWDS API Team
   */
  public static class UnitOfWorkInterceptor implements org.aopalliance.intercept.MethodInterceptor {

    @Inject
    @CmsHibernateBundle
    HibernateBundle<ApiConfiguration> cmsHibernateBundle;
    UnitOfWorkAwareProxyFactory proxyFactory;

    @Inject
    @NsHibernateBundle
    HibernateBundle<ApiConfiguration> nsHibernateBundle;

    @SuppressWarnings("unchecked")
    @Override
    public Object invoke(org.aopalliance.intercept.MethodInvocation mi) throws Throwable {

      proxyFactory =
          UnitOfWorkModule.getUnitOfWorkProxyFactory(cmsHibernateBundle, nsHibernateBundle);
      UnitOfWorkAspect aspect = proxyFactory.newAspect();
      try {
        aspect.beforeStart(mi.getMethod().getAnnotation(UnitOfWork.class));
        Object result = mi.proceed();
        aspect.afterEnd();
        return result;
      } catch (Exception e) {
        aspect.onError();
        throw e;
      } finally {
        aspect.onFinish();
      }
    }

  }

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
    bind(gov.ca.cwds.rest.services.cms.AddressService.class);

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
    bind(CmsNSReferralService.class);
    bind(ScreeningToReferral.class);
    bind(IndexQueryService.class);
    bind(StaffPersonIdRetriever.class);
    bind(DrmsDocumentService.class);
    bind(LegacyKeyService.class);
    bind(AssignmentService.class);
    bind(TickleService.class);
    bind(ClientRelationshipService.class);
    bind(ClientCollateralService.class);

    // Register CMS system code translator.
    // bind(ApiSystemCodeCache.class).to(CmsSystemCodeCacheService.class).asEagerSingleton();
    // bind(CmsSystemCodeSerializer.class).asEagerSingleton();

    UnitOfWorkInterceptor interceptor = new UnitOfWorkInterceptor();
    bindInterceptor(Matchers.any(), Matchers.annotatedWith(UnitOfWork.class), interceptor);
    requestInjection(interceptor);
  }

  @Provides
  Validator provideValidator() {
    return Validation.buildDefaultValidatorFactory().getValidator();
  }

  @Provides
  MessageBuilder provideMessageBuilder() {
    return new MessageBuilder();
  }

  @Provides
  public SystemCodeService provideSystemCodeService(SystemCodeDao systemCodeDao,
      SystemMetaDao systemMetaDao) {
    final long secondsToRefreshCache = 15L * 24 * 60 * 60; // 15 days
    return new CachingSystemCodeService(systemCodeDao, systemMetaDao, secondsToRefreshCache, false);
  }

  @Provides
  public SystemCodeCache provideSystemCodeCache(SystemCodeService systemCodeService) {
    SystemCodeCache systemCodeCache = (SystemCodeCache) systemCodeService;
    systemCodeCache.register();
    return systemCodeCache;
  }

  @Provides
  public CmsSystemCodeSerializer provideCmsSystemCodeSerializer(SystemCodeCache systemCodeCache) {
    return new CmsSystemCodeSerializer(systemCodeCache);
  }
}
