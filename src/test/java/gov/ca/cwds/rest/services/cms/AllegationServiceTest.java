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

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.fasterxml.jackson.databind.ObjectMapper;

import gov.ca.cwds.data.cms.AllegationDao;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.cms.Allegation;
import gov.ca.cwds.rest.api.domain.cms.PostedAllegation;
import gov.ca.cwds.rest.services.ServiceException;
import gov.ca.cwds.rest.services.junit.template.ServiceTestTemplate;
import io.dropwizard.jackson.Jackson;

/**
 * @author CWDS API Team
 *
 */
public class AllegationServiceTest implements ServiceTestTemplate {
  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();
  private AllegationService allegationService;
  private AllegationDao allegationDao;
  private StaffPersonIdRetriever staffPersonIdRetriever;

  @SuppressWarnings("javadoc")
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Override
  @Before
  public void setup() throws Exception {
    allegationDao = mock(AllegationDao.class);
    staffPersonIdRetriever = mock(StaffPersonIdRetriever.class);
    allegationService = new AllegationService(allegationDao, staffPersonIdRetriever);
  }

  // find test
  @Override
  @Test
  public void testFindThrowsAssertionError() {
    // expect string type for primary key test
    thrown.expect(AssertionError.class);
    try {
      allegationService.find(1);
    } catch (AssertionError e) {
      assertEquals("Expeceted AssertionError", e.getMessage());
    }
  }

  @Override
  @Test
  public void testFindReturnsCorrectEntity() throws Exception {
    String id = "Aaeae9r0F4";
    Allegation expected = MAPPER
        .readValue(fixture("fixtures/domain/legacy/Allegation/valid/valid.json"), Allegation.class);
    gov.ca.cwds.data.persistence.cms.Allegation allegation =
        new gov.ca.cwds.data.persistence.cms.Allegation(id, expected, "0XA");

    when(allegationDao.find(id)).thenReturn(allegation);
    Allegation found = allegationService.find(id);
    assertThat(found, is(expected));
  }

  @Override
  @Test
  public void testFindReturnsNullWhenNotFound() throws Exception {
    Response found = allegationService.find("ABC1234567");
    assertThat(found, is(nullValue()));
  }

  @Override
  @Test
  // delete test
  public void testDeleteThrowsAssertionError() throws Exception {
    // expect string type for primary key test
    thrown.expect(AssertionError.class);
    try {
      allegationService.delete(123);
    } catch (AssertionError e) {
      assertEquals("Expected AssertionError", e.getMessage());
    }
  }

  @Override
  @Test
  public void testDeleteDelegatesToCrudsService() {
    allegationService.delete("ABC2345678");
    verify(allegationDao, times(1)).delete("ABC2345678");
  }

  @Override
  @Test
  public void testDeleteReturnsNullWhenNotFound() throws Exception {
    Response found = allegationService.delete("ABC1234567");
    assertThat(found, is(nullValue()));
  }

  @SuppressWarnings("javadoc")
  @Test
  public void assignmentServiceDeleteReturnsNotNull() throws Exception {
    String id = "AabekZX00F";
    Allegation expected = MAPPER
        .readValue(fixture("fixtures/domain/legacy/Allegation/valid/valid.json"), Allegation.class);
    gov.ca.cwds.data.persistence.cms.Allegation allegation =
        new gov.ca.cwds.data.persistence.cms.Allegation(id, expected, "0XA");

    when(allegationDao.delete(id)).thenReturn(allegation);
    Allegation found = allegationService.delete(id);
    assertThat(found, is(expected));
  }

  @Override
  public void testDeleteThrowsNotImplementedException() throws Exception {
    // delete is implemented

  }

  @Override
  public void testDeleteReturnsClass() throws Exception {

  }

  // update test
  @Override
  @Test
  public void testUpdateThrowsAssertionError() throws Exception {
    // expected string type for primary key test
    thrown.expect(AssertionError.class);
    try {
      allegationService.update("ABC1234567", null);
    } catch (AssertionError e) {
      assertEquals("Expected AssertionError", e.getMessage());
    }
  }

  @Override
  @Test
  public void testUpdateReturnsCorrectEntity() throws Exception {
    String id = "Aaeae9r0F4";
    Allegation expected = MAPPER
        .readValue(fixture("fixtures/domain/legacy/Allegation/valid/valid.json"), Allegation.class);

    gov.ca.cwds.data.persistence.cms.Allegation allegation =
        new gov.ca.cwds.data.persistence.cms.Allegation(id, expected, "ABC");

    when(allegationDao.find("ABC1234567")).thenReturn(allegation);
    when(allegationDao.update(any())).thenReturn(allegation);

    Object retval = allegationService.update("ABC1234567", expected);
    assertThat(retval.getClass(), is(Allegation.class));
  }

  @SuppressWarnings("javadoc")
  @Test
  public void testUpdateThrowsExceptionWhenNotFound() throws Exception {
    try {
      Allegation allegationRequest = MAPPER.readValue(
          fixture("fixtures/domain/legacy/Allegation/valid/valid.json"), Allegation.class);

      when(allegationDao.update(any())).thenThrow(EntityNotFoundException.class);

      allegationService.update("ZZZZZZZZZZ", allegationRequest);
    } catch (Exception e) {
      assertEquals(e.getClass(), ServiceException.class);
    }
  }

  @Override
  public void testUpdateReturnsDomain() throws Exception {

  }

  @Override
  public void testUpdateThrowsServiceException() throws Exception {

  }

  @Override
  public void testUpdateThrowsNotImplementedException() throws Exception {

  }

