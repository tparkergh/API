package gov.ca.cwds.rest.services.cms;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import gov.ca.cwds.data.cms.ClientAddressDao;
import gov.ca.cwds.data.cms.StaffPersonDao;
import gov.ca.cwds.data.persistence.cms.ClientAddress;
import gov.ca.cwds.data.persistence.cms.StaffPerson;
import gov.ca.cwds.data.rules.TriggerTablesDao;
import gov.ca.cwds.fixture.ClientAddressResourceBuilder;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.Address;
import gov.ca.cwds.rest.api.domain.Participant;
import gov.ca.cwds.rest.business.rules.LACountyTrigger;
import gov.ca.cwds.rest.business.rules.NonLACountyTriggers;
import gov.ca.cwds.rest.services.ServiceException;
import gov.ca.cwds.rest.services.junit.template.ServiceTestTemplate;

/**
 * @author CWS-NS2
 *
 */
public class ClientAddressServiceTest implements ServiceTestTemplate {
  ClientAddressService clientAddressService;

  ClientAddressDao clientAddressDao;
  StaffPersonDao staffpersonDao;
  TriggerTablesDao triggerTablesDao;
  LACountyTrigger laCountyTrigger;
  NonLACountyTriggers nonLACountyTriggers;
  StaffPersonIdRetriever staffPersonIdRetriever;

  private static Boolean isLaCountyTrigger = false;

  @SuppressWarnings("javadoc")
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  /**
   * 
   */
  @Override
  @Before
  public void setup() throws Exception {
    clientAddressDao = mock(ClientAddressDao.class);
    staffpersonDao = mock(StaffPersonDao.class);
    triggerTablesDao = mock(TriggerTablesDao.class);
    laCountyTrigger = mock(LACountyTrigger.class);
    nonLACountyTriggers = mock(NonLACountyTriggers.class);
    staffPersonIdRetriever = mock(StaffPersonIdRetriever.class);

    clientAddressService = new ClientAddressService(clientAddressDao, staffpersonDao,
        triggerTablesDao, laCountyTrigger, staffPersonIdRetriever, nonLACountyTriggers);
  }

  /**
   * 
   */
  @Test
  public void shouldReturnNullWhenNotClientAddressesAreFound() {
    when(clientAddressDao.findByAddressAndClient(any(), any())).thenReturn(new ArrayList<>());
    Address address = mock(Address.class);
    Participant participant = mock(Participant.class);

    List foundClients = clientAddressService.findByAddressAndClient(address, participant);
    assertNull("Expected null when no elements are returned", foundClients);

  }

  /**
   * 
   */
  @Test
  public void shouldReturnTheNumberOfClientAddressesFound() {
    ClientAddress clientAddress1 = mock(ClientAddress.class);
    when(clientAddress1.getId()).thenReturn("1");
    ClientAddress clientAddress2 = mock(ClientAddress.class);
    when(clientAddress2.getId()).thenReturn("2");
    List<ClientAddress> persistedClients = new ArrayList<ClientAddress>();
    persistedClients.add(clientAddress1);
    persistedClients.add(clientAddress2);
    when(clientAddressDao.findByAddressAndClient(any(), any())).thenReturn(persistedClients);

    Address address = mock(Address.class);
    Participant participant = mock(Participant.class);

    List<Response> foundClients = clientAddressService.findByAddressAndClient(address, participant);
    assertEquals("Expected both elements returned", persistedClients.size(), foundClients.size());
    assertEquals("Expected first address to have been returned", persistedClients.get(0).getId(),
        clientAddress1.getId());
    assertEquals("Expected second address to have been returned", persistedClients.get(1).getId(),
        clientAddress2.getId());
  }

  // find
  @Override
  @Test(expected = AssertionError.class)
  public void testFindThrowsAssertionError() {
    try {
      clientAddressService.find(1);
    } catch (AssertionError e) {
      assertEquals("Expeceted AssertionError", e.getMessage());
    }
  }

  @Override
  @Test
  public void testFindReturnsNullWhenNotFound() throws Exception {
    Response found = clientAddressService.find("ABC1234567");
    assertThat(found, is(nullValue()));
  }

  @Override
  public void testFindThrowsNotImplementedException() throws Exception {

  }

  @Override
  public void testFindReturnsCorrectEntity() throws Exception {

  }

  // delete test
  @Override
  @Test(expected = AssertionError.class)
  public void testDeleteThrowsAssertionError() throws Exception {
    try {
      clientAddressService.delete(123);
    } catch (AssertionError e) {
      assertEquals("Expected AssertionError", e.getMessage());
    }
  }

