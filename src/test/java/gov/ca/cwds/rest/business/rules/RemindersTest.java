package gov.ca.cwds.rest.business.rules;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import gov.ca.cwds.data.cms.AllegationDao;
import gov.ca.cwds.data.cms.ClientDao;
import gov.ca.cwds.data.cms.CrossReportDao;
import gov.ca.cwds.data.cms.ReferralDao;
import gov.ca.cwds.data.cms.ReporterDao;
import gov.ca.cwds.fixture.AddressResourceBuilder;
import gov.ca.cwds.fixture.AllegationResourceBuilder;
import gov.ca.cwds.fixture.CrossReportResourceBuilder;
import gov.ca.cwds.fixture.MockedScreeningToReferralServiceBuilder;
import gov.ca.cwds.fixture.ParticipantResourceBuilder;
import gov.ca.cwds.fixture.ScreeningToReferralResourceBuilder;
import gov.ca.cwds.rest.api.domain.Address;
import gov.ca.cwds.rest.api.domain.Allegation;
import gov.ca.cwds.rest.api.domain.CrossReport;
import gov.ca.cwds.rest.api.domain.Participant;
import gov.ca.cwds.rest.api.domain.PostedScreeningToReferral;
import gov.ca.cwds.rest.api.domain.ScreeningToReferral;
import gov.ca.cwds.rest.api.domain.cms.Client;
import gov.ca.cwds.rest.api.domain.cms.Referral;
import gov.ca.cwds.rest.api.domain.cms.Reporter;
import gov.ca.cwds.rest.services.cms.TickleService;

/**
 * @author CWDS API Team
 *
 */
public class RemindersTest {

  private TickleService tickleService;
  private ClientDao clientDao;
  private ReferralDao referralDao;
  private AllegationDao allegationDao;
  private ReporterDao reporterDao;
  private CrossReportDao crossReportDao;

  private static final short DEFAULT_CODE = 0;
  private static final String DEFAULT_NON_PROTECTING_PARENT_CODE = "U";
  private static final String DEFAULT_COUNTY_SPECIFIC_CODE = "62";
  private static final String DEFAULT_STAFF_PERSON_ID = "0X5";
  private short allegationTypeCode = 2178;

  @SuppressWarnings("javadoc")
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  /**
   * @throws Exception general test error
   */
  @Before
  public void setup() throws Exception {
    tickleService = mock(TickleService.class);
    clientDao = mock(ClientDao.class);
    referralDao = mock(ReferralDao.class);
    allegationDao = mock(AllegationDao.class);
    reporterDao = mock(ReporterDao.class);
    crossReportDao = mock(CrossReportDao.class);
  }

  /**
   * Test for the state id missing Reminder
   * 
   * @throws Exception - exception
   */
  @Test
  public void testForStateIdMissingReminder() throws Exception {
    Participant victim =
        new ParticipantResourceBuilder().setDateOfBirth("1992-06-18").createVictimParticipant();
    Participant perp =
        new ParticipantResourceBuilder().setDateOfBirth("1992-06-18").createPerpParticipant();

    Participant reporter = new ParticipantResourceBuilder().createReporterParticipant();
    Set<Participant> participants = new HashSet<>(Arrays.asList(perp, victim, reporter));
    CrossReport crossReport = new CrossReportResourceBuilder().createCrossReport();
    Allegation allegation = new AllegationResourceBuilder().createAllegation();
    Set<CrossReport> crossReports = new HashSet<>(Arrays.asList(crossReport));
    Set<Allegation> allegations = new HashSet<>(Arrays.asList(allegation));
    ScreeningToReferral referral = new ScreeningToReferralResourceBuilder()
        .setParticipants(participants).createScreeningToReferral();
    PostedScreeningToReferral postedScreeningToReferral = PostedScreeningToReferral
        .createWithDefaults("123ABC1234", referral, participants, crossReports, allegations);

    Referral domainReferral =
        new MockedScreeningToReferralServiceBuilder().createScreeningToReferralService()
            .createReferralWithDefaults(referral, "2016-09-02", "13:00:00", new Date());

    gov.ca.cwds.data.persistence.cms.Referral savedReferral =
        new gov.ca.cwds.data.persistence.cms.Referral("123ABC1235", domainReferral, "0X5");

    Client client = Client.createWithDefaults(victim, "2016-09-02", "");
    gov.ca.cwds.data.persistence.cms.Client savedClient =
        new gov.ca.cwds.data.persistence.cms.Client("ABC1234567", client, "0X5");

    gov.ca.cwds.rest.api.domain.cms.Allegation cmsAllegation =
        new gov.ca.cwds.rest.api.domain.cms.Allegation("", DEFAULT_CODE, "",
            referral.getLocationType(), "", DEFAULT_CODE, allegationTypeCode,
            referral.getReportNarrative(), "", false, DEFAULT_NON_PROTECTING_PARENT_CODE, false,
            "ABC1234568", "ABC1234560", "123ABC1235", DEFAULT_COUNTY_SPECIFIC_CODE, false,
            DEFAULT_CODE);

    gov.ca.cwds.data.persistence.cms.Allegation savedAllegation =
        new gov.ca.cwds.data.persistence.cms.Allegation("123ABC1236", cmsAllegation, "0X5");

    Address address = new AddressResourceBuilder().createAddress();
    Reporter reporterDomain =
        Reporter.createWithDefaults("123ABC1235", false, address, reporter, "62", (short) 1828);

    gov.ca.cwds.data.persistence.cms.Reporter savedReporter =
        new gov.ca.cwds.data.persistence.cms.Reporter(reporterDomain, "0X5");

    when(referralDao.find(any(String.class))).thenReturn(savedReferral);
    when(clientDao.find(any(String.class))).thenReturn(savedClient);
    when(allegationDao.find(any(String.class))).thenReturn(savedAllegation);
    when(reporterDao.find(any(String.class))).thenReturn(savedReporter);
    Reminders reminders = new Reminders(clientDao, referralDao, allegationDao, reporterDao,
        crossReportDao, tickleService);

    reminders.createTickle(postedScreeningToReferral);
    verify(tickleService, times(2)).create(any());
  }

