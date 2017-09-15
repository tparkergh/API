package gov.ca.cwds.rest.api.domain.investigation;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.util.Set;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

import org.junit.Test;

@SuppressWarnings("javadoc")
public class SimpleScreeningTest {

  private String endDate = null;
  private String decision = "promote to referral";
  private String serviceName = null;
  private SimplePerson assignedSocialWorker = null;
  private SimplePerson reporter = null;
  private String id = "123";
  private String countyName = "Fresno";
  private Set<SimplePersonWithRoles> allPeople = null;
  private String startDate = "2017-08-31";

  @Test
  public void equalsHashCodeWork() {
    EqualsVerifier.forClass(SimpleScreening.class).suppress(Warning.NONFINAL_FIELDS).verify();
  }

  @Test
  public void jsonCreatorConstructorTest() throws Exception {
    SimpleScreening domain =
        new SimpleScreening(id, endDate, decision, serviceName, reporter, countyName, allPeople,
            assignedSocialWorker, startDate);
    assertThat(domain.getId(), is(equalTo(id)));
    assertThat(domain.getEndDate(), is(equalTo(endDate)));
    assertThat(domain.getDecision(), is(equalTo(decision)));
    assertThat(domain.getServiceName(), is(equalTo(serviceName)));
    assertThat(domain.getReporter(), is(equalTo(reporter)));
    assertThat(domain.getCountyName(), is(equalTo(countyName)));
    assertThat(domain.getAllPeople(), is(equalTo(allPeople)));
    assertThat(domain.getAssignedSocialWorker(), is(equalTo(assignedSocialWorker)));
    assertThat(domain.getStartDate(), is(equalTo(startDate)));
  }

}
