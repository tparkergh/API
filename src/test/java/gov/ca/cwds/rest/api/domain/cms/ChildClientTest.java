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
import gov.ca.cwds.rest.resources.cms.ChildClientResource;
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
public class ChildClientTest implements DomainTestTemplate {

  private static final String ROOT_RESOURCE = "/" + Api.RESOURCE_CHILDCLIENT + "/";;

  private static final ChildClientResource mockedChildClientResource =
      mock(ChildClientResource.class);

  @After
  public void ensureSerivceLocatorPopulate() {
    JerseyGuiceUtils.reset();
  }

  @ClassRule
  public static JerseyGuiceRule rule = new JerseyGuiceRule();

  @ClassRule
  public static final ResourceTestRule resources =
      ResourceTestRule.builder().addResource(mockedChildClientResource).build();

  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();
  private String lastUpdatedId = "0X5";
  private final static DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

  private String victimClientId = "ABC1234567";
  private String adoptableCode = "N";
  private Short adoptedAge = 1234;
  private Boolean afdcFcEligibilityIndicatorVar = false;
  private Boolean allEducationInfoOnFileIndicator = false;
  private Boolean allHealthInfoOnFileIndicator = false;
  private String attemptToAcquireEducInfoDesc = "Eucation";
  private String attemptToAcquireHlthInfoDesc = "Health";
  private String awolAbductedCode = "N";
  private Boolean birthHistoryIndicatorVar = false;
  private Boolean childIndianAncestryIndicator = false;
  private Boolean collegeIndicator = false;
  private String currentCaseId = "ABC1234567";
  private Short deathCircumstancesType = 1234;
  private String disabilityDiagnosedCode = "N";
  private String drmsHePassportDocOld = "Old";
  private String drmsHealthEducPassportDoc = "Document";
  private String drmsVoluntaryPlcmntAgrmntDoc = "Agreement";
  private Boolean fc2EligApplicationIndicatorVar = false;
  private String foodStampsApplicationDate = "2000-01-01";
  private Boolean foodStampsApplicationIndicator = false;
  private String icwaEligibilityCode = "N";
  private Boolean intercountryAdoptDisruptedIndicator = false;
  private Boolean intercountryAdoptDissolvedIndicator = false;
  private Boolean medEligibilityApplicationIndicatorVar = false;
  private Boolean minorNmdParentIndicator = false;
  private Boolean parentalRightsLimitedIndicator = false;
  private Boolean parentalRightsTermintnIndicatorVar = false;
  private Boolean paternityIndividualIndicatorVar = false;
  private Boolean postsecVocIndicator = false;
  private String previouslyAdoptedCode = "N";
  private Boolean safelySurrendedBabiesIndicatorVar = false;
  private Boolean saw1EligApplicationIndicatorVar = false;
  private Integer sawsCaseSerialNumber = 0;
  private String sijsScheduledInterviewDate = "2000-01-01";
  private String siiNextScreeningDueDate = "2000-01-01";
  private Boolean ssiSspApplicationIndicator = false;
  private Boolean tribalAncestryNotifctnIndicatorVar = false;
  private String tribalCustomaryAdoptionDate = "2000-01-01";
  private Boolean tribalCustomaryAdoptionIndicator = false;

  @Override
  public void setup() throws Exception {
    @SuppressWarnings("rawtypes")
    CrudsDao crudsDao = mock(CrudsDao.class);
    when(crudsDao.find(any())).thenReturn(mock(gov.ca.cwds.data.persistence.cms.ChildClient.class));

    ChildClient validChildClient = validChildClient();
    when(mockedChildClientResource.create(eq(validChildClient)))
        .thenReturn(Response.status(Response.Status.NO_CONTENT).entity(null).build());
  }

  @Override
  public void teardown() throws Exception {

  }

