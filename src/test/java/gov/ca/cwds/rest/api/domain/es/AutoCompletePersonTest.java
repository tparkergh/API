package gov.ca.cwds.rest.api.domain.es;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.io.IOException;

import org.hamcrest.junit.ExpectedException;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squarespace.jersey2.guice.JerseyGuiceUtils;

import gov.ca.cwds.data.es.ElasticSearchPerson;
import gov.ca.cwds.rest.resources.cms.JerseyGuiceRule;
import io.dropwizard.jackson.Jackson;

/**
 * 
 * @author CWDS API Team
 *
 */
public class AutoCompletePersonTest {

  String id = "1234567ABC";
  String firstName = "Mike";
  String lastName = "Smith";
  String gender = "M";
  String birthDate = "2000-10-31";
  String ssn = "111122333";
  String sourceType = "gov.ca.cwds.data.persistence.cms.rep.ReplicatedClient";
  String sourceJson = null;
  String highlight = "{\"firstName\":\"<em>mik</em>e\", \"lastName\":\"sm<em>ith</em>\"}";


  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

  @Rule
  public ExpectedException thrown = ExpectedException.none();


  @After
  public void ensureServiceLocatorPopulated() {
    JerseyGuiceUtils.reset();
  }

  @ClassRule
  public static JerseyGuiceRule rule = new JerseyGuiceRule();

  @Before
  public void setup() {}

  @Test
  public void testConstuctorSuccess() {

    ElasticSearchPerson esp = validESP();

    AutoCompletePerson acp = new AutoCompletePerson(esp);
    assertThat(acp, notNullValue());

  }

  @Test
  public void testGenderMSuccess() throws JsonProcessingException {

    ElasticSearchPerson esp = validESP();

    AutoCompletePerson acp = new AutoCompletePerson(esp);

    assertThat(acp.getGender(), is(equalTo("Male")));

  }

  @Test
  public void testSerializeAutoCompletePersonFromJSON() throws Exception {

    ElasticSearchPerson esp = validESP();

    AutoCompletePerson expected = new AutoCompletePerson(esp);

    AutoCompletePerson serialized = MAPPER.readValue(
        fixture("fixtures/domain/es/autoCompletePerson/valid.json"), AutoCompletePerson.class);

    assertThat(serialized, is(expected));

  }

  @Test
  public void testDeserializeAutoCompletePersonToJSON() throws IOException {

    ElasticSearchPerson esp = validESP();

    AutoCompletePerson actual = new AutoCompletePerson(esp);

    final String expected = MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(
        (MAPPER.readValue(fixture("fixtures/domain/es/autoCompletePerson/valid.json"),
            AutoCompletePerson.class)));

    String pa = MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(actual);
    // System.out.println(pa);
    // System.out.println(expected);

    assertThat(pa).isEqualTo(expected);

  }

  @Test
  public void testUnkownGenderSuccess() {

    ElasticSearchPerson esp = validESP();
    esp.setGender("U");

    AutoCompletePerson acp = new AutoCompletePerson(esp);

    assertThat(acp.getGender(), is(equalTo("Unknown")));

  }

  private ElasticSearchPerson validESP() {

    ElasticSearchPerson esp = new ElasticSearchPerson(id, firstName, lastName, gender, birthDate,
        ssn, sourceType, sourceJson, highlight);

    return esp;
  }
}
