package gov.ca.cwds.rest.api.domain.cms;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
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
import gov.ca.cwds.rest.resources.cms.CrossReportResource;
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
public class CrossReportTest {

  private static final String ROOT_RESOURCE = "/" + Api.RESOURCE_CROSS_REPORT + "/";

  @After
  public void ensureServiceLocatorPopulated() {
    JerseyGuiceUtils.reset();
  }

  @ClassRule
  public static JerseyGuiceRule rule = new JerseyGuiceRule();

  private static final CrossReportResource mockedCrossReportResource =
      mock(CrossReportResource.class);

  @ClassRule
  public static final ResourceTestRule resources =
      ResourceTestRule.builder().addResource(mockedCrossReportResource).build();


  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

  private final static DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
  private final static DateFormat timeOnlyFormat = new SimpleDateFormat("HH:mm:ss");
  private String thirdId = "b";
  private Short crossReportMethodType = 1;
  private Boolean filedOutOfStateIndicator = Boolean.TRUE;
  private Boolean governmentOrgCrossRptIndicatorVar = Boolean.FALSE;
  private String informTime = "16:41:49";
  private String recipientBadgeNumber = "d";
  private Integer recipientPhoneExtensionNumber = 2;
  private BigDecimal recipientPhoneNumber = new BigDecimal(3);
  private String informDate = "1973-11-22";
  private String recipientPositionTitleDesc = "e";
  private String referenceNumber = "f";
  private String referralId = "a";
  private String lawEnforcementId = "g";
  private String staffPersonId = "h";
  private String description = "i";
  private String recipientName = "j";
  private String outStateLawEnforcementAddr = "k";
  private String countySpecificCode = "l";
  private Boolean lawEnforcementIndicator = Boolean.TRUE;
  private Boolean outStateLawEnforcementIndicator = Boolean.FALSE;
  private Boolean satisfyCrossReportIndicator = Boolean.TRUE;

  @Before
  public void setup() throws Exception {
    CrossReport validCrossReport = validCrossReport();

    when(mockedCrossReportResource.create(eq(validCrossReport)))
        .thenReturn(Response.status(Response.Status.NO_CONTENT).entity(null).build());

    when(mockedCrossReportResource.create(eq(validCrossReport)))
        .thenReturn(Response.status(Response.Status.NO_CONTENT).entity(null).build());
  }

  /*
   * Constructor Tests
   */
  @Test
  public void persistentObjectConstructorTest() throws Exception {
    CrossReport domain = new CrossReport(thirdId, crossReportMethodType, filedOutOfStateIndicator,
        governmentOrgCrossRptIndicatorVar, informTime, recipientBadgeNumber,
        recipientPhoneExtensionNumber, recipientPhoneNumber, informDate, recipientPositionTitleDesc,
        referenceNumber, referralId, lawEnforcementId, staffPersonId, description, recipientName,
        outStateLawEnforcementAddr, countySpecificCode, lawEnforcementIndicator,
        outStateLawEnforcementIndicator, satisfyCrossReportIndicator);
    gov.ca.cwds.data.persistence.cms.CrossReport persistent =
        new gov.ca.cwds.data.persistence.cms.CrossReport(thirdId, domain, "lastUpdatedId");

    CrossReport totest = new CrossReport(persistent);
    assertThat(totest.getThirdId(), is(equalTo(persistent.getThirdId())));
    assertThat(totest.getCrossReportMethodType(),
        is(equalTo(persistent.getCrossReportMethodType())));
    assertThat(totest.getFiledOutOfStateIndicator(),
        is(equalTo(DomainChef.uncookBooleanString(persistent.getFiledOutOfStateIndicator()))));
    assertThat(totest.getGovernmentOrgCrossRptIndicatorVar(), is(equalTo(
        DomainChef.uncookBooleanString(persistent.getGovernmentOrgCrossRptIndicatorVar()))));
    assertThat(totest.getInformTime(),
        is(equalTo(timeOnlyFormat.format(persistent.getInformTime()))));
    assertThat(totest.getRecipientBadgeNumber(), is(equalTo(persistent.getRecipientBadgeNumber())));
    assertThat(totest.getRecipientPhoneExtensionNumber(),
        is(equalTo(persistent.getRecipientPhoneExtensionNumber())));
    assertThat(totest.getRecipientPhoneNumber(), is(equalTo(persistent.getRecipientPhoneNumber())));
    assertThat(totest.getInformDate(), is(equalTo(df.format(persistent.getInformDate()))));
    assertThat(totest.getRecipientPositionTitleDesc(),
        is(equalTo(persistent.getRecipientPositionTitleDesc())));
    assertThat(totest.getReferenceNumber(), is(equalTo(persistent.getReferenceNumber())));
    assertThat(totest.getReferralId(), is(equalTo(persistent.getReferralId())));
    assertThat(totest.getLawEnforcementId(), is(equalTo(persistent.getLawEnforcementId())));
    assertThat(totest.getStaffPersonId(), is(equalTo(persistent.getStaffPersonId())));
    assertThat(totest.getDescription(), is(equalTo(persistent.getDescription())));
    assertThat(totest.getRecipientName(), is(equalTo(persistent.getRecipientName())));
    assertThat(totest.getOutStateLawEnforcementAddr(),
        is(equalTo(persistent.getOutStateLawEnforcementAddr())));
    assertThat(totest.getCountySpecificCode(), is(equalTo(persistent.getCountySpecificCode())));
    assertThat(totest.getLawEnforcementIndicator(),
        is(equalTo(DomainChef.uncookBooleanString(persistent.getLawEnforcementIndicator()))));
    assertThat(totest.getOutStateLawEnforcementIndicator(), is(
        equalTo(DomainChef.uncookBooleanString(persistent.getOutStateLawEnforcementIndicator()))));
    assertThat(totest.getSatisfyCrossReportIndicator(),
        is(equalTo(DomainChef.uncookBooleanString(persistent.getSatisfyCrossReportIndicator()))));
  }

