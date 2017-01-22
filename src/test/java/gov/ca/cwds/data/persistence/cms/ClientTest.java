package gov.ca.cwds.data.persistence.cms;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import gov.ca.cwds.data.ILanguageAware;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

public class ClientTest {

  private static final ObjectMapper MAPPER = SystemCodeTestHarness.MAPPER;

  private Client validBean() throws JsonParseException, JsonMappingException, IOException {
    return MAPPER.readValue(fixture("fixtures/persistence/legacy/Client/valid.json"), Client.class);
  }

  @Test
  public void testSerializeJAndDeserialize() throws Exception {
    final Client tgt = validBean();

    // For pretty JSON, instead of a single line.
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    try (PrintStream ps = new PrintStream(baos)) {
      MAPPER.writerWithDefaultPrettyPrinter().writeValue(ps, tgt);
    } finally {
    }

    final String json = baos.toString(java.nio.charset.StandardCharsets.UTF_8.name());
    final Client actual = MAPPER.readValue(json, Client.class);
    assertThat(actual, is(equalTo(validBean())));
  }

  /*
   * Constructor test
   */
  @Test
  public void emtpyConstructorIsNotNull() throws Exception {
    assertThat(Client.class.newInstance(), is(notNullValue()));
  }

  @Test
  public void type() throws Exception {
    assertThat(Client.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    final Client target = validBean();
    assertThat(target, notNullValue());
  }

  @Test
  public void getPrimaryKey_Args$() throws Exception {
    final Client target = validBean();
    // given
    // e.g. : given(mocked.called()).willReturn(1);
    // when
    final String actual = target.getPrimaryKey();
    // then
    // e.g. : verify(mocked).called();
    final String expected = "B3ucP1K07n";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getMiddleName_Args$() throws Exception {
    final Client target = validBean();
    // given
    // e.g. : given(mocked.called()).willReturn(1);
    // when
    String actual = target.getMiddleName();
    // then
    // e.g. : verify(mocked).called();
    final String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getFirstName_Args$() throws Exception {
    final Client target = validBean();
    // given
    // e.g. : given(mocked.called()).willReturn(1);
    // when
    String actual = target.getFirstName();
    // then
    // e.g. : verify(mocked).called();
    final String expected = "Chicken";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getLastName_Args$() throws Exception {
    final Client target = validBean();
    // given
    // e.g. : given(mocked.called()).willReturn(1);
    // when
    String actual = target.getLastName();
    // then
    // e.g. : verify(mocked).called();
    final String expected = "Little";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getSsn_Args$() throws Exception {
    final Client target = validBean();
    // given
    // e.g. : given(mocked.called()).willReturn(1);
    // when
    String actual = target.getSsn();
    // then
    // e.g. : verify(mocked).called();
    final String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getNameSuffix_Args$() throws Exception {
    final Client target = validBean();
    final String actual = target.getNameSuffix();
    final String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getLanguages_Args$() throws Exception {
    final Client target = validBean();
    final ILanguageAware[] actual = target.getLanguages();
    assertTrue(actual.length > 0);
  }

  @Test
  public void hashCode_Args$() throws Exception {
    EqualsVerifier.forClass(Client.class).suppress(Warning.NONFINAL_FIELDS).verify();
  }

  @Test
  public void equals_Args$Object() throws Exception {
    EqualsVerifier.forClass(Client.class).suppress(Warning.NONFINAL_FIELDS).verify();
  }

}
