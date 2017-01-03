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


public class ServiceProviderTest {

  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

  @Test
  public void equalsHashCodeWork() {
    EqualsVerifier.forClass(ServiceProvider.class).suppress(Warning.NONFINAL_FIELDS).verify();
  }

  /*
   * Constructor test
   */
  @Test
  public void emtpyConstructorIsNotNull() throws Exception {
    assertThat(ServiceProvider.class.newInstance(), is(notNullValue()));
  }

  @Test
  public void persistentConstructorTest() throws Exception {
    ServiceProvider vsprv = validServiceProvider();

    ServiceProvider pers = new ServiceProvider(vsprv.getAgencyName(),
        vsprv.getArchiveAssociationIndicator(), vsprv.getCityName(), vsprv.getEmailAddress(),
        vsprv.getFaxNumber(), vsprv.getFirstName(), vsprv.getId(), vsprv.getLastName(),
        vsprv.getNamePrefixDescription(), vsprv.getPhoneExtensionNumber(), vsprv.getPhoneNumber(),
        vsprv.getPositionTitleDescription(), vsprv.getServiceProviderType(),
        vsprv.getStateCodeType(), vsprv.getStreetName(), vsprv.getStreetNumber(),
        vsprv.getSuffixTitleDescription(), vsprv.getZipNumber(), vsprv.getZipSuffixNumber());

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

  private ServiceProvider validServiceProvider()
      throws JsonParseException, JsonMappingException, IOException {

    ServiceProvider validServiceProvider = MAPPER.readValue(
        fixture("fixtures/domain/legacy/ServiceProvider/valid/valid.json"), ServiceProvider.class);

    return validServiceProvider;
  }
}
