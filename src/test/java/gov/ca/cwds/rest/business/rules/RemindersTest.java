package gov.ca.cwds.rest.business.rules;

import static org.mockito.Mockito.mock;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import gov.ca.cwds.fixture.AllegationResourceBuilder;
import gov.ca.cwds.fixture.CrossReportResourceBuilder;
import gov.ca.cwds.fixture.ParticipantResourceBuilder;
import gov.ca.cwds.fixture.ScreeningToReferralResourceBuilder;
import gov.ca.cwds.rest.api.domain.Allegation;
import gov.ca.cwds.rest.api.domain.CrossReport;
import gov.ca.cwds.rest.api.domain.Participant;
import gov.ca.cwds.rest.api.domain.PostedScreeningToReferral;
import gov.ca.cwds.rest.api.domain.ScreeningToReferral;
import gov.ca.cwds.rest.business.reminders.R04464CrossReportLawEnforcementDue;
import gov.ca.cwds.rest.business.reminders.R04631ReferralInvestigationContactDue;
import gov.ca.cwds.rest.business.reminders.R05443StateIdMissing;

/**
 * @author CWDS API Team
 *
 */
public class RemindersTest {

  private R05443StateIdMissing r05443StateIdMissing;
  private R04464CrossReportLawEnforcementDue r04464CrossReportLawEnforcementDue;
  private R04631ReferralInvestigationContactDue r04631ReferralInvestigationContactDue;

  @SuppressWarnings("javadoc")
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  /**
   * @throws Exception general test error
   */
  @Before
  public void setup() throws Exception {
    r05443StateIdMissing = mock(R05443StateIdMissing.class);
    r04464CrossReportLawEnforcementDue = mock(R04464CrossReportLawEnforcementDue.class);
    r04631ReferralInvestigationContactDue = mock(R04631ReferralInvestigationContactDue.class);
  }

  /**
   * 
   */
  @Test
  public void remindersTest() {

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

    Reminders reminders = new Reminders(r05443StateIdMissing, r04464CrossReportLawEnforcementDue,
        r04631ReferralInvestigationContactDue);
    reminders.createTickle(postedScreeningToReferral);
  }

}
