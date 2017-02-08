package gov.ca.cwds.rest.services;

import java.io.Serializable;

import org.apache.commons.lang3.NotImplementedException;
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

/**
 * Business layer object to work on {@link Person} and
 * {@link gov.ca.cwds.data.persistence.ns.Person}
 * 
 * @author CWDS API Team
 */
public class PersonService implements CrudsService {

  private static final Logger LOGGER = LoggerFactory.getLogger(PersonService.class);
  private static final ObjectMapper MAPPER = new ObjectMapper();

  private static final String INDEX_PERSON = ElasticsearchDao.DEFAULT_PERSON_IDX_NM;
  private static final String DOCUMENT_TYPE_PERSON = ElasticsearchDao.DEFAULT_PERSON_DOC_TYPE;

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
    assert request instanceof Person;
    Person person = (Person) request;
    gov.ca.cwds.data.persistence.ns.Person managed =
        new gov.ca.cwds.data.persistence.ns.Person(person, null, null);

    managed = personDao.create(managed);
    PostedPerson postedPerson = new PostedPerson(managed);
    try {
      final gov.ca.cwds.rest.api.domain.es.Person esPerson =
          new gov.ca.cwds.rest.api.domain.es.Person(managed.getId().toString(),
              managed.getFirstName(), managed.getLastName(), managed.getSsn(), managed.getGender(),
              DomainChef.cookDate(managed.getDateOfBirth()), managed.getClass().getName(),
              MAPPER.writeValueAsString(managed));
      final String document = MAPPER.writeValueAsString(esPerson);

      // If the people index is missing, create it.
      elasticsearchDao.createIndexIfNeeded(INDEX_PERSON);

      // The ES Dao manages its own connections. No need to manually start or stop.
      elasticsearchDao.index(INDEX_PERSON, DOCUMENT_TYPE_PERSON, document, esPerson.getId());
    } catch (JsonProcessingException e) {
      throw new ApiException("Unable to convert Person to JSON to Index in ElasticSearch", e);
    } catch (Exception e) {
      LOGGER.error("Unable to Index Person in ElasticSearch", e);
      throw new ApiException("Unable to Index Person in ElasticSearch", e);
    }
    return postedPerson;
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
  public Response delete(final Serializable primaryKey) {
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
  public Response update(final Serializable primaryKey, Request request) {
    assert primaryKey instanceof Long;
    throw new NotImplementedException("Update is not implemented");
  }

}
