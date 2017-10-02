package gov.ca.cwds.rest.services.cms;

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

import java.util.Date;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import gov.ca.cwds.data.cms.GovernmentOrganizationCrossReportDao;
import gov.ca.cwds.fixture.GovernmentOrganizationCrossReportResourceBuilder;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.cms.GovernmentOrganizationCrossReport;
import gov.ca.cwds.rest.filters.TestingRequestExecutionContext;
import gov.ca.cwds.rest.services.ServiceException;

/**
 * @author CWDS API Team
 *
 */
@SuppressWarnings("javadoc")
public class GovernmentOrganizationCrossReportServiceTest {

  private GovernmentOrganizationCrossReportService governmentOrganizationCrossReportService;
  private GovernmentOrganizationCrossReportDao governmentOrganizationCrossReportDao;

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Before
  public void setup() throws Exception {
    new TestingRequestExecutionContext("0X5");
    governmentOrganizationCrossReportDao = mock(GovernmentOrganizationCrossReportDao.class);
    governmentOrganizationCrossReportService =
        new GovernmentOrganizationCrossReportService(governmentOrganizationCrossReportDao);
  }

  // find test
  @Test
  public void governmentOrganizationCrossReportServiceFindReturnsCorrectEntity() throws Exception {
    String thirdId = "AbalBln0Ki";
    GovernmentOrganizationCrossReport expected =
        new GovernmentOrganizationCrossReportResourceBuilder().build();
    gov.ca.cwds.data.persistence.cms.GovernmentOrganizationCrossReport governmentOrganizationCrossReport =
        new gov.ca.cwds.data.persistence.cms.GovernmentOrganizationCrossReport(thirdId, expected,
            "0XA", new Date());

    when(governmentOrganizationCrossReportDao.find(thirdId))
        .thenReturn(governmentOrganizationCrossReport);
    GovernmentOrganizationCrossReport found =
        governmentOrganizationCrossReportService.find(thirdId);
    assertThat(found, is(expected));
  }

  @Test
  public void governmentOrganizationCrossReportServiceFindReturnsNullWhenNotFound()
      throws Exception {
    Response found = governmentOrganizationCrossReportService.find("ABC1234567");
    assertThat(found, is(nullValue()));
  }

  // delete test
  @Test
  public void governmentOrganizationCrossReportServiceDeleteDelegatesToCrudsService() {
    governmentOrganizationCrossReportService.delete("ABC2345678");
    verify(governmentOrganizationCrossReportDao, times(1)).delete("ABC2345678");
  }

  @Test
  public void governmentOrganizationCrossReportServiceDeleteReturnsNullWhenNotFound()
      throws Exception {
    Response found = governmentOrganizationCrossReportService.delete("ABC1234567");
    assertThat(found, is(nullValue()));
  }

  @Test
  public void governmentOrganizationCrossReportServiceDeleteReturnsNotNull() throws Exception {
    String thirdId = "AbalBln0Ki";
    GovernmentOrganizationCrossReport expected =
        new GovernmentOrganizationCrossReportResourceBuilder().build();
    gov.ca.cwds.data.persistence.cms.GovernmentOrganizationCrossReport governmentOrganizationCrossReport =
        new gov.ca.cwds.data.persistence.cms.GovernmentOrganizationCrossReport(thirdId, expected,
            "0XA", new Date());

    when(governmentOrganizationCrossReportDao.delete(thirdId))
        .thenReturn(governmentOrganizationCrossReport);
    GovernmentOrganizationCrossReport found =
        governmentOrganizationCrossReportService.delete(thirdId);
    assertThat(found, is(expected));
  }

  // update test
  @Test
  public void governmentOrganizationCrossReportServiceUpdateReturnsCorrectEntity()
      throws Exception {
    String thirdId = "AbalBln0Ki";
    GovernmentOrganizationCrossReport expected =
        new GovernmentOrganizationCrossReportResourceBuilder().build();

    gov.ca.cwds.data.persistence.cms.GovernmentOrganizationCrossReport governmentOrganizationCrossReport =
        new gov.ca.cwds.data.persistence.cms.GovernmentOrganizationCrossReport(thirdId, expected,
            "0XA", new Date());

    when(governmentOrganizationCrossReportDao.find("AbalBln0Ki"))
        .thenReturn(governmentOrganizationCrossReport);
    when(governmentOrganizationCrossReportDao.update(any()))
        .thenReturn(governmentOrganizationCrossReport);

    Object retval = governmentOrganizationCrossReportService.update("ABC1234567", expected);
    assertThat(retval.getClass(), is(GovernmentOrganizationCrossReport.class));
  }

  @Test
  public void governmentOrganizationCrossReportServiceUpdateThrowsExceptionWhenNotFound()
      throws Exception {
    try {
      GovernmentOrganizationCrossReport GovernmentOrganizationCrossReportRequest =
          new GovernmentOrganizationCrossReportResourceBuilder().build();

      when(governmentOrganizationCrossReportDao.update(any()))
          .thenThrow(EntityNotFoundException.class);

      governmentOrganizationCrossReportService.update("ZZZZZZZZZZ",
          GovernmentOrganizationCrossReportRequest);
    } catch (Exception e) {
      assertEquals(e.getClass(), ServiceException.class);
    }
  }

