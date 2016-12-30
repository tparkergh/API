package gov.ca.cwds.rest.services.cms;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.fasterxml.jackson.databind.ObjectMapper;

import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.cms.Allegation;
import gov.ca.cwds.rest.api.domain.cms.CmsReferral;
import gov.ca.cwds.rest.api.domain.cms.CrossReport;
import gov.ca.cwds.rest.api.domain.cms.PostedAllegation;
import gov.ca.cwds.rest.api.domain.cms.PostedCmsReferral;
import gov.ca.cwds.rest.api.domain.cms.PostedReferral;
import gov.ca.cwds.rest.api.domain.cms.PostedReporter;
import gov.ca.cwds.rest.api.domain.cms.Referral;
import gov.ca.cwds.rest.api.domain.cms.ReferralClient;
import gov.ca.cwds.rest.api.domain.cms.Reporter;
import gov.ca.cwds.rest.jdbi.cms.AllegationDao;
import gov.ca.cwds.rest.jdbi.cms.CrossReportDao;
import gov.ca.cwds.rest.jdbi.cms.ReferralClientDao;
import gov.ca.cwds.rest.jdbi.cms.ReferralDao;
import gov.ca.cwds.rest.jdbi.cms.ReporterDao;
import io.dropwizard.jackson.Jackson;

public class CmsReferralServiceTest {
  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();
  private CmsReferralService cmsReferralService;

  private ReferralService referralService;
  private ReferralClientService referralClientService;
  private AllegationService allegationService;
  private CrossReportService crossReportService;
  private ReporterService reporterService;

  private ReferralDao referralDao;
  private ReferralClientDao referralClientDao;
  private AllegationDao allegationDao;
  private CrossReportDao crossReportDao;
  private ReporterDao reporterDao;

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Before
  public void setup() throws Exception {

    referralDao = mock(ReferralDao.class);
    referralService = new ReferralService(referralDao);

    referralClientDao = mock(ReferralClientDao.class);
    referralClientService = new ReferralClientService(referralClientDao);

    allegationDao = mock(AllegationDao.class);
    allegationService = new AllegationService(allegationDao);

    crossReportDao = mock(CrossReportDao.class);
    crossReportService = new CrossReportService(crossReportDao);

    reporterDao = mock(ReporterDao.class);
    reporterService = new ReporterService(reporterDao);

    // cmsReferralDao = mock(CmsReferral.class);
    cmsReferralService = new CmsReferralService(referralService, allegationService,
        crossReportService, referralClientService, reporterService);

  }

  public Response cmsReferralServiceResponse() throws Exception {

    Referral referralDomain = MAPPER.readValue(
        fixture("fixtures/domain/cms/CmsReferral/valid/referralCmsReferral.json"), Referral.class);
    gov.ca.cwds.rest.api.persistence.cms.Referral referralToCreate =
        new gov.ca.cwds.rest.api.persistence.cms.Referral("ABC1234567", referralDomain,
            "2016-10-31");

    ReferralClient referralClientDomain = MAPPER.readValue(
        fixture("fixtures/domain/cms/CmsReferral/valid/referralClientCmsReferral.json"),
        ReferralClient.class);
    gov.ca.cwds.rest.api.persistence.cms.ReferralClient referralClientToCreate =
        new gov.ca.cwds.rest.api.persistence.cms.ReferralClient(referralClientDomain, "2016-10-31");

    Allegation allegationDomain = MAPPER.readValue(
        fixture("fixtures/domain/cms/CmsReferral/valid/allegationCmsReferral.json"),
        Allegation.class);
    gov.ca.cwds.rest.api.persistence.cms.Allegation allegationToCreate =
        new gov.ca.cwds.rest.api.persistence.cms.Allegation("ABC1234567", allegationDomain,
            "2016-10-31");

    CrossReport crossReportDomain = MAPPER.readValue(
        fixture("fixtures/domain/cms/CmsReferral/valid/crossReportCmsReferral.json"),
        CrossReport.class);
    gov.ca.cwds.rest.api.persistence.cms.CrossReport crossReportToCreate =
        new gov.ca.cwds.rest.api.persistence.cms.CrossReport(crossReportDomain.getThirdId(),
            crossReportDomain, "OXA");

    Reporter reporterDomain = MAPPER.readValue(
        fixture("fixtures/domain/cms/CmsReferral/valid/reporterCmsReferral.json"), Reporter.class);
    gov.ca.cwds.rest.api.persistence.cms.Reporter reporterToCreate =
        new gov.ca.cwds.rest.api.persistence.cms.Reporter(reporterDomain, "2016-10-31");

    Referral referralRequest = new Referral(referralToCreate);
    ReferralClient referralClientRequest = new ReferralClient(referralClientToCreate);
    Allegation allegationRequest = new Allegation(allegationToCreate);
    CrossReport crossReportRequest = new CrossReport(crossReportToCreate);
    Reporter reporterRequest = new Reporter(reporterToCreate);

    when(referralDao.create(any(gov.ca.cwds.rest.api.persistence.cms.Referral.class)))
        .thenReturn(referralToCreate);
    when(referralClientDao.create(any(gov.ca.cwds.rest.api.persistence.cms.ReferralClient.class)))
        .thenReturn(referralClientToCreate);
    when(allegationDao.create(any(gov.ca.cwds.rest.api.persistence.cms.Allegation.class)))
        .thenReturn(allegationToCreate);
    when(crossReportDao.create(any(gov.ca.cwds.rest.api.persistence.cms.CrossReport.class)))
        .thenReturn(crossReportToCreate);
    when(reporterDao.create(any(gov.ca.cwds.rest.api.persistence.cms.Reporter.class)))
        .thenReturn(reporterToCreate);

    CmsReferral cmsReferral = new CmsReferral(referralRequest, allegationRequest,
        crossReportRequest, referralClientRequest, reporterRequest);

    Response response = cmsReferralService.create(cmsReferral);

    return response;
  }

