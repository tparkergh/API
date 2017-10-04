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

  @SuppressWarnings("javadoc")
  @Test
  public void testSerializeAndDeserialize() throws Exception {
    final OtherClientName tgt = validBean();
    // Serialize to JSON.
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    try (PrintStream ps = new PrintStream(baos)) {
      MAPPER.writerWithDefaultPrettyPrinter().writeValue(ps, tgt);
    } finally {
    }

    final String json = baos.toString(java.nio.charset.StandardCharsets.UTF_8.name());
    // De serialize from JSON just written.
    final OtherClientName actual = MAPPER.readValue(json, OtherClientName.class);
    // Does it match exactly?
    assertThat(actual, is(equalTo(validBean())));
  }

  @SuppressWarnings("javadoc")
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
    // System.out.println(json);
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

  @SuppressWarnings("javadoc")
  @Test
  public void testClassType() throws Exception {
    assertThat(OtherClientName.class, notNullValue());
  }

  @SuppressWarnings("javadoc")
  @Test
  public void testInstantiation() throws Exception {
    final OtherClientName target = validBean();
    assertThat(target, notNullValue());
  }

  @SuppressWarnings("javadoc")
  @Test
  public void getClientId_Args$() throws Exception {
    final OtherClientName target = validBean();
    final String actual = target.getClientId();
    final String expected = "1234567ABC";
    assertThat(actual, is(equalTo(expected)));
  }

  @SuppressWarnings("javadoc")
  @Test
  public void getThirdId_Args$() throws Exception {
    final OtherClientName target = validBean();
    String actual = target.getThirdId();
    String expected = "2345678ABC";
    assertThat(actual, is(equalTo(expected)));
  }

  @SuppressWarnings("javadoc")
  @Test
  public void setClientId_Args$String() throws Exception {
    final OtherClientName target = validBean();
    String clientId = null;
    target.setClientId(clientId);
  }

  @SuppressWarnings("javadoc")
  @Test
  public void setThirdId_Args$String() throws Exception {
    final OtherClientName target = validBean();
    String thirdId = null;
    target.setThirdId(thirdId);
  }

  @SuppressWarnings("javadoc")
  public void hashCode_Args$() throws Exception {
    EqualsVerifier.forClass(OtherClientName.class).suppress(Warning.NONFINAL_FIELDS).verify();
  }

  @SuppressWarnings("javadoc")
  public void equals_Args$Object() throws Exception {
    EqualsVerifier.forClass(OtherClientName.class).suppress(Warning.NONFINAL_FIELDS).verify();
  }

  @SuppressWarnings("javadoc")
  @Test
  public void getGender_Args$() throws Exception {
    final OtherClientName target = validBean();
    final String actual = target.getGender();
    final String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @SuppressWarnings("javadoc")
  @Test
  public void getBirthDate_Args$() throws Exception {
    final OtherClientName target = validBean();
    final Date actual = target.getBirthDate();
    final Date expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @SuppressWarnings("javadoc")
  @Test
  public void getSsn_Args$() throws Exception {
    final OtherClientName target = validBean();
    final String actual = target.getSsn();
    final String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @SuppressWarnings("javadoc")
  @Test
  public void getNameSuffix_Args$() throws Exception {
    final OtherClientName target = validBean();
    final String actual = target.getNameSuffix();
    final String expected = "Phd";
    assertThat(actual, is(equalTo(expected)));
  }

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

  private OtherClientName validOtherClientName()
      throws JsonParseException, JsonMappingException, IOException {
    OtherClientName validOtherClientName = MAPPER.readValue(
        fixture("fixtures/domain/cms/OtherClientName/valid/valid.json"), OtherClientName.class);
    return validOtherClientName;
  }

}

