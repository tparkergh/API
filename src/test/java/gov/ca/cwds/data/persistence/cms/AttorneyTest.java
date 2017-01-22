package gov.ca.cwds.data.persistence.cms;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Date;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import gov.ca.cwds.data.CmsSystemCodeSerializer;
import gov.ca.cwds.data.IPhoneAware;
import gov.ca.cwds.data.persistence.junit.template.PersistentTestTemplate;
import io.dropwizard.jackson.Jackson;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;


/**
 * @author CWDS API Team
 */
public class AttorneyTest implements PersistentTestTemplate {

  private static final ObjectMapper MAPPER;

  /**
   * Auto-magically translate CMS system codes when serializing JSON.
   */
  static {
    // Inject system code cache.
    ObjectMapper mapper = Jackson.newObjectMapper();
    SimpleModule module = new SimpleModule("SystemCodeModule",
        new Version(1, 0, 24, "alpha", "ca.gov.data.persistence.cms", "syscode"));
    module.addSerializer(Short.class,
        new CmsSystemCodeSerializer(new CmsSystemCodeCacheService(new SystemCodeDaoFileImpl())));
    mapper.registerModule(module);
    MAPPER = mapper;
  }

  /**
   * Despite the fixture location, the domain bean is no longer in use.
   * 
   * @return valid Attorney object
   * @throws JsonParseException if unable parse the fixture JSON
   * @throws JsonMappingException if unable to map JSON elements to fields
   * @throws IOException if unable to read the JSON file
   */
  private Attorney validAttorney() throws JsonParseException, JsonMappingException, IOException {
    Attorney validAttorney = MAPPER
        .readValue(fixture("fixtures/domain/legacy/Attorney/valid/valid.json"), Attorney.class);
    return validAttorney;
  }

  @Override
  @Test
  public void testEqualsHashCodeWorks() {
    EqualsVerifier.forClass(Attorney.class).suppress(Warning.NONFINAL_FIELDS).verify();
  }

  /*
   * Constructor test
   */
  @Override
  @Test
  public void testEmptyConstructor() throws Exception {
    assertThat(Attorney.class.newInstance(), is(notNullValue()));
  }

  @Override
  @Test
  public void testPersistentConstructor() throws Exception {
    Attorney vatrny = validAttorney();
    Attorney pers = new Attorney(vatrny.getArchiveAssociationIndicator(), vatrny.getBusinessName(),
        vatrny.getCityName(), vatrny.getCwsAttorneyIndicator(), vatrny.getEmailAddress(),
        vatrny.getEndDate(), vatrny.getFaxNumber(), vatrny.getFirstName(),
        vatrny.getGovernmentEntityType(), vatrny.getId(), vatrny.getLanguageType(),
        vatrny.getLastName(), vatrny.getMessagePhoneExtensionNumber(),
        vatrny.getMessagePhoneNumber(), vatrny.getMiddleInitialName(),
        vatrny.getNamePrefixDescription(), vatrny.getPositionTitleDescription(),
        vatrny.getPrimaryPhoneExtensionNumber(), vatrny.getPrimaryPhoneNumber(),
        vatrny.getStateCodeType(), vatrny.getStreetName(), vatrny.getStreetNumber(),
        vatrny.getSuffixTitleDescription(), vatrny.getZipNumber(), vatrny.getZipSuffixNumber());

    assertThat(pers.getArchiveAssociationIndicator(),
        is(equalTo(vatrny.getArchiveAssociationIndicator())));
    assertThat(pers.getBusinessName(), is(equalTo(vatrny.getBusinessName())));
    assertThat(pers.getCityName(), is(equalTo(vatrny.getCityName())));
    assertThat(pers.getCwsAttorneyIndicator(), is(equalTo(vatrny.getCwsAttorneyIndicator())));
    assertThat(pers.getEmailAddress(), is(equalTo(vatrny.getEmailAddress())));
    assertThat(pers.getEndDate(), is(equalTo(vatrny.getEndDate())));
    assertThat(pers.getFaxNumber(), is(equalTo(vatrny.getFaxNumber())));
    assertThat(pers.getFirstName(), is(equalTo(vatrny.getFirstName())));
    assertThat(pers.getGovernmentEntityType(), is(equalTo(vatrny.getGovernmentEntityType())));
    assertThat(pers.getId(), is(equalTo(vatrny.getId())));
    assertThat(pers.getLanguageType(), is(equalTo(vatrny.getLanguageType())));
    assertThat(pers.getLastName(), is(equalTo(vatrny.getLastName())));
    assertThat(pers.getMessagePhoneExtensionNumber(),
        is(equalTo(vatrny.getMessagePhoneExtensionNumber())));
    assertThat(pers.getMessagePhoneNumber(), is(equalTo(vatrny.getMessagePhoneNumber())));
    assertThat(pers.getMiddleInitialName(), is(equalTo(vatrny.getMiddleInitialName())));
    assertThat(pers.getNamePrefixDescription(), is(equalTo(vatrny.getNamePrefixDescription())));
    assertThat(pers.getPositionTitleDescription(),
        is(equalTo(vatrny.getPositionTitleDescription())));
    assertThat(pers.getPrimaryPhoneExtensionNumber(),
        is(equalTo(vatrny.getPrimaryPhoneExtensionNumber())));
    assertThat(pers.getPrimaryPhoneNumber(), is(equalTo(vatrny.getPrimaryPhoneNumber())));
    assertThat(pers.getStateCodeType(), is(equalTo(vatrny.getStateCodeType())));
    assertThat(pers.getStreetName(), is(equalTo(vatrny.getStreetName())));
    assertThat(pers.getStreetNumber(), is(equalTo(vatrny.getStreetNumber())));
    assertThat(pers.getSuffixTitleDescription(), is(equalTo(vatrny.getSuffixTitleDescription())));
    assertThat(pers.getZipNumber(), is(equalTo(vatrny.getZipNumber())));
    assertThat(pers.getZipSuffixNumber(), is(equalTo(vatrny.getZipSuffixNumber())));
  }

