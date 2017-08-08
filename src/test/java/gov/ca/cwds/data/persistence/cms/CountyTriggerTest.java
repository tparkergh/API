package gov.ca.cwds.data.persistence.cms;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import java.io.IOException;
import java.util.Date;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

/**
 * @author CWDS API Team
 *
 */
public class CountyTriggerTest {

  private String countyOwnershipT = "1234567ABC";
  private String countyOwnership0 = "C";
  private String integratorEntity = "REFR_CLT";
  private String logicId = "99";

  /**
   * EmptyConstructor test
   * 
   * @throws Exception - exception
   */
  @Test
  public void testEmptyConstructor() throws Exception {
    assertThat(CountyTrigger.class.newInstance(), is(notNullValue()));
  }

  /**
   * @throws Exception - exception
   */
  @Test
  public void testPersistentConstructor() throws Exception {

    CountyTrigger countyTrigger = validCountyTrigger();

    CountyTrigger persistent = new CountyTrigger(logicId, countyOwnership0, countyOwnershipT,
        integratorEntity, countyTrigger.getIntegratorTimeStamp());

    assertThat(persistent.getIntegratorTimeStamp(),
        is(equalTo(countyTrigger.getIntegratorTimeStamp())));

  }

  /**
   * 
   */
  @Test
  public void testEqualsHashCodeWorks() {
    EqualsVerifier.forClass(CountyTrigger.class).suppress(Warning.NONFINAL_FIELDS).verify();

  }

  private CountyTrigger validCountyTrigger()
      throws JsonParseException, JsonMappingException, IOException {

    CountyTrigger validCountyTrigger =
        new CountyTrigger("99", "C", countyOwnershipT, "REFR_CLT", new Date());
    return validCountyTrigger;

  }

}
