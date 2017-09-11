package gov.ca.cwds.rest.services;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import gov.ca.cwds.data.cms.TestSystemCodeCache;
import gov.ca.cwds.data.ns.ParticipantDao;
import gov.ca.cwds.fixture.AddressResourceBuilder;
import gov.ca.cwds.fixture.ClientEntityBuilder;
import gov.ca.cwds.fixture.ParticipantResourceBuilder;
import gov.ca.cwds.fixture.ReporterResourceBuilder;
import gov.ca.cwds.fixture.ScreeningToReferralResourceBuilder;
import gov.ca.cwds.rest.api.domain.LegacyDescriptor;
import gov.ca.cwds.rest.api.domain.Participant;
import gov.ca.cwds.rest.api.domain.ScreeningToReferral;
import gov.ca.cwds.rest.api.domain.cms.Address;
import gov.ca.cwds.rest.api.domain.cms.ChildClient;
import gov.ca.cwds.rest.api.domain.cms.Client;
import gov.ca.cwds.rest.api.domain.cms.ClientAddress;
import gov.ca.cwds.rest.api.domain.cms.PostedAddress;
import gov.ca.cwds.rest.api.domain.cms.PostedClient;
import gov.ca.cwds.rest.api.domain.cms.PostedReporter;
import gov.ca.cwds.rest.api.domain.cms.Reporter;
import gov.ca.cwds.rest.messages.MessageBuilder;
import gov.ca.cwds.rest.services.cms.*;
import gov.ca.cwds.rest.services.cms.AddressService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.validation.Validation;
import javax.validation.Validator;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

public class ParticipantServiceTest {
  private ParticipantService participantService;

  private Participant defaultVictim;
  private Participant defaultReporter;
  private Participant defaultMandatedReporter;
  private Participant defaultPerpetrator;

  ParticipantDao participantDao;
  ClientService clientService;
  ReferralClientService referralClientService;
  ChildClientService childClientService;
  AddressService addressService;
  ClientAddressService clientAddressService;

  private DateTime lastUpdateDate;
  private DateTime modifiedLastUpdateDate;
  private DateTime updatedTime;
  private String dateStarted;
  private String referralId;
  private Date timestamp;
  private MessageBuilder messageBuilder;

  private Validator validator;

  TestSystemCodeCache testSystemCodeCache = new TestSystemCodeCache();
  private String savedAddressId;

  @Before
  public void setup(){
    defaultVictim = new ParticipantResourceBuilder().createVictimParticipant();
    defaultReporter = new ParticipantResourceBuilder().setRoles((new HashSet<>(Arrays
        .asList("Mandated Reporter")))).createReporterParticipant();
    defaultMandatedReporter= new ParticipantResourceBuilder().createReporterParticipant();

    defaultPerpetrator = new ParticipantResourceBuilder().createPerpParticipant();


    clientService = mock(ClientService.class);
    gov.ca.cwds.data.persistence.cms.Client savedEntityClient = new ClientEntityBuilder()
        .setId("1234567ABC").build();
    PostedClient savedClient = new PostedClient(savedEntityClient, false);
    when(clientService.createWithSingleTimestamp(any(), any())).thenReturn(savedClient);

    referralClientService = mock(ReferralClientService.class);

    //TODO: ReporterEntityBuilder Requires name change, and move rest of code to builder
    Reporter reporter = new ReporterResourceBuilder()
        .setReferralId("1234567ABC").build();
    gov.ca.cwds.data.persistence.cms.Reporter savedEntityReporter = new gov.ca.cwds.data.persistence
        .cms.Reporter(reporter, "1234567ABC");
    PostedReporter savedReporter = new PostedReporter(savedEntityReporter);
    ReporterService reporterService = mock(ReporterService.class);
    when(reporterService.createWithSingleTimestamp(any(), any())).thenReturn(savedReporter);

    childClientService = mock(ChildClientService.class);
    ChildClient childClient = mock(ChildClient.class);
    when(childClientService.create(any())).thenReturn(childClient);

    addressService = mock(AddressService.class);
    PostedAddress postedAddress = mock(PostedAddress.class);
    savedAddressId = "ZXCVBNMKJH";
    when(postedAddress.getExistingAddressId()).thenReturn(savedAddressId);
    when(addressService.createWithSingleTimestamp(any(), any())).thenReturn(postedAddress);

    clientAddressService = mock(ClientAddressService.class);

    messageBuilder = new MessageBuilder();

    validator = Validation.buildDefaultValidatorFactory().getValidator();
    lastUpdateDate = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
        .parseDateTime("2010-03-14T13:33:12.456-0700");
    modifiedLastUpdateDate = DateTimeFormat.forPattern("yyyy-MM-dd-HH.mm.ss.SSS")
        .parseDateTime("2010-03-14-13.33.12.456");
    updatedTime = lastUpdateDate.plusHours(2);
    dateStarted = "2017-10-21";
    referralId = "1234567890";
    timestamp = new Date();

    participantService = new ParticipantService(participantDao, clientService,
      referralClientService, reporterService, childClientService, addressService,
      clientAddressService, validator);
  }

