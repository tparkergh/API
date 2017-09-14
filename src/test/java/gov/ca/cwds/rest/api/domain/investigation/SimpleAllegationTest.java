package gov.ca.cwds.rest.api.domain.investigation;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.util.Set;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

import org.junit.Test;

@SuppressWarnings("javadoc")
public class SimpleAllegationTest {

  private String perpetratorId = "11";
  private Set<String> allegationTypes = null; // ["general neglect", "physical abuse"];
  private String victimId = "22";

  @Test
  public void equalsHashCodeWork() {
    EqualsVerifier.forClass(SimpleAllegation.class).suppress(Warning.NONFINAL_FIELDS).verify();
  }

  @Test
  public void jsonCreatorConstructorTest() throws Exception {

    SimpleAllegation domain = new SimpleAllegation(victimId, perpetratorId, allegationTypes);
    assertThat(domain.getVictimId(), is(equalTo(victimId)));
    assertThat(domain.getPerpetratorId(), is(equalTo(perpetratorId)));
    assertThat(domain.getAllegationTypes(), is(equalTo(allegationTypes)));

  }

}