  @Override
  @Test
  public void testDeleteDelegatesToCrudsService() {
    clientAddressService.delete("ABC2345678");
    verify(clientAddressDao, times(1)).delete("ABC2345678");
  }

  @Override
  @Test
  public void testDeleteReturnsNullWhenNotFound() throws Exception {
    Response found = clientAddressService.delete("ABC1234567");
    assertThat(found, is(nullValue()));
  }

  @Override
  public void testDeleteThrowsNotImplementedException() throws Exception {

  }

  @Override
  public void testDeleteReturnsClass() throws Exception {

  }

  @SuppressWarnings("javadoc")
  @Test
  public void clientAddressServiceDeleteReturnsNotNull() throws Exception {
    String id = "LOmv8Jp0Nu";
    gov.ca.cwds.rest.api.domain.cms.ClientAddress expected =
        new ClientAddressResourceBuilder().buildClientAddress();
    gov.ca.cwds.data.persistence.cms.ClientAddress clientAddress =
        new gov.ca.cwds.data.persistence.cms.ClientAddress(id, expected, "0XA");

    when(clientAddressDao.delete(id)).thenReturn(clientAddress);
    gov.ca.cwds.rest.api.domain.cms.ClientAddress found =
        (gov.ca.cwds.rest.api.domain.cms.ClientAddress) clientAddressService.delete(id);
    assertThat(found.getClientId(), is(equalTo("FKCLIENTXX")));
  }

  // update test
  @Override
  @Test(expected = AssertionError.class)
  public void testUpdateThrowsAssertionError() throws Exception {
    try {
      clientAddressService.update("ABC1234567", null);
    } catch (AssertionError e) {
      assertEquals("Expected AssertionError", e.getMessage());
    }
  }

  @Override
  @Test
  public void testUpdateReturnsCorrectEntity() throws Exception {
    String id = "LOmv8Jp0Nu";
    gov.ca.cwds.rest.api.domain.cms.ClientAddress clientAddressDomain =
        new ClientAddressResourceBuilder().setEffEndDt("2017-01-10").buildClientAddress();

    gov.ca.cwds.data.persistence.cms.ClientAddress clientAddress =
        new gov.ca.cwds.data.persistence.cms.ClientAddress(id, clientAddressDomain, "ABC");

    when(clientAddressDao.find("ABC1234567")).thenReturn(clientAddress);
    when(clientAddressDao.update(any())).thenReturn(clientAddress);

    Object retval = clientAddressService.update("ABC1234567", clientAddressDomain);
    assertThat(retval.getClass(), is(gov.ca.cwds.rest.api.domain.cms.ClientAddress.class));
  }

  @SuppressWarnings("javadoc")
  @Test
  public void testUpdateThrowsExceptionWhenNotFound() throws Exception {
    try {
      gov.ca.cwds.rest.api.domain.cms.ClientAddress clientAddressDomain =
          new ClientAddressResourceBuilder().setEffEndDt("2017-01-10").buildClientAddress();

      when(clientAddressDao.update(any())).thenThrow(EntityNotFoundException.class);

      clientAddressService.update("ZZZZZZZZZZ", clientAddressDomain);
    } catch (Exception e) {
      assertEquals(e.getClass(), ServiceException.class);
    }
  }

  @Override
  public void testUpdateReturnsDomain() throws Exception {

  }

  @Override
  public void testUpdateThrowsServiceException() throws Exception {

  }

  @Override
  public void testUpdateThrowsNotImplementedException() throws Exception {

  }

  // create test
  @Override
  @Test
  public void testCreateReturnsPostedClass() throws Exception {
    String id = "QcNOYA10Nu";
    gov.ca.cwds.rest.api.domain.cms.ClientAddress clientAddressDomain =
        new ClientAddressResourceBuilder().buildClientAddress();

    gov.ca.cwds.data.persistence.cms.ClientAddress toCreate =
        new gov.ca.cwds.data.persistence.cms.ClientAddress(id, clientAddressDomain, "ABC");

    gov.ca.cwds.rest.api.domain.cms.ClientAddress request =
        new gov.ca.cwds.rest.api.domain.cms.ClientAddress(toCreate, false);
    when(clientAddressDao.create(any(gov.ca.cwds.data.persistence.cms.ClientAddress.class)))
        .thenReturn(toCreate);

    Response response = clientAddressService.create(request);
    assertThat(response.getClass(), is(gov.ca.cwds.rest.api.domain.cms.ClientAddress.class));
  }

