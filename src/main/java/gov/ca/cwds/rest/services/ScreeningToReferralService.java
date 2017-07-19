package gov.ca.cwds.rest.services;

import gov.ca.cwds.rest.api.domain.DomainChef;
import gov.ca.cwds.rest.api.domain.comparator.EntityChangedComparator;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.Validator;

import org.apache.commons.lang3.NotImplementedException;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.Dao;
import gov.ca.cwds.data.cms.ReferralDao;
import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.Allegation;
import gov.ca.cwds.rest.api.domain.CrossReport;
import gov.ca.cwds.rest.api.domain.Participant;
import gov.ca.cwds.rest.api.domain.PostedScreeningToReferral;
import gov.ca.cwds.rest.api.domain.Screening;
import gov.ca.cwds.rest.api.domain.ScreeningToReferral;
import gov.ca.cwds.rest.api.domain.cms.Address;
import gov.ca.cwds.rest.api.domain.cms.ChildClient;
import gov.ca.cwds.rest.api.domain.cms.Client;
import gov.ca.cwds.rest.api.domain.cms.ClientAddress;
import gov.ca.cwds.rest.api.domain.cms.PostedAddress;
import gov.ca.cwds.rest.api.domain.cms.PostedAllegation;
import gov.ca.cwds.rest.api.domain.cms.PostedClient;
import gov.ca.cwds.rest.api.domain.cms.ReferralClient;
import gov.ca.cwds.rest.api.domain.cms.Reporter;
import gov.ca.cwds.rest.api.domain.error.ErrorMessage;
import gov.ca.cwds.rest.business.rules.Reminders;
import gov.ca.cwds.rest.messages.MessageBuilder;
import gov.ca.cwds.rest.services.cms.AddressService;
import gov.ca.cwds.rest.services.cms.AllegationPerpetratorHistoryService;
import gov.ca.cwds.rest.services.cms.AllegationService;
import gov.ca.cwds.rest.services.cms.AssignmentService;
import gov.ca.cwds.rest.services.cms.ChildClientService;
import gov.ca.cwds.rest.services.cms.ClientAddressService;
import gov.ca.cwds.rest.services.cms.ClientService;
import gov.ca.cwds.rest.services.cms.CrossReportService;
import gov.ca.cwds.rest.services.cms.ReferralClientService;
import gov.ca.cwds.rest.services.cms.ReferralService;
import gov.ca.cwds.rest.services.cms.ReporterService;
import gov.ca.cwds.rest.validation.ParticipantValidator;
import gov.ca.cwds.rest.validation.ValidationException;
import io.dropwizard.hibernate.UnitOfWork;

/**
 * Business layer object to work on {@link Screening}
 * 
 * @author CWDS API Team
 */
public class ScreeningToReferralService implements CrudsService {

  private static final Logger LOGGER = LoggerFactory.getLogger(ScreeningToReferral.class);

  private static final String CLIENT_TABLE_NAME = "CLIENT_T";
  private static final String ALLEGATION_TABLE_NAME = "ALLGTN_T";
  private static final String CROSS_REPORT_TABLE_NAME = "CRSS_RPT";
  private static final String CLIENT_ADDRESS_TABLE_NAME = "ADDRS_T";
  private static final String REPORTER_TABLE_NAME = "REPTR_T";

  private Validator validator;

  private MessageBuilder messageBuilder;

  private ReferralService referralService;
  private ClientService clientService;
  private AllegationService allegationService;
  private AllegationPerpetratorHistoryService allegationPerpetratorHistoryService;
  private CrossReportService crossReportService;
  private ReferralClientService referralClientService;
  private ReporterService reporterService;
  private Reporter savedReporter;
  private AddressService addressService;
  private ClientAddressService clientAddressService;
  private ChildClientService childClientService;
  private AssignmentService assignmentService;
  private Reminders reminders;

  private ReferralDao referralDao;

  private Short zipSuffix;

  LegacyDefaultValues legacyDefaultValues = new LegacyDefaultValues();

  LegacyCodes legacyCodes = new LegacyCodes();

