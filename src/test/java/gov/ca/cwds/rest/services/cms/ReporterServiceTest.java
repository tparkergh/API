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

import javax.persistence.EntityNotFoundException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.fasterxml.jackson.databind.ObjectMapper;

import gov.ca.cwds.data.cms.ReporterDao;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.cms.PostedReporter;
import gov.ca.cwds.rest.api.domain.cms.Reporter;
import gov.ca.cwds.rest.services.ServiceException;
import io.dropwizard.jackson.Jackson;

/**
 * @author CWDS API Team
 *
 */
public class ReporterServiceTest {
  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();
  private ReporterService reporterService;
  private ReporterDao reporterDao;

  @SuppressWarnings("javadoc")
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @SuppressWarnings("javadoc")
  @Before
  public void setup() throws Exception {
    reporterDao = mock(ReporterDao.class);
    reporterService = new ReporterService(reporterDao);
  }

  // find test
  @SuppressWarnings("javadoc")
  @Test
  public void findThrowsAssertionError() {
    // service is expecting a String as primary key
    thrown.expect(AssertionError.class);
    try {
      reporterService.find(1);
    } catch (AssertionError e) {
      assertEquals("Expected AssertionError", e.getMessage());
    }
  }

  @SuppressWarnings("javadoc")
  @Test
  public void findReturnsCorrectReporterWhenFound() throws Exception {
    Reporter expected = MAPPER
        .readValue(fixture("fixtures/domain/legacy/Reporter/valid/valid.json"), Reporter.class);
    gov.ca.cwds.data.persistence.cms.Reporter reporter =
        new gov.ca.cwds.data.persistence.cms.Reporter(expected, "0XA");

    when(reporterDao.find("AbiQCgu0Hj")).thenReturn(reporter);
    Reporter found = reporterService.find("AbiQCgu0Hj");
    assertThat(found, is(expected));
  }

  @SuppressWarnings("javadoc")
  @Test
  public void findReturnsNullWhenNotFound() throws Exception {
    Response found = reporterService.find("ABC1234567");
    assertThat(found, is(nullValue()));
  }

  // delete test
  @SuppressWarnings("javadoc")
  @Test
  public void deleteThrowsAssersionError() throws Exception {
    // service is expecting a String as primary key
    thrown.expect(AssertionError.class);
    try {
      reporterService.delete(1);
    } catch (AssertionError e) {
      assertEquals("Expected AssertionError", e.getMessage());
    }
  }

  @SuppressWarnings("javadoc")
  @Test
  public void deleteDelegatesToCrudsService() {
    reporterService.delete("ABC2345678");
    verify(reporterDao, times(1)).delete("ABC2345678");
  }

  @SuppressWarnings("javadoc")
  @Test
  public void deleteReturnsNullWhenNotFount() throws Exception {
    Response found = reporterService.delete("ABC1234567");
    assertThat(found, is(nullValue()));
  }

  // update test
  @SuppressWarnings("javadoc")
  @Test
  public void updateThrowsAssertionError() throws Exception {
    // service expecting domain Reporter object
    thrown.expect(AssertionError.class);
    try {
      reporterService.update("ABC1234567", null);
    } catch (AssertionError e) {
      assertEquals("Expected AssertionError", e.getMessage());
    }
  }

  @SuppressWarnings("javadoc")
  @Test
  public void updateReturnsReporterResponseOnSuccess() throws Exception {
    Reporter expected = MAPPER
        .readValue(fixture("fixtures/domain/legacy/Reporter/valid/valid.json"), Reporter.class);

    gov.ca.cwds.data.persistence.cms.Reporter reporter =
        new gov.ca.cwds.data.persistence.cms.Reporter(expected, "ABC");

    when(reporterDao.find("ABC1234567")).thenReturn(reporter);
    when(reporterDao.update(any())).thenReturn(reporter);

    Object retval = reporterService.update("ABC1234567", expected);
    assertThat(retval.getClass(), is(Reporter.class));
  }

  @SuppressWarnings("javadoc")
  @Test
  public void updateThrowsExceptionWhenReporterNotFound() throws Exception {

    try {
      Reporter reporterRequest = MAPPER
          .readValue(fixture("fixtures/domain/legacy/Reporter/valid/valid.json"), Reporter.class);

      when(reporterDao.update(any())).thenThrow(EntityNotFoundException.class);

      reporterService.update("ZZZZZZZZZZ", reporterRequest);
      Assert.fail("Expected ServiceException was not thrown");
    } catch (Exception ex) {
      assertEquals(ex.getClass(), ServiceException.class);
    }
  }