  @Test
  public void jsonCreatorConstructorTest() throws Exception {
    CrossReport domain = new CrossReport(thirdId, crossReportMethodType, filedOutOfStateIndicator,
        governmentOrgCrossRptIndicatorVar, informTime, recipientBadgeNumber,
        recipientPhoneExtensionNumber, recipientPhoneNumber, informDate, recipientPositionTitleDesc,
        referenceNumber, referralId, lawEnforcementId, staffPersonId, description, recipientName,
        outStateLawEnforcementAddr, countySpecificCode, lawEnforcementIndicator,
        outStateLawEnforcementIndicator, satisfyCrossReportIndicator);

    assertThat(domain.getThirdId(), is(equalTo(thirdId)));
    assertThat(domain.getCrossReportMethodType(), is(equalTo(crossReportMethodType)));
    assertThat(domain.getFiledOutOfStateIndicator(), is(equalTo(filedOutOfStateIndicator)));
    assertThat(domain.getGovernmentOrgCrossRptIndicatorVar(),
        is(equalTo(governmentOrgCrossRptIndicatorVar)));
    assertThat(domain.getInformTime(), is(equalTo(informTime)));
    assertThat(domain.getRecipientBadgeNumber(), is(equalTo(recipientBadgeNumber)));
    assertThat(domain.getRecipientPhoneExtensionNumber(),
        is(equalTo(recipientPhoneExtensionNumber)));
    assertThat(domain.getRecipientPhoneNumber(), is(equalTo(recipientPhoneNumber)));
    assertThat(domain.getInformDate(), is(equalTo(informDate)));
    assertThat(domain.getRecipientPositionTitleDesc(), is(equalTo(recipientPositionTitleDesc)));
    assertThat(domain.getReferenceNumber(), is(equalTo(referenceNumber)));
    assertThat(domain.getReferralId(), is(equalTo(referralId)));
    assertThat(domain.getLawEnforcementId(), is(equalTo(lawEnforcementId)));
    assertThat(domain.getStaffPersonId(), is(equalTo(staffPersonId)));
    assertThat(domain.getDescription(), is(equalTo(description)));
    assertThat(domain.getRecipientName(), is(equalTo(recipientName)));
    assertThat(domain.getOutStateLawEnforcementAddr(), is(equalTo(outStateLawEnforcementAddr)));
    assertThat(domain.getCountySpecificCode(), is(equalTo(countySpecificCode)));
    assertThat(domain.getLawEnforcementIndicator(), is(equalTo(lawEnforcementIndicator)));
    assertThat(domain.getOutStateLawEnforcementIndicator(),
        is(equalTo(outStateLawEnforcementIndicator)));
    assertThat(domain.getSatisfyCrossReportIndicator(), is(equalTo(satisfyCrossReportIndicator)));
  }

