package gov.ca.cwds.rest.services;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import javax.validation.Validator;

import org.apache.commons.lang3.NotImplementedException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.Dao;
import gov.ca.cwds.data.cms.ReferralDao;
import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.Allegation;
import gov.ca.cwds.rest.api.domain.CrossReport;
import gov.ca.cwds.rest.api.domain.PostedScreeningToReferral;
import gov.ca.cwds.rest.api.domain.Screening;
import gov.ca.cwds.rest.api.domain.ScreeningToReferral;
import gov.ca.cwds.rest.api.domain.cms.PostedAllegation;
import gov.ca.cwds.rest.api.domain.cms.Reporter;
import gov.ca.cwds.rest.api.domain.error.ErrorMessage;
import gov.ca.cwds.rest.business.rules.Reminders;
import gov.ca.cwds.rest.exception.BusinessValidationException;
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
import io.dropwizard.hibernate.UnitOfWork;

/**
 * Business layer object to work on {@link Screening}
 * 
 * @author CWDS API Team
 */
public class ScreeningToReferralService implements CrudsService {

  private static final Logger LOGGER = LoggerFactory.getLogger(ScreeningToReferralService.class);

  private static final String ALLEGATION_TABLE_NAME = "ALLGTN_T";
  private static final String CROSS_REPORT_TABLE_NAME = "CRSS_RPT";
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
  private ParticipantService participantService;
  private Reminders reminders;

  private ReferralDao referralDao;

  private Short zipSuffix;

  LegacyDefaultValues legacyDefaultValues = new LegacyDefaultValues();

  LegacyCodes legacyCodes = new LegacyCodes();

  /**
   * Constructor
   * 
   * @param referralService the referralService
   * @param clientService the clientService
   * @param allegationService the allegationService
   * @param crossReportService the crossReportService
   * @param referralClientService the referralClientService
   * @param reporterService the reporterService
   * @param addressService - cms address service
   * @param clientAddressService - cms ClientAddress service
   * @param childClientService - cms ChildClient service
   * @param assignmentService CMS assignment service
   * @param participantService CMS participantService service
   * @param validator - the validator
   * @param referralDao - The {@link Dao} handling {@link gov.ca.cwds.data.persistence.cms.Referral}
   *        objects. {@link gov.ca.cwds.rest.services.cms.StaffPersonIdRetriever} objects.
   * @param messageBuilder log message
   * @param allegationPerpetratorHistoryService the allegationPerpetratorHistoryService
   * @param reminders - reminders
   */
  @Inject
  public ScreeningToReferralService(ReferralService referralService, ClientService clientService,
      AllegationService allegationService, CrossReportService crossReportService,
      ReferralClientService referralClientService, ReporterService reporterService,
      AddressService addressService, ClientAddressService clientAddressService,
      ChildClientService childClientService, AssignmentService assignmentService,
      ParticipantService participantService, Validator validator, ReferralDao referralDao,
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
    this.participantService = participantService;
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

    ClientParticipants clientParticipants = processParticipants(screeningToReferral, dateStarted,
        referralId, timestamp, messageBuilder);

    Set<CrossReport> resultCrossReports =
        createCrossReports(screeningToReferral, referralId, timestamp);

    Set<Allegation> resultAllegations = createAllegations(screeningToReferral, referralId,
        clientParticipants.getVictimIds(), clientParticipants.getPerpetratorIds(), timestamp);

    PostedScreeningToReferral pstr =
        PostedScreeningToReferral.createWithDefaults(referralId, screeningToReferral,
            clientParticipants.getParticipants(), resultCrossReports, resultAllegations);

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
        throw new BusinessValidationException(messageBuilder.getIssues());
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

  private ClientParticipants processParticipants(ScreeningToReferral screeningToReferral,
      String dateStarted, String referralId, Date timestamp, MessageBuilder messageBuilder) {

    return participantService.saveParticipants(screeningToReferral, dateStarted, referralId,
        timestamp, messageBuilder);
  }

  private String createCmsReferral(ScreeningToReferral screeningToReferral, String dateStarted,
      String timeStarted, Date timestamp) {
    return referralService.createCmsReferralFromScreening(screeningToReferral, dateStarted,
        timeStarted, timestamp, messageBuilder);
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

        Boolean lawEnforcementIndicator = Boolean.FALSE;
        Boolean outStateLawEnforcementIndicator = Boolean.FALSE;
        String outStateLawEnforcementAddr = "";

        /**
         * <blockquote>
         * 
         * <pre>
         * BUSINESS RULE: "R - 05930" - Out of State text Mandatory
         * 
         * IF    CrossReport out  StateLaw Enforcement Indicator is 'true'
         * THEN  CrossReport outStateLawEnforcementAddr can't be blank
         * </blockquote>
         * </pre>
         */
        if (outStateLawEnforcementIndicator && StringUtils.isBlank(outStateLawEnforcementAddr)) {
          String message =
              "outStateLawEnforcementIndicator is set true, Then outStateLawEnforcementAddr can't be blank";
          ServiceException se = new ServiceException(message);
          logError(message, se);
        }

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
          lawEnforcementIndicator = Boolean.TRUE;
        }

        if (crossReport.getLegacyId() == null || crossReport.getLegacyId().isEmpty()) {
          // String outStateLawEnforcementAddr = null;
          // create the cross report
          gov.ca.cwds.rest.api.domain.cms.CrossReport cmsCrossReport =
              gov.ca.cwds.rest.api.domain.cms.CrossReport.createWithDefaults(crossReportId,
                  crossReport, referralId, LegacyDefaultValues.DEFAULT_STAFF_PERSON_ID,
                  outStateLawEnforcementAddr, scr.getIncidentCounty(), lawEnforcementIndicator,
                  outStateLawEnforcementIndicator);

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
    final Short allegationDispositionType = LegacyDefaultValues.DEFAULT_CODE;

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

      boolean allegationHasPerpPersonId = allegation.getPerpetratorPersonId() != 0;
      boolean isNotPerpetrator = !ParticipantValidator.isPerpetratorParticipant(scr,
            allegation.getPerpetratorPersonId());
      if (allegationHasPerpPersonId && isNotPerpetrator) {
          String message =
              "Allegation/Perpetrator Person Id does not contain a Participant with a role of Perpetrator";
          ServiceException exception = new ServiceException(message);
          logError(message, exception);
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
            new gov.ca.cwds.rest.api.domain.cms.Allegation("", LegacyDefaultValues.DEFAULT_CODE, "",
                scr.getLocationType(), "", allegationDispositionType, allegation.getType(), "", "",
                Boolean.FALSE, (perpatratorClientId.equals("")) ? "U" : "N", Boolean.FALSE,
                victimClientId, perpatratorClientId, referralId, scr.getIncidentCounty(),
                Boolean.FALSE, LegacyDefaultValues.DEFAULT_CODE);

        messageBuilder.addDomainValidationError(validator.validate(cmsAllegation));

        PostedAllegation postedAllegation =
            this.allegationService.createWithSingleTimestamp(cmsAllegation, timestamp);
        allegation.setLegacyId(postedAllegation.getId());
        allegation.setLegacySourceTable(ALLEGATION_TABLE_NAME);
        processedAllegations.add(allegation);

        // create the Allegation Perpetrator History
        gov.ca.cwds.rest.api.domain.cms.AllegationPerpetratorHistory cmsPerpHistory =
            new gov.ca.cwds.rest.api.domain.cms.AllegationPerpetratorHistory(
                scr.getIncidentCounty(), postedAllegation.getVictimClientId(),
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
}
