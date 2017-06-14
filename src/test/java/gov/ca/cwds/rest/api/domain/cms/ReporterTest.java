package gov.ca.cwds.rest.api.domain.cms;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.ca.cwds.data.CrudsDao;
import gov.ca.cwds.rest.api.domain.DomainChef;
import gov.ca.cwds.rest.api.domain.Participant;
import gov.ca.cwds.rest.core.Api;
import gov.ca.cwds.rest.resources.cms.JerseyGuiceRule;
import gov.ca.cwds.rest.resources.cms.ReporterResource;
import io.dropwizard.jackson.Jackson;
import io.dropwizard.testing.junit.ResourceTestRule;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashSet;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squarespace.jersey2.guice.JerseyGuiceUtils;

/**
 * 
 * @author CWDS API Team
 */
@SuppressWarnings("javadoc")
public class ReporterTest {

  private static final String ROOT_RESOURCE = "/" + Api.RESOURCE_REPORTER + "/";

  private static final ReporterResource mockedReporterResource = mock(ReporterResource.class);

  @After
  public void ensureServiceLocatorPopulated() {
    JerseyGuiceUtils.reset();
  }

  @ClassRule
  public static JerseyGuiceRule rule = new JerseyGuiceRule();

  @ClassRule
  public static final ResourceTestRule resources = ResourceTestRule.builder()
      .addResource(mockedReporterResource).build();

  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();
  private final static DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

  private String referralId = "1234567ABC";
  private String badgeNumber = "123456";
  private String cityName = "ABC";
  private Short colltrClientRptrReltnshpType = 12;
  private Short communicationMethodType = 34;
  private Boolean confidentialWaiverIndicator = Boolean.FALSE;
  private String drmsMandatedRprtrFeedback = "ABC123";
  private String employerName = "";
  private String feedbackDate = "1210-01-31";
  private Boolean feedbackRequiredIndicator = Boolean.FALSE;
  private String firstName = "John";
  private String lastName = "Smith";
  private Boolean mandatedReporterIndicator = Boolean.FALSE;
  private int messagePhoneExtensionNumber = 123;
  private BigDecimal messagePhoneNumber = new BigDecimal(1234567);
  private String middleInitialName = "A";
  private String namePrefixDescription = "ABC123";
  private BigDecimal primaryPhoneNumber = new BigDecimal(1234567);;
  private int primaryPhoneExtensionNumber = 123;
  private Short stateCodeType = 1234;
  private String streetName = "ABC STREET";
  private String streetNumber = "123";
  private String suffixTitleDescription = "Ms";
  private String zipcode = "95842";
  private String lawEnforcementId = "1234567ABC";
  private Short zipSuffixNumber = 1234;
  private String countySpecificCode = "AB";

  @Before
  public void setup() throws Exception {
    @SuppressWarnings("rawtypes")
    CrudsDao crudsDao = mock(CrudsDao.class);
    when(crudsDao.find(any())).thenReturn(mock(gov.ca.cwds.data.persistence.cms.Reporter.class));
    Reporter validReporter = validReporter();

    when(mockedReporterResource.create(eq(validReporter))).thenReturn(
        Response.status(Response.Status.NO_CONTENT).entity(null).build());
  }

  /*
   * Constructor Tests
   */
  @Test
  public void persistentObjectConstructorTest() throws Exception {
    Reporter domain =
        new Reporter(badgeNumber, cityName, colltrClientRptrReltnshpType, communicationMethodType,
            confidentialWaiverIndicator, drmsMandatedRprtrFeedback, employerName, feedbackDate,
            feedbackRequiredIndicator, firstName, lastName, mandatedReporterIndicator,
            messagePhoneExtensionNumber, messagePhoneNumber, middleInitialName,
            namePrefixDescription, primaryPhoneNumber, primaryPhoneExtensionNumber, stateCodeType,
            streetName, streetNumber, suffixTitleDescription, zipcode, referralId,
            lawEnforcementId, zipSuffixNumber, countySpecificCode);
    gov.ca.cwds.data.persistence.cms.Reporter persistent =
        new gov.ca.cwds.data.persistence.cms.Reporter(domain, "lastUpdatedId");

    Reporter totest = new Reporter(persistent);
    assertThat(totest.getReferralId(), is(equalTo(persistent.getReferralId())));
    assertThat(totest.getBadgeNumber(), is(equalTo(persistent.getBadgeNumber())));
    assertThat(totest.getCityName(), is(equalTo(persistent.getCityName())));
    assertThat(totest.getColltrClientRptrReltnshpType(),
        is(equalTo(persistent.getColltrClientRptrReltnshpType())));
    assertThat(totest.getCommunicationMethodType(),
        is(equalTo(persistent.getCommunicationMethodType())));
    assertThat(totest.getConfidentialWaiverIndicator(),
        is(equalTo(DomainChef.uncookBooleanString(persistent.getConfidentialWaiverIndicator()))));
    assertThat(totest.getDrmsMandatedRprtrFeedback(),
        is(equalTo(persistent.getDrmsMandatedRprtrFeedback())));
    assertThat(totest.getEmployerName(), is(equalTo(persistent.getEmployerName())));
    assertThat(totest.getFeedbackDate(), is(equalTo(df.format(persistent.getFeedbackDate()))));
    assertThat(totest.getFeedbackRequiredIndicator(),
        is(equalTo(DomainChef.uncookBooleanString(persistent.getFeedbackRequiredIndicator()))));
    assertThat(totest.getFirstName(), is(equalTo(persistent.getFirstName())));
    assertThat(totest.getLastName(), is(equalTo(persistent.getLastName())));
    assertThat(totest.getMandatedReporterIndicator(),
        is(equalTo(DomainChef.uncookBooleanString(persistent.getMandatedReporterIndicator()))));
    assertThat(totest.getMessagePhoneExtensionNumber(),
        is(equalTo(persistent.getMessagePhoneExtensionNumber())));
    assertThat(totest.getMessagePhoneNumber(), is(equalTo(persistent.getMessagePhoneNumber())));
    assertThat(totest.getMiddleInitialName(), is(equalTo(persistent.getMiddleInitialName())));
    assertThat(totest.getNamePrefixDescription(),
        is(equalTo(persistent.getNamePrefixDescription())));
    assertThat(totest.getPrimaryPhoneNumber(), is(equalTo(persistent.getPrimaryPhoneNumber())));
    assertThat(totest.getPrimaryPhoneExtensionNumber(),
        is(equalTo(persistent.getPrimaryPhoneExtensionNumber())));
    assertThat(totest.getStateCodeType(), is(equalTo(persistent.getStateCodeType())));
    assertThat(totest.getStreetName(), is(equalTo(persistent.getStreetName())));
    assertThat(totest.getStreetNumber(), is(equalTo(persistent.getStreetNumber())));
    assertThat(totest.getSuffixTitleDescription(),
        is(equalTo(persistent.getSuffixTitleDescription())));
    assertThat(totest.getZipcode(),
        is(equalTo(DomainChef.cookZipcodeNumber(persistent.getZipNumber()))));
    assertThat(totest.getLawEnforcementId(), is(equalTo(persistent.getLawEnforcementId())));
    assertThat(totest.getZipSuffixNumber(), is(equalTo(persistent.getZipSuffixNumber())));
    assertThat(totest.getCountySpecificCode(), is(equalTo(persistent.getCountySpecificCode())));
  }

