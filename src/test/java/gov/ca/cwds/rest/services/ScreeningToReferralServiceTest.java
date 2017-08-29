package gov.ca.cwds.rest.services;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import gov.ca.cwds.fixture.AllegationEntityBuilder;
import gov.ca.cwds.fixture.AllegationPerpetratorHistoryEntityBuilder;
import gov.ca.cwds.fixture.ClientEntityBuilder;
import gov.ca.cwds.fixture.CrossReportEntityBuilder;
import gov.ca.cwds.fixture.LongTextEntityBuilder;
import gov.ca.cwds.fixture.ReporterResourceBuilder;
import gov.ca.cwds.helper.CmsIdGenerator;
import gov.ca.cwds.rest.api.domain.cms.PostedAllegation;
import gov.ca.cwds.rest.api.domain.cms.PostedAllegationPerpetratorHistory;
import gov.ca.cwds.rest.api.domain.cms.PostedDrmsDocument;
import gov.ca.cwds.rest.api.domain.cms.PostedLongText;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.Validation;
import javax.validation.Validator;

import org.apache.commons.lang3.NotImplementedException;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.fasterxml.jackson.databind.ObjectMapper;

import gov.ca.cwds.data.cms.AddressDao;
import gov.ca.cwds.data.cms.AllegationDao;
import gov.ca.cwds.data.cms.AllegationPerpetratorHistoryDao;
import gov.ca.cwds.data.cms.AssignmentDao;
import gov.ca.cwds.data.cms.ChildClientDao;
import gov.ca.cwds.data.cms.ClientAddressDao;
import gov.ca.cwds.data.cms.ClientDao;
import gov.ca.cwds.data.cms.CrossReportDao;
import gov.ca.cwds.data.cms.DrmsDocumentDao;
import gov.ca.cwds.data.cms.LongTextDao;
import gov.ca.cwds.data.cms.ReferralClientDao;
import gov.ca.cwds.data.cms.ReferralDao;
import gov.ca.cwds.data.cms.ReporterDao;
import gov.ca.cwds.data.cms.SsaName3Dao;
import gov.ca.cwds.data.cms.StaffPersonDao;
import gov.ca.cwds.data.cms.TestSystemCodeCache;
import gov.ca.cwds.data.rules.TriggerTablesDao;
import gov.ca.cwds.fixture.AddressResourceBuilder;
import gov.ca.cwds.fixture.AllegationResourceBuilder;
import gov.ca.cwds.fixture.CrossReportResourceBuilder;
import gov.ca.cwds.fixture.MockedScreeningToReferralServiceBuilder;
import gov.ca.cwds.fixture.ParticipantResourceBuilder;
import gov.ca.cwds.fixture.ScreeningToReferralResourceBuilder;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.LegacyDescriptor;
import gov.ca.cwds.rest.api.domain.Participant;
import gov.ca.cwds.rest.api.domain.PostedScreeningToReferral;
import gov.ca.cwds.rest.api.domain.ScreeningToReferral;
import gov.ca.cwds.rest.api.domain.cms.Address;
import gov.ca.cwds.rest.api.domain.cms.Allegation;
import gov.ca.cwds.rest.api.domain.cms.ChildClient;
import gov.ca.cwds.rest.api.domain.cms.Client;
import gov.ca.cwds.rest.api.domain.cms.ClientAddress;
import gov.ca.cwds.rest.api.domain.cms.CrossReport;
import gov.ca.cwds.rest.api.domain.cms.PostedAddress;
import gov.ca.cwds.rest.api.domain.cms.PostedClient;
import gov.ca.cwds.rest.api.domain.cms.Reporter;
import gov.ca.cwds.rest.business.rules.ExternalInterfaceTables;
import gov.ca.cwds.rest.business.rules.LACountyTrigger;
import gov.ca.cwds.rest.business.rules.NonLACountyTriggers;
import gov.ca.cwds.rest.business.rules.Reminders;
import gov.ca.cwds.rest.business.rules.UpperCaseTables;
import gov.ca.cwds.rest.messages.MessageBuilder;
import gov.ca.cwds.rest.services.cms.AddressService;
import gov.ca.cwds.rest.services.cms.AllegationPerpetratorHistoryService;
import gov.ca.cwds.rest.services.cms.AllegationService;
import gov.ca.cwds.rest.services.cms.AssignmentService;
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
import gov.ca.cwds.rest.services.referentialintegrity.RIAllegation;
import gov.ca.cwds.rest.services.referentialintegrity.RIAllegationPerpetratorHistory;
import gov.ca.cwds.rest.services.referentialintegrity.RIAssignment;
import gov.ca.cwds.rest.services.referentialintegrity.RIChildClient;
import gov.ca.cwds.rest.services.referentialintegrity.RIClientAddress;
import gov.ca.cwds.rest.services.referentialintegrity.RICrossReport;
import io.dropwizard.jackson.Jackson;

/**
 * 
 * @author CWDS API Team
 */
@SuppressWarnings("unused")
public class ScreeningToReferralServiceTest {

  public static final String NON_EXISTING_REFERRAL_ID = "1234567ABC";
  private final String validReferralId = "X2WXo678Ac";

  private ScreeningToReferralResourceBuilder defaultReferralBuilder;
  private ScreeningToReferralService screeningToReferralService;
  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

  private ReferralService referralService;
  private ClientService clientService;
  private ReferralClientService referralClientService;
  private AllegationService allegationService;
  private AllegationPerpetratorHistoryService allegationPerpetratorHistoryService;
  private CrossReportService crossReportService;
  private ReporterService reporterService;
  private AddressService addressService;
  private ClientAddressService clientAddressService;
  private ChildClientService childClientService;
  private LongTextService longTextService;
  private AssignmentService assignmentService;
  private ParticipantService participantService;
  private RIChildClient riChildClient;
  private RIAllegationPerpetratorHistory riAllegationPerpetratorHistory;
  private RIAssignment riAssignment;
  private RIClientAddress riClientAddress;
  private RIAllegation riAllegation;
  private RICrossReport riCrossReport;

