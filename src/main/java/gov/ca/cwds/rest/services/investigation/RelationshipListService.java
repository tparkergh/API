package gov.ca.cwds.rest.services.investigation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;

import gov.ca.cwds.data.Dao;
import gov.ca.cwds.data.dao.investigation.RelationshipsDao;
import gov.ca.cwds.fixture.investigation.RelationshipListEntityBuilder;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.investigation.RelationshipList;
import gov.ca.cwds.rest.services.TypedCrudsService;
import io.dropwizard.jackson.Jackson;

/**
 * Business layer object to work on Investigation
 * 
 * @author CWDS API Team
 */
public class RelationshipListService
    implements TypedCrudsService<String, RelationshipList, Response> {

  private static final Logger LOGGER = LoggerFactory.getLogger(RelationshipListService.class);

  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

  private RelationshipsDao relationshipsDao;

  private RelationshipList validRelationshipList = new RelationshipListEntityBuilder().build();

  /**
   * @param relationshipsDao {@link Dao} handling
   *        {@link gov.ca.cwds.rest.api.domain.investigation.Relationship} objects
   */
  @Inject
  public RelationshipListService(RelationshipsDao relationshipsDao) {
    super();
    this.relationshipsDao = relationshipsDao;
  }

  @Override
  public Response find(String primaryKey) {
    return validRelationshipList;
  }

  @Override
  public RelationshipList delete(String primaryKey) {
    return validRelationshipList;
  }

  @Override
  public Response create(RelationshipList request) {
    return validRelationshipList;
  }

  @Override
  public Response update(String primaryKey, RelationshipList request) {
    return validRelationshipList;
  }

}