  @Test
  public void jsonCreatorConstructorTest() throws Exception {
    Reporter reporter =
        new Reporter(badgeNumber, cityName, colltrClientRptrReltnshpType, communicationMethodType,
            confidentialWaiverIndicator, drmsMandatedRprtrFeedback, employerName, feedbackDate,
            feedbackRequiredIndicator, firstName, lastName, mandatedReporterIndicator,
            messagePhoneExtensionNumber, messagePhoneNumber, middleInitialName,
            namePrefixDescription, primaryPhoneNumber, primaryPhoneExtensionNumber, stateCodeType,
            streetName, streetNumber, suffixTitleDescription, zipcode, referralId,
            lawEnforcementId, zipSuffixNumber, countySpecificCode);

    assertThat(reporter.getReferralId(), is(equalTo(referralId)));
    assertThat(reporter.getBadgeNumber(), is(equalTo(badgeNumber)));
    assertThat(reporter.getCityName(), is(equalTo(cityName)));
    assertThat(reporter.getColltrClientRptrReltnshpType(),
        is(equalTo(colltrClientRptrReltnshpType)));
    assertThat(reporter.getCommunicationMethodType(), is(equalTo(communicationMethodType)));
    assertThat(reporter.getConfidentialWaiverIndicator(), is(equalTo(confidentialWaiverIndicator)));
    assertThat(reporter.getDrmsMandatedRprtrFeedback(), is(equalTo(drmsMandatedRprtrFeedback)));
    assertThat(reporter.getEmployerName(), is(equalTo(employerName)));
    assertThat(reporter.getFeedbackDate(), is(equalTo(feedbackDate)));
    assertThat(reporter.getFeedbackRequiredIndicator(), is(equalTo(feedbackRequiredIndicator)));
    assertThat(reporter.getFirstName(), is(equalTo(firstName)));
    assertThat(reporter.getLastName(), is(equalTo(lastName)));
    assertThat(reporter.getMandatedReporterIndicator(), is(equalTo(mandatedReporterIndicator)));
    assertThat(reporter.getMessagePhoneExtensionNumber(), is(equalTo(messagePhoneExtensionNumber)));
    assertThat(reporter.getMessagePhoneNumber(), is(equalTo(messagePhoneNumber)));
    assertThat(reporter.getMiddleInitialName(), is(equalTo(middleInitialName)));
    assertThat(reporter.getNamePrefixDescription(), is(equalTo(namePrefixDescription)));
    assertThat(reporter.getPrimaryPhoneNumber(), is(equalTo(primaryPhoneNumber)));
    assertThat(reporter.getPrimaryPhoneExtensionNumber(), is(equalTo(primaryPhoneExtensionNumber)));
    assertThat(reporter.getStateCodeType(), is(equalTo(stateCodeType)));
    assertThat(reporter.getStreetName(), is(equalTo(streetName)));
    assertThat(reporter.getStreetNumber(), is(equalTo(streetNumber)));
    assertThat(reporter.getSuffixTitleDescription(), is(equalTo(suffixTitleDescription)));
    assertThat(reporter.getZipcode(), is(equalTo(zipcode)));
    assertThat(reporter.getLawEnforcementId(), is(equalTo(lawEnforcementId)));
    assertThat(reporter.getZipSuffixNumber(), is(equalTo(zipSuffixNumber)));
    assertThat(reporter.getCountySpecificCode(), is(equalTo(countySpecificCode)));
  }

