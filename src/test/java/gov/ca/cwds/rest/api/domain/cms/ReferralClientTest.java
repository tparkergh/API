package gov.ca.cwds.rest.api.domain.cms;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import gov.ca.cwds.data.CrudsDao;
import gov.ca.cwds.data.persistence.cms.Referral;
import gov.ca.cwds.rest.api.domain.DomainChef;
import gov.ca.cwds.rest.api.domain.cms.ReferralClient;
import gov.ca.cwds.rest.core.Api;
import gov.ca.cwds.rest.resources.cms.ReferralClientResource;
import io.dropwizard.jackson.Jackson;
import io.dropwizard.testing.junit.ResourceTestRule;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

public class ReferralClientTest {

  private static final String ROOT_RESOURCE = "/" + Api.RESOURCE_REFERRAL_CLIENT + "/";

  private static final ReferralClientResource mockedReferralClientResource =
      mock(ReferralClientResource.class);

  @ClassRule
  public static final ResourceTestRule resources =
      ResourceTestRule.builder().addResource(mockedReferralClientResource).build();

  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();
  private ReferralClient validReferralClient = validReferralClient();

  private final static DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
  private String referralId = "a";
  private String clientId = "b";
  private String approvalNumber = "c";
  private Short approvalStatusType = 1;
  private Short dispositionClosureReasonType = 2;
  private String dispositionCode = "d";
  private String dispositionDate = "1973-11-22";
  private Boolean selfReportedIndicator = Boolean.TRUE;
  private Boolean staffPersonAddedIndicator = Boolean.FALSE;
  private String dispositionClosureDescription = "e";
  private Short ageNumber = 3;
  private String agePeriodCode = "f";
  private String countySpecificCode = "g";
  private Boolean mentalHealthIssuesIndicator = Boolean.TRUE;
  private Boolean alcoholIndicator = null;
  private Boolean drugIndicator = Boolean.FALSE;

  public ReferralClientTest() throws ParseException {}

  @Before
  public void setup() {
    @SuppressWarnings("rawtypes")
    CrudsDao crudsDao = mock(CrudsDao.class);
    when(crudsDao.find(any())).thenReturn(mock(Referral.class));

    when(mockedReferralClientResource.create(eq(validReferralClient)))
        .thenReturn(Response.status(Response.Status.NO_CONTENT).entity(null).build());

  }

  @Test
  public void serializesToJSON() throws Exception {
    final String expected = MAPPER.writeValueAsString(MAPPER.readValue(
        fixture("fixtures/domain/legacy/ReferralClient/valid/valid.json"), ReferralClient.class));

    assertThat(MAPPER.writeValueAsString(validReferralClient()), is(equalTo(expected)));
  }

  @Test
  public void deserializesFromJSON() throws Exception {
    assertThat(MAPPER.readValue(fixture("fixtures/domain/legacy/ReferralClient/valid/valid.json"),
        ReferralClient.class), is(equalTo(validReferralClient())));
  }

  /*
   * Constructor Tests
   */
  @Test
  public void persistentObjectConstructorTest() throws Exception {
    ReferralClient domain =
        new ReferralClient(approvalNumber, approvalStatusType, dispositionClosureReasonType,
            dispositionCode, dispositionDate, selfReportedIndicator, staffPersonAddedIndicator,
            referralId, clientId, dispositionClosureDescription, ageNumber, agePeriodCode,
            countySpecificCode, mentalHealthIssuesIndicator, alcoholIndicator, drugIndicator);
    gov.ca.cwds.data.persistence.cms.ReferralClient persistent =
        new gov.ca.cwds.data.persistence.cms.ReferralClient(domain, "lastUpdatedId");

    ReferralClient totest = new ReferralClient(persistent);
    assertThat(totest.getReferralId(), is(equalTo(persistent.getReferralId())));
    assertThat(totest.getClientId(), is(equalTo(persistent.getClientId())));
    assertThat(totest.getApprovalNumber(), is(equalTo(persistent.getApprovalNumber())));
    assertThat(totest.getApprovalStatusType(), is(equalTo(persistent.getApprovalStatusType())));
    assertThat(totest.getDispositionClosureReasonType(),
        is(equalTo(persistent.getDispositionClosureReasonType())));
    assertThat(totest.getDispositionCode(), is(equalTo(persistent.getDispositionCode())));
    assertThat(totest.getDispositionDate(),
        is(equalTo(df.format(persistent.getDispositionDate()))));
    assertThat(totest.getSelfReportedIndicator(),
        is(equalTo(DomainChef.uncookBooleanString(persistent.getSelfReportedIndicator()))));
    assertThat(totest.getStaffPersonAddedIndicator(),
        is(equalTo(DomainChef.uncookBooleanString(persistent.getStaffPersonAddedIndicator()))));
    assertThat(totest.getDispositionClosureDescription(),
        is(equalTo(persistent.getDispositionClosureDescription())));
    assertThat(totest.getAgeNumber(), is(equalTo(persistent.getAgeNumber())));
    assertThat(totest.getAgePeriodCode(), is(equalTo(persistent.getAgePeriodCode())));
    assertThat(totest.getCountySpecificCode(), is(equalTo(persistent.getCountySpecificCode())));
    assertThat(totest.getMentalHealthIssuesIndicator(),
        is(equalTo(DomainChef.uncookBooleanString(persistent.getMentalHealthIssuesIndicator()))));
    assertThat(totest.getAlcoholIndicator(), is(nullValue()));
    assertThat(totest.getDrugIndicator(),
        is(equalTo(DomainChef.uncookBooleanString(persistent.getDrugIndicator()))));
  }

