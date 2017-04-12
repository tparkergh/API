package gov.ca.cwds.rest.services;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.commons.lang3.NotImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.inject.Inject;

import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.Allegation;
import gov.ca.cwds.rest.api.domain.CrossReport;
import gov.ca.cwds.rest.api.domain.Participant;
import gov.ca.cwds.rest.api.domain.Screening;
import gov.ca.cwds.rest.api.domain.ScreeningToReferral;
import gov.ca.cwds.rest.api.domain.cms.Address;
import gov.ca.cwds.rest.api.domain.cms.Client;
import gov.ca.cwds.rest.api.domain.cms.ClientAddress;
import gov.ca.cwds.rest.api.domain.cms.PostedAddress;
import gov.ca.cwds.rest.api.domain.cms.PostedAllegation;
import gov.ca.cwds.rest.api.domain.cms.PostedClient;
import gov.ca.cwds.rest.api.domain.cms.PostedCmsReferral;
import gov.ca.cwds.rest.api.domain.cms.PostedReferral;
import gov.ca.cwds.rest.api.domain.cms.PostedReporter;
import gov.ca.cwds.rest.api.domain.cms.Referral;
import gov.ca.cwds.rest.api.domain.cms.ReferralClient;
import gov.ca.cwds.rest.api.domain.cms.Reporter;
import gov.ca.cwds.rest.services.cms.AddressService;
import gov.ca.cwds.rest.services.cms.AllegationService;
import gov.ca.cwds.rest.services.cms.ClientAddressService;
import gov.ca.cwds.rest.services.cms.ClientService;
import gov.ca.cwds.rest.services.cms.CmsReferralService;
import gov.ca.cwds.rest.services.cms.CrossReportService;
import gov.ca.cwds.rest.services.cms.ReferralClientService;
import gov.ca.cwds.rest.services.cms.ReferralService;
import gov.ca.cwds.rest.services.cms.ReporterService;
import io.dropwizard.jackson.Jackson;

/**
 * Business layer object to work on {@link Screening}
 * 
 * @author CWDS API Team
 */
public class ScreeningToReferralService implements CrudsService {

  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

  private static final Logger LOGGER = LoggerFactory.getLogger(CmsReferralService.class);
  private static final String PERPATRATOR_ROLE = "perpatrator";
  private static final String MANDATED_REPORTER_ROLE = "mandated reporter";
  private static final String NON_MANDATED_REPORTER_ROLE = "non-mandated reporter";
  private static final String ANONYMOUS_REPORTER_ROLE = "anonymous reporter";
  private static final String VICTIM_ROLE = "victim";
  private static final String SELF_REPORTED_ROLE = "self reported";


  final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
  final DateFormat timeFormat = new SimpleDateFormat("HH:MM:SS");

  private ReferralService referralService;
  private ClientService clientService;
  private AllegationService allegationService;
  private CrossReportService crossReportService;
  private ReferralClientService referralClientService;
  private ReporterService reporterService;
  private PostedReporter savedReporter;
  private AddressService addressService;
  private ClientAddressService clientAddressService;

  private String genderCode;
  private Boolean lawEnforcementIndicator;
  private Boolean anonymousReporter;
  private Boolean mandatedReporterIndicator;
  private int zipCode;
  private Short zipSuffix;
  private String streetName;
  private String streetNumber;
  private String timeStarted;

  // default values
  private static final short defaultCode = 0;
  private static final BigDecimal defaultBig = new BigDecimal(0);
  private static final int defaultInt = 0;
  private static final String responsibleAgencyCode = "C";
  private static final short defaultStateCode = 99;
  // TODO: County Codes - two character string
  private String defaultCountyCode = "99";

  // TODO: determine values of sys Codes and apply
  private short communicationsMethodCode = 0;
  private short referralResponseTypeCode = 0;
  private short crossReportMethodCode = 0;
  private short allegationTypeCode = 0;
  private short allegationCountyCode = 0;
  private short addressStateCode = 0;


