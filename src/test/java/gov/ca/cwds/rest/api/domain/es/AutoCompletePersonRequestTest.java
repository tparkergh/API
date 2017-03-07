package gov.ca.cwds.rest.api.domain.es;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.hamcrest.junit.ExpectedException;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.squarespace.jersey2.guice.JerseyGuiceUtils;

import gov.ca.cwds.rest.resources.cms.JerseyGuiceRule;
import io.dropwizard.jackson.Jackson;
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
@SuppressWarnings("javadoc")
public class AutoCompletePersonRequestTest {

  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

  @Rule
  public ExpectedException thrown = ExpectedException.none();


  @After
  public void ensureServiceLocatorPopulated() {
    JerseyGuiceUtils.reset();
  }

  @ClassRule
  public static JerseyGuiceRule rule = new JerseyGuiceRule();

  @Before
  public void setup() {}

  @Test
  public void type() throws Exception {
    assertThat(AutoCompletePersonRequest.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    AutoCompletePersonRequest target = produce(null);
    assertThat(target, notNullValue());
  }

  // @Test
  // void testConstructorSuccess() throws Exception {
  //
  // }

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
