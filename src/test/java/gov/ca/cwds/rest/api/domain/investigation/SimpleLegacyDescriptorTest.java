package gov.ca.cwds.rest.api.domain.investigation;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

import org.junit.Test;

@SuppressWarnings("javadoc")
public class SimpleLegacyDescriptorTest {

  private String legacyUiId = "0762-2283-8000-4000739";

  @Test
  public void equalsHashCodeWork() {
    EqualsVerifier.forClass(LimitedAccess.class).suppress(Warning.NONFINAL_FIELDS).verify();
  }

  @Test
  public void jsonCreatorConstructorTest() throws Exception {
    SimpleLegacyDescriptor domain = new SimpleLegacyDescriptor(legacyUiId);
    assertThat(domain.getLegacyUiId(), is(equalTo(legacyUiId)));
  }

}
