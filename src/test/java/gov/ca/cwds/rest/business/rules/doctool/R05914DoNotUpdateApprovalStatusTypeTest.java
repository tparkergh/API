package gov.ca.cwds.rest.business.rules.doctool;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
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
import gov.ca.cwds.data.rules.TriggerTablesDao;
import gov.ca.cwds.rest.api.domain.ScreeningToReferral;
import gov.ca.cwds.rest.api.domain.cms.DrmsDocument;
import gov.ca.cwds.rest.api.domain.cms.LongText;
import gov.ca.cwds.rest.api.domain.cms.Referral;
import gov.ca.cwds.rest.business.rules.ExternalInterfaceTables;
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
import gov.ca.cwds.rest.services.cms.RIChildClient;
import gov.ca.cwds.rest.services.cms.ReferralClientService;
import gov.ca.cwds.rest.services.cms.ReferralService;
import gov.ca.cwds.rest.services.cms.ReporterService;
import gov.ca.cwds.rest.services.cms.StaffPersonIdRetriever;
import io.dropwizard.jackson.Jackson;

import javax.validation.Validation;
import javax.validation.Validator;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Test Class for DocTool Rule: R - 05914 - Do Not Update Approval Status Type
 * 
 * @author CWDS API Team
 */
public class R05914DoNotUpdateApprovalStatusTypeTest {
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
  private AssignmentDao assignmentDao;
  private NonLACountyTriggers nonLACountyTriggers;
  private LACountyTrigger laCountyTrigger;
  private TriggerTablesDao triggerTablesDao;
  private StaffPersonIdRetriever staffPersonIdRetriever;
  private DrmsDocumentService drmsDocumentService;
  private DrmsDocumentDao drmsDocumentDao;
  private SsaName3Dao ssaName3Dao;
  private Reminders reminders;
  private UpperCaseTables upperCaseTables;
  private Validator validator;
  private ExternalInterfaceTables externalInterfaceTables;



  @SuppressWarnings("javadoc")
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @SuppressWarnings("javadoc")
  @Before
  public void setup() throws Exception {
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    staffPersonIdRetriever = mock(StaffPersonIdRetriever.class);
    when(staffPersonIdRetriever.getStaffPersonId()).thenReturn("0X5");

    longTextDao = mock(LongTextDao.class);
    longTextService = new LongTextService(longTextDao, staffPersonIdRetriever);

    referralDao = mock(ReferralDao.class);
    nonLACountyTriggers = mock(NonLACountyTriggers.class);
    laCountyTrigger = mock(LACountyTrigger.class);
    triggerTablesDao = mock(TriggerTablesDao.class);
    staffpersonDao = mock(StaffPersonDao.class);

    drmsDocumentDao = mock(DrmsDocumentDao.class);
    drmsDocumentService = new DrmsDocumentService(drmsDocumentDao, staffPersonIdRetriever);

    addressDao = mock(AddressDao.class);
    addressService =
        new AddressService(addressDao, staffPersonIdRetriever, ssaName3Dao, upperCaseTables,
            validator);

    referralService =
        new ReferralService(referralDao, nonLACountyTriggers, laCountyTrigger, triggerTablesDao,
            staffpersonDao, staffPersonIdRetriever, assignmentService, validator,
            drmsDocumentService, addressService, longTextService);

    clientDao = mock(ClientDao.class);
    staffpersonDao = mock(StaffPersonDao.class);
    triggerTablesDao = mock(TriggerTablesDao.class);
    nonLACountyTriggers = mock(NonLACountyTriggers.class);
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
    referralClientService =
        new ReferralClientService(referralClientDao, nonLACountyTriggers, laCountyTrigger,
            triggerTablesDao, staffpersonDao, staffPersonIdRetriever);

    allegationDao = mock(AllegationDao.class);
    allegationService = new AllegationService(allegationDao, staffPersonIdRetriever);

    allegationPerpetratorHistoryDao = mock(AllegationPerpetratorHistoryDao.class);
    allegationPerpetratorHistoryService =
        new AllegationPerpetratorHistoryService(allegationPerpetratorHistoryDao,
            staffPersonIdRetriever);

    crossReportDao = mock(CrossReportDao.class);
    crossReportService = new CrossReportService(crossReportDao, staffPersonIdRetriever);

    reporterDao = mock(ReporterDao.class);
    reporterService = new ReporterService(reporterDao, staffPersonIdRetriever);

    clientAddressDao = mock(ClientAddressDao.class);
    laCountyTrigger = mock(LACountyTrigger.class);
    triggerTablesDao = mock(TriggerTablesDao.class);
    staffpersonDao = mock(StaffPersonDao.class);
    nonLACountyTriggers = mock(NonLACountyTriggers.class);
    clientAddressService =
        new ClientAddressService(clientAddressDao, staffpersonDao, triggerTablesDao,
            laCountyTrigger, staffPersonIdRetriever, nonLACountyTriggers);

    childClientDao = mock(ChildClientDao.class);
    riChildClient = mock(RIChildClient.class);
    childClientService =
        new ChildClientService(childClientDao, staffPersonIdRetriever, riChildClient);

    assignmentDao = mock(AssignmentDao.class);
    staffpersonDao = mock(StaffPersonDao.class);
    nonLACountyTriggers = mock(NonLACountyTriggers.class);
    triggerTablesDao = mock(TriggerTablesDao.class);
    assignmentService =
        new AssignmentService(assignmentDao, nonLACountyTriggers, staffpersonDao, triggerTablesDao,
            staffPersonIdRetriever, validator, externalInterfaceTables);

    reminders = mock(Reminders.class);

    screeningToReferralService =
        new ScreeningToReferralService(referralService, clientService, allegationService,
            crossReportService, referralClientService, reporterService, addressService,
            clientAddressService, childClientService, assignmentService, Validation
                .buildDefaultValidatorFactory().getValidator(), referralDao, new MessageBuilder(),
            allegationPerpetratorHistoryService, reminders);

  }

