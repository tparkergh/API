package gov.ca.cwds.rest.services.cms;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import com.google.inject.Inject;

import gov.ca.cwds.data.cms.GovernmentOrganizationDao;
import gov.ca.cwds.data.cms.LawEnforcementDao;
import gov.ca.cwds.rest.api.domain.cms.GovernmentOrganization;
import gov.ca.cwds.rest.api.domain.cms.GovernmentOrganizationResponse;
import gov.ca.cwds.rest.resources.SimpleResourceService;

/**
 * Business layer object to work on {@link GovernmentOrganization}
 * 
 * @author CWDS API Team
 */
public class GovernmentOrganizationService
    extends SimpleResourceService<String, GovernmentOrganization, GovernmentOrganizationResponse> {

  private GovernmentOrganizationDao governmentOrganizationDao;
  private LawEnforcementDao lawEnforcementDao;

  /**
   * Constructor
   * 
   * @param governmentOrganizationDao - governmentOrganizationDao
   * @param lawEnforcementDao - lawEnforcementDao
   */
  @Inject
  public GovernmentOrganizationService(GovernmentOrganizationDao governmentOrganizationDao,
      LawEnforcementDao lawEnforcementDao) {
    super();
    this.governmentOrganizationDao = governmentOrganizationDao;
    this.lawEnforcementDao = lawEnforcementDao;
  }

  @Override
  protected GovernmentOrganizationResponse handleRequest(GovernmentOrganization arg0) {
    return null;
  }

  @Override
  protected GovernmentOrganizationResponse handleFind(String countyId) {

    GovernmentOrganizationResponse governmentOrganizationResponse =
        new GovernmentOrganizationResponse(governmentOrganizationDao.findAll().stream()
            .map(GovernmentOrganization::new).collect(Collectors.toList()));

    governmentOrganizationResponse.setGovernmentOrganizations(lawEnforcementDao.getAllEnforcement()
        .stream().map(GovernmentOrganization::new).collect(Collectors.toList()));

    List<String> validCodes = Arrays.asList(new String[] {"1128", "1131", "1133", "2524"});
    if (StringUtils.isNotBlank(countyId)) {
      Short id = Short.valueOf(countyId);
      for (Iterator<GovernmentOrganization> iterator =
          governmentOrganizationResponse.getGovernmentOrganizations().iterator(); iterator
              .hasNext();) {
        GovernmentOrganization govOrgIterator = iterator.next();
        if (!(id.equals(govOrgIterator.getGovernmentEntityType())
            && validCodes.contains(govOrgIterator.getAgencyType()))) {
          iterator.remove();
        }
      }
    }

    return governmentOrganizationResponse;
  }

}
