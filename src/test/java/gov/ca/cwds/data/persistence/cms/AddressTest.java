package gov.ca.cwds.data.persistence.cms;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import java.math.BigDecimal;

import org.junit.Test;

import gov.ca.cwds.data.persistence.junit.template.PersistentTestTemplate;
import gov.ca.cwds.fixture.AddressEntityBuilder;
import gov.ca.cwds.fixture.CmsAddressResourceBuilder;
import gov.ca.cwds.rest.api.domain.DomainChef;

/**
 * 
 * @author CWDS API Team
 */
public class AddressTest implements PersistentTestTemplate {

  private String id = "ABc1r90Pg6";
  private String lastUpdatedId = "0X5";
  private String cityName = "Sacramento";
  private String description = "test CWS address";
  private int emergencyPhoneExtension = 0;
  private BigDecimal emergencyPhoneNumber = new BigDecimal(0);
  private String foreignAddressIndicator = "N";
  private Short governmentEntityType = 0;
  private int messagePhoneExtension = 0;
  private BigDecimal messagePhoneNumber = new BigDecimal(0);
  private String otherHeaderAddress = "";
  private String postDirectionTextCode = "";
  private String preDirectionTextCode = "";
  private int primaryPhoneNumberExtension = 0;
  private BigDecimal primaryPhoneNumber = new BigDecimal(0);
  private Short stateCodeType = 1828;
  private String streetName = "First Street";
  private String streetNumber = "1";
  private Short streetSuffixType = 1;
  private Short unitDesignatorType = 1;
  private String unitNumber = "";
  private String zip = "98765";
  private Short zipSuffix = 0;

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

    gov.ca.cwds.data.persistence.cms.Address pa = new AddressEntityBuilder().build();

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
    gov.ca.cwds.rest.api.domain.cms.Address da = new CmsAddressResourceBuilder().buildCmsAddress();
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

}
