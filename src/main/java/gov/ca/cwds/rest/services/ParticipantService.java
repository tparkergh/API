package gov.ca.cwds.rest.services;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.Validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.Dao;
import gov.ca.cwds.data.ns.ParticipantDao;
import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.Participant;
import gov.ca.cwds.rest.api.domain.Person;
import gov.ca.cwds.rest.api.domain.ScreeningToReferral;
import gov.ca.cwds.rest.api.domain.cms.Address;
import gov.ca.cwds.rest.api.domain.cms.ChildClient;
import gov.ca.cwds.rest.api.domain.cms.Client;
import gov.ca.cwds.rest.api.domain.cms.ClientAddress;
import gov.ca.cwds.rest.api.domain.cms.PostedAddress;
import gov.ca.cwds.rest.api.domain.cms.PostedClient;
import gov.ca.cwds.rest.api.domain.cms.ReferralClient;
import gov.ca.cwds.rest.api.domain.cms.Reporter;
import gov.ca.cwds.rest.api.domain.comparator.EntityChangedComparator;
import gov.ca.cwds.rest.messages.MessageBuilder;
import gov.ca.cwds.rest.services.cms.AddressService;
import gov.ca.cwds.rest.services.cms.ChildClientService;
import gov.ca.cwds.rest.services.cms.ClientAddressService;
import gov.ca.cwds.rest.services.cms.ClientScpEthnicityService;
import gov.ca.cwds.rest.services.cms.ClientService;
import gov.ca.cwds.rest.services.cms.ReferralClientService;
import gov.ca.cwds.rest.services.cms.ReporterService;
import gov.ca.cwds.rest.validation.ParticipantValidator;

/**
 * Business layer object to work on {@link Address}
 * 
 * @author CWDS API Team
 */
public class ParticipantService implements CrudsService {
  private static final String REPORTER_TABLE_NAME = "REPTR_T";
  private static final String CLIENT_TABLE_NAME = "CLIENT_T";
  private static final String CLIENT_ADDRESS_TABLE_NAME = "ADDRS_T";

  private static final Logger LOGGER = LoggerFactory.getLogger(ParticipantService.class);

  private Validator validator;

  private ParticipantDao participantDao;
  @Inject
  private PersonService personService;
  private ClientService clientService;
  private ReferralClientService referralClientService;
  private ReporterService reporterService;
  private ChildClientService childClientService;
  private AddressService addressService;
  private ClientAddressService clientAddressService;
  private ClientScpEthnicityService clientScpEthnicityService;

