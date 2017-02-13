package gov.ca.cwds.rest.services;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.contains;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.Objects;

import javax.persistence.EntityNotFoundException;

import org.apache.commons.lang3.NotImplementedException;
import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import gov.ca.cwds.data.ns.ScreeningDao;
import gov.ca.cwds.data.persistence.ns.Address;
import gov.ca.cwds.data.persistence.ns.Screening;
import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.DomainChef;
import gov.ca.cwds.rest.api.domain.Person;
import gov.ca.cwds.rest.api.domain.PostedScreening;
import gov.ca.cwds.rest.api.domain.ScreeningReference;
import gov.ca.cwds.rest.api.domain.ScreeningRequest;
import gov.ca.cwds.rest.api.domain.ScreeningResponse;

public class ScreeningServiceTest {
  private ScreeningService screeningService;
  private ScreeningDao screeningDao;
  private PersonService personService;

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Before
  public void setup() throws Exception {
    screeningDao = mock(ScreeningDao.class);
    personService = mock(PersonService.class);
    screeningService = new ScreeningService(screeningDao, personService);
  }

  /*
   * find tests
   */
  @Test
  public void findReturnsCorrectScreeningWhenFoundAndParticipantListIsNotNull() throws Exception {
    gov.ca.cwds.rest.api.domain.Address domainAddress = new gov.ca.cwds.rest.api.domain.Address(
        "742 Evergreen Terrace", "Springfield", "WA", 98700, "Home");
    Person bart = new Person("Bart", "Simpson", "M", "2016-10-31", "123456789", domainAddress);
    Person maggie = new Person("Maggie", "Simpson", "M", "2016-10-31", "123456789", domainAddress);

    Address address = new Address(1L, "742 Evergreen Terrace", "Springfield", "WA", 98700, "Home");
    Date date = DomainChef.uncookDateString("2016-10-31");
    ImmutableSet.Builder<gov.ca.cwds.data.persistence.ns.Person> persistentPersonSetBuilder =
        ImmutableSet.builder();
    persistentPersonSetBuilder.add(new gov.ca.cwds.data.persistence.ns.Person(bart, null, null))
        .add(new gov.ca.cwds.data.persistence.ns.Person(maggie, null, null));

    Screening screening = new Screening("X5HNJK", date, "Amador", date, "Home", "email",
        "First screening", "accept_for_investigation", date, "first narrative", address,
        persistentPersonSetBuilder.build());

    ImmutableSet.Builder<Person> domainPersonSetBuilder = ImmutableSet.builder();
    domainPersonSetBuilder.add(bart).add(maggie);

    when(screeningDao.find(new Long(123))).thenReturn(screening);
    when(personService.find(1L)).thenReturn(bart);
    when(personService.find(2L)).thenReturn(maggie);

    ScreeningResponse expected = new ScreeningResponse("X5HNJK", "2016-10-13", "Amador",
        "2016-10-13", "Home", "email", "First screening", "immediate", "accept_for_investigation",
        "10/11/2016", "first narrative", domainAddress, domainPersonSetBuilder.build());

    Response found = screeningService.find(123L);
    assertThat(found.getClass(), is(ScreeningResponse.class));
    assertThat(found, is(expected));
  }

  @Test
  public void findReturnsCorrectScreeningWhenFoundAndParticipantListIsNull() throws Exception {
    Address address = new Address(1L, "742 Evergreen Terrace", "Springfield", "WA", 98700, "Home");
    Date date = DomainChef.uncookDateString("2016-10-31");
    Screening screening = new Screening("X5HNJK", date, "Amador", date, "Home", "email",
        "First screening", "accept_for_investigation", date, "first narrative", address, null);

    gov.ca.cwds.rest.api.domain.Address domainAddress = new gov.ca.cwds.rest.api.domain.Address(
        "742 Evergreen Terrace", "Springfield", "WA", 98700, "Home");

    when(screeningDao.find(new Long(123))).thenReturn(screening);

    ImmutableSet.Builder<Person> setbuilder = ImmutableSet.builder();
    ScreeningResponse expected = new ScreeningResponse("X5HNJK", "10/13/2016", "Amador",
        "10/13/2016", "Home", "email", "First screening", null, "accept_for_investigation",
        "10/13/2016", "first narrative", domainAddress, setbuilder.build());

    Response found = screeningService.find(123L);
    assertThat(found.getClass(), is(ScreeningResponse.class));
    assertThat(found, is(expected));
  }

