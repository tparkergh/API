package gov.ca.cwds.rest.services.referentialintegrity;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import gov.ca.cwds.data.cms.ReferralDao;

/**
 * @author CWDS API Team
 *
 */
public class RIAssignmentTest {

  @Test
  public void type() throws Exception {
    assertThat(RIAssignment.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    ReferralDao referralDao = null;
    RIAssignment target = new RIAssignment(referralDao);
    assertThat(target, notNullValue());
  }

}
