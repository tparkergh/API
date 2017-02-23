package gov.ca.cwds.rest.services;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.NotImplementedException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

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
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.Address;
import gov.ca.cwds.rest.api.domain.DomainChef;
import gov.ca.cwds.rest.api.domain.Ethnicity;
import gov.ca.cwds.rest.api.domain.Language;
import gov.ca.cwds.rest.api.domain.Person;
import gov.ca.cwds.rest.api.domain.PhoneNumber;
import gov.ca.cwds.rest.api.domain.PostedPerson;
import gov.ca.cwds.rest.api.domain.Race;


/**
 * @author CWDS API Team
 *
 */
// TODO: #136527227: review:
// 1) Test conditions in PersonService.queryPersonOr().
// 2) Review possible conditions in ElasticsearchDao.queryPersonOr(), such as wildcards ("*" or
// "?").

@SuppressWarnings("javadoc")
public class PersonServiceTest {

  private PersonService personService;
  private PersonDao personDao;
  ElasticsearchDao elasticsearchDao;
  private PersonAddressDao personAddressDao;
  private AddressDao addressDao;
  private PhoneNumberDao phoneNumberDao;
  private PersonPhoneDao personPhoneDao;
  private LanguageDao languageDao;
  private PersonLanguageDao personLanguageDao;
  private RaceDao raceDao;
  private PersonRaceDao personRaceDao;
  private EthnicityDao ethinictyDao;
  private PersonEthnicityDao personEthnicityDao;

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Before
  public void setup() throws Exception {
    personDao = mock(PersonDao.class);
    elasticsearchDao = mock(ElasticsearchDao.class);
    addressDao = mock(AddressDao.class);
    personAddressDao = mock(PersonAddressDao.class);
    phoneNumberDao = mock(PhoneNumberDao.class);
    personPhoneDao = mock(PersonPhoneDao.class);
    languageDao = mock(LanguageDao.class);
    personLanguageDao = mock(PersonLanguageDao.class);
    raceDao = mock(RaceDao.class);
    personRaceDao = mock(PersonRaceDao.class);
    ethinictyDao = mock(EthnicityDao.class);
    personEthnicityDao = mock(PersonEthnicityDao.class);
    personService = new PersonService(personDao, elasticsearchDao, personAddressDao, addressDao,
        personPhoneDao, phoneNumberDao, personLanguageDao, languageDao, personRaceDao, raceDao,
        personEthnicityDao, ethinictyDao);
  }

  /*
   * find tests
   */
  @Test
  public void findReturnsCorrectPersonWhenFoundWhenFound() throws Exception {
    Address address = new Address("742 Evergreen Terrace", "Springfield", "WA", 98700, "Home");
    PhoneNumber phoneNumber = new PhoneNumber("408-277-4778", "cell");
    Language language = new Language("English");
    Race race = new Race("White", "European");
    Ethnicity ethnicity = new Ethnicity("Unknown", "South American");
    Set<Address> addresses = new HashSet<Address>();
    addresses.add(address);
    Set<PhoneNumber> phoneNumbers = new HashSet<PhoneNumber>();
    phoneNumbers.add(phoneNumber);
    Set<Language> languages = new HashSet<Language>();
    languages.add(language);
    Set<Race> races = new HashSet<Race>();
    races.add(race);
    Set<Ethnicity> ethnicities = new HashSet<Ethnicity>();
    ethnicities.add(ethnicity);
    Person expected = new Person("Bart", "Simpson", "M", "2016-10-31", "1234556789", addresses,
        phoneNumbers, languages, races, ethnicities);

    gov.ca.cwds.data.persistence.ns.Person person =
        new gov.ca.cwds.data.persistence.ns.Person(expected, null, null);

    when(personDao.find(123L)).thenReturn(person);
    Person found = personService.find(123L);
    assertThat(found, is(expected));
  }

  @Test
  public void findReturnsNullWhenNotFound() throws Exception {
    when(personDao.find(new Long(-1))).thenReturn(null);
    Person found = personService.find(new Long(-1));
    assertThat(found, is(nullValue()));
  }

