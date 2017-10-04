package gov.ca.cwds.data.persistence.cms;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Date;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import gov.ca.cwds.data.persistence.junit.template.PersistentTestTemplate;
import gov.ca.cwds.data.std.ApiPhoneAware;


/**
 * @author CWDS API Team
 */
public class AttorneyTest implements PersistentTestTemplate {

  private static final ObjectMapper MAPPER = SystemCodeTestHarness.MAPPER;

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

  @SuppressWarnings("javadoc")
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

  }

  @SuppressWarnings("javadoc")
  @Test
  public void testSerializeAndDeserialize() throws Exception {
    final Attorney vatrny = validAttorney();

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
    final String expected = MAPPER.writeValueAsString((MAPPER.readValue(
        fixture("fixtures/persistent/Attorney/valid/validWithSysCodes.json"), Attorney.class)));

    assertThat(MAPPER.writeValueAsString(persistent)).isEqualTo(expected);
  }

  private Attorney validAttorney() throws JsonParseException, JsonMappingException, IOException {
    Attorney validAttorney =
        MAPPER.readValue(fixture("fixtures/persistent/Attorney/valid/valid.json"), Attorney.class);
    return validAttorney;
  }

  @Override
  //
  // no domain class
  //
  public void testConstructorUsingDomain() throws Exception {

  }

  @SuppressWarnings("javadoc")
  @Test
  public void type() throws Exception {
    assertThat(Attorney.class, notNullValue());
  }

  @SuppressWarnings("javadoc")
  @Test
  public void instantiation() throws Exception {
    final Attorney target = new Attorney();
    assertThat(target, notNullValue());
  }

  @SuppressWarnings("javadoc")
  @Test
  public void getPrimaryKey_Args$() throws Exception {
    final Attorney target = validAttorney();
    final String actual = target.getPrimaryKey();
    final String expected = "OvViVUs0OK";
    assertThat(actual, is(equalTo(expected)));
  }

  @SuppressWarnings("javadoc")
  @Test
  public void hashCode_Args$() throws Exception {
    // EqualsVerifier.forClass(Attorney.class).suppress(Warning.NONFINAL_FIELDS).verify();
  }

  @SuppressWarnings("javadoc")
  @Test
  public void equals_Args$Object() throws Exception {
    // EqualsVerifier.forClass(Attorney.class).suppress(Warning.NONFINAL_FIELDS).verify();
  }

  @SuppressWarnings("javadoc")
  @Test
  public void getMiddleName_Args$() throws Exception {
    final Attorney target = validAttorney();
    final String actual = target.getMiddleName();
    final String expected = "M";
    assertThat(actual, is(equalTo(expected)));
  }

  @SuppressWarnings("javadoc")
  @Test
  public void getGender_Args$() throws Exception {
    final Attorney target = validAttorney();
    final String actual = target.getGender();
    final String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @SuppressWarnings("javadoc")
  @Test
  public void getBirthDate_Args$() throws Exception {
    Attorney target = validAttorney();
    Date actual = target.getBirthDate();
    Date expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @SuppressWarnings("javadoc")
  @Test
  public void getSsn_Args$() throws Exception {
    final Attorney target = validAttorney();
    String actual = target.getSsn();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @SuppressWarnings("javadoc")
  @Test
  public void getNameSuffix_Args$() throws Exception {
    final Attorney target = validAttorney();
    String actual = target.getNameSuffix();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @SuppressWarnings("javadoc")
  @Test
  public void getPhones_Args$() throws Exception {
    final Attorney target = validAttorney();
    final ApiPhoneAware[] actual = target.getPhones();
    assertTrue(actual.length > 1);
  }

}
