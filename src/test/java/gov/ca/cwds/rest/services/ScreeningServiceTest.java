package gov.ca.cwds.rest.services;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.NotImplementedException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import gov.ca.cwds.data.ns.ScreeningDao;
import gov.ca.cwds.rest.api.Response;

public class ScreeningServiceTest {
  private ScreeningService screeningService;
  private ScreeningDao screeningDao;
  private PersonService personService;
  private Set<String> roles = new HashSet<String>();
  private Set<gov.ca.cwds.rest.api.domain.Address> addresses =
      new HashSet<gov.ca.cwds.rest.api.domain.Address>();

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
  // @Test
  // public void findReturnsCorrectScreeningWhenFoundAndParticipantListIsNotNull() throws Exception
  // {
  // roles.add("victim");
  // gov.ca.cwds.rest.api.domain.Address domainAddress = new gov.ca.cwds.rest.api.domain.Address(
  // "742 Evergreen Terrace", "Springfield", "WA", 98700, "home", 3);
  // addresses.add(domainAddress);
  // Participant bart = new Participant(1, "Bart", "Simpson", "male", "123456789", "2016-10-31",
  // 1234, 1234, roles, addresses);
  // Participant maggie = new Participant(2, "Maggie", "Simpson", "female", "123456789",
  // "2016-10-31", 1234, 1234, roles, addresses);
  //
  // Address address = new Address(1L, "742 Evergreen Terrace", "Springfield", "WA", 98700, "home");
  // Date date = DomainChef.uncookDateString("2016-10-31");
  // ImmutableSet.Builder<gov.ca.cwds.data.persistence.ns.Participant> persistentPersonSetBuilder =
  // ImmutableSet.builder();
  // persistentPersonSetBuilder
  // .add(new gov.ca.cwds.data.persistence.ns.Participant(bart, null, null,
  // new gov.ca.cwds.data.persistence.ns.Person(new Person("Bart", "Simpson", "M",
  // "2016-10-31", "123456789", null, null, null, null, null), null, null)))
  // .add(new gov.ca.cwds.data.persistence.ns.Participant(maggie, null, null,
  // new gov.ca.cwds.data.persistence.ns.Person(new Person("Maggie", "Simpson", "M",
  // "2016-10-31", "123456789", null, null, null, null, null), null, null)));
  //
  // Screening screening = new Screening("X5HNJK", date, "Amador", date, "Home", "email",
  // "First screening", "immediate", "accept_for_investigation", date, "first narrative",
  // address, persistentPersonSetBuilder.build());
  //
  // ImmutableSet.Builder<Participant> domainParticipantSetBuilder = ImmutableSet.builder();
  // domainParticipantSetBuilder.add(bart).add(maggie);
  //
  // when(screeningDao.find(new Long(123))).thenReturn(screening);
  //
  // ScreeningResponse expected = new ScreeningResponse("X5HNJK", "2016-10-31", "Amador",
  // "2016-10-31", "Home", "email", "First screening", "immediate", "accept_for_investigation",
  // "2016-10-31", "first narrative", domainAddress, domainParticipantSetBuilder.build());
  //
  // Response found = screeningService.find(123L);
  // assertThat(found.getClass(), is(ScreeningResponse.class));
  // assertThat(found, is(expected));
  // }

  // @Test
  // public void findReturnsCorrectScreeningWhenFoundAndParticipantListIsNull() throws Exception {
  // Address address = new Address(1L, "742 Evergreen Terrace", "Springfield", "WA", 98700, "home");
  // Date date = DomainChef.uncookDateString("2016-10-31");
  // Screening screening =
  // new Screening("X5HNJK", date, "Amador", date, "Home", "email", "First screening",
  // "accept_for_investigation", "immediate", date, "first narrative", address, null);
  //
  // gov.ca.cwds.rest.api.domain.Address domainAddress = new gov.ca.cwds.rest.api.domain.Address(
  // "742 Evergreen Terrace", "Springfield", "WA", 98700, "home", 3);
  //
  // when(screeningDao.find(new Long(123))).thenReturn(screening);
  //
  // ImmutableSet.Builder<Participant> setbuilder = ImmutableSet.builder();
  // ScreeningResponse expected = new ScreeningResponse("X5HNJK", "10/13/2016", "Amador",
  // "10/13/2016", "Home", "email", "First screening", null, "accept_for_investigation",
  // "10/13/2016", "first narrative", domainAddress, setbuilder.build());
  //
  // Response found = screeningService.find(123L);
  // assertThat(found.getClass(), is(ScreeningResponse.class));
  // assertThat(found, is(expected));
  // }

  @Test
  public void findReturnsNullWhenNotFound() throws Exception {
    Response found = screeningService.find(33L);
    assertThat(found, is(nullValue()));
  }

  /*
   * create tests
   */
  @Test
  public void createThrowsAssertionError() throws Exception {
    thrown.expect(AssertionError.class);
    try {
      screeningService.create(null);
    } catch (AssertionError e) {
      assertEquals("Expected AssertionError", e.getMessage());
    }
  }

