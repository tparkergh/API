package gov.ca.cwds.inject;

import com.google.inject.Inject;

import gov.ca.cwds.data.cms.GovernmentOrganizationDao;
import gov.ca.cwds.data.cms.LawEnforcementDao;
import gov.ca.cwds.rest.services.cms.GovernmentOrganizationService;

public class GovtOrgSvcProvider extends TypedSingletonProvider<GovernmentOrganizationService> {

  private final GovernmentOrganizationDao govtOrgDao;
  private final LawEnforcementDao lawEnforceDao;

  @Inject
  public GovtOrgSvcProvider(final GovernmentOrganizationDao govtOrgDao,
      final LawEnforcementDao lawEnforceDao) {
    super();
    this.govtOrgDao = govtOrgDao;
    this.lawEnforceDao = lawEnforceDao;
    init();
  }

  @Override
  protected GovernmentOrganizationService createInstance() {
    return new GovernmentOrganizationService(govtOrgDao, lawEnforceDao);
  }

  @Override
  protected Class<GovernmentOrganizationService> getClassType() {
    return GovernmentOrganizationService.class;
  }

}