  /**
   * Test for the no state id missing Reminder created when the DateOfBirth is Null
   * 
   * @throws Exception - exception
   */
  @Test
  public void testForNoStateIdMissingReminderWhenDobIsNull() throws Exception {
    Participant victim =
        new ParticipantResourceBuilder().setDateOfBirth(null).createVictimParticipant();
    Participant perp =
        new ParticipantResourceBuilder().setDateOfBirth(null).createPerpParticipant();

    Participant reporter = new ParticipantResourceBuilder().createReporterParticipant();
    Set<Participant> participants = new HashSet<>(Arrays.asList(perp, victim, reporter));
    CrossReport crossReport = new CrossReportResourceBuilder().createCrossReport();
    Allegation allegation = new AllegationResourceBuilder().createAllegation();
    Set<CrossReport> crossReports = new HashSet<>(Arrays.asList(crossReport));
    Set<Allegation> allegations = new HashSet<>(Arrays.asList(allegation));
    ScreeningToReferral referral = new ScreeningToReferralResourceBuilder()
        .setParticipants(participants).createScreeningToReferral();
    PostedScreeningToReferral postedScreeningToReferral = PostedScreeningToReferral
        .createWithDefaults("123ABC1234", referral, participants, crossReports, allegations);

    Referral domainReferral =
        new MockedScreeningToReferralServiceBuilder().createScreeningToReferralService()
            .createReferralWithDefaults(referral, "2016-09-02", "13:00:00", new Date());

    gov.ca.cwds.data.persistence.cms.Referral savedReferral =
        new gov.ca.cwds.data.persistence.cms.Referral("123ABC1235", domainReferral, "0X5");

    Client client = Client.createWithDefaults(victim, "2016-09-02", "");
    gov.ca.cwds.data.persistence.cms.Client savedClient =
        new gov.ca.cwds.data.persistence.cms.Client("ABC1234567", client, "0X5");

    gov.ca.cwds.rest.api.domain.cms.Allegation cmsAllegation =
        new gov.ca.cwds.rest.api.domain.cms.Allegation("", DEFAULT_CODE, "",
            referral.getLocationType(), "", DEFAULT_CODE, allegationTypeCode,
            referral.getReportNarrative(), "", false, DEFAULT_NON_PROTECTING_PARENT_CODE, false,
            "ABC1234568", "ABC1234560", "123ABC1235", DEFAULT_COUNTY_SPECIFIC_CODE, false,
            DEFAULT_CODE);

    gov.ca.cwds.data.persistence.cms.Allegation savedAllegation =
        new gov.ca.cwds.data.persistence.cms.Allegation("123ABC1236", cmsAllegation, "0X5");

    Address address = new AddressResourceBuilder().createAddress();
    Reporter reporterDomain =
        Reporter.createWithDefaults("123ABC1235", false, address, reporter, "62", (short) 1828);

    gov.ca.cwds.data.persistence.cms.Reporter savedReporter =
        new gov.ca.cwds.data.persistence.cms.Reporter(reporterDomain, "0X5");

    when(referralDao.find(any(String.class))).thenReturn(savedReferral);
    when(clientDao.find(any(String.class))).thenReturn(savedClient);
    when(allegationDao.find(any(String.class))).thenReturn(savedAllegation);
    when(reporterDao.find(any(String.class))).thenReturn(savedReporter);
    Reminders reminders = new Reminders(clientDao, referralDao, allegationDao, reporterDao,
        crossReportDao, tickleService);

    reminders.createTickle(postedScreeningToReferral);
    verify(tickleService, times(0)).create(any());
  }

