package gov.ca.cwds.rest.api.domain;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.ws.rs.core.Response;

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.squarespace.jersey2.guice.JerseyGuiceUtils;

import gov.ca.cwds.data.cms.TestSystemCodeCache;
import gov.ca.cwds.data.persistence.junit.template.PersistentTestTemplate;
import gov.ca.cwds.fixture.AddressResourceBuilder;
import gov.ca.cwds.fixture.ParticipantResourceBuilder;
import gov.ca.cwds.rest.core.Api;
import gov.ca.cwds.rest.resources.ParticipantResource;
import gov.ca.cwds.rest.resources.cms.JerseyGuiceRule;
import io.dropwizard.jackson.Jackson;
import io.dropwizard.testing.junit.ResourceTestRule;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

/**
 * @author CWDS API Team
 *
 */
@SuppressWarnings({"javadoc"})
public class ParticipantTest implements PersistentTestTemplate {

  private final Short primaryLanguage = 1253;
  private final Short secondaryLanguage = 1271;
  private TestSystemCodeCache testSystemCodeCache = new TestSystemCodeCache();

  private long id = 5432;
  private String legacySourceTable = "CLIENT_T";
  private String clientId = "1234567ABC";
  private long personId = 12345;
  private long screeningId = 12345;
  private String firstName = "John";
  private String middleName = "T.";
  private String lastName = "Smith";
  private String suffix = "";
  private String gender = "M";
  private String dateOfBirth = "2001-03-15";
  private String ssn = "123456789";
  private Set<String> roles = new HashSet<String>();
  private Set<Address> addresses = new HashSet<Address>();

  private static final String ROOT_RESOURCE = "/" + Api.RESOURCE_PARTICIPANTS + "/";;
  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();
  private Validator validator;

  private static final ParticipantResource mockedParticipantResource =
      mock(ParticipantResource.class);

  @After
  public void ensureServiceLocatorPopulated() {
    JerseyGuiceUtils.reset();
  }

  @ClassRule
  public static JerseyGuiceRule rule = new JerseyGuiceRule();

  @ClassRule
  public static final ResourceTestRule resources =
      ResourceTestRule.builder().addResource(mockedParticipantResource).build();

  @Before
  public void setup() {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
    MAPPER.configure(SerializationFeature.INDENT_OUTPUT, true);
    Participant validParticipant = this.validParticipant();
    roles.add("Victim");
    Address address = new Address("", "", "123 First St", "San Jose", 1828, 94321, 32);
    addresses.add(address);
    MAPPER.configure(SerializationFeature.INDENT_OUTPUT, true);

    when(mockedParticipantResource.create(eq(validParticipant)))
        .thenReturn(Response.status(Response.Status.NO_CONTENT).entity(null).build());

  }

  /*
   * Serialization and de-serialization
   */
  @Test
  public void serializesToJSON() throws Exception {
    String expected = MAPPER.writeValueAsString(validParticipant());

    String serialized = MAPPER.writeValueAsString(MAPPER
        .readValue(fixture("fixtures/domain/participant/valid/valid.json"), Participant.class));

    assertThat(serialized, is(expected));
  }

  @Test
  public void deserializesFromJSON() throws Exception {
    Participant expected = this.validParticipant();

    Participant serialized = MAPPER
        .readValue(fixture("fixtures/domain/participant/valid/valid.json"), Participant.class);
    assertThat(serialized, is(expected));
  }

  @Override
  @Test
  public void testEqualsHashCodeWorks() {
    EqualsVerifier.forClass(Participant.class).suppress(Warning.NONFINAL_FIELDS)
        .withIgnoredFields("messages", "victim", "reporter", "perpetrator").verify();
  }

  @Override
  @Test
  public void testEmptyConstructor() throws Exception {
    Participant empty = new Participant();
    assertThat(empty.getClass(), is(Participant.class));
  }


  @Override
  public void testPersistentConstructor() throws Exception {
    // no persistent constructor yet

  }

  @Override
  @Test
  public void testConstructorUsingDomain() throws Exception {

    Participant domain = new Participant(id, legacySourceTable, clientId, new LegacyDescriptor(),
        firstName, middleName, lastName, suffix, gender, ssn, dateOfBirth, primaryLanguage,
        secondaryLanguage, personId, screeningId, roles, addresses);

    assertThat(domain.getId(), is(equalTo(id)));
    assertThat(domain.getLegacySourceTable(), is(equalTo(legacySourceTable)));
    assertThat(domain.getLegacyId(), is(equalTo(clientId)));
    assertThat(domain.getScreeningId(), is(equalTo(screeningId)));
    assertThat(domain.getFirstName(), is(equalTo(firstName)));
    assertThat(domain.getMiddleName(), is(equalTo(middleName)));
    assertThat(domain.getLastName(), is(equalTo(lastName)));
    assertThat(domain.getNameSuffix(), is(equalTo(suffix)));
    assertThat(domain.getGender(), is(equalTo(gender)));
    assertThat(domain.getDateOfBirth(), is(equalTo(dateOfBirth)));
    assertThat(domain.getSsn(), is(equalTo(ssn)));
    assertThat(domain.getRoles(), is(equalTo(roles)));
    assertThat(domain.getAddresses(), is(equalTo(addresses)));
  }

