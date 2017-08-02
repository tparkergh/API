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

/**
 * @author CWDS API Team
 *
 */
public class IndividualDeliveredServiceTest {

  private String deliveredServiceId = "ABC1234567";
  private String deliveredToIndividualCode = "C";
  private String deliveredToIndividualId = "AVC1098765";

  /**
   * Constructor test
   * 
   * @throws Exception - exception
   */
  @Test
  public void testEmptyConstructor() throws Exception {
    assertThat(IndividualDeliveredServiceEntity.class.newInstance(), is(notNullValue()));
  }

  /**
   * @throws Exception - exception
   */
  @Test
  public void testPersistentConstructor() throws Exception {

    IndividualDeliveredServiceEntity valid = validIndividualDeliveredService();

    IndividualDeliveredServiceEntity persistent = new IndividualDeliveredServiceEntity(deliveredServiceId,
        deliveredToIndividualCode, deliveredToIndividualId, valid.getCountySpecificCode(),
        valid.getEndDate(), valid.getServiceContactType(), valid.getStartDate());

    assertThat(persistent.getCountySpecificCode(), is(equalTo(valid.getCountySpecificCode())));
    assertThat(persistent.getServiceContactType(), is(equalTo(valid.getServiceContactType())));
    assertThat(persistent.getEndDate(), is(equalTo(valid.getEndDate())));
    assertThat(persistent.getStartDate(), is(equalTo(valid.getStartDate())));
  }

  private IndividualDeliveredServiceEntity validIndividualDeliveredService()
      throws JsonParseException, JsonMappingException, IOException {

    IndividualDeliveredServiceEntity validIndividualDeliveredService =
        new IndividualDeliveredServiceEntity(deliveredServiceId, deliveredToIndividualCode,
            deliveredToIndividualId, "99", new Date(), (short) 420, new Date());
    return validIndividualDeliveredService;

  }


}