  /**
   * Test for state Id and Referral Investigation reminder
   * 
   * @throws Exception - exception
   */
  @Test
  public void testForStateIdMissingAndReferralInvestigationReminder() throws Exception {
    Participant victim =
        new ParticipantResourceBuilder().setDateOfBirth("1992-06-18").createVictimParticipant();
    Participant perp = new ParticipantResourceBuilder().createPerpParticipant();

    Participant reporter = new ParticipantResourceBuilder().createReporterParticipant();
    Set<Participant> participants = new HashSet<>(Arrays.asList(perp, victim, reporter));
    CrossReport crossReport = new CrossReportResourceBuilder().createCrossReport();
    Allegation allegation = new AllegationResourceBuilder().createAllegation();
    Set<CrossReport> crossReports = new HashSet<>(Arrays.asList(crossReport));
    Set<Allegation> allegations = new HashSet<>(Arrays.asList(allegation));
    ScreeningToReferral referral = new ScreeningToReferralResourceBuilder()
        .setParticipants(participants).createScreeningToReferral();
    PostedScreeningToReferral postedScreeningToReferral = PostedScreeningToReferral
        .createWithDefaults("123ABC1234", referral, participants, crossReports, allegations);

    Referral domainReferral =
        new MockedScreeningToReferralServiceBuilder().createScreeningToReferralService()
            .createReferralWithDefaults(referral, "2016-09-02", "13:00:00", new Date());

    gov.ca.cwds.data.persistence.cms.Referral savedReferral =
        new gov.ca.cwds.data.persistence.cms.Referral("123ABC1235", domainReferral, "0X5");

    Client client = Client.createWithDefaults(victim, "2016-09-02", "");
    gov.ca.cwds.data.persistence.cms.Client savedClient =
        new gov.ca.cwds.data.persistence.cms.Client("ABC1234567", client, "0X5");

    gov.ca.cwds.rest.api.domain.cms.Allegation cmsAllegation =
        new gov.ca.cwds.rest.api.domain.cms.Allegation("", DEFAULT_CODE, "",
            referral.getLocationType(), "", DEFAULT_CODE, allegationTypeCode,
            referral.getReportNarrative(), "", false, DEFAULT_NON_PROTECTING_PARENT_CODE, false,
            "ABC1234568", "ABC1234560", "123ABC1235", DEFAULT_COUNTY_SPECIFIC_CODE, false,
            DEFAULT_CODE);

    gov.ca.cwds.data.persistence.cms.Allegation savedAllegation =
        new gov.ca.cwds.data.persistence.cms.Allegation("123ABC1236", cmsAllegation, "0X5");

    Address address = new AddressResourceBuilder().createAddress();

    Reporter reporterDomain =
        Reporter.createWithDefaults("123ABC1235", false, address, reporter, "62", (short) 1828);

    gov.ca.cwds.data.persistence.cms.Reporter savedReporter =
        new gov.ca.cwds.data.persistence.cms.Reporter(reporterDomain, "0X5");

    when(referralDao.find(any(String.class))).thenReturn(savedReferral);
    when(clientDao.find(any(String.class))).thenReturn(savedClient);
    when(allegationDao.find(any(String.class))).thenReturn(savedAllegation);
    when(reporterDao.find(any(String.class))).thenReturn(savedReporter);
    Reminders reminders = new Reminders(clientDao, referralDao, allegationDao, reporterDao,
        crossReportDao, tickleService);

    reminders.createTickle(postedScreeningToReferral);
    verify(tickleService, times(3)).create(any());
  }

