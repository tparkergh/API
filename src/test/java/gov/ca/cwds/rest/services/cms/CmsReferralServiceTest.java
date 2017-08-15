package gov.ca.cwds.rest.services.cms;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.validation.Validation;
import javax.validation.Validator;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import gov.ca.cwds.data.cms.AllegationDao;
import gov.ca.cwds.data.cms.ClientDao;
import gov.ca.cwds.data.cms.ClientUcDao;
import gov.ca.cwds.data.cms.CrossReportDao;
import gov.ca.cwds.data.cms.ReferralClientDao;
import gov.ca.cwds.data.cms.ReferralDao;
import gov.ca.cwds.data.cms.ReporterDao;
import gov.ca.cwds.data.cms.SsaName3Dao;
import gov.ca.cwds.data.cms.StaffPersonDao;
import gov.ca.cwds.data.rules.TriggerTablesDao;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.cms.Allegation;
import gov.ca.cwds.rest.api.domain.cms.Client;
import gov.ca.cwds.rest.api.domain.cms.CmsReferral;
import gov.ca.cwds.rest.api.domain.cms.CrossReport;
import gov.ca.cwds.rest.api.domain.cms.PostedAllegation;
import gov.ca.cwds.rest.api.domain.cms.PostedClient;
import gov.ca.cwds.rest.api.domain.cms.PostedCmsReferral;
import gov.ca.cwds.rest.api.domain.cms.PostedReferral;
import gov.ca.cwds.rest.api.domain.cms.PostedReporter;
import gov.ca.cwds.rest.api.domain.cms.Referral;
import gov.ca.cwds.rest.api.domain.cms.ReferralClient;
import gov.ca.cwds.rest.api.domain.cms.Reporter;
import gov.ca.cwds.rest.business.rules.ExternalInterfaceTables;
import gov.ca.cwds.rest.business.rules.LACountyTrigger;
import gov.ca.cwds.rest.business.rules.NonLACountyTriggers;
import gov.ca.cwds.rest.business.rules.UpperCaseTables;
import gov.ca.cwds.rest.filters.TestingRequestExecutionContext;
import gov.ca.cwds.rest.services.referentialintegrity.RIAllegation;
import io.dropwizard.jackson.Jackson;

@SuppressWarnings("javadoc")
public class CmsReferralServiceTest {
  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();
  private CmsReferralService cmsReferralService;

  private ReferralService referralService;
  private ClientService clientService;
  private ReferralClientService referralClientService;
  private AllegationService allegationService;
  private CrossReportService crossReportService;
  private ReporterService reporterService;
  private AssignmentService assignmentService;
  private DrmsDocumentService drmsDocumentService;
  private AddressService addressService;
  private LongTextService longTextService;
  private UpperCaseTables upperCaseTables;