  /**
   * Constructor
   * 
   * @param participantDao The {@link Dao} handling
   *        {@link gov.ca.cwds.data.persistence.ns.Participant}
   * @param clientService the clientService
   * @param referralClientService the referralClientService
   * @param reporterService the reporterService
   * @param childClientService the childClientService
   * @param addressService the addressService
   * @param clientAddressService the clientAddressService
   * @param validator - the validator
   * @param clientScpEthnicityService - clientScpEthnicityService
   */
  @Inject
  public ParticipantService(ParticipantDao participantDao, ClientService clientService,
      ReferralClientService referralClientService, ReporterService reporterService,
      ChildClientService childClientService, AddressService addressService,
      ClientAddressService clientAddressService, Validator validator,
      ClientScpEthnicityService clientScpEthnicityService) {
    this.participantDao = participantDao;
    this.validator = validator;
    this.clientService = clientService;
    this.referralClientService = referralClientService;
    this.reporterService = reporterService;
    this.childClientService = childClientService;
    this.addressService = addressService;
    this.clientAddressService = clientAddressService;
    this.clientScpEthnicityService = clientScpEthnicityService;
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#create(gov.ca.cwds.rest.api.Request)
   */
  @Override
  public Response create(Request request) {
    assert request instanceof Participant;
    Participant participant = (Participant) request;
    gov.ca.cwds.data.persistence.ns.Participant managed =
        new gov.ca.cwds.data.persistence.ns.Participant(participant, null, null);
    Person person = personService.find(managed.getPersonId());
    managed = participantDao.create(managed);
    return new Participant(managed, person);
  }

  /**
   * @param screeningToReferral - screeningToReferral
   * @param dateStarted - dateStarted
   * @param referralId - referralId
   * @param timestamp - timestamp
   * @param messageBuilder - messageBuilder
   * 
   * @return the savedParticioants
   */
  public ClientParticipants saveParticipants(ScreeningToReferral screeningToReferral,
      String dateStarted, String referralId, Date timestamp, MessageBuilder messageBuilder) {
    ClientParticipants clientParticipants = new ClientParticipants();

    Set<Participant> participants = screeningToReferral.getParticipants();
    for (Participant incomingParticipant : participants) {

      try {
        if (!ParticipantValidator.hasValidRoles(incomingParticipant)) {
          String message = " Participant contains incompatiable roles ";
          messageBuilder.addMessageAndLog(message, LOGGER);
          // next participant
          continue;
        }
      } catch (Exception e1) {
        String message = e1.getMessage();
        messageBuilder.addMessageAndLog(message, e1, LOGGER);
        // next participant
        continue;
      }

      String genderCode = "";
      if (!incomingParticipant.getGender().isEmpty()) {
        genderCode = incomingParticipant.getGender().toUpperCase().substring(0, 1);
      }
      Set<String> roles = new HashSet<>(incomingParticipant.getRoles());

      /**
       * process the roles of this participant
       */
      for (String role : roles) {

        try {
          if (ParticipantValidator.roleIsReporterType(role)
              && (!ParticipantValidator.roleIsAnonymousReporter(role)
                  && !ParticipantValidator.selfReported(incomingParticipant))) {
            /*
             * CMS Reporter - if role is 'mandated reporter' or 'non-mandated reporter' and not
             * anonymous reporter or self-reported
             */
            try {
              Reporter savedReporter = saveReporter(incomingParticipant, role, referralId,
                  timestamp, screeningToReferral.getIncidentCounty(), messageBuilder);
              incomingParticipant.setLegacyId(savedReporter.getReferralId());
              incomingParticipant.setLegacySourceTable(REPORTER_TABLE_NAME);
            } catch (ServiceException e) {
              String message = e.getMessage();
              messageBuilder.addMessageAndLog(message, e, LOGGER);
              // next role
              continue;
            }
          } else {
            // not a reporter participant - make a CLIENT and REFERRAL_CLIENT unless anonymous
            // reporter
            if (!ParticipantValidator.roleIsAnonymousReporter(role)) {
              String clientId;

              boolean newClient = incomingParticipant.getLegacyId() == null
                  || incomingParticipant.getLegacyId().isEmpty();
              if (newClient) {
                Client client =
                    Client.createWithDefaults(incomingParticipant, dateStarted, genderCode);
                client.applySensitivityIndicator(screeningToReferral.getLimitedAccessCode());
                client.applySensitivityIndicator(incomingParticipant.getSensitivityIndicator());
                messageBuilder.addDomainValidationError(validator.validate(client));
                PostedClient postedClient =
                    this.clientService.createWithSingleTimestamp(client, timestamp);
                clientId = postedClient.getId();
                incomingParticipant.setLegacyId(clientId);
                incomingParticipant.setLegacySourceTable(CLIENT_TABLE_NAME);
                incomingParticipant.getLegacyDescriptor()
                    .setLastUpdated(postedClient.getLastUpdatedTime());
                clientScpEthnicityService.createOtherEthnicity(postedClient.getId(),
                    incomingParticipant.getRaceAndEthnicity());
              } else {
                // legacy Id passed - check for existenct in CWS/CMS - no update yet
                clientId = incomingParticipant.getLegacyId();
                Client foundClient = this.clientService.find(clientId);
                if (foundClient != null) {
                  EntityChangedComparator comparator = new EntityChangedComparator();
                  if (comparator.compare(incomingParticipant, foundClient)) {
                    foundClient
                        .applySensitivityIndicator(screeningToReferral.getLimitedAccessCode());
                    foundClient
                        .applySensitivityIndicator(incomingParticipant.getSensitivityIndicator());
                    foundClient.update(incomingParticipant.getFirstName(),
                        incomingParticipant.getMiddleName(), incomingParticipant.getLastName(),
                        incomingParticipant.getNameSuffix());
                    gov.ca.cwds.rest.api.domain.cms.Client savedClient =
                        this.clientService.update(incomingParticipant.getLegacyId(), foundClient);
                    if (savedClient != null) {
                      incomingParticipant.getLegacyDescriptor()
                          .setLastUpdated(savedClient.getLastUpdatedTime());
                    } else {
                      String message = "Unable to save Client";
                      messageBuilder.addMessageAndLog(message, LOGGER);

                    }
                  } else {
                    String message = String.format(
                        "Unable to Update %s %s Client. Client was previously modified",
                        incomingParticipant.getFirstName(), incomingParticipant.getLastName());
                    messageBuilder.addMessageAndLog(message, LOGGER);
                  }
                } else {
                  String message =
                      " Legacy Id of Participant does not correspond to an existing CWS/CMS Client ";
                  messageBuilder.addMessageAndLog(message, LOGGER);
                  // next role
                  continue;
                }
              }

              // CMS Referral Client
              ReferralClient referralClient = ReferralClient.createWithDefault(
                  ParticipantValidator.selfReported(incomingParticipant),
                  incomingParticipant.isClientStaffPersonAdded(), referralId, clientId,
                  LegacyDefaultValues.DEFAULT_COUNTY_SPECIFIC_CODE,
                  LegacyDefaultValues.DEFAULT_APPROVAL_STATUS_CODE);

              // validate referral client
              messageBuilder.addDomainValidationError(validator.validate(referralClient));

              try {
                referralClientService.createWithSingleTimestamp(referralClient, timestamp);
              } catch (ServiceException se) {
                messageBuilder.addMessageAndLog(se.getMessage(), se, LOGGER);
              }

              /*
               * determine other participant/roles attributes relating to CWS/CMS allegation
               */
              if (ParticipantValidator.roleIsVictim(role)) {
                clientParticipants.addVictimIds(incomingParticipant.getId(), clientId);
                // since this is the victim - process the ChildClient
                try {
                  processChildClient(incomingParticipant, clientId, messageBuilder);
                } catch (ServiceException e) {
                  String message = e.getMessage();
                  messageBuilder.addMessageAndLog(message, e, LOGGER);
                  // next role
                  continue;
                }
              }

              if (ParticipantValidator.roleIsPerpetrator(role)) {
                clientParticipants.addPerpetratorIds(incomingParticipant.getId(), clientId);
              }

              try {
                // addresses associated with a client
                Participant resultParticipant = processClientAddress(incomingParticipant,
                    referralId, clientId, timestamp, messageBuilder);
              } catch (ServiceException e) {
                String message = e.getMessage();
                messageBuilder.addMessageAndLog(message, e, LOGGER);
                // next role
                continue;
              }
            }
          }
        } catch (Exception e) {
          String message = e.getMessage();
          messageBuilder.addMessageAndLog(message, e, LOGGER);
        }
        clientParticipants.addParticipant(incomingParticipant);
      } // next role
    } // next participant

    return clientParticipants;
  }

  private Reporter saveReporter(Participant ip, String role, String referralId, Date timestamp,
      String countySpecificCode, MessageBuilder messageBuilder) throws ServiceException {

    gov.ca.cwds.rest.api.domain.Address reporterAddress = null;

    if (ip.getAddresses() != null) {
      Set<gov.ca.cwds.rest.api.domain.Address> addresses = new HashSet<>(ip.getAddresses());

      // use the first address node only
      for (gov.ca.cwds.rest.api.domain.Address address : addresses) {
        // TODO: #141511573 address parsing - Smarty Streets Free Form display requires
        // standardizing parsing to fields in CMS
        if (address == null) {
          // next address
          continue;
        }
        reporterAddress = address;
        Short zipSuffix = null;
        if (address.getZip().toString().length() > 5) {
          zipSuffix = Short.parseShort(address.getZip().toString().substring(5));
        }
        break;
      }
    }

    Boolean mandatedReporterIndicator = ParticipantValidator.roleIsMandatedReporter(role);
    Reporter theReporter = reporterService.find(referralId);
    if (theReporter == null) {
      Reporter reporter = Reporter.createWithDefaults(referralId, mandatedReporterIndicator,
          reporterAddress, ip, countySpecificCode);

      messageBuilder.addDomainValidationError(validator.validate(reporter));
      theReporter = reporterService.createWithSingleTimestamp(reporter, timestamp);
    }
    return theReporter;
  }

  private ChildClient processChildClient(Participant id, String clientId,
      MessageBuilder messageBuilder) throws ServiceException {

    ChildClient exsistingChild = this.childClientService.find(clientId);
    if (exsistingChild == null) {
      ChildClient childClient = ChildClient.createWithDefaults(clientId);
      messageBuilder.addDomainValidationError(validator.validate(childClient));
      exsistingChild = this.childClientService.create(childClient);
    }
    return exsistingChild;
  }

  /*
   * CMS Address - create ADDRESS and CLIENT_ADDRESS for each address of the participant
   */
  private Participant processClientAddress(Participant clientParticipant, String referralId,
      String clientId, Date timestamp, MessageBuilder messageBuilder) throws ServiceException {

    String addressId = new String("");
    Set<gov.ca.cwds.rest.api.domain.Address> addresses;
    Set<gov.ca.cwds.rest.api.domain.Address> newAddresses = new HashSet<>();
    addresses = clientParticipant.getAddresses();

    if (addresses == null) {
      return null;
    }

    for (gov.ca.cwds.rest.api.domain.Address address : addresses) {
      if (address.getLegacyId() == null || address.getLegacyId().isEmpty()) {
        // add the Address row
        Address domainAddress = Address.createWithDefaults(address);
        Short zipSuffix = domainAddress.getZip4();

        messageBuilder.addDomainValidationError(validator.validate(domainAddress));

        PostedAddress postedAddress =
            this.addressService.createWithSingleTimestamp(domainAddress, timestamp);
        addressId = postedAddress.getExistingAddressId();
      } else {
        // verify that Address row exist - no update for now
        Address foundAddress = this.addressService.find(address.getLegacyId());
        if (foundAddress == null) {
          String message =
              " Legacy Id on Address does not correspond to an existing CMS/CWS Address ";
          ServiceException se = new ServiceException(message);
          messageBuilder.addMessageAndLog(message, se, LOGGER);
          // next address
          continue;
        }
        addressId = foundAddress.getExistingAddressId();
      }

      /*
       * CMS Client Address
       */
      if (addressId.isEmpty()) {
        String message = " ADDRESS/IDENTIFIER is required for CLIENT_ADDRESS table ";
        ServiceException se = new ServiceException(message);
        messageBuilder.addMessageAndLog(message, se, LOGGER);
        // next address
        continue;
      }
      if (clientId.isEmpty()) {
        String message = " CLIENT/IDENTIFIER is required for CLIENT_ADDRESS ";
        ServiceException se = new ServiceException(message);
        messageBuilder.addMessageAndLog(message, se, LOGGER);
        // next address
        continue;
      }

      boolean createNewClientAddress =
          address.getLegacyId() == null || address.getLegacyId().isEmpty();
      if (createNewClientAddress) {
        if (!clientAddressExists(address, clientParticipant)) {
          Short addressType = address.getType() != null ? address.getType().shortValue()
              : LegacyDefaultValues.DEFAULT_ADDRESS_TYPE;
          ClientAddress clientAddress =
              new ClientAddress(addressType, "", "", "", addressId, clientId, "", referralId);

          messageBuilder.addDomainValidationError(validator.validate(clientAddress));
          clientAddressService.createWithSingleTimestamp(clientAddress, timestamp);

          messageBuilder.addDomainValidationError(validator.validate(clientAddress));

          // update the addresses of the participant
          address.setLegacySourceTable(CLIENT_ADDRESS_TABLE_NAME);
          address.setLegacyId(addressId);
          newAddresses.add(address);
        }
      } else {
        // verify that ClientAddress exists - no update for now
        if (!clientAddressExists(address, clientParticipant)) {
          String message =
              " Legacy Id on Address does not correspond to an existing CMS/CWS Client Address ";
          ServiceException se = new ServiceException(message);
          messageBuilder.addMessageAndLog(message, se, LOGGER);
          // next address
          continue;
        }
      }
    }

    return clientParticipant;
  }

  private boolean clientAddressExists(gov.ca.cwds.rest.api.domain.Address address,
      Participant client) {
    List foundClientAddress = this.clientAddressService.findByAddressAndClient(address, client);
    return foundClientAddress != null && !foundClientAddress.isEmpty();
  }

  @Override
  public Response delete(Serializable arg0) {
    return null;
  }

  @Override
  public Response find(Serializable arg0) {
    return null;
  }

  @Override
  public Response update(Serializable arg0, Request arg1) {
    return null;
  }

}
