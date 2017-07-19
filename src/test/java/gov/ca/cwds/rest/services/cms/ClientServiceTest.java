package gov.ca.cwds.rest.services.cms;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.fasterxml.jackson.databind.ObjectMapper;

import gov.ca.cwds.data.cms.ClientDao;
import gov.ca.cwds.data.cms.SsaName3Dao;
import gov.ca.cwds.data.cms.StaffPersonDao;
import gov.ca.cwds.data.rules.TriggerTablesDao;
import gov.ca.cwds.fixture.AddressResourceBuilder;
import gov.ca.cwds.fixture.ClientResourceBuilder;
import gov.ca.cwds.fixture.ParticipantResourceBuilder;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.Address;
import gov.ca.cwds.rest.api.domain.Participant;
import gov.ca.cwds.rest.api.domain.cms.Client;
import gov.ca.cwds.rest.api.domain.cms.PostedClient;
import gov.ca.cwds.rest.business.rules.NonLACountyTriggers;
import gov.ca.cwds.rest.business.rules.UpperCaseTables;
import gov.ca.cwds.rest.services.ServiceException;
import gov.ca.cwds.rest.services.junit.template.ServiceTestTemplate;
import io.dropwizard.jackson.Jackson;

/**
 * @author CWDS API Team
 *
 */
public class ClientServiceTest implements ServiceTestTemplate {
  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();
  private ClientService clientService;
  private ClientDao clientDao;
  private StaffPersonDao staffpersonDao;
  private TriggerTablesDao triggerTablesDao;
  private NonLACountyTriggers nonLaCountyTriggers;
  private StaffPersonIdRetriever staffPersonIdRetriever;
  private SsaName3Dao ssaName3Dao;
  private UpperCaseTables upperCaseTables;

  @SuppressWarnings("javadoc")
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Override
  @Before
  public void setup() throws Exception {
    clientDao = mock(ClientDao.class);
    staffpersonDao = mock(StaffPersonDao.class);
    triggerTablesDao = mock(TriggerTablesDao.class);
    nonLaCountyTriggers = mock(NonLACountyTriggers.class);
    staffPersonIdRetriever = mock(StaffPersonIdRetriever.class);
    ssaName3Dao = mock(SsaName3Dao.class);
    upperCaseTables = mock(UpperCaseTables.class);
    clientService = new ClientService(clientDao, staffpersonDao, triggerTablesDao,
        nonLaCountyTriggers, staffPersonIdRetriever, ssaName3Dao, upperCaseTables);
  }

  // find test
  @Override
  @Test
  public void testFindThrowsAssertionError() {
    // expect string type for primary key test
    thrown.expect(AssertionError.class);
    try {
      clientService.find(1);
    } catch (AssertionError e) {
      assertEquals("Expeceted AssertionError", e.getMessage());
    }
  }

  @Override
  @Test
  public void testFindReturnsCorrectEntity() throws Exception {
    String id = "AaiU7IW0Rt";
    Date updated = new Date();
    String formatedUpdateTime = DateFormatUtils.format(updated, "yyyy-MM-dd'T'HH:mm:ss.SSZ");
    Client expected = new ClientResourceBuilder().setExistingClientId(id)
        .setLastUpdateTime(formatedUpdateTime).setConfidentialityActionDate("2016-03-11")
        .setDeathDate("2017-06-11").setFatherParentalRightTermDate("2017-04-01")
        .setMotherParentalRightTermDate("2015-01-10").setAddress(new HashSet()).build();
    gov.ca.cwds.data.persistence.cms.Client client =
        new gov.ca.cwds.data.persistence.cms.Client(id, expected, "04Z", updated);
    when(clientDao.find(id)).thenReturn(client);
    Client found = clientService.find(id);
    assertThat(found.getExistingClientId(), is(expected.getExistingClientId()));
  }

  @Override
  @Test
  public void testFindReturnsNullWhenNotFound() throws Exception {
    Response found = clientService.find("ABC1234567");
    assertThat(found, is(nullValue()));
  }

