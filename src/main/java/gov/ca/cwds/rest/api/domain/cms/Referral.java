package gov.ca.cwds.rest.api.domain.cms;

import static gov.ca.cwds.data.persistence.cms.CmsPersistentObject.CMS_ID_LEN;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.data.persistence.cms.CmsKeyIdGenerator;
import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.DomainChef;
import gov.ca.cwds.rest.api.domain.DomainObject;
import gov.ca.cwds.rest.api.domain.ReportingDomain;
import io.dropwizard.validation.OneOf;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * {@link DomainObject} as a direct representation of the physical representation of a Referral in
 * the legacy database.
 * 
 * @author CWDS API Team
 */
@ApiModel
public class Referral extends ReportingDomain implements Request, Response {
  private static final short DEFAULT_CODE = 0;
  private static final String DEFAULT_NO = "N";
  private static final String DEFAULT_LIMITIED_ACCESS_CODE = "N";
  private static final String DEFAULT_RESPONSIBLE_AGENCY_CODE = "C";

  /**
   * Serialization version
   */
  private static final long serialVersionUID = 1L;

  @NotNull
  @ApiModelProperty(required = false, readOnly = false, value = "Y/N", example = "Y")
  private Boolean additionalInfoIncludedCode;

  @NotNull
  @ApiModelProperty(required = true, readOnly = false, value = "N", example = "N")
  private Boolean anonymousReporterIndicator;

  @NotNull
  @ApiModelProperty(required = true, readOnly = false, value = "N", example = "N")
  private Boolean applicationForPetitionIndicator;

  @Size(max = 10)
  @ApiModelProperty(required = false, readOnly = false, value = "Approval Number",
      example = "ABC1234567")
  private String approvalNumber;

  @NotNull
  @ApiModelProperty(required = true, readOnly = false, value = "Approval Status", example = "1234")
  private Short approvalStatusType;

