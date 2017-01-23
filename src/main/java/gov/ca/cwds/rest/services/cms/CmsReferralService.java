package gov.ca.cwds.rest.services.cms;

import java.io.Serializable;

import org.apache.commons.lang3.NotImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.cms.Allegation;
import gov.ca.cwds.rest.api.domain.cms.CmsReferral;
import gov.ca.cwds.rest.api.domain.cms.CrossReport;
import gov.ca.cwds.rest.api.domain.cms.PostedAllegation;
import gov.ca.cwds.rest.api.domain.cms.PostedCmsReferral;
import gov.ca.cwds.rest.api.domain.cms.PostedReferral;
import gov.ca.cwds.rest.api.domain.cms.PostedReporter;
import gov.ca.cwds.rest.api.domain.cms.ReferralClient;
import gov.ca.cwds.rest.api.domain.cms.Reporter;
import gov.ca.cwds.rest.services.CrudsService;

/**
 * @author CWDS API Team
 *
 */
public class CmsReferralService implements CrudsService {

  @SuppressWarnings("unused")
  private static final Logger LOGGER = LoggerFactory.getLogger(CmsReferralService.class);

  private ReferralService referralService;
  private AllegationService allegationService;
  private CrossReportService crossReportService;
  private ReferralClientService referralClientService;
  private ReporterService reporterService;

  /**
   * Constructor
   * 
   * @param referralService the referralService
   * @param allegationService the allegationService
   * @param crossReportService the crossReportService
   * @param referralClientService the referralClientService
   * @param reporterService the reporterService
   */
  @Inject
  public CmsReferralService(ReferralService referralService, AllegationService allegationService,
      CrossReportService crossReportService, ReferralClientService referralClientService,
      ReporterService reporterService) {
    super();
    this.referralService = referralService;
    this.allegationService = allegationService;
    this.crossReportService = crossReportService;
    this.referralClientService = referralClientService;
    this.reporterService = reporterService;
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#create(gov.ca.cwds.rest.api.Request)
   */
  @Override
  public Response create(Request request) {
    assert request instanceof CmsReferral;

    CmsReferral cmsReferral = (CmsReferral) request;
    PostedReferral referral = this.referralService.create(cmsReferral.getReferral());
    String referralId = referral.getId();
    Allegation incomingAllegation = cmsReferral.getAllegation();
    Allegation allegation = new Allegation(incomingAllegation.getAbuseEndDate(),
        incomingAllegation.getAbuseFrequency(), incomingAllegation.getAbuseFrequencyPeriodCode(),
        incomingAllegation.getAbuseLocationDescription(), incomingAllegation.getAbuseStartDate(),
        incomingAllegation.getAllegationDispositionType(), incomingAllegation.getAllegationType(),
        incomingAllegation.getDispositionDescription(), incomingAllegation.getDispositionDate(),
        incomingAllegation.getInjuryHarmDetailIndicator(),
        incomingAllegation.getNonProtectingParentCode(),
        incomingAllegation.getStaffPersonAddedIndicator(), incomingAllegation.getVictimClientId(),
        incomingAllegation.getPerpetratorClientId(), referralId,
        incomingAllegation.getCountySpecificCode(), incomingAllegation.getZippyCreatedIndicator(),
        incomingAllegation.getPlacementFacilityType());
    PostedAllegation postedallegation = this.allegationService.create(allegation);

    CrossReport incomingCrossReport = cmsReferral.getCrossReport();
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

    ReferralClient incomingReferralClient = cmsReferral.getReferralClient();
    ReferralClient referralClient = new ReferralClient(incomingReferralClient.getApprovalNumber(),
        incomingReferralClient.getApprovalStatusType(),
        incomingReferralClient.getDispositionClosureReasonType(),
        incomingReferralClient.getDispositionCode(), incomingReferralClient.getDispositionDate(),
        incomingReferralClient.getSelfReportedIndicator(),
        incomingReferralClient.getStaffPersonAddedIndicator(), referralId,
        incomingReferralClient.getClientId(),
        incomingReferralClient.getDispositionClosureDescription(),
        incomingReferralClient.getAgeNumber(), incomingReferralClient.getAgePeriodCode(),
        incomingReferralClient.getCountySpecificCode(),
        incomingReferralClient.getMentalHealthIssuesIndicator(),
        incomingReferralClient.getAlcoholIndicator(), incomingReferralClient.getDrugIndicator());
    referralClient = this.referralClientService.create(referralClient);

    Reporter incomingReporter = cmsReferral.getReporter();
    Reporter reporter = new Reporter(incomingReporter.getBadgeNumber(),
        incomingReporter.getCityName(), incomingReporter.getColltrClientRptrReltnshpType(),
        incomingReporter.getCommunicationMethodType(),
        incomingReporter.getConfidentialWaiverIndicator(),
        incomingReporter.getDrmsMandatedRprtrFeedback(), incomingReporter.getEmployerName(),
        incomingReporter.getFeedbackDate(), incomingReporter.getFeedbackRequiredIndicator(),
        incomingReporter.getFirstName(), incomingReporter.getLastName(),
        incomingReporter.getMandatedReporterIndicator(),
        incomingReporter.getMessagePhoneExtensionNumber(), incomingReporter.getMessagePhoneNumber(),
        incomingReporter.getMiddleInitialName(), incomingReporter.getNamePrefixDescription(),
        incomingReporter.getPrimaryPhoneNumber(), incomingReporter.getPrimaryPhoneExtensionNumber(),
        incomingReporter.getStateCodeType(), incomingReporter.getStreetName(),
        incomingReporter.getStreetNumber(), incomingReporter.getSuffixTitleDescription(),
        incomingReporter.getZipcode(), referralId, incomingReporter.getLawEnforcementId(),
        incomingReporter.getZipSuffixNumber(), incomingReporter.getCountySpecificCode());
    PostedReporter postedreporter = this.reporterService.create(reporter);

    return new PostedCmsReferral(referral, postedallegation, crossReport, referralClient,
        postedreporter);
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