  /**
   * Test for all reminders created on the referrals
   * 
   * @throws Exception - exception
   */
  @Test
  public void testForAllCreatedReminders() throws Exception {
    Participant victim =
        new ParticipantResourceBuilder().setDateOfBirth("1992-06-18").createVictimParticipant();
    Participant perp = new ParticipantResourceBuilder().createPerpParticipant();

    Participant reporter = new ParticipantResourceBuilder().createReporterParticipant();
    Set<Participant> participants = new HashSet<>(Arrays.asList(perp, victim, reporter));
    CrossReport crossReport = new CrossReportResourceBuilder().createCrossReport();
    Allegation allegation = new AllegationResourceBuilder().createAllegation();
    Set<CrossReport> crossReports = new HashSet<>(Arrays.asList(crossReport));
    Set<Allegation> allegations = new HashSet<>(Arrays.asList(allegation));
    ScreeningToReferral referral = new ScreeningToReferralResourceBuilder()
        .setParticipants(participants).createScreeningToReferral();
    PostedScreeningToReferral postedScreeningToReferral = PostedScreeningToReferral
        .createWithDefaults("123ABC1234", referral, participants, crossReports, allegations);

    Referral domainReferral =
        new MockedScreeningToReferralServiceBuilder().createScreeningToReferralService()
            .createReferralWithDefaults(referral, "2016-09-02", "13:00:00", new Date());

    gov.ca.cwds.data.persistence.cms.Referral savedReferral =
        new gov.ca.cwds.data.persistence.cms.Referral("123ABC1235", domainReferral, "0X5");

    Client client = Client.createWithDefaults(victim, "2016-09-02", "");
    gov.ca.cwds.data.persistence.cms.Client savedClient =
        new gov.ca.cwds.data.persistence.cms.Client("ABC1234567", client, "0X5");

    gov.ca.cwds.rest.api.domain.cms.Allegation cmsAllegation =
        new gov.ca.cwds.rest.api.domain.cms.Allegation("", DEFAULT_CODE, "",
            referral.getLocationType(), "", DEFAULT_CODE, (short) 0, referral.getReportNarrative(),
            "", false, DEFAULT_NON_PROTECTING_PARENT_CODE, false, "ABC1234568", "ABC1234560",
            "123ABC1235", DEFAULT_COUNTY_SPECIFIC_CODE, false, DEFAULT_CODE);

    gov.ca.cwds.data.persistence.cms.Allegation savedAllegation =
        new gov.ca.cwds.data.persistence.cms.Allegation("123ABC1236", cmsAllegation, "0X5");

    Address address = new AddressResourceBuilder().createAddress();

    Reporter reporterDomain =
        Reporter.createWithDefaults("123ABC1235", false, address, reporter, "62", (short) 1828);

    gov.ca.cwds.data.persistence.cms.Reporter savedReporter =
        new gov.ca.cwds.data.persistence.cms.Reporter(reporterDomain, "0X5");

    gov.ca.cwds.rest.api.domain.cms.CrossReport cmsCrossReport =
        gov.ca.cwds.rest.api.domain.cms.CrossReport.createWithDefaults("123ABC1K35", crossReport,
            "123ABC1235", DEFAULT_STAFF_PERSON_ID, DEFAULT_COUNTY_SPECIFIC_CODE, false);

    gov.ca.cwds.data.persistence.cms.CrossReport savedCrossReport =
        new gov.ca.cwds.data.persistence.cms.CrossReport("123ABp1235", cmsCrossReport, "0X5");

    when(referralDao.find(any(String.class))).thenReturn(savedReferral);
    when(clientDao.find(any(String.class))).thenReturn(savedClient);
    when(allegationDao.find(any(String.class))).thenReturn(savedAllegation);
    when(reporterDao.find(any(String.class))).thenReturn(savedReporter);
    when(crossReportDao.find(any(String.class))).thenReturn(savedCrossReport);
    Reminders reminders = new Reminders(clientDao, referralDao, allegationDao, reporterDao,
        crossReportDao, tickleService);

    reminders.createTickle(postedScreeningToReferral);
    verify(tickleService, times(4)).create(any());
  }

