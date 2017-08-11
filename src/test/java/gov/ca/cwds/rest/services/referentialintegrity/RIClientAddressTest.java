package gov.ca.cwds.rest.services.referentialintegrity;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import gov.ca.cwds.data.cms.AddressDao;
import gov.ca.cwds.data.cms.ClientDao;
import gov.ca.cwds.data.cms.ReferralDao;

/**
 * @author CWDS API Team
 *
 */
public class RIClientAddressTest {

  /**
   * @throws Exception - exception
   */
  @Test
  public void type() throws Exception {
    assertThat(RIClientAddress.class, notNullValue());
  }

  /**
   * @throws Exception- exception
   */
  @Test
  public void instantiation() throws Exception {
    AddressDao addressDao = null;
    ClientDao clientDao = null;
    ReferralDao referralDao = null;
    RIClientAddress target = new RIClientAddress(addressDao, clientDao, referralDao);
    assertThat(target, notNullValue());
  }

}