  @SuppressWarnings("javadoc")
  @Test
  public void shouldFailWhenReporterHasIncompatiableRoles_MandatedNonMandatedFail()
      throws Exception {
    Participant mandatedNonMandatedReporter = new ParticipantResourceBuilder()
        .setRoles(new HashSet<>(Arrays.asList("Mandated Reporter", "Non-mandated Reporter")))
        .createParticipant();
    Set participants = new HashSet<>(Arrays.asList(mandatedNonMandatedReporter, defaultVictim));

    ScreeningToReferral screeningToReferral = new ScreeningToReferralResourceBuilder()
        .setParticipants(participants).createScreeningToReferral();

    participantService.saveParticipants(screeningToReferral, dateStarted, referralId, timestamp,
        messageBuilder);

    assertEquals("Expected only one error to have been recorded",messageBuilder.getMessages()
        .size(), 1);
    String message = messageBuilder.getMessages().get(0).getMessage().trim();
    String expectedErrorMessage = "Participant contains incompatiable roles";
    assertEquals("Expected Incompatible participant Role message to have been recorded",
        expectedErrorMessage, message);
  }

  @SuppressWarnings("javadoc")
  @Test
  // R - 00831
  public void shouldFailCreateWhenReporterRolesAreIncompatable_AnonymousSelfReporter()
      throws Exception {
    Participant anonymousSelfReporter = new ParticipantResourceBuilder()
        .setRoles(new HashSet<>(Arrays.asList("Anonymous Reporter", "Self Reported")))
        .createParticipant();
    Set participants = new HashSet<>(Arrays.asList(anonymousSelfReporter, defaultVictim));
    ScreeningToReferral screeningToReferral = new ScreeningToReferralResourceBuilder()
        .setParticipants(participants).createScreeningToReferral();

    participantService.saveParticipants(screeningToReferral, dateStarted, referralId, timestamp,
        messageBuilder);

    assertEquals("Expected only one error to have been recorded",messageBuilder.getMessages()
        .size(), 1);
    String message = messageBuilder.getMessages().get(0).getMessage().trim();
    String expectedErrorMessage = "Participant contains incompatiable roles";
    assertEquals("Expected Incompatible participant Role message to have been recorded",
        expectedErrorMessage, message);
  }

