package gov.ca.cwds.data.persistence.cms;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import java.io.IOException;

import org.junit.Ignore;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import gov.ca.cwds.data.persistence.junit.template.PersistentTestTemplate;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

/**
 * @author CWDS API Team
 *
 */
public class CollateralIndividualTest implements PersistentTestTemplate {

  private static final ObjectMapper MAPPER = SystemCodeTestHarness.MAPPER;

  public static CollateralIndividual validCollateralIndividual()
      throws JsonParseException, JsonMappingException, IOException {

    CollateralIndividual validCollateralIndividual =
        MAPPER.readValue(fixture("fixtures/persistent/CollateralIndividual/valid/valid.json"),
            CollateralIndividual.class);

    return validCollateralIndividual;
  }

  @Override
  @Test
  @Ignore
  public void testEqualsHashCodeWorks() {
    EqualsVerifier.forClass(CollateralIndividual.class).suppress(Warning.NONFINAL_FIELDS).verify();
  }

  /*
   * Constructor test
   */
  @Override
  @Test
  public void testEmptyConstructor() throws Exception {
    assertThat(CollateralIndividual.class.newInstance(), is(notNullValue()));
  }

  @Override
  @Test
  public void testPersistentConstructor() throws Exception {
    CollateralIndividual ci = validCollateralIndividual();

    CollateralIndividual persistent = new CollateralIndividual(ci.getBadgeNumber(),
        ci.getBirthDate(), ci.getCityName(), ci.getCommentDescription(), ci.getEmailAddress(),
        ci.getEmployerName(), ci.getEstablishedForCode(), ci.getFaxNumber(), ci.getFirstName(),
        ci.getForeignAddressIndicatorVariable(), ci.getGenderCode(), ci.getId(), ci.getLastName(),
        ci.getMaritalStatus(), ci.getMiddleInitialName(), ci.getNamePrefixDescription(),
        ci.getPrimaryExtensionNumber(), ci.getPrimaryPhoneNo(), ci.getResidedOutOfStateIndicator(),
        ci.getStateCode(), ci.getStreetName(), ci.getStreetNumber(), ci.getSuffixTitleDescription(),
        ci.getZipNumber(), ci.getZipSuffixNumber());

    assertThat(persistent.getBadgeNumber(), is(equalTo(ci.getBadgeNumber())));
    assertThat(persistent.getBirthDate(), is(equalTo(ci.getBirthDate())));
    assertThat(persistent.getCityName(), is(equalTo(ci.getCityName())));
    assertThat(persistent.getCommentDescription(), is(equalTo(ci.getCommentDescription())));
    assertThat(persistent.getEmailAddress(), is(equalTo(ci.getEmailAddress())));
    assertThat(persistent.getEmployerName(), is(equalTo(ci.getEmployerName())));
    assertThat(persistent.getEstablishedForCode(), is(equalTo(ci.getEstablishedForCode())));
    assertThat(persistent.getFaxNumber(), is(equalTo(ci.getFaxNumber())));
    assertThat(persistent.getFirstName(), is(equalTo(ci.getFirstName())));
    assertThat(persistent.getForeignAddressIndicatorVariable(),
        is(equalTo(ci.getForeignAddressIndicatorVariable())));
    assertThat(persistent.getGenderCode(), is(equalTo(ci.getGenderCode())));
    assertThat(persistent.getId(), is(equalTo(ci.getId())));
    assertThat(persistent.getLastName(), is(equalTo(ci.getLastName())));
    assertThat(persistent.getMaritalStatus(), is(equalTo(ci.getMaritalStatus())));
    assertThat(persistent.getMiddleInitialName(), is(equalTo(ci.getMiddleInitialName())));
    assertThat(persistent.getNamePrefixDescription(), is(equalTo(ci.getNamePrefixDescription())));
    assertThat(persistent.getPrimaryExtensionNumber(), is(equalTo(ci.getPrimaryExtensionNumber())));
    assertThat(persistent.getPrimaryPhoneNo(), is(equalTo(ci.getPrimaryPhoneNo())));
    assertThat(persistent.getResidedOutOfStateIndicator(),
        is(equalTo(ci.getResidedOutOfStateIndicator())));
    assertThat(persistent.getStateCode(), is(equalTo(ci.getStateCode())));
    assertThat(persistent.getStreetName(), is(equalTo(ci.getStreetName())));
    assertThat(persistent.getStreetNumber(), is(equalTo(ci.getStreetNumber())));
    assertThat(persistent.getSuffixTitleDescription(), is(equalTo(ci.getSuffixTitleDescription())));
    assertThat(persistent.getZipNumber(), is(equalTo(ci.getZipNumber())));
    assertThat(persistent.getZipSuffixNumber(), is(equalTo(ci.getZipSuffixNumber())));
  }

  @Override
  public void testConstructorUsingDomain() throws Exception {
    // no domain class.
  }

  @SuppressWarnings("javadoc")
  @Test
  public void testSerializeAndDeserialize() throws Exception {
    CollateralIndividual ci = validCollateralIndividual();

    CollateralIndividual persistent = new CollateralIndividual(ci.getBadgeNumber(),
        ci.getBirthDate(), ci.getCityName(), ci.getCommentDescription(), ci.getEmailAddress(),
        ci.getEmployerName(), ci.getEstablishedForCode(), ci.getFaxNumber(), ci.getFirstName(),
        ci.getForeignAddressIndicatorVariable(), ci.getGenderCode(), ci.getId(), ci.getLastName(),
        ci.getMaritalStatus(), ci.getMiddleInitialName(), ci.getNamePrefixDescription(),
        ci.getPrimaryExtensionNumber(), ci.getPrimaryPhoneNo(), ci.getResidedOutOfStateIndicator(),
        ci.getStateCode(), ci.getStreetName(), ci.getStreetNumber(), ci.getSuffixTitleDescription(),
        ci.getZipNumber(), ci.getZipSuffixNumber());
    final String expected = MAPPER.writeValueAsString((MAPPER.readValue(
        fixture("fixtures/persistent/CollateralIndividual/valid/validWithSysCodes.json"),
        CollateralIndividual.class)));

    assertThat(MAPPER.writeValueAsString(persistent)).isEqualTo(expected);
  }

}
