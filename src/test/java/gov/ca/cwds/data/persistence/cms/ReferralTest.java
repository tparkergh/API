package gov.ca.cwds.data.persistence.cms;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import gov.ca.cwds.rest.api.domain.DomainChef;
import io.dropwizard.jackson.Jackson;

/**
 * @author CWDS API Team
 *
 */
public class ReferralTest {

  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

  private final static DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
  @SuppressWarnings("unused")
  private final static DateFormat tf = new SimpleDateFormat("yyyy-MM-dd-HH.mm.ss.SSS");
  private final static DateFormat timeOnlyFormat = new SimpleDateFormat("HH:mm:ss");
  private String id = "1234567ABC";
  private String additionalInfoIncludedCode = "Y";
  private String anonymousReporterIndicator = "Y";
  private String applicationForPetitionIndicator = "N";
  private String approvalNumber = "c";
  private Short approvalStatusType = 122;
  private String caretakersPerpetratorCode = "N";
  private Date closureDate = DomainChef.uncookDateString("1991-06-06");
  private Short communicationMethodType = 408;
  private String currentLocationOfChildren = "child location";
  private String drmsAllegationDescriptionDoc = "allegation desc";
  private String drmsErReferralDoc = "g";
  private String drmsInvestigationDoc = "h";
  private String filedSuspectedChildAbuseReporttoLawEnforcementIndicator = "Y";
  private String familyAwarenessIndicator = "N";
  private Short govtEntityType = 1077;
  private String legalDefinitionCode = "i";
  private String legalRightsNoticeIndicator = "N";
  private String limitedAccessCode = "j";
  private Date mandatedCrossReportReceivedDate = DomainChef.uncookDateString("1991-06-06");
  private String referralName = "referral name";
  private String openAdequateCaseCode = "l";
  private Date receivedDate = DomainChef.uncookDateString("1991-06-06");
  private Date receivedTime = DomainChef.uncookTimeString("14:46:59");
  private Short referralResponseType = 4;
  private Short referredToResourceType = 5;
  private Date responseDeterminationDate = DomainChef.uncookDateString("1991-06-06");
  private Date responseDeterminationTime = DomainChef.uncookDateString("1991-06-06");
  private String responseRationaleText = "response rational";
  private String screenerNoteText = "screener notes";
  private String specificsIncludedCode = "o";
  private String sufficientInformationCode = "p";
  private String unfoundedSeriesCode = "q";
  private String linkToPrimaryReferralId = "r";
  private String allegesAbuseOccurredAtAddressId = "s";
  private String firstResponseDeterminedByStaffPersonId = "t";
  private String primaryContactStaffPersonId = "u";
  private String countySpecificCode = "v";
  private String specialProjectReferralIndicator = "Y";
  private String zippyCreatedIndicator = "N";
  private String homelessIndicator = "Y";
  private String familyRefusedServicesIndicator = "N";
  private Date firstEvaluatedOutApprovalDate = DomainChef.uncookDateString("1991-06-06");
  private String responsibleAgencyCode = "w";
  private Short limitedAccessGovtAgencyType = 6;
  private Date limitedAccessDate = DomainChef.uncookDateString("1991-06-06");
  private String limitedAccessDesc = "x";
  private Date originalClosureDate = DomainChef.uncookDateString("1991-06-06");
  private String lastUpdatedId = "0X5";

  /*
   * Constructor test
   */
  @Test
  public void emtpyConstructorIsNotNull() throws Exception {
    assertThat(Referral.class.newInstance(), is(notNullValue()));
  }