  @SuppressWarnings("javadoc")
  @Test
  public void testClientAddressExistSuccess() throws Exception {
    String addressId1 = "1111111111";
    gov.ca.cwds.rest.api.domain.Address address1 =
        new AddressResourceBuilder().setLegacyId("1111111111").setLegacySourceTable("ADDRS_T")
            .setStreetAddress("123 First St").createAddress();
    String addressId2 = "2222222222";
    gov.ca.cwds.rest.api.domain.Address address2 =
        new AddressResourceBuilder().setLegacyId("2222222222").setLegacySourceTable("ADDRS_T")
            .setStreetAddress("123 First St").createAddress();

    Participant selfReportingVictim = new ParticipantResourceBuilder()
        .setRoles(new HashSet<>(Arrays.asList("Non-mandated Reporter", "Victim")))
        .setAddresses(new HashSet<>(Arrays.asList(address1))).createParticipant();
    LegacyDescriptor descriptor = new LegacyDescriptor("", "", lastUpdateDate, "", "");
    selfReportingVictim.setLegacyDescriptor(descriptor);
    int numberOfReportingVictimsRoles = selfReportingVictim.getRoles().size();
    Participant perp = new ParticipantResourceBuilder()
        .setAddresses(new HashSet<>(Arrays.asList(address2))).createPerpParticipant();
    Set participants = new HashSet<>(Arrays.asList(selfReportingVictim, perp));
    ScreeningToReferral referral = new ScreeningToReferralResourceBuilder()
        .setParticipants(participants).createScreeningToReferral();

    ClientAddress clientAddress = mock(ClientAddress.class);
    ClientAddress foundClientAddress = mock(ClientAddress.class);
    List foundClientAddresses = new ArrayList<ClientAddress>();
    foundClientAddresses.add(foundClientAddress);
    Client updatedClient = mock(Client.class);
    PostedClient savedClient = mock(PostedClient.class);
    when(savedClient.getId()).thenReturn("ASDFGHHYTR");
    when(savedClient.getLastUpdatedTime()).thenReturn(modifiedLastUpdateDate);
    when(clientAddressService.find(any())).thenReturn(clientAddress);

    when(clientAddressService.findByAddressAndClient(any(), any()))
        .thenReturn(foundClientAddresses);
    when(clientService.find(any())).thenReturn(savedClient);
    when(clientService.create(any())).thenReturn(savedClient);
    when(clientService.createWithSingleTimestamp(any(), any())).thenReturn(savedClient);
    when(clientService.update(any(), any())).thenReturn(updatedClient);

    Address foundAddress = mock(Address.class);
    when(foundAddress.getExistingAddressId()).thenReturn("ZXCVBNMKJH");
    PostedAddress postedAddress = mock(PostedAddress.class);
    when(postedAddress.getExistingAddressId()).thenReturn("ZXCVBNMKJH");

    when(addressService.find(any())).thenReturn(foundAddress);
    when(addressService.create(any())).thenReturn(postedAddress);
    when(addressService.createWithSingleTimestamp(any(), any())).thenReturn(postedAddress);

    participantService.saveParticipants(referral, dateStarted, referralId, timestamp,
        messageBuilder);

    verify(addressService, times(numberOfReportingVictimsRoles)).find(eq(addressId1));
    verify(addressService).find(eq(addressId2 ));
    verify(clientAddressService, times(numberOfReportingVictimsRoles))
        .findByAddressAndClient(address1, selfReportingVictim);
    verify(clientAddressService).findByAddressAndClient(address2, perp);
  }

  @SuppressWarnings("javadoc")
  @Test
  public void shouldUpdateClientWhenClientIdIsPresent() throws Exception {
    String victimClientLegacyId = "ABC123DSAF";

    LegacyDescriptor descriptor = new LegacyDescriptor("", "", lastUpdateDate, "", "");
    Participant victim =
        new ParticipantResourceBuilder().setLegacyId(victimClientLegacyId).setLegacyDescriptor(descriptor).createParticipant();
    Set participants = new HashSet<>(Arrays.asList(victim, defaultReporter,
        defaultPerpetrator));

    ScreeningToReferral referral = new ScreeningToReferralResourceBuilder()
        .setParticipants(participants).createScreeningToReferral();

    Client foundClient = mock(Client.class);
    when(foundClient.getLastUpdatedTime()).thenReturn(modifiedLastUpdateDate);

    PostedClient createdClient = mock(PostedClient.class);
    when(createdClient.getId()).thenReturn("LEGACYIDXX");
    when(clientService.find(eq(victimClientLegacyId))).thenReturn(foundClient);
    when(clientService.create(any())).thenReturn(createdClient);

    Client updatedClient = mock(Client.class);
    when(clientService.update(eq(victimClientLegacyId), any())).thenReturn(updatedClient);

    participantService.saveParticipants(referral, dateStarted, referralId, timestamp,
        messageBuilder);

    verify(clientService).update(eq(victim.getLegacyId()), any());
  }


  @SuppressWarnings("javadoc")
  @Test
  public void shouldFailWhenVictimHasIncompatiableRoles_AnonymousVictim() throws Exception {
    Participant reporterVictim = new ParticipantResourceBuilder()
        .setRoles(new HashSet<>(Arrays.asList("Anonymous Reporter", "Victim"))).createParticipant();
    Set participants = new HashSet<>(Arrays.asList(reporterVictim, defaultPerpetrator));
    ScreeningToReferral screeningToReferral = new ScreeningToReferralResourceBuilder()
        .setParticipants(participants).createScreeningToReferral();

    participantService.saveParticipants(screeningToReferral, dateStarted, referralId, timestamp,
        messageBuilder);

    assertEquals("Expected only one error to have been recorded",messageBuilder.getMessages()
        .size(), 1);
    String message = messageBuilder.getMessages().get(0).getMessage().trim();
    String expectedErrorMessage = "Participant contains incompatiable roles";
    assertEquals("Expected Incompatible participant Role message to have been recorded",
        expectedErrorMessage, message);
  }

