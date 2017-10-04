package gov.ca.cwds.data.persistence.cms;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.assertj.core.api.Assertions.assertThat;
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
    final CollateralIndividual ci = validCollateralIndividual();

    final CollateralIndividual entity = new CollateralIndividual(ci.getBadgeNumber(),
        ci.getBirthDate(), ci.getCityName(), ci.getCommentDescription(), ci.getEmailAddress(),
        ci.getEmployerName(), ci.getEstablishedForCode(), ci.getFaxNumber(), ci.getFirstName(),
        ci.getForeignAddressIndicatorVariable(), ci.getGenderCode(), ci.getId(), ci.getLastName(),
        ci.getMaritalStatus(), ci.getMiddleInitialName(), ci.getNamePrefixDescription(),
        ci.getPrimaryExtensionNumber(), ci.getPrimaryPhoneNo(), ci.getResidedOutOfStateIndicator(),
        ci.getStateCode(), ci.getStreetName(), ci.getStreetNumber(), ci.getSuffixTitleDescription(),
        ci.getZipNumber(), ci.getZipSuffixNumber());

    assertThat(entity.getBadgeNumber(), is(equalTo(ci.getBadgeNumber())));
    assertThat(entity.getBirthDate(), is(equalTo(ci.getBirthDate())));
    assertThat(entity.getCityName(), is(equalTo(ci.getCityName())));
    assertThat(entity.getCommentDescription(), is(equalTo(ci.getCommentDescription())));
    assertThat(entity.getEmailAddress(), is(equalTo(ci.getEmailAddress())));
    assertThat(entity.getEmployerName(), is(equalTo(ci.getEmployerName())));
    assertThat(entity.getEstablishedForCode(), is(equalTo(ci.getEstablishedForCode())));
    assertThat(entity.getFaxNumber(), is(equalTo(ci.getFaxNumber())));
    assertThat(entity.getFirstName(), is(equalTo(ci.getFirstName())));
    assertThat(entity.getForeignAddressIndicatorVariable(),
        is(equalTo(ci.getForeignAddressIndicatorVariable())));
    assertThat(entity.getGenderCode(), is(equalTo(ci.getGenderCode())));
    assertThat(entity.getId(), is(equalTo(ci.getId())));
    assertThat(entity.getLastName(), is(equalTo(ci.getLastName())));
    assertThat(entity.getMaritalStatus(), is(equalTo(ci.getMaritalStatus())));
    assertThat(entity.getMiddleInitialName(), is(equalTo(ci.getMiddleInitialName())));
    assertThat(entity.getNamePrefixDescription(), is(equalTo(ci.getNamePrefixDescription())));
    assertThat(entity.getPrimaryExtensionNumber(), is(equalTo(ci.getPrimaryExtensionNumber())));
    assertThat(entity.getPrimaryPhoneNo(), is(equalTo(ci.getPrimaryPhoneNo())));
    assertThat(entity.getResidedOutOfStateIndicator(),
        is(equalTo(ci.getResidedOutOfStateIndicator())));
    assertThat(entity.getStateCode(), is(equalTo(ci.getStateCode())));
    assertThat(entity.getStreetName(), is(equalTo(ci.getStreetName())));
    assertThat(entity.getStreetNumber(), is(equalTo(ci.getStreetNumber())));
    assertThat(entity.getSuffixTitleDescription(), is(equalTo(ci.getSuffixTitleDescription())));
    assertThat(entity.getZipNumber(), is(equalTo(ci.getZipNumber())));
    assertThat(entity.getZipSuffixNumber(), is(equalTo(ci.getZipSuffixNumber())));
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
