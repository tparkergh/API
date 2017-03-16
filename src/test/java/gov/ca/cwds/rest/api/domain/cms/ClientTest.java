package gov.ca.cwds.rest.api.domain.cms;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.After;
import org.junit.ClassRule;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squarespace.jersey2.guice.JerseyGuiceUtils;

import gov.ca.cwds.data.CrudsDao;
import gov.ca.cwds.rest.api.domain.DomainChef;
import gov.ca.cwds.rest.api.domain.junit.template.DomainTestTemplate;
import gov.ca.cwds.rest.core.Api;
import gov.ca.cwds.rest.resources.cms.ClientResource;
import gov.ca.cwds.rest.resources.cms.JerseyGuiceRule;
import io.dropwizard.jackson.Jackson;
import io.dropwizard.testing.junit.ResourceTestRule;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

/**
 * @author CWDS API Team
 *
 */
@SuppressWarnings("javadoc")
public class ClientTest implements DomainTestTemplate {

  private static final String ROOT_RESOURCE = "/" + Api.RESOURCE_CLIENT + "/";;

  private static final ClientResource mockedClientResource = mock(ClientResource.class);

  @After
  public void ensureSerivceLocatorPopulate() {
    JerseyGuiceUtils.reset();
  }

  @ClassRule
  public static JerseyGuiceRule rule = new JerseyGuiceRule();

  @ClassRule
  public static final ResourceTestRule resources =
      ResourceTestRule.builder().addResource(mockedClientResource).build();

  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();
  private String lastUpdatedId = "0X5";
  private final static DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

  private String existingClientId = "ABC1234567";
  private Boolean adjudicatedDelinquentIndicator = Boolean.FALSE;
  private String adoptionStatusCode = "A";
  private String alienRegistrationNumber = "";
  private String birthCity = "Coletown";
  private Short birthCountryCodeType = 563;
  private String birthDate = "2007-01-31";
  private String birthFacilityName = "Mercy";
  private Short birthStateCodeType = 1829;
  private Boolean birthplaceVerifiedIndicator = Boolean.FALSE;
  private Boolean childClientIndicatorVar = Boolean.FALSE;
  private String clientIndexNumber = "123456789012";
  private String commentDescription = "client description";
  private String commonFirstName = "fname";
  private String commonLastName = "lname";
  private String commonMiddleName = "mname";
  private String confidentialityActionDate = null;
  private Boolean confidentialityInEffectIndicator = Boolean.FALSE;
  private String creationDate = "2016-10-31";
  private Boolean currCaChildrenServIndicator = Boolean.FALSE;
  private String currentlyOtherDescription = "other description";
  private Boolean currentlyRegionalCenterIndicator = Boolean.FALSE;
  private String deathDate = null;
  private Boolean deathDateVerified = Boolean.FALSE;
  private String deathPlace = "";
  private String deathReasonText = "";
  private String driversLicenseNumber = "";
  private Short driversLicenseStateCodeType = 1829;
  private String emailAddress = "anyone@yahoo.com";
  private String estimatedDateOfBirthCode = "Y";
  private String ethUnableToDetReasonCode = "K";
  private String fatherParentalRightTermDate = "";
  private String genderCode = "M";
  private String healthSummaryText = "good";
  private String hispUnableToDetReasonCode = "";
  private String hispanicOriginCode = "X";
  private String id = "1234567ABC";
  private Short immigrationCountryCodeType = 0;
  private Short immigrationStatusType = 1199;
  private String incapcitatedParentCode = "N";
  private Boolean individualHealthCarePlanIndicator = Boolean.FALSE;
  private Boolean limitationOnScpHealthIndicator = Boolean.FALSE;
  private String literateCode = "Y";
  private Boolean maritalCohabitatnHstryIndicatorVar = Boolean.FALSE;
  private Short maritalStatusType = 1308;
  private String militaryStatusCode = "N";
  private String motherParentalRightTermDate = null;
  private String namePrefixDescription = "";
  private Short nameType = 0;
  private Boolean outstandingWarrantIndicator = Boolean.FALSE;
  private Boolean prevCaChildrenServIndicator = Boolean.FALSE;
  private String prevOtherDescription = "the previous description";
  private Boolean prevRegionalCenterIndicator = Boolean.FALSE;
  private Short primaryEthnicityType = 839;
  private Short primaryLanguageType = 1253;
  private Short religionType = 0;
  private Short secondaryLanguageType = 0;
  private Boolean sensitiveHlthInfoOnFileIndicator = Boolean.FALSE;
  private String sensitivityIndicator = "N";
  private String soc158PlacementCode = "N";
  private Boolean soc158SealedClientIndicator = Boolean.FALSE;
  private String socialSecurityNumChangedCode = "N";
  private String socialSecurityNumber = "111122333";
  private String suffixTitleDescription = "MR";
  private Boolean tribalAncestryClientIndicatorVar = Boolean.FALSE;
  private Boolean tribalMembrshpVerifctnIndicatorVar = Boolean.FALSE;
  private String unemployedParentCode = "N";
  private Boolean zippyCreatedIndicator = Boolean.FALSE;