  @Test
  public void createWithDefaultsShouldInitializeWithPassedInValues() {

    String streetNumber = "1";
    String streetName = "main";
    String streetAddress = streetNumber + " " + streetName;
    String city = "sacramento";
    String state = "ca";
    Integer zipCode = 12345;
    String type = "type";

    gov.ca.cwds.rest.api.domain.Address address =
        new gov.ca.cwds.rest.api.domain.Address("legacy_source_table", "legacy_id", streetAddress,
            city, state, zipCode, type);

    String referralId = "referralId";
    boolean isMandatedReporter = true;
    String firstName = "firstName";
    String lastName = "lastName";
    Participant participant =
        new Participant(5L, "legacy_source_table", "legacy_client_id", firstName, lastName,
            "gender", "ssn", "date_of_birth", 7L, 8L, new HashSet<>(), new HashSet<>());
    String countyCode = "countyCode";
    Short stateCode = new Short("0");

    Reporter reporter =
        Reporter.createWithDefaults(referralId, isMandatedReporter, address, participant,
            countyCode, stateCode);
    assertEquals("Expected referralId field to have been initialized with value", referralId,
        reporter.getReferralId());
    assertEquals("Expected isMandatedReporter field to have been initialized with value",
        isMandatedReporter, reporter.getMandatedReporterIndicator());
    assertEquals("Expected streetNumer field to have been initialized with value", streetNumber,
        reporter.getStreetNumber());
    assertEquals("Expected street name field to have been initialized with value", streetName,
        reporter.getStreetName());
    assertEquals("Expected city field to have been initialized with value", city,
        reporter.getCityName());
    assertEquals("Expected zipCode field to have been initialized with value", zipCode.toString(),
        reporter.getZipcode());
    assertEquals("Expected firstName field to have been initialized with value", firstName,
        reporter.getFirstName());
    assertEquals("Expected last name field to have been initialized with value", lastName,
        reporter.getLastName());
    assertEquals("Expected countyCode field to have been initialized with value", countyCode,
        reporter.getCountySpecificCode());
    assertEquals("Expected stateCode field to have been initialized with value", stateCode,
        reporter.getStateCodeType());
  }

  @Test
  public void createWithDefaultsShouldInitializeWithDefaultValues() {

    String streetNumber = "1";
    String streetName = "main";
    String streetAddress = streetNumber + " " + streetName;
    String city = "sacramento";
    String state = "ca";
    Integer zipCode = 12345;
    String type = "type";

    gov.ca.cwds.rest.api.domain.Address address =
        new gov.ca.cwds.rest.api.domain.Address("legacy_source_table", "legacy_id", streetAddress,
            city, state, zipCode, type);

    String referralId = "referralId";
    boolean isMandatedReporter = true;
    String firstName = "firstName";
    String lastName = "lastName";
    Participant participant =
        new Participant(5L, "legacy_source_table", "legacy_client_id", firstName, lastName,
            "gender", "ssn", "date_of_birth", 7L, 8L, new HashSet<>(), new HashSet<>());
    String countyCode = "countyCode";
    Short stateCode = new Short("0");

    Reporter reporter =
        Reporter.createWithDefaults(referralId, isMandatedReporter, address, participant,
            countyCode, stateCode);
    assertEquals("Expected badgeNumber field to have been initialized with value", "",
        reporter.getBadgeNumber());
    assertEquals("Expected colltrClientRptrReltnshpType field to have been initialized with value",
        new Short("0"), reporter.getColltrClientRptrReltnshpType());
    assertEquals("Expected communicationMethodType field to have been initialized with value",
        new Short("0"), reporter.getCommunicationMethodType());
    assertEquals("Expected confidentialWaiverIndicator field to have been initialized with value",
        false, reporter.getConfidentialWaiverIndicator());
    assertEquals("Expected drmsMandatedRprtrFeedback field to have been initialized with value",
        "", reporter.getDrmsMandatedRprtrFeedback());
    assertEquals("Expected employerName field to have been initialized with value", "",
        reporter.getEmployerName());
    assertEquals("Expected feedbackDate field to have been initialized with value", "",
        reporter.getFeedbackDate());
    assertEquals("Expected feedbackRequiredIndicator field to have been initialized with value",
        false, reporter.getFeedbackRequiredIndicator());
    assertEquals("Expected messagePhoneExtensionNumber field to have been initialized with value",
        new Integer("0"), reporter.getMessagePhoneExtensionNumber());
    assertEquals("Expected messagePhoneNumber field to have been initialized with value",
        new BigDecimal(0), reporter.getMessagePhoneNumber());
    assertEquals("Expected middleInitialName field to have been initialized with value", "",
        reporter.getMiddleInitialName());
    assertEquals("Expected namePrefixDescription field to have been initialized with value", "",
        reporter.getNamePrefixDescription());
    assertEquals("Expected primaryPhoneNumber field to have been initialized with value",
        new BigDecimal(0), reporter.getPrimaryPhoneNumber());
    assertEquals("Expected primaryPhoneExtensionNumber field to have been initialized with value",
        new Integer("0"), reporter.getPrimaryPhoneExtensionNumber());
    assertEquals("Expected suffixTitleDescription field to have been initialized with value", "",
        reporter.getSuffixTitleDescription());
    assertEquals("Expected lawEnforcementId field to have been initialized with value", "",
        reporter.getLawEnforcementId());
    assertEquals("Expected zipSuffixNumber field to have been initialized with value", new Short(
        "0"), reporter.getZipSuffixNumber());
  }

  @Test
  public void streetNumberShouldBeParsedFromStreetAddress() {
    String streetAddress = "1 main";

    gov.ca.cwds.rest.api.domain.Address address =
        new gov.ca.cwds.rest.api.domain.Address("legacy_source_table", "legacy_id", streetAddress,
            "city", "state", 12345, "type");
    Participant participant =
        new Participant(5L, "legacy_source_table", "legacy_client_id", "firstName", "lastName",
            "gender", "ssn", "date_of_birth", 7L, 8L, new HashSet<>(), new HashSet<>());

    Reporter reporter =
        Reporter.createWithDefaults("referralId", true, address, participant, "countyCode",
            new Short("0"));

    assertEquals("Street Number not parsed from street address", "1", reporter.getStreetNumber());
  }

  @Test
  public void streetNameShouldBeParsedFromStreetAddress() {
    String streetAddress = "1 main";

    gov.ca.cwds.rest.api.domain.Address address =
        new gov.ca.cwds.rest.api.domain.Address("legacy_source_table", "legacy_id", streetAddress,
            "city", "state", 12345, "type");
    Participant participant =
        new Participant(5L, "legacy_source_table", "legacy_client_id", "firstName", "lastName",
            "gender", "ssn", "date_of_birth", 7L, 8L, new HashSet<>(), new HashSet<>());

    Reporter reporter =
        Reporter.createWithDefaults("referralId", true, address, participant, "countyCode",
            new Short("0"));

    assertEquals("Street Number not parsed from street address", "main", reporter.getStreetName());
  }

