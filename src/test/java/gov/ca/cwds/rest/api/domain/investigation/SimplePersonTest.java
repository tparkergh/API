package gov.ca.cwds.rest.api.domain.investigation;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

import org.junit.Test;

@SuppressWarnings("javadoc")
public class SimplePersonTest {

  private String lastName = "Smith";
  private String firstName = "John";
  private String sensitivityIndicator = "R";

  @Test
  public void equalsHashCodeWork() {
    EqualsVerifier.forClass(SimplePerson.class).suppress(Warning.NONFINAL_FIELDS).verify();
  }

  @Test
  public void jsonCreatorConstructorTest() throws Exception {
    SimplePerson domain = new SimplePerson(lastName, firstName, sensitivityIndicator);
    assertThat(domain.getLastName(), is(equalTo(lastName)));
    assertThat(domain.getFirstName(), is(equalTo(firstName)));
    assertThat(domain.getSensitivityIndicator(), is(equalTo(sensitivityIndicator)));
  }

}
