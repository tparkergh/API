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
import gov.ca.cwds.fixture.ReferralClientResourceBuilder;
import gov.ca.cwds.fixture.ReferralResourceBuilder;
import gov.ca.cwds.rest.api.domain.cms.Client;
import gov.ca.cwds.rest.api.domain.cms.Referral;
import gov.ca.cwds.rest.api.domain.cms.ReferralClient;
import gov.ca.cwds.rest.validation.ReferentialIntegrityException;

/**
 * @author CWDS API Team
 *
 */
@SuppressWarnings("javadoc")
public class RIReferralClientTest {

  private ClientDao clientDao;
  private ReferralDao referralDao;

  @Before
  public void setup() {
    clientDao = mock(ClientDao.class);
    referralDao = mock(ReferralDao.class);
  }

  @Test
  public void type() throws Exception {
    assertThat(RIReferralClient.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    RIReferralClient target = new RIReferralClient(clientDao, referralDao);
    assertThat(target, notNullValue());
  }

  @Test(expected = ReferentialIntegrityException.class)
  public void riCheckFailureWhenClientIsNotFound() throws Exception {

    ReferralClient referralClientDomain = new ReferralClientResourceBuilder().buildReferralClient();
    gov.ca.cwds.data.persistence.cms.ReferralClient referralClient =
        new gov.ca.cwds.data.persistence.cms.ReferralClient(referralClientDomain, "0X5");

    RIReferralClient target = new RIReferralClient(clientDao, referralDao);
    when(clientDao.find(any(String.class))).thenReturn(null);
    target.apply(referralClient);
  }

  @Test(expected = ReferentialIntegrityException.class)
  public void riCheckFailureWhenReferralIsNotFound() throws Exception {

    ReferralClient referralClientDomain = new ReferralClientResourceBuilder().buildReferralClient();
    gov.ca.cwds.data.persistence.cms.ReferralClient referralClient =
        new gov.ca.cwds.data.persistence.cms.ReferralClient(referralClientDomain, "0X5");

    Client clientDomain = new ClientResourceBuilder().build();
    gov.ca.cwds.data.persistence.cms.Client client =
        new gov.ca.cwds.data.persistence.cms.Client("ljnSt7KxnV", clientDomain, "0X5");

    RIReferralClient target = new RIReferralClient(clientDao, referralDao);
    when(clientDao.find(any(String.class))).thenReturn(client);
    when(referralDao.find(any(String.class))).thenReturn(null);
    target.apply(referralClient);
  }

  @Test
  public void riCheckPassWhenReferralAndClientFound() throws Exception {

    ReferralClient referralClientDomain = new ReferralClientResourceBuilder().buildReferralClient();
    gov.ca.cwds.data.persistence.cms.ReferralClient referralClient =
        new gov.ca.cwds.data.persistence.cms.ReferralClient(referralClientDomain, "0X5");

    Referral referralDomain = new ReferralResourceBuilder().build();
    gov.ca.cwds.data.persistence.cms.Referral referral =
        new gov.ca.cwds.data.persistence.cms.Referral("H5CMVTm00h", referralDomain, "0X5");

    Client clientDomain = new ClientResourceBuilder().build();
    gov.ca.cwds.data.persistence.cms.Client client =
        new gov.ca.cwds.data.persistence.cms.Client("ljnSt7KxnV", clientDomain, "0X5");

    RIReferralClient target = new RIReferralClient(clientDao, referralDao);
    when(referralDao.find(any(String.class))).thenReturn(referral);
    when(clientDao.find(any(String.class))).thenReturn(client);
    Boolean result = target.apply(referralClient);
    assertThat(result, is(equalTo(true)));
  }

}
