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

import javax.ws.rs.core.Response;

import org.junit.After;
import org.junit.ClassRule;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squarespace.jersey2.guice.JerseyGuiceUtils;

import gov.ca.cwds.data.CrudsDao;
import gov.ca.cwds.rest.api.domain.junit.template.DomainTestTemplate;
import gov.ca.cwds.rest.core.Api;
import gov.ca.cwds.rest.resources.cms.ClientResource;
import gov.ca.cwds.rest.resources.cms.JerseyGuiceRule;
import io.dropwizard.jackson.Jackson;
import io.dropwizard.testing.junit.ResourceTestRule;

/**
 * @author CWDS API Team
 *
 */
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

  private final static DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

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
  private String commentMiddleName = "mname";
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
  private String healthSummaryText = "good health";
  private String hispUnableToDetReasonCode = "";
  private String hispanicOriginCode = "X";
  private String id = "1234567ABC";
  private Short immigrationCountryCodeType = 0;
  private Short immigrationStatusType = 1199;
  private String incapcitatedParentCode = "NA";
  private Boolean individualHealthCarePlanIndicator = Boolean.TRUE;
  private Boolean limitationOnScpHealthIndicator = Boolean.FALSE;
  private String literateCode = "Y";
  private Boolean maritalCohabitatnHstryIndicatorVar = Boolean.FALSE;
  private Short maritalStatusType = 1309;
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
  private String suffixTitleDescription = "suffix title";
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
    // TODO Auto-generated method stub

  }

  @Test
  public void persistentObjectConstructorTest() throws Exception {

    Client domain = new Client(adjudicatedDelinquentIndicator, adoptionStatusCode,
        alienRegistrationNumber, birthCity, birthCountryCodeType, birthDate, birthFacilityName,
        birthStateCodeType, birthplaceVerifiedIndicator, childClientIndicatorVar, clientIndexNumber,
        commentDescription, commonFirstName, commonLastName, commentMiddleName,
        confidentialityActionDate, confidentialityInEffectIndicator, creationDate,
        currCaChildrenServIndicator, currentlyOtherDescription, currentlyRegionalCenterIndicator,
        deathDate, deathDateVerified, deathPlace, deathReasonText, driversLicenseNumber,
        driversLicenseStateCodeType, emailAddress, estimatedDateOfBirthCode,
        ethUnableToDetReasonCode, fatherParentalRightTermDate, genderCode, healthSummaryText,
        hispUnableToDetReasonCode, hispanicOriginCode, id, immigrationCountryCodeType,
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
        new gov.ca.cwds.data.persistence.cms.Client(domain, "2017-02-21");

  }

  @Test
  public void jsonCreatorConstructorTest() throws Exception {

    Client vc = validClient();

    Client domain = new Client(adjudicatedDelinquentIndicator, adoptionStatusCode,
        alienRegistrationNumber, birthCity, birthCountryCodeType, birthDate, birthFacilityName,
        birthStateCodeType, birthplaceVerifiedIndicator, childClientIndicatorVar, clientIndexNumber,
        commentDescription, commonFirstName, commonLastName, commentMiddleName,
        confidentialityActionDate, confidentialityInEffectIndicator, creationDate,
        currCaChildrenServIndicator, currentlyOtherDescription, currentlyRegionalCenterIndicator,
        deathDate, deathDateVerified, deathPlace, deathReasonText, driversLicenseNumber,
        driversLicenseStateCodeType, emailAddress, estimatedDateOfBirthCode,
        ethUnableToDetReasonCode, fatherParentalRightTermDate, genderCode, healthSummaryText,
        hispUnableToDetReasonCode, hispanicOriginCode, id, immigrationCountryCodeType,
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


    assertThat(domain.getAdjudicatedDelinquentIndicator(),
        is(equalTo(adjudicatedDelinquentIndicator)));

  }

  @Override
  public void testEqualsHashCodeWorks() throws Exception {
    // TODO Auto-generated method stub

  }

  @Override
  public void testSerializesToJSON() throws Exception {
    // TODO Auto-generated method stub

  }

  @Override
  public void testDeserializesFromJSON() throws Exception {
    // TODO Auto-generated method stub

  }

  @Override
  public void testPersistentConstructor() throws Exception {
    // TODO Auto-generated method stub

  }

  @Override
  public void testJSONCreatorConstructor() throws Exception {
    // TODO Auto-generated method stub

  }

  @Override
  public void testSuccessWithValid() throws Exception {
    // TODO Auto-generated method stub

  }

  @Override
  public void testSuccessWithOptionalsNotIncluded() throws Exception {
    // TODO Auto-generated method stub

  }

  private Client validClient() throws JsonParseException, JsonMappingException, IOException {
    Client vc =
        MAPPER.readValue(fixture("fixtures/domain/legacy/Client/valid/valid.json"), Client.class);
    return vc;
  }
}
