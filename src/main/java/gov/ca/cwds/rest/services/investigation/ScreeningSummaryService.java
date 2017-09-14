package gov.ca.cwds.rest.services.investigation;

import gov.ca.cwds.data.Dao;
import gov.ca.cwds.data.dao.contact.DeliveredServiceDao;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.investigation.ScreeningSummary;
import gov.ca.cwds.rest.api.domain.investigation.SimpleAllegation;
import gov.ca.cwds.rest.filters.RequestExecutionContext;
import gov.ca.cwds.rest.services.TypedCrudsService;
import io.dropwizard.jackson.Jackson;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.NotImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;

/**
 * Business layer object to work on Screening Summary
 * 
 * @author CWDS API Team
 */
public class ScreeningSummaryService implements
    TypedCrudsService<String, ScreeningSummary, Response> {

  private static final Logger LOGGER = LoggerFactory.getLogger(ScreeningSummaryService.class);

  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

  private String lastUpdatedId = RequestExecutionContext.instance().getUserId();
  private Date lastUpdatedTime = RequestExecutionContext.instance().getRequestStartTime();
  private DeliveredServiceDao deliveredServiceDao;

  private String perpetratorId = "11";
  private Set<String> allegationTypes = validAllegationTypes();
  private String victimId = "22";

  private String additionalInformation = "There was excessive evidence of abuse";
  private String decision = "promote_to_referral";
  private String decisionDetail = "immediate";
  private String name = "Henderson Screening";
  private Set<String> safetyAlerts = validSafetyAletrs();
  private String startedAt = "2017-09-01T16:48:05.457Z";
  private String safetyInformation = "The animal at residence is a lion";
  private String id = "1";
  private Set<SimpleAllegation> allegations = validAllegationsSet();

  private Set<String> validSafetyAletrs() {
    Set<String> safetyAlerts = new HashSet<String>();
    safetyAlerts.add("Dangerous Animal on Premises");
    safetyAlerts.add("Firearms in Home");
    safetyAlerts.add("Severe Mental Health Status");
    return safetyAlerts;
  }

  private Set<SimpleAllegation> validAllegationsSet() {
    Set<SimpleAllegation> validAllegations = new HashSet<SimpleAllegation>();
    SimpleAllegation allegation = new SimpleAllegation(victimId, perpetratorId, allegationTypes);
    validAllegations.add(allegation);
    return validAllegations;
  }

  private Set<String> validAllegationTypes() {
    Set<String> allegationTypes = new HashSet<String>();
    allegationTypes.add("General neglect");
    allegationTypes.add("Physical abuse");
    return allegationTypes;
  }


  /**
   * @param deliveredServiceDao {@link Dao} handling
   *        {@link gov.ca.cwds.data.persistence.contact.DeliveredServiceEntity} objects
   */
  @Inject
  public ScreeningSummaryService(DeliveredServiceDao deliveredServiceDao) {
    super();
    this.deliveredServiceDao = deliveredServiceDao;
  }


  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#create(gov.ca.cwds.rest.api.Request)
   */

  @Override
  public Response find(String primaryKey) {

    ScreeningSummary serialized =
        new ScreeningSummary(id, name, decision, decisionDetail, safetyAlerts, safetyInformation,
            additionalInformation, startedAt, allegations);
    return serialized;
  }


  @Override
  public Response create(ScreeningSummary request) {
    throw new NotImplementedException("create not implemented");
  }

  @Override
  public Response delete(String primaryKey) {
    throw new NotImplementedException("delete not implemented");
  }

  @Override
  public Response update(String primaryKey, ScreeningSummary request) {
    throw new NotImplementedException("update not implemented");
  }

}
