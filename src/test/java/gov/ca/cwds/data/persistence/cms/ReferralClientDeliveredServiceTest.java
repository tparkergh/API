package gov.ca.cwds.data.persistence.cms;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import java.io.IOException;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

/**
 * @author CWDS API Team
 *
 */
public class ReferralClientDeliveredServiceTest {

  private String deliveredServiceId = "ABC1234567";
  private String referralId = "ABX1234560";
  private String clientId = "APc109852u";

  /**
   * Constructor test
   * 
   * @throws Exception - exception
   */
  @Test
  public void testEmptyConstructor() throws Exception {
    assertThat(ReferralClientDeliveredService.class.newInstance(), is(notNullValue()));
  }

  /**
   * @throws Exception - exception
   */
  @Test
  public void testPersistentConstructor() throws Exception {

    ReferralClientDeliveredService valid = validReferralClientDeliveredService();

    ReferralClientDeliveredService persistent = new ReferralClientDeliveredService(
        deliveredServiceId, referralId, clientId, valid.getCountySpecificCode());

    assertThat(persistent.getCountySpecificCode(), is(equalTo(valid.getCountySpecificCode())));
  }

  private ReferralClientDeliveredService validReferralClientDeliveredService()
      throws JsonParseException, JsonMappingException, IOException {

    ReferralClientDeliveredService validReferralClientDeliveredService =
        new ReferralClientDeliveredService(deliveredServiceId, referralId, clientId, "99");
    return validReferralClientDeliveredService;

  }

}