  private ReferralDao referralDao;
  private ClientDao clientDao;
  private ReferralClientDao referralClientDao;
  private AllegationDao allegationDao;
  private AllegationPerpetratorHistoryDao allegationPerpetratorHistoryDao;
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
  private DrmsDocumentService drmsDocumentService;
  private AssignmentDao assignmentDao;
  private SsaName3Dao ssaName3Dao;
  private Reminders reminders;
  private UpperCaseTables upperCaseTables;
  private Validator validator;
  private ExternalInterfaceTables externalInterfaceTables;

  private Participant defaultVictim;
  private Participant defaultReporter;
  private Participant defaultMandatedReporter;
  private Participant defaultPerpetrator;

  private MessageBuilder messageBuilder;

  private gov.ca.cwds.data.persistence.cms.Referral referral;
  private static gov.ca.cwds.data.persistence.cms.Referral createdReferal = null;

  private DateTime lastUpdateDate;
  private DateTime updatedTime;

  /**
   * Initialize system code cache
   */
  TestSystemCodeCache testSystemCodeCache = new TestSystemCodeCache();

  @SuppressWarnings("javadoc")
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @SuppressWarnings("javadoc")
  @Before
  public void setup() throws Exception {
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    defaultReferralBuilder = new ScreeningToReferralResourceBuilder();

    StaffPersonIdRetriever staffPersonIdRetriever = mock(StaffPersonIdRetriever.class);

    DrmsDocumentDao drmsDocumentDao = mock(DrmsDocumentDao.class);
    drmsDocumentService = new DrmsDocumentService(drmsDocumentDao, staffPersonIdRetriever);

    reminders = mock(Reminders.class);

    lastUpdateDate = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
        .parseDateTime("2010-03-14T13:33:12.456-0700");
    updatedTime = lastUpdateDate.plusHours(2);
    DateTime modifiedLastUpdateDate = DateTimeFormat.forPattern("yyyy-MM-dd-HH.mm.ss.SSS")
        .parseDateTime("2010-03-14-13.33.12.456");

    referralClientService = mock(ReferralClientService.class);

    gov.ca.cwds.rest.api.domain.cms.Address existingAddress =
        mock(gov.ca.cwds.rest.api.domain.cms.Address.class);
    when(existingAddress.getExistingAddressId()).thenReturn("ASDHJYTRED");

    PostedAddress postedAddress = mock(PostedAddress.class);
    when(postedAddress.getExistingAddressId()).thenReturn("QWWERTYUIJ");
    gov.ca.cwds.data.persistence.cms.Client savedEntityClient = new ClientEntityBuilder()
        .setId("1234567ABC")
        .build();
    PostedClient savedClient = new PostedClient(savedEntityClient, false);
    clientService = mock(ClientService.class);
    when(clientService.createWithSingleTimestamp(any(), any())).thenReturn(savedClient);

    clientAddressService = mock(ClientAddressService.class);
    when(clientAddressService.find(any())).thenReturn(mock(ClientAddress.class));
    when(clientAddressService.findByAddressAndClient(any(), any())).thenReturn(mock(List.class));

    childClientService = mock(ChildClientService.class);
    ChildClient childClient = mock(ChildClient.class);
    when(childClientService.create(any())).thenReturn(childClient);

    addressService = mock(AddressService.class);

    allegationService = mock(AllegationService.class);
    gov.ca.cwds.data.persistence.cms.Allegation allegation = new AllegationEntityBuilder().build();
    PostedAllegation postedAllegation = new PostedAllegation(allegation);
    when(allegationService.createWithSingleTimestamp(any(), any())).thenReturn(postedAllegation);
    gov.ca.cwds.data.persistence.cms.AllegationPerpetratorHistory allegationPerpetratorHistory = new AllegationPerpetratorHistoryEntityBuilder().build();
    PostedAllegationPerpetratorHistory postedAllegationPerpHistory = new PostedAllegationPerpetratorHistory(allegationPerpetratorHistory);
    allegationPerpetratorHistoryService = mock(AllegationPerpetratorHistoryService.class);
    when(allegationPerpetratorHistoryService.createWithSingleTimestamp(any(), any())).thenReturn(postedAllegationPerpHistory);

    CrossReport mockLegacyCrossReport = mock(CrossReport.class);
    CrossReport crossReport = new CrossReportEntityBuilder().build();
    crossReportService = mock(CrossReportService.class);
    when(crossReportService.createWithSingleTimestamp(any(), any())).thenReturn(crossReport);
    when(crossReportService.find(any())) .thenReturn(mockLegacyCrossReport);

    PostedDrmsDocument drmsDocument = mock(PostedDrmsDocument.class);
    DrmsDocumentService drmsDocumentService = mock(DrmsDocumentService.class);
    when(drmsDocumentService.create(any())).thenReturn(drmsDocument);

    gov.ca.cwds.data.persistence.cms.LongText longTextEntity = new LongTextEntityBuilder().setId("1").build();
    PostedLongText longText = new PostedLongText(longTextEntity);
    LongTextService longTextService = mock(LongTextService.class);
    when(longTextService.create(any())).thenReturn(longText);

    ScreeningToReferral screeningToReferral = defaultReferralBuilder.createScreeningToReferral();
    referralService = mock(ReferralService.class);
    when(referralService.createCmsReferralFromScreening(eq(screeningToReferral),any(),any(), any(), any())).thenReturn(validReferralId);

    reporterService = mock(ReporterService.class);
    Reporter savedReporter = new ReporterResourceBuilder().build();
    when(reporterService.find(any())).thenReturn(savedReporter);

    participantService = mock(ParticipantService.class);

    defaultVictim = new ParticipantResourceBuilder().createVictimParticipant();
    defaultReporter = new ParticipantResourceBuilder().setRoles((new HashSet<>(Arrays
        .asList("Mandated Reporter")))).createReporterParticipant();
    defaultMandatedReporter= new ParticipantResourceBuilder().createReporterParticipant();

    defaultPerpetrator = new ParticipantResourceBuilder().createPerpParticipant();

    messageBuilder = new MessageBuilder();

    screeningToReferralService = new ScreeningToReferralService(referralService, clientService,
        allegationService, crossReportService, referralClientService, reporterService,
        addressService, clientAddressService, childClientService, assignmentService,
        participantService, Validation.buildDefaultValidatorFactory().getValidator(), referralDao,
        messageBuilder, allegationPerpetratorHistoryService, reminders);

  }


