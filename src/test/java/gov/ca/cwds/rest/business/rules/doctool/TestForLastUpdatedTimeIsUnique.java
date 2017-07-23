package gov.ca.cwds.rest.business.rules.doctool;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.validation.Validation;
import javax.validation.Validator;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

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
import gov.ca.cwds.rest.api.Response;
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
import gov.ca.cwds.rest.api.domain.cms.Referral;
import gov.ca.cwds.rest.api.domain.cms.ReferralClient;
import gov.ca.cwds.rest.api.domain.cms.Reporter;
import gov.ca.cwds.rest.api.domain.error.ErrorMessage;
import gov.ca.cwds.rest.business.rules.LACountyTrigger;
import gov.ca.cwds.rest.business.rules.NonLACountyTriggers;
import gov.ca.cwds.rest.business.rules.Reminders;
import gov.ca.cwds.rest.business.rules.UpperCaseTables;
import gov.ca.cwds.rest.messages.MessageBuilder;
import gov.ca.cwds.rest.services.ScreeningToReferralService;
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
import io.dropwizard.jackson.Jackson;

/**
 * @author CWDS API Team
 *
 */
public class TestForLastUpdatedTimeIsUnique {

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

  private static gov.ca.cwds.data.persistence.cms.Referral createdReferal = null;
  private static gov.ca.cwds.data.persistence.cms.Address createdAddress = null;
  private static gov.ca.cwds.data.persistence.cms.Client createdClient = null;
  private static gov.ca.cwds.data.persistence.cms.ReferralClient createdReferralClient = null;
  private static gov.ca.cwds.data.persistence.cms.ClientAddress createdClientAddress = null;
  private static gov.ca.cwds.data.persistence.cms.Reporter createdReporter = null;
  private static gov.ca.cwds.data.persistence.cms.CrossReport createdCrossReport = null;
  private static gov.ca.cwds.data.persistence.cms.Allegation createdAllegation = null;
  private static gov.ca.cwds.data.persistence.cms.AllegationPerpetratorHistory createdAllegationPerpetratorHistory =
      null;
  private static gov.ca.cwds.data.persistence.cms.Assignment createdAssignment = null;

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
    referralDao = mock(ReferralDao.class);
    nonLACountyTriggers = mock(NonLACountyTriggers.class);
    laCountyTrigger = mock(LACountyTrigger.class);
    triggerTablesDao = mock(TriggerTablesDao.class);
    staffpersonDao = mock(StaffPersonDao.class);
    staffPersonIdRetriever = mock(StaffPersonIdRetriever.class);

    longTextDao = mock(LongTextDao.class);
    longTextService = new LongTextService(longTextDao, staffPersonIdRetriever);

    drmsDocumentDao = mock(DrmsDocumentDao.class);
    drmsDocumentService = new DrmsDocumentService(drmsDocumentDao, staffPersonIdRetriever);

    assignmentDao = mock(AssignmentDao.class);
    staffpersonDao = mock(StaffPersonDao.class);
    nonLACountyTriggers = mock(NonLACountyTriggers.class);
    triggerTablesDao = mock(TriggerTablesDao.class);
    assignmentService = new AssignmentService(assignmentDao, nonLACountyTriggers, staffpersonDao,
        triggerTablesDao, staffPersonIdRetriever, validator);

    clientDao = mock(ClientDao.class);
    staffpersonDao = mock(StaffPersonDao.class);
    triggerTablesDao = mock(TriggerTablesDao.class);
    nonLACountyTriggers = mock(NonLACountyTriggers.class);
    ssaName3Dao = mock(SsaName3Dao.class);
    upperCaseTables = mock(UpperCaseTables.class);
    clientService = new ClientService(clientDao, staffpersonDao, triggerTablesDao,
        nonLACountyTriggers, staffPersonIdRetriever, ssaName3Dao, upperCaseTables);

