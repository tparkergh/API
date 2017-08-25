package gov.ca.cwds.rest.api.domain.cms;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;
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

import gov.ca.cwds.rest.api.domain.DomainChef;
import gov.ca.cwds.rest.core.Api;
import gov.ca.cwds.rest.resources.cms.AllegationResource;
import gov.ca.cwds.rest.resources.cms.JerseyGuiceRule;
import io.dropwizard.jackson.Jackson;
import io.dropwizard.testing.junit.ResourceTestRule;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

/**
 * @author CWDS API Team
 */
public class AllegationTest {

  private static final String ROOT_RESOURCE = "/" + Api.RESOURCE_ALLEGATION + "/";
  private static final AllegationResource mockedResource = mock(AllegationResource.class);

  @SuppressWarnings("javadoc")
  @After
  public void ensureServiceLocatorPopulated() {
    JerseyGuiceUtils.reset();
  }

  @SuppressWarnings("javadoc")
  @ClassRule
  public static JerseyGuiceRule rule = new JerseyGuiceRule();

  /**
   * 
   */
  @ClassRule
  public static final ResourceTestRule resources =
      ResourceTestRule.builder().addResource(mockedResource).build();

  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();
  private Allegation validAllegation = validAllegation();

  private final static DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
  private String id = "1234567ABC";
  private String abuseEndDate = "2016-10-31";
  private String abuseStartDate = "2016-10-31";
  private Short abuseFrequency = 1;
  private String abuseFrequencyPeriodCode = "M";
  private String abuseLocationDescription = "Home";
  private Short allegationDispositionType = 2;
  private Short allegationType = 3;
  private String dispositionDescription = "disposition description";
  private String dispositionDate = "2016-10-31";
  private Boolean injuryHarmDetailIndicator = Boolean.TRUE;
  private String nonProtectingParentCode = "N";
  private Boolean staffPersonAddedIndicator = Boolean.FALSE;
  private String victimClientId = "1234567890";
  private String perpetratorClientId = "0987654321";
  private String referralId = "2345678901";
  private String countySpecificCode = "99";
  private Boolean zippyCreatedIndicator = Boolean.TRUE;
  private Short placementFacilityType = 4;

  /**
   * 
   */
  @Before
  public void setup() {
    when(mockedResource.create(eq(validAllegation)))
        .thenReturn(Response.status(Response.Status.NO_CONTENT).entity(null).build());
  }

  /*
   * Constructor Tests
   */
  /**
   * @throws Exception test standard test standard
   */
  @Test
  public void persistentObjectConstructorTest() throws Exception {

    Allegation domain = new Allegation(abuseEndDate, abuseFrequency, abuseFrequencyPeriodCode,
        abuseLocationDescription, abuseStartDate, allegationDispositionType, allegationType,
        dispositionDescription, dispositionDate, injuryHarmDetailIndicator, nonProtectingParentCode,
        staffPersonAddedIndicator, victimClientId, perpetratorClientId, referralId,
        countySpecificCode, zippyCreatedIndicator, placementFacilityType);

    gov.ca.cwds.data.persistence.cms.Allegation persistent =
        new gov.ca.cwds.data.persistence.cms.Allegation(id, domain, "lastUpdatedId");

    Allegation totest = new Allegation(persistent);
    assertThat(totest.getAbuseEndDate(), is(equalTo(df.format(persistent.getAbuseEndDate()))));
    assertThat(totest.getAbuseFrequency(), is(equalTo(persistent.getAbuseFrequency())));
    assertThat(totest.getAbuseFrequencyPeriodCode(),
        is(equalTo(persistent.getAbuseFrequencyPeriodCode())));
    assertThat(totest.getAbuseLocationDescription(),
        is(equalTo(persistent.getAbuseLocationDescription())));
    assertThat(totest.getAbuseStartDate(), is(equalTo(df.format(persistent.getAbuseStartDate()))));
    assertThat(totest.getAllegationDispositionType(),
        is(equalTo(persistent.getAllegationDispositionType())));
    assertThat(totest.getAllegationType(), is(equalTo(persistent.getAllegationType())));
    assertThat(totest.getDispositionDescription(),
        is(equalTo(persistent.getDispositionDescription())));
    assertThat(totest.getDispositionDate(),
        is(equalTo(df.format(persistent.getDispositionDate()))));
    assertThat(totest.getInjuryHarmDetailIndicator(),
        is(equalTo(DomainChef.uncookBooleanString(persistent.getInjuryHarmDetailIndicator()))));
    assertThat(totest.getNonProtectingParentCode(),
        is(equalTo(persistent.getNonProtectingParentCode())));
    assertThat(totest.getStaffPersonAddedIndicator(),
        is(equalTo(DomainChef.uncookBooleanString(persistent.getStaffPersonAddedIndicator()))));
    assertThat(totest.getVictimClientId(), is(equalTo(persistent.getVictimClientId())));
    assertThat(totest.getPerpetratorClientId(), is(equalTo(persistent.getPerpetratorClientId())));
    assertThat(totest.getReferralId(), is(equalTo(persistent.getReferralId())));
    assertThat(totest.getCountySpecificCode(), is(equalTo(persistent.getCountySpecificCode())));
    assertThat(totest.getZippyCreatedIndicator(),
        is(equalTo(DomainChef.uncookBooleanString(persistent.getZippyCreatedIndicator()))));
    assertThat(totest.getPlacementFacilityType(),
        is(equalTo(persistent.getPlacementFacilityType())));
  }

