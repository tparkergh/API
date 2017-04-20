package gov.ca.cwds.rest.api.domain.cms;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.squarespace.jersey2.guice.JerseyGuiceUtils;

import gov.ca.cwds.data.CrudsDao;
import gov.ca.cwds.rest.api.domain.DomainChef;
import gov.ca.cwds.rest.core.Api;
import gov.ca.cwds.rest.resources.cms.JerseyGuiceRule;
import gov.ca.cwds.rest.resources.cms.ReferralResource;
import io.dropwizard.jackson.Jackson;
import io.dropwizard.testing.junit.ResourceTestRule;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

/**
 * @author CWDS API Team
 *
 */
@SuppressWarnings("javadoc")
public class ReferralTest {

  private static final String ROOT_RESOURCE = "/" + Api.RESOURCE_REFERRAL + "/";;

  private static final ReferralResource mockedReferralResource = mock(ReferralResource.class);

  @After
  public void ensureServiceLocatorPopulated() {
    JerseyGuiceUtils.reset();
  }

  @ClassRule
  public static JerseyGuiceRule rule = new JerseyGuiceRule();

  @ClassRule
  public static final ResourceTestRule resources =
      ResourceTestRule.builder().addResource(mockedReferralResource).build();

  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();
  private Referral validReferral = validReferral();

  private final static DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
  @SuppressWarnings("unused")
  private final static DateFormat tf = new SimpleDateFormat("yyyy-MM-dd-HH.mm.ss.SSS");
  private final static DateFormat timeOnlyFormat = new SimpleDateFormat("HH:mm:ss");
  private String id = "1234567ABC";
  private Boolean additionalInfoIncludedCode = Boolean.FALSE;
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
  private String receivedTime = "16:41:49";
  private Short referralResponseType = 4;
  private Short referredToResourceType = 5;
  private String responseDeterminationDate = "1985-09-04";
  private String responseDeterminationTime = "16:41:49";
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

  @Before
  public void setup() {
    @SuppressWarnings("rawtypes")
    CrudsDao crudsDao = mock(CrudsDao.class);
    when(crudsDao.find(any())).thenReturn(mock(gov.ca.cwds.data.persistence.cms.Referral.class));

    when(mockedReferralResource.create(eq(validReferral)))
        .thenReturn(Response.status(Response.Status.NO_CONTENT).entity(null).build());
  }