    referralClientDao = mock(ReferralClientDao.class);
    nonLACountyTriggers = mock(NonLACountyTriggers.class);
    laCountyTrigger = mock(LACountyTrigger.class);
    triggerTablesDao = mock(TriggerTablesDao.class);
    staffpersonDao = mock(StaffPersonDao.class);
    referralClientService = new ReferralClientService(referralClientDao, nonLACountyTriggers,
        laCountyTrigger, triggerTablesDao, staffpersonDao, staffPersonIdRetriever);

    allegationDao = mock(AllegationDao.class);
    allegationService = new AllegationService(allegationDao, staffPersonIdRetriever);

    allegationPerpetratorHistoryDao = mock(AllegationPerpetratorHistoryDao.class);
    allegationPerpetratorHistoryService = new AllegationPerpetratorHistoryService(
        allegationPerpetratorHistoryDao, staffPersonIdRetriever);

    crossReportDao = mock(CrossReportDao.class);
    crossReportService = new CrossReportService(crossReportDao, staffPersonIdRetriever);

    reporterDao = mock(ReporterDao.class);
    reporterService = new ReporterService(reporterDao, staffPersonIdRetriever);

    clientAddressDao = mock(ClientAddressDao.class);
    laCountyTrigger = mock(LACountyTrigger.class);
    triggerTablesDao = mock(TriggerTablesDao.class);
    staffpersonDao = mock(StaffPersonDao.class);
    nonLACountyTriggers = mock(NonLACountyTriggers.class);
    clientAddressService = new ClientAddressService(clientAddressDao, staffpersonDao,
        triggerTablesDao, laCountyTrigger, staffPersonIdRetriever, nonLACountyTriggers);

    childClientDao = mock(ChildClientDao.class);
    childClientService = new ChildClientService(childClientDao, staffPersonIdRetriever);

    reminders = mock(Reminders.class);

    addressDao = mock(AddressDao.class);
    addressService = new AddressService(addressDao, staffPersonIdRetriever, ssaName3Dao,
        upperCaseTables, validator);


    referralService = new ReferralService(referralDao, nonLACountyTriggers, laCountyTrigger,
        triggerTablesDao, staffpersonDao, staffPersonIdRetriever, assignmentService, validator,
        drmsDocumentService, addressService, longTextService);

