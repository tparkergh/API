package gov.ca.cwds.rest.api.domain;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;

import org.junit.After;
import org.junit.ClassRule;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.squarespace.jersey2.guice.JerseyGuiceUtils;

import gov.ca.cwds.rest.api.domain.junit.template.DomainTestTemplate;
import gov.ca.cwds.rest.resources.AddressValidationResource;
import gov.ca.cwds.rest.resources.cms.JerseyGuiceRule;
import io.dropwizard.jackson.Jackson;
import io.dropwizard.testing.junit.ResourceTestRule;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

/**
 * @author CWDS API Team
 *
 */
public class ValidatedAddressTest implements DomainTestTemplate {

  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

  private static final AddressValidationResource mockedAddressValidationResource =
      mock(AddressValidationResource.class);

  @After
  public void ensureServiceLocatorPopulated() {
    JerseyGuiceUtils.reset();
  }

  @ClassRule
  public static JerseyGuiceRule rule = new JerseyGuiceRule();

  @SuppressWarnings("javadoc")
  @ClassRule
  public static final ResourceTestRule resources =
      ResourceTestRule.builder().addResource(mockedAddressValidationResource).build();

  @Override
  public void setup() throws Exception {
    // TODO Auto-generated method stub

  }

  @Override
  public void teardown() throws Exception {
    // TODO Auto-generated method stub

  }

  /*
   * Serialization and de-serialization
   */
  @Override
  @Test
  public void testSerializesToJSON() throws Exception {
    String expected = MAPPER.writeValueAsString(new ValidatedAddress("9500 Kiefer Blvd",
        "Sacramento", "CA", 95827, -121.34332, 38.5445, true));
    String serialized = MAPPER.writeValueAsString(MAPPER.readValue(
        fixture("fixtures/domain/validatedAddress/valid/valid.json"), ValidatedAddress.class));

    assertThat(serialized, is(expected));
  }

  @Override
  @Test
  public void testDeserializesFromJSON() throws Exception {
    ValidatedAddress expected = new ValidatedAddress("9500 Kiefer Blvd", "Sacramento", "CA", 95827,
        -121.34332, 38.5445, true);
    ValidatedAddress serialized = MAPPER.readValue(
        fixture("fixtures/domain/validatedAddress/valid/valid.json"), ValidatedAddress.class);
    assertThat(serialized, is(expected));
  }

  @Override
  @Test
  public void testEqualsHashCodeWorks() throws Exception {
    EqualsVerifier.forClass(ValidatedAddress.class).suppress(Warning.NONFINAL_FIELDS).verify();
  }

  @SuppressWarnings("javadoc")
  @Test
  public void TestDomainConstructor() throws Exception {
    ValidatedAddress domain = new ValidatedAddress("9500 Kiefer Blvd", "Sacramento", "CA", 95827,
        -121.34332, 38.5445, true);

    assertThat(domain.getCity(), is(equalTo("Sacramento")));
    assertThat(domain.getState(), is(equalTo("CA")));
    assertThat(domain.getStreetAddress(), is(equalTo("9500 Kiefer Blvd")));
    assertThat(domain.getZip(), is(equalTo(95827)));
    assertThat(domain.getLongitude(), is(equalTo(-121.34332)));
    assertThat(domain.getLattitude(), is(equalTo(38.5445)));
    assertThat(domain.getDeliverable(), is(equalTo(true)));
  }

  @Override
  public void testPersistentConstructor() throws Exception {
    //

  }

  @Override
  public void testJSONCreatorConstructor() throws Exception {
    //

  }

  @Override
  public void testSuccessWithValid() throws Exception {
    //

  }

  @Override
  public void testSuccessWithOptionalsNotIncluded() throws Exception {
    //

  }

}
