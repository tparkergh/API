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

import gov.ca.cwds.data.cms.ClientDao;
import gov.ca.cwds.data.cms.ReferralDao;
import gov.ca.cwds.fixture.ClientResourceBuilder;
import gov.ca.cwds.fixture.CmsAllegationResourceBuilder;
import gov.ca.cwds.fixture.ReferralResourceBuilder;
import gov.ca.cwds.rest.api.domain.cms.Allegation;
import gov.ca.cwds.rest.api.domain.cms.Client;
import gov.ca.cwds.rest.api.domain.cms.Referral;
import gov.ca.cwds.rest.validation.ReferentialIntegrityException;

/**
 * @author CWDS API Team
 *
 */
@SuppressWarnings("javadoc")
public class RIAllegationTest {

  private ClientDao clientDao;
  private ReferralDao referralDao;

  @Before
  public void setup() {
    clientDao = mock(ClientDao.class);
    referralDao = mock(ReferralDao.class);
  }

  @Test
  public void type() throws Exception {
    assertThat(RIAllegation.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    RIAllegation target = new RIAllegation(clientDao, referralDao);
    assertThat(target, notNullValue());
  }

  @Test(expected = ReferentialIntegrityException.class)
  public void riCheckFailureWhenReferralIsNotFound() throws Exception {

    Allegation allegationDomain = new CmsAllegationResourceBuilder().buildCmsAllegation();
    gov.ca.cwds.data.persistence.cms.Allegation allegation =
        new gov.ca.cwds.data.persistence.cms.Allegation("ABC1234plo", allegationDomain, "0X5");

    RIAllegation target = new RIAllegation(clientDao, referralDao);
    when(referralDao.find(any(String.class))).thenReturn(null);
    target.apply(allegation);
  }

  @Test(expected = ReferentialIntegrityException.class)
  public void riCheckFailureWhenVictimClientIsNotFound() throws Exception {

    Allegation allegationDomain = new CmsAllegationResourceBuilder().buildCmsAllegation();
    gov.ca.cwds.data.persistence.cms.Allegation allegation =
        new gov.ca.cwds.data.persistence.cms.Allegation("ABC1234plo", allegationDomain, "0X5");

    Referral referralDomain = new ReferralResourceBuilder().build();
    gov.ca.cwds.data.persistence.cms.Referral referral =
        new gov.ca.cwds.data.persistence.cms.Referral("H5CMVTm00h", referralDomain, "0X5");

    RIAllegation target = new RIAllegation(clientDao, referralDao);
    when(referralDao.find(any(String.class))).thenReturn(referral);
    when(clientDao.find(any(String.class))).thenReturn(null);
    target.apply(allegation);
  }

  @Test
  public void riCheckPassWhenReferralAndClientFound() throws Exception {

    Allegation allegationDomain = new CmsAllegationResourceBuilder().buildCmsAllegation();
    gov.ca.cwds.data.persistence.cms.Allegation allegation =
        new gov.ca.cwds.data.persistence.cms.Allegation("ABC1234plo", allegationDomain, "0X5");

    Referral referralDomain = new ReferralResourceBuilder().build();
    gov.ca.cwds.data.persistence.cms.Referral referral =
        new gov.ca.cwds.data.persistence.cms.Referral("H5CMVTm00h", referralDomain, "0X5");

    Client clientDomain = new ClientResourceBuilder().build();
    gov.ca.cwds.data.persistence.cms.Client client =
        new gov.ca.cwds.data.persistence.cms.Client("ljnSt7KxnV", clientDomain, "0X5");

    RIAllegation target = new RIAllegation(clientDao, referralDao);
    when(referralDao.find(any(String.class))).thenReturn(referral);
    when(clientDao.find(any(String.class))).thenReturn(client);
    Boolean result = target.apply(allegation);
    assertThat(result, is(equalTo(true)));
  }

}
