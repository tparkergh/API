package gov.ca.cwds.rest.services.referentialintegrity;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.data.cms.ReferralDao;
import gov.ca.cwds.fixture.AssignmentResourceBuilder;
import gov.ca.cwds.fixture.ReferralResourceBuilder;
import gov.ca.cwds.rest.api.domain.cms.Assignment;
import gov.ca.cwds.rest.api.domain.cms.Referral;
import gov.ca.cwds.rest.validation.ReferentialIntegrityException;

/**
 * @author CWDS API Team
 *
 */
@SuppressWarnings("javadoc")
public class RIAssignmentTest {

  private ReferralDao referralDao;

  @Before
  public void setup() {
    referralDao = mock(ReferralDao.class);
  }

  @Test
  public void type() throws Exception {
    assertThat(RIAssignment.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    RIAssignment target = new RIAssignment(referralDao);
    assertThat(target, notNullValue());
  }

  /*
   * Test for test the referential Integrity Exception
   * 
   */
  @Test(expected = ReferentialIntegrityException.class)
  public void testForReferentialIntegrityException() throws Exception {
    Assignment assignmentDomain = new AssignmentResourceBuilder().buildAssignment();
    gov.ca.cwds.data.persistence.cms.Assignment assignment =
        new gov.ca.cwds.data.persistence.cms.Assignment("ABC1234567", assignmentDomain, "0X5");
    when(referralDao.find(any(String.class))).thenReturn(null);
    RIAssignment target = new RIAssignment(referralDao);
    target.apply(assignment);
  }

  /*
   * test success
   */
  @Test
  public void testForApplyTrue() throws Exception {
    Assignment assignmentDomain = new AssignmentResourceBuilder().buildAssignment();
    gov.ca.cwds.data.persistence.cms.Assignment assignment =
        new gov.ca.cwds.data.persistence.cms.Assignment("ABC1234567", assignmentDomain, "0X5");
    Referral domain = new ReferralResourceBuilder().build();
    gov.ca.cwds.data.persistence.cms.Referral referral =
        new gov.ca.cwds.data.persistence.cms.Referral("ABC1234567", domain, "0X5");

    when(referralDao.find(any(String.class))).thenReturn(referral);
    RIAssignment target = new RIAssignment(referralDao);
    Boolean result = target.apply(assignment);
    assertThat(result, is(equalTo(true)));
  }

  /*
   * test success when assignment establishedCode is C
   */
  @Test
  public void testForApplyTrueWhenEstablishedCodeC() throws Exception {
    Assignment assignmentDomain =
        new AssignmentResourceBuilder().setEstablishedForCode("C").buildAssignment();
    gov.ca.cwds.data.persistence.cms.Assignment assignment =
        new gov.ca.cwds.data.persistence.cms.Assignment("ABC1234567", assignmentDomain, "0X5");
    Referral domain = new ReferralResourceBuilder().build();
    gov.ca.cwds.data.persistence.cms.Referral referral =
        new gov.ca.cwds.data.persistence.cms.Referral("ABC1234567", domain, "0X5");

    when(referralDao.find(any(String.class))).thenReturn(referral);
    RIAssignment target = new RIAssignment(referralDao);
    Boolean result = target.apply(assignment);
    assertThat(result, is(equalTo(true)));
  }

}