  @SuppressWarnings("javadoc")
  @Test
  public void shouldNotUpdateClientWhenClientRecordHasBeenModifiedInLegacyDb() throws Exception {
    DateTime modifiedLastUpdateDate = DateTimeFormat.forPattern("yyyy-MM-dd-HH.mm.ss.SSS")
        .parseDateTime("2000-01-27-15.34.55.123");
    String victimClientLegacyId = "ABC123DSAF";

    LegacyDescriptor descriptor = new LegacyDescriptor("", "", lastUpdateDate, "", "");
    Participant victim =
        new ParticipantResourceBuilder().setLegacyId(victimClientLegacyId).setLegacyDescriptor(descriptor).createParticipant();

    Set participants = new HashSet<>(Arrays.asList(victim, defaultReporter, defaultPerpetrator));
    ScreeningToReferral referral = new ScreeningToReferralResourceBuilder()
        .setParticipants(participants).createScreeningToReferral();

    Client updatedClient = mock(Client.class);
    Client foundClient = mock(Client.class);
    when(foundClient.getLastUpdatedTime()).thenReturn(modifiedLastUpdateDate);
    PostedClient createdClient = mock(PostedClient.class);
    when(createdClient.getId()).thenReturn("LEGACYIDXX");

    when(clientService.find(eq(victimClientLegacyId))).thenReturn(foundClient);
    when(clientService.create(any())).thenReturn(createdClient);
    when(clientService.update(eq(victimClientLegacyId), any())).thenReturn(updatedClient);

    participantService.saveParticipants(referral, dateStarted, referralId, timestamp,
        messageBuilder);

    assertEquals("Expected only one error to have been recorded",messageBuilder.getMessages()
        .size(), 1);
    String message = messageBuilder.getMessages().get(0).getMessage().trim();
    String expectedErrorMessage = "Unable to Update John Smith Client. Client was previously modified";
    assertEquals("Expected client previously modified message to have been recorded",
        expectedErrorMessage, message);
  }

  @SuppressWarnings("javadoc")
  @Test
  public void shouldFailSavingWhenAddressIdDoesNotExist() throws Exception {
    gov.ca.cwds.rest.api.domain.Address perpAddress =
        new AddressResourceBuilder().setLegacyId("1234567ABC").createAddress();
    Set perpAddresses = new HashSet();
    perpAddresses.add(perpAddress);
    Participant perp =
        new ParticipantResourceBuilder().setAddresses(perpAddresses).createPerpParticipant();
    gov.ca.cwds.rest.api.domain.Address victimAddress =
        new AddressResourceBuilder().setLegacyId("1234567ABC").createAddress();
    Set victimAddresses = new HashSet();
    victimAddresses.add(victimAddress);
    Participant victim =
        new ParticipantResourceBuilder().setAddresses(victimAddresses).createVictimParticipant();
    Set participants = new HashSet<>(Arrays.asList(victim, perp, defaultReporter));

    ScreeningToReferral screeningToReferral = new ScreeningToReferralResourceBuilder()
        .setParticipants(participants).createScreeningToReferral();

    participantService.saveParticipants(screeningToReferral, dateStarted, referralId, timestamp,
        messageBuilder);

    String expectedErrorMessage = "Legacy Id on Address does not correspond to an existing CMS/CWS Address";
    assertEquals("Expected Legacy Id error messages to have been recorded for both Addresses",
        messageBuilder.getMessages()
        .size(), 2);
    String message = messageBuilder.getMessages().get(0).getMessage().trim();
    assertEquals("Expected Address Legacy Id error message to have been recorded",
        expectedErrorMessage, message);
    message = messageBuilder.getMessages().get(1).getMessage().trim();
    assertEquals("Expected Address Legacy Id error message to have been recorded",
        expectedErrorMessage, message);
  }

