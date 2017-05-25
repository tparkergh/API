package gov.ca.cwds.rest.services;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.validation.Validation;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import gov.ca.cwds.data.cms.AddressDao;
import gov.ca.cwds.data.cms.AllegationDao;
import gov.ca.cwds.data.cms.ChildClientDao;
import gov.ca.cwds.data.cms.ClientAddressDao;
import gov.ca.cwds.data.cms.ClientDao;
import gov.ca.cwds.data.cms.CrossReportDao;
import gov.ca.cwds.data.cms.LongTextDao;
import gov.ca.cwds.data.cms.ReferralClientDao;
import gov.ca.cwds.data.cms.ReferralDao;
import gov.ca.cwds.data.cms.ReporterDao;
import gov.ca.cwds.data.cms.StaffPersonDao;
import gov.ca.cwds.data.rules.TriggerTablesDao;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.PostedScreeningToReferral;
import gov.ca.cwds.rest.api.domain.ScreeningToReferral;
import gov.ca.cwds.rest.api.domain.cms.Address;
import gov.ca.cwds.rest.api.domain.cms.Allegation;
import gov.ca.cwds.rest.api.domain.cms.ChildClient;
import gov.ca.cwds.rest.api.domain.cms.Client;
import gov.ca.cwds.rest.api.domain.cms.ClientAddress;
import gov.ca.cwds.rest.api.domain.cms.CmsReferral;
import gov.ca.cwds.rest.api.domain.cms.CrossReport;
import gov.ca.cwds.rest.api.domain.cms.LongText;
import gov.ca.cwds.rest.api.domain.cms.Referral;
import gov.ca.cwds.rest.api.domain.cms.ReferralClient;
import gov.ca.cwds.rest.api.domain.cms.Reporter;
import gov.ca.cwds.rest.business.rules.LACountyTrigger;
import gov.ca.cwds.rest.business.rules.NonLACountyTriggers;
import gov.ca.cwds.rest.services.cms.AddressService;
import gov.ca.cwds.rest.services.cms.AllegationService;
import gov.ca.cwds.rest.services.cms.ChildClientService;
import gov.ca.cwds.rest.services.cms.ClientAddressService;
import gov.ca.cwds.rest.services.cms.ClientService;
import gov.ca.cwds.rest.services.cms.CrossReportService;
import gov.ca.cwds.rest.services.cms.LongTextService;
import gov.ca.cwds.rest.services.cms.ReferralClientService;
import gov.ca.cwds.rest.services.cms.ReferralService;
import gov.ca.cwds.rest.services.cms.ReporterService;
import io.dropwizard.jackson.Jackson;

/**
 * 
 * @author CWDS API Team
 */
public class ScreeningToReferralServiceTest {

  private ScreeningToReferralService screeningToReferralService;
  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

  private ReferralService referralService;
  private ClientService clientService;
  private ReferralClientService referralClientService;
  private AllegationService allegationService;
  private CrossReportService crossReportService;
  private ReporterService reporterService;
  private AddressService addressService;
  private ClientAddressService clientAddressService;
  private ChildClientService childClientService;
  private LongTextService longTextService;

  private ReferralDao referralDao;
  private ClientDao clientDao;
  private ReferralClientDao referralClientDao;
  private AllegationDao allegationDao;
  private CrossReportDao crossReportDao;
  private ReporterDao reporterDao;
  private AddressDao addressDao;
  private ClientAddressDao clientAddressDao;
  private ChildClientDao childClientDao;
  private LongTextDao longTextDao;
  private StaffPersonDao staffpersonDao;
  private NonLACountyTriggers nonLACountyTriggers;
  private LACountyTrigger laCountyTrigger;
  private TriggerTablesDao triggerTablesDao;

  @SuppressWarnings("javadoc")
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @SuppressWarnings("javadoc")
  @Before
  public void setup() throws Exception {

    referralDao = mock(ReferralDao.class);
    nonLACountyTriggers = mock(NonLACountyTriggers.class);
    laCountyTrigger = mock(LACountyTrigger.class);
    triggerTablesDao = mock(TriggerTablesDao.class);
    staffpersonDao = mock(StaffPersonDao.class);
    referralService = new ReferralService(referralDao, nonLACountyTriggers, laCountyTrigger,
        triggerTablesDao, staffpersonDao);

    clientDao = mock(ClientDao.class);
    staffpersonDao = mock(StaffPersonDao.class);
    triggerTablesDao = mock(TriggerTablesDao.class);
    nonLACountyTriggers = mock(NonLACountyTriggers.class);
    clientService =
        new ClientService(clientDao, staffpersonDao, triggerTablesDao, nonLACountyTriggers);

    referralClientDao = mock(ReferralClientDao.class);
    nonLACountyTriggers = mock(NonLACountyTriggers.class);
    laCountyTrigger = mock(LACountyTrigger.class);
    triggerTablesDao = mock(TriggerTablesDao.class);
    staffpersonDao = mock(StaffPersonDao.class);
    referralClientService = new ReferralClientService(referralClientDao, nonLACountyTriggers,
        laCountyTrigger, triggerTablesDao, staffpersonDao);

    allegationDao = mock(AllegationDao.class);
    allegationService = new AllegationService(allegationDao);

    crossReportDao = mock(CrossReportDao.class);
    crossReportService = new CrossReportService(crossReportDao);

    reporterDao = mock(ReporterDao.class);
    reporterService = new ReporterService(reporterDao);

    addressDao = mock(AddressDao.class);
    addressService = new AddressService(addressDao);

    clientAddressDao = mock(ClientAddressDao.class);
    clientAddressService = new ClientAddressService(clientAddressDao);

    longTextDao = mock(LongTextDao.class);
    longTextService = new LongTextService(longTextDao);

    childClientDao = mock(ChildClientDao.class);
    childClientService = new ChildClientService(childClientDao);

    screeningToReferralService = new ScreeningToReferralService(referralService, clientService,
        allegationService, crossReportService, referralClientService, reporterService,
        addressService, clientAddressService, longTextService, childClientService,
        Validation.buildDefaultValidatorFactory().getValidator(), referralDao);
  }

  @SuppressWarnings("javadoc")
  public Response screeningToReferralServiceResponse() throws Exception {

    MAPPER.configure(SerializationFeature.INDENT_OUTPUT, true);

    Referral referralDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validReferral.json"), Referral.class);
    gov.ca.cwds.data.persistence.cms.Referral referralToCreate =
        new gov.ca.cwds.data.persistence.cms.Referral("0123456ABC", referralDomain, "2016-10-31");

    Set<Client> clientDomain =
        MAPPER.readValue(fixture("fixtures/domain/ScreeningToReferral/valid/validClient.json"),
            new TypeReference<Set<Client>>() {});
    gov.ca.cwds.data.persistence.cms.Client clientToCreate =
        new gov.ca.cwds.data.persistence.cms.Client("1234567ABC",
            (Client) clientDomain.toArray()[0], "2016-10-31");

    ChildClient childClient = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/childClient.json"), ChildClient.class);
    gov.ca.cwds.data.persistence.cms.ChildClient childClientToCreate =
        new gov.ca.cwds.data.persistence.cms.ChildClient("1234567ABC", childClient, "0XA");

    Set<ReferralClient> referralClientDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validReferralClient.json"),
        new TypeReference<Set<ReferralClient>>() {});
    gov.ca.cwds.data.persistence.cms.ReferralClient referralClientToCreate =
        new gov.ca.cwds.data.persistence.cms.ReferralClient(
            (ReferralClient) referralClientDomain.toArray()[0], "2016-10-31");

    Set<Allegation> allegationDomain =
        MAPPER.readValue(fixture("fixtures/domain/ScreeningToReferral/valid/validAllegation.json"),
            new TypeReference<Set<Allegation>>() {});
    gov.ca.cwds.data.persistence.cms.Allegation allegationToCreate =
        new gov.ca.cwds.data.persistence.cms.Allegation("2345678ABC",
            (Allegation) allegationDomain.toArray()[0], "2016-10-31");

    Set<CrossReport> crossReportDomain =
        MAPPER.readValue(fixture("fixtures/domain/ScreeningToReferral/valid/validCrossReport.json"),
            new TypeReference<Set<CrossReport>>() {});
    gov.ca.cwds.data.persistence.cms.CrossReport crossReportToCreate =
        new gov.ca.cwds.data.persistence.cms.CrossReport("3456789ABC",
            // ((CrossReport) crossReportDomain).getThirdId(),
            (CrossReport) crossReportDomain.toArray()[0], "OXA");

    Address addressDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validAddress.json"), Address.class);
    gov.ca.cwds.data.persistence.cms.Address addressToCreate =
        new gov.ca.cwds.data.persistence.cms.Address("345678ABC", addressDomain, "ABC");

