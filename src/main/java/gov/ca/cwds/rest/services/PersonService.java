package gov.ca.cwds.rest.services;

import java.io.Serializable;
import java.util.Map;

import org.apache.commons.lang3.NotImplementedException;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.search.SearchHit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;

import gov.ca.cwds.rest.api.ApiException;
import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.DomainObject;
import gov.ca.cwds.rest.api.domain.Person;
import gov.ca.cwds.rest.api.domain.PostedPerson;
import gov.ca.cwds.rest.api.domain.es.ESSearchRequest;
import gov.ca.cwds.rest.elasticsearch.db.ElasticsearchDao;
import gov.ca.cwds.rest.jdbi.Dao;
import gov.ca.cwds.rest.jdbi.ns.PersonDao;

/**
 * Business layer object to work on {@link Person} and
 * {@link gov.ca.cwds.rest.api.persistence.ns.Person}
 * 
 * @author CWDS API Team
 */
public class PersonService implements CrudsService {
  private PersonDao personDao;
  private ElasticsearchDao elasticsearchDao;

  private static final Logger LOGGER = LoggerFactory.getLogger(PersonService.class);
  private static final ObjectMapper MAPPER = new ObjectMapper();

  /**
   * Constructor
   * 
   * @param personDao The {@link Dao} handling {@link gov.ca.cwds.rest.api.persistence.ns.Person}
   *        objects.
   * @param elasticsearchDao the Elasticsearch DAO
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
    assert (primaryKey instanceof Long);

    gov.ca.cwds.rest.api.persistence.ns.Person persistedPerson = personDao.find(primaryKey);
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

    Person person = ((Person) request);
    gov.ca.cwds.rest.api.persistence.ns.Person managed =
        new gov.ca.cwds.rest.api.persistence.ns.Person(person, null);

    managed = personDao.create(managed);
    PostedPerson postedPerson = new PostedPerson(managed);
    try {
      gov.ca.cwds.rest.api.elasticsearch.ns.Person esPerson =
          new gov.ca.cwds.rest.api.elasticsearch.ns.Person(managed.getId().toString(),
              managed.getFirstName(), managed.getLastName(), managed.getSsn(), managed.getGender(),
              DomainObject.cookDate(managed.getDateOfBirth()), managed.getClass().getName(),
              MAPPER.writeValueAsString(managed));
      String document = "";

      if (esPerson != null) {
        document = MAPPER.writeValueAsString(esPerson);
      }

      // Methods start/stop are now protected. Dao manages its own connections.
      elasticsearchDao.index(document, esPerson.getId().toString());
    } catch (JsonProcessingException e) {
      throw new ApiException("Unable to convert Person to json to Index in Elasticsearch", e);
    } catch (Exception e) {
      throw new ApiException("Unable to Index Person in Elasticsearch", e);
    }
    return postedPerson;
  }

  /**
   * Returns all persons in Elasticsearch, up the default number of rows set in
   * {@link ElasticsearchDao#fetchAllPerson()}.
   * 
   * @return array of {@link PostedPerson}
   * @throws Exception I/O error or unknown host
   */
  public PostedPerson[] fetchAllPersons() throws Exception {
    final SearchHit[] hits = this.elasticsearchDao.fetchAllPerson();

    final PostedPerson[] persons = new PostedPerson[hits.length];
    int counter = -1;
    for (SearchHit hit : hits) {
      final Map<String, Object> m = hit.getSource();
      LOGGER.debug(m.toString());

      persons[++counter] = new PostedPerson(Long.parseLong(m.getOrDefault("id", "0").toString()),
          (String) m.getOrDefault("first_name", ""), (String) m.getOrDefault("last_name", ""),
          (String) m.getOrDefault("gender", null), (String) m.getOrDefault("birth_date", null),
          (String) m.getOrDefault("ssn", null), null);
    }

    return persons;
  }

  /**
   * Search on the first non-null, non-whitespace term.
   * 
   * @param firstName first name to search, if any
   * @param lastName last name to search, if any
   * @param birthDate birth date to search, if any
   * @return array of found Persons
   * @throws Exception I/O error or unknown host
   */
  public PostedPerson[] queryPersonOr(String firstName, String lastName, String birthDate)
      throws Exception {

    ESSearchRequest req = new ESSearchRequest();
    String field = "";
    String value = "";
    if (!StringUtils.isBlank(firstName)) {
      field = "first_name";
      value = firstName;
    } else if (!StringUtils.isBlank(lastName)) {
      field = "last_name";
      value = lastName;
    } else if (!StringUtils.isBlank(birthDate)) {
      field = "birth_date";
      value = birthDate;
    }

    req.getRoot().addElem(new ESSearchRequest.ESFieldSearchEntry(field, value));
    final SearchHit[] hits = this.elasticsearchDao.queryPersonOr(req);

    final PostedPerson[] persons = new PostedPerson[hits.length];
    int counter = -1;
    for (SearchHit hit : hits) {
      final Map<String, Object> m = hit.getSource();
      // LOGGER.debug(m.toString());

      persons[++counter] = new PostedPerson(Long.parseLong(m.getOrDefault("id", "0").toString()),
          (String) m.getOrDefault("first_name", ""), (String) m.getOrDefault("last_name", ""),
          (String) m.getOrDefault("gender", null), (String) m.getOrDefault("birth_date", null),
          (String) m.getOrDefault("ssn", null), null);
    }

    return persons;
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
    assert (primaryKey instanceof Long);
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
    assert (primaryKey instanceof Long);
    throw new NotImplementedException("Update is not implemented");
  }

}