  @SuppressWarnings("javadoc")
  @Test(expected = NotImplementedException.class)
  public void deleteThrowsNotImplementedException() throws Exception {
    screeningToReferralService.delete("string");
  }

  @SuppressWarnings("javadoc")
  @Test(expected = AssertionError.class)
  public void deleteThrowsAssertionError() throws Exception {
    screeningToReferralService.delete(new Long(1));
  }

  @SuppressWarnings("javadoc")
  @Test
  public void shouldReturnPostedCmsReferralWhenSaveIsSuccessfull() throws Exception {
    ScreeningToReferral screeningToReferral = defaultReferralBuilder.createScreeningToReferral();
    mockParticipantService(screeningToReferral);

    Response response = screeningToReferralService.create(screeningToReferral);

    assertThat(response.getClass(), is(PostedScreeningToReferral.class));
  }


  @SuppressWarnings("javadoc")
  @Test
  public void shouldContainNoErrorMessageWhenSavingAValidReferral() throws Exception {
    ScreeningToReferral screeningToReferral = defaultReferralBuilder.createScreeningToReferral();

    mockParticipantService(screeningToReferral);

    Response response = screeningToReferralService.create(screeningToReferral);

    assertThat(response.hasMessages(), is(equalTo(false)));
  }

  @SuppressWarnings("javadoc")
  @Test
  public void shouldNotUpdateClientWhenUserDoesNotExists() throws Exception {
    String victimClientLegacyId = "";

    clientService = mock(ClientService.class);
    screeningToReferralService = new MockedScreeningToReferralServiceBuilder()
        .addParticipantService(participantService).addClientService(clientService)
        .createScreeningToReferralService();

    Participant victim =
        new ParticipantResourceBuilder().setLegacyId(victimClientLegacyId).createParticipant();
    Set participants = new HashSet<>(Arrays.asList(victim,defaultMandatedReporter, defaultPerpetrator));
    ScreeningToReferral referral = new ScreeningToReferralResourceBuilder()
        .setParticipants(participants).createScreeningToReferral();

    mockParticipantService(referral);

    screeningToReferralService.create(referral);
    verify(clientService, never()).update(any(), any());
  }

  @SuppressWarnings("javadoc")
  @Test
  public void shouldSuccessfullyCreateReferralWithParticipantsThatHaveBlankRoles() throws Exception {
    Participant unknownRoleParticipant =
        new ParticipantResourceBuilder().setRoles(new HashSet<>()).createParticipant();

    Set participants = new HashSet<>(Arrays.asList(unknownRoleParticipant, defaultVictim, defaultReporter));

    ScreeningToReferral referral = new ScreeningToReferralResourceBuilder()
        .setParticipants(participants).createScreeningToReferral();

    when(referralService.createCmsReferralFromScreening(any(), any(), any(), any(), any()))
        .thenReturn("REFERRALID");

    mockParticipantService(referral);

    Response response = screeningToReferralService.create(referral);
    assertFalse(response.hasMessages());
  }

  @SuppressWarnings("javadoc")
  @Test
  public void testScreeningToReferralMultipleCrossReportsSuccess() throws Exception {
    Set crossReports = new HashSet();
    gov.ca.cwds.rest.api.domain.CrossReport sheriffCrossReport =
        new CrossReportResourceBuilder().setAgencyName("Sacramento County Sheriff Deparment")
            .setAgencyType("Law enforcement").setInformDate("2017-03-15").createCrossReport();
    gov.ca.cwds.rest.api.domain.CrossReport daCrossReport =
        new CrossReportResourceBuilder().setAgencyName("Sacramento District Attorney")
            .setAgencyType("District attorney").setInformDate("2017-04-15").createCrossReport();
    crossReports.add(sheriffCrossReport);
    crossReports.add(daCrossReport);

    ScreeningToReferral screeningToReferral = defaultReferralBuilder
        .setCrossReports(crossReports).createScreeningToReferral();
    when(referralService.createCmsReferralFromScreening(eq(screeningToReferral),any(),any(),
        any(), any())).thenReturn(validReferralId);

    mockParticipantService(screeningToReferral);

    Response response = screeningToReferralService.create(screeningToReferral);
    assertThat(response.getClass(), is(PostedScreeningToReferral.class));
    assertThat(response.hasMessages(), is(equalTo(false)));
  }

  @SuppressWarnings("javadoc")
  @Test(expected = RuntimeException.class)
  public void shouldLogErrorIfUnableToCreateDRMSDocument() throws Exception {
    ScreeningToReferral referral = new ScreeningToReferralResourceBuilder()
        .setCrossReports(new HashSet<>()).createScreeningToReferral();

    when(drmsDocumentService.create(any())).thenReturn(null);
    screeningToReferralService = new MockedScreeningToReferralServiceBuilder()
        .addReferralService(referralService).createScreeningToReferralService();
    Response response = screeningToReferralService.create(referral);
    assertFalse("Expected exception to have been thrown", true);
  }