  @SuppressWarnings("javadoc")
  @Test
  public void testCreateReturnsNonNull() throws Exception {
    String id = "QcNOYA10Nu";
    gov.ca.cwds.rest.api.domain.cms.ClientAddress clientAddressDomain =
        new ClientAddressResourceBuilder().buildClientAddress();

    gov.ca.cwds.data.persistence.cms.ClientAddress toCreate =
        new gov.ca.cwds.data.persistence.cms.ClientAddress(id, clientAddressDomain, "ABC");

    gov.ca.cwds.rest.api.domain.cms.ClientAddress request =
        new gov.ca.cwds.rest.api.domain.cms.ClientAddress(toCreate, false);
    when(clientAddressDao.create(any(gov.ca.cwds.data.persistence.cms.ClientAddress.class)))
        .thenReturn(toCreate);

    Response postedClientAddress = clientAddressService.create(request);
    assertThat(postedClientAddress, is(notNullValue()));
  }

  @Override
  @Test
  public void testCreateReturnsCorrectEntity() throws Exception {
    String id = "QcNOYA10Nu";
    gov.ca.cwds.rest.api.domain.cms.ClientAddress clientAddressDomain =
        new ClientAddressResourceBuilder().buildClientAddress();

    gov.ca.cwds.data.persistence.cms.ClientAddress toCreate =
        new gov.ca.cwds.data.persistence.cms.ClientAddress(id, clientAddressDomain, "ABC");

    gov.ca.cwds.rest.api.domain.cms.ClientAddress request =
        new gov.ca.cwds.rest.api.domain.cms.ClientAddress(toCreate, false);
    when(clientAddressDao.create(any(gov.ca.cwds.data.persistence.cms.ClientAddress.class)))
        .thenReturn(toCreate);

    Response expected = new gov.ca.cwds.rest.api.domain.cms.ClientAddress(toCreate, false);
    Response returned = clientAddressService.create(request);
    assertThat(returned, is(expected));
  }

  @SuppressWarnings("javadoc")
  @Test
  public void testCreateThrowsEntityExistsException() throws Exception {
    try {
      gov.ca.cwds.rest.api.domain.cms.ClientAddress clientAddressDomain =
          new ClientAddressResourceBuilder().buildClientAddress();

      when(clientAddressDao.create(any())).thenThrow(EntityExistsException.class);

      clientAddressService.create(clientAddressDomain);
    } catch (Exception e) {
      assertEquals(e.getClass(), ServiceException.class);
    }
  }

  @Override
  @Test
  public void testCreateNullIDError() throws Exception {
    try {
      gov.ca.cwds.rest.api.domain.cms.ClientAddress clientAddressDomain =
          new ClientAddressResourceBuilder().buildClientAddress();

      gov.ca.cwds.data.persistence.cms.ClientAddress toCreate =
          new gov.ca.cwds.data.persistence.cms.ClientAddress(null, clientAddressDomain, "ABC");

      when(clientAddressDao.create(any(gov.ca.cwds.data.persistence.cms.ClientAddress.class)))
          .thenReturn(toCreate);

      Response expected = new gov.ca.cwds.rest.api.domain.cms.ClientAddress(toCreate, false);
    } catch (ServiceException e) {
      assertEquals("ClientAddress ID cannot be blank", e.getMessage());
    }

  }

  @Override
  @Test
  public void testCreateBlankIDError() throws Exception {
    try {
      gov.ca.cwds.rest.api.domain.cms.ClientAddress clientAddressDomain =
          new ClientAddressResourceBuilder().buildClientAddress();

      gov.ca.cwds.data.persistence.cms.ClientAddress toCreate =
          new gov.ca.cwds.data.persistence.cms.ClientAddress("  ", clientAddressDomain, "ABC");

      when(clientAddressDao.create(any(gov.ca.cwds.data.persistence.cms.ClientAddress.class)))
          .thenReturn(toCreate);

      Response expected = new gov.ca.cwds.rest.api.domain.cms.ClientAddress(toCreate, false);
    } catch (ServiceException e) {
      assertEquals("Assignment ID cannot be blank", e.getMessage());
    }
  }

