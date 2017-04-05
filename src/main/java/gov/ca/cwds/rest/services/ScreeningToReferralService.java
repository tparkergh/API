package gov.ca.cwds.rest.services;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.commons.lang3.NotImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.Allegation;
import gov.ca.cwds.rest.api.domain.CrossReport;
import gov.ca.cwds.rest.api.domain.Participant;
import gov.ca.cwds.rest.api.domain.Screening;
import gov.ca.cwds.rest.api.domain.ScreeningToReferral;
import gov.ca.cwds.rest.api.domain.cms.Client;
import gov.ca.cwds.rest.api.domain.cms.PostedAllegation;
import gov.ca.cwds.rest.api.domain.cms.PostedClient;
import gov.ca.cwds.rest.api.domain.cms.PostedCmsReferral;
import gov.ca.cwds.rest.api.domain.cms.PostedReferral;
import gov.ca.cwds.rest.api.domain.cms.PostedReporter;
import gov.ca.cwds.rest.api.domain.cms.Referral;
import gov.ca.cwds.rest.api.domain.cms.ReferralClient;
import gov.ca.cwds.rest.api.domain.cms.Reporter;
import gov.ca.cwds.rest.services.cms.AllegationService;
import gov.ca.cwds.rest.services.cms.ClientService;
import gov.ca.cwds.rest.services.cms.CmsReferralService;
import gov.ca.cwds.rest.services.cms.CrossReportService;
import gov.ca.cwds.rest.services.cms.ReferralClientService;
import gov.ca.cwds.rest.services.cms.ReferralService;
import gov.ca.cwds.rest.services.cms.ReporterService;

/**
 * Business layer object to work on {@link Screening}
 * 
 * @author CWDS API Team
 */
public class ScreeningToReferralService implements CrudsService {

  @SuppressWarnings("unused")
  private static final Logger LOGGER = LoggerFactory.getLogger(CmsReferralService.class);

  private ReferralService referralService;
  private ClientService clientService;
  private AllegationService allegationService;
  private CrossReportService crossReportService;
  private ReferralClientService referralClientService;
  private ReporterService reporterService;
  private PostedReporter savedreporter;


  private String genderCode;
  private Boolean lawEnforcementIndicator;
  private Boolean reporterParticipant;
  private Boolean selfReported;
  private Boolean anonymousReporter;
  private String zipCode;
  private Short zipSuffix;
  private String streetName;
  private String streetNumber;

  private final short defaultCode = 0;
  private final BigDecimal defaultBig = new BigDecimal(0);
  private final int defaultInt = 0;
  private final String responsibleAgencyCode = "C";

  // TODO: determine values of sys Codes and apply
  private short communicationsMethodCode = 0;
  private short countySpecificCode = 0;
  private short referralResponseTypeCode = 0;
  private short crossReportMethodCode = 0;
  private short allegationTypeCode = 0;
  private short allegationCountyCode = 0;
  private short addressStateCode = 0;


  // TODO: County Codes - two character string
  private String countyCode = "99";

  // TODO: create the ID values in CRUDS functions
  private String referralId = "";
  private String clientId = "";
  private String crossReportId = "34567890123ABC";
  private String allegationId = "4567890123ABC";
  private String reporterId = "5678901234ABC";
  private String victimClientId = "";
  private String perpatratorClientId = "";

  Hashtable<Long, String> victimClient = new Hashtable();
  Hashtable<Long, String> perpatratorClient = new Hashtable();

  /**
   * Constructor
   * 
   * @param referralService the referralService
   * @param allegationService the allegationService
   * @param crossReportService the crossReportService
   * @param referralClientService the referralClientService
   * @param reporterService the reporterService
   * @param clientService the clientServiec
   */
  @Inject
  public ScreeningToReferralService(ReferralService referralService, ClientService clientService,
      AllegationService allegationService, CrossReportService crossReportService,
      ReferralClientService referralClientService, ReporterService reporterService) {
    super();
    this.referralService = referralService;
    this.clientService = clientService;
    this.allegationService = allegationService;
    this.crossReportService = crossReportService;
    this.referralClientService = referralClientService;
    this.reporterService = reporterService;
  }