  /**
   * <blockquote>
   *
   * <pre>
   *
   * DocTool Rule: "R - 05914" -  Do Not Update Approval Status Type
   * 
   * When creating the Referral entity, set the Approval Status Type = 'Request Not Submitted'.
   * When updating the Referral entity, update every attribute except Approval Status Type.
   * The Approval Status Type will be updated by the Host, not the workstation.
   * </pre>
   *
   * </blockquote>
   *
   * @throws Exception general error
   */
  @Test
  public void createReferralWithDefaultsSetsApprovalCodeNotSubmitted() throws Exception {
    ScreeningToReferral screeningToReferral =
        MAPPER.readValue(fixture("fixtures/domain/ScreeningToReferral/valid/valid.json"),
            ScreeningToReferral.class);

    LongText longTextDomain =
        MAPPER.readValue(fixture("fixtures/domain/ScreeningToReferral/valid/validLongText.json"),
            LongText.class);
    gov.ca.cwds.data.persistence.cms.LongText longTextToCreate =
        new gov.ca.cwds.data.persistence.cms.LongText("567890ABC", longTextDomain, "ABC");
    when(longTextDao.create(any(gov.ca.cwds.data.persistence.cms.LongText.class))).thenReturn(
        longTextToCreate);

    DrmsDocument drmsDocumentDomain =
        MAPPER.readValue(fixture("fixtures/domain/legacy/DrmsDocument/valid/valid.json"),
            DrmsDocument.class);
    gov.ca.cwds.data.persistence.cms.DrmsDocument drmsDocumentToCreate =
        new gov.ca.cwds.data.persistence.cms.DrmsDocument("ABD1234568", drmsDocumentDomain, "ABC");
    when(drmsDocumentDao.create(any(gov.ca.cwds.data.persistence.cms.DrmsDocument.class)))
        .thenReturn(drmsDocumentToCreate);

    Referral referralCreated =
        referralService.createReferralWithDefaults(screeningToReferral, "2016-08-03T01:00:00.000Z",
            "2016-08-03T01:00:00.000Z", null, new MessageBuilder());
    System.out.println(referralCreated.getApprovalStatusType());
    assertThat(referralCreated.getApprovalStatusType(), is(equalTo((short) 118)));
  }

}
