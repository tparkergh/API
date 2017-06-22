package gov.ca.cwds.rest.services.cms;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import gov.ca.cwds.data.cms.ClientAddressDao;
import gov.ca.cwds.data.cms.StaffPersonDao;
import gov.ca.cwds.data.persistence.cms.ClientAddress;
import gov.ca.cwds.data.rules.TriggerTablesDao;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.Address;
import gov.ca.cwds.rest.api.domain.Participant;
import gov.ca.cwds.rest.business.rules.LACountyTrigger;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class ClientAddressServiceTest {
  ClientAddressService clientAddressService;

  ClientAddressDao clientAddressDao;
  StaffPersonDao staffpersonDao;
  TriggerTablesDao triggerTablesDao;
  LACountyTrigger laCountyTrigger;
  StaffPersonIdRetriever staffPersonIdRetriever;

  @Before
  public void before(){
    clientAddressDao = mock(ClientAddressDao.class);
    staffpersonDao = mock(StaffPersonDao.class);
    triggerTablesDao = mock(TriggerTablesDao.class);
    laCountyTrigger = mock(LACountyTrigger.class);
    staffPersonIdRetriever = mock(StaffPersonIdRetriever.class);

    clientAddressService = new ClientAddressService(clientAddressDao, staffpersonDao, triggerTablesDao,laCountyTrigger, staffPersonIdRetriever);
  }

  @Test
  public void shouldReturnNullWhenNotClientAddressesAreFound(){
    when(clientAddressDao.findByAddressAndClient(any(), any())).thenReturn(new ArrayList());
//    clientAddressService = new ClientAddressService(clientAddressDao, staffpersonDao, triggerTablesDao,laCountyTrigger, staffPersonIdRetriever);

    Address address = mock(Address.class);
    Participant participant = mock(Participant.class);

    List foundClients = clientAddressService.findByAddressAndClient(address, participant);
    assertNull("Expected null when no elements are returned", foundClients);

  }
  @Test
  public void shouldReturnTheNumberOfClientAddressesFound(){
    ClientAddress clientAddress1 = mock(ClientAddress.class);
    when(clientAddress1.getId()).thenReturn("1");
    ClientAddress clientAddress2 = mock(ClientAddress.class);
    when(clientAddress2.getId()).thenReturn("2");
    List persistedClients = new ArrayList();
    persistedClients.add(clientAddress1);
    persistedClients.add(clientAddress2);
    when(clientAddressDao.findByAddressAndClient(any(), any())).thenReturn(persistedClients);

    Address address = mock(Address.class);
    Participant participant = mock(Participant.class);

    List<Response> foundClients = clientAddressService.findByAddressAndClient(address, participant);
    assertEquals("Expected both elements returned", persistedClients.size(), foundClients.size());
    assertEquals("Expected first address to have been returned", ((ClientAddress)persistedClients.get(0)).getId(), clientAddress1.getId());
    assertEquals("Expected second address to have been returned", ((ClientAddress)persistedClients.get(1)).getId(), clientAddress2.getId());
  }

}