  @Test
  public void findReturnsNullWhenNotFound() throws Exception {
    Response found = screeningService.find(33L);
    assertThat(found, is(nullValue()));
  }


  @Test
  public void findThrowsAssertionError() throws Exception {
    thrown.expect(AssertionError.class);
    try {
      screeningService.find("nonLong");
    } catch (AssertionError e) {
      assertEquals("Expected AssertionError", e.getMessage());
    }
  }


  /*
   * create tests
   */
  @Test
  public void createReturnsPostedScreeningClass() throws Exception {
    Screening screeningMock = mock(Screening.class);
    when(screeningMock.getReference()).thenReturn("some_reference");
    when(screeningMock.getId()).thenReturn(1L);
    ScreeningReference request = new ScreeningReference("some_reference");

    when(screeningDao.create(any(gov.ca.cwds.data.persistence.ns.Screening.class)))
        .thenReturn(screeningMock);

    PostedScreening postedScreening = screeningService.create(request);
    assertThat(postedScreening.getClass(), is(PostedScreening.class));
  }

  @Test
  public void createReturnsNonNull() throws Exception {
    Screening screeningMock = mock(Screening.class);
    when(screeningMock.getReference()).thenReturn("some_reference");
    when(screeningMock.getId()).thenReturn(1L);
    ScreeningReference request = new ScreeningReference("some_reference");

    when(screeningDao.create(any(gov.ca.cwds.data.persistence.ns.Screening.class)))
        .thenReturn(screeningMock);

    PostedScreening postedScreening = screeningService.create(request);
    assertThat(postedScreening, is(notNullValue()));
  }

  @Test
  public void createReturnsReturnsCorrectPostedScreening() throws Exception {
    Screening screeningMock = mock(Screening.class);
    when(screeningMock.getReference()).thenReturn("some_reference");
    when(screeningMock.getId()).thenReturn(1L);
    ScreeningReference request = new ScreeningReference("some_reference");

    when(screeningDao.create(any(gov.ca.cwds.data.persistence.ns.Screening.class)))
        .thenReturn(screeningMock);

    PostedScreening expected = new PostedScreening(1L, "some_reference");
    PostedScreening returned = screeningService.create(request);
    assertThat(returned, is(expected));
  }

