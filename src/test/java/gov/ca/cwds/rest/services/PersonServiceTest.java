package gov.ca.cwds.rest.services;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.apache.commons.lang3.NotImplementedException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import gov.ca.cwds.data.es.ElasticsearchDao;
import gov.ca.cwds.data.ns.PersonDao;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.Address;
import gov.ca.cwds.rest.api.domain.DomainChef;
import gov.ca.cwds.rest.api.domain.Person;
import gov.ca.cwds.rest.api.domain.PostedPerson;


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

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Before
  public void setup() throws Exception {
    personDao = mock(PersonDao.class);
    elasticsearchDao = mock(ElasticsearchDao.class);
    personService = new PersonService(personDao, elasticsearchDao);
  }

  /*
   * find tests
   */
  @Test
  public void findReturnsCorrectPersonWhenFoundWhenFound() throws Exception {
    Address address = new Address("742 Evergreen Terrace", "Springfield", "WA", 98700, "Home");
    Person expected = new Person("Bart", "Simpson", "M", "2016-10-31", "1234556789", address);

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
    gov.ca.cwds.data.persistence.ns.Person toCreate =
        new gov.ca.cwds.data.persistence.ns.Person(2L, "Bart", "Simpson", "M",
            DomainChef.uncookDateString("2013-10-31"), "1234556789", toCreateAddress);
    Person request = new Person(toCreate);
    when(personDao.create(any(gov.ca.cwds.data.persistence.ns.Person.class))).thenReturn(toCreate);

    Response response = personService.create(request);
    assertThat(response.getClass(), is(PostedPerson.class));
  }

  @Test
  public void createReturnsNonNull() throws Exception {
    gov.ca.cwds.data.persistence.ns.Address toCreateAddress =
        new gov.ca.cwds.data.persistence.ns.Address(1L, "742 Evergreen Terrace", "Springfield",
            "WA", new Integer(98700), "Home");
    gov.ca.cwds.data.persistence.ns.Person toCreate =
        new gov.ca.cwds.data.persistence.ns.Person(2L, "Bart", "Simpson", "M",
            DomainChef.uncookDateString("2016-10-31"), "1234556789", toCreateAddress);
    Person request = new Person(toCreate);
    when(personDao.create(any(gov.ca.cwds.data.persistence.ns.Person.class))).thenReturn(toCreate);

    PostedPerson postedPerson = personService.create(request);
    assertThat(postedPerson, is(notNullValue()));
  }

  @Test
  public void createReturnsCorrectPostedPerson() throws Exception {
    gov.ca.cwds.data.persistence.ns.Address toCreateAddress =
        new gov.ca.cwds.data.persistence.ns.Address(1L, "742 Evergreen Terrace", "Springfield",
            "WA", new Integer(98700), "Home");
    gov.ca.cwds.data.persistence.ns.Person toCreate =
        new gov.ca.cwds.data.persistence.ns.Person(2L, "Bart", "Simpson", "M",
            DomainChef.uncookDateString("2016-10-31"), "1234556789", toCreateAddress);
    Person request = new Person(toCreate);
    when(personDao.create(any(gov.ca.cwds.data.persistence.ns.Person.class))).thenReturn(toCreate);

    Address address = new Address("742 Evergreen Terrace", "Springfield", "WA", 98700, "Home");
    PostedPerson expected =
        new PostedPerson(2L, "Bart", "Simpson", "M", "2016-10-31", "1234556789", address);

    PostedPerson returned = personService.create(request);
    assertThat(returned, is(expected));
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
  public void updateThrowsNotImplementedException() throws Exception {
    thrown.expect(NotImplementedException.class);

    Address address = new Address("742 Evergreen Terrace", "Springfield", "WA", 98700, "Home");
    Person toUpdate = new Person("Bart", "Simpson", "M", "04/01/1990", "1234556789", address);
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