  @SuppressWarnings("javadoc")
  @Test()
  public void testScreeningToReferralWithoutCrossReportsSuccess() throws Exception {
    ScreeningToReferral referral = new ScreeningToReferralResourceBuilder()
        .setCrossReports(new HashSet<>()).createScreeningToReferral();

    screeningToReferralService =
        new MockedScreeningToReferralServiceBuilder().addParticipantService(participantService)
            .createScreeningToReferralService();

    mockParticipantService(referral);

    Response response = screeningToReferralService.create(referral);
    assertFalse(response.hasMessages());
  }

  @SuppressWarnings("javadoc")
  @Test
  // R - 00836 Self rep Ind Limit
  public void testMoreThanOneSelfReportedFail() throws Exception {
    Participant victimReporter1 = new ParticipantResourceBuilder()
        .setRoles(new HashSet<>(Arrays.asList("Non-mandated", "Victim"))).createParticipant();
    Participant victimReporter2 = new ParticipantResourceBuilder()
        .setRoles(new HashSet<>(Arrays.asList("Non-mandated", "Victim"))).createParticipant();
    Set participants = new HashSet<>(Arrays.asList(victimReporter1, victimReporter2));

    ScreeningToReferral screeningToReferral = new ScreeningToReferralResourceBuilder()
        .setParticipants(participants).createScreeningToReferral();

    when(referralService.createCmsReferralFromScreening(eq(screeningToReferral),any(),any(),
          any(), any())).thenReturn(validReferralId);

    mockParticipantService(screeningToReferral);

    try {
      screeningToReferralService.create(screeningToReferral);
      assertTrue("expected exception to be thrown", false);
    } catch (ServiceException e) {
      String errorMessage = " Incompatiable participants included in request";
      assertTrue("Expected Incompatible participants error message to be recorded",
          e.getMessage().contains(errorMessage));
    }
  }

  @SuppressWarnings("javadoc")
  @Test
  public void testMoreThanOneReporterFail() throws Exception {
    Participant reporter1 =
        new ParticipantResourceBuilder().setRoles(new HashSet<>(Arrays.asList("Mandated Reporter")))
            .setFirstName("Bill").createParticipant();
    Participant reporter2 = new ParticipantResourceBuilder()
        .setRoles(new HashSet<>(Arrays.asList("Non-mandated Reporter"))).setFirstName("Sally")
        .createParticipant();
    Set participants = new HashSet<>(Arrays.asList(reporter1, reporter2));

    ScreeningToReferral screeningToReferral = new ScreeningToReferralResourceBuilder()
        .setParticipants(participants).createScreeningToReferral();

    when(referralService.createCmsReferralFromScreening(eq(screeningToReferral),any(),any(), any(),
          any())).thenReturn(validReferralId);

    mockParticipantService(screeningToReferral);

    try {
      Response response = screeningToReferralService.create(screeningToReferral);
      assertTrue("expected exception to be thrown", false);
    } catch (Exception e) {
      assertTrue("Expected exception to contain incompatble participants error message",
          e.getMessage().contains("Incompatiable participants included in request"));
    }
  }

  @SuppressWarnings("javadoc")
  @Test
  public void testMoreThanOneVictimSuccess() throws Exception {
    Participant victim1 = new ParticipantResourceBuilder().createVictimParticipant();
    Participant victim2 = new ParticipantResourceBuilder().createVictimParticipant();
    Set participants = new HashSet<>(Arrays.asList(victim1, victim2, defaultReporter));
    ScreeningToReferral screeningToReferral = new ScreeningToReferralResourceBuilder()
        .setParticipants(participants).createScreeningToReferral();

    when(referralService.createCmsReferralFromScreening(eq(screeningToReferral),any(),any(),
          any(), any())).thenReturn(validReferralId);

    mockParticipantService(screeningToReferral);

    Response response = screeningToReferralService.create(screeningToReferral);
    assertThat(response.getClass(), is(PostedScreeningToReferral.class));
    assertThat(response.hasMessages(), is(equalTo(false)));
  }

  @SuppressWarnings("javadoc")
  @Test
  public void shouldFailCreateWhenNoVictimIsProvided() throws Exception {
    Set participants = new HashSet<>(Arrays.asList(defaultReporter));
    ScreeningToReferral screeningToReferral = new ScreeningToReferralResourceBuilder()
        .setParticipants(participants).createScreeningToReferral();

    when(referralService.createCmsReferralFromScreening(eq(screeningToReferral),any(),any(), 
          any(), any())).thenReturn(validReferralId);

    mockParticipantService(screeningToReferral);

    try {
      screeningToReferralService.create(screeningToReferral);
      assertTrue("expected exception to be thrown", false);
    } catch (Exception e) {
      assertTrue("Expected exception to contain incompatible participants error message",
          e.getMessage().contains("Incompatiable participants included in request"));
    }
  }

  @Test
  public void shouldThrowExceptionWhenMessageBuilderContainsError(){
     Participant reporterVictim = new ParticipantResourceBuilder()
        .setRoles(new HashSet<>(Arrays.asList("Anonymous Reporter", "Victim"))).createParticipant();
    Set participants = new HashSet<>(Arrays.asList(reporterVictim, defaultPerpetrator));
    ScreeningToReferral screeningToReferral = new ScreeningToReferralResourceBuilder()
        .setParticipants(participants).createScreeningToReferral();

    when(referralService.createCmsReferralFromScreening(eq(screeningToReferral),any(),any(), any(),
        any())).thenReturn(validReferralId);

    mockParticipantService(screeningToReferral);

    String message = "Some Error Occured";
    messageBuilder.addError(message);

    try {
      Response response = screeningToReferralService.create(screeningToReferral);
      assertTrue("expected exception to be thrown", false);
    } catch (Exception e) {
      assertTrue("Expected the exception to report participant is incompatible",
          e.getMessage().contains(message));
    }
  }

