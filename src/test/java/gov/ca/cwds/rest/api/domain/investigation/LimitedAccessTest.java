package gov.ca.cwds.rest.api.domain.investigation;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

import org.junit.Test;

@SuppressWarnings("javadoc")
public class LimitedAccessTest {

  private String limitedAccessGovernmentEntityId = "0";
  private String limitedAccessCode = "n";

  @Test
  public void equalsHashCodeWork() {
    EqualsVerifier.forClass(LimitedAccess.class).suppress(Warning.NONFINAL_FIELDS).verify();

  }

  @Test
  public void jsonCreatorConstructorTest() throws Exception {
    LimitedAccess domain = new LimitedAccess(limitedAccessGovernmentEntityId, limitedAccessCode);
    assertThat(domain.getLimitedAccessGovernmentEntityId(),
        is(equalTo(limitedAccessGovernmentEntityId)));
    assertThat(domain.getLimitedAccessCode(), is(equalTo(limitedAccessCode)));
  }

}
