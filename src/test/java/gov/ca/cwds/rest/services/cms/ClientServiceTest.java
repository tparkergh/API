package gov.ca.cwds.rest.services.cms;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.fasterxml.jackson.databind.ObjectMapper;

import gov.ca.cwds.data.cms.ClientDao;
import gov.ca.cwds.data.cms.CountyOwnershipDao;
import gov.ca.cwds.data.cms.ReferralClientDao;
import gov.ca.cwds.data.cms.ReferralDao;
import gov.ca.cwds.data.cms.SsaName3Dao;
import gov.ca.cwds.data.cms.StaffPersonDao;
import gov.ca.cwds.data.persistence.cms.StaffPerson;
import gov.ca.cwds.data.rules.TriggerTablesDao;
import gov.ca.cwds.fixture.AddressResourceBuilder;
import gov.ca.cwds.fixture.ClientResourceBuilder;
import gov.ca.cwds.fixture.ParticipantResourceBuilder;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.Address;
import gov.ca.cwds.rest.api.domain.Participant;
import gov.ca.cwds.rest.api.domain.RaceAndEthnicity;
import gov.ca.cwds.rest.api.domain.cms.Client;
import gov.ca.cwds.rest.api.domain.cms.PostedClient;
import gov.ca.cwds.rest.business.rules.ExternalInterfaceTables;
import gov.ca.cwds.rest.business.rules.NonLACountyTriggers;
import gov.ca.cwds.rest.business.rules.UpperCaseTables;
import gov.ca.cwds.rest.services.ServiceException;
import io.dropwizard.jackson.Jackson;

/**
 * @author CWDS API Team
 *
 */
@SuppressWarnings("javadoc")
public class ClientServiceTest {
  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();
  private ClientService clientService;
  private ClientDao clientDao;
  private StaffPersonDao staffpersonDao;
  private TriggerTablesDao triggerTablesDao;
  private NonLACountyTriggers nonLaCountyTriggers;
  private StaffPersonIdRetriever staffPersonIdRetriever;
  private SsaName3Dao ssaName3Dao;
  private UpperCaseTables upperCaseTables;
  private ExternalInterfaceTables externalInterfaceTables;
  private CountyOwnershipDao countyOwnershipDao;
  private ReferralDao referralDao;
  private ReferralClientDao referralClientDao;

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Before
  public void setup() throws Exception {
    clientDao = mock(ClientDao.class);
    staffpersonDao = mock(StaffPersonDao.class);
    triggerTablesDao = mock(TriggerTablesDao.class);
    countyOwnershipDao = mock(CountyOwnershipDao.class);
    referralDao = mock(ReferralDao.class);
    referralClientDao = mock(ReferralClientDao.class);
    nonLaCountyTriggers =
        new NonLACountyTriggers(countyOwnershipDao, referralDao, referralClientDao);
    staffPersonIdRetriever = mock(StaffPersonIdRetriever.class);
    ssaName3Dao = mock(SsaName3Dao.class);
    upperCaseTables = mock(UpperCaseTables.class);
    externalInterfaceTables = mock(ExternalInterfaceTables.class);

    clientService =
        new ClientService(clientDao, staffpersonDao, triggerTablesDao, nonLaCountyTriggers,
            staffPersonIdRetriever, ssaName3Dao, upperCaseTables, externalInterfaceTables);
  }

  // find test
  @Test
  public void testFindReturnsCorrectEntity() throws Exception {
    String id = "AaiU7IW0Rt";
    Date updated = new Date();
    DateTime lastUpdatedTime = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
        .parseDateTime("2004-03-31T09:45:58.000-0800");
    Client expected = new ClientResourceBuilder().setExistingClientId(id)
        .setLastUpdateTime(lastUpdatedTime).setConfidentialityActionDate("2016-03-11")
        .setDeathDate("2017-06-11").setFatherParentalRightTermDate("2017-04-01")
        .setMotherParentalRightTermDate("2015-01-10").setAddress(new HashSet<>()).build();
    gov.ca.cwds.data.persistence.cms.Client client =
        new gov.ca.cwds.data.persistence.cms.Client(id, expected, "04Z", updated);
    when(clientDao.find(id)).thenReturn(client);
    Client found = clientService.find(id);
    assertThat(found.getExistingClientId(), is(expected.getExistingClientId()));
  }

  @Test
  public void testFindReturnsNullWhenNotFound() throws Exception {
    Response found = clientService.find("ABC1234567");
    assertThat(found, is(nullValue()));
  }

  // delete test
  @Test
  public void testDeleteDelegatesToCrudsService() {
    clientService.delete("ABC2345678");
    verify(clientDao, times(1)).delete("ABC2345678");
  }

  @Test
  public void testDeleteReturnsNullWhenNotFound() throws Exception {
    Response found = clientService.delete("ABC1234567");
    assertThat(found, is(nullValue()));
  }

