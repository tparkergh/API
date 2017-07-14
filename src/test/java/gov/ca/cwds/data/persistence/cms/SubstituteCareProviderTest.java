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
public class SubstituteCareProviderTest implements PersistentTestTemplate {

  private static final ObjectMapper MAPPER = SystemCodeTestHarness.MAPPER;

  @Override
  @Test
  @Ignore
  public void testEqualsHashCodeWorks() throws Exception {
    EqualsVerifier.forClass(SubstituteCareProvider.class).suppress(Warning.NONFINAL_FIELDS)
        .verify();
  }

  /*
   * Constructor test
   */
  @Override
  @Test
  public void testEmptyConstructor() throws Exception {
    assertThat(SubstituteCareProvider.class.newInstance(), is(notNullValue()));
  }

  @Override
  @Test
  public void testPersistentConstructor() throws Exception {
    SubstituteCareProvider vsucp = validSubstituteCareProvider();

    SubstituteCareProvider persistent =
        new SubstituteCareProvider(vsucp.getId(), vsucp.getAdditionalPhoneNumber(),
            vsucp.getAdditionlPhoneExtensionNumber(), vsucp.getAnnualIncomeAmount(),
            vsucp.getBirthDate(), vsucp.getCaDriverLicenseNumber(), vsucp.getCityName(),
            vsucp.getEducationType(), vsucp.getEmailAddress(), vsucp.getEmployerName(),
            vsucp.getEmploymentStatusType(), vsucp.getEthUnableToDetReasonCode(),
            vsucp.getFirstName(), vsucp.getForeignAddressIndicatorVar(), vsucp.getGenderIndicator(),
            vsucp.getHispUnableToDetReasonCode(), vsucp.getHispanicOriginCode(),
            vsucp.getIndianTribeType(), vsucp.getLastName(), vsucp.getLisOwnershipIndicator(),
            vsucp.getLisPersonId(), vsucp.getMaritalStatusType(), vsucp.getMiddleInitialName(),
            vsucp.getNamePrefixDescription(), vsucp.getPassedBackgroundCheckCode(),
            vsucp.getPrimaryIncomeType(), vsucp.getResidedOutOfStateIndicator(),
            vsucp.getSecondaryIncomeType(), vsucp.getSocialSecurityNumber(),
            vsucp.getStateCodeType(), vsucp.getStreetName(), vsucp.getStreetNumber(),
            vsucp.getSuffixTitleDescription(), vsucp.getZipNumber(), vsucp.getZipSuffixNumber());

    assertThat(persistent.getId(), is(equalTo(vsucp.getId())));
    assertThat(persistent.getAdditionalPhoneNumber(),
        is(equalTo(vsucp.getAdditionalPhoneNumber())));
    assertThat(persistent.getAdditionlPhoneExtensionNumber(),
        is(equalTo(vsucp.getAdditionlPhoneExtensionNumber())));
    assertThat(persistent.getAnnualIncomeAmount(), is(equalTo(vsucp.getAnnualIncomeAmount())));
    assertThat(persistent.getBirthDate(), is(equalTo(vsucp.getBirthDate())));
    assertThat(persistent.getCaDriverLicenseNumber(),
        is(equalTo(vsucp.getCaDriverLicenseNumber())));
    assertThat(persistent.getCityName(), is(equalTo(vsucp.getCityName())));
    assertThat(persistent.getEducationType(), is(equalTo(vsucp.getEducationType())));
    assertThat(persistent.getEmailAddress(), is(equalTo(vsucp.getEmailAddress())));
    assertThat(persistent.getEmployerName(), is(equalTo(vsucp.getEmployerName())));
    assertThat(persistent.getEmploymentStatusType(), is(equalTo(vsucp.getEmploymentStatusType())));
    assertThat(persistent.getEthUnableToDetReasonCode(),
        is(equalTo(vsucp.getEthUnableToDetReasonCode())));
    assertThat(persistent.getFirstName(), is(equalTo(vsucp.getFirstName())));
    assertThat(persistent.getForeignAddressIndicatorVar(),
        is(equalTo(vsucp.getForeignAddressIndicatorVar())));
    assertThat(persistent.getGenderIndicator(), is(equalTo(vsucp.getGenderIndicator())));
    assertThat(persistent.getHispUnableToDetReasonCode(),
        is(equalTo(vsucp.getHispUnableToDetReasonCode())));
    assertThat(persistent.getHispanicOriginCode(), is(equalTo(vsucp.getHispanicOriginCode())));
    assertThat(persistent.getIndianTribeType(), is(equalTo(vsucp.getIndianTribeType())));
    assertThat(persistent.getLastName(), is(equalTo(vsucp.getLastName())));
    assertThat(persistent.getLisOwnershipIndicator(),
        is(equalTo(vsucp.getLisOwnershipIndicator())));
    assertThat(persistent.getLisPersonId(), is(equalTo(vsucp.getLisPersonId())));
    assertThat(persistent.getMaritalStatusType(), is(equalTo(vsucp.getMaritalStatusType())));
    assertThat(persistent.getMiddleInitialName(), is(equalTo(vsucp.getMiddleInitialName())));
    assertThat(persistent.getNamePrefixDescription(),
        is(equalTo(vsucp.getNamePrefixDescription())));
    assertThat(persistent.getPassedBackgroundCheckCode(),
        is(equalTo(vsucp.getPassedBackgroundCheckCode())));
    assertThat(persistent.getPrimaryIncomeType(), is(equalTo(vsucp.getPrimaryIncomeType())));
    assertThat(persistent.getResidedOutOfStateIndicator(),
        is(equalTo(vsucp.getResidedOutOfStateIndicator())));
    assertThat(persistent.getSecondaryIncomeType(), is(equalTo(vsucp.getSecondaryIncomeType())));
    assertThat(persistent.getSocialSecurityNumber(), is(equalTo(vsucp.getSocialSecurityNumber())));
    assertThat(persistent.getStateCodeType(), is(equalTo(vsucp.getStateCodeType())));
    assertThat(persistent.getStreetName(), is(equalTo(vsucp.getStreetName())));
    assertThat(persistent.getStreetNumber(), is(equalTo(vsucp.getStreetNumber())));
    assertThat(persistent.getSuffixTitleDescription(),
        is(equalTo(vsucp.getSuffixTitleDescription())));
    assertThat(persistent.getZipNumber(), is(equalTo(vsucp.getZipNumber())));
    assertThat(persistent.getZipSuffixNumber(), is(equalTo(vsucp.getZipSuffixNumber())));

  }