  @Override
  @Test
  // delete test
  public void testDeleteThrowsAssertionError() throws Exception {
    // expect string type for primary key test
    thrown.expect(AssertionError.class);
    try {
      clientService.delete(123);
    } catch (AssertionError e) {
      assertEquals("Expected AssertionError", e.getMessage());
    }
  }

  @Override
  @Test
  public void testDeleteDelegatesToCrudsService() {
    clientService.delete("ABC2345678");
    verify(clientDao, times(1)).delete("ABC2345678");
  }

  @Override
  @Test
  public void testDeleteReturnsNullWhenNotFound() throws Exception {
    Response found = clientService.delete("ABC1234567");
    assertThat(found, is(nullValue()));
  }


  @Override
  public void testDeleteReturnsClass() throws Exception {

  }

  // update test
  @Override
  @Test
  public void testUpdateThrowsAssertionError() throws Exception {
    // expected string type for primary key test
    thrown.expect(AssertionError.class);
    try {
      clientService.update("ABC1234567", null);
    } catch (AssertionError e) {
      assertEquals("Expected AssertionError", e.getMessage());
    }
  }

  @Override
  @Test
  public void testUpdateReturnsCorrectEntity() throws Exception {
    String id = "Aaeae9r0F4";
    Client expected = MAPPER
        .readValue(fixture("fixtures/domain/legacy/Client/valid/serviceValid.json"), Client.class);

    gov.ca.cwds.data.persistence.cms.Client client =
        new gov.ca.cwds.data.persistence.cms.Client(id, expected, "ABC");

    when(clientDao.find("ABC1234567")).thenReturn(client);
    when(clientDao.update(any())).thenReturn(client);

    Object retval = clientService.update("ABC1234567", expected);
    assertThat(retval.getClass(), is(Client.class));
  }

