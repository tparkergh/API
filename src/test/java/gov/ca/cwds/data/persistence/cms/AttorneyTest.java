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
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;


/**
 * @author CWDS API Team
 *
 */
public class AttorneyTest implements PersistentTestTemplate {

  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

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
  public void testPersistentContructor() throws Exception {
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

    assertThat(persistent.getArchiveAssociationIndicator(),
        is(equalTo(vatrny.getArchiveAssociationIndicator())));
    assertThat(persistent.getBusinessName(), is(equalTo(vatrny.getBusinessName())));
    assertThat(persistent.getCityName(), is(equalTo(vatrny.getCityName())));
    assertThat(persistent.getCwsAttorneyIndicator(), is(equalTo(vatrny.getCwsAttorneyIndicator())));
    assertThat(persistent.getEmailAddress(), is(equalTo(vatrny.getEmailAddress())));
    assertThat(persistent.getEndDate(), is(equalTo(vatrny.getEndDate())));
    assertThat(persistent.getFaxNumber(), is(equalTo(vatrny.getFaxNumber())));
    assertThat(persistent.getFirstName(), is(equalTo(vatrny.getFirstName())));
    assertThat(persistent.getGovernmentEntityType(), is(equalTo(vatrny.getGovernmentEntityType())));
    assertThat(persistent.getId(), is(equalTo(vatrny.getId())));
    assertThat(persistent.getLanguageType(), is(equalTo(vatrny.getLanguageType())));
    assertThat(persistent.getLastName(), is(equalTo(vatrny.getLastName())));
    assertThat(persistent.getMessagePhoneExtensionNumber(),
        is(equalTo(vatrny.getMessagePhoneExtensionNumber())));
    assertThat(persistent.getMessagePhoneNumber(), is(equalTo(vatrny.getMessagePhoneNumber())));
    assertThat(persistent.getMiddleInitialName(), is(equalTo(vatrny.getMiddleInitialName())));
    assertThat(persistent.getNamePrefixDescription(),
        is(equalTo(vatrny.getNamePrefixDescription())));
    assertThat(persistent.getPositionTitleDescription(),
        is(equalTo(vatrny.getPositionTitleDescription())));
    assertThat(persistent.getPrimaryPhoneExtensionNumber(),
        is(equalTo(vatrny.getPrimaryPhoneExtensionNumber())));
    assertThat(persistent.getPrimaryPhoneNumber(), is(equalTo(vatrny.getPrimaryPhoneNumber())));
    assertThat(persistent.getStateCodeType(), is(equalTo(vatrny.getStateCodeType())));
    assertThat(persistent.getStreetName(), is(equalTo(vatrny.getStreetName())));
    assertThat(persistent.getStreetNumber(), is(equalTo(vatrny.getStreetNumber())));
    assertThat(persistent.getSuffixTitleDescription(),
        is(equalTo(vatrny.getSuffixTitleDescription())));
    assertThat(persistent.getZipNumber(), is(equalTo(vatrny.getZipNumber())));
    assertThat(persistent.getZipSuffixNumber(), is(equalTo(vatrny.getZipSuffixNumber())));
  }

  private Attorney validAttorney() throws JsonParseException, JsonMappingException, IOException {

    Attorney validAttorney = MAPPER
        .readValue(fixture("fixtures/domain/legacy/Attorney/valid/valid.json"), Attorney.class);

    return validAttorney;

  }

  @Override
  //
  // no domain class
  //
  public void testConstructorUsingDomain() throws Exception {

  }

}
