package gov.ca.cwds.rest.services.investigation;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import gov.ca.cwds.data.Dao;
import gov.ca.cwds.data.dao.contact.DeliveredServiceDao;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.investigation.HistoryOfInvolvement;
import gov.ca.cwds.rest.services.TypedCrudsService;
import io.dropwizard.jackson.Jackson;

import org.apache.commons.lang3.NotImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;

/**
 * Business layer object to work on History Of Involvement
 * 
 * @author CWDS API Team
 */
public class HistoryOfInvolvementService implements
    TypedCrudsService<String, HistoryOfInvolvement, Response> {

  private static final Logger LOGGER = LoggerFactory.getLogger(HistoryOfInvolvementService.class);

  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

  private DeliveredServiceDao deliveredServiceDao;

  /**
   * @param deliveredServiceDao {@link Dao} handling
   *        {@link gov.ca.cwds.data.persistence.contact.DeliveredServiceEntity} objects
   */
  @Inject
  public HistoryOfInvolvementService(DeliveredServiceDao deliveredServiceDao) {
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
    HistoryOfInvolvement serialized = new HistoryOfInvolvement();
    try {
      serialized =
          MAPPER.readValue(
              fixture("fixtures/domain/investigation/historyOfInvolvement/valid/valid.json"),
              HistoryOfInvolvement.class);
    } catch (Exception e) {

      LOGGER.error("Exception In HistoryOfInvolvement " + e.getMessage());

    }
    return serialized;
  }

  @Override
  public Response create(HistoryOfInvolvement request) {
    throw new NotImplementedException("create not implemented");
  }

  @Override
  public Response delete(String primaryKey) {
    throw new NotImplementedException("delete not implemented");
  }


  @Override
  public Response update(String primaryKey, HistoryOfInvolvement request) {
    throw new NotImplementedException("update not implemented");
  }

}