  @SuppressWarnings("javadoc")
  @Test
  public void testUpdateThrowsExceptionWhenNotFound() throws Exception {
    try {
      Client clientRequest = MAPPER.readValue(
          fixture("fixtures/domain/legacy/Client/valid/serviceValid.json"), Client.class);

      Address address = new AddressResourceBuilder().createAddress();
      Set addresses = new HashSet(Arrays.asList(address));
      Participant participant =
          new ParticipantResourceBuilder().setAddresses(addresses).createParticipant();
      Client domainClient = Client.createWithDefaults(participant, "", "m");
      gov.ca.cwds.data.persistence.cms.Client savedClient =
          new gov.ca.cwds.data.persistence.cms.Client("123", domainClient, "OX5");
      when(clientDao.find(any())).thenReturn(savedClient);
      when(clientDao.update(any())).thenThrow(EntityNotFoundException.class);

      clientService.update("ZZZZZZZZZZ", clientRequest);
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
    String id = "Aaeae9r0F4";
    Client clientDomain = MAPPER
        .readValue(fixture("fixtures/domain/legacy/Client/valid/serviceValid.json"), Client.class);
    gov.ca.cwds.data.persistence.cms.Client toCreate =
        new gov.ca.cwds.data.persistence.cms.Client(id, clientDomain, "q1p");

    Client request = new Client(toCreate, false);
    when(clientDao.create(any(gov.ca.cwds.data.persistence.cms.Client.class))).thenReturn(toCreate);

    Response response = clientService.create(request);
    assertThat(response.getClass(), is(PostedClient.class));
  }

  @SuppressWarnings("javadoc")
  @Test
  public void reporterServiceCreateThrowsEntityExistsException() throws Exception {
    try {
      Client clientRequest = MAPPER.readValue(
          fixture("fixtures/domain/legacy/Client/valid/serviceValid.json"), Client.class);

      when(clientDao.create(any())).thenThrow(EntityExistsException.class);

      clientService.create(clientRequest);
    } catch (Exception e) {
      assertEquals(e.getClass(), ServiceException.class);
    }
  }

  @SuppressWarnings("javadoc")
  @Test
  public void testCreateReturnsNonNull() throws Exception {
    String id = "Aaeae9r0F4";
    Client clientDomain = MAPPER
        .readValue(fixture("fixtures/domain/legacy/Client/valid/serviceValid.json"), Client.class);
    gov.ca.cwds.data.persistence.cms.Client toCreate =
        new gov.ca.cwds.data.persistence.cms.Client(id, clientDomain, "q1p");

    Client request = new Client(toCreate, false);
    when(clientDao.create(any(gov.ca.cwds.data.persistence.cms.Client.class))).thenReturn(toCreate);

    PostedClient postedClient = clientService.create(request);
    assertThat(postedClient, is(notNullValue()));
  }

  @Override
  @Test
  public void testCreateReturnsCorrectEntity() throws Exception {
    String id = "Aaeae9r0F4";
    Client clientDomain = MAPPER
        .readValue(fixture("fixtures/domain/legacy/Client/valid/serviceValid.json"), Client.class);
    gov.ca.cwds.data.persistence.cms.Client toCreate =
        new gov.ca.cwds.data.persistence.cms.Client(id, clientDomain, "q1p");

    Client request = new Client(toCreate, false);
    when(clientDao.create(any(gov.ca.cwds.data.persistence.cms.Client.class))).thenReturn(toCreate);

    PostedClient expected = new PostedClient(toCreate, false);
    PostedClient returned = clientService.create(request);
    assertThat(returned, is(expected));
  }

  @Override
  @Test
  public void testCreateNullIDError() throws Exception {
    try {
      Client clientDomain = MAPPER.readValue(
          fixture("fixtures/domain/legacy/Client/valid/serviceValid.json"), Client.class);
      gov.ca.cwds.data.persistence.cms.Client toCreate =
          new gov.ca.cwds.data.persistence.cms.Client(null, clientDomain, "ABC");

      when(clientDao.create(any(gov.ca.cwds.data.persistence.cms.Client.class)))
          .thenReturn(toCreate);

    } catch (ServiceException e) {
      assertEquals("Client ID cannot be empty", e.getMessage());
    }

  }

  @Override
  @Test
  public void testCreateEmptyIDError() throws Exception {

    try {
      Client clientDomain = MAPPER.readValue(
          fixture("fixtures/domain/legacy/Client/valid/serviceValid.json"), Client.class);
      gov.ca.cwds.data.persistence.cms.Client toCreate =
          new gov.ca.cwds.data.persistence.cms.Client("    ", clientDomain, "ABC");

      when(clientDao.create(any(gov.ca.cwds.data.persistence.cms.Client.class)))
          .thenReturn(toCreate);

    } catch (ServiceException e) {
      assertEquals("Client ID cannot be empty", e.getMessage());
    }


  }

  /*
   * Test for checking the new Client Id generated and lenght is 10
   */
  @SuppressWarnings("javadoc")
  @Test
  public void createReturnsGeneratedId() throws Exception {
    Client clientDomain = MAPPER
        .readValue(fixture("fixtures/domain/legacy/Client/valid/serviceValid.json"), Client.class);
    when(clientDao.create(any(gov.ca.cwds.data.persistence.cms.Client.class)))
        .thenAnswer(new Answer<gov.ca.cwds.data.persistence.cms.Client>() {

          @Override
          public gov.ca.cwds.data.persistence.cms.Client answer(InvocationOnMock invocation)
              throws Throwable {
            gov.ca.cwds.data.persistence.cms.Client report =
                (gov.ca.cwds.data.persistence.cms.Client) invocation.getArguments()[0];
            return report;
          }
        });

    PostedClient returned = clientService.create(clientDomain);
    assertEquals(returned.getId().length(), 10);
    PostedClient newReturned = clientService.create(clientDomain);
    Assert.assertNotEquals(returned.getId(), newReturned.getId());
  }

  @Override
  public void testCreateBlankIDError() throws Exception {

  }

  @Override
  public void testCreateThrowsAssertionError() throws Exception {

  }



  @Override
  public void testCreateThrowsNotImplementedException() throws Exception {

  }

  @Override
  public void testFindThrowsNotImplementedException() throws Exception {

  }

  @Override
  public void testDeleteThrowsNotImplementedException() throws Exception {

  }

}
