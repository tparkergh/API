package gov.ca.cwds.rest.api.domain.investigation;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.util.Set;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

import org.junit.Test;

@SuppressWarnings("javadoc")
public class SimplePersonWithRolesTest {

  private Set<String> roles = null;
  private String lastName = "Smith";
  private String firstName = "John";
  private String sensitivityIndicator = "R";

  @Test
  public void equalsHashCodeWork() {
    EqualsVerifier.forClass(SimplePerson.class).suppress(Warning.NONFINAL_FIELDS).verify();
  }

  @Test
  public void jsonCreatorConstructorTest() throws Exception {
    SimplePersonWithRoles domain =
        new SimplePersonWithRoles(roles, lastName, firstName, sensitivityIndicator);
    assertThat(domain.getRoles(), is(equalTo(roles)));
    assertThat(domain.getLastName(), is(equalTo(lastName)));
    assertThat(domain.getFirstName(), is(equalTo(firstName)));
    assertThat(domain.getSensitivityIndicator(), is(equalTo(sensitivityIndicator)));
  }

}