  @Override
  @Test
  public void testPersistentConstructor() throws Exception {

    ChildClient domain = new ChildClient(victimClientId, adoptableCode, adoptedAge,
        afdcFcEligibilityIndicatorVar, allEducationInfoOnFileIndicator,
        allHealthInfoOnFileIndicator, attemptToAcquireEducInfoDesc, attemptToAcquireHlthInfoDesc,
        awolAbductedCode, birthHistoryIndicatorVar, childIndianAncestryIndicator, collegeIndicator,
        currentCaseId, deathCircumstancesType, disabilityDiagnosedCode, drmsHePassportDocOld,
        drmsHealthEducPassportDoc, drmsVoluntaryPlcmntAgrmntDoc, fc2EligApplicationIndicatorVar,
        foodStampsApplicationDate, foodStampsApplicationIndicator, icwaEligibilityCode,
        intercountryAdoptDisruptedIndicator, intercountryAdoptDissolvedIndicator,
        medEligibilityApplicationIndicatorVar, minorNmdParentIndicator,
        parentalRightsLimitedIndicator, parentalRightsTermintnIndicatorVar,
        paternityIndividualIndicatorVar, postsecVocIndicator, previouslyAdoptedCode,
        safelySurrendedBabiesIndicatorVar, saw1EligApplicationIndicatorVar, sawsCaseSerialNumber,
        sijsScheduledInterviewDate, siiNextScreeningDueDate, ssiSspApplicationIndicator,
        tribalAncestryNotifctnIndicatorVar, tribalCustomaryAdoptionDate,
        tribalCustomaryAdoptionIndicator);

    gov.ca.cwds.data.persistence.cms.ChildClient persistent =
        new gov.ca.cwds.data.persistence.cms.ChildClient(victimClientId, domain, lastUpdatedId);

    assertThat(persistent.getVictimClientId(), is(equalTo(victimClientId)));
    assertThat(persistent.getAdoptableCode(), is(equalTo(adoptableCode)));
    assertThat(persistent.getAdoptedAge(), is(equalTo(adoptedAge)));
    assertThat(persistent.getAfdcFcEligibilityIndicatorVar(),
        is(equalTo(DomainChef.cookBoolean(afdcFcEligibilityIndicatorVar))));
    assertThat(persistent.getAllEducationInfoOnFileIndicator(),
        is(equalTo(DomainChef.cookBoolean(allEducationInfoOnFileIndicator))));
    assertThat(persistent.getAllHealthInfoOnFileIndicator(),
        is(equalTo(DomainChef.cookBoolean(allHealthInfoOnFileIndicator))));
    assertThat(persistent.getAttemptToAcquireEducInfoDesc(),
        is(equalTo(attemptToAcquireEducInfoDesc)));
    assertThat(persistent.getAttemptToAcquireHlthInfoDesc(),
        is(equalTo(attemptToAcquireHlthInfoDesc)));
    assertThat(persistent.getAwolAbductedCode(), is(equalTo(awolAbductedCode)));
    assertThat(persistent.getBirthHistoryIndicatorVar(),
        is(equalTo(DomainChef.cookBoolean(birthHistoryIndicatorVar))));
    assertThat(persistent.getChildIndianAncestryIndicator(),
        is(equalTo(DomainChef.cookBoolean(childIndianAncestryIndicator))));
    assertThat(persistent.getCollegeIndicator(),
        is(equalTo(DomainChef.cookBoolean(collegeIndicator))));
    assertThat(persistent.getCurrentCaseId(), is(equalTo(currentCaseId)));
    assertThat(persistent.getDeathCircumstancesType(), is(equalTo(deathCircumstancesType)));
    assertThat(persistent.getDisabilityDiagnosedCode(), is(equalTo(disabilityDiagnosedCode)));
    assertThat(persistent.getDrmsHePassportDocOld(), is(equalTo(drmsHePassportDocOld)));
    assertThat(persistent.getDrmsHealthEducPassportDoc(), is(equalTo(drmsHealthEducPassportDoc)));
    assertThat(persistent.getDrmsVoluntaryPlcmntAgrmntDoc(),
        is(equalTo(drmsVoluntaryPlcmntAgrmntDoc)));
    assertThat(persistent.getFc2EligApplicationIndicatorVar(),
        is(equalTo(DomainChef.cookBoolean(fc2EligApplicationIndicatorVar))));
    assertThat(persistent.getFoodStampsApplicationDate(),
        is(equalTo(DomainChef.uncookDateString(foodStampsApplicationDate))));
    assertThat(persistent.getFoodStampsApplicationIndicator(),
        is(equalTo(DomainChef.cookBoolean(foodStampsApplicationIndicator))));
    assertThat(persistent.getIcwaEligibilityCode(), is(equalTo(icwaEligibilityCode)));
    assertThat(persistent.getIntercountryAdoptDisruptedIndicator(),
        is(equalTo(DomainChef.cookBoolean(intercountryAdoptDisruptedIndicator))));
    assertThat(persistent.getIntercountryAdoptDissolvedIndicator(),
        is(equalTo(DomainChef.cookBoolean(intercountryAdoptDissolvedIndicator))));
    assertThat(persistent.getMedEligibilityApplicationIndicatorVar(),
        is(equalTo(DomainChef.cookBoolean(medEligibilityApplicationIndicatorVar))));
    assertThat(persistent.getMinorNmdParentIndicator(),
        is(equalTo(DomainChef.cookBoolean(minorNmdParentIndicator))));
    assertThat(persistent.getParentalRightsLimitedIndicator(),
        is(equalTo(DomainChef.cookBoolean(parentalRightsLimitedIndicator))));
    assertThat(persistent.getParentalRightsTermintnIndicatorVar(),
        is(equalTo(DomainChef.cookBoolean(parentalRightsTermintnIndicatorVar))));
    assertThat(persistent.getPaternityIndividualIndicatorVar(),
        is(equalTo(DomainChef.cookBoolean(paternityIndividualIndicatorVar))));
    assertThat(persistent.getPostsecVocIndicator(),
        is(equalTo(DomainChef.cookBoolean(postsecVocIndicator))));
    assertThat(persistent.getPreviouslyAdoptedCode(), is(equalTo(previouslyAdoptedCode)));
    assertThat(persistent.getSafelySurrendedBabiesIndicatorVar(),
        is(equalTo(DomainChef.cookBoolean(safelySurrendedBabiesIndicatorVar))));
    assertThat(persistent.getSaw1EligApplicationIndicatorVar(),
        is(equalTo(DomainChef.cookBoolean(saw1EligApplicationIndicatorVar))));
    assertThat(persistent.getSawsCaseSerialNumber(), is(equalTo(sawsCaseSerialNumber)));
    assertThat(persistent.getSijsScheduledInterviewDate(),
        is(equalTo(DomainChef.uncookDateString(sijsScheduledInterviewDate))));
    assertThat(persistent.getSiiNextScreeningDueDate(),
        is(equalTo(DomainChef.uncookDateString(siiNextScreeningDueDate))));
    assertThat(persistent.getSsiSspApplicationIndicator(),
        is(equalTo(DomainChef.cookBoolean(ssiSspApplicationIndicator))));
    assertThat(persistent.getTribalAncestryNotifctnIndicatorVar(),
        is(equalTo(DomainChef.cookBoolean(tribalAncestryNotifctnIndicatorVar))));
    assertThat(persistent.getTribalCustomaryAdoptionDate(),
        is(equalTo(DomainChef.uncookDateString(tribalCustomaryAdoptionDate))));
    assertThat(persistent.getTribalCustomaryAdoptionIndicator(),
        is(equalTo(DomainChef.cookBoolean(tribalCustomaryAdoptionIndicator))));

  }