  /**
   * @throws Exception test standard test standard
   */
  @Test
  public void jsonCreatorConstructorTest() throws Exception {
    Allegation domain = new Allegation(abuseEndDate, abuseFrequency, abuseFrequencyPeriodCode,
        abuseLocationDescription, abuseStartDate, allegationDispositionType, allegationType,
        dispositionDescription, dispositionDate, injuryHarmDetailIndicator, nonProtectingParentCode,
        staffPersonAddedIndicator, victimClientId, perpetratorClientId, referralId,
        countySpecificCode, zippyCreatedIndicator, placementFacilityType);

    assertThat(domain.getAbuseEndDate(), is(equalTo(abuseEndDate)));
    assertThat(domain.getAbuseFrequency(), is(equalTo(abuseFrequency)));
    assertThat(domain.getAbuseFrequencyPeriodCode(), is(equalTo(abuseFrequencyPeriodCode)));
    assertThat(domain.getAbuseLocationDescription(), is(equalTo(abuseLocationDescription)));
    assertThat(domain.getAbuseStartDate(), is(equalTo(abuseStartDate)));
    assertThat(domain.getAllegationDispositionType(), is(equalTo(allegationDispositionType)));
    assertThat(domain.getAllegationType(), is(equalTo(allegationType)));
    assertThat(domain.getDispositionDescription(), is(equalTo(dispositionDescription)));
    assertThat(domain.getDispositionDate(), is(equalTo(dispositionDate)));
    assertThat(domain.getInjuryHarmDetailIndicator(), is(equalTo(injuryHarmDetailIndicator)));
    assertThat(domain.getNonProtectingParentCode(), is(equalTo(nonProtectingParentCode)));
    assertThat(domain.getStaffPersonAddedIndicator(), is(equalTo(staffPersonAddedIndicator)));
    assertThat(domain.getVictimClientId(), is(equalTo(victimClientId)));
    assertThat(domain.getPerpetratorClientId(), is(equalTo(perpetratorClientId)));
    assertThat(domain.getReferralId(), is(equalTo(referralId)));
    assertThat(domain.getCountySpecificCode(), is(equalTo(countySpecificCode)));
    assertThat(domain.getZippyCreatedIndicator(), is(equalTo(zippyCreatedIndicator)));
    assertThat(domain.getPlacementFacilityType(), is(equalTo(placementFacilityType)));
  }

  /**
   * 
   */
  @Test
  public void equalsHashCodeWork() {
    EqualsVerifier.forClass(Allegation.class).suppress(Warning.NONFINAL_FIELDS).verify();
  }