  @SuppressWarnings("javadoc")
  @Test
  public void testMoreThanOneParticipantRoleSuccess() throws Exception {
    Participant victimAndReporter = new ParticipantResourceBuilder()
        .setRoles(new HashSet<>(Arrays.asList("Non-mandated Reporter", "Victim")))
        .createParticipant();
    Set participants = new HashSet<>(Arrays.asList(victimAndReporter, defaultPerpetrator));

    ScreeningToReferral referral = new ScreeningToReferralResourceBuilder()
        .setParticipants(new HashSet<>(participants)).createScreeningToReferral();

    screeningToReferralService =
        new MockedScreeningToReferralServiceBuilder().addParticipantService(participantService)
            .createScreeningToReferralService();

    mockParticipantService(referral);

    Response response = screeningToReferralService.create(referral);
    assertFalse(response.hasMessages());
  }

  @SuppressWarnings("javadoc")
  @Test
  public void testMultipleAddressPerParticipantSuccess() throws Exception {
    screeningToReferralService =
        new MockedScreeningToReferralServiceBuilder().addParticipantService(participantService).
    createScreeningToReferralService();

    gov.ca.cwds.rest.api.domain.Address address1 =
        new AddressResourceBuilder().setStreetAddress("123 First St").createAddress();
    gov.ca.cwds.rest.api.domain.Address address2 =
        new AddressResourceBuilder().setStreetAddress("7466 Campbell St").createAddress();
    Participant reporter = new ParticipantResourceBuilder()
        .setRoles(new HashSet<>(Arrays.asList("Mandated Reporter", "Victim")))
        .setAddresses(new HashSet<>(Arrays.asList(address1, address2))).createParticipant();
    Set participants = new HashSet<>(Arrays.asList(reporter, defaultPerpetrator, defaultVictim));
    ScreeningToReferral referral = new ScreeningToReferralResourceBuilder()
        .setParticipants(participants).createScreeningToReferral();

    mockParticipantService(referral);

    Response response = screeningToReferralService.create(referral);
    assertThat(response.getClass(), is(PostedScreeningToReferral.class));
    assertThat(response.hasMessages(), is(equalTo(false)));
  }

  @SuppressWarnings("javadoc")
  @Test
  public void shouldSaveSucessfullyWhenPerpHasNoAddress() throws Exception {
    Participant perpWithNoAddress =
        new ParticipantResourceBuilder().setAddresses(null).setId(12345).createPerpParticipant();
    Set participants = new HashSet<>(Arrays.asList(perpWithNoAddress, defaultVictim, defaultReporter));

    ScreeningToReferral screeningToReferral = new ScreeningToReferralResourceBuilder()
        .setParticipants(participants).createScreeningToReferral();

    when(referralService.createCmsReferralFromScreening(eq(screeningToReferral),any(),any(), any(),
        any())).thenReturn(validReferralId);

    mockParticipantService(screeningToReferral);

    Response response = screeningToReferralService.create(screeningToReferral);
    assertThat(response.getClass(), is(PostedScreeningToReferral.class));
    assertThat(response.hasMessages(), is(equalTo(false)));
  }

  @SuppressWarnings("javadoc")
  @Test
  public void shouldSaveSucessfullyWhenReporterHasNoAddress() throws Exception {
    Participant reporter = new ParticipantResourceBuilder()
        .setRoles(new HashSet<>(Arrays.asList("Non-mandated Reporter"))).setAddresses(null)
        .createParticipant();
    Set participants = new HashSet<>(Arrays.asList(reporter, defaultPerpetrator, defaultVictim));

    ScreeningToReferral screeningToReferral = new ScreeningToReferralResourceBuilder()
        .setParticipants(participants).createScreeningToReferral();

    when(referralService.createCmsReferralFromScreening(eq(screeningToReferral),any(),any(), any(),
        any())).thenReturn(validReferralId);

    mockParticipantService(screeningToReferral);

    Response response = screeningToReferralService.create(screeningToReferral);
    assertThat(response.getClass(), is(PostedScreeningToReferral.class));
    assertThat(response.hasMessages(), is(equalTo(false)));
  }

  @SuppressWarnings("javadoc")
  @Test
  public void testNoAddressForPerpetratorSuccess() throws Exception {
    Participant perp =
        new ParticipantResourceBuilder().setRoles(new HashSet<>(Arrays.asList("Perpetrator")))
            .setAddresses(new HashSet<>()).createParticipant();
    Set participants = new HashSet<>(Arrays.asList(perp, defaultReporter, defaultVictim));

    ScreeningToReferral referral = new ScreeningToReferralResourceBuilder()
        .setParticipants(participants).createScreeningToReferral();

    screeningToReferralService =
        new MockedScreeningToReferralServiceBuilder().addParticipantService(participantService)
        .createScreeningToReferralService();

    mockParticipantService(referral);

    Response response = screeningToReferralService.create(referral);
    assertFalse(response.hasMessages());
  }