  @Override
  public Response create(Request request) {
    assert request instanceof ScreeningToReferral;

    PostedReporter savedreporter = new PostedReporter();

    /*
     * CMS Referral
     */
    ScreeningToReferral screeningToReferral = (ScreeningToReferral) request;

    // set the
    String[] receivedDateTime = screeningToReferral.getStartedAt().split("T");

    // TODO: anonymous reporter should be part of the screeningToReferral object since there will be
    // no reporter type participant
    // associated with the referral
    Referral referral = new Referral(false, anonymousReporter(screeningToReferral), false, "",
        defaultCode, false, "", communicationsMethodCode, "", "", "", "", false, false, defaultCode,
        "", false, "", "", screeningToReferral.getName(), "", receivedDateTime[0],
        receivedDateTime[1], referralResponseTypeCode, defaultCode, "", "", "",
        screeningToReferral.getReportNarrative(), "", "", "", "", "", "", "", "", false, false,
        false, false, "", responsibleAgencyCode, defaultCode, "", "", "");
    // IDENTIFIER and LAST_UPD_ID are assigned in the create method
    PostedReferral postedReferral = this.referralService.create(referral);
    referralId = postedReferral.getId();

    /*
     * CWS/CMS Clients and Reporter
     */
    // TODO: what about self-reported - for now - create CLIENT and REFERRAL_CLIENT
    // TODO: what about anonymous reporter ??
    Set<gov.ca.cwds.rest.api.domain.cms.ReferralClient> resultReferralClients =
        new LinkedHashSet<>();
    Set<PostedClient> postedClients = new LinkedHashSet<>();
    Set<Participant> participants = new LinkedHashSet<>();
    participants = screeningToReferral.getParticipants();
    for (Participant incomingParticipant : participants) {

      genderCode = incomingParticipant.getGender();
      reporterParticipant = false;
      victimClient.clear();
      perpatratorClient.clear();

      Set<String> roles = new HashSet<String>(incomingParticipant.getRoles());
      /**
       * process the roles of this participant
       */
      for (String role : roles) {

        if ((role.equalsIgnoreCase("mandated reporter")
            || role.equalsIgnoreCase("non-mandated reporter"))
            && !anonymousReporter(screeningToReferral)) {
          /*
           * CMS Reporter - if role is 'mandated reporter' or 'non-mandated reporter'
           */
          if (savedreporter != null) {
            throw new ServiceException("ERROR - only one Reporter per Referral allowed");
          }

          Reporter reporter = new Reporter("", "", defaultCode, defaultCode, false, "", "", "",
              false, incomingParticipant.getFirstName(), incomingParticipant.getLastName(), false,
              0, defaultBig, "", "", defaultBig, 0, defaultCode, "", "", "", "", "", "",
              defaultCode, countyCode);
          PostedReporter postedreporter = this.reporterService.create(reporter);
          savedreporter = postedreporter;
        } else {
          // not a reporter participant - make a CLIENT and REFERRAL_CLIENT
          // TODO: create the Client IDENTIFIER

          if (!anonymousReporter) {
            // not an anonymous reporter participant - create some kind of client
            Client client = new Client("", false, "", "", "", defaultCode,
                incomingParticipant.getDateOfBirth(), "", defaultCode, false, false, "", "",
                incomingParticipant.getFirstName(), incomingParticipant.getLastName(), "", "",
                false, "", false, "", false, "", false, "", "", "", defaultCode, "", "", "", "", "",
                genderCode, "", "", defaultCode, defaultCode, "", false, false, "", false,
                defaultCode, "", "", "", defaultCode, false, false, "", false, defaultCode,
                defaultCode, defaultCode, defaultCode, false, "", "", false,
                incomingParticipant.getSsn(), "", "", false, false, "", false);
            PostedClient postedClient = this.clientService.create(client);
            postedClients.add(postedClient);
            clientId = postedClient.getId();

            selfReported = false;
            if (role.equalsIgnoreCase("self reported")) {
              selfReported = true;
            }
            // TODO: set FKCLIENT_T and FKREFERL_T
            ReferralClient referralClient =
                new ReferralClient("", defaultCode, defaultCode, "", "", selfReported, false,
                    referralId, clientId, "", defaultCode, "", "", false, false, false);
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

              zipCode = address.getZip().toString().substring(0, 4);
              zipSuffix = Short.parseShort(address.getZip().toString().substring(5, 4));

              String[] streetAddress = address.getStreetAddress().split(" ");
              streetNumber = streetAddress[0];
              streetName = streetAddress[1];

              // TODO: include logic and processing to create/update ADDRESS rows in CWS/CMS
              // database
              // gov.ca.cwds.rest.api.domain.cms.Address cmsAddress =
              // new gov.ca.cwds.rest.api.domain.cms.Address(address.getCity(), "", defaultInt,
              // defaultBig, false, defaultCode, defaultInt, defaultBig, "", "", "", defaultInt,
              // defaultBig, addressStateCode, streetNumber, streetName, defaultCode, defaultCode,
              // "", zipCode, zipSuffix);



              /*
               * CMS Client Address
               */
              /*
               * determine other participant attributes relating to CWS/CMS allegation
               */
              if (role.equalsIgnoreCase("victim") || role.equalsIgnoreCase("self reported")) {
                victimClient.put(incomingParticipant.getId(), postedClient.getId());
              }
              if (role.equalsIgnoreCase("perpatrator")) {
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

    crossReports = screeningToReferral.getCrossReports();
    for (CrossReport crossReport : crossReports) {

      lawEnforcementIndicator = false;
      if (crossReport.getAgencyType().contains("Law Enforcement")) {
        lawEnforcementIndicator = true;
      }
      gov.ca.cwds.rest.api.domain.cms.CrossReport cmsCrossReport =
          new gov.ca.cwds.rest.api.domain.cms.CrossReport(crossReportId, crossReportMethodCode,
              false, false, "", "", defaultInt, defaultBig, crossReport.getInformDate(), "", "", "",
              referralId, "", crossReport.getAgencyName(), "", "", "", lawEnforcementIndicator,
              false, false);
      gov.ca.cwds.rest.api.domain.cms.CrossReport postedCrossReport =
          this.crossReportService.create(cmsCrossReport);
      resultCrossReports.add(postedCrossReport);
    }

    /*
     * CMS Allegation - one for each allegation
     */
    // TODO: set up ALLEGATION_PERPATRATOR_HISTORY record for Allegation
    Set<PostedAllegation> postedAllegations = new LinkedHashSet<>();
    Set<Allegation> allegations = new LinkedHashSet<>();
    allegations = screeningToReferral.getAllegations();
    for (Allegation allegation : allegations) {
      victimClientId = victimClient.get(allegation.getVictimPersonId());
      perpatratorClientId = perpatratorClient.get(allegation.getPerpetratorPersonId());
      // TODO: throw error if victimClientId is not set
      if (victimClientId.isEmpty()) {
        throw new ServiceException("ERROR - victim could not be determined for an allegation");
      }

      gov.ca.cwds.rest.api.domain.cms.Allegation cmsAllegation =
          new gov.ca.cwds.rest.api.domain.cms.Allegation("", defaultCode, "",
              screeningToReferral.getLocationType(), "", defaultCode, allegationTypeCode,
              screeningToReferral.getReportNarrative(), "", false, "", false, victimClientId,
              perpatratorClientId, referralId, countyCode, false, defaultCode);
      PostedAllegation postedAllegation = this.allegationService.create(cmsAllegation);
      postedAllegations.add(postedAllegation);
      allegationId = postedAllegation.getId();
    }

    return new PostedCmsReferral(postedReferral, postedClients, postedAllegations,
        resultCrossReports, resultReferralClients, savedreporter);
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

        if (role.equalsIgnoreCase("anonymous reporter")) {
          return true;
        }
      }
    }
    return false;
  }

}
