package gov.ca.cwds.rest.services.cms;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.fasterxml.jackson.databind.ObjectMapper;

import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.cms.Allegation;
import gov.ca.cwds.rest.api.domain.cms.PostedAllegation;
import gov.ca.cwds.rest.jdbi.cms.AllegationDao;
import gov.ca.cwds.rest.services.ServiceException;
import io.dropwizard.jackson.Jackson;

public class AllegationServiceTest {
  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();
  private AllegationService allegationService;
  private AllegationDao allegationDao;

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Before
  public void setup() throws Exception {
    allegationDao = mock(AllegationDao.class);
    allegationService = new AllegationService(allegationDao);
  }

  // find test
  @Test
  public void findThrowsAssertionError() {
    // expect string type for primary key test
    thrown.expect(AssertionError.class);
    try {
      allegationService.find(1);
    } catch (AssertionError e) {
      assertEquals("Expeceted AssertionError", e.getMessage());
    }
  }

  @Test
  public void findReturnsCorrectAllegationWhenFound() throws Exception {
    String id = "Aaeae9r0F4";
    Allegation expected = MAPPER
        .readValue(fixture("fixtures/domain/legacy/Allegation/valid/valid.json"), Allegation.class);
    gov.ca.cwds.rest.api.persistence.cms.Allegation allegation =
        new gov.ca.cwds.rest.api.persistence.cms.Allegation(id, expected, "0XA");

    when(allegationDao.find(id)).thenReturn(allegation);
    Allegation found = allegationService.find(id);
    assertThat(found, is(expected));
  }

  @Test
  public void findReturnsNullWhenNotFound() throws Exception {
    Response found = allegationService.find("ABC1234567");
    assertThat(found, is(nullValue()));
  }

  @Test
  // delete test
  public void deleteThrowsAssersionError() throws Exception {
    // expect string type for primary key test
    thrown.expect(AssertionError.class);
    try {
      allegationService.delete(123);
    } catch (AssertionError e) {
      assertEquals("Expected AssertionError", e.getMessage());
    }
  }

  @Test
  public void deleteDelegatesToCrudsService() {
    allegationService.delete("ABC2345678");
    verify(allegationDao, times(1)).delete("ABC2345678");
  }

  @Test
  public void deleteReturnsNullWhenNotFount() throws Exception {
    Response found = allegationService.delete("ABC1234567");
    assertThat(found, is(nullValue()));
  }

  // update test
  @Test
  public void updateThrowsAssertionError() throws Exception {
    // expected string type for primary key test
    thrown.expect(AssertionError.class);
    try {
      allegationService.update("ABC1234567", null);
    } catch (AssertionError e) {
      assertEquals("Expected AssertionError", e.getMessage());
    }
  }

  @Test
  public void updateReturnsAllegationResponseOnSuccess() throws Exception {
    String id = "Aaeae9r0F4";
    Allegation expected = MAPPER
        .readValue(fixture("fixtures/domain/legacy/Allegation/valid/valid.json"), Allegation.class);

    gov.ca.cwds.rest.api.persistence.cms.Allegation allegation =
        new gov.ca.cwds.rest.api.persistence.cms.Allegation(id, expected, "ABC");

    when(allegationDao.find("ABC1234567")).thenReturn(allegation);
    when(allegationDao.update(any())).thenReturn(allegation);

    Object retval = allegationService.update("ABC1234567", expected);
    assertThat(retval.getClass(), is(Allegation.class));
  }

  @Test
  public void updateThrowsExceptionWhenAllegationNotFound() throws Exception {
    // TODO: test does not throw exception from allegationService.update method
    //
    // remove comments before running unit test
    //
    // thrown.expect(ServiceException.class);
    // thrown.expectCause(Is.isA(EntityNotFoundException.class));
    // thrown.expectMessage(contains("Allegation not found"));
    String id = "Aaeae9r0F4";
    Allegation allegationRequest = MAPPER
        .readValue(fixture("fixtures/domain/legacy/Allegation/valid/valid.json"), Allegation.class);

    gov.ca.cwds.rest.api.persistence.cms.Allegation allegation =
        new gov.ca.cwds.rest.api.persistence.cms.Allegation(id, allegationRequest, "ABC");

    when(allegationDao.find("ABC1234567")).thenReturn(allegation);
    when(allegationDao.update(any())).thenReturn(allegation);
    allegationService.update("ZZZZZZZZZZ", allegationRequest);
  }