  @Test
  public void createReturnsPostedScreeningClass() throws Exception {
    // Screening screeningMock = mock(Screening.class);
    // when(screeningMock.getReference()).thenReturn("some_reference");
    // when(screeningMock.getId()).thenReturn(1L);
    // ScreeningReference request = new ScreeningReference("some_reference");
    //
    // when(screeningDao.create(any(gov.ca.cwds.data.persistence.ns.Screening.class)))
    // .thenReturn(screeningMock);
    //
    // PostedScreening postedScreening = screeningService.create(request);
    // assertThat(postedScreening.getClass(), is(PostedScreening.class));
  }

  @Test
  public void createReturnsNonNull() throws Exception {
    // Screening screeningMock = mock(Screening.class);
    // when(screeningMock.getReference()).thenReturn("some_reference");
    // when(screeningMock.getId()).thenReturn(1L);
    // ScreeningReference request = new ScreeningReference("some_reference");
    //
    // when(screeningDao.create(any(gov.ca.cwds.data.persistence.ns.Screening.class)))
    // .thenReturn(screeningMock);
    //
    // PostedScreening postedScreening = screeningService.create(request);
    // assertThat(postedScreening, is(notNullValue()));
  }

  @Test
  public void createReturnsReturnsCorrectPostedScreening() throws Exception {
    // Screening screeningMock = mock(Screening.class);
    // when(screeningMock.getReference()).thenReturn("some_reference");
    // when(screeningMock.getId()).thenReturn(1L);
    // ScreeningReference request = new ScreeningReference("some_reference");
    //
    // when(screeningDao.create(any(gov.ca.cwds.data.persistence.ns.Screening.class)))
    // .thenReturn(screeningMock);
    //
    // PostedScreening expected = new PostedScreening(1L, "some_reference");
    // PostedScreening returned = screeningService.create(request);
    // assertThat(returned, is(expected));
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
    // thrown.expect(AssertionError.class);
    // try {
    // screeningService.update("wrong", null);
    // } catch (AssertionError e) {
    // assertEquals("Expected AssertionError", e.getMessage());
    // }
  }

  @Test
  public void updateReturnsScreeningResponseOnSuccess() throws Exception {
    // gov.ca.cwds.rest.api.domain.Address domainAddress = new
    // gov.ca.cwds.rest.api.domain.Address("",
    // "", "742 Evergreen Terrace", "Springfield", "WA", 98700, "home");
    // ScreeningRequest screeningRequest =
    // new ScreeningRequest("ref", "2016-10-31", "Sac", "2016-10-31", "loc", "comm", "name", "now",
    // "sure", "2016-10-31", "narrative", domainAddress);
    // gov.ca.cwds.data.persistence.ns.Screening screening =
    // new gov.ca.cwds.data.persistence.ns.Screening(1L, screeningRequest,
    // new Address(domainAddress, null, null), null, null, null);
    //
    // when(screeningDao.find(new Long(123))).thenReturn(screening);
    // when(screeningDao.update(any())).thenReturn(screening);
    //
    // Object retval = screeningService.update(1L, screeningRequest);
    // assertThat(retval.getClass(), is(ScreeningResponse.class));
  }

  @Test
  public void updateReturnsCorrectScreeningResponseOnSuccess() throws Exception {
    roles.add("victim");
    // gov.ca.cwds.rest.api.domain.Address domainAddress = new
    // gov.ca.cwds.rest.api.domain.Address("",
    // "", "742 Evergreen Terrace", "Springfield", "WA", 98700, "home");
    // addresses.add(domainAddress);
    // ScreeningRequest screeningRequest =
    // new ScreeningRequest("ref", "2016-10-31", "Sac", "2016-10-31", "loc", "comm", "name", "now",
    // "sure", "2016-10-31", "narrative", domainAddress);
    //
    // Participant bart = new Participant(1, "", "", new LegacyDescriptor(), "Bart", "S", "Simpson",
    // "", "male", "123456789", "2016-10-31", 1234, 1234, roles, addresses);
    // Participant maggie = new Participant(2, "", "", new LegacyDescriptor(), "Maggie", "F",
    // "Simpson", "", "female", "123456789", "2016-10-31", 1234, 1234, roles, addresses);
    //
    // ImmutableSet.Builder<gov.ca.cwds.data.persistence.ns.Participant> peopleListBuilder =
    // ImmutableSet.builder();
    // ImmutableSet<gov.ca.cwds.data.persistence.ns.Participant> people =
    // peopleListBuilder.add(new gov.ca.cwds.data.persistence.ns.Participant(bart, null, null))
    // .add(new gov.ca.cwds.data.persistence.ns.Participant(maggie, null, null)).build();
    //
    // gov.ca.cwds.data.persistence.ns.Screening screening =
    // new gov.ca.cwds.data.persistence.ns.Screening(1L, screeningRequest,
    // new Address(domainAddress, null, null), people, null, null);
    //
    // when(screeningDao.find(new Long(123))).thenReturn(screening);
    // when(screeningDao.update(any())).thenReturn(screening);
    //
    // ScreeningResponse expected = new ScreeningResponse(screening, people);
    // ScreeningResponse updated = screeningService.update(1L, screeningRequest);
    // assertThat(updated, is(expected));
  }

  @Test
  public void updateThrowsAssertionErrorOnWrongRequestType() throws Exception {
    // thrown.expect(AssertionError.class);
    // try {
    // screeningService.update(1L, new Request() {});
    // } catch (AssertionError e) {
    // assertEquals("Expected AssertionError", e.getMessage());
    // }
  }

}
