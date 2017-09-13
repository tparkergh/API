package gov.ca.cwds.rest.services;

import java.util.Date;

import com.google.inject.Inject;

import gov.ca.cwds.rest.api.domain.RaceAndEthnicity;
import gov.ca.cwds.rest.filters.RequestExecutionContext;
import gov.ca.cwds.rest.services.cms.ClientScpEthnicityService;

/**
 * @author CWS-NS2
 *
 */
public class RaceAndEthnicityService {

  private String lastUpdatedId = RequestExecutionContext.instance().getStaffId();
  private Date lastUpdatedTime = RequestExecutionContext.instance().getRequestStartTime();

  private ClientScpEthnicityService clientScpEthnicityService;

  /**
   * @param clientScpEthnicityService
   */
  @Inject
  public RaceAndEthnicityService(ClientScpEthnicityService clientScpEthnicityService) {
    super();
    this.clientScpEthnicityService = clientScpEthnicityService;
  }

  /**
   * @param raceAndEthnicity The raceAndEthnicity
   * @return the race codes
   */
  public static Short getRaceCode(RaceAndEthnicity raceAndEthnicity) {
    Short raceCode = null;
    if (!raceAndEthnicity.getRaceCode().isEmpty()) {
      raceCode = raceAndEthnicity.getRaceCode().iterator().next();
    } else if (!raceAndEthnicity.getHispanicCode().isEmpty()) {
      raceCode = raceAndEthnicity.getHispanicCode().iterator().next();
    }
    return raceCode;
  }

}
