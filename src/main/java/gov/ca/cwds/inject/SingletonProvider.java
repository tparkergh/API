package gov.ca.cwds.inject;

import com.google.inject.Inject;
import com.google.inject.Provider;

import gov.ca.cwds.data.cms.GovernmentOrganizationDao;
import gov.ca.cwds.data.cms.LawEnforcementDao;
import gov.ca.cwds.data.persistence.cms.DeferredRegistry;
import gov.ca.cwds.rest.services.cms.GovernmentOrganizationService;

public class SingletonProvider implements Provider<GovernmentOrganizationService> {

  private static DeferredRegistry<GovernmentOrganizationService> registry;

  @Inject
  public SingletonProvider(GovernmentOrganizationDao governmentOrganizationDao,
      LawEnforcementDao lawEnforcementDao) {

    if (registry == null) {
      registry = new DeferredRegistry(GovernmentOrganizationService.class,
          new GovernmentOrganizationService(governmentOrganizationDao, lawEnforcementDao), false);
    }
  }

  @Override
  public GovernmentOrganizationService get() {
    return DeferredRegistry
        .<GovernmentOrganizationService>unwrap(GovernmentOrganizationService.class);
  }

}
