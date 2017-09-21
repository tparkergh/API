package gov.ca.cwds.rest.api.domain.investigation;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import gov.ca.cwds.fixture.investigation.CaseEntityBuilder;
import gov.ca.cwds.fixture.investigation.HistoryOfInvolvementEntityBuilder;
import gov.ca.cwds.fixture.investigation.SimpleReferralEntityBuilder;
import gov.ca.cwds.fixture.investigation.SimpleScreeningEntityBuilder;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

@SuppressWarnings("javadoc")
public class HistoryOfInvolvementTest {

  private Case caze = new CaseEntityBuilder().build();
  private SimpleScreening screening = new SimpleScreeningEntityBuilder().build();
  private SimpleReferral referral = new SimpleReferralEntityBuilder().build();

  @Test
  public void equalsHashCodeWork() {
    EqualsVerifier.forClass(HistoryOfInvolvement.class).suppress(Warning.NONFINAL_FIELDS).verify();
  }

  @Test
  public void shouldCreateObjectWithDefaultConstructor() {
    HistoryOfInvolvement historyOfInvolvement = new HistoryOfInvolvement();
    assertNotNull(historyOfInvolvement);
  }

  @Test
  public void jsonCreatorConstructorTest() throws Exception {
    Set<Case> cases = new HashSet<>();
    cases.add(caze);
    cases.add(caze);

    Set<SimpleScreening> screenings = new HashSet<>();
    screenings.add(screening);

    Set<SimpleReferral> referrals = new HashSet<>();
    referrals.add(referral);

    HistoryOfInvolvement domain = new HistoryOfInvolvement(cases, referrals, screenings);

    assertThat(domain.getCases(), is(equalTo(cases)));
    assertThat(domain.getReferrals(), is(equalTo(referrals)));
    assertThat(domain.getScreenings(), is(equalTo(screenings)));

  }

  @Test
  public void shouldCompareEqualsToObjectWithSameValues() {
    HistoryOfInvolvement historyOfInvolvement = new HistoryOfInvolvementEntityBuilder().build();
    HistoryOfInvolvement otherHistoryOfInvolvement =
        new HistoryOfInvolvementEntityBuilder().build();
    assertEquals(historyOfInvolvement, otherHistoryOfInvolvement);
  }

  @Test
  public void shouldCompareNotEqualsToObjectWithDifferentValues() {
    Set<Case> cases = new HashSet<>();
    cases.add(caze);
    cases.add(caze);

    HistoryOfInvolvement historyOfInvolvement = new HistoryOfInvolvementEntityBuilder().build();
    HistoryOfInvolvement otherHistoryOfInvolvement =
        new HistoryOfInvolvementEntityBuilder().setCases(cases).build();
    assertThat(historyOfInvolvement, is(not(equals(otherHistoryOfInvolvement))));
  }

  @Test
  public void shouldFindSingleItemInHashSetWhenMultipleItemsAddedWithSameValue() {
    HistoryOfInvolvement historyOfInvolvement = new HistoryOfInvolvementEntityBuilder().build();
    HistoryOfInvolvement otherHistoryOfInvolvement =
        new HistoryOfInvolvementEntityBuilder().build();
    Set<HistoryOfInvolvement> items = new HashSet<>();
    items.add(historyOfInvolvement);
    items.add(otherHistoryOfInvolvement);
    assertTrue(items.contains(historyOfInvolvement));
    assertTrue(items.contains(otherHistoryOfInvolvement));
    assertEquals(1, items.size());


  }
}
