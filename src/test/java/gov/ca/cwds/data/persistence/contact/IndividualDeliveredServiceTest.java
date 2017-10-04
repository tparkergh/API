package gov.ca.cwds.data.persistence.contact;

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
  private String lastUpdatedId = "0X5";
  private Date lastUpdatedTime = new Date();


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

    IndividualDeliveredServiceEntity individualDeliveredServiceEntity =
        new IndividualDeliveredServiceEntity(deliveredServiceId, deliveredToIndividualCode,
            deliveredToIndividualId, valid.getCountySpecificCode(), valid.getEndDate(),
            valid.getServiceContactType(), valid.getStartDate(), lastUpdatedId, lastUpdatedTime);

    assertThat(individualDeliveredServiceEntity.getCountySpecificCode(),
        is(equalTo(valid.getCountySpecificCode())));
    assertThat(individualDeliveredServiceEntity.getServiceContactType(),
        is(equalTo(valid.getServiceContactType())));
    assertThat(individualDeliveredServiceEntity.getEndDate(), is(equalTo(valid.getEndDate())));
    assertThat(individualDeliveredServiceEntity.getStartDate(), is(equalTo(valid.getStartDate())));
    assertThat(individualDeliveredServiceEntity.getLastUpdatedId(),
        is(equalTo(valid.getLastUpdatedId())));
    assertThat(individualDeliveredServiceEntity.getLastUpdatedTime(),
        is(equalTo(valid.getLastUpdatedTime())));
  }

  private IndividualDeliveredServiceEntity validIndividualDeliveredService()
      throws JsonParseException, JsonMappingException, IOException {

    IndividualDeliveredServiceEntity validIndividualDeliveredService =
        new IndividualDeliveredServiceEntity(deliveredServiceId, deliveredToIndividualCode,
            deliveredToIndividualId, "99", new Date(), (short) 420, new Date(), lastUpdatedId,
            lastUpdatedTime);
    return validIndividualDeliveredService;

  }


}