  @Test
  public void findThrowsAssertionError() throws Exception {
    thrown.expect(AssertionError.class);
    try {
      personService.find("nonLong");
    } catch (AssertionError e) {
      assertEquals("Expected AssertionError", e.getMessage());
    }
  }

  /*
   * create tests
   */
  @Test
  public void createReturnsPostedPerson() throws Exception {
    gov.ca.cwds.data.persistence.ns.Address toCreateAddress =
        new gov.ca.cwds.data.persistence.ns.Address(1L, "742 Evergreen Terrace", "Springfield",
            "WA", new Integer(98700), "Home");
    Set<PersonAddress> personAddresses = new HashSet<>();

    PersonAddress personAddress = new PersonAddress();
    personAddress.setAddress(toCreateAddress);
    personAddresses.add(personAddress);
    gov.ca.cwds.data.persistence.ns.Person toCreate = new gov.ca.cwds.data.persistence.ns.Person(2L,
        "Bart", "Simpson", "M", DomainChef.uncookDateString("2013-10-31"), "1234556789",
        personAddresses, null, null, null, null);

    Person request = new Person(toCreate);
    when(personDao.create(any(gov.ca.cwds.data.persistence.ns.Person.class))).thenReturn(toCreate);
    when(personDao.find(any(gov.ca.cwds.data.persistence.ns.Person.class))).thenReturn(toCreate);
    Response response = personService.create(request);
    assertThat(response.getClass(), is(PostedPerson.class));
  }

  @Test
  public void createReturnsNonNull() throws Exception {
    gov.ca.cwds.data.persistence.ns.Address toCreateAddress =
        new gov.ca.cwds.data.persistence.ns.Address(1L, "742 Evergreen Terrace", "Springfield",
            "WA", new Integer(98700), "Home");
    Set<PersonAddress> personAddresses = new HashSet<>();

    PersonAddress personAddress = new PersonAddress();
    personAddress.setAddress(toCreateAddress);
    personAddresses.add(personAddress);
    gov.ca.cwds.data.persistence.ns.Person toCreate = new gov.ca.cwds.data.persistence.ns.Person(2L,
        "Bart", "Simpson", "M", DomainChef.uncookDateString("2016-10-31"), "1234556789",
        personAddresses, null, null, null, null);
    Person request = new Person(toCreate);
    when(personDao.create(any(gov.ca.cwds.data.persistence.ns.Person.class))).thenReturn(toCreate);
    when(personDao.find(any(gov.ca.cwds.data.persistence.ns.Person.class))).thenReturn(toCreate);

    PostedPerson postedPerson = personService.create(request);
    assertThat(postedPerson, is(notNullValue()));
  }

  @Test
  public void createThrowsAssertionError() throws Exception {
    thrown.expect(AssertionError.class);
    try {
      @SuppressWarnings("unused")
      PostedPerson postedPerson = personService.create(null);
    } catch (AssertionError e) {
      assertEquals("Expected AssertionError", e.getMessage());
    }
  }

  /*
   * delete tests
   */
  @Test
  public void deleteThrowsNotImplementedException() throws Exception {
    thrown.expect(NotImplementedException.class);
    personService.delete(124L);
  }

  @Test
  public void deleteThrowsAssertionError() throws Exception {
    thrown.expect(AssertionError.class);
    try {
      personService.delete("nonLong");
    } catch (AssertionError e) {
      assertEquals("Expected AssertionError", e.getMessage());
    }
  }

  /*
   * update tests
   */
  @Test
  public void updateThrowsNullPointerException() throws Exception {
    thrown.expect(NullPointerException.class);

    Address address = new Address("742 Evergreen Terrace", "Springfield", "WA", 98700, "Home");
    Set<Address> addresses = new HashSet<>();
    addresses.add(address);
    Person toUpdate = new Person("Bart", "Simpson", "M", "2013-10-31", "1234556789", addresses,
        null, null, null, null);
    personService.update(1L, toUpdate);
  }

  @Test
  public void updateThrowsAssertionError() throws Exception {
    thrown.expect(AssertionError.class);
    try {
      personService.update("wrong", null);
      Assert.fail("Expected AssertionError");
    } catch (AssertionError e) {
      assertEquals("Expected AssertionError", e.getMessage());
    }
  }
}