  // Create Tests
  @Test
  public void createReturnsPostedCmsReferral() throws Exception {

    Response response = cmsReferralServiceResponse();
    assertThat(response.getClass(), is(PostedCmsReferral.class));
  }

  @Test
  public void createReturnsPostedCmsReferralWithIdenticalReferralIds() throws Exception {

    Response response = cmsReferralServiceResponse();
    PostedCmsReferral postedCmsReferral = (PostedCmsReferral) response;
    String referralId = postedCmsReferral.getCrossReport().getReferralId();
    assertThat(postedCmsReferral.getReferralClient().getReferralId(), is(referralId));
    assertThat(postedCmsReferral.getReporter().getReferralId(), is(referralId));
    assertThat(postedCmsReferral.getAllegation().getReferralId(), is(referralId));
  }

  @Test
  public void createReturnsNonNull() throws Exception {

    Referral referralDomain = MAPPER.readValue(
        fixture("fixtures/domain/cms/CmsReferral/valid/referralCmsReferral.json"), Referral.class);
    gov.ca.cwds.rest.api.persistence.cms.Referral referralToCreate =
        new gov.ca.cwds.rest.api.persistence.cms.Referral("ABC1234567", referralDomain,
            "2016-10-31");

    ReferralClient referralClientDomain = MAPPER.readValue(
        fixture("fixtures/domain/cms/CmsReferral/valid/referralClientCmsReferral.json"),
        ReferralClient.class);
    gov.ca.cwds.rest.api.persistence.cms.ReferralClient referralClientToCreate =
        new gov.ca.cwds.rest.api.persistence.cms.ReferralClient(referralClientDomain, "2016-10-31");

    Allegation allegationDomain = MAPPER.readValue(
        fixture("fixtures/domain/cms/CmsReferral/valid/allegationCmsReferral.json"),
        Allegation.class);
    gov.ca.cwds.rest.api.persistence.cms.Allegation allegationToCreate =
        new gov.ca.cwds.rest.api.persistence.cms.Allegation("ABC1234567", allegationDomain,
            "2016-10-31");

    CrossReport crossReportDomain = MAPPER.readValue(
        fixture("fixtures/domain/cms/CmsReferral/valid/crossReportCmsReferral.json"),
        CrossReport.class);
    gov.ca.cwds.rest.api.persistence.cms.CrossReport crossReportToCreate =
        new gov.ca.cwds.rest.api.persistence.cms.CrossReport(crossReportDomain.getThirdId(),
            crossReportDomain, "2016-10-31");

    Reporter reporterDomain = MAPPER.readValue(
        fixture("fixtures/domain/cms/CmsReferral/valid/reporterCmsReferral.json"), Reporter.class);
    gov.ca.cwds.rest.api.persistence.cms.Reporter reporterToCreate =
        new gov.ca.cwds.rest.api.persistence.cms.Reporter(reporterDomain, "2016-10-31");

    Referral referralRequest = new Referral(referralToCreate);
    ReferralClient referralClientRequest = new ReferralClient(referralClientToCreate);
    Allegation allegationRequest = new Allegation(allegationToCreate);
    CrossReport crossReportRequest = new CrossReport(crossReportToCreate);
    Reporter reporterRequest = new Reporter(reporterToCreate);

    CmsReferral cmsReferralToCreate = new CmsReferral(referralRequest, allegationRequest,
        crossReportRequest, referralClientRequest, reporterRequest);

    when(referralDao.create(any(gov.ca.cwds.rest.api.persistence.cms.Referral.class)))
        .thenReturn(referralToCreate);
    when(referralClientDao.create(any(gov.ca.cwds.rest.api.persistence.cms.ReferralClient.class)))
        .thenReturn(referralClientToCreate);
    when(allegationDao.create(any(gov.ca.cwds.rest.api.persistence.cms.Allegation.class)))
        .thenReturn(allegationToCreate);
    when(crossReportDao.create(any(gov.ca.cwds.rest.api.persistence.cms.CrossReport.class)))
        .thenReturn(crossReportToCreate);
    when(reporterDao.create(any(gov.ca.cwds.rest.api.persistence.cms.Reporter.class)))
        .thenReturn(reporterToCreate);

    CmsReferralService cmsReferralRequest = new CmsReferralService(referralService,
        allegationService, crossReportService, referralClientService, reporterService);

    PostedCmsReferral returned = (PostedCmsReferral) cmsReferralRequest.create(cmsReferralToCreate);

    assertThat(returned, is(notNullValue()));
  }