  /**
   * @throws Exception test standard
   * 
   */
  @Test
  public void serializesToJSON() throws Exception {
    final String expected = MAPPER.writeValueAsString(MAPPER.readValue(
        fixture("fixtures/domain/legacy/Allegation/valid/valid.json"), Allegation.class));

    assertThat(MAPPER.writeValueAsString(validAllegation()), is(equalTo(expected)));
  }

  /**
   * @throws Exception test standard
   * 
   */
  @Test
  public void deserializesFromJSON() throws Exception {
    assertThat(MAPPER.readValue(fixture("fixtures/domain/legacy/Allegation/valid/valid.json"),
        Allegation.class), is(equalTo(validAllegation())));
  }

  /*
   * Successful Tests
   */
  /**
   * @throws Exception test standard
   * 
   */
  @Test
  public void successfulWithValid() throws Exception {
    Allegation toCreate = MAPPER
        .readValue(fixture("fixtures/domain/legacy/Allegation/valid/valid.json"), Allegation.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  /**
   * @throws Exception test standard
   * 
   */
  @Test
  public void successfulWithOptionalsNotIncluded() throws Exception {
    Allegation toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Allegation/valid/optionalsNotIncluded.json"),
        Allegation.class);
    assertThat(
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON)).getStatus(),
        is(equalTo(204)));
  }

  /*
   * abuseEndDate Tests
   */
  /**
   * @throws Exception test standard
   * 
   */
  @Test
  public void successWhenAbuseEndDateEmpty() throws Exception {
    Allegation toCreate =
        MAPPER.readValue(fixture("fixtures/domain/legacy/Allegation/valid/abuseEndDateEmpty.json"),
            Allegation.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  /**
   * @throws Exception test standard
   * 
   */
  @Test
  public void successWhenAbuseEndDateNull() throws Exception {
    Allegation toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Allegation/valid/abuseEndDateNull.json"), Allegation.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  /**
   * @throws Exception test standard
   * 
   */
  @Test
  public void failsWhenAbuseEndDateWrongFormat() throws Exception {
    Allegation toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Allegation/invalid/abuseEndDateWrongFormat.json"),
        Allegation.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("abuseEndDate must be in the format of"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * abuseFrequency Tests
   */
  @SuppressWarnings("javadoc")
  @Test
  public void failsWhenAbuseFrequencyMissing() throws Exception {
    Allegation toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Allegation/invalid/abuseFrequencyMissing.json"),
        Allegation.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("abuseFrequency may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  /**
   * @throws Exception test standard
   * 
   */
  @Test
  public void failsWhenAbuseFrequencyNull() throws Exception {
    Allegation toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Allegation/invalid/abuseFrequencyNull.json"),
        Allegation.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("abuseFrequency may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * abuseFrequencyPeriodCode Tests
   */
  /**
   * @throws Exception test standard
   * 
   */
  @Test
  public void failsWhenAbuseFrequencyPeriodCodeMissing() throws Exception {
    Allegation toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Allegation/invalid/abuseFrequencyPeriodCodeMissing.json"),
        Allegation.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("abuseFrequencyPeriodCode may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  /**
   * @throws Exception test standard
   * 
   */
  @Test
  public void failsWhenAbuseFrequencyPeriodCodeNull() throws Exception {
    Allegation toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Allegation/invalid/abuseFrequencyPeriodCodeNull.json"),
        Allegation.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("abuseFrequencyPeriodCode may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  /**
   * @throws Exception test standard
   * 
   */
  @Test
  public void successWhenAbuseFrequencyPeriodCodeEmpty() throws Exception {
    Allegation toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Allegation/valid/abuseFrequencyPeriodCodeEmpty.json"),
        Allegation.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  /**
   * @throws Exception test standard
   * 
   */
  @Test
  public void failsWhenAbuseFrequencyPeriodCodeTooLong() throws Exception {
    Allegation toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Allegation/invalid/abuseFrequencyPeriodCodeTooLong.json"),
        Allegation.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf(
        "abuseFrequencyPeriodCode size must be between 0 and 1"), is(greaterThanOrEqualTo(0)));
  }

  /**
   * @throws Exception test standard
   * 
   */
  @Test
  public void failsWhenAbuseFrequencyPeriodCodeNotValidValue() throws Exception {
    Allegation toCreate = MAPPER.readValue(
        fixture(
            "fixtures/domain/legacy/Allegation/invalid/abuseFrequencyPeriodCodeNotValidValue.json"),
        Allegation.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf(
        "abuseFrequencyPeriodCode must be one of [D, M, W, Y, ]"), is(greaterThanOrEqualTo(0)));
  }

  /**
   * @throws Exception test standard
   * 
   */
  @Test
  public void successWhenAbuseFrequencyPeriodCodeIsD() throws Exception {
    Allegation toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Allegation/valid/abuseFrequencyPeriodCodeD.json"),
        Allegation.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  /**
   * @throws Exception test standard
   * 
   */
  @Test
  public void successWhenAbuseFrequencyPeriodCodeIsM() throws Exception {
    Allegation toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Allegation/valid/abuseFrequencyPeriodCodeM.json"),
        Allegation.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  /**
   * @throws Exception test standard
   * 
   */
  @Test
  public void successWhenAbuseFrequencyPeriodCodeIsW() throws Exception {
    Allegation toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Allegation/valid/abuseFrequencyPeriodCodeW.json"),
        Allegation.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  /**
   * @throws Exception test standard
   * 
   */
  @Test
  public void successWhenAbuseFrequencyPeriodCodeIsY() throws Exception {
    Allegation toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Allegation/valid/abuseFrequencyPeriodCodeY.json"),
        Allegation.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  /**
   * @throws Exception test standard
   * 
   */
  @Test
  public void successWhenAbuseFrequencyPeriodCodeIsLowerCase() throws Exception {
    Allegation toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Allegation/valid/abuseFrequencyPeriodCodeLowerCase.json"),
        Allegation.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    // String message = response.readEntity(String.class);
    // System.out.print(message);
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  /*
   * abuseLocationDescription Tests
   */
  /**
   * @throws Exception test standard
   * 
   */
  @Test
  public void failsWhenAbuseLocationDescriptionMissing() throws Exception {
    Allegation toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Allegation/invalid/abuseLocationDescriptionMissing.json"),
        Allegation.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("abuseLocationDescription may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  /**
   * @throws Exception test standard
   * 
   */
  @Test
  public void failsWhenAbuseLocationDescriptionNull() throws Exception {
    Allegation toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Allegation/invalid/abuseLocationDescriptionNull.json"),
        Allegation.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("abuseLocationDescription may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  /**
   * @throws Exception test standard
   * 
   */
  @Test
  public void failsWhenAbuseLocationDescriptionEmpty() throws Exception {
    Allegation toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Allegation/invalid/abuseLocationDescriptionEmpty.json"),
        Allegation.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("abuseLocationDescription may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  /**
   * @throws Exception test standard
   * 
   */
  @Test
  public void failsWhenAbuseLocationDescriptionTooLong() throws Exception {
    Allegation toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Allegation/invalid/abuseLocationDescriptionTooLong.json"),
        Allegation.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf(
        "abuseLocationDescription size must be between 0 and 75"), is(greaterThanOrEqualTo(0)));
  }

  /*
   * abuseStartDate Tests
   */
  /**
   * @throws Exception test standard
   * 
   */
  @Test
  public void successWhenAbuseStartDateEmpty() throws Exception {
    Allegation toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Allegation/valid/abuseStartDateEmpty.json"),
        Allegation.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  /**
   * @throws Exception test standard
   * 
   */
  @Test
  public void successWhenAbuseStartDateNull() throws Exception {
    Allegation toCreate =
        MAPPER.readValue(fixture("fixtures/domain/legacy/Allegation/valid/abuseStartDateNull.json"),
            Allegation.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  /**
   * @throws Exception test standard
   * 
   */
  @Test
  public void failsWhenAbuseStartDateWrongFormat() throws Exception {
    Allegation toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Allegation/invalid/abuseStartDateWrongFormat.json"),
        Allegation.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    // String message = response.readEntity(String.class);
    // System.out.print(message);
    assertThat(response.readEntity(String.class).indexOf("abuseStartDate must be in the format of"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * allegationDispositionType Tests
   */
  /**
   * @throws Exception test standard
   * 
   */
  @Test
  public void failsWhenAllegationDispositionTypeMissing() throws Exception {
    Allegation toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Allegation/invalid/allegationDispositionTypeMissing.json"),
        Allegation.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("allegationDispositionType may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  /**
   * @throws Exception test standard
   * 
   */
  @Test
  public void failsWhenAllegationDispositionTypeNull() throws Exception {
    Allegation toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Allegation/invalid/allegationDispositionTypeNull.json"),
        Allegation.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("allegationDispositionType may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  /**
   * @throws Exception test standard
   * 
   */
  @Test
  public void failsWhenAllegationDispositionTypeEmplty() throws Exception {
    Allegation toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Allegation/invalid/allegationDispositionTypeEmpty.json"),
        Allegation.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("allegationDispositionType may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * allegationType Tests
   */
  /**
   * @throws Exception test standard
   * 
   */
  @Test
  public void failsWhenAllegationTypeMissing() throws Exception {
    Allegation toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Allegation/invalid/allegationTypeMissing.json"),
        Allegation.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("allegationType may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  /**
   * @throws Exception test standard
   * 
   */
  @Test
  public void failsWhenAllegationTypeNull() throws Exception {
    Allegation toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Allegation/invalid/allegationTypeNull.json"),
        Allegation.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("allegationType may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  /**
   * @throws Exception test standard
   * 
   */
  @Test
  public void failsWhenAllegationTypeEmpty() throws Exception {
    Allegation toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Allegation/invalid/allegationTypeEmpty.json"),
        Allegation.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("allegationType may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * dispositionDescription Tests
   */
  /**
   * @throws Exception test standard
   * 
   */
  @Test
  public void failsWhenDispositionDescriptionMissing() throws Exception {
    Allegation toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Allegation/invalid/dispositionDescriptionMissing.json"),
        Allegation.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("dispositionDescription may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  /**
   * @throws Exception test standard
   * 
   */
  @Test
  public void failsWhenDispositionDescriptionNull() throws Exception {
    Allegation toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Allegation/invalid/dispositionDescriptionNull.json"),
        Allegation.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("dispositionDescription may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  /**
   * @throws Exception test standard
   * 
   */
  @Test
  public void successWhenDispositionDescriptionEmpty() throws Exception {
    Allegation toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Allegation/valid/dispositionDescriptionEmpty.json"),
        Allegation.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  /*
   * dispositionDate Tests
   */
  /**
   * @throws Exception test standard
   * 
   */
  @Test
  public void successWhenDispositionDateEmpty() throws Exception {
    Allegation toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Allegation/valid/dispositionDateEmpty.json"),
        Allegation.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  /**
   * @throws Exception test standard
   * 
   */
  @Test
  public void successWhenDispositionDateNull() throws Exception {
    Allegation toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Allegation/valid/dispositionDateNull.json"),
        Allegation.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  /**
   * @throws Exception test standard
   * 
   */
  @Test
  public void failsWhenDispositionDateWrongFormat() throws Exception {
    Allegation toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Allegation/invalid/dispositionDateWrongFormat.json"),
        Allegation.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("dispositionDate must be in the format of"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * injuryHarmDetailIndicator Tests
   */
  /**
   * @throws Exception test standard
   * 
   */
  @Test
  public void failsWhenInjuryHarmDetailIndicatorMissing() throws Exception {
    Allegation toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Allegation/invalid/injuryHarmDetailIndicatorMissing.json"),
        Allegation.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("injuryHarmDetailIndicator may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  /**
   * @throws Exception test standard
   * 
   */
  @Test
  public void failsWhenInjuryHarmDetailIndicatorNull() throws Exception {
    Allegation toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Allegation/invalid/injuryHarmDetailIndicatorNull.json"),
        Allegation.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("injuryHarmDetailIndicator may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  /**
   * @throws Exception test standard
   * 
   */
  @Test
  public void failsWhenInjuryHarmDetailIndicatorEmpty() throws Exception {
    Allegation toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Allegation/invalid/injuryHarmDetailIndicatorEmpty.json"),
        Allegation.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("injuryHarmDetailIndicator may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  /**
   * @throws Exception test standard
   * 
   */
  @Test
  public void failsWhenInjuryHarmDetailIndicatorAllWhitespace() throws Exception {
    Allegation toCreate = MAPPER.readValue(
        fixture(
            "fixtures/domain/legacy/Allegation/invalid/injuryHarmDetailIndicatorAllWhitespace.json"),
        Allegation.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("injuryHarmDetailIndicator may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * nonProtectingParentCode Tests
   */
  /**
   * @throws Exception test standard
   * 
   */
  @Test
  public void failsWhenNonProtectingParentCodeMissing() throws Exception {
    Allegation toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Allegation/invalid/nonProtectingParentCodeMissing.json"),
        Allegation.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("nonProtectingParentCode may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  /**
   * @throws Exception test standard
   * 
   */
  @Test
  public void failsWhenNonProtectingParentCodeNull() throws Exception {
    Allegation toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Allegation/invalid/nonProtectingParentCodeNull.json"),
        Allegation.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("nonProtectingParentCode may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  /**
   * @throws Exception test standard
   * 
   */
  @Test
  public void failsWhenNonProtectingParentCodeEmpty() throws Exception {
    Allegation toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Allegation/invalid/nonProtectingParentCodeEmpty.json"),
        Allegation.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("nonProtectingParentCode may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  /**
   * @throws Exception test standard
   * 
   */
  @Test
  public void failsWhenNonProtectingParentCodeTooLong() throws Exception {
    Allegation toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Allegation/invalid/nonProtectingParentCodeTooLong.json"),
        Allegation.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("nonProtectingParentCode size must be 1"),
        is(greaterThanOrEqualTo(0)));
  }

  /**
   * @throws Exception test standard
   * 
   */
  @Test
  public void failsWhenNonProtectingParentCodeNotValidValue() throws Exception {
    Allegation toCreate = MAPPER.readValue(
        fixture(
            "fixtures/domain/legacy/Allegation/invalid/nonProtectingParentCodeNotValidValue.json"),
        Allegation.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf(
        "nonProtectingParentCode must be one of [U, P, Y, N]"), is(greaterThanOrEqualTo(0)));
  }

  /**
   * @throws Exception test standard
   * 
   */
  @Test
  public void successWhenNonProtectingParentCodeIsU() throws Exception {
    Allegation toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Allegation/valid/nonProtectingParentCodeU.json"),
        Allegation.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  /**
   * @throws Exception test standard
   * 
   */
  @Test
  public void successWhenNonProtectingParentCodeIsP() throws Exception {
    Allegation toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Allegation/valid/nonProtectingParentCodeP.json"),
        Allegation.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  /**
   * @throws Exception test standard
   * 
   */
  @Test
  public void successWhenNonProtectingParentCodeIsY() throws Exception {
    Allegation toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Allegation/valid/nonProtectingParentCodeY.json"),
        Allegation.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  /**
   * @throws Exception test standard
   * 
   */
  @Test
  public void successWhenNonProtectingParentCodeIsN() throws Exception {
    Allegation toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Allegation/valid/nonProtectingParentCodeN.json"),
        Allegation.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  /**
   * @throws Exception test standard
   * 
   */
  @Test
  public void successWhenNonProtectingParentCodeIsLowerCase() throws Exception {
    Allegation toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Allegation/valid/nonProtectingParentCodeLowerCase.json"),
        Allegation.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  /*
   * staffPersonAddedIndicator Tests
   */
  /**
   * @throws Exception test standard
   * 
   */
  @Test
  public void failsWhenStaffPersonAddedIndicatorMissing() throws Exception {
    Allegation toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Allegation/invalid/staffPersonAddedIndicatorMissing.json"),
        Allegation.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("staffPersonAddedIndicator may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  /**
   * @throws Exception test standard
   * 
   */
  @Test
  public void failsWhenStaffPersonAddedIndicatorNull() throws Exception {
    Allegation toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Allegation/invalid/staffPersonAddedIndicatorNull.json"),
        Allegation.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("staffPersonAddedIndicator may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  /**
   * @throws Exception test standard
   * 
   */
  @Test
  public void failsWhenStaffPersonAddedIndicatorEmpty() throws Exception {
    Allegation toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Allegation/invalid/staffPersonAddedIndicatorEmpty.json"),
        Allegation.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("staffPersonAddedIndicator may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  /**
   * @throws Exception test standard
   * 
   */
  @Test
  public void failsWhenStaffPersonAddedIndicatorAllWhitespace() throws Exception {
    Allegation toCreate = MAPPER.readValue(
        fixture(
            "fixtures/domain/legacy/Allegation/invalid/staffPersonAddedIndicatorAllWhitespace.json"),
        Allegation.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("staffPersonAddedIndicator may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * victimClientId Tests
   */
  /**
   * @throws Exception test standard
   * 
   */
  @Test
  public void failsWhenVictimClientIdMissing() throws Exception {
    Allegation toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Allegation/invalid/victimClientIdMissing.json"),
        Allegation.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("victimClientId may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  /**
   * @throws Exception test standard
   * 
   */
  @Test
  public void failsWhenVictimClientIdNull() throws Exception {
    Allegation toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Allegation/invalid/victimClientIdNull.json"),
        Allegation.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("victimClientId may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  /**
   * @throws Exception test standard
   * 
   */
  @Test
  public void failsWhenVictimClientIdEmpty() throws Exception {
    Allegation toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Allegation/invalid/victimClientIdEmpty.json"),
        Allegation.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("victimClientId may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  /**
   * @throws Exception test standard
   * 
   */
  @Test
  public void failsWhenVictimClientIdTooLong() throws Exception {
    Allegation toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Allegation/invalid/victimClientIdTooLong.json"),
        Allegation.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("victimClientId size must be between 10 and 10"),
        is(greaterThanOrEqualTo(0)));
  }

  /**
   * @throws Exception test standard
   * 
   */
  @Test
  public void failsWhenVictimClientIdTooShort() throws Exception {
    Allegation toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Allegation/invalid/victimClientIdTooShort.json"),
        Allegation.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("victimClientId size must be between 10 and 10"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * perpetratorClientId Tests
   */
  /**
   * @throws Exception test standard
   * 
   */
  @Test
  public void successWhenPerpetratorClientIdEmpty() throws Exception {
    Allegation toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Allegation/valid/perpetratorClientIEempty.json"),
        Allegation.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  /**
   * @throws Exception test standard
   * 
   */
  @Test
  public void successWhenPerpetratorClientIdNull() throws Exception {
    Allegation toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Allegation/valid/perpetratorClientIdNull.json"),
        Allegation.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  /**
   * @throws Exception test standard
   * 
   */
  @Test
  public void failsWhenPerpetratorClientIdTooLong() throws Exception {
    Allegation toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Allegation/invalid/perpetratorClientIdTooLong.json"),
        Allegation.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    // String message = response.readEntity(String.class);
    // System.out.print(message);
    assertThat(response.readEntity(String.class)
        .indexOf("perpetratorClientId size must be between 0 and 10"), is(greaterThanOrEqualTo(0)));
  }

  /*
   * referralId Tests
   */
  /**
   * @throws Exception test standard
   * 
   */
  @Test
  public void failsWhenReferralIdMissing() throws Exception {
    Allegation toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Allegation/invalid/referralIdMmissing.json"),
        Allegation.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("referralId may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  /**
   * @throws Exception test standard
   * 
   */
  @Test
  public void failsWhenReferralIdNull() throws Exception {
    Allegation toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Allegation/invalid/referralIdNull.json"), Allegation.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("referralId may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  /**
   * @throws Exception test standard
   * 
   */
  @Test
  public void failsWhenReferralIdEmpty() throws Exception {
    Allegation toCreate =
        MAPPER.readValue(fixture("fixtures/domain/legacy/Allegation/invalid/referralIdEmpty.json"),
            Allegation.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("referralId may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  /**
   * @throws Exception test standard
   * 
   */
  @Test
  public void failsWhenReferralIdTooLong() throws Exception {
    Allegation toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Allegation/invalid/referralIdTooLong.json"),
        Allegation.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("referralId size must be between 10 and 10"),
        is(greaterThanOrEqualTo(0)));
  }

  /**
   * @throws Exception test standard
   * 
   */
  @Test
  public void failsWhenReferralIdTooShort() throws Exception {
    Allegation toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Allegation/invalid/referralIdTooShort.json"),
        Allegation.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("referralId size must be between 10 and 10"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * countySpecificCode Tests
   */
  /**
   * @throws Exception test standard
   * 
   */
  @Test
  public void failsWhenCountySpecificCodeMissing() throws Exception {
    Allegation toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Allegation/invalid/countySpecificCodeMissing.json"),
        Allegation.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("countySpecificCode may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  /**
   * @throws Exception test standard
   * 
   */
  @Test
  public void failsWhenCountySpecificCodeNull() throws Exception {
    Allegation toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Allegation/invalid/countySpecificCodeNull.json"),
        Allegation.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("countySpecificCode may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  /**
   * @throws Exception test standard
   * 
   */
  @Test
  public void failsWhenCountySpecificCodeEmpty() throws Exception {
    Allegation toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Allegation/invalid/countySpecificCodeEmpty.json"),
        Allegation.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("countySpecificCode may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  /**
   * @throws Exception test standard
   * 
   */
  @Test
  public void failsWhenCountySpecificCodeTooLong() throws Exception {
    Allegation toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Allegation/invalid/countySpecificCodeTooLong.json"),
        Allegation.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class)
        .indexOf("countySpecificCode size must be between 1 and 2"), is(greaterThanOrEqualTo(0)));
  }

  /*
   * zippyCreatedIndicator Tests
   */
  /**
   * @throws Exception test standard
   * 
   */
  @Test
  public void failsWhenZippyCreatedIndicatorMissing() throws Exception {
    Allegation toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Allegation/invalid/zippyCreatedIndicatorMissing.json"),
        Allegation.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("zippyCreatedIndicator may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  /**
   * @throws Exception test standard
   * 
   */
  @Test
  public void failsWhenZippyCreatedIndicatorNull() throws Exception {
    Allegation toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Allegation/invalid/zippyCreatedIndicatorNull.json"),
        Allegation.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("zippyCreatedIndicator may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  /**
   * @throws Exception test standard
   * 
   */
  @Test
  public void failsWhenZippyCreatedIndicatorEmpty() throws Exception {
    Allegation toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Allegation/invalid/zippyCreatedIndicatorEmpty.json"),
        Allegation.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("zippyCreatedIndicator may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  /**
   * @throws Exception test standard
   * 
   */
  @Test
  public void failsWhenZippyCreatedIndicatorAllWhitespace() throws Exception {
    Allegation toCreate = MAPPER.readValue(
        fixture(
            "fixtures/domain/legacy/Allegation/invalid/zippyCreatedIndicatorAllWhitespace.json"),
        Allegation.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("zippyCreatedIndicator may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * placementFacilityType Tests
   */
  /**
   * @throws Exception test standard
   * 
   */
  @Test
  public void successWhenPlacementFacilityTypeEmpty() throws Exception {
    Allegation toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Allegation/valid/placementFacilityTypeEmpty.json"),
        Allegation.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  /**
   * @throws Exception test standard
   * 
   */
  @Test
  public void successWhenPlacementFacilityTypeNull() throws Exception {
    Allegation toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Allegation/valid/placementFacilityTypeNull.json"),
        Allegation.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  /*
   * Utils
   */
  private Allegation validAllegation() {
    return new Allegation("2016-10-31", (short) 2, "M", "Barber Shop", "2016-10-31", (short) 0,
        (short) 2180, "Fremont", "2016-10-31", false, "N", false, "AHooKwN0F4", "MKPFcB90F4",
        "AbiQCgu0Hj", "19", false, (short) 6574);
  }
}
