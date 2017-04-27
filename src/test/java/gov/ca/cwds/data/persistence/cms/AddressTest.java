package gov.ca.cwds.data.persistence.cms;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import java.io.IOException;
import java.math.BigDecimal;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import gov.ca.cwds.data.persistence.junit.template.PersistentTestTemplate;
import gov.ca.cwds.rest.api.domain.DomainChef;

/**
 * 
 * @author CWDS API Team
 */
public class AddressTest implements PersistentTestTemplate {

  private String id = "1234567ABC";
  private String lastUpdatedId = "0X5";
  private String cityName = "Sacramento";
  private String description = "test CWS address";
  private int emergencyPhoneExtension = 1234;
  private BigDecimal emergencyPhoneNumber = new BigDecimal(9876543);
  private String foreignAddressIndicator = "N";
  private Short governmentEntityType = 99;
  private int messagePhoneExtension = 1234;
  private BigDecimal messagePhoneNumber = new BigDecimal(9876543);
  private String otherHeaderAddress = "";
  private String postDirectionTextCode = "";
  private String preDirectionTextCode = "";
  private int primaryPhoneNumberExtension = 4321;
  private BigDecimal primaryPhoneNumber = new BigDecimal(8765432);
  private Short stateCodeType = 99;
  private String streetName = "First Street";
  private String streetNumber = "1234";
  private Short streetSuffixType = 0;
  private Short unitDesignatorType = 0;
  private String unitNumber = "";
  private String zip = "95666";
  private Short zipSuffix = 1234;


  private static final ObjectMapper MAPPER = SystemCodeTestHarness.MAPPER;

  /*
   * Constructor test
   */
  @Override
  @Test
  public void testEmptyConstructor() throws Exception {
    assertThat(Client.class.newInstance(), is(notNullValue()));
  }

  @Override
  @Test
  public void testPersistentConstructor() throws Exception {

    gov.ca.cwds.data.persistence.cms.Address pa = new gov.ca.cwds.data.persistence.cms.Address(id,
        cityName, emergencyPhoneNumber, emergencyPhoneExtension, foreignAddressIndicator,
        governmentEntityType, messagePhoneNumber, messagePhoneExtension, otherHeaderAddress,
        primaryPhoneNumber, primaryPhoneNumberExtension, stateCodeType, streetName, streetNumber,
        zip, description, zipSuffix, postDirectionTextCode, preDirectionTextCode, streetSuffixType,
        unitDesignatorType, unitNumber);

    assertThat(pa.getId(), is(equalTo(id)));
    assertThat(pa.getAddressDescription(), is(equalTo(description)));
    assertThat(pa.getCity(), is(equalTo(cityName)));
    assertThat(pa.getEmergencyExtension(), is(equalTo(emergencyPhoneExtension)));
    assertThat(pa.getEmergencyNumber(), is(equalTo(emergencyPhoneNumber)));
    assertThat(pa.getFrgAdrtB(), is(equalTo(foreignAddressIndicator)));
    assertThat(pa.getGovernmentEntityCd(), is(equalTo(governmentEntityType)));
    assertThat(pa.getMessageExtension(), is(equalTo(messagePhoneExtension)));
    assertThat(pa.getMessageNumber(), is(equalTo(messagePhoneNumber)));
    assertThat(pa.getHeaderAddress(), is(equalTo(otherHeaderAddress)));
    assertThat(pa.getPostDirCd(), is(equalTo(postDirectionTextCode)));
    assertThat(pa.getPreDirCd(), is(equalTo(preDirectionTextCode)));
    assertThat(pa.getPrimaryExtension(), is(equalTo(primaryPhoneNumberExtension)));
    assertThat(pa.getPrimaryNumber(), is(equalTo(primaryPhoneNumber)));
    assertThat(pa.getStateCd(), is(equalTo(stateCodeType)));
    assertThat(pa.getStreetName(), is(equalTo(streetName)));
    assertThat(pa.getStreetNumber(), is(equalTo(streetNumber)));
    assertThat(pa.getStreetSuffixCd(), is(equalTo(streetSuffixType)));
    assertThat(pa.getUnitDesignationCd(), is(equalTo(unitDesignatorType)));
    assertThat(pa.getUnitNumber(), is(equalTo(unitNumber)));
    assertThat(pa.getZip(), is(equalTo(zip.toString())));
    assertThat(pa.getZip4(), is(equalTo(zipSuffix)));
  }

  @Override
  @Test
  public void testConstructorUsingDomain() throws Exception {
    gov.ca.cwds.rest.api.domain.cms.Address da = validDomainAddress();
    gov.ca.cwds.data.persistence.cms.Address pa =
        new gov.ca.cwds.data.persistence.cms.Address(id, da, lastUpdatedId);

    assertThat(pa.getId(), is(equalTo(id)));
    assertThat(pa.getLastUpdatedId(), is(equalTo(lastUpdatedId)));
    assertThat(pa.getAddressDescription(), is(equalTo(da.getAddressDescription())));
    assertThat(pa.getCity(), is(equalTo(da.getCity())));
    assertThat(pa.getEmergencyExtension(), is(equalTo(da.getEmergencyExtension())));
    assertThat(pa.getEmergencyNumber(), is(equalTo(da.getEmergencyNumber())));
    assertThat(pa.getFrgAdrtB(), is(equalTo(DomainChef.cookBoolean(da.getFrgAdrtB()))));
    assertThat(pa.getGovernmentEntityCd(), is(equalTo(da.getGovernmentEntityCd())));
    assertThat(pa.getMessageExtension(), is(equalTo(da.getMessageExtension())));
    assertThat(pa.getMessageNumber(), is(equalTo(da.getMessageNumber())));
    assertThat(pa.getHeaderAddress(), is(equalTo(da.getHeaderAddress())));
    assertThat(pa.getPostDirCd(), is(equalTo(da.getPostDirCd())));
    assertThat(pa.getPreDirCd(), is(equalTo(da.getPreDirCd())));
    assertThat(pa.getPrimaryExtension(), is(equalTo(da.getPrimaryExtension())));
    assertThat(pa.getPrimaryNumber(), is(equalTo(da.getPrimaryNumber())));
    assertThat(pa.getStateCd(), is(equalTo(da.getState())));
    assertThat(pa.getStreetName(), is(equalTo(da.getStreetName())));
    assertThat(pa.getStreetNumber(), is(equalTo(da.getStreetNumber())));
    assertThat(pa.getStreetSuffixCd(), is(equalTo(da.getStreetSuffixCd())));
    assertThat(pa.getUnitDesignationCd(), is(equalTo(da.getUnitDesignationCd())));
    assertThat(pa.getUnitNumber(), is(equalTo(da.getUnitNumber())));
    assertThat(pa.getZip(), is(equalTo(da.getZip().toString())));
    assertThat(pa.getZip4(), is(equalTo(da.getZip4())));
  }

  @Override
  // @Test
  public void testEqualsHashCodeWorks() throws Exception {
    // EqualsVerifier.forClass(gov.ca.cwds.data.persistence.cms.Address.class)
    // .suppress(Warning.NONFINAL_FIELDS).verify();
  }

  private gov.ca.cwds.rest.api.domain.cms.Address validDomainAddress()
      throws JsonParseException, JsonMappingException, IOException {

    gov.ca.cwds.rest.api.domain.cms.Address validDomainAddress =
        MAPPER.readValue(fixture("fixtures/domain/legacy/Address/valid/validAddress.json"),
            gov.ca.cwds.rest.api.domain.cms.Address.class);

    return validDomainAddress;
  }
}