  @Test
  public void testSerializeJson() throws Exception {
    Attorney vatrny = validAttorney();
    Attorney persistent = new Attorney(vatrny.getArchiveAssociationIndicator(),
        vatrny.getBusinessName(), vatrny.getCityName(), vatrny.getCwsAttorneyIndicator(),
        vatrny.getEmailAddress(), vatrny.getEndDate(), vatrny.getFaxNumber(), vatrny.getFirstName(),
        vatrny.getGovernmentEntityType(), vatrny.getId(), vatrny.getLanguageType(),
        vatrny.getLastName(), vatrny.getMessagePhoneExtensionNumber(),
        vatrny.getMessagePhoneNumber(), vatrny.getMiddleInitialName(),
        vatrny.getNamePrefixDescription(), vatrny.getPositionTitleDescription(),
        vatrny.getPrimaryPhoneExtensionNumber(), vatrny.getPrimaryPhoneNumber(),
        vatrny.getStateCodeType(), vatrny.getStreetName(), vatrny.getStreetNumber(),
        vatrny.getSuffixTitleDescription(), vatrny.getZipNumber(), vatrny.getZipSuffixNumber());

    assertThat(persistent.getZipSuffixNumber(), is(equalTo(vatrny.getZipSuffixNumber())));

    // For pretty JSON, instead of a single line.
    MAPPER.writerWithDefaultPrettyPrinter().writeValue(System.out, persistent);
  }

  @Test
  public void testSerializeJAndDeserialize() throws Exception {
    final Attorney att = validAttorney();

    // For pretty JSON, instead of a single line.
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    try (PrintStream ps = new PrintStream(baos)) {
      MAPPER.writerWithDefaultPrettyPrinter().writeValue(ps, att);
    } finally {
    }

    final String json = baos.toString(java.nio.charset.StandardCharsets.UTF_8.name());
    final Attorney actual = MAPPER.readValue(json, Attorney.class);
    assertThat(actual, is(equalTo(validAttorney())));
  }

  @Override
  //
  // no domain class
  //
  public void testConstructorUsingDomain() throws Exception {

  }

  @Test
  public void type() throws Exception {
    assertThat(Attorney.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    final Attorney target = new Attorney();
    assertThat(target, notNullValue());
  }

  @Test
  public void getPrimaryKey_Args$() throws Exception {
    final Attorney target = validAttorney();
    final String actual = target.getPrimaryKey();
    final String expected = "OvViVUs0OK";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void hashCode_Args$() throws Exception {
    EqualsVerifier.forClass(Attorney.class).suppress(Warning.NONFINAL_FIELDS).verify();
  }

  @Test
  public void equals_Args$Object() throws Exception {
    EqualsVerifier.forClass(Attorney.class).suppress(Warning.NONFINAL_FIELDS).verify();
  }

  @Test
  public void getMiddleName_Args$() throws Exception {
    final Attorney target = validAttorney();
    final String actual = target.getMiddleName();
    final String expected = "M";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getGender_Args$() throws Exception {
    final Attorney target = validAttorney();
    final String actual = target.getGender();
    final String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getBirthDate_Args$() throws Exception {
    Attorney target = validAttorney();
    Date actual = target.getBirthDate();
    Date expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getSsn_Args$() throws Exception {
    final Attorney target = validAttorney();
    String actual = target.getSsn();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getNameSuffix_Args$() throws Exception {
    final Attorney target = validAttorney();
    String actual = target.getNameSuffix();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getPhones_Args$() throws Exception {
    final Attorney target = validAttorney();
    final IPhoneAware[] actual = target.getPhones();
    assertTrue(actual.length > 1);
  }

}
