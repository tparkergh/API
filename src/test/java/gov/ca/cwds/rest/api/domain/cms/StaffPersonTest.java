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
import gov.ca.cwds.rest.resources.cms.JerseyGuiceRule;
import gov.ca.cwds.rest.resources.cms.StaffPersonResource;
import io.dropwizard.jackson.Jackson;
import io.dropwizard.testing.junit.ResourceTestRule;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

@SuppressWarnings("javadoc")
public class StaffPersonTest {

  private static final String ROOT_RESOURCE = "/" + Api.RESOURCE_STAFF_PERSON + "/";

  private static final StaffPersonResource mockedStaffPersonResource =
      mock(StaffPersonResource.class);

  @After
  public void ensureServiceLocatorPopulated() {
    JerseyGuiceUtils.reset();
  }

  @ClassRule
  public static JerseyGuiceRule rule = new JerseyGuiceRule();

  @ClassRule
  public static final ResourceTestRule resources =
      ResourceTestRule.builder().addResource(mockedStaffPersonResource).build();

  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();
  private StaffPerson validStaffPerson = validStaffPerson();

  private final static DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
  private String id = "a";
  private String endDate = "";
  private String firstName = "b";
  private String jobTitle = "c";
  private String lastName = "d";
  private String middleInitial = "e";
  private String namePrefix = "f";
  private BigDecimal phoneNumber = new BigDecimal(1);
  private Integer phoneExt = 2;
  private String startDate = "2016-10-31";
  private String nameSuffix = "g";
  private Boolean telecommuterIndicator = Boolean.TRUE;
  private String cwsOffice = "h";
  private String availabilityAndLocationDescription = "i";
  private String ssrsLicensingWorkerId = "j";
  private String countyCode = "k";
  private Boolean dutyWorkerIndicator = Boolean.FALSE;
  private String cwsOfficeAddress = "l";
  private String emailAddress = "m";
  // private String twitterName = "n";

  @Before
  public void setup() {
    when(mockedStaffPersonResource.create(eq(validStaffPerson)))
        .thenReturn(Response.status(Response.Status.NO_CONTENT).entity(null).build());
  }

  /*
   * Constructor Tests
   */
  @Test
  public void persistentObjectConstructorTest() throws Exception {
    StaffPerson domain = new StaffPerson(endDate, firstName, jobTitle, lastName, middleInitial,
        namePrefix, phoneNumber, phoneExt, startDate, nameSuffix, telecommuterIndicator, cwsOffice,
        availabilityAndLocationDescription, ssrsLicensingWorkerId, countyCode, dutyWorkerIndicator,
        cwsOfficeAddress, emailAddress);
    gov.ca.cwds.data.persistence.cms.StaffPerson persistent =
        new gov.ca.cwds.data.persistence.cms.StaffPerson(id, domain, "lastUpdatedId");

    assertThat(domain.getEndDate(),
        is(equalTo(persistent.getEndDate() == null ? "" : df.format(persistent.getEndDate()))));
    assertThat(domain.getFirstName(), is(equalTo(persistent.getFirstName())));
    assertThat(domain.getJobTitle(), is(equalTo(persistent.getJobTitle())));
    assertThat(domain.getLastName(), is(equalTo(persistent.getLastName())));
    assertThat(domain.getMiddleInitial(), is(equalTo(persistent.getMiddleInitial())));
    assertThat(domain.getNamePrefix(), is(equalTo(persistent.getNamePrefix())));
    assertThat(domain.getPhoneNumber(), is(equalTo(persistent.getPhoneNumber())));
    assertThat(domain.getPhoneExt(), is(equalTo(persistent.getPhoneExt())));
    assertThat(domain.getStartDate(), is(equalTo(df.format(persistent.getStartDate()))));
    assertThat(domain.getNameSuffix(), is(equalTo(persistent.getNameSuffix())));
    assertThat(domain.getTelecommuterIndicator(),
        is(equalTo(DomainChef.uncookBooleanString(persistent.getTelecommuterIndicator()))));
    assertThat(domain.getCwsOffice(), is(equalTo(persistent.getCwsOffice())));
    assertThat(domain.getAvailabilityAndLocationDescription(),
        is(equalTo(persistent.getAvailabilityAndLocationDescription())));
    assertThat(domain.getSsrsLicensingWorkerId(),
        is(equalTo(persistent.getSsrsLicensingWorkerId())));
    assertThat(domain.getCountyCode(), is(equalTo(persistent.getCountyCode())));
    assertThat(domain.getDutyWorkerIndicator(),
        is(equalTo(DomainChef.uncookBooleanString(persistent.getDutyWorkerIndicator()))));
    assertThat(domain.getCwsOfficeAddress(), is(equalTo(persistent.getCwsOfficeAddress())));
    assertThat(domain.getEmailAddress(), is(equalTo(persistent.getEmailAddress())));
  }