  /**
   * Constructor
   * 
   * @param referralService the referralService
   * @param clientService the clientServiec
   * @param allegationService the allegationService
   * @param crossReportService the crossReportService
   * @param referralClientService the referralClientService
   * @param reporterService the reporterService
   * @param addressService - cms address service
   * @param clientAddressService - cms ClientAddress service
   * @param childClientService - cms ChildClient service
   * @param assignmentService CMS assignment service
   * @param validator - the validator
   * @param referralDao - The {@link Dao} handling {@link gov.ca.cwds.data.persistence.cms.Referral}
   *        objects.
   *        {@link gov.ca.cwds.rest.services.cms.StaffPersonIdRetriever} objects.
   * @param messageBuilder log message
   * @param allegationPerpetratorHistoryService the allegationPerpetratorHistoryService
   * @param reminders - reminders
   */
  @Inject
  public ScreeningToReferralService(ReferralService referralService, ClientService clientService,
      AllegationService allegationService, CrossReportService crossReportService,
      ReferralClientService referralClientService, ReporterService reporterService,
      AddressService addressService, ClientAddressService clientAddressService,
      ChildClientService childClientService,
      AssignmentService assignmentService, Validator validator, ReferralDao referralDao,
      MessageBuilder messageBuilder,
      AllegationPerpetratorHistoryService allegationPerpetratorHistoryService,
      Reminders reminders) {

    super();
    this.referralService = referralService;
    this.clientService = clientService;
    this.allegationService = allegationService;
    this.crossReportService = crossReportService;
    this.referralClientService = referralClientService;
    this.reporterService = reporterService;
    this.addressService = addressService;
    this.clientAddressService = clientAddressService;
    this.childClientService = childClientService;
    this.assignmentService = assignmentService;
    this.validator = validator;
    this.referralDao = referralDao;
    this.messageBuilder = messageBuilder;
    this.allegationPerpetratorHistoryService = allegationPerpetratorHistoryService;
    this.reminders = reminders;
  }

  @UnitOfWork(value = "cms")
  @Override
  public Response create(Request request) {
    ScreeningToReferral screeningToReferral = (ScreeningToReferral) request;
    verifyReferralHasValidParticipants(screeningToReferral);

    /**
     * For the referral transaction all the persisted objects lastupdatedTime should be unique
     */
    Date timestamp = new Date();

    /**
     * <blockquote>
     * 
     * <pre>
     * BUSINESS RULE: "R - 05446" - Default dateStarted and timeStarted
     * 
     * Referral received date and received time need to set when referral was created 
     * </blockquote>
     * </pre>
     */
    String dateStarted = ParticipantValidator.extractStartDate(screeningToReferral, messageBuilder);
    String timeStarted = ParticipantValidator.extractStartTime(screeningToReferral, messageBuilder);

    String referralId = createCmsReferral(screeningToReferral, dateStarted, timeStarted, timestamp);

    Set<Participant> resultParticipants = new HashSet<>();
    HashMap<Long, String> victimClient = new HashMap<>();
    HashMap<Long, String> perpatratorClient = new HashMap<>();

    processParticipants(screeningToReferral, dateStarted, referralId, resultParticipants,
        victimClient, perpatratorClient, timestamp);

    Set<CrossReport> resultCrossReports =
        createCrossReports(screeningToReferral, referralId, timestamp);

    Set<Allegation> resultAllegations = createAllegations(screeningToReferral, referralId,
        victimClient, perpatratorClient, timestamp);

    PostedScreeningToReferral pstr = PostedScreeningToReferral.createWithDefaults(referralId,
        screeningToReferral, resultParticipants, resultCrossReports, resultAllegations);

    reminders.createTickle(pstr);

    StringBuilder errorMessage = new StringBuilder();
    boolean foundError = false;
    if (!messageBuilder.getMessages().isEmpty()) {
      for (ErrorMessage message : messageBuilder.getMessages()) {
        if (StringUtils.isNotBlank(message.getMessage())) {
          foundError = true;
          errorMessage.append(message.getMessage());
          errorMessage.append("&&");
        }
      }
      if (foundError) {
        throw new ServiceException(errorMessage.toString(), new ValidationException());
      }
    }

    return pstr;
  }

  private Set<Allegation> createAllegations(ScreeningToReferral screeningToReferral,
      String referralId, HashMap<Long, String> victimClient,
      HashMap<Long, String> perpatratorClient, Date timestamp) {
    Set<Allegation> resultAllegations = null;
    try {
      resultAllegations = processAllegations(screeningToReferral, referralId, perpatratorClient,
          victimClient, timestamp);
    } catch (ServiceException e) {
      String message = e.getMessage();
      logError(message, e);
    }
    return resultAllegations;
  }