  /**
   * Test if the allegation depsotion type is Entered in Error(5918) CrossReport reminder is not
   * created
   * 
   * @throws Exception - exception
   */
  @Test
  public void testForAllegationDispostionTypeIsEnteredInError() throws Exception {
    Participant victim =
        new ParticipantResourceBuilder().setDateOfBirth("1992-06-18").createVictimParticipant();
    Participant perp = new ParticipantResourceBuilder().createPerpParticipant();

    Participant reporter = new ParticipantResourceBuilder().createReporterParticipant();
    Set<Participant> participants = new HashSet<>(Arrays.asList(perp, victim, reporter));
    CrossReport crossReport = new CrossReportResourceBuilder().createCrossReport();
    Allegation allegation = new AllegationResourceBuilder().createAllegation();
    Set<CrossReport> crossReports = new HashSet<>(Arrays.asList(crossReport));
    Set<Allegation> allegations = new HashSet<>(Arrays.asList(allegation));
    ScreeningToReferral referral = new ScreeningToReferralResourceBuilder()
        .setParticipants(participants).createScreeningToReferral();
    PostedScreeningToReferral postedScreeningToReferral = PostedScreeningToReferral
        .createWithDefaults("123ABC1234", referral, participants, crossReports, allegations);

    Referral domainReferral =
        new MockedScreeningToReferralServiceBuilder().createScreeningToReferralService()
            .createReferralWithDefaults(referral, "2016-09-02", "13:00:00", new Date());

    gov.ca.cwds.data.persistence.cms.Referral savedReferral =
        new gov.ca.cwds.data.persistence.cms.Referral("123ABC1235", domainReferral, "0X5");

    Client client = Client.createWithDefaults(victim, "2016-09-02", "");
    gov.ca.cwds.data.persistence.cms.Client savedClient =
        new gov.ca.cwds.data.persistence.cms.Client("ABC1234567", client, "0X5");

    gov.ca.cwds.rest.api.domain.cms.Allegation cmsAllegation =
        new gov.ca.cwds.rest.api.domain.cms.Allegation("", DEFAULT_CODE, "",
            referral.getLocationType(), "", (short) 5918, (short) 0, referral.getReportNarrative(),
            "", false, DEFAULT_NON_PROTECTING_PARENT_CODE, false, "ABC1234568", "ABC1234560",
            "123ABC1235", DEFAULT_COUNTY_SPECIFIC_CODE, false, DEFAULT_CODE);

    gov.ca.cwds.data.persistence.cms.Allegation savedAllegation =
        new gov.ca.cwds.data.persistence.cms.Allegation("123ABC1236", cmsAllegation, "0X5");

    Address address = new AddressResourceBuilder().createAddress();

    Reporter reporterDomain =
        Reporter.createWithDefaults("123ABC1235", false, address, reporter, "62", (short) 1828);

    gov.ca.cwds.data.persistence.cms.Reporter savedReporter =
        new gov.ca.cwds.data.persistence.cms.Reporter(reporterDomain, "0X5");

    gov.ca.cwds.rest.api.domain.cms.CrossReport cmsCrossReport =
        gov.ca.cwds.rest.api.domain.cms.CrossReport.createWithDefaults("123ABC1K35", crossReport,
            "123ABC1235", DEFAULT_STAFF_PERSON_ID, DEFAULT_COUNTY_SPECIFIC_CODE, false);

    gov.ca.cwds.data.persistence.cms.CrossReport savedCrossReport =
        new gov.ca.cwds.data.persistence.cms.CrossReport("123ABp1235", cmsCrossReport, "0X5");

    when(referralDao.find(any(String.class))).thenReturn(savedReferral);
    when(clientDao.find(any(String.class))).thenReturn(savedClient);
    when(allegationDao.find(any(String.class))).thenReturn(savedAllegation);
    when(reporterDao.find(any(String.class))).thenReturn(savedReporter);
    when(crossReportDao.find(any(String.class))).thenReturn(savedCrossReport);
    Reminders reminders = new Reminders(clientDao, referralDao, allegationDao, reporterDao,
        crossReportDao, tickleService);

    reminders.createTickle(postedScreeningToReferral);
    verify(tickleService, times(3)).create(any());
  }

  /**
   * Test for the Client DateOfBirth below 19 years referral Investigation Reminder is created
   * 
   * @throws Exception - exception
   */
  @Test
  public void testForClientDateOfBirthLessThan19ReferralInvestigationReminder() throws Exception {
    Participant victim =
        new ParticipantResourceBuilder().setDateOfBirth("2010-03-15").createVictimParticipant();
    Participant perp =
        new ParticipantResourceBuilder().setDateOfBirth("2011-03-15").createPerpParticipant();

    Participant reporter = new ParticipantResourceBuilder().createReporterParticipant();
    Set<Participant> participants = new HashSet<>(Arrays.asList(perp, victim, reporter));
    CrossReport crossReport = new CrossReportResourceBuilder().createCrossReport();
    Allegation allegation = new AllegationResourceBuilder().createAllegation();
    Set<CrossReport> crossReports = new HashSet<>(Arrays.asList(crossReport));
    Set<Allegation> allegations = new HashSet<>(Arrays.asList(allegation));
    ScreeningToReferral referral = new ScreeningToReferralResourceBuilder()
        .setParticipants(participants).createScreeningToReferral();
    PostedScreeningToReferral postedScreeningToReferral = PostedScreeningToReferral
        .createWithDefaults("123ABC1234", referral, participants, crossReports, allegations);

    Referral domainReferral =
        new MockedScreeningToReferralServiceBuilder().createScreeningToReferralService()
            .createReferralWithDefaults(referral, "2016-09-02", "13:00:00", new Date());

    gov.ca.cwds.data.persistence.cms.Referral savedReferral =
        new gov.ca.cwds.data.persistence.cms.Referral("123ABC1235", domainReferral, "0X5");

    Client client = Client.createWithDefaults(victim, "2016-09-02", "");
    gov.ca.cwds.data.persistence.cms.Client savedClient =
        new gov.ca.cwds.data.persistence.cms.Client("ABC1234567", client, "0X5");

    gov.ca.cwds.rest.api.domain.cms.Allegation cmsAllegation =
        new gov.ca.cwds.rest.api.domain.cms.Allegation("", DEFAULT_CODE, "",
            referral.getLocationType(), "", DEFAULT_CODE, (short) 0, referral.getReportNarrative(),
            "", false, DEFAULT_NON_PROTECTING_PARENT_CODE, false, "ABC1234568", "ABC1234560",
            "123ABC1235", DEFAULT_COUNTY_SPECIFIC_CODE, false, DEFAULT_CODE);

    gov.ca.cwds.data.persistence.cms.Allegation savedAllegation =
        new gov.ca.cwds.data.persistence.cms.Allegation("123ABC1236", cmsAllegation, "0X5");

    Address address = new AddressResourceBuilder().createAddress();

    Reporter reporterDomain =
        Reporter.createWithDefaults("123ABC1235", false, address, reporter, "62", (short) 1828);

    gov.ca.cwds.data.persistence.cms.Reporter savedReporter =
        new gov.ca.cwds.data.persistence.cms.Reporter(reporterDomain, "0X5");

    gov.ca.cwds.rest.api.domain.cms.CrossReport cmsCrossReport =
        gov.ca.cwds.rest.api.domain.cms.CrossReport.createWithDefaults("123ABC1K35", crossReport,
            "123ABC1235", DEFAULT_STAFF_PERSON_ID, DEFAULT_COUNTY_SPECIFIC_CODE, false);

    gov.ca.cwds.data.persistence.cms.CrossReport savedCrossReport =
        new gov.ca.cwds.data.persistence.cms.CrossReport("123ABp1235", cmsCrossReport, "0X5");

    when(referralDao.find(any(String.class))).thenReturn(savedReferral);
    when(clientDao.find(any(String.class))).thenReturn(savedClient);
    when(allegationDao.find(any(String.class))).thenReturn(savedAllegation);
    when(reporterDao.find(any(String.class))).thenReturn(savedReporter);
    when(crossReportDao.find(any(String.class))).thenReturn(savedCrossReport);
    Reminders reminders = new Reminders(clientDao, referralDao, allegationDao, reporterDao,
        crossReportDao, tickleService);

    reminders.createTickle(postedScreeningToReferral);
    verify(tickleService, times(5)).create(any());
  }

