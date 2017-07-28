package gov.ca.cwds.rest.business.rules;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import gov.ca.cwds.data.DaoException;
import gov.ca.cwds.data.cms.AddressUcDao;
import gov.ca.cwds.data.cms.ClientUcDao;
import gov.ca.cwds.data.persistence.cms.AddressUc;
import gov.ca.cwds.data.persistence.cms.ClientUc;
import gov.ca.cwds.fixture.ClientResourceBuilder;
import gov.ca.cwds.fixture.CmsAddressResourceBuilder;
import gov.ca.cwds.rest.api.domain.cms.Address;
import gov.ca.cwds.rest.api.domain.cms.Client;
import gov.ca.cwds.rest.services.ServiceException;

/**
 * @author CWDS API Team
 *
 */
public class UpperCaseTablesTest {

  private ClientUcDao clientUcDao;
  private AddressUcDao addressUcDao;
  private UpperCaseTables upperCaseTables;

  private static ClientUc clientUc;
  private static AddressUc addressUc;

  @SuppressWarnings("javadoc")
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Before
  public void setup() throws Exception {
    clientUcDao = mock(ClientUcDao.class);
    addressUcDao = mock(AddressUcDao.class);

    upperCaseTables = new UpperCaseTables(clientUcDao, addressUcDao);
  }

  /**
   * @throws Exception - Exception
   */
  @Test
  public void testForClientUpperCaseCreated() throws Exception {
    Client clientDomain = new ClientResourceBuilder().build();
    gov.ca.cwds.data.persistence.cms.Client client =
        new gov.ca.cwds.data.persistence.cms.Client("ABC1234567", clientDomain, "0X5");
    upperCaseTables.createClientUc(client);
    verify(clientUcDao, times(1)).create(any());

  }

  /**
   * @throws Exception - Exception
   */
  @Test(expected = DaoException.class)
  public void testForClientUpperCaseCreateThrowsDaoException() throws Exception {
    when(clientUcDao.create(any())).thenThrow(new ServiceException());
    Client clientDomain = new ClientResourceBuilder().build();
    gov.ca.cwds.data.persistence.cms.Client client =
        new gov.ca.cwds.data.persistence.cms.Client("ABC1234567", clientDomain, "0X5");
    upperCaseTables.createClientUc(client);

  }

  /**
   * @throws Exception - Exception
   */
  @Test
  public void testForClientUpperCase() throws Exception {
    Client clientDomain = new ClientResourceBuilder().build();
    gov.ca.cwds.data.persistence.cms.Client client =
        new gov.ca.cwds.data.persistence.cms.Client("ABC1234567", clientDomain, "0X5");

    when(clientUcDao.create(any(ClientUc.class))).thenAnswer(new Answer<ClientUc>() {
      @Override
      public ClientUc answer(InvocationOnMock invocation) throws Throwable {

        ClientUc report = (ClientUc) invocation.getArguments()[0];
        clientUc = report;
        return report;
      }
    });
    upperCaseTables.createClientUc(client);
    assertThat(clientUc.getCommonFirstName(), is(equalTo("BILLY")));

  }

  /**
   * @throws Exception - Exception
   */
  @Test
  public void testForClientUpperCaseUpdated() throws Exception {
    Client clientDomain = new ClientResourceBuilder().setCommonFirstName("BILLYS").build();
    gov.ca.cwds.data.persistence.cms.Client client =
        new gov.ca.cwds.data.persistence.cms.Client("ABC1234567", clientDomain, "0X5");
    upperCaseTables.updateClientUc(client);
    verify(clientUcDao, times(1)).update(any());

  }

  /**
   * @throws Exception - Exception
   */
  @Test(expected = DaoException.class)
  public void testForClientUpperCaseUpdateThrowsDaoException() throws Exception {
    when(clientUcDao.update(any())).thenThrow(new ServiceException());
    Client clientDomain = new ClientResourceBuilder().setCommonFirstName("BILLYS").build();
    gov.ca.cwds.data.persistence.cms.Client client =
        new gov.ca.cwds.data.persistence.cms.Client("ABC1234567", clientDomain, "0X5");
    upperCaseTables.updateClientUc(client);

  }

  /**
   * @throws Exception - exception
   */
  @Test
  public void testForClientUpperCaseUpdateThrowsDaoExceptionWhenNull() throws Exception {
    Client clientDomain = new ClientResourceBuilder().setCommonFirstName(null).build();

    gov.ca.cwds.data.persistence.cms.Client client =
        new gov.ca.cwds.data.persistence.cms.Client("ABC1234567", clientDomain, "0X5");
    upperCaseTables.updateClientUc(client);

  }

  /**
   * @throws Exception - Exception
   */
  @Test
  public void testForClientUpperCaseUpdate() throws Exception {
    Client clientDomain = new ClientResourceBuilder().setCommonFirstName("BILLYS").build();
    gov.ca.cwds.data.persistence.cms.Client client =
        new gov.ca.cwds.data.persistence.cms.Client("ABC1234567", clientDomain, "0X5");

    when(clientUcDao.update(any(ClientUc.class))).thenAnswer(new Answer<ClientUc>() {
      @Override
      public ClientUc answer(InvocationOnMock invocation) throws Throwable {

        ClientUc report = (ClientUc) invocation.getArguments()[0];
        clientUc = report;
        return report;
      }
    });
    upperCaseTables.updateClientUc(client);
    assertThat(clientUc.getCommonFirstName(), is(equalTo("BILLYS")));

  }

