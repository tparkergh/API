package gov.ca.cwds.rest.api.domain;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;

import org.junit.After;
import org.junit.ClassRule;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.squarespace.jersey2.guice.JerseyGuiceUtils;

import gov.ca.cwds.rest.resources.ScreeningResource;
import gov.ca.cwds.rest.resources.cms.JerseyGuiceRule;
import io.dropwizard.jackson.Jackson;
import io.dropwizard.testing.junit.ResourceTestRule;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

/**
 * @author CWDS API Team
 *
 */
public class ScreeningReferenceTest {

  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

  private static final ScreeningResource resource = mock(ScreeningResource.class);

  @After
  public void ensureServiceLocatorPopulated() {
    JerseyGuiceUtils.reset();
  }

  @ClassRule
  public static JerseyGuiceRule rule = new JerseyGuiceRule();

  @ClassRule
  public static final ResourceTestRule resources =
      ResourceTestRule.builder().addResource(resource).build();

  /*
   * Serialization and deserialization
   */
  @SuppressWarnings("javadoc")
  @Test
  public void serializesToJSON() throws Exception {
    String expected = MAPPER.writeValueAsString(new ScreeningReference("foobar"));

    String serialized = MAPPER.writeValueAsString(MAPPER.readValue(
        fixture("fixtures/domain/screeningReference/valid/valid.json"), ScreeningReference.class));

    assertThat(serialized, is(expected));
  }

  @SuppressWarnings("javadoc")
  @Test
  public void deserializesFromJSON() throws Exception {
    ScreeningReference expected = new ScreeningReference("foobar");
    ScreeningReference serialized = MAPPER.readValue(
        fixture("fixtures/domain/screeningReference/valid/valid.json"), ScreeningReference.class);
    assertThat(serialized, is(expected));
  }

  @SuppressWarnings("javadoc")
  @Test
  public void equalsHashCodeWork() {
    EqualsVerifier.forClass(ScreeningReference.class)
            .suppress(Warning.NONFINAL_FIELDS)
            .withIgnoredFields("messages")
            .verify();
  }
}