  @Override
  public void setup() throws Exception {
    @SuppressWarnings("rawtypes")
    CrudsDao crudsDao = mock(CrudsDao.class);
    when(crudsDao.find(any())).thenReturn(mock(gov.ca.cwds.data.persistence.cms.Client.class));

    Client validClient = validClient();
    when(mockedClientResource.create(eq(validClient)))
        .thenReturn(Response.status(Response.Status.NO_CONTENT).entity(null).build());
  }

  @Override
  public void teardown() throws Exception {

  }

  @Override
  @Test
  public void testPersistentConstructor() throws Exception {

    Client domain = new Client(existingClientId, adjudicatedDelinquentIndicator, adoptionStatusCode,
        alienRegistrationNumber, birthCity, birthCountryCodeType, birthDate, birthFacilityName,
        birthStateCodeType, birthplaceVerifiedIndicator, childClientIndicatorVar, clientIndexNumber,
        commentDescription, commonFirstName, commonLastName, commonMiddleName,
        confidentialityActionDate, confidentialityInEffectIndicator, creationDate,
        currCaChildrenServIndicator, currentlyOtherDescription, currentlyRegionalCenterIndicator,
        deathDate, deathDateVerified, deathPlace, deathReasonText, driversLicenseNumber,
        driversLicenseStateCodeType, emailAddress, estimatedDateOfBirthCode,
        ethUnableToDetReasonCode, fatherParentalRightTermDate, genderCode, healthSummaryText,
        hispUnableToDetReasonCode, hispanicOriginCode, immigrationCountryCodeType,
        immigrationStatusType, incapcitatedParentCode, individualHealthCarePlanIndicator,
        limitationOnScpHealthIndicator, literateCode, maritalCohabitatnHstryIndicatorVar,
        maritalStatusType, militaryStatusCode, motherParentalRightTermDate, namePrefixDescription,
        nameType, outstandingWarrantIndicator, prevCaChildrenServIndicator, prevOtherDescription,
        prevRegionalCenterIndicator, primaryEthnicityType, primaryLanguageType, religionType,
        secondaryLanguageType, sensitiveHlthInfoOnFileIndicator, sensitivityIndicator,
        soc158PlacementCode, soc158SealedClientIndicator, socialSecurityNumChangedCode,
        socialSecurityNumber, suffixTitleDescription, tribalAncestryClientIndicatorVar,
        tribalMembrshpVerifctnIndicatorVar, unemployedParentCode, zippyCreatedIndicator);

    gov.ca.cwds.data.persistence.cms.Client persistent =
        new gov.ca.cwds.data.persistence.cms.Client(id, domain, lastUpdatedId);

    assertThat(persistent.getAdjudicatedDelinquentIndicator(),
        is(equalTo(DomainChef.cookBoolean(adjudicatedDelinquentIndicator))));
    assertThat(persistent.getAdoptionStatusCode(), is(equalTo(adoptionStatusCode)));
    assertThat(persistent.getAlienRegistrationNumber(), is(equalTo(alienRegistrationNumber)));
    assertThat(persistent.getBirthCity(), is(equalTo(birthCity)));
    assertThat(persistent.getBirthCountryCodeType(), is(equalTo(birthCountryCodeType)));
    assertThat(persistent.getBirthDate(), is(equalTo(DomainChef.uncookDateString(birthDate))));
    assertThat(persistent.getBirthFacilityName(), is(equalTo(birthFacilityName)));
    assertThat(persistent.getBirthStateCodeType(), is(equalTo(birthStateCodeType)));
    assertThat(persistent.getBirthplaceVerifiedIndicator(),
        is(equalTo(DomainChef.cookBoolean(birthplaceVerifiedIndicator))));
    assertThat(persistent.getChildClientIndicatorVar(),
        is(equalTo(DomainChef.cookBoolean(childClientIndicatorVar))));
    assertThat(persistent.getClientIndexNumber(), is(equalTo(clientIndexNumber)));
    assertThat(persistent.getCommentDescription(), is(equalTo(commentDescription)));
    assertThat(persistent.getCommonFirstName(), is(equalTo(commonFirstName)));
    assertThat(persistent.getCommonLastName(), is(equalTo(commonLastName)));
    assertThat(persistent.getCommonMiddleName(), is(equalTo(commonMiddleName)));
    assertThat(persistent.getConfidentialityActionDate(), is(equalTo(confidentialityActionDate)));
    assertThat(persistent.getConfidentialityInEffectIndicator(),
        is(equalTo(DomainChef.cookBoolean(confidentialityInEffectIndicator))));
    assertThat(persistent.getCreationDate(),
        is(equalTo(DomainChef.uncookDateString(creationDate))));
    assertThat(persistent.getCurrCaChildrenServIndicator(),
        is(equalTo(DomainChef.cookBoolean(currCaChildrenServIndicator))));
    assertThat(persistent.getCurrentlyOtherDescription(), is(equalTo(currentlyOtherDescription)));
    assertThat(persistent.getCurrentlyRegionalCenterIndicator(),
        is(equalTo(DomainChef.cookBoolean(currentlyRegionalCenterIndicator))));
    assertThat(persistent.getDeathDate(), is(equalTo(DomainChef.uncookDateString(deathDate))));
    assertThat(persistent.getDeathDateVerifiedIndicator(),
        is(equalTo(DomainChef.cookBoolean(deathDateVerified))));
    assertThat(persistent.getDeathReasonText(), is(equalTo(deathReasonText)));
    assertThat(persistent.getDriverLicenseNumber(), is(equalTo(driversLicenseNumber)));
    assertThat(persistent.getDriverLicenseStateCodeType(),
        is(equalTo(driversLicenseStateCodeType)));
    assertThat(persistent.getEmailAddress(), is(equalTo(emailAddress)));
    assertThat(persistent.getEstimatedDobCode(), is(equalTo(estimatedDateOfBirthCode)));
    assertThat(persistent.getEthUnableToDetReasonCode(), is(equalTo(ethUnableToDetReasonCode)));
    assertThat(persistent.getFatherParentalRightTermDate(),
        is(equalTo(DomainChef.uncookDateString(fatherParentalRightTermDate))));
    assertThat(persistent.getGenderCode(), is(equalTo(genderCode)));
    assertThat(persistent.getHealthSummaryText(), is(equalTo(healthSummaryText)));
    assertThat(persistent.getHispUnableToDetReasonCode(), is(equalTo(hispUnableToDetReasonCode)));
    assertThat(persistent.getHispanicOriginCode(), is(equalTo(hispanicOriginCode)));
    assertThat(persistent.getId(), is(equalTo(id)));
    assertThat(persistent.getImmigrationCountryCodeType(), is(equalTo(immigrationCountryCodeType)));
    assertThat(persistent.getIncapacitatedParentCode(), is(equalTo(incapcitatedParentCode)));
    assertThat(persistent.getIndividualHealthCarePlanIndicator(),
        is(equalTo(DomainChef.cookBoolean(individualHealthCarePlanIndicator))));
    assertThat(persistent.getLimitationOnScpHealthIndicator(),
        is(equalTo(DomainChef.cookBoolean(limitationOnScpHealthIndicator))));
    assertThat(persistent.getLiterateCode(), is(equalTo(literateCode)));
    assertThat(persistent.getMaritalCohabitatnHstryIndicatorVar(),
        is(equalTo(DomainChef.cookBoolean(maritalCohabitatnHstryIndicatorVar))));
    assertThat(persistent.getMaritalStatusType(), is(equalTo(maritalStatusType)));
    assertThat(persistent.getMilitaryStatusCode(), is(equalTo(militaryStatusCode)));
    assertThat(persistent.getMotherParentalRightTermDate(),
        is(equalTo(DomainChef.uncookDateString(motherParentalRightTermDate))));
    assertThat(persistent.getNamePrefixDescription(), is(equalTo(namePrefixDescription)));
    assertThat(persistent.getNameType(), is(equalTo(nameType)));
    assertThat(persistent.getOutstandingWarrantIndicator(),
        is(equalTo(DomainChef.cookBoolean(outstandingWarrantIndicator))));
    assertThat(persistent.getPrevCaChildrenServIndicator(),
        is(equalTo(DomainChef.cookBoolean(prevCaChildrenServIndicator))));
    assertThat(persistent.getPrevOtherDescription(), is(equalTo(prevOtherDescription)));
    assertThat(persistent.getPrimaryEthnicityType(), is(equalTo(primaryEthnicityType)));
    assertThat(persistent.getPrimaryLanguageType(), is(equalTo(primaryLanguageType)));
    assertThat(persistent.getReligionType(), is(equalTo(religionType)));
    assertThat(persistent.getSecondaryLanguageType(), is(equalTo(secondaryLanguageType)));
    assertThat(persistent.getSensitiveHlthInfoOnFileIndicator(),
        is(equalTo(DomainChef.cookBoolean(sensitiveHlthInfoOnFileIndicator))));
    assertThat(persistent.getSensitivityIndicator(), is(equalTo(sensitivityIndicator)));
    assertThat(persistent.getSoc158PlacementCode(), is(equalTo(soc158PlacementCode)));
    assertThat(persistent.getSoc158SealedClientIndicator(),
        is(equalTo(DomainChef.cookBoolean(soc158SealedClientIndicator))));
    assertThat(persistent.getSocialSecurityNumChangedCode(),
        is(equalTo(socialSecurityNumChangedCode)));
    assertThat(persistent.getSocialSecurityNumber(), is(equalTo(socialSecurityNumber)));
    assertThat(persistent.getSuffixTitleDescription(), is(equalTo(suffixTitleDescription)));
    assertThat(persistent.getTribalAncestryClientIndicatorVar(),
        is(equalTo(DomainChef.cookBoolean(tribalAncestryClientIndicatorVar))));
    assertThat(persistent.getUnemployedParentCode(), is(equalTo(unemployedParentCode)));
    assertThat(persistent.getZippyCreatedIndicator(),
        is(equalTo(DomainChef.cookBoolean(zippyCreatedIndicator))));

  }