  /**
   * Test for crossReport Law Enforcement reminder not created if the reporter is a Mandated
   * Reporter
   * 
   * @throws Exception - exception
   */
  @Test
  public void testForCrossReportForLawEnforcmentDueReminderNotCretaed() throws Exception {
    Participant victim =
        new ParticipantResourceBuilder().setDateOfBirth("1992-06-18").createVictimParticipant();
    Participant perp = new ParticipantResourceBuilder().createPerpParticipant();

    Participant reporter = new ParticipantResourceBuilder().createReporterParticipant();
    Set<Participant> participants = new HashSet<>(Arrays.asList(perp, victim, reporter));
    CrossReport crossReport = new CrossReportResourceBuilder().createCrossReport();
    Allegation allegation = new AllegationResourceBuilder().createAllegation();
    Set<CrossReport> crossReports = new HashSet<>(Arrays.asList(crossReport));
    Set<Allegation> allegations = new HashSet<>(Arrays.asList(allegation));
    ScreeningToReferral referral = new ScreeningToReferralResourceBuilder()
        .setParticipants(participants).createScreeningToReferral();
    PostedScreeningToReferral postedScreeningToReferral = PostedScreeningToReferral
        .createWithDefaults("123ABC1234", referral, participants, crossReports, allegations);

    Referral domainReferral =
        new MockedScreeningToReferralServiceBuilder().createScreeningToReferralService()
            .createReferralWithDefaults(referral, "2016-09-02", "13:00:00", new Date());

    gov.ca.cwds.data.persistence.cms.Referral savedReferral =
        new gov.ca.cwds.data.persistence.cms.Referral("123ABC1235", domainReferral, "0X5");

    Client client = Client.createWithDefaults(victim, "2016-09-02", "");
    gov.ca.cwds.data.persistence.cms.Client savedClient =
        new gov.ca.cwds.data.persistence.cms.Client("ABC1234567", client, "0X5");

    gov.ca.cwds.rest.api.domain.cms.Allegation cmsAllegation =
        new gov.ca.cwds.rest.api.domain.cms.Allegation("", DEFAULT_CODE, "",
            referral.getLocationType(), "", DEFAULT_CODE, (short) 0, referral.getReportNarrative(),
            "", false, DEFAULT_NON_PROTECTING_PARENT_CODE, false, "ABC1234568", "ABC1234560",
            "123ABC1235", DEFAULT_COUNTY_SPECIFIC_CODE, false, DEFAULT_CODE);

    gov.ca.cwds.data.persistence.cms.Allegation savedAllegation =
        new gov.ca.cwds.data.persistence.cms.Allegation("123ABC1236", cmsAllegation, "0X5");

    Address address = new AddressResourceBuilder().createAddress();

    Reporter reporterDomain =
        Reporter.createWithDefaults("123ABC1235", true, address, reporter, "62", (short) 1828);

    gov.ca.cwds.data.persistence.cms.Reporter savedReporter =
        new gov.ca.cwds.data.persistence.cms.Reporter(reporterDomain, "0X5");

    gov.ca.cwds.rest.api.domain.cms.CrossReport cmsCrossReport =
        gov.ca.cwds.rest.api.domain.cms.CrossReport.createWithDefaults("123ABC1K35", crossReport,
            "123ABC1235", DEFAULT_STAFF_PERSON_ID, DEFAULT_COUNTY_SPECIFIC_CODE, false);

    gov.ca.cwds.data.persistence.cms.CrossReport savedCrossReport =
        new gov.ca.cwds.data.persistence.cms.CrossReport("123ABp1235", cmsCrossReport, "0X5");

    when(referralDao.find(any(String.class))).thenReturn(savedReferral);
    when(clientDao.find(any(String.class))).thenReturn(savedClient);
    when(allegationDao.find(any(String.class))).thenReturn(savedAllegation);
    when(reporterDao.find(any(String.class))).thenReturn(savedReporter);
    when(crossReportDao.find(any(String.class))).thenReturn(savedCrossReport);
    Reminders reminders = new Reminders(clientDao, referralDao, allegationDao, reporterDao,
        crossReportDao, tickleService);

    reminders.createTickle(postedScreeningToReferral);
    verify(tickleService, times(3)).create(any());
  }