  @Override
  @Test
  public void testJSONCreatorConstructor() throws Exception {

    ChildClient vchc = validChildClient();

    ChildClient domain = new ChildClient(victimClientId, adoptableCode, adoptedAge,
        afdcFcEligibilityIndicatorVar, allEducationInfoOnFileIndicator,
        allHealthInfoOnFileIndicator, attemptToAcquireEducInfoDesc, attemptToAcquireHlthInfoDesc,
        awolAbductedCode, birthHistoryIndicatorVar, childIndianAncestryIndicator, collegeIndicator,
        currentCaseId, deathCircumstancesType, disabilityDiagnosedCode, drmsHePassportDocOld,
        drmsHealthEducPassportDoc, drmsVoluntaryPlcmntAgrmntDoc, fc2EligApplicationIndicatorVar,
        foodStampsApplicationDate, foodStampsApplicationIndicator, icwaEligibilityCode,
        intercountryAdoptDisruptedIndicator, intercountryAdoptDissolvedIndicator,
        medEligibilityApplicationIndicatorVar, minorNmdParentIndicator,
        parentalRightsLimitedIndicator, parentalRightsTermintnIndicatorVar,
        paternityIndividualIndicatorVar, postsecVocIndicator, previouslyAdoptedCode,
        safelySurrendedBabiesIndicatorVar, saw1EligApplicationIndicatorVar, sawsCaseSerialNumber,
        sijsScheduledInterviewDate, siiNextScreeningDueDate, ssiSspApplicationIndicator,
        tribalAncestryNotifctnIndicatorVar, tribalCustomaryAdoptionDate,
        tribalCustomaryAdoptionIndicator);

    assertThat(domain.getVictimClientId(), is(equalTo(vchc.getVictimClientId())));
    assertThat(domain.getAdoptableCode(), is(equalTo(vchc.getAdoptableCode())));
    assertThat(domain.getAdoptedAge(), is(equalTo(vchc.getAdoptedAge())));
    assertThat(domain.getAfdcFcEligibilityIndicatorVar(),
        is(equalTo(vchc.getAfdcFcEligibilityIndicatorVar())));
    assertThat(domain.getAllEducationInfoOnFileIndicator(),
        is(equalTo(vchc.getAllEducationInfoOnFileIndicator())));
    assertThat(domain.getAllHealthInfoOnFileIndicator(),
        is(equalTo(vchc.getAllHealthInfoOnFileIndicator())));
    assertThat(domain.getAttemptToAcquireEducInfoDesc(),
        is(equalTo(vchc.getAttemptToAcquireEducInfoDesc())));
    assertThat(domain.getAttemptToAcquireHlthInfoDesc(),
        is(equalTo(vchc.getAttemptToAcquireHlthInfoDesc())));
    assertThat(domain.getAwolAbductedCode(), is(equalTo(vchc.getAwolAbductedCode())));
    assertThat(domain.getBirthHistoryIndicatorVar(),
        is(equalTo(vchc.getBirthHistoryIndicatorVar())));
    assertThat(domain.getChildIndianAncestryIndicator(),
        is(equalTo(vchc.getChildIndianAncestryIndicator())));
    assertThat(domain.getCollegeIndicator(), is(equalTo(vchc.getCollegeIndicator())));
    assertThat(domain.getCurrentCaseId(), is(equalTo(vchc.getCurrentCaseId())));
    assertThat(domain.getDeathCircumstancesType(), is(equalTo(vchc.getDeathCircumstancesType())));
    assertThat(domain.getDisabilityDiagnosedCode(), is(equalTo(vchc.getDisabilityDiagnosedCode())));
    assertThat(domain.getDrmsHePassportDocOld(), is(equalTo(vchc.getDrmsHePassportDocOld())));
    assertThat(domain.getDrmsHealthEducPassportDoc(),
        is(equalTo(vchc.getDrmsHealthEducPassportDoc())));
    assertThat(domain.getDrmsVoluntaryPlcmntAgrmntDoc(),
        is(equalTo(vchc.getDrmsVoluntaryPlcmntAgrmntDoc())));
    assertThat(domain.getFc2EligApplicationIndicatorVar(),
        is(equalTo(vchc.getFc2EligApplicationIndicatorVar())));
    assertThat(domain.getFoodStampsApplicationDate(),
        is(equalTo(vchc.getFoodStampsApplicationDate())));
    assertThat(domain.getFoodStampsApplicationIndicator(),
        is(equalTo(vchc.getFoodStampsApplicationIndicator())));
    assertThat(domain.getIcwaEligibilityCode(), is(equalTo(vchc.getIcwaEligibilityCode())));
    assertThat(domain.getIntercountryAdoptDisruptedIndicator(),
        is(equalTo(vchc.getIntercountryAdoptDisruptedIndicator())));
    assertThat(domain.getIntercountryAdoptDissolvedIndicator(),
        is(equalTo(vchc.getIntercountryAdoptDissolvedIndicator())));
    assertThat(domain.getMedEligibilityApplicationIndicatorVar(),
        is(equalTo(vchc.getMedEligibilityApplicationIndicatorVar())));
    assertThat(domain.getMinorNmdParentIndicator(), is(equalTo(vchc.getMinorNmdParentIndicator())));
    assertThat(domain.getParentalRightsLimitedIndicator(),
        is(equalTo(vchc.getParentalRightsLimitedIndicator())));
    assertThat(domain.getParentalRightsTermintnIndicatorVar(),
        is(equalTo(vchc.getParentalRightsTermintnIndicatorVar())));
    assertThat(domain.getPaternityIndividualIndicatorVar(),
        is(equalTo(vchc.getPaternityIndividualIndicatorVar())));
    assertThat(domain.getPostsecVocIndicator(), is(equalTo(vchc.getPostsecVocIndicator())));
    assertThat(domain.getPreviouslyAdoptedCode(), is(equalTo(vchc.getPreviouslyAdoptedCode())));
    assertThat(domain.getSafelySurrendedBabiesIndicatorVar(),
        is(equalTo(vchc.getSafelySurrendedBabiesIndicatorVar())));
    assertThat(domain.getSaw1EligApplicationIndicatorVar(),
        is(equalTo(vchc.getSaw1EligApplicationIndicatorVar())));
    assertThat(domain.getSawsCaseSerialNumber(), is(equalTo(vchc.getSawsCaseSerialNumber())));
    assertThat(domain.getSijsScheduledInterviewDate(),
        is(equalTo(vchc.getSijsScheduledInterviewDate())));
    assertThat(domain.getSiiNextScreeningDueDate(), is(equalTo(vchc.getSiiNextScreeningDueDate())));
    assertThat(domain.getSsiSspApplicationIndicator(),
        is(equalTo(vchc.getSsiSspApplicationIndicator())));
    assertThat(domain.getTribalAncestryNotifctnIndicatorVar(),
        is(equalTo(vchc.getTribalAncestryNotifctnIndicatorVar())));
    assertThat(domain.getTribalCustomaryAdoptionDate(),
        is(equalTo(vchc.getTribalCustomaryAdoptionDate())));
    assertThat(domain.getTribalCustomaryAdoptionIndicator(),
        is(equalTo(vchc.getTribalCustomaryAdoptionIndicator())));
  }

  @Override
  @Test
  public void testEqualsHashCodeWorks() throws Exception {
    EqualsVerifier.forClass(ChildClient.class).suppress(Warning.NONFINAL_FIELDS).verify();

  }

  @Override
  @Test
  public void testSerializesToJSON() throws Exception {
    ChildClient validChildClient = validDomainChildClient();
    final String expected = MAPPER.writeValueAsString(MAPPER.readValue(
        fixture("fixtures/domain/legacy/ChildClient/valid/valid.json"), ChildClient.class));

    assertThat(MAPPER.writeValueAsString(validChildClient), is(equalTo(expected)));
  }

  @Override
  @Test
  public void testDeserializesFromJSON() throws Exception {
    ChildClient validChildClient = validDomainChildClient();
    assertThat(MAPPER.readValue(fixture("fixtures/domain/legacy/ChildClient/valid/valid.json"),
        ChildClient.class), is(equalTo(validChildClient)));

  }

  @Override
  @Test
  public void testSuccessWithValid() throws Exception {
    ChildClient toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/ChildClient/valid/valid.json"), ChildClient.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));

  }

