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
public class ReferralClientDeliveredServiceEntityTest {

  private String deliveredServiceId = "ABC1234567";
  private String referralId = "ABX1234560";
  private String clientId = "APc109852u";
  private String lastUpdatedId = "0X5";
  private Date lastUpdatedTime = new Date();


  /**
   * Constructor test
   * 
   * @throws Exception - exception
   */
  @Test
  public void testEmptyConstructor() throws Exception {
    assertThat(ReferralClientDeliveredServiceEntity.class.newInstance(), is(notNullValue()));
  }

  /**
   * @throws Exception - exception
   */
  @Test
  public void testPersistentConstructor() throws Exception {

    ReferralClientDeliveredServiceEntity valid = validReferralClientDeliveredService();

    ReferralClientDeliveredServiceEntity referralClientDeliveredServiceEntity =
        new ReferralClientDeliveredServiceEntity(deliveredServiceId, referralId, clientId,
            valid.getCountySpecificCode(), lastUpdatedId, lastUpdatedTime);

    assertThat(referralClientDeliveredServiceEntity.getCountySpecificCode(),
        is(equalTo(valid.getCountySpecificCode())));
  }

  private ReferralClientDeliveredServiceEntity validReferralClientDeliveredService()
      throws JsonParseException, JsonMappingException, IOException {

    ReferralClientDeliveredServiceEntity validReferralClientDeliveredService =
        new ReferralClientDeliveredServiceEntity(deliveredServiceId, referralId, clientId, "99",
            lastUpdatedId, lastUpdatedTime);
    return validReferralClientDeliveredService;

  }

}