  /**
   * Test for No remiders created
   * 
   * @throws Exception - exception
   */
  @Test
  public void testForNoRemindersCreated() throws Exception {
    Participant victim =
        new ParticipantResourceBuilder().setDateOfBirth("1987-06-18").createVictimParticipant();
    Participant perp =
        new ParticipantResourceBuilder().setDateOfBirth("1987-06-18").createPerpParticipant();

    Participant reporter = new ParticipantResourceBuilder().createReporterParticipant();
    Set<Participant> participants = new HashSet<>(Arrays.asList(perp, victim, reporter));
    CrossReport crossReport = new CrossReportResourceBuilder().createCrossReport();
    Allegation allegation = new AllegationResourceBuilder().createAllegation();
    Set<CrossReport> crossReports = new HashSet<>(Arrays.asList(crossReport));
    Set<Allegation> allegations = new HashSet<>(Arrays.asList(allegation));
    ScreeningToReferral referral = new ScreeningToReferralResourceBuilder()
        .setParticipants(participants).createScreeningToReferral();
    PostedScreeningToReferral postedScreeningToReferral = PostedScreeningToReferral
        .createWithDefaults("123ABC1234", referral, participants, crossReports, allegations);

    Referral domainReferral =
        new MockedScreeningToReferralServiceBuilder().createScreeningToReferralService()
            .createReferralWithDefaults(referral, "2016-09-02", "13:00:00", new Date());

    gov.ca.cwds.data.persistence.cms.Referral savedReferral =
        new gov.ca.cwds.data.persistence.cms.Referral("123ABC1235", domainReferral, "0X5");

    Client client = Client.createWithDefaults(victim, "2016-09-02", "");
    gov.ca.cwds.data.persistence.cms.Client savedClient =
        new gov.ca.cwds.data.persistence.cms.Client("ABC1234567", client, "0X5");

    gov.ca.cwds.rest.api.domain.cms.Allegation cmsAllegation =
        new gov.ca.cwds.rest.api.domain.cms.Allegation("", DEFAULT_CODE, "",
            referral.getLocationType(), "", DEFAULT_CODE, (short) 0, referral.getReportNarrative(),
            "", false, DEFAULT_NON_PROTECTING_PARENT_CODE, false, "ABC1234568", "ABC1234560",
            "123ABC1235", DEFAULT_COUNTY_SPECIFIC_CODE, false, DEFAULT_CODE);

    gov.ca.cwds.data.persistence.cms.Allegation savedAllegation =
        new gov.ca.cwds.data.persistence.cms.Allegation("123ABC1236", cmsAllegation, "0X5");

    Address address = new AddressResourceBuilder().createAddress();

    Reporter reporterDomain =
        Reporter.createWithDefaults("123ABC1235", true, address, reporter, "62", (short) 1828);

    gov.ca.cwds.data.persistence.cms.Reporter savedReporter =
        new gov.ca.cwds.data.persistence.cms.Reporter(reporterDomain, "0X5");

    gov.ca.cwds.rest.api.domain.cms.CrossReport cmsCrossReport =
        gov.ca.cwds.rest.api.domain.cms.CrossReport.createWithDefaults("123ABC1K35", crossReport,
            "123ABC1235", DEFAULT_STAFF_PERSON_ID, DEFAULT_COUNTY_SPECIFIC_CODE, false);

    gov.ca.cwds.data.persistence.cms.CrossReport savedCrossReport =
        new gov.ca.cwds.data.persistence.cms.CrossReport("123ABp1235", cmsCrossReport, "0X5");

    when(referralDao.find(any(String.class))).thenReturn(savedReferral);
    when(clientDao.find(any(String.class))).thenReturn(savedClient);
    when(allegationDao.find(any(String.class))).thenReturn(savedAllegation);
    when(reporterDao.find(any(String.class))).thenReturn(savedReporter);
    when(crossReportDao.find(any(String.class))).thenReturn(savedCrossReport);
    Reminders reminders = new Reminders(clientDao, referralDao, allegationDao, reporterDao,
        crossReportDao, tickleService);

    reminders.createTickle(postedScreeningToReferral);
    verify(tickleService, times(0)).create(any());
  }

