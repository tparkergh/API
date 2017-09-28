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

/**
 * @author CWDS API Team
 *
 */
public class AddressUcTest {


  private static final ObjectMapper MAPPER = SystemCodeTestHarness.MAPPER;

  /**
   * Constructor test
   * 
   * @throws Exception test general
   */
  @Test
  public void testEmptyConstructor() throws Exception {
    assertThat(AddressUc.class.newInstance(), is(notNullValue()));
  }

  @Test
  public void testPersistentConstructor() throws Exception {
    AddressUc aduc = validAddressUc();

    gov.ca.cwds.data.persistence.cms.AddressUc persistent =
        new gov.ca.cwds.data.persistence.cms.AddressUc(aduc.getPktableId(),
            aduc.getSourceTableCode(), aduc.getCityName(), aduc.getStreetName(),
            aduc.getStreetNumber());

    assertThat(persistent.getPktableId(), is(equalTo(aduc.getPktableId())));
    assertThat(persistent.getSourceTableCode(), is(equalTo(aduc.getSourceTableCode())));
    assertThat(persistent.getCityName(), is(equalTo(aduc.getCityName())));
    assertThat(persistent.getStreetName(), is(equalTo(aduc.getStreetName())));
    assertThat(persistent.getStreetNumber(), is(equalTo(aduc.getStreetNumber())));
  }

  private AddressUc validAddressUc() throws JsonParseException, JsonMappingException, IOException {

    AddressUc validAddressUc = MAPPER
        .readValue(fixture("fixtures/persistent/AddressUc/valid/valid.json"), AddressUc.class);
    return validAddressUc;
  }

}
