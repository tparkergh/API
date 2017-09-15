package gov.ca.cwds.rest.api.domain.investigation;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.util.HashSet;
import java.util.Set;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

import org.junit.Test;

@SuppressWarnings("javadoc")
public class SimpleReferralTest {

  private String victimFirstName = "sharon";
  private String perpetratorLastName = "w.";
  private String perpetratorFirstName = "ricky";
  private String dispositionDescription = "substantiated";
  private String victimLastName = "w.";
  private String allegationDescription = "sexual abuse";
  private String legacyUiId = "0762-2283-8000-4000739";
  private String limitedAccessGovernmentEntityId = "0";
  private String limitedAccessCode = "n";
  private String endDate = "2000-02-01";
  private Set<Allegation> allegations = validAllegations();
  private SimplePerson assignedSocialWorker = validSocialWorker();
  private LimitedAccess accessLimitation = validLimitedAccess();
  private SimpleLegacyDescriptor legacyDescriptor = validLegacyDescriptor();
  private SimplePerson reporter = validReporter();
  private String responseTime = "immediate";
  private String countyName = "Plumas";
  private String responseTimeId = "1520";
  private String startDate = "1999-02-28";

  @Test
  public void equalsHashCodeWork() {
    EqualsVerifier.forClass(ScreeningSummary.class).suppress(Warning.NONFINAL_FIELDS).verify();

  }

  @Test
  public void jsonCreatorConstructorTest() throws Exception {
    SimpleReferral domain =
        new SimpleReferral(endDate, legacyDescriptor, reporter, countyName, responseTimeId,
            allegations, assignedSocialWorker, accessLimitation, responseTime, startDate);
    assertThat(domain.getEndDate(), is(equalTo(endDate)));
    assertThat(domain.getLegacyDescriptor(), is(equalTo(legacyDescriptor)));
    assertThat(domain.getReporter(), is(equalTo(reporter)));
    assertThat(domain.getCountyName(), is(equalTo(countyName)));
    assertThat(domain.getResponseTimeId(), is(equalTo(responseTimeId)));
    assertThat(domain.getAllegations(), is(equalTo(allegations)));
    assertThat(domain.getAssignedSocialWorker(), is(equalTo(assignedSocialWorker)));
    assertThat(domain.getAccessLimitation(), is(equalTo(accessLimitation)));
    assertThat(domain.getResponseTime(), is(equalTo(responseTime)));
    assertThat(domain.getStartDate(), is(equalTo(startDate)));
  }

  private Set<Allegation> validAllegations() {
    Set<Allegation> validAllegations = new HashSet<Allegation>();
    Allegation allegation =
        new Allegation(victimLastName, victimFirstName, perpetratorLastName, perpetratorFirstName,
            dispositionDescription, allegationDescription);
    validAllegations.add(allegation);
    return validAllegations;
  }

  private SimplePerson validReporter() {
    return new SimplePerson("One", "Reporter", "R");
  }

  private SimpleLegacyDescriptor validLegacyDescriptor() {
    return new SimpleLegacyDescriptor(legacyUiId);
  }

  private LimitedAccess validLimitedAccess() {
    return new LimitedAccess(limitedAccessGovernmentEntityId, limitedAccessCode);
  }

  private SimplePerson validSocialWorker() {
    return new SimplePerson("Joe", "Wilson", "R");
  }


}