  @SuppressWarnings("javadoc")
  @Test
  public void testCreateLACountyTriggerForClientAddress() throws Exception {
    gov.ca.cwds.rest.api.domain.cms.ClientAddress clientAddressDomain =
        new ClientAddressResourceBuilder().buildClientAddress();

    gov.ca.cwds.data.persistence.cms.ClientAddress toCreate =
        new gov.ca.cwds.data.persistence.cms.ClientAddress("1234567ABC", clientAddressDomain,
            "BTr");

    gov.ca.cwds.rest.api.domain.cms.ClientAddress request =
        new gov.ca.cwds.rest.api.domain.cms.ClientAddress(toCreate, false);

    when(triggerTablesDao.getLaCountySpecificCode()).thenReturn("19");

    StaffPerson staffPerson = new StaffPerson("BTr", null, "External Interface",
        "external interface", "SCXCIN7", " ", "", BigDecimal.valueOf(9165672100L), 0, null, "    ",
        "N", "MIZN02k00E", "  ", "    ", "19", "N", "3XPCP92q38", null);

    when(staffpersonDao.find(any(String.class))).thenReturn(staffPerson);
    when(clientAddressDao.create(any(gov.ca.cwds.data.persistence.cms.ClientAddress.class)))
        .thenReturn(toCreate);

    when(laCountyTrigger.createClientAddressCountyTrigger(
        any(gov.ca.cwds.data.persistence.cms.ClientAddress.class)))
            .thenAnswer(new Answer<Boolean>() {

              @Override
              public Boolean answer(InvocationOnMock invocation) throws Throwable {
                isLaCountyTrigger = true;
                return true;
              }
            });

    clientAddressService.create(request);
    assertThat(isLaCountyTrigger, is(true));

  }

  @SuppressWarnings("javadoc")
  @Test
  public void testCreateLACountyTriggerForClientAddressNotCreated() throws Exception {
    gov.ca.cwds.rest.api.domain.cms.ClientAddress clientAddressDomain =
        new ClientAddressResourceBuilder().buildClientAddress();

    gov.ca.cwds.data.persistence.cms.ClientAddress toCreate =
        new gov.ca.cwds.data.persistence.cms.ClientAddress("1234567ABC", clientAddressDomain,
            "q1p");

    gov.ca.cwds.rest.api.domain.cms.ClientAddress request =
        new gov.ca.cwds.rest.api.domain.cms.ClientAddress(toCreate, false);

    when(triggerTablesDao.getLaCountySpecificCode()).thenReturn("20");

    StaffPerson staffPerson = new StaffPerson("BTr", null, "External Interface",
        "external interface", "SCXCIN7", " ", "", BigDecimal.valueOf(9165672100L), 0, null, "    ",
        "N", "MIZN02k00E", "  ", "    ", "19", "N", "3XPCP92q38", null);

    when(staffpersonDao.find(any(String.class))).thenReturn(staffPerson);
    when(clientAddressDao.create(any(gov.ca.cwds.data.persistence.cms.ClientAddress.class)))
        .thenReturn(toCreate);

    when(laCountyTrigger.createClientAddressCountyTrigger(
        any(gov.ca.cwds.data.persistence.cms.ClientAddress.class)))
            .thenAnswer(new Answer<Boolean>() {

              @Override
              public Boolean answer(InvocationOnMock invocation) throws Throwable {
                isLaCountyTrigger = true;
                return true;
              }
            });

    clientAddressService.create(request);
    assertThat(isLaCountyTrigger, is(true));

  }

  @SuppressWarnings("javadoc")
  @Test
  public void testUpdateLACountyTriggerForClientAddressNotCreated() throws Exception {
    gov.ca.cwds.rest.api.domain.cms.ClientAddress clientAddressDomain =
        new ClientAddressResourceBuilder().buildClientAddress();

    gov.ca.cwds.data.persistence.cms.ClientAddress toCreate =
        new gov.ca.cwds.data.persistence.cms.ClientAddress("1234567ABC", clientAddressDomain,
            "BTr");

    gov.ca.cwds.rest.api.domain.cms.ClientAddress request =
        new gov.ca.cwds.rest.api.domain.cms.ClientAddress(toCreate, false);

    when(triggerTablesDao.getLaCountySpecificCode()).thenReturn("19");

    StaffPerson staffPerson = new StaffPerson("BTr", null, "External Interface",
        "external interface", "SCXCIN7", " ", "", BigDecimal.valueOf(9165672100L), 0, null, "    ",
        "N", "MIZN02k00E", "  ", "    ", "19", "N", "3XPCP92q38", null);

    when(staffpersonDao.find(any(String.class))).thenReturn(staffPerson);
    when(clientAddressDao.update(any(gov.ca.cwds.data.persistence.cms.ClientAddress.class)))
        .thenReturn(toCreate);

    when(laCountyTrigger.createClientAddressCountyTrigger(
        any(gov.ca.cwds.data.persistence.cms.ClientAddress.class)))
            .thenAnswer(new Answer<Boolean>() {

              @Override
              public Boolean answer(InvocationOnMock invocation) throws Throwable {
                isLaCountyTrigger = true;
                return true;
              }
            });

    clientAddressService.update("1234567ABC", request);
    assertThat(isLaCountyTrigger, is(true));

  }

  @Override
  public void testCreateThrowsAssertionError() throws Exception {

  }

  @Override
  public void testCreateEmptyIDError() throws Exception {

  }

  @Override
  public void testCreateThrowsNotImplementedException() throws Exception {

  }

}
