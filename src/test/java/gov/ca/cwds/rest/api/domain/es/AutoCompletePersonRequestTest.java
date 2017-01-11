package gov.ca.cwds.rest.api.domain.es;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.hamcrest.junit.ExpectedException;
import org.junit.Rule;
import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

/**
 * Test domain request class, {@link AutoCompletePersonRequest}.
 * 
 * <p>
 * NOTE: Mockito cannot mock up or spy on final classes, like String, and thereby that framework
 * cannot inject test artifacts into test objects.
 * </p>
 * 
 * @author CWDS API Team
 */
public class AutoCompletePersonRequestTest {

  @Rule
  public ExpectedException thrown = ExpectedException.none();


  @Test
  public void type() throws Exception {
    assertThat(AutoCompletePersonRequest.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    AutoCompletePersonRequest target = produce(null);
    assertThat(target, notNullValue());
  }

  @Test
  public void equalsHashCodeWork() throws Exception {
    EqualsVerifier.forClass(AutoCompletePersonRequest.class).suppress(Warning.NONFINAL_FIELDS)
        .verify();
  }

  @Test
  public void equals_Args$Object() throws Exception {
    AutoCompletePersonRequest target = produce(null);
    // given
    Object obj = null;
    // e.g. : given(mocked.called()).willReturn(1);
    // when
    final boolean actual = target.equals(obj);
    // then
    // e.g. : verify(mocked).called();
    boolean expected = false;
    assertThat(actual, is(equalTo(expected)));
  }

  protected AutoCompletePersonRequest produce(String s) {
    return new AutoCompletePersonRequest(s);
  }

}
