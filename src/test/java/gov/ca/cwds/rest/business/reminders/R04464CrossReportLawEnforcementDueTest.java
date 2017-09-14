package gov.ca.cwds.rest.business.reminders;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.fasterxml.jackson.databind.ObjectMapper;

import gov.ca.cwds.data.cms.AllegationDao;
import gov.ca.cwds.data.cms.ClientDao;
import gov.ca.cwds.data.cms.CrossReportDao;
import gov.ca.cwds.data.cms.ReferralDao;
import gov.ca.cwds.data.cms.ReporterDao;
import gov.ca.cwds.fixture.AddressResourceBuilder;
import gov.ca.cwds.fixture.AllegationResourceBuilder;
import gov.ca.cwds.fixture.CrossReportResourceBuilder;
import gov.ca.cwds.fixture.ParticipantResourceBuilder;
import gov.ca.cwds.fixture.ReferralResourceBuilder;
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
import io.dropwizard.jackson.Jackson;

/**
 * @author CWDS API Team
 *
 */
public class R04464CrossReportLawEnforcementDueTest {

  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

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

    Referral domainReferral = new ReferralResourceBuilder().setReceivedDate("2016-09-02")
        .setReceivedTime("13:00:00").build();

    gov.ca.cwds.data.persistence.cms.Referral savedReferral =
        new gov.ca.cwds.data.persistence.cms.Referral("123ABC1235", domainReferral, "0X5");

    Client client = Client.createWithDefaults(victim, "2016-09-02", "", (short) 0);
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
        Reporter.createWithDefaults("123ABC1235", false, address, reporter, "62");

    gov.ca.cwds.data.persistence.cms.Reporter savedReporter =
        new gov.ca.cwds.data.persistence.cms.Reporter(reporterDomain, "0X5");

    gov.ca.cwds.rest.api.domain.cms.CrossReport cmsCrossReport =
        gov.ca.cwds.rest.api.domain.cms.CrossReport.createWithDefaults("123ABC1K35", crossReport,
            "123ABC1235", DEFAULT_STAFF_PERSON_ID, "", DEFAULT_COUNTY_SPECIFIC_CODE, false, false);

    gov.ca.cwds.data.persistence.cms.CrossReport savedCrossReport =
        new gov.ca.cwds.data.persistence.cms.CrossReport("123ABp1235", cmsCrossReport, "0X5");

    when(referralDao.find(any(String.class))).thenReturn(savedReferral);
    when(clientDao.find(any(String.class))).thenReturn(savedClient);
    when(allegationDao.find(any(String.class))).thenReturn(savedAllegation);
    when(reporterDao.find(any(String.class))).thenReturn(savedReporter);
    when(crossReportDao.find(any(String.class))).thenReturn(savedCrossReport);
    R05443StateIdMissing r05443StateIdMissing =
        new R05443StateIdMissing(clientDao, referralDao, tickleService);
    R04464CrossReportLawEnforcementDue r04464CrossReportLawEnforcementDue =
        new R04464CrossReportLawEnforcementDue(referralDao, allegationDao, reporterDao,
            crossReportDao, tickleService);
    R04631ReferralInvestigationContactDue r04631ReferralInvestigationContactDue =
        new R04631ReferralInvestigationContactDue(clientDao, referralDao, tickleService);

