package gov.ca.cwds.rest.services.cms;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.service.spi.ServiceException;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.inject.Inject;

import gov.ca.cwds.data.cms.GovernmentOrganizationDao;
import gov.ca.cwds.data.cms.LawEnforcementDao;
import gov.ca.cwds.rest.api.domain.cms.GovernmentOrganization;
import gov.ca.cwds.rest.api.domain.cms.GovernmentOrganizationResponse;
import gov.ca.cwds.rest.api.domain.cms.SystemCodeCache;
import gov.ca.cwds.rest.resources.SimpleResourceService;

/**
 * Business layer object to work on {@link GovernmentOrganization}
 * 
 * @author CWDS API Team
 */
public class GovernmentOrganizationService
    extends SimpleResourceService<String, GovernmentOrganization, GovernmentOrganizationResponse> {

  private static final String LAWENFORCEMENT = "law_enforcement";
  private static final String COUNTY_LICENSING = "county_licensing";
  private static final String DISTRICT_ATTORNEY = "district_attorney";
  private static final String DEPARTMENT_OF_JUSTICE = "department_of_justice";
  private static final String COMMUNITY_CARE_LICENSING = "community_care_licensing";
  private static final String ALL_COUNTY_CACHE_KEY = "ALL_COUNTIES";

  private transient LoadingCache<String, GovernmentOrganizationResponse> governmentOrganizationResponseCache;

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

    GovernmentOrganizationCacheLoader cacheLoader =
        new GovernmentOrganizationCacheLoader(governmentOrganizationDao, lawEnforcementDao);
    governmentOrganizationResponseCache =
        CacheBuilder.newBuilder().refreshAfterWrite(15, TimeUnit.DAYS).build(cacheLoader);
  }

  @Override
  protected GovernmentOrganizationResponse handleRequest(GovernmentOrganization arg0) {
    return null;
  }

  @Override
  protected GovernmentOrganizationResponse handleFind(String countyId) {
    try {
      String key = ALL_COUNTY_CACHE_KEY;
      if (!StringUtils.isBlank(countyId)) {
        key = countyId;
      }
      return governmentOrganizationResponseCache.get(key);
    } catch (ExecutionException e) {
      throw new ServiceException(e.getMessage(), e);
    }
  }

  /**
   * =============================================================================== <br>
   * Cache loader for cross report agencies and law enforcement. <br>
   * ===============================================================================
   */
  private static class GovernmentOrganizationCacheLoader
      extends CacheLoader<String, GovernmentOrganizationResponse> {

    private GovernmentOrganizationDao governmentOrganizationDao;
    private LawEnforcementDao lawEnforcementDao;

    /**
     * Construct the object
     * 
     * @param systemCodeService
     */
    GovernmentOrganizationCacheLoader(GovernmentOrganizationDao governmentOrganizationDao,
        LawEnforcementDao lawEnforcementDao) {
      this.governmentOrganizationDao = governmentOrganizationDao;
      this.lawEnforcementDao = lawEnforcementDao;
    }

    @Override
    public GovernmentOrganizationResponse load(String key) throws Exception {

      GovernmentOrganizationResponse governmentOrganizationResponse =
          new GovernmentOrganizationResponse(governmentOrganizationDao.findAll().stream()
              .map(temp -> new GovernmentOrganization(temp.getId(),
                  temp.getGovernmentOrganizationName(),
                  SystemCodeCache.global()
                      .getSystemCodeShortDescription(temp.getGovernmentOrganizationType())
                      .replace(' ', '_'),
                  temp.getGovernmentEntityType()))
              .collect(Collectors.toList()));

      governmentOrganizationResponse.setGovernmentOrganizations(lawEnforcementDao.findAll().stream()
          .map(GovernmentOrganization::new).collect(Collectors.toList()));

      List<String> validCodes = Arrays.asList(new String[] {COMMUNITY_CARE_LICENSING,
          DEPARTMENT_OF_JUSTICE, DISTRICT_ATTORNEY, COUNTY_LICENSING, LAWENFORCEMENT});
      if (!ALL_COUNTY_CACHE_KEY.equals(key)) {
        fetchAgencyByCounty(key, governmentOrganizationResponse, validCodes);
      }
      return governmentOrganizationResponse;
    }

    private static void fetchAgencyByCounty(String countyId,
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
  }
}
