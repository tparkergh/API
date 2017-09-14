package gov.ca.cwds.rest.api.domain.investigation;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.util.Set;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

import org.junit.Test;

@SuppressWarnings("javadoc")
public class HistoryOfInvolvementTest {

  private Set<Case> cases = null;
  private Set<SimpleScreening> screenings = null;
  private Set<SimpleReferral> referrals = null;

  @Test
  public void equalsHashCodeWork() {
    EqualsVerifier.forClass(HistoryOfInvolvement.class).suppress(Warning.NONFINAL_FIELDS).verify();
  }

  @Test
  public void jsonCreatorConstructorTest() throws Exception {
    HistoryOfInvolvement domain = new HistoryOfInvolvement(cases, referrals, screenings);

    assertThat(domain.getCases(), is(equalTo(cases)));
    assertThat(domain.getReferrals(), is(equalTo(referrals)));
    assertThat(domain.getScreenings(), is(equalTo(screenings)));

  }
}