  // IDENTIFIERS created by ADD
  private String crossReportId = "34567890123ABC";
  private String allegationId = "";
  private String reporterId = "5678901234ABC";
  private String victimClientId = "";
  private String perpatratorClientId = "";
  private String addressId;
  private String clientAddressId;

  Hashtable<Long, String> victimClient = new Hashtable();
  Hashtable<Long, String> perpatratorClient = new Hashtable();

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
   */
  @Inject
  public ScreeningToReferralService(ReferralService referralService, ClientService clientService,
      AllegationService allegationService, CrossReportService crossReportService,
      ReferralClientService referralClientService, ReporterService reporterService,
      AddressService addressService, ClientAddressService clientAddressService) {

    super();
    this.referralService = referralService;
    this.clientService = clientService;
    this.allegationService = allegationService;
    this.crossReportService = crossReportService;
    this.referralClientService = referralClientService;
    this.reporterService = reporterService;
    this.addressService = addressService;
    this.clientAddressService = clientAddressService;
  }


  @Override
  public Response create(Request request) {
    String referralId = "";
    String clientId = "";
    String crossReportId = "";
    String allegationId = "";
    String reporterId = "";
    String victimClientId = "";
    String perpatratorClientId = "";
    String addressId;
    String clientAddressId;
    String dateStarted;
    String timeStarted;
    final Date now = new Date();

    dateStarted = dateFormat.format(now);
    timeStarted = timeFormat.format(now);


    MAPPER.configure(SerializationFeature.INDENT_OUTPUT, true);

    assert request instanceof ScreeningToReferral;

    ScreeningToReferral screeningToReferral = (ScreeningToReferral) request;

    // TODO: date/time (2016-08-03T01:00:00.000) parse to separate date (YYYY-MM-DD) and time
    // (HH:MM:SS) fields.
    // for date started and time stared.
    // create a CMS Referral
    Referral referral = new Referral(false, anonymousReporter(screeningToReferral), false, "",
        defaultCode, false, "", communicationsMethodCode, "", "", "", "", false, false, defaultCode,
        "", false, "", "", screeningToReferral.getName(), "", dateStarted, timeStarted,
        referralResponseTypeCode, defaultCode, "", "", "", screeningToReferral.getReportNarrative(),
        "", "", "", "", "", "", "", "", false, false, false, false, "", responsibleAgencyCode,
        defaultCode, "", "", "");
    PostedReferral postedReferral = this.referralService.create(referral);
    referralId = postedReferral.getId();

    /*
     * CWS/CMS Clients and Reporter
     */
    // TODO: what about self-reported - for now - create CLIENT and REFERRAL_CLIENT
    Set<gov.ca.cwds.rest.api.domain.cms.ReferralClient> resultReferralClients =
        new LinkedHashSet<>();
    Set<PostedClient> postedClients = new LinkedHashSet<>();
    Set<Participant> participants = new LinkedHashSet<>();
    participants = screeningToReferral.getParticipants();
    for (Participant incomingParticipant : participants) {

      genderCode = incomingParticipant.getGender();
      victimClient.clear();
      perpatratorClient.clear();

      Set<String> roles = new HashSet<String>(incomingParticipant.getRoles());
      /**
       * process the roles of this participant
       */
      for (String role : roles) {

        if ((role.equalsIgnoreCase(MANDATED_REPORTER_ROLE)
            || role.equalsIgnoreCase(NON_MANDATED_REPORTER_ROLE))
            && !anonymousReporter(screeningToReferral)) {
          /*
           * CMS Reporter - if role is 'mandated reporter' or 'non-mandated reporter' and not
           * anonymous reporter
           */
          if (savedReporter != null) {
            LOGGER.error("ERROR - only one Reporter per Referral allowed", savedReporter);
            throw new ServiceException("ERROR - only one Reporter per Referral allowed");
          }

          mandatedReporterIndicator = false;
          if (role.equalsIgnoreCase(MANDATED_REPORTER_ROLE)) {
            mandatedReporterIndicator = true;
          }

          Reporter reporter = new Reporter("", "", defaultCode, defaultCode, false, "", "", "",
              false, incomingParticipant.getFirstName(), incomingParticipant.getLastName(),
              mandatedReporterIndicator, 0, defaultBig, "", "", defaultBig, 0, defaultCode, "", "",
              "", "", "referralId", "", defaultCode, defaultCountyCode);
          PostedReporter postedreporter = this.reporterService.create(reporter);
          savedReporter = postedreporter;
        } else {
          // not a reporter participant - make a CLIENT and REFERRAL_CLIENT
          if (!anonymousReporter(screeningToReferral)) {
            // not an anonymous reporter participant - create client
            Client client = new Client("", false, "", "", "", defaultCode,
                incomingParticipant.getDateOfBirth(), "", defaultCode, false, false, "", "",
                incomingParticipant.getFirstName(), incomingParticipant.getLastName(), "", "",
                false, "", false, "", false, "", false, "", "", "", defaultCode, "", "", "", "",
                genderCode, "", "", "", defaultCode, defaultCode, "", false, false, "", false,
                defaultCode, "", "", "", defaultCode, false, false, "", false, defaultCode,
                defaultCode, defaultCode, defaultCode, false, "", "", false, "",
                incomingParticipant.getSsn(), "", false, false, "", false);
            PostedClient postedClient = this.clientService.create(client);
            postedClients.add(postedClient);
            clientId = postedClient.getId();

            // CMS Referral Client
            ReferralClient referralClient = new ReferralClient("", defaultCode, defaultCode, "", "",
                role.equalsIgnoreCase(SELF_REPORTED_ROLE), false, referralId, clientId, "",
                defaultCode, "", "", false, false, false);
            gov.ca.cwds.rest.api.domain.cms.ReferralClient postedReferralClient =
                this.referralClientService.create(referralClient);
            resultReferralClients.add(postedReferralClient);

            /*
             * CMS Address - create ADDRESS and CLIENT_ADDRESS for each address of the participant
             */
            Set<gov.ca.cwds.rest.api.domain.Address> addresses =
                new HashSet<gov.ca.cwds.rest.api.domain.Address>(
                    incomingParticipant.getAddresses());
            for (gov.ca.cwds.rest.api.domain.Address address : addresses) {

              // TODO: address parsing - requires standardizing in seperate class
              zipCode = address.getZip();
              zipSuffix = null;
              if (address.getZip().toString().length() > 5) {
                zipSuffix = Short.parseShort(address.getZip().toString().substring(5));
              }
              String[] streetAddress = address.getStreetAddress().split(" ");
              streetNumber = streetAddress[0];
              streetName = streetAddress[1];

              Address domainAddress = new Address("", address.getCity(), defaultBig, defaultInt,
                  false, defaultCode, defaultBig, defaultInt, address.getType(), defaultBig,
                  defaultInt, defaultStateCode, streetName, streetNumber, zipCode, "", zipSuffix,
                  "", "", defaultCode, defaultCode, "");

              try {
                final String domainAddressString = MAPPER.writeValueAsString(domainAddress);
                System.out.println(domainAddressString);
              } catch (JsonProcessingException e) {
                e.printStackTrace();
              }
              PostedAddress postedAddress =
                  (PostedAddress) this.addressService.create(domainAddress);
              addressId = postedAddress.getExistingAddressId();

              /*
               * CMS Client Address
               */
              ClientAddress clientAddress =
                  new ClientAddress(defaultCode, "", "", "", addressId, clientId, "", referralId);
              ClientAddress postedClientAddress =
                  (ClientAddress) this.clientAddressService.create(clientAddress);
              clientAddressId = postedClientAddress.getClientAddressId();
              try {
                final String domainClientAddressString = MAPPER.writeValueAsString(clientAddress);
                System.out.println(domainClientAddressString);
              } catch (JsonProcessingException e) {
                e.printStackTrace();
              }
              /*
               * determine other participant attributes relating to CWS/CMS allegation
               */
              if (role.equalsIgnoreCase(VICTIM_ROLE) || role.equalsIgnoreCase(SELF_REPORTED_ROLE)) {
                victimClient.put(incomingParticipant.getId(), postedClient.getId());
              }
              if (role.equalsIgnoreCase(PERPATRATOR_ROLE)) {
                perpatratorClient.put(incomingParticipant.getId(), postedClient.getId());
              }
            }
          }
        }
      }
    }

    /*
     * CMS Cross Report - one for each cross_report
     */
    Set<gov.ca.cwds.rest.api.domain.cms.CrossReport> resultCrossReports = new LinkedHashSet<>();
    Set<CrossReport> crossReports = new LinkedHashSet<>();
    crossReports = screeningToReferral.getCrossReports();

    for (CrossReport crossReport : crossReports) {

      lawEnforcementIndicator = false;
      if (crossReport.getAgencyType().contains("Law Enforcement")) {
        lawEnforcementIndicator = true;
      }
      gov.ca.cwds.rest.api.domain.cms.CrossReport cmsCrossReport =
          new gov.ca.cwds.rest.api.domain.cms.CrossReport(crossReportId, crossReportMethodCode,
              false, false, "", "", defaultInt, defaultBig, crossReport.getInformDate(), "", "",
              referralId, "", "", crossReport.getAgencyName(), "", "", "", lawEnforcementIndicator,
              false, false);
      gov.ca.cwds.rest.api.domain.cms.CrossReport postedCrossReport =
          this.crossReportService.create(cmsCrossReport);
      resultCrossReports.add(postedCrossReport);
    }

    /*
     * CMS Allegation - one for each allegation
     */
    // TODO: set up ALLEGATION_PERPATRATOR_HISTORY record for Allegation
    // TODO: create CMS CHILD_CLIENT for the victim
    Set<PostedAllegation> postedAllegations = new LinkedHashSet<>();
    Set<Allegation> allegations = new LinkedHashSet<>();
    allegations = screeningToReferral.getAllegations();
    for (Allegation allegation : allegations) {

      if (victimClient.containsKey(allegation.getVictimPersonId())) {
        victimClientId = victimClient.get(allegation.getVictimPersonId());
      }
      if (perpatratorClient.containsKey(allegation.getPerpetratorPersonId())) {
        perpatratorClientId = perpatratorClient.get(allegation.getPerpetratorPersonId());
      }
      if (victimClientId.isEmpty()) {
        throw new ServiceException("ERROR - victim could not be determined for an allegation");
      }

      // create an allegation in CMS legacy database
      gov.ca.cwds.rest.api.domain.cms.Allegation cmsAllegation =
          new gov.ca.cwds.rest.api.domain.cms.Allegation("", defaultCode, "",
              screeningToReferral.getLocationType(), "", defaultCode, allegationTypeCode,
              screeningToReferral.getReportNarrative(), "", false, "", false, victimClientId,
              perpatratorClientId, referralId, defaultCountyCode, false, defaultCode);
      PostedAllegation postedAllegation = this.allegationService.create(cmsAllegation);
      postedAllegations.add(postedAllegation);
      allegationId = postedAllegation.getId();

    }

    // TODO: return a CmsReferral for now - must define what should be returned from 'referrals'
    // service
    return new PostedCmsReferral(postedReferral, postedClients, postedAllegations,
        resultCrossReports, resultReferralClients, savedReporter);
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
  public Response find(Serializable primaryKey) {
    assert primaryKey instanceof String;
    throw new NotImplementedException("Find is not implemented");
  }

  @Override
  public Response update(Serializable primaryKey, Request request) {
    assert primaryKey instanceof String;
    throw new NotImplementedException("Update is not implemented");
  }

  private Boolean anonymousReporter(ScreeningToReferral str) {
    Set<Participant> participants = new HashSet<>();
    participants = str.getParticipants();
    for (Participant incomingParticipant : participants) {
      Set<String> roles = new HashSet<String>(incomingParticipant.getRoles());
      /**
       * process the roles of this participant
       */
      for (String role : roles) {

        if (role.equalsIgnoreCase(ANONYMOUS_REPORTER_ROLE)) {
          return true;
        }
      }
    }
    return false;
  }

}
