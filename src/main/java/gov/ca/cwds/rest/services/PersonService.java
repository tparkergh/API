package gov.ca.cwds.rest.services;

import java.io.Serializable;

import org.apache.commons.lang3.NotImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;

import gov.ca.cwds.data.Dao;
import gov.ca.cwds.data.es.ElasticsearchDao;
import gov.ca.cwds.data.ns.AddressDao;
import gov.ca.cwds.data.ns.EthnicityDao;
import gov.ca.cwds.data.ns.LanguageDao;
import gov.ca.cwds.data.ns.PersonAddressDao;
import gov.ca.cwds.data.ns.PersonDao;
import gov.ca.cwds.data.ns.PersonEthnicityDao;
import gov.ca.cwds.data.ns.PersonLanguageDao;
import gov.ca.cwds.data.ns.PersonPhoneDao;
import gov.ca.cwds.data.ns.PersonRaceDao;
import gov.ca.cwds.data.ns.PhoneNumberDao;
import gov.ca.cwds.data.ns.RaceDao;
import gov.ca.cwds.data.persistence.ns.PersonAddress;
import gov.ca.cwds.data.persistence.ns.PersonEthnicity;
import gov.ca.cwds.data.persistence.ns.PersonLanguage;
import gov.ca.cwds.data.persistence.ns.PersonPhone;
import gov.ca.cwds.data.persistence.ns.PersonRace;
import gov.ca.cwds.rest.api.ApiException;
import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.Address;
import gov.ca.cwds.rest.api.domain.Ethnicity;
import gov.ca.cwds.rest.api.domain.Language;
import gov.ca.cwds.rest.api.domain.Person;
import gov.ca.cwds.rest.api.domain.PhoneNumber;
import gov.ca.cwds.rest.api.domain.PostedPerson;
import gov.ca.cwds.rest.api.domain.Race;

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
  private PersonAddressDao personAddressDao;
  private AddressDao addressDao;
  private PhoneNumberDao phoneNumberDao;
  private PersonPhoneDao personPhoneDao;
  private LanguageDao languageDao;
  private PersonLanguageDao personLanguageDao;
  private RaceDao raceDao;
  private PersonRaceDao personRaceDao;
  private EthnicityDao ethnicityDao;
  private PersonEthnicityDao personEthnicityDao;

  /**
   * Constructor
   * 
   * @param personDao The {@link Dao} handling {@link gov.ca.cwds.data.persistence.ns.Person}
   *        objects.
   * @param elasticsearchDao The ElasticSearch DAO
   * @param personAddressDao The {@link Dao} handling
   *        {@link gov.ca.cwds.data.persistence.ns.PersonAddress}
   * @param addressDao The {@link Dao} handling {@link gov.ca.cwds.data.persistence.ns.Address}
   * @param personPhoneDao The {@link Dao} handling
   *        {@link gov.ca.cwds.data.persistence.ns.PersonPhone}
   * @param phoneNumberDao The {@link Dao} handling
   *        {@link gov.ca.cwds.data.persistence.ns.PhoneNumber}
   * @param personLanguageDao The {@link Dao} handling
   *        {@link gov.ca.cwds.data.persistence.ns.PersonLanguage}
   * @param languageDao The {@link Dao} handling {@link gov.ca.cwds.data.persistence.ns.Language}
   * @param personRaceDao The {@link Dao} handling
   *        {@link gov.ca.cwds.data.persistence.ns.PersonRace}
   * @param raceDao The {@link Dao} handling {@link gov.ca.cwds.data.persistence.ns.Race}
   * @param personEthnicityDao The {@link Dao} handling
   *        {@link gov.ca.cwds.data.persistence.ns.PersonEthnicity}
   * @param ethnicityDao The {@link Dao} handling {@link gov.ca.cwds.data.persistence.ns.Ethnicity}
   * 
   */
  @Inject
  public PersonService(PersonDao personDao, ElasticsearchDao elasticsearchDao,
      PersonAddressDao personAddressDao, AddressDao addressDao, PersonPhoneDao personPhoneDao,
      PhoneNumberDao phoneNumberDao, PersonLanguageDao personLanguageDao, LanguageDao languageDao,
      PersonRaceDao personRaceDao, RaceDao raceDao, PersonEthnicityDao personEthnicityDao,
      EthnicityDao ethnicityDao) {
    this.personDao = personDao;
    this.elasticsearchDao = elasticsearchDao;
    this.personAddressDao = personAddressDao;
    this.addressDao = addressDao;
    this.personPhoneDao = personPhoneDao;
    this.phoneNumberDao = phoneNumberDao;
    this.personLanguageDao = personLanguageDao;
    this.languageDao = languageDao;
    this.personRaceDao = personRaceDao;
    this.raceDao = raceDao;
    this.personEthnicityDao = personEthnicityDao;
    this.ethnicityDao = ethnicityDao;
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
    gov.ca.cwds.data.persistence.ns.Person managedPerson =
        new gov.ca.cwds.data.persistence.ns.Person(person, null, null);
    managedPerson = personDao.create(managedPerson);
    PopulatePersonDetails(person, managedPerson);
    managedPerson = personDao.find(managedPerson.getId());
    PostedPerson postedPerson = new PostedPerson(managedPerson);
    try {
      // final gov.ca.cwds.rest.api.domain.es.Person esPerson =
      // new gov.ca.cwds.rest.api.domain.es.Person(managedPerson.getId().toString(),
      // managedPerson.getFirstName(), managedPerson.getLastName(), managedPerson.getSsn(),
      // managedPerson.getGender(), DomainChef.cookDate(managedPerson.getDateOfBirth()),
      // managedPerson.getClass().getName(), MAPPER.writeValueAsString(managedPerson));
      // // final String document = MAPPER.writeValueAsString(esPerson);

      // If the people index is missing, create it.
      // elasticsearchDao.createIndexIfNeeded(INDEX_PERSON);

      // The ES Dao manages its own connections. No need to manually start or stop.
      // elasticsearchDao.index(INDEX_PERSON, DOCUMENT_TYPE_PERSON, document, esPerson.getId());
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
    assert request instanceof Person;
    Person person = (Person) request;
    gov.ca.cwds.data.persistence.ns.Person managedPerson =
        new gov.ca.cwds.data.persistence.ns.Person(person, null, null);
    managedPerson = personDao.update(managedPerson);
    PopulatePersonDetails(person, managedPerson);
    managedPerson = personDao.find(managedPerson.getId());
    PostedPerson postedPerson = new PostedPerson(managedPerson);
    return postedPerson;
  }

  /**
   * 
   * @param person - The person
   * @param managedPerson - The managedPerson
   */
  private void PopulatePersonDetails(Person person,
      gov.ca.cwds.data.persistence.ns.Person managedPerson) {
    if (person.getAddress() != null && person.getAddress().size() > 0) {
      for (Address address : person.getAddress()) {
        gov.ca.cwds.data.persistence.ns.Address managedAddress =
            new gov.ca.cwds.data.persistence.ns.Address(address, null, null);
        PersonAddress personAddress = new PersonAddress(managedPerson, managedAddress);
        managedPerson.addPersonAddress(personAddress);
        addressDao.create(managedAddress);
        personAddressDao.create(personAddress);
      }
    }
    if (person.getPhoneNumber() != null && person.getPhoneNumber().size() > 0) {
      for (PhoneNumber phoneNumber : person.getPhoneNumber()) {
        gov.ca.cwds.data.persistence.ns.PhoneNumber managedPhoneNumber =
            new gov.ca.cwds.data.persistence.ns.PhoneNumber(phoneNumber, null, null);
        PersonPhone personPhone = new PersonPhone(managedPerson, managedPhoneNumber);
        managedPerson.addPersonPhone(personPhone);
        phoneNumberDao.create(managedPhoneNumber);
        personPhoneDao.create(personPhone);
      }
    }
    if (person.getLanguage() != null && person.getLanguage().size() > 0) {
      for (Language language : person.getLanguage()) {
        gov.ca.cwds.data.persistence.ns.Language managedLanguage =
            new gov.ca.cwds.data.persistence.ns.Language(language, null, null);
        PersonLanguage personLanguage = new PersonLanguage(managedPerson, managedLanguage);
        managedPerson.addPersonLanguage(personLanguage);
        languageDao.create(managedLanguage);
        personLanguageDao.create(personLanguage);
      }
    }
    if (person.getRace() != null && person.getRace().size() > 0) {
      for (Race race : person.getRace()) {
        gov.ca.cwds.data.persistence.ns.Race managedRace =
            new gov.ca.cwds.data.persistence.ns.Race(race, null, null);
        PersonRace personRace = new PersonRace(managedPerson, managedRace);
        managedPerson.addPersonRace(personRace);
        raceDao.create(managedRace);
        personRaceDao.create(personRace);
      }
    }
    if (person.getEthnicity() != null && person.getEthnicity().size() > 0) {
      for (Ethnicity ethnicity : person.getEthnicity()) {
        gov.ca.cwds.data.persistence.ns.Ethnicity managedEthnicity =
            new gov.ca.cwds.data.persistence.ns.Ethnicity(ethnicity, null, null);
        PersonEthnicity personEthnicity = new PersonEthnicity(managedPerson, managedEthnicity);
        managedPerson.addPersonEthnicity(personEthnicity);
        ethnicityDao.create(managedEthnicity);
        personEthnicityDao.create(personEthnicity);
      }
    }
  }

}