    screeningToReferralService = new ScreeningToReferralService(referralService, clientService,
        allegationService, crossReportService, referralClientService, reporterService,
        addressService, clientAddressService, childClientService, assignmentService,
        Validation.buildDefaultValidatorFactory().getValidator(), referralDao, new MessageBuilder(),
        allegationPerpetratorHistoryService, reminders);

  }

  /**
   * Test for when the referrals is posted all referrals related entity maintained the same
   * lastUpdatedTime
   * 
   * @throws Exception on general error
   */
  @Test
  public void testForLastUpdatedTimeIsUnique() throws Exception {
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
            createdReferal =
                (gov.ca.cwds.data.persistence.cms.Referral) invocation.getArguments()[0];
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
        .thenAnswer(new Answer<gov.ca.cwds.data.persistence.cms.Client>() {

          @Override
          public gov.ca.cwds.data.persistence.cms.Client answer(InvocationOnMock invocation)
              throws Throwable {
            createdClient = (gov.ca.cwds.data.persistence.cms.Client) invocation.getArguments()[0];
            return clientToCreate;
          }
        });

    Set<ReferralClient> referralClientDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validReferralClient.json"),
        new TypeReference<Set<ReferralClient>>() {});
    gov.ca.cwds.data.persistence.cms.ReferralClient referralClientToCreate =
        new gov.ca.cwds.data.persistence.cms.ReferralClient(
            (ReferralClient) referralClientDomain.toArray()[0], "2016-10-31");

    when(referralClientDao.create(any(gov.ca.cwds.data.persistence.cms.ReferralClient.class)))
        .thenAnswer(new Answer<gov.ca.cwds.data.persistence.cms.ReferralClient>() {

          @Override
          public gov.ca.cwds.data.persistence.cms.ReferralClient answer(InvocationOnMock invocation)
              throws Throwable {
            createdReferralClient =
                (gov.ca.cwds.data.persistence.cms.ReferralClient) invocation.getArguments()[0];
            return referralClientToCreate;
          }
        });

    Set<Allegation> allegationDomain =
        MAPPER.readValue(fixture("fixtures/domain/ScreeningToReferral/valid/validAllegation.json"),
            new TypeReference<Set<Allegation>>() {});
    gov.ca.cwds.data.persistence.cms.Allegation allegationToCreate =
        new gov.ca.cwds.data.persistence.cms.Allegation("2345678ABC",
            (Allegation) allegationDomain.toArray()[0], "2016-10-31");

    when(allegationDao.create(any(gov.ca.cwds.data.persistence.cms.Allegation.class)))
        .thenAnswer(new Answer<gov.ca.cwds.data.persistence.cms.Allegation>() {

          @Override
          public gov.ca.cwds.data.persistence.cms.Allegation answer(InvocationOnMock invocation)
              throws Throwable {
            createdAllegation =
                (gov.ca.cwds.data.persistence.cms.Allegation) invocation.getArguments()[0];
            return allegationToCreate;
          }
        });

    AllegationPerpetratorHistory allegationPerpHistoryDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validAllegationPerpetratorHistory.json"),
        AllegationPerpetratorHistory.class);
    gov.ca.cwds.data.persistence.cms.AllegationPerpetratorHistory allegationPerpHistoryToCreate =
        new gov.ca.cwds.data.persistence.cms.AllegationPerpetratorHistory("567890ABC",
            allegationPerpHistoryDomain, "2017-07-03");
    when(allegationPerpetratorHistoryDao
        .create(any(gov.ca.cwds.data.persistence.cms.AllegationPerpetratorHistory.class)))
            .thenReturn(allegationPerpHistoryToCreate);

    when(allegationPerpetratorHistoryDao
        .create(any(gov.ca.cwds.data.persistence.cms.AllegationPerpetratorHistory.class)))
            .thenAnswer(
                new Answer<gov.ca.cwds.data.persistence.cms.AllegationPerpetratorHistory>() {

                  @Override
                  public gov.ca.cwds.data.persistence.cms.AllegationPerpetratorHistory answer(
                      InvocationOnMock invocation) throws Throwable {
                    createdAllegationPerpetratorHistory =
                        (gov.ca.cwds.data.persistence.cms.AllegationPerpetratorHistory) invocation
                            .getArguments()[0];
                    return allegationPerpHistoryToCreate;
                  }
                });

    Set<CrossReport> crossReportDomain =
        MAPPER.readValue(fixture("fixtures/domain/ScreeningToReferral/valid/validCrossReport.json"),
            new TypeReference<Set<CrossReport>>() {});
    gov.ca.cwds.data.persistence.cms.CrossReport crossReportToCreate =
        new gov.ca.cwds.data.persistence.cms.CrossReport("3456789ABC",
            (CrossReport) crossReportDomain.toArray()[0], "OXA");

    when(crossReportDao.create(any(gov.ca.cwds.data.persistence.cms.CrossReport.class)))
        .thenAnswer(new Answer<gov.ca.cwds.data.persistence.cms.CrossReport>() {

          @Override
          public gov.ca.cwds.data.persistence.cms.CrossReport answer(InvocationOnMock invocation)
              throws Throwable {
            createdCrossReport =
                (gov.ca.cwds.data.persistence.cms.CrossReport) invocation.getArguments()[0];
            return crossReportToCreate;
          }
        });

    Reporter reporterDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validReporter.json"), Reporter.class);
    gov.ca.cwds.data.persistence.cms.Reporter reporterToCreate =
        new gov.ca.cwds.data.persistence.cms.Reporter(reporterDomain, "ABC");

    when(reporterDao.create(any(gov.ca.cwds.data.persistence.cms.Reporter.class)))
        .thenAnswer(new Answer<gov.ca.cwds.data.persistence.cms.Reporter>() {

          @Override
          public gov.ca.cwds.data.persistence.cms.Reporter answer(InvocationOnMock invocation)
              throws Throwable {
            createdReporter =
                (gov.ca.cwds.data.persistence.cms.Reporter) invocation.getArguments()[0];
            return reporterToCreate;
          }
        });

    Address addressDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validAddress.json"), Address.class);
    gov.ca.cwds.data.persistence.cms.Address addressToCreate =
        new gov.ca.cwds.data.persistence.cms.Address("345678ABC", addressDomain, "ABC");

    when(addressDao.create(any(gov.ca.cwds.data.persistence.cms.Address.class)))
        .thenAnswer(new Answer<gov.ca.cwds.data.persistence.cms.Address>() {

          @Override
          public gov.ca.cwds.data.persistence.cms.Address answer(InvocationOnMock invocation)
              throws Throwable {
            createdAddress =
                (gov.ca.cwds.data.persistence.cms.Address) invocation.getArguments()[0];
            return addressToCreate;
          }
        });

    ClientAddress clientAddressDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validClientAddress.json"),
        ClientAddress.class);
    gov.ca.cwds.data.persistence.cms.ClientAddress clientAddressToCreate =
        new gov.ca.cwds.data.persistence.cms.ClientAddress("456789ABC", clientAddressDomain, "ABC");

    when(clientAddressDao.create(any(gov.ca.cwds.data.persistence.cms.ClientAddress.class)))
        .thenAnswer(new Answer<gov.ca.cwds.data.persistence.cms.ClientAddress>() {

          @Override
          public gov.ca.cwds.data.persistence.cms.ClientAddress answer(InvocationOnMock invocation)
              throws Throwable {
            createdClientAddress =
                (gov.ca.cwds.data.persistence.cms.ClientAddress) invocation.getArguments()[0];
            return clientAddressToCreate;
          }
        });

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
        .thenAnswer(new Answer<gov.ca.cwds.data.persistence.cms.Assignment>() {

          @Override
          public gov.ca.cwds.data.persistence.cms.Assignment answer(InvocationOnMock invocation)
              throws Throwable {
            createdAssignment =
                (gov.ca.cwds.data.persistence.cms.Assignment) invocation.getArguments()[0];
            return assignmentToCreate;
          }
        });

    ScreeningToReferral screeningToReferral = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/valid.json"), ScreeningToReferral.class);

    Response response = screeningToReferralService.create(screeningToReferral);
    if (response.hasMessages()) {
      List<ErrorMessage> messages = response.getMessages();
      for (ErrorMessage message : messages) {
        System.out.println(message.getMessage());
      }
    }
    final Date createdTimestamp = createdReferal.getLastUpdatedTime();
    assertThat(createdAddress.getLastUpdatedTime(), is(equalTo(createdTimestamp)));
    assertThat(createdAllegation.getLastUpdatedTime(), is(equalTo(createdTimestamp)));
    assertThat(createdClient.getLastUpdatedTime(), is(equalTo(createdTimestamp)));
    assertThat(createdClientAddress.getLastUpdatedTime(), is(equalTo(createdTimestamp)));
    assertThat(createdCrossReport.getLastUpdatedTime(), is(equalTo(createdTimestamp)));
    assertThat(createdReporter.getLastUpdatedTime(), is(equalTo(createdTimestamp)));
    assertThat(createdReferralClient.getLastUpdatedTime(), is(equalTo(createdTimestamp)));
    assertThat(createdAssignment.getLastUpdatedTime(), is(equalTo(createdTimestamp)));
    assertThat(createdAllegationPerpetratorHistory.getLastUpdatedTime(),
        is(equalTo(createdTimestamp)));
    assertThat(response.hasMessages(), is(equalTo(false)));
  }

}