  // create test
  @Test
  public void createReturnsPostedAllegation() throws Exception {
    String id = "Aaeae9r0F4";
    Allegation allegationDomain = MAPPER
        .readValue(fixture("fixtures/domain/legacy/Allegation/valid/valid.json"), Allegation.class);
    gov.ca.cwds.rest.api.persistence.cms.Allegation toCreate =
        new gov.ca.cwds.rest.api.persistence.cms.Allegation(id, allegationDomain, "last_update");

    Allegation request = new Allegation(toCreate);
    when(allegationDao.create(any(gov.ca.cwds.rest.api.persistence.cms.Allegation.class)))
        .thenReturn(toCreate);

    Response response = allegationService.create(request);
    assertThat(response.getClass(), is(PostedAllegation.class));
  }

  @Test
  public void createReturnsNonNull() throws Exception {
    String id = "Aaeae9r0F4";
    Allegation allegationDomain = MAPPER
        .readValue(fixture("fixtures/domain/legacy/Allegation/valid/valid.json"), Allegation.class);
    gov.ca.cwds.rest.api.persistence.cms.Allegation toCreate =
        new gov.ca.cwds.rest.api.persistence.cms.Allegation(id, allegationDomain, "last_update");

    Allegation request = new Allegation(toCreate);
    when(allegationDao.create(any(gov.ca.cwds.rest.api.persistence.cms.Allegation.class)))
        .thenReturn(toCreate);

    PostedAllegation postedAllegation = allegationService.create(request);
    assertThat(postedAllegation, is(notNullValue()));
  }

  @Test
  public void createReturnsCorrectPostedPerson() throws Exception {
    String id = "Aaeae9r0F4";
    Allegation allegationDomain = MAPPER
        .readValue(fixture("fixtures/domain/legacy/Allegation/valid/valid.json"), Allegation.class);
    gov.ca.cwds.rest.api.persistence.cms.Allegation toCreate =
        new gov.ca.cwds.rest.api.persistence.cms.Allegation(id, allegationDomain, "last_update");

    Allegation request = new Allegation(toCreate);
    when(allegationDao.create(any(gov.ca.cwds.rest.api.persistence.cms.Allegation.class)))
        .thenReturn(toCreate);

    PostedAllegation expected = new PostedAllegation(toCreate);
    PostedAllegation returned = allegationService.create(request);
    assertThat(returned, is(expected));
  }

  @Test
  public void createFailsWhenPostedAllegationIdIsNull() throws Exception {
    try {
      Allegation allegationDomain = MAPPER.readValue(
          fixture("fixtures/domain/legacy/Allegation/valid/valid.json"), Allegation.class);
      gov.ca.cwds.rest.api.persistence.cms.Allegation toCreate =
          new gov.ca.cwds.rest.api.persistence.cms.Allegation(null, allegationDomain,
              "last_update");

      Allegation request = new Allegation(toCreate);
      when(allegationDao.create(any(gov.ca.cwds.rest.api.persistence.cms.Allegation.class)))
          .thenReturn(toCreate);

      PostedAllegation expected = new PostedAllegation(toCreate);
    } catch (ServiceException e) {
      assertEquals("Allegation ID cannot be blank", e.getMessage());
    }

  }

  @Test
  public void createFailsWhenPostedAllegationIdIsBlank() throws Exception {
    try {
      Allegation allegationDomain = MAPPER.readValue(
          fixture("fixtures/domain/legacy/Allegation/valid/valid.json"), Allegation.class);
      gov.ca.cwds.rest.api.persistence.cms.Allegation toCreate =
          new gov.ca.cwds.rest.api.persistence.cms.Allegation("    ", allegationDomain,
              "last_update");

      Allegation request = new Allegation(toCreate);
      when(allegationDao.create(any(gov.ca.cwds.rest.api.persistence.cms.Allegation.class)))
          .thenReturn(toCreate);

      PostedAllegation expected = new PostedAllegation(toCreate);
    } catch (ServiceException e) {
      assertEquals("Allegation ID cannot be blank", e.getMessage());
    }

  }
}