  private Set<CrossReport> createCrossReports(ScreeningToReferral screeningToReferral,
      String referralId, Date timestamp) {
    Set<CrossReport> resultCrossReports = null;
    try {
      resultCrossReports = processCrossReports(screeningToReferral, referralId, timestamp);
    } catch (ServiceException e) {
      String message = e.getMessage();
      logError(message, e);
    }
    return resultCrossReports;
  }

  private void processParticipants(ScreeningToReferral screeningToReferral, String dateStarted,
      String referralId, Set<Participant> resultParticipants, HashMap<Long, String> victimClient,
      HashMap<Long, String> perpetratorClient, Date timestamp) {
    Set<Participant> participants = screeningToReferral.getParticipants();
    for (Participant incomingParticipant : participants) {

      try {
        if (!ParticipantValidator.hasValidRoles(incomingParticipant)) {
          String message = " Participant contains incompatiable roles ";
          logError(message);
          // next participant
          continue;
        }
      } catch (Exception e1) {
        String message = e1.getMessage();
        logError(message, e1);
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
              savedReporter = processReporter(incomingParticipant, role, referralId, timestamp);
              incomingParticipant.setLegacyId(savedReporter.getReferralId());
              incomingParticipant.setLegacySourceTable(REPORTER_TABLE_NAME);
            } catch (ServiceException e) {
              String message = e.getMessage();
              logError(message, e);
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
                messageBuilder.addDomainValidationError(validator.validate(client));
                PostedClient postedClient =
                    this.clientService.createWithSingleTimestamp(client, timestamp);
                clientId = postedClient.getId();
                incomingParticipant.setLegacyId(clientId);
                incomingParticipant.setLegacySourceTable(CLIENT_TABLE_NAME);
              } else {
                // legacy Id passed - check for existenct in CWS/CMS - no update yet
                clientId = incomingParticipant.getLegacyId();
                Client foundClient = this.clientService.find(clientId);
                if (foundClient != null) {
                  EntityChangedComparator comparator = new EntityChangedComparator();
                  if (comparator.compare(incomingParticipant, foundClient)) {
                    foundClient.update(incomingParticipant.getFirstName(),
                        incomingParticipant.getMiddleName(), incomingParticipant.getLastName(),
                        incomingParticipant.getNameSuffix());
                    gov.ca.cwds.rest.api.domain.cms.Client savedClient =
                        this.clientService.update(incomingParticipant.getLegacyId(), foundClient);
                    if (savedClient == null) {
                      String message = "Unable to save Client";
                      logError(message);
                    }
                  } else {
                    String message = String.format(
                        "Unable to Update %s %s Client. Client was previously modified",
                        incomingParticipant.getFirstName(), incomingParticipant.getLastName());
                    logError(message);
                  }
                } else {
                  String message =
                      " Legacy Id of Participant does not correspond to an existing CWS/CMS Client ";
                  logError(message);
                  // next role
                  continue;
                }
              }

              // CMS Referral Client
              ReferralClient referralClient = ReferralClient.createWithDefault(
                  ParticipantValidator.selfReported(incomingParticipant), referralId, clientId,
                  legacyDefaultValues.DEFAULT_COUNTY_SPECIFIC_CODE, legacyDefaultValues.DEFAULT_APPROVAL_STATUS_CODE);

              // validate referral client
              messageBuilder.addDomainValidationError(validator.validate(referralClient));

              try {
                this.referralClientService.createWithSingleTimestamp(referralClient, timestamp);
              } catch (ServiceException se) {
                logError(se.getMessage(), se);
              }

              /*
               * determine other participant/roles attributes relating to CWS/CMS allegation
               */
              if (ParticipantValidator.roleIsVictim(role)) {
                victimClient.put(incomingParticipant.getId(), clientId);
                // since this is the victim - process the ChildClient
                try {
                  this.processChildClient(incomingParticipant, clientId);
                } catch (ServiceException e) {
                  String message = e.getMessage();
                  logError(message, e);
                  // next role
                  continue;
                }
              }

              if (ParticipantValidator.roleIsPerpetrator(role)) {
                perpetratorClient.put(incomingParticipant.getId(), clientId);
              }

              try {
                // addresses associated with a client
                Participant resultParticipant =
                    processClientAddress(incomingParticipant, referralId, clientId, timestamp);
              } catch (ServiceException e) {
                String message = e.getMessage();
                logError(message, e);
                // next role
                continue;
              }
            }
          }
        } catch (Exception e) {
          String message = e.getMessage();
          logError(message, e);
        }
        resultParticipants.add(incomingParticipant);
      } // next role
    } // next participant

  }

  private boolean unchanged(Participant incomingClient, Client savedClient) throws ParseException {
    DateTimeFormatter formatter = DateTimeFormat.forPattern(DomainChef.TIMESTAMP_FORMAT);;
    DateTime dbDate = formatter.parseDateTime(savedClient.getLastUpdatedTime());

    DateTimeFormatter formatter2 = DateTimeFormat.forPattern(DomainChef.TIMESTAMP_STRICT_FORMAT);
    DateTime incommingDate = formatter2.parseDateTime(incomingClient.getLegacyDescriptor().getLastUpdated());
    return dbDate.isEqual(incommingDate);
  }

  private String createCmsReferral(ScreeningToReferral screeningToReferral, String dateStarted,
      String timeStarted, Date timestamp) {
      return referralService.createCmsReferralFromScreening(screeningToReferral, dateStarted, timeStarted, timestamp, messageBuilder );
  }

  private void verifyReferralHasValidParticipants(ScreeningToReferral screeningToReferral) {
    try {
      if (!ParticipantValidator.hasValidParticipants(screeningToReferral)) {
        String message = " Incompatiable participants included in request";
        logError(message);
      }
    } catch (Exception e) {
      String message = e.getMessage();
      logError(message, e);
    }
  }

  private void logError(String message) {
    messageBuilder.addError(message);
    LOGGER.error(message);
  }

  private void logError(String message, Exception exception) {
    messageBuilder.addError(message);
    LOGGER.error(message, exception.getMessage());
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#delete(java.io.Serializable)
   * 
   *      <pre>
   * 
   * DocTool Rule R - 00796 
   * 
   * If the user had originally indicated that the call should be treated as a referral,
   * and that referral had been committed to the database, that referral must be deleted from the
   * system. Also deleted would be any referral clients associated with the referral, any clients
   * associated to the referral if that referral had been their only interaction with the system,
   * and all allegations associated with the referral. Do NOT delete the client if the client
   * already exists on the Host and associated to other Case or Referral.
   * 
   * This rule involves deleting a referral and associated referral clients, clients and
   * allegations. Since there is no business requirement at this time to delete a referral we will
   * not be implementing this rule. A NO-OP delete method is provided that gives a Not
   * Implemented Exception.
   *      </pre>
   */
  @Override
  public Response delete(Serializable primaryKey) {
    assert primaryKey instanceof String;
    throw new NotImplementedException("Delete is not implemented");
  }

  @Override
  public gov.ca.cwds.rest.api.domain.cms.Referral find(Serializable primaryKey) {
    assert primaryKey instanceof String;
    gov.ca.cwds.data.persistence.cms.Referral persistedReferral = referralDao.find(primaryKey);
    if (persistedReferral != null) {
      return new gov.ca.cwds.rest.api.domain.cms.Referral(persistedReferral);
    }
    return null;
  }

  @Override
  public Response update(Serializable primaryKey, Request request) {
    assert primaryKey instanceof String;
    throw new NotImplementedException("Update is not implemented");
  }

  /*
   * CMS Cross Report
   */
  private Set<gov.ca.cwds.rest.api.domain.CrossReport> processCrossReports(ScreeningToReferral scr,
      String referralId, Date timestamp) throws ServiceException {

    String crossReportId = "";
    Set<gov.ca.cwds.rest.api.domain.CrossReport> resultCrossReports = new HashSet<>();
    Set<CrossReport> crossReports;
    crossReports = scr.getCrossReports();

    if (crossReports != null) {

      for (CrossReport crossReport : crossReports) {

        Boolean lawEnforcementIndicator = false;

        /**
         * <blockquote>
         * 
         * <pre>
         * BUSINESS RULE: "R - 02535" - Do not report to In-State Law
         * 
         * IF    CrossReport agency type is 'Law Enforcement' 
         * AND   Reporter is 'Mandated Reporter'
         * THEN  Set lawEnforcementIndicator = false
         * </blockquote>
         * </pre>
         */
        boolean mandatedReporter =
            ParticipantValidator.hasMandatedReporterRole(scr.getParticipants());
        boolean lawEnforcementAgencyType = crossReport.getAgencyType().contains("Law Enforcement");

        if (lawEnforcementAgencyType && !mandatedReporter) {
          lawEnforcementIndicator = true;
        }

        if (crossReport.getLegacyId() == null || crossReport.getLegacyId().isEmpty()) {
          // create the cross report
          gov.ca.cwds.rest.api.domain.cms.CrossReport cmsCrossReport =
              gov.ca.cwds.rest.api.domain.cms.CrossReport.createWithDefaults(crossReportId,
                  crossReport, referralId, legacyDefaultValues.DEFAULT_STAFF_PERSON_ID, legacyDefaultValues.DEFAULT_COUNTY_SPECIFIC_CODE,
                  lawEnforcementIndicator);

          messageBuilder.addDomainValidationError(validator.validate(cmsCrossReport));

          gov.ca.cwds.rest.api.domain.cms.CrossReport postedCrossReport =
              this.crossReportService.createWithSingleTimestamp(cmsCrossReport, timestamp);

          crossReport.setLegacyId(postedCrossReport.getThirdId());
          crossReport.setLegacySourceTable(CROSS_REPORT_TABLE_NAME);
          resultCrossReports.add(crossReport);
        } else {
          gov.ca.cwds.rest.api.domain.cms.CrossReport foundCrossReport =
              this.crossReportService.find(crossReport.getLegacyId());
          if (foundCrossReport == null) {
            String message =
                " Legacy Id on Cross Report does not correspond to an existing CMS/CWS Cross Report ";
            ServiceException se = new ServiceException(message);
            logError(message, se);

          }
        }
      }
    }

    return resultCrossReports;
  }

  /*
   * CMS Allegation - one for each allegation
   */
  private Set<Allegation> processAllegations(ScreeningToReferral scr, String referralId,
      HashMap<Long, String> perpatratorClient, HashMap<Long, String> victimClient, Date timestamp)
      throws ServiceException {

    Set<Allegation> processedAllegations = new HashSet<>();
    Set<Allegation> allegations;
    String victimClientId = "";
    String perpatratorClientId = "";

    allegations = scr.getAllegations();

    /**
     * <blockquote>
     * 
     * <pre>
     * BUSINESS RULE: "R - 03895"
     * 
     * There must be at least one allegation
     * AND
     * Allegation disposition type should be 0 (zero)
     * </blockquote>
     * </pre>
     */
    final Short allegationDispositionType = legacyDefaultValues.DEFAULT_CODE;

    if (allegations == null || allegations.isEmpty()) {
      String message = " Referral must have at least one Allegation ";
      ServiceException exception = new ServiceException(message);
      logError(message, exception);
      return processedAllegations;
    }

    for (Allegation allegation : allegations) {

      try {
        if (!ParticipantValidator.isVictimParticipant(scr, allegation.getVictimPersonId())) {
          String message =
              " Allegation/Victim Person Id does not contain a Participant with a role of Victim ";
          logError(message);
        }
      } catch (Exception e) {
        logError(e.getMessage(), e);
        // next allegation
        continue;
      }
      if (victimClient.containsKey(allegation.getVictimPersonId())) {
        // this is the legacy Id (CLIENT) of the victim
        victimClientId = victimClient.get(allegation.getVictimPersonId());
      }

      if (allegation.getPerpetratorPersonId() != 0) {
        try {
          if (!ParticipantValidator.isPerpetratorParticipant(scr,
              allegation.getPerpetratorPersonId())) {
            String message =
                "Allegation/Perpetrator Person Id does not contain a Participant with a role of Perpetrator";
            ServiceException exception = new ServiceException(message);
            logError(message, exception);
          }
        } catch (Exception e) {
          logError(e.getMessage(), e);
          // next allegation
          continue;
        }
      }

      if (perpatratorClient.containsKey(allegation.getPerpetratorPersonId())) {
        // this is the legacy Id (CLIENT) of the perpetrator
        perpatratorClientId = perpatratorClient.get(allegation.getPerpetratorPersonId());
      }
      if (victimClientId.isEmpty()) {
        String message = "Victim could not be determined for an allegation";
        ServiceException exception = new ServiceException(message);
        logError(message, exception);
        // next allegation
        continue;
      }

      if (allegation.getLegacyId() == null || allegation.getLegacyId().isEmpty()) {
        // create an allegation in CMS legacy database
        gov.ca.cwds.rest.api.domain.cms.Allegation cmsAllegation =
            new gov.ca.cwds.rest.api.domain.cms.Allegation("", legacyDefaultValues.DEFAULT_CODE, "",
                scr.getLocationType(), "", allegationDispositionType, allegation.getType(),
                "", "", false, legacyDefaultValues.DEFAULT_NON_PROTECTING_PARENT_CODE, false,
                victimClientId, perpatratorClientId, referralId, legacyDefaultValues.DEFAULT_COUNTY_SPECIFIC_CODE,
                false, legacyDefaultValues.DEFAULT_CODE);

        messageBuilder.addDomainValidationError(validator.validate(cmsAllegation));

        PostedAllegation postedAllegation =
            this.allegationService.createWithSingleTimestamp(cmsAllegation, timestamp);
        allegation.setLegacyId(postedAllegation.getId());
        allegation.setLegacySourceTable(ALLEGATION_TABLE_NAME);
        processedAllegations.add(allegation);

        // create the Allegation Perpetrator History
        gov.ca.cwds.rest.api.domain.cms.AllegationPerpetratorHistory cmsPerpHistory =
            new gov.ca.cwds.rest.api.domain.cms.AllegationPerpetratorHistory(
                legacyDefaultValues.DEFAULT_COUNTY_SPECIFIC_CODE, postedAllegation.getVictimClientId(),
                postedAllegation.getId(), "2017-07-03");

        messageBuilder.addDomainValidationError(validator.validate(cmsPerpHistory));

        this.allegationPerpetratorHistoryService.createWithSingleTimestamp(cmsPerpHistory,
            timestamp);

      } else {
        gov.ca.cwds.rest.api.domain.cms.Allegation foundAllegation =
            this.allegationService.find(allegation.getLegacyId());
        if (foundAllegation == null) {
          String message =
              "Legacy Id on Allegation does not correspond to an existing CMS/CWS Allegation";
          ServiceException se = new ServiceException(message);
          logError(message, se);
          // next allegation
          continue;
        }
      }
    }
    return processedAllegations;
  }

  /*
   * CMS Address - create ADDRESS and CLIENT_ADDRESS for each address of the participant
   */
  private Participant processClientAddress(Participant clientParticipant, String referralId,
      String clientId, Date timestamp) throws ServiceException {

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
        Address domainAddress = Address.createWithDefaults(address, legacyDefaultValues.DEFAULT_STATE_CODE);
        zipSuffix = domainAddress.getZip4();

        messageBuilder.addDomainValidationError(validator.validate(domainAddress));

        PostedAddress postedAddress =
            (PostedAddress) this.addressService.createWithSingleTimestamp(domainAddress, timestamp);
        addressId = postedAddress.getExistingAddressId();
      } else {
        // verify that Address row exist - no update for now
        Address foundAddress = (Address) this.addressService.find(address.getLegacyId());
        if (foundAddress == null) {
          String message =
              " Legacy Id on Address does not correspond to an existing CMS/CWS Address ";
          ServiceException se = new ServiceException(message);
          logError(message, se);
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
        logError(message, se);
        // next address
        continue;
      }
      if (clientId.isEmpty()) {
        String message = " CLIENT/IDENTIFIER is required for CLIENT_ADDRESS ";
        ServiceException se = new ServiceException(message);
        logError(message, se);
        // next address
        continue;
      }

      boolean createNewClientAddress =
          address.getLegacyId() == null || address.getLegacyId().isEmpty();
      if (createNewClientAddress) {
        if (!clientAddressExists(address, clientParticipant)) {
          ClientAddress clientAddress = new ClientAddress(legacyDefaultValues.DEFAULT_ADDRESS_TYPE, "", "", "",
              addressId, clientId, "", referralId);

          messageBuilder.addDomainValidationError(validator.validate(clientAddress));
          this.clientAddressService.createWithSingleTimestamp(clientAddress, timestamp);

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
          logError(message, se);
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

  private Reporter processReporter(Participant ip, String role, String referralId, Date timestamp)
      throws ServiceException {

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
        zipSuffix = null;
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
          reporterAddress, ip, legacyDefaultValues.DEFAULT_COUNTY_SPECIFIC_CODE, legacyDefaultValues.DEFAULT_STATE_CODE);

      messageBuilder.addDomainValidationError(validator.validate(reporter));
      theReporter = reporterService.createWithSingleTimestamp(reporter, timestamp);
    }
    return theReporter;
  }

  private ChildClient processChildClient(Participant id, String clientId) throws ServiceException {

    ChildClient exsistingChild = this.childClientService.find(clientId);
    if (exsistingChild == null) {
      ChildClient childClient = ChildClient.createWithDefaults(clientId);
      messageBuilder.addDomainValidationError(validator.validate(childClient));
      exsistingChild = this.childClientService.create(childClient);
    }
    return exsistingChild;
  }
}