  @Test
  public void jsonCreatorConstructorTest() throws Exception {
    ReferralClient referralClient =
        new ReferralClient(approvalNumber, approvalStatusType, dispositionClosureReasonType,
            dispositionCode, dispositionDate, selfReportedIndicator, staffPersonAddedIndicator,
            referralId, clientId, dispositionClosureDescription, ageNumber, agePeriodCode,
            countySpecificCode, mentalHealthIssuesIndicator, alcoholIndicator, drugIndicator);

    assertThat(referralClient.getReferralId(), is(equalTo(referralId)));
    assertThat(referralClient.getClientId(), is(equalTo(clientId)));
    assertThat(referralClient.getApprovalNumber(), is(equalTo(approvalNumber)));
    assertThat(referralClient.getApprovalStatusType(), is(equalTo(approvalStatusType)));
    assertThat(referralClient.getDispositionClosureReasonType(),
        is(equalTo(dispositionClosureReasonType)));
    assertThat(referralClient.getDispositionCode(), is(equalTo(dispositionCode)));
    assertThat(referralClient.getDispositionDate(), is(equalTo(dispositionDate)));
    assertThat(referralClient.getSelfReportedIndicator(), is(equalTo(Boolean.TRUE)));
    assertThat(referralClient.getStaffPersonAddedIndicator(), is(equalTo(Boolean.FALSE)));
    assertThat(referralClient.getDispositionClosureDescription(),
        is(equalTo(dispositionClosureDescription)));
    assertThat(referralClient.getAgeNumber(), is(equalTo(ageNumber)));
    assertThat(referralClient.getAgePeriodCode(), is(equalTo(agePeriodCode)));
    assertThat(referralClient.getCountySpecificCode(), is(equalTo(countySpecificCode)));
    assertThat(referralClient.getMentalHealthIssuesIndicator(), is(equalTo(Boolean.TRUE)));
    assertThat(referralClient.getAlcoholIndicator(), is(nullValue()));
    assertThat(referralClient.getDrugIndicator(), is(equalTo(Boolean.FALSE)));
  }

  @Test
  public void equalsHashCodeWork() {
    EqualsVerifier.forClass(ReferralClient.class).suppress(Warning.NONFINAL_FIELDS).verify();
  }

