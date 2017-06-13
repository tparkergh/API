package gov.ca.cwds.data.persistence.cms;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import gov.ca.cwds.data.persistence.junit.template.PersistentTestTemplate;
import gov.ca.cwds.rest.api.domain.DomainChef;

/**
 * @author CWDS API Team
 *
 */
public class ReferralTest implements PersistentTestTemplate {

  private static final ObjectMapper MAPPER = SystemCodeTestHarness.MAPPER;

  private final static DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
  @SuppressWarnings("unused")
  private final static DateFormat tf = new SimpleDateFormat("yyyy-MM-dd-HH.mm.ss.SSS");
  private final static DateFormat timeOnlyFormat = new SimpleDateFormat("HH:mm:ss");
  private String id = "1234567ABC";
  private String lastUpdatedId = "0X5";

  @Override
  public void testEqualsHashCodeWorks() throws Exception {
    // no equals() or hash() methods in persistent class.
  }

  /*
   * Constructor test
   */
  @Override
  @Test
  public void testEmptyConstructor() throws Exception {
    assertThat(Referral.class.newInstance(), is(notNullValue()));
  }

  @Override
  @Test
  public void testConstructorUsingDomain() throws Exception {
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

  @Override
  @Test
  public void testPersistentConstructor() throws Exception {
    Referral vr = validReferral();
    Referral pr = new Referral(vr.getId(), vr.getAdditionalInfoIncludedCode(),
        vr.getAnonymousReporterIndicator(), vr.getApplicationForPetitionIndicator(),
        vr.getApprovalNumber(), vr.getApprovalStatusType(), vr.getCaretakersPerpetratorCode(),
        vr.getClosureDate(), vr.getCommunicationMethodType(), vr.getCurrentLocationOfChildren(),
        vr.getDrmsAllegationDescriptionDoc(), vr.getDrmsErReferralDoc(),
        vr.getDrmsInvestigationDoc(),
        vr.getFiledSuspectedChildAbuseReporttoLawEnforcementIndicator(),
        vr.getFamilyAwarenessIndicator(), vr.getGovtEntityType(), vr.getLegalDefinitionCode(),
        vr.getLegalRightsNoticeIndicator(), vr.getLimitedAccessCode(),
        vr.getMandatedCrossReportReceivedDate(), vr.getReferralName(), vr.getOpenAdequateCaseCode(),
        vr.getReceivedDate(), vr.getReceivedTime(), vr.getReferralResponseType(),
        vr.getReferredToResourceType(), vr.getResponseDeterminationDate(),
        vr.getResponseDeterminationTime(), vr.getResponseRationaleText(), vr.getScreenerNoteText(),
        vr.getSpecificsIncludedCode(), vr.getSufficientInformationCode(),
        vr.getUnfoundedSeriesCode(), vr.getLinkToPrimaryReferralId(),
        vr.getAllegesAbuseOccurredAtAddressId(), vr.getFirstResponseDeterminedByStaffPersonId(),
        vr.getPrimaryContactStaffPersonId(), vr.getCountySpecificCode(),
        vr.getSpecialProjectReferralIndicator(), vr.getZippyCreatedIndicator(),
        vr.getHomelessIndicator(), vr.getFamilyRefusedServicesIndicator(),
        vr.getFirstEvaluatedOutApprovalDate(), vr.getResponsibleAgencyCode(),
        vr.getLimitedAccessGovtAgencyType(), vr.getLimitedAccessDate(), vr.getLimitedAccessDesc(),
        vr.getOriginalClosureDate(), null, null, null, null);

    assertThat(pr.getId(), is(equalTo(vr.getId())));
    assertThat(pr.getAdditionalInfoIncludedCode(), is(equalTo(vr.getAdditionalInfoIncludedCode())));
    assertThat(pr.getAnonymousReporterIndicator(), is(equalTo(vr.getAnonymousReporterIndicator())));
    assertThat(pr.getApplicationForPetitionIndicator(),
        is(equalTo(vr.getApplicationForPetitionIndicator())));
    assertThat(pr.getApprovalNumber(), is(equalTo(vr.getApprovalNumber())));
    assertThat(pr.getApprovalStatusType(), is(equalTo(vr.getApprovalStatusType())));
    assertThat(pr.getCaretakersPerpetratorCode(), is(equalTo(vr.getCaretakersPerpetratorCode())));
    assertThat(pr.getClosureDate(), is(equalTo(vr.getClosureDate())));
    assertThat(pr.getCommunicationMethodType(), is(equalTo(vr.getCommunicationMethodType())));
    assertThat(pr.getCurrentLocationOfChildren(), is(equalTo(vr.getCurrentLocationOfChildren())));
    assertThat(pr.getDrmsAllegationDescriptionDoc(),
        is(equalTo(vr.getDrmsAllegationDescriptionDoc())));
    assertThat(pr.getDrmsErReferralDoc(), is(equalTo(vr.getDrmsErReferralDoc())));
    assertThat(pr.getDrmsInvestigationDoc(), is(equalTo(vr.getDrmsInvestigationDoc())));
    assertThat(pr.getFiledSuspectedChildAbuseReporttoLawEnforcementIndicator(),
        is(equalTo(pr.getFiledSuspectedChildAbuseReporttoLawEnforcementIndicator())));
    assertThat(pr.getFamilyAwarenessIndicator(), is(equalTo(vr.getFamilyAwarenessIndicator())));
    assertThat(pr.getGovtEntityType(), is(equalTo(vr.getGovtEntityType())));
    assertThat(pr.getLegalDefinitionCode(), is(equalTo(vr.getLegalDefinitionCode())));
    assertThat(pr.getLegalRightsNoticeIndicator(), is(equalTo(vr.getLegalRightsNoticeIndicator())));
    assertThat(pr.getGovtEntityType(), is(equalTo(vr.getGovtEntityType())));
    assertThat(pr.getLegalDefinitionCode(), is(equalTo(vr.getLegalDefinitionCode())));
    assertThat(pr.getLegalRightsNoticeIndicator(), is(equalTo(vr.getLegalRightsNoticeIndicator())));
    assertThat(pr.getLimitedAccessCode(), is(equalTo(vr.getLimitedAccessCode())));
    assertThat(pr.getMandatedCrossReportReceivedDate(),
        is(equalTo(vr.getMandatedCrossReportReceivedDate())));
    assertThat(pr.getReferralName(), is(equalTo(vr.getReferralName())));
    assertThat(pr.getOpenAdequateCaseCode(), is(equalTo(vr.getOpenAdequateCaseCode())));
    assertThat(pr.getReceivedDate(), is(equalTo(vr.getReceivedDate())));
    assertThat(pr.getReceivedTime(), is(equalTo(vr.getReceivedTime())));
    assertThat(pr.getReferralResponseType(), is(equalTo(vr.getReferralResponseType())));
    assertThat(pr.getReferredToResourceType(), is(equalTo(vr.getReferredToResourceType())));
    assertThat(pr.getResponseDeterminationDate(), is(equalTo(vr.getResponseDeterminationDate())));
    assertThat(pr.getResponseDeterminationTime(), is(equalTo(vr.getResponseDeterminationTime())));
    assertThat(pr.getResponseRationaleText(), is(equalTo(vr.getResponseRationaleText())));
    assertThat(pr.getScreenerNoteText(), is(equalTo(vr.getScreenerNoteText())));
    assertThat(pr.getSpecificsIncludedCode(), is(equalTo(vr.getSpecificsIncludedCode())));
    assertThat(pr.getSufficientInformationCode(), is(equalTo(vr.getSufficientInformationCode())));
    assertThat(pr.getUnfoundedSeriesCode(), is(equalTo(vr.getUnfoundedSeriesCode())));
    assertThat(pr.getLinkToPrimaryReferralId(), is(equalTo(vr.getLinkToPrimaryReferralId())));
    assertThat(pr.getAllegesAbuseOccurredAtAddressId(),
        is(equalTo(vr.getAllegesAbuseOccurredAtAddressId())));
    assertThat(pr.getFirstResponseDeterminedByStaffPersonId(),
        is(equalTo(vr.getFirstResponseDeterminedByStaffPersonId())));
    assertThat(pr.getPrimaryContactStaffPersonId(),
        is(equalTo(vr.getPrimaryContactStaffPersonId())));
    assertThat(pr.getCountySpecificCode(), is(equalTo(vr.getCountySpecificCode())));
    assertThat(pr.getSpecialProjectReferralIndicator(),
        is(equalTo(vr.getSpecialProjectReferralIndicator())));
    assertThat(pr.getZippyCreatedIndicator(), is(equalTo(vr.getZippyCreatedIndicator())));
    assertThat(pr.getHomelessIndicator(), is(equalTo(vr.getHomelessIndicator())));
    assertThat(pr.getFamilyRefusedServicesIndicator(),
        is(equalTo(vr.getFamilyRefusedServicesIndicator())));
    assertThat(pr.getFirstEvaluatedOutApprovalDate(),
        is(equalTo(vr.getFirstEvaluatedOutApprovalDate())));
    assertThat(pr.getResponsibleAgencyCode(), is(equalTo(vr.getResponsibleAgencyCode())));
    assertThat(pr.getLimitedAccessGovtAgencyType(),
        is(equalTo(vr.getLimitedAccessGovtAgencyType())));
    assertThat(pr.getOriginalClosureDate(), is(equalTo(vr.getOriginalClosureDate())));
  }

  @SuppressWarnings("javadoc")
  @Test
  public void testSerializeAndDeserialize() throws Exception {
    Referral vr = validReferral();
    Referral pr = new Referral(vr.getId(), vr.getAdditionalInfoIncludedCode(),
        vr.getAnonymousReporterIndicator(), vr.getApplicationForPetitionIndicator(),
        vr.getApprovalNumber(), vr.getApprovalStatusType(), vr.getCaretakersPerpetratorCode(),
        vr.getClosureDate(), vr.getCommunicationMethodType(), vr.getCurrentLocationOfChildren(),
        vr.getDrmsAllegationDescriptionDoc(), vr.getDrmsErReferralDoc(),
        vr.getDrmsInvestigationDoc(),
        vr.getFiledSuspectedChildAbuseReporttoLawEnforcementIndicator(),
        vr.getFamilyAwarenessIndicator(), vr.getGovtEntityType(), vr.getLegalDefinitionCode(),
        vr.getLegalRightsNoticeIndicator(), vr.getLimitedAccessCode(),
        vr.getMandatedCrossReportReceivedDate(), vr.getReferralName(), vr.getOpenAdequateCaseCode(),
        vr.getReceivedDate(), vr.getReceivedTime(), vr.getReferralResponseType(),
        vr.getReferredToResourceType(), vr.getResponseDeterminationDate(),
        vr.getResponseDeterminationTime(), vr.getResponseRationaleText(), vr.getScreenerNoteText(),
        vr.getSpecificsIncludedCode(), vr.getSufficientInformationCode(),
        vr.getUnfoundedSeriesCode(), vr.getLinkToPrimaryReferralId(),
        vr.getAllegesAbuseOccurredAtAddressId(), vr.getFirstResponseDeterminedByStaffPersonId(),
        vr.getPrimaryContactStaffPersonId(), vr.getCountySpecificCode(),
        vr.getSpecialProjectReferralIndicator(), vr.getZippyCreatedIndicator(),
        vr.getHomelessIndicator(), vr.getFamilyRefusedServicesIndicator(),
        vr.getFirstEvaluatedOutApprovalDate(), vr.getResponsibleAgencyCode(),
        vr.getLimitedAccessGovtAgencyType(), vr.getLimitedAccessDate(), vr.getLimitedAccessDesc(),
        vr.getOriginalClosureDate(), null, null, null, null);

    final String expected = MAPPER.writeValueAsString((MAPPER.readValue(
        fixture("fixtures/persistent/Referral/valid/validWithSysCodes.json"), Referral.class)));

    assertThat(MAPPER.writeValueAsString(pr)).isEqualTo(expected);
  }

  private Referral validReferral() throws JsonParseException, JsonMappingException, IOException {
    Referral vr =
        MAPPER.readValue(fixture("fixtures/persistent/Referral/valid/valid.json"), Referral.class);
    return vr;
  }

  private gov.ca.cwds.rest.api.domain.cms.Referral validDomainReferral()
      throws JsonParseException, JsonMappingException, IOException {
    gov.ca.cwds.rest.api.domain.cms.Referral validDomainReferral =
        MAPPER.readValue(fixture("fixtures/domain/legacy/Referral/valid/valid.json"),
            gov.ca.cwds.rest.api.domain.cms.Referral.class);
    return validDomainReferral;
  }

}
