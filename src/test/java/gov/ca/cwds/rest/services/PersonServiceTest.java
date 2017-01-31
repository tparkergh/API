package gov.ca.cwds.rest.services;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.NotImplementedException;
import org.elasticsearch.search.SearchHit;
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
import gov.ca.cwds.rest.api.domain.es.ESPerson;
import gov.ca.cwds.rest.api.domain.es.ESSearchRequest;


// TODO: #136527227: review:
// 1) Test conditions in PersonService.queryPersonOr().
// 2) Review possible conditions in ElasticsearchDao.queryPersonOr(), such as wildcards ("*" or
// "?").

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
    Address address = new Address("742 Evergreen Terrace", "Springfield", "WA", 98700);
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
            "WA", new Integer(98700));
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
            "WA", new Integer(98700));
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
            "WA", new Integer(98700));
    gov.ca.cwds.data.persistence.ns.Person toCreate =
        new gov.ca.cwds.data.persistence.ns.Person(2L, "Bart", "Simpson", "M",
            DomainChef.uncookDateString("2016-10-31"), "1234556789", toCreateAddress);
    Person request = new Person(toCreate);
    when(personDao.create(any(gov.ca.cwds.data.persistence.ns.Person.class))).thenReturn(toCreate);

    Address address = new Address("742 Evergreen Terrace", "Springfield", "WA", 98700);
    PostedPerson expected =
        new PostedPerson(2L, "Bart", "Simpson", "M", "2016-10-31", "1234556789", address);

    PostedPerson returned = personService.create(request);
    assertThat(returned, is(expected));
  }

  @Test
  public void createThrowsAssertionError() throws Exception {
    thrown.expect(AssertionError.class);
    try {
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

    Address address = new Address("742 Evergreen Terrace", "Springfield", "WA", 98700);
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

  /*
   * Test for all Success Hits
   */
  @Test
  public void TestForFetchAllPersonsWithSuccessHits() throws Exception {
    SearchHit[] searchHits = new SearchHit[2];
    SearchHit hit1 = mock(SearchHit.class);
    SearchHit hit2 = mock(SearchHit.class);
    searchHits[0] = hit1;
    searchHits[1] = hit2;
    when(elasticsearchDao.fetchAllPerson()).thenReturn(searchHits);
    ESPerson[] persons = personService.fetchAllPersons();
    assertThat(persons.length, is(2));
  }

  /*
   * Test with Success Content
   */
  @Test
  public void TestForFetchAllPersonsWithSuccessContent() throws Exception {
    SearchHit[] searchHits = new SearchHit[1];
    Map<String, Object> map = new HashMap<String, Object>();
    map.put("first_name", "ABC");
    SearchHit hit1 = mock(SearchHit.class);
    searchHits[0] = hit1;
    when(hit1.getSource()).thenReturn(map);
    when(elasticsearchDao.fetchAllPerson()).thenReturn(searchHits);
    ESPerson[] persons = personService.fetchAllPersons();
    assertThat(persons[0].getFirstName(), is("ABC"));
  }

  /*
   * Test for no results
   */
  @Test
  public void TestForFetchAllPersonsWithAsserExceptionResult() throws Exception {
    thrown.expect(Exception.class);
    when(elasticsearchDao.fetchAllPerson()).thenReturn(null);
    ESPerson[] persons = personService.fetchAllPersons();
  }

  /*
   * Test to find person with First_Name
   */
  @Test
  public void TestQueryPersonOrWithFirstName() throws Exception {
    SearchHit[] searchHits = new SearchHit[1];
    Map<String, Object> map = new HashMap<String, Object>();
    map.put("first_name", "ABC");
    map.put("last_name", "YYY");
    map.put("date_of_birth", "01-06-1998");
    SearchHit hit = mock(SearchHit.class);
    searchHits[0] = hit;
    when(hit.getSource()).thenReturn(map);
    ESSearchRequest reqFirstName = new ESSearchRequest();
    reqFirstName.getRoot().addElem(new ESSearchRequest.ESFieldSearchEntry("first_name", "ABC"));
    ESSearchRequest reqLastName = new ESSearchRequest();
    reqLastName.getRoot().addElem(new ESSearchRequest.ESFieldSearchEntry("last_name", "YYY"));
    ESSearchRequest reqDOB = new ESSearchRequest();
    reqDOB.getRoot().addElem(new ESSearchRequest.ESFieldSearchEntry("date_of_birth", "01-06-1998"));

    when(elasticsearchDao.queryPersonOr(reqFirstName)).thenReturn(searchHits);
    when(elasticsearchDao.queryPersonOr(reqLastName)).thenReturn(searchHits);
    when(elasticsearchDao.queryPersonOr(reqDOB)).thenReturn(searchHits);

    ESPerson[] persons = personService.queryPersonOr("ABC", "", "");
    assertThat(persons[0].getFirstName(), is("ABC"));
  }

  /*
   * Test to find person with Last_Name
   */
  @Test
  public void TestQueryPersonOrWithLastName() throws Exception {
    SearchHit[] searchHits = new SearchHit[1];
    Map<String, Object> map = new HashMap<String, Object>();
    map.put("first_name", "ABC");
    map.put("last_name", "YYY");
    map.put("date_of_birth", "01-06-1998");
    SearchHit hit = mock(SearchHit.class);
    searchHits[0] = hit;
    when(hit.getSource()).thenReturn(map);
    ESSearchRequest reqFirstName = new ESSearchRequest();
    reqFirstName.getRoot().addElem(new ESSearchRequest.ESFieldSearchEntry("first_name", "ABC"));
    ESSearchRequest reqLastName = new ESSearchRequest();
    reqLastName.getRoot().addElem(new ESSearchRequest.ESFieldSearchEntry("last_name", "YYY"));
    ESSearchRequest reqDOB = new ESSearchRequest();
    reqDOB.getRoot().addElem(new ESSearchRequest.ESFieldSearchEntry("date_of_birth", "01-06-1998"));

    when(elasticsearchDao.queryPersonOr(reqFirstName)).thenReturn(searchHits);
    when(elasticsearchDao.queryPersonOr(reqLastName)).thenReturn(searchHits);
    when(elasticsearchDao.queryPersonOr(reqDOB)).thenReturn(searchHits);

    ESPerson[] persons = personService.queryPersonOr("", "YYY", "");
    assertThat(persons[0].getLastName(), is("YYY"));
  }

  /*
   * Test to find person with Date of Birth
   */
  @Test
  public void TestQueryPersonOrWithDateOfBirth() throws Exception {
    SearchHit[] searchHits = new SearchHit[1];
    Map<String, Object> map = new HashMap<String, Object>();
    map.put("first_name", "ABC");
    map.put("last_name", "YYY");
    map.put("date_of_birth", "01-06-1998");
    SearchHit hit = mock(SearchHit.class);
    searchHits[0] = hit;
    when(hit.getSource()).thenReturn(map);
    ESSearchRequest reqFirstName = new ESSearchRequest();
    reqFirstName.getRoot().addElem(new ESSearchRequest.ESFieldSearchEntry("first_name", "ABC"));
    ESSearchRequest reqLastName = new ESSearchRequest();
    reqLastName.getRoot().addElem(new ESSearchRequest.ESFieldSearchEntry("last_name", "YYY"));
    ESSearchRequest reqDOB = new ESSearchRequest();
    reqDOB.getRoot().addElem(new ESSearchRequest.ESFieldSearchEntry("date_of_birth", "01-06-1998"));

    when(elasticsearchDao.queryPersonOr(reqFirstName)).thenReturn(searchHits);
    when(elasticsearchDao.queryPersonOr(reqLastName)).thenReturn(searchHits);
    when(elasticsearchDao.queryPersonOr(reqDOB)).thenReturn(searchHits);

    ESPerson[] persons = personService.queryPersonOr("", "", "01-06-1998");
    assertThat(persons[0].getBirthDate(), is("01-06-1998"));
  }

  /*
   * Test to find person with all request
   */
  @Test
  public void TestQueryPersonOrWithSuccess() throws Exception {
    SearchHit[] searchHits = new SearchHit[1];
    Map<String, Object> map = new HashMap<String, Object>();
    map.put("first_name", "ABC");
    map.put("last_name", "YYY");
    map.put("date_of_birth", "01-06-1998");
    SearchHit hit = mock(SearchHit.class);
    searchHits[0] = hit;
    when(hit.getSource()).thenReturn(map);
    ESSearchRequest reqFirstName = new ESSearchRequest();
    reqFirstName.getRoot().addElem(new ESSearchRequest.ESFieldSearchEntry("first_name", "ABC"));
    ESSearchRequest reqLastName = new ESSearchRequest();
    reqLastName.getRoot().addElem(new ESSearchRequest.ESFieldSearchEntry("last_name", "YYY"));
    ESSearchRequest reqDOB = new ESSearchRequest();
    reqDOB.getRoot().addElem(new ESSearchRequest.ESFieldSearchEntry("date_of_birth", "01-06-1998"));

    when(elasticsearchDao.queryPersonOr(reqFirstName)).thenReturn(searchHits);
    when(elasticsearchDao.queryPersonOr(reqLastName)).thenReturn(searchHits);
    when(elasticsearchDao.queryPersonOr(reqDOB)).thenReturn(searchHits);

    ESPerson[] persons = personService.queryPersonOr("ABC", "YYY", "01-06-1998");
    assertThat(persons[0].getFirstName(), is("ABC"));
    assertThat(persons[0].getLastName(), is("YYY"));
    assertThat(persons[0].getBirthDate(), is("01-06-1998"));
  }

  /*
   * Test query when no record found
   */
  @Test
  public void TestQueryPersonOrWithAssertNull() throws Exception {
    thrown.expect(Exception.class);
    SearchHit[] searchHits = new SearchHit[1];
    Map<String, Object> map = new HashMap<String, Object>();
    map.put("first_name", "ABC");
    map.put("last_name", "YYY");
    map.put("date_of_birth", "01-06-1998");
    SearchHit hit = mock(SearchHit.class);
    searchHits[0] = hit;
    when(hit.getSource()).thenReturn(map);
    ESSearchRequest reqFirstName = new ESSearchRequest();
    reqFirstName.getRoot().addElem(new ESSearchRequest.ESFieldSearchEntry("first_name", "ABC"));
    ESSearchRequest reqLastName = new ESSearchRequest();
    reqLastName.getRoot().addElem(new ESSearchRequest.ESFieldSearchEntry("last_name", "YYY"));
    ESSearchRequest reqDOB = new ESSearchRequest();
    reqDOB.getRoot().addElem(new ESSearchRequest.ESFieldSearchEntry("date_of_birth", "01-06-1998"));
    when(elasticsearchDao.queryPersonOr(reqFirstName)).thenReturn(searchHits);
    when(elasticsearchDao.queryPersonOr(reqLastName)).thenReturn(searchHits);
    when(elasticsearchDao.queryPersonOr(reqDOB)).thenReturn(searchHits);
    ESPerson[] persons = personService.queryPersonOr("", "", "01-06-1999");
  }

  /*
   * Test query exception when request Empty
   */
  @Test
  public void TestQueryPersonOrWithEmptyRequest() throws Exception {
    thrown.expect(Exception.class);
    SearchHit[] searchHits = new SearchHit[1];
    Map<String, Object> map = new HashMap<String, Object>();
    map.put("first_name", "ABC");
    map.put("last_name", "YYY");
    map.put("date_of_birth", "1988");
    SearchHit hit = mock(SearchHit.class);
    searchHits[0] = hit;
    when(hit.getSource()).thenReturn(map);
    ESSearchRequest reqFirstName = new ESSearchRequest();
    reqFirstName.getRoot().addElem(new ESSearchRequest.ESFieldSearchEntry("first_name", "ABC"));
    ESSearchRequest reqLastName = new ESSearchRequest();
    reqLastName.getRoot().addElem(new ESSearchRequest.ESFieldSearchEntry("last_name", "YYY"));
    ESSearchRequest reqDOB = new ESSearchRequest();
    reqDOB.getRoot().addElem(new ESSearchRequest.ESFieldSearchEntry("date_of_birth", "1988"));
    when(elasticsearchDao.queryPersonOr(reqFirstName)).thenReturn(searchHits);
    when(elasticsearchDao.queryPersonOr(reqLastName)).thenReturn(searchHits);
    when(elasticsearchDao.queryPersonOr(reqDOB)).thenReturn(searchHits);
    ESPerson[] persons = personService.queryPersonOr("", "", "");
  }

}
