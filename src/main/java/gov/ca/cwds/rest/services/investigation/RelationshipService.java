package gov.ca.cwds.rest.services.investigation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;

import gov.ca.cwds.data.Dao;
import gov.ca.cwds.data.dao.investigation.RelationshipsDao;
import gov.ca.cwds.fixture.investigation.RelationshipEntityBuilder;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.investigation.Relationship;
import gov.ca.cwds.rest.services.TypedCrudsService;
import io.dropwizard.jackson.Jackson;

/**
 * Business layer object to work on Investigation
 * 
 * @author CWDS API Team
 */
public class RelationshipService implements TypedCrudsService<String, Relationship, Response> {

  private static final Logger LOGGER = LoggerFactory.getLogger(RelationshipService.class);

  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

  private RelationshipsDao relationshipsDao;

  private Relationship validRelationship = new RelationshipEntityBuilder().build();

  /**
   * @param relationshipsDao {@link Dao} handling
   *        {@link gov.ca.cwds.rest.api.domain.investigation.Relationship} objects
   */
  @Inject
  public RelationshipService(RelationshipsDao relationshipsDao) {
    super();
    this.relationshipsDao = relationshipsDao;
  }

  @Override
  public Response find(String primaryKey) {
    return validRelationship;
  }

  @Override
  public Relationship delete(String primaryKey) {
    return validRelationship;
  }

  @Override
  public Response create(Relationship request) {
    return validRelationship;
  }

  @Override
  public Response update(String primaryKey, Relationship request) {
    return validRelationship;
  }

}
