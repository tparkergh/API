package gov.ca.cwds.rest.business.reminders;

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

/**
 * @author CWDS API Team
 *
 */
public class R04631ReferralInvestigationContactDueTest {

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
    R04631ReferralInvestigationContactDue r04631ReferralInvestigationContactDue =
        new R04631ReferralInvestigationContactDue(clientDao, referralDao, tickleService);

    r05443StateIdMissing.stateIdMissing(postedScreeningToReferral);
    r04631ReferralInvestigationContactDue
        .referralInvestigationContactDue(postedScreeningToReferral);
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
    verify(tickleService, times(5)).create(any());
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
    verify(tickleService, times(0)).create(any());
  }

  /**
   * @throws Exception - ParseException
   */
  @Test
  public void testForReferralInvestigationDateOfBirthParseException() throws Exception {
    Participant victim =
        new ParticipantResourceBuilder().setDateOfBirth("1992-06-18").createVictimParticipant();
    Participant perp =
        new ParticipantResourceBuilder().setDateOfBirth("1992:06:18").createPerpParticipant();

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

    R04631ReferralInvestigationContactDue r04631ReferralInvestigationContactDue =
        new R04631ReferralInvestigationContactDue(clientDao, referralDao, tickleService);

    r04631ReferralInvestigationContactDue
        .referralInvestigationContactDue(postedScreeningToReferral);
    verify(tickleService, times(1)).create(any());
  }

}