  /*
   * Successful Tests
   */
  @Test
  public void successfulWithValid() throws Exception {
    ReferralClient toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/ReferralClient/valid/valid.json"), ReferralClient.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    // String message = response.readEntity(String.class);
    // System.out.print(message);
    //
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void successfulWithOptionalsNotIncluded() throws Exception {
    ReferralClient toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/ReferralClient/valid/optionalsNotIncluded.json"),
        ReferralClient.class);
    assertThat(
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON)).getStatus(),
        is(equalTo(204)));
  }

  /*
   * approvalNumber Tests
   */
  @Test
  public void successWhenApprovalNumberEmpty() throws Exception {
    ReferralClient toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/ReferralClient/valid/approvalNumberempty.json"),
        ReferralClient.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void successWhenApprovalNumberNull() throws Exception {
    ReferralClient toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/ReferralClient/valid/approvalNumbernull.json"),
        ReferralClient.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void failsWhenApprovalNumberTooLong() throws Exception {
    ReferralClient toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/ReferralClient/invalid/approvalNumberTooLong.json"),
        ReferralClient.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("approvalNumber size must be between 0 and 10"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * dispositionClosureReasonType Tests
   */
  @Test
  public void failsWhenDispositionClosureReasonTypeMissing() throws Exception {
    ReferralClient toCreate = MAPPER.readValue(
        fixture(
            "fixtures/domain/legacy/ReferralClient/invalid/dispositionClosureReasonTypeMissing.json"),
        ReferralClient.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("dispositionClosureReasonType may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenDispositionClosureReasonTypeNull() throws Exception {
    ReferralClient toCreate = MAPPER.readValue(
        fixture(
            "fixtures/domain/legacy/ReferralClient/invalid/dispositionClosureReasonTypeNull.json"),
        ReferralClient.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("dispositionClosureReasonType may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenDispositionClosureReasonTypeAllWhiteSpace() throws Exception {
    ReferralClient toCreate = MAPPER.readValue(
        fixture(
            "fixtures/domain/legacy/ReferralClient/invalid/dispositionClosureReasonTypeAllWhiteSpace.json"),
        ReferralClient.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("dispositionClosureReasonType may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void successWhenDispositionClosureReasonTypeZero() throws Exception {
    ReferralClient toCreate = MAPPER.readValue(
        fixture(
            "fixtures/domain/legacy/ReferralClient/valid/dispositionClosureReasonTypeZero.json"),
        ReferralClient.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  /*
   * approvalStatusType Tests
   */
  @Test
  public void failsWhenApprovalStatusTypeMissing() throws Exception {
    ReferralClient toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/ReferralClient/invalid/approvalStatusTypeMissing.json"),
        ReferralClient.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("approvalStatusType may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenApprovalStatusTypeNull() throws Exception {
    ReferralClient toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/ReferralClient/invalid/approvalStatusTypeNull.json"),
        ReferralClient.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("approvalStatusType may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenApprovalStatusTypeEmpty() throws Exception {
    ReferralClient toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/ReferralClient/invalid/approvalStatusTypeEmpty.json"),
        ReferralClient.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("approvalStatusType may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * dispositionCode Tests
   */
  @Test
  public void failsWhenDispositionCodeMissing() throws Exception {
    ReferralClient toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/ReferralClient/invalid/dispositionCodeMissing.json"),
        ReferralClient.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("dispositionCode may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenDispositionCodeNull() throws Exception {
    ReferralClient toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/ReferralClient/invalid/dispositionCodeNull.json"),
        ReferralClient.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("dispositionCode may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenDispositionCodeEmpty() throws Exception {
    ReferralClient toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/ReferralClient/invalid/dispositionCodeEmpty.json"),
        ReferralClient.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("dispositionCode may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenDispositionCodeTooLong() throws Exception {
    ReferralClient toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/ReferralClient/invalid/dispositionCodeTooLong.json"),
        ReferralClient.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("dispositionCode size must be 1"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenDispositionCodeInvalid() throws Exception {
    ReferralClient toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/ReferralClient/invalid/dispositionCodeInvalid.json"),
        ReferralClient.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    // String message = response.readEntity(String.class);
    // System.out.print(message);
    assertThat(
        response.readEntity(String.class).indexOf("dispositionCode must be one of [A, I, S, X]"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void successWhenDispositionCodeA() throws Exception {
    ReferralClient toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/ReferralClient/valid/dispositionCodeA.json"),
        ReferralClient.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void successWhenDispositionCodeI() throws Exception {
    ReferralClient toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/ReferralClient/valid/dispositionCodeI.json"),
        ReferralClient.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void successWhenDispositionCodeS() throws Exception {
    ReferralClient toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/ReferralClient/valid/dispositionCodeS.json"),
        ReferralClient.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void successWhenDispositionCodeX() throws Exception {
    ReferralClient toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/ReferralClient/valid/dispositionCodeX.json"),
        ReferralClient.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  /*
   * dispositionDate Tests
   */
  @Test
  public void successWhenDispositionDateEmpty() throws Exception {
    ReferralClient toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/ReferralClient/valid/dispositionDateEmpty.json"),
        ReferralClient.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void successWhenDispositionDateNull() throws Exception {
    ReferralClient toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/ReferralClient/valid/dispositionDateNull.json"),
        ReferralClient.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void failsWhenDispositionDateWrongFormat() throws Exception {
    ReferralClient toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/ReferralClient/invalid/dispositionDateWrongFormat.json"),
        ReferralClient.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf(
        "dispositionDate must be in the format of yyyy-MM-dd"), is(greaterThanOrEqualTo(0)));
  }

  /*
   * selfReportedIndicator Tests
   */
  @Test
  public void failsWhenSelfReportedIndicatorMissing() throws Exception {
    ReferralClient toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/ReferralClient/invalid/selfReportedIndicatorMissing.json"),
        ReferralClient.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("selfReportedIndicator may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenSelfReportedIndicatorNull() throws Exception {
    ReferralClient toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/ReferralClient/invalid/selfReportedIndicatorNull.json"),
        ReferralClient.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("selfReportedIndicator may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenSelfReportedIndicatorEmpty() throws Exception {
    ReferralClient toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/ReferralClient/invalid/selfReportedIndicatorEmpty.json"),
        ReferralClient.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("selfReportedIndicator may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenSelfReportedIndicatorAllWhitespace() throws Exception {
    ReferralClient toCreate = MAPPER.readValue(
        fixture(
            "fixtures/domain/legacy/ReferralClient/invalid/selfReportedIndicatorAllWhitespace.json"),
        ReferralClient.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("selfReportedIndicator may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * staffPersonAddedIndicator Tests
   */
  @Test
  public void failsWhenStaffPersonAddedIndicatorMissing() throws Exception {
    ReferralClient toCreate = MAPPER.readValue(
        fixture(
            "fixtures/domain/legacy/ReferralClient/invalid/staffPersonAddedIndicatorMissing.json"),
        ReferralClient.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("staffPersonAddedIndicator may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenStaffPersonAddedIndicatorNull() throws Exception {
    ReferralClient toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/ReferralClient/invalid/staffPersonAddedIndicatorNull.json"),
        ReferralClient.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("staffPersonAddedIndicator may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenStaffPersonAddedIndicatorEmpty() throws Exception {
    ReferralClient toCreate = MAPPER.readValue(
        fixture(
            "fixtures/domain/legacy/ReferralClient/invalid/staffPersonAddedIndicatorEmpty.json"),
        ReferralClient.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("staffPersonAddedIndicator may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenStaffPersonAddedIndicatorAllWhitespace() throws Exception {
    ReferralClient toCreate = MAPPER.readValue(
        fixture(
            "fixtures/domain/legacy/ReferralClient/invalid/staffPersonAddedIndicatorAllWhitespace.json"),
        ReferralClient.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("staffPersonAddedIndicator may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * referralId Tests
   */
  @Test
  public void failsWhenReferralIdMissing() throws Exception {
    ReferralClient toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/ReferralClient/invalid/referralIdMissing.json"),
        ReferralClient.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("referralId may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenReferralIdNull() throws Exception {
    ReferralClient toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/ReferralClient/invalid/referralIdNull.json"),
        ReferralClient.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("referralId may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenReferralIdEmpty() throws Exception {
    ReferralClient toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/ReferralClient/invalid/referralIdEmpty.json"),
        ReferralClient.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("referralId may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenReferralIdTooLong() throws Exception {
    ReferralClient toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/ReferralClient/invalid/referralIdTooLong.json"),
        ReferralClient.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("referralId size must be between 10 and 10"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenReferralIdAllWhiteSpace() throws Exception {
    ReferralClient toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/ReferralClient/invalid/referralIdAllWhiteSpace.json"),
        ReferralClient.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    // String message = response.readEntity(String.class);
    // System.out.print(message);
    assertThat(response.readEntity(String.class).indexOf("referralId may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * clientId Tests
   */
  @Test
  public void failsWhenClientIdMissing() throws Exception {
    ReferralClient toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/ReferralClient/invalid/clientIdMissing.json"),
        ReferralClient.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("clientId may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenClientIdNull() throws Exception {
    ReferralClient toCreate =
        MAPPER.readValue(fixture("fixtures/domain/legacy/ReferralClient/invalid/clientIdNull.json"),
            ReferralClient.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("clientId may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenClientIdEmpty() throws Exception {
    ReferralClient toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/ReferralClient/invalid/clientIdEmpty.json"),
        ReferralClient.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("clientId may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenClientIdTooLong() throws Exception {
    ReferralClient toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/ReferralClient/invalid/clientIdTooLong.json"),
        ReferralClient.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("clientId size must be between 10 and 10"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenClientIdAllWhiteSpace() throws Exception {
    ReferralClient toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/ReferralClient/invalid/clientIdAllWhiteSpace.json"),
        ReferralClient.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("clientId may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * dispositionClosureDescription Tests
   */
  @Test
  public void failsWhenDispositionClosureDescriptionMissing() throws Exception {
    ReferralClient toCreate = MAPPER.readValue(
        fixture(
            "fixtures/domain/legacy/ReferralClient/invalid/dispositionClosureDescriptionMissing.json"),
        ReferralClient.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("dispositionClosureDescription may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenDispositionClosureDescriptionNull() throws Exception {
    ReferralClient toCreate = MAPPER.readValue(
        fixture(
            "fixtures/domain/legacy/ReferralClient/invalid/dispositionClosureDescriptionNull.json"),
        ReferralClient.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("dispositionClosureDescription may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenDispositionClosureDescriptionEmpty() throws Exception {
    ReferralClient toCreate = MAPPER.readValue(
        fixture(
            "fixtures/domain/legacy/ReferralClient/invalid/dispositionClosureDescriptionEmpty.json"),
        ReferralClient.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("dispositionClosureDescription may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * ageNumber Tests
   */
  @Test
  public void failsWhenAgeNumberMissing() throws Exception {
    ReferralClient toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/ReferralClient/invalid/ageNumberMissing.json"),
        ReferralClient.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("ageNumber may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenAgeNumberNull() throws Exception {
    ReferralClient toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/ReferralClient/invalid/ageNumberNull.json"),
        ReferralClient.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("ageNumber may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * agePeriodCode Tests
   */
  @Test
  public void failsWhenAgePeriodCodeMissing() throws Exception {
    ReferralClient toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/ReferralClient/invalid/agePeriodCodeMissing.json"),
        ReferralClient.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("agePeriodCode may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenAgePeriodCodeNull() throws Exception {
    ReferralClient toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/ReferralClient/invalid/agePeriodCodeNull.json"),
        ReferralClient.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("agePeriodCode may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenAgePeriodCodeEmpty() throws Exception {
    ReferralClient toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/ReferralClient/invalid/agePeriodCodeEmpty.json"),
        ReferralClient.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("agePeriodCode may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenAgePeriodCodeTooLong() throws Exception {
    ReferralClient toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/ReferralClient/invalid/agePeriodCodeTooLong.json"),
        ReferralClient.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("agePeriodCode size must be 1"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void successWhenAgePeriodCodeAllWhiteSpace() throws Exception {
    ReferralClient toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/ReferralClient/valid/agePeriodCodeAllWhiteSpace.json"),
        ReferralClient.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  /*
   * countySpecificCode Tests
   */
  @Test
  public void failsWhenCountySpecificCodeMissing() throws Exception {
    ReferralClient toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/ReferralClient/invalid/countySpecificCodeMissing.json"),
        ReferralClient.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("countySpecificCode may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenCountySpecificCodeNull() throws Exception {
    ReferralClient toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/ReferralClient/invalid/countySpecificCodeNull.json"),
        ReferralClient.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("countySpecificCode may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenCountySpecificCodeEmpty() throws Exception {
    ReferralClient toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/ReferralClient/invalid/countySpecificCodeEmpty.json"),
        ReferralClient.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("countySpecificCode may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenCountySpecificCodeTooLong() throws Exception {
    ReferralClient toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/ReferralClient/invalid/countySpecificCodeTooLong.json"),
        ReferralClient.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class)
        .indexOf("countySpecificCode size must be between 1 and 2"), is(greaterThanOrEqualTo(0)));
  }

  /*
   * mentalHealthIssuesIndicator Tests
   */
  @Test
  public void successWhenMentalHealthIssuesIndicatorEmpty() throws Exception {
    ReferralClient toCreate = MAPPER.readValue(
        fixture(
            "fixtures/domain/legacy/ReferralClient/valid/mentalHealthIssuesIndicatorEmpty.json"),
        ReferralClient.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void successWhenMentalHealthIssuesIndicatorNull() throws Exception {
    ReferralClient toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/ReferralClient/valid/mentalHealthIssuesIndicatorNull.json"),
        ReferralClient.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  /*
   * alcoholIndicator Tests
   */
  @Test
  public void successWhenAlcoholIndicatorEmpty() throws Exception {
    ReferralClient toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/ReferralClient/valid/alcoholIndicatorEmpty.json"),
        ReferralClient.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void successWhenAlcoholIndicatorNull() throws Exception {
    ReferralClient toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/ReferralClient/valid/alcoholIndicatorNull.json"),
        ReferralClient.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  /*
   * drugIndicator Tests
   */
  @Test
  public void successWhenDrugIndicatorEmpty() throws Exception {
    ReferralClient toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/ReferralClient/valid/drugIndicatorEmpty.json"),
        ReferralClient.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void successWhenDrugIndicatorNull() throws Exception {
    ReferralClient toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/ReferralClient/valid/drugIndicatorNull.json"),
        ReferralClient.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  /*
   * Utils
   */
  private ReferralClient validReferralClient() {
    return new ReferralClient("A123", new Short((short) 123), new Short((short) 123), "A",
        "2000-01-01", false, false, "1234567ABC", "ABC1234567", "description abc",
        new Short((short) 12), "M", "AB", false, false, false);
  }
}