  // create test
  @Override
  @Test
  public void testCreateReturnsPostedClass() throws Exception {
    String id = "Aaeae9r0F4";
    Allegation allegationDomain = MAPPER
        .readValue(fixture("fixtures/domain/legacy/Allegation/valid/valid.json"), Allegation.class);
    gov.ca.cwds.data.persistence.cms.Allegation toCreate =
        new gov.ca.cwds.data.persistence.cms.Allegation(id, allegationDomain, "ABC");

    Allegation request = new Allegation(toCreate);
    when(allegationDao.create(any(gov.ca.cwds.data.persistence.cms.Allegation.class)))
        .thenReturn(toCreate);

    Response response = allegationService.create(request);
    assertThat(response.getClass(), is(PostedAllegation.class));
  }

  @SuppressWarnings("javadoc")
  @Test
  public void allegationServiceServiceCreateThrowsEntityExistsException() throws Exception {
    try {
      Allegation allegationRequest = MAPPER.readValue(
          fixture("fixtures/domain/legacy/Allegation/valid/valid.json"), Allegation.class);

      when(allegationDao.create(any())).thenThrow(EntityExistsException.class);
      allegationService.create(allegationRequest);
    } catch (Exception e) {
      assertEquals(e.getClass(), ServiceException.class);
    }
  }

  @SuppressWarnings("javadoc")
  @Test
  public void testCreateReturnsNonNull() throws Exception {
    String id = "Aaeae9r0F4";
    Allegation allegationDomain = MAPPER
        .readValue(fixture("fixtures/domain/legacy/Allegation/valid/valid.json"), Allegation.class);
    gov.ca.cwds.data.persistence.cms.Allegation toCreate =
        new gov.ca.cwds.data.persistence.cms.Allegation(id, allegationDomain, "ABC");

    Allegation request = new Allegation(toCreate);
    when(allegationDao.create(any(gov.ca.cwds.data.persistence.cms.Allegation.class)))
        .thenReturn(toCreate);

    PostedAllegation postedAllegation = allegationService.create(request);
    assertThat(postedAllegation, is(notNullValue()));
  }

  @Override
  @Test
  public void testCreateReturnsCorrectEntity() throws Exception {
    String id = "Aaeae9r0F4";
    Allegation allegationDomain = MAPPER
        .readValue(fixture("fixtures/domain/legacy/Allegation/valid/valid.json"), Allegation.class);
    gov.ca.cwds.data.persistence.cms.Allegation toCreate =
        new gov.ca.cwds.data.persistence.cms.Allegation(id, allegationDomain, "ABC");

    Allegation request = new Allegation(toCreate);
    when(allegationDao.create(any(gov.ca.cwds.data.persistence.cms.Allegation.class)))
        .thenReturn(toCreate);

    PostedAllegation expected = new PostedAllegation(toCreate);
    PostedAllegation returned = allegationService.create(request);
    assertThat(returned, is(expected));
  }

  @Override
  @Test
  public void testCreateNullIDError() throws Exception {
    try {
      Allegation allegationDomain = MAPPER.readValue(
          fixture("fixtures/domain/legacy/Allegation/valid/valid.json"), Allegation.class);
      gov.ca.cwds.data.persistence.cms.Allegation toCreate =
          new gov.ca.cwds.data.persistence.cms.Allegation(null, allegationDomain, "ABC");

      when(allegationDao.create(any(gov.ca.cwds.data.persistence.cms.Allegation.class)))
          .thenReturn(toCreate);

      PostedAllegation expected = new PostedAllegation(toCreate);
    } catch (ServiceException e) {
      assertEquals("Allegation ID cannot be blank", e.getMessage());
    }

  }

  @Override
  @Test
  public void testCreateBlankIDError() throws Exception {
    try {
      Allegation allegationDomain = MAPPER.readValue(
          fixture("fixtures/domain/legacy/Allegation/valid/valid.json"), Allegation.class);
      gov.ca.cwds.data.persistence.cms.Allegation toCreate =
          new gov.ca.cwds.data.persistence.cms.Allegation("    ", allegationDomain, "ABC");

      when(allegationDao.create(any(gov.ca.cwds.data.persistence.cms.Allegation.class)))
          .thenReturn(toCreate);

      PostedAllegation expected = new PostedAllegation(toCreate);
    } catch (ServiceException e) {
      assertEquals("Allegation ID cannot be blank", e.getMessage());
    }

  }

  /*
   * Test for checking the new Allegation Id generated and lenght is 10
   */
  @SuppressWarnings("javadoc")
  @Test
  public void createReturnsGeneratedId() throws Exception {
    Allegation allegationDomain = MAPPER
        .readValue(fixture("fixtures/domain/legacy/Allegation/valid/valid.json"), Allegation.class);
    when(allegationDao.create(any(gov.ca.cwds.data.persistence.cms.Allegation.class)))
        .thenAnswer(new Answer<gov.ca.cwds.data.persistence.cms.Allegation>() {

          @Override
          public gov.ca.cwds.data.persistence.cms.Allegation answer(InvocationOnMock invocation)
              throws Throwable {
            gov.ca.cwds.data.persistence.cms.Allegation report =
                (gov.ca.cwds.data.persistence.cms.Allegation) invocation.getArguments()[0];
            return report;
          }
        });

    PostedAllegation returned = allegationService.create(allegationDomain);
    assertEquals(returned.getId().length(), 10);
    PostedAllegation newReturned = allegationService.create(allegationDomain);
    Assert.assertNotEquals(returned.getId(), newReturned.getId());
  }

  @Override
  public void testCreateThrowsAssertionError() throws Exception {

  }

  @Override
  public void testCreateEmptyIDError() throws Exception {

  }

  @Override
  public void testCreateThrowsNotImplementedException() throws Exception {

  }

  @Override
  public void testFindThrowsNotImplementedException() throws Exception {

  }

}
