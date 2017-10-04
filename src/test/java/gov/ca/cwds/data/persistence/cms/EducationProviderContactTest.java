package gov.ca.cwds.data.persistence.cms;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import java.io.IOException;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import gov.ca.cwds.data.persistence.junit.template.PersistentTestTemplate;
import io.dropwizard.jackson.Jackson;

/**
 * @author CWDS API Team
 */
public class EducationProviderContactTest implements PersistentTestTemplate {

  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

  /*
   * Constructor test
   */
  @Override
  @Test
  public void testEmptyConstructor() throws Exception {
    assertThat(EducationProviderContact.class.newInstance(), is(notNullValue()));
  }

  @Override
  @Test
  public void testPersistentConstructor() throws Exception {
    EducationProviderContact epc = validEducationProviderContact();
    EducationProviderContact pre = new EducationProviderContact(
        epc.getdepartmentOfEducationIndicator(), epc.getEmailAddress(), epc.getFaxNumber(),
        epc.getFirstName(), epc.getfKeyEducationProvider(), epc.getId(), epc.getLastName(),
        epc.getMiddleName(), epc.getNamePrefixDescription(), epc.getPhoneExtensionNumber(),
        epc.getPhoneNumberAsDecimal(), epc.getPrimaryContactIndicator(),
        epc.getSuffixTitleDescription(), epc.getTitleDescription());

    assertThat(pre.getdepartmentOfEducationIndicator(),
        is(equalTo(epc.getdepartmentOfEducationIndicator())));
    assertThat(pre.getEmailAddress(), is(equalTo(epc.getEmailAddress())));
    assertThat(pre.getFaxNumber(), is(equalTo(epc.getFaxNumber())));
    assertThat(pre.getFirstName(), is(equalTo(epc.getFirstName())));
    assertThat(pre.getfKeyEducationProvider(), is(equalTo(epc.getfKeyEducationProvider())));
    assertThat(pre.getId(), is(equalTo(epc.getId())));
    assertThat(pre.getLastName(), is(equalTo(epc.getLastName())));
    assertThat(pre.getMiddleName(), is(equalTo(epc.getMiddleName())));
    assertThat(pre.getNamePrefixDescription(), is(equalTo(epc.getNamePrefixDescription())));
    assertThat(pre.getPhoneExtensionNumber(), is(equalTo(epc.getPhoneExtensionNumber())));
    assertThat(pre.getPhoneNumberAsDecimal(), is(equalTo(epc.getPhoneNumberAsDecimal())));
    assertThat(pre.getPrimaryContactIndicator(), is(equalTo(epc.getPrimaryContactIndicator())));
    assertThat(pre.getSuffixTitleDescription(), is(equalTo(epc.getSuffixTitleDescription())));
    assertThat(pre.getTitleDescription(), is(equalTo(epc.getTitleDescription())));
  }

  private EducationProviderContact validEducationProviderContact()
      throws JsonParseException, JsonMappingException, IOException {

    EducationProviderContact validEducationProviderContact = MAPPER.readValue(
        fixture("fixtures/domain/legacy/EducationProviderContact/valid/valid.json"),
        EducationProviderContact.class);

    return validEducationProviderContact;
  }

  @Override
  public void testConstructorUsingDomain() throws Exception {
    // no domain class
  }

}
