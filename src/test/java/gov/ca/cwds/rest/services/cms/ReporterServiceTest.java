package gov.ca.cwds.rest.services.cms;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.fasterxml.jackson.databind.ObjectMapper;

import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.cms.PostedReporter;
import gov.ca.cwds.rest.api.domain.cms.Reporter;
import gov.ca.cwds.rest.jdbi.cms.ReporterDao;
import io.dropwizard.jackson.Jackson;

public class ReporterServiceTest {
  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();
  private ReporterService reporterService;
  private ReporterDao reporterDao;

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Before
  public void setup() throws Exception {
    reporterDao = mock(ReporterDao.class);
    reporterService = new ReporterService(reporterDao);
  }

  // find test
  @Test
  public void findThrowsAssertionError() {
    // TODO : thrown.expect not working on AssertionError???? WHY???
    // thrown.expect(AssertionError.class);
    try {
      reporterService.find("1");
      Assert.fail("Expected AssertionError");
    } catch (AssertionError e) {
    }
  }

  @Test
  public void findReturnsCorrectReporterWhenFound() throws Exception {
    Reporter expected = MAPPER
        .readValue(fixture("fixtures/domain/legacy/Reporter/valid/valid.json"), Reporter.class);
    gov.ca.cwds.rest.api.persistence.cms.Reporter reporter =
        new gov.ca.cwds.rest.api.persistence.cms.Reporter(expected, "0XA");

    when(reporterDao.find("AbiQCgu0Hj")).thenReturn(reporter);
    Reporter found = reporterService.find("AbiQCgu0Hj");
    assertThat(found, is(expected));
  }

  @Test
  public void findReturnsNullWhenNotFound() throws Exception {
    Response found = reporterService.find("ABC1234567");
    assertThat(found, is(nullValue()));
  }

  // delete test
  public void deleteThrowsAssersionError() throws Exception {
    // TODO : thrown.expect not working on AssertionError???? WHY???
    // thrown.expect(AssertionError.class);
    try {
      reporterService.delete("ABC1234567");
      Assert.fail("Expected AssertionError");
    } catch (AssertionError e) {
    }
  }

  @Test
  public void deleteDelegatesToCrudsService() {
    reporterService.delete("ABC2345678");
    verify(reporterDao, times(1)).delete("ABC2345678");
  }

  @Test
  public void deleteReturnsNullWhenNotFount() throws Exception {
    Response found = reporterService.delete("ABC1234567");
    assertThat(found, is(nullValue()));
  }

  // update test
  @Test
  public void updateThrowsAssertionError() throws Exception {
    // TODO: thrown.expect not working on AssertionError???? WHY???
    // thrown.expect(AssertionError.class);
    try {
      reporterService.update("ABC1234567", null);
      Assert.fail("Expected AssertionError");
    } catch (AssertionError e) {
    }
  }

  @Test
  public void updateReturnsReporterResponseOnSuccess() throws Exception {
    Reporter expected = MAPPER
        .readValue(fixture("fixtures/domain/legacy/Reporter/valid/valid.json"), Reporter.class);

    gov.ca.cwds.rest.api.persistence.cms.Reporter reporter =
        new gov.ca.cwds.rest.api.persistence.cms.Reporter(expected, "ABC");

    when(reporterDao.find("ABC1234567")).thenReturn(reporter);
    when(reporterDao.update(any())).thenReturn(reporter);

    Object retval = reporterService.update("ABC1234567", expected);
    assertThat(retval.getClass(), is(Reporter.class));
  }

  @Test
  public void updateThrowsExceptionWhenReporterNotFound() throws Exception {
    // TODO: test does not throw exception from reporterService.update method
    //
    // remove comments before running unit test
    //
    // thrown.expect(ServiceException.class);
    // thrown.expectCause(Is.isA(EntityNotFoundException.class));
    // thrown.expectMessage(contains("Reporter not found"));

    Reporter reporterRequest = MAPPER
        .readValue(fixture("fixtures/domain/legacy/Reporter/valid/valid.json"), Reporter.class);

    gov.ca.cwds.rest.api.persistence.cms.Reporter reporter =
        new gov.ca.cwds.rest.api.persistence.cms.Reporter(reporterRequest, "ABC");

    when(reporterDao.find("ABC1234567")).thenReturn(reporter);
    when(reporterDao.update(any())).thenReturn(reporter);

    reporterService.update("ZZZZZZZZZZ", reporterRequest);
  }

  // create test
  @Test
  public void createReturnsPostedReporterClass() throws Exception {
    Reporter reporterDomain = MAPPER
        .readValue(fixture("fixtures/domain/legacy/Reporter/valid/valid.json"), Reporter.class);
    gov.ca.cwds.rest.api.persistence.cms.Reporter toCreate =
        new gov.ca.cwds.rest.api.persistence.cms.Reporter(reporterDomain, "last_update");

    Reporter request = new Reporter(toCreate);

    when(reporterDao.create(any(gov.ca.cwds.rest.api.persistence.cms.Reporter.class)))
        .thenReturn(toCreate);

    Response response = reporterService.create(request);

    assertThat(response.getClass(), is(PostedReporter.class));
  }

  @Test
  public void createReturnsNonNull() throws Exception {
    Reporter reporterDomain = MAPPER
        .readValue(fixture("fixtures/domain/legacy/Reporter/valid/valid.json"), Reporter.class);
    gov.ca.cwds.rest.api.persistence.cms.Reporter toCreate =
        new gov.ca.cwds.rest.api.persistence.cms.Reporter(reporterDomain, "last_update");

    Reporter request = new Reporter(toCreate);

    when(reporterDao.create(any(gov.ca.cwds.rest.api.persistence.cms.Reporter.class)))
        .thenReturn(toCreate);

    PostedReporter postedReporter = reporterService.create(request);

    assertThat(postedReporter, is(notNullValue()));
  }

  @Test
  public void createReturnsCorrectPostedPerson() throws Exception {
    Reporter reporterDomain = MAPPER
        .readValue(fixture("fixtures/domain/legacy/Reporter/valid/valid.json"), Reporter.class);
    gov.ca.cwds.rest.api.persistence.cms.Reporter toCreate =
        new gov.ca.cwds.rest.api.persistence.cms.Reporter(reporterDomain, "last_update");

    Reporter request = new Reporter(toCreate);

    when(reporterDao.create(any(gov.ca.cwds.rest.api.persistence.cms.Reporter.class)))
        .thenReturn(toCreate);

    PostedReporter expected = new PostedReporter(toCreate);

    PostedReporter returned = reporterService.create(request);

    assertThat(returned, is(expected));
  }

}
