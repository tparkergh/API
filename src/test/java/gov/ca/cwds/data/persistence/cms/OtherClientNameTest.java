package gov.ca.cwds.data.persistence.cms;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Date;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import gov.ca.cwds.data.persistence.junit.template.PersistentTestTemplate;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;


/**
 * @author CWDS API Team
 */
public class OtherClientNameTest implements PersistentTestTemplate {

  private static final ObjectMapper MAPPER = SystemCodeTestHarness.MAPPER;

  private OtherClientName validBean() throws JsonParseException, JsonMappingException, IOException {
    return MAPPER.readValue(fixture("fixtures/domain/cms/OtherClientName/valid/valid.json"),
        OtherClientName.class);
  }

  private OtherClientName validBeanNewStyle()
      throws JsonParseException, JsonMappingException, IOException {
    return MAPPER.readValue(
        fixture("fixtures/domain/cms/OtherClientName/valid/new_style_valid.json"),
        OtherClientName.class);
  }

  @Override
  @Test
  public void testEqualsHashCodeWorks() throws Exception {
    EqualsVerifier.forClass(OtherClientName.class).suppress(Warning.NONFINAL_FIELDS).verify();
  }

  @Test
  public void testSerializeJAndDeserialize() throws Exception {
    final OtherClientName tgt = validBean();

    // For pretty JSON, instead of a single line.
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    try (PrintStream ps = new PrintStream(baos)) {
      MAPPER.writerWithDefaultPrettyPrinter().writeValue(ps, tgt);
    } finally {
    }

    final String json = baos.toString(java.nio.charset.StandardCharsets.UTF_8.name());
    System.out.println(json);
    final OtherClientName actual = MAPPER.readValue(json, OtherClientName.class);
    assertThat(actual, is(equalTo(validBean())));
  }

  @Test
  public void testSerializeJAndDeserializeNewStyle() throws Exception {
    final OtherClientName tgt = validBeanNewStyle();

    // For pretty JSON, instead of a single line.
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    try (PrintStream ps = new PrintStream(baos)) {
      MAPPER.writerWithDefaultPrettyPrinter().writeValue(ps, tgt);
    } finally {
    }

    final String json = baos.toString(java.nio.charset.StandardCharsets.UTF_8.name());
    System.out.println(json);
    final OtherClientName actual = MAPPER.readValue(json, OtherClientName.class);
    assertThat(actual, is(equalTo(validBean())));
  }

  /*
   * Constructor test
   */
  @Override
  @Test
  public void testEmptyConstructor() throws Exception {
    assertThat(OtherClientName.class.newInstance(), is(notNullValue()));
  }

  @Override
  @Test
  public void testPersistentConstructor() throws Exception {
    OtherClientName vocn = validOtherClientName();
    OtherClientName pers = new OtherClientName(vocn.getClientId(), vocn.getFirstName(),
        vocn.getLastName(), vocn.getMiddleName(), vocn.getNamePrefixDescription(),
        vocn.getNameType(), vocn.getSuffixTitleDescription(), vocn.getThirdId());

    assertThat(pers.getClientId(), is(equalTo(vocn.getClientId())));
    assertThat(pers.getFirstName(), is(equalTo(vocn.getFirstName())));
    assertThat(pers.getLastName(), is(equalTo(vocn.getLastName())));
    assertThat(pers.getMiddleName(), is(equalTo(vocn.getMiddleName())));
    assertThat(pers.getNamePrefixDescription(), is(equalTo(vocn.getNamePrefixDescription())));
    assertThat(pers.getNameType(), is(equalTo(vocn.getNameType())));
    assertThat(pers.getSuffixTitleDescription(), is(equalTo(vocn.getSuffixTitleDescription())));
    assertThat(pers.getThirdId(), is(equalTo(vocn.getThirdId())));
  }

  @Override
  public void testConstructorUsingDomain() throws Exception {
    // no domain class
  }

  private OtherClientName validOtherClientName()
      throws JsonParseException, JsonMappingException, IOException {

    OtherClientName validOtherClientName = MAPPER.readValue(
        fixture("fixtures/domain/cms/OtherClientName/valid/valid.json"), OtherClientName.class);
    return validOtherClientName;
  }

  @Test
  public void type() throws Exception {
    assertThat(OtherClientName.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    final OtherClientName target = validBean();
    assertThat(target, notNullValue());
  }

  @Test
  public void getClientId_Args$() throws Exception {
    final OtherClientName target = validBean();
    // given
    // e.g. : given(mocked.called()).willReturn(1);
    // when
    final String actual = target.getClientId();
    // then
    // e.g. : verify(mocked).called();
    final String expected = "1234567ABC";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getThirdId_Args$() throws Exception {
    final OtherClientName target = validBean();
    // given
    // e.g. : given(mocked.called()).willReturn(1);
    // when
    String actual = target.getThirdId();
    // then
    // e.g. : verify(mocked).called();
    String expected = "2345678ABC";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setClientId_Args$String() throws Exception {
    final OtherClientName target = validBean();
    // given
    String clientId = null;
    // e.g. : given(mocked.called()).willReturn(1);
    // when
    target.setClientId(clientId);
    // then
    // e.g. : verify(mocked).called();
  }

  @Test
  public void setThirdId_Args$String() throws Exception {
    final OtherClientName target = validBean();
    // given
    String thirdId = null;
    // e.g. : given(mocked.called()).willReturn(1);
    // when
    target.setThirdId(thirdId);
    // then
    // e.g. : verify(mocked).called();
  }

  @Test
  public void hashCode_Args$() throws Exception {
    EqualsVerifier.forClass(OtherClientName.class).suppress(Warning.NONFINAL_FIELDS).verify();
  }

  @Test
  public void equals_Args$Object() throws Exception {
    EqualsVerifier.forClass(OtherClientName.class).suppress(Warning.NONFINAL_FIELDS).verify();
  }

  @Test
  public void getGender_Args$() throws Exception {
    final OtherClientName target = validBean();
    // given
    // e.g. : given(mocked.called()).willReturn(1);
    // when
    final String actual = target.getGender();
    // then
    // e.g. : verify(mocked).called();
    final String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getBirthDate_Args$() throws Exception {
    final OtherClientName target = validBean();
    // given
    // e.g. : given(mocked.called()).willReturn(1);
    // when
    final Date actual = target.getBirthDate();
    // then
    // e.g. : verify(mocked).called();
    final Date expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getSsn_Args$() throws Exception {
    final OtherClientName target = validBean();
    // given
    // e.g. : given(mocked.called()).willReturn(1);
    // when
    final String actual = target.getSsn();
    // then
    // e.g. : verify(mocked).called();
    final String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getNameSuffix_Args$() throws Exception {
    final OtherClientName target = validBean();
    // given
    // e.g. : given(mocked.called()).willReturn(1);
    // when
    final String actual = target.getNameSuffix();
    // then
    // e.g. : verify(mocked).called();
    final String expected = "Phd";
    assertThat(actual, is(equalTo(expected)));
  }

}
