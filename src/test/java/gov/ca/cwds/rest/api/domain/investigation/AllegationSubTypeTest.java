package gov.ca.cwds.rest.api.domain.investigation;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;


@SuppressWarnings("javadoc")
public class AllegationSubTypeTest {
  private Short injuryHarmType1 = 1372;
  private Short injuryHarmType2 = 1372;
  private Short injuryHarmSubType1 = 6;
  private Short injuryHarmSubType2 = 7;

  @Before
  public void setup() {}

  @Test
  public void shouldCreateObjectWithDefaultConstructor() {
    AllegationSubType allegationSubType = new AllegationSubType();
    assertNotNull(allegationSubType);
  }

  @Test
  public void domainConstructorTest() {
    AllegationSubType domain = new AllegationSubType(injuryHarmType1, injuryHarmSubType1);
    assertThat(domain.getInjuryHarmType(), is(equalTo(injuryHarmType1)));
    assertThat(domain.getInjuryHarmSubType(), is(equalTo(injuryHarmSubType1)));
  }

}