  @Test
  public void constructorUsingDomainTest() throws Exception {

    gov.ca.cwds.rest.api.domain.cms.Referral domainReferral = validDomainReferral();

    Referral persistent = new Referral(id, domainReferral, "0X5");

    assertThat(persistent.getId(), is(equalTo(id)));
    assertThat(DomainChef.uncookBooleanString(persistent.getAdditionalInfoIncludedCode()),
        is(equalTo(domainReferral.getAdditionalInfoIncludedCode())));
    assertThat(DomainChef.uncookBooleanString(persistent.getAnonymousReporterIndicator()),
        is(equalTo(domainReferral.getAnonymousReporterIndicator())));
    assertThat(DomainChef.uncookBooleanString(persistent.getApplicationForPetitionIndicator()),
        is(equalTo(domainReferral.getApplicationForPetitionIndicator())));
    assertThat(persistent.getApprovalNumber(), is(equalTo(domainReferral.getApprovalNumber())));
    assertThat(persistent.getApprovalStatusType(),
        is(equalTo(domainReferral.getApprovalStatusType())));
    assertThat(DomainChef.uncookBooleanString(persistent.getCaretakersPerpetratorCode()),
        is(equalTo(domainReferral.getCaretakersPerpetratorCode())));
    assertThat(persistent.getClosureDate(), is(equalTo(df.parse(domainReferral.getClosureDate()))));
    assertThat(persistent.getCommunicationMethodType(),
        is(equalTo(domainReferral.getCommunicationMethodType())));
    assertThat(persistent.getCurrentLocationOfChildren(),
        is(equalTo(domainReferral.getCurrentLocationOfChildren())));
    assertThat(persistent.getDrmsAllegationDescriptionDoc(),
        is(equalTo(domainReferral.getDrmsAllegationDescriptionDoc())));
    assertThat(persistent.getDrmsErReferralDoc(),
        is(equalTo(domainReferral.getDrmsErReferralDoc())));
    assertThat(persistent.getDrmsInvestigationDoc(),
        is(equalTo(domainReferral.getDrmsInvestigationDoc())));
    assertThat(
        DomainChef.uncookBooleanString(
            persistent.getFiledSuspectedChildAbuseReporttoLawEnforcementIndicator()),
        is(equalTo(domainReferral.getFiledSuspectedChildAbuseReporttoLawEnforcementIndicator())));
    assertThat(DomainChef.uncookBooleanString(persistent.getFamilyAwarenessIndicator()),
        is(equalTo(domainReferral.getFamilyAwarenessIndicator())));
    assertThat(persistent.getGovtEntityType(), is(equalTo(domainReferral.getGovtEntityType())));
    assertThat(persistent.getLegalDefinitionCode(),
        is(equalTo(domainReferral.getLegalDefinitionCode())));
    assertThat(DomainChef.uncookBooleanString(persistent.getLegalRightsNoticeIndicator()),
        is(equalTo(domainReferral.getLegalRightsNoticeIndicator())));
    assertThat(persistent.getLimitedAccessCode(),
        is(equalTo(domainReferral.getLimitedAccessCode())));
    assertThat(persistent.getMandatedCrossReportReceivedDate(),
        is(equalTo(df.parse(domainReferral.getMandatedCrossReportReceivedDate()))));
    assertThat(persistent.getReferralName(), is(equalTo(domainReferral.getReferralName())));
    assertThat(persistent.getOpenAdequateCaseCode(),
        is(equalTo(domainReferral.getOpenAdequateCaseCode())));
    assertThat(persistent.getReceivedDate(),
        is(equalTo(df.parse(domainReferral.getReceivedDate()))));
    assertThat(persistent.getReceivedTime(),
        is(equalTo(timeOnlyFormat.parse(domainReferral.getReceivedTime()))));
    assertThat(persistent.getReferralResponseType(),
        is(equalTo(domainReferral.getReferralResponseType())));
    assertThat(persistent.getReferredToResourceType(),
        is(equalTo(domainReferral.getReferredToResourceType())));
    assertThat(persistent.getResponseDeterminationDate(),
        is(equalTo(df.parse(domainReferral.getResponseDeterminationDate()))));
    assertThat(persistent.getResponseDeterminationTime(),
        is(equalTo(timeOnlyFormat.parse(domainReferral.getResponseDeterminationTime()))));
    assertThat(persistent.getResponseRationaleText(),
        is(equalTo(domainReferral.getResponseRationaleText())));
    assertThat(persistent.getScreenerNoteText(), is(equalTo(domainReferral.getScreenerNoteText())));
    assertThat(persistent.getSpecificsIncludedCode(),
        is(equalTo(domainReferral.getSpecificsIncludedCode())));
    assertThat(persistent.getSufficientInformationCode(),
        is(equalTo(domainReferral.getSufficientInformationCode())));
    assertThat(persistent.getUnfoundedSeriesCode(),
        is(equalTo(domainReferral.getUnfoundedSeriesCode())));
    assertThat(persistent.getLinkToPrimaryReferralId(),
        is(equalTo(domainReferral.getLinkToPrimaryReferralId())));
    assertThat(persistent.getAllegesAbuseOccurredAtAddressId(),
        is(equalTo(domainReferral.getAllegesAbuseOccurredAtAddressId())));
    assertThat(persistent.getFirstResponseDeterminedByStaffPersonId(),
        is(equalTo(domainReferral.getFirstResponseDeterminedByStaffPersonId())));
    assertThat(persistent.getPrimaryContactStaffPersonId(),
        is(equalTo(domainReferral.getPrimaryContactStaffPersonId())));
    assertThat(persistent.getCountySpecificCode(),
        is(equalTo(domainReferral.getCountySpecificCode())));
    assertThat(DomainChef.uncookBooleanString(persistent.getSpecialProjectReferralIndicator()),
        is(equalTo(domainReferral.getSpecialProjectReferralIndicator())));
    assertThat(DomainChef.uncookBooleanString(persistent.getZippyCreatedIndicator()),
        is(equalTo(domainReferral.getZippyCreatedIndicator())));
    assertThat(DomainChef.uncookBooleanString(persistent.getHomelessIndicator()),
        is(equalTo(domainReferral.getHomelessIndicator())));
    assertThat(DomainChef.uncookBooleanString(persistent.getFamilyRefusedServicesIndicator()),
        is(equalTo(domainReferral.getFamilyRefusedServicesIndicator())));
    assertThat(persistent.getFirstEvaluatedOutApprovalDate(),
        is(equalTo(df.parse(domainReferral.getFirstEvaluatedOutApprovalDate()))));
    assertThat(persistent.getResponsibleAgencyCode(),
        is(equalTo(domainReferral.getResponsibleAgencyCode())));
    assertThat(persistent.getLimitedAccessGovtAgencyType(),
        is(equalTo(domainReferral.getLimitedAccessGovtAgencyType())));
    assertThat(persistent.getLimitedAccessDate(),
        is(equalTo(df.parse(domainReferral.getLimitedAccessDate()))));
    assertThat(persistent.getLimitedAccessDesc(),
        is(equalTo(domainReferral.getLimitedAccessDesc())));
    assertThat(persistent.getOriginalClosureDate(),
        is(equalTo(df.parse(domainReferral.getOriginalClosureDate()))));
    assertThat(persistent.getLastUpdatedId(), is(equalTo(lastUpdatedId)));
  }

