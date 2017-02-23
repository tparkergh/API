package gov.ca.cwds.rest.api.domain;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.io.IOException;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.dropwizard.jackson.Jackson;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

/**
 * @author CWDS API Team
 *
 */
@SuppressWarnings("javadoc")
public class LanguageTest {

  private String language = "English";

  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

  /*
   * Serialization and de-serialization
   */
  @Test
  public void serializesToJSON() throws Exception {
    String expected = MAPPER.writeValueAsString(new Language("English"));

    String serialized = MAPPER.writeValueAsString(
        MAPPER.readValue(fixture("fixtures/domain/language/valid/valid.json"), Language.class));

    assertThat(serialized, is(expected));
  }

  @Test
  public void testDeserializesFromJSON() throws Exception {
    Language expected = new Language("English");

    Language serialized =
        MAPPER.readValue(fixture("fixtures/domain/language/valid/valid.json"), Language.class);
    assertThat(serialized, is(expected));

  }

  @Test
  public void equalsHashCodeWork() throws Exception {
    EqualsVerifier.forClass(Language.class).suppress(Warning.NONFINAL_FIELDS).verify();
  }

  @Test
  public void persistentObjectConstructorTest() throws Exception {
    Language domain = this.validLanguage();

    gov.ca.cwds.data.persistence.ns.Language persistent =
        new gov.ca.cwds.data.persistence.ns.Language(domain, "lastUpdatedId", "createUserId");

    Language totest = new Language(persistent);

    assertThat(totest.getLanguage(), is(equalTo(persistent.getLanguageCodeId())));
  }

  @Test
  public void testJSONConstructorTest() throws Exception {
    Language domain = new Language(language);

    assertThat(domain.getLanguage(), is(equalTo(language)));
  }

  private Language validLanguage() {

    try {
      Language validLanguage =
          MAPPER.readValue(fixture("fixtures/domain/language/valid/valid.json"), Language.class);

      return validLanguage;

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
