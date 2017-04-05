package gov.ca.cwds.rest.services;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
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

  // TODO: create the ID values
  private String referralId = "1234567890ABC";
  private String clientId = "2345678901ABC";
  private String crossReportId = "34567890123ABC";
  private String allegationId = "4567890123ABC";


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
    String[] received = screeningToReferral.getStartedAt().split("T");

    Referral referral = new Referral(false, false, false, "", defaultCode, false, "",
        communicationsMethodCode, "", "", "", "", false, false, defaultCode, "", false, "", "",
        screeningToReferral.getName(), "", received[0], received[1], referralResponseTypeCode,
        defaultCode, "", "", "", screeningToReferral.getReportNarrative(), "", "", "", "", "", "",
        "", "", false, false, false, false, "", responsibleAgencyCode, defaultCode, "", "", "");
    // IDENTIFIER and LAST_UPD_ID are assigned in this create method
    PostedReferral postedReferral = this.referralService.create(referral);

    /*
     * CMS Clients, Referral Clients, Client Addresses, and Reporter - one for each Participant
     */

    // TODO: create the Client IDENTIFIER
    Set<gov.ca.cwds.rest.api.domain.cms.ReferralClient> resultReferralClients =
        new LinkedHashSet<>();
    Set<PostedClient> postedClients = new LinkedHashSet<>();
    Set<Participant> participants = new LinkedHashSet<>();
    participants = screeningToReferral.getParticipants();
    for (Participant incomingParticipant : participants) {

      genderCode = incomingParticipant.getGender();

      /**
       * process the roles of this participant
       */
      reporterParticipant = false;
      Set<String> roles = new HashSet<String>(incomingParticipant.getRoles());
      for (String role : roles) {
        if (role.equalsIgnoreCase("mandated reporter") != role
            .equalsIgnoreCase("non-mandated reporter")) {
          reporterParticipant = true;
        }
      }

      if (reporterParticipant) {
        /*
         * CMS Reporter - if role is 'mandated reporter' or 'non-mandated reporter'
         */
        // TODO: what about self-reported - no role for this yet

        Reporter reporter = new Reporter("", "", defaultCode, defaultCode, false, "", "", "", false,
            incomingParticipant.getFirstName(), incomingParticipant.getLastName(), false, 0,
            defaultBig, "", "", defaultBig, 0, defaultCode, "", "", "", "", "", "", defaultCode,
            countyCode);
        PostedReporter postedreporter = this.reporterService.create(reporter);
        savedreporter = postedreporter;
      } else {

        Client client =
            new Client("", false, "", "", "", defaultCode, incomingParticipant.getDateOfBirth(), "",
                defaultCode, false, false, "", "", incomingParticipant.getFirstName(),
                incomingParticipant.getLastName(), "", "", false, "", false, "", false, "", false,
                "", "", "", defaultCode, "", "", "", "", "", genderCode, "", "", defaultCode,
                defaultCode, "", false, false, "", false, defaultCode, "", "", "", defaultCode,
                false, false, "", false, defaultCode, defaultCode, defaultCode, defaultCode, false,
                "", "", false, incomingParticipant.getSsn(), "", "", false, false, "", false);
        PostedClient postedclient = this.clientService.create(client);
        postedClients.add(postedclient);

        // TODO: set FKCLIENT_T and FKREFERL_T
        ReferralClient referralClient = new ReferralClient("", defaultCode, defaultCode, "", "",
            false, false, "", "", "", defaultCode, "", "", false, false, false);
        gov.ca.cwds.rest.api.domain.cms.ReferralClient postedReferralClient =
            this.referralClientService.create(referralClient);
        resultReferralClients.add(postedReferralClient);

        /*
         * CMS Address
         */
        Set<gov.ca.cwds.rest.api.domain.Address> addresses =
            new HashSet<gov.ca.cwds.rest.api.domain.Address>(incomingParticipant.getAddresses());
        for (gov.ca.cwds.rest.api.domain.Address address : addresses) {

          zipCode = address.getZip().toString().substring(0, 4);
          zipSuffix = Short.parseShort(address.getZip().toString().substring(5, 4));

          String[] streetAddress = address.getStreetAddress().split(" ");
          streetNumber = streetAddress[0];
          streetName = streetAddress[1];

          // TODO: include logic and processing to create/update ADDRESS rows in CWS/CMS database
          gov.ca.cwds.rest.api.domain.cms.Address cmsAddress =
              new gov.ca.cwds.rest.api.domain.cms.Address(address.getCity(), "", defaultInt,
                  defaultBig, false, defaultCode, defaultInt, defaultBig, "", "", "", defaultInt,
                  defaultBig, addressStateCode, streetNumber, streetName, defaultCode, defaultCode,
                  "", zipCode, zipSuffix);


          /*
           * CMS Client Address
           */

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
    // TODO: set up Client ID for perpatrator
    // TODO: set up Client ID for victim
    Set<PostedAllegation> postedAllegations = new LinkedHashSet<>();
    Set<Allegation> allegations = new LinkedHashSet<>();
    allegations = screeningToReferral.getAllegations();
    for (Allegation allegation : allegations) {
      gov.ca.cwds.rest.api.domain.cms.Allegation cmsAllegation =
          new gov.ca.cwds.rest.api.domain.cms.Allegation("", defaultCode, "",
              screeningToReferral.getLocationType(), "", defaultCode, allegationTypeCode,
              screeningToReferral.getReportNarrative(), "", false, "", false, "", "", referralId,
              countyCode, false, defaultCode);
      PostedAllegation postedAllegation = this.allegationService.create(cmsAllegation);
      postedAllegations.add(postedAllegation);
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

}