  @SuppressWarnings("javadoc")
  @Test
  public void testAddressExistSuccess() throws Exception {
    gov.ca.cwds.rest.api.domain.Address address =
        new AddressResourceBuilder().setLegacyId("ASDFGHJKLQ").createAddress();
    Participant victim =
        new ParticipantResourceBuilder().setRoles(new HashSet<>(Arrays.asList("Victim")))
            .setAddresses(new HashSet<>(Arrays.asList(address))).createParticipant();
    Set participants = new HashSet<>(Arrays.asList(victim, defaultPerpetrator, defaultReporter));

    ScreeningToReferral referral = new ScreeningToReferralResourceBuilder()
        .setParticipants(participants).createScreeningToReferral();

    gov.ca.cwds.data.persistence.cms.DrmsDocument drmsDocument =
        new gov.ca.cwds.data.persistence.cms.DrmsDocument("ABC1234560", null, null, null, null,
            null);
    Address victimFoundAddress = mock(Address.class);
    when(victimFoundAddress.getExistingAddressId()).thenReturn("ADDRESS_ID");
    PostedAddress perpCreatedAddress = mock(PostedAddress.class);
    when(perpCreatedAddress.getExistingAddressId()).thenReturn("PERPADDRID");
    ClientAddress clientAddress = new ClientAddress();
    List clientAddresses = new ArrayList();
    clientAddresses.add(clientAddress);
    when(addressService.find(address.getLegacyId())).thenReturn(victimFoundAddress);
    when(addressService.create(any())).thenReturn(perpCreatedAddress);
    when(addressService.createWithSingleTimestamp(any(), any())).thenReturn(perpCreatedAddress);

    when(clientAddressService.find(address.getLegacyId())).thenReturn(mock(ClientAddress.class));
    when(clientAddressService.findByAddressAndClient(any(), any())).thenReturn(clientAddresses);
    MessageBuilder messageBuilder = new MessageBuilder();

    participantService.saveParticipants(referral, dateStarted, referralId, timestamp,
        messageBuilder);

    verify(addressService).find(eq(address.getLegacyId()));
  }

  @SuppressWarnings("javadoc")
  @Test
  public void shouldFailWhenVictimIsIncompatiableRoles_VictimPerpetrator() throws Exception {
    Participant perpVictim = new ParticipantResourceBuilder()
        .setRoles(new HashSet<>(Arrays.asList("Perpetrator", "Victim"))).createParticipant();
    Set participants = new HashSet<>(Arrays.asList(perpVictim, defaultMandatedReporter));

    ScreeningToReferral screeningToReferral = new ScreeningToReferralResourceBuilder()
        .setParticipants(participants).createScreeningToReferral();
    participantService.saveParticipants(screeningToReferral, dateStarted, referralId, timestamp,
        messageBuilder);

    assertEquals("Expected only one error to have been recorded",messageBuilder.getMessages()
        .size(), 1);
    String message = messageBuilder.getMessages().get(0).getMessage().trim();
    String expectedErrorMessage = "Participant contains incompatiable roles";
    assertEquals("Expected Incompatible participant Role message to have been recorded",
        expectedErrorMessage, message);
  }

  @SuppressWarnings("javadoc")
  @Test
  public void shouldFailWhenReporterHasIncompatiableRoles_AnonymousReporterFail() throws Exception {
    Participant anonymousNonMandatedReporter = new ParticipantResourceBuilder()
        .setRoles(new HashSet<>(Arrays.asList("Anonymous Reporter", "Non-mandated Reporter")))
        .createParticipant();
    Set participants = new HashSet<>(Arrays.asList(anonymousNonMandatedReporter, defaultVictim));
    ScreeningToReferral screeningToReferral = new ScreeningToReferralResourceBuilder()
        .setParticipants(participants).createScreeningToReferral();

    participantService.saveParticipants(screeningToReferral, dateStarted, referralId, timestamp,
        messageBuilder);

    assertEquals("Expected only one error to have been recorded",messageBuilder.getMessages()
        .size(), 1);
    String message = messageBuilder.getMessages().get(0).getMessage().trim();
    String expectedErrorMessage = "Participant contains incompatiable roles";
    assertEquals("Expected Incompatible participant Role message to have been recorded",
        expectedErrorMessage, message);
  }

  @SuppressWarnings("javadoc")
  @Test
  public void testClientDoesNotExsitFail() throws Exception {
    String badLegacyId = "IUKNOWNIDI";

    Participant perp = new ParticipantResourceBuilder().setLegacyId(badLegacyId)
        .setRoles(new HashSet<>(Arrays.asList("Perpetrator"))).createParticipant();
    Set participants = new HashSet<>(Arrays.asList(perp, defaultReporter, defaultVictim));

    ScreeningToReferral referral = new ScreeningToReferralResourceBuilder()
        .setParticipants(participants).createScreeningToReferral();

    participantService.saveParticipants(referral, dateStarted, referralId, timestamp,
        messageBuilder);

    assertEquals("Expected only one error to have been recorded",messageBuilder.getMessages()
        .size(), 1);
    String message = messageBuilder.getMessages().get(0).getMessage().trim();
    String expectedErrorMessage = "Legacy Id of Participant does not correspond to an existing "
        + "CWS/CMS Client";
    assertEquals("Expected participant to not been found message to have been recorded",
        expectedErrorMessage, message);
      verify(clientService, never()).update(eq(badLegacyId), any());
  }