  @Test
  public void streetNameShouldNotIncludeTypeOfStreetsOrMultiPartStreetNames() {
    String streetAddress = "1 San Andreas Blvd";

    gov.ca.cwds.rest.api.domain.Address address =
        new gov.ca.cwds.rest.api.domain.Address("legacy_source_table", "legacy_id", streetAddress,
            "city", "state", 12345, "type");
    Participant participant =
        new Participant(5L, "legacy_source_table", "legacy_client_id", "firstName", "lastName",
            "gender", "ssn", "date_of_birth", 7L, 8L, new HashSet<>(), new HashSet<>());

    Reporter reporter =
        Reporter.createWithDefaults("referralId", true, address, participant, "countyCode",
            new Short("0"));

    assertEquals("Street Number not parsed from street address", "San Andreas Blvd",
        reporter.getStreetName());
  }

  @Test
  public void streetNameShouldContainSecondWordWhenOnlyNoStreetNumberIsPresent() {
    String streetAddress = "Main St";

    gov.ca.cwds.rest.api.domain.Address address =
        new gov.ca.cwds.rest.api.domain.Address("legacy_source_table", "legacy_id", streetAddress,
            "city", "state", 12345, "type");
    Participant participant =
        new Participant(5L, "legacy_source_table", "legacy_client_id", "firstName", "lastName",
            "gender", "ssn", "date_of_birth", 7L, 8L, new HashSet<>(), new HashSet<>());

    Reporter reporter =
        Reporter.createWithDefaults("referralId", true, address, participant, "countyCode",
            new Short("0"));

    assertEquals("Expected StreetName to contain second word as street name", "Main St",
        reporter.getStreetName());
  }

  @Test
  public void testForStreetAddressContainOnlyWordWhereStreetNumberisNull() {
    String streetAddress = "Main St";

    gov.ca.cwds.rest.api.domain.Address nsAddress =
        new gov.ca.cwds.rest.api.domain.Address("legacy_source_table", "legacy_id", streetAddress,
            "city", "state", 12345, "type");

    Address address = Address.createWithDefaults(nsAddress, new Short("5"));
    assertThat(address.getStreetNumber(), is(equalTo(null)));
  }

  @Test
  public void equalsHashCodeWork() {
    EqualsVerifier.forClass(Reporter.class).suppress(Warning.NONFINAL_FIELDS)
        .withIgnoredFields("messages").verify();
  }

  @Test
  public void serializesToJSON() throws Exception {
    final String expected =
        MAPPER.writeValueAsString(MAPPER.readValue(
            fixture("fixtures/domain/legacy/Reporter/valid/valid.json"), Reporter.class));

    assertThat(MAPPER.writeValueAsString(validReporter()), is(equalTo(expected)));
  }

  @Test
  public void deserializesFromJSON() throws Exception {
    assertThat(MAPPER.readValue(fixture("fixtures/domain/legacy/Reporter/valid/valid.json"),
        Reporter.class), is(equalTo(validReporter())));
  }

