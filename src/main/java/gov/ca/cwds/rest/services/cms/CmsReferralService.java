package gov.ca.cwds.rest.services.cms;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.NotImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.cms.Allegation;
import gov.ca.cwds.rest.api.domain.cms.Client;
import gov.ca.cwds.rest.api.domain.cms.ClientUc;
import gov.ca.cwds.rest.api.domain.cms.CmsReferral;
import gov.ca.cwds.rest.api.domain.cms.CrossReport;
import gov.ca.cwds.rest.api.domain.cms.PostedAllegation;
import gov.ca.cwds.rest.api.domain.cms.PostedClient;
import gov.ca.cwds.rest.api.domain.cms.PostedCmsReferral;
import gov.ca.cwds.rest.api.domain.cms.PostedReferral;
import gov.ca.cwds.rest.api.domain.cms.PostedReporter;
import gov.ca.cwds.rest.api.domain.cms.ReferralClient;
import gov.ca.cwds.rest.api.domain.cms.Reporter;
import gov.ca.cwds.rest.services.CrudsService;
import io.dropwizard.hibernate.UnitOfWork;

/**
 * @author CWDS API Team
 *
 */
public class CmsReferralService implements CrudsService {

  @SuppressWarnings("unused")
  private static final Logger LOGGER = LoggerFactory.getLogger(CmsReferralService.class);

  private ReferralService referralService;
  private ClientService clientService;
  private AllegationService allegationService;
  private CrossReportService crossReportService;
  private ReferralClientService referralClientService;
  private ReporterService reporterService;
  private ClientUcService clientUcService;

