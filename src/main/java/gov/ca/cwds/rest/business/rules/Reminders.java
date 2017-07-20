package gov.ca.cwds.rest.business.rules;

import com.google.inject.Inject;

import gov.ca.cwds.rest.api.domain.PostedScreeningToReferral;
import gov.ca.cwds.rest.business.reminders.R04464CrossReportLawEnforcementDue;
import gov.ca.cwds.rest.business.reminders.R04631ReferralInvestigationContactDue;
import gov.ca.cwds.rest.business.reminders.R05443StateIdMissing;

/**
 * Business layer object to work on Reminders
 * 
 * <p>
 * This class is handling the some of the business rules for creating the reminders when the zippy
 * referral is created.
 * <p>
 * 
 * @author CWDS API Team
 */
public class Reminders {

  private R05443StateIdMissing r05443StateIdMissing;
  private R04464CrossReportLawEnforcementDue r04464CrossReportLawEnforcementDue;
  private R04631ReferralInvestigationContactDue r04631ReferralInvestigationContactDue;

  /**
   * @param r05443StateIdMissing rule 05443 StateIdMissing
   * @param r04464CrossReportLawEnforcementDue rule 04464 CrossReportLawEnforcementDue
   * @param r04631ReferralInvestigationContactDue rule 04631 ReferralInvestigationContactDue
   */
  @Inject
  public Reminders(R05443StateIdMissing r05443StateIdMissing,
      R04464CrossReportLawEnforcementDue r04464CrossReportLawEnforcementDue,
      R04631ReferralInvestigationContactDue r04631ReferralInvestigationContactDue) {
    super();
    this.r05443StateIdMissing = r05443StateIdMissing;
    this.r04464CrossReportLawEnforcementDue = r04464CrossReportLawEnforcementDue;
    this.r04631ReferralInvestigationContactDue = r04631ReferralInvestigationContactDue;
  }

  /**
   * @param postedScreeningToReferral - postedScreeningToReferral
   */
  public void createTickle(PostedScreeningToReferral postedScreeningToReferral) {
    r05443StateIdMissing.stateIdMissing(postedScreeningToReferral);
    r04464CrossReportLawEnforcementDue.crossReportForLawEnforcmentDue(postedScreeningToReferral);
    r04631ReferralInvestigationContactDue
        .referralInvestigationContactDue(postedScreeningToReferral);
  }

}
