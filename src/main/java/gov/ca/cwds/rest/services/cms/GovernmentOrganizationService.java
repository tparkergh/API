package gov.ca.cwds.rest.services.cms;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Lists;
import com.google.inject.Inject;

import gov.ca.cwds.data.cms.GovernmentOrganizationDao;
import gov.ca.cwds.data.cms.LawEnforcementDao;
import gov.ca.cwds.data.std.ApiMarker;
import gov.ca.cwds.rest.api.domain.cms.AgencyType;
import gov.ca.cwds.rest.api.domain.cms.GovernmentOrganization;
import gov.ca.cwds.rest.api.domain.cms.GovernmentOrganizationResponse;
import gov.ca.cwds.rest.resources.SimpleResourceService;

/**
 * Business layer object to work on {@link GovernmentOrganization}
 * 
 * @author CWDS API Team
 */
// @Singleton
// @Named("govt_org_svc")
public class GovernmentOrganizationService
    extends SimpleResourceService<String, GovernmentOrganization, GovernmentOrganizationResponse>
    implements ApiMarker {

  /**
   * Default serialization.
   */
  private static final long serialVersionUID = 1L;

  private static final String ALL_COUNTY_CACHE_KEY = "ALL_COUNTIES";

  private static final Logger LOGGER = LoggerFactory.getLogger(GovernmentOrganizationService.class);

  private static final AtomicInteger factoryCounter = new AtomicInteger(0);

  private final int instanceCounter;

  private transient LoadingCache<String, GovernmentOrganizationResponse> governmentOrganizationResponseCache;

  /**
   * Constructor
   * 
   * @param governmentOrganizationDao - governmentOrganizationDao
   * @param lawEnforcementDao - lawEnforcementDao
   */
  @Inject
  public GovernmentOrganizationService(
      // @Named("something") String test,
      GovernmentOrganizationDao governmentOrganizationDao, LawEnforcementDao lawEnforcementDao) {
    super();
    GovernmentOrganizationCacheLoader cacheLoader =
        new GovernmentOrganizationCacheLoader(governmentOrganizationDao, lawEnforcementDao);
    governmentOrganizationResponseCache =
        CacheBuilder.newBuilder().refreshAfterWrite(15, TimeUnit.DAYS).build(cacheLoader);

    this.instanceCounter = factoryCounter.incrementAndGet();
    LOGGER.info("Construct instance # {}", this.instanceCounter);
  }

  @Override
  protected GovernmentOrganizationResponse handleRequest(GovernmentOrganization arg0) {
    return null;
  }

  @Override
  protected GovernmentOrganizationResponse handleFind(String countyId) {
    String key = StringUtils.isBlank(countyId) ? ALL_COUNTY_CACHE_KEY : countyId;
    GovernmentOrganizationResponse governmentOrganizationResponse = null;
    try {
      governmentOrganizationResponse = governmentOrganizationResponseCache.get(key);
    } catch (ExecutionException e) {
      throw new ServiceException("Unable to load government organization: " + key, e);
    }
    return governmentOrganizationResponse;
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
     * @param governmentOrganizationService
     */
    GovernmentOrganizationCacheLoader(GovernmentOrganizationDao governmentOrganizationDao,
        LawEnforcementDao lawEnforcementDao) {
      this.governmentOrganizationDao = governmentOrganizationDao;
      this.lawEnforcementDao = lawEnforcementDao;
    }

    @Override
    public GovernmentOrganizationResponse load(String key) throws Exception {
      List<GovernmentOrganization> allAgencies = new ArrayList<>();

      allAgencies.addAll(governmentOrganizationDao.findAll().stream()
          .map(GovernmentOrganization::new).collect(Collectors.toList()));

      allAgencies.addAll(lawEnforcementDao.findAll().stream().map(GovernmentOrganization::new)
          .collect(Collectors.toList()));

      List<GovernmentOrganization> responseAgencies;
      if (ALL_COUNTY_CACHE_KEY.equals(key)) {
        responseAgencies = allAgencies;
      } else {
        responseAgencies = getSupportedAgenciesForCounty(key, allAgencies);
      }

      return new GovernmentOrganizationResponse(responseAgencies);
    }

    private static List<GovernmentOrganization> getSupportedAgenciesForCounty(String countyId,
        List<GovernmentOrganization> allAgencies) {

      List<AgencyType> supportedAgencyTypes =
          Lists.newArrayList(AgencyType.COUNTY_LICENSING, AgencyType.DISTRICT_ATTORNEY,
              AgencyType.DEPARTMENT_OF_JUSTICE, AgencyType.LAW_ENFORCEMENT);

      Short id = Short.valueOf(countyId);
      List<GovernmentOrganization> supportedAgencies = new ArrayList<>();

      for (GovernmentOrganization agency : allAgencies) {
        AgencyType agencyType = AgencyType.getByName(agency.getAgencyType());
        if ((agency.getCountyId().equals(id) && supportedAgencyTypes.contains(agencyType))
            || agencyType == AgencyType.COMMUNITY_CARE_LICENSING) {
          supportedAgencies.add(agency);
        }
      }

      return supportedAgencies;
    }
  }

  /**
   * @return the instance
   */
  public int getInstanceCounter() {
    return instanceCounter;
  }

}