  /**
   * Constructor
   * 
   * @param referralService the referralService
   * @param allegationService the allegationService
   * @param crossReportService the crossReportService
   * @param referralClientService the referralClientService
   * @param reporterService the reporterService
   * @param clientService the clientServiec
   * @param clientUcService the clientUcServiec
   */
  @Inject
  public CmsReferralService(ReferralService referralService, ClientService clientService,
      AllegationService allegationService, CrossReportService crossReportService,
      ReferralClientService referralClientService, ReporterService reporterService,
      ClientUcService clientUcService) {
    super();
    this.referralService = referralService;
    this.clientService = clientService;
    this.allegationService = allegationService;
    this.crossReportService = crossReportService;
    this.referralClientService = referralClientService;
    this.reporterService = reporterService;
    this.clientUcService = clientUcService;
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#create(gov.ca.cwds.rest.api.Request)
   */
  @UnitOfWork(value = "cms")
  @Override
  public Response create(Request request) {
    assert request instanceof CmsReferral;

    CmsReferral cmsReferral = (CmsReferral) request;
    PostedReferral referral = this.referralService.create(cmsReferral.getReferral());
    String referralId = referral.getId();
    List<String> clientIds = new ArrayList<>();
    int clientInst = 0;
    String sourceTableCode = "C";

    Set<ClientUc> resultClientUc = new LinkedHashSet<>();
    Set<PostedClient> postedClients = new LinkedHashSet<>();
    if (cmsReferral.getClient() != null && !cmsReferral.getClient().isEmpty()) {
      for (Client incomingClient : cmsReferral.getClient()) {
        PostedClient savedClient = null;
        if (incomingClient.getExistingClientId() != null) {
          savedClient = clientService.findInboundId(incomingClient.getExistingClientId());
        }

        if (savedClient == null) {
          Client client = new Client(incomingClient.getExistingClientId(),
              incomingClient.getAdjudicatedDelinquentIndicator(),
              incomingClient.getAdoptionStatusCode(), incomingClient.getAlienRegistrationNumber(),
              incomingClient.getBirthCity(), incomingClient.getBirthCountryCodeType(),
              incomingClient.getBirthDate(), incomingClient.getBirthFacilityName(),
              incomingClient.getBirthStateCodeType(),
              incomingClient.getBirthplaceVerifiedIndicator(),
              incomingClient.getChildClientIndicatorVar(), incomingClient.getClientIndexNumber(),
              incomingClient.getCommentDescription(), incomingClient.getCommonFirstName(),
              incomingClient.getCommonLastName(), incomingClient.getCommonMiddleName(),
              incomingClient.getConfidentialityActionDate(),
              incomingClient.getConfidentialityInEffectIndicator(),
              incomingClient.getCreationDate(), incomingClient.getCurrCaChildrenServIndicator(),
              incomingClient.getCurrentlyOtherDescription(),
              incomingClient.getCurrentlyRegionalCenterIndicator(), incomingClient.getDeathDate(),
              incomingClient.getDeathDateVerifiedIndicator(), incomingClient.getDeathPlace(),
              incomingClient.getDeathReasonText(), incomingClient.getDriverLicenseNumber(),
              incomingClient.getDriverLicenseStateCodeType(), incomingClient.getEmailAddress(),
              incomingClient.getEstimatedDobCode(), incomingClient.getEthUnableToDetReasonCode(),
              incomingClient.getFatherParentalRightTermDate(), incomingClient.getGenderCode(),
              incomingClient.getHealthSummaryText(), incomingClient.getHispUnableToDetReasonCode(),
              incomingClient.getHispanicOriginCode(),
              incomingClient.getImmigrationCountryCodeType(),
              incomingClient.getImmigrationStatusType(),
              incomingClient.getIncapacitatedParentCode(),
              incomingClient.getIndividualHealthCarePlanIndicator(),
              incomingClient.getLimitationOnScpHealthIndicator(), incomingClient.getLiterateCode(),
              incomingClient.getMaritalCohabitatnHstryIndicatorVar(),
              incomingClient.getMaritalStatusType(), incomingClient.getMilitaryStatusCode(),
              incomingClient.getMotherParentalRightTermDate(),
              incomingClient.getNamePrefixDescription(), incomingClient.getNameType(),
              incomingClient.getOutstandingWarrantIndicator(),
              incomingClient.getPrevCaChildrenServIndicator(),
              incomingClient.getPrevOtherDescription(),
              incomingClient.getPrevRegionalCenterIndicator(),
              incomingClient.getPrimaryEthnicityType(), incomingClient.getPrimaryLanguageType(),
              incomingClient.getReligionType(), incomingClient.getSecondaryLanguageType(),
              incomingClient.getSensitiveHlthInfoOnFileIndicator(),
              incomingClient.getSensitivityIndicator(), incomingClient.getSoc158PlacementCode(),
              incomingClient.getSoc158SealedClientIndicator(),
              incomingClient.getSocialSecurityNumChangedCode(),
              incomingClient.getSocialSecurityNumber(), incomingClient.getSuffixTitleDescription(),
              incomingClient.getTribalAncestryClientIndicatorVar(),
              incomingClient.getTribalMembrshpVerifctnIndicatorVar(),
              incomingClient.getUnemployedParentCode(), incomingClient.getZippyCreatedIndicator());
          PostedClient postedclient = this.clientService.create(client);
          postedClients.add(postedclient);
          clientIds.add(postedclient.getId());
          if (postedclient.getCommonFirstName() != null) {
            ClientUc clientUc = new ClientUc(postedclient.getId(), sourceTableCode,
                postedclient.getCommonFirstName().toUpperCase(),
                postedclient.getCommonLastName().toUpperCase(),
                postedclient.getCommonMiddleName().toUpperCase());
            clientUc = this.clientUcService.create(clientUc);
            resultClientUc.add(clientUc);
          }
        } else {
          clientIds.add(savedClient.getId());
          postedClients.add(savedClient);
        }
      }
    }

    Set<PostedAllegation> resultAllegation = new LinkedHashSet<>();
    if (cmsReferral.getAllegation() != null && !cmsReferral.getAllegation().isEmpty()) {
      for (Allegation incomingAllegation : cmsReferral.getAllegation()) {
        Allegation allegation = new Allegation(incomingAllegation.getAbuseEndDate(),
            incomingAllegation.getAbuseFrequency(),
            incomingAllegation.getAbuseFrequencyPeriodCode(),
            incomingAllegation.getAbuseLocationDescription(),
            incomingAllegation.getAbuseStartDate(),
            incomingAllegation.getAllegationDispositionType(),
            incomingAllegation.getAllegationType(), incomingAllegation.getDispositionDescription(),
            incomingAllegation.getDispositionDate(),
            incomingAllegation.getInjuryHarmDetailIndicator(),
            incomingAllegation.getNonProtectingParentCode(),
            incomingAllegation.getStaffPersonAddedIndicator(),
            incomingAllegation.getVictimClientId(), incomingAllegation.getPerpetratorClientId(),
            referralId, incomingAllegation.getCountySpecificCode(),
            incomingAllegation.getZippyCreatedIndicator(),
            incomingAllegation.getPlacementFacilityType());
        PostedAllegation postedallegation = this.allegationService.create(allegation);
        resultAllegation.add(postedallegation);
      }
    }

    Set<CrossReport> resultCrossReport = new LinkedHashSet<>();
    if (cmsReferral.getCrossReport() != null && !cmsReferral.getCrossReport().isEmpty()) {
      for (CrossReport incomingCrossReport : cmsReferral.getCrossReport()) {
        CrossReport crossReport = new CrossReport(incomingCrossReport.getThirdId(),
            incomingCrossReport.getCrossReportMethodType(),
            incomingCrossReport.getFiledOutOfStateIndicator(),
            incomingCrossReport.getGovernmentOrgCrossRptIndicatorVar(),
            incomingCrossReport.getInformTime(), incomingCrossReport.getRecipientBadgeNumber(),
            incomingCrossReport.getRecipientPhoneExtensionNumber(),
            incomingCrossReport.getRecipientPhoneNumber(), incomingCrossReport.getInformDate(),
            incomingCrossReport.getRecipientPositionTitleDesc(),
            incomingCrossReport.getReferenceNumber(), referralId,
            incomingCrossReport.getLawEnforcementId(), incomingCrossReport.getStaffPersonId(),
            incomingCrossReport.getDescription(), incomingCrossReport.getRecipientName(),
            incomingCrossReport.getOutStateLawEnforcementAddr(),
            incomingCrossReport.getCountySpecificCode(),
            incomingCrossReport.getLawEnforcementIndicator(),
            incomingCrossReport.getOutStateLawEnforcementIndicator(),
            incomingCrossReport.getSatisfyCrossReportIndicator());
        crossReport = this.crossReportService.create(crossReport);
        resultCrossReport.add(crossReport);
      }
    }

    Set<ReferralClient> resultReferralClient = new LinkedHashSet<>();
    if (cmsReferral.getReferralClient() != null && !cmsReferral.getReferralClient().isEmpty()) {
      for (ReferralClient incomingReferralClient : cmsReferral.getReferralClient()) {
        ReferralClient referralClient =
            new ReferralClient(incomingReferralClient.getApprovalNumber(),
                incomingReferralClient.getApprovalStatusType(),
                incomingReferralClient.getDispositionClosureReasonType(),
                incomingReferralClient.getDispositionCode(),
                incomingReferralClient.getDispositionDate(),
                incomingReferralClient.getSelfReportedIndicator(),
                incomingReferralClient.getStaffPersonAddedIndicator(), referralId,
                (String) clientIds.toArray()[clientInst],
                incomingReferralClient.getDispositionClosureDescription(),
                incomingReferralClient.getAgeNumber(), incomingReferralClient.getAgePeriodCode(),
                incomingReferralClient.getCountySpecificCode(),
                incomingReferralClient.getMentalHealthIssuesIndicator(),
                incomingReferralClient.getAlcoholIndicator(),
                incomingReferralClient.getDrugIndicator());
        referralClient = this.referralClientService.create(referralClient);
        resultReferralClient.add(referralClient);
        clientInst++;
      }
    }

    PostedReporter savedreporter = new PostedReporter();
    if (cmsReferral.getReporter() != null) {
      Reporter incomingReporter = cmsReferral.getReporter();
      Reporter reporter = new Reporter(incomingReporter.getBadgeNumber(),
          incomingReporter.getCityName(), incomingReporter.getColltrClientRptrReltnshpType(),
          incomingReporter.getCommunicationMethodType(),
          incomingReporter.getConfidentialWaiverIndicator(),
          incomingReporter.getDrmsMandatedRprtrFeedback(), incomingReporter.getEmployerName(),
          incomingReporter.getFeedbackDate(), incomingReporter.getFeedbackRequiredIndicator(),
          incomingReporter.getFirstName(), incomingReporter.getLastName(),
          incomingReporter.getMandatedReporterIndicator(),
          incomingReporter.getMessagePhoneExtensionNumber(),
          incomingReporter.getMessagePhoneNumber(), incomingReporter.getMiddleInitialName(),
          incomingReporter.getNamePrefixDescription(), incomingReporter.getPrimaryPhoneNumber(),
          incomingReporter.getPrimaryPhoneExtensionNumber(), incomingReporter.getStateCodeType(),
          incomingReporter.getStreetName(), incomingReporter.getStreetNumber(),
          incomingReporter.getSuffixTitleDescription(), incomingReporter.getZipcode(), referralId,
          incomingReporter.getLawEnforcementId(), incomingReporter.getZipSuffixNumber(),
          incomingReporter.getCountySpecificCode());
      PostedReporter postedreporter = this.reporterService.create(reporter);
      savedreporter = postedreporter;
    }

    return new PostedCmsReferral(referral, postedClients, resultAllegation, resultCrossReport,
        resultReferralClient, savedreporter);
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#find(java.io.Serializable)
   */
  @Override
  public Response find(Serializable primaryKey) {
    throw new NotImplementedException("find not implement");
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#delete(java.io.Serializable)
   */
  @Override
  public Response delete(Serializable primaryKey) {
    throw new NotImplementedException("delete not implement");
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#update(java.io.Serializable,
   *      gov.ca.cwds.rest.api.Request)
   */
  @Override
  public Response update(Serializable primaryKey, Request request) {
    throw new NotImplementedException("update not implement");
  }

}