  private ReferralDao referralDao;
  private ClientDao clientDao;
  private ReferralClientDao referralClientDao;
  private AllegationDao allegationDao;
  private CrossReportDao crossReportDao;
  private ReporterDao reporterDao;
  private ClientUcDao clientUcDao;
  private StaffPersonDao staffpersonDao;
  private NonLACountyTriggers nonLACountyTriggers;
  private LACountyTrigger laCountyTrigger;
  private TriggerTablesDao triggerTablesDao;
  private StaffPersonIdRetriever staffPersonIdRetriever;
  private SsaName3Dao ssaName3Dao;
  private Validator validator;
  private ExternalInterfaceTables externalInterfaceTables;
  private RIAllegation riAllegation;

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Before
  public void setup() throws Exception {

    new TestingRequestExecutionContext("0X5");
    referralDao = mock(ReferralDao.class);
    nonLACountyTriggers = mock(NonLACountyTriggers.class);
    laCountyTrigger = mock(LACountyTrigger.class);
    triggerTablesDao = mock(TriggerTablesDao.class);
    staffpersonDao = mock(StaffPersonDao.class);
    staffPersonIdRetriever = mock(StaffPersonIdRetriever.class);
    assignmentService = mock(AssignmentService.class);
    drmsDocumentService = mock(DrmsDocumentService.class);
    addressService = mock(AddressService.class);
    longTextService = mock(LongTextService.class);
    referralService = new ReferralService(referralDao, nonLACountyTriggers, laCountyTrigger,
        triggerTablesDao, staffpersonDao, staffPersonIdRetriever, assignmentService, validator,
        drmsDocumentService, addressService, longTextService);

    clientDao = mock(ClientDao.class);
    staffpersonDao = mock(StaffPersonDao.class);
    triggerTablesDao = mock(TriggerTablesDao.class);
    nonLACountyTriggers = mock(NonLACountyTriggers.class);
    ssaName3Dao = mock(SsaName3Dao.class);
    upperCaseTables = mock(UpperCaseTables.class);
    externalInterfaceTables = mock(ExternalInterfaceTables.class);
    clientService =
        new ClientService(clientDao, staffpersonDao, triggerTablesDao, nonLACountyTriggers,
            staffPersonIdRetriever, ssaName3Dao, upperCaseTables, externalInterfaceTables);

    referralClientDao = mock(ReferralClientDao.class);
    nonLACountyTriggers = mock(NonLACountyTriggers.class);
    laCountyTrigger = mock(LACountyTrigger.class);
    triggerTablesDao = mock(TriggerTablesDao.class);
    staffpersonDao = mock(StaffPersonDao.class);
    referralClientService = new ReferralClientService(referralClientDao, nonLACountyTriggers,
        laCountyTrigger, triggerTablesDao, staffpersonDao, staffPersonIdRetriever);

    allegationDao = mock(AllegationDao.class);
    riAllegation = mock(RIAllegation.class);
    allegationService = new AllegationService(allegationDao, staffPersonIdRetriever, riAllegation);

    crossReportDao = mock(CrossReportDao.class);
    crossReportService = new CrossReportService(crossReportDao, staffPersonIdRetriever);

    reporterDao = mock(ReporterDao.class);
    reporterService = new ReporterService(reporterDao, staffPersonIdRetriever);

    clientUcDao = mock(ClientUcDao.class);

    ssaName3Dao = mock(SsaName3Dao.class);

    // cmsReferralDao = mock(CmsReferral.class);
    cmsReferralService = new CmsReferralService(referralService, clientService, allegationService,
        crossReportService, referralClientService, reporterService);

    validator = Validation.buildDefaultValidatorFactory().getValidator();

  }

  public Response cmsReferralServiceResponse() throws Exception {

    Referral referralDomain = MAPPER.readValue(
        fixture("fixtures/domain/cms/CmsReferral/valid/referralCmsReferral.json"), Referral.class);
    gov.ca.cwds.data.persistence.cms.Referral referralToCreate =
        new gov.ca.cwds.data.persistence.cms.Referral("ABC1234567", referralDomain, "2016-10-31");

    Set<Client> clientDomain =
        MAPPER.readValue(fixture("fixtures/domain/cms/CmsReferral/valid/clientCmsReferral.json"),
            new TypeReference<Set<Client>>() {});
    gov.ca.cwds.data.persistence.cms.Client clientToCreate =
        new gov.ca.cwds.data.persistence.cms.Client("ABC1234567",
            (Client) clientDomain.toArray()[0], "2016-10-31");

    Set<ReferralClient> referralClientDomain = MAPPER.readValue(
        fixture("fixtures/domain/cms/CmsReferral/valid/referralClientCmsReferral.json"),
        new TypeReference<Set<ReferralClient>>() {});
    gov.ca.cwds.data.persistence.cms.ReferralClient referralClientToCreate =
        new gov.ca.cwds.data.persistence.cms.ReferralClient(
            (ReferralClient) referralClientDomain.toArray()[0], "2016-10-31");

    Set<Allegation> allegationDomain = MAPPER.readValue(
        fixture("fixtures/domain/cms/CmsReferral/valid/allegationCmsReferral.json"),
        new TypeReference<Set<Allegation>>() {});
    gov.ca.cwds.data.persistence.cms.Allegation allegationToCreate =
        new gov.ca.cwds.data.persistence.cms.Allegation("ABC1234567",
            (Allegation) allegationDomain.toArray()[0], "2016-10-31");

    Set<CrossReport> crossReportDomain = MAPPER.readValue(
        fixture("fixtures/domain/cms/CmsReferral/valid/crossReportCmsReferral.json"),
        new TypeReference<Set<CrossReport>>() {});
    gov.ca.cwds.data.persistence.cms.CrossReport crossReportToCreate =
        new gov.ca.cwds.data.persistence.cms.CrossReport("1234ABC123",
            // ((CrossReport) crossReportDomain).getThirdId(),
            (CrossReport) crossReportDomain.toArray()[0], "OXA");

    Reporter reporterDomain = MAPPER.readValue(
        fixture("fixtures/domain/cms/CmsReferral/valid/reporterCmsReferral.json"), Reporter.class);
    gov.ca.cwds.data.persistence.cms.Reporter reporterToCreate =
        new gov.ca.cwds.data.persistence.cms.Reporter(reporterDomain, "OXA");

    Referral referralRequest = new Referral(referralToCreate);

    ReferralClient referralClientRequest = new ReferralClient(referralClientToCreate);
    Set<ReferralClient> referralClientRequestSet = new LinkedHashSet<>();
    referralClientRequestSet.add(referralClientRequest);

    Client clientRequest = new Client(clientToCreate, false);
    Set<Client> clientRequestSet = new LinkedHashSet<>();
    clientRequestSet.add(clientRequest);

    Allegation allegationRequest = new Allegation(allegationToCreate);
    Set<Allegation> allegationRequestSet = new LinkedHashSet<>();
    allegationRequestSet.add(allegationRequest);

    CrossReport crossReportRequest = new CrossReport(crossReportToCreate);
    Set<CrossReport> crossReportRequestSet = new LinkedHashSet<>();
    crossReportRequestSet.add(crossReportRequest);

    Reporter reporterRequest = new Reporter(reporterToCreate);

    when(referralDao.create(any(gov.ca.cwds.data.persistence.cms.Referral.class)))
        .thenReturn(referralToCreate);
    when(clientDao.create(any(gov.ca.cwds.data.persistence.cms.Client.class)))
        .thenReturn(clientToCreate);
    when(referralClientDao.create(any(gov.ca.cwds.data.persistence.cms.ReferralClient.class)))
        .thenReturn(referralClientToCreate);
    when(allegationDao.create(any(gov.ca.cwds.data.persistence.cms.Allegation.class)))
        .thenReturn(allegationToCreate);
    when(crossReportDao.create(any(gov.ca.cwds.data.persistence.cms.CrossReport.class)))
        .thenReturn(crossReportToCreate);
    when(reporterDao.create(any(gov.ca.cwds.data.persistence.cms.Reporter.class)))
        .thenReturn(reporterToCreate);

    CmsReferral cmsReferral = new CmsReferral(referralRequest, clientRequestSet,
        allegationRequestSet, crossReportRequestSet, referralClientRequestSet, reporterRequest);

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
    String referralId =
        ((CrossReport) postedCmsReferral.getCrossReport().toArray()[0]).getReferralId();
    assertThat(
        ((ReferralClient) postedCmsReferral.getReferralClient().toArray()[0]).getReferralId(),
        is(referralId));
    assertThat(postedCmsReferral.getReporter().getReferralId(), is(referralId));
    assertThat(((Allegation) postedCmsReferral.getAllegation().toArray()[0]).getReferralId(),
        is(referralId));
  }

