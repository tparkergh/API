package gov.ca.cwds.rest.services.investigation;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import gov.ca.cwds.data.Dao;
import gov.ca.cwds.data.dao.contact.DeliveredServiceDao;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.investigation.ScreeningSummary;
import gov.ca.cwds.rest.filters.RequestExecutionContext;
import gov.ca.cwds.rest.services.TypedCrudsService;
import io.dropwizard.jackson.Jackson;

import java.util.Date;

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
    ScreeningSummary serialized = new ScreeningSummary();
    try {
      serialized =
          MAPPER.readValue(fixture("fixtures/domain/investigation/screening/valid/valid.json"),
              ScreeningSummary.class);
    } catch (Exception e) {
      LOGGER.error("Exception In HistoryOfInvolvement " + e.getMessage());
    }
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
