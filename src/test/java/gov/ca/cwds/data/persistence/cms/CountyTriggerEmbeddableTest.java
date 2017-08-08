package gov.ca.cwds.data.persistence.cms;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import java.io.IOException;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

/**
 * @author CWDS API Team
 *
 */
public class CountyTriggerEmbeddableTest {

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
    assertThat(CountyTriggerEmbeddable.class.newInstance(), is(notNullValue()));
  }

  /**
   * 
   */
  @Test
  public void testEqualsHashCodeWorks() {
    EqualsVerifier.forClass(CountyTriggerEmbeddable.class).suppress(Warning.NONFINAL_FIELDS)
        .verify();
  }

  /**
   * @throws Exception - exception
   */
  @Test
  public void testPersistentConstructor() throws Exception {

    CountyTriggerEmbeddable valid = validCountyTriggerEmbeddable();

    CountyTriggerEmbeddable countyTriggerEmbeddable =
        new CountyTriggerEmbeddable(valid.getLogicId(), valid.getCountyOwnership0(),
            valid.getCountyOwnershipT(), valid.getIntegratorEntity());

    assertThat(countyTriggerEmbeddable.getLogicId(), is(equalTo(valid.getLogicId())));
    assertThat(countyTriggerEmbeddable.getCountyOwnership0(),
        is(equalTo(valid.getCountyOwnership0())));
    assertThat(countyTriggerEmbeddable.getCountyOwnershipT(),
        is(equalTo(valid.getCountyOwnershipT())));
    assertThat(countyTriggerEmbeddable.getIntegratorEntity(),
        is(equalTo(valid.getIntegratorEntity())));

  }

  private CountyTriggerEmbeddable validCountyTriggerEmbeddable()
      throws JsonParseException, JsonMappingException, IOException {

    CountyTriggerEmbeddable validCountyTriggerEmbeddable =
        new CountyTriggerEmbeddable(logicId, countyOwnership0, countyOwnershipT, integratorEntity);
    return validCountyTriggerEmbeddable;

  }

}