    ClientAddress clientAddressDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validClientAddress.json"),
        ClientAddress.class);
    gov.ca.cwds.data.persistence.cms.ClientAddress clientAddressToCreate =
        new gov.ca.cwds.data.persistence.cms.ClientAddress("456789ABC", clientAddressDomain, "ABC");


    LongText longTextDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validLongText.json"), LongText.class);
    gov.ca.cwds.data.persistence.cms.LongText longTextToCreate =
        new gov.ca.cwds.data.persistence.cms.LongText("567890ABC", longTextDomain, "ABC");

    Reporter reporterDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validReporter.json"), Reporter.class);
    gov.ca.cwds.data.persistence.cms.Reporter reporterToCreate =
        new gov.ca.cwds.data.persistence.cms.Reporter(reporterDomain, "ABC");
    Reporter reporterRequest = new Reporter(reporterToCreate);

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

    when(referralDao.create(any(gov.ca.cwds.data.persistence.cms.Referral.class)))
        .thenReturn(referralToCreate);
    when(childClientDao.create(any(gov.ca.cwds.data.persistence.cms.ChildClient.class)))
        .thenReturn(childClientToCreate);
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
    when(addressDao.create(any(gov.ca.cwds.data.persistence.cms.Address.class)))
        .thenReturn(addressToCreate);
    when(clientAddressDao.create(any(gov.ca.cwds.data.persistence.cms.ClientAddress.class)))
        .thenReturn(clientAddressToCreate);
    when(longTextDao.create(any(gov.ca.cwds.data.persistence.cms.LongText.class)))
        .thenReturn(longTextToCreate);

    CmsReferral cmsReferral = new CmsReferral(referralRequest, clientRequestSet,
        allegationRequestSet, crossReportRequestSet, referralClientRequestSet, reporterRequest);

    ScreeningToReferral screeningToReferral = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/valid.json"), ScreeningToReferral.class);

    Response response = screeningToReferralService.create(screeningToReferral);
    return response;
  }

  // Create Tests
  @SuppressWarnings("javadoc")
  @Test
  public void createReturnsPostedCmsReferral() throws Exception {
    Response response = screeningToReferralServiceResponse();
    assertThat(response.getClass(), is(PostedScreeningToReferral.class));
  }

  @SuppressWarnings("javadoc")
  @Test
  public void testScreeningToReferralSuccess() throws Exception {
    Referral referralDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validReferral.json"), Referral.class);
    gov.ca.cwds.data.persistence.cms.Referral referralToCreate =
        new gov.ca.cwds.data.persistence.cms.Referral("0123456ABC", referralDomain, "2016-10-31");
    when(referralDao.create(any(gov.ca.cwds.data.persistence.cms.Referral.class)))
        .thenReturn(referralToCreate);

    ChildClient childClient = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/childClient.json"), ChildClient.class);
    gov.ca.cwds.data.persistence.cms.ChildClient childClientToCreate =
        new gov.ca.cwds.data.persistence.cms.ChildClient("1234567ABC", childClient, "0XA");
    when(childClientDao.create(any(gov.ca.cwds.data.persistence.cms.ChildClient.class)))
        .thenReturn(childClientToCreate);
    Set<Client> clientDomain =
        MAPPER.readValue(fixture("fixtures/domain/ScreeningToReferral/valid/validClient.json"),
            new TypeReference<Set<Client>>() {});
    gov.ca.cwds.data.persistence.cms.Client clientToCreate =
        new gov.ca.cwds.data.persistence.cms.Client("1234567ABC",
            (Client) clientDomain.toArray()[0], "2016-10-31");
    when(clientDao.create(any(gov.ca.cwds.data.persistence.cms.Client.class)))
        .thenReturn(clientToCreate);

    Set<ReferralClient> referralClientDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validReferralClient.json"),
        new TypeReference<Set<ReferralClient>>() {});
    gov.ca.cwds.data.persistence.cms.ReferralClient referralClientToCreate =
        new gov.ca.cwds.data.persistence.cms.ReferralClient(
            (ReferralClient) referralClientDomain.toArray()[0], "2016-10-31");
    when(referralClientDao.create(any(gov.ca.cwds.data.persistence.cms.ReferralClient.class)))
        .thenReturn(referralClientToCreate);

    Set<Allegation> allegationDomain =
        MAPPER.readValue(fixture("fixtures/domain/ScreeningToReferral/valid/validAllegation.json"),
            new TypeReference<Set<Allegation>>() {});
    gov.ca.cwds.data.persistence.cms.Allegation allegationToCreate =
        new gov.ca.cwds.data.persistence.cms.Allegation("2345678ABC",
            (Allegation) allegationDomain.toArray()[0], "2016-10-31");
    when(allegationDao.create(any(gov.ca.cwds.data.persistence.cms.Allegation.class)))
        .thenReturn(allegationToCreate);

    Set<CrossReport> crossReportDomain =
        MAPPER.readValue(fixture("fixtures/domain/ScreeningToReferral/valid/validCrossReport.json"),
            new TypeReference<Set<CrossReport>>() {});
    gov.ca.cwds.data.persistence.cms.CrossReport crossReportToCreate =
        new gov.ca.cwds.data.persistence.cms.CrossReport("3456789ABC",
            // ((CrossReport) crossReportDomain).getThirdId(),
            (CrossReport) crossReportDomain.toArray()[0], "OXA");
    when(crossReportDao.create(any(gov.ca.cwds.data.persistence.cms.CrossReport.class)))
        .thenReturn(crossReportToCreate);

    Reporter reporterDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validReporter.json"), Reporter.class);
    gov.ca.cwds.data.persistence.cms.Reporter reporterToCreate =
        new gov.ca.cwds.data.persistence.cms.Reporter(reporterDomain, "ABC");
    when(reporterDao.create(any(gov.ca.cwds.data.persistence.cms.Reporter.class)))
        .thenReturn(reporterToCreate);

    Address addressDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validAddress.json"), Address.class);
    gov.ca.cwds.data.persistence.cms.Address addressToCreate =
        new gov.ca.cwds.data.persistence.cms.Address("345678ABC", addressDomain, "ABC");
    when(addressDao.create(any(gov.ca.cwds.data.persistence.cms.Address.class)))
        .thenReturn(addressToCreate);

    ClientAddress clientAddressDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validClientAddress.json"),
        ClientAddress.class);
    gov.ca.cwds.data.persistence.cms.ClientAddress clientAddressToCreate =
        new gov.ca.cwds.data.persistence.cms.ClientAddress("456789ABC", clientAddressDomain, "ABC");
    when(clientAddressDao.create(any(gov.ca.cwds.data.persistence.cms.ClientAddress.class)))
        .thenReturn(clientAddressToCreate);

    LongText longTextDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validLongText.json"), LongText.class);
    gov.ca.cwds.data.persistence.cms.LongText longTextToCreate =
        new gov.ca.cwds.data.persistence.cms.LongText("567890ABC", longTextDomain, "ABC");
    when(longTextDao.create(any(gov.ca.cwds.data.persistence.cms.LongText.class)))
        .thenReturn(longTextToCreate);

    ScreeningToReferral screeningToReferral = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/valid.json"), ScreeningToReferral.class);

    Response response = screeningToReferralService.create(screeningToReferral);

    assertThat(response.getClass(), is(PostedScreeningToReferral.class));
  }

  @SuppressWarnings("javadoc")
  @Test
  public void testWithReferralExistSuccess() throws Exception {
    Referral referralDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validReferral.json"), Referral.class);
    gov.ca.cwds.data.persistence.cms.Referral referralToCreate =
        new gov.ca.cwds.data.persistence.cms.Referral("0123456ABC", referralDomain, "2016-10-31");
    when(referralDao.create(any(gov.ca.cwds.data.persistence.cms.Referral.class)))
        .thenReturn(referralToCreate);
    when(referralDao.find("0123456ABC")).thenReturn(referralToCreate);

    ChildClient childClient = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/childClient.json"), ChildClient.class);
    gov.ca.cwds.data.persistence.cms.ChildClient childClientToCreate =
        new gov.ca.cwds.data.persistence.cms.ChildClient("1234567ABC", childClient, "0XA");
    when(childClientDao.create(any(gov.ca.cwds.data.persistence.cms.ChildClient.class)))
        .thenReturn(childClientToCreate);
    Set<Client> clientDomain =
        MAPPER.readValue(fixture("fixtures/domain/ScreeningToReferral/valid/validClient.json"),
            new TypeReference<Set<Client>>() {});
    gov.ca.cwds.data.persistence.cms.Client clientToCreate =
        new gov.ca.cwds.data.persistence.cms.Client("1234567ABC",
            (Client) clientDomain.toArray()[0], "2016-10-31");
    when(clientDao.create(any(gov.ca.cwds.data.persistence.cms.Client.class)))
        .thenReturn(clientToCreate);

    Set<ReferralClient> referralClientDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validReferralClient.json"),
        new TypeReference<Set<ReferralClient>>() {});
    gov.ca.cwds.data.persistence.cms.ReferralClient referralClientToCreate =
        new gov.ca.cwds.data.persistence.cms.ReferralClient(
            (ReferralClient) referralClientDomain.toArray()[0], "2016-10-31");
    when(referralClientDao.create(any(gov.ca.cwds.data.persistence.cms.ReferralClient.class)))
        .thenReturn(referralClientToCreate);

    Set<Allegation> allegationDomain =
        MAPPER.readValue(fixture("fixtures/domain/ScreeningToReferral/valid/validAllegation.json"),
            new TypeReference<Set<Allegation>>() {});
    gov.ca.cwds.data.persistence.cms.Allegation allegationToCreate =
        new gov.ca.cwds.data.persistence.cms.Allegation("2345678ABC",
            (Allegation) allegationDomain.toArray()[0], "2016-10-31");
    when(allegationDao.create(any(gov.ca.cwds.data.persistence.cms.Allegation.class)))
        .thenReturn(allegationToCreate);

    Set<CrossReport> crossReportDomain =
        MAPPER.readValue(fixture("fixtures/domain/ScreeningToReferral/valid/validCrossReport.json"),
            new TypeReference<Set<CrossReport>>() {});
    gov.ca.cwds.data.persistence.cms.CrossReport crossReportToCreate =
        new gov.ca.cwds.data.persistence.cms.CrossReport("3456789ABC",
            // ((CrossReport) crossReportDomain).getThirdId(),
            (CrossReport) crossReportDomain.toArray()[0], "OXA");
    when(crossReportDao.create(any(gov.ca.cwds.data.persistence.cms.CrossReport.class)))
        .thenReturn(crossReportToCreate);

    Reporter reporterDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validReporter.json"), Reporter.class);
    gov.ca.cwds.data.persistence.cms.Reporter reporterToCreate =
        new gov.ca.cwds.data.persistence.cms.Reporter(reporterDomain, "ABC");
    when(reporterDao.create(any(gov.ca.cwds.data.persistence.cms.Reporter.class)))
        .thenReturn(reporterToCreate);

    Address addressDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validAddress.json"), Address.class);
    gov.ca.cwds.data.persistence.cms.Address addressToCreate =
        new gov.ca.cwds.data.persistence.cms.Address("345678ABC", addressDomain, "ABC");
    when(addressDao.create(any(gov.ca.cwds.data.persistence.cms.Address.class)))
        .thenReturn(addressToCreate);

    ClientAddress clientAddressDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validClientAddress.json"),
        ClientAddress.class);
    gov.ca.cwds.data.persistence.cms.ClientAddress clientAddressToCreate =
        new gov.ca.cwds.data.persistence.cms.ClientAddress("456789ABC", clientAddressDomain, "ABC");
    when(clientAddressDao.create(any(gov.ca.cwds.data.persistence.cms.ClientAddress.class)))
        .thenReturn(clientAddressToCreate);

    LongText longTextDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validLongText.json"), LongText.class);
    gov.ca.cwds.data.persistence.cms.LongText longTextToCreate =
        new gov.ca.cwds.data.persistence.cms.LongText("567890ABC", longTextDomain, "ABC");
    when(longTextDao.create(any(gov.ca.cwds.data.persistence.cms.LongText.class)))
        .thenReturn(longTextToCreate);

    ScreeningToReferral screeningToReferral =
        MAPPER.readValue(fixture("fixtures/domain/ScreeningToReferral/valid/withReferralId.json"),
            ScreeningToReferral.class);

    Response response = screeningToReferralService.create(screeningToReferral);

    assertThat(response.getClass(), is(PostedScreeningToReferral.class));
  }

  @SuppressWarnings("javadoc")
  @Test
  public void testWithReferralDoesNotExistFail() throws Exception {
    Referral referralDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validReferral.json"), Referral.class);
    gov.ca.cwds.data.persistence.cms.Referral referralToCreate =
        new gov.ca.cwds.data.persistence.cms.Referral("0123456ABC", referralDomain, "2016-10-31");
    when(referralDao.create(any(gov.ca.cwds.data.persistence.cms.Referral.class)))
        .thenReturn(referralToCreate);
    when(referralDao.find("0123456ABC")).thenReturn(referralToCreate);

    ChildClient childClient = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/childClient.json"), ChildClient.class);
    gov.ca.cwds.data.persistence.cms.ChildClient childClientToCreate =
        new gov.ca.cwds.data.persistence.cms.ChildClient("1234567ABC", childClient, "0XA");
    when(childClientDao.create(any(gov.ca.cwds.data.persistence.cms.ChildClient.class)))
        .thenReturn(childClientToCreate);
    Set<Client> clientDomain =
        MAPPER.readValue(fixture("fixtures/domain/ScreeningToReferral/valid/validClient.json"),
            new TypeReference<Set<Client>>() {});
    gov.ca.cwds.data.persistence.cms.Client clientToCreate =
        new gov.ca.cwds.data.persistence.cms.Client("1234567ABC",
            (Client) clientDomain.toArray()[0], "2016-10-31");
    when(clientDao.create(any(gov.ca.cwds.data.persistence.cms.Client.class)))
        .thenReturn(clientToCreate);

    Set<ReferralClient> referralClientDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validReferralClient.json"),
        new TypeReference<Set<ReferralClient>>() {});
    gov.ca.cwds.data.persistence.cms.ReferralClient referralClientToCreate =
        new gov.ca.cwds.data.persistence.cms.ReferralClient(
            (ReferralClient) referralClientDomain.toArray()[0], "2016-10-31");
    when(referralClientDao.create(any(gov.ca.cwds.data.persistence.cms.ReferralClient.class)))
        .thenReturn(referralClientToCreate);

    Set<Allegation> allegationDomain =
        MAPPER.readValue(fixture("fixtures/domain/ScreeningToReferral/valid/validAllegation.json"),
            new TypeReference<Set<Allegation>>() {});
    gov.ca.cwds.data.persistence.cms.Allegation allegationToCreate =
        new gov.ca.cwds.data.persistence.cms.Allegation("2345678ABC",
            (Allegation) allegationDomain.toArray()[0], "2016-10-31");
    when(allegationDao.create(any(gov.ca.cwds.data.persistence.cms.Allegation.class)))
        .thenReturn(allegationToCreate);

    Set<CrossReport> crossReportDomain =
        MAPPER.readValue(fixture("fixtures/domain/ScreeningToReferral/valid/validCrossReport.json"),
            new TypeReference<Set<CrossReport>>() {});
    gov.ca.cwds.data.persistence.cms.CrossReport crossReportToCreate =
        new gov.ca.cwds.data.persistence.cms.CrossReport("3456789ABC",
            // ((CrossReport) crossReportDomain).getThirdId(),
            (CrossReport) crossReportDomain.toArray()[0], "OXA");
    when(crossReportDao.create(any(gov.ca.cwds.data.persistence.cms.CrossReport.class)))
        .thenReturn(crossReportToCreate);

    Reporter reporterDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validReporter.json"), Reporter.class);
    gov.ca.cwds.data.persistence.cms.Reporter reporterToCreate =
        new gov.ca.cwds.data.persistence.cms.Reporter(reporterDomain, "ABC");
    when(reporterDao.create(any(gov.ca.cwds.data.persistence.cms.Reporter.class)))
        .thenReturn(reporterToCreate);

    Address addressDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validAddress.json"), Address.class);
    gov.ca.cwds.data.persistence.cms.Address addressToCreate =
        new gov.ca.cwds.data.persistence.cms.Address("345678ABC", addressDomain, "ABC");
    when(addressDao.create(any(gov.ca.cwds.data.persistence.cms.Address.class)))
        .thenReturn(addressToCreate);

    ClientAddress clientAddressDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validClientAddress.json"),
        ClientAddress.class);
    gov.ca.cwds.data.persistence.cms.ClientAddress clientAddressToCreate =
        new gov.ca.cwds.data.persistence.cms.ClientAddress("456789ABC", clientAddressDomain, "ABC");
    when(clientAddressDao.create(any(gov.ca.cwds.data.persistence.cms.ClientAddress.class)))
        .thenReturn(clientAddressToCreate);

    LongText longTextDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validLongText.json"), LongText.class);
    gov.ca.cwds.data.persistence.cms.LongText longTextToCreate =
        new gov.ca.cwds.data.persistence.cms.LongText("567890ABC", longTextDomain, "ABC");
    when(longTextDao.create(any(gov.ca.cwds.data.persistence.cms.LongText.class)))
        .thenReturn(longTextToCreate);

    ScreeningToReferral screeningToReferral = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/invalid/withReferralIdNotExist.json"),
        ScreeningToReferral.class);

    try {
      this.screeningToReferralService.create(screeningToReferral);
      Assert.fail("Expected ServiceException was not thrown");
    } catch (Exception e) {
      assertThat(
          e.getMessage().contains("Legacy Id does not correspond to an existing CMS/CWS Referral"),
          is(equalTo(true)));
    }
  }

  @SuppressWarnings("javadoc")
  @Test
  public void testScreeningToReferralParticipantWithBlankRolesSuccess() throws Exception {
    Referral referralDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validReferral.json"), Referral.class);
    gov.ca.cwds.data.persistence.cms.Referral referralToCreate =
        new gov.ca.cwds.data.persistence.cms.Referral("0123456ABC", referralDomain, "2016-10-31");
    when(referralDao.create(any(gov.ca.cwds.data.persistence.cms.Referral.class)))
        .thenReturn(referralToCreate);

    ChildClient childClient = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/childClient.json"), ChildClient.class);
    gov.ca.cwds.data.persistence.cms.ChildClient childClientToCreate =
        new gov.ca.cwds.data.persistence.cms.ChildClient("1234567ABC", childClient, "0XA");
    when(childClientDao.create(any(gov.ca.cwds.data.persistence.cms.ChildClient.class)))
        .thenReturn(childClientToCreate);

    Set<Client> clientDomain =
        MAPPER.readValue(fixture("fixtures/domain/ScreeningToReferral/valid/validClient.json"),
            new TypeReference<Set<Client>>() {});
    gov.ca.cwds.data.persistence.cms.Client clientToCreate =
        new gov.ca.cwds.data.persistence.cms.Client("1234567ABC",
            (Client) clientDomain.toArray()[0], "2016-10-31");
    when(clientDao.create(any(gov.ca.cwds.data.persistence.cms.Client.class)))
        .thenReturn(clientToCreate);
    when(clientDao.find("1234567ABC")).thenReturn(clientToCreate);

    Set<ReferralClient> referralClientDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validReferralClient.json"),
        new TypeReference<Set<ReferralClient>>() {});
    gov.ca.cwds.data.persistence.cms.ReferralClient referralClientToCreate =
        new gov.ca.cwds.data.persistence.cms.ReferralClient(
            (ReferralClient) referralClientDomain.toArray()[0], "2016-10-31");
    when(referralClientDao.create(any(gov.ca.cwds.data.persistence.cms.ReferralClient.class)))
        .thenReturn(referralClientToCreate);

    Set<Allegation> allegationDomain =
        MAPPER.readValue(fixture("fixtures/domain/ScreeningToReferral/valid/validAllegation.json"),
            new TypeReference<Set<Allegation>>() {});
    gov.ca.cwds.data.persistence.cms.Allegation allegationToCreate =
        new gov.ca.cwds.data.persistence.cms.Allegation("2345678ABC",
            (Allegation) allegationDomain.toArray()[0], "2016-10-31");
    when(allegationDao.create(any(gov.ca.cwds.data.persistence.cms.Allegation.class)))
        .thenReturn(allegationToCreate);

    Set<CrossReport> crossReportDomain =
        MAPPER.readValue(fixture("fixtures/domain/ScreeningToReferral/valid/validCrossReport.json"),
            new TypeReference<Set<CrossReport>>() {});
    gov.ca.cwds.data.persistence.cms.CrossReport crossReportToCreate =
        new gov.ca.cwds.data.persistence.cms.CrossReport("3456789ABC",
            // ((CrossReport) crossReportDomain).getThirdId(),
            (CrossReport) crossReportDomain.toArray()[0], "OXA");
    when(crossReportDao.create(any(gov.ca.cwds.data.persistence.cms.CrossReport.class)))
        .thenReturn(crossReportToCreate);

    Reporter reporterDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validReporter.json"), Reporter.class);
    gov.ca.cwds.data.persistence.cms.Reporter reporterToCreate =
        new gov.ca.cwds.data.persistence.cms.Reporter(reporterDomain, "ABC");
    when(reporterDao.create(any(gov.ca.cwds.data.persistence.cms.Reporter.class)))
        .thenReturn(reporterToCreate);

    Address addressDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validAddress.json"), Address.class);
    gov.ca.cwds.data.persistence.cms.Address addressToCreate =
        new gov.ca.cwds.data.persistence.cms.Address("3456789ABC", addressDomain, "ABC");
    when(addressDao.create(any(gov.ca.cwds.data.persistence.cms.Address.class)))
        .thenReturn(addressToCreate);
    when(addressDao.find("3456789ABC")).thenReturn(addressToCreate);

    ClientAddress clientAddressDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validClientAddress.json"),
        ClientAddress.class);
    gov.ca.cwds.data.persistence.cms.ClientAddress clientAddressToCreate =
        new gov.ca.cwds.data.persistence.cms.ClientAddress("3456789ABC", clientAddressDomain,
            "ABC");
    when(clientAddressDao.create(any(gov.ca.cwds.data.persistence.cms.ClientAddress.class)))
        .thenReturn(clientAddressToCreate);
    when(clientAddressDao.find("3456789ABC")).thenReturn(clientAddressToCreate);

    LongText longTextDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validLongText.json"), LongText.class);
    gov.ca.cwds.data.persistence.cms.LongText longTextToCreate =
        new gov.ca.cwds.data.persistence.cms.LongText("567890ABC", longTextDomain, "ABC");
    when(longTextDao.create(any(gov.ca.cwds.data.persistence.cms.LongText.class)))
        .thenReturn(longTextToCreate);

    ScreeningToReferral screeningToReferral = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/participantWithBlankRole.json"),
        ScreeningToReferral.class);

    Response response = screeningToReferralService.create(screeningToReferral);

    assertThat(response.getClass(), is(PostedScreeningToReferral.class));
  }

  @SuppressWarnings("javadoc")
  @Test
  public void testScreeningToReferralMultipleCrossReportsSuccess() throws Exception {
    Referral referralDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validReferral.json"), Referral.class);
    gov.ca.cwds.data.persistence.cms.Referral referralToCreate =
        new gov.ca.cwds.data.persistence.cms.Referral("0123456ABC", referralDomain, "2016-10-31");
    when(referralDao.create(any(gov.ca.cwds.data.persistence.cms.Referral.class)))
        .thenReturn(referralToCreate);

    ChildClient childClient = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/childClient.json"), ChildClient.class);
    gov.ca.cwds.data.persistence.cms.ChildClient childClientToCreate =
        new gov.ca.cwds.data.persistence.cms.ChildClient("1234567ABC", childClient, "0XA");
    when(childClientDao.create(any(gov.ca.cwds.data.persistence.cms.ChildClient.class)))
        .thenReturn(childClientToCreate);

    Set<Client> clientDomain =
        MAPPER.readValue(fixture("fixtures/domain/ScreeningToReferral/valid/validClient.json"),
            new TypeReference<Set<Client>>() {});
    gov.ca.cwds.data.persistence.cms.Client clientToCreate =
        new gov.ca.cwds.data.persistence.cms.Client("1234567ABC",
            (Client) clientDomain.toArray()[0], "2016-10-31");
    when(clientDao.create(any(gov.ca.cwds.data.persistence.cms.Client.class)))
        .thenReturn(clientToCreate);

    Set<ReferralClient> referralClientDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validReferralClient.json"),
        new TypeReference<Set<ReferralClient>>() {});
    gov.ca.cwds.data.persistence.cms.ReferralClient referralClientToCreate =
        new gov.ca.cwds.data.persistence.cms.ReferralClient(
            (ReferralClient) referralClientDomain.toArray()[0], "2016-10-31");
    when(referralClientDao.create(any(gov.ca.cwds.data.persistence.cms.ReferralClient.class)))
        .thenReturn(referralClientToCreate);

    Set<Allegation> allegationDomain =
        MAPPER.readValue(fixture("fixtures/domain/ScreeningToReferral/valid/validAllegation.json"),
            new TypeReference<Set<Allegation>>() {});
    gov.ca.cwds.data.persistence.cms.Allegation allegationToCreate =
        new gov.ca.cwds.data.persistence.cms.Allegation("2345678ABC",
            (Allegation) allegationDomain.toArray()[0], "2016-10-31");
    when(allegationDao.create(any(gov.ca.cwds.data.persistence.cms.Allegation.class)))
        .thenReturn(allegationToCreate);

    Set<CrossReport> crossReportDomain =
        MAPPER.readValue(fixture("fixtures/domain/ScreeningToReferral/valid/validCrossReport.json"),
            new TypeReference<Set<CrossReport>>() {});
    gov.ca.cwds.data.persistence.cms.CrossReport crossReportToCreate =
        new gov.ca.cwds.data.persistence.cms.CrossReport("3456789ABC",
            // ((CrossReport) crossReportDomain).getThirdId(),
            (CrossReport) crossReportDomain.toArray()[0], "OXA");
    when(crossReportDao.create(any(gov.ca.cwds.data.persistence.cms.CrossReport.class)))
        .thenReturn(crossReportToCreate);

    Reporter reporterDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validReporter.json"), Reporter.class);
    gov.ca.cwds.data.persistence.cms.Reporter reporterToCreate =
        new gov.ca.cwds.data.persistence.cms.Reporter(reporterDomain, "ABC");
    when(reporterDao.create(any(gov.ca.cwds.data.persistence.cms.Reporter.class)))
        .thenReturn(reporterToCreate);

    Address addressDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validAddress.json"), Address.class);
    gov.ca.cwds.data.persistence.cms.Address addressToCreate =
        new gov.ca.cwds.data.persistence.cms.Address("345678ABC", addressDomain, "ABC");
    when(addressDao.create(any(gov.ca.cwds.data.persistence.cms.Address.class)))
        .thenReturn(addressToCreate);

    ClientAddress clientAddressDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validClientAddress.json"),
        ClientAddress.class);
    gov.ca.cwds.data.persistence.cms.ClientAddress clientAddressToCreate =
        new gov.ca.cwds.data.persistence.cms.ClientAddress("456789ABC", clientAddressDomain, "ABC");
    when(clientAddressDao.create(any(gov.ca.cwds.data.persistence.cms.ClientAddress.class)))
        .thenReturn(clientAddressToCreate);

    LongText longTextDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validLongText.json"), LongText.class);
    gov.ca.cwds.data.persistence.cms.LongText longTextToCreate =
        new gov.ca.cwds.data.persistence.cms.LongText("567890ABC", longTextDomain, "ABC");
    when(longTextDao.create(any(gov.ca.cwds.data.persistence.cms.LongText.class)))
        .thenReturn(longTextToCreate);

    ScreeningToReferral screeningToReferral = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validMultipleCrossReports.json"),
        ScreeningToReferral.class);

    try {
      Response response = screeningToReferralService.create(screeningToReferral);
      assertThat(response.getClass(), is(PostedScreeningToReferral.class));
    } catch (Exception e) {
      // System.out.println("error = " + e.getMessage());
      Assert.fail("Unexpected ServiceException was thrown" + e.getMessage());
    }

  }

  @SuppressWarnings("javadoc")
  @Test
  public void testScreeningToReferralWithoutCrossReportsSuccess() throws Exception {
    Referral referralDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validReferral.json"), Referral.class);
    gov.ca.cwds.data.persistence.cms.Referral referralToCreate =
        new gov.ca.cwds.data.persistence.cms.Referral("0123456ABC", referralDomain, "2016-10-31");
    when(referralDao.create(any(gov.ca.cwds.data.persistence.cms.Referral.class)))
        .thenReturn(referralToCreate);

    Set<Client> clientDomain =
        MAPPER.readValue(fixture("fixtures/domain/ScreeningToReferral/valid/validClient.json"),
            new TypeReference<Set<Client>>() {});
    gov.ca.cwds.data.persistence.cms.Client clientToCreate =
        new gov.ca.cwds.data.persistence.cms.Client("1234567ABC",
            (Client) clientDomain.toArray()[0], "2016-10-31");
    when(clientDao.create(any(gov.ca.cwds.data.persistence.cms.Client.class)))
        .thenReturn(clientToCreate);
    when(clientDao.find("1234567ABC")).thenReturn(clientToCreate);

    ChildClient childClient = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/childClient.json"), ChildClient.class);
    gov.ca.cwds.data.persistence.cms.ChildClient childClientToCreate =
        new gov.ca.cwds.data.persistence.cms.ChildClient("1234567ABC", childClient, "0XA");
    when(childClientDao.create(any(gov.ca.cwds.data.persistence.cms.ChildClient.class)))
        .thenReturn(childClientToCreate);

    Set<ReferralClient> referralClientDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validReferralClient.json"),
        new TypeReference<Set<ReferralClient>>() {});
    gov.ca.cwds.data.persistence.cms.ReferralClient referralClientToCreate =
        new gov.ca.cwds.data.persistence.cms.ReferralClient(
            (ReferralClient) referralClientDomain.toArray()[0], "2016-10-31");
    when(referralClientDao.create(any(gov.ca.cwds.data.persistence.cms.ReferralClient.class)))
        .thenReturn(referralClientToCreate);

    Set<Allegation> allegationDomain =
        MAPPER.readValue(fixture("fixtures/domain/ScreeningToReferral/valid/validAllegation.json"),
            new TypeReference<Set<Allegation>>() {});
    gov.ca.cwds.data.persistence.cms.Allegation allegationToCreate =
        new gov.ca.cwds.data.persistence.cms.Allegation("2345678ABC",
            (Allegation) allegationDomain.toArray()[0], "2016-10-31");
    when(allegationDao.create(any(gov.ca.cwds.data.persistence.cms.Allegation.class)))
        .thenReturn(allegationToCreate);

    Set<CrossReport> crossReportDomain =
        MAPPER.readValue(fixture("fixtures/domain/ScreeningToReferral/valid/validCrossReport.json"),
            new TypeReference<Set<CrossReport>>() {});
    gov.ca.cwds.data.persistence.cms.CrossReport crossReportToCreate =
        new gov.ca.cwds.data.persistence.cms.CrossReport("3456789ABC",
            // ((CrossReport) crossReportDomain).getThirdId(),
            (CrossReport) crossReportDomain.toArray()[0], "OXA");
    when(crossReportDao.create(any(gov.ca.cwds.data.persistence.cms.CrossReport.class)))
        .thenReturn(crossReportToCreate);

    Reporter reporterDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validReporter.json"), Reporter.class);
    gov.ca.cwds.data.persistence.cms.Reporter reporterToCreate =
        new gov.ca.cwds.data.persistence.cms.Reporter(reporterDomain, "ABC");
    when(reporterDao.create(any(gov.ca.cwds.data.persistence.cms.Reporter.class)))
        .thenReturn(reporterToCreate);

    Address addressDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validAddress.json"), Address.class);
    gov.ca.cwds.data.persistence.cms.Address addressToCreate =
        new gov.ca.cwds.data.persistence.cms.Address("3456789ABC", addressDomain, "ABC");
    when(addressDao.create(any(gov.ca.cwds.data.persistence.cms.Address.class)))
        .thenReturn(addressToCreate);
    when(addressDao.find("3456789ABC")).thenReturn(addressToCreate);

    ClientAddress clientAddressDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validClientAddress.json"),
        ClientAddress.class);
    gov.ca.cwds.data.persistence.cms.ClientAddress clientAddressToCreate =
        new gov.ca.cwds.data.persistence.cms.ClientAddress("3456789ABC", clientAddressDomain,
            "ABC");
    when(clientAddressDao.create(any(gov.ca.cwds.data.persistence.cms.ClientAddress.class)))
        .thenReturn(clientAddressToCreate);
    when(clientAddressDao.find("3456789ABC")).thenReturn(clientAddressToCreate);

    LongText longTextDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validLongText.json"), LongText.class);
    gov.ca.cwds.data.persistence.cms.LongText longTextToCreate =
        new gov.ca.cwds.data.persistence.cms.LongText("567890ABC", longTextDomain, "ABC");
    when(longTextDao.create(any(gov.ca.cwds.data.persistence.cms.LongText.class)))
        .thenReturn(longTextToCreate);

    ScreeningToReferral screeningToReferral = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validWithoutCrossReports.json"),
        ScreeningToReferral.class);

    try {
      Response response = screeningToReferralService.create(screeningToReferral);
      assertThat(response.getClass(), is(PostedScreeningToReferral.class));
    } catch (Exception e) {
      // System.out.println("error = " + e.getMessage());
      Assert.fail("Unexpected ServiceException was thrown" + e.getMessage());
    }

  }

  @SuppressWarnings("javadoc")
  @Test
  // R - 00836 Self rep Ind Limit
  public void testMoreThanOneSelfReportedFail() throws Exception {
    Referral referralDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validReferral.json"), Referral.class);
    gov.ca.cwds.data.persistence.cms.Referral referralToCreate =
        new gov.ca.cwds.data.persistence.cms.Referral("0123456ABC", referralDomain, "2016-10-31");
    when(referralDao.create(any(gov.ca.cwds.data.persistence.cms.Referral.class)))
        .thenReturn(referralToCreate);

    ChildClient childClient = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/childClient.json"), ChildClient.class);
    gov.ca.cwds.data.persistence.cms.ChildClient childClientToCreate =
        new gov.ca.cwds.data.persistence.cms.ChildClient("1234567ABC", childClient, "0XA");
    when(childClientDao.create(any(gov.ca.cwds.data.persistence.cms.ChildClient.class)))
        .thenReturn(childClientToCreate);

    Set<Client> clientDomain =
        MAPPER.readValue(fixture("fixtures/domain/ScreeningToReferral/valid/validClient.json"),
            new TypeReference<Set<Client>>() {});
    gov.ca.cwds.data.persistence.cms.Client clientToCreate =
        new gov.ca.cwds.data.persistence.cms.Client("1234567ABC",
            (Client) clientDomain.toArray()[0], "2016-10-31");
    when(clientDao.create(any(gov.ca.cwds.data.persistence.cms.Client.class)))
        .thenReturn(clientToCreate);
    when(clientDao.find("1234567ABC")).thenReturn(clientToCreate);

    Set<ReferralClient> referralClientDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validReferralClient.json"),
        new TypeReference<Set<ReferralClient>>() {});
    gov.ca.cwds.data.persistence.cms.ReferralClient referralClientToCreate =
        new gov.ca.cwds.data.persistence.cms.ReferralClient(
            (ReferralClient) referralClientDomain.toArray()[0], "2016-10-31");
    when(referralClientDao.create(any(gov.ca.cwds.data.persistence.cms.ReferralClient.class)))
        .thenReturn(referralClientToCreate);

    Set<Allegation> allegationDomain =
        MAPPER.readValue(fixture("fixtures/domain/ScreeningToReferral/valid/validAllegation.json"),
            new TypeReference<Set<Allegation>>() {});
    gov.ca.cwds.data.persistence.cms.Allegation allegationToCreate =
        new gov.ca.cwds.data.persistence.cms.Allegation("2345678ABC",
            (Allegation) allegationDomain.toArray()[0], "2016-10-31");
    when(allegationDao.create(any(gov.ca.cwds.data.persistence.cms.Allegation.class)))
        .thenReturn(allegationToCreate);

    Set<CrossReport> crossReportDomain =
        MAPPER.readValue(fixture("fixtures/domain/ScreeningToReferral/valid/validCrossReport.json"),
            new TypeReference<Set<CrossReport>>() {});
    gov.ca.cwds.data.persistence.cms.CrossReport crossReportToCreate =
        new gov.ca.cwds.data.persistence.cms.CrossReport("3456789ABC",
            // ((CrossReport) crossReportDomain).getThirdId(),
            (CrossReport) crossReportDomain.toArray()[0], "OXA");
    when(crossReportDao.create(any(gov.ca.cwds.data.persistence.cms.CrossReport.class)))
        .thenReturn(crossReportToCreate);

    Reporter reporterDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validReporter.json"), Reporter.class);
    gov.ca.cwds.data.persistence.cms.Reporter reporterToCreate =
        new gov.ca.cwds.data.persistence.cms.Reporter(reporterDomain, "ABC");
    when(reporterDao.create(any(gov.ca.cwds.data.persistence.cms.Reporter.class)))
        .thenReturn(reporterToCreate);

    Address addressDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validAddress.json"), Address.class);
    gov.ca.cwds.data.persistence.cms.Address addressToCreate =
        new gov.ca.cwds.data.persistence.cms.Address("3456789ABC", addressDomain, "ABC");
    when(addressDao.create(any(gov.ca.cwds.data.persistence.cms.Address.class)))
        .thenReturn(addressToCreate);
    when(addressDao.find("3456789ABC")).thenReturn(addressToCreate);

    ClientAddress clientAddressDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validClientAddress.json"),
        ClientAddress.class);
    gov.ca.cwds.data.persistence.cms.ClientAddress clientAddressToCreate =
        new gov.ca.cwds.data.persistence.cms.ClientAddress("3456789ABC", clientAddressDomain,
            "ABC");
    when(clientAddressDao.create(any(gov.ca.cwds.data.persistence.cms.ClientAddress.class)))
        .thenReturn(clientAddressToCreate);
    when(clientAddressDao.find("3456789ABC")).thenReturn(clientAddressToCreate);


    LongText longTextDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validLongText.json"), LongText.class);
    gov.ca.cwds.data.persistence.cms.LongText longTextToCreate =
        new gov.ca.cwds.data.persistence.cms.LongText("567890ABC", longTextDomain, "ABC");
    when(longTextDao.create(any(gov.ca.cwds.data.persistence.cms.LongText.class)))
        .thenReturn(longTextToCreate);

    ScreeningToReferral screeningToReferral = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/invalid/moreThanOneSelfReported.json"),
        ScreeningToReferral.class);

    try {
      this.screeningToReferralService.create(screeningToReferral);
      Assert.fail("Expected ServiceException was not thrown");
    } catch (Exception e) {
      assertThat(e.getMessage().contains("Incompatiable participants included in request"),
          is(equalTo(true)));
    }
  }

  @SuppressWarnings("javadoc")
  @Test
  public void testMoreThanOneReporterFail() throws Exception {
    Referral referralDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validReferral.json"), Referral.class);
    gov.ca.cwds.data.persistence.cms.Referral referralToCreate =
        new gov.ca.cwds.data.persistence.cms.Referral("0123456ABC", referralDomain, "2016-10-31");
    when(referralDao.create(any(gov.ca.cwds.data.persistence.cms.Referral.class)))
        .thenReturn(referralToCreate);

    ChildClient childClient = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/childClient.json"), ChildClient.class);
    gov.ca.cwds.data.persistence.cms.ChildClient childClientToCreate =
        new gov.ca.cwds.data.persistence.cms.ChildClient("1234567ABC", childClient, "0XA");
    when(childClientDao.create(any(gov.ca.cwds.data.persistence.cms.ChildClient.class)))
        .thenReturn(childClientToCreate);

    Set<Client> clientDomain =
        MAPPER.readValue(fixture("fixtures/domain/ScreeningToReferral/valid/validClient.json"),
            new TypeReference<Set<Client>>() {});
    gov.ca.cwds.data.persistence.cms.Client clientToCreate =
        new gov.ca.cwds.data.persistence.cms.Client("1234567ABC",
            (Client) clientDomain.toArray()[0], "2016-10-31");
    when(clientDao.create(any(gov.ca.cwds.data.persistence.cms.Client.class)))
        .thenReturn(clientToCreate);

    Set<ReferralClient> referralClientDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validReferralClient.json"),
        new TypeReference<Set<ReferralClient>>() {});
    gov.ca.cwds.data.persistence.cms.ReferralClient referralClientToCreate =
        new gov.ca.cwds.data.persistence.cms.ReferralClient(
            (ReferralClient) referralClientDomain.toArray()[0], "2016-10-31");
    when(referralClientDao.create(any(gov.ca.cwds.data.persistence.cms.ReferralClient.class)))
        .thenReturn(referralClientToCreate);

    Set<Allegation> allegationDomain =
        MAPPER.readValue(fixture("fixtures/domain/ScreeningToReferral/valid/validAllegation.json"),
            new TypeReference<Set<Allegation>>() {});
    gov.ca.cwds.data.persistence.cms.Allegation allegationToCreate =
        new gov.ca.cwds.data.persistence.cms.Allegation("2345678ABC",
            (Allegation) allegationDomain.toArray()[0], "2016-10-31");
    when(allegationDao.create(any(gov.ca.cwds.data.persistence.cms.Allegation.class)))
        .thenReturn(allegationToCreate);

    Set<CrossReport> crossReportDomain =
        MAPPER.readValue(fixture("fixtures/domain/ScreeningToReferral/valid/validCrossReport.json"),
            new TypeReference<Set<CrossReport>>() {});
    gov.ca.cwds.data.persistence.cms.CrossReport crossReportToCreate =
        new gov.ca.cwds.data.persistence.cms.CrossReport("3456789ABC",
            // ((CrossReport) crossReportDomain).getThirdId(),
            (CrossReport) crossReportDomain.toArray()[0], "OXA");
    when(crossReportDao.create(any(gov.ca.cwds.data.persistence.cms.CrossReport.class)))
        .thenReturn(crossReportToCreate);

    Reporter reporterDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validReporter.json"), Reporter.class);
    gov.ca.cwds.data.persistence.cms.Reporter reporterToCreate =
        new gov.ca.cwds.data.persistence.cms.Reporter(reporterDomain, "ABC");
    when(reporterDao.create(any(gov.ca.cwds.data.persistence.cms.Reporter.class)))
        .thenReturn(reporterToCreate);


    Address addressDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validAddress.json"), Address.class);
    gov.ca.cwds.data.persistence.cms.Address addressToCreate =
        new gov.ca.cwds.data.persistence.cms.Address("345678ABC", addressDomain, "ABC");
    when(addressDao.create(any(gov.ca.cwds.data.persistence.cms.Address.class)))
        .thenReturn(addressToCreate);

    ClientAddress clientAddressDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validClientAddress.json"),
        ClientAddress.class);
    gov.ca.cwds.data.persistence.cms.ClientAddress clientAddressToCreate =
        new gov.ca.cwds.data.persistence.cms.ClientAddress("456789ABC", clientAddressDomain, "ABC");
    when(clientAddressDao.create(any(gov.ca.cwds.data.persistence.cms.ClientAddress.class)))
        .thenReturn(clientAddressToCreate);

    LongText longTextDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validLongText.json"), LongText.class);
    gov.ca.cwds.data.persistence.cms.LongText longTextToCreate =
        new gov.ca.cwds.data.persistence.cms.LongText("567890ABC", longTextDomain, "ABC");
    when(longTextDao.create(any(gov.ca.cwds.data.persistence.cms.LongText.class)))
        .thenReturn(longTextToCreate);

    ScreeningToReferral screeningToReferral = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/invalid/moreThanOneReporter.json"),
        ScreeningToReferral.class);

    try {
      this.screeningToReferralService.create(screeningToReferral);
      Assert.fail("Expected ServiceException was not thrown");
    } catch (Exception e) {
      assertThat(e.getMessage().contains("Incompatiable participants included in request"),
          is(equalTo(true)));
    }
  }

  @SuppressWarnings("javadoc")
  @Test
  public void testMoreThanOneVictimSuccess() throws Exception {
    Referral referralDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validReferral.json"), Referral.class);
    gov.ca.cwds.data.persistence.cms.Referral referralToCreate =
        new gov.ca.cwds.data.persistence.cms.Referral("0123456ABC", referralDomain, "2016-10-31");
    when(referralDao.create(any(gov.ca.cwds.data.persistence.cms.Referral.class)))
        .thenReturn(referralToCreate);

    ChildClient childClient = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/childClient.json"), ChildClient.class);
    gov.ca.cwds.data.persistence.cms.ChildClient childClientToCreate =
        new gov.ca.cwds.data.persistence.cms.ChildClient("1234567ABC", childClient, "0XA");
    when(childClientDao.create(any(gov.ca.cwds.data.persistence.cms.ChildClient.class)))
        .thenReturn(childClientToCreate);

    Set<Client> clientDomain =
        MAPPER.readValue(fixture("fixtures/domain/ScreeningToReferral/valid/validClient.json"),
            new TypeReference<Set<Client>>() {});
    gov.ca.cwds.data.persistence.cms.Client clientToCreate =
        new gov.ca.cwds.data.persistence.cms.Client("1234567ABC",
            (Client) clientDomain.toArray()[0], "2016-10-31");
    when(clientDao.create(any(gov.ca.cwds.data.persistence.cms.Client.class)))
        .thenReturn(clientToCreate);

    Set<ReferralClient> referralClientDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validReferralClient.json"),
        new TypeReference<Set<ReferralClient>>() {});
    gov.ca.cwds.data.persistence.cms.ReferralClient referralClientToCreate =
        new gov.ca.cwds.data.persistence.cms.ReferralClient(
            (ReferralClient) referralClientDomain.toArray()[0], "2016-10-31");
    when(referralClientDao.create(any(gov.ca.cwds.data.persistence.cms.ReferralClient.class)))
        .thenReturn(referralClientToCreate);

    Set<Allegation> allegationDomain =
        MAPPER.readValue(fixture("fixtures/domain/ScreeningToReferral/valid/validAllegation.json"),
            new TypeReference<Set<Allegation>>() {});
    gov.ca.cwds.data.persistence.cms.Allegation allegationToCreate =
        new gov.ca.cwds.data.persistence.cms.Allegation("2345678ABC",
            (Allegation) allegationDomain.toArray()[0], "2016-10-31");
    when(allegationDao.create(any(gov.ca.cwds.data.persistence.cms.Allegation.class)))
        .thenReturn(allegationToCreate);

    Set<CrossReport> crossReportDomain =
        MAPPER.readValue(fixture("fixtures/domain/ScreeningToReferral/valid/validCrossReport.json"),
            new TypeReference<Set<CrossReport>>() {});
    gov.ca.cwds.data.persistence.cms.CrossReport crossReportToCreate =
        new gov.ca.cwds.data.persistence.cms.CrossReport("3456789ABC",
            // ((CrossReport) crossReportDomain).getThirdId(),
            (CrossReport) crossReportDomain.toArray()[0], "OXA");
    when(crossReportDao.create(any(gov.ca.cwds.data.persistence.cms.CrossReport.class)))
        .thenReturn(crossReportToCreate);

    Reporter reporterDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validReporter.json"), Reporter.class);
    gov.ca.cwds.data.persistence.cms.Reporter reporterToCreate =
        new gov.ca.cwds.data.persistence.cms.Reporter(reporterDomain, "ABC");
    when(reporterDao.create(any(gov.ca.cwds.data.persistence.cms.Reporter.class)))
        .thenReturn(reporterToCreate);


    Address addressDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validAddress.json"), Address.class);
    gov.ca.cwds.data.persistence.cms.Address addressToCreate =
        new gov.ca.cwds.data.persistence.cms.Address("345678ABC", addressDomain, "ABC");
    when(addressDao.create(any(gov.ca.cwds.data.persistence.cms.Address.class)))
        .thenReturn(addressToCreate);

    ClientAddress clientAddressDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validClientAddress.json"),
        ClientAddress.class);
    gov.ca.cwds.data.persistence.cms.ClientAddress clientAddressToCreate =
        new gov.ca.cwds.data.persistence.cms.ClientAddress("456789ABC", clientAddressDomain, "ABC");
    when(clientAddressDao.create(any(gov.ca.cwds.data.persistence.cms.ClientAddress.class)))
        .thenReturn(clientAddressToCreate);

    LongText longTextDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validLongText.json"), LongText.class);
    gov.ca.cwds.data.persistence.cms.LongText longTextToCreate =
        new gov.ca.cwds.data.persistence.cms.LongText("567890ABC", longTextDomain, "ABC");
    when(longTextDao.create(any(gov.ca.cwds.data.persistence.cms.LongText.class)))
        .thenReturn(longTextToCreate);

    ScreeningToReferral screeningToReferral = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/moreThanOneVictim.json"),
        ScreeningToReferral.class);

    try {
      Response response = screeningToReferralService.create(screeningToReferral);
      assertThat(response.getClass(), is(PostedScreeningToReferral.class));
    } catch (Exception e) {
      // System.out.println("error = " + e.getMessage());
      Assert.fail("Unexpected ServiceException was thrown" + e.getMessage());
    }
  }

  @SuppressWarnings("javadoc")
  @Test
  public void testNoVictimFail() throws Exception {
    Referral referralDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validReferral.json"), Referral.class);
    gov.ca.cwds.data.persistence.cms.Referral referralToCreate =
        new gov.ca.cwds.data.persistence.cms.Referral("0123456ABC", referralDomain, "2016-10-31");
    when(referralDao.create(any(gov.ca.cwds.data.persistence.cms.Referral.class)))
        .thenReturn(referralToCreate);

    Set<Client> clientDomain =
        MAPPER.readValue(fixture("fixtures/domain/ScreeningToReferral/valid/validClient.json"),
            new TypeReference<Set<Client>>() {});
    gov.ca.cwds.data.persistence.cms.Client clientToCreate =
        new gov.ca.cwds.data.persistence.cms.Client("1234567ABC",
            (Client) clientDomain.toArray()[0], "2016-10-31");
    when(clientDao.create(any(gov.ca.cwds.data.persistence.cms.Client.class)))
        .thenReturn(clientToCreate);

    Set<ReferralClient> referralClientDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validReferralClient.json"),
        new TypeReference<Set<ReferralClient>>() {});
    gov.ca.cwds.data.persistence.cms.ReferralClient referralClientToCreate =
        new gov.ca.cwds.data.persistence.cms.ReferralClient(
            (ReferralClient) referralClientDomain.toArray()[0], "2016-10-31");
    when(referralClientDao.create(any(gov.ca.cwds.data.persistence.cms.ReferralClient.class)))
        .thenReturn(referralClientToCreate);

    Set<Allegation> allegationDomain =
        MAPPER.readValue(fixture("fixtures/domain/ScreeningToReferral/valid/validAllegation.json"),
            new TypeReference<Set<Allegation>>() {});
    gov.ca.cwds.data.persistence.cms.Allegation allegationToCreate =
        new gov.ca.cwds.data.persistence.cms.Allegation("2345678ABC",
            (Allegation) allegationDomain.toArray()[0], "2016-10-31");
    when(allegationDao.create(any(gov.ca.cwds.data.persistence.cms.Allegation.class)))
        .thenReturn(allegationToCreate);

    Set<CrossReport> crossReportDomain =
        MAPPER.readValue(fixture("fixtures/domain/ScreeningToReferral/valid/validCrossReport.json"),
            new TypeReference<Set<CrossReport>>() {});
    gov.ca.cwds.data.persistence.cms.CrossReport crossReportToCreate =
        new gov.ca.cwds.data.persistence.cms.CrossReport("3456789ABC",
            // ((CrossReport) crossReportDomain).getThirdId(),
            (CrossReport) crossReportDomain.toArray()[0], "OXA");
    when(crossReportDao.create(any(gov.ca.cwds.data.persistence.cms.CrossReport.class)))
        .thenReturn(crossReportToCreate);

    Reporter reporterDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validReporter.json"), Reporter.class);
    gov.ca.cwds.data.persistence.cms.Reporter reporterToCreate =
        new gov.ca.cwds.data.persistence.cms.Reporter(reporterDomain, "ABC");
    when(reporterDao.create(any(gov.ca.cwds.data.persistence.cms.Reporter.class)))
        .thenReturn(reporterToCreate);


    Address addressDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validAddress.json"), Address.class);
    gov.ca.cwds.data.persistence.cms.Address addressToCreate =
        new gov.ca.cwds.data.persistence.cms.Address("345678ABC", addressDomain, "ABC");
    when(addressDao.create(any(gov.ca.cwds.data.persistence.cms.Address.class)))
        .thenReturn(addressToCreate);

    ClientAddress clientAddressDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validClientAddress.json"),
        ClientAddress.class);
    gov.ca.cwds.data.persistence.cms.ClientAddress clientAddressToCreate =
        new gov.ca.cwds.data.persistence.cms.ClientAddress("456789ABC", clientAddressDomain, "ABC");
    when(clientAddressDao.create(any(gov.ca.cwds.data.persistence.cms.ClientAddress.class)))
        .thenReturn(clientAddressToCreate);

    LongText longTextDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validLongText.json"), LongText.class);
    gov.ca.cwds.data.persistence.cms.LongText longTextToCreate =
        new gov.ca.cwds.data.persistence.cms.LongText("567890ABC", longTextDomain, "ABC");
    when(longTextDao.create(any(gov.ca.cwds.data.persistence.cms.LongText.class)))
        .thenReturn(longTextToCreate);

    ScreeningToReferral screeningToReferral =
        MAPPER.readValue(fixture("fixtures/domain/ScreeningToReferral/invalid/noVictim.json"),
            ScreeningToReferral.class);

    try {
      this.screeningToReferralService.create(screeningToReferral);
      Assert.fail("Expected ServiceException was not thrown");
    } catch (Exception e) {
      assertThat(e.getMessage().contains("Incompatiable participants included in request"),
          is(equalTo(true)));
    }

  }

  @SuppressWarnings("javadoc")
  @Test
  // R - 00831
  public void testIncompatiableRolesAnonymousSelfFail() throws Exception {
    Referral referralDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validReferral.json"), Referral.class);
    gov.ca.cwds.data.persistence.cms.Referral referralToCreate =
        new gov.ca.cwds.data.persistence.cms.Referral("0123456ABC", referralDomain, "2016-10-31");
    when(referralDao.create(any(gov.ca.cwds.data.persistence.cms.Referral.class)))
        .thenReturn(referralToCreate);

    ChildClient childClient = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/childClient.json"), ChildClient.class);
    gov.ca.cwds.data.persistence.cms.ChildClient childClientToCreate =
        new gov.ca.cwds.data.persistence.cms.ChildClient("1234567ABC", childClient, "0XA");
    when(childClientDao.create(any(gov.ca.cwds.data.persistence.cms.ChildClient.class)))
        .thenReturn(childClientToCreate);

    Set<Client> clientDomain =
        MAPPER.readValue(fixture("fixtures/domain/ScreeningToReferral/valid/validClient.json"),
            new TypeReference<Set<Client>>() {});
    gov.ca.cwds.data.persistence.cms.Client clientToCreate =
        new gov.ca.cwds.data.persistence.cms.Client("1234567ABC",
            (Client) clientDomain.toArray()[0], "2016-10-31");
    when(clientDao.create(any(gov.ca.cwds.data.persistence.cms.Client.class)))
        .thenReturn(clientToCreate);

    Set<ReferralClient> referralClientDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validReferralClient.json"),
        new TypeReference<Set<ReferralClient>>() {});
    gov.ca.cwds.data.persistence.cms.ReferralClient referralClientToCreate =
        new gov.ca.cwds.data.persistence.cms.ReferralClient(
            (ReferralClient) referralClientDomain.toArray()[0], "2016-10-31");
    when(referralClientDao.create(any(gov.ca.cwds.data.persistence.cms.ReferralClient.class)))
        .thenReturn(referralClientToCreate);

    Set<Allegation> allegationDomain =
        MAPPER.readValue(fixture("fixtures/domain/ScreeningToReferral/valid/validAllegation.json"),
            new TypeReference<Set<Allegation>>() {});
    gov.ca.cwds.data.persistence.cms.Allegation allegationToCreate =
        new gov.ca.cwds.data.persistence.cms.Allegation("2345678ABC",
            (Allegation) allegationDomain.toArray()[0], "2016-10-31");
    when(allegationDao.create(any(gov.ca.cwds.data.persistence.cms.Allegation.class)))
        .thenReturn(allegationToCreate);

    Set<CrossReport> crossReportDomain =
        MAPPER.readValue(fixture("fixtures/domain/ScreeningToReferral/valid/validCrossReport.json"),
            new TypeReference<Set<CrossReport>>() {});
    gov.ca.cwds.data.persistence.cms.CrossReport crossReportToCreate =
        new gov.ca.cwds.data.persistence.cms.CrossReport("3456789ABC",
            // ((CrossReport) crossReportDomain).getThirdId(),
            (CrossReport) crossReportDomain.toArray()[0], "OXA");
    when(crossReportDao.create(any(gov.ca.cwds.data.persistence.cms.CrossReport.class)))
        .thenReturn(crossReportToCreate);

    Reporter reporterDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validReporter.json"), Reporter.class);
    gov.ca.cwds.data.persistence.cms.Reporter reporterToCreate =
        new gov.ca.cwds.data.persistence.cms.Reporter(reporterDomain, "ABC");
    when(reporterDao.create(any(gov.ca.cwds.data.persistence.cms.Reporter.class)))
        .thenReturn(reporterToCreate);


    Address addressDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validAddress.json"), Address.class);
    gov.ca.cwds.data.persistence.cms.Address addressToCreate =
        new gov.ca.cwds.data.persistence.cms.Address("345678ABC", addressDomain, "ABC");
    when(addressDao.create(any(gov.ca.cwds.data.persistence.cms.Address.class)))
        .thenReturn(addressToCreate);

    ClientAddress clientAddressDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validClientAddress.json"),
        ClientAddress.class);
    gov.ca.cwds.data.persistence.cms.ClientAddress clientAddressToCreate =
        new gov.ca.cwds.data.persistence.cms.ClientAddress("456789ABC", clientAddressDomain, "ABC");
    when(clientAddressDao.create(any(gov.ca.cwds.data.persistence.cms.ClientAddress.class)))
        .thenReturn(clientAddressToCreate);

    LongText longTextDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validLongText.json"), LongText.class);
    gov.ca.cwds.data.persistence.cms.LongText longTextToCreate =
        new gov.ca.cwds.data.persistence.cms.LongText("567890ABC", longTextDomain, "ABC");
    when(longTextDao.create(any(gov.ca.cwds.data.persistence.cms.LongText.class)))
        .thenReturn(longTextToCreate);

    ScreeningToReferral screeningToReferral = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/invalid/incompatiableRoleAnonymousSelf.json"),
        ScreeningToReferral.class);

    try {
      this.screeningToReferralService.create(screeningToReferral);
      Assert.fail("Expected ServiceException was not thrown");
    } catch (Exception e) {
      assertThat(e.getMessage().contains("Participant contains incompatiable roles"),
          is(equalTo(true)));
    }

  }

  @SuppressWarnings("javadoc")
  @Test
  public void testIncompatiableRolesAnonymousVictimFail() throws Exception {
    Referral referralDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validReferral.json"), Referral.class);
    gov.ca.cwds.data.persistence.cms.Referral referralToCreate =
        new gov.ca.cwds.data.persistence.cms.Referral("0123456ABC", referralDomain, "2016-10-31");
    when(referralDao.create(any(gov.ca.cwds.data.persistence.cms.Referral.class)))
        .thenReturn(referralToCreate);

    ChildClient childClient = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/childClient.json"), ChildClient.class);
    gov.ca.cwds.data.persistence.cms.ChildClient childClientToCreate =
        new gov.ca.cwds.data.persistence.cms.ChildClient("1234567ABC", childClient, "0XA");
    when(childClientDao.create(any(gov.ca.cwds.data.persistence.cms.ChildClient.class)))
        .thenReturn(childClientToCreate);

    Set<Client> clientDomain =
        MAPPER.readValue(fixture("fixtures/domain/ScreeningToReferral/valid/validClient.json"),
            new TypeReference<Set<Client>>() {});
    gov.ca.cwds.data.persistence.cms.Client clientToCreate =
        new gov.ca.cwds.data.persistence.cms.Client("1234567ABC",
            (Client) clientDomain.toArray()[0], "2016-10-31");
    when(clientDao.create(any(gov.ca.cwds.data.persistence.cms.Client.class)))
        .thenReturn(clientToCreate);

    Set<ReferralClient> referralClientDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validReferralClient.json"),
        new TypeReference<Set<ReferralClient>>() {});
    gov.ca.cwds.data.persistence.cms.ReferralClient referralClientToCreate =
        new gov.ca.cwds.data.persistence.cms.ReferralClient(
            (ReferralClient) referralClientDomain.toArray()[0], "2016-10-31");
    when(referralClientDao.create(any(gov.ca.cwds.data.persistence.cms.ReferralClient.class)))
        .thenReturn(referralClientToCreate);

    Set<Allegation> allegationDomain =
        MAPPER.readValue(fixture("fixtures/domain/ScreeningToReferral/valid/validAllegation.json"),
            new TypeReference<Set<Allegation>>() {});
    gov.ca.cwds.data.persistence.cms.Allegation allegationToCreate =
        new gov.ca.cwds.data.persistence.cms.Allegation("2345678ABC",
            (Allegation) allegationDomain.toArray()[0], "2016-10-31");
    when(allegationDao.create(any(gov.ca.cwds.data.persistence.cms.Allegation.class)))
        .thenReturn(allegationToCreate);

    Set<CrossReport> crossReportDomain =
        MAPPER.readValue(fixture("fixtures/domain/ScreeningToReferral/valid/validCrossReport.json"),
            new TypeReference<Set<CrossReport>>() {});
    gov.ca.cwds.data.persistence.cms.CrossReport crossReportToCreate =
        new gov.ca.cwds.data.persistence.cms.CrossReport("3456789ABC",
            // ((CrossReport) crossReportDomain).getThirdId(),
            (CrossReport) crossReportDomain.toArray()[0], "OXA");
    when(crossReportDao.create(any(gov.ca.cwds.data.persistence.cms.CrossReport.class)))
        .thenReturn(crossReportToCreate);

    Reporter reporterDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validReporter.json"), Reporter.class);
    gov.ca.cwds.data.persistence.cms.Reporter reporterToCreate =
        new gov.ca.cwds.data.persistence.cms.Reporter(reporterDomain, "ABC");
    when(reporterDao.create(any(gov.ca.cwds.data.persistence.cms.Reporter.class)))
        .thenReturn(reporterToCreate);


    Address addressDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validAddress.json"), Address.class);
    gov.ca.cwds.data.persistence.cms.Address addressToCreate =
        new gov.ca.cwds.data.persistence.cms.Address("345678ABC", addressDomain, "ABC");
    when(addressDao.create(any(gov.ca.cwds.data.persistence.cms.Address.class)))
        .thenReturn(addressToCreate);

    ClientAddress clientAddressDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validClientAddress.json"),
        ClientAddress.class);
    gov.ca.cwds.data.persistence.cms.ClientAddress clientAddressToCreate =
        new gov.ca.cwds.data.persistence.cms.ClientAddress("456789ABC", clientAddressDomain, "ABC");
    when(clientAddressDao.create(any(gov.ca.cwds.data.persistence.cms.ClientAddress.class)))
        .thenReturn(clientAddressToCreate);

    LongText longTextDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validLongText.json"), LongText.class);
    gov.ca.cwds.data.persistence.cms.LongText longTextToCreate =
        new gov.ca.cwds.data.persistence.cms.LongText("567890ABC", longTextDomain, "ABC");
    when(longTextDao.create(any(gov.ca.cwds.data.persistence.cms.LongText.class)))
        .thenReturn(longTextToCreate);

    ScreeningToReferral screeningToReferral = MAPPER.readValue(
        fixture(
            "fixtures/domain/ScreeningToReferral/invalid/incompatiableRoleAnonymousVictim.json"),
        ScreeningToReferral.class);

    try {
      this.screeningToReferralService.create(screeningToReferral);
      Assert.fail("Expected ServiceException was not thrown");
    } catch (Exception e) {
      assertThat(e.getMessage().contains("Participant contains incompatiable roles"),
          is(equalTo(true)));
    }

  }

  @SuppressWarnings("javadoc")
  @Test
  public void testIncompatiableRolesAnonymousReporterFail() throws Exception {
    Referral referralDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validReferral.json"), Referral.class);
    gov.ca.cwds.data.persistence.cms.Referral referralToCreate =
        new gov.ca.cwds.data.persistence.cms.Referral("0123456ABC", referralDomain, "2016-10-31");
    when(referralDao.create(any(gov.ca.cwds.data.persistence.cms.Referral.class)))
        .thenReturn(referralToCreate);

    ChildClient childClient = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/childClient.json"), ChildClient.class);
    gov.ca.cwds.data.persistence.cms.ChildClient childClientToCreate =
        new gov.ca.cwds.data.persistence.cms.ChildClient("1234567ABC", childClient, "0XA");
    when(childClientDao.create(any(gov.ca.cwds.data.persistence.cms.ChildClient.class)))
        .thenReturn(childClientToCreate);

    Set<Client> clientDomain =
        MAPPER.readValue(fixture("fixtures/domain/ScreeningToReferral/valid/validClient.json"),
            new TypeReference<Set<Client>>() {});
    gov.ca.cwds.data.persistence.cms.Client clientToCreate =
        new gov.ca.cwds.data.persistence.cms.Client("1234567ABC",
            (Client) clientDomain.toArray()[0], "2016-10-31");
    when(clientDao.create(any(gov.ca.cwds.data.persistence.cms.Client.class)))
        .thenReturn(clientToCreate);

    Set<ReferralClient> referralClientDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validReferralClient.json"),
        new TypeReference<Set<ReferralClient>>() {});
    gov.ca.cwds.data.persistence.cms.ReferralClient referralClientToCreate =
        new gov.ca.cwds.data.persistence.cms.ReferralClient(
            (ReferralClient) referralClientDomain.toArray()[0], "2016-10-31");
    when(referralClientDao.create(any(gov.ca.cwds.data.persistence.cms.ReferralClient.class)))
        .thenReturn(referralClientToCreate);

    Set<Allegation> allegationDomain =
        MAPPER.readValue(fixture("fixtures/domain/ScreeningToReferral/valid/validAllegation.json"),
            new TypeReference<Set<Allegation>>() {});
    gov.ca.cwds.data.persistence.cms.Allegation allegationToCreate =
        new gov.ca.cwds.data.persistence.cms.Allegation("2345678ABC",
            (Allegation) allegationDomain.toArray()[0], "2016-10-31");
    when(allegationDao.create(any(gov.ca.cwds.data.persistence.cms.Allegation.class)))
        .thenReturn(allegationToCreate);

    Set<CrossReport> crossReportDomain =
        MAPPER.readValue(fixture("fixtures/domain/ScreeningToReferral/valid/validCrossReport.json"),
            new TypeReference<Set<CrossReport>>() {});
    gov.ca.cwds.data.persistence.cms.CrossReport crossReportToCreate =
        new gov.ca.cwds.data.persistence.cms.CrossReport("3456789ABC",
            // ((CrossReport) crossReportDomain).getThirdId(),
            (CrossReport) crossReportDomain.toArray()[0], "OXA");
    when(crossReportDao.create(any(gov.ca.cwds.data.persistence.cms.CrossReport.class)))
        .thenReturn(crossReportToCreate);

    Reporter reporterDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validReporter.json"), Reporter.class);
    gov.ca.cwds.data.persistence.cms.Reporter reporterToCreate =
        new gov.ca.cwds.data.persistence.cms.Reporter(reporterDomain, "ABC");
    when(reporterDao.create(any(gov.ca.cwds.data.persistence.cms.Reporter.class)))
        .thenReturn(reporterToCreate);


    Address addressDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validAddress.json"), Address.class);
    gov.ca.cwds.data.persistence.cms.Address addressToCreate =
        new gov.ca.cwds.data.persistence.cms.Address("345678ABC", addressDomain, "ABC");
    when(addressDao.create(any(gov.ca.cwds.data.persistence.cms.Address.class)))
        .thenReturn(addressToCreate);

    ClientAddress clientAddressDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validClientAddress.json"),
        ClientAddress.class);
    gov.ca.cwds.data.persistence.cms.ClientAddress clientAddressToCreate =
        new gov.ca.cwds.data.persistence.cms.ClientAddress("456789ABC", clientAddressDomain, "ABC");
    when(clientAddressDao.create(any(gov.ca.cwds.data.persistence.cms.ClientAddress.class)))
        .thenReturn(clientAddressToCreate);

    LongText longTextDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validLongText.json"), LongText.class);
    gov.ca.cwds.data.persistence.cms.LongText longTextToCreate =
        new gov.ca.cwds.data.persistence.cms.LongText("567890ABC", longTextDomain, "ABC");
    when(longTextDao.create(any(gov.ca.cwds.data.persistence.cms.LongText.class)))
        .thenReturn(longTextToCreate);

    ScreeningToReferral screeningToReferral = MAPPER.readValue(
        fixture(
            "fixtures/domain/ScreeningToReferral/invalid/incompatiableRoleAnonymousReporter.json"),
        ScreeningToReferral.class);

    try {
      this.screeningToReferralService.create(screeningToReferral);
      Assert.fail("Expected ServiceException was not thrown");
    } catch (Exception e) {

      assertThat(e.getMessage().contains("Participant contains incompatiable roles"),
          is(equalTo(true)));
    }

  }

  @SuppressWarnings("javadoc")
  @Test
  public void testIncompatiableRolesVictimPerpetratorFail() throws Exception {
    Referral referralDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validReferral.json"), Referral.class);
    gov.ca.cwds.data.persistence.cms.Referral referralToCreate =
        new gov.ca.cwds.data.persistence.cms.Referral("0123456ABC", referralDomain, "2016-10-31");
    when(referralDao.create(any(gov.ca.cwds.data.persistence.cms.Referral.class)))
        .thenReturn(referralToCreate);

    ChildClient childClient = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/childClient.json"), ChildClient.class);
    gov.ca.cwds.data.persistence.cms.ChildClient childClientToCreate =
        new gov.ca.cwds.data.persistence.cms.ChildClient("1234567ABC", childClient, "0XA");
    when(childClientDao.create(any(gov.ca.cwds.data.persistence.cms.ChildClient.class)))
        .thenReturn(childClientToCreate);

    Set<Client> clientDomain =
        MAPPER.readValue(fixture("fixtures/domain/ScreeningToReferral/valid/validClient.json"),
            new TypeReference<Set<Client>>() {});
    gov.ca.cwds.data.persistence.cms.Client clientToCreate =
        new gov.ca.cwds.data.persistence.cms.Client("1234567ABC",
            (Client) clientDomain.toArray()[0], "2016-10-31");
    when(clientDao.create(any(gov.ca.cwds.data.persistence.cms.Client.class)))
        .thenReturn(clientToCreate);

    Set<ReferralClient> referralClientDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validReferralClient.json"),
        new TypeReference<Set<ReferralClient>>() {});
    gov.ca.cwds.data.persistence.cms.ReferralClient referralClientToCreate =
        new gov.ca.cwds.data.persistence.cms.ReferralClient(
            (ReferralClient) referralClientDomain.toArray()[0], "2016-10-31");
    when(referralClientDao.create(any(gov.ca.cwds.data.persistence.cms.ReferralClient.class)))
        .thenReturn(referralClientToCreate);

    Set<Allegation> allegationDomain =
        MAPPER.readValue(fixture("fixtures/domain/ScreeningToReferral/valid/validAllegation.json"),
            new TypeReference<Set<Allegation>>() {});
    gov.ca.cwds.data.persistence.cms.Allegation allegationToCreate =
        new gov.ca.cwds.data.persistence.cms.Allegation("2345678ABC",
            (Allegation) allegationDomain.toArray()[0], "2016-10-31");
    when(allegationDao.create(any(gov.ca.cwds.data.persistence.cms.Allegation.class)))
        .thenReturn(allegationToCreate);

    Set<CrossReport> crossReportDomain =
        MAPPER.readValue(fixture("fixtures/domain/ScreeningToReferral/valid/validCrossReport.json"),
            new TypeReference<Set<CrossReport>>() {});
    gov.ca.cwds.data.persistence.cms.CrossReport crossReportToCreate =
        new gov.ca.cwds.data.persistence.cms.CrossReport("3456789ABC",
            // ((CrossReport) crossReportDomain).getThirdId(),
            (CrossReport) crossReportDomain.toArray()[0], "OXA");
    when(crossReportDao.create(any(gov.ca.cwds.data.persistence.cms.CrossReport.class)))
        .thenReturn(crossReportToCreate);

    Reporter reporterDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validReporter.json"), Reporter.class);
    gov.ca.cwds.data.persistence.cms.Reporter reporterToCreate =
        new gov.ca.cwds.data.persistence.cms.Reporter(reporterDomain, "ABC");
    when(reporterDao.create(any(gov.ca.cwds.data.persistence.cms.Reporter.class)))
        .thenReturn(reporterToCreate);


    Address addressDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validAddress.json"), Address.class);
    gov.ca.cwds.data.persistence.cms.Address addressToCreate =
        new gov.ca.cwds.data.persistence.cms.Address("345678ABC", addressDomain, "ABC");
    when(addressDao.create(any(gov.ca.cwds.data.persistence.cms.Address.class)))
        .thenReturn(addressToCreate);

    ClientAddress clientAddressDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validClientAddress.json"),
        ClientAddress.class);
    gov.ca.cwds.data.persistence.cms.ClientAddress clientAddressToCreate =
        new gov.ca.cwds.data.persistence.cms.ClientAddress("456789ABC", clientAddressDomain, "ABC");
    when(clientAddressDao.create(any(gov.ca.cwds.data.persistence.cms.ClientAddress.class)))
        .thenReturn(clientAddressToCreate);

    LongText longTextDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validLongText.json"), LongText.class);
    gov.ca.cwds.data.persistence.cms.LongText longTextToCreate =
        new gov.ca.cwds.data.persistence.cms.LongText("567890ABC", longTextDomain, "ABC");
    when(longTextDao.create(any(gov.ca.cwds.data.persistence.cms.LongText.class)))
        .thenReturn(longTextToCreate);

    ScreeningToReferral screeningToReferral = MAPPER.readValue(
        fixture(
            "fixtures/domain/ScreeningToReferral/invalid/incompatiableRoleVictimPerpetrator.json"),
        ScreeningToReferral.class);

    try {
      this.screeningToReferralService.create(screeningToReferral);
      Assert.fail("Expected ServiceException was not thrown");
    } catch (Exception e) {
      assertThat(e.getMessage().contains("Participant contains incompatiable roles"),
          is(equalTo(true)));
    }

  }

  @SuppressWarnings("javadoc")
  @Test
  public void testIncompatiableRolesMandatedNonMandatedFail() throws Exception {
    Referral referralDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validReferral.json"), Referral.class);
    gov.ca.cwds.data.persistence.cms.Referral referralToCreate =
        new gov.ca.cwds.data.persistence.cms.Referral("0123456ABC", referralDomain, "2016-10-31");
    when(referralDao.create(any(gov.ca.cwds.data.persistence.cms.Referral.class)))
        .thenReturn(referralToCreate);

    ChildClient childClient = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/childClient.json"), ChildClient.class);
    gov.ca.cwds.data.persistence.cms.ChildClient childClientToCreate =
        new gov.ca.cwds.data.persistence.cms.ChildClient("1234567ABC", childClient, "0XA");
    when(childClientDao.create(any(gov.ca.cwds.data.persistence.cms.ChildClient.class)))
        .thenReturn(childClientToCreate);

    Set<Client> clientDomain =
        MAPPER.readValue(fixture("fixtures/domain/ScreeningToReferral/valid/validClient.json"),
            new TypeReference<Set<Client>>() {});
    gov.ca.cwds.data.persistence.cms.Client clientToCreate =
        new gov.ca.cwds.data.persistence.cms.Client("1234567ABC",
            (Client) clientDomain.toArray()[0], "2016-10-31");
    when(clientDao.create(any(gov.ca.cwds.data.persistence.cms.Client.class)))
        .thenReturn(clientToCreate);

    Set<ReferralClient> referralClientDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validReferralClient.json"),
        new TypeReference<Set<ReferralClient>>() {});
    gov.ca.cwds.data.persistence.cms.ReferralClient referralClientToCreate =
        new gov.ca.cwds.data.persistence.cms.ReferralClient(
            (ReferralClient) referralClientDomain.toArray()[0], "2016-10-31");
    when(referralClientDao.create(any(gov.ca.cwds.data.persistence.cms.ReferralClient.class)))
        .thenReturn(referralClientToCreate);

    Set<Allegation> allegationDomain =
        MAPPER.readValue(fixture("fixtures/domain/ScreeningToReferral/valid/validAllegation.json"),
            new TypeReference<Set<Allegation>>() {});
    gov.ca.cwds.data.persistence.cms.Allegation allegationToCreate =
        new gov.ca.cwds.data.persistence.cms.Allegation("2345678ABC",
            (Allegation) allegationDomain.toArray()[0], "2016-10-31");
    when(allegationDao.create(any(gov.ca.cwds.data.persistence.cms.Allegation.class)))
        .thenReturn(allegationToCreate);

    Set<CrossReport> crossReportDomain =
        MAPPER.readValue(fixture("fixtures/domain/ScreeningToReferral/valid/validCrossReport.json"),
            new TypeReference<Set<CrossReport>>() {});
    gov.ca.cwds.data.persistence.cms.CrossReport crossReportToCreate =
        new gov.ca.cwds.data.persistence.cms.CrossReport("3456789ABC",
            // ((CrossReport) crossReportDomain).getThirdId(),
            (CrossReport) crossReportDomain.toArray()[0], "OXA");
    when(crossReportDao.create(any(gov.ca.cwds.data.persistence.cms.CrossReport.class)))
        .thenReturn(crossReportToCreate);

    Reporter reporterDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validReporter.json"), Reporter.class);
    gov.ca.cwds.data.persistence.cms.Reporter reporterToCreate =
        new gov.ca.cwds.data.persistence.cms.Reporter(reporterDomain, "ABC");
    when(reporterDao.create(any(gov.ca.cwds.data.persistence.cms.Reporter.class)))
        .thenReturn(reporterToCreate);


    Address addressDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validAddress.json"), Address.class);
    gov.ca.cwds.data.persistence.cms.Address addressToCreate =
        new gov.ca.cwds.data.persistence.cms.Address("345678ABC", addressDomain, "ABC");
    when(addressDao.create(any(gov.ca.cwds.data.persistence.cms.Address.class)))
        .thenReturn(addressToCreate);

    ClientAddress clientAddressDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validClientAddress.json"),
        ClientAddress.class);
    gov.ca.cwds.data.persistence.cms.ClientAddress clientAddressToCreate =
        new gov.ca.cwds.data.persistence.cms.ClientAddress("456789ABC", clientAddressDomain, "ABC");
    when(clientAddressDao.create(any(gov.ca.cwds.data.persistence.cms.ClientAddress.class)))
        .thenReturn(clientAddressToCreate);

    LongText longTextDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validLongText.json"), LongText.class);
    gov.ca.cwds.data.persistence.cms.LongText longTextToCreate =
        new gov.ca.cwds.data.persistence.cms.LongText("567890ABC", longTextDomain, "ABC");
    when(longTextDao.create(any(gov.ca.cwds.data.persistence.cms.LongText.class)))
        .thenReturn(longTextToCreate);

    ScreeningToReferral screeningToReferral = MAPPER.readValue(
        fixture(
            "fixtures/domain/ScreeningToReferral/invalid/incompatiableRoleMandatedNonMandated.json"),
        ScreeningToReferral.class);

    try {
      this.screeningToReferralService.create(screeningToReferral);
      Assert.fail("Expected ServiceException was not thrown");
    } catch (Exception e) {
      assertThat(e.getMessage().contains("Participant contains incompatiable roles"),
          is(equalTo(true)));
    }

  }

  @SuppressWarnings("javadoc")
  @Test
  public void testMoreThanOneParticipantRoleSuccess() throws Exception {
    Referral referralDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validReferral.json"), Referral.class);
    gov.ca.cwds.data.persistence.cms.Referral referralToCreate =
        new gov.ca.cwds.data.persistence.cms.Referral("0123456ABC", referralDomain, "2016-10-31");
    when(referralDao.create(any(gov.ca.cwds.data.persistence.cms.Referral.class)))
        .thenReturn(referralToCreate);

    ChildClient childClient = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/childClient.json"), ChildClient.class);
    gov.ca.cwds.data.persistence.cms.ChildClient childClientToCreate =
        new gov.ca.cwds.data.persistence.cms.ChildClient("1234567ABC", childClient, "0XA");
    when(childClientDao.create(any(gov.ca.cwds.data.persistence.cms.ChildClient.class)))
        .thenReturn(childClientToCreate);

    Set<Client> clientDomain =
        MAPPER.readValue(fixture("fixtures/domain/ScreeningToReferral/valid/validClient.json"),
            new TypeReference<Set<Client>>() {});
    gov.ca.cwds.data.persistence.cms.Client clientToCreate =
        new gov.ca.cwds.data.persistence.cms.Client("1234567ABC",
            (Client) clientDomain.toArray()[0], "2016-10-31");
    when(clientDao.create(any(gov.ca.cwds.data.persistence.cms.Client.class)))
        .thenReturn(clientToCreate);
    when(clientDao.find("1234567ABC")).thenReturn(clientToCreate);

    Set<ReferralClient> referralClientDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validReferralClient.json"),
        new TypeReference<Set<ReferralClient>>() {});
    gov.ca.cwds.data.persistence.cms.ReferralClient referralClientToCreate =
        new gov.ca.cwds.data.persistence.cms.ReferralClient(
            (ReferralClient) referralClientDomain.toArray()[0], "2016-10-31");
    when(referralClientDao.create(any(gov.ca.cwds.data.persistence.cms.ReferralClient.class)))
        .thenReturn(referralClientToCreate);

    Set<Allegation> allegationDomain =
        MAPPER.readValue(fixture("fixtures/domain/ScreeningToReferral/valid/validAllegation.json"),
            new TypeReference<Set<Allegation>>() {});
    gov.ca.cwds.data.persistence.cms.Allegation allegationToCreate =
        new gov.ca.cwds.data.persistence.cms.Allegation("2345678ABC",
            (Allegation) allegationDomain.toArray()[0], "2016-10-31");
    when(allegationDao.create(any(gov.ca.cwds.data.persistence.cms.Allegation.class)))
        .thenReturn(allegationToCreate);

    Set<CrossReport> crossReportDomain =
        MAPPER.readValue(fixture("fixtures/domain/ScreeningToReferral/valid/validCrossReport.json"),
            new TypeReference<Set<CrossReport>>() {});
    gov.ca.cwds.data.persistence.cms.CrossReport crossReportToCreate =
        new gov.ca.cwds.data.persistence.cms.CrossReport("3456789ABC",
            // ((CrossReport) crossReportDomain).getThirdId(),
            (CrossReport) crossReportDomain.toArray()[0], "OXA");
    when(crossReportDao.create(any(gov.ca.cwds.data.persistence.cms.CrossReport.class)))
        .thenReturn(crossReportToCreate);

    Reporter reporterDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validReporter.json"), Reporter.class);
    gov.ca.cwds.data.persistence.cms.Reporter reporterToCreate =
        new gov.ca.cwds.data.persistence.cms.Reporter(reporterDomain, "ABC");
    when(reporterDao.create(any(gov.ca.cwds.data.persistence.cms.Reporter.class)))
        .thenReturn(reporterToCreate);


    Address addressDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validAddress.json"), Address.class);
    gov.ca.cwds.data.persistence.cms.Address addressToCreate =
        new gov.ca.cwds.data.persistence.cms.Address("3456789ABC", addressDomain, "ABC");
    when(addressDao.create(any(gov.ca.cwds.data.persistence.cms.Address.class)))
        .thenReturn(addressToCreate);
    when(addressDao.find("3456789ABC")).thenReturn(addressToCreate);

    ClientAddress clientAddressDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validClientAddress.json"),
        ClientAddress.class);
    gov.ca.cwds.data.persistence.cms.ClientAddress clientAddressToCreate =
        new gov.ca.cwds.data.persistence.cms.ClientAddress("3456789ABC", clientAddressDomain,
            "ABC");
    when(clientAddressDao.create(any(gov.ca.cwds.data.persistence.cms.ClientAddress.class)))
        .thenReturn(clientAddressToCreate);
    when(clientAddressDao.find("3456789ABC")).thenReturn(clientAddressToCreate);

    LongText longTextDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validLongText.json"), LongText.class);
    gov.ca.cwds.data.persistence.cms.LongText longTextToCreate =
        new gov.ca.cwds.data.persistence.cms.LongText("567890ABC", longTextDomain, "ABC");
    when(longTextDao.create(any(gov.ca.cwds.data.persistence.cms.LongText.class)))
        .thenReturn(longTextToCreate);

    ScreeningToReferral screeningToReferral = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validMoreThanOneParticipantRole.json"),
        ScreeningToReferral.class);

    try {
      Response response = screeningToReferralService.create(screeningToReferral);
      assertThat(response.getClass(), is(PostedScreeningToReferral.class));
    } catch (Exception e) {
      // System.out.println("error = " + e.getMessage());
      Assert.fail("Unexpected ServiceException was thrown" + e.getMessage());
    }
  }

  @SuppressWarnings("javadoc")
  @Test
  public void testMultipleAddressPerParticipantSuccess() throws Exception {
    Referral referralDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validReferral.json"), Referral.class);
    gov.ca.cwds.data.persistence.cms.Referral referralToCreate =
        new gov.ca.cwds.data.persistence.cms.Referral("0123456ABC", referralDomain, "2016-10-31");
    when(referralDao.create(any(gov.ca.cwds.data.persistence.cms.Referral.class)))
        .thenReturn(referralToCreate);

    ChildClient childClient = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/childClient.json"), ChildClient.class);
    gov.ca.cwds.data.persistence.cms.ChildClient childClientToCreate =
        new gov.ca.cwds.data.persistence.cms.ChildClient("1234567ABC", childClient, "0XA");
    when(childClientDao.create(any(gov.ca.cwds.data.persistence.cms.ChildClient.class)))
        .thenReturn(childClientToCreate);

    Set<Client> clientDomain =
        MAPPER.readValue(fixture("fixtures/domain/ScreeningToReferral/valid/validClient.json"),
            new TypeReference<Set<Client>>() {});
    gov.ca.cwds.data.persistence.cms.Client clientToCreate =
        new gov.ca.cwds.data.persistence.cms.Client("1234567ABC",
            (Client) clientDomain.toArray()[0], "2016-10-31");
    when(clientDao.create(any(gov.ca.cwds.data.persistence.cms.Client.class)))
        .thenReturn(clientToCreate);
    when(clientDao.find("1234567ABC")).thenReturn(clientToCreate);

    Set<ReferralClient> referralClientDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validReferralClient.json"),
        new TypeReference<Set<ReferralClient>>() {});
    gov.ca.cwds.data.persistence.cms.ReferralClient referralClientToCreate =
        new gov.ca.cwds.data.persistence.cms.ReferralClient(
            (ReferralClient) referralClientDomain.toArray()[0], "2016-10-31");
    when(referralClientDao.create(any(gov.ca.cwds.data.persistence.cms.ReferralClient.class)))
        .thenReturn(referralClientToCreate);

    Set<Allegation> allegationDomain =
        MAPPER.readValue(fixture("fixtures/domain/ScreeningToReferral/valid/validAllegation.json"),
            new TypeReference<Set<Allegation>>() {});
    gov.ca.cwds.data.persistence.cms.Allegation allegationToCreate =
        new gov.ca.cwds.data.persistence.cms.Allegation("2345678ABC",
            (Allegation) allegationDomain.toArray()[0], "2016-10-31");
    when(allegationDao.create(any(gov.ca.cwds.data.persistence.cms.Allegation.class)))
        .thenReturn(allegationToCreate);

    Set<CrossReport> crossReportDomain =
        MAPPER.readValue(fixture("fixtures/domain/ScreeningToReferral/valid/validCrossReport.json"),
            new TypeReference<Set<CrossReport>>() {});
    gov.ca.cwds.data.persistence.cms.CrossReport crossReportToCreate =
        new gov.ca.cwds.data.persistence.cms.CrossReport("3456789ABC",
            // ((CrossReport) crossReportDomain).getThirdId(),
            (CrossReport) crossReportDomain.toArray()[0], "OXA");
    when(crossReportDao.create(any(gov.ca.cwds.data.persistence.cms.CrossReport.class)))
        .thenReturn(crossReportToCreate);

    Reporter reporterDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validReporter.json"), Reporter.class);
    gov.ca.cwds.data.persistence.cms.Reporter reporterToCreate =
        new gov.ca.cwds.data.persistence.cms.Reporter(reporterDomain, "ABC");
    when(reporterDao.create(any(gov.ca.cwds.data.persistence.cms.Reporter.class)))
        .thenReturn(reporterToCreate);


    Address addressDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validAddress.json"), Address.class);
    gov.ca.cwds.data.persistence.cms.Address addressToCreate =
        new gov.ca.cwds.data.persistence.cms.Address("3456789ABC", addressDomain, "ABC");
    when(addressDao.create(any(gov.ca.cwds.data.persistence.cms.Address.class)))
        .thenReturn(addressToCreate);
    when(addressDao.find("3456789ABC")).thenReturn(addressToCreate);

    ClientAddress clientAddressDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validClientAddress.json"),
        ClientAddress.class);
    gov.ca.cwds.data.persistence.cms.ClientAddress clientAddressToCreate =
        new gov.ca.cwds.data.persistence.cms.ClientAddress("3456789ABC", clientAddressDomain,
            "ABC");
    when(clientAddressDao.create(any(gov.ca.cwds.data.persistence.cms.ClientAddress.class)))
        .thenReturn(clientAddressToCreate);
    when(clientAddressDao.find("3456789ABC")).thenReturn(clientAddressToCreate);

    LongText longTextDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validLongText.json"), LongText.class);
    gov.ca.cwds.data.persistence.cms.LongText longTextToCreate =
        new gov.ca.cwds.data.persistence.cms.LongText("567890ABC", longTextDomain, "ABC");
    when(longTextDao.create(any(gov.ca.cwds.data.persistence.cms.LongText.class)))
        .thenReturn(longTextToCreate);

    ScreeningToReferral screeningToReferral = MAPPER.readValue(
        fixture(
            "fixtures/domain/ScreeningToReferral/valid/validMultipleAddressPerParticipant.json"),
        ScreeningToReferral.class);

    try {
      Response response = screeningToReferralService.create(screeningToReferral);
      assertThat(response.getClass(), is(PostedScreeningToReferral.class));
    } catch (Exception e) {
      Assert.fail("Unexpected ServiceException was thrown" + e.getMessage());
    }
  }

  @SuppressWarnings("javadoc")
  @Test
  public void testNoAddressPerParticipantSuccess() throws Exception {
    Referral referralDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validReferral.json"), Referral.class);
    gov.ca.cwds.data.persistence.cms.Referral referralToCreate =
        new gov.ca.cwds.data.persistence.cms.Referral("0123456ABC", referralDomain, "2016-10-31");
    when(referralDao.create(any(gov.ca.cwds.data.persistence.cms.Referral.class)))
        .thenReturn(referralToCreate);

    ChildClient childClient = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/childClient.json"), ChildClient.class);
    gov.ca.cwds.data.persistence.cms.ChildClient childClientToCreate =
        new gov.ca.cwds.data.persistence.cms.ChildClient("1234567ABC", childClient, "0XA");
    when(childClientDao.create(any(gov.ca.cwds.data.persistence.cms.ChildClient.class)))
        .thenReturn(childClientToCreate);

    Set<Client> clientDomain =
        MAPPER.readValue(fixture("fixtures/domain/ScreeningToReferral/valid/validClient.json"),
            new TypeReference<Set<Client>>() {});
    gov.ca.cwds.data.persistence.cms.Client clientToCreate =
        new gov.ca.cwds.data.persistence.cms.Client("1234567ABC",
            (Client) clientDomain.toArray()[0], "2016-10-31");
    when(clientDao.create(any(gov.ca.cwds.data.persistence.cms.Client.class)))
        .thenReturn(clientToCreate);
    when(clientDao.find("1234567ABC")).thenReturn(clientToCreate);

    Set<ReferralClient> referralClientDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validReferralClient.json"),
        new TypeReference<Set<ReferralClient>>() {});
    gov.ca.cwds.data.persistence.cms.ReferralClient referralClientToCreate =
        new gov.ca.cwds.data.persistence.cms.ReferralClient(
            (ReferralClient) referralClientDomain.toArray()[0], "2016-10-31");
    when(referralClientDao.create(any(gov.ca.cwds.data.persistence.cms.ReferralClient.class)))
        .thenReturn(referralClientToCreate);

    Set<Allegation> allegationDomain =
        MAPPER.readValue(fixture("fixtures/domain/ScreeningToReferral/valid/validAllegation.json"),
            new TypeReference<Set<Allegation>>() {});
    gov.ca.cwds.data.persistence.cms.Allegation allegationToCreate =
        new gov.ca.cwds.data.persistence.cms.Allegation("2345678ABC",
            (Allegation) allegationDomain.toArray()[0], "2016-10-31");
    when(allegationDao.create(any(gov.ca.cwds.data.persistence.cms.Allegation.class)))
        .thenReturn(allegationToCreate);

    Set<CrossReport> crossReportDomain =
        MAPPER.readValue(fixture("fixtures/domain/ScreeningToReferral/valid/validCrossReport.json"),
            new TypeReference<Set<CrossReport>>() {});
    gov.ca.cwds.data.persistence.cms.CrossReport crossReportToCreate =
        new gov.ca.cwds.data.persistence.cms.CrossReport("3456789ABC",
            // ((CrossReport) crossReportDomain).getThirdId(),
            (CrossReport) crossReportDomain.toArray()[0], "OXA");
    when(crossReportDao.create(any(gov.ca.cwds.data.persistence.cms.CrossReport.class)))
        .thenReturn(crossReportToCreate);

    Reporter reporterDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validReporter.json"), Reporter.class);
    gov.ca.cwds.data.persistence.cms.Reporter reporterToCreate =
        new gov.ca.cwds.data.persistence.cms.Reporter(reporterDomain, "ABC");
    when(reporterDao.create(any(gov.ca.cwds.data.persistence.cms.Reporter.class)))
        .thenReturn(reporterToCreate);


    Address addressDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validAddress.json"), Address.class);
    gov.ca.cwds.data.persistence.cms.Address addressToCreate =
        new gov.ca.cwds.data.persistence.cms.Address("3456789ABC", addressDomain, "ABC");
    when(addressDao.create(any(gov.ca.cwds.data.persistence.cms.Address.class)))
        .thenReturn(addressToCreate);
    when(addressDao.find("3456789ABC")).thenReturn(addressToCreate);

    ClientAddress clientAddressDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validClientAddress.json"),
        ClientAddress.class);
    gov.ca.cwds.data.persistence.cms.ClientAddress clientAddressToCreate =
        new gov.ca.cwds.data.persistence.cms.ClientAddress("3456789ABC", clientAddressDomain,
            "ABC");
    when(clientAddressDao.create(any(gov.ca.cwds.data.persistence.cms.ClientAddress.class)))
        .thenReturn(clientAddressToCreate);
    when(clientAddressDao.find("3456789ABC")).thenReturn(clientAddressToCreate);

    LongText longTextDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validLongText.json"), LongText.class);
    gov.ca.cwds.data.persistence.cms.LongText longTextToCreate =
        new gov.ca.cwds.data.persistence.cms.LongText("567890ABC", longTextDomain, "ABC");
    when(longTextDao.create(any(gov.ca.cwds.data.persistence.cms.LongText.class)))
        .thenReturn(longTextToCreate);

    ScreeningToReferral screeningToReferral = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validNoAddressPerParticipant.json"),
        ScreeningToReferral.class);

    try {
      Response response = screeningToReferralService.create(screeningToReferral);
      assertThat(response.getClass(), is(PostedScreeningToReferral.class));
    } catch (Exception e) {
      // System.out.println("error = " + e.getMessage());
      Assert.fail("Unexpected ServiceException was thrown" + e.getMessage());
    }
  }

  @SuppressWarnings("javadoc")
  @Test
  public void testReporterWithNoAddressSuccess() throws Exception {
    Referral referralDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validReferral.json"), Referral.class);
    gov.ca.cwds.data.persistence.cms.Referral referralToCreate =
        new gov.ca.cwds.data.persistence.cms.Referral("0123456ABC", referralDomain, "2016-10-31");
    when(referralDao.create(any(gov.ca.cwds.data.persistence.cms.Referral.class)))
        .thenReturn(referralToCreate);

    ChildClient childClient = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/childClient.json"), ChildClient.class);
    gov.ca.cwds.data.persistence.cms.ChildClient childClientToCreate =
        new gov.ca.cwds.data.persistence.cms.ChildClient("1234567ABC", childClient, "0XA");
    when(childClientDao.create(any(gov.ca.cwds.data.persistence.cms.ChildClient.class)))
        .thenReturn(childClientToCreate);

    Set<Client> clientDomain =
        MAPPER.readValue(fixture("fixtures/domain/ScreeningToReferral/valid/validClient.json"),
            new TypeReference<Set<Client>>() {});
    gov.ca.cwds.data.persistence.cms.Client clientToCreate =
        new gov.ca.cwds.data.persistence.cms.Client("1234567ABC",
            (Client) clientDomain.toArray()[0], "2016-10-31");
    when(clientDao.create(any(gov.ca.cwds.data.persistence.cms.Client.class)))
        .thenReturn(clientToCreate);
    when(clientDao.find("1234567ABC")).thenReturn(clientToCreate);

    Set<ReferralClient> referralClientDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validReferralClient.json"),
        new TypeReference<Set<ReferralClient>>() {});
    gov.ca.cwds.data.persistence.cms.ReferralClient referralClientToCreate =
        new gov.ca.cwds.data.persistence.cms.ReferralClient(
            (ReferralClient) referralClientDomain.toArray()[0], "2016-10-31");
    when(referralClientDao.create(any(gov.ca.cwds.data.persistence.cms.ReferralClient.class)))
        .thenReturn(referralClientToCreate);

    Set<Allegation> allegationDomain =
        MAPPER.readValue(fixture("fixtures/domain/ScreeningToReferral/valid/validAllegation.json"),
            new TypeReference<Set<Allegation>>() {});
    gov.ca.cwds.data.persistence.cms.Allegation allegationToCreate =
        new gov.ca.cwds.data.persistence.cms.Allegation("2345678ABC",
            (Allegation) allegationDomain.toArray()[0], "2016-10-31");
    when(allegationDao.create(any(gov.ca.cwds.data.persistence.cms.Allegation.class)))
        .thenReturn(allegationToCreate);

    Set<CrossReport> crossReportDomain =
        MAPPER.readValue(fixture("fixtures/domain/ScreeningToReferral/valid/validCrossReport.json"),
            new TypeReference<Set<CrossReport>>() {});
    gov.ca.cwds.data.persistence.cms.CrossReport crossReportToCreate =
        new gov.ca.cwds.data.persistence.cms.CrossReport("3456789ABC",
            // ((CrossReport) crossReportDomain).getThirdId(),
            (CrossReport) crossReportDomain.toArray()[0], "OXA");
    when(crossReportDao.create(any(gov.ca.cwds.data.persistence.cms.CrossReport.class)))
        .thenReturn(crossReportToCreate);

    Reporter reporterDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validReporter.json"), Reporter.class);
    gov.ca.cwds.data.persistence.cms.Reporter reporterToCreate =
        new gov.ca.cwds.data.persistence.cms.Reporter(reporterDomain, "ABC");
    when(reporterDao.create(any(gov.ca.cwds.data.persistence.cms.Reporter.class)))
        .thenReturn(reporterToCreate);

    Address addressDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validAddress.json"), Address.class);
    gov.ca.cwds.data.persistence.cms.Address addressToCreate =
        new gov.ca.cwds.data.persistence.cms.Address("3456789ABC", addressDomain, "ABC");
    when(addressDao.create(any(gov.ca.cwds.data.persistence.cms.Address.class)))
        .thenReturn(addressToCreate);
    when(addressDao.find("3456789ABC")).thenReturn(addressToCreate);

    ClientAddress clientAddressDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validClientAddress.json"),
        ClientAddress.class);
    gov.ca.cwds.data.persistence.cms.ClientAddress clientAddressToCreate =
        new gov.ca.cwds.data.persistence.cms.ClientAddress("456789ABC", clientAddressDomain, "ABC");
    when(clientAddressDao.create(any(gov.ca.cwds.data.persistence.cms.ClientAddress.class)))
        .thenReturn(clientAddressToCreate);
    when(clientAddressDao.find("3456789ABC")).thenReturn(clientAddressToCreate);

    LongText longTextDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validLongText.json"), LongText.class);
    gov.ca.cwds.data.persistence.cms.LongText longTextToCreate =
        new gov.ca.cwds.data.persistence.cms.LongText("567890ABC", longTextDomain, "ABC");
    when(longTextDao.create(any(gov.ca.cwds.data.persistence.cms.LongText.class)))
        .thenReturn(longTextToCreate);

    ScreeningToReferral screeningToReferral = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validNoAddressPerReporter.json"),
        ScreeningToReferral.class);

    try {
      Response response = screeningToReferralService.create(screeningToReferral);
      assertThat(response.getClass(), is(PostedScreeningToReferral.class));
    } catch (Exception e) {
      // System.out.println("error = " + e.getMessage());
      Assert.fail("Unexpected ServiceException was thrown" + e.getMessage());
    }

  }

  @SuppressWarnings("javadoc")
  @Test
  public void testWithInvalidDateTimeFormatFail() throws Exception {
    Referral referralDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validReferral.json"), Referral.class);
    gov.ca.cwds.data.persistence.cms.Referral referralToCreate =
        new gov.ca.cwds.data.persistence.cms.Referral("0123456ABC", referralDomain, "2016-10-31");
    when(referralDao.create(any(gov.ca.cwds.data.persistence.cms.Referral.class)))
        .thenReturn(referralToCreate);

    ChildClient childClient = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/childClient.json"), ChildClient.class);
    gov.ca.cwds.data.persistence.cms.ChildClient childClientToCreate =
        new gov.ca.cwds.data.persistence.cms.ChildClient("1234567ABC", childClient, "0XA");
    when(childClientDao.create(any(gov.ca.cwds.data.persistence.cms.ChildClient.class)))
        .thenReturn(childClientToCreate);

    Set<Client> clientDomain =
        MAPPER.readValue(fixture("fixtures/domain/ScreeningToReferral/valid/validClient.json"),
            new TypeReference<Set<Client>>() {});
    gov.ca.cwds.data.persistence.cms.Client clientToCreate =
        new gov.ca.cwds.data.persistence.cms.Client("1234567ABC",
            (Client) clientDomain.toArray()[0], "2016-10-31");
    when(clientDao.create(any(gov.ca.cwds.data.persistence.cms.Client.class)))
        .thenReturn(clientToCreate);
    when(clientDao.find("1234567ABC")).thenReturn(clientToCreate);

    Set<ReferralClient> referralClientDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validReferralClient.json"),
        new TypeReference<Set<ReferralClient>>() {});
    gov.ca.cwds.data.persistence.cms.ReferralClient referralClientToCreate =
        new gov.ca.cwds.data.persistence.cms.ReferralClient(
            (ReferralClient) referralClientDomain.toArray()[0], "2016-10-31");
    when(referralClientDao.create(any(gov.ca.cwds.data.persistence.cms.ReferralClient.class)))
        .thenReturn(referralClientToCreate);

    Set<Allegation> allegationDomain =
        MAPPER.readValue(fixture("fixtures/domain/ScreeningToReferral/valid/validAllegation.json"),
            new TypeReference<Set<Allegation>>() {});
    gov.ca.cwds.data.persistence.cms.Allegation allegationToCreate =
        new gov.ca.cwds.data.persistence.cms.Allegation("2345678ABC",
            (Allegation) allegationDomain.toArray()[0], "2016-10-31");
    when(allegationDao.create(any(gov.ca.cwds.data.persistence.cms.Allegation.class)))
        .thenReturn(allegationToCreate);

    Set<CrossReport> crossReportDomain =
        MAPPER.readValue(fixture("fixtures/domain/ScreeningToReferral/valid/validCrossReport.json"),
            new TypeReference<Set<CrossReport>>() {});
    gov.ca.cwds.data.persistence.cms.CrossReport crossReportToCreate =
        new gov.ca.cwds.data.persistence.cms.CrossReport("3456789ABC",
            // ((CrossReport) crossReportDomain).getThirdId(),
            (CrossReport) crossReportDomain.toArray()[0], "OXA");
    when(crossReportDao.create(any(gov.ca.cwds.data.persistence.cms.CrossReport.class)))
        .thenReturn(crossReportToCreate);

    Reporter reporterDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validReporter.json"), Reporter.class);
    gov.ca.cwds.data.persistence.cms.Reporter reporterToCreate =
        new gov.ca.cwds.data.persistence.cms.Reporter(reporterDomain, "ABC");
    when(reporterDao.create(any(gov.ca.cwds.data.persistence.cms.Reporter.class)))
        .thenReturn(reporterToCreate);

    Address addressDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validAddress.json"), Address.class);
    gov.ca.cwds.data.persistence.cms.Address addressToCreate =
        new gov.ca.cwds.data.persistence.cms.Address("3456789ABC", addressDomain, "ABC");
    when(addressDao.create(any(gov.ca.cwds.data.persistence.cms.Address.class)))
        .thenReturn(addressToCreate);
    when(addressDao.find("3456789ABC")).thenReturn(addressToCreate);

    ClientAddress clientAddressDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validClientAddress.json"),
        ClientAddress.class);
    gov.ca.cwds.data.persistence.cms.ClientAddress clientAddressToCreate =
        new gov.ca.cwds.data.persistence.cms.ClientAddress("3456789ABC", clientAddressDomain,
            "ABC");
    when(clientAddressDao.create(any(gov.ca.cwds.data.persistence.cms.ClientAddress.class)))
        .thenReturn(clientAddressToCreate);
    when(clientAddressDao.find("3456789ABC")).thenReturn(clientAddressToCreate);

    LongText longTextDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validLongText.json"), LongText.class);
    gov.ca.cwds.data.persistence.cms.LongText longTextToCreate =
        new gov.ca.cwds.data.persistence.cms.LongText("567890ABC", longTextDomain, "ABC");
    when(longTextDao.create(any(gov.ca.cwds.data.persistence.cms.LongText.class)))
        .thenReturn(longTextToCreate);

    ScreeningToReferral screeningToReferral = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/invalid/invalidDateTimeStamp.json"),
        ScreeningToReferral.class);

    try {
      this.screeningToReferralService.create(screeningToReferral);
      Assert.fail("Expected ServiceException was not thrown");
    } catch (Exception e) {
      assertThat(e.getMessage().contains("parsing Start Date/Time"), is(equalTo(true)));
    }
  }

  @SuppressWarnings("javadoc")
  @Test
  public void testNullAddressOnScreeningFail() throws Exception {
    Referral referralDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validReferral.json"), Referral.class);
    gov.ca.cwds.data.persistence.cms.Referral referralToCreate =
        new gov.ca.cwds.data.persistence.cms.Referral("0123456ABC", referralDomain, "2016-10-31");
    when(referralDao.create(any(gov.ca.cwds.data.persistence.cms.Referral.class)))
        .thenReturn(referralToCreate);

    ChildClient childClient = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/childClient.json"), ChildClient.class);
    gov.ca.cwds.data.persistence.cms.ChildClient childClientToCreate =
        new gov.ca.cwds.data.persistence.cms.ChildClient("1234567ABC", childClient, "0XA");
    when(childClientDao.create(any(gov.ca.cwds.data.persistence.cms.ChildClient.class)))
        .thenReturn(childClientToCreate);

    Set<Client> clientDomain =
        MAPPER.readValue(fixture("fixtures/domain/ScreeningToReferral/valid/validClient.json"),
            new TypeReference<Set<Client>>() {});
    gov.ca.cwds.data.persistence.cms.Client clientToCreate =
        new gov.ca.cwds.data.persistence.cms.Client("1234567ABC",
            (Client) clientDomain.toArray()[0], "2016-10-31");
    when(clientDao.create(any(gov.ca.cwds.data.persistence.cms.Client.class)))
        .thenReturn(clientToCreate);

    Set<ReferralClient> referralClientDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validReferralClient.json"),
        new TypeReference<Set<ReferralClient>>() {});
    gov.ca.cwds.data.persistence.cms.ReferralClient referralClientToCreate =
        new gov.ca.cwds.data.persistence.cms.ReferralClient(
            (ReferralClient) referralClientDomain.toArray()[0], "2016-10-31");
    when(referralClientDao.create(any(gov.ca.cwds.data.persistence.cms.ReferralClient.class)))
        .thenReturn(referralClientToCreate);

    Set<Allegation> allegationDomain =
        MAPPER.readValue(fixture("fixtures/domain/ScreeningToReferral/valid/validAllegation.json"),
            new TypeReference<Set<Allegation>>() {});
    gov.ca.cwds.data.persistence.cms.Allegation allegationToCreate =
        new gov.ca.cwds.data.persistence.cms.Allegation("2345678ABC",
            (Allegation) allegationDomain.toArray()[0], "2016-10-31");
    when(allegationDao.create(any(gov.ca.cwds.data.persistence.cms.Allegation.class)))
        .thenReturn(allegationToCreate);

    Set<CrossReport> crossReportDomain =
        MAPPER.readValue(fixture("fixtures/domain/ScreeningToReferral/valid/validCrossReport.json"),
            new TypeReference<Set<CrossReport>>() {});
    gov.ca.cwds.data.persistence.cms.CrossReport crossReportToCreate =
        new gov.ca.cwds.data.persistence.cms.CrossReport("3456789ABC",
            // ((CrossReport) crossReportDomain).getThirdId(),
            (CrossReport) crossReportDomain.toArray()[0], "OXA");
    when(crossReportDao.create(any(gov.ca.cwds.data.persistence.cms.CrossReport.class)))
        .thenReturn(crossReportToCreate);

    Reporter reporterDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validReporter.json"), Reporter.class);
    gov.ca.cwds.data.persistence.cms.Reporter reporterToCreate =
        new gov.ca.cwds.data.persistence.cms.Reporter(reporterDomain, "ABC");
    when(reporterDao.create(any(gov.ca.cwds.data.persistence.cms.Reporter.class)))
        .thenReturn(reporterToCreate);

    Address addressDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validAddress.json"), Address.class);
    gov.ca.cwds.data.persistence.cms.Address addressToCreate =
        new gov.ca.cwds.data.persistence.cms.Address("345678ABC", addressDomain, "ABC");
    when(addressDao.create(any(gov.ca.cwds.data.persistence.cms.Address.class)))
        .thenReturn(addressToCreate);

    ClientAddress clientAddressDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validClientAddress.json"),
        ClientAddress.class);
    gov.ca.cwds.data.persistence.cms.ClientAddress clientAddressToCreate =
        new gov.ca.cwds.data.persistence.cms.ClientAddress("456789ABC", clientAddressDomain, "ABC");
    when(clientAddressDao.create(any(gov.ca.cwds.data.persistence.cms.ClientAddress.class)))
        .thenReturn(clientAddressToCreate);

    LongText longTextDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validLongText.json"), LongText.class);
    gov.ca.cwds.data.persistence.cms.LongText longTextToCreate =
        new gov.ca.cwds.data.persistence.cms.LongText("567890ABC", longTextDomain, "ABC");
    when(longTextDao.create(any(gov.ca.cwds.data.persistence.cms.LongText.class)))
        .thenReturn(longTextToCreate);

    ScreeningToReferral screeningToReferral = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/invalid/nullAddressOnScreening.json"),
        ScreeningToReferral.class);

    try {
      this.screeningToReferralService.create(screeningToReferral);
      Assert.fail("Expected ServiceException was not thrown");
    } catch (Exception e) {
      System.out.println(e.getMessage());
      assertThat(e.getMessage().contains("Screening Address is null or empty"), is(equalTo(true)));
    }
  }

  @SuppressWarnings("javadoc")
  @Test
  public void testEmptyAddressOnScreeningFail() throws Exception {
    Referral referralDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validReferral.json"), Referral.class);
    gov.ca.cwds.data.persistence.cms.Referral referralToCreate =
        new gov.ca.cwds.data.persistence.cms.Referral("0123456ABC", referralDomain, "2016-10-31");
    when(referralDao.create(any(gov.ca.cwds.data.persistence.cms.Referral.class)))
        .thenReturn(referralToCreate);

    ChildClient childClient = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/childClient.json"), ChildClient.class);
    gov.ca.cwds.data.persistence.cms.ChildClient childClientToCreate =
        new gov.ca.cwds.data.persistence.cms.ChildClient("1234567ABC", childClient, "0XA");
    when(childClientDao.create(any(gov.ca.cwds.data.persistence.cms.ChildClient.class)))
        .thenReturn(childClientToCreate);

    Set<Client> clientDomain =
        MAPPER.readValue(fixture("fixtures/domain/ScreeningToReferral/valid/validClient.json"),
            new TypeReference<Set<Client>>() {});
    gov.ca.cwds.data.persistence.cms.Client clientToCreate =
        new gov.ca.cwds.data.persistence.cms.Client("1234567ABC",
            (Client) clientDomain.toArray()[0], "2016-10-31");
    when(clientDao.create(any(gov.ca.cwds.data.persistence.cms.Client.class)))
        .thenReturn(clientToCreate);

    Set<ReferralClient> referralClientDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validReferralClient.json"),
        new TypeReference<Set<ReferralClient>>() {});
    gov.ca.cwds.data.persistence.cms.ReferralClient referralClientToCreate =
        new gov.ca.cwds.data.persistence.cms.ReferralClient(
            (ReferralClient) referralClientDomain.toArray()[0], "2016-10-31");
    when(referralClientDao.create(any(gov.ca.cwds.data.persistence.cms.ReferralClient.class)))
        .thenReturn(referralClientToCreate);

    Set<Allegation> allegationDomain =
        MAPPER.readValue(fixture("fixtures/domain/ScreeningToReferral/valid/validAllegation.json"),
            new TypeReference<Set<Allegation>>() {});
    gov.ca.cwds.data.persistence.cms.Allegation allegationToCreate =
        new gov.ca.cwds.data.persistence.cms.Allegation("2345678ABC",
            (Allegation) allegationDomain.toArray()[0], "2016-10-31");
    when(allegationDao.create(any(gov.ca.cwds.data.persistence.cms.Allegation.class)))
        .thenReturn(allegationToCreate);

    Set<CrossReport> crossReportDomain =
        MAPPER.readValue(fixture("fixtures/domain/ScreeningToReferral/valid/validCrossReport.json"),
            new TypeReference<Set<CrossReport>>() {});
    gov.ca.cwds.data.persistence.cms.CrossReport crossReportToCreate =
        new gov.ca.cwds.data.persistence.cms.CrossReport("3456789ABC",
            // ((CrossReport) crossReportDomain).getThirdId(),
            (CrossReport) crossReportDomain.toArray()[0], "OXA");
    when(crossReportDao.create(any(gov.ca.cwds.data.persistence.cms.CrossReport.class)))
        .thenReturn(crossReportToCreate);

    Reporter reporterDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validReporter.json"), Reporter.class);
    gov.ca.cwds.data.persistence.cms.Reporter reporterToCreate =
        new gov.ca.cwds.data.persistence.cms.Reporter(reporterDomain, "ABC");
    when(reporterDao.create(any(gov.ca.cwds.data.persistence.cms.Reporter.class)))
        .thenReturn(reporterToCreate);


    Address addressDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validAddress.json"), Address.class);
    gov.ca.cwds.data.persistence.cms.Address addressToCreate =
        new gov.ca.cwds.data.persistence.cms.Address("345678ABC", addressDomain, "ABC");
    when(addressDao.create(any(gov.ca.cwds.data.persistence.cms.Address.class)))
        .thenReturn(addressToCreate);

    ClientAddress clientAddressDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validClientAddress.json"),
        ClientAddress.class);
    gov.ca.cwds.data.persistence.cms.ClientAddress clientAddressToCreate =
        new gov.ca.cwds.data.persistence.cms.ClientAddress("456789ABC", clientAddressDomain, "ABC");
    when(clientAddressDao.create(any(gov.ca.cwds.data.persistence.cms.ClientAddress.class)))
        .thenReturn(clientAddressToCreate);

    LongText longTextDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validLongText.json"), LongText.class);
    gov.ca.cwds.data.persistence.cms.LongText longTextToCreate =
        new gov.ca.cwds.data.persistence.cms.LongText("567890ABC", longTextDomain, "ABC");
    when(longTextDao.create(any(gov.ca.cwds.data.persistence.cms.LongText.class)))
        .thenReturn(longTextToCreate);

    ScreeningToReferral screeningToReferral = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/invalid/emptyAddressOnScreening.json"),
        ScreeningToReferral.class);

    try {
      this.screeningToReferralService.create(screeningToReferral);
      Assert.fail("Expected ServiceException was not thrown");
    } catch (Exception e) {
      // System.out.println(e.getMessage());
      assertThat(e.getMessage().contains("Referral Address is null or empty"), is(equalTo(true)));
    }
  }

  @SuppressWarnings("javadoc")
  @Test
  public void testScreeningToReferralEmptyAdditionalInfoSuccess() throws Exception {
    Referral referralDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validReferral.json"), Referral.class);
    gov.ca.cwds.data.persistence.cms.Referral referralToCreate =
        new gov.ca.cwds.data.persistence.cms.Referral("0123456ABC", referralDomain, "2016-10-31");
    when(referralDao.create(any(gov.ca.cwds.data.persistence.cms.Referral.class)))
        .thenReturn(referralToCreate);

    ChildClient childClient = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/childClient.json"), ChildClient.class);
    gov.ca.cwds.data.persistence.cms.ChildClient childClientToCreate =
        new gov.ca.cwds.data.persistence.cms.ChildClient("1234567ABC", childClient, "0XA");
    when(childClientDao.create(any(gov.ca.cwds.data.persistence.cms.ChildClient.class)))
        .thenReturn(childClientToCreate);

    Set<Client> clientDomain =
        MAPPER.readValue(fixture("fixtures/domain/ScreeningToReferral/valid/validClient.json"),
            new TypeReference<Set<Client>>() {});
    gov.ca.cwds.data.persistence.cms.Client clientToCreate =
        new gov.ca.cwds.data.persistence.cms.Client("1234567ABC",
            (Client) clientDomain.toArray()[0], "2016-10-31");
    when(clientDao.create(any(gov.ca.cwds.data.persistence.cms.Client.class)))
        .thenReturn(clientToCreate);

    Set<ReferralClient> referralClientDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validReferralClient.json"),
        new TypeReference<Set<ReferralClient>>() {});
    gov.ca.cwds.data.persistence.cms.ReferralClient referralClientToCreate =
        new gov.ca.cwds.data.persistence.cms.ReferralClient(
            (ReferralClient) referralClientDomain.toArray()[0], "2016-10-31");
    when(referralClientDao.create(any(gov.ca.cwds.data.persistence.cms.ReferralClient.class)))
        .thenReturn(referralClientToCreate);

    Set<Allegation> allegationDomain =
        MAPPER.readValue(fixture("fixtures/domain/ScreeningToReferral/valid/validAllegation.json"),
            new TypeReference<Set<Allegation>>() {});
    gov.ca.cwds.data.persistence.cms.Allegation allegationToCreate =
        new gov.ca.cwds.data.persistence.cms.Allegation("2345678ABC",
            (Allegation) allegationDomain.toArray()[0], "2016-10-31");
    when(allegationDao.create(any(gov.ca.cwds.data.persistence.cms.Allegation.class)))
        .thenReturn(allegationToCreate);

    Set<CrossReport> crossReportDomain =
        MAPPER.readValue(fixture("fixtures/domain/ScreeningToReferral/valid/validCrossReport.json"),
            new TypeReference<Set<CrossReport>>() {});
    gov.ca.cwds.data.persistence.cms.CrossReport crossReportToCreate =
        new gov.ca.cwds.data.persistence.cms.CrossReport("3456789ABC",
            // ((CrossReport) crossReportDomain).getThirdId(),
            (CrossReport) crossReportDomain.toArray()[0], "OXA");
    when(crossReportDao.create(any(gov.ca.cwds.data.persistence.cms.CrossReport.class)))
        .thenReturn(crossReportToCreate);

    Reporter reporterDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validReporter.json"), Reporter.class);
    gov.ca.cwds.data.persistence.cms.Reporter reporterToCreate =
        new gov.ca.cwds.data.persistence.cms.Reporter(reporterDomain, "ABC");
    when(reporterDao.create(any(gov.ca.cwds.data.persistence.cms.Reporter.class)))
        .thenReturn(reporterToCreate);

    Address addressDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validAddress.json"), Address.class);
    gov.ca.cwds.data.persistence.cms.Address addressToCreate =
        new gov.ca.cwds.data.persistence.cms.Address("345678ABC", addressDomain, "ABC");
    when(addressDao.create(any(gov.ca.cwds.data.persistence.cms.Address.class)))
        .thenReturn(addressToCreate);

    ClientAddress clientAddressDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validClientAddress.json"),
        ClientAddress.class);
    gov.ca.cwds.data.persistence.cms.ClientAddress clientAddressToCreate =
        new gov.ca.cwds.data.persistence.cms.ClientAddress("456789ABC", clientAddressDomain, "ABC");
    when(clientAddressDao.create(any(gov.ca.cwds.data.persistence.cms.ClientAddress.class)))
        .thenReturn(clientAddressToCreate);

    LongText longTextDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validLongText.json"), LongText.class);
    gov.ca.cwds.data.persistence.cms.LongText longTextToCreate =
        new gov.ca.cwds.data.persistence.cms.LongText("567890ABC", longTextDomain, "ABC");
    when(longTextDao.create(any(gov.ca.cwds.data.persistence.cms.LongText.class)))
        .thenReturn(longTextToCreate);

    ScreeningToReferral screeningToReferral = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/emptyAdditionalInfo.json"),
        ScreeningToReferral.class);

    Response response = screeningToReferralService.create(screeningToReferral);

    assertThat(response.getClass(), is(PostedScreeningToReferral.class));
  }

  @SuppressWarnings("javadoc")
  @Test
  public void testScreeningToReferralNullAdditionalInfoSuccess() throws Exception {
    Referral referralDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validReferral.json"), Referral.class);
    gov.ca.cwds.data.persistence.cms.Referral referralToCreate =
        new gov.ca.cwds.data.persistence.cms.Referral("0123456ABC", referralDomain, "2016-10-31");
    when(referralDao.create(any(gov.ca.cwds.data.persistence.cms.Referral.class)))
        .thenReturn(referralToCreate);

    ChildClient childClient = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/childClient.json"), ChildClient.class);
    gov.ca.cwds.data.persistence.cms.ChildClient childClientToCreate =
        new gov.ca.cwds.data.persistence.cms.ChildClient("1234567ABC", childClient, "0XA");
    when(childClientDao.create(any(gov.ca.cwds.data.persistence.cms.ChildClient.class)))
        .thenReturn(childClientToCreate);

    Set<Client> clientDomain =
        MAPPER.readValue(fixture("fixtures/domain/ScreeningToReferral/valid/validClient.json"),
            new TypeReference<Set<Client>>() {});
    gov.ca.cwds.data.persistence.cms.Client clientToCreate =
        new gov.ca.cwds.data.persistence.cms.Client("1234567ABC",
            (Client) clientDomain.toArray()[0], "2016-10-31");
    when(clientDao.create(any(gov.ca.cwds.data.persistence.cms.Client.class)))
        .thenReturn(clientToCreate);

    Set<ReferralClient> referralClientDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validReferralClient.json"),
        new TypeReference<Set<ReferralClient>>() {});
    gov.ca.cwds.data.persistence.cms.ReferralClient referralClientToCreate =
        new gov.ca.cwds.data.persistence.cms.ReferralClient(
            (ReferralClient) referralClientDomain.toArray()[0], "2016-10-31");
    when(referralClientDao.create(any(gov.ca.cwds.data.persistence.cms.ReferralClient.class)))
        .thenReturn(referralClientToCreate);

    Set<Allegation> allegationDomain =
        MAPPER.readValue(fixture("fixtures/domain/ScreeningToReferral/valid/validAllegation.json"),
            new TypeReference<Set<Allegation>>() {});
    gov.ca.cwds.data.persistence.cms.Allegation allegationToCreate =
        new gov.ca.cwds.data.persistence.cms.Allegation("2345678ABC",
            (Allegation) allegationDomain.toArray()[0], "2016-10-31");
    when(allegationDao.create(any(gov.ca.cwds.data.persistence.cms.Allegation.class)))
        .thenReturn(allegationToCreate);

    Set<CrossReport> crossReportDomain =
        MAPPER.readValue(fixture("fixtures/domain/ScreeningToReferral/valid/validCrossReport.json"),
            new TypeReference<Set<CrossReport>>() {});
    gov.ca.cwds.data.persistence.cms.CrossReport crossReportToCreate =
        new gov.ca.cwds.data.persistence.cms.CrossReport("3456789ABC",
            // ((CrossReport) crossReportDomain).getThirdId(),
            (CrossReport) crossReportDomain.toArray()[0], "OXA");
    when(crossReportDao.create(any(gov.ca.cwds.data.persistence.cms.CrossReport.class)))
        .thenReturn(crossReportToCreate);

    Reporter reporterDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validReporter.json"), Reporter.class);
    gov.ca.cwds.data.persistence.cms.Reporter reporterToCreate =
        new gov.ca.cwds.data.persistence.cms.Reporter(reporterDomain, "ABC");
    when(reporterDao.create(any(gov.ca.cwds.data.persistence.cms.Reporter.class)))
        .thenReturn(reporterToCreate);

    Address addressDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validAddress.json"), Address.class);
    gov.ca.cwds.data.persistence.cms.Address addressToCreate =
        new gov.ca.cwds.data.persistence.cms.Address("345678ABC", addressDomain, "ABC");
    when(addressDao.create(any(gov.ca.cwds.data.persistence.cms.Address.class)))
        .thenReturn(addressToCreate);

    ClientAddress clientAddressDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validClientAddress.json"),
        ClientAddress.class);
    gov.ca.cwds.data.persistence.cms.ClientAddress clientAddressToCreate =
        new gov.ca.cwds.data.persistence.cms.ClientAddress("456789ABC", clientAddressDomain, "ABC");
    when(clientAddressDao.create(any(gov.ca.cwds.data.persistence.cms.ClientAddress.class)))
        .thenReturn(clientAddressToCreate);

    LongText longTextDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validLongText.json"), LongText.class);
    gov.ca.cwds.data.persistence.cms.LongText longTextToCreate =
        new gov.ca.cwds.data.persistence.cms.LongText("567890ABC", longTextDomain, "ABC");
    when(longTextDao.create(any(gov.ca.cwds.data.persistence.cms.LongText.class)))
        .thenReturn(longTextToCreate);

    ScreeningToReferral screeningToReferral = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/nullAddtionalInfo.json"),
        ScreeningToReferral.class);

    Response response = screeningToReferralService.create(screeningToReferral);

    assertThat(response.getClass(), is(PostedScreeningToReferral.class));
  }

  @SuppressWarnings("javadoc")
  @Test
  // R - 03895
  public void testScreeningToReferralWithoutAllegationFail() throws Exception {
    Referral referralDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validReferral.json"), Referral.class);
    gov.ca.cwds.data.persistence.cms.Referral referralToCreate =
        new gov.ca.cwds.data.persistence.cms.Referral("0123456ABC", referralDomain, "2016-10-31");
    when(referralDao.create(any(gov.ca.cwds.data.persistence.cms.Referral.class)))
        .thenReturn(referralToCreate);

    ChildClient childClient = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/childClient.json"), ChildClient.class);
    gov.ca.cwds.data.persistence.cms.ChildClient childClientToCreate =
        new gov.ca.cwds.data.persistence.cms.ChildClient("1234567ABC", childClient, "0XA");
    when(childClientDao.create(any(gov.ca.cwds.data.persistence.cms.ChildClient.class)))
        .thenReturn(childClientToCreate);

    Set<Client> clientDomain =
        MAPPER.readValue(fixture("fixtures/domain/ScreeningToReferral/valid/validClient.json"),
            new TypeReference<Set<Client>>() {});
    gov.ca.cwds.data.persistence.cms.Client clientToCreate =
        new gov.ca.cwds.data.persistence.cms.Client("1234567ABC",
            (Client) clientDomain.toArray()[0], "2016-10-31");
    when(clientDao.create(any(gov.ca.cwds.data.persistence.cms.Client.class)))
        .thenReturn(clientToCreate);
    when(clientDao.find("1234567ABC")).thenReturn(clientToCreate);

    Set<ReferralClient> referralClientDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validReferralClient.json"),
        new TypeReference<Set<ReferralClient>>() {});
    gov.ca.cwds.data.persistence.cms.ReferralClient referralClientToCreate =
        new gov.ca.cwds.data.persistence.cms.ReferralClient(
            (ReferralClient) referralClientDomain.toArray()[0], "2016-10-31");
    when(referralClientDao.create(any(gov.ca.cwds.data.persistence.cms.ReferralClient.class)))
        .thenReturn(referralClientToCreate);

    Set<Allegation> allegationDomain =
        MAPPER.readValue(fixture("fixtures/domain/ScreeningToReferral/valid/validAllegation.json"),
            new TypeReference<Set<Allegation>>() {});
    gov.ca.cwds.data.persistence.cms.Allegation allegationToCreate =
        new gov.ca.cwds.data.persistence.cms.Allegation("2345678ABC",
            (Allegation) allegationDomain.toArray()[0], "2016-10-31");
    when(allegationDao.create(any(gov.ca.cwds.data.persistence.cms.Allegation.class)))
        .thenReturn(allegationToCreate);

    Set<CrossReport> crossReportDomain =
        MAPPER.readValue(fixture("fixtures/domain/ScreeningToReferral/valid/validCrossReport.json"),
            new TypeReference<Set<CrossReport>>() {});
    gov.ca.cwds.data.persistence.cms.CrossReport crossReportToCreate =
        new gov.ca.cwds.data.persistence.cms.CrossReport("3456789ABC",
            // ((CrossReport) crossReportDomain).getThirdId(),
            (CrossReport) crossReportDomain.toArray()[0], "OXA");
    when(crossReportDao.create(any(gov.ca.cwds.data.persistence.cms.CrossReport.class)))
        .thenReturn(crossReportToCreate);

    Reporter reporterDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validReporter.json"), Reporter.class);
    gov.ca.cwds.data.persistence.cms.Reporter reporterToCreate =
        new gov.ca.cwds.data.persistence.cms.Reporter(reporterDomain, "ABC");
    when(reporterDao.create(any(gov.ca.cwds.data.persistence.cms.Reporter.class)))
        .thenReturn(reporterToCreate);

    Address addressDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validAddress.json"), Address.class);
    gov.ca.cwds.data.persistence.cms.Address addressToCreate =
        new gov.ca.cwds.data.persistence.cms.Address("3456789ABC", addressDomain, "ABC");
    when(addressDao.create(any(gov.ca.cwds.data.persistence.cms.Address.class)))
        .thenReturn(addressToCreate);
    when(addressDao.find("3456789ABC")).thenReturn(addressToCreate);

    ClientAddress clientAddressDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validClientAddress.json"),
        ClientAddress.class);
    gov.ca.cwds.data.persistence.cms.ClientAddress clientAddressToCreate =
        new gov.ca.cwds.data.persistence.cms.ClientAddress("3456789ABC", clientAddressDomain,
            "ABC");
    when(clientAddressDao.create(any(gov.ca.cwds.data.persistence.cms.ClientAddress.class)))
        .thenReturn(clientAddressToCreate);
    when(clientAddressDao.find("3456789ABC")).thenReturn(clientAddressToCreate);

    LongText longTextDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validLongText.json"), LongText.class);
    gov.ca.cwds.data.persistence.cms.LongText longTextToCreate =
        new gov.ca.cwds.data.persistence.cms.LongText("567890ABC", longTextDomain, "ABC");
    when(longTextDao.create(any(gov.ca.cwds.data.persistence.cms.LongText.class)))
        .thenReturn(longTextToCreate);

    ScreeningToReferral screeningToReferral = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/invalid/withoutAllegation.json"),
        ScreeningToReferral.class);

    try {
      this.screeningToReferralService.create(screeningToReferral);
      Assert.fail("Expected ServiceException was not thrown");
    } catch (Exception e) {
      assertThat(e.getMessage().contains("Referral must have at least one Allegation"),
          is(equalTo(true)));
    }
  }

  @SuppressWarnings("javadoc")
  @Test
  public void testScreeningToReferralMultipleAllegationsSuccess() throws Exception {
    Referral referralDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validReferral.json"), Referral.class);
    gov.ca.cwds.data.persistence.cms.Referral referralToCreate =
        new gov.ca.cwds.data.persistence.cms.Referral("0123456ABC", referralDomain, "2016-10-31");
    when(referralDao.create(any(gov.ca.cwds.data.persistence.cms.Referral.class)))
        .thenReturn(referralToCreate);
    when(referralDao.find("0123456ABC")).thenReturn(referralToCreate);

    ChildClient childClient = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/childClient.json"), ChildClient.class);
    gov.ca.cwds.data.persistence.cms.ChildClient childClientToCreate =
        new gov.ca.cwds.data.persistence.cms.ChildClient("1234567ABC", childClient, "0XA");
    when(childClientDao.create(any(gov.ca.cwds.data.persistence.cms.ChildClient.class)))
        .thenReturn(childClientToCreate);

    Set<Client> clientDomain =
        MAPPER.readValue(fixture("fixtures/domain/ScreeningToReferral/valid/validClient.json"),
            new TypeReference<Set<Client>>() {});
    gov.ca.cwds.data.persistence.cms.Client clientToCreate =
        new gov.ca.cwds.data.persistence.cms.Client("1234567ABC",
            (Client) clientDomain.toArray()[0], "2016-10-31");
    when(clientDao.create(any(gov.ca.cwds.data.persistence.cms.Client.class)))
        .thenReturn(clientToCreate);
    when(clientDao.find("1234567ABC")).thenReturn(clientToCreate);

    Set<ReferralClient> referralClientDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validReferralClient.json"),
        new TypeReference<Set<ReferralClient>>() {});
    gov.ca.cwds.data.persistence.cms.ReferralClient referralClientToCreate =
        new gov.ca.cwds.data.persistence.cms.ReferralClient(
            (ReferralClient) referralClientDomain.toArray()[0], "2016-10-31");
    when(referralClientDao.create(any(gov.ca.cwds.data.persistence.cms.ReferralClient.class)))
        .thenReturn(referralClientToCreate);

    Set<Allegation> allegationDomain =
        MAPPER.readValue(fixture("fixtures/domain/ScreeningToReferral/valid/validAllegation.json"),
            new TypeReference<Set<Allegation>>() {});
    gov.ca.cwds.data.persistence.cms.Allegation allegationToCreate =
        new gov.ca.cwds.data.persistence.cms.Allegation("2345678ABC",
            (Allegation) allegationDomain.toArray()[0], "2016-10-31");
    when(allegationDao.create(any(gov.ca.cwds.data.persistence.cms.Allegation.class)))
        .thenReturn(allegationToCreate);

    Set<CrossReport> crossReportDomain =
        MAPPER.readValue(fixture("fixtures/domain/ScreeningToReferral/valid/validCrossReport.json"),
            new TypeReference<Set<CrossReport>>() {});
    gov.ca.cwds.data.persistence.cms.CrossReport crossReportToCreate =
        new gov.ca.cwds.data.persistence.cms.CrossReport("3456789ABC",
            // ((CrossReport) crossReportDomain).getThirdId(),
            (CrossReport) crossReportDomain.toArray()[0], "OXA");
    when(crossReportDao.create(any(gov.ca.cwds.data.persistence.cms.CrossReport.class)))
        .thenReturn(crossReportToCreate);

    Reporter reporterDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validReporter.json"), Reporter.class);
    gov.ca.cwds.data.persistence.cms.Reporter reporterToCreate =
        new gov.ca.cwds.data.persistence.cms.Reporter(reporterDomain, "ABC");
    when(reporterDao.create(any(gov.ca.cwds.data.persistence.cms.Reporter.class)))
        .thenReturn(reporterToCreate);

    Address addressDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validAddress.json"), Address.class);
    gov.ca.cwds.data.persistence.cms.Address addressToCreate =
        new gov.ca.cwds.data.persistence.cms.Address("3456789ABC", addressDomain, "ABC");
    when(addressDao.create(any(gov.ca.cwds.data.persistence.cms.Address.class)))
        .thenReturn(addressToCreate);
    when(addressDao.find("3456789ABC")).thenReturn(addressToCreate);

    ClientAddress clientAddressDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validClientAddress.json"),
        ClientAddress.class);
    gov.ca.cwds.data.persistence.cms.ClientAddress clientAddressToCreate =
        new gov.ca.cwds.data.persistence.cms.ClientAddress("3456789ABC", clientAddressDomain,
            "ABC");
    when(clientAddressDao.create(any(gov.ca.cwds.data.persistence.cms.ClientAddress.class)))
        .thenReturn(clientAddressToCreate);
    when(clientAddressDao.find("3456789ABC")).thenReturn(clientAddressToCreate);

    LongText longTextDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validLongText.json"), LongText.class);
    gov.ca.cwds.data.persistence.cms.LongText longTextToCreate =
        new gov.ca.cwds.data.persistence.cms.LongText("567890ABC", longTextDomain, "ABC");
    when(longTextDao.create(any(gov.ca.cwds.data.persistence.cms.LongText.class)))
        .thenReturn(longTextToCreate);

    ScreeningToReferral screeningToReferral = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/multipleAllegation.json"),
        ScreeningToReferral.class);

    try {
      Response response = screeningToReferralService.create(screeningToReferral);
      assertThat(response.getClass(), is(PostedScreeningToReferral.class));
    } catch (Exception e) {
      Assert.fail("Unexpected ServiceException was thrown" + e.getMessage());
    }
  }

  @SuppressWarnings("javadoc")
  @Test
  public void testAllegationNotPointingToVictimFail() throws Exception {
    Referral referralDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validReferral.json"), Referral.class);
    gov.ca.cwds.data.persistence.cms.Referral referralToCreate =
        new gov.ca.cwds.data.persistence.cms.Referral("0123456ABC", referralDomain, "2016-10-31");
    when(referralDao.create(any(gov.ca.cwds.data.persistence.cms.Referral.class)))
        .thenReturn(referralToCreate);

    ChildClient childClient = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/childClient.json"), ChildClient.class);
    gov.ca.cwds.data.persistence.cms.ChildClient childClientToCreate =
        new gov.ca.cwds.data.persistence.cms.ChildClient("1234567ABC", childClient, "0XA");
    when(childClientDao.create(any(gov.ca.cwds.data.persistence.cms.ChildClient.class)))
        .thenReturn(childClientToCreate);

    Set<Client> clientDomain =
        MAPPER.readValue(fixture("fixtures/domain/ScreeningToReferral/valid/validClient.json"),
            new TypeReference<Set<Client>>() {});
    gov.ca.cwds.data.persistence.cms.Client clientToCreate =
        new gov.ca.cwds.data.persistence.cms.Client("1234567ABC",
            (Client) clientDomain.toArray()[0], "2016-10-31");
    when(clientDao.create(any(gov.ca.cwds.data.persistence.cms.Client.class)))
        .thenReturn(clientToCreate);

    Set<ReferralClient> referralClientDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validReferralClient.json"),
        new TypeReference<Set<ReferralClient>>() {});
    gov.ca.cwds.data.persistence.cms.ReferralClient referralClientToCreate =
        new gov.ca.cwds.data.persistence.cms.ReferralClient(
            (ReferralClient) referralClientDomain.toArray()[0], "2016-10-31");
    when(referralClientDao.create(any(gov.ca.cwds.data.persistence.cms.ReferralClient.class)))
        .thenReturn(referralClientToCreate);

    Set<Allegation> allegationDomain =
        MAPPER.readValue(fixture("fixtures/domain/ScreeningToReferral/valid/validAllegation.json"),
            new TypeReference<Set<Allegation>>() {});
    gov.ca.cwds.data.persistence.cms.Allegation allegationToCreate =
        new gov.ca.cwds.data.persistence.cms.Allegation("2345678ABC",
            (Allegation) allegationDomain.toArray()[0], "2016-10-31");
    when(allegationDao.create(any(gov.ca.cwds.data.persistence.cms.Allegation.class)))
        .thenReturn(allegationToCreate);

    Set<CrossReport> crossReportDomain =
        MAPPER.readValue(fixture("fixtures/domain/ScreeningToReferral/valid/validCrossReport.json"),
            new TypeReference<Set<CrossReport>>() {});
    gov.ca.cwds.data.persistence.cms.CrossReport crossReportToCreate =
        new gov.ca.cwds.data.persistence.cms.CrossReport("3456789ABC",
            // ((CrossReport) crossReportDomain).getThirdId(),
            (CrossReport) crossReportDomain.toArray()[0], "OXA");
    when(crossReportDao.create(any(gov.ca.cwds.data.persistence.cms.CrossReport.class)))
        .thenReturn(crossReportToCreate);

    Reporter reporterDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validReporter.json"), Reporter.class);
    gov.ca.cwds.data.persistence.cms.Reporter reporterToCreate =
        new gov.ca.cwds.data.persistence.cms.Reporter(reporterDomain, "ABC");
    when(reporterDao.create(any(gov.ca.cwds.data.persistence.cms.Reporter.class)))
        .thenReturn(reporterToCreate);

    Address addressDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validAddress.json"), Address.class);
    gov.ca.cwds.data.persistence.cms.Address addressToCreate =
        new gov.ca.cwds.data.persistence.cms.Address("345678ABC", addressDomain, "ABC");
    when(addressDao.create(any(gov.ca.cwds.data.persistence.cms.Address.class)))
        .thenReturn(addressToCreate);

    ClientAddress clientAddressDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validClientAddress.json"),
        ClientAddress.class);
    gov.ca.cwds.data.persistence.cms.ClientAddress clientAddressToCreate =
        new gov.ca.cwds.data.persistence.cms.ClientAddress("456789ABC", clientAddressDomain, "ABC");
    when(clientAddressDao.create(any(gov.ca.cwds.data.persistence.cms.ClientAddress.class)))
        .thenReturn(clientAddressToCreate);

    LongText longTextDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validLongText.json"), LongText.class);
    gov.ca.cwds.data.persistence.cms.LongText longTextToCreate =
        new gov.ca.cwds.data.persistence.cms.LongText("567890ABC", longTextDomain, "ABC");
    when(longTextDao.create(any(gov.ca.cwds.data.persistence.cms.LongText.class)))
        .thenReturn(longTextToCreate);

    ScreeningToReferral screeningToReferral = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/invalid/allegationNotPointingToVictim.json"),
        ScreeningToReferral.class);

    try {
      this.screeningToReferralService.create(screeningToReferral);
      Assert.fail("Expected ServiceException was not thrown");
    } catch (Exception e) {
      // System.out.println("error = " + e.getMessage());
      assertThat(
          e.getMessage().contains(
              "Allegation/Victim Person Id does not contain a Participant with a role of Victim"),
          is(equalTo(true)));
    }

  }

  @SuppressWarnings("javadoc")
  @Test
  public void testAllegationNotPointingToPerpetratorFail() throws Exception {
    Referral referralDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validReferral.json"), Referral.class);
    gov.ca.cwds.data.persistence.cms.Referral referralToCreate =
        new gov.ca.cwds.data.persistence.cms.Referral("0123456ABC", referralDomain, "2016-10-31");
    when(referralDao.create(any(gov.ca.cwds.data.persistence.cms.Referral.class)))
        .thenReturn(referralToCreate);

    ChildClient childClient = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/childClient.json"), ChildClient.class);
    gov.ca.cwds.data.persistence.cms.ChildClient childClientToCreate =
        new gov.ca.cwds.data.persistence.cms.ChildClient("1234567ABC", childClient, "0XA");
    when(childClientDao.create(any(gov.ca.cwds.data.persistence.cms.ChildClient.class)))
        .thenReturn(childClientToCreate);

    Set<Client> clientDomain =
        MAPPER.readValue(fixture("fixtures/domain/ScreeningToReferral/valid/validClient.json"),
            new TypeReference<Set<Client>>() {});
    gov.ca.cwds.data.persistence.cms.Client clientToCreate =
        new gov.ca.cwds.data.persistence.cms.Client("1234567ABC",
            (Client) clientDomain.toArray()[0], "2016-10-31");
    when(clientDao.create(any(gov.ca.cwds.data.persistence.cms.Client.class)))
        .thenReturn(clientToCreate);

    Set<ReferralClient> referralClientDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validReferralClient.json"),
        new TypeReference<Set<ReferralClient>>() {});
    gov.ca.cwds.data.persistence.cms.ReferralClient referralClientToCreate =
        new gov.ca.cwds.data.persistence.cms.ReferralClient(
            (ReferralClient) referralClientDomain.toArray()[0], "2016-10-31");
    when(referralClientDao.create(any(gov.ca.cwds.data.persistence.cms.ReferralClient.class)))
        .thenReturn(referralClientToCreate);

    Set<Allegation> allegationDomain =
        MAPPER.readValue(fixture("fixtures/domain/ScreeningToReferral/valid/validAllegation.json"),
            new TypeReference<Set<Allegation>>() {});
    gov.ca.cwds.data.persistence.cms.Allegation allegationToCreate =
        new gov.ca.cwds.data.persistence.cms.Allegation("2345678ABC",
            (Allegation) allegationDomain.toArray()[0], "2016-10-31");
    when(allegationDao.create(any(gov.ca.cwds.data.persistence.cms.Allegation.class)))
        .thenReturn(allegationToCreate);

    Set<CrossReport> crossReportDomain =
        MAPPER.readValue(fixture("fixtures/domain/ScreeningToReferral/valid/validCrossReport.json"),
            new TypeReference<Set<CrossReport>>() {});
    gov.ca.cwds.data.persistence.cms.CrossReport crossReportToCreate =
        new gov.ca.cwds.data.persistence.cms.CrossReport("3456789ABC",
            // ((CrossReport) crossReportDomain).getThirdId(),
            (CrossReport) crossReportDomain.toArray()[0], "OXA");
    when(crossReportDao.create(any(gov.ca.cwds.data.persistence.cms.CrossReport.class)))
        .thenReturn(crossReportToCreate);

    Reporter reporterDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validReporter.json"), Reporter.class);
    gov.ca.cwds.data.persistence.cms.Reporter reporterToCreate =
        new gov.ca.cwds.data.persistence.cms.Reporter(reporterDomain, "ABC");
    when(reporterDao.create(any(gov.ca.cwds.data.persistence.cms.Reporter.class)))
        .thenReturn(reporterToCreate);

    Address addressDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validAddress.json"), Address.class);
    gov.ca.cwds.data.persistence.cms.Address addressToCreate =
        new gov.ca.cwds.data.persistence.cms.Address("345678ABC", addressDomain, "ABC");
    when(addressDao.create(any(gov.ca.cwds.data.persistence.cms.Address.class)))
        .thenReturn(addressToCreate);

    ClientAddress clientAddressDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validClientAddress.json"),
        ClientAddress.class);
    gov.ca.cwds.data.persistence.cms.ClientAddress clientAddressToCreate =
        new gov.ca.cwds.data.persistence.cms.ClientAddress("456789ABC", clientAddressDomain, "ABC");
    when(clientAddressDao.create(any(gov.ca.cwds.data.persistence.cms.ClientAddress.class)))
        .thenReturn(clientAddressToCreate);

    LongText longTextDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validLongText.json"), LongText.class);
    gov.ca.cwds.data.persistence.cms.LongText longTextToCreate =
        new gov.ca.cwds.data.persistence.cms.LongText("567890ABC", longTextDomain, "ABC");
    when(longTextDao.create(any(gov.ca.cwds.data.persistence.cms.LongText.class)))
        .thenReturn(longTextToCreate);

    ScreeningToReferral screeningToReferral = MAPPER.readValue(
        fixture(
            "fixtures/domain/ScreeningToReferral/invalid/allegationNotPointingToPerpetrator.json"),
        ScreeningToReferral.class);

    try {
      this.screeningToReferralService.create(screeningToReferral);
      Assert.fail("Expected ServiceException was not thrown");
    } catch (Exception e) {
      assertThat(
          e.getMessage().contains(
              "Allegation/Perpetrator Person Id does not contain a Participant with a role of Perpetrator"),
          is(equalTo(true)));
    }
  }

  @SuppressWarnings("javadoc")
  @Test
  public void testWithClientExsitSuccess() throws Exception {
    Referral referralDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validReferral.json"), Referral.class);
    gov.ca.cwds.data.persistence.cms.Referral referralToCreate =
        new gov.ca.cwds.data.persistence.cms.Referral("0123456ABC", referralDomain, "2016-10-31");
    when(referralDao.create(any(gov.ca.cwds.data.persistence.cms.Referral.class)))
        .thenReturn(referralToCreate);

    ChildClient childClient = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/childClient.json"), ChildClient.class);
    gov.ca.cwds.data.persistence.cms.ChildClient childClientToCreate =
        new gov.ca.cwds.data.persistence.cms.ChildClient("1234567ABC", childClient, "0XA");
    when(childClientDao.create(any(gov.ca.cwds.data.persistence.cms.ChildClient.class)))
        .thenReturn(childClientToCreate);

    Set<Client> clientDomain =
        MAPPER.readValue(fixture("fixtures/domain/ScreeningToReferral/valid/validClient.json"),
            new TypeReference<Set<Client>>() {});
    gov.ca.cwds.data.persistence.cms.Client clientToCreate =
        new gov.ca.cwds.data.persistence.cms.Client("1234567ABC",
            (Client) clientDomain.toArray()[0], "2016-10-31");
    when(clientDao.create(any(gov.ca.cwds.data.persistence.cms.Client.class)))
        .thenReturn(clientToCreate);
    when(clientDao.find("1234567ABC")).thenReturn(clientToCreate);

    Set<ReferralClient> referralClientDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validReferralClient.json"),
        new TypeReference<Set<ReferralClient>>() {});
    gov.ca.cwds.data.persistence.cms.ReferralClient referralClientToCreate =
        new gov.ca.cwds.data.persistence.cms.ReferralClient(
            (ReferralClient) referralClientDomain.toArray()[0], "2016-10-31");
    when(referralClientDao.create(any(gov.ca.cwds.data.persistence.cms.ReferralClient.class)))
        .thenReturn(referralClientToCreate);

    Set<Allegation> allegationDomain =
        MAPPER.readValue(fixture("fixtures/domain/ScreeningToReferral/valid/validAllegation.json"),
            new TypeReference<Set<Allegation>>() {});
    gov.ca.cwds.data.persistence.cms.Allegation allegationToCreate =
        new gov.ca.cwds.data.persistence.cms.Allegation("2345678ABC",
            (Allegation) allegationDomain.toArray()[0], "2016-10-31");
    when(allegationDao.create(any(gov.ca.cwds.data.persistence.cms.Allegation.class)))
        .thenReturn(allegationToCreate);

    Set<CrossReport> crossReportDomain =
        MAPPER.readValue(fixture("fixtures/domain/ScreeningToReferral/valid/validCrossReport.json"),
            new TypeReference<Set<CrossReport>>() {});
    gov.ca.cwds.data.persistence.cms.CrossReport crossReportToCreate =
        new gov.ca.cwds.data.persistence.cms.CrossReport("3456789ABC",
            // ((CrossReport) crossReportDomain).getThirdId(),
            (CrossReport) crossReportDomain.toArray()[0], "OXA");
    when(crossReportDao.create(any(gov.ca.cwds.data.persistence.cms.CrossReport.class)))
        .thenReturn(crossReportToCreate);

    Reporter reporterDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validReporter.json"), Reporter.class);
    gov.ca.cwds.data.persistence.cms.Reporter reporterToCreate =
        new gov.ca.cwds.data.persistence.cms.Reporter(reporterDomain, "ABC");
    when(reporterDao.create(any(gov.ca.cwds.data.persistence.cms.Reporter.class)))
        .thenReturn(reporterToCreate);

    Address addressDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validAddress.json"), Address.class);
    gov.ca.cwds.data.persistence.cms.Address addressToCreate =
        new gov.ca.cwds.data.persistence.cms.Address("3456789ABC", addressDomain, "ABC");
    when(addressDao.create(any(gov.ca.cwds.data.persistence.cms.Address.class)))
        .thenReturn(addressToCreate);
    when(addressDao.find("3456789ABC")).thenReturn(addressToCreate);

    ClientAddress clientAddressDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validClientAddress.json"),
        ClientAddress.class);
    gov.ca.cwds.data.persistence.cms.ClientAddress clientAddressToCreate =
        new gov.ca.cwds.data.persistence.cms.ClientAddress("3456789ABC", clientAddressDomain,
            "ABC");
    when(clientAddressDao.create(any(gov.ca.cwds.data.persistence.cms.ClientAddress.class)))
        .thenReturn(clientAddressToCreate);
    when(clientAddressDao.find("3456789ABC")).thenReturn(clientAddressToCreate);

    LongText longTextDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validLongText.json"), LongText.class);
    gov.ca.cwds.data.persistence.cms.LongText longTextToCreate =
        new gov.ca.cwds.data.persistence.cms.LongText("567890ABC", longTextDomain, "ABC");
    when(longTextDao.create(any(gov.ca.cwds.data.persistence.cms.LongText.class)))
        .thenReturn(longTextToCreate);

    ScreeningToReferral screeningToReferral =
        MAPPER.readValue(fixture("fixtures/domain/ScreeningToReferral/valid/clientIdExist.json"),
            ScreeningToReferral.class);

    try {
      Response response = screeningToReferralService.create(screeningToReferral);
      assertThat(response.getClass(), is(PostedScreeningToReferral.class));
    } catch (Exception e) {
      // System.out.println("error = " + e.getMessage());
      Assert.fail("Unexpected ServiceException was thrown" + e.getMessage());
    }
  }

  @SuppressWarnings("javadoc")
  @Test
  public void testClientDoesNotExsitFail() throws Exception {
    Referral referralDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validReferral.json"), Referral.class);
    gov.ca.cwds.data.persistence.cms.Referral referralToCreate =
        new gov.ca.cwds.data.persistence.cms.Referral("0123456ABC", referralDomain, "2016-10-31");
    when(referralDao.create(any(gov.ca.cwds.data.persistence.cms.Referral.class)))
        .thenReturn(referralToCreate);

    ChildClient childClient = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/childClient.json"), ChildClient.class);
    gov.ca.cwds.data.persistence.cms.ChildClient childClientToCreate =
        new gov.ca.cwds.data.persistence.cms.ChildClient("1234567ABC", childClient, "0XA");
    when(childClientDao.create(any(gov.ca.cwds.data.persistence.cms.ChildClient.class)))
        .thenReturn(childClientToCreate);

    Set<Client> clientDomain =
        MAPPER.readValue(fixture("fixtures/domain/ScreeningToReferral/valid/validClient.json"),
            new TypeReference<Set<Client>>() {});
    gov.ca.cwds.data.persistence.cms.Client clientToCreate =
        new gov.ca.cwds.data.persistence.cms.Client("1234567ABC",
            (Client) clientDomain.toArray()[0], "2016-10-31");
    when(clientDao.create(any(gov.ca.cwds.data.persistence.cms.Client.class)))
        .thenReturn(clientToCreate);
    when(clientDao.find("1234567ABC")).thenReturn(clientToCreate);

    Set<ReferralClient> referralClientDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validReferralClient.json"),
        new TypeReference<Set<ReferralClient>>() {});
    gov.ca.cwds.data.persistence.cms.ReferralClient referralClientToCreate =
        new gov.ca.cwds.data.persistence.cms.ReferralClient(
            (ReferralClient) referralClientDomain.toArray()[0], "2016-10-31");
    when(referralClientDao.create(any(gov.ca.cwds.data.persistence.cms.ReferralClient.class)))
        .thenReturn(referralClientToCreate);

    Set<Allegation> allegationDomain =
        MAPPER.readValue(fixture("fixtures/domain/ScreeningToReferral/valid/validAllegation.json"),
            new TypeReference<Set<Allegation>>() {});
    gov.ca.cwds.data.persistence.cms.Allegation allegationToCreate =
        new gov.ca.cwds.data.persistence.cms.Allegation("2345678ABC",
            (Allegation) allegationDomain.toArray()[0], "2016-10-31");
    when(allegationDao.create(any(gov.ca.cwds.data.persistence.cms.Allegation.class)))
        .thenReturn(allegationToCreate);

    Set<CrossReport> crossReportDomain =
        MAPPER.readValue(fixture("fixtures/domain/ScreeningToReferral/valid/validCrossReport.json"),
            new TypeReference<Set<CrossReport>>() {});
    gov.ca.cwds.data.persistence.cms.CrossReport crossReportToCreate =
        new gov.ca.cwds.data.persistence.cms.CrossReport("3456789ABC",
            // ((CrossReport) crossReportDomain).getThirdId(),
            (CrossReport) crossReportDomain.toArray()[0], "OXA");
    when(crossReportDao.create(any(gov.ca.cwds.data.persistence.cms.CrossReport.class)))
        .thenReturn(crossReportToCreate);

    Reporter reporterDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validReporter.json"), Reporter.class);
    gov.ca.cwds.data.persistence.cms.Reporter reporterToCreate =
        new gov.ca.cwds.data.persistence.cms.Reporter(reporterDomain, "ABC");
    when(reporterDao.create(any(gov.ca.cwds.data.persistence.cms.Reporter.class)))
        .thenReturn(reporterToCreate);

    Address addressDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validAddress.json"), Address.class);
    gov.ca.cwds.data.persistence.cms.Address addressToCreate =
        new gov.ca.cwds.data.persistence.cms.Address("3456789ABC", addressDomain, "ABC");
    when(addressDao.create(any(gov.ca.cwds.data.persistence.cms.Address.class)))
        .thenReturn(addressToCreate);
    when(addressDao.find("3456789ABC")).thenReturn(addressToCreate);

    ClientAddress clientAddressDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validClientAddress.json"),
        ClientAddress.class);
    gov.ca.cwds.data.persistence.cms.ClientAddress clientAddressToCreate =
        new gov.ca.cwds.data.persistence.cms.ClientAddress("3456789ABC", clientAddressDomain,
            "ABC");
    when(clientAddressDao.create(any(gov.ca.cwds.data.persistence.cms.ClientAddress.class)))
        .thenReturn(clientAddressToCreate);
    when(clientAddressDao.find("3456789ABC")).thenReturn(clientAddressToCreate);

    LongText longTextDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validLongText.json"), LongText.class);
    gov.ca.cwds.data.persistence.cms.LongText longTextToCreate =
        new gov.ca.cwds.data.persistence.cms.LongText("567890ABC", longTextDomain, "ABC");
    when(longTextDao.create(any(gov.ca.cwds.data.persistence.cms.LongText.class)))
        .thenReturn(longTextToCreate);

    ScreeningToReferral screeningToReferral = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/invalid/clientIdDoesNotExist.json"),
        ScreeningToReferral.class);

    try {
      this.screeningToReferralService.create(screeningToReferral);
      Assert.fail("Expected ServiceException was not thrown");
    } catch (Exception e) {
      assertThat(
          e.getMessage().contains(
              "Legacy Id of Participant does not correspond to an existing CWS/CMS Client"),
          is(equalTo(true)));
    }
  }

  @SuppressWarnings("javadoc")
  @Test
  public void testAllegationExsitSuccess() throws Exception {
    Referral referralDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validReferral.json"), Referral.class);
    gov.ca.cwds.data.persistence.cms.Referral referralToCreate =
        new gov.ca.cwds.data.persistence.cms.Referral("0123456ABC", referralDomain, "2016-10-31");
    when(referralDao.create(any(gov.ca.cwds.data.persistence.cms.Referral.class)))
        .thenReturn(referralToCreate);

    ChildClient childClient = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/childClient.json"), ChildClient.class);
    gov.ca.cwds.data.persistence.cms.ChildClient childClientToCreate =
        new gov.ca.cwds.data.persistence.cms.ChildClient("1234567ABC", childClient, "0XA");
    when(childClientDao.create(any(gov.ca.cwds.data.persistence.cms.ChildClient.class)))
        .thenReturn(childClientToCreate);

    Set<Client> clientDomain =
        MAPPER.readValue(fixture("fixtures/domain/ScreeningToReferral/valid/validClient.json"),
            new TypeReference<Set<Client>>() {});
    gov.ca.cwds.data.persistence.cms.Client clientToCreate =
        new gov.ca.cwds.data.persistence.cms.Client("1234567ABC",
            (Client) clientDomain.toArray()[0], "2016-10-31");
    when(clientDao.create(any(gov.ca.cwds.data.persistence.cms.Client.class)))
        .thenReturn(clientToCreate);
    when(clientDao.find("1234567ABC")).thenReturn(clientToCreate);

    Set<ReferralClient> referralClientDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validReferralClient.json"),
        new TypeReference<Set<ReferralClient>>() {});
    gov.ca.cwds.data.persistence.cms.ReferralClient referralClientToCreate =
        new gov.ca.cwds.data.persistence.cms.ReferralClient(
            (ReferralClient) referralClientDomain.toArray()[0], "2016-10-31");
    when(referralClientDao.create(any(gov.ca.cwds.data.persistence.cms.ReferralClient.class)))
        .thenReturn(referralClientToCreate);

    Set<Allegation> allegationDomain =
        MAPPER.readValue(fixture("fixtures/domain/ScreeningToReferral/valid/validAllegation.json"),
            new TypeReference<Set<Allegation>>() {});
    gov.ca.cwds.data.persistence.cms.Allegation allegationToCreate =
        new gov.ca.cwds.data.persistence.cms.Allegation("2345678ABC",
            (Allegation) allegationDomain.toArray()[0], "2016-10-31");
    when(allegationDao.create(any(gov.ca.cwds.data.persistence.cms.Allegation.class)))
        .thenReturn(allegationToCreate);
    when(allegationDao.find("2345678ABC")).thenReturn(allegationToCreate);

    Set<CrossReport> crossReportDomain =
        MAPPER.readValue(fixture("fixtures/domain/ScreeningToReferral/valid/validCrossReport.json"),
            new TypeReference<Set<CrossReport>>() {});
    gov.ca.cwds.data.persistence.cms.CrossReport crossReportToCreate =
        new gov.ca.cwds.data.persistence.cms.CrossReport("3456789ABC",
            // ((CrossReport) crossReportDomain).getThirdId(),
            (CrossReport) crossReportDomain.toArray()[0], "OXA");
    when(crossReportDao.create(any(gov.ca.cwds.data.persistence.cms.CrossReport.class)))
        .thenReturn(crossReportToCreate);

    Reporter reporterDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validReporter.json"), Reporter.class);
    gov.ca.cwds.data.persistence.cms.Reporter reporterToCreate =
        new gov.ca.cwds.data.persistence.cms.Reporter(reporterDomain, "ABC");
    when(reporterDao.create(any(gov.ca.cwds.data.persistence.cms.Reporter.class)))
        .thenReturn(reporterToCreate);

    Address addressDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validAddress.json"), Address.class);
    gov.ca.cwds.data.persistence.cms.Address addressToCreate =
        new gov.ca.cwds.data.persistence.cms.Address("3456789ABC", addressDomain, "ABC");
    when(addressDao.create(any(gov.ca.cwds.data.persistence.cms.Address.class)))
        .thenReturn(addressToCreate);
    when(addressDao.find("3456789ABC")).thenReturn(addressToCreate);

    ClientAddress clientAddressDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validClientAddress.json"),
        ClientAddress.class);
    gov.ca.cwds.data.persistence.cms.ClientAddress clientAddressToCreate =
        new gov.ca.cwds.data.persistence.cms.ClientAddress("456789ABC", clientAddressDomain, "ABC");
    when(clientAddressDao.create(any(gov.ca.cwds.data.persistence.cms.ClientAddress.class)))
        .thenReturn(clientAddressToCreate);
    when(clientAddressDao.find("3456789ABC")).thenReturn(clientAddressToCreate);

    LongText longTextDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validLongText.json"), LongText.class);
    gov.ca.cwds.data.persistence.cms.LongText longTextToCreate =
        new gov.ca.cwds.data.persistence.cms.LongText("567890ABC", longTextDomain, "ABC");
    when(longTextDao.create(any(gov.ca.cwds.data.persistence.cms.LongText.class)))
        .thenReturn(longTextToCreate);

    ScreeningToReferral screeningToReferral =
        MAPPER.readValue(fixture("fixtures/domain/ScreeningToReferral/valid/allegationExist.json"),
            ScreeningToReferral.class);

    try {
      Response response = screeningToReferralService.create(screeningToReferral);
      assertThat(response.getClass(), is(PostedScreeningToReferral.class));
    } catch (Exception e) {
      // System.out.println("error = " + e.getMessage());
      Assert.fail("Unexpected ServiceException was thrown" + e.getMessage());
    }
  }

  @SuppressWarnings("javadoc")
  @Test
  public void testAllegationDoesNotExsitFail() throws Exception {
    Referral referralDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validReferral.json"), Referral.class);
    gov.ca.cwds.data.persistence.cms.Referral referralToCreate =
        new gov.ca.cwds.data.persistence.cms.Referral("0123456ABC", referralDomain, "2016-10-31");
    when(referralDao.create(any(gov.ca.cwds.data.persistence.cms.Referral.class)))
        .thenReturn(referralToCreate);

    ChildClient childClient = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/childClient.json"), ChildClient.class);
    gov.ca.cwds.data.persistence.cms.ChildClient childClientToCreate =
        new gov.ca.cwds.data.persistence.cms.ChildClient("1234567ABC", childClient, "0XA");
    when(childClientDao.create(any(gov.ca.cwds.data.persistence.cms.ChildClient.class)))
        .thenReturn(childClientToCreate);

    Set<Client> clientDomain =
        MAPPER.readValue(fixture("fixtures/domain/ScreeningToReferral/valid/validClient.json"),
            new TypeReference<Set<Client>>() {});
    gov.ca.cwds.data.persistence.cms.Client clientToCreate =
        new gov.ca.cwds.data.persistence.cms.Client("1234567ABC",
            (Client) clientDomain.toArray()[0], "2016-10-31");
    when(clientDao.create(any(gov.ca.cwds.data.persistence.cms.Client.class)))
        .thenReturn(clientToCreate);

    Set<ReferralClient> referralClientDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validReferralClient.json"),
        new TypeReference<Set<ReferralClient>>() {});
    gov.ca.cwds.data.persistence.cms.ReferralClient referralClientToCreate =
        new gov.ca.cwds.data.persistence.cms.ReferralClient(
            (ReferralClient) referralClientDomain.toArray()[0], "2016-10-31");
    when(referralClientDao.create(any(gov.ca.cwds.data.persistence.cms.ReferralClient.class)))
        .thenReturn(referralClientToCreate);

    Set<Allegation> allegationDomain =
        MAPPER.readValue(fixture("fixtures/domain/ScreeningToReferral/valid/validAllegation.json"),
            new TypeReference<Set<Allegation>>() {});
    gov.ca.cwds.data.persistence.cms.Allegation allegationToCreate =
        new gov.ca.cwds.data.persistence.cms.Allegation("2345678ABC",
            (Allegation) allegationDomain.toArray()[0], "2016-10-31");
    when(allegationDao.create(any(gov.ca.cwds.data.persistence.cms.Allegation.class)))
        .thenReturn(allegationToCreate);
    when(allegationDao.find("2345678ABC")).thenReturn(allegationToCreate);

    Set<CrossReport> crossReportDomain =
        MAPPER.readValue(fixture("fixtures/domain/ScreeningToReferral/valid/validCrossReport.json"),
            new TypeReference<Set<CrossReport>>() {});
    gov.ca.cwds.data.persistence.cms.CrossReport crossReportToCreate =
        new gov.ca.cwds.data.persistence.cms.CrossReport("3456789ABC",
            // ((CrossReport) crossReportDomain).getThirdId(),
            (CrossReport) crossReportDomain.toArray()[0], "OXA");
    when(crossReportDao.create(any(gov.ca.cwds.data.persistence.cms.CrossReport.class)))
        .thenReturn(crossReportToCreate);

    Reporter reporterDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validReporter.json"), Reporter.class);
    gov.ca.cwds.data.persistence.cms.Reporter reporterToCreate =
        new gov.ca.cwds.data.persistence.cms.Reporter(reporterDomain, "ABC");
    when(reporterDao.create(any(gov.ca.cwds.data.persistence.cms.Reporter.class)))
        .thenReturn(reporterToCreate);


    Address addressDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validAddress.json"), Address.class);
    gov.ca.cwds.data.persistence.cms.Address addressToCreate =
        new gov.ca.cwds.data.persistence.cms.Address("345678ABC", addressDomain, "ABC");
    when(addressDao.create(any(gov.ca.cwds.data.persistence.cms.Address.class)))
        .thenReturn(addressToCreate);

    ClientAddress clientAddressDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validClientAddress.json"),
        ClientAddress.class);
    gov.ca.cwds.data.persistence.cms.ClientAddress clientAddressToCreate =
        new gov.ca.cwds.data.persistence.cms.ClientAddress("456789ABC", clientAddressDomain, "ABC");
    when(clientAddressDao.create(any(gov.ca.cwds.data.persistence.cms.ClientAddress.class)))
        .thenReturn(clientAddressToCreate);

    LongText longTextDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validLongText.json"), LongText.class);
    gov.ca.cwds.data.persistence.cms.LongText longTextToCreate =
        new gov.ca.cwds.data.persistence.cms.LongText("567890ABC", longTextDomain, "ABC");
    when(longTextDao.create(any(gov.ca.cwds.data.persistence.cms.LongText.class)))
        .thenReturn(longTextToCreate);

    ScreeningToReferral screeningToReferral = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/invalid/allegationIdDoesNotExist.json"),
        ScreeningToReferral.class);

    try {
      this.screeningToReferralService.create(screeningToReferral);
      Assert.fail("Expected ServiceException was not thrown");
    } catch (Exception e) {
      System.out.println(e.getMessage());
      assertThat(
          e.getMessage().contains(
              "Legacy Id on Allegation does not correspond to an existing CMS/CWS Allegation"),
          is(equalTo(true)));
    }
  }

  @SuppressWarnings("javadoc")
  @Test
  public void testCrossReportExsitSuccess() throws Exception {
    Referral referralDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validReferral.json"), Referral.class);
    gov.ca.cwds.data.persistence.cms.Referral referralToCreate =
        new gov.ca.cwds.data.persistence.cms.Referral("0123456ABC", referralDomain, "2016-10-31");
    when(referralDao.create(any(gov.ca.cwds.data.persistence.cms.Referral.class)))
        .thenReturn(referralToCreate);

    ChildClient childClient = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/childClient.json"), ChildClient.class);
    gov.ca.cwds.data.persistence.cms.ChildClient childClientToCreate =
        new gov.ca.cwds.data.persistence.cms.ChildClient("1234567ABC", childClient, "0XA");
    when(childClientDao.create(any(gov.ca.cwds.data.persistence.cms.ChildClient.class)))
        .thenReturn(childClientToCreate);

    Set<Client> clientDomain =
        MAPPER.readValue(fixture("fixtures/domain/ScreeningToReferral/valid/validClient.json"),
            new TypeReference<Set<Client>>() {});
    gov.ca.cwds.data.persistence.cms.Client clientToCreate =
        new gov.ca.cwds.data.persistence.cms.Client("1234567ABC",
            (Client) clientDomain.toArray()[0], "2016-10-31");
    when(clientDao.create(any(gov.ca.cwds.data.persistence.cms.Client.class)))
        .thenReturn(clientToCreate);
    when(clientDao.find("1234567ABC")).thenReturn(clientToCreate);

    Set<ReferralClient> referralClientDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validReferralClient.json"),
        new TypeReference<Set<ReferralClient>>() {});
    gov.ca.cwds.data.persistence.cms.ReferralClient referralClientToCreate =
        new gov.ca.cwds.data.persistence.cms.ReferralClient(
            (ReferralClient) referralClientDomain.toArray()[0], "2016-10-31");
    when(referralClientDao.create(any(gov.ca.cwds.data.persistence.cms.ReferralClient.class)))
        .thenReturn(referralClientToCreate);

    Set<Allegation> allegationDomain =
        MAPPER.readValue(fixture("fixtures/domain/ScreeningToReferral/valid/validAllegation.json"),
            new TypeReference<Set<Allegation>>() {});
    gov.ca.cwds.data.persistence.cms.Allegation allegationToCreate =
        new gov.ca.cwds.data.persistence.cms.Allegation("2345678ABC",
            (Allegation) allegationDomain.toArray()[0], "2016-10-31");
    when(allegationDao.create(any(gov.ca.cwds.data.persistence.cms.Allegation.class)))
        .thenReturn(allegationToCreate);
    when(allegationDao.find("2345678ABC")).thenReturn(allegationToCreate);

    Set<CrossReport> crossReportDomain =
        MAPPER.readValue(fixture("fixtures/domain/ScreeningToReferral/valid/validCrossReport.json"),
            new TypeReference<Set<CrossReport>>() {});
    gov.ca.cwds.data.persistence.cms.CrossReport crossReportToCreate =
        new gov.ca.cwds.data.persistence.cms.CrossReport("3456789ABC",
            // ((CrossReport) crossReportDomain).getThirdId(),
            (CrossReport) crossReportDomain.toArray()[0], "OXA");
    when(crossReportDao.create(any(gov.ca.cwds.data.persistence.cms.CrossReport.class)))
        .thenReturn(crossReportToCreate);
    when(crossReportDao.find("3456789ABC")).thenReturn(crossReportToCreate);

    Reporter reporterDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validReporter.json"), Reporter.class);
    gov.ca.cwds.data.persistence.cms.Reporter reporterToCreate =
        new gov.ca.cwds.data.persistence.cms.Reporter(reporterDomain, "ABC");
    when(reporterDao.create(any(gov.ca.cwds.data.persistence.cms.Reporter.class)))
        .thenReturn(reporterToCreate);

    Address addressDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validAddress.json"), Address.class);
    gov.ca.cwds.data.persistence.cms.Address addressToCreate =
        new gov.ca.cwds.data.persistence.cms.Address("3456789ABC", addressDomain, "ABC");
    when(addressDao.create(any(gov.ca.cwds.data.persistence.cms.Address.class)))
        .thenReturn(addressToCreate);
    when(addressDao.find("3456789ABC")).thenReturn(addressToCreate);

    ClientAddress clientAddressDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validClientAddress.json"),
        ClientAddress.class);
    gov.ca.cwds.data.persistence.cms.ClientAddress clientAddressToCreate =
        new gov.ca.cwds.data.persistence.cms.ClientAddress("3456789ABC", clientAddressDomain,
            "ABC");
    when(clientAddressDao.create(any(gov.ca.cwds.data.persistence.cms.ClientAddress.class)))
        .thenReturn(clientAddressToCreate);
    when(clientAddressDao.find("3456789ABC")).thenReturn(clientAddressToCreate);

    LongText longTextDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validLongText.json"), LongText.class);
    gov.ca.cwds.data.persistence.cms.LongText longTextToCreate =
        new gov.ca.cwds.data.persistence.cms.LongText("567890ABC", longTextDomain, "ABC");
    when(longTextDao.create(any(gov.ca.cwds.data.persistence.cms.LongText.class)))
        .thenReturn(longTextToCreate);

    ScreeningToReferral screeningToReferral =
        MAPPER.readValue(fixture("fixtures/domain/ScreeningToReferral/valid/crossReportExist.json"),
            ScreeningToReferral.class);

    try {
      Response response = screeningToReferralService.create(screeningToReferral);
      assertThat(response.getClass(), is(PostedScreeningToReferral.class));
    } catch (Exception e) {
      // System.out.println("error = " + e.getMessage());
      Assert.fail("Unexpected ServiceException was thrown" + e.getMessage());
    }
  }

  @SuppressWarnings("javadoc")
  @Test
  public void testCrossReportDoesNotExsitFail() throws Exception {
    Referral referralDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validReferral.json"), Referral.class);
    gov.ca.cwds.data.persistence.cms.Referral referralToCreate =
        new gov.ca.cwds.data.persistence.cms.Referral("0123456ABC", referralDomain, "2016-10-31");
    when(referralDao.create(any(gov.ca.cwds.data.persistence.cms.Referral.class)))
        .thenReturn(referralToCreate);

    ChildClient childClient = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/childClient.json"), ChildClient.class);
    gov.ca.cwds.data.persistence.cms.ChildClient childClientToCreate =
        new gov.ca.cwds.data.persistence.cms.ChildClient("1234567ABC", childClient, "0XA");
    when(childClientDao.create(any(gov.ca.cwds.data.persistence.cms.ChildClient.class)))
        .thenReturn(childClientToCreate);

    Set<Client> clientDomain =
        MAPPER.readValue(fixture("fixtures/domain/ScreeningToReferral/valid/validClient.json"),
            new TypeReference<Set<Client>>() {});
    gov.ca.cwds.data.persistence.cms.Client clientToCreate =
        new gov.ca.cwds.data.persistence.cms.Client("1234567ABC",
            (Client) clientDomain.toArray()[0], "2016-10-31");
    when(clientDao.create(any(gov.ca.cwds.data.persistence.cms.Client.class)))
        .thenReturn(clientToCreate);
    when(clientDao.find("1234567ABC")).thenReturn(clientToCreate);

    Set<ReferralClient> referralClientDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validReferralClient.json"),
        new TypeReference<Set<ReferralClient>>() {});
    gov.ca.cwds.data.persistence.cms.ReferralClient referralClientToCreate =
        new gov.ca.cwds.data.persistence.cms.ReferralClient(
            (ReferralClient) referralClientDomain.toArray()[0], "2016-10-31");
    when(referralClientDao.create(any(gov.ca.cwds.data.persistence.cms.ReferralClient.class)))
        .thenReturn(referralClientToCreate);

    Set<Allegation> allegationDomain =
        MAPPER.readValue(fixture("fixtures/domain/ScreeningToReferral/valid/validAllegation.json"),
            new TypeReference<Set<Allegation>>() {});
    gov.ca.cwds.data.persistence.cms.Allegation allegationToCreate =
        new gov.ca.cwds.data.persistence.cms.Allegation("2345678ABC",
            (Allegation) allegationDomain.toArray()[0], "2016-10-31");
    when(allegationDao.create(any(gov.ca.cwds.data.persistence.cms.Allegation.class)))
        .thenReturn(allegationToCreate);
    when(allegationDao.find("2345678ABC")).thenReturn(allegationToCreate);

    Set<CrossReport> crossReportDomain =
        MAPPER.readValue(fixture("fixtures/domain/ScreeningToReferral/valid/validCrossReport.json"),
            new TypeReference<Set<CrossReport>>() {});
    gov.ca.cwds.data.persistence.cms.CrossReport crossReportToCreate =
        new gov.ca.cwds.data.persistence.cms.CrossReport("3456789ABC",
            // ((CrossReport) crossReportDomain).getThirdId(),
            (CrossReport) crossReportDomain.toArray()[0], "OXA");
    when(crossReportDao.create(any(gov.ca.cwds.data.persistence.cms.CrossReport.class)))
        .thenReturn(crossReportToCreate);

    Reporter reporterDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validReporter.json"), Reporter.class);
    gov.ca.cwds.data.persistence.cms.Reporter reporterToCreate =
        new gov.ca.cwds.data.persistence.cms.Reporter(reporterDomain, "ABC");
    when(reporterDao.create(any(gov.ca.cwds.data.persistence.cms.Reporter.class)))
        .thenReturn(reporterToCreate);


    Address addressDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validAddress.json"), Address.class);
    gov.ca.cwds.data.persistence.cms.Address addressToCreate =
        new gov.ca.cwds.data.persistence.cms.Address("4567890ABC", addressDomain, "ABC");
    when(addressDao.create(any(gov.ca.cwds.data.persistence.cms.Address.class)))
        .thenReturn(addressToCreate);
    when(addressDao.find("4567890ABC")).thenReturn(addressToCreate);

    ClientAddress clientAddressDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validClientAddress.json"),
        ClientAddress.class);
    gov.ca.cwds.data.persistence.cms.ClientAddress clientAddressToCreate =
        new gov.ca.cwds.data.persistence.cms.ClientAddress("4567890ABC", clientAddressDomain,
            "ABC");
    when(clientAddressDao.create(any(gov.ca.cwds.data.persistence.cms.ClientAddress.class)))
        .thenReturn(clientAddressToCreate);
    when(clientAddressDao.find("4567890ABC")).thenReturn(clientAddressToCreate);

    LongText longTextDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validLongText.json"), LongText.class);
    gov.ca.cwds.data.persistence.cms.LongText longTextToCreate =
        new gov.ca.cwds.data.persistence.cms.LongText("3456789ABC", longTextDomain, "ABC");
    when(longTextDao.create(any(gov.ca.cwds.data.persistence.cms.LongText.class)))
        .thenReturn(longTextToCreate);

    ScreeningToReferral screeningToReferral = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/invalid/crossReportIdDoesNotExist.json"),
        ScreeningToReferral.class);

    try {
      this.screeningToReferralService.create(screeningToReferral);
      Assert.fail("Expected ServiceException was not thrown");
    } catch (Exception e) {
      System.out.println(e.getMessage());
      assertThat(
          e.getMessage().contains(
              "Legacy Id on Cross Report does not correspond to an existing CMS/CWS Cross Report"),
          is(equalTo(true)));
    }
  }

  @SuppressWarnings("javadoc")
  @Test
  public void testAddressExsitSuccess() throws Exception {
    Referral referralDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validReferral.json"), Referral.class);
    gov.ca.cwds.data.persistence.cms.Referral referralToCreate =
        new gov.ca.cwds.data.persistence.cms.Referral("0123456ABC", referralDomain, "2016-10-31");
    when(referralDao.create(any(gov.ca.cwds.data.persistence.cms.Referral.class)))
        .thenReturn(referralToCreate);

    ChildClient childClient = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/childClient.json"), ChildClient.class);
    gov.ca.cwds.data.persistence.cms.ChildClient childClientToCreate =
        new gov.ca.cwds.data.persistence.cms.ChildClient("1234567ABC", childClient, "0XA");
    when(childClientDao.create(any(gov.ca.cwds.data.persistence.cms.ChildClient.class)))
        .thenReturn(childClientToCreate);

    Set<Client> clientDomain =
        MAPPER.readValue(fixture("fixtures/domain/ScreeningToReferral/valid/validClient.json"),
            new TypeReference<Set<Client>>() {});
    gov.ca.cwds.data.persistence.cms.Client clientToCreate =
        new gov.ca.cwds.data.persistence.cms.Client("1234567ABC",
            (Client) clientDomain.toArray()[0], "2016-10-31");
    when(clientDao.create(any(gov.ca.cwds.data.persistence.cms.Client.class)))
        .thenReturn(clientToCreate);
    when(clientDao.find("1234567ABC")).thenReturn(clientToCreate);

    Set<ReferralClient> referralClientDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validReferralClient.json"),
        new TypeReference<Set<ReferralClient>>() {});
    gov.ca.cwds.data.persistence.cms.ReferralClient referralClientToCreate =
        new gov.ca.cwds.data.persistence.cms.ReferralClient(
            (ReferralClient) referralClientDomain.toArray()[0], "2016-10-31");
    when(referralClientDao.create(any(gov.ca.cwds.data.persistence.cms.ReferralClient.class)))
        .thenReturn(referralClientToCreate);

    Set<Allegation> allegationDomain =
        MAPPER.readValue(fixture("fixtures/domain/ScreeningToReferral/valid/validAllegation.json"),
            new TypeReference<Set<Allegation>>() {});
    gov.ca.cwds.data.persistence.cms.Allegation allegationToCreate =
        new gov.ca.cwds.data.persistence.cms.Allegation("2345678ABC",
            (Allegation) allegationDomain.toArray()[0], "2016-10-31");
    when(allegationDao.create(any(gov.ca.cwds.data.persistence.cms.Allegation.class)))
        .thenReturn(allegationToCreate);
    when(allegationDao.find("2345678ABC")).thenReturn(allegationToCreate);

    Set<CrossReport> crossReportDomain =
        MAPPER.readValue(fixture("fixtures/domain/ScreeningToReferral/valid/validCrossReport.json"),
            new TypeReference<Set<CrossReport>>() {});
    gov.ca.cwds.data.persistence.cms.CrossReport crossReportToCreate =
        new gov.ca.cwds.data.persistence.cms.CrossReport("3456789ABC",
            // ((CrossReport) crossReportDomain).getThirdId(),
            (CrossReport) crossReportDomain.toArray()[0], "OXA");
    when(crossReportDao.create(any(gov.ca.cwds.data.persistence.cms.CrossReport.class)))
        .thenReturn(crossReportToCreate);
    when(crossReportDao.find("3456789ABC")).thenReturn(crossReportToCreate);

    Reporter reporterDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validReporter.json"), Reporter.class);
    gov.ca.cwds.data.persistence.cms.Reporter reporterToCreate =
        new gov.ca.cwds.data.persistence.cms.Reporter(reporterDomain, "ABC");
    when(reporterDao.create(any(gov.ca.cwds.data.persistence.cms.Reporter.class)))
        .thenReturn(reporterToCreate);


    Address addressDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validAddress.json"), Address.class);
    gov.ca.cwds.data.persistence.cms.Address addressToCreate =
        new gov.ca.cwds.data.persistence.cms.Address("345678ABC", addressDomain, "ABC");
    when(addressDao.create(any(gov.ca.cwds.data.persistence.cms.Address.class)))
        .thenReturn(addressToCreate);
    when(addressDao.find("345678ABC")).thenReturn(addressToCreate);

    ClientAddress clientAddressDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validClientAddress.json"),
        ClientAddress.class);
    gov.ca.cwds.data.persistence.cms.ClientAddress clientAddressToCreate =
        new gov.ca.cwds.data.persistence.cms.ClientAddress("345678ABC", clientAddressDomain, "ABC");
    when(clientAddressDao.create(any(gov.ca.cwds.data.persistence.cms.ClientAddress.class)))
        .thenReturn(clientAddressToCreate);
    when(clientAddressDao.find("345678ABC")).thenReturn(clientAddressToCreate);

    LongText longTextDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validLongText.json"), LongText.class);
    gov.ca.cwds.data.persistence.cms.LongText longTextToCreate =
        new gov.ca.cwds.data.persistence.cms.LongText("567890ABC", longTextDomain, "ABC");
    when(longTextDao.create(any(gov.ca.cwds.data.persistence.cms.LongText.class)))
        .thenReturn(longTextToCreate);

    ScreeningToReferral screeningToReferral =
        MAPPER.readValue(fixture("fixtures/domain/ScreeningToReferral/valid/addressExist.json"),
            ScreeningToReferral.class);

    try {
      Response response = screeningToReferralService.create(screeningToReferral);
      assertThat(response.getClass(), is(PostedScreeningToReferral.class));
    } catch (Exception e) {
      // System.out.println("error = " + e.getMessage());
      Assert.fail("Unexpected ServiceException was thrown" + e.getMessage());
    }
  }

  @SuppressWarnings("javadoc")
  @Test
  public void testAddressDoesNotExsitFail() throws Exception {
    Referral referralDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validReferral.json"), Referral.class);
    gov.ca.cwds.data.persistence.cms.Referral referralToCreate =
        new gov.ca.cwds.data.persistence.cms.Referral("0123456ABC", referralDomain, "2016-10-31");
    when(referralDao.create(any(gov.ca.cwds.data.persistence.cms.Referral.class)))
        .thenReturn(referralToCreate);

    ChildClient childClient = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/childClient.json"), ChildClient.class);
    gov.ca.cwds.data.persistence.cms.ChildClient childClientToCreate =
        new gov.ca.cwds.data.persistence.cms.ChildClient("1234567ABC", childClient, "0XA");
    when(childClientDao.create(any(gov.ca.cwds.data.persistence.cms.ChildClient.class)))
        .thenReturn(childClientToCreate);

    Set<Client> clientDomain =
        MAPPER.readValue(fixture("fixtures/domain/ScreeningToReferral/valid/validClient.json"),
            new TypeReference<Set<Client>>() {});
    gov.ca.cwds.data.persistence.cms.Client clientToCreate =
        new gov.ca.cwds.data.persistence.cms.Client("1234567ABC",
            (Client) clientDomain.toArray()[0], "2016-10-31");
    when(clientDao.create(any(gov.ca.cwds.data.persistence.cms.Client.class)))
        .thenReturn(clientToCreate);
    when(clientDao.find("1234567ABC")).thenReturn(clientToCreate);

    Set<ReferralClient> referralClientDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validReferralClient.json"),
        new TypeReference<Set<ReferralClient>>() {});
    gov.ca.cwds.data.persistence.cms.ReferralClient referralClientToCreate =
        new gov.ca.cwds.data.persistence.cms.ReferralClient(
            (ReferralClient) referralClientDomain.toArray()[0], "2016-10-31");
    when(referralClientDao.create(any(gov.ca.cwds.data.persistence.cms.ReferralClient.class)))
        .thenReturn(referralClientToCreate);

    Set<Allegation> allegationDomain =
        MAPPER.readValue(fixture("fixtures/domain/ScreeningToReferral/valid/validAllegation.json"),
            new TypeReference<Set<Allegation>>() {});
    gov.ca.cwds.data.persistence.cms.Allegation allegationToCreate =
        new gov.ca.cwds.data.persistence.cms.Allegation("2345678ABC",
            (Allegation) allegationDomain.toArray()[0], "2016-10-31");
    when(allegationDao.create(any(gov.ca.cwds.data.persistence.cms.Allegation.class)))
        .thenReturn(allegationToCreate);
    when(allegationDao.find("2345678ABC")).thenReturn(allegationToCreate);

    Set<CrossReport> crossReportDomain =
        MAPPER.readValue(fixture("fixtures/domain/ScreeningToReferral/valid/validCrossReport.json"),
            new TypeReference<Set<CrossReport>>() {});
    gov.ca.cwds.data.persistence.cms.CrossReport crossReportToCreate =
        new gov.ca.cwds.data.persistence.cms.CrossReport("3456789ABC",
            // ((CrossReport) crossReportDomain).getThirdId(),
            (CrossReport) crossReportDomain.toArray()[0], "OXA");
    when(crossReportDao.create(any(gov.ca.cwds.data.persistence.cms.CrossReport.class)))
        .thenReturn(crossReportToCreate);

    Reporter reporterDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validReporter.json"), Reporter.class);
    gov.ca.cwds.data.persistence.cms.Reporter reporterToCreate =
        new gov.ca.cwds.data.persistence.cms.Reporter(reporterDomain, "ABC");
    when(reporterDao.create(any(gov.ca.cwds.data.persistence.cms.Reporter.class)))
        .thenReturn(reporterToCreate);


    Address addressDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validAddress.json"), Address.class);
    gov.ca.cwds.data.persistence.cms.Address addressToCreate =
        new gov.ca.cwds.data.persistence.cms.Address("345678ABC", addressDomain, "ABC");
    when(addressDao.create(any(gov.ca.cwds.data.persistence.cms.Address.class)))
        .thenReturn(addressToCreate);

    ClientAddress clientAddressDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validClientAddress.json"),
        ClientAddress.class);
    gov.ca.cwds.data.persistence.cms.ClientAddress clientAddressToCreate =
        new gov.ca.cwds.data.persistence.cms.ClientAddress("456789ABC", clientAddressDomain, "ABC");
    when(clientAddressDao.create(any(gov.ca.cwds.data.persistence.cms.ClientAddress.class)))
        .thenReturn(clientAddressToCreate);

    LongText longTextDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validLongText.json"), LongText.class);
    gov.ca.cwds.data.persistence.cms.LongText longTextToCreate =
        new gov.ca.cwds.data.persistence.cms.LongText("567890ABC", longTextDomain, "ABC");
    when(longTextDao.create(any(gov.ca.cwds.data.persistence.cms.LongText.class)))
        .thenReturn(longTextToCreate);

    ScreeningToReferral screeningToReferral = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/invalid/addressIdDoesNotExist.json"),
        ScreeningToReferral.class);

    try {
      this.screeningToReferralService.create(screeningToReferral);
      Assert.fail("Expected ServiceException was not thrown");
    } catch (Exception e) {
      assertThat(
          e.getMessage()
              .contains("Legacy Id on Address does not correspond to an existing CMS/CWS Address"),
          is(equalTo(true)));
    }
  }

  @SuppressWarnings("javadoc")
  @Test
  public void testClientAddressExsitSuccess() throws Exception {
    Referral referralDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validReferral.json"), Referral.class);
    gov.ca.cwds.data.persistence.cms.Referral referralToCreate =
        new gov.ca.cwds.data.persistence.cms.Referral("0123456ABC", referralDomain, "2016-10-31");
    when(referralDao.create(any(gov.ca.cwds.data.persistence.cms.Referral.class)))
        .thenReturn(referralToCreate);

    ChildClient childClient = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/childClient.json"), ChildClient.class);
    gov.ca.cwds.data.persistence.cms.ChildClient childClientToCreate =
        new gov.ca.cwds.data.persistence.cms.ChildClient("1234567ABC", childClient, "0XA");
    when(childClientDao.create(any(gov.ca.cwds.data.persistence.cms.ChildClient.class)))
        .thenReturn(childClientToCreate);

    Set<Client> clientDomain =
        MAPPER.readValue(fixture("fixtures/domain/ScreeningToReferral/valid/validClient.json"),
            new TypeReference<Set<Client>>() {});
    gov.ca.cwds.data.persistence.cms.Client clientToCreate =
        new gov.ca.cwds.data.persistence.cms.Client("1234567ABC",
            (Client) clientDomain.toArray()[0], "2016-10-31");
    when(clientDao.create(any(gov.ca.cwds.data.persistence.cms.Client.class)))
        .thenReturn(clientToCreate);
    when(clientDao.find("1234567ABC")).thenReturn(clientToCreate);

    Set<ReferralClient> referralClientDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validReferralClient.json"),
        new TypeReference<Set<ReferralClient>>() {});
    gov.ca.cwds.data.persistence.cms.ReferralClient referralClientToCreate =
        new gov.ca.cwds.data.persistence.cms.ReferralClient(
            (ReferralClient) referralClientDomain.toArray()[0], "2016-10-31");
    when(referralClientDao.create(any(gov.ca.cwds.data.persistence.cms.ReferralClient.class)))
        .thenReturn(referralClientToCreate);

    Set<Allegation> allegationDomain =
        MAPPER.readValue(fixture("fixtures/domain/ScreeningToReferral/valid/validAllegation.json"),
            new TypeReference<Set<Allegation>>() {});
    gov.ca.cwds.data.persistence.cms.Allegation allegationToCreate =
        new gov.ca.cwds.data.persistence.cms.Allegation("2345678ABC",
            (Allegation) allegationDomain.toArray()[0], "2016-10-31");
    when(allegationDao.create(any(gov.ca.cwds.data.persistence.cms.Allegation.class)))
        .thenReturn(allegationToCreate);
    when(allegationDao.find("2345678ABC")).thenReturn(allegationToCreate);

    Set<CrossReport> crossReportDomain =
        MAPPER.readValue(fixture("fixtures/domain/ScreeningToReferral/valid/validCrossReport.json"),
            new TypeReference<Set<CrossReport>>() {});
    gov.ca.cwds.data.persistence.cms.CrossReport crossReportToCreate =
        new gov.ca.cwds.data.persistence.cms.CrossReport("3456789ABC",
            // ((CrossReport) crossReportDomain).getThirdId(),
            (CrossReport) crossReportDomain.toArray()[0], "OXA");
    when(crossReportDao.create(any(gov.ca.cwds.data.persistence.cms.CrossReport.class)))
        .thenReturn(crossReportToCreate);
    when(crossReportDao.find("3456789ABC")).thenReturn(crossReportToCreate);

    Reporter reporterDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validReporter.json"), Reporter.class);
    gov.ca.cwds.data.persistence.cms.Reporter reporterToCreate =
        new gov.ca.cwds.data.persistence.cms.Reporter(reporterDomain, "ABC");
    when(reporterDao.create(any(gov.ca.cwds.data.persistence.cms.Reporter.class)))
        .thenReturn(reporterToCreate);


    Address addressDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validAddress.json"), Address.class);
    gov.ca.cwds.data.persistence.cms.Address addressToCreate =
        new gov.ca.cwds.data.persistence.cms.Address("3456789ABC", addressDomain, "ABC");
    when(addressDao.create(any(gov.ca.cwds.data.persistence.cms.Address.class)))
        .thenReturn(addressToCreate);
    when(addressDao.find("3456789ABC")).thenReturn(addressToCreate);

    ClientAddress clientAddressDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validClientAddress.json"),
        ClientAddress.class);
    gov.ca.cwds.data.persistence.cms.ClientAddress clientAddressToCreate =
        new gov.ca.cwds.data.persistence.cms.ClientAddress("3456789ABC", clientAddressDomain,
            "ABC");
    when(clientAddressDao.create(any(gov.ca.cwds.data.persistence.cms.ClientAddress.class)))
        .thenReturn(clientAddressToCreate);
    when(clientAddressDao.find("3456789ABC")).thenReturn(clientAddressToCreate);

    LongText longTextDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validLongText.json"), LongText.class);
    gov.ca.cwds.data.persistence.cms.LongText longTextToCreate =
        new gov.ca.cwds.data.persistence.cms.LongText("567890ABC", longTextDomain, "ABC");
    when(longTextDao.create(any(gov.ca.cwds.data.persistence.cms.LongText.class)))
        .thenReturn(longTextToCreate);

    ScreeningToReferral screeningToReferral = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/clientAddressExist.json"),
        ScreeningToReferral.class);

    try {
      Response response = screeningToReferralService.create(screeningToReferral);
      assertThat(response.getClass(), is(PostedScreeningToReferral.class));
    } catch (Exception e) {
      System.out.println("error = " + e.getMessage());
      Assert.fail("Unexpected ServiceException was thrown" + e.getMessage());
    }
  }

  @SuppressWarnings("javadoc")
  @Test
  public void testClientAddressDoesNotExsitFail() throws Exception {
    Referral referralDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validReferral.json"), Referral.class);
    gov.ca.cwds.data.persistence.cms.Referral referralToCreate =
        new gov.ca.cwds.data.persistence.cms.Referral("0123456ABC", referralDomain, "2016-10-31");
    when(referralDao.create(any(gov.ca.cwds.data.persistence.cms.Referral.class)))
        .thenReturn(referralToCreate);

    ChildClient childClient = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/childClient.json"), ChildClient.class);
    gov.ca.cwds.data.persistence.cms.ChildClient childClientToCreate =
        new gov.ca.cwds.data.persistence.cms.ChildClient("1234567ABC", childClient, "0XA");
    when(childClientDao.create(any(gov.ca.cwds.data.persistence.cms.ChildClient.class)))
        .thenReturn(childClientToCreate);

    Set<Client> clientDomain =
        MAPPER.readValue(fixture("fixtures/domain/ScreeningToReferral/valid/validClient.json"),
            new TypeReference<Set<Client>>() {});
    gov.ca.cwds.data.persistence.cms.Client clientToCreate =
        new gov.ca.cwds.data.persistence.cms.Client("1234567ABC",
            (Client) clientDomain.toArray()[0], "2016-10-31");
    when(clientDao.create(any(gov.ca.cwds.data.persistence.cms.Client.class)))
        .thenReturn(clientToCreate);
    when(clientDao.find("1234567ABC")).thenReturn(clientToCreate);

    Set<ReferralClient> referralClientDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validReferralClient.json"),
        new TypeReference<Set<ReferralClient>>() {});
    gov.ca.cwds.data.persistence.cms.ReferralClient referralClientToCreate =
        new gov.ca.cwds.data.persistence.cms.ReferralClient(
            (ReferralClient) referralClientDomain.toArray()[0], "2016-10-31");
    when(referralClientDao.create(any(gov.ca.cwds.data.persistence.cms.ReferralClient.class)))
        .thenReturn(referralClientToCreate);

    Set<Allegation> allegationDomain =
        MAPPER.readValue(fixture("fixtures/domain/ScreeningToReferral/valid/validAllegation.json"),
            new TypeReference<Set<Allegation>>() {});
    gov.ca.cwds.data.persistence.cms.Allegation allegationToCreate =
        new gov.ca.cwds.data.persistence.cms.Allegation("2345678ABC",
            (Allegation) allegationDomain.toArray()[0], "2016-10-31");
    when(allegationDao.create(any(gov.ca.cwds.data.persistence.cms.Allegation.class)))
        .thenReturn(allegationToCreate);
    when(allegationDao.find("2345678ABC")).thenReturn(allegationToCreate);

    Set<CrossReport> crossReportDomain =
        MAPPER.readValue(fixture("fixtures/domain/ScreeningToReferral/valid/validCrossReport.json"),
            new TypeReference<Set<CrossReport>>() {});
    gov.ca.cwds.data.persistence.cms.CrossReport crossReportToCreate =
        new gov.ca.cwds.data.persistence.cms.CrossReport("3456789ABC",
            // ((CrossReport) crossReportDomain).getThirdId(),
            (CrossReport) crossReportDomain.toArray()[0], "OXA");
    when(crossReportDao.create(any(gov.ca.cwds.data.persistence.cms.CrossReport.class)))
        .thenReturn(crossReportToCreate);

    Reporter reporterDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validReporter.json"), Reporter.class);
    gov.ca.cwds.data.persistence.cms.Reporter reporterToCreate =
        new gov.ca.cwds.data.persistence.cms.Reporter(reporterDomain, "ABC");
    when(reporterDao.create(any(gov.ca.cwds.data.persistence.cms.Reporter.class)))
        .thenReturn(reporterToCreate);


    Address addressDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validAddress.json"), Address.class);
    gov.ca.cwds.data.persistence.cms.Address addressToCreate =
        new gov.ca.cwds.data.persistence.cms.Address("3456789ABC", addressDomain, "ABC");
    when(addressDao.create(any(gov.ca.cwds.data.persistence.cms.Address.class)))
        .thenReturn(addressToCreate);
    when(addressDao.find("3456789ABC")).thenReturn(addressToCreate);

    ClientAddress clientAddressDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validClientAddress.json"),
        ClientAddress.class);
    gov.ca.cwds.data.persistence.cms.ClientAddress clientAddressToCreate =
        new gov.ca.cwds.data.persistence.cms.ClientAddress("4567890ABC", clientAddressDomain,
            "ABC");
    when(clientAddressDao.create(any(gov.ca.cwds.data.persistence.cms.ClientAddress.class)))
        .thenReturn(clientAddressToCreate);
    when(clientAddressDao.find("4567890ABC")).thenReturn(clientAddressToCreate);

    LongText longTextDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validLongText.json"), LongText.class);
    gov.ca.cwds.data.persistence.cms.LongText longTextToCreate =
        new gov.ca.cwds.data.persistence.cms.LongText("567890ABC", longTextDomain, "ABC");
    when(longTextDao.create(any(gov.ca.cwds.data.persistence.cms.LongText.class)))
        .thenReturn(longTextToCreate);

    ScreeningToReferral screeningToReferral = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/invalid/clientAddressIdDoesNotExist.json"),
        ScreeningToReferral.class);

    try {
      this.screeningToReferralService.create(screeningToReferral);
      Assert.fail("Expected ServiceException was not thrown");
    } catch (Exception e) {
      assertThat(
          e.getMessage().contains(
              "Legacy Id on Address does not correspond to an existing CMS/CWS Client Address"),
          is(equalTo(true)));
    }
  }

  @SuppressWarnings("javadoc")
  @Test
  public void testReporterExistSuccess() throws Exception {
    Referral referralDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validReferral.json"), Referral.class);
    gov.ca.cwds.data.persistence.cms.Referral referralToCreate =
        new gov.ca.cwds.data.persistence.cms.Referral("0123456ABC", referralDomain, "2016-10-31");
    when(referralDao.create(any(gov.ca.cwds.data.persistence.cms.Referral.class)))
        .thenReturn(referralToCreate);

    ChildClient childClient = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/childClient.json"), ChildClient.class);
    gov.ca.cwds.data.persistence.cms.ChildClient childClientToCreate =
        new gov.ca.cwds.data.persistence.cms.ChildClient("1234567ABC", childClient, "0XA");
    when(childClientDao.create(any(gov.ca.cwds.data.persistence.cms.ChildClient.class)))
        .thenReturn(childClientToCreate);

    Set<Client> clientDomain =
        MAPPER.readValue(fixture("fixtures/domain/ScreeningToReferral/valid/validClient.json"),
            new TypeReference<Set<Client>>() {});
    gov.ca.cwds.data.persistence.cms.Client clientToCreate =
        new gov.ca.cwds.data.persistence.cms.Client("1234567ABC",
            (Client) clientDomain.toArray()[0], "2016-10-31");
    when(clientDao.create(any(gov.ca.cwds.data.persistence.cms.Client.class)))
        .thenReturn(clientToCreate);
    when(clientDao.find("1234567ABC")).thenReturn(clientToCreate);

    Set<ReferralClient> referralClientDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validReferralClient.json"),
        new TypeReference<Set<ReferralClient>>() {});
    gov.ca.cwds.data.persistence.cms.ReferralClient referralClientToCreate =
        new gov.ca.cwds.data.persistence.cms.ReferralClient(
            (ReferralClient) referralClientDomain.toArray()[0], "2016-10-31");
    when(referralClientDao.create(any(gov.ca.cwds.data.persistence.cms.ReferralClient.class)))
        .thenReturn(referralClientToCreate);

    Set<Allegation> allegationDomain =
        MAPPER.readValue(fixture("fixtures/domain/ScreeningToReferral/valid/validAllegation.json"),
            new TypeReference<Set<Allegation>>() {});
    gov.ca.cwds.data.persistence.cms.Allegation allegationToCreate =
        new gov.ca.cwds.data.persistence.cms.Allegation("2345678ABC",
            (Allegation) allegationDomain.toArray()[0], "2016-10-31");
    when(allegationDao.create(any(gov.ca.cwds.data.persistence.cms.Allegation.class)))
        .thenReturn(allegationToCreate);

    Set<CrossReport> crossReportDomain =
        MAPPER.readValue(fixture("fixtures/domain/ScreeningToReferral/valid/validCrossReport.json"),
            new TypeReference<Set<CrossReport>>() {});
    gov.ca.cwds.data.persistence.cms.CrossReport crossReportToCreate =
        new gov.ca.cwds.data.persistence.cms.CrossReport("3456789ABC",
            // ((CrossReport) crossReportDomain).getThirdId(),
            (CrossReport) crossReportDomain.toArray()[0], "OXA");
    when(crossReportDao.create(any(gov.ca.cwds.data.persistence.cms.CrossReport.class)))
        .thenReturn(crossReportToCreate);
    when(crossReportDao.find("3456789ABC")).thenReturn(crossReportToCreate);

    Reporter reporterDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validReporter.json"), Reporter.class);
    gov.ca.cwds.data.persistence.cms.Reporter reporterToCreate =
        new gov.ca.cwds.data.persistence.cms.Reporter(reporterDomain, "1234567ABC");
    when(reporterDao.create(any(gov.ca.cwds.data.persistence.cms.Reporter.class)))
        .thenReturn(reporterToCreate);
    when(reporterDao.find("1234567ABC")).thenReturn(reporterToCreate);

    Address addressDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validAddress.json"), Address.class);
    gov.ca.cwds.data.persistence.cms.Address addressToCreate =
        new gov.ca.cwds.data.persistence.cms.Address("3456789ABC", addressDomain, "ABC");
    when(addressDao.create(any(gov.ca.cwds.data.persistence.cms.Address.class)))
        .thenReturn(addressToCreate);
    when(addressDao.find("3456789ABC")).thenReturn(addressToCreate);

    ClientAddress clientAddressDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validClientAddress.json"),
        ClientAddress.class);
    gov.ca.cwds.data.persistence.cms.ClientAddress clientAddressToCreate =
        new gov.ca.cwds.data.persistence.cms.ClientAddress("3456789ABC", clientAddressDomain,
            "ABC");
    when(clientAddressDao.create(any(gov.ca.cwds.data.persistence.cms.ClientAddress.class)))
        .thenReturn(clientAddressToCreate);
    when(clientAddressDao.find("3456789ABC")).thenReturn(clientAddressToCreate);

    LongText longTextDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validLongText.json"), LongText.class);
    gov.ca.cwds.data.persistence.cms.LongText longTextToCreate =
        new gov.ca.cwds.data.persistence.cms.LongText("567890ABC", longTextDomain, "ABC");
    when(longTextDao.create(any(gov.ca.cwds.data.persistence.cms.LongText.class)))
        .thenReturn(longTextToCreate);

    ScreeningToReferral screeningToReferral =
        MAPPER.readValue(fixture("fixtures/domain/ScreeningToReferral/valid/reporterExist.json"),
            ScreeningToReferral.class);

    try {
      Response response = screeningToReferralService.create(screeningToReferral);
      assertThat(response.getClass(), is(PostedScreeningToReferral.class));
    } catch (Exception e) {
      // System.out.println("error = " + e.getMessage());
      Assert.fail("Unexpected ServiceException was thrown" + e.getMessage());
    }
  }
}
