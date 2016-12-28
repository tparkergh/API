package gov.ca.cwds.rest.api.persistence.cms;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.junit.Test;

import gov.ca.cwds.rest.api.domain.DomainObject;

public class ReferralTest {
  private final static DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
  @SuppressWarnings("unused")
  private final static DateFormat tf = new SimpleDateFormat("yyyy-MM-dd-HH.mm.ss.SSS");
  private final static DateFormat timeOnlyFormat = new SimpleDateFormat("HH:mm:ss");
  private String id = "a";
  private Boolean additionalInfoIncludedCode = Boolean.TRUE;
  private Boolean anonymousReporterIndicator = Boolean.TRUE;
  private Boolean applicationForPetitionIndicator = Boolean.FALSE;
  private String approvalNumber = "c";
  private Short approvalStatusType = 1;
  private Boolean caretakersPerpetratorCode = Boolean.FALSE;
  private String closureDate = "1991-06-06";
  private Short communicationMethodType = 2;
  private String currentLocationOfChildren = "e";
  private String drmsAllegationDescriptionDoc = "f";
  private String drmsErReferralDoc = "g";
  private String drmsInvestigationDoc = "h";
  private Boolean filedSuspectedChildAbuseReporttoLawEnforcementIndicator = Boolean.TRUE;
  private Boolean familyAwarenessIndicator = Boolean.FALSE;
  private Short govtEntityType = 3;
  private String legalDefinitionCode = "i";
  private Boolean legalRightsNoticeIndicator = Boolean.FALSE;
  private String limitedAccessCode = "j";
  private String mandatedCrossReportReceivedDate = "2001-01-01";
  private String referralName = "k";
  private String openAdequateCaseCode = "l";
  private String receivedDate = "2010-06-30";
  private String receivedTime = "14:46:00";
  private Short referralResponseType = 4;
  private Short referredToResourceType = 5;
  private String responseDeterminationDate = "1985-09-04";
  private String responseDeterminationTime = "14:46:00";
  private String responseRationaleText = "m";
  private String screenerNoteText = "n";
  private String specificsIncludedCode = "o";
  private String sufficientInformationCode = "p";
  private String unfoundedSeriesCode = "q";
  private String linkToPrimaryReferralId = "r";
  private String allegesAbuseOccurredAtAddressId = "s";
  private String firstResponseDeterminedByStaffPersonId = "t";
  private String primaryContactStaffPersonId = "u";
  private String countySpecificCode = "v";
  private Boolean specialProjectReferralIndicator = Boolean.TRUE;
  private Boolean zippyCreatedIndicator = Boolean.FALSE;
  private Boolean homelessIndicator = Boolean.TRUE;
  private Boolean familyRefusedServicesIndicator = Boolean.FALSE;
  private String firstEvaluatedOutApprovalDate = "1995-07-31";
  private String responsibleAgencyCode = "w";
  private Short limitedAccessGovtAgencyType = 6;
  private String limitedAccessDate = "2001-01-01";
  private String limitedAccessDesc = "x";
  private String originalClosureDate = "1946-02-09";
  private String lastUpdatedId = "z";

  /*
   * Constructor test
   */
  @Test
  public void emtpyConstructorIsNotNull() throws Exception {
    assertThat(Referral.class.newInstance(), is(notNullValue()));
  }

