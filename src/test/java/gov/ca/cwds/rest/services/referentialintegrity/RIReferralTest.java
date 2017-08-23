package gov.ca.cwds.rest.services.referentialintegrity;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import gov.ca.cwds.data.cms.AddressDao;
import gov.ca.cwds.data.cms.DrmsDocumentDao;
import gov.ca.cwds.data.cms.LongTextDao;
import gov.ca.cwds.data.cms.ReferralDao;
import gov.ca.cwds.data.cms.StaffPersonDao;
import gov.ca.cwds.data.persistence.cms.Referral;
import gov.ca.cwds.rest.validation.ReferentialIntegrityException;

/**
 * @author Tabpcenc
 *
 */
public class RIReferralTest {

  /**
   * 
   */
  @Rule
  public ExpectedException expectedException = ExpectedException.none();

  /**
   * @throws Exception generic error
   */
  @Test
  public void type() throws Exception {
    assertThat(RIReferral.class, notNullValue());
  }

  /**
   * @throws Exception generic error
   */
  @Test
  public void instantiation() throws Exception {
    AddressDao addressDao = null;
    StaffPersonDao staffPersonDao = null;
    DrmsDocumentDao drmsDocumentDao = null;
    LongTextDao longTextDao = null;
    ReferralDao referralDao = null;
    RIReferral target =
        new RIReferral(addressDao, staffPersonDao, drmsDocumentDao, longTextDao, referralDao);
    assertThat(target, notNullValue());
  }


  /**
   * @throws Exception Message
   */
  @Test
  public void testExceptionMessage() throws Exception {
    AddressDao addressDao = mock(AddressDao.class);
    StaffPersonDao staffPersonDao = mock(StaffPersonDao.class);
    DrmsDocumentDao drmsDocumentDao = mock(DrmsDocumentDao.class);
    LongTextDao longTextDao = mock(LongTextDao.class);
    ReferralDao referralDao = mock(ReferralDao.class);
    RIReferral target =
        new RIReferral(addressDao, staffPersonDao, drmsDocumentDao, longTextDao, referralDao);
    Referral t = mock(Referral.class);
    expectedException.expect(ReferentialIntegrityException.class);
    when(target.apply(t)).thenThrow(new ReferentialIntegrityException(
        "Referral => Staff Person with given Identifier is not present in database"));
  }

}
