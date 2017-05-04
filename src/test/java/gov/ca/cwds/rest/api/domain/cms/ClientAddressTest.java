package gov.ca.cwds.rest.api.domain.cms;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.squarespace.jersey2.guice.JerseyGuiceUtils;

import gov.ca.cwds.rest.resources.cms.JerseyGuiceRule;
import gov.ca.cwds.rest.resources.cms.ReporterResource;
import io.dropwizard.jackson.Jackson;
import io.dropwizard.testing.junit.ResourceTestRule;

/**
 * 
 * @author CWDS API Team
 * 
 */
public class ClientAddressTest {

  private static final ReporterResource mockedReporterResource = mock(ReporterResource.class);

  @After
  public void ensureServiceLocatorPopulated() {
    JerseyGuiceUtils.reset();
  }

  @ClassRule
  public static JerseyGuiceRule rule = new JerseyGuiceRule();

  @ClassRule
  public static final ResourceTestRule resources =
      ResourceTestRule.builder().addResource(mockedReporterResource).build();

  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

  private Short addressType = 32;
  private String bookingOrInmateId = "";
  private String effectiveEndDate = "";
  private String effectiveStartDate = "2017-04-04";
  private String addressId = "1234567ABC";
  private String clientId = "2345678ABC";
  private String referralId = "";
  private String homelessIndicator = "";

  @Before
  public void setup() throws Exception {}

  /*
   * Constructor Tests
   */
  @SuppressWarnings("javadoc")
  @Test
  public void testDomainConstructorSuccess() throws Exception {

    ClientAddress clientAddress = new ClientAddress(addressType, bookingOrInmateId,
        effectiveEndDate, effectiveStartDate, addressId, clientId, homelessIndicator, referralId);

    assertThat(clientAddress.getAddressType(), is(equalTo(addressType)));
    assertThat(clientAddress.getBookingOrInmateId(), is(equalTo(bookingOrInmateId)));
    assertThat(clientAddress.getClientId(), is(equalTo(clientId)));
    assertThat(clientAddress.getAddressId(), is(equalTo(addressId)));
    MAPPER.configure(SerializationFeature.INDENT_OUTPUT, true);
    // try {
    // final String domainClientAddressString = MAPPER.writeValueAsString(clientAddress);
    // System.out.println(domainClientAddressString);
    // } catch (JsonProcessingException e) {
    // e.printStackTrace();
    // }

  }

  @SuppressWarnings("javadoc")
  @Test
  public void testPersistentConstructorSuccess() throws Exception {
    ClientAddress dca = validClientAddress();

    gov.ca.cwds.data.persistence.cms.ClientAddress pca =
        new gov.ca.cwds.data.persistence.cms.ClientAddress("1234567ABC", dca, "OX5");

    assertThat(dca.getAddressType(), is(equalTo(pca.getAddressType())));
    assertThat(dca.getBookingOrInmateId(), is(equalTo(pca.getBkInmtId())));
    assertThat(dca.getClientId(), is(equalTo(pca.getFkClient())));
    assertThat(dca.getAddressId(), is(equalTo(pca.getFkAddress())));
    assertThat("OX5", is(equalTo(pca.getLastUpdatedId())));

  }

  @SuppressWarnings("javadoc")
  @Test
  public void serializesToJSON() throws Exception {
    final String expected = MAPPER.writeValueAsString(MAPPER.readValue(
        fixture("fixtures/domain/legacy/ClientAddress/valid/validClientAddress.json"),
        ClientAddress.class));

    assertThat(MAPPER.writeValueAsString(validClientAddress()), is(equalTo(expected)));
  }

  @SuppressWarnings("javadoc")
  @Test
  public void deserializesFromJSON() throws Exception {
    assertThat(MAPPER.readValue(
        fixture("fixtures/domain/legacy/ClientAddress/valid/validClientAddress.json"),
        ClientAddress.class), is(equalTo(validClientAddress())));
  }

  // @SuppressWarnings("javadoc")
  // @Test
  // public void equalsHashCodeWork() {
  // EqualsVerifier.forClass(ClientAddress.class)
  // .suppress(Warning.NONFINAL_FIELDS)
  // .withIgnoredFields("messages")
  // .verify();
  // }

  private ClientAddress validClientAddress()
      throws JsonParseException, JsonMappingException, IOException {

    ClientAddress ca = MAPPER.readValue(
        fixture("fixtures/domain/legacy/ClientAddress/valid/validClientAddress.json"),
        ClientAddress.class);
    return ca;

  }

}
