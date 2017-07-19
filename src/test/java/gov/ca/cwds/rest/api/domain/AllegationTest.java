package gov.ca.cwds.rest.api.domain;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;

import gov.ca.cwds.data.cms.TestSystemCodeCache;
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

import io.dropwizard.jackson.Jackson;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

/**
 * @author CWDS API Team
 */
@SuppressWarnings("javadoc")
public class AllegationTest {

  private long victimPersonId = 1;
  private long perpetratorPersonId = 2;
  private Short type = 2179;
  private String county = "Sacramento";
  private String legacySourceTable = "ALLGTN_T";
  private String legacyId = "1234567ABC";

  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();
  private Validator validator;

  TestSystemCodeCache testSystemCodeCache = new TestSystemCodeCache();

  private Short abuseCode = (short)2179;

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
    String expected = MAPPER.writeValueAsString(
        new Allegation("ALLGTN_T", "1234567ABC", 5432, 2, abuseCode, "Sacramento"));

    String serialized = MAPPER.writeValueAsString(
        MAPPER.readValue(fixture("fixtures/domain/Allegation/valid/valid.json"), Allegation.class));

    assertThat(serialized, is(expected));
  }


  @Test
  public void testDeserializesFromJSON() throws Exception {
    Allegation expected =
        new Allegation("ALLGTN_T", "1234567ABC", 5432, 2, abuseCode, "Sacramento");

    Allegation serialized =
        MAPPER.readValue(fixture("fixtures/domain/Allegation/valid/valid.json"), Allegation.class);
    assertThat(serialized, is(expected));

  }

  @Test
  public void equalsHashCodeWork() throws Exception {
    EqualsVerifier.forClass(Allegation.class).suppress(Warning.NONFINAL_FIELDS, Warning.NULL_FIELDS)
        .withIgnoredFields("messages").verify();
  }

  @Test
  public void testDomainConstructorTest() throws Exception {
    Allegation domain = new Allegation(legacySourceTable, legacyId, victimPersonId,
        perpetratorPersonId, type, county);

    assertThat(domain.getVictimPersonId(), is(equalTo(victimPersonId)));
    assertThat(domain.getPerpetratorPersonId(), is(equalTo(perpetratorPersonId)));
    assertThat(domain.getType(), is(equalTo(type)));
    assertThat(domain.getCounty(), is(equalTo(county)));
    assertThat(domain.getLegacyId(), is(equalTo(legacyId)));
    assertThat(domain.getLegacySourceTable(), is(equalTo(legacySourceTable)));
  }

  @Test
  public void testBlankLegacySourceTableSuccess() throws Exception {
    Allegation toValidate = MAPPER.readValue(
        fixture("fixtures/domain/Allegation/valid/blankLegacySourceTable.json"), Allegation.class);

    Set<ConstraintViolation<Allegation>> constraintViolations = validator.validate(toValidate);
    assertEquals(0, constraintViolations.size());
  }

  @Test
  public void testNullLegacySourceTableSuccess() throws Exception {
    Allegation toValidate = MAPPER.readValue(
        fixture("fixtures/domain/Allegation/valid/nullLegacySourceTable.json"), Allegation.class);

    Set<ConstraintViolation<Allegation>> constraintViolations = validator.validate(toValidate);
    // System.out.println(constraintViolations.iterator().next().getMessage());
    assertEquals(0, constraintViolations.size());
    // assertEquals(
    // "may not be null",
    // constraintViolations.iterator().next().getMessage()
  }

  @Test
  public void testMissingLegacySourceTableSuccess() throws Exception {
    Allegation toValidate =
        MAPPER.readValue(fixture("fixtures/domain/Allegation/valid/missingLegacySourceTable.json"),
            Allegation.class);
    Set<ConstraintViolation<Allegation>> constraintViolations = validator.validate(toValidate);
    assertEquals(0, constraintViolations.size());
  }

  @Test
  public void testBlankLegacyIdSuccess() throws Exception {
    Allegation toValidate = MAPPER.readValue(
        fixture("fixtures/domain/Allegation/valid/blankLegacyId.json"), Allegation.class);
    Set<ConstraintViolation<Allegation>> constraintViolations = validator.validate(toValidate);
    assertEquals(0, constraintViolations.size());
  }

  @Test
  public void testNullLegacyIdFail() throws Exception {
    Allegation toValidate = MAPPER
        .readValue(fixture("fixtures/domain/Allegation/valid/nullLegacyId.json"), Allegation.class);
    Set<ConstraintViolation<Allegation>> constraintViolations = validator.validate(toValidate);
    assertEquals(0, constraintViolations.size());

  }

  @Test
  public void testMissingLegacyIdSuccess() throws Exception {
    Allegation toValidate = MAPPER.readValue(
        fixture("fixtures/domain/Allegation/valid/missingLegacyId.json"), Allegation.class);
    Set<ConstraintViolation<Allegation>> constraintViolations = validator.validate(toValidate);
    assertEquals(0, constraintViolations.size());
  }

  @Test
  public void testLegacyIdTooLongFail() throws Exception {
    Allegation toValidate = MAPPER.readValue(
        fixture("fixtures/domain/Allegation/invalid/legacyIdTooLong.json"), Allegation.class);
    Set<ConstraintViolation<Allegation>> constraintViolations = validator.validate(toValidate);
    assertEquals(1, constraintViolations.size());
    assertEquals("size must be between 0 and 10",
        constraintViolations.iterator().next().getMessage());
    // System.out.println(constraintViolations.iterator().next().getMessage());
  }

  @SuppressWarnings("unused")
  private Allegation validAllegation() {
    try {
      Allegation validAllegation = MAPPER
          .readValue(fixture("fixtures/domain/Allegation/valid/valid.json"), Allegation.class);

      return validAllegation;

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