  @Override
  @Test
  public void testSuccessWithOptionalsNotIncluded() throws Exception {

    ChildClient validChildClient = MAPPER.readValue(
        fixture("fixtures/domain/legacy/ChildClient/valid/optionalsNotIncluded.json"),
        ChildClient.class);

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validChildClient, MediaType.APPLICATION_JSON));

    assertThat(response.getStatus(), is(equalTo(204)));
  }

  /*
   * adoptable code test
   */
  @Test
  public void testFailAdoptableCodeEmpty() throws Exception {

    ChildClient validChildClient = MAPPER.readValue(
        fixture("fixtures/domain/legacy/ChildClient/invalid/adoptableCodeEmpty.json"),
        ChildClient.class);

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validChildClient, MediaType.APPLICATION_JSON));

    assertThat(response.getStatus(), is(equalTo(422)));
  }

  @Test
  public void testFailAdoptableCodeInvalid() throws Exception {

    ChildClient validChildClient = MAPPER.readValue(
        fixture("fixtures/domain/legacy/ChildClient/invalid/adoptableCodeInvalid.json"),
        ChildClient.class);

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validChildClient, MediaType.APPLICATION_JSON));

    assertThat(response.getStatus(), is(equalTo(422)));
  }

  @Test
  public void testFailAdoptableCodeNull() throws Exception {

    ChildClient validChildClient = MAPPER.readValue(
        fixture("fixtures/domain/legacy/ChildClient/invalid/adoptableCodeNull.json"),
        ChildClient.class);

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validChildClient, MediaType.APPLICATION_JSON));

    assertThat(response.getStatus(), is(equalTo(422)));
  }

  @Test
  public void testFailAdoptableCodeWhiteSpace() throws Exception {

    ChildClient validChildClient = MAPPER.readValue(
        fixture("fixtures/domain/legacy/ChildClient/invalid/adoptableCodeWhiteSpace.json"),
        ChildClient.class);

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validChildClient, MediaType.APPLICATION_JSON));

    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("adoptableCode must be one of [N, Y, NA]"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void testSuccessAdoptableCodeN() throws Exception {

    ChildClient validChildClient = MAPPER.readValue(
        fixture("fixtures/domain/legacy/ChildClient/valid/adoptableCodeN.json"), ChildClient.class);

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validChildClient, MediaType.APPLICATION_JSON));

    assertThat(response.getStatus(), is(equalTo(204)));

  }

  @Test
  public void testSuccessAdoptableCodeY() throws Exception {

    ChildClient validChildClient = MAPPER.readValue(
        fixture("fixtures/domain/legacy/ChildClient/valid/adoptableCodeY.json"), ChildClient.class);

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validChildClient, MediaType.APPLICATION_JSON));

    assertThat(response.getStatus(), is(equalTo(204)));

  }

  @Test
  public void testSuccessAdoptableCodeNA() throws Exception {

    ChildClient validChildClient =
        MAPPER.readValue(fixture("fixtures/domain/legacy/ChildClient/valid/adoptableCodeNA.json"),
            ChildClient.class);

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validChildClient, MediaType.APPLICATION_JSON));

    assertThat(response.getStatus(), is(equalTo(204)));

  }

  /*
   * adoptedAge test
   */
  @Test
  public void testSuccessAdoptedAgeValid() throws Exception {

    ChildClient validChildClient =
        MAPPER.readValue(fixture("fixtures/domain/legacy/ChildClient/valid/adoptedAgeValid.json"),
            ChildClient.class);

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validChildClient, MediaType.APPLICATION_JSON));

    assertThat(response.getStatus(), is(equalTo(204)));

  }

  @Test
  public void testFailAdoptedAgeNull() throws Exception {

    ChildClient validChildClient =
        MAPPER.readValue(fixture("fixtures/domain/legacy/ChildClient/invalid/adoptedAgeNull.json"),
            ChildClient.class);

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validChildClient, MediaType.APPLICATION_JSON));

    assertThat(response.getStatus(), is(equalTo(422)));
  }

  @Test
  public void testFailAdoptedAgeMissing() throws Exception {

    ChildClient validChildClient = MAPPER.readValue(
        fixture("fixtures/domain/legacy/ChildClient/invalid/adoptedAgeMissing.json"),
        ChildClient.class);

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validChildClient, MediaType.APPLICATION_JSON));

    assertThat(response.getStatus(), is(equalTo(422)));
  }

  /*
   * afdcFcEligibilityIndicatorVar test
   */
  @Test
  public void failWhenAfdcFcEligibilityIndicatorVarNull() throws Exception {
    ChildClient validChildClient = MAPPER.readValue(
        fixture(
            "fixtures/domain/legacy/ChildClient/invalid/afdcFcEligibilityIndicatorVarNull.json"),
        ChildClient.class);

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validChildClient, MediaType.APPLICATION_JSON));

    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("afdcFcEligibilityIndicatorVar may not be null"),
        is(greaterThanOrEqualTo(0)));

  }

  @Test
  public void failWhenAfdcFcEligibilityIndicatorVarEmpty() throws Exception {
    ChildClient validChildClient = MAPPER.readValue(
        fixture(
            "fixtures/domain/legacy/ChildClient/invalid/afdcFcEligibilityIndicatorVarEmpty.json"),
        ChildClient.class);

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validChildClient, MediaType.APPLICATION_JSON));

    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("afdcFcEligibilityIndicatorVar may not be null"),
        is(greaterThanOrEqualTo(0)));

  }

  @Test
  public void failWhenAfdcFcEligibilityIndicatorVarMissing() throws Exception {
    ChildClient validChildClient = MAPPER.readValue(
        fixture(
            "fixtures/domain/legacy/ChildClient/invalid/afdcFcEligibilityIndicatorVarMissing.json"),
        ChildClient.class);

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validChildClient, MediaType.APPLICATION_JSON));

    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("afdcFcEligibilityIndicatorVar may not be null"),
        is(greaterThanOrEqualTo(0)));

  }

  /*
   * allEducationInfoOnFileIndicator test
   */
  @Test
  public void failWhenAllEducationInfoOnFileIndicatorNull() throws Exception {
    ChildClient validChildClient = MAPPER.readValue(
        fixture(
            "fixtures/domain/legacy/ChildClient/invalid/allEducationInfoOnFileIndicatorNull.json"),
        ChildClient.class);

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validChildClient, MediaType.APPLICATION_JSON));

    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class)
        .indexOf("allEducationInfoOnFileIndicator may not be null"), is(greaterThanOrEqualTo(0)));

  }

  @Test
  public void failWhenAllEducationInfoOnFileIndicatorEmpty() throws Exception {
    ChildClient validChildClient = MAPPER.readValue(
        fixture(
            "fixtures/domain/legacy/ChildClient/invalid/allEducationInfoOnFileIndicatorEmpty.json"),
        ChildClient.class);

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validChildClient, MediaType.APPLICATION_JSON));

    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class)
        .indexOf("allEducationInfoOnFileIndicator may not be null"), is(greaterThanOrEqualTo(0)));

  }

  @Test
  public void failWhenAllEducationInfoOnFileIndicatorMissing() throws Exception {
    ChildClient validChildClient = MAPPER.readValue(
        fixture(
            "fixtures/domain/legacy/ChildClient/invalid/allEducationInfoOnFileIndicatorMissing.json"),
        ChildClient.class);

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validChildClient, MediaType.APPLICATION_JSON));

    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class)
        .indexOf("allEducationInfoOnFileIndicator may not be null"), is(greaterThanOrEqualTo(0)));

  }

  /*
   * allHealthInfoOnFileIndicator test
   */
  @Test
  public void failWhenAllHealthInfoOnFileIndicatorNull() throws Exception {
    ChildClient validChildClient = MAPPER.readValue(
        fixture("fixtures/domain/legacy/ChildClient/invalid/allHealthInfoOnFileIndicatorNull.json"),
        ChildClient.class);

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validChildClient, MediaType.APPLICATION_JSON));

    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("allHealthInfoOnFileIndicator may not be null"),
        is(greaterThanOrEqualTo(0)));

  }

  @Test
  public void failWhenAllHealthInfoOnFileIndicatorEmpty() throws Exception {
    ChildClient validChildClient = MAPPER.readValue(
        fixture(
            "fixtures/domain/legacy/ChildClient/invalid/allHealthInfoOnFileIndicatorEmpty.json"),
        ChildClient.class);

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validChildClient, MediaType.APPLICATION_JSON));

    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("allHealthInfoOnFileIndicator may not be null"),
        is(greaterThanOrEqualTo(0)));

  }

  @Test
  public void failWhenAllHealthInfoOnFileIndicatorMissing() throws Exception {
    ChildClient validChildClient = MAPPER.readValue(
        fixture(
            "fixtures/domain/legacy/ChildClient/invalid/allHealthInfoOnFileIndicatorMissing.json"),
        ChildClient.class);

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validChildClient, MediaType.APPLICATION_JSON));

    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("allHealthInfoOnFileIndicator may not be null"),
        is(greaterThanOrEqualTo(0)));

  }

  /*
   * attemptToAcquireEducInfoDesc test
   */
  @Test
  public void failWhenAttemptToAcquireEducInfoDescNull() throws Exception {
    ChildClient validChildClient = MAPPER.readValue(
        fixture("fixtures/domain/legacy/ChildClient/invalid/attemptToAcquireEducInfoDescNull.json"),
        ChildClient.class);

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validChildClient, MediaType.APPLICATION_JSON));

    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("attemptToAcquireEducInfoDesc may not be null"),
        is(greaterThanOrEqualTo(0)));

  }

  @Test
  public void failWhenAttemptToAcquireEducInfoDescMissing() throws Exception {
    ChildClient validChildClient = MAPPER.readValue(
        fixture(
            "fixtures/domain/legacy/ChildClient/invalid/attemptToAcquireEducInfoDescMissing.json"),
        ChildClient.class);

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validChildClient, MediaType.APPLICATION_JSON));

    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("attemptToAcquireEducInfoDesc may not be null"),
        is(greaterThanOrEqualTo(0)));

  }

  /*
   * attemptToAcquireHlthInfoDesc test
   */
  @Test
  public void failWhenAttemptToAcquireHlthInfoDescNull() throws Exception {
    ChildClient validChildClient = MAPPER.readValue(
        fixture("fixtures/domain/legacy/ChildClient/invalid/attemptToAcquireHlthInfoDescNull.json"),
        ChildClient.class);

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validChildClient, MediaType.APPLICATION_JSON));

    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("attemptToAcquireHlthInfoDesc may not be null"),
        is(greaterThanOrEqualTo(0)));

  }

  @Test
  public void failWhenAttemptToAcquireHlthInfoDescMissing() throws Exception {
    ChildClient validChildClient = MAPPER.readValue(
        fixture(
            "fixtures/domain/legacy/ChildClient/invalid/attemptToAcquireHlthInfoDescMissing.json"),
        ChildClient.class);

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validChildClient, MediaType.APPLICATION_JSON));

    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("attemptToAcquireHlthInfoDesc may not be null"),
        is(greaterThanOrEqualTo(0)));

  }

  /*
   * awolAbductedCode test
   */
  @Test
  public void testFailAwolAbductedCodeInvalid() throws Exception {

    ChildClient validChildClient = MAPPER.readValue(
        fixture("fixtures/domain/legacy/ChildClient/invalid/awolAbductedCodeInvalid.json"),
        ChildClient.class);

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validChildClient, MediaType.APPLICATION_JSON));

    assertThat(response.getStatus(), is(equalTo(422)));
  }

  @Test
  public void testFailAwolAbductedCodeNull() throws Exception {

    ChildClient validChildClient = MAPPER.readValue(
        fixture("fixtures/domain/legacy/ChildClient/invalid/awolAbductedCodeNull.json"),
        ChildClient.class);

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validChildClient, MediaType.APPLICATION_JSON));

    assertThat(response.getStatus(), is(equalTo(422)));
  }

  @Test
  public void testFailAwolAbductedCodeMissing() throws Exception {

    ChildClient validChildClient = MAPPER.readValue(
        fixture("fixtures/domain/legacy/ChildClient/invalid/awolAbductedCodeMissing.json"),
        ChildClient.class);

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validChildClient, MediaType.APPLICATION_JSON));

    assertThat(response.getStatus(), is(equalTo(422)));
  }

  /*
   * birthHistoryIndicatorVar test
   */
  @Test
  public void failWhenBirthHistoryIndicatorVarNull() throws Exception {
    ChildClient validChildClient = MAPPER.readValue(
        fixture("fixtures/domain/legacy/ChildClient/invalid/birthHistoryIndicatorVarNull.json"),
        ChildClient.class);

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validChildClient, MediaType.APPLICATION_JSON));

    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("birthHistoryIndicatorVar may not be null"),
        is(greaterThanOrEqualTo(0)));

  }

  @Test
  public void failWhenBirthHistoryIndicatorVarEmpty() throws Exception {
    ChildClient validChildClient = MAPPER.readValue(
        fixture("fixtures/domain/legacy/ChildClient/invalid/birthHistoryIndicatorVarEmpty.json"),
        ChildClient.class);

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validChildClient, MediaType.APPLICATION_JSON));

    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("birthHistoryIndicatorVar may not be null"),
        is(greaterThanOrEqualTo(0)));

  }

  @Test
  public void failWhenBirthHistoryIndicatorVarMissing() throws Exception {
    ChildClient validChildClient = MAPPER.readValue(
        fixture("fixtures/domain/legacy/ChildClient/invalid/birthHistoryIndicatorVarMissing.json"),
        ChildClient.class);

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validChildClient, MediaType.APPLICATION_JSON));

    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("birthHistoryIndicatorVar may not be null"),
        is(greaterThanOrEqualTo(0)));

  }

  /*
   * childIndianAncestryIndicator test
   */
  @Test
  public void failWhenChildIndianAncestryIndicatorNull() throws Exception {
    ChildClient validChildClient = MAPPER.readValue(
        fixture("fixtures/domain/legacy/ChildClient/invalid/childIndianAncestryIndicatorNull.json"),
        ChildClient.class);

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validChildClient, MediaType.APPLICATION_JSON));

    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("childIndianAncestryIndicator may not be null"),
        is(greaterThanOrEqualTo(0)));

  }

  @Test
  public void failWhenChildIndianAncestryIndicatorEmpty() throws Exception {
    ChildClient validChildClient = MAPPER.readValue(
        fixture(
            "fixtures/domain/legacy/ChildClient/invalid/childIndianAncestryIndicatorEmpty.json"),
        ChildClient.class);

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validChildClient, MediaType.APPLICATION_JSON));

    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("childIndianAncestryIndicator may not be null"),
        is(greaterThanOrEqualTo(0)));

  }

  @Test
  public void failWhenChildIndianAncestryIndicatorMissing() throws Exception {
    ChildClient validChildClient = MAPPER.readValue(
        fixture(
            "fixtures/domain/legacy/ChildClient/invalid/childIndianAncestryIndicatorMissing.json"),
        ChildClient.class);

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validChildClient, MediaType.APPLICATION_JSON));

    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("childIndianAncestryIndicator may not be null"),
        is(greaterThanOrEqualTo(0)));

  }

  /*
   * collegeIndicator test
   */
  @Test
  public void testSuccessCollegeIndicatorEmpty() throws Exception {

    ChildClient validChildClient = MAPPER.readValue(
        fixture("fixtures/domain/legacy/ChildClient/valid/collegeIndicatorEmpty.json"),
        ChildClient.class);

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validChildClient, MediaType.APPLICATION_JSON));

    assertThat(response.getStatus(), is(equalTo(204)));

  }

  @Test
  public void testSuccessCollegeIndicatorMissing() throws Exception {

    ChildClient validChildClient = MAPPER.readValue(
        fixture("fixtures/domain/legacy/ChildClient/valid/collegeIndicatorMissing.json"),
        ChildClient.class);

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validChildClient, MediaType.APPLICATION_JSON));

    assertThat(response.getStatus(), is(equalTo(204)));

  }

  @Test
  public void testSuccessCollegeIndicatorNull() throws Exception {

    ChildClient validChildClient = MAPPER.readValue(
        fixture("fixtures/domain/legacy/ChildClient/valid/collegeIndicatorNull.json"),
        ChildClient.class);

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validChildClient, MediaType.APPLICATION_JSON));

    assertThat(response.getStatus(), is(equalTo(204)));

  }

  /*
   * currentCaseId test
   */
  @Test
  public void testSuccessCurrentCaseIdEmpty() throws Exception {

    ChildClient validChildClient = MAPPER.readValue(
        fixture("fixtures/domain/legacy/ChildClient/valid/currentCaseIdEmpty.json"),
        ChildClient.class);

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validChildClient, MediaType.APPLICATION_JSON));

    assertThat(response.getStatus(), is(equalTo(204)));

  }

  @Test
  public void testSuccessCurrentCaseIdMissing() throws Exception {

    ChildClient validChildClient = MAPPER.readValue(
        fixture("fixtures/domain/legacy/ChildClient/valid/currentCaseIdMissing.json"),
        ChildClient.class);

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validChildClient, MediaType.APPLICATION_JSON));

    assertThat(response.getStatus(), is(equalTo(204)));

  }

  @Test
  public void testSuccessCurrentCaseIdNull() throws Exception {

    ChildClient validChildClient =
        MAPPER.readValue(fixture("fixtures/domain/legacy/ChildClient/valid/currentCaseIdNull.json"),
            ChildClient.class);

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validChildClient, MediaType.APPLICATION_JSON));

    assertThat(response.getStatus(), is(equalTo(204)));

  }

  @Test
  public void failWhenCurrentCaseIdTooLong() throws Exception {
    ChildClient validChildClient = MAPPER.readValue(
        fixture("fixtures/domain/legacy/ChildClient/invalid/currentCaseIdTooLong.json"),
        ChildClient.class);

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validChildClient, MediaType.APPLICATION_JSON));

    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("currentCaseId size must be between 0 and 10"),
        is(greaterThanOrEqualTo(0)));

  }

  /*
   * deathCircumstancesType test
   */
  @Test
  public void failWhenDeathCircumstancesTypeNull() throws Exception {
    ChildClient validChildClient = MAPPER.readValue(
        fixture("fixtures/domain/legacy/ChildClient/invalid/deathCircumstancesTypeNull.json"),
        ChildClient.class);

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validChildClient, MediaType.APPLICATION_JSON));

    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("deathCircumstancesType may not be null"),
        is(greaterThanOrEqualTo(0)));

  }

  @Test
  public void failWhenDeathCircumstancesTypeEmpty() throws Exception {
    ChildClient validChildClient = MAPPER.readValue(
        fixture("fixtures/domain/legacy/ChildClient/invalid/deathCircumstancesTypeEmpty.json"),
        ChildClient.class);

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validChildClient, MediaType.APPLICATION_JSON));

    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("deathCircumstancesType may not be null"),
        is(greaterThanOrEqualTo(0)));

  }

  @Test
  public void failWhenDeathCircumstancesTypeMissing() throws Exception {
    ChildClient validChildClient = MAPPER.readValue(
        fixture("fixtures/domain/legacy/ChildClient/invalid/deathCircumstancesTypeMissing.json"),
        ChildClient.class);

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validChildClient, MediaType.APPLICATION_JSON));

    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("deathCircumstancesType may not be null"),
        is(greaterThanOrEqualTo(0)));

  }

  /*
   * disabilityDiagnosedCode test
   */
  @Test
  public void testFailDisabilityDiagnosedCodeInvalid() throws Exception {

    ChildClient validChildClient = MAPPER.readValue(
        fixture("fixtures/domain/legacy/ChildClient/invalid/disabilityDiagnosedCodeInvalid.json"),
        ChildClient.class);

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validChildClient, MediaType.APPLICATION_JSON));

    assertThat(response.getStatus(), is(equalTo(422)));
  }

  @Test
  public void testFailDisabilityDiagnosedCodeEmpty() throws Exception {

    ChildClient validChildClient = MAPPER.readValue(
        fixture("fixtures/domain/legacy/ChildClient/invalid/disabilityDiagnosedCodeEmpty.json"),
        ChildClient.class);

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validChildClient, MediaType.APPLICATION_JSON));

    assertThat(response.getStatus(), is(equalTo(422)));
  }

  @Test
  public void testSuccessDisabilityDiagnosedCodeMissing() throws Exception {

    ChildClient validChildClient = MAPPER.readValue(
        fixture("fixtures/domain/legacy/ChildClient/valid/disabilityDiagnosedCodeMissing.json"),
        ChildClient.class);

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validChildClient, MediaType.APPLICATION_JSON));

    assertThat(response.getStatus(), is(equalTo(204)));

  }

  @Test
  public void testSuccessDisabilityDiagnosedCodeNull() throws Exception {

    ChildClient validChildClient = MAPPER.readValue(
        fixture("fixtures/domain/legacy/ChildClient/valid/disabilityDiagnosedCodeNull.json"),
        ChildClient.class);

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validChildClient, MediaType.APPLICATION_JSON));

    assertThat(response.getStatus(), is(equalTo(204)));

  }

  @Test
  public void testFailDisabilityDiagnosedCodeWhiteSpace() throws Exception {

    ChildClient validChildClient = MAPPER.readValue(
        fixture(
            "fixtures/domain/legacy/ChildClient/invalid/disabilityDiagnosedCodeWhiteSpace.json"),
        ChildClient.class);

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validChildClient, MediaType.APPLICATION_JSON));

    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class)
        .indexOf("disabilityDiagnosedCode must be one of [N, Y, D]"), is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void testSuccessDisabilityDiagnosedCodeN() throws Exception {

    ChildClient validChildClient = MAPPER.readValue(
        fixture("fixtures/domain/legacy/ChildClient/valid/disabilityDiagnosedCodeN.json"),
        ChildClient.class);

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validChildClient, MediaType.APPLICATION_JSON));

    assertThat(response.getStatus(), is(equalTo(204)));

  }

  @Test
  public void testSuccessDisabilityDiagnosedCodeY() throws Exception {

    ChildClient validChildClient = MAPPER.readValue(
        fixture("fixtures/domain/legacy/ChildClient/valid/disabilityDiagnosedCodeY.json"),
        ChildClient.class);

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validChildClient, MediaType.APPLICATION_JSON));

    assertThat(response.getStatus(), is(equalTo(204)));

  }

  @Test
  public void testSuccessDisabilityDiagnosedCodeD() throws Exception {

    ChildClient validChildClient = MAPPER.readValue(
        fixture("fixtures/domain/legacy/ChildClient/valid/disabilityDiagnosedCodeD.json"),
        ChildClient.class);

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validChildClient, MediaType.APPLICATION_JSON));

    assertThat(response.getStatus(), is(equalTo(204)));

  }

  /*
   * drmsHePassportDocOld test
   */
  @Test
  public void testSuccessDrmsHePassportDocOldMissing() throws Exception {

    ChildClient validChildClient = MAPPER.readValue(
        fixture("fixtures/domain/legacy/ChildClient/valid/drmsHePassportDocOldMissing.json"),
        ChildClient.class);

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validChildClient, MediaType.APPLICATION_JSON));

    assertThat(response.getStatus(), is(equalTo(204)));

  }

  @Test
  public void testSuccessDrmsHePassportDocOldNull() throws Exception {

    ChildClient validChildClient = MAPPER.readValue(
        fixture("fixtures/domain/legacy/ChildClient/valid/drmsHePassportDocOldNull.json"),
        ChildClient.class);

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validChildClient, MediaType.APPLICATION_JSON));

    assertThat(response.getStatus(), is(equalTo(204)));

  }

  @Test
  public void testSuccessDrmsHePassportDocOldEmpty() throws Exception {

    ChildClient validChildClient = MAPPER.readValue(
        fixture("fixtures/domain/legacy/ChildClient/valid/drmsHePassportDocOldEmpty.json"),
        ChildClient.class);

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validChildClient, MediaType.APPLICATION_JSON));

    assertThat(response.getStatus(), is(equalTo(204)));

  }

  /*
   * drmsHealthEducPassportDoc test
   */
  @Test
  public void testSuccessDrmsHealthEducPassportDocMissing() throws Exception {

    ChildClient validChildClient = MAPPER.readValue(
        fixture("fixtures/domain/legacy/ChildClient/valid/drmsHealthEducPassportDocMissing.json"),
        ChildClient.class);

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validChildClient, MediaType.APPLICATION_JSON));

    assertThat(response.getStatus(), is(equalTo(204)));

  }

  @Test
  public void testSuccessDrmsHealthEducPassportDocNull() throws Exception {

    ChildClient validChildClient = MAPPER.readValue(
        fixture("fixtures/domain/legacy/ChildClient/valid/drmsHealthEducPassportDocNull.json"),
        ChildClient.class);

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validChildClient, MediaType.APPLICATION_JSON));

    assertThat(response.getStatus(), is(equalTo(204)));

  }

  @Test
  public void testSuccessDrmsHealthEducPassportDocEmpty() throws Exception {

    ChildClient validChildClient = MAPPER.readValue(
        fixture("fixtures/domain/legacy/ChildClient/valid/drmsHealthEducPassportDocEmpty.json"),
        ChildClient.class);

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validChildClient, MediaType.APPLICATION_JSON));

    assertThat(response.getStatus(), is(equalTo(204)));

  }

  /*
   * drmsVoluntaryPlcmntAgrmntDoc test
   */
  @Test
  public void testSuccessDrmsVoluntaryPlcmntAgrmntDocMissing() throws Exception {

    ChildClient validChildClient = MAPPER.readValue(
        fixture(
            "fixtures/domain/legacy/ChildClient/valid/drmsVoluntaryPlcmntAgrmntDocMissing.json"),
        ChildClient.class);

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validChildClient, MediaType.APPLICATION_JSON));

    assertThat(response.getStatus(), is(equalTo(204)));

  }

  @Test
  public void testSuccessDrmsVoluntaryPlcmntAgrmntDocNull() throws Exception {

    ChildClient validChildClient = MAPPER.readValue(
        fixture("fixtures/domain/legacy/ChildClient/valid/drmsVoluntaryPlcmntAgrmntDocNull.json"),
        ChildClient.class);

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validChildClient, MediaType.APPLICATION_JSON));

    assertThat(response.getStatus(), is(equalTo(204)));

  }

  @Test
  public void testSuccessDrmsVoluntaryPlcmntAgrmntDocEmpty() throws Exception {

    ChildClient validChildClient = MAPPER.readValue(
        fixture("fixtures/domain/legacy/ChildClient/valid/drmsVoluntaryPlcmntAgrmntDocEmpty.json"),
        ChildClient.class);

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validChildClient, MediaType.APPLICATION_JSON));

    assertThat(response.getStatus(), is(equalTo(204)));

  }

  /*
   * fc2EligApplicationIndicatorVar test
   */
  @Test
  public void failWhenFc2EligApplicationIndicatorVarNull() throws Exception {
    ChildClient validChildClient = MAPPER.readValue(
        fixture(
            "fixtures/domain/legacy/ChildClient/invalid/fc2EligApplicationIndicatorVarNull.json"),
        ChildClient.class);

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validChildClient, MediaType.APPLICATION_JSON));

    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("fc2EligApplicationIndicatorVar may not be null"),
        is(greaterThanOrEqualTo(0)));

  }

  @Test
  public void failWhenFc2EligApplicationIndicatorVarEmpty() throws Exception {
    ChildClient validChildClient = MAPPER.readValue(
        fixture(
            "fixtures/domain/legacy/ChildClient/invalid/fc2EligApplicationIndicatorVarEmpty.json"),
        ChildClient.class);

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validChildClient, MediaType.APPLICATION_JSON));

    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("fc2EligApplicationIndicatorVar may not be null"),
        is(greaterThanOrEqualTo(0)));

  }

  @Test
  public void failWhenFc2EligApplicationIndicatorVarMissing() throws Exception {
    ChildClient validChildClient = MAPPER.readValue(
        fixture(
            "fixtures/domain/legacy/ChildClient/invalid/fc2EligApplicationIndicatorVarMissing.json"),
        ChildClient.class);

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validChildClient, MediaType.APPLICATION_JSON));

    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("fc2EligApplicationIndicatorVar may not be null"),
        is(greaterThanOrEqualTo(0)));

  }

  /*
   * foodStampsApplicationDate test
   */
  @Test
  public void failWhenFoodStampsApplicationDateInvalid() throws Exception {
    ChildClient validChildClient = MAPPER.readValue(
        fixture("fixtures/domain/legacy/ChildClient/invalid/foodStampsApplicationDateInvalid.json"),
        ChildClient.class);

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validChildClient, MediaType.APPLICATION_JSON));

    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class)
            .indexOf("foodStampsApplicationDate must be in the format of yyyy-MM-dd"),
        is(greaterThanOrEqualTo(0)));

  }

  @Test
  public void testSuccessFoodStampsApplicationDateMissing() throws Exception {

    ChildClient validChildClient = MAPPER.readValue(
        fixture("fixtures/domain/legacy/ChildClient/valid/foodStampsApplicationDateMissing.json"),
        ChildClient.class);

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validChildClient, MediaType.APPLICATION_JSON));

    assertThat(response.getStatus(), is(equalTo(204)));

  }

  /*
   * foodStampsApplicationIndicator test
   */
  @Test
  public void failWhenFoodStampsApplicationIndicatorNull() throws Exception {
    ChildClient validChildClient = MAPPER.readValue(
        fixture(
            "fixtures/domain/legacy/ChildClient/invalid/foodStampsApplicationIndicatorNull.json"),
        ChildClient.class);

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validChildClient, MediaType.APPLICATION_JSON));

    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("foodStampsApplicationIndicator may not be null"),
        is(greaterThanOrEqualTo(0)));

  }

  @Test
  public void failWhenFoodStampsApplicationIndicatorEmpty() throws Exception {
    ChildClient validChildClient = MAPPER.readValue(
        fixture(
            "fixtures/domain/legacy/ChildClient/invalid/foodStampsApplicationIndicatorEmpty.json"),
        ChildClient.class);

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validChildClient, MediaType.APPLICATION_JSON));

    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("foodStampsApplicationIndicator may not be null"),
        is(greaterThanOrEqualTo(0)));

  }

  @Test
  public void failWhenFoodStampsApplicationIndicatorMissing() throws Exception {
    ChildClient validChildClient = MAPPER.readValue(
        fixture(
            "fixtures/domain/legacy/ChildClient/invalid/foodStampsApplicationIndicatorMissing.json"),
        ChildClient.class);

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validChildClient, MediaType.APPLICATION_JSON));

    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("foodStampsApplicationIndicator may not be null"),
        is(greaterThanOrEqualTo(0)));

  }

  /*
   * icwaEligibilityCode test
   */
  @Test
  public void testFailIcwaEligibilityCodeInvalid() throws Exception {

    ChildClient validChildClient = MAPPER.readValue(
        fixture("fixtures/domain/legacy/ChildClient/invalid/icwaEligibilityCodeInvalid.json"),
        ChildClient.class);

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validChildClient, MediaType.APPLICATION_JSON));

    assertThat(response.getStatus(), is(equalTo(422)));
  }

  @Test
  public void testFailIcwaEligibilityCodeEmpty() throws Exception {

    ChildClient validChildClient = MAPPER.readValue(
        fixture("fixtures/domain/legacy/ChildClient/invalid/icwaEligibilityCodeEmpty.json"),
        ChildClient.class);

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validChildClient, MediaType.APPLICATION_JSON));

    assertThat(response.getStatus(), is(equalTo(422)));
  }

  @Test
  public void testFailIcwaEligibilityCodeMissing() throws Exception {

    ChildClient validChildClient = MAPPER.readValue(
        fixture("fixtures/domain/legacy/ChildClient/invalid/icwaEligibilityCodeMissing.json"),
        ChildClient.class);

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validChildClient, MediaType.APPLICATION_JSON));

    assertThat(response.getStatus(), is(equalTo(422)));

  }

  @Test
  public void testFailIcwaEligibilityCodeNull() throws Exception {

    ChildClient validChildClient = MAPPER.readValue(
        fixture("fixtures/domain/legacy/ChildClient/invalid/icwaEligibilityCodeNull.json"),
        ChildClient.class);

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validChildClient, MediaType.APPLICATION_JSON));

    assertThat(response.getStatus(), is(equalTo(422)));

  }

  @Test
  public void testFailIcwaEligibilityCodeWhiteSpace() throws Exception {

    ChildClient validChildClient = MAPPER.readValue(
        fixture("fixtures/domain/legacy/ChildClient/invalid/icwaEligibilityCodeWhiteSpace.json"),
        ChildClient.class);

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validChildClient, MediaType.APPLICATION_JSON));

    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class)
        .indexOf("icwaEligibilityCode must be one of [Y, N, U, P]"), is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void testSuccessIcwaEligibilityCodeY() throws Exception {

    ChildClient validChildClient = MAPPER.readValue(
        fixture("fixtures/domain/legacy/ChildClient/valid/icwaEligibilityCodeY.json"),
        ChildClient.class);

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validChildClient, MediaType.APPLICATION_JSON));

    assertThat(response.getStatus(), is(equalTo(204)));

  }

  @Test
  public void testSuccessIcwaEligibilityCodeN() throws Exception {

    ChildClient validChildClient = MAPPER.readValue(
        fixture("fixtures/domain/legacy/ChildClient/valid/icwaEligibilityCodeN.json"),
        ChildClient.class);

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validChildClient, MediaType.APPLICATION_JSON));

    assertThat(response.getStatus(), is(equalTo(204)));

  }

  @Test
  public void testSuccessIcwaEligibilityCodeU() throws Exception {

    ChildClient validChildClient = MAPPER.readValue(
        fixture("fixtures/domain/legacy/ChildClient/valid/icwaEligibilityCodeU.json"),
        ChildClient.class);

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validChildClient, MediaType.APPLICATION_JSON));

    assertThat(response.getStatus(), is(equalTo(204)));

  }

  @Test
  public void testSuccessIcwaEligibilityCodeP() throws Exception {

    ChildClient validChildClient = MAPPER.readValue(
        fixture("fixtures/domain/legacy/ChildClient/valid/icwaEligibilityCodeP.json"),
        ChildClient.class);

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validChildClient, MediaType.APPLICATION_JSON));

    assertThat(response.getStatus(), is(equalTo(204)));

  }


  private ChildClient validChildClient()
      throws JsonParseException, JsonMappingException, IOException {
    ChildClient vchc = MAPPER.readValue(
        fixture("fixtures/domain/legacy/ChildClient/valid/valid.json"), ChildClient.class);
    return vchc;
  }

  private ChildClient validDomainChildClient() {
    ChildClient domain = new ChildClient(victimClientId, adoptableCode, adoptedAge,
        afdcFcEligibilityIndicatorVar, allEducationInfoOnFileIndicator,
        allHealthInfoOnFileIndicator, attemptToAcquireEducInfoDesc, attemptToAcquireHlthInfoDesc,
        awolAbductedCode, birthHistoryIndicatorVar, childIndianAncestryIndicator, collegeIndicator,
        currentCaseId, deathCircumstancesType, disabilityDiagnosedCode, drmsHePassportDocOld,
        drmsHealthEducPassportDoc, drmsVoluntaryPlcmntAgrmntDoc, fc2EligApplicationIndicatorVar,
        foodStampsApplicationDate, foodStampsApplicationIndicator, icwaEligibilityCode,
        intercountryAdoptDisruptedIndicator, intercountryAdoptDissolvedIndicator,
        medEligibilityApplicationIndicatorVar, minorNmdParentIndicator,
        parentalRightsLimitedIndicator, parentalRightsTermintnIndicatorVar,
        paternityIndividualIndicatorVar, postsecVocIndicator, previouslyAdoptedCode,
        safelySurrendedBabiesIndicatorVar, saw1EligApplicationIndicatorVar, sawsCaseSerialNumber,
        sijsScheduledInterviewDate, siiNextScreeningDueDate, ssiSspApplicationIndicator,
        tribalAncestryNotifctnIndicatorVar, tribalCustomaryAdoptionDate,
        tribalCustomaryAdoptionIndicator);

    return domain;
  }

}
