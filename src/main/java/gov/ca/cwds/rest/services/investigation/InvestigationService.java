package gov.ca.cwds.rest.services.investigation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;

import gov.ca.cwds.data.Dao;
import gov.ca.cwds.data.dao.investigation.InvestigationDao;
import gov.ca.cwds.fixture.investigation.InvestigationEntityBuilder;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.investigation.Investigation;
import gov.ca.cwds.rest.services.TypedCrudsService;
import io.dropwizard.jackson.Jackson;

/**
 * Business layer object to work on Investigation
 * 
 * @author CWDS API Team
 */
public class InvestigationService implements TypedCrudsService<String, Investigation, Response> {

  private static final Logger LOGGER = LoggerFactory.getLogger(InvestigationService.class);

  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

  private InvestigationDao investigationDao;

  private Investigation validInvestigation = new InvestigationEntityBuilder().build();

  /**
   * @param InvestigationDao {@link Dao} handling
   *        {@link gov.ca.cwds.rest.api.domain.investigation.Investigation} objects
   */
  @Inject
  public InvestigationService(InvestigationDao InvestigationDao) {
    super();
    this.investigationDao = InvestigationDao;
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#create(gov.ca.cwds.rest.api.Request)
   */

  @Override
  public Response find(String primaryKey) {
    return validInvestigation;
  }

  @Override
  public Investigation delete(String primaryKey) {
    return validInvestigation;
  }

  @Override
  public Response create(Investigation request) {
    return validInvestigation;
  }

  @Override
  public Response update(String primaryKey, Investigation request) {
    return validInvestigation;
  }

}