  /*
   * Successful Tests
   */
  @Test
  public void successfulWithValid() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(fixture("fixtures/domain/legacy/Reporter/valid/valid.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  /*
   * Class level streetNameAndCityName tests
   * 
   * @IfThen(ifProperty = "streetName", thenProperty = "cityName", required = false)
   */
  @Test
  public void testStreetNameNotCityNameFails() throws Exception {
    Reporter toCreate =
        MAPPER
            .readValue(
                fixture("fixtures/domain/legacy/Reporter/invalid/_classLevel/streetNameProvidedAndCityNameNot.json"),
                Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class),
        is(equalTo("{\"errors\":[\"cityName is required since streetName is set\"]}")));
  }

  @Test
  public void testStreetNameAndCityNameSuccess() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(
            fixture("fixtures/domain/legacy/Reporter/valid/streetNameAndCityName.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void testNotStreetNameNotCityNameSuccess() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(
            fixture("fixtures/domain/legacy/Reporter/valid/notStreetNameNotCityName.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void testNotStreetNameAndCityNameSuccess() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(
            fixture("fixtures/domain/legacy/Reporter/valid/NotStreetNameAndCityName.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  /*
   * 
   * Class level streetNumberAndStreetName tests
   * 
   * @IfThen.List({@IfThen(ifProperty = "streetNumber", thenProperty = "streetName", required =
   * false)
   */
  @Test
  public void testStreetNumberNotStreetNameFails() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(
            fixture("fixtures/domain/legacy/Reporter/invalid//streetNumberNotStreetName.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class),
        is(equalTo("{\"errors\":[\"streetName is required since streetNumber is set\"]}")));
  }

  @Test
  public void testStreetNumberAndStreetNameSuccess() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(
            fixture("fixtures/domain/legacy/Reporter/valid/streetNumberAndStreetName.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void testNotStreetNumberNotStreetNameSuccess() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(
            fixture("fixtures/domain/legacy/Reporter/valid/notStreetNumberNotStreetName.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void testStreetNameNotStreetNumberSuccess() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(
            fixture("fixtures/domain/legacy/Reporter/valid/StreetNameNotStreetNumber.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  /*
   * Class level lawEnforcementIdAndEmployerName tests
   * 
   * Referential Integrity Rule tests R - 00849 Employer Name specification. If REPORTER is
   * associated with LAW ENFORCEMENT, the EMPLOYER NAME (AGENCY NAME) must not be specified.
   * 
   * @MutuallyExclusive(required = false, properties = {"employerName", "lawEnforcementId"})
   */
  @Test
  public void testLawEnforcementIdAndEmployerNameFails() throws Exception {
    Reporter toCreate =
        MAPPER
            .readValue(
                fixture("fixtures/domain/legacy/Reporter/invalid/LawEnforcementIdAndEmployerName.json"),
                Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class),
        is(equalTo("{\"errors\":[\"Properties [employerName, lawEnforcementId] are mutually exclusive but multiple values are set\"]}")));
  }

  @Test
  public void testLawEnforcementIdNotEmployerNameNotSuccess() throws Exception {
    Reporter toCreate =
        MAPPER
            .readValue(
                fixture("fixtures/domain/legacy/Reporter/valid/notLawEnforcementIdNotEmployerName.json"),
                Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));

  }

  @Test
  public void testLawEnforcementIdNullEmployerNameSuccess() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(
            fixture("fixtures/domain/legacy/Reporter/valid/lawEnforcementIdNullEmployerName.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void testLawEnforcementIdEmployerNameNotSuccess() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(
            fixture("fixtures/domain/legacy/Reporter/valid/lawEnforcementIdEmployerNameNot.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  /*
   * badgeNumber Tests
   * 
   * Referential Integrity Rule tests R - 00846 Badge Number specification. BADGE NUMBER may only be
   * specified if the REPORTER is associated with LAW ENFORCEMENT.
   * 
   * @OnlyIf(property = "badgeNumber", ifProperty = "lawEnforcementId")
   */
  @Test
  public void testBadgeNumberTooLong() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(
            fixture("fixtures/domain/legacy/Reporter/invalid/badgeNumberTooLong.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class),
        is(equalTo("{\"errors\":[\"badgeNumber size must be less than or equal to 6\"]}")));
  }

  @Test
  public void testBadgeNumberNotLawEnforcementIdFails() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(
            fixture("fixtures/domain/legacy/Reporter/invalid/badgeNumberNotLawEnforcementId.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class),
        is(equalTo("{\"errors\":[\"badgeNumber can only be set if lawEnforcementId is set\"]}")));
  }

  @Test
  public void testBadgeNumberEmptyLawEnforcementIdSuccess() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(
            fixture("fixtures/domain/legacy/Reporter/valid/badgeNumberEmptyLawEnforcementId.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void testBadgeNumberEmptySuccess() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(fixture("fixtures/domain/legacy/Reporter/valid/badgeNumberEmpty.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void testBadgeNumberMissingSuccess() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(fixture("fixtures/domain/legacy/Reporter/valid/badgeNumberMissing.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void testBadgeNumberNullSuccess() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(fixture("fixtures/domain/legacy/Reporter/valid/badgeNumberNull.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  /*
   * cityName Tests
   */
  @Test
  public void successWhenCityNameMissing() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(fixture("fixtures/domain/legacy/Reporter/invalid/cityNameMissing.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class),
        is(equalTo("{\"errors\":[\"cityName may not be null\"]}")));
  }

  @Test
  public void successWhenCityNameNull() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(fixture("fixtures/domain/legacy/Reporter/invalid/cityNameNull.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class),
        is(equalTo("{\"errors\":[\"cityName may not be null\"]}")));
  }

  @Test
  public void successWhenCityNameEmpty() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(fixture("fixtures/domain/legacy/Reporter/valid/cityNameEmpty.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void failsWhenCityNameTooLong() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(fixture("fixtures/domain/legacy/Reporter/invalid/cityNameTooLong.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class),
        is(equalTo("{\"errors\":[\"cityName size must be less than or equal to 20\"]}")));
  }

  /*
   * colltrClientRptrReltnshpType Tests
   */
  @Test
  public void failsWhenColltrClientRptrReltnshpTypeMissing() throws Exception {
    Reporter toCreate =
        MAPPER
            .readValue(
                fixture("fixtures/domain/legacy/Reporter/invalid/colltrClientRptrReltnshpTypemissing.json"),
                Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("colltrClientRptrReltnshpType may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenColltrClientRptrReltnshpTypeNull() throws Exception {
    Reporter toCreate =
        MAPPER
            .readValue(
                fixture("fixtures/domain/legacy/Reporter/invalid/colltrClientRptrReltnshpTypeNull.json"),
                Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("colltrClientRptrReltnshpType may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenColltrClientRptrReltnshpTypeAllWhiteSpace() throws Exception {
    Reporter toCreate =
        MAPPER
            .readValue(
                fixture("fixtures/domain/legacy/Reporter/invalid/colltrClientRptrReltnshpTypeAllWhiteSpace.json"),
                Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("colltrClientRptrReltnshpType may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * communicationMethodType Tests
   */
  @Test
  public void failsWhenCommunicationMethodTypeMissing() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(
            fixture("fixtures/domain/legacy/Reporter/invalid/communicationMethodTypeMissing.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("communicationMethodType may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenCommunicationMethodTypeNull() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(
            fixture("fixtures/domain/legacy/Reporter/invalid/communicationMethodTypeNull.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("communicationMethodType may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenCommunicationMethodTypeAllWhiteSpace() throws Exception {
    Reporter toCreate =
        MAPPER
            .readValue(
                fixture("fixtures/domain/legacy/Reporter/invalid/communicationMethodTypeAllWhiteSpace.json"),
                Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("communicationMethodType may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * confidentialWaiverIndicator Tests
   */
  @Test
  public void failsWhenConfidentialWaiverIndicatorMissing() throws Exception {
    Reporter toCreate =
        MAPPER
            .readValue(
                fixture("fixtures/domain/legacy/Reporter/invalid/confidentialWaiverIndicatorMissing.json"),
                Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("confidentialWaiverIndicator may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenConfidentialWaiverIndicatorNull() throws Exception {
    Reporter toCreate =
        MAPPER
            .readValue(
                fixture("fixtures/domain/legacy/Reporter/invalid/confidentialWaiverIndicatorNull.json"),
                Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("confidentialWaiverIndicator may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenConfidentialWaiverIndicatorEmpty() throws Exception {
    Reporter toCreate =
        MAPPER
            .readValue(
                fixture("fixtures/domain/legacy/Reporter/invalid/confidentialWaiverIndicatorEmpty.json"),
                Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("confidentialWaiverIndicator may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenConfidentialWaiverIndicatorAllWhitespace() throws Exception {
    Reporter toCreate =
        MAPPER
            .readValue(
                fixture("fixtures/domain/legacy/Reporter/invalid/confidentialWaiverIndicatorAllWhitespace.json"),
                Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("confidentialWaiverIndicator may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * drmsMandatedRprtrFeedback Tests
   */
  @Test
  public void successWhenDrmsMandatedRprtrFeedbackEmpty() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(
            fixture("fixtures/domain/legacy/Reporter/valid/drmsMandatedRprtrFeedbackEmpty.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void successWhenDrmsMandatedRprtrFeedbackNull() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(
            fixture("fixtures/domain/legacy/Reporter/valid/drmsMandatedRprtrFeedbackNull.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void failsWhenDrmsMandatedRprtrFeedbackTooLong() throws Exception {
    Reporter toCreate =
        MAPPER
            .readValue(
                fixture("fixtures/domain/legacy/Reporter/invalid/drmsMandatedRprtrFeedbackTooLong.json"),
                Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf(
            "drmsMandatedRprtrFeedback size must be between 0 and 10"), is(greaterThanOrEqualTo(0)));
  }

  /*
   * employerName Tests
   */
  @Test
  public void successWhenEmployerNameValid() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(fixture("fixtures/domain/legacy/Reporter/valid/valid.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void testEmployerNameNullFails() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(fixture("fixtures/domain/legacy/Reporter/invalid/employerNameNull.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    // System.out.println(response.readEntity(String.class));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class),
        is(equalTo("{\"errors\":[\"employerName may not be null\"]}")));
  }

  @Test
  public void failsWhenEmployerNameTooLong() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(
            fixture("fixtures/domain/legacy/Reporter/invalid/employerNameTooLong.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class),
        is(equalTo("{\"errors\":[\"employerName size must be less than or equal to 35\"]}")));
  }

  /*
   * feedbackDate Tests
   */
  @Test
  public void successWhenFeedbackDateEmpty() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(fixture("fixtures/domain/legacy/Reporter/valid/feedbackDateEmpty.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void successWhenFeedbackDateNull() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(fixture("fixtures/domain/legacy/Reporter/valid/feedbackDateNull.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void failsWhenFeedbackDateWrongFormat() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(
            fixture("fixtures/domain/legacy/Reporter/invalid/feedbackDateWrongFormat.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf(
            "feedbackDate must be in the format of yyyy-MM-dd"), is(greaterThanOrEqualTo(0)));
  }

  /*
   * feedbackRequiredIndicator Tests
   */
  @Test
  public void failsWhenFeedbackRequiredIndicatorMissing() throws Exception {
    Reporter toCreate =
        MAPPER
            .readValue(
                fixture("fixtures/domain/legacy/Reporter/invalid/feedbackRequiredIndicatorMissing.json"),
                Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("feedbackRequiredIndicator may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenFeedbackRequiredIndicatorNull() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(
            fixture("fixtures/domain/legacy/Reporter/invalid/feedbackRequiredIndicatorNull.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("feedbackRequiredIndicator may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenFeedbackRequiredIndicatorEmpty() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(
            fixture("fixtures/domain/legacy/Reporter/invalid/feedbackRequiredIndicatorEmpty.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("feedbackRequiredIndicator may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenFeedbackRequiredIndicatorAllWhitespace() throws Exception {
    Reporter toCreate =
        MAPPER
            .readValue(
                fixture("fixtures/domain/legacy/Reporter/invalid/feedbackRequiredIndicatorAllWhitespace.json"),
                Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("feedbackRequiredIndicator may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * firstName Tests
   */
  @Test
  public void failsWhenFirstNameMissing() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(fixture("fixtures/domain/legacy/Reporter/invalid/firstNameMissing.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("firstName may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenFirstNameNull() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(fixture("fixtures/domain/legacy/Reporter/invalid/firstNameNull.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("firstName may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenFirstNameEmpty() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(fixture("fixtures/domain/legacy/Reporter/invalid/firstNameEmpty.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("firstName may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenFirstNameTooLong() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(fixture("fixtures/domain/legacy/Reporter/invalid/firstNameTooLong.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("firstName size must be between 1 and 20"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void successWhenFirstNameAllWhiteSpace() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(
            fixture("fixtures/domain/legacy/Reporter/valid/firstNameAllWhiteSpace.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  /*
   * lastName Tests
   */
  @Test
  public void failsWhenLastNameMissing() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(fixture("fixtures/domain/legacy/Reporter/invalid/lastNameMissing.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("lastName may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenLastNameNull() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(fixture("fixtures/domain/legacy/Reporter/invalid/lastNameNull.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("lastName may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenLastNameEmpty() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(fixture("fixtures/domain/legacy/Reporter/invalid/lastNameEmpty.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("lastName may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void successWhenLastNameAllWhiteSpace() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(
            fixture("fixtures/domain/legacy/Reporter/valid/lastNameAllWhiteSpace.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void failsWhenLastNameTooLong() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(fixture("fixtures/domain/legacy/Reporter/invalid/lastNameTooLong.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("lastName size must be between 1 and 25"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * mandatedReporterIndicator Tests
   */
  @Test
  public void failsWhenMandatedReporterIndicatorMissing() throws Exception {
    Reporter toCreate =
        MAPPER
            .readValue(
                fixture("fixtures/domain/legacy/Reporter/invalid/mandatedReporterIndicatorMissing.json"),
                Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("mandatedReporterIndicator may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenMandatedReporterIndicatorNull() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(
            fixture("fixtures/domain/legacy/Reporter/invalid/mandatedReporterIndicatorNull.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("mandatedReporterIndicator may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenMandatedReporterIndicatorEmpty() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(
            fixture("fixtures/domain/legacy/Reporter/invalid/mandatedReporterIndicatorEmpty.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("mandatedReporterIndicator may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenMandatedReporterIndicatorAllWhitespace() throws Exception {
    Reporter toCreate =
        MAPPER
            .readValue(
                fixture("fixtures/domain/legacy/Reporter/invalid/mandatedReporterIndicatorAllWhitespace.json"),
                Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("mandatedReporterIndicator may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * messagePhoneExtensionNumber Tests
   */
  @Test
  public void failsWhenMessagePhoneExtensionNumberMissing() throws Exception {
    Reporter toCreate =
        MAPPER
            .readValue(
                fixture("fixtures/domain/legacy/Reporter/invalid/messagePhoneExtensionNumberMissing.json"),
                Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("messagePhoneExtensionNumber may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenMessagePhoneExtensionNumberNull() throws Exception {
    Reporter toCreate =
        MAPPER
            .readValue(
                fixture("fixtures/domain/legacy/Reporter/invalid/messagePhoneExtensionNumberNull.json"),
                Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("messagePhoneExtensionNumber may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failWhenMessagePhoneExtensionNumberAllWhiteSpace() throws Exception {
    Reporter toCreate =
        MAPPER
            .readValue(
                fixture("fixtures/domain/legacy/Reporter/invalid/messagePhoneExtensionNumberAllWhiteSpace.json"),
                Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("messagePhoneExtensionNumber may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * messagePhoneNumber Tests
   */
  @Test
  public void failsWhenMessagePhoneNumberMissing() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(
            fixture("fixtures/domain/legacy/Reporter/invalid/messagePhoneNumberMissing.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("messagePhoneNumber may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenMessagePhoneNumberNull() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(
            fixture("fixtures/domain/legacy/Reporter/invalid/messagePhoneNumberNull.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("messagePhoneNumber may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenMessagePhoneNumberEmpty() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(
            fixture("fixtures/domain/legacy/Reporter/invalid/messagePhoneNumberEmpty.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("messagePhoneNumber may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * middleInitialName Tests
   */
  @Test
  public void failsWhenMiddleInitialNameMissing() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(
            fixture("fixtures/domain/legacy/Reporter/invalid/middleInitialNameMissing.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("middleInitialName may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenMiddleInitialNameNull() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(
            fixture("fixtures/domain/legacy/Reporter/invalid/middleInitialNameNull.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("middleInitialName may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void successWhenMiddleInitialNameEmpty() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(
            fixture("fixtures/domain/legacy/Reporter/valid/middleInitialNameEmpty.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void failsWhenMiddleInitialNameTooLong() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(
            fixture("fixtures/domain/legacy/Reporter/invalid/middleInitialNameTooLong.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("middleInitialName size must be between 0 and 1"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void successWhenMiddleInitialNameAllWhiteSpace() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(
            fixture("fixtures/domain/legacy/Reporter/valid/middleInitialNameTAllWhiteSpace.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  /*
   * namePrefixDescription Tests
   */
  @Test
  public void failsWhenNamePrefixDescriptionMissing() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(
            fixture("fixtures/domain/legacy/Reporter/invalid/namePrefixDescriptionMissing.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("namePrefixDescription may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenNamePrefixDescriptionNull() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(
            fixture("fixtures/domain/legacy/Reporter/invalid/namePrefixDescriptionNull.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("namePrefixDescription may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void successWhenNamePrefixDescriptionEmpty() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(
            fixture("fixtures/domain/legacy/Reporter/valid/namePrefixDescriptionEmpty.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void failsWhenNamePrefixDescriptionTooLong() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(
            fixture("fixtures/domain/legacy/Reporter/invalid/namePrefixDescriptionTooLong.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf(
            "namePrefixDescription size must be between 0 and 6"), is(greaterThanOrEqualTo(0)));
  }

  /*
   * primaryPhoneNumber Tests
   */
  @Test
  public void failsWhenPrimaryPhoneNumberMissing() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(
            fixture("fixtures/domain/legacy/Reporter/invalid/primaryPhoneNumberMissing.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("primaryPhoneNumber may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenPrimaryPhoneNumberNull() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(
            fixture("fixtures/domain/legacy/Reporter/invalid/primaryPhoneNumberNull.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("primaryPhoneNumber may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * primaryPhoneExtensionNumber Tests
   */
  @Test
  public void failsWhenPrimaryPhoneExtensionNumberMissing() throws Exception {
    Reporter toCreate =
        MAPPER
            .readValue(
                fixture("fixtures/domain/legacy/Reporter/invalid/primaryPhoneExtensionNumberMissing.json"),
                Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("primaryPhoneExtensionNumber may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenPrimaryPhoneExtensionNumberNull() throws Exception {
    Reporter toCreate =
        MAPPER
            .readValue(
                fixture("fixtures/domain/legacy/Reporter/invalid/primaryPhoneExtensionNumberNull.json"),
                Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("primaryPhoneExtensionNumber may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * stateCodeType Tests
   */
  @Test
  public void failsWhenStateCodeTypeMissing() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(
            fixture("fixtures/domain/legacy/Reporter/invalid/stateCodeTypeMissing.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("stateCodeType may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenStateCodeTypeNull() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(fixture("fixtures/domain/legacy/Reporter/invalid/stateCodeTypeNull.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("stateCodeType may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * streetName Tests
   */
  @Test
  public void failureWhenStreetNameMissing() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(fixture("fixtures/domain/legacy/Reporter/invalid/streetNameMissing.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("streetName may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failureWhenStreetNameNull() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(fixture("fixtures/domain/legacy/Reporter/invalid/streetNameNull.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("streetName may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void successWhenStreetNameEmpty() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(fixture("fixtures/domain/legacy/Reporter/valid/streetNameEmpty.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void failsWhenStreetNameTooLong() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(fixture("fixtures/domain/legacy/Reporter/invalid/streetNameTooLong.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class),
        is(equalTo("{\"errors\":[\"streetName size must be less than or equal to 40\"]}")));
  }

  /*
   * streetNumber Tests
   */
  @Test
  public void failWhenStreetNumberMissing() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(
            fixture("fixtures/domain/legacy/Reporter/invalid/streetNumberMissing.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("streetNumber may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failWhenStreetNumberNull() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(fixture("fixtures/domain/legacy/Reporter/invalid/streetNumberNull.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("streetNumber may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void successWhenStreetNumberEmpty() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(fixture("fixtures/domain/legacy/Reporter/valid/streetNumberEmpty.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void failsWhenStreetNumberTooLong() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(
            fixture("fixtures/domain/legacy/Reporter/invalid/streetNumberTooLong.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class),
        is(equalTo("{\"errors\":[\"streetNumber size must be less than or equal to 10\"]}")));
  }

  /*
   * suffixTitleDescription Tests
   */
  @Test
  public void failsWhenSuffixTitleDescriptionMissing() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(
            fixture("fixtures/domain/legacy/Reporter/invalid/suffixTitleDescriptionMissing.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("suffixTitleDescription may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenSuffixTitleDescriptionNull() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(
            fixture("fixtures/domain/legacy/Reporter/invalid/suffixTitleDescriptionNull.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("suffixTitleDescription may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void sucessWhenSuffixTitleDescriptionEmpty() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(
            fixture("fixtures/domain/legacy/Reporter/valid/suffixTitleDescriptionEmpty.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void failsWhenSuffixTitleDescriptionTooLong() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(
            fixture("fixtures/domain/legacy/Reporter/invalid/suffixTitleDescriptionTooLong.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf(
            "suffixTitleDescription size must be between 0 and 4"), is(greaterThanOrEqualTo(0)));
  }

  /*
   * Zipcode tests
   */
  @Test
  public void failsWhenZipcodeMissing() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(fixture("fixtures/domain/legacy/Reporter/invalid/zipcodeMissing.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("zipcode may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenZipcodeNull() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(fixture("fixtures/domain/legacy/Reporter/invalid/zipcodeNull.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("zipcode may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenZipcodeTooShort() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(fixture("fixtures/domain/legacy/Reporter/invalid/zipcodeTooShort.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("zipcode size must be between 5 and 5"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenZipcodeTooLong() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(fixture("fixtures/domain/legacy/Reporter/invalid/zipcodeTooLong.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class),
        is(equalTo("{\"errors\":[\"zipcode size must be between 5 and 5\"]}")));
  }

  // @Test
  // public void failsWhenZipcodeNonDigits() throws Exception {
  // Reporter toCreate = MAPPER.readValue(
  // fixture("fixtures/domain/legacy/Reporter/invalid/zipcodeNonDigits.json"), Reporter.class);
  // Response response =
  // resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
  // .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
  // assertThat(response.getStatus(), is(equalTo(422)));
  // String message = response.readEntity(String.class);
  // System.out.print(message);
  // assertThat(response.readEntity(String.class),
  // is(equalTo("{\"errors\":[\"zipcode must be 5 digits\"]}")));
  // }
  //
  // @Test
  // public void successWhenZipcodeFiveDigits() throws Exception {
  // Reporter toCreate = MAPPER.readValue(
  // fixture("fixtures/domain/legacy/Reporter/valid/zipcode/fiveDigits.json"), Reporter.class);
  // Response response =
  // resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
  // .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
  // assertThat(response.getStatus(), is(equalTo(204)));
  // }

  /*
   * referralId Tests
   */
  @Test
  public void failsWhenReferralIdMissing() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(fixture("fixtures/domain/legacy/Reporter/invalid/referralIdMissing.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("referralId may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenReferralIdNull() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(fixture("fixtures/domain/legacy/Reporter/invalid/referralIdNull.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("referralId may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenReferralIdEmpty() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(fixture("fixtures/domain/legacy/Reporter/invalid/referralIdEmpty.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("referralId may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenReferralIdTooLong() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(fixture("fixtures/domain/legacy/Reporter/invalid/referralIdTooLong.json"),
            Reporter.class);
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
  public void successWhenLawEnforcementIdValid() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(fixture("fixtures/domain/legacy/Reporter/valid/valid.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    String message = response.readEntity(String.class);
    System.out.print(message);
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void successWhenLawEnforcementNull() throws Exception {
    Reporter toCreate =
        MAPPER
            .readValue(
                fixture("fixtures/domain/legacy/Reporter/valid/lawEnforcementIdLawEnforcementNull.json"),
                Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void failsWhenLawEnforcementIdTooLong() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(
            fixture("fixtures/domain/legacy/Reporter/invalid/lawEnforcementIdTooLong.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("lawEnforcementId size must be 10"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * zipSuffixNumber Tests
   */
  @Test
  public void failsWhenZipSuffixNumberMissing() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(
            fixture("fixtures/domain/legacy/Reporter/invalid/zipSuffixNumberMissing.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("zipSuffixNumber may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenZipSuffixNumberNull() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(
            fixture("fixtures/domain/legacy/Reporter/invalid/zipSuffixNumberNull.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("zipSuffixNumber may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * countySpecificCode Tests
   */
  @Test
  public void failsWhenCountySpecificCodeMissing() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(
            fixture("fixtures/domain/legacy/Reporter/invalid/countySpecificCodeMissing.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("countySpecificCode may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenCountySpecificCodeNull() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(
            fixture("fixtures/domain/legacy/Reporter/invalid/countySpecificCodeNull.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("countySpecificCode may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenCountySpecificCodeEmpty() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(
            fixture("fixtures/domain/legacy/Reporter/invalid/countySpecificCodeEmpty.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("countySpecificCode may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenCountySpecificCodeTooLong() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(
            fixture("fixtures/domain/legacy/Reporter/invalid/countySpecificCodeTooLong.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class)
            .indexOf("countySpecificCode size must be between 1 and 2"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * Utilities
   */
  private Reporter validReporter() throws JsonParseException, JsonMappingException, IOException {

    Reporter validReporter =
        MAPPER.readValue(fixture("fixtures/domain/legacy/Reporter/valid/valid.json"),
            Reporter.class);
    return validReporter;

  }
}