  @Test
  public void shouldNotHaveValidationErrorForValidGenders() {
    List<String> acceptableGenders = Arrays.asList("M", "F", "U");
    acceptableGenders.forEach(gender -> {
      Participant participant =
          new ParticipantResourceBuilder().setGender(gender).createParticipant();
      String errorMessage = "Expected no validation error for gender value: " + gender;
      assertEquals(errorMessage, 0, validator.validate(participant).size());
    });
  }

  @Test
  public void shouldHaveValidationErrorsForInvaldGenders() {
    List<String> acceptableGenders = Arrays.asList("q", "1", "6", null, "Male", "Female", "O");
    acceptableGenders.forEach(gender -> {
      Participant participant =
          new ParticipantResourceBuilder().setGender(gender).createParticipant();
      String errorMessage = "Expected a validation error for gender value: " + gender;
      assertEquals(errorMessage, 1, validator.validate(participant).size());
    });

  }

  @Test
  public void testBlankLegacySourceTableSuccess() throws Exception {
    Participant toValidate =
        MAPPER.readValue(fixture("fixtures/domain/participant/valid/blankLegacySourceTable.json"),
            Participant.class);

    Set<ConstraintViolation<Participant>> constraintViolations = validator.validate(toValidate);
    assertEquals(0, constraintViolations.size());
  }

  @Test
  public void testNullLegacySourceTableSuccess() throws Exception {
    Participant toValidate = MAPPER.readValue(
        fixture("fixtures/domain/participant/valid/nullLegacySourceTable.json"), Participant.class);

    Set<ConstraintViolation<Participant>> constraintViolations = validator.validate(toValidate);
    assertEquals(0, constraintViolations.size());
  }

  @Test
  public void testMissingLegacySourceTableSuccess() throws Exception {
    Participant toValidate =
        MAPPER.readValue(fixture("fixtures/domain/participant/valid/missingLegacySourceTable.json"),
            Participant.class);
    Set<ConstraintViolation<Participant>> constraintViolations = validator.validate(toValidate);
    assertEquals(0, constraintViolations.size());
  }

  @Test
  public void testWithEmptyClientIdSuccess() throws Exception {

    Participant toValidate = MAPPER.readValue(
        fixture("fixtures/domain/participant/valid/validEmptyClientId.json"), Participant.class);
    Set<ConstraintViolation<Participant>> constraintViolations = validator.validate(toValidate);
    assertEquals(0, constraintViolations.size());
  }

  @Test
  public void testWithNullClientIdSuccess() throws Exception {
    Participant toValidate = MAPPER.readValue(
        fixture("fixtures/domain/participant/valid/nullClientId.json"), Participant.class);
    Set<ConstraintViolation<Participant>> constraintViolations = validator.validate(toValidate);
    assertEquals(0, constraintViolations.size());
  }

  @Test
  public void testWithMissingClientIdSuccess() throws Exception {
    Participant toValidate = MAPPER.readValue(
        fixture("fixtures/domain/participant/valid/missingClientId.json"), Participant.class);
    Set<ConstraintViolation<Participant>> constraintViolations = validator.validate(toValidate);
    assertEquals(0, constraintViolations.size());
  }

  @Test
  public void testLegacyIdTooLongFail() throws Exception {
    Participant toValidate = MAPPER.readValue(
        fixture("fixtures/domain/participant/invalid/legacyIdTooLong.json"), Participant.class);
    Set<ConstraintViolation<Participant>> constraintViolations = validator.validate(toValidate);
    assertEquals(1, constraintViolations.size());
    assertEquals("size must be between 0 and 10",
        constraintViolations.iterator().next().getMessage());
    // System.out.println(constraintViolations.iterator().next().getMessage());
  }

  @Test
  public void testParticipantSsnTooLongFail() throws Exception {
    Participant toValidate = MAPPER.readValue(
        fixture("fixtures/domain/participant/invalid/ssnTooLong.json"), Participant.class);
    Set<ConstraintViolation<Participant>> constraintViolations = validator.validate(toValidate);
    assertEquals(1, constraintViolations.size());
    assertEquals("ssn size must be between 1 and 9 or assign the value to defalut 0",
        constraintViolations.iterator().next().getMessage());

  }