  @Override
  @Test
  public void testJSONCreatorConstructor() throws Exception {

    Client vc = validClient();

    Client domain = new Client(existingClientId, adjudicatedDelinquentIndicator, adoptionStatusCode,
        alienRegistrationNumber, birthCity, birthCountryCodeType, birthDate, birthFacilityName,
        birthStateCodeType, birthplaceVerifiedIndicator, childClientIndicatorVar, clientIndexNumber,
        commentDescription, commonFirstName, commonLastName, commonMiddleName,
        confidentialityActionDate, confidentialityInEffectIndicator, creationDate,
        currCaChildrenServIndicator, currentlyOtherDescription, currentlyRegionalCenterIndicator,
        deathDate, deathDateVerified, deathPlace, deathReasonText, driversLicenseNumber,
        driversLicenseStateCodeType, emailAddress, estimatedDateOfBirthCode,
        ethUnableToDetReasonCode, fatherParentalRightTermDate, genderCode, healthSummaryText,
        hispUnableToDetReasonCode, hispanicOriginCode, immigrationCountryCodeType,
        immigrationStatusType, incapcitatedParentCode, individualHealthCarePlanIndicator,
        limitationOnScpHealthIndicator, literateCode, maritalCohabitatnHstryIndicatorVar,
        maritalStatusType, militaryStatusCode, motherParentalRightTermDate, namePrefixDescription,
        nameType, outstandingWarrantIndicator, prevCaChildrenServIndicator, prevOtherDescription,
        prevRegionalCenterIndicator, primaryEthnicityType, primaryLanguageType, religionType,
        secondaryLanguageType, sensitiveHlthInfoOnFileIndicator, sensitivityIndicator,
        soc158PlacementCode, soc158SealedClientIndicator, socialSecurityNumChangedCode,
        socialSecurityNumber, suffixTitleDescription, tribalAncestryClientIndicatorVar,
        tribalMembrshpVerifctnIndicatorVar, unemployedParentCode, zippyCreatedIndicator);

    assertThat(domain.getAdjudicatedDelinquentIndicator(),
        is(equalTo(vc.getAdjudicatedDelinquentIndicator())));
    assertThat(domain.getAdoptionStatusCode(), is(equalTo(vc.getAdoptionStatusCode())));
    assertThat(domain.getAlienRegistrationNumber(), is(equalTo(vc.getAlienRegistrationNumber())));
    assertThat(domain.getBirthCity(), is(equalTo(vc.getBirthCity())));
    assertThat(domain.getBirthCountryCodeType(), is(equalTo(vc.getBirthCountryCodeType())));
    assertThat(domain.getBirthDate(), is(equalTo(vc.getBirthDate())));
    assertThat(domain.getBirthFacilityName(), is(equalTo(vc.getBirthFacilityName())));
    assertThat(domain.getBirthStateCodeType(), is(equalTo(vc.getBirthStateCodeType())));
    assertThat(domain.getBirthplaceVerifiedIndicator(),
        is(equalTo(vc.getBirthplaceVerifiedIndicator())));
    assertThat(domain.getChildClientIndicatorVar(), is(equalTo(vc.getChildClientIndicatorVar())));
    assertThat(domain.getClientIndexNumber(), is(equalTo(vc.getClientIndexNumber())));
    assertThat(domain.getCommentDescription(), is(equalTo(vc.getCommentDescription())));
    assertThat(domain.getCommonFirstName(), is(equalTo(vc.getCommonFirstName())));
    assertThat(domain.getCommonLastName(), is(equalTo(vc.getCommonLastName())));
    assertThat(domain.getCommonMiddleName(), is(equalTo(vc.getCommonMiddleName())));
    assertThat(domain.getConfidentialityActionDate(),
        is(equalTo(vc.getConfidentialityActionDate())));
    assertThat(domain.getConfidentialityInEffectIndicator(),
        is(equalTo(vc.getConfidentialityInEffectIndicator())));
    assertThat(domain.getCreationDate(), is(equalTo(vc.getCreationDate())));
    assertThat(domain.getCurrCaChildrenServIndicator(),
        is(equalTo(vc.getCurrCaChildrenServIndicator())));
    assertThat(domain.getCurrentlyOtherDescription(),
        is(equalTo(vc.getCurrentlyOtherDescription())));
    assertThat(domain.getCurrentlyRegionalCenterIndicator(),
        is(equalTo(vc.getCurrentlyRegionalCenterIndicator())));
    assertThat(domain.getDeathDate(), is(equalTo(vc.getDeathDate())));
    assertThat(domain.getDeathDateVerifiedIndicator(),
        is(equalTo(vc.getDeathDateVerifiedIndicator())));
    assertThat(domain.getDeathReasonText(), is(equalTo(vc.getDeathReasonText())));
    assertThat(domain.getDriverLicenseNumber(), is(equalTo(vc.getDriverLicenseNumber())));
    assertThat(domain.getDriverLicenseStateCodeType(),
        is(equalTo(vc.getDriverLicenseStateCodeType())));
    assertThat(domain.getEmailAddress(), is(equalTo(vc.getEmailAddress())));
    assertThat(domain.getEstimatedDobCode(), is(equalTo(vc.getEstimatedDobCode())));
    assertThat(domain.getEthUnableToDetReasonCode(), is(equalTo(vc.getEthUnableToDetReasonCode())));
    assertThat(domain.getFatherParentalRightTermDate(),
        is(equalTo(vc.getFatherParentalRightTermDate())));
    assertThat(domain.getGenderCode(), is(equalTo(vc.getGenderCode())));
    assertThat(domain.getHealthSummaryText(), is(equalTo(vc.getHealthSummaryText())));
    assertThat(domain.getHispUnableToDetReasonCode(),
        is(equalTo(vc.getHispUnableToDetReasonCode())));
    assertThat(domain.getHispanicOriginCode(), is(equalTo(vc.getHispanicOriginCode())));
    assertThat(domain.getImmigrationCountryCodeType(),
        is(equalTo(vc.getImmigrationCountryCodeType())));
    assertThat(domain.getIncapacitatedParentCode(), is(equalTo(vc.getIncapacitatedParentCode())));
    assertThat(domain.getIndividualHealthCarePlanIndicator(),
        is(equalTo(vc.getIndividualHealthCarePlanIndicator())));
    assertThat(domain.getLimitationOnScpHealthIndicator(),
        is(equalTo(vc.getLimitationOnScpHealthIndicator())));
    assertThat(domain.getLiterateCode(), is(equalTo(vc.getLiterateCode())));
    assertThat(domain.getMaritalCohabitatnHstryIndicatorVar(),
        is(equalTo(vc.getMaritalCohabitatnHstryIndicatorVar())));
    assertThat(domain.getMaritalStatusType(), is(equalTo(vc.getMaritalStatusType())));
    assertThat(domain.getMilitaryStatusCode(), is(equalTo(vc.getMilitaryStatusCode())));
    assertThat(domain.getMotherParentalRightTermDate(),
        is(equalTo(vc.getMotherParentalRightTermDate())));
    assertThat(domain.getNamePrefixDescription(), is(equalTo(vc.getNamePrefixDescription())));
    assertThat(domain.getNameType(), is(equalTo(vc.getNameType())));
    assertThat(domain.getOutstandingWarrantIndicator(),
        is(equalTo(vc.getOutstandingWarrantIndicator())));
    assertThat(domain.getPrevCaChildrenServIndicator(),
        is(equalTo(vc.getPrevCaChildrenServIndicator())));
    assertThat(domain.getPrevOtherDescription(), is(equalTo(vc.getPrevOtherDescription())));
    assertThat(domain.getPrimaryEthnicityType(), is(equalTo(vc.getPrimaryEthnicityType())));
    assertThat(domain.getPrimaryLanguageType(), is(equalTo(vc.getPrimaryLanguageType())));
    assertThat(domain.getReligionType(), is(equalTo(vc.getReligionType())));
    assertThat(domain.getSecondaryLanguageType(), is(equalTo(vc.getSecondaryLanguageType())));
    assertThat(domain.getSensitiveHlthInfoOnFileIndicator(),
        is(equalTo(vc.getSensitiveHlthInfoOnFileIndicator())));
    assertThat(domain.getSensitivityIndicator(), is(equalTo(vc.getSensitivityIndicator())));
    assertThat(domain.getSoc158PlacementCode(), is(equalTo(vc.getSoc158PlacementCode())));
    assertThat(domain.getSoc158SealedClientIndicator(),
        is(equalTo(vc.getSoc158SealedClientIndicator())));
    assertThat(domain.getSocialSecurityNumChangedCode(),
        is(equalTo(vc.getSocialSecurityNumChangedCode())));
    assertThat(domain.getSocialSecurityNumber(), is(equalTo(vc.getSocialSecurityNumber())));
    assertThat(domain.getSuffixTitleDescription(), is(equalTo(vc.getSuffixTitleDescription())));
    assertThat(domain.getTribalAncestryClientIndicatorVar(),
        is(equalTo(vc.getTribalAncestryClientIndicatorVar())));
    assertThat(domain.getUnemployedParentCode(), is(equalTo(vc.getUnemployedParentCode())));
    assertThat(domain.getZippyCreatedIndicator(), is(equalTo(vc.getZippyCreatedIndicator())));

  }