  @Override
  public void testConstructorUsingDomain() throws Exception {
    // no domain class
  }

  @SuppressWarnings("javadoc")
  @Test
  public void testSerializeAndDeserialize() throws Exception {
    SubstituteCareProvider vsucp = validSubstituteCareProvider();

    SubstituteCareProvider persistent =
        new SubstituteCareProvider(vsucp.getId(), vsucp.getAdditionalPhoneNumber(),
            vsucp.getAdditionlPhoneExtensionNumber(), vsucp.getAnnualIncomeAmount(),
            vsucp.getBirthDate(), vsucp.getCaDriverLicenseNumber(), vsucp.getCityName(),
            vsucp.getEducationType(), vsucp.getEmailAddress(), vsucp.getEmployerName(),
            vsucp.getEmploymentStatusType(), vsucp.getEthUnableToDetReasonCode(),
            vsucp.getFirstName(), vsucp.getForeignAddressIndicatorVar(), vsucp.getGenderIndicator(),
            vsucp.getHispUnableToDetReasonCode(), vsucp.getHispanicOriginCode(),
            vsucp.getIndianTribeType(), vsucp.getLastName(), vsucp.getLisOwnershipIndicator(),
            vsucp.getLisPersonId(), vsucp.getMaritalStatusType(), vsucp.getMiddleInitialName(),
            vsucp.getNamePrefixDescription(), vsucp.getPassedBackgroundCheckCode(),
            vsucp.getPrimaryIncomeType(), vsucp.getResidedOutOfStateIndicator(),
            vsucp.getSecondaryIncomeType(), vsucp.getSocialSecurityNumber(),
            vsucp.getStateCodeType(), vsucp.getStreetName(), vsucp.getStreetNumber(),
            vsucp.getSuffixTitleDescription(), vsucp.getZipNumber(), vsucp.getZipSuffixNumber());

    final String expected = MAPPER.writeValueAsString((MAPPER.readValue(
        fixture("fixtures/persistent/SubstituteCareProvider/valid/validWithSysCodes.json"),
        SubstituteCareProvider.class)));

    assertThat(MAPPER.writeValueAsString(persistent)).isEqualTo(expected);
  }

  private SubstituteCareProvider validSubstituteCareProvider()
      throws JsonParseException, JsonMappingException, IOException {

    SubstituteCareProvider validSubstituteCareProvider =
        MAPPER.readValue(fixture("fixtures/persistent/SubstituteCareProvider/valid/valid.json"),
            SubstituteCareProvider.class);

    return validSubstituteCareProvider;
  }

}
