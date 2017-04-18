package gov.ca.cwds.rest.services;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Set;

import org.apache.commons.lang3.NotImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

  private static final Logger LOGGER = LoggerFactory.getLogger(ScreeningToReferral.class);
  private static final String PERPETRATOR_ROLE = "perpetrator";
  private static final String MANDATED_REPORTER_ROLE = "mandated reporter";
  private static final String NON_MANDATED_REPORTER_ROLE = "non-mandated reporter";
  private static final String ANONYMOUS_REPORTER_ROLE = "anonymous reporter";
  private static final String VICTIM_ROLE = "victim";
  private static final String SELF_REPORTED_ROLE = "self reported";

  final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
  final DateFormat timeFormat = new SimpleDateFormat("HH:MM:SS");
  final DateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);

  private ReferralService referralService;
  private ClientService clientService;
  private AllegationService allegationService;
  private CrossReportService crossReportService;
  private ReferralClientService referralClientService;
  private ReporterService reporterService;
  private PostedReporter savedReporter;
  private AddressService addressService;
  private ClientAddressService clientAddressService;

  private Short zipSuffix;

  // default values
  private static final short DEFAULT_CODE = 0;
  private static final BigDecimal DEFAULT_DECIMAL = new BigDecimal(0);
  private static final int DEFAULT_INT = 0;
  private static final String DEFAULT_RESPONSIBLE_AGENCY_CODE = "C";
  private static final short DEFAULT_STATE_CODE = 1828; // california
  private static final String DEFAULT_LIMITIED_ACCESS_CODE = "N";
  private static final short DEFAULT_APPROVAL_STATUS_CODE = 118;
  private static final String DEFAULT_SENSITIVITY_INDICATOR = "N";
  private static final short DEFAULT_SECONDARY_LANGUAGE_TYPE = 1253; // english
  private static final String DEFAULT_SOC158_PLACEMENT_CODE = "N";
  private static final String DEFAULT_SOCIAL_SECURITY_NUM_CHANGE_CODE = "N";
  private static final short DEFAULT_NAME_TYPE = 1313;
  private static final String DEFAULT_LITERATE_CODE = "U";
  private static final String DEFAULT_ESTIMATED_DOB_CODE = "N";
  private static final Boolean DEFAULT_CHILD_CLIENT_INDICATOR = false;
  private static final String DEFAULT_ADOPTION_STATUS_CODE = "N";
  private static final String DEFAULT_NON_PROTECTING_PARENT_CODE = "U";
  private static final short DEFAULT_ADDRESS_TYPE = 32; // residence
  private static String DEFAULT_COUNTY_SPECIFIC_CODE = "99";
  private static final short DEFAULT_GOVERNMENT_ENTITY_TYPE = 1126;

  // TODO: #142337489 Develop List of Value service to support Pi2 Save Referral to CWS/CMS
  private short communicationsMethodCode = 409; // default to telephone until #142337489 complete
  private short referralResponseTypeCode = 0;
  private short crossReportMethodCode = 0;
  private short allegationTypeCode = 0;

  HashMap<Long, String> victimClient = new HashMap<>();
  HashMap<Long, String> perpatratorClient = new HashMap<>();

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
    String referralId;
    String clientId;
    String dateStarted = null;
    String timeStarted = null;
    String referralAddressId = null;
    MAPPER.configure(SerializationFeature.INDENT_OUTPUT, true);

    assert request instanceof ScreeningToReferral;

    ScreeningToReferral screeningToReferral = (ScreeningToReferral) request;

    try {
      Date dateTime = dateTimeFormat.parse(screeningToReferral.getStartedAt());
      dateStarted = dateFormat.format(dateTime);
      timeStarted = timeFormat.format(dateTime);
    } catch (ParseException e1) {
      LOGGER.error("ERROR - parsing Start Date/Time", e1.getMessage());
      throw new ServiceException("ERROR - parsing Start Date/Time " + e1.getMessage());
    }

    try {
      referralAddressId = processReferralAddress(screeningToReferral);
    } catch (Exception e1) {
      LOGGER.error("ERROR - processing Address associated with the Referral ", e1.getMessage());
      throw new ServiceException(
          "ERROR - processing Address associated with the Referral " + e1.getMessage());
    }

    // create a CMS Referral
    Referral referral = new Referral(false, anonymousReporter(screeningToReferral), false, "",
        DEFAULT_APPROVAL_STATUS_CODE, false, "", communicationsMethodCode, "", "", "", "", false,
        false, DEFAULT_CODE, "", false, DEFAULT_LIMITIED_ACCESS_CODE, "",
        screeningToReferral.getName(), "", dateStarted, timeStarted, referralResponseTypeCode,
        DEFAULT_CODE, "", "", "", screeningToReferral.getReportNarrative(), "", "", "", "",
        referralAddressId, "", "", DEFAULT_COUNTY_SPECIFIC_CODE, false, false, false, false, "",
        DEFAULT_RESPONSIBLE_AGENCY_CODE, DEFAULT_CODE, "", "", "");
    PostedReferral postedReferral = this.referralService.create(referral);
    referralId = postedReferral.getId();
    /*
     * CWS/CMS Clients and Reporter
     */
    // TODO: what about self-reported - for now - create CLIENT and REFERRAL_CLIENT
    Set<gov.ca.cwds.rest.api.domain.cms.ReferralClient> resultReferralClients =
        new LinkedHashSet<>();
    Set<PostedClient> postedClients = new LinkedHashSet<>();
    Set<Participant> participants;
    victimClient.clear();
    perpatratorClient.clear();

    participants = screeningToReferral.getParticipants();
    for (Participant incomingParticipant : participants) {

      String genderCode = "";
      if (!incomingParticipant.getGender().isEmpty()) {
        genderCode = incomingParticipant.getGender().toUpperCase().substring(0, 1);
      }
      Set<String> roles = new HashSet<>(incomingParticipant.getRoles());
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
          try {
            savedReporter = processReporter(incomingParticipant, role, referralId);
          } catch (Exception e) {
            LOGGER.error("ERROR - creating Reporter" + e.getMessage());
            throw new ServiceException(e);
          }

        } else {

          // not a reporter participant - make a CLIENT and REFERRAL_CLIENT
          if (!anonymousReporter(screeningToReferral)) {

            // not an anonymous reporter participant - create client
            Client client = new Client("", false, DEFAULT_ADOPTION_STATUS_CODE, "", "",
                DEFAULT_CODE, incomingParticipant.getDateOfBirth(), "", DEFAULT_CODE, false,
                DEFAULT_CHILD_CLIENT_INDICATOR, "", "", incomingParticipant.getFirstName(),
                incomingParticipant.getLastName(), "", "", false, dateStarted, false, "", false, "",
                false, "", "", "", DEFAULT_CODE, "", DEFAULT_ESTIMATED_DOB_CODE, "", "", genderCode,
                "", "", "", DEFAULT_CODE, DEFAULT_CODE, "", false, false, DEFAULT_LITERATE_CODE,
                false, DEFAULT_CODE, "", "", "", DEFAULT_NAME_TYPE, false, false, "", false,
                DEFAULT_CODE, DEFAULT_CODE, DEFAULT_CODE, DEFAULT_SECONDARY_LANGUAGE_TYPE, false,
                DEFAULT_SENSITIVITY_INDICATOR, DEFAULT_SOC158_PLACEMENT_CODE, false,
                DEFAULT_SOCIAL_SECURITY_NUM_CHANGE_CODE, incomingParticipant.getSsn(), "", false,
                false, "", false);

            PostedClient postedClient = this.clientService.create(client);
            postedClients.add(postedClient);
            clientId = postedClient.getId();

            // CMS Referral Client
            ReferralClient referralClient = new ReferralClient("", DEFAULT_APPROVAL_STATUS_CODE,
                DEFAULT_CODE, "", "", role.equalsIgnoreCase(SELF_REPORTED_ROLE), false, referralId,
                clientId, "", DEFAULT_CODE, "", DEFAULT_COUNTY_SPECIFIC_CODE, false, false, false);
            gov.ca.cwds.rest.api.domain.cms.ReferralClient postedReferralClient =
                this.referralClientService.create(referralClient);
            resultReferralClients.add(postedReferralClient);

            /*
             * determine other participant attributes relating to CWS/CMS allegation
             */
            if (role.equalsIgnoreCase(VICTIM_ROLE) || role.equalsIgnoreCase(SELF_REPORTED_ROLE)) {
              victimClient.put(incomingParticipant.getId(), postedClient.getId());
            }
            if (role.equalsIgnoreCase(PERPETRATOR_ROLE)) {
              perpatratorClient.put(incomingParticipant.getId(), postedClient.getId());
            }
            try {
              // addresses associated with a client
              processClientAddress(incomingParticipant, referralId, clientId);
            } catch (Exception e) {
              LOGGER.error("ERROR - creating Addresses" + e.getMessage());
              throw new ServiceException(e);
            }
          }
        }
      }
    }

    Set<gov.ca.cwds.rest.api.domain.cms.CrossReport> resultCrossReports;
    try {
      resultCrossReports = processCrossReports(screeningToReferral, referralId);
    } catch (Exception e) {
      LOGGER.error("ERROR - creating CrossReport" + e.getMessage());
      throw new ServiceException(e);
    }


    Set<PostedAllegation> postedAllegations;
    try {
      postedAllegations = processAllegations(screeningToReferral, referralId);
    } catch (Exception e) {
      LOGGER.error("ERROR - creating Allegations" + e.getMessage());
      throw new ServiceException(e);
    }

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
    Set<Participant> participants;
    participants = str.getParticipants();
    for (Participant incomingParticipant : participants) {
      Set<String> roles = new HashSet<>(incomingParticipant.getRoles());
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

  /*
   * CMS Cross Report
   */
  private Set<gov.ca.cwds.rest.api.domain.cms.CrossReport> processCrossReports(
      ScreeningToReferral scr, String referralId) throws Exception {

    String crossReportId = "";
    Set<gov.ca.cwds.rest.api.domain.cms.CrossReport> resultCrossReports = new LinkedHashSet<>();
    Set<CrossReport> crossReports;
    crossReports = scr.getCrossReports();

    for (CrossReport crossReport : crossReports) {

      Boolean lawEnforcementIndicator = false;
      if (crossReport.getAgencyType().contains("Law Enforcement")) {
        lawEnforcementIndicator = true;
      }
      // create the cross report

      gov.ca.cwds.rest.api.domain.cms.CrossReport cmsCrossReport =
          new gov.ca.cwds.rest.api.domain.cms.CrossReport(crossReportId, crossReportMethodCode,
              false, false, "", "", DEFAULT_INT, DEFAULT_DECIMAL, crossReport.getInformDate(), "",
              "", referralId, "", "", crossReport.getAgencyName(), "", "",
              DEFAULT_COUNTY_SPECIFIC_CODE, lawEnforcementIndicator, false, false);
      gov.ca.cwds.rest.api.domain.cms.CrossReport postedCrossReport =
          this.crossReportService.create(cmsCrossReport);
      resultCrossReports.add(postedCrossReport);
    }
    return resultCrossReports;

  }

  /*
   * CMS Allegation - one for each allegation
   */
  private Set<PostedAllegation> processAllegations(ScreeningToReferral scr, String referralId)
      throws Exception {

    // TODO: #143899869 Add CHILD_CLIENT processing to 'referrals' service
    Set<PostedAllegation> postedAllegations = new LinkedHashSet<>();
    Set<Allegation> allegations;
    String victimClientId = "";
    String perpatratorClientId = "";

    allegations = scr.getAllegations();
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
          new gov.ca.cwds.rest.api.domain.cms.Allegation("", DEFAULT_CODE, "",
              scr.getLocationType(), "", DEFAULT_CODE, allegationTypeCode, scr.getReportNarrative(),
              "", false, DEFAULT_NON_PROTECTING_PARENT_CODE, false, victimClientId,
              perpatratorClientId, referralId, DEFAULT_COUNTY_SPECIFIC_CODE, false, DEFAULT_CODE);
      PostedAllegation postedAllegation = this.allegationService.create(cmsAllegation);
      postedAllegations.add(postedAllegation);

    }
    return postedAllegations;
  }

  /*
   * CMS Address - create ADDRESS and CLIENT_ADDRESS for each address of the participant
   */
  private void processClientAddress(Participant incomingParticipant, String referralId,
      String clientId) throws Exception {

    Set<gov.ca.cwds.rest.api.domain.Address> addresses =
        new HashSet<>(incomingParticipant.getAddresses());

    for (gov.ca.cwds.rest.api.domain.Address address : addresses) {

      String addressId;

      // TODO: address parsing - requires standardizing in seperate class
      int zipCode = address.getZip();
      zipSuffix = null;
      if (address.getZip().toString().length() > 5) {
        zipSuffix = Short.parseShort(address.getZip().toString().substring(5));
      }
      String[] streetAddress = address.getStreetAddress().split(" ");
      String streetNumber = streetAddress[0];
      String streetName = streetAddress[1];

      Address domainAddress = new Address("", address.getCity(), DEFAULT_DECIMAL, DEFAULT_INT,
          false, DEFAULT_CODE, DEFAULT_DECIMAL, DEFAULT_INT, "", DEFAULT_DECIMAL, DEFAULT_INT,
          DEFAULT_STATE_CODE, streetName, streetNumber, zipCode, address.getType(), zipSuffix, "",
          "", DEFAULT_CODE, DEFAULT_CODE, "");

      PostedAddress postedAddress = (PostedAddress) this.addressService.create(domainAddress);
      addressId = postedAddress.getExistingAddressId();

      /*
       * CMS Client Address
       */
      if (addressId.isEmpty()) {
        LOGGER.error("ERROR - ADDRESS/IDENTIFIER is required for CLIENT_ADDRESS ", postedAddress);
        throw new ServiceException("ERROR - ADDRESS/IDENTIFIER is required for CLIENT_ADDRESS");
      }
      if (clientId.isEmpty()) {
        LOGGER.error("ERROR - CLIENT/IDENTIFIER is required for CLIENT_ADDRESS ", postedAddress);
        throw new ServiceException("ERROR - CLIENT/IDENTIFIER is required for CLIENT_ADDRESS");
      }

      ClientAddress clientAddress =
          new ClientAddress(DEFAULT_ADDRESS_TYPE, "", "", "", addressId, clientId, "", referralId);
      this.clientAddressService.create(clientAddress);
    }
  }

  private String processReferralAddress(ScreeningToReferral scr) throws Exception {
    gov.ca.cwds.rest.api.domain.Address address = scr.getAddress();

    Integer zipCode = address.getZip();
    zipSuffix = null;
    if (address.getZip().toString().length() > 5) {
      zipSuffix = Short.parseShort(address.getZip().toString().substring(5));
    }
    String[] streetAddress = address.getStreetAddress().split(" ");
    String streetNumber = streetAddress[0];
    String streetName = streetAddress[1];

    Address domainAddress = new Address("", address.getCity(), DEFAULT_DECIMAL, DEFAULT_INT, false,
        DEFAULT_CODE, DEFAULT_DECIMAL, DEFAULT_INT, "", DEFAULT_DECIMAL, DEFAULT_INT,
        DEFAULT_STATE_CODE, streetName, streetNumber, zipCode, address.getType(), zipSuffix, "", "",
        DEFAULT_CODE, DEFAULT_CODE, "");

    PostedAddress postedAddress = (PostedAddress) this.addressService.create(domainAddress);

    return postedAddress.getExistingAddressId();

  }

  private PostedReporter processReporter(Participant ip, String role, String referralId)
      throws Exception {

    Boolean mandatedReporterIndicator = false;
    if (role.equalsIgnoreCase(MANDATED_REPORTER_ROLE)) {
      mandatedReporterIndicator = true;
    }

    // TODO: map the address fields on REPORTER
    Reporter reporter = new Reporter("", "", DEFAULT_CODE, DEFAULT_CODE, false, "", "", "", false,
        ip.getFirstName(), ip.getLastName(), mandatedReporterIndicator, 0, DEFAULT_DECIMAL, "", "",
        DEFAULT_DECIMAL, 0, DEFAULT_STATE_CODE, "", "", "", "", referralId, "", DEFAULT_CODE,
        DEFAULT_COUNTY_SPECIFIC_CODE);

    return this.reporterService.create(reporter);

  }
}

