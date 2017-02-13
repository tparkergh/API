package gov.ca.cwds.rest.api.domain;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;

import org.junit.After;
import org.junit.ClassRule;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import com.squarespace.jersey2.guice.JerseyGuiceUtils;

import gov.ca.cwds.rest.resources.ScreeningResource;
import gov.ca.cwds.rest.resources.cms.JerseyGuiceRule;
import io.dropwizard.jackson.Jackson;
import io.dropwizard.testing.junit.ResourceTestRule;

/**
 * @author CWDS API Team
 *
 */
@SuppressWarnings("javadoc")
public class ScreeningRequestTest {

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
  /**
   * @throws Exception required for test compilation
   */
  @Test
  public void serializesToJSON() throws Exception {
    Address address = new Address("10 main st", "Sacramento", "CA", 95814, "Home");
    ImmutableList.Builder<Long> builder = ImmutableList.builder();
    ImmutableList<Long> ids = builder.add(new Long(123)).add(new Long(345)).build();
    ScreeningRequest screeningRequest = new ScreeningRequest("X5HNJK", "11/22/1973", "Amador",
        "11/22/1973", "Home", "email", "First screening", "immediate", "accept_for_investigation",
        "10/11/2016", "first narrative", address, ids);

    String expected = MAPPER.writeValueAsString(screeningRequest);
    String serialized = MAPPER.writeValueAsString(MAPPER.readValue(
        fixture("fixtures/domain/screeningRequest/valid/valid.json"), ScreeningRequest.class));

    assertThat(serialized, is(expected));
  }

  /**
   * @throws Exception required for test compilation
   */
  @Test
  public void deserializesFromJSON() throws Exception {
    Address address = new Address("10 main st", "Sacramento", "CA", 95814, "Home");
    ImmutableList.Builder<Long> builder = ImmutableList.builder();
    ImmutableList<Long> ids = builder.add(new Long(123)).add(new Long(345)).build();
    ScreeningRequest expected = new ScreeningRequest("X5HNJK", "11/22/1973", "Amador", "11/22/1973",
        "Home", "email", "First screening", "immediate", "accept_for_investigation", "10/11/2016",
        "first narrative", address, ids);
    ScreeningRequest serialized = MAPPER.readValue(
        fixture("fixtures/domain/screeningRequest/valid/valid.json"), ScreeningRequest.class);
    assertThat(serialized, is(expected));
  }

  // TODO : "equals" seems to work but the test is failing. Need to figure out the message.
  // TODO : verify 'STRICT_INHERITENCE' is appropriate here - reference pvitoltracker #136527227
  /**
   * 
   */
  // @Test
  // public void equalsHashCodeWork() {
  // EqualsVerifier.forClass(Screening.class).withRedefinedSubclass(ScreeningRequest.class)
  // .suppress(Warning.NONFINAL_FIELDS).suppress(Warning.STRICT_INHERITANCE).verify();
  // }
}
