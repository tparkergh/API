package gov.ca.cwds.rest.services.cms;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
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

import gov.ca.cwds.data.cms.CrossReportDao;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.cms.CrossReport;
import io.dropwizard.jackson.Jackson;

public class CrossReportServiceTest {
  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();
  private CrossReportService crossReportService;
  private CrossReportDao crossReportDao;

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Before
  public void setup() throws Exception {
    crossReportDao = mock(CrossReportDao.class);
    crossReportService = new CrossReportService(crossReportDao);
  }

  // find test
  // TODO: Story #136701343: Tech debt: exception handling in service layer.
  @Test
  public void findThrowsAssertionError() {
    // TODO : thrown.expect not working on AssertionError???? WHY???
    // thrown.expect(AssertionError.class);
    try {
      crossReportService.find(1);
      Assert.fail("Expected AssertionError");
    } catch (AssertionError e) {

    }
  }

  @Test
  public void findReturnsCorrectCrossReportWhenFound() throws Exception {
    CrossReport expected = MAPPER.readValue(
        fixture("fixtures/domain/legacy/CrossReport/valid/valid.json"), CrossReport.class);

    gov.ca.cwds.data.persistence.cms.CrossReport crossReport =
        new gov.ca.cwds.data.persistence.cms.CrossReport(expected.getThirdId(), expected,
            "0X5");
    when(crossReportDao.find(eq(expected.getThirdId()))).thenReturn(crossReport);
    CrossReport found = crossReportService.find("ABC1234567");
    assertThat(found, is(expected));
  }

  @Test
  public void findReturnsNullWhenNotFound() throws Exception {
    Response found = crossReportService.find("0X51234567");
    assertThat(found, is(nullValue()));
  }

  // delete test
  public void deleteThrowsAssersionError() throws Exception {
    // TODO : thrown.expect not working on AssertionError???? WHY???
    // thrown.expect(AssertionError.class);
    try {
      crossReportService.delete("1");
      Assert.fail("Expected AssertionError");
    } catch (AssertionError e) {
    }
  }

  @Test
  public void deleteDelegatesToCrudsService() {
    crossReportService.delete("ABC2345678");
    verify(crossReportDao, times(1)).delete("ABC2345678");
  }

  @Test
  public void deleteReturnsNullWhenNotFount() throws Exception {
    Response found = crossReportService.delete("ABC1234567");
    assertThat(found, is(nullValue()));
  }

  // update test
  @Test
  public void updateThrowsAssertionError() throws Exception {
    // TODO: thrown.expect not working on AssertionError???? WHY???
    // thrown.expect(AssertionError.class);
    try {
      crossReportService.update("xxx", null);
      Assert.fail("Expected AssertionError");
    } catch (AssertionError e) {
    }
  }

  @Test
  public void updateReturnsCrossReportResponseOnSuccess() throws Exception {
    CrossReport expected = MAPPER.readValue(
        fixture("fixtures/domain/legacy/CrossReport/valid/valid.json"), CrossReport.class);

    gov.ca.cwds.data.persistence.cms.CrossReport crossReport =
        new gov.ca.cwds.data.persistence.cms.CrossReport(expected.getThirdId(), expected,
            "ABC");

    when(crossReportDao.find("ABC1234567")).thenReturn(crossReport);
    when(crossReportDao.update(any())).thenReturn(crossReport);
    Object retval = crossReportService.update("ABC1234567", expected);
    assertThat(retval.getClass(), is(CrossReport.class));
  }

  @Test
  public void updateReturnsCorrectCrossReportOnSuccess() throws Exception {
    CrossReport crossReportRequest = MAPPER.readValue(
        fixture("fixtures/domain/legacy/CrossReport/valid/valid.json"), CrossReport.class);

    gov.ca.cwds.data.persistence.cms.CrossReport crossReport =
        new gov.ca.cwds.data.persistence.cms.CrossReport(crossReportRequest.getThirdId(),
            crossReportRequest, "ABC");

    when(crossReportDao.find("ABC1234567")).thenReturn(crossReport);
    when(crossReportDao.update(any())).thenReturn(crossReport);

    CrossReport expected = new CrossReport(crossReport);
    CrossReport updated = crossReportService.update("ABC1234567", expected);
    assertThat(updated, is(expected));
  }

  @Test
  public void updateThrowsExceptionWhenCrossReportNotFound() throws Exception {
    // TODO: test does not throw exception from crossReportService.update method
    //
    // remove comments before running unit test
    //
    // thrown.expect(ServiceException.class);
    // thrown.expectCause(Is.isA(EntityNotFoundException.class));
    // thrown.expectMessage(contains("Expected test to throw"));

    CrossReport crossReportRequest = MAPPER.readValue(
        fixture("fixtures/domain/legacy/CrossReport/valid/valid.json"), CrossReport.class);

    gov.ca.cwds.data.persistence.cms.CrossReport crossReport =
        new gov.ca.cwds.data.persistence.cms.CrossReport(crossReportRequest.getThirdId(),
            crossReportRequest, "ABC");

    when(crossReportDao.find("ABC1234567")).thenReturn(crossReport);
    when(crossReportDao.update(any())).thenReturn(crossReport);
    crossReportService.update("ZZZ1234567", crossReportRequest);
  }

  // create test
  @Test
  public void createReturnsPostedCrossReportClass() throws Exception {
    CrossReport crossReportDomain = MAPPER.readValue(
        fixture("fixtures/domain/legacy/CrossReport/valid/valid.json"), CrossReport.class);
    gov.ca.cwds.data.persistence.cms.CrossReport toCreate =
        new gov.ca.cwds.data.persistence.cms.CrossReport(crossReportDomain.getThirdId(),
            crossReportDomain, "ABC");

    CrossReport request = new CrossReport(toCreate);
    when(crossReportDao.create(any(gov.ca.cwds.data.persistence.cms.CrossReport.class)))
        .thenReturn(toCreate);

    Response response = crossReportService.create(request);
    assertThat(response.getClass(), is(CrossReport.class));
  }

  @Test
  public void createReturnsNonNull() throws Exception {
    CrossReport crossReportDomain = MAPPER.readValue(
        fixture("fixtures/domain/legacy/CrossReport/valid/valid.json"), CrossReport.class);
    gov.ca.cwds.data.persistence.cms.CrossReport toCreate =
        new gov.ca.cwds.data.persistence.cms.CrossReport(crossReportDomain.getThirdId(),
            crossReportDomain, "ABC");

    CrossReport request = new CrossReport(toCreate);
    when(crossReportDao.create(any(gov.ca.cwds.data.persistence.cms.CrossReport.class)))
        .thenReturn(toCreate);

    CrossReport postedCrossReport = crossReportService.create(request);
    assertThat(postedCrossReport, is(notNullValue()));
  }

  @Test
  public void createReturnsCorrectPostedCrossReport() throws Exception {
    CrossReport crossReportDomain = MAPPER.readValue(
        fixture("fixtures/domain/legacy/CrossReport/valid/valid.json"), CrossReport.class);
    gov.ca.cwds.data.persistence.cms.CrossReport toCreate =
        new gov.ca.cwds.data.persistence.cms.CrossReport(crossReportDomain.getThirdId(),
            crossReportDomain, "ABC");

    CrossReport request = new CrossReport(toCreate);

    when(crossReportDao.create(any(gov.ca.cwds.data.persistence.cms.CrossReport.class)))
        .thenReturn(toCreate);

    CrossReport expected = new CrossReport(toCreate);
    CrossReport returned = crossReportService.create(request);
    assertThat(returned, is(expected));
  }
}
