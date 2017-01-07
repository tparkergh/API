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

import io.dropwizard.jackson.Jackson;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

/**
 * @author CWDS API Team
 *
 */
public class EducationProviderContactTest {

  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

  @SuppressWarnings("javadoc")
  @Test
  public void equalsHashCodeWork() {
    EqualsVerifier.forClass(EducationProviderContact.class).suppress(Warning.NONFINAL_FIELDS)
        .verify();
  }

  /*
   * Constructor test
   */
  @SuppressWarnings("javadoc")
  @Test
  public void emtpyConstructorIsNotNull() throws Exception {
    assertThat(EducationProviderContact.class.newInstance(), is(notNullValue()));
  }


  @SuppressWarnings("javadoc")
  @Test
  public void persistentConstructorTest() throws Exception {

    EducationProviderContact epc = validEducationProviderContact();

    EducationProviderContact pre =
        new EducationProviderContact(epc.getdepartmentOfEducationIndicator(), epc.getEmailAddress(),
            epc.getFaxNumber(), epc.getFirstName(), epc.getfKeyEducationProvider(), epc.getId(),
            epc.getLastName(), epc.getMiddleName(), epc.getNamePrefixDescription(),
            epc.getPhoneExtensionNumber(), epc.getPhoneNumber(), epc.getPrimaryContactIndicator(),
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
    assertThat(pre.getPhoneNumber(), is(equalTo(epc.getPhoneNumber())));
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
}
