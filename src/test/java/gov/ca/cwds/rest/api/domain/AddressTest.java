package gov.ca.cwds.rest.api.domain;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import gov.ca.cwds.data.cms.TestSystemCodeCache;
import io.dropwizard.jackson.Jackson;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

/**
 * @author CWDS API Team
 *
 */
@SuppressWarnings("javadoc")
public class AddressTest {

  private String street_name = "123 Main";
  private String city = "Sacramento";
  private String state = "CA";
  private Integer zip = 95757;
  private Integer type = 32; // "Residence"
  private String legacySourceTable = "CLIENT_T";
  private String legacyId = "1234567ABC";

  private Validator validator;

  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

  private TestSystemCodeCache testSystemCodeCache = new TestSystemCodeCache();

  @Before
  public void setup() {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();

    MAPPER.configure(SerializationFeature.INDENT_OUTPUT, true);
  }

  /*
   * Serialization and de-serialization
   */
  @Test
  public void serializesToJSON() throws Exception {
    String expected =
        MAPPER.writeValueAsString(new Address("", "", "123 Main", "Sacramento", "CA", 95757, 32));

    String serialized = MAPPER.writeValueAsString(
        MAPPER.readValue(fixture("fixtures/domain/address/valid/valid.json"), Address.class));

    assertThat(serialized, is(expected));
  }

  @Test
  public void testDeserializesFromJSON() throws Exception {
    Address expected = new Address("", "", "123 Main", "Sacramento", "CA", 95757, 32);

    Address serialized =
        MAPPER.readValue(fixture("fixtures/domain/address/valid/valid.json"), Address.class);
    assertThat(serialized, is(expected));

  }

  @Test
  public void equalsHashCodeWork() throws Exception {
    EqualsVerifier.forClass(Address.class).suppress(Warning.NONFINAL_FIELDS, Warning.NULL_FIELDS)
        .withIgnoredFields("messages").verify();
  }

  @Test
  public void persistentObjectConstructorTest() throws Exception {
    Address domain = this.validAddress();

    gov.ca.cwds.data.persistence.ns.Address persistent =
        new gov.ca.cwds.data.persistence.ns.Address(domain, "12345", "12345");

    Address totest = new Address(persistent);
    assertThat(totest.getCity(), is(equalTo(persistent.getCity())));
    assertThat(totest.getState(), is(equalTo(persistent.getState())));
    assertThat(totest.getStreetAddress(), is(equalTo(persistent.getStreetAddress())));
    assertThat(totest.getZip(), is(equalTo(persistent.getZip())));
    assertThat(totest.getType(), is(equalTo(persistent.getType())));
  }

  @Test
  public void testJSONConstructorTest() throws Exception {
    Address domain = new Address("CLIENT_T", "1234567ABC", street_name, city, state, zip, type);

    assertThat(domain.getCity(), is(equalTo(city)));
    assertThat(domain.getState(), is(equalTo(state)));
    assertThat(domain.getStreetAddress(), is(equalTo(street_name)));
    assertThat(domain.getZip(), is(equalTo(zip)));
    assertThat(domain.getType(), is(equalTo(type)));
    assertThat(domain.getLegacySourceTable(), is(equalTo(legacySourceTable)));
    assertThat(domain.getLegacyId(), is(equalTo(legacyId)));
  }

  @Test
  // R - 03232 Zip codes are five digits
  public void testZipCodeTooLongFail() throws Exception {
    Address toValidate =
        MAPPER.readValue(fixture("fixtures/domain/address/invalid/zipTooLong.json"), Address.class);
    Set<ConstraintViolation<Address>> constraintViolations = validator.validate(toValidate);
    System.out.println(constraintViolations.iterator().next().getMessage());
    assertEquals(1, constraintViolations.size());
    assertEquals("must be less than or equal to 99999",
        constraintViolations.iterator().next().getMessage());
  }

  @Test
  public void testBlankLegacySourceTableSuccess() throws Exception {
    Address toValidate = MAPPER.readValue(
        fixture("fixtures/domain/address/valid/blankLegacySourceTable.json"), Address.class);
    Set<ConstraintViolation<Address>> constraintViolations = validator.validate(toValidate);
    assertEquals(0, constraintViolations.size());

  }

  @Test
  public void testNullLegacySourceTableSuccess() throws Exception {
    Address toValidate = MAPPER.readValue(
        fixture("fixtures/domain/address/valid/nullLegacySourceTable.json"), Address.class);
    Set<ConstraintViolation<Address>> constraintViolations = validator.validate(toValidate);
    assertEquals(0, constraintViolations.size());

  }

  @Test
  public void testMissingLegacySourceTableSuccess() throws Exception {
    Address toValidate = MAPPER.readValue(
        fixture("fixtures/domain/address/valid/missingLegacySourceTable.json"), Address.class);
    Set<ConstraintViolation<Address>> constraintViolations = validator.validate(toValidate);
    assertEquals(0, constraintViolations.size());
  }

  @Test
  public void testBlankLegacyIdSuccess() throws Exception {
    Address toValidate = MAPPER
        .readValue(fixture("fixtures/domain/address/valid/blankLegacyId.json"), Address.class);
    Set<ConstraintViolation<Address>> constraintViolations = validator.validate(toValidate);
    assertEquals(0, constraintViolations.size());
  }


  @Test
  public void testNullLegacyIdSuccess() throws Exception {
    Address toValidate =
        MAPPER.readValue(fixture("fixtures/domain/address/valid/nullLegacyId.json"), Address.class);
    Set<ConstraintViolation<Address>> constraintViolations = validator.validate(toValidate);
    assertEquals(0, constraintViolations.size());
  }

  @Test
  public void testMissingLegacyIdSuccess() throws Exception {
    Address toValidate = MAPPER
        .readValue(fixture("fixtures/domain/address/valid/missingLegacyId.json"), Address.class);
    Set<ConstraintViolation<Address>> constraintViolations = validator.validate(toValidate);
    assertEquals(0, constraintViolations.size());
  }

  @Test
  public void testLegacyIdTooLongFail() throws Exception {
    Address toValidate = MAPPER
        .readValue(fixture("fixtures/domain/address/invalid/legacyIdTooLong.json"), Address.class);
    Set<ConstraintViolation<Address>> constraintViolations = validator.validate(toValidate);
    assertEquals(1, constraintViolations.size());
    assertEquals("size must be between 0 and 10",
        constraintViolations.iterator().next().getMessage());
  }

  private Address validAddress() {

    try {
      Address validAddress =
          MAPPER.readValue(fixture("fixtures/domain/address/valid/valid.json"), Address.class);

      return validAddress;

    } catch (JsonParseException e) {
      e.printStackTrace();
      return null;
    } catch (JsonMappingException e) {
      e.printStackTrace();
      return null;
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }
}