  // create test
  @SuppressWarnings("javadoc")
  @Test
  public void createThrowsAssertionError() throws Exception {
    // service expecting domain Reporter object - test when null passed to service
    thrown.expect(AssertionError.class);
    try {
      reporterService.create(null);
    } catch (AssertionError e) {
      assertEquals("Expected AssertionError", e.getMessage());
    }
  }

  @SuppressWarnings("javadoc")
  @Test
  public void createReturnsPostedReporterClass() throws Exception {
    Reporter reporterDomain = MAPPER
        .readValue(fixture("fixtures/domain/legacy/Reporter/valid/valid.json"), Reporter.class);
    gov.ca.cwds.data.persistence.cms.Reporter toCreate =
        new gov.ca.cwds.data.persistence.cms.Reporter(reporterDomain, "last_update");

    Reporter request = new Reporter(toCreate);

    when(reporterDao.create(any(gov.ca.cwds.data.persistence.cms.Reporter.class)))
        .thenReturn(toCreate);

    Response response = reporterService.create(request);

    assertThat(response.getClass(), is(PostedReporter.class));
  }

  @SuppressWarnings("javadoc")
  @Test
  public void createReturnsNonNull() throws Exception {
    Reporter reporterDomain = MAPPER
        .readValue(fixture("fixtures/domain/legacy/Reporter/valid/valid.json"), Reporter.class);
    gov.ca.cwds.data.persistence.cms.Reporter toCreate =
        new gov.ca.cwds.data.persistence.cms.Reporter(reporterDomain, "last_update");

    Reporter request = new Reporter(toCreate);

    when(reporterDao.create(any(gov.ca.cwds.data.persistence.cms.Reporter.class)))
        .thenReturn(toCreate);

    PostedReporter postedReporter = reporterService.create(request);

    assertThat(postedReporter, is(notNullValue()));
  }

  @SuppressWarnings("javadoc")
  @Test
  public void createReturnsCorrectPostedPerson() throws Exception {
    Reporter reporterDomain = MAPPER
        .readValue(fixture("fixtures/domain/legacy/Reporter/valid/valid.json"), Reporter.class);
    gov.ca.cwds.data.persistence.cms.Reporter toCreate =
        new gov.ca.cwds.data.persistence.cms.Reporter(reporterDomain, "last_update");

    Reporter request = new Reporter(toCreate);

    when(reporterDao.create(any(gov.ca.cwds.data.persistence.cms.Reporter.class)))
        .thenReturn(toCreate);

    PostedReporter expected = new PostedReporter(toCreate);

    PostedReporter returned = reporterService.create(request);

    assertThat(returned, is(expected));
  }

  @Test
  public void failsWhenPostedReporterServiceEmpty() throws Exception {
    try {
      Reporter reporterDomain = MAPPER.readValue(
          fixture("fixtures/domain/legacy/Reporter/invalid/referralIdEmpty.json"), Reporter.class);

      gov.ca.cwds.data.persistence.cms.Reporter toCreate =
          new gov.ca.cwds.data.persistence.cms.Reporter(reporterDomain, "last_update");

      when(reporterDao.create(any(gov.ca.cwds.data.persistence.cms.Reporter.class)))
          .thenReturn(toCreate);

      PostedReporter expected = new PostedReporter(toCreate);
      Assert.fail("Expected ServiceException was not thrown");
    } catch (ServiceException e) {
      assertEquals("Reporter ID cannot be empty", e.getMessage());
    }

  }

  @SuppressWarnings("javadoc")
  @Test
  public void failsWhenPostedReporterServiceNull() throws Exception {
    try {
      Reporter reporterDomain = MAPPER.readValue(
          fixture("fixtures/domain/legacy/Reporter/invalid/referralIdNull.json"), Reporter.class);
      gov.ca.cwds.data.persistence.cms.Reporter toCreate =
          new gov.ca.cwds.data.persistence.cms.Reporter(reporterDomain, "last_update");

      when(reporterDao.create(any(gov.ca.cwds.data.persistence.cms.Reporter.class)))
          .thenReturn(toCreate);
      PostedReporter expected = new PostedReporter(toCreate);
      Assert.fail("Expected ServiceException was not thrown");


    } catch (ServiceException e) {
      assertEquals("Reporter ID cannot be empty", e.getMessage());
    } catch (NullPointerException ex) {
      assertEquals(ex.getClass(), NullPointerException.class);
    }

  }
}