  @SuppressWarnings("javadoc")
  @Test
  public void testMultipleVictimSuccess() throws Exception {
    Participant victim1 =
        new ParticipantResourceBuilder().setFirstName("Sally").createVictimParticipant();
    Participant victim2 =
        new ParticipantResourceBuilder().setFirstName("Fred").createVictimParticipant();
    Set participants = new HashSet<>(Arrays.asList(victim1, victim2, defaultReporter, defaultVictim));
    int numberOfClientsThatAreNotReporters = 3;

    ScreeningToReferral referral = new ScreeningToReferralResourceBuilder()
        .setParticipants(participants).createScreeningToReferral();

    participantService.saveParticipants(referral, dateStarted, referralId,
        timestamp,
        messageBuilder);

    assertEquals("Expected no error to have been recorded",messageBuilder.getMessages()
        .size(), 0);
    verify(clientService, times(numberOfClientsThatAreNotReporters))
        .createWithSingleTimestamp(any(), any());
  }

  @SuppressWarnings("javadoc")
  @Test
  public void shouldUpdatePerpetratorWhenAlreadyExists() throws Exception {
    String victimId = "VICTIM__ID";
    String existingPerpId = "1234567ABC";
    Participant reporter = new ParticipantResourceBuilder().setFirstName("Barney")
        .setLegacyId(victimId).setMiddleName("middlestone").setLastName("Rubble")
        .setRoles(new HashSet<>(Arrays.asList("Non-mandated Reporter", "Victim")))
        .createParticipant();
    LegacyDescriptor descriptor = new LegacyDescriptor("", "", lastUpdateDate, "", "");
    reporter.setLegacyDescriptor(descriptor);
    Participant perp = new ParticipantResourceBuilder().setLegacyId(existingPerpId)
        .setFirstName("Fred").setMiddleName("Finnigan").setLastName("Flintsone")
        .setRoles(new HashSet<>(Arrays.asList("Perpetrator"))).createParticipant();
    descriptor = new LegacyDescriptor("", "", lastUpdateDate, "", "");
    perp.setLegacyDescriptor(descriptor);
    Set participants = new HashSet<>(Arrays.asList(reporter, perp));

    ScreeningToReferral referral = new ScreeningToReferralResourceBuilder()
        .setParticipants(participants).createScreeningToReferral();

    gov.ca.cwds.rest.api.domain.cms.Client updatedPerp =
        mock(gov.ca.cwds.rest.api.domain.cms.Client.class);
    gov.ca.cwds.rest.api.domain.cms.Client updatedReporter =
        mock(gov.ca.cwds.rest.api.domain.cms.Client.class);
    when(updatedReporter.getLastUpdatedTime()).thenReturn(updatedTime);
    gov.ca.cwds.rest.api.domain.cms.Client foundVictim =
        mock(gov.ca.cwds.rest.api.domain.cms.Client.class);
    when(foundVictim.getLastUpdatedTime()).thenReturn(lastUpdateDate) // first call return original
        .thenReturn(updatedTime); // second call return updated time
    gov.ca.cwds.rest.api.domain.cms.Client foundPerp =
        mock(gov.ca.cwds.rest.api.domain.cms.Client.class);
    when(foundPerp.getLastUpdatedTime()).thenReturn(lastUpdateDate);
    gov.ca.cwds.rest.api.domain.cms.PostedClient savedVictim =
        mock(gov.ca.cwds.rest.api.domain.cms.PostedClient.class);
    when(savedVictim.getId()).thenReturn(victimId);
    when(savedVictim.getLastUpdatedTime()).thenReturn(updatedTime);
    when(clientService.update(eq(existingPerpId), any())).thenReturn(updatedPerp);
    when(clientService.create(any())).thenReturn(savedVictim);
    when(clientService.createWithSingleTimestamp(any(), any())).thenReturn(savedVictim);
    when(clientService.update(eq(victimId), any())).thenReturn(updatedReporter);
    when(clientService.find(victimId)).thenReturn(foundVictim);
    when(clientService.find(existingPerpId)).thenReturn(foundPerp);

    participantService.saveParticipants(referral, dateStarted, referralId, timestamp,
        messageBuilder);
    verify(foundVictim, times(2)).update("Barney", "middlestone", "Rubble", "Jr.");
    verify(foundPerp, times(1)).update("Fred", "Finnigan", "Flintsone", "Jr.");
    verify(clientService).update(eq(existingPerpId), any());
  }