  @Test
  public void jsonCreatorConstructorTest() throws Exception {
    StaffPerson domain = new StaffPerson(endDate, firstName, jobTitle, lastName, middleInitial,
        namePrefix, phoneNumber, phoneExt, startDate, nameSuffix, telecommuterIndicator, cwsOffice,
        availabilityAndLocationDescription, ssrsLicensingWorkerId, countyCode, dutyWorkerIndicator,
        cwsOfficeAddress, emailAddress);

    // assertThat(domain.getId(), is(equalTo(id)));
    assertThat(domain.getEndDate(), is(equalTo(endDate)));
    assertThat(domain.getFirstName(), is(equalTo(firstName)));
    assertThat(domain.getJobTitle(), is(equalTo(jobTitle)));
    assertThat(domain.getLastName(), is(equalTo(lastName)));
    assertThat(domain.getMiddleInitial(), is(equalTo(middleInitial)));
    assertThat(domain.getNamePrefix(), is(equalTo(namePrefix)));
    assertThat(domain.getPhoneNumber(), is(equalTo(phoneNumber)));
    assertThat(domain.getPhoneExt(), is(equalTo(phoneExt)));
    assertThat(domain.getStartDate(), is(equalTo(startDate)));
    assertThat(domain.getNameSuffix(), is(equalTo(nameSuffix)));
    assertThat(domain.getTelecommuterIndicator(), is(equalTo(telecommuterIndicator)));
    assertThat(domain.getCwsOffice(), is(equalTo(cwsOffice)));
    assertThat(domain.getAvailabilityAndLocationDescription(),
        is(equalTo(availabilityAndLocationDescription)));
    assertThat(domain.getSsrsLicensingWorkerId(), is(equalTo(ssrsLicensingWorkerId)));
    assertThat(domain.getCountyCode(), is(equalTo(countyCode)));
    assertThat(domain.getDutyWorkerIndicator(), is(equalTo(dutyWorkerIndicator)));
    assertThat(domain.getCwsOfficeAddress(), is(equalTo(cwsOfficeAddress)));
    assertThat(domain.getEmailAddress(), is(equalTo(emailAddress)));
    // assertThat(domain.getTwitterName(), is(equalTo(twitterName)));
  }

  @Test
  public void serializesToJSON() throws Exception {
    final String expected = MAPPER.writeValueAsString(MAPPER.readValue(
        fixture("fixtures/domain/legacy/StaffPerson/valid/valid.json"), StaffPerson.class));

    assertThat(MAPPER.writeValueAsString(validStaffPerson()), is(equalTo(expected)));
  }

  @Test
  public void deserializesFromJSON() throws Exception {
    assertThat(MAPPER.readValue(fixture("fixtures/domain/legacy/StaffPerson/valid/valid.json"),
        StaffPerson.class), is(equalTo(validStaffPerson())));
  }

  @Test
  public void equalsHashCodeWork() {
    EqualsVerifier.forClass(StaffPerson.class).suppress(Warning.NONFINAL_FIELDS)
        .withIgnoredFields("messages").verify();
  }