  @SuppressWarnings("javadoc")
  @Test
  public void testSelfReportedReporterWithNoAddressSuccess() throws Exception {
    String perpId = "PERP_ID_12";
    String victimId = "VICTIM__ID";
    Participant reporter =
        new ParticipantResourceBuilder().setLegacyId("").setFirstName("Vinny").setLastName("Victim")
            .setRoles(new HashSet<>(Arrays.asList("Victim", "Non-mandated Reporter")))
            .setAddresses(new HashSet<>()).createParticipant();
    LegacyDescriptor descriptor = new LegacyDescriptor("", "", lastUpdateDate, "", "");
    reporter.setLegacyDescriptor(descriptor);
    Set participants = new HashSet<>(Arrays.asList(reporter, defaultPerpetrator));

    ScreeningToReferral referral = new ScreeningToReferralResourceBuilder()
        .setParticipants(participants).createScreeningToReferral();

    PostedClient savedClient = mock(PostedClient.class);
    when(savedClient.getId()).thenReturn(victimId);
    when(savedClient.getLastUpdatedTime()).thenReturn(updatedTime);
    when(clientService.update(any(), any()))
        .thenReturn(mock(gov.ca.cwds.rest.api.domain.cms.Client.class));
    when(clientService.create(any())).thenReturn(savedClient);
    when(clientService.createWithSingleTimestamp(any(), any())).thenReturn(savedClient);

    Client foundVictimClient = mock(Client.class);
    when(foundVictimClient.getLastUpdatedTime()).thenReturn(updatedTime);
    Client foundPerpClient = mock(Client.class);
    when(foundPerpClient.getLastUpdatedTime()).thenReturn(lastUpdateDate);
    when(clientService.find(eq(victimId))).thenReturn(foundVictimClient);
    when(clientService.find(eq(perpId))).thenReturn(foundPerpClient);

    when(referralService.createCmsReferralFromScreening(any(), any(), any(), any(), any()))
        .thenReturn("REFERRALID");

    mockParticipantService(referral);

    Response response = screeningToReferralService.create(referral);
    assertFalse(response.hasMessages());
  }


  @SuppressWarnings("javadoc")
  @Test
  // R - 03895
  public void shouldFailSavingReferralWhenAllegationIsMissing() throws Exception {
    ScreeningToReferral screeningToReferral =
        new ScreeningToReferralResourceBuilder().setAllegations(null).createScreeningToReferral();

    when(referralService.createCmsReferralFromScreening(eq(screeningToReferral),any(),any(), any(),
        any())).thenReturn(validReferralId);

    mockParticipantService(screeningToReferral);

    try {
      Response response = screeningToReferralService.create(screeningToReferral);
      assertFalse("Expected an Exception to have been thrown", true);
    } catch (Exception e) {
      assertTrue("Expected Exception to contain message that Referral must have allegation",
          e.getMessage().contains("Referral must have at least one Allegation"));
    }
  }