  @SuppressWarnings("javadoc")
  @Test
  public void shouldReturnErrorMessageWhenUnableToSaveClient() throws Exception {
    String existingPerpId = "1234567ABC";
    Participant reporter =
        new ParticipantResourceBuilder().setFirstName("Barney").setLastName("Rubble")
            .setRoles(new HashSet<>(Arrays.asList("Non-mandated Reporter")))
            .createParticipant();
    LegacyDescriptor descriptor = new LegacyDescriptor("", "", lastUpdateDate, "", "");
    reporter.setLegacyDescriptor(descriptor);
    Participant perp = new ParticipantResourceBuilder().setLegacyId(existingPerpId)
        .setFirstName("Fred").setLastName("Flintsone")
        .setRoles(new HashSet<>(Arrays.asList("Perpetrator"))).createParticipant();
    Participant victim = new ParticipantResourceBuilder().createVictimParticipant();
    descriptor = new LegacyDescriptor("", "", lastUpdateDate, "", "");
    perp.setLegacyDescriptor(descriptor);
    Set participants = new HashSet<>(Arrays.asList(reporter, perp,victim));

    ScreeningToReferral referral = new ScreeningToReferralResourceBuilder()
        .setParticipants(participants).createScreeningToReferral();

    String updatedReporterId = "ASDFGHJZXC";
    gov.ca.cwds.rest.api.domain.cms.Client updatedPerp =
        mock(gov.ca.cwds.rest.api.domain.cms.Client.class);
    gov.ca.cwds.rest.api.domain.cms.Client updatedReporter =
        mock(gov.ca.cwds.rest.api.domain.cms.Client.class);
    gov.ca.cwds.rest.api.domain.cms.Client foundClient =
        mock(gov.ca.cwds.rest.api.domain.cms.Client.class);
    gov.ca.cwds.rest.api.domain.cms.PostedClient savedVictim =
        mock(gov.ca.cwds.rest.api.domain.cms.PostedClient.class);
    when(savedVictim.getId()).thenReturn(updatedReporterId);
    when(clientService.update(eq(existingPerpId), any())).thenReturn(updatedPerp);
    when(clientService.create(any())).thenReturn(savedVictim);
    when(clientService.update(any(), any())).thenReturn(null);
    when(clientService.find(any())).thenReturn(foundClient);
    when(foundClient.getLastUpdatedTime()).thenReturn(lastUpdateDate);
    Address foundAddress = mock(Address.class);
    when(foundAddress.getExistingAddressId()).thenReturn(savedAddressId);
    when(addressService.find(savedAddressId)).thenReturn(foundAddress);

    ClientAddress clientAddress = mock(ClientAddress.class);
    ArrayList addresses = new ArrayList();
    addresses.add(clientAddress);
    when(clientAddressService.findByAddressAndClient(any(),eq(reporter))).thenReturn(addresses);

    participantService.saveParticipants(referral, dateStarted, referralId, timestamp,
        messageBuilder);

    assertEquals("Expected only one error to have been recorded", 1, messageBuilder.getMessages()
        .size());
    String message = messageBuilder.getMessages().get(0).getMessage().trim();
    String expectedErrorMessage = "Unable to save Client";
    assertEquals("Expected unable to save message to have been recorded",
        expectedErrorMessage, message);
  }