  @Test
  public void persistenConstructorTest() throws Exception {

    Referral persistentReferral = new Referral(id, additionalInfoIncludedCode,
        anonymousReporterIndicator, applicationForPetitionIndicator, approvalNumber,
        approvalStatusType, caretakersPerpetratorCode, closureDate, communicationMethodType,
        currentLocationOfChildren, drmsAllegationDescriptionDoc, drmsErReferralDoc,
        drmsInvestigationDoc, filedSuspectedChildAbuseReporttoLawEnforcementIndicator,
        familyAwarenessIndicator, govtEntityType, legalDefinitionCode, legalRightsNoticeIndicator,
        limitedAccessCode, mandatedCrossReportReceivedDate, referralName, openAdequateCaseCode,
        receivedDate, receivedTime, referralResponseType, referredToResourceType,
        responseDeterminationDate, responseDeterminationTime, responseRationaleText,
        screenerNoteText, specificsIncludedCode, sufficientInformationCode, unfoundedSeriesCode,
        linkToPrimaryReferralId, allegesAbuseOccurredAtAddressId,
        firstResponseDeterminedByStaffPersonId, primaryContactStaffPersonId, countySpecificCode,
        specialProjectReferralIndicator, zippyCreatedIndicator, homelessIndicator,
        familyRefusedServicesIndicator, firstEvaluatedOutApprovalDate, responsibleAgencyCode,
        limitedAccessGovtAgencyType, limitedAccessDate, limitedAccessDesc, originalClosureDate);

  }

  private gov.ca.cwds.rest.api.domain.cms.Referral validDomainReferral()
      throws JsonParseException, JsonMappingException, IOException {

    gov.ca.cwds.rest.api.domain.cms.Referral validDomainReferral =
        MAPPER.readValue(fixture("fixtures/domain/legacy/Referral/valid/valid.json"),
            gov.ca.cwds.rest.api.domain.cms.Referral.class);
    return validDomainReferral;

  }
}
