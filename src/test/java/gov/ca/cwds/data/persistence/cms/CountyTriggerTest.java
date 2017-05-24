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
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

/**
 * @author CWDS API Team
 *
 */
public class CountyTriggerTest implements PersistentTestTemplate {

  private String countyOwnershipT = "1234567ABC";

  private static final ObjectMapper MAPPER = SystemCodeTestHarness.MAPPER;

  /*
   * Constructor test
   */
  @Override
  @Test
  public void testEmptyConstructor() throws Exception {
    assertThat(CountyTrigger.class.newInstance(), is(notNullValue()));
  }

  @Override
  @Test
  public void testPersistentConstructor() throws Exception {

    CountyTrigger vct = validCountyTrigger();

    CountyTrigger persistent = new CountyTrigger(countyOwnershipT, vct.getLogicId(),
        vct.getCountyOwnership0(), vct.getIntegratorTimeStamp(), vct.getIntegratorEntity());

    assertThat(persistent.getCountyOwnershipT(), is(equalTo(countyOwnershipT)));
    assertThat(persistent.getLogicId(), is(equalTo(vct.getLogicId())));
    assertThat(persistent.getCountyOwnership0(), is(equalTo(vct.getCountyOwnership0())));
    assertThat(persistent.getIntegratorEntity(), is(equalTo(vct.getIntegratorEntity())));

  }

  @Override
  @Test
  public void testEqualsHashCodeWorks() {
    EqualsVerifier.forClass(CountyTrigger.class).suppress(Warning.NONFINAL_FIELDS).verify();

  }

  private CountyTrigger validCountyTrigger()
      throws JsonParseException, JsonMappingException, IOException {

    CountyTrigger validCountyTrigger = MAPPER.readValue(
        fixture("fixtures/persistent/CountyOwnership/valid/valid.json"), CountyTrigger.class);
    return validCountyTrigger;

  }

  @Override
  public void testConstructorUsingDomain() throws Exception {
    // No Domain Class
  }

}