  @Test
  public void createThrowsAssertionError() throws Exception {
    thrown.expect(AssertionError.class);
    try {
      screeningService.create(null);
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
    screeningService.delete(new Long(1));
  }

  @Test
  public void deleteThrowsAssertionError() throws Exception {
    thrown.expect(AssertionError.class);
    try {
      screeningService.delete("nonLong");
    } catch (AssertionError e) {
      assertEquals("Expected AssertionError", e.getMessage());
    }
  }

  /*
   * update tests
   */
  @Test
  public void updateThrowsAssertionError() throws Exception {
    thrown.expect(AssertionError.class);
    try {
      screeningService.update("wrong", null);
    } catch (AssertionError e) {
      assertEquals("Expected AssertionError", e.getMessage());
    }
  }

  @Test
  public void updateReturnsScreeningResponseOnSuccess() throws Exception {
    gov.ca.cwds.rest.api.domain.Address domainAddress = new gov.ca.cwds.rest.api.domain.Address(
        "742 Evergreen Terrace", "Springfield", "WA", 98700, "Home");
    ImmutableList.Builder<Long> peopleIdListBuilder = ImmutableList.builder();
    ImmutableList<Long> peopleIds = peopleIdListBuilder.add(1L).add(2L).build();

    ScreeningRequest screeningRequest =
        new ScreeningRequest("ref", "2016-10-31", "Sac", "2016-10-31", "loc", "comm", "name", "now",
            "sure", "2016-10-31", "narrative", domainAddress, peopleIds);

    Person bart = new Person("Bart", "Simpson", "M", "2016-10-31", "123456789", domainAddress);
    Person maggie = new Person("Maggie", "Simpson", "M", "2016-10-31", "123456789", domainAddress);
    gov.ca.cwds.data.persistence.ns.Screening screening =
        new gov.ca.cwds.data.persistence.ns.Screening(1L, screeningRequest,
            new Address(domainAddress, null, null), null, null, null);

    when(screeningDao.find(new Long(123))).thenReturn(screening);
    when(personService.find(1L)).thenReturn(bart);
    when(personService.find(2L)).thenReturn(maggie);
    when(screeningDao.update(any())).thenReturn(screening);

    Object retval = screeningService.update(1L, screeningRequest);
    assertThat(retval.getClass(), is(ScreeningResponse.class));
  }

  @Test
  public void updateReturnsCorrectScreeningResponseOnSuccess() throws Exception {
    gov.ca.cwds.rest.api.domain.Address domainAddress = new gov.ca.cwds.rest.api.domain.Address(
        "742 Evergreen Terrace", "Springfield", "WA", 98700, "Home");
    ImmutableList.Builder<Long> peopleIdListBuilder = ImmutableList.builder();
    ImmutableList<Long> peopleIds = peopleIdListBuilder.add(1L).add(2L).build();

    ScreeningRequest screeningRequest =
        new ScreeningRequest("ref", "2016-10-31", "Sac", "2016-10-31", "loc", "comm", "name", "now",
            "sure", "2016-10-31", "narrative", domainAddress, peopleIds);

    ImmutableSet.Builder<gov.ca.cwds.data.persistence.ns.Person> peopleListBuilder =
        ImmutableSet.builder();
    Person bart = new Person("Bart", "Simpson", "M", "2016-10-31", "123456789", domainAddress);
    Person maggie = new Person("Maggie", "Simpson", "M", "2016-10-31", "123456789", domainAddress);
    ImmutableSet<gov.ca.cwds.data.persistence.ns.Person> people =
        peopleListBuilder.add(new gov.ca.cwds.data.persistence.ns.Person(bart, null, null))
            .add(new gov.ca.cwds.data.persistence.ns.Person(maggie, null, null)).build();
    gov.ca.cwds.data.persistence.ns.Screening screening =
        new gov.ca.cwds.data.persistence.ns.Screening(1L, screeningRequest,
            new Address(domainAddress, null, null), people, null, null);

    when(screeningDao.find(new Long(123))).thenReturn(screening);
    when(personService.find(1L)).thenReturn(bart);
    when(personService.find(2L)).thenReturn(maggie);
    when(screeningDao.update(any())).thenReturn(screening);

    ScreeningResponse expected = new ScreeningResponse(screening, people);
    ScreeningResponse updated = screeningService.update(1L, screeningRequest);
    assertThat(updated, is(expected));
  }

  @Test
  public void updateThrowsExceptionWhenParticipantNotFound() throws Exception {
    thrown.expect(ServiceException.class);
    thrown.expectCause(Is.isA(EntityNotFoundException.class));
    thrown.expectMessage(contains("Unable to find participant"));

    gov.ca.cwds.rest.api.domain.Address domainAddress = new gov.ca.cwds.rest.api.domain.Address(
        "742 Evergreen Terrace", "Springfield", "WA", 98700, "Home");
    ImmutableList.Builder<Long> peopleIdListBuilder = ImmutableList.builder();
    ImmutableList<Long> peopleIds = peopleIdListBuilder.add(11L).build();

    ScreeningRequest screeningRequest =
        new ScreeningRequest("ref", "2016-10-31", "Sac", "2016-10-31", "loc", "comm", "name", "now",
            "sure", "2016-10-31", "narrative", domainAddress, peopleIds);

    gov.ca.cwds.data.persistence.ns.Screening screening =
        new gov.ca.cwds.data.persistence.ns.Screening(1L, screeningRequest,
            new Address(domainAddress, null, null), null, null, null);

    when(screeningDao.find(new Long(123))).thenReturn(screening);
    when(personService.find(11L)).thenReturn(null);
    when(screeningDao.update(any())).thenReturn(screening);

    screeningService.update(123L, screeningRequest);
  }

  @Test
  public void updateThrowsAssertionErrorOnWrongIdType() throws Exception {
    // TODO : thrown.expect not working on AssertionError???? WHY???
    // thrown.expect(AssertionError.class);
    try {
      screeningService.update("nonLong", new ScreeningRequest("ref", "10/1/2016", "Sac",
          "10/1/2016", "loc", "comm", "name", "now", "sure", "1/1/2015", "narrative", null, null));
      Assert.fail("Expected AssertionError");
    } catch (AssertionError e) {
    }
  }

  @Test
  public void updateThrowsAssertionErrorOnWrongRequestType() throws Exception {
    thrown.expect(AssertionError.class);
    try {
      screeningService.update(1L, new Request() {});
    } catch (AssertionError e) {
      assertEquals("Expected AssertionError", e.getMessage());
    }
  }

  @Test
  public void equalsHashCodeWork() {
    // Trivial objects.
    ScreeningResponse one = new ScreeningResponse();
    ScreeningResponse two = new ScreeningResponse();
    gov.ca.cwds.rest.api.domain.Screening parent = new gov.ca.cwds.rest.api.domain.Screening();
    gov.ca.cwds.rest.api.domain.PostedScreening sibling =
        new gov.ca.cwds.rest.api.domain.PostedScreening();

    System.out.println("equals: " + one.equals(two));
    System.out.println("Objects.equals: one=>two: " + Objects.equals(one, two));
    System.out.println("Objects.equals: one=>sibling: " + Objects.equals(one, parent));
    System.out.println("Objects.equals: one=>child: " + Objects.equals(one, sibling));

    System.out.println("hash: one: " + one.hashCode() + ", two: " + two.hashCode());

    // Questions.
    // EqualsVerifier claims that a trivial superclass equals this class, but java.util.Objects
    // disproves that claim.
    // That assertion *could be valid*, if a parent class' key *also* defines a child class's key,
    // but for domain classes, inheritance just reduces duplication of columns.
    //
    // In other words, if you stored different class variations of in a single collection, then yes,
    // these tests would matter, but doesn't always apply to API. For instance, you would *never*
    // mix and match domain classes for the same resource or ever mix persistence classes in the
    // same collection.

    // "does not equal superclass instance".
    // Um, it better not!!
    // EqualsVerifier.forClass(ScreeningResponse.class).suppress(Warning.NONFINAL_FIELDS).verify();

    // Subclass: object is not equal to an instance of a trivial subclass with equal fields:
    // Consider making the class final.

    // EqualsVerifier.forClass(ScreeningResponse.class).suppress(Warning.NONFINAL_FIELDS)
    // .withRedefinedSuperclass().verify();

    // EqualsVerifier.forClass(ScreeningResponse.class).withRedefinedSuperclass()
    // .suppress(Warning.TRANSIENT_FIELDS).verify();

    // EqualsVerifier.forClass(ScreeningResponse.class).suppress(Warning.NONFINAL_FIELDS,
    // Warning.NULL_FIELDS, Warning.ANNOTATION, Warning.STRICT_INHERITANCE).verify();

    // EqualsVerifier.forClass(ScreeningResponse.class)
    // .suppress(Warning.NONFINAL_FIELDS, Warning.NULL_FIELDS, Warning.STRICT_INHERITANCE)
    // .withRedefinedSuperclass().withRedefinedSubclass(ScreeningResponse.class).verify();

    // EqualsVerifier.forClass(ScreeningResponse.class)
    // .suppress(Warning.NONFINAL_FIELDS, Warning.NULL_FIELDS).withRedefinedSuperclass()
    // .verify();
  }

}
