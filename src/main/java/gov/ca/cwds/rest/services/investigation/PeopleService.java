package gov.ca.cwds.rest.services.investigation;

import com.google.inject.Inject;

import gov.ca.cwds.data.Dao;
import gov.ca.cwds.data.dao.investigation.PeopleDao;
import gov.ca.cwds.fixture.investigation.PeopleEntityBuilder;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.investigation.People;
import gov.ca.cwds.rest.api.domain.investigation.Person;
import gov.ca.cwds.rest.services.TypedCrudsService;

/**
 * Business layer object to work on Investigation
 * 
 * @author CWDS API Team
 */
public class PeopleService implements TypedCrudsService<String, Person, Response> {

  private PeopleDao peopleDao;

  private People validPeople = new PeopleEntityBuilder().build();

  /**
   * @param peopleDao {@link Dao} handling {@link gov.ca.cwds.rest.api.domain.investigation.Person}
   *        objects
   */
  @Inject
  public PeopleService(PeopleDao peopleDao) {
    super();
    this.peopleDao = peopleDao;
  }

  @Override
  public Response find(String primaryKey) {
    return validPeople;
  }

  @Override
  public Response create(Person request) {
    return validPeople;
  }

  @Override
  public Response delete(String primaryKey) {
    return validPeople;
  }

  @Override
  public Response update(String primaryKey, Person request) {
    return validPeople;
  }
}