  @SuppressWarnings("javadoc")
  @Test
  public void shouldSaveSuccessfullyWhenReferraHasMultipleAllegations() throws Exception {
    Set allegations = new HashSet();
    gov.ca.cwds.rest.api.domain.Allegation allegation1 =
        new AllegationResourceBuilder().createAllegation();
    gov.ca.cwds.rest.api.domain.Allegation allegation2 =
        new AllegationResourceBuilder().createAllegation();
    allegations.addAll(Arrays.asList(allegation1, allegation2));

    ScreeningToReferral screeningToReferral = new ScreeningToReferralResourceBuilder()
        .setAllegations(allegations).createScreeningToReferral();

    when(referralService.createCmsReferralFromScreening(eq(screeningToReferral),any(),any(), any(),
        any())).thenReturn(validReferralId);

    mockParticipantService(screeningToReferral);

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
  public void shouldFailSavingWhenAllegationDoesNotHaveMatchingVictim() throws Exception {
    int nonExistentVictimId = -999999999;

    Set allegations = new HashSet();
    gov.ca.cwds.rest.api.domain.Allegation allegation =
        new AllegationResourceBuilder().setVictimPersonId(nonExistentVictimId).createAllegation();
    allegations.addAll(Arrays.asList(allegation));

    ScreeningToReferral screeningToReferral = new ScreeningToReferralResourceBuilder()
        .setAllegations(allegations).createScreeningToReferral();

    when(referralService.createCmsReferralFromScreening(eq(screeningToReferral),any(),any(), any(),
        any())).thenReturn(validReferralId);

    mockParticipantService(screeningToReferral);

    Boolean theErrorDetected = false;
    try {
      Response response = screeningToReferralService.create(screeningToReferral);
      assertFalse("Expected exception to have been thrown", true);
    } catch (Exception e) {
      assertTrue(
          "Expected Exception to conatin Allegation/Victim doesn't contain a victim participant",
          e.getMessage().contains(
              "Allegation/Victim Person Id does not contain a Participant with a role of Victim"));
    }
  }

  @SuppressWarnings("javadoc")
  @Test
  public void shouldFailSavingWhenReferralDoesNotContainAPerpatratorMatchingAllegation()
      throws Exception {
    long nonExistentPerpId = -9999999;

    Set allegations = new HashSet();
    gov.ca.cwds.rest.api.domain.Allegation allegation = new AllegationResourceBuilder()
        .setPerpetratorPersonId(nonExistentPerpId).createAllegation();
    allegations.addAll(Arrays.asList(allegation));
    ScreeningToReferral screeningToReferral = new ScreeningToReferralResourceBuilder()
        .setAllegations(allegations).createScreeningToReferral();

    when(referralService.createCmsReferralFromScreening(eq(screeningToReferral),any(),any(), any(),
        any())).thenReturn(validReferralId);

    mockParticipantService(screeningToReferral);

    try {
      Response response = screeningToReferralService.create(screeningToReferral);
      assertFalse("Expected an exception to have been thrown", true);
    } catch (Exception e) {
      assertTrue("Expected Exception to contain allegation/perpetrator don't correspond error message", e.getMessage().contains(
          "Allegation/Perpetrator Person Id does not contain a Participant with a role of Perpetrator"));
      }
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
    gov.ca.cwds.rest.api.domain.cms.Client updatedPerp =
        mock(gov.ca.cwds.rest.api.domain.cms.Client.class);
    gov.ca.cwds.rest.api.domain.cms.Client updatedReporter =
        mock(gov.ca.cwds.rest.api.domain.cms.Client.class);
    gov.ca.cwds.rest.api.domain.cms.Client foundClient =
        mock(gov.ca.cwds.rest.api.domain.cms.Client.class);
    gov.ca.cwds.rest.api.domain.cms.PostedClient savedVictim =
        mock(gov.ca.cwds.rest.api.domain.cms.PostedClient.class);
    when(savedVictim.getId()).thenReturn(updatedReporterId);
    when(clientService.update(eq(existingPerpId), any())).thenReturn(updatedPerp);
    when(clientService.create(any())).thenReturn(savedVictim);
    when(clientService.update(eq(updatedReporterId), any())).thenReturn(updatedReporter);
    when(clientService.find(any())).thenReturn(null);

    when(clientAddressService.find(any())).thenReturn(mock(ClientAddress.class));

    mockParticipantService(referral);

    try {
      Response response = screeningToReferralService.create(referral);
    } catch (ServiceException e) {
      // not interested in exception for this test
    }
    verify(foundClient, times(0)).update(any(), any(), any(), any());
    verify(foundClient, times(0)).update(any(), any(), any(), any());
  }

  @SuppressWarnings("javadoc")
  @Test
  public void testAllegationExistSuccess() throws Exception {
    gov.ca.cwds.rest.api.domain.Allegation allegation =
        new AllegationResourceBuilder().setLegacyId("GHJKLCVBNM").createAllegation();
    ScreeningToReferral referral = new ScreeningToReferralResourceBuilder()
        .setAllegations(new HashSet<>(Arrays.asList(allegation))).createScreeningToReferral();

    when(allegationService.find(allegation.getLegacyId())).thenReturn(mock(Allegation.class));
    MessageBuilder messageBuilder = new MessageBuilder();
    when(referralService.createCmsReferralFromScreening(any(), any(), any(), any(), any()))
        .thenReturn("REFERRALID");

    mockParticipantService(referral);

    Response response = screeningToReferralService.create(referral);

    verify(allegationService).find(eq(allegation.getLegacyId()));
    assertFalse(response.hasMessages());
  }

  @SuppressWarnings("javadoc")
  @Test
  public void shouldFailSavingWhenAllegationIdIsNotFound() throws Exception {
    Participant victim = new ParticipantResourceBuilder().setId(1234).createVictimParticipant();
    Set participants = new HashSet<>(Arrays.asList(victim, defaultReporter));

    gov.ca.cwds.rest.api.domain.Allegation allegation =
        new AllegationResourceBuilder().setLegacyId("1234567ABC").setVictimPersonId(1234)
            .setPerpetratorPersonId(0).createAllegation();
    Set allegations = new HashSet();
    allegations.addAll(Arrays.asList(allegation));
    ScreeningToReferral screeningToReferral = new ScreeningToReferralResourceBuilder()
        .setAllegations(allegations).setParticipants(participants).createScreeningToReferral();

    when(referralService.createCmsReferralFromScreening(eq(screeningToReferral),any(),any(), any(),
        any())).thenReturn(validReferralId);

    mockParticipantService(screeningToReferral);

    try {
      Response response = screeningToReferralService.create(screeningToReferral);
      assertFalse("Expected exception to have been thrown", true);
    } catch (Exception e) {
      assertTrue("Expected exception to contain allegation ", e.getMessage().contains(
          "Legacy Id on Allegation does not correspond to an existing CMS/CWS Allegation"));
    }
  }

  @SuppressWarnings("javadoc")
  @Test
  public void shouldFailSavingWhenCrossReportIdIsNotFound() throws Exception {
    gov.ca.cwds.rest.api.domain.CrossReport crossReport =
        new CrossReportResourceBuilder().setLegacyId("3456789ABC").createCrossReport();
    Set crossReports = new HashSet<>(Arrays.asList(crossReport));
    ScreeningToReferral screeningToReferral = new ScreeningToReferralResourceBuilder()
        .setCrossReports(crossReports).createScreeningToReferral();

    when(referralService.createCmsReferralFromScreening(eq(screeningToReferral),any(),any(), any(),
        any())).thenReturn(validReferralId);

    when(crossReportService.find(eq(crossReport.getLegacyId()))).thenReturn(null);

    mockParticipantService(screeningToReferral);


    try {
      Response response = screeningToReferralService.create(screeningToReferral);
      assertFalse("expected exception to have been thrown", true);
    } catch (Exception e) {
      assertTrue("Expected exception to contain cross report does not exist in legacy",
          e.getMessage().contains(
              "Legacy Id on Cross Report does not correspond to an existing CMS/CWS Cross Report"));
    }
  }

  public void testCrossReportExistSuccess() throws Exception {
    gov.ca.cwds.rest.api.domain.CrossReport crossReport =
        new CrossReportResourceBuilder().setLegacyId("ASDFGHJKLQ").createCrossReport();

    ScreeningToReferral referral = new ScreeningToReferralResourceBuilder()
        .setCrossReports(new HashSet<>(Arrays.asList(crossReport))).createScreeningToReferral();

    mockParticipantService(referral);

    Response response = screeningToReferralService.create(referral);
    assertFalse(response.hasMessages());
  }

  @SuppressWarnings("javadoc")
  @Test
  public void shouldFailSavingWhenCrossReportIdIsDoesNotExist() throws Exception {
    gov.ca.cwds.rest.api.domain.CrossReport crossReport =
        new CrossReportResourceBuilder().setLegacyId("3456789ABC").createCrossReport();
    Set crossReports = new HashSet<>(Arrays.asList(crossReport));
    ScreeningToReferral screeningToReferral = new ScreeningToReferralResourceBuilder()
        .setCrossReports(crossReports).createScreeningToReferral();

    when(referralService.createCmsReferralFromScreening(eq(screeningToReferral),any(),any(), any(),
        any())).thenReturn(validReferralId);
    when(crossReportService.find(eq(crossReport.getLegacyId()))).thenReturn(null);

    mockParticipantService(screeningToReferral);

    try {
      Response response = screeningToReferralService.create(screeningToReferral);
      assertFalse("Expected exception to have been thrown", true);
    } catch (Exception e) {
      assertTrue("Expected exception to contain crossreport doesn't exist in legacy",
          e.getMessage().contains(
              "Legacy Id on Cross Report does not correspond to an existing CMS/CWS Cross Report"));
    }
  }

  @SuppressWarnings("javadoc")
  @Test
  public void shouldFailSavingWhenClientAddressDoesNotExist() throws Exception {
    String nonExistantPerpAddressId = "999999999";
    String nonExistentVictimAddressId = "888888888";

    gov.ca.cwds.rest.api.domain.Address perpAddress =
        new AddressResourceBuilder().setLegacyId(nonExistantPerpAddressId).createAddress();
    Set perpAddresses = new HashSet();
    perpAddresses.add(perpAddress);
    Participant perp =
        new ParticipantResourceBuilder().setAddresses(perpAddresses).createPerpParticipant();
    gov.ca.cwds.rest.api.domain.Address victimAddress =
        new AddressResourceBuilder().setLegacyId(nonExistentVictimAddressId).createAddress();
    Set victimAddresses = new HashSet();
    victimAddresses.add(victimAddress);
    Participant victim =
        new ParticipantResourceBuilder().setAddresses(victimAddresses).createVictimParticipant();
    Set participants = new HashSet<>(Arrays.asList(victim, perp, defaultReporter));

    ScreeningToReferral screeningToReferral = new ScreeningToReferralResourceBuilder()
        .setParticipants(participants).createScreeningToReferral();

    when(referralService.createCmsReferralFromScreening(eq(screeningToReferral),any(),any(), any(),
        any())).thenReturn(validReferralId);

    Address victimLegacyAddress = new Address().createWithDefaults(victimAddress);
    Set victimLegacyAddresses = new HashSet();
    victimAddresses.add(victimLegacyAddress);
    Address perpLegacyAddress = new Address().createWithDefaults(perpAddress);
    Set perpLegacyAddresses = new HashSet();
    perpAddresses.add(perpLegacyAddress);
    addressService = mock(AddressService.class);
    when(addressService.find(nonExistentVictimAddressId)).thenReturn(victimLegacyAddress);
    when(addressService.find(nonExistantPerpAddressId)).thenReturn(perpLegacyAddress);

    when(clientAddressService.findByAddressAndClient(eq(victimAddress), any())).thenReturn(null);
    when(clientAddressService.findByAddressAndClient(eq(perpAddress), any())).thenReturn(null);

    screeningToReferralService = new ScreeningToReferralService(referralService, clientService,
        allegationService, crossReportService, referralClientService, reporterService,
        addressService, clientAddressService, childClientService, assignmentService,
        participantService, Validation.buildDefaultValidatorFactory().getValidator(), referralDao,
        new MessageBuilder(), allegationPerpetratorHistoryService, reminders);

    mockParticipantService(screeningToReferral);

    try {
      Response response = screeningToReferralService.create(screeningToReferral);
      assertFalse("Expected exception to have been thrown", false);
    } catch (Exception e) {
      assertTrue("Expected exception to contain address does not exist message",
          e.getMessage().contains(
              "Legacy Id on Address does not correspond to an existing CMS/CWS Client Address"));
    }
  }

  @SuppressWarnings("javadoc")
  @Test
  public void shouldSaveWhenReporterExists() throws Exception {
    Participant reporter =
        new ParticipantResourceBuilder().setLegacyId(validReferralId).createReporterParticipant();
    Set participants = new HashSet<>(Arrays.asList(reporter, defaultPerpetrator, defaultVictim));

    ScreeningToReferral screeningToReferral = new ScreeningToReferralResourceBuilder()
        .setParticipants(participants).createScreeningToReferral();

    Reporter savedReporter = new ReporterResourceBuilder().build();
    when(reporterService.find(any())).thenReturn(savedReporter);

    when(referralService.createCmsReferralFromScreening(eq(screeningToReferral),any(),any(), any(),
        any())).thenReturn(validReferralId);

    mockParticipantService(screeningToReferral);

    Response response = screeningToReferralService.create(screeningToReferral);
    assertThat(response.getClass(), is(PostedScreeningToReferral.class));
    assertThat(response.hasMessages(), is(equalTo(false)));
  }

  @SuppressWarnings("javadoc")
  @Test
  public void shouldSaveReferralWhenReporterIsANonReporter() throws Exception {
    Set roles = new HashSet();
    roles.add("Anonymous Reporter");
    Participant reporter =
        new ParticipantResourceBuilder().setRoles(roles).createReporterParticipant();
    Set participants = new HashSet<>(Arrays.asList(reporter, defaultPerpetrator, defaultVictim));

    ScreeningToReferral screeningToReferral = new ScreeningToReferralResourceBuilder()
        .setParticipants(participants).createScreeningToReferral();

    when(referralService.createCmsReferralFromScreening(eq(screeningToReferral),any(),any(), any(),
        any())).thenReturn(validReferralId);

    mockParticipantService(screeningToReferral);

    Response response = screeningToReferralService.create(screeningToReferral);
    assertThat(response.getClass(), is(PostedScreeningToReferral.class));
    assertThat(response.hasMessages(), is(equalTo(false)));
  }

  private void mockParticipantService(ScreeningToReferral screeningToReferral){

    ClientParticipants clientParticipants = new ClientParticipants();
    Set <Participant>participants = screeningToReferral.getParticipants();

    CmsIdGenerator generator = new CmsIdGenerator();
    for(Participant participant : participants){
      participant.setLegacyId(generator.generate());
    }
    clientParticipants.addParticipants(participants);
    when(participantService.saveParticipants(any(), any(), any(), any(), any()))
        .thenReturn(clientParticipants);
  }
}