  /*
   * Successful Tests
   */
  @Test
  public void successfulWithValid() throws Exception {
    StaffPerson toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/StaffPerson/valid/valid.json"), StaffPerson.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  // test of columns that are not mandatory and cannot be null
  @Test
  public void successfulWithOptionalsNotIncluded() throws Exception {
    StaffPerson toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/StaffPerson/valid/optionalsNotIncluded.json"),
        StaffPerson.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void failsWhenEndDateWrongFormat() throws Exception {
    StaffPerson toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/StaffPerson/invalid/endDatewrongFormat.json"),
        StaffPerson.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("endDate must be in the format of"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * firstName Tests
   */
  @Test
  public void failsWhenFirstNameMissing() throws Exception {
    StaffPerson toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/StaffPerson/invalid/firstNameMissing.json"),
        StaffPerson.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("firstName may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenFirstNameNull() throws Exception {
    StaffPerson toCreate =
        MAPPER.readValue(fixture("fixtures/domain/legacy/StaffPerson/invalid/firstNameNull.json"),
            StaffPerson.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("firstName may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenFirstNameEmpty() throws Exception {
    StaffPerson toCreate =
        MAPPER.readValue(fixture("fixtures/domain/legacy/StaffPerson/invalid/firstNamEmpty.json"),
            StaffPerson.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("firstName may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenFirstNameTooLong() throws Exception {
    StaffPerson toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/StaffPerson/invalid/firstNameTooLong.json"),
        StaffPerson.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("firstName size must be between 1 and 20"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * jobTitle Tests
   */
  @Test
  public void successWhenJobTitleMissing() throws Exception {
    StaffPerson toCreate =
        MAPPER.readValue(fixture("fixtures/domain/legacy/StaffPerson/valid/jobTitleMissing.json"),
            StaffPerson.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void successWhenJobTitleNull() throws Exception {
    StaffPerson toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/StaffPerson/valid/jobTitleNull.json"), StaffPerson.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void failsWhenJobTitleTooLong() throws Exception {
    StaffPerson toCreate =
        MAPPER.readValue(fixture("fixtures/domain/legacy/StaffPerson/invalid/jobTitleTooLong.json"),
            StaffPerson.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("jobTitle size must be between 0 and 30"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * lastName Tests
   */
  @Test
  public void failsWhenLastNameMissing() throws Exception {
    StaffPerson toCreate =
        MAPPER.readValue(fixture("fixtures/domain/legacy/StaffPerson/invalid/lastNameMissing.json"),
            StaffPerson.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("lastName may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenLastNameNull() throws Exception {
    StaffPerson toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/StaffPerson/invalid/lastNameNull.json"), StaffPerson.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("lastName may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenLastNameEmpty() throws Exception {
    StaffPerson toCreate =
        MAPPER.readValue(fixture("fixtures/domain/legacy/StaffPerson/invalid/lastNameEmpty.json"),
            StaffPerson.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("lastName may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenLastNameTooLong() throws Exception {
    StaffPerson toCreate =
        MAPPER.readValue(fixture("fixtures/domain/legacy/StaffPerson/invalid/lastNameTooLong.json"),
            StaffPerson.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("lastName size must be between 1 and 25"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * middleInitial Tests
   */
  // @Test
  // public void failsWhenMiddleInitialMissing() throws Exception {
  // StaffPerson toCreate =
  // MAPPER.readValue(fixture("fixtures/domain/legacy/StaffPerson/valid/middleInitialMissing.json"),
  // StaffPerson.class);
  // Response response =
  // resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
  // .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
  // assertThat(response.getStatus(), is(equalTo(422)));
  // assertThat(response.readEntity(String.class).indexOf("middleInitial may not be empty"),
  // is(greaterThanOrEqualTo(0)));
  // }

  @Test
  public void failsWhenMiddleInitialNull() throws Exception {
    StaffPerson toCreate =
        MAPPER.readValue(fixture("fixtures/domain/legacy/StaffPerson/valid/middleInitialNull.json"),
            StaffPerson.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void SuccessWhenMiddleInitialEmpty() throws Exception {
    StaffPerson toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/StaffPerson/valid/middleInitialEmpty.json"),
        StaffPerson.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void failsWhenMiddleInitialTooLong() throws Exception {
    StaffPerson toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/StaffPerson/invalid/middleInitialTooLong.json"),
        StaffPerson.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("middleInitial size must be 1"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * namePrefix Tests
   */
  @Test
  public void successWhenNamePrefixMissing() throws Exception {
    StaffPerson toCreate =
        MAPPER.readValue(fixture("fixtures/domain/legacy/StaffPerson/valid/namePrefixMissing.json"),
            StaffPerson.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void successWhenNamePrefixNull() throws Exception {
    StaffPerson toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/StaffPerson/valid/namePrefixNull.json"), StaffPerson.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void successWhenNamePrefixEmpty() throws Exception {
    StaffPerson toCreate =
        MAPPER.readValue(fixture("fixtures/domain/legacy/StaffPerson/valid/namePrefixEmpty.json"),
            StaffPerson.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void failsWhenNamePrefixTooLong() throws Exception {
    StaffPerson toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/StaffPerson/invalid/namePrefixTooLong.json"),
        StaffPerson.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("namePrefix size must be between 0 and 6"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * phoneNumber Tests
   */
  @Test
  public void failsWhenPhoneNumberMissing() throws Exception {
    StaffPerson toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/StaffPerson/invalid/phoneNumberMissing.json"),
        StaffPerson.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("phoneNumber may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenPhoneNumberNull() throws Exception {
    StaffPerson toCreate =
        MAPPER.readValue(fixture("fixtures/domain/legacy/StaffPerson/invalid/phoneNumberNull.json"),
            StaffPerson.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("phoneNumber may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenPhoneNumberEmpty() throws Exception {
    StaffPerson toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/StaffPerson/invalid/phoneNumberEmpty.json"),
        StaffPerson.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("phoneNumber may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * phoneExt Tests
   */
  @Test
  public void testWhenPhoneExtMissingFails() throws Exception {
    StaffPerson toCreate =
        MAPPER.readValue(fixture("fixtures/domain/legacy/StaffPerson/invalid/phoneExtMissing.json"),
            StaffPerson.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("phoneExt may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenPhoneExtNull() throws Exception {
    StaffPerson toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/StaffPerson/invalid/phoneExtNull.json"), StaffPerson.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("phoneExt may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenPhoneExtTooLong() throws Exception {
    StaffPerson toCreate =
        MAPPER.readValue(fixture("fixtures/domain/legacy/StaffPerson/invalid/phoneExtEmpty.json"),
            StaffPerson.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("phoneExt may not be null"),
        is(greaterThanOrEqualTo(0)));

  }

  @Test
  public void testWhenPhoneExtZeroSucess() throws Exception {
    StaffPerson toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/StaffPerson/valid/phoneExtZero.json"), StaffPerson.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    // System.out.println("message = " + response.readEntity(String.class));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  /*
   * startDate Tests
   */
  @Test
  public void failsWhenStartDateMissing() throws Exception {
    StaffPerson toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/StaffPerson/invalid/startDateIsMissing.json"),
        StaffPerson.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("startDate must be in the format of"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenStartDateNull() throws Exception {
    StaffPerson toCreate =
        MAPPER.readValue(fixture("fixtures/domain/legacy/StaffPerson/invalid/startDateNull.json"),
            StaffPerson.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("startDate must be in the format of"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenStartDateWrongFormat() throws Exception {
    StaffPerson toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/StaffPerson/invalid/startDateWrongFormat.json"),
        StaffPerson.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("startDate must be in the format of"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenStartDateEmpty() throws Exception {
    StaffPerson toCreate =
        MAPPER.readValue(fixture("fixtures/domain/legacy/StaffPerson/invalid/startDateEmpty.json"),
            StaffPerson.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("startDate must be in the format of"),
        is(greaterThanOrEqualTo(0)));
  }

  // /*
  // * nameSuffix Tests
  // */
  @Test
  public void testNameSuffixMissingSuccess() throws Exception {
    StaffPerson toCreate =
        MAPPER.readValue(fixture("fixtures/domain/legacy/StaffPerson/valid/nameSuffixMissing.json"),
            StaffPerson.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void testNameSuffixNullSuccess() throws Exception {
    StaffPerson toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/StaffPerson/valid/nameSuffixNull.json"), StaffPerson.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void testNameSuffixEmptySuccess() throws Exception {
    StaffPerson toCreate =
        MAPPER.readValue(fixture("fixtures/domain/legacy/StaffPerson/valid/nameSuffixEmpty.json"),
            StaffPerson.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void failsWhenNameSuffixTooLong() throws Exception {
    StaffPerson toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/StaffPerson/invalid/nameSuffixTooLong.json"),
        StaffPerson.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("nameSuffix size must be between 0 and 4"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * telecommuterIndicator Tests
   */
  @Test
  public void failsWhenTelecommuterIndicatorMissing() throws Exception {
    StaffPerson toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/StaffPerson/invalid/telecommuterIndicatorMissing.json"),
        StaffPerson.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("telecommuterIndicator may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenTelecommuterIndicatorNull() throws Exception {
    StaffPerson toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/StaffPerson/invalid/telecommuterIndicatorNull.json"),
        StaffPerson.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("telecommuterIndicator may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenTelecommuterIndicatorEmpty() throws Exception {
    StaffPerson toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/StaffPerson/invalid/telecommuterIndicatorEmpty.json"),
        StaffPerson.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("telecommuterIndicator may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenTelecommuterIndicatorAllWhitespace() throws Exception {
    StaffPerson toCreate = MAPPER.readValue(
        fixture(
            "fixtures/domain/legacy/StaffPerson/invalid/telecommuterIndicatorAllWhiteSpace.json"),
        StaffPerson.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("telecommuterIndicator may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * cwsOffice Tests
   */
  @Test
  public void failsWhenCwsOfficeMissing() throws Exception {
    StaffPerson toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/StaffPerson/invalid/cwsOfficeMissing.json"),
        StaffPerson.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("cwsOffice may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenCwsOfficeNull() throws Exception {
    StaffPerson toCreate =
        MAPPER.readValue(fixture("fixtures/domain/legacy/StaffPerson/invalid/cwsOfficeNull.json"),
            StaffPerson.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("cwsOffice may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenCwsOfficeEmpty() throws Exception {
    StaffPerson toCreate =
        MAPPER.readValue(fixture("fixtures/domain/legacy/StaffPerson/invalid/cwsOfficeEmpty.json"),
            StaffPerson.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("cwsOffice may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenCwsOfficeTooShort() throws Exception {
    StaffPerson toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/StaffPerson/invalid/cwsOfficeTooShort.json"),
        StaffPerson.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("cwsOffice size must be"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenCwsOfficeTooLong() throws Exception {
    StaffPerson toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/StaffPerson/invalid/cwsOfficeTooLong.json"),
        StaffPerson.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("cwsOffice size must be"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * availabilityAndLocationDescription Tests
   */
  @Test
  public void failsWhenAvailabilityAndLocationDescriptionMissing() throws Exception {
    StaffPerson toCreate = MAPPER.readValue(
        fixture(
            "fixtures/domain/legacy/StaffPerson/invalid/availabilityAndLocationDescriptionMissing.json"),
        StaffPerson.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf(
        "availabilityAndLocationDescription may not be null"), is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void testAvailabilityAndLocationDescriptionEmptySuccess() throws Exception {
    StaffPerson toCreate = MAPPER.readValue(
        fixture(
            "fixtures/domain/legacy/StaffPerson/valid/availabilityAndLocationDescriptionEmpty.json"),
        StaffPerson.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void failsWhenAvailabilityAndLocationDescriptionNull() throws Exception {
    StaffPerson toCreate = MAPPER.readValue(
        fixture(
            "fixtures/domain/legacy/StaffPerson/invalid/availabilityAndLocationDescriptionNull.json"),
        StaffPerson.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf(
        "availabilityAndLocationDescription may not be null"), is(greaterThanOrEqualTo(0)));
  }

  /*
   * ssrsLicensingWorkerId Tests
   */
  @Test
  public void failsWhenSsrsLicensingWorkerIdMissing() throws Exception {
    StaffPerson toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/StaffPerson/invalid/ssrsLicensingWorkerIdMissing.json"),
        StaffPerson.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("ssrsLicensingWorkerId may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenSsrsLicensingWorkerIdNull() throws Exception {
    StaffPerson toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/StaffPerson/invalid/ssrsLicensingWorkerIdNull.json"),
        StaffPerson.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("ssrsLicensingWorkerId may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void testSsrsLicensingWorkerIdEmptySuccess() throws Exception {
    StaffPerson toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/StaffPerson/valid/ssrsLicensingWorkerIdEmpty.json"),
        StaffPerson.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void failsWhenSsrsLicensingWorkerIdTooLong() throws Exception {
    StaffPerson toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/StaffPerson/invalid/ssrsLicensingWorkerIdTooLong.json"),
        StaffPerson.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf(
        "ssrsLicensingWorkerId size must be between 0 and 4"), is(greaterThanOrEqualTo(0)));
  }

  /*
   * countyCode Tests
   */
  @Test
  public void failsWhenCountyCodeMissing() throws Exception {
    StaffPerson toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/StaffPerson/invalid/countyCodeMissing.json"),
        StaffPerson.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("countyCode may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenCountyCodeNull() throws Exception {
    StaffPerson toCreate =
        MAPPER.readValue(fixture("fixtures/domain/legacy/StaffPerson/invalid/countyCodeNull.json"),
            StaffPerson.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("countyCode may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenCountyCodeEmpty() throws Exception {
    StaffPerson toCreate =
        MAPPER.readValue(fixture("fixtures/domain/legacy/StaffPerson/invalid/countyCodeEmpty.json"),
            StaffPerson.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("countyCode may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenCountyCodeTooLong() throws Exception {
    StaffPerson toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/StaffPerson/invalid/countyCodeTooLong.json"),
        StaffPerson.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("countyCode size must be between 1 and 2"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * dutyWorkerIndicator Tests
   */
  @Test
  public void failsWhenDutyWorkerIndicatorMissing() throws Exception {
    StaffPerson toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/StaffPerson/invalid/dutyWorkerIndicatorMissing.json"),
        StaffPerson.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("dutyWorkerIndicator may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenDutyWorkerIndicatorNull() throws Exception {
    StaffPerson toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/StaffPerson/invalid/dutyWorkerIndicatorNull.json"),
        StaffPerson.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("dutyWorkerIndicator may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenDutyWorkerIndicatorEmpty() throws Exception {
    StaffPerson toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/StaffPerson/invalid/dutyWorkerIndicatorEmpty.json"),
        StaffPerson.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("dutyWorkerIndicator may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenDutyWorkerIndicatorAllWhitespace() throws Exception {
    StaffPerson toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/StaffPerson/invalid/dutyWorkerIndicatorAllWhitespace.json"),
        StaffPerson.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("dutyWorkerIndicator may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * cwsOfficeAddress Tests
   */
  @Test
  public void failsWhenCwsOfficeAddressMissing() throws Exception {
    StaffPerson toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/StaffPerson/invalid/cwsOfficeAddressMissing.json"),
        StaffPerson.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("cwsOfficeAddress may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenCwsOfficeAddressNull() throws Exception {
    StaffPerson toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/StaffPerson/invalid/cwsOfficeAddressNull.json"),
        StaffPerson.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("cwsOfficeAddress may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenCwsOfficeAddressEmpty() throws Exception {
    StaffPerson toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/StaffPerson/invalid/cwsOfficeAddressEmpty.json"),
        StaffPerson.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("cwsOfficeAddress may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenCwsOfficeAddressTooLong() throws Exception {
    StaffPerson toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/StaffPerson/invalid/cwsOfficeAddressTooLong.json"),
        StaffPerson.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("cwsOfficeAddress size must be"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenCwsOfficeAddressTooShort() throws Exception {
    StaffPerson toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/StaffPerson/invalid/cwsOfficeAddressTooShort.json"),
        StaffPerson.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("cwsOfficeAddress size must be"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * emailAddress Tests
   */
  @Test
  public void successWhenEmailAddressEmpty() throws Exception {
    StaffPerson toCreate =
        MAPPER.readValue(fixture("fixtures/domain/legacy/StaffPerson/valid/emailAddressEmpty.json"),
            StaffPerson.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void successWhenEmailAddressNull() throws Exception {
    StaffPerson toCreate =
        MAPPER.readValue(fixture("fixtures/domain/legacy/StaffPerson/valid/emailAddressNull.json"),
            StaffPerson.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void successWhenEmailAddressMissing() throws Exception {
    StaffPerson toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/StaffPerson/valid/emailAddressMissing.json"),
        StaffPerson.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void failsWhenEmailAddressTooLong() throws Exception {
    StaffPerson toCreate = MAPPER.readValue(
        fixture("fixtures/domain/legacy/StaffPerson/invalid/emailAddressTooLong.json"),
        StaffPerson.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("emailAddress size must be between 0 and 50"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * Utils
   */
  private StaffPerson validStaffPerson() {
    return new StaffPerson("2016-10-31", "John", "CEO", "Doe", "C", "Mr",
        new BigDecimal(9165551212L), 22, "2016-10-31", "III", true, "MIZN02k11B", "abc", "def",
        "99", false, "3XPCP92b24", "john.doe@anyco.com");
  }
}
