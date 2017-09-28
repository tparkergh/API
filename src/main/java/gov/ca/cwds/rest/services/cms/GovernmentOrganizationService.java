package gov.ca.cwds.rest.services.cms;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import com.google.inject.Inject;

import gov.ca.cwds.data.cms.GovernmentOrganizationDao;
import gov.ca.cwds.data.cms.LawEnforcementDao;
import gov.ca.cwds.data.cms.SystemCodeDao;
import gov.ca.cwds.data.persistence.cms.SystemCode;
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

  private static final String GOVERNMENT_ORGANIZATION = "GVR_ORGC";
  private static final String LAWENFORCEMENT = "law_enforcement";
  private static final String COUNTY_LICENSING = "county_licensing";
  private static final String DISTRICT_ATTORNEY = "district_attorney";
  private static final String DEPARTMENT_OF_JUSTICE = "department_of_justice";
  private static final String COMMUNITY_CARE_LICENSING = "community_care_licensing";

  private GovernmentOrganizationDao governmentOrganizationDao;
  private LawEnforcementDao lawEnforcementDao;
  private Map<Short, String> systemCodeMap;
  private SystemCodeDao systemCodeDao;

  /**
   * Constructor
   * 
   * @param governmentOrganizationDao - governmentOrganizationDao
   * @param lawEnforcementDao - lawEnforcementDao
   * @param systemCodeDao - systemCodeDao
   */
  @Inject
  public GovernmentOrganizationService(GovernmentOrganizationDao governmentOrganizationDao,
      LawEnforcementDao lawEnforcementDao, SystemCodeDao systemCodeDao) {
    super();
    this.governmentOrganizationDao = governmentOrganizationDao;
    this.lawEnforcementDao = lawEnforcementDao;
    this.systemCodeDao = systemCodeDao;
  }

  @Override
  protected GovernmentOrganizationResponse handleRequest(GovernmentOrganization arg0) {
    return null;
  }

  @Override
  protected GovernmentOrganizationResponse handleFind(String countyId) {
    systemCodeMap = getSystemCodeMap();

    GovernmentOrganizationResponse governmentOrganizationResponse =
        new GovernmentOrganizationResponse(
            governmentOrganizationDao.findAll().stream().map(temp -> {
              return new GovernmentOrganization(temp.getId(), temp.getGovernmentOrganizationName(),
                  systemCodeMap.get(temp.getGovernmentOrganizationType()).replace(" ", "_"),
                  temp.getGovernmentEntityType());

            }).collect(Collectors.toList()));

    governmentOrganizationResponse.setGovernmentOrganizations(lawEnforcementDao.findAll().stream()
        .map(GovernmentOrganization::new).collect(Collectors.toList()));

    List<String> validCodes = Arrays.asList(new String[] {COMMUNITY_CARE_LICENSING,
        DEPARTMENT_OF_JUSTICE, DISTRICT_ATTORNEY, COUNTY_LICENSING, LAWENFORCEMENT});
    if (StringUtils.isNotBlank(countyId)) {

      fetchAgencyByCounty(countyId, governmentOrganizationResponse, validCodes);
    }
    return governmentOrganizationResponse;
  }

  private void fetchAgencyByCounty(String countyId,
      GovernmentOrganizationResponse governmentOrganizationResponse, List<String> validCodes) {
    Short id = Short.valueOf(countyId);
    for (Iterator<GovernmentOrganization> iterator =
        governmentOrganizationResponse.getGovernmentOrganizations().iterator(); iterator
            .hasNext();) {
      GovernmentOrganization govOrg = iterator.next();
      if (!(id.equals(govOrg.getGovernmentEntityType())
          && validCodes.contains(govOrg.getAgencyType()))) {
        iterator.remove();
      }
    }
  }

  private Map<Short, String> getSystemCodeMap() {
    if (systemCodeMap == null) {
      systemCodeMap = new HashMap<>();
      SystemCode[] systemCodes = systemCodeDao.findByForeignKeyMetaTable(GOVERNMENT_ORGANIZATION);
      for (SystemCode code : systemCodes) {
        systemCodeMap.put(code.getSystemId(), code.getShortDescription());
      }
    }
    return systemCodeMap;
  }

}
