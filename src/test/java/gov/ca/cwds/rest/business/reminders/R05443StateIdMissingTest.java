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
import gov.ca.cwds.data.cms.ReferralDao;
import gov.ca.cwds.data.cms.ReporterDao;
import gov.ca.cwds.fixture.AddressResourceBuilder;
import gov.ca.cwds.fixture.AllegationResourceBuilder;
import gov.ca.cwds.fixture.ClientResourceBuilder;
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
public class R05443StateIdMissingTest {

  private TickleService tickleService;
  private ClientDao clientDao;
  private ReferralDao referralDao;
  private AllegationDao allegationDao;
  private ReporterDao reporterDao;

  private static final short DEFAULT_CODE = 0;
  private static final String DEFAULT_NON_PROTECTING_PARENT_CODE = "U";
  private static final String DEFAULT_COUNTY_SPECIFIC_CODE = "62";
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

    Referral domainReferral = new ReferralResourceBuilder().setReceivedDate("2016-09-02")
        .setReceivedTime("13:00:00").build();

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
        Reporter.createWithDefaults("123ABC1235", false, address, reporter, "62");

    gov.ca.cwds.data.persistence.cms.Reporter savedReporter =
        new gov.ca.cwds.data.persistence.cms.Reporter(reporterDomain, "0X5");

    when(referralDao.find(any(String.class))).thenReturn(savedReferral);
    when(clientDao.find(any(String.class))).thenReturn(savedClient);
    when(allegationDao.find(any(String.class))).thenReturn(savedAllegation);
    when(reporterDao.find(any(String.class))).thenReturn(savedReporter);
    R05443StateIdMissing r05443StateIdMissing =
        new R05443StateIdMissing(clientDao, referralDao, tickleService);

    r05443StateIdMissing.stateIdMissing(postedScreeningToReferral);
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

    Referral domainReferral = new ReferralResourceBuilder().setReceivedDate("2016-09-02")
        .setReceivedTime("13:00:00").build();

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
        Reporter.createWithDefaults("123ABC1235", false, address, reporter, "62");

    gov.ca.cwds.data.persistence.cms.Reporter savedReporter =
        new gov.ca.cwds.data.persistence.cms.Reporter(reporterDomain, "0X5");

    when(referralDao.find(any(String.class))).thenReturn(savedReferral);
    when(clientDao.find(any(String.class))).thenReturn(savedClient);
    when(allegationDao.find(any(String.class))).thenReturn(savedAllegation);
    when(reporterDao.find(any(String.class))).thenReturn(savedReporter);
    R05443StateIdMissing r05443StateIdMissing =
        new R05443StateIdMissing(clientDao, referralDao, tickleService);

    r05443StateIdMissing.stateIdMissing(postedScreeningToReferral);
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

    Referral domainReferral = new ReferralResourceBuilder().setReceivedDate("2016-09-02")
        .setReceivedTime("13:00:00").build();

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
        Reporter.createWithDefaults("123ABC1235", false, address, reporter, "62");

    gov.ca.cwds.data.persistence.cms.Reporter savedReporter =
        new gov.ca.cwds.data.persistence.cms.Reporter(reporterDomain, "0X5");

    when(referralDao.find(any(String.class))).thenReturn(savedReferral);
    when(clientDao.find(any(String.class))).thenReturn(savedClient);
    when(allegationDao.find(any(String.class))).thenReturn(savedAllegation);
    when(reporterDao.find(any(String.class))).thenReturn(savedReporter);
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
   * @throws Exception - ParseException
   */
  @Test
  public void testForStateIdMissingDateOfBirthParseException() throws Exception {
    Participant victim =
        new ParticipantResourceBuilder().setDateOfBirth("1986-06-18").createVictimParticipant();
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
        Reporter.createWithDefaults("123ABC1235", false, address, reporter, "62");

    gov.ca.cwds.data.persistence.cms.Reporter savedReporter =
        new gov.ca.cwds.data.persistence.cms.Reporter(reporterDomain, "0X5");


    when(referralDao.find(any(String.class))).thenReturn(savedReferral);
    when(clientDao.find(any(String.class))).thenReturn(savedClient);
    when(allegationDao.find(any(String.class))).thenReturn(savedAllegation);
    when(reporterDao.find(any(String.class))).thenReturn(savedReporter);

    R05443StateIdMissing r05443StateIdMissing =
        new R05443StateIdMissing(clientDao, referralDao, tickleService);

    r05443StateIdMissing.stateIdMissing(postedScreeningToReferral);
    verify(tickleService, times(1)).create(any());
  }

  /**
   * Test for no remindes created when Client age is above 26
   * 
   * @throws Exception - exception
   */
  @Test
  public void testClientAgeIsAbove26() throws Exception {
    Participant victim =
        new ParticipantResourceBuilder().setDateOfBirth("1986-06-18").createVictimParticipant();
    Participant perp =
        new ParticipantResourceBuilder().setDateOfBirth("1986-06-18").createPerpParticipant();

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

    Client client = new ClientResourceBuilder().build();
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
        Reporter.createWithDefaults("123ABC1235", false, address, reporter, "62");

    gov.ca.cwds.data.persistence.cms.Reporter savedReporter =
        new gov.ca.cwds.data.persistence.cms.Reporter(reporterDomain, "0X5");

    when(referralDao.find(any(String.class))).thenReturn(savedReferral);
    when(clientDao.find(any(String.class))).thenReturn(savedClient);
    when(allegationDao.find(any(String.class))).thenReturn(savedAllegation);
    when(reporterDao.find(any(String.class))).thenReturn(savedReporter);
    R05443StateIdMissing r05443StateIdMissing =
        new R05443StateIdMissing(clientDao, referralDao, tickleService);

    r05443StateIdMissing.stateIdMissing(postedScreeningToReferral);
    verify(tickleService, times(0)).create(any());
  }

}
