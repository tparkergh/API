package gov.ca.cwds.rest.services;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.validation.Validation;

import org.apache.commons.lang3.NotImplementedException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import gov.ca.cwds.data.cms.AddressDao;
import gov.ca.cwds.data.cms.AllegationDao;
import gov.ca.cwds.data.cms.ChildClientDao;
import gov.ca.cwds.data.cms.ClientAddressDao;
import gov.ca.cwds.data.cms.ClientDao;
import gov.ca.cwds.data.cms.CrossReportDao;
import gov.ca.cwds.data.cms.DrmsDocumentDao;
import gov.ca.cwds.data.cms.LongTextDao;
import gov.ca.cwds.data.cms.ReferralClientDao;
import gov.ca.cwds.data.cms.ReferralDao;
import gov.ca.cwds.data.cms.ReporterDao;
import gov.ca.cwds.data.cms.StaffPersonDao;
import gov.ca.cwds.data.rules.TriggerTablesDao;
import gov.ca.cwds.fixture.AddressResourceBuilder;
import gov.ca.cwds.fixture.AllegationResourceBuilder;
import gov.ca.cwds.fixture.CrossReportResourceBuilder;
import gov.ca.cwds.fixture.MockedScreeningToReferralServiceBuilder;
import gov.ca.cwds.fixture.ParticipantResourceBuilder;
import gov.ca.cwds.fixture.ScreeningToReferralResourceBuilder;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.Participant;
import gov.ca.cwds.rest.api.domain.PostedScreeningToReferral;
import gov.ca.cwds.rest.api.domain.ScreeningToReferral;
import gov.ca.cwds.rest.api.domain.cms.Address;
import gov.ca.cwds.rest.api.domain.cms.Allegation;
import gov.ca.cwds.rest.api.domain.cms.ChildClient;
import gov.ca.cwds.rest.api.domain.cms.Client;
import gov.ca.cwds.rest.api.domain.cms.ClientAddress;
import gov.ca.cwds.rest.api.domain.cms.CmsReferral;
import gov.ca.cwds.rest.api.domain.cms.CrossReport;
import gov.ca.cwds.rest.api.domain.cms.DrmsDocument;
import gov.ca.cwds.rest.api.domain.cms.LongText;
import gov.ca.cwds.rest.api.domain.cms.PostedAddress;
import gov.ca.cwds.rest.api.domain.cms.PostedClient;
import gov.ca.cwds.rest.api.domain.cms.Referral;
import gov.ca.cwds.rest.api.domain.cms.ReferralClient;
import gov.ca.cwds.rest.api.domain.cms.Reporter;
import gov.ca.cwds.rest.api.domain.error.ErrorMessage;
import gov.ca.cwds.rest.business.rules.LACountyTrigger;
import gov.ca.cwds.rest.business.rules.NonLACountyTriggers;
import gov.ca.cwds.rest.messages.MessageBuilder;
import gov.ca.cwds.rest.services.cms.AddressService;
import gov.ca.cwds.rest.services.cms.AllegationService;
import gov.ca.cwds.rest.services.cms.ChildClientService;
import gov.ca.cwds.rest.services.cms.ClientAddressService;
import gov.ca.cwds.rest.services.cms.ClientService;
import gov.ca.cwds.rest.services.cms.CrossReportService;
import gov.ca.cwds.rest.services.cms.DrmsDocumentService;
import gov.ca.cwds.rest.services.cms.LongTextService;
import gov.ca.cwds.rest.services.cms.ReferralClientService;
import gov.ca.cwds.rest.services.cms.ReferralService;
import gov.ca.cwds.rest.services.cms.ReporterService;
import gov.ca.cwds.rest.services.cms.StaffPersonIdRetriever;
import io.dropwizard.jackson.Jackson;

/**
 * 
 * @author CWDS API Team
 */
