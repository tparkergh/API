package gov.ca.cwds.data.cms;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import gov.ca.cwds.data.persistence.cms.Address;
import gov.ca.cwds.data.persistence.cms.ClientAddress;
import gov.ca.cwds.fixture.AddressEntityBuilder;
import gov.ca.cwds.fixture.ClientAddressEntityBuilder;
import gov.ca.cwds.fixture.ParticipantResourceBuilder;
import gov.ca.cwds.rest.api.domain.Participant;
import gov.ca.cwds.rest.api.domain.cms.Client;

/**
 * @author CWDS API Team
 *
 */
public class ClientAddressDaoIT {

  public static final String DEFAULT_DATE = "2010-01-28";
  private static SessionFactory sessionFactory;
  private static ClientAddressDao clientAddressDao;
  private Session session;

  private static final String CLIENT_ID = "CLIENTIDXX";
  private static final String ADDRESS_ID = "ADDRESSIDX";
  gov.ca.cwds.data.persistence.cms.Client entityClient;

  /**
   * 
   */
  @BeforeClass
  public static void beforeClass() {
    sessionFactory = new Configuration().configure().buildSessionFactory();
    clientAddressDao = new ClientAddressDao(sessionFactory);
  }

  /**
   * 
   */
  @AfterClass
  public static void afterClass() {
    sessionFactory.close();
  }

  /**
   * 
   */
  @Before
  public void setup() {
    session = sessionFactory.getCurrentSession();
    session.beginTransaction();

    createClientRecords();
  }

  private void createClientRecords() {
    Address address = new AddressEntityBuilder().setId(ADDRESS_ID).create();
    gov.ca.cwds.rest.api.domain.cms.Address cmsDomainAddress =
        new gov.ca.cwds.rest.api.domain.cms.Address(address, false);
    Address entityAddress = new Address(ADDRESS_ID, cmsDomainAddress, "OX5");

    ClientAddress clientAddress =
        new ClientAddressEntityBuilder().setFkAddress(ADDRESS_ID).setFkClient(CLIENT_ID)
            .setAddresses(entityAddress).setLastUpdatedId("0X5").buildClientAddress();
    Set<ClientAddress> clientAddresses = new HashSet<>(Arrays.asList(clientAddress));

    Participant participant =
        new ParticipantResourceBuilder().setLegacyId(CLIENT_ID).createParticipant();
    Client domainClient = Client.createWithDefaults(participant, DEFAULT_DATE, "M");
    entityClient = new gov.ca.cwds.data.persistence.cms.Client(CLIENT_ID, domainClient, "0X5");
    entityClient.setClientAddress(clientAddresses);

    gov.ca.cwds.data.persistence.cms.Client savedClient =
        new ClientDao(sessionFactory).create(entityClient);
    assertNotNull(savedClient.getId());
  }

  /**
   * 
   */
  @After
  public void teardown() {
    session.getTransaction().rollback();
  }

  /**
   * clientAddress Exists
   */
  @Test
  public void shouldReturnListOfClientAddressesWhenExists() {
    List<ClientAddress> foundClientAddresses =
        clientAddressDao.findByAddressAndClient(ADDRESS_ID, CLIENT_ID);

    ClientAddress savedClientAddress = foundClientAddresses.get(0);
    assertEquals("Expected Client Address to have saved address id", ADDRESS_ID,
        savedClientAddress.getFkAddress());
    assertEquals("Expected Client Address to have saved client id", CLIENT_ID,
        savedClientAddress.getFkClient());
  }

  /**
   * clientAddress doesn't exists
   */
  @Test
  public void shouldReturnEmptyListWhenClientAddressDoesntExist() {
    List<ClientAddress> foundClientAddresses =
        clientAddressDao.findByAddressAndClient("UNKNOWNID", CLIENT_ID);

    assertTrue("Expected no Addresses to have been found", foundClientAddresses.isEmpty());
  }

  /**
   * clientId is null
   */
  @Test
  public void shouldReturnEmptyListWhenClientIdIsNull() {
    List<ClientAddress> foundClientAddresses =
        clientAddressDao.findByAddressAndClient(ADDRESS_ID, null);

    assertTrue("Expected no Addresses to have been found when Client id is null",
        foundClientAddresses.isEmpty());

  }

  /**
   * addressId is null
   */
  @Test
  public void shouldReturnEmptyListWhenAddressIdIsNull() {
    List<ClientAddress> foundClientAddresses =
        clientAddressDao.findByAddressAndClient(null, CLIENT_ID);

    assertTrue("Expected no Addresses to have been found when Client id is null",
        foundClientAddresses.isEmpty());
  }
}