  /*
   * Constructor Tests
   */
  @Test
  public void persistentObjectConstructorTest() throws Exception {
    Referral domain = new Referral(additionalInfoIncludedCode, anonymousReporterIndicator,
        applicationForPetitionIndicator, approvalNumber, approvalStatusType,
        caretakersPerpetratorCode, closureDate, communicationMethodType, currentLocationOfChildren,
        drmsAllegationDescriptionDoc, drmsErReferralDoc, drmsInvestigationDoc,
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

    gov.ca.cwds.data.persistence.cms.Referral persistent =
        new gov.ca.cwds.data.persistence.cms.Referral(id, domain, "lastUpdatedId");

    Referral totest = new Referral(persistent);
    assertThat(totest.getAdditionalInfoIncludedCode(),
        is(equalTo(DomainChef.uncookBooleanString(persistent.getAdditionalInfoIncludedCode()))));
    assertThat(totest.getAnonymousReporterIndicator(),
        is(equalTo(DomainChef.uncookBooleanString(persistent.getAnonymousReporterIndicator()))));
    assertThat(totest.getApplicationForPetitionIndicator(), is(
        equalTo(DomainChef.uncookBooleanString(persistent.getApplicationForPetitionIndicator()))));
    assertThat(totest.getApprovalNumber(), is(equalTo(persistent.getApprovalNumber())));
    assertThat(totest.getApprovalStatusType(), is(equalTo(persistent.getApprovalStatusType())));
    assertThat(totest.getCaretakersPerpetratorCode(),
        is(equalTo(DomainChef.uncookBooleanString(persistent.getCaretakersPerpetratorCode()))));
    assertThat(totest.getClosureDate(), is(equalTo(df.format(persistent.getClosureDate()))));
    assertThat(totest.getCommunicationMethodType(),
        is(equalTo(persistent.getCommunicationMethodType())));
    assertThat(totest.getCurrentLocationOfChildren(),
        is(equalTo(persistent.getCurrentLocationOfChildren())));
    assertThat(totest.getDrmsAllegationDescriptionDoc(),
        is(equalTo(persistent.getDrmsAllegationDescriptionDoc())));
    assertThat(totest.getDrmsErReferralDoc(), is(equalTo(persistent.getDrmsErReferralDoc())));
    assertThat(totest.getDrmsInvestigationDoc(), is(equalTo(persistent.getDrmsInvestigationDoc())));
    assertThat(totest.getFiledSuspectedChildAbuseReporttoLawEnforcementIndicator(),
        is(equalTo(DomainChef.uncookBooleanString(
            persistent.getFiledSuspectedChildAbuseReporttoLawEnforcementIndicator()))));
    assertThat(totest.getFamilyAwarenessIndicator(),
        is(equalTo(DomainChef.uncookBooleanString(persistent.getFamilyAwarenessIndicator()))));
    assertThat(totest.getGovtEntityType(), is(equalTo(persistent.getGovtEntityType())));
    assertThat(totest.getLegalDefinitionCode(), is(equalTo(persistent.getLegalDefinitionCode())));
    assertThat(totest.getLegalRightsNoticeIndicator(),
        is(equalTo(DomainChef.uncookBooleanString(persistent.getLegalRightsNoticeIndicator()))));
    assertThat(totest.getLimitedAccessCode(), is(equalTo(persistent.getLimitedAccessCode())));
    assertThat(totest.getMandatedCrossReportReceivedDate(),
        is(equalTo(df.format(persistent.getMandatedCrossReportReceivedDate()))));
    assertThat(totest.getReferralName(), is(equalTo(persistent.getReferralName())));
    assertThat(totest.getOpenAdequateCaseCode(), is(equalTo(persistent.getOpenAdequateCaseCode())));
    assertThat(totest.getReceivedDate(), is(equalTo(df.format(persistent.getReceivedDate()))));
    assertThat(totest.getReceivedTime(),
        is(equalTo(timeOnlyFormat.format(persistent.getReceivedTime()))));
    assertThat(totest.getReferralResponseType(), is(equalTo(persistent.getReferralResponseType())));
    assertThat(totest.getReferredToResourceType(),
        is(equalTo(persistent.getReferredToResourceType())));
    assertThat(totest.getResponseDeterminationDate(),
        is(equalTo(df.format(persistent.getResponseDeterminationDate()))));
    assertThat(totest.getResponseDeterminationTime(),
        is(equalTo(timeOnlyFormat.format(persistent.getResponseDeterminationTime()))));
    assertThat(totest.getResponseRationaleText(),
        is(equalTo(persistent.getResponseRationaleText())));
    assertThat(totest.getScreenerNoteText(), is(equalTo(persistent.getScreenerNoteText())));
    assertThat(totest.getSpecificsIncludedCode(),
        is(equalTo(persistent.getSpecificsIncludedCode())));
    assertThat(totest.getSufficientInformationCode(),
        is(equalTo(persistent.getSufficientInformationCode())));
    assertThat(totest.getUnfoundedSeriesCode(), is(equalTo(persistent.getUnfoundedSeriesCode())));
    assertThat(totest.getLinkToPrimaryReferralId(),
        is(equalTo(persistent.getLinkToPrimaryReferralId())));
    assertThat(totest.getAllegesAbuseOccurredAtAddressId(),
        is(equalTo(persistent.getAllegesAbuseOccurredAtAddressId())));
    assertThat(totest.getFirstResponseDeterminedByStaffPersonId(),
        is(equalTo(persistent.getFirstResponseDeterminedByStaffPersonId())));
    assertThat(totest.getPrimaryContactStaffPersonId(),
        is(equalTo(persistent.getPrimaryContactStaffPersonId())));
    assertThat(totest.getCountySpecificCode(), is(equalTo(persistent.getCountySpecificCode())));
    assertThat(totest.getSpecialProjectReferralIndicator(), is(
        equalTo(DomainChef.uncookBooleanString(persistent.getSpecialProjectReferralIndicator()))));
    assertThat(totest.getZippyCreatedIndicator(),
        is(equalTo(DomainChef.uncookBooleanString(persistent.getZippyCreatedIndicator()))));
    assertThat(totest.getHomelessIndicator(),
        is(equalTo(DomainChef.uncookBooleanString(persistent.getHomelessIndicator()))));
    assertThat(totest.getFamilyRefusedServicesIndicator(), is(
        equalTo(DomainChef.uncookBooleanString(persistent.getFamilyRefusedServicesIndicator()))));
    assertThat(totest.getFirstEvaluatedOutApprovalDate(),
        is(equalTo(df.format(persistent.getFirstEvaluatedOutApprovalDate()))));
    assertThat(totest.getResponsibleAgencyCode(),
        is(equalTo(persistent.getResponsibleAgencyCode())));
    assertThat(totest.getLimitedAccessGovtAgencyType(),
        is(equalTo(persistent.getLimitedAccessGovtAgencyType())));
    assertThat(totest.getLimitedAccessDate(),
        is(equalTo(df.format(persistent.getLimitedAccessDate()))));
    assertThat(totest.getLimitedAccessDesc(), is(equalTo(persistent.getLimitedAccessDesc())));
    assertThat(totest.getOriginalClosureDate(),
        is(equalTo(df.format(persistent.getOriginalClosureDate()))));
  }

  @Test
  public void jsonCreatorConstructorTest() throws Exception {
    Referral domain = new Referral(additionalInfoIncludedCode, anonymousReporterIndicator,
        applicationForPetitionIndicator, approvalNumber, approvalStatusType,
        caretakersPerpetratorCode, closureDate, communicationMethodType, currentLocationOfChildren,
        drmsAllegationDescriptionDoc, drmsErReferralDoc, drmsInvestigationDoc,
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

    assertThat(domain.getAdditionalInfoIncludedCode(), is(equalTo(additionalInfoIncludedCode)));
    assertThat(domain.getAnonymousReporterIndicator(), is(equalTo(anonymousReporterIndicator)));
    assertThat(domain.getApplicationForPetitionIndicator(),
        is(equalTo(applicationForPetitionIndicator)));
    assertThat(domain.getApprovalNumber(), is(equalTo(approvalNumber)));
    assertThat(domain.getApprovalStatusType(), is(equalTo(approvalStatusType)));
    assertThat(domain.getCaretakersPerpetratorCode(), is(equalTo(caretakersPerpetratorCode)));
    assertThat(domain.getClosureDate(), is(equalTo(closureDate)));
    assertThat(domain.getCommunicationMethodType(), is(equalTo(communicationMethodType)));
    assertThat(domain.getCurrentLocationOfChildren(), is(equalTo(currentLocationOfChildren)));
    assertThat(domain.getDrmsAllegationDescriptionDoc(), is(equalTo(drmsAllegationDescriptionDoc)));
    assertThat(domain.getDrmsErReferralDoc(), is(equalTo(drmsErReferralDoc)));
    assertThat(domain.getDrmsInvestigationDoc(), is(equalTo(drmsInvestigationDoc)));
    assertThat(domain.getFiledSuspectedChildAbuseReporttoLawEnforcementIndicator(),
        is(equalTo(filedSuspectedChildAbuseReporttoLawEnforcementIndicator)));
    assertThat(domain.getFamilyAwarenessIndicator(), is(equalTo(familyAwarenessIndicator)));
    assertThat(domain.getGovtEntityType(), is(equalTo(govtEntityType)));
    assertThat(domain.getLegalDefinitionCode(), is(equalTo(legalDefinitionCode)));
    assertThat(domain.getLegalRightsNoticeIndicator(), is(equalTo(legalRightsNoticeIndicator)));
    assertThat(domain.getLimitedAccessCode(), is(equalTo(limitedAccessCode)));
    assertThat(domain.getMandatedCrossReportReceivedDate(),
        is(equalTo(mandatedCrossReportReceivedDate)));
    assertThat(domain.getReferralName(), is(equalTo(referralName)));
    assertThat(domain.getOpenAdequateCaseCode(), is(equalTo(openAdequateCaseCode)));
    assertThat(domain.getReceivedDate(), is(equalTo(receivedDate)));
    assertThat(domain.getReceivedTime(), is(equalTo(receivedTime)));
    assertThat(domain.getReferralResponseType(), is(equalTo(referralResponseType)));
    assertThat(domain.getReferredToResourceType(), is(equalTo(referredToResourceType)));
    assertThat(domain.getResponseDeterminationDate(), is(equalTo(responseDeterminationDate)));
    assertThat(domain.getResponseDeterminationTime(), is(equalTo(responseDeterminationTime)));
    assertThat(domain.getResponseRationaleText(), is(equalTo(responseRationaleText)));
    assertThat(domain.getScreenerNoteText(), is(equalTo(screenerNoteText)));
    assertThat(domain.getSpecificsIncludedCode(), is(equalTo(specificsIncludedCode)));
    assertThat(domain.getSufficientInformationCode(), is(equalTo(sufficientInformationCode)));
    assertThat(domain.getUnfoundedSeriesCode(), is(equalTo(unfoundedSeriesCode)));
    assertThat(domain.getLinkToPrimaryReferralId(), is(equalTo(linkToPrimaryReferralId)));
    assertThat(domain.getAllegesAbuseOccurredAtAddressId(),
        is(equalTo(allegesAbuseOccurredAtAddressId)));
    assertThat(domain.getFirstResponseDeterminedByStaffPersonId(),
        is(equalTo(firstResponseDeterminedByStaffPersonId)));
    assertThat(domain.getPrimaryContactStaffPersonId(), is(equalTo(primaryContactStaffPersonId)));
    assertThat(domain.getCountySpecificCode(), is(equalTo(countySpecificCode)));
    assertThat(domain.getSpecialProjectReferralIndicator(),
        is(equalTo(specialProjectReferralIndicator)));
    assertThat(domain.getZippyCreatedIndicator(), is(equalTo(zippyCreatedIndicator)));
    assertThat(domain.getHomelessIndicator(), is(equalTo(homelessIndicator)));
    assertThat(domain.getFamilyRefusedServicesIndicator(),
        is(equalTo(familyRefusedServicesIndicator)));
    assertThat(domain.getFirstEvaluatedOutApprovalDate(),
        is(equalTo(firstEvaluatedOutApprovalDate)));
    assertThat(domain.getResponsibleAgencyCode(), is(equalTo(responsibleAgencyCode)));
    assertThat(domain.getLimitedAccessGovtAgencyType(), is(equalTo(limitedAccessGovtAgencyType)));
    assertThat(domain.getLimitedAccessDate(), is(equalTo(limitedAccessDate)));
    assertThat(domain.getLimitedAccessDesc(), is(equalTo(limitedAccessDesc)));
    assertThat(domain.getOriginalClosureDate(), is(equalTo(originalClosureDate)));
  }

  @Test
  public void equalsHashCodeWork() {
    EqualsVerifier.forClass(Referral.class).suppress(Warning.NONFINAL_FIELDS).verify();
  }

  @Test
  public void serializesToJSON() throws Exception {
    final String expected = MAPPER.writeValueAsString(MAPPER
        .readValue(fixture("fixtures/domain/legacy/Referral/valid/valid.json"), Referral.class));

    assertThat(MAPPER.writeValueAsString(validReferral()), is(equalTo(expected)));
  }

  @Test
  public void deserializesFromJSON() throws Exception {
    assertThat(MAPPER.readValue(fixture("fixtures/domain/legacy/Referral/valid/valid.json"),
        Referral.class), is(equalTo(validReferral())));
  }

  /*
   * Successful Tests
   */
  @Test
  public void successfulWithValid() throws Exception {
    Referral toCreate = MAPPER
        .readValue(fixture("fixtures/domain/legacy/Referral/valid/valid.json"), Referral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void successfulWithOptionalsNotIncluded() throws Exception {
    Referral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Referral/valid/optionalsNotIncluded.json"), Referral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  /*
   * additionalInfoIncludedCode Tests
   */
  @Test
  public void failsWhenAdditionalInfoIncludedCodeNull() throws Exception {
    Referral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Referral/invalid/additionalInfoIncludedCodeNull.json"),
        Referral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("additionalInfoIncludedCode may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenAdditionalInfoIncludedCodeEmpty() throws Exception {
    Referral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Referral/invalid/additionalInfoIncludedCodeEmpty.json"),
        Referral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("additionalInfoIncludedCode may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * anonymousReporterIndicator Tests
   */
  @Test
  public void failsWhenAnonymousReporterIndicatorMissing() throws Exception {
    Referral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Referral/invalid/anonymousReporterIndicatorMissing.json"),
        Referral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("anonymousReporterIndicator may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenAnonymousReporterIndicatorNull() throws Exception {
    Referral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Referral/invalid/anonymousReporterIndicatorNull.json"),
        Referral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("anonymousReporterIndicator may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenAnonymousReporterIndicatorEmpty() throws Exception {
    Referral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Referral/invalid/anonymousReporterIndicatorEmpty.json"),
        Referral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("anonymousReporterIndicator may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenAnonymousReporterIndicatorAllWhitespace() throws Exception {
    Referral toCreate = MAPPER.readValue(
        fixture(
            "fixtures/domain/legacy/Referral/invalid/anonymousReporterIndicatorAllWhitespace.json"),
        Referral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("anonymousReporterIndicator may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * applicationForPetitionIndicator Tests
   */
  @Test
  public void failsWhenApplicationForPetitionIndicatorMissing() throws Exception {
    Referral toCreate = MAPPER.readValue(
        fixture(
            "fixtures/domain/legacy/Referral/invalid/applicationForPetitionIndicatorMissing.json"),
        Referral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class)
        .indexOf("applicationForPetitionIndicator may not be null"), is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenApplicationForPetitionIndicatorNull() throws Exception {
    Referral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Referral/invalid/applicationForPetitionIndicatorNull.json"),
        Referral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class)
        .indexOf("applicationForPetitionIndicator may not be null"), is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenApplicationForPetitionIndicatorEmpty() throws Exception {
    Referral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Referral/invalid/applicationForPetitionIndicatoEmpty.json"),
        Referral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class)
        .indexOf("applicationForPetitionIndicator may not be null"), is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenApplicationForPetitionIndicatorAllWhitespace() throws Exception {
    Referral toCreate = MAPPER.readValue(
        fixture(
            "fixtures/domain/legacy/Referral/invalid/applicationForPetitionIndicatorAllWhitespace.json"),
        Referral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class)
        .indexOf("applicationForPetitionIndicator may not be null"), is(greaterThanOrEqualTo(0)));
  }

  /*
   * approvalNumber Tests
   */

  @Test
  public void failsWhenApprovalNumberTooLong() throws Exception {
    Referral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Referral/invalid/approvalNumberTooLong.json"),
        Referral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("approvalNumber size must be between 0 and 10"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * approvalStatusType Tests
   */
  @Test
  public void failsWhenApprovalStatusTypeMissing() throws Exception {
    Referral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Referral/invalid/approvalStatusTypeMissing.json"),
        Referral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("approvalStatusType may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenApprovalStatusTypeNull() throws Exception {
    Referral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Referral/invalid/approvalStatusTypeNull.json"),
        Referral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("approvalStatusType may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenApprovalStatusTypeEmpty() throws Exception {
    Referral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Referral/invalid/approvalStatusTypeEmpty.json"),
        Referral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("approvalStatusType may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * caretakersPerpetratorCode Tests
   */
  @Test
  public void failsWhenCaretakersPerpetratorCodeMissing() throws Exception {
    Referral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Referral/invalid/caretakersPerpetratorCodeMissing.json"),
        Referral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("caretakersPerpetratorCode may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenCaretakersPerpetratorCodeNull() throws Exception {
    Referral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Referral/invalid/caretakersPerpetratorCodeNull.json"),
        Referral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("caretakersPerpetratorCode may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenCaretakersPerpetratorCodeEmpty() throws Exception {
    Referral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Referral/invalid/caretakersPerpetratorCodeEmpty.json"),
        Referral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("caretakersPerpetratorCode may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * closureDate Tests
   */
  @Test
  public void successWhenClosureDateEmpty() throws Exception {
    Referral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Referral/valid/closureDateEmpty.json"), Referral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void successWhenClosureDateNull() throws Exception {
    Referral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Referral/valid/closureDateNull.json"), Referral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void failsWhenClosureDateWrongFormat() throws Exception {
    Referral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Referral/invalid/closureDateWrongFormat.json"),
        Referral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class)
        .indexOf("closureDate must be in the format of yyyy-MM-dd"), is(greaterThanOrEqualTo(0)));
  }

  /*
   * communicationMethodType Tests
   */
  @Test
  public void failsWhenCommunicationMethodTypeMissing() throws Exception {
    Referral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Referral/invalid/communicationMethodTypeMissing.json"),
        Referral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("communicationMethodType may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenCommunicationMethodTypeNull() throws Exception {
    Referral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Referral/invalid/communicationMethodTypeNull.json"),
        Referral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("communicationMethodType may not be null"),
        is(greaterThanOrEqualTo(0)));
  }


  /*
   * currentLocationOfChildren Tests
   */
  @Test
  public void successWhenCurrentLocationOfChildrenTooLong() throws Exception {
    Referral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Referral/invalid/currentLocationOfChildrenTooLong.json"),
        Referral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class)
            .indexOf("currentLocationOfChildren size must be between 0 and 10"),
        is(greaterThanOrEqualTo(0)));
  }


  /*
   * drmsAllegationDescriptionDoc Tests
   */

  @Test
  public void failsWhenDrmsAllegationDescriptionDocTooLong() throws Exception {
    Referral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Referral/invalid/drmsAllegationDescriptionDocTooLong.json"),
        Referral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class)
            .indexOf("drmsAllegationDescriptionDoc size must be between 0 and 10"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenDrmsErReferralDocTooLong() throws Exception {
    Referral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Referral/invalid/drmsErReferralDocTooLong.json"),
        Referral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class)
        .indexOf("drmsErReferralDoc size must be between 0 and 10"), is(greaterThanOrEqualTo(0)));
  }

  /*
   * drmsInvestigationDoc Tests
   */
  @Test
  public void failsWhenDrmsInvestigationDocTooLong() throws Exception {
    Referral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Referral/invalid/drmsInvestigationDocTooLong.json"),
        Referral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf(
        "drmsInvestigationDoc size must be between 0 and 10"), is(greaterThanOrEqualTo(0)));
  }

  /*
   * filedSuspectedChildAbuseReporttoLawEnforcementIndicator Tests
   */
  @Test
  public void failsWhenFiledSuspectedChildAbuseReporttoLawEnforcementIndicatorMissing()
      throws Exception {
    Referral toCreate = MAPPER.readValue(
        fixture(
            "fixtures/domain/legacy/Referral/invalid/filedSuspectedChildAbuseReporttoLawEnforcementIndicatorMissing.json"),
        Referral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class)
            .indexOf("filedSuspectedChildAbuseReporttoLawEnforcementIndicator may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenFiledSuspectedChildAbuseReporttoLawEnforcementIndicatorNull()
      throws Exception {
    Referral toCreate = MAPPER.readValue(
        fixture(
            "fixtures/domain/legacy/Referral/invalid/filedSuspectedChildAbuseReporttoLawEnforcementIndicatorNull.json"),
        Referral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class)
            .indexOf("filedSuspectedChildAbuseReporttoLawEnforcementIndicator may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenFiledSuspectedChildAbuseReporttoLawEnforcementIndicatorEmpty()
      throws Exception {
    Referral toCreate = MAPPER.readValue(
        fixture(
            "fixtures/domain/legacy/Referral/invalid/filedSuspectedChildAbuseReporttoLawEnforcementIndicatorEmpty.json"),
        Referral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class)
            .indexOf("filedSuspectedChildAbuseReporttoLawEnforcementIndicator may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenFiledSuspectedChildAbuseReporttoLawEnforcementIndicatorAllWhitespace()
      throws Exception {
    Referral toCreate = MAPPER.readValue(
        fixture(
            "fixtures/domain/legacy/Referral/invalid/filedSuspectedChildAbuseReporttoLawEnforcementIndicatorAllWhitespace.json"),
        Referral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class)
            .indexOf("filedSuspectedChildAbuseReporttoLawEnforcementIndicator may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * familyAwarenessIndicator Tests
   */
  @Test
  public void failsWhenFamilyAwarenessIndicatorMissing() throws Exception {
    Referral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Referral/invalid/familyAwarenessIndicatorMissing.json"),
        Referral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("familyAwarenessIndicator may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenFamilyAwarenessIndicatorNull() throws Exception {
    Referral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Referral/invalid/familyAwarenessIndicatorNull.json"),
        Referral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("familyAwarenessIndicator may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenFamilyAwarenessIndicatorEmpty() throws Exception {
    Referral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Referral/invalid/familyAwarenessIndicatorEmpty.json"),
        Referral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("familyAwarenessIndicator may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenFamilyAwarenessIndicatorAllWhitespace() throws Exception {
    Referral toCreate = MAPPER.readValue(
        fixture(
            "fixtures/domain/legacy/Referral/invalid/familyAwarenessIndicatorAllWhitespace.json"),
        Referral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("familyAwarenessIndicator may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * govtEntityType Tests
   */
  @Test
  public void failsWhenGovtEntityTypeMissing() throws Exception {
    Referral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Referral/invalid/govtEntityTypeMissing.json"),
        Referral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("govtEntityType may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenGovtEntityTypeNull() throws Exception {
    Referral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Referral/invalid/govtEntityTypeNull.json"), Referral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("govtEntityType may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * legalDefinitionCode Tests
   */
  @Test
  public void failsWhenLegalDefinitionCodeMissing() throws Exception {
    Referral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Referral/invalid/legalDefinitionCodeMissing.json"),
        Referral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("legalDefinitionCode may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenLegalDefinitionCodeNull() throws Exception {
    Referral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Referral/invalid/legalDefinitionCodeNull.json"),
        Referral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("legalDefinitionCode may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenLegalDefinitionCodeEmpty() throws Exception {
    Referral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Referral/invalid/legalDefinitionCodeEmpty.json"),
        Referral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("legalDefinitionCode may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenLegalDefinitionCodeTooLong() throws Exception {
    Referral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Referral/invalid/legalDefinitionCodeTooLong.json"),
        Referral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("legalDefinitionCode size must be 1"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * legalRightsNoticeIndicator Tests
   */
  @Test
  public void failsWhenLegalRightsNoticeIndicatorMissing() throws Exception {
    Referral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Referral/invalid/legalRightsNoticeIndicatorMissing.json"),
        Referral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("legalRightsNoticeIndicator may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenLegalRightsNoticeIndicatorNull() throws Exception {
    Referral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Referral/invalid/legalRightsNoticeIndicatorNull.json"),
        Referral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("legalRightsNoticeIndicator may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenLegalRightsNoticeIndicatorEmpty() throws Exception {
    Referral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Referral/invalid/legalRightsNoticeIndicatorEmpty.json"),
        Referral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("legalRightsNoticeIndicator may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenLegalRightsNoticeIndicatorAllWhitespace() throws Exception {
    Referral toCreate = MAPPER.readValue(
        fixture(
            "fixtures/domain/legacy/Referral/invalid/legalRightsNoticeIndicatorAllWhitespace.json"),
        Referral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("legalRightsNoticeIndicator may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * limitedAccessCode Tests
   */
  @Test
  public void failsWhenLimitedAccessCodeMissing() throws Exception {
    Referral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Referral/invalid/limitedAccessCodeMissing.json"),
        Referral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("limitedAccessCode may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenLimitedAccessCodeNull() throws Exception {
    Referral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Referral/invalid/limitedAccessCodeNull.json"),
        Referral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("limitedAccessCode may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenLimitedAccessCodeEmpty() throws Exception {
    Referral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Referral/invalid/limitedAccessCodeEmpty.json"),
        Referral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("limitedAccessCode may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenLimitedAccessCodeTooLong() throws Exception {
    Referral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Referral/invalid/limitedAccessCodeTooLong.json"),
        Referral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("limitedAccessCode size must be 1"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenLimitedAccessCodeNotValidValue() throws Exception {
    Referral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Referral/invalid/limitedAccessCodeNotValidValue.json"),
        Referral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("limitedAccessCode must be one of [S, R, N]"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void successWhenLimitedAccessCodeIsS() throws Exception {
    Referral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Referral/valid/limitedAccessCodeS.json"), Referral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void successWhenLimitedAccessCodeIsR() throws Exception {
    Referral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Referral/valid/limitedAccessCodeR.json"), Referral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void successWhenLimitedAccessCodeIsN() throws Exception {
    Referral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Referral/valid/limitedAccessCodeN.json"), Referral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  /*
   * mandatedCrossReportReceivedDate Tests
   */
  @Test
  public void successWhenMandatedCrossReportReceivedDateEmpty() throws Exception {
    Referral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Referral/valid/mandatedCrossReportReceivedDateEmpty.json"),
        Referral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void successWhenMandatedCrossReportReceivedDateNull() throws Exception {
    Referral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Referral/valid/mandatedCrossReportReceivedDateNull.json"),
        Referral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void failsWhenMandatedCrossReportReceivedDateWrongFormat() throws Exception {
    Referral toCreate = MAPPER.readValue(
        fixture(
            "fixtures/domain/legacy/Referral/invalid/mandatedCrossReportReceivedDateWrongFormat.json"),
        Referral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class)
            .indexOf("mandatedCrossReportReceivedDate must be in the format of "),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * referralName Tests
   */
  @Test
  public void failsWhenReferralNameMissing() throws Exception {
    Referral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Referral/invalid/referralNameMissing.json"),
        Referral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("referralName may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenReferralNameNull() throws Exception {
    Referral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Referral/invalid/referralNameNull.json"), Referral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("referralName may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenReferralNameTooLong() throws Exception {
    Referral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Referral/invalid/referralNameTooLong.json"),
        Referral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("referralName size must be between 0 and 35"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * openAdequateCaseCode Tests
   */
  @Test
  public void failsWhenOpenAdequateCaseCodeMissing() throws Exception {
    Referral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Referral/invalid/openAdequateCaseCodeMissing.json"),
        Referral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("openAdequateCaseCode may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenOpenAdequateCaseCodeNull() throws Exception {
    Referral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Referral/invalid/openAdequateCaseCodeNull.json"),
        Referral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("openAdequateCaseCode may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenOpenAdequateCaseCodeTooLong() throws Exception {
    Referral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Referral/invalid/openAdequateCaseCodeTooLong.json"),
        Referral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("openAdequateCaseCode size must be 1"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * receivedDate Tests
   */
  @Test
  public void failsWhenReceivedDateMissing() throws Exception {
    Referral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Referral/invalid/receivedDateMissing.json"),
        Referral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("receivedDate must be in the format of"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenReceivedDateNull() throws Exception {
    Referral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Referral/invalid/receivedDateNull.json"), Referral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("receivedDate must be in the format of"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenReceivedDateWrongFormat() throws Exception {
    Referral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Referral/invalid/receivedDateWrongFormat.json"),
        Referral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("receivedDate must be in the format of"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenReceivedDateInvalid() throws Exception {
    Referral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Referral/invalid/receivedDateInvalid.json"),
        Referral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("receivedDate must be in the format of"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * receivedTime Tests
   */
  @Test
  public void failsWhenReceivedTimeMissing() throws Exception {
    Referral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Referral/invalid/receivedTimeMissing.json"),
        Referral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("receivedTime must be in the format of HH:mm:ss"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenReceivedTimeNull() throws Exception {
    Referral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Referral/invalid/receivedTimeNull.json"), Referral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("receivedTime must be in the format of HH:mm:ss"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenReceivedTimeWrongFormat() throws Exception {
    Referral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Referral/invalid/receivedTimeWrongFormat.json"),
        Referral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("receivedTime must be in the format of HH:mm:ss"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * referralResponseType Tests
   */
  @Test
  public void failsWhenReferralResponseTypeMissing() throws Exception {
    Referral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Referral/invalid/referralResponseTypeMissing.json"),
        Referral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("referralResponseType may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenReferralResponseTypeNull() throws Exception {
    Referral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Referral/invalid/referralResponseTypeNull.json"),
        Referral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("referralResponseType may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * referredToResourceType Tests
   */
  @Test
  public void failsWhenReferredToResourceTypeMissing() throws Exception {
    Referral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Referral/invalid/referredToResourceTypeMissing.json"),
        Referral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("referredToResourceType may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenReferredToResourceTypeNull() throws Exception {
    Referral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Referral/invalid/referredToResourceTypeNull.json"),
        Referral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("referredToResourceType may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * responseDeterminationDate Tests
   */
  @Test
  public void successWhenResponseDeterminationDateEmpty() throws Exception {
    Referral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Referral/valid/responseDeterminationDateEmpty.json"),
        Referral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void successWhenResponseDeterminationDateNull() throws Exception {
    Referral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Referral/valid/responseDeterminationDateNull.json"),
        Referral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void failsWhenResponseDeterminationDateWrongFormat() throws Exception {
    Referral toCreate = MAPPER.readValue(
        fixture(
            "fixtures/domain/legacy/Referral/invalid/responseDeterminationDatewrongFormat.json"),
        Referral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf(
        "responseDeterminationDate must be in the format of"), is(greaterThanOrEqualTo(0)));
  }

  /*
   * responseDeterminationTime Tests
   */
  @Test
  public void successWhenResponseDeterminationTimeEmpty() throws Exception {
    Referral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Referral/valid/responseDeterminationTimeEmpty.json"),
        Referral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void successWhenResponseDeterminationTimeNull() throws Exception {
    Referral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Referral/valid/responseDeterminationTimeNull.json"),
        Referral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void failsWhenResponseDeterminationTimeWrongFormat() throws Exception {
    Referral toCreate = MAPPER.readValue(
        fixture(
            "fixtures/domain/legacy/Referral/invalid/responseDeterminationTimeWrongFormat.json"),
        Referral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class)
            .indexOf("responseDeterminationTime must be in the format of HH:mm:ss"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * responseRationaleText Tests
   */
  @Test
  public void successWhenResponseRationaleTextEmpty() throws Exception {
    Referral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Referral/valid/responseRationaleTextEmpty.json"),
        Referral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void successWhenResponseRationaleTextNull() throws Exception {
    Referral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Referral/valid/responseRationaleTextNull.json"),
        Referral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void failsWhenResponseRationaleTextTooLong() throws Exception {
    Referral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Referral/invalid/responseRationaleTextTooLong.json"),
        Referral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf(
        "responseRationaleText size must be between 0 and 10"), is(greaterThanOrEqualTo(0)));
  }

  /*
   * screenerNoteText Tests
   */
  @Test
  public void successWhenScreenerNoteTextEmpty() throws Exception {
    Referral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Referral/valid/screenerNoteTextEmpty.json"),
        Referral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void successWhenScreenerNoteTextNull() throws Exception {
    Referral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Referral/valid/screenerNoteTextNull.json"), Referral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void failsWhenScreenerNoteTextTooLong() throws Exception {
    Referral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Referral/invalid/screenerNoteTextTooLong.json"),
        Referral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("screenerNoteText size must be between 0 and 10"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * specificsIncludedCode Tests
   */
  @Test
  public void failsWhenSpecificsIncludedCodeMissing() throws Exception {
    Referral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Referral/invalid/specificsIncludedCodeMissing.json"),
        Referral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("specificsIncludedCode may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenSpecificsIncludedCodeNull() throws Exception {
    Referral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Referral/invalid/specificsIncludedCodeNull.json"),
        Referral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("specificsIncludedCode may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenSpecificsIncludedCodeEmpty() throws Exception {
    Referral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Referral/invalid/specificsIncludedCodeEmpty.json"),
        Referral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("specificsIncludedCode may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenSpecificsIncludedCodeTooLong() throws Exception {
    Referral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Referral/invalid/specificsIncludedCodeTooLong.json"),
        Referral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("specificsIncludedCode size must be 1"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * sufficientInformationCode Tests
   */
  @Test
  public void failsWhenSufficientInformationCodeMissing() throws Exception {
    Referral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Referral/invalid/sufficientInformationCodeMissing.json"),
        Referral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("sufficientInformationCode may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenSufficientInformationCodeNull() throws Exception {
    Referral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Referral/invalid/sufficientInformationCodeNull.json"),
        Referral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("sufficientInformationCode may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenSufficientInformationCodeEmpty() throws Exception {
    Referral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Referral/invalid/sufficientInformationCodeEmpty.json"),
        Referral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("sufficientInformationCode may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenSufficientInformationCodeTooLong() throws Exception {
    Referral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Referral/invalid/sufficientInformationCodeTooLong.json"),
        Referral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("sufficientInformationCode size must be 1"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * unfoundedSeriesCode Tests
   */
  @Test
  public void failsWhenUnfoundedSeriesCodeMissing() throws Exception {
    Referral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Referral/invalid/unfoundedSeriesCodeMissing.json"),
        Referral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("unfoundedSeriesCode may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenUnfoundedSeriesCodeNull() throws Exception {
    Referral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Referral/invalid/unfoundedSeriesCodeNull.json"),
        Referral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("unfoundedSeriesCode may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenUnfoundedSeriesCodeEmpty() throws Exception {
    Referral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Referral/invalid/unfoundedSeriesCodeEmpty.json"),
        Referral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("unfoundedSeriesCode may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenUnfoundedSeriesCodeTooLong() throws Exception {
    Referral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Referral/invalid/unfoundedSeriesCodeTooLong.json"),
        Referral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("unfoundedSeriesCode size must be 1"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * linkToPrimaryReferralId Tests
   */
  @Test
  public void successWhenLinkToPrimaryReferralIdEmpty() throws Exception {
    Referral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Referral/valid/linkToPrimaryReferralIdEmpty.json"),
        Referral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void successWhenLinkToPrimaryReferralIdNull() throws Exception {
    Referral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Referral/valid/linkToPrimaryReferralIdNull.json"),
        Referral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void failsWhenLinkToPrimaryReferralIdTooLong() throws Exception {
    Referral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Referral/invalid/linkToPrimaryReferralIdTooLong.json"),
        Referral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf(
        "linkToPrimaryReferralId size must be between 0 and 10"), is(greaterThanOrEqualTo(0)));
  }

  /*
   * allegesAbuseOccurredAtAddressId Tests
   */
  @Test
  public void successWhenAllegesAbuseOccurredAtAddressIdEmpty() throws Exception {
    Referral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Referral/valid/allegesAbuseOccurredAtAddressIdEmpty.json"),
        Referral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void successWhenAllegesAbuseOccurredAtAddressIdNull() throws Exception {
    Referral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Referral/valid/allegesAbuseOccurredAtAddressIdNull.json"),
        Referral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void failsWhenAllegesAbuseOccurredAtAddressIdTooLong() throws Exception {
    Referral toCreate = MAPPER.readValue(
        fixture(
            "fixtures/domain/legacy/Referral/invalid/allegesAbuseOccurredAtAddressIdTooLong.json"),
        Referral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class)
            .indexOf("allegesAbuseOccurredAtAddressId size must be between 0 and 10"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * firstResponseDeterminedByStaffPersonId Tests
   */
  @Test
  public void successWhenFirstResponseDeterminedByStaffPersonIdEmpty() throws Exception {
    Referral toCreate = MAPPER.readValue(
        fixture(
            "fixtures/domain/legacy/Referral/valid/firstResponseDeterminedByStaffPersonIdEmpty.json"),
        Referral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void successWhenFirstResponseDeterminedByStaffPersonIdNull() throws Exception {
    Referral toCreate = MAPPER.readValue(
        fixture(
            "fixtures/domain/legacy/Referral/valid/firstResponseDeterminedByStaffPersonIdNull.json"),
        Referral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void failsWhenFirstResponseDeterminedByStaffPersonIdTooLong() throws Exception {
    Referral toCreate = MAPPER.readValue(
        fixture(
            "fixtures/domain/legacy/Referral/invalid/firstResponseDeterminedByStaffPersonIdTooLong.json"),
        Referral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class)
            .indexOf("firstResponseDeterminedByStaffPersonId size must be between 0 and 3"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * primaryContactStaffPersonId Tests
   */
  @Test
  public void failsWhenPrimaryContactStaffPersonIdMissing() throws Exception {
    Referral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Referral/invalid/primaryContactStaffPersonIdMissing.json"),
        Referral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("primaryContactStaffPersonId may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenPrimaryContactStaffPersonIdNull() throws Exception {
    Referral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Referral/invalid/primaryContactStaffPersonIdNull.json"),
        Referral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("primaryContactStaffPersonId may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenPrimaryContactStaffPersonIdEmpty() throws Exception {
    Referral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Referral/invalid/primaryContactStaffPersonIdEmpty.json"),
        Referral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("primaryContactStaffPersonId may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenPrimaryContactStaffPersonIdTooLong() throws Exception {
    Referral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Referral/invalid/primaryContactStaffPersonIdTooLong.json"),
        Referral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class)
            .indexOf("primaryContactStaffPersonId size must be between 3 and 3"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * countySpecificCode Tests
   */
  @Test
  public void failsWhenCountySpecificCodeMissing() throws Exception {
    Referral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Referral/invalid/countySpecificCodeMissing.json"),
        Referral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("countySpecificCode may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenCountySpecificCodeNull() throws Exception {
    Referral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Referral/invalid/countySpecificCodeNull.json"),
        Referral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("countySpecificCode may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenCountySpecificCodeEmpty() throws Exception {
    Referral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Referral/invalid/countySpecificCodeEmpty.json"),
        Referral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("countySpecificCode may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenCountySpecificCodeTooLong() throws Exception {
    Referral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Referral/invalid/countySpecificCodeTooLong.json"),
        Referral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class)
        .indexOf("countySpecificCode size must be between 1 and 2"), is(greaterThanOrEqualTo(0)));
  }

  /*
   * specialProjectReferralIndicator Tests
   */
  @Test
  public void failsWhenSpecialProjectReferralIndicatorMissing() throws Exception {
    Referral toCreate = MAPPER.readValue(
        fixture(
            "fixtures/domain/legacy/Referral/invalid/specialProjectReferralIndicatorMissing.json"),
        Referral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class)
        .indexOf("specialProjectReferralIndicator may not be null"), is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenSpecialProjectReferralIndicatorNull() throws Exception {
    Referral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Referral/invalid/specialProjectReferralIndicatorNull.json"),
        Referral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class)
        .indexOf("specialProjectReferralIndicator may not be null"), is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenSpecialProjectReferralIndicatorEmpty() throws Exception {
    Referral toCreate = MAPPER.readValue(
        fixture(
            "fixtures/domain/legacy/Referral/invalid/specialProjectReferralIndicatorEmpty.json"),
        Referral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class)
        .indexOf("specialProjectReferralIndicator may not be null"), is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenSpecialProjectReferralIndicatorAllWhitespace() throws Exception {
    Referral toCreate = MAPPER.readValue(
        fixture(
            "fixtures/domain/legacy/Referral/invalid/specialProjectReferralIndicatorAllWhitespace.json"),
        Referral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class)
        .indexOf("specialProjectReferralIndicator may not be null"), is(greaterThanOrEqualTo(0)));
  }

  /*
   * zippyCreatedIndicator Tests
   */
  @Test
  public void failsWhenZippyCreatedIndicatorMissing() throws Exception {
    Referral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Referral/invalid/zippyCreatedIndicatorMissing.json"),
        Referral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("zippyCreatedIndicator may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenZippyCreatedIndicatorNull() throws Exception {
    Referral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Referral/invalid/zippyCreatedIndicatorNull.json"),
        Referral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("zippyCreatedIndicator may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenZippyCreatedIndicatorEmpty() throws Exception {
    Referral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Referral/invalid/zippyCreatedIndicatorEmpty.json"),
        Referral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("zippyCreatedIndicator may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenZippyCreatedIndicatorAllWhitespace() throws Exception {
    Referral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Referral/invalid/zippyCreatedIndicatorAllWhitespace.json"),
        Referral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("zippyCreatedIndicator may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * homelessIndicator Tests
   */
  @Test
  public void failsWhenHomelessIndicatorMissing() throws Exception {
    Referral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Referral/invalid/homelessIndicatorMissing.json"),
        Referral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("homelessIndicator may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenHomelessIndicatorNull() throws Exception {
    Referral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Referral/invalid/homelessIndicatorNull.json"),
        Referral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("homelessIndicator may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenHomelessIndicatorEmpty() throws Exception {
    Referral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Referral/invalid/homelessIndicatorEmpty.json"),
        Referral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("homelessIndicator may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenHomelessIndicatorAllWhitespace() throws Exception {
    Referral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Referral/invalid/homelessIndicatorAllWhitespace.json"),
        Referral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("homelessIndicator may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * familyRefusedServicesIndicator Tests
   */
  @Test
  public void failsWhenFamilyRefusedServicesIndicatorMissing() throws Exception {
    Referral toCreate = MAPPER.readValue(
        fixture(
            "fixtures/domain/legacy/Referral/invalid/familyRefusedServicesIndicatorMissing.json"),
        Referral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("familyRefusedServicesIndicator may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenFamilyRefusedServicesIndicatorNull() throws Exception {
    Referral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Referral/invalid/familyRefusedServicesIndicatorNull.json"),
        Referral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("familyRefusedServicesIndicator may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenFamilyRefusedServicesIndicatorEmpty() throws Exception {
    Referral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Referral/invalid/familyRefusedServicesIndicatorEmpty.json"),
        Referral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("familyRefusedServicesIndicator may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenFamilyRefusedServicesIndicatorAllWhitespace() throws Exception {
    Referral toCreate = MAPPER.readValue(
        fixture(
            "fixtures/domain/legacy/Referral/invalid/familyRefusedServicesIndicatorAllWhitespace.json"),
        Referral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("familyRefusedServicesIndicator may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * firstEvaluatedOutApprovalDate Tests
   */
  @Test
  public void successWhenFirstEvaluatedOutApprovalDateEmpty() throws Exception {
    Referral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Referral/valid/firstEvaluatedOutApprovalDateEmpty.json"),
        Referral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void successWhenFirstEvaluatedOutApprovalDateNull() throws Exception {
    Referral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Referral/valid/firstEvaluatedOutApprovalDateNull.json"),
        Referral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void failsWhenFirstEvaluatedOutApprovalDateWrongFormat() throws Exception {
    Referral toCreate = MAPPER.readValue(
        fixture(
            "fixtures/domain/legacy/Referral/invalid/firstEvaluatedOutApprovalDateWrongFormat.json"),
        Referral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class)
            .indexOf("firstEvaluatedOutApprovalDate must be in the format of yyyy-MM-dd"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * responsibleAgencyCode Tests
   */
  @Test
  public void failsWhenResponsibleAgencyCodeMissing() throws Exception {
    Referral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Referral/invalid/responsibleAgencyCodeMissing.json"),
        Referral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("responsibleAgencyCode may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenResponsibleAgencyCodeNull() throws Exception {
    Referral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Referral/invalid/responsibleAgencyCodeNull.json"),
        Referral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("responsibleAgencyCode may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenResponsibleAgencyCodeEmpty() throws Exception {
    Referral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Referral/invalid/responsibleAgencyCodeEmpty.json"),
        Referral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("responsibleAgencyCode may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenResponsibleAgencyCodeTooLong() throws Exception {
    Referral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Referral/invalid/responsibleAgencyCodeTooLong.json"),
        Referral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("responsibleAgencyCode size must be 1"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenResponsibleAgencyCodeNotValidValue() throws Exception {
    Referral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Referral/invalid/responsibleAgencyCodeNotValidValue.json"),
        Referral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class)
            .indexOf("responsibleAgencyCode must be one of [C, P, O, A, S, I, K, M]"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void successWhenResponsibleAgencyCodeIsC() throws Exception {
    Referral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Referral/valid/responsibleAgencyCodeC.json"),
        Referral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void successWhenResponsibleAgencyCodeIsP() throws Exception {
    Referral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Referral/valid/responsibleAgencyCodeP.json"),
        Referral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void successWhenResponsibleAgencyCodeIsO() throws Exception {
    Referral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Referral/valid/responsibleAgencyCodeO.json"),
        Referral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void successWhenResponsibleAgencyCodeIsA() throws Exception {
    Referral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Referral/valid/responsibleAgencyCodeA.json"),
        Referral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void successWhenResponsibleAgencyCodeIsS() throws Exception {
    Referral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Referral/valid/responsibleAgencyCodeS.json"),
        Referral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void successWhenResponsibleAgencyCodeIsI() throws Exception {
    Referral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Referral/valid/responsibleAgencyCodeI.json"),
        Referral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void successWhenResponsibleAgencyCodeIsK() throws Exception {
    Referral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Referral/valid/responsibleAgencyCodeK.json"),
        Referral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void successWhenResponsibleAgencyCodeIsM() throws Exception {
    Referral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Referral/valid/responsibleAgencyCodeM.json"),
        Referral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  /*
   * limitedAccessGovtAgencyType Tests
   */
  @Test
  public void successWhenLimitedAccessGovtAgencyTypeEmpty() throws Exception {
    Referral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Referral/valid/limitedAccessGovtAgencyTypeEmpty.json"),
        Referral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void successWhenLimitedAccessGovtAgencyTypeNull() throws Exception {
    Referral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Referral/valid/limitedAccessGovtAgencyTypeNull.json"),
        Referral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  /*
   * limitedAccessDate Tests
   */
  @Test
  public void successWhenLimitedAccessDateEmpty() throws Exception {
    Referral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Referral/valid/limitedAccessDateEmpty.json"),
        Referral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void successWhenLimitedAccessDateNull() throws Exception {
    Referral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Referral/valid/limitedAccessDateNull.json"),
        Referral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void failsWhenLimitedAccessDateWrongFormat() throws Exception {
    Referral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Referral/invalid/limitedAccessDateWrongFormat.json"),
        Referral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf(
        "limitedAccessDate must be in the format of yyyy-MM-dd"), is(greaterThanOrEqualTo(0)));
  }

  /*
   * limitedAccessDesc Tests
   */
  @Test
  public void successWhenLimitedAccessDescEmpty() throws Exception {
    Referral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Referral/valid/limitedAccessDescEmpty.json"),
        Referral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void successWhenLimitedAccessDescNull() throws Exception {
    Referral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Referral/valid/limitedAccessDescNull.json"),
        Referral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  /*
   * originalClosureDate Tests
   */
  @Test
  public void successWhenOriginalClosureDateEmpty() throws Exception {
    Referral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Referral/valid/originalClosureDateEmpty.json"),
        Referral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void successWhenOriginalClosureDateNull() throws Exception {
    Referral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Referral/valid/originalClosureDateNull.json"),
        Referral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void failsWhenOriginalClosureDateWrongFormat() throws Exception {
    Referral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Referral/invalid/originalClosureDateWrongFormat.json"),
        Referral.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class)
            .indexOf("originalClosureDate must be in the format of yyyy-MM-dd"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * Utils
   */
  private Referral validReferral() {
    return new Referral(false, false, false, "A3CDEOm0Ab", new Short((short) 122), false,
        "2000-03-03", new Short((short) 409), "current", "1234567ABC", "A3B7sSC0Ab", "asdbdfdrsd",
        false, false, new Short((short) 1118), "A", false, "N", "2000-01-31",
        "Verification (R3)                  ", "A", "2000-01-01", "16:41:49",
        new Short((short) 1520), new Short((short) 0), "2000-01-31", "16:41:49", "1234567ABC",
        "1234567ABC", "A", "A", "A", "1234567ABC", "1234567ABC", "0Ab", "q1p", "51", false, false,
        false, false, "2000-05-05", "C", new Short((short) 1234), "2000-05-05", "thjkl",
        "2000-05-05");
  }
}
