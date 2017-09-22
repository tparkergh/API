package gov.ca.cwds.fixture.investigation;

import java.util.LinkedHashSet;
import java.util.Set;

import gov.ca.cwds.rest.api.domain.investigation.Case;
import gov.ca.cwds.rest.api.domain.investigation.HistoryOfInvolvement;
import gov.ca.cwds.rest.api.domain.investigation.SimpleReferral;
import gov.ca.cwds.rest.api.domain.investigation.SimpleScreening;

@SuppressWarnings("javadoc")
public class HistoryOfInvolvementEntityBuilder {
  private Case caseInvestigation = new CaseEntityBuilder().build();
  private SimpleReferral referral = new SimpleReferralEntityBuilder().build();
  private SimpleScreening screening = new SimpleScreeningEntityBuilder().build();
  private Set<Case> cases = new LinkedHashSet<>();
  private Set<SimpleReferral> referrals = new LinkedHashSet<>();
  private Set<SimpleScreening> screenings = new LinkedHashSet<>();

  public HistoryOfInvolvement build() {
    cases.add(caseInvestigation);
    referrals.add(referral);
    screenings.add(screening);
    return new HistoryOfInvolvement(cases, referrals, screenings);


  }

  public HistoryOfInvolvementEntityBuilder setCases(Set<Case> cases) {
    this.cases = cases;
    return this;
  }

  public HistoryOfInvolvementEntityBuilder setReferrals(Set<SimpleReferral> referrals) {
    this.referrals = referrals;
    return this;
  }

  public HistoryOfInvolvementEntityBuilder setScreenings(Set<SimpleScreening> screenings) {
    this.screenings = screenings;
    return this;
  }

  public Set<Case> getCases() {
    return cases;
  }

  public Set<SimpleReferral> getReferrals() {
    return referrals;
  }

  public Set<SimpleScreening> getScreenings() {
    return screenings;
  }


}
