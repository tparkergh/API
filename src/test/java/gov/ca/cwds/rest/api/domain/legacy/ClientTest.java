package gov.ca.cwds.rest.api.domain.legacy;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import gov.ca.cwds.rest.api.domain.DomainObject;
import gov.ca.cwds.rest.api.domain.cms.Client;
import gov.ca.cwds.rest.core.Api;
import gov.ca.cwds.rest.jdbi.CrudsDao;
import gov.ca.cwds.rest.jdbi.DataAccessEnvironment;
import gov.ca.cwds.rest.resources.cms.ClientResource;
import io.dropwizard.jackson.Jackson;
import io.dropwizard.testing.junit.ResourceTestRule;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

public class ClientTest {
  private static final String ROOT_RESOURCE = "/" + Api.RESOURCE_CLIENT + "/";;
  private static final ClientResource mockedClientResource = mock(ClientResource.class);

  @ClassRule
  public static final ResourceTestRule resources =
      ResourceTestRule.builder().addResource(mockedClientResource).build();
  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

  private final static DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
  @SuppressWarnings("unused")
  private final static DateFormat tf = new SimpleDateFormat("yyyy-MM-dd-HH.mm.ss.SSS");

  @Before
  public void setup() {

    try {
      Client vc = validClient();

      @SuppressWarnings("rawtypes")
      CrudsDao crudsDao = mock(CrudsDao.class);
      DataAccessEnvironment.register(gov.ca.cwds.rest.api.persistence.cms.Client.class, crudsDao);
      when(crudsDao.find(any()))
          .thenReturn(mock(gov.ca.cwds.rest.api.persistence.cms.Client.class));

      when(mockedClientResource.create(eq(vc)))
          .thenReturn(Response.status(Response.Status.NO_CONTENT).entity(null).build());

    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  @Test
  public void equalsHashCodeWork() {
    EqualsVerifier.forClass(Client.class).suppress(Warning.NONFINAL_FIELDS).verify();
  }

  @Test
  public void serializesToJSON() throws Exception {
    final String expected = MAPPER.writeValueAsString(
        MAPPER.readValue(fixture("fixtures/domain/legacy/Client/valid/valid.json"), Client.class));

    assertThat(MAPPER.writeValueAsString(validClient()), is(equalTo(expected)));
  }

  @Test
  public void deserializesFromJSON() throws Exception {
    assertThat(
        MAPPER.readValue(fixture("fixtures/domain/legacy/Client/valid/valid.json"), Client.class),
        is(equalTo(validClient())));
  }

  @Test
  public void failsWhenNotEquals() throws Exception {
    final String expected = MAPPER.writeValueAsString(
        MAPPER.readValue(fixture("fixtures/domain/legacy/Client/valid/valid.json"), Client.class));
    final String notExpected = MAPPER.writeValueAsString(MAPPER.readValue(
        fixture("fixtures/domain/legacy/Client/valid/optionalsNotIncluded.json"), Client.class));
    assertThat(expected, not(equalTo(notExpected)));

  }

  /*
   * Constructor Tests
   */
  @Test
  public void persistentObjectConstructorTest() throws Exception {

    gov.ca.cwds.rest.api.persistence.cms.Client persistent =
        new gov.ca.cwds.rest.api.persistence.cms.Client(validClient(), "2016-12-06");

    Client totest = new Client(persistent);
    assertThat(totest.getAdjudicatedDelinquentIndicator(), is(
        equalTo(DomainObject.uncookBooleanString(persistent.getAdjudicatedDelinquentIndicator()))));
    assertThat(totest.getAdoptionStatusCode(), is(equalTo(persistent.getAdoptionStatusCode())));
    assertThat(totest.getAlienRegistrationNumber(),
        is(equalTo(persistent.getAlienRegistrationNumber())));
    assertThat(totest.getBirthCity(), is(equalTo(persistent.getBirthCity())));
    assertThat(totest.getBirthCountryCodeType(), is(equalTo(persistent.getBirthCountryCodeType())));
    assertThat(totest.getBirthDate(), is(equalTo(df.format(persistent.getBirthDate()))));
    assertThat(totest.getBirthFacilityName(), is(equalTo(persistent.getBirthFacilityName())));
    assertThat(totest.getBirthplaceVerifiedIndicator(),
        is(equalTo(DomainObject.uncookBooleanString(persistent.getBirthplaceVerifiedIndicator()))));
    assertThat(totest.getBirthStateCodeType(), is(equalTo(persistent.getBirthStateCodeType())));
    assertThat(totest.getChildClientIndicatorVar(),
        is(equalTo(DomainObject.uncookBooleanString(persistent.getChildClientIndicatorVar()))));
    assertThat(totest.getClientIndexNumber(), is(equalTo(persistent.getClientIndexNumber())));
    assertThat(totest.getCommentDescription(), is(equalTo(persistent.getCommentDescription())));
    assertThat(totest.getCommonFirstName(), is(equalTo(persistent.getCommonFirstName())));
    assertThat(totest.getCommonLastName(), is(equalTo(persistent.getCommonLastName())));
    assertThat(totest.getCommonMiddleName(), is(equalTo(persistent.getCommonMiddleName())));
    assertThat(totest.getConfidentialityActionDate(),
        is(equalTo(df.format(persistent.getConfidentialityActionDate()))));
    assertThat(totest.getConfidentialityInEffectIndicator(), is(equalTo(
        DomainObject.uncookBooleanString(persistent.getConfidentialityInEffectIndicator()))));
    assertThat(totest.getCreationDate(), is(equalTo(df.format(persistent.getCreationDate()))));
    assertThat(totest.getCurrCaChildrenServIndicator(),
        is(equalTo(DomainObject.uncookBooleanString(persistent.getCurrCaChildrenServIndicator()))));
    assertThat(totest.getCurrentlyOtherDescription(),
        is(equalTo(persistent.getCurrentlyOtherDescription())));
    assertThat(totest.getCurrentlyRegionalCenterIndicator(), is(equalTo(
        DomainObject.uncookBooleanString(persistent.getCurrentlyRegionalCenterIndicator()))));
    assertThat(totest.getDeathDate(), is(equalTo(df.format(persistent.getDeathDate()))));
    assertThat(totest.getDeathDateVerifiedIndicator(),
        is(equalTo(DomainObject.uncookBooleanString(persistent.getDeathDateVerifiedIndicator()))));
    assertThat(totest.getDeathReasonText(), is(equalTo(persistent.getDeathReasonText())));
    assertThat(totest.getDriverLicenseNumber(), is(equalTo(persistent.getDriverLicenseNumber())));
    assertThat(totest.getDriverLicenseStateCodeType(),
        is(equalTo(persistent.getDriverLicenseStateCodeType())));
    assertThat(totest.getEmailAddress(), is(equalTo(persistent.getEmailAddress())));
    assertThat(totest.getEstimatedDobCode(), is(equalTo(persistent.getEstimatedDobCode())));
    assertThat(totest.getEthUnableToDetReasonCode(),
        is(equalTo(persistent.getEthUnableToDetReasonCode())));
    assertThat(totest.getFatherParentalRightTermDate(),
        is(equalTo(df.format(persistent.getFatherParentalRightTermDate()))));
    assertThat(totest.getGenderCode(), is(equalTo(persistent.getGenderCode())));
    assertThat(totest.getHealthSummaryText(), is(equalTo(persistent.getHealthSummaryText())));
    assertThat(totest.getHispUnableToDetReasonCode(),
        is(equalTo(persistent.getHispUnableToDetReasonCode())));
    assertThat(totest.getHispanicOriginCode(), is(equalTo(persistent.getHispanicOriginCode())));
    assertThat(totest.getImmigrationCountryCodeType(),
        is(equalTo(persistent.getImmigrationCountryCodeType())));
    assertThat(totest.getImmigrationStatusType(),
        is(equalTo(persistent.getImmigrationStatusType())));
    assertThat(totest.getIncapacitatedParentCode(),
        is(equalTo(persistent.getIncapacitatedParentCode())));
    assertThat(totest.getIndividualHealthCarePlanIndicator(), is(equalTo(
        DomainObject.uncookBooleanString(persistent.getIndividualHealthCarePlanIndicator()))));
    assertThat(totest.getLimitationOnScpHealthIndicator(), is(
        equalTo(DomainObject.uncookBooleanString(persistent.getLimitationOnScpHealthIndicator()))));
    assertThat(totest.getLiterateCode(), is(equalTo(persistent.getLiterateCode())));
    assertThat(totest.getMaritalCohabitatnHstryIndicatorVar(), is(equalTo(
        DomainObject.uncookBooleanString(persistent.getMaritalCohabitatnHstryIndicatorVar()))));
    assertThat(totest.getMaritalStatusType(), is(equalTo(persistent.getMaritalStatusType())));
    assertThat(totest.getMilitaryStatusCode(), is(equalTo(persistent.getMilitaryStatusCode())));
    assertThat(totest.getMotherParentalRightTermDate(),
        is(equalTo(df.format(persistent.getMotherParentalRightTermDate()))));
    assertThat(totest.getNamePrefixDescription(),
        is(equalTo(persistent.getNamePrefixDescription())));
    assertThat(totest.getNameType(), is(equalTo(persistent.getNameType())));
    assertThat(totest.getOutstandingWarrantIndicator(),
        is(equalTo(DomainObject.uncookBooleanString(persistent.getOutstandingWarrantIndicator()))));
    assertThat(totest.getPrevCaChildrenServIndicator(),
        is(equalTo(DomainObject.uncookBooleanString(persistent.getPrevCaChildrenServIndicator()))));
    assertThat(totest.getPrevRegionalCenterIndicator(),
        is(equalTo(DomainObject.uncookBooleanString(persistent.getPrevRegionalCenterIndicator()))));
    assertThat(totest.getPrevOtherDescription(), is(equalTo(persistent.getPrevOtherDescription())));
    assertThat(totest.getPrimaryEthnicityType(), is(equalTo(persistent.getPrimaryEthnicityType())));
    assertThat(totest.getPrimaryLanguageType(), is(equalTo(persistent.getPrimaryLanguageType())));
    assertThat(totest.getReligionType(), is(equalTo(persistent.getReligionType())));
    assertThat(totest.getSecondaryLanguageType(),
        is(equalTo(persistent.getSecondaryLanguageType())));
    assertThat(totest.getSensitiveHlthInfoOnFileIndicator(), is(equalTo(
        DomainObject.uncookBooleanString(persistent.getSensitiveHlthInfoOnFileIndicator()))));
    assertThat(totest.getSensitivityIndicator(), is(equalTo(persistent.getSensitivityIndicator())));
    assertThat(totest.getSoc158PlacementCode(), is(equalTo(persistent.getSoc158PlacementCode())));
    assertThat(totest.getSoc158SealedClientIndicator(),
        is(equalTo(DomainObject.uncookBooleanString(persistent.getSoc158SealedClientIndicator()))));
    assertThat(totest.getSocialSecurityNumChangedCode(),
        is(equalTo(persistent.getSocialSecurityNumChangedCode())));
    assertThat(totest.getSocialSecurityNumber(), is(equalTo(persistent.getSocialSecurityNumber())));
    assertThat(totest.getSuffixTitleDescription(),
        is(equalTo(persistent.getSuffixTitleDescription())));
    assertThat(totest.getTribalAncestryClientIndicatorVar(), is(equalTo(
        DomainObject.uncookBooleanString(persistent.getTribalAncestryClientIndicatorVar()))));
    assertThat(totest.getTribalMembrshpVerifctnIndicatorVar(), is(equalTo(
        DomainObject.uncookBooleanString(persistent.getTribalMembrshpVerifctnIndicatorVar()))));
    assertThat(totest.getUnemployedParentCode(), is(equalTo(persistent.getUnemployedParentCode())));
    assertThat(totest.getZippyCreatedIndicator(),
        is(equalTo(DomainObject.uncookBooleanString(persistent.getZippyCreatedIndicator()))));

  }

  @Test
  public void jsonCreatorConstructorTest() throws Exception {

    Client vc = validClient();

    Client totest = new Client(vc.getAdjudicatedDelinquentIndicator(), vc.getAdoptionStatusCode(),
        vc.getAlienRegistrationNumber(), vc.getBirthCity(), vc.getBirthCountryCodeType(),
        vc.getBirthDate(), vc.getBirthFacilityName(), vc.getBirthStateCodeType(),
        vc.getBirthplaceVerifiedIndicator(), vc.getChildClientIndicatorVar(),
        vc.getClientIndexNumber(), vc.getCommentDescription(), vc.getCommonFirstName(),
        vc.getCommonLastName(), vc.getCommonMiddleName(), vc.getConfidentialityActionDate(),
        vc.getConfidentialityInEffectIndicator(), vc.getCreationDate(),
        vc.getCurrCaChildrenServIndicator(), vc.getCurrentlyOtherDescription(),
        vc.getCurrentlyRegionalCenterIndicator(), vc.getDeathDate(),
        vc.getDeathDateVerifiedIndicator(), vc.getDeathPlace(), vc.getDeathReasonText(),
        vc.getDriverLicenseNumber(), vc.getDriverLicenseStateCodeType(), vc.getEmailAddress(),
        vc.getEstimatedDobCode(), vc.getEthUnableToDetReasonCode(),
        vc.getFatherParentalRightTermDate(), vc.getGenderCode(), vc.getHealthSummaryText(),
        vc.getHispUnableToDetReasonCode(), vc.getHispanicOriginCode(), vc.getId(),
        vc.getImmigrationCountryCodeType(), vc.getImmigrationStatusType(),
        vc.getIncapacitatedParentCode(), vc.getIndividualHealthCarePlanIndicator(),
        vc.getLimitationOnScpHealthIndicator(), vc.getLiterateCode(),
        vc.getMaritalCohabitatnHstryIndicatorVar(), vc.getMaritalStatusType(),
        vc.getMilitaryStatusCode(), vc.getMotherParentalRightTermDate(),
        vc.getNamePrefixDescription(), vc.getNameType(), vc.getOutstandingWarrantIndicator(),
        vc.getPrevCaChildrenServIndicator(), vc.getPrevOtherDescription(),
        vc.getPrevRegionalCenterIndicator(), vc.getPrimaryEthnicityType(),
        vc.getPrimaryLanguageType(), vc.getReligionType(), vc.getSecondaryLanguageType(),
        vc.getSensitiveHlthInfoOnFileIndicator(), vc.getSensitivityIndicator(),
        vc.getSoc158PlacementCode(), vc.getSoc158SealedClientIndicator(),
        vc.getSocialSecurityNumChangedCode(), vc.getSocialSecurityNumber(),
        vc.getSuffixTitleDescription(), vc.getTribalAncestryClientIndicatorVar(),
        vc.getTribalMembrshpVerifctnIndicatorVar(), vc.getUnemployedParentCode(),
        vc.getZippyCreatedIndicator());

    assertThat(totest.getAdjudicatedDelinquentIndicator(),
        is(equalTo((vc.getAdjudicatedDelinquentIndicator()))));
    assertThat(totest.getAdoptionStatusCode(), is(equalTo(vc.getAdoptionStatusCode())));
    assertThat(totest.getAlienRegistrationNumber(), is(equalTo(vc.getAlienRegistrationNumber())));
    assertThat(totest.getBirthCity(), is(equalTo(vc.getBirthCity())));
    assertThat(totest.getBirthCountryCodeType(), is(equalTo(vc.getBirthCountryCodeType())));
    assertThat(totest.getBirthDate(), is(equalTo(vc.getBirthDate())));
    assertThat(totest.getBirthFacilityName(), is(equalTo(vc.getBirthFacilityName())));
    assertThat(totest.getBirthplaceVerifiedIndicator(),
        is(equalTo(vc.getBirthplaceVerifiedIndicator())));
    assertThat(totest.getBirthStateCodeType(), is(equalTo(vc.getBirthStateCodeType())));
    assertThat(totest.getChildClientIndicatorVar(), is(equalTo(vc.getChildClientIndicatorVar())));
    assertThat(totest.getClientIndexNumber(), is(equalTo(vc.getClientIndexNumber())));
    assertThat(totest.getCommentDescription(), is(equalTo(vc.getCommentDescription())));
    assertThat(totest.getCommonFirstName(), is(equalTo(vc.getCommonFirstName())));
    assertThat(totest.getCommonLastName(), is(equalTo(vc.getCommonLastName())));
    assertThat(totest.getCommonMiddleName(), is(equalTo(vc.getCommonMiddleName())));
    assertThat(totest.getConfidentialityActionDate(),
        is(equalTo(vc.getConfidentialityActionDate())));
    assertThat(totest.getConfidentialityInEffectIndicator(),
        is(equalTo(vc.getConfidentialityInEffectIndicator())));
    assertThat(totest.getCreationDate(), is(equalTo(vc.getCreationDate())));
    assertThat(totest.getCurrCaChildrenServIndicator(),
        is(equalTo(vc.getCurrCaChildrenServIndicator())));
    assertThat(totest.getCurrentlyOtherDescription(),
        is(equalTo(vc.getCurrentlyOtherDescription())));
    assertThat(totest.getCurrentlyRegionalCenterIndicator(),
        is(equalTo(vc.getCurrentlyRegionalCenterIndicator())));
    assertThat(totest.getDeathDate(), is(equalTo(vc.getDeathDate())));
    assertThat(totest.getDeathDateVerifiedIndicator(),
        is(equalTo(vc.getDeathDateVerifiedIndicator())));
    assertThat(totest.getDeathReasonText(), is(equalTo(vc.getDeathReasonText())));
    assertThat(totest.getDriverLicenseNumber(), is(equalTo(vc.getDriverLicenseNumber())));
    assertThat(totest.getDriverLicenseStateCodeType(),
        is(equalTo(vc.getDriverLicenseStateCodeType())));
    assertThat(totest.getEmailAddress(), is(equalTo(vc.getEmailAddress())));
    assertThat(totest.getEstimatedDobCode(), is(equalTo(vc.getEstimatedDobCode())));
    assertThat(totest.getEthUnableToDetReasonCode(), is(equalTo(vc.getEthUnableToDetReasonCode())));
    assertThat(totest.getFatherParentalRightTermDate(),
        is(equalTo(vc.getFatherParentalRightTermDate())));
    assertThat(totest.getGenderCode(), is(equalTo(vc.getGenderCode())));
    assertThat(totest.getHealthSummaryText(), is(equalTo(vc.getHealthSummaryText())));
    assertThat(totest.getHispUnableToDetReasonCode(),
        is(equalTo(vc.getHispUnableToDetReasonCode())));
    assertThat(totest.getHispanicOriginCode(), is(equalTo(vc.getHispanicOriginCode())));
    assertThat(totest.getImmigrationCountryCodeType(),
        is(equalTo(vc.getImmigrationCountryCodeType())));
    assertThat(totest.getImmigrationStatusType(), is(equalTo(vc.getImmigrationStatusType())));
    assertThat(totest.getIncapacitatedParentCode(), is(equalTo(vc.getIncapacitatedParentCode())));
    assertThat(totest.getIndividualHealthCarePlanIndicator(),
        is(equalTo(vc.getIndividualHealthCarePlanIndicator())));
    assertThat(totest.getLimitationOnScpHealthIndicator(),
        is(equalTo(vc.getLimitationOnScpHealthIndicator())));
    assertThat(totest.getLiterateCode(), is(equalTo(vc.getLiterateCode())));
    assertThat(totest.getMaritalCohabitatnHstryIndicatorVar(),
        is(equalTo(vc.getMaritalCohabitatnHstryIndicatorVar())));
    assertThat(totest.getMaritalStatusType(), is(equalTo(vc.getMaritalStatusType())));
    assertThat(totest.getMilitaryStatusCode(), is(equalTo(vc.getMilitaryStatusCode())));
    assertThat(totest.getMotherParentalRightTermDate(),
        is(equalTo(vc.getMotherParentalRightTermDate())));
    assertThat(totest.getNamePrefixDescription(), is(equalTo(vc.getNamePrefixDescription())));
    assertThat(totest.getNameType(), is(equalTo(vc.getNameType())));
    assertThat(totest.getOutstandingWarrantIndicator(),
        is(equalTo(vc.getOutstandingWarrantIndicator())));
    assertThat(totest.getPrevCaChildrenServIndicator(),
        is(equalTo(vc.getPrevCaChildrenServIndicator())));
    assertThat(totest.getPrevRegionalCenterIndicator(),
        is(equalTo(vc.getPrevRegionalCenterIndicator())));
    assertThat(totest.getPrevOtherDescription(), is(equalTo(vc.getPrevOtherDescription())));
    assertThat(totest.getPrimaryEthnicityType(), is(equalTo(vc.getPrimaryEthnicityType())));
    assertThat(totest.getPrimaryLanguageType(), is(equalTo(vc.getPrimaryLanguageType())));
    assertThat(totest.getReligionType(), is(equalTo(vc.getReligionType())));
    assertThat(totest.getSecondaryLanguageType(), is(equalTo(vc.getSecondaryLanguageType())));
    assertThat(totest.getSensitiveHlthInfoOnFileIndicator(),
        is(equalTo(vc.getSensitiveHlthInfoOnFileIndicator())));
    assertThat(totest.getSensitivityIndicator(), is(equalTo(vc.getSensitivityIndicator())));
    assertThat(totest.getSoc158PlacementCode(), is(equalTo(vc.getSoc158PlacementCode())));
    assertThat(totest.getSoc158SealedClientIndicator(),
        is(equalTo(vc.getSoc158SealedClientIndicator())));
    assertThat(totest.getSocialSecurityNumChangedCode(),
        is(equalTo(vc.getSocialSecurityNumChangedCode())));
    assertThat(totest.getSocialSecurityNumber(), is(equalTo(vc.getSocialSecurityNumber())));
    assertThat(totest.getSuffixTitleDescription(), is(equalTo(vc.getSuffixTitleDescription())));
    assertThat(totest.getTribalAncestryClientIndicatorVar(),
        is(equalTo(vc.getTribalAncestryClientIndicatorVar())));
    assertThat(totest.getTribalMembrshpVerifctnIndicatorVar(),
        is(equalTo(vc.getTribalMembrshpVerifctnIndicatorVar())));
    assertThat(totest.getUnemployedParentCode(), is(equalTo(vc.getUnemployedParentCode())));
    assertThat(totest.getZippyCreatedIndicator(), is(equalTo(vc.getZippyCreatedIndicator())));

  }

  /*
   * Successful Tests
   */
  @Test
  public void successfulWithValid() throws Exception {
    Client toCreate =
        MAPPER.readValue(fixture("fixtures/domain/legacy/Client/valid/valid.json"), Client.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    String message = response.readEntity(String.class);
    System.out.print(message);
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void successfulWithOptionalsNotIncluded() throws Exception {
    Client toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Client/valid/optionalsNotIncluded.json"), Client.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    // String message = response.readEntity(String.class);
    // System.out.print(message);
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  /*
   * adoptionStatusCode Tests
   */
  @Test
  public void failsWhenAdoptionStatusCodeNull() throws Exception {
    Client toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Client/invalid/adoptionStatusCodeNull.json"), Client.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    // String message = response.readEntity(String.class);
    // System.out.print(message);
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("adoptionStatusCode may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }


  @Test
  public void failsWhenAdoptionStatusCodeEmpty() throws Exception {
    Client toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Client/invalid/adoptionStatusCodeEmpty.json"),
        Client.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    // String message = response.readEntity(String.class);
    // System.out.print(message);
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("adoptionStatusCode may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenAdoptionStatusCodeWhiteSpace() throws Exception {
    Client toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Client/invalid/adoptionStatusWhiteSpace.json"),
        Client.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("adoptionStatusCode must be one of"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenAdoptionStatusCodeInvalid() throws Exception {
    Client toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Client/invalid/adoptionStatusCodeInvalid.json"),
        Client.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    // String message = response.readEntity(String.class);
    // System.out.print(message);
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("adoptionStatusCode must be one of"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void successWhenAdoptionStatusCodeT() throws Exception {
    Client toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Client/valid/adoptionStatusCodeT.json"), Client.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void successWhenAdoptionStatusCodeP() throws Exception {
    Client toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Client/valid/adoptionStatusCodeP.json"), Client.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void successWhenAdoptionStatusCodeN() throws Exception {
    Client toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Client/valid/adoptionStatusCodeN.json"), Client.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void successWhenAdoptionStatusCodeA() throws Exception {
    Client toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Client/valid/adoptionStatusCodeA.json"), Client.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  /*
   * alienRegistrationNumber test
   */
  @Test
  public void failsWhenAlienRegistrationNumberNull() throws Exception {
    Client toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Client/invalid/alienRegistratioNumberNull.json"),
        Client.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("alienRegistrationNumber may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void sucessWhenAlienRegistrationNumberEmpty() throws Exception {
    Client toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Client/valid/alienRegistrationNumberEmpty.json"),
        Client.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));

  }

  @Test
  public void failsWhenAlienRegistrationNumberTooLong() throws Exception {
    Client toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Client/invalid/alienRegistratioNumberTooLong.json"),
        Client.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf(
        "alienRegistrationNumber size must be between 0 and 12"), is(greaterThanOrEqualTo(0)));

  }

  /*
   * birthCity test
   */
  @Test
  public void failsWhenBirthCityNull() throws Exception {
    Client toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Client/invalid/birthCityNull.json"), Client.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    // String message = response.readEntity(String.class);
    // System.out.print(message);
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("birthCity may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenBirthCityToLong() throws Exception {
    Client toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Client/invalid/birthCityToLong.json"), Client.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("birthCity size must be between 0 and 35"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void successWhenBirthCityEmpty() throws Exception {
    Client toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Client/valid/birthCityEmpty.json"), Client.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));

  }

  /*
   * birthCountryCodeType test
   */
  @Test
  public void failsWhenBirthCountryCodeTypeNull() throws Exception {
    Client toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Client/invalid/birthCountryCodeTypeNull.json"),
        Client.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("birthCountryCodeType may not be null"),
        is(greaterThanOrEqualTo(0)));

  }

  @Test
  public void failsWhenBirthCountryCodeTypeMissing() throws Exception {
    Client toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Client/invalid/birthCountryCodeTypeMissing.json"),
        Client.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("birthCountryCodeType may not be null"),
        is(greaterThanOrEqualTo(0)));

  }

  /*
   * birthDate test
   */
  @Test
  public void successWhenBirthDateNull() throws Exception {
    Client toCreate = MAPPER
        .readValue(fixture("fixtures/domain/legacy/Client/valid/birthDateNull.json"), Client.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void successWhenBirthDateBlank() throws Exception {
    Client toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Client/valid/birthDateBlank.json"), Client.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void failureWhenBirthDateInvalidFormat() throws Exception {
    Client toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Client/invalid/birthDateInvalidFormat.json"), Client.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    // String message = response.readEntity(String.class);
    // System.out.print(message);
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("birthDate must be in the format of"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * birthFacilityName
   */
  @Test
  public void successWhenBirthFacilityNameBlank() throws Exception {
    Client toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Client/valid/birthFacilityNameBlank.json"), Client.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));

  }

  @Test
  public void failureWhenBirthFacilityNameNull() throws Exception {
    Client toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Client/invalid/birthFacilityNameNull.json"), Client.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("birthFacilityName may not be null"),
        is(greaterThanOrEqualTo(0)));
  }


  /*
   * birthStateCodeType test
   */
  @Test
  public void failsWhenBirthStateCodeTypeNull() throws Exception {
    Client toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Client/invalid/birthStateCodeTypeNull.json"), Client.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("birthStateCodeType may not be null"),
        is(greaterThanOrEqualTo(0)));

  }

  /*
   * birthPlaceVerifiedIndicator
   */
  @Test
  public void failsWhenBirthPlaceVerifiedIndicatorMissing() throws Exception {
    Client toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Client/invalid/birthPlaceVerifiedIndicatorMissing.json"),
        Client.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    // String message = response.readEntity(String.class);
    // System.out.print(message);
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("birthplaceVerifiedIndicator may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenBirthPlaceVerifiedIndicatorNull() throws Exception {
    Client toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Client/invalid/birthPlaceVerifiedIndicatorNull.json"),
        Client.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    // String message = response.readEntity(String.class);
    // System.out.print(message);
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("birthplaceVerifiedIndicator may not be null"),
        is(greaterThanOrEqualTo(0)));

  }

  @Test
  public void failsWhenBirthPlaceVerifiedIndicatorEmpty() throws Exception {
    Client toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Client/invalid/birthPlaceVerifiedIndicatorEmpty.json"),
        Client.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    // String message = response.readEntity(String.class);
    // System.out.print(message);
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("birthplaceVerifiedIndicator may not be null"),
        is(greaterThanOrEqualTo(0)));

  }

  @Test
  public void failsWhenBirthPlaceVerifiedIndicatorWhiteSpace() throws Exception {
    Client toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Client/invalid/birthPlaceVerifiedIndicatorWhiteSpace.json"),
        Client.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    // String message = response.readEntity(String.class);
    // System.out.print(message);
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("birthplaceVerifiedIndicator may not be null"),
        is(greaterThanOrEqualTo(0)));

  }

  /*
   * childClientIndicator test
   */
  @Test
  public void failsWhenChildClientIndicatorNull() throws Exception {
    Client toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Client/invalid/childClientIndicatorNull.json"),
        Client.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("childClientIndicatorVar may not be null"),
        is(greaterThanOrEqualTo(0)));

  }

  @Test
  public void failsWhenChildClientIndicatorEmpty() throws Exception {
    Client toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Client/invalid/childClientIndicatorEmpty.json"),
        Client.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("childClientIndicatorVar may not be null"),
        is(greaterThanOrEqualTo(0)));

  }

  @Test
  public void failsWhenChildClientIndicatorMissing() throws Exception {
    Client toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Client/invalid/childClientIndicatorMissing.json"),
        Client.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("childClientIndicatorVar may not be null"),
        is(greaterThanOrEqualTo(0)));

  }

  @Test
  public void failsWhenChildClientIndicatorWhiteSpace() throws Exception {
    Client toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Client/invalid/childClientIndicatorWhiteSpace.json"),
        Client.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("childClientIndicatorVar may not be null"),
        is(greaterThanOrEqualTo(0)));

  }

  /*
   * clientIndexNumber test
   */
  @Test
  public void failsWhenClientIndexNumberTooLong() throws Exception {
    Client toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Client/invalid/clientIndexNumberTooLong.json"),
        Client.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    // String message = response.readEntity(String.class);
    // System.out.print(message);
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class)
        .indexOf("clientIndexNumber size must be between 0 and 12"), is(greaterThanOrEqualTo(0)));

  }

  @Test
  public void successWhenClientIndexNumberWhiteSpace() throws Exception {
    Client toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Client/invalid/clientIndexNumberWhiteSpace.json"),
        Client.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));

  }

  /*
   * commentDescription test
   */
  @Test
  public void failsWhenCommentDescriptionNull() throws Exception {
    Client toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Client/invalid/clientCommentDescriptionNull.json"),
        Client.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    // String message = response.readEntity(String.class);
    // System.out.print(message);
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("commentDescription may not be null"),
        is(greaterThanOrEqualTo(0)));

  }

  /*
   * commonFirstName test
   */
  @Test
  public void failsWhenCommonFirstNameNull() throws Exception {
    Client toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Client/invalid/clientCommonFirstNameNull.json"),
        Client.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("commonFirstName may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenCommonFirstNameEmpty() throws Exception {
    Client toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Client/invalid/clientCommonFirstNameEmpty.json"),
        Client.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("commonFirstName may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenCommonFirstNameTooLong() throws Exception {
    Client toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Client/invalid/clientCommonFirstNameTooLong.json"),
        Client.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    // String message = response.readEntity(String.class);
    // System.out.print(message);
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("commonFirstName size must be between 1 and 2"),
        is(greaterThanOrEqualTo(0)));
  }

  // @Test
  // public void failsWhenCommonFirstNameWhiteSpace() throws Exception {
  // Client toCreate = MAPPER.readValue(
  // fixture("fixtures/domain/legacy/Client/invalid/clientCommonFirstWhiteSpace.json"),
  // Client.class);
  // Response response =
  // resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
  // .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
  // assertThat(response.getStatus(), is(equalTo(422)));
  // assertThat(response.readEntity(String.class).indexOf("commonFirstName may not be empty"),
  // is(greaterThanOrEqualTo(0)));
  // }

  /*
   * commonFirstName test
   */
  @Test
  public void failsWhenCommonLastNameNull() throws Exception {
    Client toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Client/invalid/clientCommonLastNameNull.json"),
        Client.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    // String message = response.readEntity(String.class);
    // System.out.print(message);
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("commonLastName may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenCommonLastNameEmpty() throws Exception {
    Client toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Client/invalid/clientCommonLastNameEmpty.json"),
        Client.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("commonLastName may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenCommonLastNameTooLong() throws Exception {
    Client toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Client/invalid/clientCommonLastNameTooLong.json"),
        Client.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    // String message = response.readEntity(String.class);
    // System.out.print(message);
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("commonLastName size must be between 1 and 25"),
        is(greaterThanOrEqualTo(0)));
  }

  private Client validClient() throws Exception {

    Client validClient =
        MAPPER.readValue(fixture("fixtures/domain/legacy/Client/valid/valid.json"), Client.class);

    return validClient;

  }
}