  @Test
  public void createReturnsNonNull() throws Exception {

    Referral referralDomain = MAPPER.readValue(
        fixture("fixtures/domain/cms/CmsReferral/valid/referralCmsReferral.json"), Referral.class);
    gov.ca.cwds.data.persistence.cms.Referral referralToCreate =
        new gov.ca.cwds.data.persistence.cms.Referral("ABC1234567", referralDomain, "2016-10-31");

    Set<Client> clientDomain =
        MAPPER.readValue(fixture("fixtures/domain/cms/CmsReferral/valid/clientCmsReferral.json"),
            new TypeReference<Set<Client>>() {});
    gov.ca.cwds.data.persistence.cms.Client clientToCreate =
        new gov.ca.cwds.data.persistence.cms.Client("ABC1234567",
            (Client) clientDomain.toArray()[0], "2016-10-31");

    Set<ReferralClient> referralClientDomain = MAPPER.readValue(
        fixture("fixtures/domain/cms/CmsReferral/valid/referralClientCmsReferral.json"),
        new TypeReference<Set<ReferralClient>>() {});
    gov.ca.cwds.data.persistence.cms.ReferralClient referralClientToCreate =
        new gov.ca.cwds.data.persistence.cms.ReferralClient(
            (ReferralClient) referralClientDomain.toArray()[0], "2016-10-31");

    Set<Allegation> allegationDomain = MAPPER.readValue(
        fixture("fixtures/domain/cms/CmsReferral/valid/allegationCmsReferral.json"),
        new TypeReference<Set<Allegation>>() {});
    gov.ca.cwds.data.persistence.cms.Allegation allegationToCreate =
        new gov.ca.cwds.data.persistence.cms.Allegation("ABC1234567",
            (Allegation) allegationDomain.toArray()[0], "2016-10-31");

    Set<CrossReport> crossReportDomain = MAPPER.readValue(
        fixture("fixtures/domain/cms/CmsReferral/valid/crossReportCmsReferral.json"),
        new TypeReference<Set<CrossReport>>() {});
    gov.ca.cwds.data.persistence.cms.CrossReport crossReportToCreate =
        new gov.ca.cwds.data.persistence.cms.CrossReport("1234ABC123",
            (CrossReport) crossReportDomain.toArray()[0], "2016-10-31");

    Reporter reporterDomain = MAPPER.readValue(
        fixture("fixtures/domain/cms/CmsReferral/valid/reporterCmsReferral.json"), Reporter.class);
    gov.ca.cwds.data.persistence.cms.Reporter reporterToCreate =
        new gov.ca.cwds.data.persistence.cms.Reporter(reporterDomain, "2016-10-31");

    Referral referralRequest = new Referral(referralToCreate);
    ReferralClient referralClientRequest = new ReferralClient(referralClientToCreate);
    Set<ReferralClient> referralClientRequestSet = new LinkedHashSet<>();
    referralClientRequestSet.add(referralClientRequest);

    Client clientRequest = new Client(clientToCreate, false);
    Set<Client> clientRequestSet = new LinkedHashSet<>();
    clientRequestSet.add(clientRequest);

    Allegation allegationRequest = new Allegation(allegationToCreate);
    Set<Allegation> allegationRequestSet = new LinkedHashSet<>();
    allegationRequestSet.add(allegationRequest);

    CrossReport crossReportRequest = new CrossReport(crossReportToCreate);
    Set<CrossReport> crossReportRequestSet = new LinkedHashSet<>();
    crossReportRequestSet.add(crossReportRequest);

    Reporter reporterRequest = new Reporter(reporterToCreate);

    CmsReferral cmsReferralToCreate = new CmsReferral(referralRequest, clientRequestSet,
        allegationRequestSet, crossReportRequestSet, referralClientRequestSet, reporterRequest);

    when(referralDao.create(any(gov.ca.cwds.data.persistence.cms.Referral.class)))
        .thenReturn(referralToCreate);
    when(clientDao.create(any(gov.ca.cwds.data.persistence.cms.Client.class)))
        .thenReturn(clientToCreate);
    when(referralClientDao.create(any(gov.ca.cwds.data.persistence.cms.ReferralClient.class)))
        .thenReturn(referralClientToCreate);
    when(allegationDao.create(any(gov.ca.cwds.data.persistence.cms.Allegation.class)))
        .thenReturn(allegationToCreate);
    when(crossReportDao.create(any(gov.ca.cwds.data.persistence.cms.CrossReport.class)))
        .thenReturn(crossReportToCreate);
    when(reporterDao.create(any(gov.ca.cwds.data.persistence.cms.Reporter.class)))
        .thenReturn(reporterToCreate);

    CmsReferralService cmsReferralRequest = new CmsReferralService(referralService, clientService,
        allegationService, crossReportService, referralClientService, reporterService);

    PostedCmsReferral returned = (PostedCmsReferral) cmsReferralRequest.create(cmsReferralToCreate);

    assertThat(returned, is(notNullValue()));
  }