  @Test
  public void domainReferralLastUpdateConstructorTest() throws Exception {
    gov.ca.cwds.rest.api.domain.cms.Referral domain = new gov.ca.cwds.rest.api.domain.cms.Referral(
        additionalInfoIncludedCode, anonymousReporterIndicator, applicationForPetitionIndicator,
        approvalNumber, approvalStatusType, caretakersPerpetratorCode, closureDate,
        communicationMethodType, currentLocationOfChildren, drmsAllegationDescriptionDoc,
        drmsErReferralDoc, drmsInvestigationDoc,
        filedSuspectedChildAbuseReporttoLawEnforcementIndicator, familyAwarenessIndicator,
        govtEntityType, legalDefinitionCode, legalRightsNoticeIndicator, limitedAccessCode,
        mandatedCrossReportReceivedDate, referralName, openAdequateCaseCode, receivedDate,
        receivedTime, referralResponseType, referredToResourceType, responseDeterminationDate,
        responseDeterminationTime, responseRationaleText, screenerNoteText, specificsIncludedCode,
        sufficientInformationCode, unfoundedSeriesCode, linkToPrimaryReferralId,
        allegesAbuseOccurredAtAddressId, firstResponseDeterminedByStaffPersonId,
        primaryContactStaffPersonId, countySpecificCode, specialProjectReferralIndicator,
        zippyCreatedIndicator, homelessIndicator, familyRefusedServicesIndicator,
        firstEvaluatedOutApprovalDate, responsibleAgencyCode, limitedAccessGovtAgencyType,
        limitedAccessDate, limitedAccessDesc, originalClosureDate);

    Referral persistent = new Referral(id, domain, "z");
    assertThat(persistent.getId(), is(equalTo(id)));
    assertThat(DomainObject.uncookBooleanString(persistent.getAdditionalInfoIncludedCode()),
        is(equalTo(additionalInfoIncludedCode)));
    assertThat(persistent.getAnonymousReporterIndicator(), is(equalTo("Y")));
    assertThat(persistent.getApplicationForPetitionIndicator(), is(equalTo("N")));
    assertThat(persistent.getApprovalNumber(), is(equalTo(approvalNumber)));
    assertThat(persistent.getApprovalStatusType(), is(equalTo(approvalStatusType)));
    assertThat(DomainObject.uncookBooleanString(persistent.getCaretakersPerpetratorCode()),
        is(equalTo(caretakersPerpetratorCode)));
    assertThat(persistent.getClosureDate(), is(equalTo(df.parse(closureDate))));
    assertThat(persistent.getCommunicationMethodType(), is(equalTo(communicationMethodType)));
    assertThat(persistent.getCurrentLocationOfChildren(), is(equalTo(currentLocationOfChildren)));
    assertThat(persistent.getDrmsAllegationDescriptionDoc(),
        is(equalTo(drmsAllegationDescriptionDoc)));
    assertThat(persistent.getDrmsErReferralDoc(), is(equalTo(drmsErReferralDoc)));
    assertThat(persistent.getDrmsInvestigationDoc(), is(equalTo(drmsInvestigationDoc)));
    assertThat(persistent.getFiledSuspectedChildAbuseReporttoLawEnforcementIndicator(),
        is(equalTo("Y")));
    assertThat(persistent.getFamilyAwarenessIndicator(), is(equalTo("N")));
    assertThat(persistent.getGovtEntityType(), is(equalTo(govtEntityType)));
    assertThat(persistent.getLegalDefinitionCode(), is(equalTo(legalDefinitionCode)));
    assertThat(persistent.getLegalRightsNoticeIndicator(), is(equalTo("N")));
    assertThat(persistent.getLimitedAccessCode(), is(equalTo(limitedAccessCode)));
    assertThat(persistent.getMandatedCrossReportReceivedDate(),
        is(equalTo(df.parse(mandatedCrossReportReceivedDate))));
    assertThat(persistent.getReferralName(), is(equalTo(referralName)));
    assertThat(persistent.getOpenAdequateCaseCode(), is(equalTo(openAdequateCaseCode)));
    assertThat(persistent.getReceivedDate(), is(equalTo(df.parse(receivedDate))));
    assertThat(persistent.getReceivedTime(), is(equalTo(timeOnlyFormat.parse(receivedTime))));
    assertThat(persistent.getReferralResponseType(), is(equalTo(referralResponseType)));
    assertThat(persistent.getReferredToResourceType(), is(equalTo(referredToResourceType)));
    assertThat(persistent.getResponseDeterminationDate(),
        is(equalTo(df.parse(responseDeterminationDate))));
    assertThat(persistent.getResponseDeterminationTime(),
        is(equalTo(timeOnlyFormat.parse(responseDeterminationTime))));
    assertThat(persistent.getResponseRationaleText(), is(equalTo(responseRationaleText)));
    assertThat(persistent.getScreenerNoteText(), is(equalTo(screenerNoteText)));
    assertThat(persistent.getSpecificsIncludedCode(), is(equalTo(specificsIncludedCode)));
    assertThat(persistent.getSufficientInformationCode(), is(equalTo(sufficientInformationCode)));
    assertThat(persistent.getUnfoundedSeriesCode(), is(equalTo(unfoundedSeriesCode)));
    assertThat(persistent.getLinkToPrimaryReferralId(), is(equalTo(linkToPrimaryReferralId)));
    assertThat(persistent.getAllegesAbuseOccurredAtAddressId(),
        is(equalTo(allegesAbuseOccurredAtAddressId)));
    assertThat(persistent.getFirstResponseDeterminedByStaffPersonId(),
        is(equalTo(firstResponseDeterminedByStaffPersonId)));
    assertThat(persistent.getPrimaryContactStaffPersonId(),
        is(equalTo(primaryContactStaffPersonId)));
    assertThat(persistent.getCountySpecificCode(), is(equalTo(countySpecificCode)));
    assertThat(persistent.getSpecialProjectReferralIndicator(), is(equalTo("Y")));
    assertThat(persistent.getZippyCreatedIndicator(), is(equalTo("N")));
    assertThat(persistent.getHomelessIndicator(), is(equalTo("Y")));
    assertThat(persistent.getFamilyRefusedServicesIndicator(), is(equalTo("N")));
    assertThat(persistent.getFirstEvaluatedOutApprovalDate(),
        is(equalTo(df.parse(firstEvaluatedOutApprovalDate))));
    assertThat(persistent.getResponsibleAgencyCode(), is(equalTo(responsibleAgencyCode)));
    assertThat(persistent.getLimitedAccessGovtAgencyType(),
        is(equalTo(limitedAccessGovtAgencyType)));
    assertThat(persistent.getLimitedAccessDate(), is(equalTo(df.parse(limitedAccessDate))));
    assertThat(persistent.getLimitedAccessDesc(), is(equalTo(limitedAccessDesc)));
    assertThat(persistent.getOriginalClosureDate(), is(equalTo(df.parse(originalClosureDate))));
    assertThat(persistent.getLastUpdatedId(), is(equalTo(lastUpdatedId)));
  }

}
