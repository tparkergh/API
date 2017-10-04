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


/**
 * @author CWDS API Team
 */
public class ServiceProviderTest {

  private static final ObjectMapper MAPPER = SystemCodeTestHarness.MAPPER;

  /*
   * Constructor test
   */
  @SuppressWarnings("javadoc")
  @Test
  public void emtpyConstructorIsNotNull() throws Exception {
    assertThat(ServiceProvider.class.newInstance(), is(notNullValue()));
  }

  @SuppressWarnings("javadoc")
  @Test
  public void testPersistentConstructor() throws Exception {
    ServiceProvider vsprv = validServiceProvider();

    ServiceProvider pers = new ServiceProvider(vsprv.getAgencyName(),
        vsprv.getArchiveAssociationIndicator(), vsprv.getCityName(), vsprv.getEmailAddress(),
        vsprv.getFaxNumber(), vsprv.getFirstName(), vsprv.getId(), vsprv.getLastName(),
        vsprv.getNamePrefixDescription(), vsprv.getPhoneExtensionNumber(),
        vsprv.getPhoneNumberAsDecimal(), vsprv.getPositionTitleDescription(),
        vsprv.getServiceProviderType(), vsprv.getStateCodeType(), vsprv.getStreetName(),
        vsprv.getStreetNumber(), vsprv.getSuffixTitleDescription(), vsprv.getZipNumber(),
        vsprv.getZipSuffixNumber());

    assertThat(pers.getAgencyName(), is(equalTo(vsprv.getAgencyName())));
    assertThat(pers.getArchiveAssociationIndicator(),
        is(equalTo(vsprv.getArchiveAssociationIndicator())));
    assertThat(pers.getCityName(), is(equalTo(vsprv.getCityName())));
    assertThat(pers.getEmailAddress(), is(equalTo(vsprv.getEmailAddress())));
    assertThat(pers.getFaxNumber(), is(equalTo(vsprv.getFaxNumber())));
    assertThat(pers.getFirstName(), is(equalTo(vsprv.getFirstName())));
    assertThat(pers.getId(), is(equalTo(vsprv.getId())));
    assertThat(pers.getLastName(), is(equalTo(vsprv.getLastName())));
    assertThat(pers.getNamePrefixDescription(), is(equalTo(vsprv.getNamePrefixDescription())));
    assertThat(pers.getPhoneExtensionNumber(), is(equalTo(vsprv.getPhoneExtensionNumber())));
    assertThat(pers.getPhoneNumber(), is(equalTo(vsprv.getPhoneNumber())));
    assertThat(pers.getPositionTitleDescription(),
        is(equalTo(vsprv.getPositionTitleDescription())));
    assertThat(pers.getServiceProviderType(), is(equalTo(vsprv.getServiceProviderType())));
    assertThat(pers.getStateCodeType(), is(equalTo(vsprv.getStateCodeType())));
    assertThat(pers.getStreetName(), is(equalTo(vsprv.getStreetName())));
    assertThat(pers.getStreetNumber(), is(equalTo(vsprv.getStreetNumber())));
    assertThat(pers.getSuffixTitleDescription(), is(equalTo(vsprv.getSuffixTitleDescription())));
    assertThat(pers.getZipNumber(), is(equalTo(vsprv.getZipNumber())));
    assertThat(pers.getZipSuffixNumber(), is(equalTo(vsprv.getZipSuffixNumber())));
  }

  @SuppressWarnings("javadoc")
  @Test
  public void testSerializeAndDeserialize() throws Exception {
    ServiceProvider vsprv = validServiceProvider();

    ServiceProvider pers = new ServiceProvider(vsprv.getAgencyName(),
        vsprv.getArchiveAssociationIndicator(), vsprv.getCityName(), vsprv.getEmailAddress(),
        vsprv.getFaxNumber(), vsprv.getFirstName(), vsprv.getId(), vsprv.getLastName(),
        vsprv.getNamePrefixDescription(), vsprv.getPhoneExtensionNumber(),
        vsprv.getPhoneNumberAsDecimal(), vsprv.getPositionTitleDescription(),
        vsprv.getServiceProviderType(), vsprv.getStateCodeType(), vsprv.getStreetName(),
        vsprv.getStreetNumber(), vsprv.getSuffixTitleDescription(), vsprv.getZipNumber(),
        vsprv.getZipSuffixNumber());

    final String expected = MAPPER.writeValueAsString((MAPPER.readValue(
        fixture("fixtures/persistent/ServiceProvider/valid/validWithSysCodes.json"),
        ServiceProvider.class)));

    assertThat(MAPPER.writeValueAsString(pers)).isEqualTo(expected);
  }

  private ServiceProvider validServiceProvider()
      throws JsonParseException, JsonMappingException, IOException {

    ServiceProvider validServiceProvider = MAPPER.readValue(
        fixture("fixtures/persistent/ServiceProvider/valid/valid.json"), ServiceProvider.class);

    return validServiceProvider;
  }

}