  @Test
  public void createReturnsCorrectPostedCmsReferral() throws Exception {

    Referral referralDomain = MAPPER.readValue(
        fixture("fixtures/domain/cms/CmsReferral/valid/referralCmsReferral.json"), Referral.class);
    gov.ca.cwds.rest.api.persistence.cms.Referral referralToCreate =
        new gov.ca.cwds.rest.api.persistence.cms.Referral("ABC1234567", referralDomain,
            "2016-10-31");

    ReferralClient referralClientDomain = MAPPER.readValue(
        fixture("fixtures/domain/cms/CmsReferral/valid/referralClientCmsReferral.json"),
        ReferralClient.class);
    gov.ca.cwds.rest.api.persistence.cms.ReferralClient referralClientToCreate =
        new gov.ca.cwds.rest.api.persistence.cms.ReferralClient(referralClientDomain, "2016-10-31");

    Allegation allegationDomain = MAPPER.readValue(
        fixture("fixtures/domain/cms/CmsReferral/valid/allegationCmsReferral.json"),
        Allegation.class);
    gov.ca.cwds.rest.api.persistence.cms.Allegation allegationToCreate =
        new gov.ca.cwds.rest.api.persistence.cms.Allegation("ABC1234567", allegationDomain,
            "2016-10-31");

    CrossReport crossReportDomain = MAPPER.readValue(
        fixture("fixtures/domain/cms/CmsReferral/valid/crossReportCmsReferral.json"),
        CrossReport.class);
    gov.ca.cwds.rest.api.persistence.cms.CrossReport crossReportToCreate =
        new gov.ca.cwds.rest.api.persistence.cms.CrossReport("1234567ABC", crossReportDomain,
            "2016-10-31");

    Reporter reporterDomain = MAPPER.readValue(
        fixture("fixtures/domain/cms/CmsReferral/valid/reporterCmsReferral.json"), Reporter.class);
    gov.ca.cwds.rest.api.persistence.cms.Reporter reporterToCreate =
        new gov.ca.cwds.rest.api.persistence.cms.Reporter(reporterDomain, "2016-10-31");

    Referral referralRequest = new Referral(referralToCreate);
    ReferralClient referralClientRequest = new ReferralClient(referralClientToCreate);
    Allegation allegationRequest = new Allegation(allegationToCreate);
    CrossReport crossReportRequest = new CrossReport(crossReportToCreate);
    Reporter reporterRequest = new Reporter(reporterToCreate);

    PostedReferral postedReferral = new PostedReferral(referralToCreate);
    ReferralClient postedReferralClient = new ReferralClient(referralClientToCreate);
    PostedAllegation postedAllegation = new PostedAllegation(allegationToCreate);
    CrossReport postedCrossReport = new CrossReport(crossReportToCreate);
    PostedReporter postedReporter = new PostedReporter(reporterToCreate);

    when(referralDao.create(any(gov.ca.cwds.rest.api.persistence.cms.Referral.class)))
        .thenReturn(referralToCreate);
    when(referralClientDao.create(any(gov.ca.cwds.rest.api.persistence.cms.ReferralClient.class)))
        .thenReturn(referralClientToCreate);
    when(allegationDao.create(any(gov.ca.cwds.rest.api.persistence.cms.Allegation.class)))
        .thenReturn(allegationToCreate);
    when(crossReportDao.create(any(gov.ca.cwds.rest.api.persistence.cms.CrossReport.class)))
        .thenReturn(crossReportToCreate);
    when(reporterDao.create(any(gov.ca.cwds.rest.api.persistence.cms.Reporter.class)))
        .thenReturn(reporterToCreate);

    CmsReferral cmsReferralToCreate = new CmsReferral(referralRequest, allegationRequest,
        crossReportRequest, referralClientRequest, reporterRequest);

    CmsReferralService cmsReferralRequest = new CmsReferralService(referralService,
        allegationService, crossReportService, referralClientService, reporterService);

    PostedCmsReferral expected = new PostedCmsReferral(postedReferral, postedAllegation,
        postedCrossReport, postedReferralClient, postedReporter);

    PostedCmsReferral returned = (PostedCmsReferral) cmsReferralRequest.create(cmsReferralToCreate);

    assertThat(returned, is(expected));
  }
}