  @SuppressWarnings("javadoc")
  @Test
  public void failsSavingWhenParticipantHasTwoRolesForLastUpdateTimeConflict() throws Exception {
    String existingPerpId = "1234567ABC";
    Participant reporter =
        new ParticipantResourceBuilder().setFirstName("Barney").setLastName("Rubble")
            .setRoles(new HashSet<>(Arrays.asList("Non-mandated Reporter", "Victim")))
            .createParticipant();
    LegacyDescriptor descriptor = new LegacyDescriptor("", "", lastUpdateDate, "", "");
    reporter.setLegacyDescriptor(descriptor);
    Participant perp = new ParticipantResourceBuilder().setLegacyId(existingPerpId)
        .setFirstName("Fred").setLastName("Flintsone")
        .setRoles(new HashSet<>(Arrays.asList("Perpetrator"))).createParticipant();
    descriptor = new LegacyDescriptor("", "", lastUpdateDate, "", "");
    perp.setLegacyDescriptor(descriptor);
    Set participants = new HashSet<>(Arrays.asList(reporter, perp));

    ScreeningToReferral referral = new ScreeningToReferralResourceBuilder()
        .setParticipants(participants).createScreeningToReferral();

    String updatedReporterId = "ASDFGHJZXC";
    gov.ca.cwds.rest.api.domain.cms.Client updatedPerp =
        mock(gov.ca.cwds.rest.api.domain.cms.Client.class);
    gov.ca.cwds.rest.api.domain.cms.Client updatedReporter =
        mock(gov.ca.cwds.rest.api.domain.cms.Client.class);
    gov.ca.cwds.rest.api.domain.cms.Client foundClient =
        mock(gov.ca.cwds.rest.api.domain.cms.Client.class);
    gov.ca.cwds.rest.api.domain.cms.PostedClient savedVictim =
        mock(gov.ca.cwds.rest.api.domain.cms.PostedClient.class);
    when(savedVictim.getId()).thenReturn(updatedReporterId);
    when(clientService.update(eq(existingPerpId), any())).thenReturn(updatedPerp);
    when(clientService.create(any())).thenReturn(savedVictim);
    when(clientService.find(any())).thenReturn(foundClient);
    when(foundClient.getLastUpdatedTime()).thenReturn(lastUpdateDate);
    Address foundAddress = mock(Address.class);
    when(foundAddress.getExistingAddressId()).thenReturn(savedAddressId);
    when(addressService.find(savedAddressId)).thenReturn(foundAddress);

    ClientAddress clientAddress = mock(ClientAddress.class);
    ArrayList addresses = new ArrayList();
    addresses.add(clientAddress);
    when(clientAddressService.findByAddressAndClient(any(),eq(reporter))).thenReturn(addresses);

    participantService.saveParticipants(referral, dateStarted, referralId, timestamp,
        messageBuilder);

    assertEquals("Expected only one error to have been recorded",1, messageBuilder.getMessages()
        .size());
    String message = messageBuilder.getMessages().get(0).getMessage().trim();
    String expectedErrorMessage = "Unable to Update Barney Rubble Client. Client was previously "
        + "modified";
    assertEquals("Expected client previously modified message to have been recorded",
        expectedErrorMessage, message);
  }

  @Test
  public void shouldApplySensitivityIndicatorFromReferralWhenSavingNewClient(){
    Set participants = new HashSet<>(Arrays.asList(defaultVictim, defaultReporter,
        defaultPerpetrator));

    ScreeningToReferral referral = new ScreeningToReferralResourceBuilder()
        .setLimitedAccessCode("S")
        .setParticipants(participants)
        .createScreeningToReferral();

    PostedClient createdClient = mock(PostedClient.class);
    when(clientService.create(any())).thenReturn(createdClient);

    ArgumentCaptor<Client> clientArgCaptor = ArgumentCaptor.forClass(Client.class);

    participantService.saveParticipants(referral, dateStarted, referralId, timestamp,
        messageBuilder);

    verify(clientService,times(2)).createWithSingleTimestamp(clientArgCaptor.capture() , any());
    assertEquals("Expected client to have sensitivty indicator applied", referral.getLimitedAccessCode(),
        clientArgCaptor.getValue().getSensitivityIndicator());
  }

  @Test
  public void shouldApplySensitivityIndicatorFromReferralWhenUpdatingClient(){
    String victimClientLegacyId = "ABC123DSAF";

    LegacyDescriptor descriptor = new LegacyDescriptor("", "", lastUpdateDate, "", "");
    Participant victim =
        new ParticipantResourceBuilder().setLegacyId(victimClientLegacyId).setLegacyDescriptor(descriptor).createParticipant();
    Set participants = new HashSet<>(Arrays.asList(victim, defaultReporter,
        defaultPerpetrator));

    ScreeningToReferral referral = new ScreeningToReferralResourceBuilder()
        .setLimitedAccessCode("S")
        .setParticipants(participants)
        .createScreeningToReferral();

    Client foundClient = mock(Client.class);
    when(foundClient.getLastUpdatedTime()).thenReturn(modifiedLastUpdateDate);

    PostedClient createdClient = mock(PostedClient.class);
    when(createdClient.getId()).thenReturn("LEGACYIDXX");
    when(clientService.find(eq(victimClientLegacyId))).thenReturn(foundClient);
    when(clientService.create(any())).thenReturn(createdClient);

    Client updatedClient = mock(Client.class);
    when(clientService.update(eq(victimClientLegacyId), any())).thenReturn(updatedClient);

    participantService.saveParticipants(referral, dateStarted, referralId, timestamp,
        messageBuilder);

    verify(foundClient).applySensitivityIndicator(eq(referral.getLimitedAccessCode()));
  }
}