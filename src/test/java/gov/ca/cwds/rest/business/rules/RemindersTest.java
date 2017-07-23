package gov.ca.cwds.rest.business.rules;

import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

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
}
