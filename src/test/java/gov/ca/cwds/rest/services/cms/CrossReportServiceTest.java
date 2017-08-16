package gov.ca.cwds.rest.services.cms;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
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

import gov.ca.cwds.data.cms.CrossReportDao;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.cms.CrossReport;
import gov.ca.cwds.rest.services.ServiceException;
import gov.ca.cwds.rest.services.referentialintegrity.RICrossReport;
import io.dropwizard.jackson.Jackson;

/**
 * @author CWDS API Team
 *
 */
public class CrossReportServiceTest {
  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();
  private CrossReportService crossReportService;
  private CrossReportDao crossReportDao;
  private StaffPersonIdRetriever staffPersonIdRetriever;
  private RICrossReport riCrossReport;

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @SuppressWarnings("javadoc")
  @Before
  public void setup() throws Exception {
    crossReportDao = mock(CrossReportDao.class);
    staffPersonIdRetriever = mock(StaffPersonIdRetriever.class);
    crossReportService =
        new CrossReportService(crossReportDao, staffPersonIdRetriever, riCrossReport);
  }

  // find test
  // TODO: Story #136701343: Tech debt: exception handling in service layer.
  @SuppressWarnings("javadoc")
  @Test
  public void findThrowsAssertionError() {
    thrown.expect(AssertionError.class);
    try {
      crossReportService.find(1);
    } catch (AssertionError e) {
      assertEquals("Expected AssertionError", e.getMessage());

    }
  }

  @SuppressWarnings("javadoc")
  @Test
  public void findReturnsCorrectCrossReportWhenFound() throws Exception {
    CrossReport expected = MAPPER.readValue(
        fixture("fixtures/domain/legacy/CrossReport/valid/valid.json"), CrossReport.class);

    gov.ca.cwds.data.persistence.cms.CrossReport crossReport =
        new gov.ca.cwds.data.persistence.cms.CrossReport(expected.getThirdId(), expected, "0X5");
    when(crossReportDao.find(eq(expected.getThirdId()))).thenReturn(crossReport);
    CrossReport found = crossReportService.find("ABC1234567");
    assertThat(found, is(expected));
  }

  @SuppressWarnings("javadoc")
  @Test
  public void findReturnsNullWhenNotFound() throws Exception {
    Response found = crossReportService.find("0X51234567");
    assertThat(found, is(nullValue()));
  }

  // delete test
  @SuppressWarnings("javadoc")
  @Test
  public void deleteThrowsAssersionError() throws Exception {
    thrown.expect(AssertionError.class);
    try {
      crossReportService.delete(1);
    } catch (AssertionError e) {
      assertEquals("Expected AssertionError", e.getMessage());
    }
  }

  @SuppressWarnings("javadoc")
  @Test
  public void deleteDelegatesToCrudsService() {
    crossReportService.delete("ABC2345678");
    verify(crossReportDao, times(1)).delete("ABC2345678");
  }

  @SuppressWarnings("javadoc")
  @Test
  public void deleteReturnsNullWhenNotFount() throws Exception {
    Response found = crossReportService.delete("ABC1234567");
    assertThat(found, is(nullValue()));
  }

  @SuppressWarnings("javadoc")
  @Test
  public void crossReportServiceDeleteReturnsNotNull() throws Exception {
    String thirdId = "ABC1234567";
    CrossReport expected = MAPPER.readValue(
        fixture("fixtures/domain/legacy/CrossReport/valid/valid.json"), CrossReport.class);
    gov.ca.cwds.data.persistence.cms.CrossReport crossReport =
        new gov.ca.cwds.data.persistence.cms.CrossReport(thirdId, expected, "0XA");

    when(crossReportDao.delete(thirdId)).thenReturn(crossReport);
    CrossReport found = crossReportService.delete(thirdId);
    assertThat(found, is(expected));
  }

  // update test
  @SuppressWarnings("javadoc")
  @Test
  public void updateThrowsAssertionError() throws Exception {
    thrown.expect(AssertionError.class);
    try {
      crossReportService.update("xxx", null);
    } catch (AssertionError e) {
      assertEquals("Expected AssertionError", e.getMessage());
    }
  }

  @SuppressWarnings("javadoc")
  @Test
  public void updateReturnsCrossReportResponseOnSuccess() throws Exception {
    CrossReport expected = MAPPER.readValue(
        fixture("fixtures/domain/legacy/CrossReport/valid/valid.json"), CrossReport.class);

    gov.ca.cwds.data.persistence.cms.CrossReport crossReport =
        new gov.ca.cwds.data.persistence.cms.CrossReport(expected.getThirdId(), expected, "ABC");

    when(crossReportDao.find("ABC1234567")).thenReturn(crossReport);
    when(crossReportDao.update(any())).thenReturn(crossReport);
    Object retval = crossReportService.update("ABC1234567", expected);
    assertThat(retval.getClass(), is(CrossReport.class));
  }

  @SuppressWarnings("javadoc")
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

  @SuppressWarnings("javadoc")
  @Test
  public void updateThrowsExceptionWhenCrossReportNotFound() throws Exception {

    try {
      CrossReport crossReportRequest = MAPPER.readValue(
          fixture("fixtures/domain/legacy/CrossReport/valid/valid.json"), CrossReport.class);

      when(crossReportDao.update(any())).thenThrow(EntityNotFoundException.class);

      crossReportService.update("ZZZ1234567", crossReportRequest);
    } catch (Exception e) {
      assertEquals(e.getClass(), ServiceException.class);
    }
  }

  // create test
  @SuppressWarnings("javadoc")
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

  @SuppressWarnings("javadoc")
  @Test
  public void crossReportServiceCreateThrowsEntityExistsException() throws Exception {
    try {
      CrossReport crossReportRequest = MAPPER.readValue(
          fixture("fixtures/domain/legacy/CrossReport/valid/valid.json"), CrossReport.class);

      when(crossReportDao.create(any())).thenThrow(EntityExistsException.class);

      crossReportService.create(crossReportRequest);
    } catch (Exception e) {
      assertEquals(e.getClass(), ServiceException.class);
    }
  }

  @SuppressWarnings("javadoc")
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

  @SuppressWarnings("javadoc")
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

  /*
   * Test for checking the new thirdId Generated for crossReport
   */
  @SuppressWarnings("javadoc")
  @Test
  public void createReturnsGeneratedThirdId() throws Exception {
    CrossReport crossReportDomain = MAPPER.readValue(
        fixture("fixtures/domain/legacy/CrossReport/valid/valid.json"), CrossReport.class);
    when(crossReportDao.create(any(gov.ca.cwds.data.persistence.cms.CrossReport.class)))
        .thenAnswer(new Answer<gov.ca.cwds.data.persistence.cms.CrossReport>() {

          @Override
          public gov.ca.cwds.data.persistence.cms.CrossReport answer(InvocationOnMock invocation)
              throws Throwable {
            gov.ca.cwds.data.persistence.cms.CrossReport report =
                (gov.ca.cwds.data.persistence.cms.CrossReport) invocation.getArguments()[0];
            return report;
          }
        });

    CrossReport returned = crossReportService.create(crossReportDomain);
    Assert.assertNotEquals(returned.getThirdId(), crossReportDomain.getThirdId());
  }
}