  @Test
  public void testIsVictim() throws IOException {
    Set<String> roles = new HashSet<String>(Arrays.asList("Victim"));
    Participant participant = createParticipantWithRoles(roles);
    assertTrue("Expected participant with a victim role to be a victim", participant.isVictim());
  }

  @Test
  public void testParticipantIsNotAVictimWhenVictimIsNotInRole() throws IOException {
    Set<String> roles = new HashSet<String>();
    Participant participant = createParticipantWithRoles(roles);
    assertFalse("Expected participant with out a victim role not to be a victim",
        participant.isVictim());
  }

  @Test
  public void testIsReporter() throws IOException {
    Set<String> roles = new HashSet<String>(Arrays.asList("Mandated Reporter"));
    Participant participant = createParticipantWithRoles(roles);
    assertTrue("Expected participant with a reporter role to be a reporter",
        participant.isReporter());
  }

  @Test
  public void testParticipantIsNotAReporterWhenReportersNotInRole() throws IOException {
    Set<String> roles = new HashSet<String>();
    Participant participant = createParticipantWithRoles(roles);
    assertFalse("Expected participant with out a reporter role not to be a reporter",
        participant.isReporter());
  }

  @Test
  public void testIsPerpetrator() throws IOException {
    Set<String> roles = new HashSet<String>(Arrays.asList("Perpetrator"));
    Participant participant = createParticipantWithRoles(roles);
    assertTrue("Expected participant with a perpetrator role to be a perpetrator",
        participant.isPerpetrator());
  }

  @Test
  public void testParticipantIsNotAReporterWhenPerpetratorNotInRole() throws IOException {
    Set<String> roles = new HashSet<String>();
    Participant participant = createParticipantWithRoles(roles);
    assertFalse("Expected participant with out a perpetrator role not to be a perpetrator",
        participant.isPerpetrator());
  }

  public void testParticipantDateOfBirthInvalidFail() throws Exception {
    Participant toValidate = MAPPER.readValue(
        fixture("fixtures/domain/participant/invalid/dateOfBirthInvalid.json"), Participant.class);
    Set<ConstraintViolation<Participant>> constraintViolations = validator.validate(toValidate);
    assertEquals(1, constraintViolations.size());
    assertEquals("must be in the format of yyyy-MM-dd",
        constraintViolations.iterator().next().getMessage());
  }

  @Test
  public void testGenderInvalidFail() throws Exception {
    Participant toValidate = MAPPER.readValue(
        fixture("fixtures/domain/participant/invalid/genderInvalid.json"), Participant.class);
    Set<ConstraintViolation<Participant>> constraintViolations = validator.validate(toValidate);
    assertEquals(1, constraintViolations.size());
    assertEquals("must be one of [M, F, U]", constraintViolations.iterator().next().getMessage());
  }

  @Test
  public void shouldAddListOfAddressesToExistingAddresses() {
    Address existingAddress = new AddressResourceBuilder().setLegacyId("1").createAddress();
    Set<Address> existingAddresses = new HashSet(Arrays.asList(existingAddress));
    Participant participant =
        new ParticipantResourceBuilder().setAddresses(existingAddresses).createParticipant();

    Address newAddress = new AddressResourceBuilder().setLegacyId("2").createAddress();
    Set<Address> newAddresses = new HashSet(Arrays.asList(newAddress));
    participant.addAddresses(newAddresses);
    assertEquals("Expected 2 addresses", 2, participant.getAddresses().size());
    assertEquals("Expected 2 addresses", 2, participant.getAddresses().size());
    assertTrue("Expected to find existing Address",
        participant.getAddresses().contains(existingAddress));
    assertTrue("Expected to find new Address", participant.getAddresses().contains(newAddress));
  }

  @Test
  public void shouldReturnNewLegacyDescriptorWhenItHasANullValue() {
    Participant participant =
        new ParticipantResourceBuilder().setLegacyDescriptor(null).createParticipant();
    assertNotNull(participant.getLegacyDescriptor());

  }

  private Participant createParticipantWithRoles(Set<String> roles) {
    return createParticipant(roles);

  }

  private Participant validParticipant() {
    return createParticipant(roles);
  }

  private Participant createParticipant(Set<String> roles) {
    Participant validParticipant = null;
    try {
      validParticipant = new Participant(id, legacySourceTable, clientId, new LegacyDescriptor(),
          firstName, middleName, lastName, suffix, gender, ssn, dateOfBirth, primaryLanguage,
          secondaryLanguage, personId, screeningId, roles, addresses);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return validParticipant;

  }

}