  @NotNull
  @ApiModelProperty(required = false, readOnly = false, value = "Caretakers Perpetrator Code",
      example = "Y")
  private Boolean caretakersPerpetratorCode;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT)
  @JsonProperty(value = "closureDate")
  @gov.ca.cwds.rest.validation.Date(format = DATE_FORMAT, required = false)
  @ApiModelProperty(required = false, readOnly = false, value = "yyyy-MM-dd",
      example = "2000-01-01")
  private String closureDate;

  @NotNull
  @ApiModelProperty(required = true, readOnly = false, value = "Communication Method",
      example = "444")
  private Short communicationMethodType;

  @NotEmpty
  @Size(min = 1, max = 2)
  @ApiModelProperty(required = true, readOnly = false, value = "County Specific Code",
      example = "99")
  private String countySpecificCode;

  @Size(max = 10)
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "ABC1234567")
  private String currentLocationOfChildren;

  @Size(max = 10)
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "ABC1234567")
  private String drmsAllegationDescriptionDoc;

  @Size(max = 10)
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "ABC1234567")
  private String drmsErReferralDoc;

  @Size(max = 10)
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "ABC1234567")
  private String drmsInvestigationDoc;

  @NotNull
  @ApiModelProperty(required = true, readOnly = false)
  private Boolean filedSuspectedChildAbuseReporttoLawEnforcementIndicator;

  @NotNull
  @ApiModelProperty(required = true, readOnly = false, value = "N", example = "N")
  private Boolean familyAwarenessIndicator;

  @NotNull
  @ApiModelProperty(required = false, readOnly = false, value = "0", example = "1234")
  private Short govtEntityType;

  @NotEmpty
  @Size(min = 1, max = 1, message = "size must be 1")
  @ApiModelProperty(required = true, readOnly = false, value = "", example = "A")
  private String legalDefinitionCode;

  @NotNull
  @ApiModelProperty(required = false, readOnly = false, value = "N", example = "N")
  private Boolean legalRightsNoticeIndicator;

  @NotEmpty
  @Size(min = 1, max = 1, message = "size must be 1")
  @OneOf(value = {"S", "R", "N"}, ignoreCase = true, ignoreWhitespace = true)
  @ApiModelProperty(required = true, readOnly = false,
      value = "S = sensitive, R = sealed, N = no restriction", example = "S",
      allowableValues = "S, R, N")
  private String limitedAccessCode;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT)
  @JsonProperty(value = "mandatedCrossReportReceivedDate")
  @gov.ca.cwds.rest.validation.Date(format = DATE_FORMAT, required = false)
  @ApiModelProperty(required = false, readOnly = false, value = "yyyy-MM-dd",
      example = "2000-01-01")
  private String mandatedCrossReportReceivedDate;

  @NotNull
  @Size(max = 35)
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "ABC123")
  private String referralName;

  @NotNull
  @Size(max = 1, message = "size must be 1")
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "N")
  private String openAdequateCaseCode;

  @NotNull
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT)
  @JsonProperty(value = "receivedDate")
  @gov.ca.cwds.rest.validation.Date(format = DATE_FORMAT, required = true)
  @ApiModelProperty(required = true, readOnly = false, value = "yyyy-MM-dd", example = "2000-01-01")
  private String receivedDate;

  @NotNull
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = TIME_FORMAT)
  @JsonProperty(value = "receivedTime")
  @gov.ca.cwds.rest.validation.Date(format = TIME_FORMAT, required = true)
  @ApiModelProperty(required = true, readOnly = false, example = "16:41:49")
  private String receivedTime;

  @NotNull
  @ApiModelProperty(required = false, readOnly = false, example = "1234")
  private Short referralResponseType;

  @NotNull
  @ApiModelProperty(required = false, readOnly = false, example = "1234")
  private Short referredToResourceType;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT)
  @JsonProperty(value = "responseDeterminationDate")
  @gov.ca.cwds.rest.validation.Date(format = DATE_FORMAT, required = false)
  @ApiModelProperty(required = false, readOnly = false, value = "yyyy-MM-dd",
      example = "2000-01-01")
  private String responseDeterminationDate;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = TIME_FORMAT)
  @JsonProperty(value = "responseDeterminationTime")
  @gov.ca.cwds.rest.validation.Date(format = TIME_FORMAT, required = false)
  @ApiModelProperty(required = false, readOnly = false, example = "16:41:49")
  private String responseDeterminationTime;

  @Size(max = 10)
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "ABC1234567")
  private String responseRationaleText;

  @Size(max = 10)
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "ABC1234567")
  private String screenerNoteText;

  @NotEmpty
  @Size(max = 1, message = "size must be 1")
  @ApiModelProperty(readOnly = false, value = "", example = "N")
  private String specificsIncludedCode;

  @NotEmpty
  @Size(min = 1, max = 1, message = "size must be 1")
  @ApiModelProperty(required = true, readOnly = false, value = "", example = "N")
  private String sufficientInformationCode;

  @NotEmpty
  @Size(min = 1, max = 1, message = "size must be 1")
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "N")
  private String unfoundedSeriesCode;

  @Size(max = CMS_ID_LEN)
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "ABC1234567")
  private String linkToPrimaryReferralId;

  @Size(max = CMS_ID_LEN)
  @ApiModelProperty(required = false, readOnly = false, value = "alleged abuse occurred at address",
      example = "ABC1234567")
  private String allegesAbuseOccurredAtAddressId;

  @Size(max = 3)
  @ApiModelProperty(required = false, readOnly = false,
      value = "first response determined by staff person id", example = "A1A")
  private String firstResponseDeterminedByStaffPersonId;

  @NotEmpty
  @Size(min = 3, max = 3)
  @ApiModelProperty(required = true, readOnly = false, value = "primary contact staff person id",
      example = "A1A")
  private String primaryContactStaffPersonId;

  @NotNull
  @ApiModelProperty(required = true, readOnly = false)
  private Boolean specialProjectReferralIndicator;

  @NotNull
  @ApiModelProperty(required = true, readOnly = false)
  private Boolean zippyCreatedIndicator;

  @NotNull
  @ApiModelProperty(required = true, readOnly = false)
  private Boolean homelessIndicator;

  @NotNull
  @ApiModelProperty(required = true, readOnly = false)
  private Boolean familyRefusedServicesIndicator;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT)
  @JsonProperty(value = "firstEvaluatedOutApprovalDate")
  @gov.ca.cwds.rest.validation.Date(format = DATE_FORMAT, required = false)
  @ApiModelProperty(required = false, readOnly = false, value = "yyyy-MM-dd",
      example = "2000-01-01")
  private String firstEvaluatedOutApprovalDate;

  @NotEmpty
  @Size(min = 1, max = 1, message = "size must be 1")
  @OneOf(value = {"C", "P", "O", "A", "S", "I", "K", "M"}, ignoreCase = true,
      ignoreWhitespace = true)
  @ApiModelProperty(required = true, readOnly = false, value = "", example = "C",
      allowableValues = "C, P, O, A, S, I, K, M")
  private String responsibleAgencyCode;

  @ApiModelProperty(required = false, readOnly = false, example = "1234")
  private Short limitedAccessGovtAgencyType;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT)
  @JsonProperty(value = "limitedAccessDate")
  @gov.ca.cwds.rest.validation.Date(format = DATE_FORMAT, required = false)
  @ApiModelProperty(required = false, readOnly = false, value = "yyyy-MM-dd",
      example = "2000-01-01")
  private String limitedAccessDate;

  @Size(max = 254)
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "ABC123")
  private String limitedAccessDesc;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT)
  @JsonProperty(value = "originalClosureDate")
  @gov.ca.cwds.rest.validation.Date(format = DATE_FORMAT, required = false)
  @ApiModelProperty(required = false, readOnly = false, value = "yyyy-MM-dd",
      example = "2000-01-01")
  private String originalClosureDate;

  /**
   * 19-digit, hyphen delimited key, the so-called UI Identifier.
   */
  @Size(min = 22, max = 22)
  private String uiIdentifier;

  @JsonProperty("addresses")
  private Set<gov.ca.cwds.rest.api.domain.cms.Address> address;

  @JsonProperty("reporter")
  private Set<Reporter> reporter;

  @JsonProperty("crossReports")
  private Set<CrossReport> crossReport;

  @JsonProperty("allegation")
  private Set<Allegation> allegation;

  @JsonProperty("victimClients")
  private Set<Client> victimClient;

  @JsonProperty("perpetratorClients")
  private Set<Client> perpetratorClient;

  /**
   * @param additionalInfoIncludedCode additional Info Included Code
   * @param anonymousReporterIndicator anonymous Reporter Indicator
   * @param applicationForPetitionIndicator application For Petition Indicator
   * @param approvalNumber approval Number
   * @param approvalStatusType approval Status Type
   * @param caretakersPerpetratorCode caretakers Perpetrator Code
   * @param closureDate closure Date
   * @param communicationMethodType communication Method Type
   * @param currentLocationOfChildren current Location Of Children
   * @param drmsAllegationDescriptionDoc drms Allegation Description Doc
   * @param drmsErReferralDoc drms Er Referral Doc
   * @param drmsInvestigationDoc drms Investigation Doc
   * @param filedSuspectedChildAbuseReporttoLawEnforcementIndicator filed Suspected Child Abuse
   * @param familyAwarenessIndicator family Awareness Indicator
   * @param govtEntityType govt Entity Type
   * @param legalDefinitionCode legal Definition Code
   * @param legalRightsNoticeIndicator legal Rights Notice Indicator
   * @param limitedAccessCode limited Access Code
   * @param mandatedCrossReportReceivedDate mandated Cross Report Received Date
   * @param referralName referral Name
   * @param openAdequateCaseCode open Adequate Case Code
   * @param receivedDate received Date
   * @param receivedTime received Time
   * @param referralResponseType referral Response Type
   * @param referredToResourceType referred To Resource Type
   * @param responseDeterminationDate response Determination Date
   * @param responseDeterminationTime response Determination Time
   * @param responseRationaleText response Rationale Text
   * @param screenerNoteText screener Note Text
   * @param specificsIncludedCode specifics Included Code
   * @param sufficientInformationCode sufficient Information Code
   * @param unfoundedSeriesCode unfounded Series Code
   * @param linkToPrimaryReferralId link To Primary Referral Id
   * @param allegesAbuseOccurredAtAddressId alleges Abuse Occurred At Address Id
   * @param firstResponseDeterminedByStaffPersonId first Response Determined By Staff Person Id
   * @param primaryContactStaffPersonId primary Contact Staff Person Id
   * @param countySpecificCode county Specific Code
   * @param specialProjectReferralIndicator special Project Referral Indicator
   * @param zippyCreatedIndicator zippy Created Indicator
   * @param homelessIndicator homeless Indicator
   * @param familyRefusedServicesIndicator family Refused Services Indicator
   * @param firstEvaluatedOutApprovalDate first Evaluated Out Approval Date
   * @param responsibleAgencyCode responsible Agency Code
   * @param limitedAccessGovtAgencyType limited Access Govt Agency Type
   * @param limitedAccessDate limited Access Date
   * @param limitedAccessDesc limited Access Desc
   * @param originalClosureDate original Closure Date
   * @param uiIdentifier 19-digit, hyphen delimited UI identifier
   * @param address address
   * @param reporter reporter
   * @param crossReport crossReport
   * @param allegation allegation
   * @param victimClient victimClient
   * @param perpetratorClient perpetratorClient
   */
  @JsonCreator
  public Referral(@JsonProperty("additionalInfoIncludedCode") Boolean additionalInfoIncludedCode,
      @JsonProperty("anonymousReporterIndicator") Boolean anonymousReporterIndicator,
      @JsonProperty("applicationForPetitionIndicator") Boolean applicationForPetitionIndicator,
      @JsonProperty("approvalNumber") String approvalNumber,
      @JsonProperty("approvalStatusType") Short approvalStatusType,
      @JsonProperty("caretakersPerpetratorCode") Boolean caretakersPerpetratorCode,
      @JsonProperty("closureDate") String closureDate,
      @JsonProperty("communicationMethodType") Short communicationMethodType,
      @JsonProperty("currentLocationOfChildren") String currentLocationOfChildren,
      @JsonProperty("drmsAllegationDescriptionDoc") String drmsAllegationDescriptionDoc,
      @JsonProperty("drmsErReferralDoc") String drmsErReferralDoc,
      @JsonProperty("drmsInvestigationDoc") String drmsInvestigationDoc,
      @JsonProperty("filedSuspectedChildAbuseReporttoLawEnforcementIndicator") Boolean filedSuspectedChildAbuseReporttoLawEnforcementIndicator,
      @JsonProperty("familyAwarenessIndicator") Boolean familyAwarenessIndicator,
      @JsonProperty("govtEntityType") Short govtEntityType,
      @JsonProperty("legalDefinitionCode") String legalDefinitionCode,
      @JsonProperty("legalRightsNoticeIndicator") Boolean legalRightsNoticeIndicator,
      @JsonProperty("limitedAccessCode") String limitedAccessCode,
      @JsonProperty("mandatedCrossReportReceivedDate") String mandatedCrossReportReceivedDate,
      @JsonProperty("referralName") String referralName,
      @JsonProperty("openAdequateCaseCode") String openAdequateCaseCode,
      @JsonProperty("receivedDate") String receivedDate,
      @JsonProperty("receivedTime") String receivedTime,
      @JsonProperty("referralResponseType") Short referralResponseType,
      @JsonProperty("referredToResourceType") Short referredToResourceType,
      @JsonProperty("responseDeterminationDate") String responseDeterminationDate,
      @JsonProperty("responseDeterminationTime") String responseDeterminationTime,
      @JsonProperty("responseRationaleText") String responseRationaleText,
      @JsonProperty("screenerNoteText") String screenerNoteText,
      @JsonProperty("specificsIncludedCode") String specificsIncludedCode,
      @JsonProperty("sufficientInformationCode") String sufficientInformationCode,
      @JsonProperty("unfoundedSeriesCode") String unfoundedSeriesCode,
      @JsonProperty("linkToPrimaryReferralId") String linkToPrimaryReferralId,
      @JsonProperty("allegesAbuseOccurredAtAddressId") String allegesAbuseOccurredAtAddressId,
      @JsonProperty("firstResponseDeterminedByStaffPersonId") String firstResponseDeterminedByStaffPersonId,
      @JsonProperty("primaryContactStaffPersonId") String primaryContactStaffPersonId,
      @JsonProperty("countySpecificCode") String countySpecificCode,
      @JsonProperty("specialProjectReferralIndicator") Boolean specialProjectReferralIndicator,
      @JsonProperty("zippyCreatedIndicator") Boolean zippyCreatedIndicator,
      @JsonProperty("homelessIndicator") Boolean homelessIndicator,
      @JsonProperty("familyRefusedServicesIndicator") Boolean familyRefusedServicesIndicator,
      @JsonProperty("firstEvaluatedOutApprovalDate") String firstEvaluatedOutApprovalDate,
      @JsonProperty("responsibleAgencyCode") String responsibleAgencyCode,
      @JsonProperty("limitedAccessGovtAgencyType") Short limitedAccessGovtAgencyType,
      @JsonProperty("limitedAccessDate") String limitedAccessDate,
      @JsonProperty("limitedAccessDesc") String limitedAccessDesc,
      @JsonProperty("originalClosureDate") String originalClosureDate,
      @JsonProperty("uiIdentifier") String uiIdentifier,
      @JsonProperty("addresses") Set<gov.ca.cwds.rest.api.domain.cms.Address> address,
      @JsonProperty("reporter") Set<Reporter> reporter,
      @JsonProperty("crossReports") Set<CrossReport> crossReport,
      @JsonProperty("allegation") Set<Allegation> allegation,
      @JsonProperty("victimClients") Set<Client> victimClient,
      @JsonProperty("perpetratorClients") Set<Client> perpetratorClient) {
    super();
    this.additionalInfoIncludedCode = additionalInfoIncludedCode;
    this.anonymousReporterIndicator = anonymousReporterIndicator;
    this.applicationForPetitionIndicator = applicationForPetitionIndicator;
    this.approvalNumber = approvalNumber;
    this.approvalStatusType = approvalStatusType;
    this.caretakersPerpetratorCode = caretakersPerpetratorCode;
    this.closureDate = closureDate;
    this.communicationMethodType = communicationMethodType;
    this.currentLocationOfChildren = currentLocationOfChildren;
    this.drmsAllegationDescriptionDoc = drmsAllegationDescriptionDoc;
    this.drmsErReferralDoc = drmsErReferralDoc;
    this.drmsInvestigationDoc = drmsInvestigationDoc;
    this.filedSuspectedChildAbuseReporttoLawEnforcementIndicator =
        filedSuspectedChildAbuseReporttoLawEnforcementIndicator;
    this.familyAwarenessIndicator = familyAwarenessIndicator;
    this.govtEntityType = govtEntityType;
    this.legalDefinitionCode = legalDefinitionCode;
    this.legalRightsNoticeIndicator = legalRightsNoticeIndicator;
    this.limitedAccessCode = limitedAccessCode;
    this.mandatedCrossReportReceivedDate = mandatedCrossReportReceivedDate;
    this.referralName = referralName;
    this.openAdequateCaseCode = openAdequateCaseCode;
    this.receivedDate = receivedDate;
    this.receivedTime = receivedTime;
    this.referralResponseType = referralResponseType;
    this.referredToResourceType = referredToResourceType;
    this.responseDeterminationDate = responseDeterminationDate;
    this.responseDeterminationTime = responseDeterminationTime;
    this.responseRationaleText = responseRationaleText;
    this.screenerNoteText = screenerNoteText;
    this.specificsIncludedCode = specificsIncludedCode;
    this.sufficientInformationCode = sufficientInformationCode;
    this.unfoundedSeriesCode = unfoundedSeriesCode;
    this.linkToPrimaryReferralId = linkToPrimaryReferralId;
    this.allegesAbuseOccurredAtAddressId = allegesAbuseOccurredAtAddressId;
    this.firstResponseDeterminedByStaffPersonId = firstResponseDeterminedByStaffPersonId;
    this.primaryContactStaffPersonId = primaryContactStaffPersonId;
    this.countySpecificCode = countySpecificCode;
    this.specialProjectReferralIndicator = specialProjectReferralIndicator;
    this.zippyCreatedIndicator = zippyCreatedIndicator;
    this.homelessIndicator = homelessIndicator;
    this.familyRefusedServicesIndicator = familyRefusedServicesIndicator;
    this.firstEvaluatedOutApprovalDate = firstEvaluatedOutApprovalDate;
    this.responsibleAgencyCode = responsibleAgencyCode;
    this.limitedAccessGovtAgencyType = limitedAccessGovtAgencyType;
    this.limitedAccessDate = limitedAccessDate;
    this.limitedAccessDesc = limitedAccessDesc;
    this.originalClosureDate = originalClosureDate;
    this.address = address;
    this.reporter = reporter;
    this.crossReport = crossReport;
    this.allegation = allegation;
    this.victimClient = victimClient;
    this.perpetratorClient = perpetratorClient;
    this.uiIdentifier = uiIdentifier;
  }

  /**
   * @param persistedReferral - persistence Referral object
   */
  public Referral(gov.ca.cwds.data.persistence.cms.Referral persistedReferral) {
    this.additionalInfoIncludedCode =
        DomainChef.uncookBooleanString(persistedReferral.getAdditionalInfoIncludedCode());
    this.anonymousReporterIndicator =
        DomainChef.uncookBooleanString(persistedReferral.getAnonymousReporterIndicator());
    this.applicationForPetitionIndicator =
        DomainChef.uncookBooleanString(persistedReferral.getApplicationForPetitionIndicator());
    this.approvalNumber = persistedReferral.getApprovalNumber();
    this.approvalStatusType = persistedReferral.getApprovalStatusType();
    this.caretakersPerpetratorCode =
        DomainChef.uncookBooleanString(persistedReferral.getCaretakersPerpetratorCode());
    this.closureDate = DomainChef.cookDate(persistedReferral.getClosureDate());
    this.communicationMethodType = persistedReferral.getCommunicationMethodType();
    this.currentLocationOfChildren = persistedReferral.getCurrentLocationOfChildren();
    this.drmsAllegationDescriptionDoc = persistedReferral.getDrmsAllegationDescriptionDoc();
    this.drmsErReferralDoc = persistedReferral.getDrmsErReferralDoc();
    this.drmsInvestigationDoc = persistedReferral.getDrmsInvestigationDoc();
    this.filedSuspectedChildAbuseReporttoLawEnforcementIndicator = DomainChef.uncookBooleanString(
        persistedReferral.getFiledSuspectedChildAbuseReporttoLawEnforcementIndicator());
    this.familyAwarenessIndicator =
        DomainChef.uncookBooleanString(persistedReferral.getFamilyAwarenessIndicator());
    this.govtEntityType = persistedReferral.getGovtEntityType();
    this.legalDefinitionCode = persistedReferral.getLegalDefinitionCode();
    this.legalRightsNoticeIndicator =
        DomainChef.uncookBooleanString(persistedReferral.getLegalRightsNoticeIndicator());
    this.limitedAccessCode = persistedReferral.getLimitedAccessCode();
    this.mandatedCrossReportReceivedDate =
        DomainChef.cookDate(persistedReferral.getMandatedCrossReportReceivedDate());
    this.referralName = persistedReferral.getReferralName();
    this.openAdequateCaseCode = persistedReferral.getOpenAdequateCaseCode();
    this.receivedDate = DomainChef.cookDate(persistedReferral.getReceivedDate());
    this.receivedTime = DomainChef.cookTime(persistedReferral.getReceivedTime());
    this.referralResponseType = persistedReferral.getReferralResponseType();
    this.referredToResourceType = persistedReferral.getReferredToResourceType();
    this.responseDeterminationDate =
        DomainChef.cookDate(persistedReferral.getResponseDeterminationDate());
    this.responseDeterminationTime =
        DomainChef.cookTime(persistedReferral.getResponseDeterminationTime());
    this.responseRationaleText = persistedReferral.getResponseRationaleText();
    this.screenerNoteText = persistedReferral.getScreenerNoteText();
    this.specificsIncludedCode = persistedReferral.getSpecificsIncludedCode();
    this.sufficientInformationCode = persistedReferral.getSufficientInformationCode();
    this.unfoundedSeriesCode = persistedReferral.getUnfoundedSeriesCode();
    this.linkToPrimaryReferralId = persistedReferral.getLinkToPrimaryReferralId();
    this.allegesAbuseOccurredAtAddressId = persistedReferral.getAllegesAbuseOccurredAtAddressId();
    this.firstResponseDeterminedByStaffPersonId =
        persistedReferral.getFirstResponseDeterminedByStaffPersonId();
    this.primaryContactStaffPersonId = persistedReferral.getPrimaryContactStaffPersonId();
    this.countySpecificCode = persistedReferral.getCountySpecificCode();
    this.specialProjectReferralIndicator =
        DomainChef.uncookBooleanString(persistedReferral.getSpecialProjectReferralIndicator());
    this.zippyCreatedIndicator =
        DomainChef.uncookBooleanString(persistedReferral.getZippyCreatedIndicator());
    this.homelessIndicator =
        DomainChef.uncookBooleanString(persistedReferral.getHomelessIndicator());
    this.familyRefusedServicesIndicator =
        DomainChef.uncookBooleanString(persistedReferral.getFamilyRefusedServicesIndicator());
    this.firstEvaluatedOutApprovalDate =
        DomainChef.cookDate(persistedReferral.getFirstEvaluatedOutApprovalDate());
    this.responsibleAgencyCode = persistedReferral.getResponsibleAgencyCode();
    this.limitedAccessGovtAgencyType = persistedReferral.getLimitedAccessGovtAgencyType();
    this.limitedAccessDate = DomainChef.cookDate(persistedReferral.getLimitedAccessDate());
    this.limitedAccessDesc = persistedReferral.getLimitedAccessDesc();
    this.originalClosureDate = DomainChef.cookDate(persistedReferral.getOriginalClosureDate());

    // #145948067: convert legacy id to UI identifier.
    this.uiIdentifier = legacyIdToUIIdentifier(persistedReferral.getId());

    this.address = new HashSet<>();
    if (persistedReferral.getAddresses() != null && !persistedReferral.getAddresses().isEmpty()) {
      for (gov.ca.cwds.data.persistence.cms.Address persistenceAddress : persistedReferral
          .getAddresses()) {
        this.address.add(new Address(persistenceAddress, true));
      }
    }
    this.reporter = new HashSet<>();
    if (persistedReferral.getReporters() != null && !persistedReferral.getReporters().isEmpty()) {
      for (gov.ca.cwds.data.persistence.cms.Reporter persistedReporter : persistedReferral
          .getReporters()) {
        this.reporter.add(new Reporter(persistedReporter));
      }
    }
    this.crossReport = new HashSet<>();
    if (persistedReferral.getCrossReports() != null
        && !persistedReferral.getCrossReports().isEmpty()) {
      for (gov.ca.cwds.data.persistence.cms.CrossReport persistedCrossReport : persistedReferral
          .getCrossReports()) {
        this.crossReport.add(new CrossReport(persistedCrossReport));
      }
    }
    this.allegation = new HashSet<>();
    this.victimClient = new HashSet<>();
    this.perpetratorClient = new HashSet<>();
    if (persistedReferral.getAllegations() != null
        && !persistedReferral.getAllegations().isEmpty()) {
      for (gov.ca.cwds.data.persistence.cms.Allegation persistedAllegation : persistedReferral
          .getAllegations()) {
        this.allegation.add(new Allegation(persistedAllegation));

        if (persistedAllegation.getVictimClients() != null
            && !persistedAllegation.getVictimClients().isEmpty()) {
          for (gov.ca.cwds.data.persistence.cms.Client persistentClient : persistedAllegation
              .getVictimClients()) {
            this.victimClient.add(new Client(persistentClient, true));
          }
        }
        if (persistedAllegation.getPerpetratorClients() != null
            && !persistedAllegation.getPerpetratorClients().isEmpty()) {
          for (gov.ca.cwds.data.persistence.cms.Client persistentPerpetratorClient : persistedAllegation
              .getPerpetratorClients()) {
            this.perpetratorClient.add(new Client(persistentPerpetratorClient, true));
          }
        }
      }
    }
  }

  /**
   * Construct an empty, minimal Referral object.
   * 
   * @param anonymousReporter - anonymousReporter
   * @param communicationsMethodCode - communicationsMethodCode
   * @param referalName - referalName
   * @param dateStarted - dateStarted
   * @param timeStarted - timeStarted
   * @param referralResponseTypeCode - referralResponseTypeCode
   * @param allegesAbuseOccurredAtAddressId - allegesAbuseOccurredAtAddressId
   * @param firstResponseDeterminedByStaffPersonId - firstResponseDeterminedByStaffPersonId
   * @param longTextId - longTextId
   * @param countyCode - countyCode
   * @param approvalCode - approvalCode
   * @param staffId - staffId
   * @return the referral
   */
  public static Referral createWithDefaults(Boolean anonymousReporter,
      short communicationsMethodCode, String referalName, String dateStarted, String timeStarted,
      short referralResponseTypeCode, String allegesAbuseOccurredAtAddressId,
      String firstResponseDeterminedByStaffPersonId, String longTextId, String countyCode,
      short approvalCode, String staffId) {
    return new Referral(false, anonymousReporter, false, "", approvalCode, false, "",
        communicationsMethodCode, "", "", "", "", false, false, DEFAULT_CODE, DEFAULT_NO, false,
        DEFAULT_LIMITIED_ACCESS_CODE, "", referalName, "", dateStarted, timeStarted,
        referralResponseTypeCode, DEFAULT_CODE, "", "", "", longTextId, DEFAULT_NO, DEFAULT_NO,
        DEFAULT_NO, "", allegesAbuseOccurredAtAddressId, firstResponseDeterminedByStaffPersonId,
        staffId, countyCode, false, false, false, false, "", DEFAULT_RESPONSIBLE_AGENCY_CODE,
        DEFAULT_CODE, "", "", "", null, null, null, null, null, null, null);
  }

  /**
   * Story #145948067: convert legacy id to UI identifier.
   * 
   * @param key key to convert
   * @return 19-digit, hyphen delimited UI identifier.
   */
  protected String legacyIdToUIIdentifier(String key) {
    String ret = null;
    if (StringUtils.isNotBlank(key)) {
      ret = CmsKeyIdGenerator.getUIIdentifierFromKey(key.trim());
    }

    return ret;
  }

  /**
   * @return the additionalInfoIncludedCode
   */
  public Boolean getAdditionalInfoIncludedCode() {
    return additionalInfoIncludedCode;
  }

  /**
   * @return the anonymousReporterIndicator
   */
  public Boolean getAnonymousReporterIndicator() {
    return anonymousReporterIndicator;
  }

  /**
   * @return the applicationForPetitionIndicator
   */
  public Boolean getApplicationForPetitionIndicator() {
    return applicationForPetitionIndicator;
  }

  /**
   * @return the approvalNumber
   */
  public String getApprovalNumber() {
    return approvalNumber;
  }

  /**
   * @return the approvalStatusType
   */
  public Short getApprovalStatusType() {
    return approvalStatusType;
  }

  /**
   * @return the caretakersPerpetratorCode
   */
  public Boolean getCaretakersPerpetratorCode() {
    return caretakersPerpetratorCode;
  }

  /**
   * @return the closureDate
   */
  public String getClosureDate() {
    return closureDate;
  }

  /**
   * @return the communicationMethodType
   */
  public Short getCommunicationMethodType() {
    return communicationMethodType;
  }

  /**
   * @return the currentLocationOfChildren
   */
  public String getCurrentLocationOfChildren() {
    return currentLocationOfChildren;
  }

  /**
   * @return the drmsAllegationDescriptionDoc
   */
  public String getDrmsAllegationDescriptionDoc() {
    return drmsAllegationDescriptionDoc;
  }

  /**
   * @return the drmsErReferralDoc
   */
  public String getDrmsErReferralDoc() {
    return drmsErReferralDoc;
  }

  /**
   * @return the drmsInvestigationDoc
   */
  public String getDrmsInvestigationDoc() {
    return drmsInvestigationDoc;
  }

  /**
   * @return the filedSuspectedChildAbuseReporttoLawEnforcementIndicator
   */
  public Boolean getFiledSuspectedChildAbuseReporttoLawEnforcementIndicator() {
    return filedSuspectedChildAbuseReporttoLawEnforcementIndicator;
  }

  /**
   * @return the familyAwarenessIndicator
   */
  public Boolean getFamilyAwarenessIndicator() {
    return familyAwarenessIndicator;
  }

  /**
   * @return the govtEntityType
   */
  public Short getGovtEntityType() {
    return govtEntityType;
  }

  /**
   * @return the legalDefinitionCode
   */
  public String getLegalDefinitionCode() {
    return legalDefinitionCode;
  }

  /**
   * @return the legalRightsNoticeIndicator
   */
  public Boolean getLegalRightsNoticeIndicator() {
    return legalRightsNoticeIndicator;
  }

  /**
   * @return the limitedAccessCode
   */
  public String getLimitedAccessCode() {
    return limitedAccessCode;
  }

  /**
   * @return the mandatedCrossReportReceivedDate
   */
  public String getMandatedCrossReportReceivedDate() {
    return mandatedCrossReportReceivedDate;
  }

  /**
   * @return the referralName
   */
  public String getReferralName() {
    return referralName;
  }

  /**
   * @return the openAdequateCaseCode
   */
  public String getOpenAdequateCaseCode() {
    return openAdequateCaseCode;
  }

  /**
   * @return the receivedDate
   */
  public String getReceivedDate() {
    return receivedDate;
  }

  /**
   * @return the receivedTime
   */
  public String getReceivedTime() {
    return receivedTime;
  }

  /**
   * @return the referralResponseType
   */
  public Short getReferralResponseType() {
    return referralResponseType;
  }

  /**
   * @return the referredToResourceType
   */
  public Short getReferredToResourceType() {
    return referredToResourceType;
  }

  /**
   * @return the responseDeterminationDate
   */
  public String getResponseDeterminationDate() {
    return responseDeterminationDate;
  }

  /**
   * @return the responseDeterminationTime
   */
  public String getResponseDeterminationTime() {
    return responseDeterminationTime;
  }

  /**
   * @return the responseRationaleText
   */
  public String getResponseRationaleText() {
    return responseRationaleText;
  }

  /**
   * @return the screenerNoteText
   */
  public String getScreenerNoteText() {
    return screenerNoteText;
  }

  /**
   * @return the specificsIncludedCode
   */
  public String getSpecificsIncludedCode() {
    return specificsIncludedCode;
  }

  /**
   * @return the sufficientInformationCode
   */
  public String getSufficientInformationCode() {
    return sufficientInformationCode;
  }

  /**
   * @return the unfoundedSeriesCode
   */
  public String getUnfoundedSeriesCode() {
    return unfoundedSeriesCode;
  }

  /**
   * @return the linkToPrimaryReferralId
   */
  public String getLinkToPrimaryReferralId() {
    return linkToPrimaryReferralId;
  }

  /**
   * @return the allegesAbuseOccurredAtAddressId
   */
  public String getAllegesAbuseOccurredAtAddressId() {
    return allegesAbuseOccurredAtAddressId;
  }

  /**
   * @return the firstResponseDeterminedByStaffPersonId
   */
  public String getFirstResponseDeterminedByStaffPersonId() {
    return firstResponseDeterminedByStaffPersonId;
  }

  /**
   * @return the primaryContactStaffPersonId
   */
  public String getPrimaryContactStaffPersonId() {
    return primaryContactStaffPersonId;
  }

  /**
   * @return the countySpecificCode
   */
  public String getCountySpecificCode() {
    return countySpecificCode;
  }

  /**
   * @return the specialProjectReferralIndicator
   */
  public Boolean getSpecialProjectReferralIndicator() {
    return specialProjectReferralIndicator;
  }

  /**
   * @return the zippyCreatedIndicator
   */
  public Boolean getZippyCreatedIndicator() {
    return zippyCreatedIndicator;
  }

  /**
   * @return the homelessIndicator
   */
  public Boolean getHomelessIndicator() {
    return homelessIndicator;
  }

  /**
   * @return the familyRefusedServicesIndicator
   */
  public Boolean getFamilyRefusedServicesIndicator() {
    return familyRefusedServicesIndicator;
  }

  /**
   * @return the firstEvaluatedOutApprovalDate
   */
  public String getFirstEvaluatedOutApprovalDate() {
    return firstEvaluatedOutApprovalDate;
  }

  /**
   * @return the responsibleAgencyCode
   */
  public String getResponsibleAgencyCode() {
    return responsibleAgencyCode;
  }

  /**
   * @return the limitedAccessGovtAgencyType
   */
  public Short getLimitedAccessGovtAgencyType() {
    return limitedAccessGovtAgencyType;
  }

  /**
   * @return the limitedAccessDate
   */
  public String getLimitedAccessDate() {
    return limitedAccessDate;
  }

  /**
   * @return the limitedAccessDesc
   */
  public String getLimitedAccessDesc() {
    return limitedAccessDesc;
  }

  /**
   * @return the originalClosureDate
   */
  public String getOriginalClosureDate() {
    return originalClosureDate;
  }

  /**
   * @return the referral Address
   */
  public Set<gov.ca.cwds.rest.api.domain.cms.Address> getAddress() {
    return address;
  }

  /**
   * @return the reporter
   */
  public Set<Reporter> getReporter() {
    return reporter;
  }

  /**
   * @return the crossReports
   */
  public Set<CrossReport> getCrossReport() {
    return crossReport;
  }

  /**
   * @return the allegation
   */
  public Set<Allegation> getAllegation() {
    return allegation;
  }

  /**
   * @return the victimClient
   */
  public Set<Client> getVictimClient() {
    return victimClient;
  }

  /**
   * @return the perpetratorClient
   */
  public Set<Client> getPerpetratorClient() {
    return perpetratorClient;
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.lang.Object#hashCode()
   */
  @Override
  public final int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result
        + ((additionalInfoIncludedCode == null) ? 0 : additionalInfoIncludedCode.hashCode());
    result = prime * result + ((address == null) ? 0 : address.hashCode());
    result = prime * result + ((allegation == null) ? 0 : allegation.hashCode());
    result = prime * result + ((allegesAbuseOccurredAtAddressId == null) ? 0
        : allegesAbuseOccurredAtAddressId.hashCode());
    result = prime * result
        + ((anonymousReporterIndicator == null) ? 0 : anonymousReporterIndicator.hashCode());
    result = prime * result + ((applicationForPetitionIndicator == null) ? 0
        : applicationForPetitionIndicator.hashCode());
    result = prime * result + ((approvalNumber == null) ? 0 : approvalNumber.hashCode());
    result = prime * result + ((approvalStatusType == null) ? 0 : approvalStatusType.hashCode());
    result = prime * result
        + ((caretakersPerpetratorCode == null) ? 0 : caretakersPerpetratorCode.hashCode());
    result = prime * result + ((closureDate == null) ? 0 : closureDate.hashCode());
    result = prime * result
        + ((communicationMethodType == null) ? 0 : communicationMethodType.hashCode());
    result = prime * result + ((countySpecificCode == null) ? 0 : countySpecificCode.hashCode());
    result = prime * result + ((crossReport == null) ? 0 : crossReport.hashCode());
    result = prime * result
        + ((currentLocationOfChildren == null) ? 0 : currentLocationOfChildren.hashCode());
    result = prime * result
        + ((drmsAllegationDescriptionDoc == null) ? 0 : drmsAllegationDescriptionDoc.hashCode());
    result = prime * result + ((drmsErReferralDoc == null) ? 0 : drmsErReferralDoc.hashCode());
    result =
        prime * result + ((drmsInvestigationDoc == null) ? 0 : drmsInvestigationDoc.hashCode());
    result = prime * result
        + ((familyAwarenessIndicator == null) ? 0 : familyAwarenessIndicator.hashCode());
    result = prime * result + ((familyRefusedServicesIndicator == null) ? 0
        : familyRefusedServicesIndicator.hashCode());
    result = prime * result + ((filedSuspectedChildAbuseReporttoLawEnforcementIndicator == null) ? 0
        : filedSuspectedChildAbuseReporttoLawEnforcementIndicator.hashCode());
    result = prime * result
        + ((firstEvaluatedOutApprovalDate == null) ? 0 : firstEvaluatedOutApprovalDate.hashCode());
    result = prime * result + ((firstResponseDeterminedByStaffPersonId == null) ? 0
        : firstResponseDeterminedByStaffPersonId.hashCode());
    result = prime * result + ((govtEntityType == null) ? 0 : govtEntityType.hashCode());
    result = prime * result + ((homelessIndicator == null) ? 0 : homelessIndicator.hashCode());
    result = prime * result + ((legalDefinitionCode == null) ? 0 : legalDefinitionCode.hashCode());
    result = prime * result
        + ((legalRightsNoticeIndicator == null) ? 0 : legalRightsNoticeIndicator.hashCode());
    result = prime * result + ((limitedAccessCode == null) ? 0 : limitedAccessCode.hashCode());
    result = prime * result + ((limitedAccessDate == null) ? 0 : limitedAccessDate.hashCode());
    result = prime * result + ((limitedAccessDesc == null) ? 0 : limitedAccessDesc.hashCode());
    result = prime * result
        + ((limitedAccessGovtAgencyType == null) ? 0 : limitedAccessGovtAgencyType.hashCode());
    result = prime * result
        + ((linkToPrimaryReferralId == null) ? 0 : linkToPrimaryReferralId.hashCode());
    result = prime * result + ((mandatedCrossReportReceivedDate == null) ? 0
        : mandatedCrossReportReceivedDate.hashCode());
    result =
        prime * result + ((openAdequateCaseCode == null) ? 0 : openAdequateCaseCode.hashCode());
    result = prime * result + ((originalClosureDate == null) ? 0 : originalClosureDate.hashCode());
    result = prime * result + ((perpetratorClient == null) ? 0 : perpetratorClient.hashCode());
    result = prime * result
        + ((primaryContactStaffPersonId == null) ? 0 : primaryContactStaffPersonId.hashCode());
    result = prime * result + ((receivedDate == null) ? 0 : receivedDate.hashCode());
    result = prime * result + ((receivedTime == null) ? 0 : receivedTime.hashCode());
    result = prime * result + ((referralName == null) ? 0 : referralName.hashCode());
    result =
        prime * result + ((referralResponseType == null) ? 0 : referralResponseType.hashCode());
    result =
        prime * result + ((referredToResourceType == null) ? 0 : referredToResourceType.hashCode());
    result = prime * result + ((reporter == null) ? 0 : reporter.hashCode());
    result = prime * result
        + ((responseDeterminationDate == null) ? 0 : responseDeterminationDate.hashCode());
    result = prime * result
        + ((responseDeterminationTime == null) ? 0 : responseDeterminationTime.hashCode());
    result =
        prime * result + ((responseRationaleText == null) ? 0 : responseRationaleText.hashCode());
    result =
        prime * result + ((responsibleAgencyCode == null) ? 0 : responsibleAgencyCode.hashCode());
    result = prime * result + ((screenerNoteText == null) ? 0 : screenerNoteText.hashCode());
    result = prime * result + ((specialProjectReferralIndicator == null) ? 0
        : specialProjectReferralIndicator.hashCode());
    result =
        prime * result + ((specificsIncludedCode == null) ? 0 : specificsIncludedCode.hashCode());
    result = prime * result
        + ((sufficientInformationCode == null) ? 0 : sufficientInformationCode.hashCode());
    result = prime * result + ((unfoundedSeriesCode == null) ? 0 : unfoundedSeriesCode.hashCode());
    result = prime * result + ((victimClient == null) ? 0 : victimClient.hashCode());
    result =
        prime * result + ((zippyCreatedIndicator == null) ? 0 : zippyCreatedIndicator.hashCode());
    result = prime * result + ((uiIdentifier == null) ? 0 : uiIdentifier.hashCode());
    return result;
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public final boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (!(obj instanceof Referral)) {
      return false;
    }
    Referral other = (Referral) obj;
    if (additionalInfoIncludedCode == null) {
      if (other.additionalInfoIncludedCode != null)
        return false;
    } else if (!additionalInfoIncludedCode.equals(other.additionalInfoIncludedCode))
      return false;
    if (address == null) {
      if (other.address != null)
        return false;
    } else if (!address.equals(other.address))
      return false;
    if (allegation == null) {
      if (other.allegation != null)
        return false;
    } else if (!allegation.equals(other.allegation))
      return false;
    if (allegesAbuseOccurredAtAddressId == null) {
      if (other.allegesAbuseOccurredAtAddressId != null)
        return false;
    } else if (!allegesAbuseOccurredAtAddressId.equals(other.allegesAbuseOccurredAtAddressId))
      return false;
    if (anonymousReporterIndicator == null) {
      if (other.anonymousReporterIndicator != null)
        return false;
    } else if (!anonymousReporterIndicator.equals(other.anonymousReporterIndicator))
      return false;
    if (applicationForPetitionIndicator == null) {
      if (other.applicationForPetitionIndicator != null)
        return false;
    } else if (!applicationForPetitionIndicator.equals(other.applicationForPetitionIndicator))
      return false;
    if (approvalNumber == null) {
      if (other.approvalNumber != null)
        return false;
    } else if (!approvalNumber.equals(other.approvalNumber))
      return false;
    if (approvalStatusType == null) {
      if (other.approvalStatusType != null)
        return false;
    } else if (!approvalStatusType.equals(other.approvalStatusType))
      return false;
    if (caretakersPerpetratorCode == null) {
      if (other.caretakersPerpetratorCode != null)
        return false;
    } else if (!caretakersPerpetratorCode.equals(other.caretakersPerpetratorCode))
      return false;
    if (closureDate == null) {
      if (other.closureDate != null)
        return false;
    } else if (!closureDate.equals(other.closureDate))
      return false;
    if (communicationMethodType == null) {
      if (other.communicationMethodType != null)
        return false;
    } else if (!communicationMethodType.equals(other.communicationMethodType))
      return false;
    if (countySpecificCode == null) {
      if (other.countySpecificCode != null)
        return false;
    } else if (!countySpecificCode.equals(other.countySpecificCode))
      return false;
    if (crossReport == null) {
      if (other.crossReport != null)
        return false;
    } else if (!crossReport.equals(other.crossReport))
      return false;
    if (currentLocationOfChildren == null) {
      if (other.currentLocationOfChildren != null)
        return false;
    } else if (!currentLocationOfChildren.equals(other.currentLocationOfChildren))
      return false;
    if (drmsAllegationDescriptionDoc == null) {
      if (other.drmsAllegationDescriptionDoc != null)
        return false;
    } else if (!drmsAllegationDescriptionDoc.equals(other.drmsAllegationDescriptionDoc))
      return false;
    if (drmsErReferralDoc == null) {
      if (other.drmsErReferralDoc != null)
        return false;
    } else if (!drmsErReferralDoc.equals(other.drmsErReferralDoc))
      return false;
    if (drmsInvestigationDoc == null) {
      if (other.drmsInvestigationDoc != null)
        return false;
    } else if (!drmsInvestigationDoc.equals(other.drmsInvestigationDoc))
      return false;
    if (familyAwarenessIndicator == null) {
      if (other.familyAwarenessIndicator != null)
        return false;
    } else if (!familyAwarenessIndicator.equals(other.familyAwarenessIndicator))
      return false;
    if (familyRefusedServicesIndicator == null) {
      if (other.familyRefusedServicesIndicator != null)
        return false;
    } else if (!familyRefusedServicesIndicator.equals(other.familyRefusedServicesIndicator))
      return false;
    if (filedSuspectedChildAbuseReporttoLawEnforcementIndicator == null) {
      if (other.filedSuspectedChildAbuseReporttoLawEnforcementIndicator != null)
        return false;
    } else if (!filedSuspectedChildAbuseReporttoLawEnforcementIndicator
        .equals(other.filedSuspectedChildAbuseReporttoLawEnforcementIndicator))
      return false;
    if (firstEvaluatedOutApprovalDate == null) {
      if (other.firstEvaluatedOutApprovalDate != null)
        return false;
    } else if (!firstEvaluatedOutApprovalDate.equals(other.firstEvaluatedOutApprovalDate))
      return false;
    if (firstResponseDeterminedByStaffPersonId == null) {
      if (other.firstResponseDeterminedByStaffPersonId != null)
        return false;
    } else if (!firstResponseDeterminedByStaffPersonId
        .equals(other.firstResponseDeterminedByStaffPersonId))
      return false;
    if (govtEntityType == null) {
      if (other.govtEntityType != null)
        return false;
    } else if (!govtEntityType.equals(other.govtEntityType))
      return false;
    if (homelessIndicator == null) {
      if (other.homelessIndicator != null)
        return false;
    } else if (!homelessIndicator.equals(other.homelessIndicator))
      return false;
    if (legalDefinitionCode == null) {
      if (other.legalDefinitionCode != null)
        return false;
    } else if (!legalDefinitionCode.equals(other.legalDefinitionCode))
      return false;
    if (legalRightsNoticeIndicator == null) {
      if (other.legalRightsNoticeIndicator != null)
        return false;
    } else if (!legalRightsNoticeIndicator.equals(other.legalRightsNoticeIndicator))
      return false;
    if (limitedAccessCode == null) {
      if (other.limitedAccessCode != null)
        return false;
    } else if (!limitedAccessCode.equals(other.limitedAccessCode))
      return false;
    if (limitedAccessDate == null) {
      if (other.limitedAccessDate != null)
        return false;
    } else if (!limitedAccessDate.equals(other.limitedAccessDate))
      return false;
    if (limitedAccessDesc == null) {
      if (other.limitedAccessDesc != null)
        return false;
    } else if (!limitedAccessDesc.equals(other.limitedAccessDesc))
      return false;
    if (limitedAccessGovtAgencyType == null) {
      if (other.limitedAccessGovtAgencyType != null)
        return false;
    } else if (!limitedAccessGovtAgencyType.equals(other.limitedAccessGovtAgencyType))
      return false;
    if (linkToPrimaryReferralId == null) {
      if (other.linkToPrimaryReferralId != null)
        return false;
    } else if (!linkToPrimaryReferralId.equals(other.linkToPrimaryReferralId))
      return false;
    if (mandatedCrossReportReceivedDate == null) {
      if (other.mandatedCrossReportReceivedDate != null)
        return false;
    } else if (!mandatedCrossReportReceivedDate.equals(other.mandatedCrossReportReceivedDate))
      return false;
    if (openAdequateCaseCode == null) {
      if (other.openAdequateCaseCode != null)
        return false;
    } else if (!openAdequateCaseCode.equals(other.openAdequateCaseCode))
      return false;
    if (originalClosureDate == null) {
      if (other.originalClosureDate != null)
        return false;
    } else if (!originalClosureDate.equals(other.originalClosureDate))
      return false;
    if (perpetratorClient == null) {
      if (other.perpetratorClient != null)
        return false;
    } else if (!perpetratorClient.equals(other.perpetratorClient))
      return false;
    if (primaryContactStaffPersonId == null) {
      if (other.primaryContactStaffPersonId != null)
        return false;
    } else if (!primaryContactStaffPersonId.equals(other.primaryContactStaffPersonId))
      return false;
    if (receivedDate == null) {
      if (other.receivedDate != null)
        return false;
    } else if (!receivedDate.equals(other.receivedDate))
      return false;
    if (receivedTime == null) {
      if (other.receivedTime != null)
        return false;
    } else if (!receivedTime.equals(other.receivedTime))
      return false;
    if (referralName == null) {
      if (other.referralName != null)
        return false;
    } else if (!referralName.equals(other.referralName))
      return false;
    if (referralResponseType == null) {
      if (other.referralResponseType != null)
        return false;
    } else if (!referralResponseType.equals(other.referralResponseType))
      return false;
    if (referredToResourceType == null) {
      if (other.referredToResourceType != null)
        return false;
    } else if (!referredToResourceType.equals(other.referredToResourceType))
      return false;
    if (reporter == null) {
      if (other.reporter != null)
        return false;
    } else if (!reporter.equals(other.reporter))
      return false;
    if (responseDeterminationDate == null) {
      if (other.responseDeterminationDate != null)
        return false;
    } else if (!responseDeterminationDate.equals(other.responseDeterminationDate))
      return false;
    if (responseDeterminationTime == null) {
      if (other.responseDeterminationTime != null)
        return false;
    } else if (!responseDeterminationTime.equals(other.responseDeterminationTime))
      return false;
    if (responseRationaleText == null) {
      if (other.responseRationaleText != null)
        return false;
    } else if (!responseRationaleText.equals(other.responseRationaleText))
      return false;
    if (responsibleAgencyCode == null) {
      if (other.responsibleAgencyCode != null)
        return false;
    } else if (!responsibleAgencyCode.equals(other.responsibleAgencyCode))
      return false;
    if (screenerNoteText == null) {
      if (other.screenerNoteText != null)
        return false;
    } else if (!screenerNoteText.equals(other.screenerNoteText))
      return false;
    if (specialProjectReferralIndicator == null) {
      if (other.specialProjectReferralIndicator != null)
        return false;
    } else if (!specialProjectReferralIndicator.equals(other.specialProjectReferralIndicator))
      return false;
    if (specificsIncludedCode == null) {
      if (other.specificsIncludedCode != null)
        return false;
    } else if (!specificsIncludedCode.equals(other.specificsIncludedCode))
      return false;
    if (sufficientInformationCode == null) {
      if (other.sufficientInformationCode != null)
        return false;
    } else if (!sufficientInformationCode.equals(other.sufficientInformationCode))
      return false;
    if (unfoundedSeriesCode == null) {
      if (other.unfoundedSeriesCode != null)
        return false;
    } else if (!unfoundedSeriesCode.equals(other.unfoundedSeriesCode))
      return false;
    if (victimClient == null) {
      if (other.victimClient != null)
        return false;
    } else if (!victimClient.equals(other.victimClient))
      return false;

    if (zippyCreatedIndicator == null) {
      if (other.zippyCreatedIndicator != null)
        return false;
    } else if (!zippyCreatedIndicator.equals(other.zippyCreatedIndicator))
      return false;

    if (uiIdentifier == null) {
      if (other.uiIdentifier != null)
        return false;
    } else if (!uiIdentifier.equals(other.uiIdentifier))
      return false;

    return true;
  }

  public String getUiIdentifier() {
    return uiIdentifier;
  }

  public void setUiIdentifier(String uiIdentifier) {
    this.uiIdentifier = uiIdentifier;
  }

}