    r05443StateIdMissing.stateIdMissing(postedScreeningToReferral);
    r04464CrossReportLawEnforcementDue.crossReportForLawEnforcmentDue(postedScreeningToReferral);
    r04631ReferralInvestigationContactDue
        .referralInvestigationContactDue(postedScreeningToReferral);
    verify(tickleService, times(4)).create(any());
  }

  /**
   * Test for only crossReport Law Enforcement reminder if the reporter is Non-mandated Reporter
   * 
   * @throws Exception - exception
   */
  @Test
  public void testForCrossReportForLawEnforcmentDueReminder() throws Exception {
    Participant victim =
        new ParticipantResourceBuilder().setDateOfBirth("1987-06-18").createVictimParticipant();
    Participant perp =
        new ParticipantResourceBuilder().setDateOfBirth("1987-06-18").createPerpParticipant();

    Participant reporter = new ParticipantResourceBuilder()
        .setRoles(new HashSet<>(Arrays.asList("Non-mandated Reporter")))
        .createReporterParticipant();
    Set<Participant> participants = new HashSet<>(Arrays.asList(perp, victim, reporter));
    CrossReport crossReport = new CrossReportResourceBuilder().createCrossReport();
    Allegation allegation = new AllegationResourceBuilder().createAllegation();
    Set<CrossReport> crossReports = new HashSet<>(Arrays.asList(crossReport));
    Set<Allegation> allegations = new HashSet<>(Arrays.asList(allegation));
    ScreeningToReferral referral = new ScreeningToReferralResourceBuilder()
        .setParticipants(participants).createScreeningToReferral();
    PostedScreeningToReferral postedScreeningToReferral = PostedScreeningToReferral
        .createWithDefaults("123ABC1234", referral, participants, crossReports, allegations);

    Referral domainReferral = new ReferralResourceBuilder().setReceivedDate("2016-09-02")
        .setReceivedTime("13:00:00").build();

    gov.ca.cwds.data.persistence.cms.Referral savedReferral =
        new gov.ca.cwds.data.persistence.cms.Referral("123ABC1235", domainReferral, "0X5");

    Client client = Client.createWithDefaults(victim, "2016-09-02", "", (short) 0);
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
        Reporter.createWithDefaults("123ABC1235", false, address, reporter, "62");

    gov.ca.cwds.data.persistence.cms.Reporter savedReporter =
        new gov.ca.cwds.data.persistence.cms.Reporter(reporterDomain, "0X5");

    gov.ca.cwds.rest.api.domain.cms.CrossReport cmsCrossReport =
        gov.ca.cwds.rest.api.domain.cms.CrossReport.createWithDefaults("123ABC1K35", crossReport,
            "123ABC1235", DEFAULT_STAFF_PERSON_ID, "", DEFAULT_COUNTY_SPECIFIC_CODE, false, false);

    gov.ca.cwds.data.persistence.cms.CrossReport savedCrossReport =
        new gov.ca.cwds.data.persistence.cms.CrossReport("123ABp1235", cmsCrossReport, "0X5");

    when(referralDao.find(any(String.class))).thenReturn(savedReferral);
    when(clientDao.find(any(String.class))).thenReturn(savedClient);
    when(allegationDao.find(any(String.class))).thenReturn(savedAllegation);
    when(reporterDao.find(any(String.class))).thenReturn(savedReporter);
    when(crossReportDao.find(any(String.class))).thenReturn(savedCrossReport);
    R04464CrossReportLawEnforcementDue r04464CrossReportLawEnforcementDue =
        new R04464CrossReportLawEnforcementDue(referralDao, allegationDao, reporterDao,
            crossReportDao, tickleService);

    r04464CrossReportLawEnforcementDue.crossReportForLawEnforcmentDue(postedScreeningToReferral);
    verify(tickleService, times(1)).create(any());
  }

  /**
   * Test for crossReport Law Enforcement reminder not created if the reporter is a Mandated
   * Reporter
   * 
   * @throws Exception - exception
   */
  @Test
  public void testForCrossReportForLawEnforcmentDueReminderNotCreated() throws Exception {
    Participant victim =
        new ParticipantResourceBuilder().setDateOfBirth("1992-06-18").createVictimParticipant();
    Participant perp = new ParticipantResourceBuilder().createPerpParticipant();

    Participant reporter = new ParticipantResourceBuilder()
        .setRoles(new HashSet<>(Arrays.asList("Mandated Reporter"))).createReporterParticipant();
    Set<Participant> participants = new HashSet<>(Arrays.asList(perp, victim, reporter));
    CrossReport crossReport = new CrossReportResourceBuilder().createCrossReport();
    Allegation allegation = new AllegationResourceBuilder().createAllegation();
    Set<CrossReport> crossReports = new HashSet<>(Arrays.asList(crossReport));
    Set<Allegation> allegations = new HashSet<>(Arrays.asList(allegation));
    ScreeningToReferral referral = new ScreeningToReferralResourceBuilder()
        .setParticipants(participants).createScreeningToReferral();
    PostedScreeningToReferral postedScreeningToReferral = PostedScreeningToReferral
        .createWithDefaults("123ABC1234", referral, participants, crossReports, allegations);

    Referral domainReferral = new ReferralResourceBuilder().setReceivedDate("2016-09-02")
        .setReceivedTime("13:00:00").build();

    gov.ca.cwds.data.persistence.cms.Referral savedReferral =
        new gov.ca.cwds.data.persistence.cms.Referral("123ABC1235", domainReferral, "0X5");

    Client client = Client.createWithDefaults(victim, "2016-09-02", "", (short) 0);
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
        Reporter.createWithDefaults("123ABC1235", true, address, reporter, "62");

    gov.ca.cwds.data.persistence.cms.Reporter savedReporter =
        new gov.ca.cwds.data.persistence.cms.Reporter(reporterDomain, "0X5");

    gov.ca.cwds.rest.api.domain.cms.CrossReport cmsCrossReport =
        gov.ca.cwds.rest.api.domain.cms.CrossReport.createWithDefaults("123ABC1K35", crossReport,
            "123ABC1235", DEFAULT_STAFF_PERSON_ID, "", DEFAULT_COUNTY_SPECIFIC_CODE, false, false);

    gov.ca.cwds.data.persistence.cms.CrossReport savedCrossReport =
        new gov.ca.cwds.data.persistence.cms.CrossReport("123ABp1235", cmsCrossReport, "0X5");

    when(referralDao.find(any(String.class))).thenReturn(savedReferral);
    when(clientDao.find(any(String.class))).thenReturn(savedClient);
    when(allegationDao.find(any(String.class))).thenReturn(savedAllegation);
    when(reporterDao.find(any(String.class))).thenReturn(savedReporter);
    when(crossReportDao.find(any(String.class))).thenReturn(savedCrossReport);
    R05443StateIdMissing r05443StateIdMissing =
        new R05443StateIdMissing(clientDao, referralDao, tickleService);
    R04464CrossReportLawEnforcementDue r04464CrossReportLawEnforcementDue =
        new R04464CrossReportLawEnforcementDue(referralDao, allegationDao, reporterDao,
            crossReportDao, tickleService);
    R04631ReferralInvestigationContactDue r04631ReferralInvestigationContactDue =
        new R04631ReferralInvestigationContactDue(clientDao, referralDao, tickleService);

    r05443StateIdMissing.stateIdMissing(postedScreeningToReferral);
    r04464CrossReportLawEnforcementDue.crossReportForLawEnforcmentDue(postedScreeningToReferral);
    r04631ReferralInvestigationContactDue
        .referralInvestigationContactDue(postedScreeningToReferral);
    verify(tickleService, times(3)).create(any());
  }

  /**
   * Test for the crossReport Law Enforcement reminder create when the referral closure date is null
   * 
   * @throws Exception - exception
   */
  @Test
  public void testForCrossReportForLawEnforcmentDueReminderCreatedWhenClosureDateNull()
      throws Exception {
    Participant victim =
        new ParticipantResourceBuilder().setDateOfBirth("1992-06-18").createVictimParticipant();
    Participant perp = new ParticipantResourceBuilder().createPerpParticipant();

    Participant reporter = new ParticipantResourceBuilder()
        .setRoles(new HashSet<>(Arrays.asList("Non-mandated Reporter")))
        .createReporterParticipant();
    Set<Participant> participants = new HashSet<>(Arrays.asList(perp, victim, reporter));
    CrossReport crossReport = new CrossReportResourceBuilder().createCrossReport();
    Allegation allegation = new AllegationResourceBuilder().createAllegation();
    Set<CrossReport> crossReports = new HashSet<>(Arrays.asList(crossReport));
    Set<Allegation> allegations = new HashSet<>(Arrays.asList(allegation));
    ScreeningToReferral referral = new ScreeningToReferralResourceBuilder()
        .setParticipants(participants).createScreeningToReferral();
    PostedScreeningToReferral postedScreeningToReferral = PostedScreeningToReferral
        .createWithDefaults("123ABC1234", referral, participants, crossReports, allegations);

    Referral domainReferral = new ReferralResourceBuilder().setReceivedDate("2016-09-02")
        .setReceivedTime("13:00:00").setClosureDate(null).build();

    gov.ca.cwds.data.persistence.cms.Referral savedReferral =
        new gov.ca.cwds.data.persistence.cms.Referral("123ABC1235", domainReferral, "0X5");

    Client client = Client.createWithDefaults(victim, "2016-09-02", "", (short) 0);
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
        Reporter.createWithDefaults("123ABC1235", false, address, reporter, "62");

    gov.ca.cwds.data.persistence.cms.Reporter savedReporter =
        new gov.ca.cwds.data.persistence.cms.Reporter(reporterDomain, "0X5");

    gov.ca.cwds.rest.api.domain.cms.CrossReport cmsCrossReport =
        gov.ca.cwds.rest.api.domain.cms.CrossReport.createWithDefaults("123ABC1K35", crossReport,
            "123ABC1235", DEFAULT_STAFF_PERSON_ID, "", DEFAULT_COUNTY_SPECIFIC_CODE, false, false);

    gov.ca.cwds.data.persistence.cms.CrossReport savedCrossReport =
        new gov.ca.cwds.data.persistence.cms.CrossReport("123ABp1235", cmsCrossReport, "0X5");

    when(referralDao.find(any(String.class))).thenReturn(savedReferral);
    when(clientDao.find(any(String.class))).thenReturn(savedClient);
    when(allegationDao.find(any(String.class))).thenReturn(savedAllegation);
    when(reporterDao.find(any(String.class))).thenReturn(savedReporter);
    when(crossReportDao.find(any(String.class))).thenReturn(savedCrossReport);
    R05443StateIdMissing r05443StateIdMissing =
        new R05443StateIdMissing(clientDao, referralDao, tickleService);
    R04464CrossReportLawEnforcementDue r04464CrossReportLawEnforcementDue =
        new R04464CrossReportLawEnforcementDue(referralDao, allegationDao, reporterDao,
            crossReportDao, tickleService);
    R04631ReferralInvestigationContactDue r04631ReferralInvestigationContactDue =
        new R04631ReferralInvestigationContactDue(clientDao, referralDao, tickleService);

    r05443StateIdMissing.stateIdMissing(postedScreeningToReferral);
    r04464CrossReportLawEnforcementDue.crossReportForLawEnforcmentDue(postedScreeningToReferral);
    r04631ReferralInvestigationContactDue
        .referralInvestigationContactDue(postedScreeningToReferral);
    verify(tickleService, times(4)).create(any());
  }

  /**
   * Test for the crossReport Law Enforcement reminder not create when the referral closure date is
   * not null
   * 
   * @throws Exception - exception
   */
  @Test
  public void testForCrossReportForLawEnforcmentDueReminderNotCreatedWhenClosureDateIsNotNull()
      throws Exception {
    Participant victim =
        new ParticipantResourceBuilder().setDateOfBirth("1992-06-18").createVictimParticipant();
    Participant perp = new ParticipantResourceBuilder().createPerpParticipant();

    Participant reporter = new ParticipantResourceBuilder()
        .setRoles(new HashSet<>(Arrays.asList("Non-mandated Reporter")))
        .createReporterParticipant();
    Set<Participant> participants = new HashSet<>(Arrays.asList(perp, victim, reporter));
    CrossReport crossReport = new CrossReportResourceBuilder().createCrossReport();
    Allegation allegation = new AllegationResourceBuilder().createAllegation();
    Set<CrossReport> crossReports = new HashSet<>(Arrays.asList(crossReport));
    Set<Allegation> allegations = new HashSet<>(Arrays.asList(allegation));
    ScreeningToReferral referral = new ScreeningToReferralResourceBuilder()
        .setParticipants(participants).createScreeningToReferral();
    PostedScreeningToReferral postedScreeningToReferral = PostedScreeningToReferral
        .createWithDefaults("123ABC1234", referral, participants, crossReports, allegations);

    Referral domainReferral = new ReferralResourceBuilder().setReceivedDate("2016-09-02")
        .setReceivedTime("13:00:00").setClosureDate("2000-01-01").build();

    gov.ca.cwds.data.persistence.cms.Referral savedReferral =
        new gov.ca.cwds.data.persistence.cms.Referral("123ABC1235", domainReferral, "0X5");

    Client client = Client.createWithDefaults(victim, "2016-09-02", "", (short) 0);
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
        Reporter.createWithDefaults("123ABC1235", false, address, reporter, "62");

    gov.ca.cwds.data.persistence.cms.Reporter savedReporter =
        new gov.ca.cwds.data.persistence.cms.Reporter(reporterDomain, "0X5");

    gov.ca.cwds.rest.api.domain.cms.CrossReport cmsCrossReport =
        gov.ca.cwds.rest.api.domain.cms.CrossReport.createWithDefaults("123ABC1K35", crossReport,
            "123ABC1235", DEFAULT_STAFF_PERSON_ID, "", DEFAULT_COUNTY_SPECIFIC_CODE, false, false);

    gov.ca.cwds.data.persistence.cms.CrossReport savedCrossReport =
        new gov.ca.cwds.data.persistence.cms.CrossReport("123ABp1235", cmsCrossReport, "0X5");

    when(referralDao.find(any(String.class))).thenReturn(savedReferral);
    when(clientDao.find(any(String.class))).thenReturn(savedClient);
    when(allegationDao.find(any(String.class))).thenReturn(savedAllegation);
    when(reporterDao.find(any(String.class))).thenReturn(savedReporter);
    when(crossReportDao.find(any(String.class))).thenReturn(savedCrossReport);
    R05443StateIdMissing r05443StateIdMissing =
        new R05443StateIdMissing(clientDao, referralDao, tickleService);
    R04464CrossReportLawEnforcementDue r04464CrossReportLawEnforcementDue =
        new R04464CrossReportLawEnforcementDue(referralDao, allegationDao, reporterDao,
            crossReportDao, tickleService);
    R04631ReferralInvestigationContactDue r04631ReferralInvestigationContactDue =
        new R04631ReferralInvestigationContactDue(clientDao, referralDao, tickleService);

    r05443StateIdMissing.stateIdMissing(postedScreeningToReferral);
    r04464CrossReportLawEnforcementDue.crossReportForLawEnforcmentDue(postedScreeningToReferral);
    r04631ReferralInvestigationContactDue
        .referralInvestigationContactDue(postedScreeningToReferral);
    verify(tickleService, times(3)).create(any());
  }

  /**
   * Test for reminder not created when allegation type is General Neglect
   * 
   * @throws Exception - exception
   */
  @Test
  public void reminderNotCreatedWhenAllegationTypeIsGeneralNeglect() throws Exception {
    Participant victim =
        new ParticipantResourceBuilder().setDateOfBirth("1992-06-18").createVictimParticipant();
    Participant perp = new ParticipantResourceBuilder().createPerpParticipant();

    Participant reporter = new ParticipantResourceBuilder()
        .setRoles(new HashSet<>(Arrays.asList("Non-mandated Reporter")))
        .createReporterParticipant();
    Set<Participant> participants = new HashSet<>(Arrays.asList(perp, victim, reporter));
    CrossReport crossReport = new CrossReportResourceBuilder().createCrossReport();
    Allegation allegation = new AllegationResourceBuilder().createAllegation();
    Set<CrossReport> crossReports = new HashSet<>(Arrays.asList(crossReport));
    Set<Allegation> allegations = new HashSet<>(Arrays.asList(allegation));
    ScreeningToReferral referral = new ScreeningToReferralResourceBuilder()
        .setParticipants(participants).createScreeningToReferral();
    PostedScreeningToReferral postedScreeningToReferral = PostedScreeningToReferral
        .createWithDefaults("123ABC1234", referral, participants, crossReports, allegations);

    Referral domainReferral = new ReferralResourceBuilder().setReceivedDate("2016-09-02")
        .setReceivedTime("13:00:00").build();

    gov.ca.cwds.data.persistence.cms.Referral savedReferral =
        new gov.ca.cwds.data.persistence.cms.Referral("123ABC1235", domainReferral, "0X5");

    Client client = Client.createWithDefaults(victim, "2016-09-02", "", (short) 0);
    gov.ca.cwds.data.persistence.cms.Client savedClient =
        new gov.ca.cwds.data.persistence.cms.Client("ABC1234567", client, "0X5");

    gov.ca.cwds.rest.api.domain.cms.Allegation cmsAllegation =
        new gov.ca.cwds.rest.api.domain.cms.Allegation("", DEFAULT_CODE, "",
            referral.getLocationType(), "", DEFAULT_CODE, (short) 2178,
            referral.getReportNarrative(), "", false, DEFAULT_NON_PROTECTING_PARENT_CODE, false,
            "ABC1234568", "ABC1234560", "123ABC1235", DEFAULT_COUNTY_SPECIFIC_CODE, false,
            DEFAULT_CODE);

    gov.ca.cwds.data.persistence.cms.Allegation savedAllegation =
        new gov.ca.cwds.data.persistence.cms.Allegation("123ABC1236", cmsAllegation, "0X5");

    Address address = new AddressResourceBuilder().createAddress();

    Reporter reporterDomain =
        Reporter.createWithDefaults("123ABC1235", false, address, reporter, "62");

    gov.ca.cwds.data.persistence.cms.Reporter savedReporter =
        new gov.ca.cwds.data.persistence.cms.Reporter(reporterDomain, "0X5");

    gov.ca.cwds.rest.api.domain.cms.CrossReport cmsCrossReport =
        gov.ca.cwds.rest.api.domain.cms.CrossReport.createWithDefaults("123ABC1K35", crossReport,
            "123ABC1235", DEFAULT_STAFF_PERSON_ID, "", DEFAULT_COUNTY_SPECIFIC_CODE, false, false);

    gov.ca.cwds.data.persistence.cms.CrossReport savedCrossReport =
        new gov.ca.cwds.data.persistence.cms.CrossReport("123ABp1235", cmsCrossReport, "0X5");

    when(referralDao.find(any(String.class))).thenReturn(savedReferral);
    when(clientDao.find(any(String.class))).thenReturn(savedClient);
    when(allegationDao.find(any(String.class))).thenReturn(savedAllegation);
    when(reporterDao.find(any(String.class))).thenReturn(savedReporter);
    when(crossReportDao.find(any(String.class))).thenReturn(savedCrossReport);
    R05443StateIdMissing r05443StateIdMissing =
        new R05443StateIdMissing(clientDao, referralDao, tickleService);
    R04464CrossReportLawEnforcementDue r04464CrossReportLawEnforcementDue =
        new R04464CrossReportLawEnforcementDue(referralDao, allegationDao, reporterDao,
            crossReportDao, tickleService);
    R04631ReferralInvestigationContactDue r04631ReferralInvestigationContactDue =
        new R04631ReferralInvestigationContactDue(clientDao, referralDao, tickleService);

    r05443StateIdMissing.stateIdMissing(postedScreeningToReferral);
    r04464CrossReportLawEnforcementDue.crossReportForLawEnforcmentDue(postedScreeningToReferral);
    r04631ReferralInvestigationContactDue
        .referralInvestigationContactDue(postedScreeningToReferral);
    verify(tickleService, times(3)).create(any());
  }

  /**
   * Test for reminder not created when allegation type is SubstantialAbuse
   * 
   * @throws Exception - exception
   */
  @Test
  public void reminderNotCreatedWhenAllegationTypeIsSubstantialAbuse() throws Exception {
    Participant victim =
        new ParticipantResourceBuilder().setDateOfBirth("1992-06-18").createVictimParticipant();
    Participant perp = new ParticipantResourceBuilder().createPerpParticipant();

    Participant reporter = new ParticipantResourceBuilder()
        .setRoles(new HashSet<>(Arrays.asList("Non-mandated Reporter")))
        .createReporterParticipant();
    Set<Participant> participants = new HashSet<>(Arrays.asList(perp, victim, reporter));
    CrossReport crossReport = new CrossReportResourceBuilder().createCrossReport();
    Allegation allegation = new AllegationResourceBuilder().createAllegation();
    Set<CrossReport> crossReports = new HashSet<>(Arrays.asList(crossReport));
    Set<Allegation> allegations = new HashSet<>(Arrays.asList(allegation));
    ScreeningToReferral referral = new ScreeningToReferralResourceBuilder()
        .setParticipants(participants).createScreeningToReferral();
    PostedScreeningToReferral postedScreeningToReferral = PostedScreeningToReferral
        .createWithDefaults("123ABC1234", referral, participants, crossReports, allegations);

    Referral domainReferral = new ReferralResourceBuilder().setReceivedDate("2016-09-02")
        .setReceivedTime("13:00:00").build();

    gov.ca.cwds.data.persistence.cms.Referral savedReferral =
        new gov.ca.cwds.data.persistence.cms.Referral("123ABC1235", domainReferral, "0X5");

    Client client = Client.createWithDefaults(victim, "2016-09-02", "", (short) 0);
    gov.ca.cwds.data.persistence.cms.Client savedClient =
        new gov.ca.cwds.data.persistence.cms.Client("ABC1234567", client, "0X5");

    gov.ca.cwds.rest.api.domain.cms.Allegation cmsAllegation =
        new gov.ca.cwds.rest.api.domain.cms.Allegation("", DEFAULT_CODE, "",
            referral.getLocationType(), "", DEFAULT_CODE, (short) 5624,
            referral.getReportNarrative(), "", false, DEFAULT_NON_PROTECTING_PARENT_CODE, false,
            "ABC1234568", "ABC1234560", "123ABC1235", DEFAULT_COUNTY_SPECIFIC_CODE, false,
            DEFAULT_CODE);

    gov.ca.cwds.data.persistence.cms.Allegation savedAllegation =
        new gov.ca.cwds.data.persistence.cms.Allegation("123ABC1236", cmsAllegation, "0X5");

    Address address = new AddressResourceBuilder().createAddress();

    Reporter reporterDomain =
        Reporter.createWithDefaults("123ABC1235", false, address, reporter, "62");

    gov.ca.cwds.data.persistence.cms.Reporter savedReporter =
        new gov.ca.cwds.data.persistence.cms.Reporter(reporterDomain, "0X5");

    gov.ca.cwds.rest.api.domain.cms.CrossReport cmsCrossReport =
        gov.ca.cwds.rest.api.domain.cms.CrossReport.createWithDefaults("123ABC1K35", crossReport,
            "123ABC1235", DEFAULT_STAFF_PERSON_ID, "", DEFAULT_COUNTY_SPECIFIC_CODE, false, false);

    gov.ca.cwds.data.persistence.cms.CrossReport savedCrossReport =
        new gov.ca.cwds.data.persistence.cms.CrossReport("123ABp1235", cmsCrossReport, "0X5");

    when(referralDao.find(any(String.class))).thenReturn(savedReferral);
    when(clientDao.find(any(String.class))).thenReturn(savedClient);
    when(allegationDao.find(any(String.class))).thenReturn(savedAllegation);
    when(reporterDao.find(any(String.class))).thenReturn(savedReporter);
    when(crossReportDao.find(any(String.class))).thenReturn(savedCrossReport);
    R05443StateIdMissing r05443StateIdMissing =
        new R05443StateIdMissing(clientDao, referralDao, tickleService);
    R04464CrossReportLawEnforcementDue r04464CrossReportLawEnforcementDue =
        new R04464CrossReportLawEnforcementDue(referralDao, allegationDao, reporterDao,
            crossReportDao, tickleService);
    R04631ReferralInvestigationContactDue r04631ReferralInvestigationContactDue =
        new R04631ReferralInvestigationContactDue(clientDao, referralDao, tickleService);

    r05443StateIdMissing.stateIdMissing(postedScreeningToReferral);
    r04464CrossReportLawEnforcementDue.crossReportForLawEnforcmentDue(postedScreeningToReferral);
    r04631ReferralInvestigationContactDue
        .referralInvestigationContactDue(postedScreeningToReferral);
    verify(tickleService, times(3)).create(any());
  }

  /**
   * Test for reminder not created when allegation disposition type is Entered in Error
   * 
   * @throws Exception - exception
   */
  @Test
  public void reminderNotCreatedWhenAllegationDispositionTypeIsEnteredInError() throws Exception {
    Participant victim =
        new ParticipantResourceBuilder().setDateOfBirth("1992-06-18").createVictimParticipant();
    Participant perp = new ParticipantResourceBuilder().createPerpParticipant();

    Participant reporter = new ParticipantResourceBuilder()
        .setRoles(new HashSet<>(Arrays.asList("Non-mandated Reporter")))
        .createReporterParticipant();
    Set<Participant> participants = new HashSet<>(Arrays.asList(perp, victim, reporter));
    CrossReport crossReport = new CrossReportResourceBuilder().createCrossReport();
    Allegation allegation = new AllegationResourceBuilder().createAllegation();
    Set<CrossReport> crossReports = new HashSet<>(Arrays.asList(crossReport));
    Set<Allegation> allegations = new HashSet<>(Arrays.asList(allegation));
    ScreeningToReferral referral = new ScreeningToReferralResourceBuilder()
        .setParticipants(participants).createScreeningToReferral();
    PostedScreeningToReferral postedScreeningToReferral = PostedScreeningToReferral
        .createWithDefaults("123ABC1234", referral, participants, crossReports, allegations);

    Referral domainReferral = new ReferralResourceBuilder().setReceivedDate("2016-09-02")
        .setReceivedTime("13:00:00").build();

    gov.ca.cwds.data.persistence.cms.Referral savedReferral =
        new gov.ca.cwds.data.persistence.cms.Referral("123ABC1235", domainReferral, "0X5");

    Client client = Client.createWithDefaults(victim, "2016-09-02", "", (short) 0);
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
        Reporter.createWithDefaults("123ABC1235", false, address, reporter, "62");

    gov.ca.cwds.data.persistence.cms.Reporter savedReporter =
        new gov.ca.cwds.data.persistence.cms.Reporter(reporterDomain, "0X5");

    gov.ca.cwds.rest.api.domain.cms.CrossReport cmsCrossReport =
        gov.ca.cwds.rest.api.domain.cms.CrossReport.createWithDefaults("123ABC1K35", crossReport,
            "123ABC1235", DEFAULT_STAFF_PERSON_ID, "", DEFAULT_COUNTY_SPECIFIC_CODE, false, false);

    gov.ca.cwds.data.persistence.cms.CrossReport savedCrossReport =
        new gov.ca.cwds.data.persistence.cms.CrossReport("123ABp1235", cmsCrossReport, "0X5");

    when(referralDao.find(any(String.class))).thenReturn(savedReferral);
    when(clientDao.find(any(String.class))).thenReturn(savedClient);
    when(allegationDao.find(any(String.class))).thenReturn(savedAllegation);
    when(reporterDao.find(any(String.class))).thenReturn(savedReporter);
    when(crossReportDao.find(any(String.class))).thenReturn(savedCrossReport);
    R05443StateIdMissing r05443StateIdMissing =
        new R05443StateIdMissing(clientDao, referralDao, tickleService);
    R04464CrossReportLawEnforcementDue r04464CrossReportLawEnforcementDue =
        new R04464CrossReportLawEnforcementDue(referralDao, allegationDao, reporterDao,
            crossReportDao, tickleService);
    R04631ReferralInvestigationContactDue r04631ReferralInvestigationContactDue =
        new R04631ReferralInvestigationContactDue(clientDao, referralDao, tickleService);

    r05443StateIdMissing.stateIdMissing(postedScreeningToReferral);
    r04464CrossReportLawEnforcementDue.crossReportForLawEnforcmentDue(postedScreeningToReferral);
    r04631ReferralInvestigationContactDue
        .referralInvestigationContactDue(postedScreeningToReferral);
    verify(tickleService, times(3)).create(any());
  }

  /**
   * Test for reminder created when reporter law Enforcement Id is Null
   * 
   * @throws Exception - exception
   */
  @Test
  public void reminderCreatedWhenReporterEnforcementIdNull() throws Exception {
    Participant victim =
        new ParticipantResourceBuilder().setDateOfBirth("1992-06-18").createVictimParticipant();
    Participant perp = new ParticipantResourceBuilder().createPerpParticipant();

    Participant reporter = new ParticipantResourceBuilder()
        .setRoles(new HashSet<>(Arrays.asList("Non-mandated Reporter")))
        .createReporterParticipant();
    Set<Participant> participants = new HashSet<>(Arrays.asList(perp, victim, reporter));
    CrossReport crossReport = new CrossReportResourceBuilder().createCrossReport();
    Allegation allegation = new AllegationResourceBuilder().createAllegation();
    Set<CrossReport> crossReports = new HashSet<>(Arrays.asList(crossReport));
    Set<Allegation> allegations = new HashSet<>(Arrays.asList(allegation));
    ScreeningToReferral referral = new ScreeningToReferralResourceBuilder()
        .setParticipants(participants).createScreeningToReferral();
    PostedScreeningToReferral postedScreeningToReferral = PostedScreeningToReferral
        .createWithDefaults("123ABC1234", referral, participants, crossReports, allegations);

    Referral domainReferral = new ReferralResourceBuilder().setReceivedDate("2016-09-02")
        .setReceivedTime("13:00:00").build();

    gov.ca.cwds.data.persistence.cms.Referral savedReferral =
        new gov.ca.cwds.data.persistence.cms.Referral("123ABC1235", domainReferral, "0X5");

    Client client = Client.createWithDefaults(victim, "2016-09-02", "", (short) 0);
    gov.ca.cwds.data.persistence.cms.Client savedClient =
        new gov.ca.cwds.data.persistence.cms.Client("ABC1234567", client, "0X5");

    gov.ca.cwds.rest.api.domain.cms.Allegation cmsAllegation =
        new gov.ca.cwds.rest.api.domain.cms.Allegation("", DEFAULT_CODE, "",
            referral.getLocationType(), "", DEFAULT_CODE, (short) 0, referral.getReportNarrative(),
            "", false, DEFAULT_NON_PROTECTING_PARENT_CODE, false, "ABC1234568", "ABC1234560",
            "123ABC1235", DEFAULT_COUNTY_SPECIFIC_CODE, false, DEFAULT_CODE);

    gov.ca.cwds.data.persistence.cms.Allegation savedAllegation =
        new gov.ca.cwds.data.persistence.cms.Allegation("123ABC1236", cmsAllegation, "0X5");

    Reporter reporterDomain = MAPPER
        .readValue(fixture("fixtures/domain/legacy/Reporter/valid/valid.json"), Reporter.class);

    gov.ca.cwds.data.persistence.cms.Reporter savedReporter =
        new gov.ca.cwds.data.persistence.cms.Reporter(reporterDomain, "0X5");

    gov.ca.cwds.rest.api.domain.cms.CrossReport cmsCrossReport =
        gov.ca.cwds.rest.api.domain.cms.CrossReport.createWithDefaults("123ABC1K35", crossReport,
            "123ABC1235", DEFAULT_STAFF_PERSON_ID, "", DEFAULT_COUNTY_SPECIFIC_CODE, false, false);

    gov.ca.cwds.data.persistence.cms.CrossReport savedCrossReport =
        new gov.ca.cwds.data.persistence.cms.CrossReport("123ABp1235", cmsCrossReport, "0X5");

    when(referralDao.find(any(String.class))).thenReturn(savedReferral);
    when(clientDao.find(any(String.class))).thenReturn(savedClient);
    when(allegationDao.find(any(String.class))).thenReturn(savedAllegation);
    when(reporterDao.find(any(String.class))).thenReturn(savedReporter);
    when(crossReportDao.find(any(String.class))).thenReturn(savedCrossReport);
    R05443StateIdMissing r05443StateIdMissing =
        new R05443StateIdMissing(clientDao, referralDao, tickleService);
    R04464CrossReportLawEnforcementDue r04464CrossReportLawEnforcementDue =
        new R04464CrossReportLawEnforcementDue(referralDao, allegationDao, reporterDao,
            crossReportDao, tickleService);
    R04631ReferralInvestigationContactDue r04631ReferralInvestigationContactDue =
        new R04631ReferralInvestigationContactDue(clientDao, referralDao, tickleService);

    r05443StateIdMissing.stateIdMissing(postedScreeningToReferral);
    r04464CrossReportLawEnforcementDue.crossReportForLawEnforcmentDue(postedScreeningToReferral);
    r04631ReferralInvestigationContactDue
        .referralInvestigationContactDue(postedScreeningToReferral);
    verify(tickleService, times(4)).create(any());
  }

  /**
   * Test for reminder created when crossReport law Enforcement Id is Null
   * 
   * @throws Exception - exception
   */
  @Test
  public void reminderCreatedWhenCrossReportEnforcementIdNull() throws Exception {
    Participant victim =
        new ParticipantResourceBuilder().setDateOfBirth("1992-06-18").createVictimParticipant();
    Participant perp = new ParticipantResourceBuilder().createPerpParticipant();

    Participant reporter = new ParticipantResourceBuilder()
        .setRoles(new HashSet<>(Arrays.asList("Non-mandated Reporter")))
        .createReporterParticipant();
    Set<Participant> participants = new HashSet<>(Arrays.asList(perp, victim, reporter));
    CrossReport crossReport = new CrossReportResourceBuilder().createCrossReport();
    Allegation allegation = new AllegationResourceBuilder().createAllegation();
    Set<CrossReport> crossReports = new HashSet<>(Arrays.asList(crossReport));
    Set<Allegation> allegations = new HashSet<>(Arrays.asList(allegation));
    ScreeningToReferral referral = new ScreeningToReferralResourceBuilder()
        .setParticipants(participants).createScreeningToReferral();
    PostedScreeningToReferral postedScreeningToReferral = PostedScreeningToReferral
        .createWithDefaults("123ABC1234", referral, participants, crossReports, allegations);

    Referral domainReferral = new ReferralResourceBuilder().setReceivedDate("2016-09-02")
        .setReceivedTime("13:00:00").build();

    gov.ca.cwds.data.persistence.cms.Referral savedReferral =
        new gov.ca.cwds.data.persistence.cms.Referral("123ABC1235", domainReferral, "0X5");

    Client client = Client.createWithDefaults(victim, "2016-09-02", "", (short) 0);
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
        Reporter.createWithDefaults("123ABC1235", false, address, reporter, "62");

    gov.ca.cwds.data.persistence.cms.Reporter savedReporter =
        new gov.ca.cwds.data.persistence.cms.Reporter(reporterDomain, "0X5");

    gov.ca.cwds.rest.api.domain.cms.CrossReport cmsCrossReport = MAPPER.readValue(
        fixture("fixtures/domain/legacy/CrossReport/invalid/lawEnforcementIndicatorNull.json"),
        gov.ca.cwds.rest.api.domain.cms.CrossReport.class);

    gov.ca.cwds.data.persistence.cms.CrossReport savedCrossReport =
        new gov.ca.cwds.data.persistence.cms.CrossReport("123ABp1235", cmsCrossReport, "0X5");

    when(referralDao.find(any(String.class))).thenReturn(savedReferral);
    when(clientDao.find(any(String.class))).thenReturn(savedClient);
    when(allegationDao.find(any(String.class))).thenReturn(savedAllegation);
    when(reporterDao.find(any(String.class))).thenReturn(savedReporter);
    when(crossReportDao.find(any(String.class))).thenReturn(savedCrossReport);
    R05443StateIdMissing r05443StateIdMissing =
        new R05443StateIdMissing(clientDao, referralDao, tickleService);
    R04464CrossReportLawEnforcementDue r04464CrossReportLawEnforcementDue =
        new R04464CrossReportLawEnforcementDue(referralDao, allegationDao, reporterDao,
            crossReportDao, tickleService);
    R04631ReferralInvestigationContactDue r04631ReferralInvestigationContactDue =
        new R04631ReferralInvestigationContactDue(clientDao, referralDao, tickleService);

    r05443StateIdMissing.stateIdMissing(postedScreeningToReferral);
    r04464CrossReportLawEnforcementDue.crossReportForLawEnforcmentDue(postedScreeningToReferral);
    r04631ReferralInvestigationContactDue
        .referralInvestigationContactDue(postedScreeningToReferral);
    verify(tickleService, times(4)).create(any());
  }


  /**
   * Test for reminder not created when crossReport satisfy ind is true or Y
   * 
   * @throws Exception - exception
   */
  @Test
  public void reminderNotCreatedWhenSatisfyCrossReportIndTrue() throws Exception {
    Participant victim =
        new ParticipantResourceBuilder().setDateOfBirth("1992-06-18").createVictimParticipant();
    Participant perp = new ParticipantResourceBuilder().createPerpParticipant();

    Participant reporter = new ParticipantResourceBuilder()
        .setRoles(new HashSet<>(Arrays.asList("Non-mandated Reporter")))
        .createReporterParticipant();
    Set<Participant> participants = new HashSet<>(Arrays.asList(perp, victim, reporter));
    CrossReport crossReport = new CrossReportResourceBuilder().createCrossReport();
    Allegation allegation = new AllegationResourceBuilder().createAllegation();
    Set<CrossReport> crossReports = new HashSet<>(Arrays.asList(crossReport));
    Set<Allegation> allegations = new HashSet<>(Arrays.asList(allegation));
    ScreeningToReferral referral = new ScreeningToReferralResourceBuilder()
        .setParticipants(participants).createScreeningToReferral();
    PostedScreeningToReferral postedScreeningToReferral = PostedScreeningToReferral
        .createWithDefaults("123ABC1234", referral, participants, crossReports, allegations);

    Referral domainReferral = new ReferralResourceBuilder().setReceivedDate("2016-09-02")
        .setReceivedTime("13:00:00").build();

    gov.ca.cwds.data.persistence.cms.Referral savedReferral =
        new gov.ca.cwds.data.persistence.cms.Referral("123ABC1235", domainReferral, "0X5");

    Client client = Client.createWithDefaults(victim, "2016-09-02", "", (short) 0);
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
        Reporter.createWithDefaults("123ABC1235", false, address, reporter, "62");

    gov.ca.cwds.data.persistence.cms.Reporter savedReporter =
        new gov.ca.cwds.data.persistence.cms.Reporter(reporterDomain, "0X5");

    gov.ca.cwds.rest.api.domain.cms.CrossReport cmsCrossReport =
        MAPPER.readValue(fixture("fixtures/domain/legacy/CrossReport/valid/valid.json"),
            gov.ca.cwds.rest.api.domain.cms.CrossReport.class);

    gov.ca.cwds.data.persistence.cms.CrossReport savedCrossReport =
        new gov.ca.cwds.data.persistence.cms.CrossReport("123ABp1235", cmsCrossReport, "0X5");

    when(referralDao.find(any(String.class))).thenReturn(savedReferral);
    when(clientDao.find(any(String.class))).thenReturn(savedClient);
    when(allegationDao.find(any(String.class))).thenReturn(savedAllegation);
    when(reporterDao.find(any(String.class))).thenReturn(savedReporter);
    when(crossReportDao.find(any(String.class))).thenReturn(savedCrossReport);
    R05443StateIdMissing r05443StateIdMissing =
        new R05443StateIdMissing(clientDao, referralDao, tickleService);
    R04464CrossReportLawEnforcementDue r04464CrossReportLawEnforcementDue =
        new R04464CrossReportLawEnforcementDue(referralDao, allegationDao, reporterDao,
            crossReportDao, tickleService);
    R04631ReferralInvestigationContactDue r04631ReferralInvestigationContactDue =
        new R04631ReferralInvestigationContactDue(clientDao, referralDao, tickleService);

    r05443StateIdMissing.stateIdMissing(postedScreeningToReferral);
    r04464CrossReportLawEnforcementDue.crossReportForLawEnforcmentDue(postedScreeningToReferral);
    r04631ReferralInvestigationContactDue
        .referralInvestigationContactDue(postedScreeningToReferral);
    verify(tickleService, times(3)).create(any());
  }

}