  @Test
  public void createReturnsCorrectPostedCmsReferral() throws Exception {

    Referral referralDomain = MAPPER.readValue(
        fixture("fixtures/domain/cms/CmsReferral/valid/referralCmsReferral.json"), Referral.class);
    gov.ca.cwds.data.persistence.cms.Referral referralToCreate =
        new gov.ca.cwds.data.persistence.cms.Referral("ABC1234567", referralDomain, "2016-10-31");

    Set<ReferralClient> referralClientDomain = MAPPER.readValue(
        fixture("fixtures/domain/cms/CmsReferral/valid/referralClientCmsReferral.json"),
        new TypeReference<Set<ReferralClient>>() {});
    gov.ca.cwds.data.persistence.cms.ReferralClient referralClientToCreate =
        new gov.ca.cwds.data.persistence.cms.ReferralClient(
            (ReferralClient) referralClientDomain.toArray()[0], "2016-10-31");

    Set<Allegation> allegationDomain = MAPPER.readValue(
        fixture("fixtures/domain/cms/CmsReferral/valid/allegationCmsReferral.json"),
        new TypeReference<Set<Allegation>>() {});
    gov.ca.cwds.data.persistence.cms.Allegation allegationToCreate =
        new gov.ca.cwds.data.persistence.cms.Allegation("ABC1234567",
            (Allegation) allegationDomain.toArray()[0], "2016-10-31");

    Set<CrossReport> crossReportDomain = MAPPER.readValue(
        fixture("fixtures/domain/cms/CmsReferral/valid/crossReportCmsReferral.json"),
        new TypeReference<Set<CrossReport>>() {});
    gov.ca.cwds.data.persistence.cms.CrossReport crossReportToCreate =
        new gov.ca.cwds.data.persistence.cms.CrossReport("1234567ABC",
            (CrossReport) crossReportDomain.toArray()[0], "2016-10-31");

    Reporter reporterDomain = MAPPER.readValue(
        fixture("fixtures/domain/cms/CmsReferral/valid/reporterCmsReferral.json"), Reporter.class);
    gov.ca.cwds.data.persistence.cms.Reporter reporterToCreate =
        new gov.ca.cwds.data.persistence.cms.Reporter(reporterDomain, "2016-10-31");

    Set<Client> clientDomain =
        MAPPER.readValue(fixture("fixtures/domain/cms/CmsReferral/valid/clientCmsReferral.json"),
            new TypeReference<Set<Client>>() {});
    gov.ca.cwds.data.persistence.cms.Client clientToCreate =
        new gov.ca.cwds.data.persistence.cms.Client("ABC1234567",
            (Client) clientDomain.toArray()[0], "2016-10-31");

    Referral referralRequest = new Referral(referralToCreate);
    ReferralClient referralClientRequest = new ReferralClient(referralClientToCreate);
    Set<ReferralClient> referralClientRequestSet = new LinkedHashSet<>();
    referralClientRequestSet.add(referralClientRequest);

    Allegation allegationRequest = new Allegation(allegationToCreate);
    Set<Allegation> allegationRequestSet = new LinkedHashSet<>();
    allegationRequestSet.add(allegationRequest);

    CrossReport crossReportRequest = new CrossReport(crossReportToCreate);
    Set<CrossReport> crossReportRequestSet = new LinkedHashSet<>();
    crossReportRequestSet.add(crossReportRequest);

    Reporter reporterRequest = new Reporter(reporterToCreate);
    Client clientRequest = new Client(clientToCreate, false);

    Set<Client> clientRequestSet = new LinkedHashSet<>();
    clientRequestSet.add(clientRequest);

    PostedReferral postedReferral = new PostedReferral(referralToCreate);
    ReferralClient postedReferralClient = new ReferralClient(referralClientToCreate);
    Set<ReferralClient> postedReferralClientSet = new LinkedHashSet<>();
    postedReferralClientSet.add(postedReferralClient);

    PostedAllegation postedAllegation = new PostedAllegation(allegationToCreate);
    Set<PostedAllegation> postedAllegationSet = new LinkedHashSet<>();
    postedAllegationSet.add(postedAllegation);

    CrossReport postedCrossReport = new CrossReport(crossReportToCreate);
    Set<CrossReport> postedCrossReportSet = new LinkedHashSet<>();
    postedCrossReportSet.add(postedCrossReport);

    PostedReporter postedReporter = new PostedReporter(reporterToCreate);
    PostedClient postedClient = new PostedClient(clientToCreate, false);

    Set<PostedClient> postedClientSet = new LinkedHashSet<>();
    postedClientSet.add(postedClient);

    when(referralDao.create(any(gov.ca.cwds.data.persistence.cms.Referral.class)))
        .thenReturn(referralToCreate);
    when(referralClientDao.create(any(gov.ca.cwds.data.persistence.cms.ReferralClient.class)))
        .thenReturn(referralClientToCreate);
    when(allegationDao.create(any(gov.ca.cwds.data.persistence.cms.Allegation.class)))
        .thenReturn(allegationToCreate);
    when(crossReportDao.create(any(gov.ca.cwds.data.persistence.cms.CrossReport.class)))
        .thenReturn(crossReportToCreate);
    when(reporterDao.create(any(gov.ca.cwds.data.persistence.cms.Reporter.class)))
        .thenReturn(reporterToCreate);
    when(clientDao.create(any(gov.ca.cwds.data.persistence.cms.Client.class)))
        .thenReturn(clientToCreate);

    CmsReferral cmsReferralToCreate = new CmsReferral(referralRequest, clientRequestSet,
        allegationRequestSet, crossReportRequestSet, referralClientRequestSet, reporterRequest);

    CmsReferralService cmsReferralRequest = new CmsReferralService(referralService, clientService,
        allegationService, crossReportService, referralClientService, reporterService);

    PostedCmsReferral expected = new PostedCmsReferral(postedReferral, postedClientSet,
        postedAllegationSet, postedCrossReportSet, postedReferralClientSet, postedReporter);

    PostedCmsReferral returned = (PostedCmsReferral) cmsReferralRequest.create(cmsReferralToCreate);

    assertThat(returned, is(expected));
  }



}