@SuppressWarnings("unused")
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
  private StaffPersonIdRetriever staffPersonIdRetriever;
  private DrmsDocumentService drmsDocumentService;
  private DrmsDocumentDao drmsDocumentDao;

  private gov.ca.cwds.data.persistence.cms.Referral referral;
  private static gov.ca.cwds.data.persistence.cms.Referral createdReferal = null;

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
    staffPersonIdRetriever = mock(StaffPersonIdRetriever.class);
    referralService = new ReferralService(referralDao, nonLACountyTriggers, laCountyTrigger,
        triggerTablesDao, staffpersonDao, staffPersonIdRetriever);

    clientDao = mock(ClientDao.class);
    staffpersonDao = mock(StaffPersonDao.class);
    triggerTablesDao = mock(TriggerTablesDao.class);
    nonLACountyTriggers = mock(NonLACountyTriggers.class);
    clientService = new ClientService(clientDao, staffpersonDao, triggerTablesDao,
        nonLACountyTriggers, staffPersonIdRetriever);

    referralClientDao = mock(ReferralClientDao.class);
    nonLACountyTriggers = mock(NonLACountyTriggers.class);
    laCountyTrigger = mock(LACountyTrigger.class);
    triggerTablesDao = mock(TriggerTablesDao.class);
    staffpersonDao = mock(StaffPersonDao.class);
    referralClientService = new ReferralClientService(referralClientDao, nonLACountyTriggers,
        laCountyTrigger, triggerTablesDao, staffpersonDao, staffPersonIdRetriever);

    allegationDao = mock(AllegationDao.class);
    allegationService = new AllegationService(allegationDao, staffPersonIdRetriever);

    crossReportDao = mock(CrossReportDao.class);
    crossReportService = new CrossReportService(crossReportDao, staffPersonIdRetriever);

    reporterDao = mock(ReporterDao.class);
    reporterService = new ReporterService(reporterDao, staffPersonIdRetriever);

    addressDao = mock(AddressDao.class);
    addressService = new AddressService(addressDao, staffPersonIdRetriever);

    clientAddressDao = mock(ClientAddressDao.class);
    laCountyTrigger = mock(LACountyTrigger.class);
    triggerTablesDao = mock(TriggerTablesDao.class);
    staffpersonDao = mock(StaffPersonDao.class);
    clientAddressService = new ClientAddressService(clientAddressDao, staffpersonDao,
        triggerTablesDao, laCountyTrigger, staffPersonIdRetriever);

    longTextDao = mock(LongTextDao.class);
    longTextService = new LongTextService(longTextDao, staffPersonIdRetriever);

    drmsDocumentDao = mock(DrmsDocumentDao.class);
    drmsDocumentService = new DrmsDocumentService(drmsDocumentDao, staffPersonIdRetriever);

    childClientDao = mock(ChildClientDao.class);
    childClientService = new ChildClientService(childClientDao, staffPersonIdRetriever);

    screeningToReferralService = new ScreeningToReferralService(referralService, clientService,
        allegationService, crossReportService, referralClientService, reporterService,
        addressService, clientAddressService, longTextService, childClientService,
        Validation.buildDefaultValidatorFactory().getValidator(), referralDao,
        staffPersonIdRetriever, new MessageBuilder(), drmsDocumentService);
  }

  @SuppressWarnings("javadoc")
  public Response screeningToReferralServiceResponse() throws Exception {

    MAPPER.configure(SerializationFeature.INDENT_OUTPUT, true);

    Referral referralDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validReferral.json"), Referral.class);
    gov.ca.cwds.data.persistence.cms.Referral referralToCreate =
        new gov.ca.cwds.data.persistence.cms.Referral("0123456ABC", referralDomain, "2016-10-31");

    DrmsDocument drmsDocumentDomain = MAPPER.readValue(
        fixture("fixtures/domain/legacy/DrmsDocument/valid/valid.json"), DrmsDocument.class);
    gov.ca.cwds.data.persistence.cms.DrmsDocument drmsDocumentToCreate =
        new gov.ca.cwds.data.persistence.cms.DrmsDocument("ABD1234568", drmsDocumentDomain, "ABC");
    when(drmsDocumentDao.create(any(gov.ca.cwds.data.persistence.cms.DrmsDocument.class)))
        .thenReturn(drmsDocumentToCreate);

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


  @SuppressWarnings("javadoc")
  @Test
  public void createReferralWithDefaultsSetsApprovalCodeNotSubmitted() throws Exception {
    ScreeningToReferral screeningToReferral = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/valid.json"), ScreeningToReferral.class);

    DrmsDocument drmsDocumentDomain = MAPPER.readValue(
        fixture("fixtures/domain/legacy/DrmsDocument/valid/valid.json"), DrmsDocument.class);
    gov.ca.cwds.data.persistence.cms.DrmsDocument drmsDocumentToCreate =
        new gov.ca.cwds.data.persistence.cms.DrmsDocument("ABD1234568", drmsDocumentDomain, "ABC");
    when(drmsDocumentDao.create(any(gov.ca.cwds.data.persistence.cms.DrmsDocument.class)))
        .thenReturn(drmsDocumentToCreate);

    LongText longTextDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validLongText.json"), LongText.class);
    gov.ca.cwds.data.persistence.cms.LongText longTextToCreate =
        new gov.ca.cwds.data.persistence.cms.LongText("567890ABC", longTextDomain, "ABC");

    when(longTextDao.create(any(gov.ca.cwds.data.persistence.cms.LongText.class)))
        .thenReturn(longTextToCreate);

    Referral referralCreated = screeningToReferralService.createReferralWithDefaults(
        screeningToReferral, "2016-08-03T01:00:00.000Z", "2016-08-03T01:00:00.000Z");
    assertThat(referralCreated.getApprovalStatusType(), is(equalTo((short) 118)));
  }

  // Delete Tests
  @Test
  public void deleteThrowsNotImplementedException() throws Exception {
    thrown.expect(NotImplementedException.class);
    screeningToReferralService.delete("string");
  }

  @Test
  public void deleteThrowsAssertionError() throws Exception {
    thrown.expect(AssertionError.class);
    try {
      screeningToReferralService.delete(new Long(1));
    } catch (AssertionError e) {
      assertEquals("Expected AssertionError", e.getMessage());
    }
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

    DrmsDocument drmsDocumentDomain = MAPPER.readValue(
        fixture("fixtures/domain/legacy/DrmsDocument/valid/valid.json"), DrmsDocument.class);
    gov.ca.cwds.data.persistence.cms.DrmsDocument drmsDocumentToCreate =
        new gov.ca.cwds.data.persistence.cms.DrmsDocument("ABD1234568", drmsDocumentDomain, "ABC");
    when(drmsDocumentDao.create(any(gov.ca.cwds.data.persistence.cms.DrmsDocument.class)))
        .thenReturn(drmsDocumentToCreate);

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
    if (response.hasMessages()) {
      List<ErrorMessage> messages = response.getMessages();
      for (ErrorMessage message : messages) {
        System.out.println(message.getMessage());
      }
    }

    assertThat(response.getClass(), is(PostedScreeningToReferral.class));
    assertThat(response.hasMessages(), is(equalTo(false)));
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
    if (response.hasMessages()) {
      List<ErrorMessage> messages = response.getMessages();
      for (ErrorMessage message : messages) {
        System.out.println(message.getMessage());
      }
    }

    assertThat(response.getClass(), is(PostedScreeningToReferral.class));
    assertThat(response.hasMessages(), is(equalTo(false)));
  }

  @SuppressWarnings("javadoc")
  @Test
  public void shouldFailWhenSpecifyingALegacyReferralIdThatDoesNotExist() throws Exception {
    MockedScreeningToReferralServiceBuilder builder = new MockedScreeningToReferralServiceBuilder();
    screeningToReferralService = builder.createScreeningToReferralService();

    ScreeningToReferral referral = new ScreeningToReferralResourceBuilder()
        .setReferralId("1234567ABC").createScreeningToReferral();

    Response response = screeningToReferralService.create(referral);
    verify(builder.getMessageBuilder())
        .addError("Legacy Id does not correspond to an existing CMS/CWS Referral");
  }

  @SuppressWarnings("javadoc")
  @Test
  public void shouldUpdateClientWhenClientIdIsPresent() throws Exception {
    String victimClientLegacyId = "ABC123DSAF";

    Participant victim =
        new ParticipantResourceBuilder().setLegacyId(victimClientLegacyId).createParticipant();
    Participant reporter = new ParticipantResourceBuilder().createReporterParticipant();
    Participant perp = new ParticipantResourceBuilder().createReporterParticipant();
    Set participants = new HashSet<>(Arrays.asList(reporter, victim, perp));
    ScreeningToReferral referral = new ScreeningToReferralResourceBuilder()
        .setParticipants(participants).createScreeningToReferral();

    gov.ca.cwds.data.persistence.cms.DrmsDocument drmsDocument =
        new gov.ca.cwds.data.persistence.cms.DrmsDocument("ABC1234560", null, null, null, null,
            null);
    clientService = mock(ClientService.class);
    Client updatedClient = mock(Client.class);
    Client foundClient = mock(Client.class);
    when(drmsDocumentDao.create(any())).thenReturn(drmsDocument);
    when(clientService.find(eq(victimClientLegacyId))).thenReturn(foundClient);
    when(clientService.update(eq(victimClientLegacyId), any())).thenReturn(updatedClient);
    screeningToReferralService = new MockedScreeningToReferralServiceBuilder()
        .addClientService(clientService).addDrmsDocumentService(drmsDocumentService)
        .addMessageBuilder(new MessageBuilder()).createScreeningToReferralService();

    screeningToReferralService.create(referral);

    verify(clientService).update(eq(victim.getLegacyId()), any());
  }

  @SuppressWarnings("javadoc")
  @Test
  public void shouldNotUpdateClientWhenUserDoesNotExists() throws Exception {
    String victimClientLegacyId = "";

    clientService = mock(ClientService.class);

    screeningToReferralService = new MockedScreeningToReferralServiceBuilder()
        .addClientService(clientService).createScreeningToReferralService();

    Participant victim =
        new ParticipantResourceBuilder().setLegacyId(victimClientLegacyId).createParticipant();
    Participant reporter = new ParticipantResourceBuilder()
        .setRoles(new HashSet<>(Arrays.asList("Mandated Reporter"))).createParticipant();
    Participant perp = new ParticipantResourceBuilder()
        .setRoles(new HashSet<>(Arrays.asList("Perpetrator"))).createParticipant();
    Set participants = new HashSet<>(Arrays.asList(reporter, victim, perp));
    ScreeningToReferral referral = new ScreeningToReferralResourceBuilder()
        .setParticipants(participants).createScreeningToReferral();

    screeningToReferralService.create(referral);

    verify(clientService, never()).update(any(), any());
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

    Boolean theErrorDetected = false;
    try {
      Response response = screeningToReferralService.create(screeningToReferral);
    } catch (Exception e) {
      if (e.getMessage()
          .contains("Legacy Id does not correspond to an existing CMS/CWS Referral")) {
        theErrorDetected = true;
      }
      assertThat(theErrorDetected, is(equalTo(true)));
    }
  }

  @SuppressWarnings("javadoc")
  @Test
  public void testScreeningToReferralParticipantWithBlankRolesSuccess() throws Exception {
    Participant unknownRoleParticipant =
        new ParticipantResourceBuilder().setRoles(new HashSet<>()).createParticipant();

    gov.ca.cwds.data.persistence.cms.DrmsDocument drmsDocument =
        new gov.ca.cwds.data.persistence.cms.DrmsDocument("ABC1234560", null, null, null, null,
            null);
    Participant victim = new ParticipantResourceBuilder().createVictimParticipant();
    Participant reporter = new ParticipantResourceBuilder().createReporterParticipant();
    Set participants = new HashSet<>(Arrays.asList(unknownRoleParticipant, victim, reporter));
    when(drmsDocumentDao.create(any())).thenReturn(drmsDocument);

    ScreeningToReferral referral = new ScreeningToReferralResourceBuilder()
        .setParticipants(participants).createScreeningToReferral();

    screeningToReferralService =
        new MockedScreeningToReferralServiceBuilder().addDrmsDocumentService(drmsDocumentService)
            .addMessageBuilder(new MessageBuilder()).createScreeningToReferralService();

    Response response = screeningToReferralService.create(referral);
    assertFalse(response.hasMessages());
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

    DrmsDocument drmsDocumentDomain = MAPPER.readValue(
        fixture("fixtures/domain/legacy/DrmsDocument/valid/valid.json"), DrmsDocument.class);
    gov.ca.cwds.data.persistence.cms.DrmsDocument drmsDocumentToCreate =
        new gov.ca.cwds.data.persistence.cms.DrmsDocument("ABD1234568", drmsDocumentDomain, "ABC");
    when(drmsDocumentDao.create(any(gov.ca.cwds.data.persistence.cms.DrmsDocument.class)))
        .thenReturn(drmsDocumentToCreate);

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
      assertThat(response.hasMessages(), is(equalTo(false)));
    } catch (Exception e) {
      // System.out.println("error = " + e.getMessage());
      Assert.fail("Unexpected ServiceException was thrown" + e.getMessage());
    }

  }

  @SuppressWarnings("javadoc")
  @Test
  public void testScreeningToReferralWithoutCrossReportsSuccess() throws Exception {
    ScreeningToReferral referral = new ScreeningToReferralResourceBuilder()
        .setCrossReports(new HashSet<>()).createScreeningToReferral();

    screeningToReferralService =
        new MockedScreeningToReferralServiceBuilder().createScreeningToReferralService();

    Response response = screeningToReferralService.create(referral);
    assertFalse(response.hasMessages());

  }

  @SuppressWarnings("javadoc")
  @Test(expected = ServiceException.class)
  // R - 00836 Self rep Ind Limit
  public void testMoreThanOneSelfReportedFail() throws Exception {
    Participant victimReporter1 = new ParticipantResourceBuilder()
        .setRoles(new HashSet<>(Arrays.asList("Non-mandated", "Victim"))).createParticipant();
    Participant victimReporter2 = new ParticipantResourceBuilder()
        .setRoles(new HashSet<>(Arrays.asList("Non-mandated", "Victim"))).createParticipant();
    Set participants = new HashSet<>(Arrays.asList(victimReporter1, victimReporter2));

    ScreeningToReferral referral = new ScreeningToReferralResourceBuilder()
        .setParticipants(participants).createScreeningToReferral();

    gov.ca.cwds.data.persistence.cms.DrmsDocument drmsDocument =
        new gov.ca.cwds.data.persistence.cms.DrmsDocument("ABC1234560", null, null, null, null,
            null);
    gov.ca.cwds.rest.api.domain.cms.Client updatedClient =
        mock(gov.ca.cwds.rest.api.domain.cms.Client.class);
    clientService = mock(ClientService.class);
    when(clientService.update(any(), any())).thenReturn(updatedClient);
    PostedClient postedClient = mock(PostedClient.class);
    when(postedClient.getId()).thenReturn("ASDFGNCBDS");
    when(clientService.create(any())).thenReturn(postedClient);
    Client foundClient = mock(Client.class);
    when(clientService.find(any())).thenReturn(foundClient);
    when(drmsDocumentDao.create(any())).thenReturn(drmsDocument);

    Address foundAddress = mock(Address.class);
    when(foundAddress.getExistingAddressId()).thenReturn("JHGFRTYUIO");
    PostedAddress postedAddress = mock(PostedAddress.class);
    when(postedAddress.getExistingAddressId()).thenReturn("FGTREWDFGH");
    addressService = mock(AddressService.class);
    when(addressService.find(any())).thenReturn(foundAddress);
    when(addressService.create(any())).thenReturn(postedAddress);

    ClientAddress foundClientAddress = mock(ClientAddress.class);
    clientAddressService = mock(ClientAddressService.class);
    when(clientAddressService.find(any())).thenReturn(foundClientAddress);

    screeningToReferralService = new MockedScreeningToReferralServiceBuilder()
        .addAddressService(addressService).addClientAddressService(clientAddressService)
        .addClientService(clientService).addDrmsDocumentService(drmsDocumentService)
        .addMessageBuilder(new MessageBuilder()).createScreeningToReferralService();

    Response response = screeningToReferralService.create(referral);

    // TODO:Reenable as soon as we remove validation exception
    // String errorMessage = " Incompatiable participants included in request";
    // assertEquals("Expected only one message to be found",1,response.getMessages().size());
    // assertEquals("Expected Incompatible participants error message to be recorded",errorMessage,
    // response.getMessages().get(0).getMessage());
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

    DrmsDocument drmsDocumentDomain = MAPPER.readValue(
        fixture("fixtures/domain/legacy/DrmsDocument/valid/valid.json"), DrmsDocument.class);
    gov.ca.cwds.data.persistence.cms.DrmsDocument drmsDocumentToCreate =
        new gov.ca.cwds.data.persistence.cms.DrmsDocument("ABD1234568", drmsDocumentDomain, "ABC");
    when(drmsDocumentDao.create(any(gov.ca.cwds.data.persistence.cms.DrmsDocument.class)))
        .thenReturn(drmsDocumentToCreate);

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

    Boolean theErrorDetected = false;
    try {
      Response response = screeningToReferralService.create(screeningToReferral);
    } catch (Exception e) {
      if (e.getMessage().contains("Incompatiable participants included in request")) {
        theErrorDetected = true;
      }
      assertThat(theErrorDetected, is(equalTo(true)));
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

    DrmsDocument drmsDocumentDomain = MAPPER.readValue(
        fixture("fixtures/domain/legacy/DrmsDocument/valid/valid.json"), DrmsDocument.class);
    gov.ca.cwds.data.persistence.cms.DrmsDocument drmsDocumentToCreate =
        new gov.ca.cwds.data.persistence.cms.DrmsDocument("ABD1234568", drmsDocumentDomain, "ABC");
    when(drmsDocumentDao.create(any(gov.ca.cwds.data.persistence.cms.DrmsDocument.class)))
        .thenReturn(drmsDocumentToCreate);

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
      assertThat(response.hasMessages(), is(equalTo(false)));
    } catch (Exception e) {
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

    DrmsDocument drmsDocumentDomain = MAPPER.readValue(
        fixture("fixtures/domain/legacy/DrmsDocument/valid/valid.json"), DrmsDocument.class);
    gov.ca.cwds.data.persistence.cms.DrmsDocument drmsDocumentToCreate =
        new gov.ca.cwds.data.persistence.cms.DrmsDocument("ABD1234568", drmsDocumentDomain, "ABC");
    when(drmsDocumentDao.create(any(gov.ca.cwds.data.persistence.cms.DrmsDocument.class)))
        .thenReturn(drmsDocumentToCreate);

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
    Boolean theErrorDetected = false;
    try {
      Response response = screeningToReferralService.create(screeningToReferral);
    } catch (Exception e) {
      if (e.getMessage().contains("Incompatiable participants included in request")) {
        theErrorDetected = true;
      }
      assertThat(theErrorDetected, is(equalTo(true)));
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

    DrmsDocument drmsDocumentDomain = MAPPER.readValue(
        fixture("fixtures/domain/legacy/DrmsDocument/valid/valid.json"), DrmsDocument.class);
    gov.ca.cwds.data.persistence.cms.DrmsDocument drmsDocumentToCreate =
        new gov.ca.cwds.data.persistence.cms.DrmsDocument("ABD1234568", drmsDocumentDomain, "ABC");
    when(drmsDocumentDao.create(any(gov.ca.cwds.data.persistence.cms.DrmsDocument.class)))
        .thenReturn(drmsDocumentToCreate);

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

    Boolean theErrorDetected = false;
    try {
      Response response = screeningToReferralService.create(screeningToReferral);
    } catch (Exception e) {
      if (e.getMessage().contains("Participant contains incompatiable roles")) {
        theErrorDetected = true;
      }
      assertThat(theErrorDetected, is(equalTo(true)));
    }
  }

  @SuppressWarnings("javadoc")
  @Test
  public void testForStreetAddressEmptyFailure() throws Exception {
    Referral referralDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validReferral.json"), Referral.class);
    gov.ca.cwds.data.persistence.cms.Referral referralToCreate =
        new gov.ca.cwds.data.persistence.cms.Referral("0123456ABC", referralDomain, "2016-10-31");
    when(referralDao.create(any(gov.ca.cwds.data.persistence.cms.Referral.class)))
        .thenReturn(referralToCreate);

    DrmsDocument drmsDocumentDomain = MAPPER.readValue(
        fixture("fixtures/domain/legacy/DrmsDocument/valid/valid.json"), DrmsDocument.class);
    gov.ca.cwds.data.persistence.cms.DrmsDocument drmsDocumentToCreate =
        new gov.ca.cwds.data.persistence.cms.DrmsDocument("ABD1234568", drmsDocumentDomain, "ABC");
    when(drmsDocumentDao.create(any(gov.ca.cwds.data.persistence.cms.DrmsDocument.class)))
        .thenReturn(drmsDocumentToCreate);

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
        fixture("fixtures/domain/ScreeningToReferral/invalid/streetAddressEmpty.json"),
        ScreeningToReferral.class);

    Boolean theErrorDetected = false;
    try {
      Response response = screeningToReferralService.create(screeningToReferral);
    } catch (Exception e) {
      if (e.getMessage().contains("Screening address is null or empty")) {
        theErrorDetected = true;
      }
      assertThat(theErrorDetected, is(equalTo(true)));
    }
  }

  @SuppressWarnings("javadoc")
  @Test
  public void testForReporterStreetAddressEmptyFailure() throws Exception {
    Referral referralDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validReferral.json"), Referral.class);
    gov.ca.cwds.data.persistence.cms.Referral referralToCreate =
        new gov.ca.cwds.data.persistence.cms.Referral("0123456ABC", referralDomain, "2016-10-31");
    when(referralDao.create(any(gov.ca.cwds.data.persistence.cms.Referral.class)))
        .thenReturn(referralToCreate);

    DrmsDocument drmsDocumentDomain = MAPPER.readValue(
        fixture("fixtures/domain/legacy/DrmsDocument/valid/valid.json"), DrmsDocument.class);
    gov.ca.cwds.data.persistence.cms.DrmsDocument drmsDocumentToCreate =
        new gov.ca.cwds.data.persistence.cms.DrmsDocument("ABD1234568", drmsDocumentDomain, "ABC");
    when(drmsDocumentDao.create(any(gov.ca.cwds.data.persistence.cms.DrmsDocument.class)))
        .thenReturn(drmsDocumentToCreate);

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
        fixture("fixtures/domain/ScreeningToReferral/invalid/reporterStreetAddressEmpty.json"),
        ScreeningToReferral.class);

    Boolean theErrorDetected = false;
    try {
      Response response = screeningToReferralService.create(screeningToReferral);
    } catch (Exception e) {
      if (e.getMessage().contains("streetName may not be null")) {
        theErrorDetected = true;
      }
      assertThat(theErrorDetected, is(equalTo(true)));
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

    DrmsDocument drmsDocumentDomain = MAPPER.readValue(
        fixture("fixtures/domain/legacy/DrmsDocument/valid/valid.json"), DrmsDocument.class);
    gov.ca.cwds.data.persistence.cms.DrmsDocument drmsDocumentToCreate =
        new gov.ca.cwds.data.persistence.cms.DrmsDocument("ABD1234568", drmsDocumentDomain, "ABC");
    when(drmsDocumentDao.create(any(gov.ca.cwds.data.persistence.cms.DrmsDocument.class)))
        .thenReturn(drmsDocumentToCreate);

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

    Boolean theErrorDetected = false;
    try {
      Response response = screeningToReferralService.create(screeningToReferral);
    } catch (Exception e) {
      if (e.getMessage().contains("Participant contains incompatiable roles")) {
        theErrorDetected = true;
      }
      assertThat(theErrorDetected, is(equalTo(true)));
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

    DrmsDocument drmsDocumentDomain = MAPPER.readValue(
        fixture("fixtures/domain/legacy/DrmsDocument/valid/valid.json"), DrmsDocument.class);
    gov.ca.cwds.data.persistence.cms.DrmsDocument drmsDocumentToCreate =
        new gov.ca.cwds.data.persistence.cms.DrmsDocument("ABD1234568", drmsDocumentDomain, "ABC");
    when(drmsDocumentDao.create(any(gov.ca.cwds.data.persistence.cms.DrmsDocument.class)))
        .thenReturn(drmsDocumentToCreate);

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

    Boolean theErrorDetected = false;
    try {
      Response response = screeningToReferralService.create(screeningToReferral);
    } catch (Exception e) {
      if (e.getMessage().contains("Participant contains incompatiable roles")) {
        theErrorDetected = true;
      }
      assertThat(theErrorDetected, is(equalTo(true)));
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

    DrmsDocument drmsDocumentDomain = MAPPER.readValue(
        fixture("fixtures/domain/legacy/DrmsDocument/valid/valid.json"), DrmsDocument.class);
    gov.ca.cwds.data.persistence.cms.DrmsDocument drmsDocumentToCreate =
        new gov.ca.cwds.data.persistence.cms.DrmsDocument("ABD1234568", drmsDocumentDomain, "ABC");
    when(drmsDocumentDao.create(any(gov.ca.cwds.data.persistence.cms.DrmsDocument.class)))
        .thenReturn(drmsDocumentToCreate);

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

    Boolean theErrorDetected = false;
    try {
      Response response = screeningToReferralService.create(screeningToReferral);
    } catch (Exception e) {
      if (e.getMessage().contains("Participant contains incompatiable roles")) {
        theErrorDetected = true;
      }
      assertThat(theErrorDetected, is(equalTo(true)));
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

    DrmsDocument drmsDocumentDomain = MAPPER.readValue(
        fixture("fixtures/domain/legacy/DrmsDocument/valid/valid.json"), DrmsDocument.class);
    gov.ca.cwds.data.persistence.cms.DrmsDocument drmsDocumentToCreate =
        new gov.ca.cwds.data.persistence.cms.DrmsDocument("ABD1234568", drmsDocumentDomain, "ABC");
    when(drmsDocumentDao.create(any(gov.ca.cwds.data.persistence.cms.DrmsDocument.class)))
        .thenReturn(drmsDocumentToCreate);

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

    Boolean theErrorDetected = false;
    try {
      Response response = screeningToReferralService.create(screeningToReferral);
    } catch (Exception e) {
      if (e.getMessage().contains("Participant contains incompatiable roles")) {
        theErrorDetected = true;
      }
      assertThat(theErrorDetected, is(equalTo(true)));
    }
  }

  @SuppressWarnings("javadoc")
  @Test
  public void testMoreThanOneParticipantRoleSuccess() throws Exception {
    Participant victimAndReporter = new ParticipantResourceBuilder()
        .setRoles(new HashSet<>(Arrays.asList("Non-mandated Reporter", "Victim")))
        .createParticipant();
    Participant perp = new ParticipantResourceBuilder().createPerpParticipant();
    Set participants = new HashSet<>(Arrays.asList(victimAndReporter, perp));

    ScreeningToReferral referral = new ScreeningToReferralResourceBuilder()
        .setParticipants(new HashSet<>(participants)).createScreeningToReferral();

    screeningToReferralService =
        new MockedScreeningToReferralServiceBuilder().createScreeningToReferralService();

    Response response = screeningToReferralService.create(referral);
    assertFalse(response.hasMessages());
  }

  @SuppressWarnings("javadoc")
  @Test
  public void testMultipleAddressPerParticipantSuccess() throws Exception {
    screeningToReferralService =
        new MockedScreeningToReferralServiceBuilder().createScreeningToReferralService();

    gov.ca.cwds.rest.api.domain.Address address1 =
        new AddressResourceBuilder().setStreetAddress("123 First St").createAddress();
    gov.ca.cwds.rest.api.domain.Address address2 =
        new AddressResourceBuilder().setStreetAddress("7466 Campbell St").createAddress();
    Participant reporter = new ParticipantResourceBuilder()
        .setRoles(new HashSet<>(Arrays.asList("Mandated Reporter", "Victim")))
        .setAddresses(new HashSet<>(Arrays.asList(address1, address2))).createParticipant();
    Participant perp = new ParticipantResourceBuilder()
        .setRoles(new HashSet<>(Arrays.asList("Perpetrator", "Victim"))).createParticipant();
    Set participants = new HashSet<>(Arrays.asList(reporter, perp));
    ScreeningToReferral referral = new ScreeningToReferralResourceBuilder()
        .setParticipants(participants).createScreeningToReferral();

    Response response = screeningToReferralService.create(referral);
    assertThat(response.getClass(), is(PostedScreeningToReferral.class));
    assertThat(response.hasMessages(), is(equalTo(false)));
  }

  @Ignore
  @SuppressWarnings("javadoc")
  @Test
  public void testNoAddressPerParticipantSuccess() throws Exception {
    Referral referralDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validReferral.json"), Referral.class);
    gov.ca.cwds.data.persistence.cms.Referral referralToCreate =
        new gov.ca.cwds.data.persistence.cms.Referral("0123456ABC", referralDomain, "2016-10-31");
    when(referralDao.create(any(gov.ca.cwds.data.persistence.cms.Referral.class)))
        .thenReturn(referralToCreate);

    DrmsDocument drmsDocumentDomain = MAPPER.readValue(
        fixture("fixtures/domain/legacy/DrmsDocument/valid/valid.json"), DrmsDocument.class);
    gov.ca.cwds.data.persistence.cms.DrmsDocument drmsDocumentToCreate =
        new gov.ca.cwds.data.persistence.cms.DrmsDocument("ABD1234568", drmsDocumentDomain, "ABC");
    when(drmsDocumentDao.create(any(gov.ca.cwds.data.persistence.cms.DrmsDocument.class)))
        .thenReturn(drmsDocumentToCreate);

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
      assertThat(response.hasMessages(), is(equalTo(false)));
    } catch (Exception e) {
      // System.out.println("error = " + e.getMessage());
      Assert.fail("Unexpected ServiceException was thrown" + e.getMessage());
    }
  }

  @Ignore()
  @SuppressWarnings("javadoc")
  @Test
  public void testReporterWithNoAddressSuccess() throws Exception {
    Referral referralDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validReferral.json"), Referral.class);
    gov.ca.cwds.data.persistence.cms.Referral referralToCreate =
        new gov.ca.cwds.data.persistence.cms.Referral("0123456ABC", referralDomain, "2016-10-31");
    when(referralDao.create(any(gov.ca.cwds.data.persistence.cms.Referral.class)))
        .thenReturn(referralToCreate);

    DrmsDocument drmsDocumentDomain = MAPPER.readValue(
        fixture("fixtures/domain/legacy/DrmsDocument/valid/valid.json"), DrmsDocument.class);
    gov.ca.cwds.data.persistence.cms.DrmsDocument drmsDocumentToCreate =
        new gov.ca.cwds.data.persistence.cms.DrmsDocument("ABD1234568", drmsDocumentDomain, "ABC");
    when(drmsDocumentDao.create(any(gov.ca.cwds.data.persistence.cms.DrmsDocument.class)))
        .thenReturn(drmsDocumentToCreate);

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
      assertThat(response.hasMessages(), is(equalTo(false)));
    } catch (Exception e) {
      // System.out.println("error = " + e.getMessage());
      Assert.fail("Unexpected ServiceException was thrown" + e.getMessage());
    }

  }


  @SuppressWarnings("javadoc")
  @Test
  public void testNoAddressForPerpetratorSuccess() throws Exception {
    Participant reporter = new ParticipantResourceBuilder()
        .setRoles(new HashSet<>(Arrays.asList("Mandated Reporter", "Victim"))).createParticipant();
    Participant perp =
        new ParticipantResourceBuilder().setRoles(new HashSet<>(Arrays.asList("Perpetrator")))
            .setAddresses(new HashSet<>()).createParticipant();
    Set participants = new HashSet<>(Arrays.asList(reporter, perp));

    ScreeningToReferral referral = new ScreeningToReferralResourceBuilder()
        .setParticipants(participants).createScreeningToReferral();

    screeningToReferralService =
        new MockedScreeningToReferralServiceBuilder().createScreeningToReferralService();

    Response response = screeningToReferralService.create(referral);
    assertFalse(response.hasMessages());
  }

  @SuppressWarnings("javadoc")
  @Test
  public void testSelfReportedReporterWithNoAddressSuccess() throws Exception {
    Participant reporter = new ParticipantResourceBuilder()
        .setRoles(new HashSet<>(Arrays.asList("Victim", "Non-mandated Reporter")))
        .setAddresses(new HashSet<>()).createParticipant();
    Participant perp = new ParticipantResourceBuilder().createPerpParticipant();
    Set participants = new HashSet<>(Arrays.asList(reporter, perp));

    gov.ca.cwds.data.persistence.cms.DrmsDocument drmsDocument =
        new gov.ca.cwds.data.persistence.cms.DrmsDocument("ABC1234560", null, null, null, null,
            null);
    ScreeningToReferral referral = new ScreeningToReferralResourceBuilder()
        .setParticipants(participants).createScreeningToReferral();

    PostedClient savedClient = mock(PostedClient.class);
    when(savedClient.getId()).thenReturn("FDDSTHJKHG");
    clientService = mock(ClientService.class);
    when(clientService.update(any(), any()))
        .thenReturn(mock(gov.ca.cwds.rest.api.domain.cms.Client.class));
    when(clientService.create(any())).thenReturn(savedClient);
    Client foundClient = mock(Client.class);
    when(clientService.find(any())).thenReturn(foundClient);
    when(drmsDocumentDao.create(any())).thenReturn(drmsDocument);

    screeningToReferralService = new MockedScreeningToReferralServiceBuilder()
        .addClientService(clientService).addDrmsDocumentService(drmsDocumentService)
        .addMessageBuilder(new MessageBuilder()).createScreeningToReferralService();

    Response response = screeningToReferralService.create(referral);
    assertFalse(response.hasMessages());
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

    DrmsDocument drmsDocumentDomain = MAPPER.readValue(
        fixture("fixtures/domain/legacy/DrmsDocument/valid/valid.json"), DrmsDocument.class);
    gov.ca.cwds.data.persistence.cms.DrmsDocument drmsDocumentToCreate =
        new gov.ca.cwds.data.persistence.cms.DrmsDocument("ABD1234568", drmsDocumentDomain, "ABC");
    when(drmsDocumentDao.create(any(gov.ca.cwds.data.persistence.cms.DrmsDocument.class)))
        .thenReturn(drmsDocumentToCreate);

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

    Boolean theErrorDetected = false;
    try {
      Response response = screeningToReferralService.create(screeningToReferral);
    } catch (Exception e) {
      if (e.getMessage().contains("parsing Start Date/Time")) {
        theErrorDetected = true;
      }
      assertThat(theErrorDetected, is(equalTo(true)));
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

    Boolean theErrorDetected = false;
    try {
      Response response = screeningToReferralService.create(screeningToReferral);
    } catch (Exception e) {
      if (e.getMessage().contains("The object to be validated must not be null")) {
        theErrorDetected = true;
      }
      assertThat(theErrorDetected, is(equalTo(true)));
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

    DrmsDocument drmsDocumentDomain = MAPPER.readValue(
        fixture("fixtures/domain/legacy/DrmsDocument/valid/valid.json"), DrmsDocument.class);
    gov.ca.cwds.data.persistence.cms.DrmsDocument drmsDocumentToCreate =
        new gov.ca.cwds.data.persistence.cms.DrmsDocument("ABD1234568", drmsDocumentDomain, "ABC");
    when(drmsDocumentDao.create(any(gov.ca.cwds.data.persistence.cms.DrmsDocument.class)))
        .thenReturn(drmsDocumentToCreate);

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

    Boolean theErrorDetected = false;
    try {
      Response response = screeningToReferralService.create(screeningToReferral);
    } catch (Exception e) {
      if (e.getMessage().contains("Screening address is null or empty")) {
        theErrorDetected = true;
      }
      assertThat(theErrorDetected, is(equalTo(true)));
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

    DrmsDocument drmsDocumentDomain = MAPPER.readValue(
        fixture("fixtures/domain/legacy/DrmsDocument/valid/valid.json"), DrmsDocument.class);
    gov.ca.cwds.data.persistence.cms.DrmsDocument drmsDocumentToCreate =
        new gov.ca.cwds.data.persistence.cms.DrmsDocument("ABD1234568", drmsDocumentDomain, "ABC");
    when(drmsDocumentDao.create(any(gov.ca.cwds.data.persistence.cms.DrmsDocument.class)))
        .thenReturn(drmsDocumentToCreate);

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
    assertThat(response.hasMessages(), is(equalTo(false)));
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

    DrmsDocument drmsDocumentDomain = MAPPER.readValue(
        fixture("fixtures/domain/legacy/DrmsDocument/valid/valid.json"), DrmsDocument.class);
    gov.ca.cwds.data.persistence.cms.DrmsDocument drmsDocumentToCreate =
        new gov.ca.cwds.data.persistence.cms.DrmsDocument("ABD1234568", drmsDocumentDomain, "ABC");
    when(drmsDocumentDao.create(any(gov.ca.cwds.data.persistence.cms.DrmsDocument.class)))
        .thenReturn(drmsDocumentToCreate);

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
    assertThat(response.hasMessages(), is(equalTo(false)));
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

    DrmsDocument drmsDocumentDomain = MAPPER.readValue(
        fixture("fixtures/domain/legacy/DrmsDocument/valid/valid.json"), DrmsDocument.class);
    gov.ca.cwds.data.persistence.cms.DrmsDocument drmsDocumentToCreate =
        new gov.ca.cwds.data.persistence.cms.DrmsDocument("ABD1234568", drmsDocumentDomain, "ABC");
    when(drmsDocumentDao.create(any(gov.ca.cwds.data.persistence.cms.DrmsDocument.class)))
        .thenReturn(drmsDocumentToCreate);

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

    Boolean theErrorDetected = false;
    try {
      Response response = screeningToReferralService.create(screeningToReferral);
    } catch (Exception e) {
      if (e.getMessage().contains("Referral must have at least one Allegation")) {
        theErrorDetected = true;
      }
      assertThat(theErrorDetected, is(equalTo(true)));
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
      assertThat(response.hasMessages(), is(equalTo(false)));
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

    DrmsDocument drmsDocumentDomain = MAPPER.readValue(
        fixture("fixtures/domain/legacy/DrmsDocument/valid/valid.json"), DrmsDocument.class);
    gov.ca.cwds.data.persistence.cms.DrmsDocument drmsDocumentToCreate =
        new gov.ca.cwds.data.persistence.cms.DrmsDocument("ABD1234568", drmsDocumentDomain, "ABC");
    when(drmsDocumentDao.create(any(gov.ca.cwds.data.persistence.cms.DrmsDocument.class)))
        .thenReturn(drmsDocumentToCreate);

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

    Boolean theErrorDetected = false;
    try {
      Response response = screeningToReferralService.create(screeningToReferral);
    } catch (Exception e) {
      if (e.getMessage().contains(
          "Allegation/Victim Person Id does not contain a Participant with a role of Victim")) {
        theErrorDetected = true;
      }
      assertThat(theErrorDetected, is(equalTo(true)));
    }
  }

  /*
   * test for the referral-AllegesAbuseOccurredAtAddressId(FKAddrs_T) is updated with the
   * referralAddress id
   */
  @SuppressWarnings("javadoc")
  @Test
  public void testAllegesAbuseOccurredAtAddressId() throws Exception {
    Referral referralDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validReferral.json"), Referral.class);
    gov.ca.cwds.data.persistence.cms.Referral referralToCreate =
        new gov.ca.cwds.data.persistence.cms.Referral("0123456ABC", referralDomain, "2016-10-31");
    when(referralDao.create(any(gov.ca.cwds.data.persistence.cms.Referral.class)))
        .thenReturn(referralToCreate);

    when(referralDao.create(any(gov.ca.cwds.data.persistence.cms.Referral.class)))
        .thenAnswer(new Answer<gov.ca.cwds.data.persistence.cms.Referral>() {

          @Override
          public gov.ca.cwds.data.persistence.cms.Referral answer(InvocationOnMock invocation)
              throws Throwable {
            gov.ca.cwds.data.persistence.cms.Referral report =
                (gov.ca.cwds.data.persistence.cms.Referral) invocation.getArguments()[0];
            createdReferal = report;
            return referralToCreate;
          }
        });

    DrmsDocument drmsDocumentDomain = MAPPER.readValue(
        fixture("fixtures/domain/legacy/DrmsDocument/valid/valid.json"), DrmsDocument.class);
    gov.ca.cwds.data.persistence.cms.DrmsDocument drmsDocumentToCreate =
        new gov.ca.cwds.data.persistence.cms.DrmsDocument("ABD1234568", drmsDocumentDomain, "ABC");
    when(drmsDocumentDao.create(any(gov.ca.cwds.data.persistence.cms.DrmsDocument.class)))
        .thenReturn(drmsDocumentToCreate);

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
    if (response.hasMessages()) {
      List<ErrorMessage> messages = response.getMessages();
      for (ErrorMessage message : messages) {
        System.out.println(message.getMessage());
      }
    }

    assertThat(response.getClass(), is(PostedScreeningToReferral.class));
    assertThat(response.hasMessages(), is(equalTo(false)));
    assertThat(createdReferal.getAllegesAbuseOccurredAtAddressId(), is(equalTo("345678ABC")));
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

    DrmsDocument drmsDocumentDomain = MAPPER.readValue(
        fixture("fixtures/domain/legacy/DrmsDocument/valid/valid.json"), DrmsDocument.class);
    gov.ca.cwds.data.persistence.cms.DrmsDocument drmsDocumentToCreate =
        new gov.ca.cwds.data.persistence.cms.DrmsDocument("ABD1234568", drmsDocumentDomain, "ABC");
    when(drmsDocumentDao.create(any(gov.ca.cwds.data.persistence.cms.DrmsDocument.class)))
        .thenReturn(drmsDocumentToCreate);

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

    Boolean theErrorDetected = false;
    try {
      Response response = screeningToReferralService.create(screeningToReferral);
    } catch (Exception e) {
      if (e.getMessage().contains(
          "Allegation/Perpetrator Person Id does not contain a Participant with a role of Perpetrator")) {
        theErrorDetected = true;
      }
      assertThat(theErrorDetected, is(equalTo(true)));
    }
  }

  @SuppressWarnings("javadoc")
  @Test
  public void shouldUpdatePerpetratorWhenAlreadyExists() throws Exception {

    String existingPerpId = "1234567ABC";
    Participant reporter =
        new ParticipantResourceBuilder().setFirstName("Barney").setLastName("Rubble")
            .setRoles(new HashSet<>(Arrays.asList("Non-mandated Reporter", "Victim")))
            .createParticipant();
    Participant perp = new ParticipantResourceBuilder().setLegacyId(existingPerpId)
        .setFirstName("Fred").setLastName("Flintsone")
        .setRoles(new HashSet<>(Arrays.asList("Perpetrator"))).createParticipant();
    Set participants = new HashSet<>(Arrays.asList(reporter, perp));

    ScreeningToReferral referral = new ScreeningToReferralResourceBuilder()
        .setParticipants(participants).createScreeningToReferral();

    String updatedReporterId = "ASDFGHJZXC";
    gov.ca.cwds.data.persistence.cms.DrmsDocument drmsDocument =
        new gov.ca.cwds.data.persistence.cms.DrmsDocument("ABC1234560", null, null, null, null,
            null);
    gov.ca.cwds.rest.api.domain.cms.Client updatedPerp =
        mock(gov.ca.cwds.rest.api.domain.cms.Client.class);
    gov.ca.cwds.rest.api.domain.cms.Client updatedReporter =
        mock(gov.ca.cwds.rest.api.domain.cms.Client.class);
    gov.ca.cwds.rest.api.domain.cms.Client foundClient =
        mock(gov.ca.cwds.rest.api.domain.cms.Client.class);
    gov.ca.cwds.rest.api.domain.cms.PostedClient savedVictim =
        mock(gov.ca.cwds.rest.api.domain.cms.PostedClient.class);
    when(savedVictim.getId()).thenReturn(updatedReporterId);
    clientService = mock(ClientService.class);
    when(clientService.update(eq(existingPerpId), any())).thenReturn(updatedPerp);
    when(clientService.create(any())).thenReturn(savedVictim);
    when(clientService.update(eq(updatedReporterId), any())).thenReturn(updatedReporter);
    when(clientService.find(any())).thenReturn(foundClient);
    when(drmsDocumentDao.create(any())).thenReturn(drmsDocument);

    clientAddressService = mock(ClientAddressService.class);
    when(clientAddressService.find(any())).thenReturn(mock(ClientAddress.class));

    gov.ca.cwds.rest.api.domain.cms.Address existingAddress =
        mock(gov.ca.cwds.rest.api.domain.cms.Address.class);
    when(existingAddress.getExistingAddressId()).thenReturn("ASDHJYTRED");
    PostedAddress postedAddress = mock(PostedAddress.class);
    when(postedAddress.getExistingAddressId()).thenReturn("QWWERTYUIJ");
    addressService = mock(AddressService.class);
    when(addressService.find(any())).thenReturn(existingAddress);
    when(addressService.create(any())).thenReturn(postedAddress);

    screeningToReferralService = new MockedScreeningToReferralServiceBuilder()
        .addClientAddressService(clientAddressService).addDrmsDocumentService(drmsDocumentService)
        .addClientService(clientService).addAddressService(addressService)
        .addMessageBuilder(new MessageBuilder()).createScreeningToReferralService();

    Response response = screeningToReferralService.create(referral);
    assertFalse(response.hasMessages());
    verify(foundClient, times(1)).update("Fred", "Flintsone");
    verify(foundClient, times(1)).update("Barney", "Rubble");
    verify(clientService).update(eq(existingPerpId), any());
  }

  @SuppressWarnings("javadoc")
  @Test
  public void shouldNotUpdatePerpetratorWhenClientIsNotFound() throws Exception {

    String existingPerpId = "1234567ABC";
    Participant reporter =
        new ParticipantResourceBuilder().setFirstName("Barney").setLastName("Rubble")
            .setRoles(new HashSet<>(Arrays.asList("Non-mandated Reporter", "Victim")))
            .createParticipant();
    Participant perp = new ParticipantResourceBuilder().setLegacyId(existingPerpId)
        .setFirstName("Fred").setLastName("Flintsone")
        .setRoles(new HashSet<>(Arrays.asList("Perpetrator"))).createParticipant();
    Set participants = new HashSet<>(Arrays.asList(reporter, perp));

    ScreeningToReferral referral = new ScreeningToReferralResourceBuilder()
        .setParticipants(participants).createScreeningToReferral();

    String updatedReporterId = "ASDFGHJZXC";
    gov.ca.cwds.data.persistence.cms.DrmsDocument drmsDocument =
        new gov.ca.cwds.data.persistence.cms.DrmsDocument("ABC1234560", null, null, null, null,
            null);
    gov.ca.cwds.rest.api.domain.cms.Client updatedPerp =
        mock(gov.ca.cwds.rest.api.domain.cms.Client.class);
    gov.ca.cwds.rest.api.domain.cms.Client updatedReporter =
        mock(gov.ca.cwds.rest.api.domain.cms.Client.class);
    gov.ca.cwds.rest.api.domain.cms.Client foundClient =
        mock(gov.ca.cwds.rest.api.domain.cms.Client.class);
    gov.ca.cwds.rest.api.domain.cms.PostedClient savedVictim =
        mock(gov.ca.cwds.rest.api.domain.cms.PostedClient.class);
    when(savedVictim.getId()).thenReturn(updatedReporterId);
    clientService = mock(ClientService.class);
    when(clientService.update(eq(existingPerpId), any())).thenReturn(updatedPerp);
    when(clientService.create(any())).thenReturn(savedVictim);
    when(clientService.update(eq(updatedReporterId), any())).thenReturn(updatedReporter);
    when(clientService.find(any())).thenReturn(null);
    when(drmsDocumentDao.create(any())).thenReturn(drmsDocument);

    clientAddressService = mock(ClientAddressService.class);
    when(clientAddressService.find(any())).thenReturn(mock(ClientAddress.class));

    gov.ca.cwds.rest.api.domain.cms.Address existingAddress =
        mock(gov.ca.cwds.rest.api.domain.cms.Address.class);
    when(existingAddress.getExistingAddressId()).thenReturn("ASDHJYTRED");
    PostedAddress postedAddress = mock(PostedAddress.class);
    when(postedAddress.getExistingAddressId()).thenReturn("QWWERTYUIJ");
    addressService = mock(AddressService.class);
    when(addressService.find(any())).thenReturn(existingAddress);
    when(addressService.create(any())).thenReturn(postedAddress);

    screeningToReferralService = new MockedScreeningToReferralServiceBuilder()
        .addClientAddressService(clientAddressService).addDrmsDocumentService(drmsDocumentService)
        .addClientService(clientService).addAddressService(addressService)
        .addMessageBuilder(new MessageBuilder()).createScreeningToReferralService();

    try {
      Response response = screeningToReferralService.create(referral);
    } catch (ServiceException e) {
      // not interested in exception for this test
    }
    verify(foundClient, times(0)).update("Fred", "Flintsone");
    verify(foundClient, times(0)).update("Barney", "Rubble");
  }

  @SuppressWarnings("javadoc")
  @Test
  public void shouldReturnErrorMessageWhenUnableToSaveClient() throws Exception {
    String existingPerpId = "1234567ABC";
    Participant reporter =
        new ParticipantResourceBuilder().setFirstName("Barney").setLastName("Rubble")
            .setRoles(new HashSet<>(Arrays.asList("Non-mandated Reporter", "Victim")))
            .createParticipant();
    Participant perp = new ParticipantResourceBuilder().setLegacyId(existingPerpId)
        .setFirstName("Fred").setLastName("Flintsone")
        .setRoles(new HashSet<>(Arrays.asList("Perpetrator"))).createParticipant();
    Set participants = new HashSet<>(Arrays.asList(reporter, perp));

    ScreeningToReferral referral = new ScreeningToReferralResourceBuilder()
        .setParticipants(participants).createScreeningToReferral();

    String updatedReporterId = "ASDFGHJZXC";
    gov.ca.cwds.data.persistence.cms.DrmsDocument drmsDocument =
        new gov.ca.cwds.data.persistence.cms.DrmsDocument("ABC1234560", null, null, null, null,
            null);
    gov.ca.cwds.rest.api.domain.cms.Client updatedPerp =
        mock(gov.ca.cwds.rest.api.domain.cms.Client.class);
    gov.ca.cwds.rest.api.domain.cms.Client updatedReporter =
        mock(gov.ca.cwds.rest.api.domain.cms.Client.class);
    gov.ca.cwds.rest.api.domain.cms.Client foundClient =
        mock(gov.ca.cwds.rest.api.domain.cms.Client.class);
    gov.ca.cwds.rest.api.domain.cms.PostedClient savedVictim =
        mock(gov.ca.cwds.rest.api.domain.cms.PostedClient.class);
    when(savedVictim.getId()).thenReturn(updatedReporterId);
    clientService = mock(ClientService.class);
    when(clientService.update(eq(existingPerpId), any())).thenReturn(updatedPerp);
    when(clientService.create(any())).thenReturn(savedVictim);
    when(clientService.update(any(), any())).thenReturn(null);
    when(clientService.find(any())).thenReturn(foundClient);
    when(drmsDocumentDao.create(any())).thenReturn(drmsDocument);

    clientAddressService = mock(ClientAddressService.class);
    when(clientAddressService.find(any())).thenReturn(mock(ClientAddress.class));

    gov.ca.cwds.rest.api.domain.cms.Address existingAddress =
        mock(gov.ca.cwds.rest.api.domain.cms.Address.class);
    when(existingAddress.getExistingAddressId()).thenReturn("ASDHJYTRED");
    PostedAddress postedAddress = mock(PostedAddress.class);
    when(postedAddress.getExistingAddressId()).thenReturn("QWWERTYUIJ");
    addressService = mock(AddressService.class);
    when(addressService.find(any())).thenReturn(existingAddress);
    when(addressService.create(any())).thenReturn(postedAddress);

    MessageBuilder messageBuilder = mock(MessageBuilder.class);
    screeningToReferralService = new MockedScreeningToReferralServiceBuilder()
        .addClientAddressService(clientAddressService).addDrmsDocumentService(drmsDocumentService)
        .addClientService(clientService).addAddressService(addressService)
        .addMessageBuilder(messageBuilder).createScreeningToReferralService();

    Response response = screeningToReferralService.create(referral);
    verify(messageBuilder, atLeastOnce()).addError("Unable to save Client");
  }

  @SuppressWarnings("javadoc")
  @Test
  public void testClientDoesNotExsitFail() throws Exception {
    String errorMessage =
        " Legacy Id of Participant does not correspond to an existing CWS/CMS Client ";
    String badLegacyId = "IUKNOWNIDI";

    Participant reporter = new ParticipantResourceBuilder().createReporterParticipant();
    Participant victim = new ParticipantResourceBuilder().createVictimParticipant();
    Participant perp = new ParticipantResourceBuilder().setLegacyId(badLegacyId)
        .setRoles(new HashSet<>(Arrays.asList("Perpetrator"))).createParticipant();
    Set participants = new HashSet<>(Arrays.asList(reporter, perp, victim));

    ScreeningToReferral referral = new ScreeningToReferralResourceBuilder()
        .setParticipants(participants).createScreeningToReferral();
    gov.ca.cwds.data.persistence.cms.DrmsDocument drmsDocument =
        new gov.ca.cwds.data.persistence.cms.DrmsDocument("ABC1234560", null, null, null, null,
            null);

    PostedClient postedClient = mock(PostedClient.class);
    when(postedClient.getId()).thenReturn("OIUYTREEWQ");
    clientService = mock(ClientService.class);
    when(clientService.update(eq(badLegacyId), any())).thenReturn(null);
    when(clientService.create(any())).thenReturn(postedClient);
    when(clientService.find(any())).thenReturn(null);
    when(drmsDocumentDao.create(any())).thenReturn(drmsDocument);
    screeningToReferralService = new MockedScreeningToReferralServiceBuilder()
        .addClientService(clientService).addDrmsDocumentService(drmsDocumentService)
        .addMessageBuilder(new MessageBuilder()).createScreeningToReferralService();


    try {
      Response response = screeningToReferralService.create(referral);
    } catch (ServiceException e) {
      // not interested in exception for this test
      assertTrue("Expected thrown error to contain error", e.getMessage().contains(errorMessage));

    }
    verify(clientService, never()).update(eq(badLegacyId), any());
    // TODO:Reenable when exception removed as message feedback mechanism.
    // assertEquals("Expected only the legacy Id error message", 1, response.getMessages().size());
    // assertEquals("Expected messge to be Legacy errorMessage", errorMessage,
    // response.getMessages().get(0).getMessage());
  }

  @SuppressWarnings("javadoc")
  @Test
  public void testAllegationExsitSuccess() throws Exception {

    gov.ca.cwds.rest.api.domain.Allegation allegation =
        new AllegationResourceBuilder().setLegacyId("GHJKLCVBNM").createAllegation();
    ScreeningToReferral referral = new ScreeningToReferralResourceBuilder()
        .setAllegations(new HashSet<>(Arrays.asList(allegation))).createScreeningToReferral();

    gov.ca.cwds.data.persistence.cms.DrmsDocument drmsDocument =
        new gov.ca.cwds.data.persistence.cms.DrmsDocument("ABC1234560", null, null, null, null,
            null);
    allegationService = mock(AllegationService.class);
    when(drmsDocumentDao.create(any())).thenReturn(drmsDocument);
    when(allegationService.find(allegation.getLegacyId())).thenReturn(mock(Allegation.class));
    MessageBuilder messageBuilder = new MessageBuilder();
    screeningToReferralService = new MockedScreeningToReferralServiceBuilder()
        .addMessageBuilder(messageBuilder).addAllegationService(allegationService)
        .addDrmsDocumentService(drmsDocumentService).createScreeningToReferralService();

    Response response = screeningToReferralService.create(referral);

    verify(allegationService).find(eq(allegation.getLegacyId()));
    assertFalse(response.hasMessages());
  }

  @SuppressWarnings("javadoc")
  @Test
  public void testAllegationDoesNotExistFail() throws Exception {
    Referral referralDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validReferral.json"), Referral.class);
    gov.ca.cwds.data.persistence.cms.Referral referralToCreate =
        new gov.ca.cwds.data.persistence.cms.Referral("0123456ABC", referralDomain, "2016-10-31");
    when(referralDao.create(any(gov.ca.cwds.data.persistence.cms.Referral.class)))
        .thenReturn(referralToCreate);

    DrmsDocument drmsDocumentDomain = MAPPER.readValue(
        fixture("fixtures/domain/legacy/DrmsDocument/valid/valid.json"), DrmsDocument.class);
    gov.ca.cwds.data.persistence.cms.DrmsDocument drmsDocumentToCreate =
        new gov.ca.cwds.data.persistence.cms.DrmsDocument("ABD1234568", drmsDocumentDomain, "ABC");
    when(drmsDocumentDao.create(any(gov.ca.cwds.data.persistence.cms.DrmsDocument.class)))
        .thenReturn(drmsDocumentToCreate);

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

    Boolean theErrorDetected = false;
    try {
      Response response = screeningToReferralService.create(screeningToReferral);
    } catch (Exception e) {
      if (e.getMessage().contains(
          " Legacy Id on Allegation does not correspond to an existing CMS/CWS Allegation ")) {
        theErrorDetected = true;
      }
      assertThat(theErrorDetected, is(equalTo(true)));
    }
  }

  @SuppressWarnings("javadoc")
  @Test
  public void testCrossReportDoesNotExistFail() throws Exception {
    Referral referralDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validReferral.json"), Referral.class);
    gov.ca.cwds.data.persistence.cms.Referral referralToCreate =
        new gov.ca.cwds.data.persistence.cms.Referral("0123456ABC", referralDomain, "2016-10-31");
    when(referralDao.create(any(gov.ca.cwds.data.persistence.cms.Referral.class)))
        .thenReturn(referralToCreate);

    DrmsDocument drmsDocumentDomain = MAPPER.readValue(
        fixture("fixtures/domain/legacy/DrmsDocument/valid/valid.json"), DrmsDocument.class);
    gov.ca.cwds.data.persistence.cms.DrmsDocument drmsDocumentToCreate =
        new gov.ca.cwds.data.persistence.cms.DrmsDocument("ABD1234568", drmsDocumentDomain, "ABC");
    when(drmsDocumentDao.create(any(gov.ca.cwds.data.persistence.cms.DrmsDocument.class)))
        .thenReturn(drmsDocumentToCreate);

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

    Boolean theErrorDetected = false;
    try {
      Response response = screeningToReferralService.create(screeningToReferral);
    } catch (Exception e) {
      if (e.getMessage().contains(
          "Legacy Id on Cross Report does not correspond to an existing CMS/CWS Cross Report")) {
        theErrorDetected = true;
      }
      assertThat(theErrorDetected, is(equalTo(true)));
    }
  }

  public void testCrossReportExsitSuccess() throws Exception {
    gov.ca.cwds.rest.api.domain.CrossReport crossReport =
        new CrossReportResourceBuilder().setLegacyId("ASDFGHJKLQ").createCrossReport();

    ScreeningToReferral referral = new ScreeningToReferralResourceBuilder()
        .setCrossReports(new HashSet<>(Arrays.asList(crossReport))).createScreeningToReferral();
    crossReportService = mock(CrossReportService.class);
    when(crossReportService.find(eq(crossReport.getLegacyId())))
        .thenReturn(mock(gov.ca.cwds.rest.api.domain.cms.CrossReport.class));
    screeningToReferralService =
        new MockedScreeningToReferralServiceBuilder().addCrossReportService(crossReportService)
            .addMessageBuilder(new MessageBuilder()).createScreeningToReferralService();

    Response response = screeningToReferralService.create(referral);
    assertFalse(response.hasMessages());
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

    DrmsDocument drmsDocumentDomain = MAPPER.readValue(
        fixture("fixtures/domain/legacy/DrmsDocument/valid/valid.json"), DrmsDocument.class);
    gov.ca.cwds.data.persistence.cms.DrmsDocument drmsDocumentToCreate =
        new gov.ca.cwds.data.persistence.cms.DrmsDocument("ABD1234568", drmsDocumentDomain, "ABC");
    when(drmsDocumentDao.create(any(gov.ca.cwds.data.persistence.cms.DrmsDocument.class)))
        .thenReturn(drmsDocumentToCreate);

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

    Boolean theErrorDetected = false;
    try {
      Response response = screeningToReferralService.create(screeningToReferral);
    } catch (Exception e) {
      if (e.getMessage().contains(
          "Legacy Id on Cross Report does not correspond to an existing CMS/CWS Cross Report")) {
        theErrorDetected = true;
      }
      assertThat(theErrorDetected, is(equalTo(true)));
    }
  }

  @SuppressWarnings("javadoc")
  @Test
  public void testAddressExsitSuccess() throws Exception {
    // Referral referralDom

    gov.ca.cwds.rest.api.domain.Address address =
        new AddressResourceBuilder().setLegacyId("ASDFGHJKLQ").createAddress();
    Participant victim =
        new ParticipantResourceBuilder().setRoles(new HashSet<>(Arrays.asList("Victim")))
            .setAddresses(new HashSet<>(Arrays.asList(address))).createParticipant();
    Participant perp = new ParticipantResourceBuilder().createPerpParticipant();
    Participant reporter = new ParticipantResourceBuilder().createReporterParticipant();
    Set participants = new HashSet<>(Arrays.asList(victim, perp, reporter));

    ScreeningToReferral referral = new ScreeningToReferralResourceBuilder()
        .setParticipants(participants).createScreeningToReferral();

    gov.ca.cwds.data.persistence.cms.DrmsDocument drmsDocument =
        new gov.ca.cwds.data.persistence.cms.DrmsDocument("ABC1234560", null, null, null, null,
            null);
    Address victimFoundAddress = mock(Address.class);
    when(victimFoundAddress.getExistingAddressId()).thenReturn("ADDRESS_ID");
    PostedAddress perpCreatedAddress = mock(PostedAddress.class);
    when(perpCreatedAddress.getExistingAddressId()).thenReturn("PERPADDRID");
    addressService = mock(AddressService.class);
    when(addressService.find(address.getLegacyId())).thenReturn(victimFoundAddress);
    when(addressService.create(any())).thenReturn(perpCreatedAddress);
    when(drmsDocumentDao.create(any())).thenReturn(drmsDocument);

    clientAddressService = mock(ClientAddressService.class);
    when(clientAddressService.find(address.getLegacyId())).thenReturn(mock(ClientAddress.class));
    MessageBuilder messageBuilder = new MessageBuilder();
    screeningToReferralService =
        new MockedScreeningToReferralServiceBuilder().addMessageBuilder(messageBuilder)
            .addAddressService(addressService).addDrmsDocumentService(drmsDocumentService)
            .addClientAddressService(clientAddressService).createScreeningToReferralService();
    Response response = screeningToReferralService.create(referral);

    verify(addressService).find(eq(address.getLegacyId()));
    assertFalse(response.hasMessages());
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

    DrmsDocument drmsDocumentDomain = MAPPER.readValue(
        fixture("fixtures/domain/legacy/DrmsDocument/valid/valid.json"), DrmsDocument.class);
    gov.ca.cwds.data.persistence.cms.DrmsDocument drmsDocumentToCreate =
        new gov.ca.cwds.data.persistence.cms.DrmsDocument("ABD1234568", drmsDocumentDomain, "ABC");
    when(drmsDocumentDao.create(any(gov.ca.cwds.data.persistence.cms.DrmsDocument.class)))
        .thenReturn(drmsDocumentToCreate);

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

    Boolean theErrorDetected = false;
    try {
      Response response = screeningToReferralService.create(screeningToReferral);
    } catch (Exception e) {
      if (e.getMessage()
          .contains("Legacy Id on Address does not correspond to an existing CMS/CWS Address")) {
        theErrorDetected = true;
      }
      assertThat(theErrorDetected, is(equalTo(true)));
    }
  }

  @SuppressWarnings("javadoc")
  @Test
  public void testClientAddressExsitSuccess() throws Exception {
    String addressId1 = "1111111111";
    gov.ca.cwds.rest.api.domain.Address address1 =
        new AddressResourceBuilder().setLegacyId("1111111111").setLegacySourceTable("ADDRS_T")
            .setStreetAddress("123 First St").createAddress();
    String addressId2 = "2222222222";
    gov.ca.cwds.rest.api.domain.Address address2 =
        new AddressResourceBuilder().setLegacyId("2222222222").setLegacySourceTable("ADDRS_T")
            .setStreetAddress("123 First St").createAddress();

    Participant selfReportingVictim = new ParticipantResourceBuilder()
        .setRoles(new HashSet(Arrays.asList("Non-mandated Reporter", "Victim")))
        .setAddresses(new HashSet<>(Arrays.asList(address1))).createParticipant();
    Participant perp = new ParticipantResourceBuilder()
        .setAddresses(new HashSet<>(Arrays.asList(address2))).createPerpParticipant();
    Set participants = new HashSet(Arrays.asList(selfReportingVictim, perp));
    ScreeningToReferral referral = new ScreeningToReferralResourceBuilder()
        .setParticipants(participants).createScreeningToReferral();

    // mock clientAddressService to find our address
    gov.ca.cwds.data.persistence.cms.DrmsDocument drmsDocument =
        new gov.ca.cwds.data.persistence.cms.DrmsDocument("ABC1234560", null, null, null, null,
            null);
    ClientAddress clientAddress = mock(ClientAddress.class);
    Client updatedClient = mock(Client.class);
    PostedClient savedClient = mock(PostedClient.class);
    when(savedClient.getId()).thenReturn("ASDFGHHYTR");
    clientAddressService = mock(ClientAddressService.class);
    when(clientAddressService.find(any())).thenReturn(clientAddress);
    clientService = mock(ClientService.class);
    when(clientService.find(any())).thenReturn(savedClient);
    when(clientService.create(any())).thenReturn(savedClient);
    when(clientService.update(any(), any())).thenReturn(updatedClient);
    when(drmsDocumentDao.create(any())).thenReturn(drmsDocument);

    Address foundAddress = mock(Address.class);
    when(foundAddress.getExistingAddressId()).thenReturn("ZXCVBNMKJH");
    PostedAddress postedAddress = mock(PostedAddress.class);
    when(postedAddress.getExistingAddressId()).thenReturn("ZXCVBNMKJH");

    addressService = mock(AddressService.class);
    when(addressService.find(any())).thenReturn(foundAddress);
    when(addressService.create(any())).thenReturn(postedAddress);

    screeningToReferralService = new MockedScreeningToReferralServiceBuilder()
        .addAddressService(addressService).addClientAddressService(clientAddressService)
        .addClientService(clientService).addDrmsDocumentService(drmsDocumentService)
        .addMessageBuilder(new MessageBuilder()).createScreeningToReferralService();

    Response response = screeningToReferralService.create(referral);

    assertFalse(response.hasMessages());
    verify(addressService).find(eq(addressId1));
    verify(addressService).find(eq(addressId2));
    verify(clientAddressService).find(eq(addressId1));
    verify(clientAddressService).find(eq(addressId2));
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

    DrmsDocument drmsDocumentDomain = MAPPER.readValue(
        fixture("fixtures/domain/legacy/DrmsDocument/valid/valid.json"), DrmsDocument.class);
    gov.ca.cwds.data.persistence.cms.DrmsDocument drmsDocumentToCreate =
        new gov.ca.cwds.data.persistence.cms.DrmsDocument("ABD1234568", drmsDocumentDomain, "ABC");
    when(drmsDocumentDao.create(any(gov.ca.cwds.data.persistence.cms.DrmsDocument.class)))
        .thenReturn(drmsDocumentToCreate);

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

    Boolean theErrorDetected = false;
    try {
      Response response = screeningToReferralService.create(screeningToReferral);
    } catch (Exception e) {
      if (e.getMessage().contains(
          "Legacy Id on Address does not correspond to an existing CMS/CWS Client Address")) {
        theErrorDetected = true;
      }
      assertThat(theErrorDetected, is(equalTo(true)));
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

    DrmsDocument drmsDocumentDomain = MAPPER.readValue(
        fixture("fixtures/domain/legacy/DrmsDocument/valid/valid.json"), DrmsDocument.class);
    gov.ca.cwds.data.persistence.cms.DrmsDocument drmsDocumentToCreate =
        new gov.ca.cwds.data.persistence.cms.DrmsDocument("ABD1234568", drmsDocumentDomain, "ABC");
    when(drmsDocumentDao.create(any(gov.ca.cwds.data.persistence.cms.DrmsDocument.class)))
        .thenReturn(drmsDocumentToCreate);

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
        new gov.ca.cwds.data.persistence.cms.Reporter(reporterDomain, "0123456ABC");
    when(reporterDao.create(any(gov.ca.cwds.data.persistence.cms.Reporter.class)))
        .thenReturn(reporterToCreate);
    when(reporterDao.find("0123456ABC")).thenReturn(reporterToCreate);

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
      assertThat(response.hasMessages(), is(equalTo(false)));
    } catch (Exception e) {
      // System.out.println("error = " + e.getMessage());
      Assert.fail("Unexpected ServiceException was thrown" + e.getMessage());
    }
  }

  @SuppressWarnings("javadoc")
  @Test
  public void testAnonymousReporterSuccess() throws Exception {
    Referral referralDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validReferral.json"), Referral.class);
    gov.ca.cwds.data.persistence.cms.Referral referralToCreate =
        new gov.ca.cwds.data.persistence.cms.Referral("0123456ABC", referralDomain, "2016-10-31");
    when(referralDao.create(any(gov.ca.cwds.data.persistence.cms.Referral.class)))
        .thenReturn(referralToCreate);

    DrmsDocument drmsDocumentDomain = MAPPER.readValue(
        fixture("fixtures/domain/legacy/DrmsDocument/valid/valid.json"), DrmsDocument.class);
    gov.ca.cwds.data.persistence.cms.DrmsDocument drmsDocumentToCreate =
        new gov.ca.cwds.data.persistence.cms.DrmsDocument("ABD1234568", drmsDocumentDomain, "ABC");
    when(drmsDocumentDao.create(any(gov.ca.cwds.data.persistence.cms.DrmsDocument.class)))
        .thenReturn(drmsDocumentToCreate);

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

    ScreeningToReferral screeningToReferral = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/referralWithAnonymousReporter.json"),
        ScreeningToReferral.class);

    try {
      Response response = screeningToReferralService.create(screeningToReferral);
      assertThat(response.getClass(), is(PostedScreeningToReferral.class));
      assertThat(response.hasMessages(), is(equalTo(false)));
    } catch (Exception e) {
      // System.out.println("error = " + e.getMessage());
      Assert.fail("Unexpected ServiceException was thrown" + e.getMessage());
    }
  }

  @SuppressWarnings("javadoc")
  @Test
  public void testMultipleVictimSuccess() throws Exception {
    Participant victim1 =
        new ParticipantResourceBuilder().setFirstName("Sally").createVictimParticipant();
    Participant victim2 =
        new ParticipantResourceBuilder().setFirstName("Fred").createVictimParticipant();
    Participant reporter = new ParticipantResourceBuilder().createReporterParticipant();
    Participant perp = new ParticipantResourceBuilder().createPerpParticipant();
    Set participants = new HashSet<>(Arrays.asList(reporter, perp, victim1, victim2));
    int numberOfClientsThatAreNotReporters = 3;

    gov.ca.cwds.data.persistence.cms.DrmsDocument drmsDocument =
        new gov.ca.cwds.data.persistence.cms.DrmsDocument("ABC1234560", null, null, null, null,
            null);
    ScreeningToReferral referral = new ScreeningToReferralResourceBuilder()
        .setParticipants(participants).createScreeningToReferral();

    PostedClient savedClient = mock(PostedClient.class);
    when(savedClient.getId()).thenReturn("ASDFGHJKWE");
    clientService = mock(ClientService.class);
    when(clientService.create(any())).thenReturn(savedClient);
    when(drmsDocumentDao.create(any())).thenReturn(drmsDocument);

    screeningToReferralService = new MockedScreeningToReferralServiceBuilder()
        .addClientService(clientService).addDrmsDocumentService(drmsDocumentService)
        .addMessageBuilder(new MessageBuilder()).createScreeningToReferralService();

    Response response = screeningToReferralService.create(referral);
    assertFalse(response.hasMessages());
    verify(clientService, times(numberOfClientsThatAreNotReporters)).create(any());
  }
}
