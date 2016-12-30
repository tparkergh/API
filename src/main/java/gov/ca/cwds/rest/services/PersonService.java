package gov.ca.cwds.rest.services;

import java.io.Serializable;

import org.apache.commons.lang3.NotImplementedException;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.search.SearchHit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;

import gov.ca.cwds.data.Dao;
import gov.ca.cwds.data.es.ElasticsearchDao;
import gov.ca.cwds.data.ns.PersonDao;
import gov.ca.cwds.rest.api.ApiException;
import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.DomainChef;
import gov.ca.cwds.rest.api.domain.Person;
import gov.ca.cwds.rest.api.domain.PostedPerson;
import gov.ca.cwds.rest.api.domain.es.ESPerson;
import gov.ca.cwds.rest.api.domain.es.ESSearchRequest;

/**
 * Business layer object to work on {@link Person} and
 * {@link gov.ca.cwds.data.persistence.ns.Person}
 * 
 * @author CWDS API Team
 */
public class PersonService implements CrudsService {

  private static final Logger LOGGER = LoggerFactory.getLogger(PersonService.class);
  private static final ObjectMapper MAPPER = new ObjectMapper();

  private PersonDao personDao;
  private ElasticsearchDao elasticsearchDao;

  /**
   * Constructor
   * 
   * @param personDao The {@link Dao} handling {@link gov.ca.cwds.data.persistence.ns.Person}
   *        objects.
   * @param elasticsearchDao the ElasticSearch DAO
   */
  @Inject
  public PersonService(PersonDao personDao, ElasticsearchDao elasticsearchDao) {
    this.personDao = personDao;
    this.elasticsearchDao = elasticsearchDao;
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#find(java.io.Serializable)
   */
  @Override
  public Person find(Serializable primaryKey) {
    assert primaryKey instanceof Long;
    gov.ca.cwds.data.persistence.ns.Person persistedPerson = personDao.find(primaryKey);
    if (persistedPerson != null) {
      return new Person(persistedPerson);
    }
    return null;
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#create(gov.ca.cwds.rest.api.Request)
   */
  @Override
  public PostedPerson create(Request request) {
    assert (request instanceof Person);
    Person person = (Person) request;
    gov.ca.cwds.data.persistence.ns.Person managed =
        new gov.ca.cwds.data.persistence.ns.Person(person, null);

    managed = personDao.create(managed);
    PostedPerson postedPerson = new PostedPerson(managed);
    try {
      gov.ca.cwds.rest.api.domain.es.Person esPerson = new gov.ca.cwds.rest.api.domain.es.Person(
          managed.getId().toString(), managed.getFirstName(), managed.getLastName(),
          managed.getSsn(), managed.getGender(), DomainChef.cookDate(managed.getDateOfBirth()),
          managed.getClass().getName(), MAPPER.writeValueAsString(managed));
      String document = MAPPER.writeValueAsString(esPerson);

      // The ES Dao manages its own connections. No need to manually start or stop.
      elasticsearchDao.index(document, esPerson.getId().toString());
    } catch (JsonProcessingException e) {
      throw new ApiException("Unable to convert Person to JSON to Index in ElasticSearch", e);
    } catch (Exception e) {
      LOGGER.error("Unable to Index Person in ElasticSearch", e);
      throw new ApiException("Unable to Index Person in ElasticSearch", e);
    }
    return postedPerson;
  }

  /**
   * Returns all persons in ElasticSearch, up the default number of rows set in
   * {@link ElasticsearchDao#fetchAllPerson()}.
   * 
   * @return array of {@link ESPerson}
   * @throws Exception due to I/O error, unknown host, etc.
   */
  public ESPerson[] fetchAllPersons() throws Exception {
    final SearchHit[] hits = this.elasticsearchDao.fetchAllPerson();

    final ESPerson[] persons = new ESPerson[hits.length];
    int counter = -1;
    for (SearchHit hit : hits) {
      persons[++counter] = ESPerson.makeESPerson(hit);
    }

    return persons;
  }

  /**
   * Search on the first non-null, non-whitespace term.
   * 
   * @param firstName first name to search, if any
   * @param lastName last name to search, if any
   * @param birthDate birth date to search, if any
   * @return array of matching {@link ESPerson}
   * @throws Exception due to I/O error, unknown host, etc.
   */
  public ESPerson[] queryPersonOr(String firstName, String lastName, String birthDate)
      throws Exception {

    // Parse inputs.
    ESSearchRequest req = new ESSearchRequest();
    String field = "";
    String value = "";
    if (!StringUtils.isBlank(firstName)) {
      field = ESPerson.ESColumn.FIRST_NAME.getCol();
      value = firstName;
    } else if (!StringUtils.isBlank(lastName)) {
      field = ESPerson.ESColumn.LAST_NAME.getCol();
      value = lastName;
    } else if (!StringUtils.isBlank(birthDate)) {
      field = ESPerson.ESColumn.BIRTH_DATE.getCol();
      value = birthDate;
    }

    // Build search request.
    req.getRoot().addElem(new ESSearchRequest.ESFieldSearchEntry(field, value));

    // Search.
    final SearchHit[] hits = this.elasticsearchDao.queryPersonOr(req);
    final ESPerson[] ret = new ESPerson[hits.length];

    // Prep results.
    int ctr = -1;
    for (SearchHit hit : hits) {
      ret[++ctr] = ESPerson.makeESPerson(hit);
    }

    return ret;
  }

  // ===================
  // NOT IMPLEMENTED:
  // ===================

  /**
   * <strong>NOT IMPLEMENTED! REQUIRED BY {@link CrudsService}!</strong> {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#delete(java.io.Serializable)
   */
  @Override
  public Response delete(Serializable primaryKey) {
    assert primaryKey instanceof Long;
    throw new NotImplementedException("Delete is not implemented");
  }

  /**
   * <strong>NOT IMPLEMENTED! REQUIRED BY {@link CrudsService}!</strong> {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#update(java.io.Serializable,
   *      gov.ca.cwds.rest.api.Request)
   */
  @Override
  public Response update(Serializable primaryKey, Request request) {
    assert primaryKey instanceof Long;
    throw new NotImplementedException("Update is not implemented");
  }

}