  /**
   * Test for only crossReport Law Enforcement reminer if the reporter is Non-mandated Reporter
   * 
   * @throws Exception - exception
   */
  @Test
  public void testForCrossReportForLawEnforcmentDueReminder() throws Exception {
    Participant victim =
        new ParticipantResourceBuilder().setDateOfBirth("1987-06-18").createVictimParticipant();
    Participant perp =
        new ParticipantResourceBuilder().setDateOfBirth("1987-06-18").createPerpParticipant();

    Participant reporter = new ParticipantResourceBuilder().createReporterParticipant();
    Set<Participant> participants = new HashSet<>(Arrays.asList(perp, victim, reporter));
    CrossReport crossReport = new CrossReportResourceBuilder().createCrossReport();
    Allegation allegation = new AllegationResourceBuilder().createAllegation();
    Set<CrossReport> crossReports = new HashSet<>(Arrays.asList(crossReport));
    Set<Allegation> allegations = new HashSet<>(Arrays.asList(allegation));
    ScreeningToReferral referral = new ScreeningToReferralResourceBuilder()
        .setParticipants(participants).createScreeningToReferral();
    PostedScreeningToReferral postedScreeningToReferral = PostedScreeningToReferral
        .createWithDefaults("123ABC1234", referral, participants, crossReports, allegations);

    Referral domainReferral =
        new MockedScreeningToReferralServiceBuilder().createScreeningToReferralService()
            .createReferralWithDefaults(referral, "2016-09-02", "13:00:00", new Date());

    gov.ca.cwds.data.persistence.cms.Referral savedReferral =
        new gov.ca.cwds.data.persistence.cms.Referral("123ABC1235", domainReferral, "0X5");

    Client client = Client.createWithDefaults(victim, "2016-09-02", "");
    gov.ca.cwds.data.persistence.cms.Client savedClient =
        new gov.ca.cwds.data.persistence.cms.Client("ABC1234567", client, "0X5");

    gov.ca.cwds.rest.api.domain.cms.Allegation cmsAllegation =
        new gov.ca.cwds.rest.api.domain.cms.Allegation("", DEFAULT_CODE, "",
            referral.getLocationType(), "", DEFAULT_CODE, (short) 0, referral.getReportNarrative(),
            "", false, DEFAULT_NON_PROTECTING_PARENT_CODE, false, "ABC1234568", "ABC1234560",
            "123ABC1235", DEFAULT_COUNTY_SPECIFIC_CODE, false, DEFAULT_CODE);

    gov.ca.cwds.data.persistence.cms.Allegation savedAllegation =
        new gov.ca.cwds.data.persistence.cms.Allegation("123ABC1236", cmsAllegation, "0X5");

    Address address = new AddressResourceBuilder().createAddress();

    Reporter reporterDomain =
        Reporter.createWithDefaults("123ABC1235", false, address, reporter, "62", (short) 1828);

    gov.ca.cwds.data.persistence.cms.Reporter savedReporter =
        new gov.ca.cwds.data.persistence.cms.Reporter(reporterDomain, "0X5");

    gov.ca.cwds.rest.api.domain.cms.CrossReport cmsCrossReport =
        gov.ca.cwds.rest.api.domain.cms.CrossReport.createWithDefaults("123ABC1K35", crossReport,
            "123ABC1235", DEFAULT_STAFF_PERSON_ID, DEFAULT_COUNTY_SPECIFIC_CODE, false);

    gov.ca.cwds.data.persistence.cms.CrossReport savedCrossReport =
        new gov.ca.cwds.data.persistence.cms.CrossReport("123ABp1235", cmsCrossReport, "0X5");

    when(referralDao.find(any(String.class))).thenReturn(savedReferral);
    when(clientDao.find(any(String.class))).thenReturn(savedClient);
    when(allegationDao.find(any(String.class))).thenReturn(savedAllegation);
    when(reporterDao.find(any(String.class))).thenReturn(savedReporter);
    when(crossReportDao.find(any(String.class))).thenReturn(savedCrossReport);
    Reminders reminders = new Reminders(clientDao, referralDao, allegationDao, reporterDao,
        crossReportDao, tickleService);

    reminders.createTickle(postedScreeningToReferral);
    verify(tickleService, times(1)).create(any());
  }

}