  // create test
  @Test
  public void governmentOrganizationCrossReportServiceCreateReturnsPostedClass() throws Exception {
    String thirdId = "AbalBln0Ki";
    GovernmentOrganizationCrossReport governmentOrganizationCrossReportDomain =
        new GovernmentOrganizationCrossReportResourceBuilder().build();

    gov.ca.cwds.data.persistence.cms.GovernmentOrganizationCrossReport toCreate =
        new gov.ca.cwds.data.persistence.cms.GovernmentOrganizationCrossReport(thirdId,
            governmentOrganizationCrossReportDomain, "0XA", new Date());

    GovernmentOrganizationCrossReport request = new GovernmentOrganizationCrossReport(toCreate);
    when(governmentOrganizationCrossReportDao
        .create(any(gov.ca.cwds.data.persistence.cms.GovernmentOrganizationCrossReport.class)))
            .thenReturn(toCreate);

    Response response = governmentOrganizationCrossReportService.create(request);
    assertThat(response.getClass(), is(GovernmentOrganizationCrossReport.class));
  }

  @Test
  public void governmentOrganizationCrossReportServiceCreateReturnsNonNull() throws Exception {
    String thirdId = "AbalBln0Ki";
    GovernmentOrganizationCrossReport governmentOrganizationCrossReportDomain =
        new GovernmentOrganizationCrossReportResourceBuilder().build();

    gov.ca.cwds.data.persistence.cms.GovernmentOrganizationCrossReport toCreate =
        new gov.ca.cwds.data.persistence.cms.GovernmentOrganizationCrossReport(thirdId,
            governmentOrganizationCrossReportDomain, "0XA", new Date());

    GovernmentOrganizationCrossReport request = new GovernmentOrganizationCrossReport(toCreate);
    when(governmentOrganizationCrossReportDao
        .create(any(gov.ca.cwds.data.persistence.cms.GovernmentOrganizationCrossReport.class)))
            .thenReturn(toCreate);

    GovernmentOrganizationCrossReport postedtickle =
        governmentOrganizationCrossReportService.create(request);
    assertThat(postedtickle, is(notNullValue()));
  }

  @Test
  public void governmentOrganizationCrossReportServiceCreateReturnsCorrectEntity()
      throws Exception {
    String thirdId = "AbalBln0Ki";
    GovernmentOrganizationCrossReport governmentOrganizationCrossReportDomain =
        new GovernmentOrganizationCrossReportResourceBuilder().build();

    gov.ca.cwds.data.persistence.cms.GovernmentOrganizationCrossReport toCreate =
        new gov.ca.cwds.data.persistence.cms.GovernmentOrganizationCrossReport(thirdId,
            governmentOrganizationCrossReportDomain, "0XA", new Date());

    GovernmentOrganizationCrossReport request = new GovernmentOrganizationCrossReport(toCreate);
    when(governmentOrganizationCrossReportDao
        .create(any(gov.ca.cwds.data.persistence.cms.GovernmentOrganizationCrossReport.class)))
            .thenReturn(toCreate);

    GovernmentOrganizationCrossReport expected = new GovernmentOrganizationCrossReport(toCreate);
    GovernmentOrganizationCrossReport returned =
        governmentOrganizationCrossReportService.create(request);
    assertThat(returned, is(expected));
  }

  @Test
  public void governmentOrganizationCrossReportServiceCreateThrowsEntityExistsException()
      throws Exception {
    try {
      GovernmentOrganizationCrossReport governmentOrganizationCrossReportDomain =
          new GovernmentOrganizationCrossReportResourceBuilder().build();

      when(governmentOrganizationCrossReportDao.create(any()))
          .thenThrow(EntityExistsException.class);

      governmentOrganizationCrossReportService.create(governmentOrganizationCrossReportDomain);
    } catch (Exception e) {
      assertEquals(e.getClass(), ServiceException.class);
    }
  }

  @Test
  public void governmentOrganizationCrossReportServiceCreateNullIDError() throws Exception {
    try {
      GovernmentOrganizationCrossReport governmentOrganizationCrossReportDomain =
          new GovernmentOrganizationCrossReportResourceBuilder().build();

      gov.ca.cwds.data.persistence.cms.GovernmentOrganizationCrossReport toCreate =
          new gov.ca.cwds.data.persistence.cms.GovernmentOrganizationCrossReport(null,
              governmentOrganizationCrossReportDomain, "0XA", new Date());

      when(governmentOrganizationCrossReportDao
          .create(any(gov.ca.cwds.data.persistence.cms.GovernmentOrganizationCrossReport.class)))
              .thenReturn(toCreate);

    } catch (ServiceException e) {
      assertEquals("GovernmentOrganizationCrossReport ID cannot be blank", e.getMessage());
    }

  }

  @Test
  public void governmentOrganizationCrossReportServiceCreateBlankIDError() throws Exception {
    try {
      GovernmentOrganizationCrossReport governmentOrganizationCrossReportDomain =
          new GovernmentOrganizationCrossReportResourceBuilder().build();

      gov.ca.cwds.data.persistence.cms.GovernmentOrganizationCrossReport toCreate =
          new gov.ca.cwds.data.persistence.cms.GovernmentOrganizationCrossReport(" ",
              governmentOrganizationCrossReportDomain, "0XA", new Date());

      when(governmentOrganizationCrossReportDao
          .create(any(gov.ca.cwds.data.persistence.cms.GovernmentOrganizationCrossReport.class)))
              .thenReturn(toCreate);

    } catch (ServiceException e) {
      assertEquals("GovernmentOrganizationCrossReport ID cannot be blank", e.getMessage());
    }

  }


}
