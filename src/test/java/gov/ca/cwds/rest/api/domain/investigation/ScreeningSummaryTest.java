package gov.ca.cwds.rest.api.domain.investigation;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import gov.ca.cwds.rest.resources.cms.JerseyGuiceRule;

import java.text.ParseException;
import java.util.HashSet;
import java.util.Set;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

import org.junit.After;
import org.junit.ClassRule;
import org.junit.Test;

import com.squarespace.jersey2.guice.JerseyGuiceUtils;

@SuppressWarnings("javadoc")
public class ScreeningSummaryTest {

  private String additionalInformation = "there was excessive evidence of abuse";
  private String decision = "promoteToReferral";
  private String decisionDetail = "immediate";
  private String name = "henderson screening";
  private Set<String> safetyAlerts = validSafetyAletrs();
  private String startedAt = "2017-09-01t16:48:05.457z";
  private String safetyInformation = "the animal at residence is a lion";
  private String id = "1";

  @After
  public void ensureServiceLocatorPopulated() {
    JerseyGuiceUtils.reset();
  }

  private Set<String> validSafetyAletrs() {
    Set<String> safetyAlerts = new HashSet<String>();
    safetyAlerts.add("dangerous animal on premises");
    safetyAlerts.add("firearms in home");
    safetyAlerts.add("severe mental health status");
    return safetyAlerts;
  }

  @ClassRule
  public static JerseyGuiceRule rule = new JerseyGuiceRule();

  public ScreeningSummaryTest() throws ParseException {}

  @Test
  public void equalsHashCodeWork() {
    EqualsVerifier.forClass(ScreeningSummary.class).suppress(Warning.NONFINAL_FIELDS).verify();

  }

  @Test
  public void jsonCreatorConstructorTest() throws Exception {
    ScreeningSummary domain =
        new ScreeningSummary(id, name, decision, decisionDetail, safetyAlerts, safetyInformation,
            additionalInformation, startedAt, null);
    assertThat(domain.getId(), is(equalTo(id)));
    assertThat(domain.getName(), is(equalTo(name)));
    assertThat(domain.getDecision(), is(equalTo(decision)));
    assertThat(domain.getDecisionDetail(), is(equalTo(decisionDetail)));
    assertThat(domain.getSafetyAlerts(), is(equalTo(safetyAlerts)));
    assertThat(domain.getSafetyInformation(), is(equalTo(safetyInformation)));
    assertThat(domain.getAdditionalInformation(), is(equalTo(additionalInformation)));
    assertThat(domain.getStartedAt(), is(equalTo(startedAt)));
    assertThat(domain.getAllegations(), is(equalTo(null)));
  }

}
