package gov.ca.cwds.rest.services;

import gov.ca.cwds.rest.messages.MessageBuilder;
import gov.ca.cwds.rest.validation.ValidationException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.apache.commons.lang3.NotImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.Dao;
import gov.ca.cwds.data.cms.ReferralDao;
import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.Allegation;
import gov.ca.cwds.rest.api.domain.CrossReport;
import gov.ca.cwds.rest.api.domain.DomainObject;
import gov.ca.cwds.rest.api.domain.Participant;
import gov.ca.cwds.rest.api.domain.PostedScreeningToReferral;
import gov.ca.cwds.rest.api.domain.Screening;
import gov.ca.cwds.rest.api.domain.ScreeningToReferral;
import gov.ca.cwds.rest.api.domain.cms.Address;
import gov.ca.cwds.rest.api.domain.cms.ChildClient;
import gov.ca.cwds.rest.api.domain.cms.Client;
import gov.ca.cwds.rest.api.domain.cms.ClientAddress;
import gov.ca.cwds.rest.api.domain.cms.LongText;
import gov.ca.cwds.rest.api.domain.cms.PostedAddress;
import gov.ca.cwds.rest.api.domain.cms.PostedAllegation;
import gov.ca.cwds.rest.api.domain.cms.PostedClient;
import gov.ca.cwds.rest.api.domain.cms.PostedLongText;
import gov.ca.cwds.rest.api.domain.cms.PostedReferral;
import gov.ca.cwds.rest.api.domain.cms.Referral;
import gov.ca.cwds.rest.api.domain.cms.ReferralClient;
import gov.ca.cwds.rest.api.domain.cms.Reporter;
import gov.ca.cwds.rest.api.domain.error.ErrorMessage;
import gov.ca.cwds.rest.services.cms.AddressService;
import gov.ca.cwds.rest.services.cms.AllegationService;
import gov.ca.cwds.rest.services.cms.ChildClientService;
import gov.ca.cwds.rest.services.cms.ClientAddressService;
import gov.ca.cwds.rest.services.cms.ClientService;
import gov.ca.cwds.rest.services.cms.CrossReportService;
import gov.ca.cwds.rest.services.cms.LongTextService;
import gov.ca.cwds.rest.services.cms.ReferralClientService;
import gov.ca.cwds.rest.services.cms.ReferralService;
import gov.ca.cwds.rest.services.cms.ReporterService;
import gov.ca.cwds.rest.services.cms.StaffPersonIdRetriever;
import gov.ca.cwds.rest.validation.ParticipantValidator;
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

  final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
  final DateFormat timeFormat = new SimpleDateFormat("HH:MM:SS");
  final DateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);

  private Validator validator;

  private MessageBuilder messageBuilder;

  private ReferralService referralService;
  private ClientService clientService;
  private AllegationService allegationService;
  private CrossReportService crossReportService;
  private ReferralClientService referralClientService;
  private ReporterService reporterService;
  private Reporter savedReporter;
  private AddressService addressService;
  private ClientAddressService clientAddressService;
  private LongTextService longTextService;
  private ChildClientService childClientService;
  private StaffPersonIdRetriever staffPersonIdRetriever;

  private ReferralDao referralDao;

  private Short zipSuffix;

  // default values
  private static final short DEFAULT_CODE = 0;
  private static final short DEFAULT_STATE_CODE = 1828; // california
  private static final short DEFAULT_APPROVAL_STATUS_CODE = 118;
  private static final String DEFAULT_NON_PROTECTING_PARENT_CODE = "U";
  private static final short DEFAULT_ADDRESS_TYPE = 32; // residence
  private static final String DEFAULT_COUNTY_SPECIFIC_CODE = "62";
  private static final String DEFAULT_STAFF_PERSON_ID = "0X5";

  // TODO: #142337489 Develop List of Value service to support Pi2 Save Referral to CWS/CMS
  private short communicationsMethodCode = 409; // default to telephone until #142337489 complete
  private short referralResponseTypeCode = 0;
  private short allegationTypeCode = 0;

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
   * @param longTextService - cms LongText service
   * @param childClientService - cms ChildClient service
   * @param validator - the validator
   * @param referralDao - The {@link Dao} handling {@link gov.ca.cwds.data.persistence.cms.Referral}
   *        objects.
   * @param staffPersonIdRetriever = The staffPersonId handling
   *        {@link gov.ca.cwds.rest.services.cms.StaffPersonIdRetriever} objects.
   */
  @Inject
  public ScreeningToReferralService(ReferralService referralService, ClientService clientService,
      AllegationService allegationService, CrossReportService crossReportService,
      ReferralClientService referralClientService, ReporterService reporterService,
      AddressService addressService, ClientAddressService clientAddressService,
      LongTextService longTextService, ChildClientService childClientService, Validator validator,
      ReferralDao referralDao, StaffPersonIdRetriever staffPersonIdRetriever, MessageBuilder messageBuilder) {

    super();
    this.referralService = referralService;
    this.clientService = clientService;
    this.allegationService = allegationService;
    this.crossReportService = crossReportService;
    this.referralClientService = referralClientService;
    this.reporterService = reporterService;
    this.addressService = addressService;
    this.clientAddressService = clientAddressService;
    this.longTextService = longTextService;
    this.childClientService = childClientService;
    this.validator = validator;
    this.referralDao = referralDao;
    this.staffPersonIdRetriever = staffPersonIdRetriever;
    this.messageBuilder = messageBuilder;
  }

  @UnitOfWork(value = "cms")
  @Override
  public Response create(Request request) {
    ScreeningToReferral screeningToReferral = (ScreeningToReferral) request;

    Set<ErrorMessage> messages = new HashSet<>();

    verifyReferralHasValidParticipants(screeningToReferral);

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

    String referralId = createCmsReferral(screeningToReferral, dateStarted, timeStarted);
    createReferralAddress(screeningToReferral);

    Set<Participant> resultParticipants = new HashSet<>();
    HashMap<Long, String> victimClient = new HashMap<>();
    HashMap<Long, String> perpatratorClient = new HashMap<>();

    processParticipants(screeningToReferral, dateStarted, referralId, resultParticipants,
        victimClient, perpatratorClient);

    Set<CrossReport> resultCrossReports =
        createCrossReports(screeningToReferral, referralId);

    Set<Allegation> resultAllegations = createAllegations(screeningToReferral, referralId,
        victimClient, perpatratorClient);

    PostedScreeningToReferral pstr = PostedScreeningToReferral.createWithDefaults(referralId,
        screeningToReferral, resultParticipants, resultCrossReports, resultAllegations);
    pstr.setMessages(messageBuilder.getMessages());
    return pstr;
  }

  private Set<Allegation> createAllegations(ScreeningToReferral screeningToReferral,
      String referralId, HashMap<Long, String> victimClient,
      HashMap<Long, String> perpatratorClient) {
    Set<Allegation> resultAllegations = null;
    try {
      resultAllegations = processAllegations(screeningToReferral, referralId, perpatratorClient,
          victimClient);
    } catch (ServiceException e) {
      String message = e.getMessage();
      logError(message, e);
    }
    return resultAllegations;
  }

  private Set<CrossReport> createCrossReports(ScreeningToReferral screeningToReferral,
     String referralId) {
    Set<CrossReport> resultCrossReports = null;
    try {
      resultCrossReports = processCrossReports(screeningToReferral, referralId);
    } catch (ServiceException e) {
      String message = e.getMessage();
    }
    return resultCrossReports;
  }

  private void processParticipants(ScreeningToReferral screeningToReferral,
       String dateStarted, String referralId,
      Set<Participant> resultParticipants, HashMap<Long, String> victimClient,
      HashMap<Long, String> perpatratorClient) {
    Set<Participant> participants = screeningToReferral.getParticipants();
    for (Participant incomingParticipant : participants) {

      try {
        if (!ParticipantValidator.hasValidRoles(incomingParticipant)) {
          String message = " Participant contains incompatiable roles ";
          ServiceException exception = new ServiceException(message);
          logError(message, exception);
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
              savedReporter = processReporter(incomingParticipant, role, referralId);
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

              if (incomingParticipant.getLegacyId() == null
                  || incomingParticipant.getLegacyId().isEmpty()) {
                // legacy Id not set - create a CLIENT row
                PostedClient postedClient;
                // not an anonymous reporter participant - create client
                Client client =
                    Client.createWithDefaults(incomingParticipant, dateStarted, genderCode);
                messageBuilder.addDomainValidationError(validator.validate(client));

                postedClient = this.clientService.create(client);
                clientId = postedClient.getId();
                incomingParticipant.setLegacyId(clientId);
                incomingParticipant.setLegacySourceTable(CLIENT_TABLE_NAME);
              } else {
                // legacy Id passed - check for existenct in CWS/CMS - no update yet
                clientId = incomingParticipant.getLegacyId();
                Client foundClient = this.clientService.find(clientId);
                if (foundClient == null) {
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
                  DEFAULT_COUNTY_SPECIFIC_CODE, DEFAULT_APPROVAL_STATUS_CODE);

              // validate referral client
              messageBuilder.addDomainValidationError(validator.validate(referralClient));

              try {
                gov.ca.cwds.rest.api.domain.cms.ReferralClient postedReferralClient =
                    this.referralClientService.create(referralClient);
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
                perpatratorClient.put(incomingParticipant.getId(), clientId);
              }
              try {
                // addresses associated with a client
                Participant resultParticipant =
                    processClientAddress(incomingParticipant, referralId, clientId);
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

  private void createReferralAddress(ScreeningToReferral screeningToReferral) {
    try {
      gov.ca.cwds.rest.api.domain.Address referralAddress =
          processReferralAddress(screeningToReferral);
      screeningToReferral.setAddress(referralAddress);
    } catch (ServiceException e1) {
      String message = e1.getMessage();
      logError(message, e1);
    }
  }

  private String createCmsReferral(ScreeningToReferral screeningToReferral,
      String dateStarted, String timeStarted) {
    String referralId = null;

    /**
     * <blockquote>
     * 
     * <pre>
     * BUSINESS RULE: "R - 04537" - FKSTFPERS0 set when first referral determined
     * 
     * IF    referralResponseTypeCode is set to default
     * THEN  firstResponseDeterminedByStaffPersonId is set to the staffpersonId
     * </blockquote>
     * </pre>
     */
    String firstResponseDeterminedByStaffPersonId = staffPersonIdRetriever.getStaffPersonId();

    if (screeningToReferral.getReferralId() == null
        || screeningToReferral.getReferralId().isEmpty()) {
      // the legacy id is not set - create the referral
      String longTextId = generateLongTextId(screeningToReferral);
      // create a CMS Referral
      Referral referral = null;
      try {
        referral = Referral.createWithDefaults(
            ParticipantValidator.anonymousReporter(screeningToReferral), communicationsMethodCode,
            screeningToReferral.getName(), dateStarted, timeStarted, referralResponseTypeCode,
            firstResponseDeterminedByStaffPersonId, longTextId, DEFAULT_COUNTY_SPECIFIC_CODE,
            DEFAULT_APPROVAL_STATUS_CODE, DEFAULT_STAFF_PERSON_ID);
      } catch (Exception e1) {
        String message = e1.getMessage();
        logError(message, e1);
      }

      messageBuilder.addDomainValidationError(validator.validate(referral));

      PostedReferral postedReferral = this.referralService.create(referral);
      referralId = postedReferral.getId();

    } else {
      // Referral ID passed - validate that Referral exist in CWS/CMS - no update for now
      referralId = screeningToReferral.getReferralId();
      Referral foundReferral = this.referralService.find(referralId);
      if (foundReferral == null) {
        String message = "Legacy Id does not correspond to an existing CMS/CWS Referral";
        ServiceException se = new ServiceException(message);
        logError(message, se);
      }
    }
    return referralId;
  }

  private String generateLongTextId(ScreeningToReferral screeningToReferral ) {
    String longTextId = null;
    if (screeningToReferral.getAdditionalInformation() == null
        || screeningToReferral.getAdditionalInformation().isEmpty()) {
      longTextId = null;
    } else {
      try {
        longTextId = createLongText(DEFAULT_COUNTY_SPECIFIC_CODE,
            screeningToReferral.getAdditionalInformation());
      } catch (ServiceException e) {
        String message = e.getMessage();
        logError(message, e);
      }
    }
    return longTextId;
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
      String referralId) throws ServiceException {

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
                  crossReport, referralId, DEFAULT_STAFF_PERSON_ID, DEFAULT_COUNTY_SPECIFIC_CODE,
                  lawEnforcementIndicator);

          messageBuilder.addDomainValidationError(validator.validate(cmsCrossReport));

          gov.ca.cwds.rest.api.domain.cms.CrossReport postedCrossReport =
              this.crossReportService.create(cmsCrossReport);

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
      HashMap<Long, String> perpatratorClient, HashMap<Long, String> victimClient
      ) throws ServiceException {

    Set<Allegation> processedAllegations = new HashSet<>();
    Set<Allegation> allegations;
    String victimClientId = "";
    String perpatratorClientId = "";

    allegations = scr.getAllegations();
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
        // this is the legacy Id (CLIENT) of the victime
        victimClientId = victimClient.get(allegation.getVictimPersonId());
      }

      if (allegation.getPerpetratorPersonId() != 0) {
        try {
          if (!ParticipantValidator.isPerpetratorParticipant(scr,
              allegation.getPerpetratorPersonId())) {
            String message =
                " Allegation/Perpetrator Person Id does not contain a Participant with a role of Perpetrator ";
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
        String message = "Victim could not be determined for an allegation ";
        ServiceException exception = new ServiceException(message);
        logError(message, exception);
        // next allegation
        continue;
      }

      if (allegation.getLegacyId() == null || allegation.getLegacyId().isEmpty()) {
        // create an allegation in CMS legacy database
        gov.ca.cwds.rest.api.domain.cms.Allegation cmsAllegation =
            new gov.ca.cwds.rest.api.domain.cms.Allegation("", DEFAULT_CODE, "",
                scr.getLocationType(), "", DEFAULT_CODE, allegationTypeCode,
                scr.getReportNarrative(), "", false, DEFAULT_NON_PROTECTING_PARENT_CODE, false,
                victimClientId, perpatratorClientId, referralId, DEFAULT_COUNTY_SPECIFIC_CODE,
                false, DEFAULT_CODE);

        messageBuilder.addDomainValidationError(validator.validate(cmsAllegation));

        PostedAllegation postedAllegation = this.allegationService.create(cmsAllegation);
        allegation.setLegacyId(postedAllegation.getId());
        allegation.setLegacySourceTable(ALLEGATION_TABLE_NAME);
        processedAllegations.add(allegation);
      } else {
        gov.ca.cwds.rest.api.domain.cms.Allegation foundAllegation =
            this.allegationService.find(allegation.getLegacyId());
        if (foundAllegation == null) {
          String message =
              " Legacy Id on Allegation does not correspond to an existing CMS/CWS Allegation ";
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
      String clientId) throws ServiceException {

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
        Address domainAddress = Address.createWithDefaults(address, DEFAULT_STATE_CODE);
        zipSuffix = domainAddress.getZip4();

        messageBuilder.addDomainValidationError(validator.validate(domainAddress));

        PostedAddress postedAddress = (PostedAddress) this.addressService.create(domainAddress);
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

      if (address.getLegacyId() == null || address.getLegacyId().isEmpty()) {

        ClientAddress clientAddress = new ClientAddress(DEFAULT_ADDRESS_TYPE, "", "", "", addressId,
            clientId, "", referralId);

        messageBuilder.addDomainValidationError(validator.validate(clientAddress));
        this.clientAddressService.create(clientAddress);

        messageBuilder.addDomainValidationError(validator.validate(clientAddress));

        // update the addresses of the participant
        address.setLegacySourceTable(CLIENT_ADDRESS_TABLE_NAME);
        address.setLegacyId(addressId);
        newAddresses.add(address);
      } else {
        // verify that ClientAddress exists - no update for now
        ClientAddress foundClientAddress =
            (ClientAddress) this.clientAddressService.find(address.getLegacyId());
        if (foundClientAddress == null) {
          String message =
              " Legacy Id on Address does not correspond to an existing CMS/CWS Client Address ";
          ServiceException se = new ServiceException(message);
          logError(message, se);
          // next address
          continue;
        }
      }
    }

    clientParticipant.setAddresses(newAddresses);

    return clientParticipant;
  }

  private gov.ca.cwds.rest.api.domain.Address processReferralAddress(ScreeningToReferral scr ) throws ServiceException {
    gov.ca.cwds.rest.api.domain.Address address = scr.getAddress();
    if (address == null || address.getZip() == null || address.getStreetAddress() == null
        || address.getType() == null) {
      String message = " Screening address is null or empty";
      logError(message);
      return address;
    }

    try {
      Address domainAddress = Address.createWithDefaults(address, DEFAULT_STATE_CODE);

      messageBuilder.addDomainValidationError(validator.validate(domainAddress));

      PostedAddress postedAddress = (PostedAddress) this.addressService.create(domainAddress);

      address.setLegacyId(postedAddress.getExistingAddressId());
      address.setLegacySourceTable("ADDRS_T");
    } catch (Exception e) {
      logError(e.getMessage(), e);
    }

    return address;

  }

  private Reporter processReporter(Participant ip, String role, String referralId) throws ServiceException {

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
          reporterAddress, ip, DEFAULT_COUNTY_SPECIFIC_CODE, DEFAULT_STATE_CODE);

      messageBuilder.addDomainValidationError(validator.validate(reporter));
      theReporter = reporterService.create(reporter);
    }
    return theReporter;
  }

  private String createLongText(String countySpecificCode, String textDescription) throws ServiceException {

    LongText longText = new LongText(countySpecificCode, textDescription);
    PostedLongText postedLongText = longTextService.create(longText);

    messageBuilder.addDomainValidationError(validator.validate(longText));

    return postedLongText.getId();

  }

  private ChildClient processChildClient(Participant id, String clientId) throws ServiceException {

    ChildClient childClient = ChildClient.createWithDefaults(clientId);
    messageBuilder.addDomainValidationError(validator.validate(childClient));
    return this.childClientService.create(childClient);
  }
}

