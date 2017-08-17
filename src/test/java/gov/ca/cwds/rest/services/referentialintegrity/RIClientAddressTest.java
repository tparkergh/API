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

import gov.ca.cwds.data.cms.AddressDao;
import gov.ca.cwds.data.cms.ClientDao;
import gov.ca.cwds.data.cms.ReferralDao;
import gov.ca.cwds.fixture.ClientAddressResourceBuilder;
import gov.ca.cwds.fixture.ClientResourceBuilder;
import gov.ca.cwds.fixture.CmsAddressResourceBuilder;
import gov.ca.cwds.fixture.ReferralResourceBuilder;
import gov.ca.cwds.rest.api.domain.cms.Address;
import gov.ca.cwds.rest.api.domain.cms.Client;
import gov.ca.cwds.rest.api.domain.cms.ClientAddress;
import gov.ca.cwds.rest.api.domain.cms.Referral;
import gov.ca.cwds.rest.validation.ReferentialIntegrityException;

/**
 * @author CWDS API Team
 *
 */
@SuppressWarnings("javadoc")
public class RIClientAddressTest {

  private AddressDao addressDao;
  private ClientDao clientDao;
  private ReferralDao referralDao;

  @Before
  public void setup() {
    addressDao = mock(AddressDao.class);
    clientDao = mock(ClientDao.class);
    referralDao = mock(ReferralDao.class);
  }

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
    RIClientAddress target = new RIClientAddress(addressDao, clientDao, referralDao);
    assertThat(target, notNullValue());
  }

  /*
   * Test for the referential Integrity Exception
   */
  @Test(expected = ReferentialIntegrityException.class)
  public void riCheckForReferentialIntegrityException() throws Exception {
    ClientAddress clientAddressDomain = new ClientAddressResourceBuilder().buildClientAddress();
    gov.ca.cwds.data.persistence.cms.ClientAddress clientAddress =
        new gov.ca.cwds.data.persistence.cms.ClientAddress("ABC1234567", clientAddressDomain,
            "0X5");

    when(addressDao.find(any(String.class))).thenReturn(null);
    when(clientDao.find(any(String.class))).thenReturn(null);
    when(referralDao.find(any(String.class))).thenReturn(null);
    RIClientAddress target = new RIClientAddress(addressDao, clientDao, referralDao);
    target.apply(clientAddress);
  }

  @Test(expected = ReferentialIntegrityException.class)
  public void riCheckFailureWhenFkReferralNotFound() throws Exception {
    ClientAddress clientAddressDomain = new ClientAddressResourceBuilder()
        .setFkAddress("ABC1234560").setFkClient("ABC123456k").buildClientAddress();
    gov.ca.cwds.data.persistence.cms.ClientAddress clientAddress =
        new gov.ca.cwds.data.persistence.cms.ClientAddress("ABC1234567", clientAddressDomain,
            "0X5");

    Address addressDomain = new CmsAddressResourceBuilder().buildCmsAddress();
    gov.ca.cwds.data.persistence.cms.Address address =
        new gov.ca.cwds.data.persistence.cms.Address("ABC1234560", addressDomain, "0X5");

    Client clientDomain = new ClientResourceBuilder().build();
    gov.ca.cwds.data.persistence.cms.Client client =
        new gov.ca.cwds.data.persistence.cms.Client("ABC123456k", clientDomain, "0X5");

    when(addressDao.find(any(String.class))).thenReturn(address);
    when(clientDao.find(any(String.class))).thenReturn(client);
    when(referralDao.find(any(String.class))).thenReturn(null);
    RIClientAddress target = new RIClientAddress(addressDao, clientDao, referralDao);
    target.apply(clientAddress);
  }

  @Test
  public void riCheckPass() throws Exception {
    ClientAddress clientAddressDomain =
        new ClientAddressResourceBuilder().setFkReferral(null).buildClientAddress();
    gov.ca.cwds.data.persistence.cms.ClientAddress clientAddress =
        new gov.ca.cwds.data.persistence.cms.ClientAddress("ABC1234567", clientAddressDomain,
            "0X5");

    Address addressDomain = new CmsAddressResourceBuilder().buildCmsAddress();
    gov.ca.cwds.data.persistence.cms.Address address =
        new gov.ca.cwds.data.persistence.cms.Address("ABC1234560", addressDomain, "0X5");

    Client clientDomain = new ClientResourceBuilder().build();
    gov.ca.cwds.data.persistence.cms.Client client =
        new gov.ca.cwds.data.persistence.cms.Client("ABC123456k", clientDomain, "0X5");

    Referral referralDomain = new ReferralResourceBuilder().build();
    gov.ca.cwds.data.persistence.cms.Referral referral =
        new gov.ca.cwds.data.persistence.cms.Referral("ABC1234ftd", referralDomain, "0X5");

    when(addressDao.find(any(String.class))).thenReturn(address);
    when(clientDao.find(any(String.class))).thenReturn(client);
    when(referralDao.find(any(String.class))).thenReturn(referral);
    RIClientAddress target = new RIClientAddress(addressDao, clientDao, referralDao);
    Boolean result = target.apply(clientAddress);
    assertThat(result, is(equalTo(true)));
  }

  /*
   * Test success when fkReferral is null
   */
  @Test
  public void riCheckPassesWhenFkReferralIsNull() throws Exception {
    ClientAddress clientAddressDomain =
        new ClientAddressResourceBuilder().setFkReferral(null).buildClientAddress();
    gov.ca.cwds.data.persistence.cms.ClientAddress clientAddress =
        new gov.ca.cwds.data.persistence.cms.ClientAddress("ABC1234567", clientAddressDomain,
            "0X5");

    Address addressDomain = new CmsAddressResourceBuilder().buildCmsAddress();
    gov.ca.cwds.data.persistence.cms.Address address =
        new gov.ca.cwds.data.persistence.cms.Address("ABC1234560", addressDomain, "0X5");

    Client clientDomain = new ClientResourceBuilder().build();
    gov.ca.cwds.data.persistence.cms.Client client =
        new gov.ca.cwds.data.persistence.cms.Client("ABC123456k", clientDomain, "0X5");

    when(addressDao.find(any(String.class))).thenReturn(address);
    when(clientDao.find(any(String.class))).thenReturn(client);
    when(referralDao.find(any(String.class))).thenReturn(null);
    RIClientAddress target = new RIClientAddress(addressDao, clientDao, referralDao);
    Boolean result = target.apply(clientAddress);
    assertThat(result, is(equalTo(true)));
  }

  @Test(expected = ReferentialIntegrityException.class)
  public void riCheckFailureWhenFkClientlIsNull() throws Exception {
    ClientAddress clientAddressDomain =
        new ClientAddressResourceBuilder().setFkClient(null).buildClientAddress();
    gov.ca.cwds.data.persistence.cms.ClientAddress clientAddress =
        new gov.ca.cwds.data.persistence.cms.ClientAddress("ABC1234567", clientAddressDomain,
            "0X5");

    Address addressDomain = new CmsAddressResourceBuilder().buildCmsAddress();
    gov.ca.cwds.data.persistence.cms.Address address =
        new gov.ca.cwds.data.persistence.cms.Address("ABC1234560", addressDomain, "0X5");

    when(addressDao.find(any(String.class))).thenReturn(address);
    when(clientDao.find(any(String.class))).thenReturn(null);
    when(referralDao.find(any(String.class))).thenReturn(null);
    RIClientAddress target = new RIClientAddress(addressDao, clientDao, referralDao);
    target.apply(clientAddress);

  }

}