  @Test
  public void clientServiceDeleteReturnsNotNull() throws Exception {
    String id = "LOmv8Jp0Nu";
    gov.ca.cwds.rest.api.domain.cms.Client expected = new ClientResourceBuilder().build();
    gov.ca.cwds.data.persistence.cms.Client client =
        new gov.ca.cwds.data.persistence.cms.Client(id, expected, "0XA");

    when(clientDao.delete(id)).thenReturn(client);
    gov.ca.cwds.rest.api.domain.cms.Client found = clientService.delete(id);
    assertThat(found.getExistingClientId(), is(equalTo("LOmv8Jp0Nu")));
  }

  // update test
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

  @Test
  public void testUpdateThrowsExceptionWhenNotFound() throws Exception {
    try {
      Client clientRequest = MAPPER.readValue(
          fixture("fixtures/domain/legacy/Client/valid/serviceValid.json"), Client.class);

      Address address = new AddressResourceBuilder().createAddress();
      RaceAndEthnicity raceAndEthnicity =
          new RaceAndEthnicity(new LinkedHashSet<>(), "A", new LinkedHashSet<>(), "X", "A");
      Set<Address> addresses = new HashSet<>(Arrays.asList(address));
      Participant participant = new ParticipantResourceBuilder().setAddresses(addresses)
          .setRaceAndEthnicity(raceAndEthnicity).createParticipant();
      Client domainClient = Client.createWithDefaults(participant, "", "m", (short) 0);
      gov.ca.cwds.data.persistence.cms.Client savedClient =
          new gov.ca.cwds.data.persistence.cms.Client("123", domainClient, "OX5");
      when(clientDao.find(any())).thenReturn(savedClient);
      when(clientDao.update(any())).thenThrow(EntityNotFoundException.class);

      clientService.update("ZZZZZZZZZZ", clientRequest);
    } catch (Exception e) {
      assertEquals(e.getClass(), ServiceException.class);
    }
  }


  // create test
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

  @Test
  public void testFindInboundIdReturnsExisting() throws Exception {
    String id = "Aaeae9r0F4";
    Client clientDomain = MAPPER
        .readValue(fixture("fixtures/domain/legacy/Client/valid/serviceValid.json"), Client.class);
    gov.ca.cwds.data.persistence.cms.Client toCreate =
        new gov.ca.cwds.data.persistence.cms.Client(id, clientDomain, "q1p");

    when(clientDao.find(any(String.class))).thenReturn(toCreate);

    PostedClient postedClient = clientService.findInboundId("Aaeae9r0F4");
    assertThat(postedClient.getExistingClientId(), is(equalTo("Aaeae9r0F4")));

  }

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


  @Test
  public void testCreateNonLACountyTriggerForClient() throws Exception {
    Client clientDomain = new ClientResourceBuilder().build();
    gov.ca.cwds.data.persistence.cms.Client toCreate =
        new gov.ca.cwds.data.persistence.cms.Client("ABC1234567", clientDomain, "q1p");

    Client request = new Client(toCreate, false);

    when(triggerTablesDao.getLaCountySpecificCode()).thenReturn("20");

    StaffPerson staffPerson = new StaffPerson("BTr", null, "External Interface",
        "external interface", "SCXCIN7", " ", "", BigDecimal.valueOf(9165672100L), 0, null, "    ",
        "N", "MIZN02k00E", "  ", "    ", "19", "N", "3XPCP92q38", null);

    when(staffpersonDao.find(any(String.class))).thenReturn(staffPerson);
    when(clientDao.create(any(gov.ca.cwds.data.persistence.cms.Client.class))).thenReturn(toCreate);
    clientService.create(request);

    verify(countyOwnershipDao, times(1)).create(any());
  }

  @Test
  public void testCreateNonLACountyTriggerForClientNotCreated() throws Exception {
    Client clientDomain = new ClientResourceBuilder().build();
    gov.ca.cwds.data.persistence.cms.Client toCreate =
        new gov.ca.cwds.data.persistence.cms.Client("ABC1234567", clientDomain, "BTr");

    Client request = new Client(toCreate, false);

    when(triggerTablesDao.getLaCountySpecificCode()).thenReturn("19");

    StaffPerson staffPerson = new StaffPerson("BTr", null, "External Interface",
        "external interface", "SCXCIN7", " ", "", BigDecimal.valueOf(9165672100L), 0, null, "    ",
        "N", "MIZN02k00E", "  ", "    ", "19", "N", "3XPCP92q38", null);

    when(staffpersonDao.find(any(String.class))).thenReturn(staffPerson);
    when(clientDao.create(any(gov.ca.cwds.data.persistence.cms.Client.class))).thenReturn(toCreate);
    clientService.create(request);

    verify(countyOwnershipDao, times(0)).create(any());
  }

}
