package gov.ca.cwds.rest.services;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
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
  private Boolean mandatedReporterIndicator;
  private int zipCode;
  private Short zipSuffix;
  private String streetName;
  private String streetNumber;

  // default values
  private static final short defaultCode = 0;
  private static final BigDecimal defaultBig = new BigDecimal(0);
  private static final int defaultInt = 0;
  private static final String defaultResponsibleAgencyCode = "C";
  private static final short defaultStateCode = 1828; // california
  private static final String defaultLimitedAccessCode = "N";
  private static final short defaultApprovalStatusType = 118;
  private static final String defaultSensitivityIndicator = "N";
  private static final short defaultSecondaryLanguageType = 1253; // english
  private static final String defaultSoc158PlacementCode = "N";
  private static final String defaultSocialSecurityNumChangedCode = "N";
  private static final short defaultNameType = 1313;
  private static final String defaultLiterateCode = "U";
  private static final String defaultEstimateDobCode = "N";
  private static final Boolean defaultChildClientIndicator = false;
  private static final String defaultAdoptionStatusCode = "N";
  private static final String defaultNonProtectingParentCode = "U";
  private static final short defaultAddressType = 32; // residence
  private static String defaultCountySpecificCode = "99";

  // TODO: #142337489 Develop List of Value service to support Pi2 Save Referral to CWS/CMS
  private short communicationsMethodCode = 409; // default to telephone until #142337489 complete
  private short referralResponseTypeCode = 0;
  private short crossReportMethodCode = 0;
  private short allegationTypeCode = 0;

  HashMap<Long, String> victimClient = new HashMap<Long, String>();
  HashMap<Long, String> perpatratorClient = new HashMap<Long, String>();

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
    String referralId = "";
    String clientId = "";
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
        defaultApprovalStatusType, false, "", communicationsMethodCode, "", "", "", "", false,
        false, defaultCode, "", false, defaultLimitedAccessCode, "", screeningToReferral.getName(),
        "", dateStarted, timeStarted, referralResponseTypeCode, defaultCode, "", "", "",
        screeningToReferral.getReportNarrative(), "", "", "", "", "", "", "",
        defaultCountySpecificCode, false, false, false, false, "", defaultResponsibleAgencyCode,
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

      genderCode = "";
      if (!incomingParticipant.getGender().isEmpty()) {
        genderCode = incomingParticipant.getGender().toUpperCase().substring(0, 1);
      }
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
          try {
            PostedReporter savedReporter = processReporter(incomingParticipant, role, referralId);
          } catch (Exception e) {
            LOGGER.error("ERROR - creating Reporter" + e.getMessage());
            throw new ServiceException(e);
          }

        } else {

          // not a reporter participant - make a CLIENT and REFERRAL_CLIENT
          if (!anonymousReporter(screeningToReferral)) {

            // not an anonymous reporter participant - create client
            Client client = new Client("", false, defaultAdoptionStatusCode, "", "", defaultCode,
                incomingParticipant.getDateOfBirth(), "", defaultCode, false,
                defaultChildClientIndicator, "", "", incomingParticipant.getFirstName(),
                incomingParticipant.getLastName(), "", "", false, dateStarted, false, "", false, "",
                false, "", "", "", defaultCode, "", defaultEstimateDobCode, "", "", genderCode, "",
                "", "", defaultCode, defaultCode, "", false, false, defaultLiterateCode, false,
                defaultCode, "", "", "", defaultNameType, false, false, "", false, defaultCode,
                defaultCode, defaultCode, defaultSecondaryLanguageType, false,
                defaultSensitivityIndicator, defaultSoc158PlacementCode, false,
                defaultSocialSecurityNumChangedCode, incomingParticipant.getSsn(), "", false, false,
                "", false);

            PostedClient postedClient = this.clientService.create(client);
            postedClients.add(postedClient);
            clientId = postedClient.getId();

            // CMS Referral Client
            ReferralClient referralClient = new ReferralClient("", defaultApprovalStatusType,
                defaultCode, "", "", role.equalsIgnoreCase(SELF_REPORTED_ROLE), false, referralId,
                clientId, "", defaultCode, "", defaultCountySpecificCode, false, false, false);
            gov.ca.cwds.rest.api.domain.cms.ReferralClient postedReferralClient =
                this.referralClientService.create(referralClient);
            resultReferralClients.add(postedReferralClient);

            /*
             * determine other participant attributes relating to CWS/CMS allegation
             */
            if (role.equalsIgnoreCase(VICTIM_ROLE) || role.equalsIgnoreCase(SELF_REPORTED_ROLE)) {
              victimClient.put(incomingParticipant.getId(), postedClient.getId());
            }
            if (role.equalsIgnoreCase(PERPATRATOR_ROLE)) {
              perpatratorClient.put(incomingParticipant.getId(), postedClient.getId());
            }
            try {
              processAddress(incomingParticipant, referralId, clientId);
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

  /*
   * CMS Cross Report
   */
  private Set<gov.ca.cwds.rest.api.domain.cms.CrossReport> processCrossReports(
      ScreeningToReferral scr, String referralId) throws Exception {

    String crossReportId = "";
    Set<gov.ca.cwds.rest.api.domain.cms.CrossReport> resultCrossReports = new LinkedHashSet<>();
    Set<CrossReport> crossReports = new LinkedHashSet<>();
    crossReports = scr.getCrossReports();

    for (CrossReport crossReport : crossReports) {

      lawEnforcementIndicator = false;
      if (crossReport.getAgencyType().contains("Law Enforcement")) {
        lawEnforcementIndicator = true;
      }
      // create the cross report

      gov.ca.cwds.rest.api.domain.cms.CrossReport cmsCrossReport =
          new gov.ca.cwds.rest.api.domain.cms.CrossReport(crossReportId, crossReportMethodCode,
              false, false, "", "", defaultInt, defaultBig, crossReport.getInformDate(), "", "",
              referralId, "", "", crossReport.getAgencyName(), "", "", defaultCountySpecificCode,
              lawEnforcementIndicator, false, false);
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


    // TODO: #143616481 create CMS CHILD_CLIENT for the victim
    Set<PostedAllegation> postedAllegations = new LinkedHashSet<>();
    Set<Allegation> allegations = new LinkedHashSet<>();
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
          new gov.ca.cwds.rest.api.domain.cms.Allegation("", defaultCode, "", scr.getLocationType(),
              "", defaultCode, allegationTypeCode, scr.getReportNarrative(), "", false,
              defaultNonProtectingParentCode, false, victimClientId, perpatratorClientId,
              referralId, defaultCountySpecificCode, false, defaultCode);
      PostedAllegation postedAllegation = this.allegationService.create(cmsAllegation);
      postedAllegations.add(postedAllegation);

    }
    return postedAllegations;
  }

  /*
   * CMS Address - create ADDRESS and CLIENT_ADDRESS for each address of the participant
   */
  private void processAddress(Participant incomingParticipant, String referralId, String clientId)
      throws Exception {

    Set<gov.ca.cwds.rest.api.domain.Address> addresses =
        new HashSet<gov.ca.cwds.rest.api.domain.Address>(incomingParticipant.getAddresses());

    for (gov.ca.cwds.rest.api.domain.Address address : addresses) {

      String addressId = "";

      // TODO: address parsing - requires standardizing in seperate class
      zipCode = address.getZip();
      zipSuffix = null;
      if (address.getZip().toString().length() > 5) {
        zipSuffix = Short.parseShort(address.getZip().toString().substring(5));
      }
      String[] streetAddress = address.getStreetAddress().split(" ");
      streetNumber = streetAddress[0];
      streetName = streetAddress[1];

      Address domainAddress =
          new Address("", address.getCity(), defaultBig, defaultInt, false, defaultCode, defaultBig,
              defaultInt, address.getType(), defaultBig, defaultInt, defaultStateCode, streetName,
              streetNumber, zipCode, "", zipSuffix, "", "", defaultCode, defaultCode, "");

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
          new ClientAddress(defaultAddressType, "", "", "", addressId, clientId, "", referralId);
      ClientAddress postedClientAddress =
          (ClientAddress) this.clientAddressService.create(clientAddress);
    }
  }



  private PostedReporter processReporter(Participant ip, String role, String referralId)
      throws Exception {

    mandatedReporterIndicator = false;
    if (role.equalsIgnoreCase(MANDATED_REPORTER_ROLE)) {
      mandatedReporterIndicator = true;
    }

    // TODO: map the address fields on REPORTER
    Reporter reporter = new Reporter("", "", defaultCode, defaultCode, false, "", "", "", false,
        ip.getFirstName(), ip.getLastName(), mandatedReporterIndicator, 0, defaultBig, "", "",
        defaultBig, 0, defaultStateCode, "", "", "", "", referralId, "", defaultCode,
        defaultCountySpecificCode);

    PostedReporter postedreporter = this.reporterService.create(reporter);
    return postedreporter;

  }
}