  @Test
  public void equalsHashCodeWork() {
    EqualsVerifier.forClass(CrossReport.class).suppress(Warning.NONFINAL_FIELDS)
        .withIgnoredFields("messages").verify();
  }

  @Test
  public void serializesToJSON() throws Exception {
    final String expected = MAPPER.writeValueAsString(MAPPER.readValue(
        fixture("fixtures/domain/legacy/CrossReport/valid/valid.json"), CrossReport.class));

    assertThat(MAPPER.writeValueAsString(validCrossReport()), is(equalTo(expected)));
  }

  @Test
  public void deserializesFromJSON() throws Exception {
    assertThat(MAPPER.readValue(fixture("fixtures/domain/legacy/CrossReport/valid/valid.json"),
        CrossReport.class), is(equalTo(validCrossReport())));
  }

  /*
   * Successful Tests
   */
  @Test
  public void successfulWithValid() throws Exception {
    CrossReport toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/CrossReport/valid/valid.json"), CrossReport.class);
    assertThat(
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON)).getStatus(),
        is(equalTo(204)));
  }

  @Test
  public void successfulWithOptionalsNotIncluded() throws Exception {
    CrossReport toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/CrossReport/valid/optionalsNotIncluded.json"),
        CrossReport.class);
    assertThat(
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON)).getStatus(),
        is(equalTo(204)));
  }

  /*
   * thirdId Tests
   */
  @Test
  public void failsWhenThirdIdMissing() throws Exception {
    CrossReport toCreate =
        MAPPER.readValue(fixture("fixtures/domain/legacy/CrossReport/invalid/thirdIdMissing.json"),
            CrossReport.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("thirdId may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenThirdIdNull() throws Exception {
    CrossReport toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/CrossReport/invalid/thirdIdNull.json"), CrossReport.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("thirdId may not be null"),
        is(greaterThanOrEqualTo(0)));
  }


  @Test
  public void failsWhenThirdIdTooLong() throws Exception {
    CrossReport toCreate =
        MAPPER.readValue(fixture("fixtures/domain/legacy/CrossReport/invalid/thirdIdTooLong.json"),
            CrossReport.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("thirdId size must be between 0 and 10"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * crossReportMethodType Tests
   */
  @Test
  public void failsWhenCrossReportMethodTypeMissing() throws Exception {
    CrossReport toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/CrossReport/invalid/crossReportMethodTypeMissing.json"),
        CrossReport.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("crossReportMethodType may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenCrossReportMethodTypeNull() throws Exception {
    CrossReport toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/CrossReport/invalid/crossReportMethodTypeNull.json"),
        CrossReport.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("crossReportMethodType may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenCrossReportMethodTypeEmpty() throws Exception {
    CrossReport toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/CrossReport/invalid/crossReportMethodTypeEmpty.json"),
        CrossReport.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("crossReportMethodType may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * filedOutOfStateIndicator Tests
   */
  @Test
  public void failsWhenFiledOutOfStateIndicatorMissing() throws Exception {
    CrossReport toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/CrossReport/invalid/filedOutOfStateIndicatorMissing.json"),
        CrossReport.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("filedOutOfStateIndicator may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenFiledOutOfStateIndicatorNull() throws Exception {
    CrossReport toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/CrossReport/invalid/filedOutOfStateIndicatorNull.json"),
        CrossReport.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("filedOutOfStateIndicator may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenFiledOutOfStateIndicatorEmpty() throws Exception {
    CrossReport toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/CrossReport/invalid/filedOutOfStateIndicatorEmpty.json"),
        CrossReport.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("filedOutOfStateIndicator may not be null"),
        is(greaterThanOrEqualTo(0)));
  }


  @Test
  public void failsWhenFiledOutOfStateIndicatorAllWhitespace() throws Exception {
    CrossReport toCreate = MAPPER.readValue(
        fixture(
            "fixtures/domain/legacy/CrossReport/invalid/filedOutOfStateIndicatorAllWhitespace.json"),
        CrossReport.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("filedOutOfStateIndicator may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * governmentOrgCrossRptIndicatorVar Tests
   */
  @Test
  public void failsWhenGovernmentOrgCrossRptIndicatorVarMissing() throws Exception {
    CrossReport toCreate = MAPPER.readValue(
        fixture(
            "fixtures/domain/legacy/CrossReport/invalid/governmentOrgCrossRptIndicatorVarMissing.json"),
        CrossReport.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class)
        .indexOf("governmentOrgCrossRptIndicatorVar may not be null"), is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenGovernmentOrgCrossRptIndicatorVarNull() throws Exception {
    CrossReport toCreate = MAPPER.readValue(
        fixture(
            "fixtures/domain/legacy/CrossReport/invalid/governmentOrgCrossRptIndicatorVarNull.json"),
        CrossReport.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class)
        .indexOf("governmentOrgCrossRptIndicatorVar may not be null"), is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenGovernmentOrgCrossRptIndicatorVarEmpty() throws Exception {
    CrossReport toCreate = MAPPER.readValue(
        fixture(
            "fixtures/domain/legacy/CrossReport/invalid/governmentOrgCrossRptIndicatorVarEmpty.json"),
        CrossReport.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class)
        .indexOf("governmentOrgCrossRptIndicatorVar may not be null"), is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenGovernmentOrgCrossRptIndicatorVarAllWhitespace() throws Exception {
    CrossReport toCreate = MAPPER.readValue(
        fixture(
            "fixtures/domain/legacy/CrossReport/invalid/governmentOrgCrossRptIndicatorVarAllWhitespace.json"),
        CrossReport.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class)
        .indexOf("governmentOrgCrossRptIndicatorVar may not be null"), is(greaterThanOrEqualTo(0)));
  }

  /*
   * informTime Tests
   */
  @Test
  public void successWhenInformTimeEmpty() throws Exception {
    CrossReport toCreate =
        MAPPER.readValue(fixture("fixtures/domain/legacy/CrossReport/valid/informTimeEmpty.json"),
            CrossReport.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void successWhenInformTimeNull() throws Exception {
    CrossReport toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/CrossReport/valid/informTimeNull.json"), CrossReport.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void failsWhenInformTimeWrongFormat() throws Exception {
    CrossReport toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/CrossReport/invalid/informTimeWrongFormat.json"),
        CrossReport.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("informTime must be in the format of HH:mm:ss"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * recipientBadgeNumber Tests
   */
  @Test
  public void failsWhenRecipientBadgeNumberMissing() throws Exception {
    CrossReport toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/CrossReport/invalid/recipientBadgeNumberMissing.json"),
        CrossReport.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("recipientBadgeNumber may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenRecipientBadgeNumberNull() throws Exception {
    CrossReport toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/CrossReport/invalid/recipientBadgeNumberNull.json"),
        CrossReport.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("recipientBadgeNumber may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void successWhenRecipientBadgeNumberEmpty() throws Exception {
    CrossReport toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/CrossReport/valid/recipientBadgeNumberEmpty.json"),
        CrossReport.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void failsWhenRecipientBadgeNumberTooLong() throws Exception {
    CrossReport toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/CrossReport/invalid/recipientBadgeNumberTooLong.json"),
        CrossReport.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class)
        .indexOf("recipientBadgeNumber size must be between 0 and 6"), is(greaterThanOrEqualTo(0)));
  }

  /*
   * recipientPhoneExtensionNumber Tests
   */
  @Test
  public void failsWhenRecipientPhoneExtensionNumberMissing() throws Exception {
    CrossReport toCreate = MAPPER.readValue(
        fixture(
            "fixtures/domain/legacy/CrossReport/invalid/recipientPhoneExtensionNumberMissing.json"),
        CrossReport.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("recipientPhoneExtensionNumber may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenRecipientPhoneExtensionNumberNull() throws Exception {
    CrossReport toCreate = MAPPER.readValue(
        fixture(
            "fixtures/domain/legacy/CrossReport/invalid/recipientPhoneExtensionNumberNull.json"),
        CrossReport.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("recipientPhoneExtensionNumber may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenRecipientPhoneExtensionNumberEmpty() throws Exception {
    CrossReport toCreate = MAPPER.readValue(
        fixture(
            "fixtures/domain/legacy/CrossReport/invalid/recipientPhoneExtensionNumberEmpty.json"),
        CrossReport.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("recipientPhoneExtensionNumber may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * recipientPhoneNumber Tests
   */
  @Test
  public void failsWhenRecipientPhoneNumberMissing() throws Exception {
    CrossReport toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/CrossReport/invalid/recipientPhoneNumberMissing.json"),
        CrossReport.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("recipientPhoneNumber may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenRecipientPhoneNumberNull() throws Exception {
    CrossReport toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/CrossReport/invalid/recipientPhoneNumberNull.json"),
        CrossReport.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("recipientPhoneNumber may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * informDate Tests
   */
  @Test
  public void failsWhenInformDateMissing() throws Exception {
    CrossReport toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/CrossReport/invalid/informDateMissing.json"),
        CrossReport.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("informDate must be in the format of yyyy-MM-dd"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenInformDateNull() throws Exception {
    CrossReport toCreate =
        MAPPER.readValue(fixture("fixtures/domain/legacy/CrossReport/invalid/informDateNull.json"),
            CrossReport.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("informDate must be in the format of"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenInformDateEmpty() throws Exception {
    CrossReport toCreate =
        MAPPER.readValue(fixture("fixtures/domain/legacy/CrossReport/invalid/informDateEmpty.json"),
            CrossReport.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("informDate must be in the format of"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenInformDateWrongFormat() throws Exception {
    CrossReport toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/CrossReport/invalid/informDateWrongFormat.json"),
        CrossReport.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("informDate must be in the format of yyyy-MM-dd"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * recipientPositionTitleDesc Tests
   */
  @Test
  public void failsWhenRecipientPositionTitleDescMissing() throws Exception {
    CrossReport toCreate = MAPPER.readValue(
        fixture(
            "fixtures/domain/legacy/CrossReport/invalid/recipientPositionTitleDescMissing.json"),
        CrossReport.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("recipientPositionTitleDesc may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenRecipientPositionTitleDescNull() throws Exception {
    CrossReport toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/CrossReport/invalid/recipientPositionTitleDescNull.json"),
        CrossReport.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("recipientPositionTitleDesc may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void successWhenRecipientPositionTitleDescEmpty() throws Exception {
    CrossReport toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/CrossReport/valid/recipientPositionTitleDescEmpty.json"),
        CrossReport.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void failsWhenRecipientPositionTitleDescTooLong() throws Exception {
    CrossReport toCreate = MAPPER.readValue(
        fixture(
            "fixtures/domain/legacy/CrossReport/invalid/recipientPositionTitleDescTooLong.json"),
        CrossReport.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class)
            .indexOf("recipientPositionTitleDesc size must be between 0 and 30"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * referenceNumber Tests
   */
  @Test
  public void failsWhenReferenceNumberMissing() throws Exception {
    CrossReport toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/CrossReport/invalid/referenceNumberMissing.json"),
        CrossReport.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("referenceNumber may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenReferenceNumberNull() throws Exception {
    CrossReport toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/CrossReport/invalid/referenceNumberNull.json"),
        CrossReport.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("referenceNumber may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void successWhenReferenceNumberEmpty() throws Exception {
    CrossReport toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/CrossReport/valid/referenceNumberEmpty.json"),
        CrossReport.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void failsWhenReferenceNumberTooLong() throws Exception {
    CrossReport toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/CrossReport/invalid/referenceNumberTooLong.json"),
        CrossReport.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("referenceNumber size must be between 0 and 10"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * referralId Tests
   */
  @Test
  public void failsWhenReferralIdMissing() throws Exception {
    CrossReport toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/CrossReport/invalid/referralIdMissing.json"),
        CrossReport.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("referralId may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenReferralIdNull() throws Exception {
    CrossReport toCreate =
        MAPPER.readValue(fixture("fixtures/domain/legacy/CrossReport/invalid/referralIdNull.json"),
            CrossReport.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    // String message = response.readEntity(String.class);
    // System.out.print(message);
    assertThat(response.readEntity(String.class).indexOf("referralId may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenReferralIdEmpty() throws Exception {
    CrossReport toCreate =
        MAPPER.readValue(fixture("fixtures/domain/legacy/CrossReport/invalid/referralIdEmpty.json"),
            CrossReport.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("referralId size must be between 10 and 10"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenReferralIdTooLong() throws Exception {
    CrossReport toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/CrossReport/invalid/referralIdTooLong.json"),
        CrossReport.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("referralId size must be between 10 and 10"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * lawEnforcementId Tests
   */
  @Test
  public void successWhenLawEnforcementIdEmpty() throws Exception {
    CrossReport toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/CrossReport/valid/lawEnforcementIdEmpty.json"),
        CrossReport.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void successWhenLawEnforcementIdNull() throws Exception {
    CrossReport toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/CrossReport/valid/lawEnforcementIdNull.json"),
        CrossReport.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void failsWhenLawEnforcementIdTooLong() throws Exception {
    CrossReport toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/CrossReport/invalid/lawEnforcementIdTooLong.json"),
        CrossReport.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("lawEnforcementId size must be between 0 and 10"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * staffPersonId Tests
   */
  @Test
  public void failsWhenStaffPersonIdMissing() throws Exception {
    CrossReport toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/CrossReport/invalid/staffPersonIdMissing.json"),
        CrossReport.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("staffPersonId may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenStaffPersonIdNull() throws Exception {
    CrossReport toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/CrossReport/invalid/staffPersonIdNull.json"),
        CrossReport.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("staffPersonId may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenStaffPersonIdEmpty() throws Exception {
    CrossReport toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/CrossReport/invalid/staffPersonIdEmpty.json"),
        CrossReport.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("staffPersonId may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenStaffPersonIdTooLong() throws Exception {
    CrossReport toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/CrossReport/invalid/staffPersonIdTooLong.json"),
        CrossReport.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("staffPersonId size must be between 3 and 3"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenStaffPersonIdTooShort() throws Exception {
    CrossReport toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/CrossReport/invalid/staffPersonIdTooShort.json"),
        CrossReport.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("staffPersonId size must be between 3 and 3"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * description Tests
   */
  @Test
  public void failsWhenDescriptionMissing() throws Exception {
    CrossReport toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/CrossReport/invalid/descriptionMissing.json"),
        CrossReport.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("description may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenDescriptionNull() throws Exception {
    CrossReport toCreate =
        MAPPER.readValue(fixture("fixtures/domain/legacy/CrossReport/invalid/descriptionNull.json"),
            CrossReport.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("description may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenDescriptionEmpty() throws Exception {
    CrossReport toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/CrossReport/invalid/descriptionEmpty.json"),
        CrossReport.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("description size must be between 1 and 120"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void successWhenDescriptionAllWhiteSpace() throws Exception {
    CrossReport toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/CrossReport/valid/descriptionAllWhiteSpace.json"),
        CrossReport.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  /*
   * recipientName Tests
   */
  @Test
  public void failsWhenRecipientNameMissing() throws Exception {
    CrossReport toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/CrossReport/invalid/recipientNameMissing.json"),
        CrossReport.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("recipientName may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenRecipientNameNull() throws Exception {
    CrossReport toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/CrossReport/invalid/recipientNameNull.json"),
        CrossReport.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("recipientName may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void successWhenRecipientNameEmpty() throws Exception {
    CrossReport toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/CrossReport/valid/recipientNameEmpty.json"),
        CrossReport.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void failsWhenRecipientNameTooLong() throws Exception {
    CrossReport toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/CrossReport/invalid/recipientNameTooLong.json"),
        CrossReport.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("recipientName size must be between 0 and 40"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * outStateLawEnforcementAddr Tests
   */
  @Test
  public void failsWhenOutstateLawEnforcementAddrMissing() throws Exception {
    CrossReport toCreate = MAPPER.readValue(
        fixture(
            "fixtures/domain/legacy/CrossReport/invalid/outStateLawEnforcementAddrMissing.json"),
        CrossReport.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("outStateLawEnforcementAddr may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenOutstateLawEnforcementAddrNull() throws Exception {
    CrossReport toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/CrossReport/invalid/outStateLawEnforcementAddrNull.json"),
        CrossReport.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("outStateLawEnforcementAddr may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void successWhenOutstateLawEnforcementAddrEmpty() throws Exception {
    CrossReport toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/CrossReport/valid/outStateLawEnforcementAddrEmpty.json"),
        CrossReport.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void successWhenOutstateLawEnforcementAddrAllWhiteSpace() throws Exception {
    CrossReport toCreate = MAPPER.readValue(
        fixture(
            "fixtures/domain/legacy/CrossReport/valid/outStateLawEnforcementAddrAllWhiteSpace.json"),
        CrossReport.class);
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
    CrossReport toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/CrossReport/invalid/countySpecificCodeMissing.json"),
        CrossReport.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("countySpecificCode may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenCountySpecificCodeNull() throws Exception {
    CrossReport toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/CrossReport/invalid/countySpecificCodeNull.json"),
        CrossReport.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("countySpecificCode may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenCountySpecificCodeEmpty() throws Exception {
    CrossReport toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/CrossReport/invalid/countySpecificCodeEmpty.json"),
        CrossReport.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("countySpecificCode may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenCountySpecificCodeTooLong() throws Exception {
    CrossReport toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/CrossReport/invalid/countySpecificCodeTooLong.json"),
        CrossReport.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class)
        .indexOf("countySpecificCode size must be between 1 and 2"), is(greaterThanOrEqualTo(0)));
  }

  /*
   * lawEnforcementIndicator Tests
   */
  @Test
  public void failsWhenLawEnforcementIndicatorMissing() throws Exception {
    CrossReport toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/CrossReport/invalid/lawEnforcementIndicatorMissing.json"),
        CrossReport.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("lawEnforcementIndicator may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenLawEnforcementIndicatorNull() throws Exception {
    CrossReport toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/CrossReport/invalid/lawEnforcementIndicatorNull.json"),
        CrossReport.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("lawEnforcementIndicator may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenLawEnforcementIndicatorEmpty() throws Exception {
    CrossReport toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/CrossReport/invalid/lawEnforcementIndicatorEmpty.json"),
        CrossReport.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("lawEnforcementIndicator may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenLawEnforcementIndicatorAllWhitespace() throws Exception {
    CrossReport toCreate = MAPPER.readValue(
        fixture(
            "fixtures/domain/legacy/CrossReport/invalid/lawEnforcementIndicatorAllWhitespace.json"),
        CrossReport.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("lawEnforcementIndicator may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * outStateLawEnforcementIndicator Tests
   */
  @Test
  public void failsWhenOutStateLawEnforcementIndicatorMissing() throws Exception {
    CrossReport toCreate = MAPPER.readValue(
        fixture(
            "fixtures/domain/legacy/CrossReport/invalid/outStateLawEnforcementIndicatorMissing.json"),
        CrossReport.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class)
        .indexOf("outStateLawEnforcementIndicator may not be null"), is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenOutStateLawEnforcementIndicatorNull() throws Exception {
    CrossReport toCreate = MAPPER.readValue(
        fixture(
            "fixtures/domain/legacy/CrossReport/invalid/outStateLawEnforcementIndicatorNull.json"),
        CrossReport.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class)
        .indexOf("outStateLawEnforcementIndicator may not be null"), is(greaterThanOrEqualTo(0)));
  }


  @Test
  public void failsWhenOutStateLawEnforcementIndicatorAllWhitespace() throws Exception {
    CrossReport toCreate = MAPPER.readValue(
        fixture(
            "fixtures/domain/legacy/CrossReport/invalid/outStateLawEnforcementIndicatorAllWhitespace.json"),
        CrossReport.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class)
        .indexOf("outStateLawEnforcementIndicator may not be null"), is(greaterThanOrEqualTo(0)));
  }

  /*
   * satisfyCrossReportIndicator Tests
   */
  @Test
  public void failsWhenSatisfyCrossReportIndicatorMissing() throws Exception {
    CrossReport toCreate = MAPPER.readValue(
        fixture(
            "fixtures/domain/legacy/CrossReport/invalid/satisfyCrossReportIndicatorMissing.json"),
        CrossReport.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("satisfyCrossReportIndicator may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenSatisfyCrossReportIndicatorNull() throws Exception {
    CrossReport toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/CrossReport/invalid/satisfyCrossReportIndicatorNull.json"),
        CrossReport.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("satisfyCrossReportIndicator may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenSatisfyCrossReportIndicatorEmpty() throws Exception {
    CrossReport toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/CrossReport/invalid/satisfyCrossReportIndicatorEmpty.json"),
        CrossReport.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("satisfyCrossReportIndicator may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenSatisfyCrossReportIndicatorAllWhitespace() throws Exception {
    CrossReport toCreate = MAPPER.readValue(
        fixture(
            "fixtures/domain/legacy/CrossReport/invalid/satisfyCrossReportIndicatorAllWhitespace.json"),
        CrossReport.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("satisfyCrossReportIndicator may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * Utils
   */
  private CrossReport validCrossReport() throws Exception {

    CrossReport validCrossReport = MAPPER.readValue(
        fixture("fixtures/domain/legacy/CrossReport/valid/valid.json"), CrossReport.class);
    return validCrossReport;
  }
}