  @Override
  @Test
  public void testEqualsHashCodeWorks() throws Exception {
    EqualsVerifier.forClass(Client.class).suppress(Warning.NONFINAL_FIELDS).verify();

  }

  @Override
  @Test
  public void testSerializesToJSON() throws Exception {
    Client validClient = validDomainClient();
    final String expected = MAPPER.writeValueAsString(
        MAPPER.readValue(fixture("fixtures/domain/legacy/Client/valid/valid.json"), Client.class));

    assertThat(MAPPER.writeValueAsString(validClient), is(equalTo(expected)));
  }

  @Override
  @Test
  public void testDeserializesFromJSON() throws Exception {
    Client validClient = validDomainClient();
    assertThat(
        MAPPER.readValue(fixture("fixtures/domain/legacy/Client/valid/valid.json"), Client.class),
        is(equalTo(validClient)));

  }


  @Override
  @Test
  public void testSuccessWithValid() throws Exception {
    Client validClient = validClient();
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    // System.out.println(response.readEntity(String.class));
    assertThat(response.getStatus(), is(equalTo(204)));

  }

  @Override
  @Test
  public void testSuccessWithOptionalsNotIncluded() throws Exception {

    Client validClient = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Client/valid/optionalsNotIncluded.json"), Client.class);

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));

    assertThat(response.getStatus(), is(equalTo(204)));
  }


  /*
   * adoption status code test
   */
  @Test
  public void testFailAdoptionStatusCodeEmpty() throws Exception {

    Client validClient = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Client/invalid/adoptionStatusCodeEmpty.json"),
        Client.class);

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));

    assertThat(response.getStatus(), is(equalTo(422)));
  }

  @Test
  public void testFailAdoptionStatusCodeInvalid() throws Exception {

    Client validClient = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Client/invalid/adoptionStatusCodeInvalid.json"),
        Client.class);

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));

    assertThat(response.getStatus(), is(equalTo(422)));
  }

  @Test
  public void testFailAdoptionStatusCodeNull() throws Exception {

    Client validClient = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Client/invalid/adoptionStatusCodeNull.json"), Client.class);

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));

    assertThat(response.getStatus(), is(equalTo(422)));
  }

  @Test
  public void testFailAdoptionStatusCodeWhiteSpace() throws Exception {

    Client validClient = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Client/invalid/adoptionStatusWhiteSpace.json"),
        Client.class);

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));

    assertThat(response.getStatus(), is(equalTo(422)));
  }

  @Test
  public void testSuccessAdoptionStatusCodeA() throws Exception {

    Client validClient = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Client/valid/adoptionStatusCodeA.json"), Client.class);

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));

    assertThat(response.getStatus(), is(equalTo(204)));

  }

  @Test
  public void testSuccessAdoptionStatusCodeN() throws Exception {

    Client validClient = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Client/valid/adoptionStatusCodeN.json"), Client.class);

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));

    assertThat(response.getStatus(), is(equalTo(204)));

  }

  @Test
  public void testSuccessAdoptionStatusCodeP() throws Exception {

    Client validClient = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Client/valid/adoptionStatusCodeP.json"), Client.class);

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));

    assertThat(response.getStatus(), is(equalTo(204)));

  }

  @Test
  public void testSuccessAdoptionStatusCodeT() throws Exception {

    Client validClient = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Client/valid/adoptionStatusCodeT.json"), Client.class);

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));

    assertThat(response.getStatus(), is(equalTo(204)));

  }

  /*
   * alienRegistrationNumber test
   */
  @Test
  public void testSuccessAlienRegistrationNumberEmpty() throws Exception {

    Client validClient = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Client/valid/alienRegistrationNumberEmpty.json"),
        Client.class);

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));

    assertThat(response.getStatus(), is(equalTo(204)));

  }

  @Test
  public void testFailAlienRegistrationNumberNull() throws Exception {

    Client validClient = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Client/invalid/alienRegistrationNumberNull.json"),
        Client.class);

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));

    assertThat(response.getStatus(), is(equalTo(422)));

  }

  @Test
  public void testFailAlienRegistrationNumberTooLong() throws Exception {

    Client validClient = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Client/invalid/alienRegistrationNumberTooLong.json"),
        Client.class);

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));

    assertThat(response.getStatus(), is(equalTo(422)));

  }

  /*
   * birth city test
   * 
   */
  @Test
  public void testSuccessBirthCityEmpty() throws Exception {

    Client validClient = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Client/valid/birthCityEmpty.json"), Client.class);

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));

    assertThat(response.getStatus(), is(equalTo(204)));

  }

  @Test
  public void testFailBirthCityNull() throws Exception {

    Client validClient = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Client/invalid/birthCityNull.json"), Client.class);

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));

    assertThat(response.getStatus(), is(equalTo(422)));

  }

  @Test
  public void testFailBirthCityTooLong() throws Exception {

    Client validClient = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Client/invalid/birthCityTooLong.json"), Client.class);

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));

    assertThat(response.getStatus(), is(equalTo(422)));

  }

  /*
   * birthCountryCode test
   */
  @Test
  public void testSuccessBirthCountryCodeValid() throws Exception {

    Client validClient = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Client/valid/birthCountryCodeValid.json"), Client.class);

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));

    assertThat(response.getStatus(), is(equalTo(204)));

  }


  private Client validClient() throws JsonParseException, JsonMappingException, IOException {
    Client vc =
        MAPPER.readValue(fixture("fixtures/domain/legacy/Client/valid/valid.json"), Client.class);
    return vc;
  }

  private Client validDomainClient() {
    Client domain = new Client(existingClientId, adjudicatedDelinquentIndicator, adoptionStatusCode,
        alienRegistrationNumber, birthCity, birthCountryCodeType, birthDate, birthFacilityName,
        birthStateCodeType, birthplaceVerifiedIndicator, childClientIndicatorVar, clientIndexNumber,
        commentDescription, commonFirstName, commonLastName, commonMiddleName,
        confidentialityActionDate, confidentialityInEffectIndicator, creationDate,
        currCaChildrenServIndicator, currentlyOtherDescription, currentlyRegionalCenterIndicator,
        deathDate, deathDateVerified, deathPlace, deathReasonText, driversLicenseNumber,
        driversLicenseStateCodeType, emailAddress, estimatedDateOfBirthCode,
        ethUnableToDetReasonCode, fatherParentalRightTermDate, genderCode, healthSummaryText,
        hispUnableToDetReasonCode, hispanicOriginCode, immigrationCountryCodeType,
        immigrationStatusType, incapcitatedParentCode, individualHealthCarePlanIndicator,
        limitationOnScpHealthIndicator, literateCode, maritalCohabitatnHstryIndicatorVar,
        maritalStatusType, militaryStatusCode, motherParentalRightTermDate, namePrefixDescription,
        nameType, outstandingWarrantIndicator, prevCaChildrenServIndicator, prevOtherDescription,
        prevRegionalCenterIndicator, primaryEthnicityType, primaryLanguageType, religionType,
        secondaryLanguageType, sensitiveHlthInfoOnFileIndicator, sensitivityIndicator,
        soc158PlacementCode, soc158SealedClientIndicator, socialSecurityNumChangedCode,
        socialSecurityNumber, suffixTitleDescription, tribalAncestryClientIndicatorVar,
        tribalMembrshpVerifctnIndicatorVar, unemployedParentCode, zippyCreatedIndicator);

    return domain;

  }
}
