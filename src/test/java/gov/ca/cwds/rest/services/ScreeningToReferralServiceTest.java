package gov.ca.cwds.rest.services;

import static io.dropwizard.testing.FixtureHelpers.fixture;
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
import gov.ca.cwds.fixture.ClientEntityResourceBuilder;
import gov.ca.cwds.fixture.CrossReportEntityBuilder;
import gov.ca.cwds.fixture.LongTextEntityBuilder;
import gov.ca.cwds.fixture.ReporterEntityBuilder;
import gov.ca.cwds.rest.api.domain.cms.PostedAllegation;
import gov.ca.cwds.rest.api.domain.cms.PostedAllegationPerpetratorHistory;
import gov.ca.cwds.rest.api.domain.cms.PostedDrmsDocument;
import gov.ca.cwds.rest.api.domain.cms.PostedLongText;
import java.util.ArrayList;
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
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.fasterxml.jackson.core.type.TypeReference;
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
import gov.ca.cwds.rest.api.domain.cms.AllegationPerpetratorHistory;
import gov.ca.cwds.rest.api.domain.cms.Assignment;
import gov.ca.cwds.rest.api.domain.cms.ChildClient;
import gov.ca.cwds.rest.api.domain.cms.Client;
import gov.ca.cwds.rest.api.domain.cms.ClientAddress;
import gov.ca.cwds.rest.api.domain.cms.CrossReport;
import gov.ca.cwds.rest.api.domain.cms.DrmsDocument;
import gov.ca.cwds.rest.api.domain.cms.LongText;
import gov.ca.cwds.rest.api.domain.cms.PostedAddress;
import gov.ca.cwds.rest.api.domain.cms.PostedClient;
import gov.ca.cwds.rest.api.domain.cms.Referral;
import gov.ca.cwds.rest.api.domain.cms.ReferralClient;
import gov.ca.cwds.rest.api.domain.cms.Reporter;
import gov.ca.cwds.rest.api.domain.error.ErrorMessage;
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
  private StaffPersonIdRetriever staffPersonIdRetriever;
  private DrmsDocumentService drmsDocumentService;
  private DrmsDocumentDao drmsDocumentDao;
  private AssignmentDao assignmentDao;
  private SsaName3Dao ssaName3Dao;
  private Reminders reminders;
  private UpperCaseTables upperCaseTables;
  private Validator validator;
  private ExternalInterfaceTables externalInterfaceTables;

  private Participant defaultVictim;
  private Participant defaultVictimReporter;
  private Participant defaultReporter;
  private Participant defaultMandatedReporter;
  private Participant defaultPerpetrator;

  private gov.ca.cwds.rest.api.domain.cms.CrossReport mockLegacyCrossReport;
  private MessageBuilder messageBuilder;

  private gov.ca.cwds.data.persistence.cms.Referral referral;
  private static gov.ca.cwds.data.persistence.cms.Referral createdReferal = null;

  private DateTime lastUpdateDate;
  private DateTime updatedTime;
  private DateTime modifiedLastUpdateDate;

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
    defaultVictim = new ParticipantResourceBuilder().createVictimParticipant();
    defaultReporter = new ParticipantResourceBuilder().setRoles((new HashSet<>(Arrays
        .asList("Mandated Reporter")))).createReporterParticipant();
    defaultMandatedReporter= new ParticipantResourceBuilder().createReporterParticipant();

    defaultPerpetrator = new ParticipantResourceBuilder().createPerpParticipant();
    defaultVictimReporter = new ParticipantResourceBuilder().createVictimParticipant();

    validator = Validation.buildDefaultValidatorFactory().getValidator();
    defaultReferralBuilder = new ScreeningToReferralResourceBuilder();

    staffPersonIdRetriever = mock(StaffPersonIdRetriever.class);

    drmsDocumentDao = mock(DrmsDocumentDao.class);
    drmsDocumentService = new DrmsDocumentService(drmsDocumentDao, staffPersonIdRetriever);

    reminders = mock(Reminders.class);

    lastUpdateDate = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
        .parseDateTime("2010-03-14T13:33:12.456-0700");
    updatedTime = lastUpdateDate.plusHours(2);
    modifiedLastUpdateDate = DateTimeFormat.forPattern("yyyy-MM-dd-HH.mm.ss.SSS")
        .parseDateTime("2010-03-14-13.33.12.456");

    referralClientService = mock(ReferralClientService.class);

    gov.ca.cwds.rest.api.domain.cms.Address existingAddress =
        mock(gov.ca.cwds.rest.api.domain.cms.Address.class);
    when(existingAddress.getExistingAddressId()).thenReturn("ASDHJYTRED");

    PostedAddress postedAddress = mock(PostedAddress.class);
    when(postedAddress.getExistingAddressId()).thenReturn("QWWERTYUIJ");
    gov.ca.cwds.data.persistence.cms.Client savedEntityClient = new ClientEntityResourceBuilder()
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

    mockLegacyCrossReport = mock(gov.ca.cwds.rest.api.domain.cms.CrossReport.class);
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
    Reporter savedReporter = new ReporterEntityBuilder().build();
    when(reporterService.find(any())).thenReturn(savedReporter);

    messageBuilder = new MessageBuilder();

    screeningToReferralService = new ScreeningToReferralService(referralService, clientService,
        allegationService, crossReportService, referralClientService, reporterService,
        addressService, clientAddressService, childClientService, assignmentService,
        Validation.buildDefaultValidatorFactory().getValidator(), referralDao, messageBuilder,
        allegationPerpetratorHistoryService, reminders);

  }

  //TODO:Move to referralService
  @SuppressWarnings("javadoc")
  @Ignore
  @Test
  public void shouldSetApprovalCodeToNotSubmittedDefaultValue() throws Exception {
    ScreeningToReferral screeningToReferral = defaultReferralBuilder.createScreeningToReferral();

//    PostedDrmsDocument drmsDocument = mock(PostedDrmsDocument.class);
//    DrmsDocumentService drmsDocumentService = mock(DrmsDocumentService.class);
//    when(drmsDocumentService.create(any())).thenReturn(drmsDocument);
//
//    gov.ca.cwds.data.persistence.cms.LongText longTextEntity = new LongTextEntityBuilder().setId("1").build();
//    PostedLongText longText = new PostedLongText(longTextEntity);
//    LongTextService longTextService = mock(LongTextService.class);
//    when(longTextService.create(any())).thenReturn(longText);
//
//    referralService = new ReferralService(referralDao, nonLACountyTriggers, laCountyTrigger,
//        triggerTablesDao, staffpersonDao, staffPersonIdRetriever, assignmentService, validator,
//        drmsDocumentService, addressService, longTextService);

    Referral referralCreated = referralService.createReferralWithDefaults(screeningToReferral,
        "2016-08-03T01:00:00.000Z", "2016-08-03T01:00:00.000Z", null, new MessageBuilder());

    assertThat(referralCreated.getApprovalStatusType(), is(equalTo((short) 118)));
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
    Response response = screeningToReferralService.create(screeningToReferral);

    assertThat(response.getClass(), is(PostedScreeningToReferral.class));
  }


  @SuppressWarnings("javadoc")
  @Test
  public void shouldContainNoErrorMessageWhenSavingAValidReferral() throws Exception {
    ScreeningToReferral screeningToReferral = defaultReferralBuilder.createScreeningToReferral();

    Response response = screeningToReferralService.create(screeningToReferral);

    assertThat(response.hasMessages(), is(equalTo(false)));
  }

  // TODO: Move to referral service
  @Ignore
  @SuppressWarnings("javadoc")
  @Test
  public void shouldSaveSuccessfullyWhenReferrralAlreadyExistsWithAReferralId() throws Exception {
    ScreeningToReferral screeningToReferral =
        defaultReferralBuilder.setReferralId(validReferralId).createScreeningToReferral();

    referralService = mock(ReferralService.class);
    when(referralService.createCmsReferralFromScreening(eq(screeningToReferral), any(), any(),
        any(), any())).thenReturn(validReferralId);

    screeningToReferralService = new ScreeningToReferralService(referralService, clientService,
        allegationService, crossReportService, referralClientService, reporterService,
        addressService, clientAddressService, childClientService, assignmentService,
        Validation.buildDefaultValidatorFactory().getValidator(), referralDao, new MessageBuilder(),
        allegationPerpetratorHistoryService, reminders);

    Response response = screeningToReferralService.create(screeningToReferral);

    assertThat(response.getClass(), is(PostedScreeningToReferral.class));
    assertThat(response.hasMessages(), is(equalTo(false)));
  }

  //TODO:move to referralService
  @Ignore
  @SuppressWarnings("javadoc")
  @Test
  public void shouldFailWhenSpecifyingALegacyReferralIdThatDoesNotExist() throws Exception {
    MockedScreeningToReferralServiceBuilder builder = new MockedScreeningToReferralServiceBuilder();
//    screeningToReferralService = builder.addReferralService(referralService)
//      .createScreeningToReferralService();

    ScreeningToReferral referral = new ScreeningToReferralResourceBuilder()
        .setReferralId(NON_EXISTING_REFERRAL_ID).createScreeningToReferral();

    Response response = screeningToReferralService.create(referral);
    verify(builder.getMessageBuilder()).addMessageAndLog(
        eq("Legacy Id does not correspond to an existing CMS/CWS Referral"), any(), any());
  }

  @SuppressWarnings("javadoc")
  @Test
  public void shouldUpdateClientWhenClientIdIsPresent() throws Exception {
    String victimClientLegacyId = "ABC123DSAF";

    LegacyDescriptor descriptor = new LegacyDescriptor("", "", lastUpdateDate, "", "");
    Participant victim =
        new ParticipantResourceBuilder().setLegacyId(victimClientLegacyId).setLegacyDescriptor(descriptor).createParticipant();
    Set participants = new HashSet<>(Arrays.asList(victim, defaultReporter,
        defaultPerpetrator));

    ScreeningToReferral referral = new ScreeningToReferralResourceBuilder()
        .setParticipants(participants).createScreeningToReferral();

    Client foundClient = mock(Client.class);
    when(foundClient.getLastUpdatedTime()).thenReturn(modifiedLastUpdateDate);

    PostedClient createdClient = mock(PostedClient.class);
    when(createdClient.getId()).thenReturn("LEGACYIDXX");
    when(clientService.find(eq(victimClientLegacyId))).thenReturn(foundClient);
    when(clientService.create(any())).thenReturn(createdClient);

    Client updatedClient = mock(Client.class);
    when(clientService.update(eq(victimClientLegacyId), any())).thenReturn(updatedClient);

    when(referralService.createCmsReferralFromScreening(eq(referral),any(),any(), any(), any())).thenReturn(validReferralId);
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
    Set participants = new HashSet<>(Arrays.asList(victim,defaultMandatedReporter, defaultPerpetrator));
    ScreeningToReferral referral = new ScreeningToReferralResourceBuilder()
        .setParticipants(participants).createScreeningToReferral();

    screeningToReferralService.create(referral);
    verify(clientService, never()).update(any(), any());
  }

  @SuppressWarnings("javadoc")
  @Test
  public void shouldNotUpdateClientWhenClientRecordHasBeenModifiedInLegacyDb() throws Exception {
    DateTime modifiedLastUpdateDate = DateTimeFormat.forPattern("yyyy-MM-dd-HH.mm.ss.SSS")
        .parseDateTime("2000-01-27-15.34.55.123");
    String victimClientLegacyId = "ABC123DSAF";

    LegacyDescriptor descriptor = new LegacyDescriptor("", "", lastUpdateDate, "", "");
    Participant victim =
        new ParticipantResourceBuilder().setLegacyId(victimClientLegacyId).setLegacyDescriptor(descriptor).createParticipant();

    Set participants = new HashSet<>(Arrays.asList(victim, defaultReporter, defaultPerpetrator));
    ScreeningToReferral referral = new ScreeningToReferralResourceBuilder()
        .setParticipants(participants).createScreeningToReferral();

    Client updatedClient = mock(Client.class);
    Client foundClient = mock(Client.class);
    when(foundClient.getLastUpdatedTime()).thenReturn(modifiedLastUpdateDate);
    PostedClient createdClient = mock(PostedClient.class);
    when(createdClient.getId()).thenReturn("LEGACYIDXX");

    when(clientService.find(eq(victimClientLegacyId))).thenReturn(foundClient);
    when(clientService.create(any())).thenReturn(createdClient);
    when(clientService.update(eq(victimClientLegacyId), any())).thenReturn(updatedClient);

    try {
      screeningToReferralService.create(referral);

    } catch (ServiceException e) {
      assertTrue(e.getMessage()
          .contains("Unable to Update John Smith Client. Client was previously modified&"));
    }
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
        new MockedScreeningToReferralServiceBuilder().createScreeningToReferralService();

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

    try {
      screeningToReferralService.create(screeningToReferral);
      assertTrue("expected exception to be thrown", false);
    } catch (Exception e) {
      assertTrue("Expected exception to contain incompatible participants error message",
          e.getMessage().contains("Incompatiable participants included in request"));
    }
  }

  @SuppressWarnings("javadoc")
  @Test
  // R - 00831
  public void shouldFailCreateWhenReporterRolesAreIncompatable_AnonymousSelfReporter()
      throws Exception {
    Participant anonymousSelfReporter = new ParticipantResourceBuilder()
        .setRoles(new HashSet<>(Arrays.asList("Anonymous Reporter", "Self Reported")))
        .createParticipant();
    Set participants = new HashSet<>(Arrays.asList(anonymousSelfReporter, defaultVictim));
    ScreeningToReferral screeningToReferral = new ScreeningToReferralResourceBuilder()
        .setParticipants(participants).createScreeningToReferral();

    when(referralService.createCmsReferralFromScreening(eq(screeningToReferral),any(),any(),
          any(), any())).thenReturn(validReferralId);

    try {
      Response response = screeningToReferralService.create(screeningToReferral);
      assertTrue("expected exception to be thrown", false);
    } catch (Exception e) {
      assertTrue("Expected Exception to report Participants contain incompatible roles",
          e.getMessage().contains("Participant contains incompatiable roles"));
    }
  }

  @SuppressWarnings("javadoc")
  @Test
  public void shouldThrowExceptionWithMessagesReferralServiceAddsMessageToMessageBuilder()
      throws Exception {
    String message = "Error occured in referralService";
    MessageBuilder messageBuilder = new MessageBuilder();
    messageBuilder.addError(message);

    screeningToReferralService = new ScreeningToReferralService(referralService, clientService,
        allegationService, crossReportService, referralClientService, reporterService,
        addressService, clientAddressService, childClientService, assignmentService,
        Validation.buildDefaultValidatorFactory().getValidator(), referralDao, messageBuilder,
        allegationPerpetratorHistoryService, reminders);

    ScreeningToReferral screeningToReferral = new ScreeningToReferralResourceBuilder()
        .createScreeningToReferral();
    when(referralService.createCmsReferralFromScreening(eq(screeningToReferral),any(),any(), any(), any())).thenReturn(validReferralId);
    try {
      Response response = screeningToReferralService.create(screeningToReferral);
      assertTrue("expected exception to be thrown", false);
    } catch (Exception e) {
      assertTrue("Expected error message to report error.", e.getMessage().contains(message));
    }
  }

  // TODO: MOVE TO Address or Referral service. Doesn't test anything here
  @Ignore
  @SuppressWarnings("javadoc")
  @Test
  public void testForStreetAddressEmptyFailure() throws Exception {
    gov.ca.cwds.rest.api.domain.Address address =
        new AddressResourceBuilder().setStreetAddress("").createAddress();
    ScreeningToReferral screeningToReferral =
        new ScreeningToReferralResourceBuilder().setAddress(address).createScreeningToReferral();

//    referralService = mock(ReferralService.class);
    when(referralService.createCmsReferralFromScreening(eq(screeningToReferral),any(),any(), any(), any())).thenReturn(validReferralId);

//    screeningToReferralService = new ScreeningToReferralService(referralService, clientService,
//        allegationService, crossReportService, referralClientService, reporterService,
//        addressService, clientAddressService, childClientService, assignmentService,
//        Validation.buildDefaultValidatorFactory().getValidator(), referralDao, new MessageBuilder(),
//        allegationPerpetratorHistoryService, reminders);

    Boolean theErrorDetected = false;
    try {
      Response response = screeningToReferralService.create(screeningToReferral);
      assertTrue("expected exception to be thrown", false);
    } catch (Exception e) {
      assertTrue("Expected error message to report error.",
          e.getMessage().contains("Screening address is null or empty"));
    }
  }

  //TODO: move to reporter service
  @Ignore
  @SuppressWarnings("javadoc")
  @Test
  public void shouldThrowExceptionWithMessageWhenReporterStreetAddressIsEmpty() throws Exception {
    gov.ca.cwds.rest.api.domain.Address address =
        new AddressResourceBuilder().setStreetAddress("").createAddress();
    Set addresses = new HashSet<>();
    addresses.add(address);
    Participant reporter =
        new ParticipantResourceBuilder().setAddresses(addresses).createReporterParticipant();
    Participant victim = new ParticipantResourceBuilder().createVictimParticipant();
    Set participants = new HashSet<>();
    participants.add(reporter);
    participants.add(victim);
    ScreeningToReferral screeningToReferral = new ScreeningToReferralResourceBuilder()
        .setParticipants(participants).createScreeningToReferral();

//    referralService = mock(ReferralService.class);
    when(referralService.createCmsReferralFromScreening(eq(screeningToReferral),any(),any(), any(), any())).thenReturn(validReferralId);

//    screeningToReferralService = new ScreeningToReferralService(referralService, clientService,
//        allegationService, crossReportService, referralClientService, reporterService,
//        addressService, clientAddressService, childClientService, assignmentService,
//        Validation.buildDefaultValidatorFactory().getValidator(), referralDao, new MessageBuilder(),
//        allegationPerpetratorHistoryService, reminders);

    try {
      Response response = screeningToReferralService.create(screeningToReferral);
      assertTrue("expected exception to be thrown", false);
    } catch (Exception e) {
      assertTrue("Expected exception to report street name may not be null",
          e.getMessage().contains("streetName may not be null"));
    }
  }

  @SuppressWarnings("javadoc")
  @Test
  public void shouldFailWhenVictimeHasIncompatiableRoles_AnonymousVictim() throws Exception {
    Participant reporterVictim = new ParticipantResourceBuilder()
        .setRoles(new HashSet<>(Arrays.asList("Anonymous Reporter", "Victim"))).createParticipant();
    Set participants = new HashSet<>(Arrays.asList(reporterVictim, defaultPerpetrator));
    ScreeningToReferral screeningToReferral = new ScreeningToReferralResourceBuilder()
        .setParticipants(participants).createScreeningToReferral();

    when(referralService.createCmsReferralFromScreening(eq(screeningToReferral),any(),any(), any(), any())).thenReturn(validReferralId);

    try {
      Response response = screeningToReferralService.create(screeningToReferral);
      assertTrue("expected exception to be thrown", false);
    } catch (Exception e) {
      assertTrue("Expected the exception to report participant is incompatible",
          e.getMessage().contains("Participant contains incompatiable roles"));
    }
  }

  @SuppressWarnings("javadoc")
  @Test
  public void shouldFailWhenReporterHasIncompatiableRoles_AnonymousReporterFail() throws Exception {
    Participant anonymousNonMandatedReporter = new ParticipantResourceBuilder()
        .setRoles(new HashSet<>(Arrays.asList("Anonymous Reporter", "Non-mandated Reporter")))
        .createParticipant();
    Set participants = new HashSet<>(Arrays.asList(anonymousNonMandatedReporter, defaultVictim));
    ScreeningToReferral screeningToReferral = new ScreeningToReferralResourceBuilder()
        .setParticipants(participants).createScreeningToReferral();

    when(referralService.createCmsReferralFromScreening(eq(screeningToReferral),any(),any(), any(), any())).thenReturn(validReferralId);

    try {
      Response response = screeningToReferralService.create(screeningToReferral);
      assertTrue("expected exception to be thrown", false);
    } catch (Exception e) {
      assertTrue(e.getMessage().contains("Participant contains incompatiable roles"));
    }
  }

  @SuppressWarnings("javadoc")
  @Test
  public void shouldFailWhenVictimIsIncompatiableRoles_VictimPerpetrator() throws Exception {
    Participant perpVictim = new ParticipantResourceBuilder()
        .setRoles(new HashSet<>(Arrays.asList("Perpetrator", "Victim"))).createParticipant();
    Set participants = new HashSet<>(Arrays.asList(perpVictim, defaultMandatedReporter));

    ScreeningToReferral screeningToReferral = new ScreeningToReferralResourceBuilder()
        .setParticipants(participants).createScreeningToReferral();
    when(referralService.createCmsReferralFromScreening(eq(screeningToReferral),any(),any(), any(), any())).thenReturn(validReferralId);

    try {
      Response response = screeningToReferralService.create(screeningToReferral);
      assertTrue("expected exception to be thrown", false);

    } catch (Exception e) {
      assertTrue("Expected Exception to contain participant invalid error message",
          e.getMessage().contains("Participant contains incompatiable roles"));
    }
  }

  @SuppressWarnings("javadoc")
  @Test
  public void shouldFaileWhenReporterHasIncompatiableRoles_MandatedNonMandatedFail()
      throws Exception {
    Participant mandatedNonMandatedReporter = new ParticipantResourceBuilder()
        .setRoles(new HashSet<>(Arrays.asList("Mandated Reporter", "Non-mandated Reporter")))
        .createParticipant();
    Set participants = new HashSet<>(Arrays.asList(mandatedNonMandatedReporter, defaultVictim));

    ScreeningToReferral screeningToReferral = new ScreeningToReferralResourceBuilder()
        .setParticipants(participants).createScreeningToReferral();

    when(referralService.createCmsReferralFromScreening(eq(screeningToReferral),any(),any(), any(), any())).thenReturn(validReferralId);

    try {
      Response response = screeningToReferralService.create(screeningToReferral);
      assertTrue("expected exception to be thrown", false);
    } catch (Exception e) {
      assertTrue("Expected Exception to contain participant incompatible message",
          e.getMessage().contains("Participant contains incompatiable roles"));
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
    Set participants = new HashSet<>(Arrays.asList(reporter, defaultPerpetrator, defaultVictim));
    ScreeningToReferral referral = new ScreeningToReferralResourceBuilder()
        .setParticipants(participants).createScreeningToReferral();

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

    when(referralService.createCmsReferralFromScreening(eq(screeningToReferral),any(),any(), any(), any())).thenReturn(validReferralId);

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

    when(referralService.createCmsReferralFromScreening(eq(screeningToReferral),any(),any(), any(), any())).thenReturn(validReferralId);

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
        new MockedScreeningToReferralServiceBuilder().createScreeningToReferralService();

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

    Response response = screeningToReferralService.create(referral);
    assertFalse(response.hasMessages());
  }

  // TODO:MOVE TO referralService
  @SuppressWarnings("javadoc")
  @Test
  public void shouldFailSavingWhenReferralHasInvalidDateTimeFormat() throws Exception {

//    ScreeningToReferral screeningToReferral = MAPPER.readValue(
//        fixture("fixtures/domain/ScreeningToReferral/invalid/invalidDateTimeStamp.json"),
//        ScreeningToReferral.class);
    ScreeningToReferral screeningToReferral = new ScreeningToReferralResourceBuilder()
        // .setEndedAt("2016/08/03T01:00:00.000Z")
        // .setStartedAt("08/2016/03T01:00:00.000Z")
        .createScreeningToReferral();

//    referralService = mock(ReferralService.class);
    when(referralService.createCmsReferralFromScreening(eq(screeningToReferral),any(),any(), any(), any())).thenReturn(validReferralId);

//    screeningToReferralService = new ScreeningToReferralService(referralService, clientService,
//        allegationService, crossReportService, referralClientService, reporterService,
//        addressService, clientAddressService, childClientService, assignmentService,
//        Validation.buildDefaultValidatorFactory().getValidator(), referralDao, new MessageBuilder(),
//        allegationPerpetratorHistoryService, reminders);

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

  // TODO: Move to ReferralService
  @Ignore
  @SuppressWarnings("javadoc")
  @Test
  public void testNullAddressOnScreeningFail() throws Exception {
    ScreeningToReferral screeningToReferral = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/invalid/nullAddressOnScreening.json"),
        ScreeningToReferral.class);

//    referralService = mock(ReferralService.class);
    when(referralService.createCmsReferralFromScreening(eq(screeningToReferral),any(),any(), any(), any())).thenReturn(validReferralId);

//    screeningToReferralService = new ScreeningToReferralService(referralService, clientService,
//        allegationService, crossReportService, referralClientService, reporterService,
//        addressService, clientAddressService, childClientService, assignmentService,
//        Validation.buildDefaultValidatorFactory().getValidator(), referralDao, new MessageBuilder(),
//        allegationPerpetratorHistoryService, reminders);

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

  //TODO: Move to ReferralService
  @Ignore
  @SuppressWarnings("javadoc")
  @Test
  public void shouldFailSavingWhenReferralAddressIsEmptyAddress() throws Exception {
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

    AllegationPerpetratorHistory allegationPerpHistoryDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validAllegationPerpetratorHistory.json"),
        AllegationPerpetratorHistory.class);
    gov.ca.cwds.data.persistence.cms.AllegationPerpetratorHistory allegationPerpHistoryToCreate =
        new gov.ca.cwds.data.persistence.cms.AllegationPerpetratorHistory("567890ABC",
            allegationPerpHistoryDomain, "2017-07-03");
    when(allegationPerpetratorHistoryDao
        .create(any(gov.ca.cwds.data.persistence.cms.AllegationPerpetratorHistory.class)))
            .thenReturn(allegationPerpHistoryToCreate);

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

    Assignment assignment =
        MAPPER.readValue(fixture("fixtures/domain/ScreeningToReferral/valid/validAssignment.json"),
            Assignment.class);
    gov.ca.cwds.data.persistence.cms.Assignment assignmentToCreate =
        new gov.ca.cwds.data.persistence.cms.Assignment("6789012ABC", assignment, "ABC");
    when(assignmentDao.create(any(gov.ca.cwds.data.persistence.cms.Assignment.class)))
        .thenReturn(assignmentToCreate);

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

  //TODO:MOVE TO referralService
  @Ignore
  @SuppressWarnings("javadoc")
  @Test
  public void shouldSuccessfullySaveReferralWhenAdditionalInfoIsEmtpy() throws Exception {
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

    AllegationPerpetratorHistory allegationPerpHistoryDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validAllegationPerpetratorHistory.json"),
        AllegationPerpetratorHistory.class);
    gov.ca.cwds.data.persistence.cms.AllegationPerpetratorHistory allegationPerpHistoryToCreate =
        new gov.ca.cwds.data.persistence.cms.AllegationPerpetratorHistory("567890ABC",
            allegationPerpHistoryDomain, "2017-07-03");
    when(allegationPerpetratorHistoryDao
        .create(any(gov.ca.cwds.data.persistence.cms.AllegationPerpetratorHistory.class)))
            .thenReturn(allegationPerpHistoryToCreate);

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

    Assignment assignment =
        MAPPER.readValue(fixture("fixtures/domain/ScreeningToReferral/valid/validAssignment.json"),
            Assignment.class);
    gov.ca.cwds.data.persistence.cms.Assignment assignmentToCreate =
        new gov.ca.cwds.data.persistence.cms.Assignment("6789012ABC", assignment, "ABC");
    when(assignmentDao.create(any(gov.ca.cwds.data.persistence.cms.Assignment.class)))
        .thenReturn(assignmentToCreate);

    ScreeningToReferral screeningToReferral = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/emptyAdditionalInfo.json"),
        ScreeningToReferral.class);

    Response response = screeningToReferralService.create(screeningToReferral);

    assertThat(response.getClass(), is(PostedScreeningToReferral.class));
    assertThat(response.hasMessages(), is(equalTo(false)));
  }

  //TODO:MOVE TO referralService
  @Ignore
  @SuppressWarnings("javadoc")
  @Test
  public void shouldSaveSuccessfullWhenReferralAdditionalInfoIsNull() throws Exception {
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

    AllegationPerpetratorHistory allegationPerpHistoryDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validAllegationPerpetratorHistory.json"),
        AllegationPerpetratorHistory.class);
    gov.ca.cwds.data.persistence.cms.AllegationPerpetratorHistory allegationPerpHistoryToCreate =
        new gov.ca.cwds.data.persistence.cms.AllegationPerpetratorHistory("567890ABC",
            allegationPerpHistoryDomain, "2017-07-03");
    when(allegationPerpetratorHistoryDao
        .create(any(gov.ca.cwds.data.persistence.cms.AllegationPerpetratorHistory.class)))
            .thenReturn(allegationPerpHistoryToCreate);

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

    Assignment assignment =
        MAPPER.readValue(fixture("fixtures/domain/ScreeningToReferral/valid/validAssignment.json"),
            Assignment.class);
    gov.ca.cwds.data.persistence.cms.Assignment assignmentToCreate =
        new gov.ca.cwds.data.persistence.cms.Assignment("6789012ABC", assignment, "ABC");
    when(assignmentDao.create(any(gov.ca.cwds.data.persistence.cms.Assignment.class)))
        .thenReturn(assignmentToCreate);

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
  public void shouldFailSavingReferralWhenAllegationIsMissing() throws Exception {
    ScreeningToReferral screeningToReferral =
        new ScreeningToReferralResourceBuilder().setAllegations(null).createScreeningToReferral();

    when(referralService.createCmsReferralFromScreening(eq(screeningToReferral),any(),any(), any(), any())).thenReturn(validReferralId);

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

    when(referralService.createCmsReferralFromScreening(eq(screeningToReferral),any(),any(), any(), any())).thenReturn(validReferralId);

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

    when(referralService.createCmsReferralFromScreening(eq(screeningToReferral),any(),any(), any(), any())).thenReturn(validReferralId);

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

  // TODO: move to referralService
  /*
   * test for the referral-AllegesAbuseOccurredAtAddressId(FKAddrs_T) is updated with the
   * referralAddress id
   */
  @Ignore
  @SuppressWarnings("javadoc")
  @Test
  public void testAllegesAbuseOccurredAtAddressId() throws Exception {
    ScreeningToReferral screeningToReferral = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/valid.json"), ScreeningToReferral.class);

//    referralService = mock(ReferralService.class);
    when(referralService.createCmsReferralFromScreening(eq(screeningToReferral),any(),any(), any(), any())).thenReturn(validReferralId);

//    screeningToReferralService = new ScreeningToReferralService(referralService, clientService,
//        allegationService, crossReportService, referralClientService, reporterService,
//        addressService, clientAddressService, childClientService, assignmentService,
//        Validation.buildDefaultValidatorFactory().getValidator(), referralDao, new MessageBuilder(),
//        allegationPerpetratorHistoryService, reminders);

    Response response = null;
    try {
      response = screeningToReferralService.create(screeningToReferral);
      if (response.hasMessages()) {
        List<ErrorMessage> messages = response.getMessages();
        for (ErrorMessage message : messages) {
          System.out.println(message.getMessage());
        }
      }
    } catch (Exception e) {
      assertFalse(true);
    }

    assertThat(response.getClass(), is(PostedScreeningToReferral.class));
    assertThat(response.hasMessages(), is(equalTo(false)));
    assertThat(createdReferal.getAllegesAbuseOccurredAtAddressId(), is(equalTo("345678ABC")));
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

    when(referralService.createCmsReferralFromScreening(eq(screeningToReferral),any(),any(), any(), any())).thenReturn(validReferralId);

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
  public void shouldUpdatePerpetratorWhenAlreadyExists() throws Exception {
    String victimId = "VICTIM__ID";
    String existingPerpId = "1234567ABC";
    Participant reporter = new ParticipantResourceBuilder().setFirstName("Barney")
        .setLegacyId(victimId).setMiddleName("middlestone").setLastName("Rubble")
        .setRoles(new HashSet<>(Arrays.asList("Non-mandated Reporter", "Victim")))
        .createParticipant();
    LegacyDescriptor descriptor = new LegacyDescriptor("", "", lastUpdateDate, "", "");
    reporter.setLegacyDescriptor(descriptor);
    Participant perp = new ParticipantResourceBuilder().setLegacyId(existingPerpId)
        .setFirstName("Fred").setMiddleName("Finnigan").setLastName("Flintsone")
        .setRoles(new HashSet<>(Arrays.asList("Perpetrator"))).createParticipant();
    descriptor = new LegacyDescriptor("", "", lastUpdateDate, "", "");
    perp.setLegacyDescriptor(descriptor);
    Set participants = new HashSet<>(Arrays.asList(reporter, perp));

    ScreeningToReferral referral = new ScreeningToReferralResourceBuilder()
        .setParticipants(participants).createScreeningToReferral();

    gov.ca.cwds.rest.api.domain.cms.Client updatedPerp =
        mock(gov.ca.cwds.rest.api.domain.cms.Client.class);
    gov.ca.cwds.rest.api.domain.cms.Client updatedReporter =
        mock(gov.ca.cwds.rest.api.domain.cms.Client.class);
    when(updatedReporter.getLastUpdatedTime()).thenReturn(updatedTime);
    gov.ca.cwds.rest.api.domain.cms.Client foundVictim =
        mock(gov.ca.cwds.rest.api.domain.cms.Client.class);
    when(foundVictim.getLastUpdatedTime()).thenReturn(lastUpdateDate) // first call return original
        .thenReturn(updatedTime); // second call return updated time
    gov.ca.cwds.rest.api.domain.cms.Client foundPerp =
        mock(gov.ca.cwds.rest.api.domain.cms.Client.class);
    when(foundPerp.getLastUpdatedTime()).thenReturn(lastUpdateDate);
    gov.ca.cwds.rest.api.domain.cms.PostedClient savedVictim =
        mock(gov.ca.cwds.rest.api.domain.cms.PostedClient.class);
    when(savedVictim.getId()).thenReturn(victimId);
    when(savedVictim.getLastUpdatedTime()).thenReturn(updatedTime);
    when(clientService.update(eq(existingPerpId), any())).thenReturn(updatedPerp);
    when(clientService.create(any())).thenReturn(savedVictim);
    when(clientService.createWithSingleTimestamp(any(), any())).thenReturn(savedVictim);
    when(clientService.update(eq(victimId), any())).thenReturn(updatedReporter);
    when(clientService.find(victimId)).thenReturn(foundVictim);
    when(clientService.find(existingPerpId)).thenReturn(foundPerp);
    when(referralService.createCmsReferralFromScreening(any(), any(), any(), any(), any()))
        .thenReturn("REFERRALID");

    Response response = screeningToReferralService.create(referral);
    assertFalse(response.hasMessages());
    verify(foundVictim, times(2)).update("Barney", "middlestone", "Rubble", "Jr.");
    verify(foundPerp, times(1)).update("Fred", "Finnigan", "Flintsone", "Jr.");
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
  public void shouldReturnErrorMessageWhenUnableToSaveClient() throws Exception {
    String existingPerpId = "1234567ABC";
    Participant reporter =
        new ParticipantResourceBuilder().setFirstName("Barney").setLastName("Rubble")
            .setRoles(new HashSet<>(Arrays.asList("Non-mandated Reporter", "Victim")))
            .createParticipant();
    LegacyDescriptor descriptor = new LegacyDescriptor("", "", lastUpdateDate, "", "");
    reporter.setLegacyDescriptor(descriptor);
    Participant perp = new ParticipantResourceBuilder().setLegacyId(existingPerpId)
        .setFirstName("Fred").setLastName("Flintsone")
        .setRoles(new HashSet<>(Arrays.asList("Perpetrator"))).createParticipant();
    descriptor = new LegacyDescriptor("", "", lastUpdateDate, "", "");
    perp.setLegacyDescriptor(descriptor);
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
    when(clientService.update(any(), any())).thenReturn(null);
    when(clientService.find(any())).thenReturn(foundClient);
    when(foundClient.getLastUpdatedTime()).thenReturn(modifiedLastUpdateDate);

    when(referralService.createCmsReferralFromScreening(eq(referral),any(),any(), any(), any())).thenReturn(validReferralId);

    try {
      screeningToReferralService.create(referral);
      assertFalse("Expected an exception to have been thrown ", true);
    } catch (ServiceException e) {
      // not interested in exception for this test
      assertTrue("Expected exception to contain unable to save Client error message", e.getMessage().contains("Unable to save Client"));

    }
  }

  @SuppressWarnings("javadoc")
  @Test
  public void testClientDoesNotExsitFail() throws Exception {
    String badLegacyId = "IUKNOWNIDI";

    Participant perp = new ParticipantResourceBuilder().setLegacyId(badLegacyId)
        .setRoles(new HashSet<>(Arrays.asList("Perpetrator"))).createParticipant();
    Set participants = new HashSet<>(Arrays.asList(perp, defaultReporter, defaultVictim));

    ScreeningToReferral referral = new ScreeningToReferralResourceBuilder()
        .setParticipants(participants).createScreeningToReferral();

    try {
      screeningToReferralService.create(referral);
      assertFalse("Expected an exception to have been thrown ", true);
    } catch (ServiceException e) {
      // not interested in exception for this test
      String errorMessage =
          " Legacy Id of Participant does not correspond to an existing CWS/CMS Client ";
      assertTrue("Expected thrown error to contain error", e.getMessage().contains(errorMessage));
      verify(clientService, never()).update(eq(badLegacyId), any());

    }
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

    when(referralService.createCmsReferralFromScreening(eq(screeningToReferral),any(),any(), any(), any())).thenReturn(validReferralId);

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

    when(referralService.createCmsReferralFromScreening(eq(screeningToReferral),any(),any(), any(), any())).thenReturn(validReferralId);

    when(crossReportService.find(eq(crossReport.getLegacyId()))).thenReturn(null);

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

    when(referralService.createCmsReferralFromScreening(eq(screeningToReferral),any(),any(), any(), any())).thenReturn(validReferralId);
    when(crossReportService.find(eq(crossReport.getLegacyId()))).thenReturn(null);

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
  public void testAddressExistSuccess() throws Exception {
    gov.ca.cwds.rest.api.domain.Address address =
        new AddressResourceBuilder().setLegacyId("ASDFGHJKLQ").createAddress();
    Participant victim =
        new ParticipantResourceBuilder().setRoles(new HashSet<>(Arrays.asList("Victim")))
            .setAddresses(new HashSet<>(Arrays.asList(address))).createParticipant();
    Set participants = new HashSet<>(Arrays.asList(victim, defaultPerpetrator, defaultReporter));

    ScreeningToReferral referral = new ScreeningToReferralResourceBuilder()
        .setParticipants(participants).createScreeningToReferral();

    gov.ca.cwds.data.persistence.cms.DrmsDocument drmsDocument =
        new gov.ca.cwds.data.persistence.cms.DrmsDocument("ABC1234560", null, null, null, null,
            null);
    Address victimFoundAddress = mock(Address.class);
    when(victimFoundAddress.getExistingAddressId()).thenReturn("ADDRESS_ID");
    PostedAddress perpCreatedAddress = mock(PostedAddress.class);
    when(perpCreatedAddress.getExistingAddressId()).thenReturn("PERPADDRID");
    ClientAddress clientAddress = new ClientAddress();
    List clientAddresses = new ArrayList();
    clientAddresses.add(clientAddress);
    addressService = mock(AddressService.class);
    when(addressService.find(address.getLegacyId())).thenReturn(victimFoundAddress);
    when(addressService.create(any())).thenReturn(perpCreatedAddress);
    when(addressService.createWithSingleTimestamp(any(), any())).thenReturn(perpCreatedAddress);
    when(drmsDocumentDao.create(any())).thenReturn(drmsDocument);

    when(clientAddressService.find(address.getLegacyId())).thenReturn(mock(ClientAddress.class));
    when(clientAddressService.findByAddressAndClient(any(), any())).thenReturn(clientAddresses);
    MessageBuilder messageBuilder = new MessageBuilder();

    when(referralService.createCmsReferralFromScreening(any(), any(), any(), any(), any()))
        .thenReturn("REFERRALID");
    screeningToReferralService = new ScreeningToReferralService(referralService, clientService,
        allegationService, crossReportService, referralClientService, reporterService,
        addressService, clientAddressService, childClientService, assignmentService,
        Validation.buildDefaultValidatorFactory().getValidator(), referralDao, messageBuilder,
        allegationPerpetratorHistoryService, reminders);

    Response response = screeningToReferralService.create(referral);

    verify(addressService).find(eq(address.getLegacyId()));
    assertFalse(response.hasMessages());
  }

  @SuppressWarnings("javadoc")
  @Test
  public void shouldFailSavingWhenAddressIdDoesNotExist() throws Exception {
    gov.ca.cwds.rest.api.domain.Address perpAddress =
        new AddressResourceBuilder().setLegacyId("1234567ABC").createAddress();
    Set perpAddresses = new HashSet();
    perpAddresses.add(perpAddress);
    Participant perp =
        new ParticipantResourceBuilder().setAddresses(perpAddresses).createPerpParticipant();
    gov.ca.cwds.rest.api.domain.Address victimAddress =
        new AddressResourceBuilder().setLegacyId("1234567ABC").createAddress();
    Set victimAddresses = new HashSet();
    victimAddresses.add(victimAddress);
    Participant victim =
        new ParticipantResourceBuilder().setAddresses(victimAddresses).createVictimParticipant();
    Set participants = new HashSet<>(Arrays.asList(victim, perp, defaultReporter));

    ScreeningToReferral screeningToReferral = new ScreeningToReferralResourceBuilder()
        .setParticipants(participants).createScreeningToReferral();

    when(referralService.createCmsReferralFromScreening(eq(screeningToReferral),any(),any(), any(), any())).thenReturn(validReferralId);

    try {
      Response response = screeningToReferralService.create(screeningToReferral);
      assertFalse("Expected exception to have been thrown", true);
    } catch (Exception e) {
      assertTrue("Expected exception to contain Address does not exist message", e.getMessage()
          .contains("Legacy Id on Address does not correspond to an existing CMS/CWS Address"));
    }
  }

  @SuppressWarnings("javadoc")
  @Test
  public void testClientAddressExistSuccess() throws Exception {
    String addressId1 = "1111111111";
    gov.ca.cwds.rest.api.domain.Address address1 =
        new AddressResourceBuilder().setLegacyId("1111111111").setLegacySourceTable("ADDRS_T")
            .setStreetAddress("123 First St").createAddress();
    String addressId2 = "2222222222";
    gov.ca.cwds.rest.api.domain.Address address2 =
        new AddressResourceBuilder().setLegacyId("2222222222").setLegacySourceTable("ADDRS_T")
            .setStreetAddress("123 First St").createAddress();

    Participant selfReportingVictim = new ParticipantResourceBuilder()
        .setRoles(new HashSet<>(Arrays.asList("Non-mandated Reporter", "Victim")))
        .setAddresses(new HashSet<>(Arrays.asList(address1))).createParticipant();
    LegacyDescriptor descriptor = new LegacyDescriptor("", "", lastUpdateDate, "", "");
    selfReportingVictim.setLegacyDescriptor(descriptor);
    int numberOfReportingVictimsRoles = selfReportingVictim.getRoles().size();
    Participant perp = new ParticipantResourceBuilder()
        .setAddresses(new HashSet<>(Arrays.asList(address2))).createPerpParticipant();
    Set participants = new HashSet<>(Arrays.asList(selfReportingVictim, perp));
    ScreeningToReferral referral = new ScreeningToReferralResourceBuilder()
        .setParticipants(participants).createScreeningToReferral();

    ClientAddress clientAddress = mock(ClientAddress.class);
    ClientAddress foundClientAddress = mock(ClientAddress.class);
    List foundClientAddresses = new ArrayList<ClientAddress>();
    foundClientAddresses.add(foundClientAddress);
    Client updatedClient = mock(Client.class);
    PostedClient savedClient = mock(PostedClient.class);
    when(savedClient.getId()).thenReturn("ASDFGHHYTR");
    when(savedClient.getLastUpdatedTime()).thenReturn(modifiedLastUpdateDate);
    when(clientAddressService.find(any())).thenReturn(clientAddress);

    when(clientAddressService.findByAddressAndClient(any(), any()))
        .thenReturn(foundClientAddresses);
    when(clientService.find(any())).thenReturn(savedClient);
    when(clientService.create(any())).thenReturn(savedClient);
    when(clientService.createWithSingleTimestamp(any(), any())).thenReturn(savedClient);
    when(clientService.update(any(), any())).thenReturn(updatedClient);

    Address foundAddress = mock(Address.class);
    when(foundAddress.getExistingAddressId()).thenReturn("ZXCVBNMKJH");
    PostedAddress postedAddress = mock(PostedAddress.class);
    when(postedAddress.getExistingAddressId()).thenReturn("ZXCVBNMKJH");

    when(addressService.find(any())).thenReturn(foundAddress);
    when(addressService.create(any())).thenReturn(postedAddress);
    when(addressService.createWithSingleTimestamp(any(), any())).thenReturn(postedAddress);

    when(referralService.createCmsReferralFromScreening(any(), any(), any(), any(), any()))
        .thenReturn("REFERRALID");

    Response response = screeningToReferralService.create(referral);

    assertFalse(response.hasMessages());
    verify(addressService, times(numberOfReportingVictimsRoles)).find(eq(addressId1));
    verify(addressService).find(eq(addressId2));
    verify(clientAddressService, times(numberOfReportingVictimsRoles))
        .findByAddressAndClient(address1, selfReportingVictim);
    verify(clientAddressService).findByAddressAndClient(address2, perp);
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

    when(referralService.createCmsReferralFromScreening(eq(screeningToReferral),any(),any(), any(), any())).thenReturn(validReferralId);

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
        Validation.buildDefaultValidatorFactory().getValidator(), referralDao, new MessageBuilder(),
        allegationPerpetratorHistoryService, reminders);

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

    Reporter savedReporter = new ReporterEntityBuilder().build();
    when(reporterService.find(any())).thenReturn(savedReporter);

    when(referralService.createCmsReferralFromScreening(eq(screeningToReferral),any(),any(), any(), any())).thenReturn(validReferralId);

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

    when(referralService.createCmsReferralFromScreening(eq(screeningToReferral),any(),any(), any(), any())).thenReturn(validReferralId);

    Response response = screeningToReferralService.create(screeningToReferral);
    assertThat(response.getClass(), is(PostedScreeningToReferral.class));
    assertThat(response.hasMessages(), is(equalTo(false)));
  }

  @SuppressWarnings("javadoc")
  @Test
  public void testMultipleVictimSuccess() throws Exception {
    Participant victim1 =
        new ParticipantResourceBuilder().setFirstName("Sally").createVictimParticipant();
    Participant victim2 =
        new ParticipantResourceBuilder().setFirstName("Fred").createVictimParticipant();
    Set participants = new HashSet<>(Arrays.asList(victim1, victim2, defaultReporter, defaultVictim));
    int numberOfClientsThatAreNotReporters = 3;

    ScreeningToReferral referral = new ScreeningToReferralResourceBuilder()
        .setParticipants(participants).createScreeningToReferral();

    when(referralService.createCmsReferralFromScreening(any(), any(), any(), any(), any()))
        .thenReturn("REFERRALID");

    Response response = screeningToReferralService.create(referral);
    assertFalse(response.hasMessages());
    verify(clientService, times(numberOfClientsThatAreNotReporters))
        .createWithSingleTimestamp(any(), any());
  }
}