  /**
   * @throws Exception - Exception
   */
  @Test
  public void testForAddressUpperCaseCreated() throws Exception {
    Address addressDomain = new CmsAddressResourceBuilder().buildCmsAddress();
    gov.ca.cwds.data.persistence.cms.Address address =
        new gov.ca.cwds.data.persistence.cms.Address("ABC1234567", addressDomain, "0X5");
    upperCaseTables.createAddressUc(address);
    verify(addressUcDao, times(1)).create(any());

  }

  /**
   * @throws Exception - Exception
   */
  @Test(expected = DaoException.class)
  public void testForAddressUpperCaseCreateThrowsDaoException() throws Exception {
    when(addressUcDao.create(any())).thenThrow(new ServiceException());
    Address addressDomain = new CmsAddressResourceBuilder().buildCmsAddress();
    gov.ca.cwds.data.persistence.cms.Address address =
        new gov.ca.cwds.data.persistence.cms.Address("ABC1234567", addressDomain, "0X5");
    upperCaseTables.createAddressUc(address);

  }

  /**
   * @throws Exception - Exception
   */
  @Test
  public void testForAddressUpperCase() throws Exception {
    Address addressDomain = new CmsAddressResourceBuilder().buildCmsAddress();
    gov.ca.cwds.data.persistence.cms.Address address =
        new gov.ca.cwds.data.persistence.cms.Address("ABC1234567", addressDomain, "0X5");

    when(addressUcDao.create(any(AddressUc.class))).thenAnswer(new Answer<AddressUc>() {
      @Override
      public AddressUc answer(InvocationOnMock invocation) throws Throwable {

        AddressUc report = (AddressUc) invocation.getArguments()[0];
        addressUc = report;
        return report;
      }
    });
    upperCaseTables.createAddressUc(address);
    assertThat(addressUc.getStreetName(), is(equalTo("2870")));

  }

  /**
   * @throws Exception - Exception
   */
  @Test
  public void testForAddressUpperCaseUpdated() throws Exception {
    Address addressDomain = new CmsAddressResourceBuilder().setStreetName("2860").buildCmsAddress();
    gov.ca.cwds.data.persistence.cms.Address address =
        new gov.ca.cwds.data.persistence.cms.Address("ABC1234567", addressDomain, "0X5");
    upperCaseTables.updateAddressUc(address);
    verify(addressUcDao, times(1)).update(any());

  }

  /**
   * @throws Exception - Exception
   */
  @Test(expected = DaoException.class)
  public void testForAddressUpperCaseUpdateThrowsDaoException() throws Exception {
    when(addressUcDao.update(any())).thenThrow(new ServiceException());
    Address addressDomain = new CmsAddressResourceBuilder().setStreetName("2860").buildCmsAddress();
    gov.ca.cwds.data.persistence.cms.Address address =
        new gov.ca.cwds.data.persistence.cms.Address("ABC1234567", addressDomain, "0X5");
    upperCaseTables.updateAddressUc(address);

  }

  /**
   * @throws Exception - Exception
   */
  @Test
  public void testForAddressUpperCaseUpdate() throws Exception {
    Address addressDomain = new CmsAddressResourceBuilder().setStreetName("2860").buildCmsAddress();
    gov.ca.cwds.data.persistence.cms.Address address =
        new gov.ca.cwds.data.persistence.cms.Address("ABC1234567", addressDomain, "0X5");

    when(addressUcDao.update(any(AddressUc.class))).thenAnswer(new Answer<AddressUc>() {
      @Override
      public AddressUc answer(InvocationOnMock invocation) throws Throwable {

        AddressUc report = (AddressUc) invocation.getArguments()[0];
        addressUc = report;
        return report;
      }
    });
    upperCaseTables.updateAddressUc(address);
    assertThat(addressUc.getStreetName(), is(equalTo("2860")));

  }

  /**
   * @throws Exception - Exception
   */
  @Test
  public void testForClientUpperCaseDeleted() throws Exception {
    upperCaseTables.deleteClientUc("ABC1234567");
    verify(clientUcDao, times(1)).delete(any());
  }

  /**
   * @throws Exception - Exception
   */
  @Test
  public void testForClientUpperCaseDeleteVerify() throws Exception {
    upperCaseTables.deleteClientUc(null);
    verify(clientUcDao, times(0)).delete(any());
  }

  /**
   * @throws Exception - Exception
   */
  @Test(expected = DaoException.class)
  public void testForClientUpperCaseDeletedThrowsDaoException() throws Exception {
    when(clientUcDao.delete(any())).thenThrow(new ServiceException());
    upperCaseTables.deleteClientUc("ABC1234567");
    verify(clientUcDao, times(0)).delete(any());
  }

  /**
   * @throws Exception - Exception
   */
  @Test
  public void testForAddressUpperCaseDeleted() throws Exception {
    upperCaseTables.deleteAddressUc("ABC1234567");
    verify(addressUcDao, times(1)).delete(any());
  }

  /**
   * @throws Exception - Exception
   */
  @Test
  public void testForAddressUpperCaseDeleteVerify() throws Exception {
    upperCaseTables.deleteAddressUc(null);
    verify(addressUcDao, times(0)).delete(any());
  }

  /**
   * @throws Exception - Exception
   */
  @Test(expected = DaoException.class)
  public void testForAddressUpperCaseDeletedThrowsDaoException() throws Exception {
    when(addressUcDao.delete(any())).thenThrow(new ServiceException());
    upperCaseTables.deleteAddressUc("ABC1234567");
    verify(addressUcDao, times(0)).delete(any());
  }

}
