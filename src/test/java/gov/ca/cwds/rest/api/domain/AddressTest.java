package gov.ca.cwds.rest.api.domain;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.squarespace.jersey2.guice.JerseyGuiceUtils;

import gov.ca.cwds.rest.core.Api;
import gov.ca.cwds.rest.resources.AddressResource;
import gov.ca.cwds.rest.resources.cms.JerseyGuiceRule;
import io.dropwizard.jackson.Jackson;
import io.dropwizard.testing.junit.ResourceTestRule;
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
  private String type = "Home";
  private String legacySourceTable = "CLIENT_T";
  private String legacyId = "1234567ABC";

  private static final String ROOT_RESOURCE = "/" + Api.RESOURCE_ADDRESSES + "/";;
  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

  private static final AddressResource mockedAddressResource = mock(AddressResource.class);

  @After
  public void ensureServiceLocatorPopulated() {
    JerseyGuiceUtils.reset();
  }

  @ClassRule
  public static JerseyGuiceRule rule = new JerseyGuiceRule();

  @ClassRule
  public static final ResourceTestRule resources =
      ResourceTestRule.builder().addResource(mockedAddressResource).build();

  @Before
  public void setup() {
    Address address = validAddress();
    when(mockedAddressResource.create(eq(address)))
        .thenReturn(Response.status(Response.Status.NO_CONTENT).entity(null).build());
    MAPPER.configure(SerializationFeature.INDENT_OUTPUT, true);
  }

  /*
   * Serialization and de-serialization
   */
  @Test
  public void serializesToJSON() throws Exception {
    String expected = MAPPER
        .writeValueAsString(new Address("", "", "123 Main", "Sacramento", "CA", 95757, "Home"));

    String serialized = MAPPER.writeValueAsString(
        MAPPER.readValue(fixture("fixtures/domain/address/valid/valid.json"), Address.class));

    assertThat(serialized, is(expected));
  }

  @Test
  public void testDeserializesFromJSON() throws Exception {
    Address expected = new Address("", "", "123 Main", "Sacramento", "CA", 95757, "Home");

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
    assertThat(domain.getAddressId(), is(equalTo(legacyId)));
  }

  @Test
  // R - 03232 Zip codes are five digits
  public void testZipCodeTooLongFail() throws Exception {
    Address toCreate =
        MAPPER.readValue(fixture("fixtures/domain/address/invalid/zipTooLong.json"), Address.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    // System.out.println(response.readEntity(String.class));
    assertThat(response.getStatus(), is(equalTo(422)));

  }

  @Test
  public void testBlankLegacySourceTableSuccess() throws Exception {
    Address toCreate = MAPPER.readValue(
        fixture("fixtures/domain/address/valid/blankLegacySourceTable.json"), Address.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    // System.out.println(response.readEntity(String.class));
    assertThat(response.getStatus(), is(equalTo(204)));

  }

  @Test
  public void testNullLegacySourceTableSuccess() throws Exception {
    Address toCreate = MAPPER.readValue(
        fixture("fixtures/domain/address/valid/nullLegacySourceTable.json"), Address.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    // System.out.println(response.readEntity(String.class));
    assertThat(response.getStatus(), is(equalTo(204)));

  }

  @Test
  public void testMissingLegacySourceTableSuccess() throws Exception {
    Address toCreate = MAPPER.readValue(
        fixture("fixtures/domain/address/valid/missingLegacySourceTable.json"), Address.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    // System.out.println(response.readEntity(String.class));
    assertThat(response.getStatus(), is(equalTo(204)));
    // response.readEntity(String.class).indexOf("anonymousReporterIndicator may not be missing"),
    // is(greaterThanOrEqualTo(0)));

  }

  @Test
  public void testBlankLegacyIdSuccess() throws Exception {
    Address toCreate = MAPPER.readValue(fixture("fixtures/domain/address/valid/blankLegacyId.json"),
        Address.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    // System.out.println(response.readEntity(String.class));
    assertThat(response.getStatus(), is(equalTo(204)));

  }


  @Test
  public void testNullLegacyIdFail() throws Exception {
    Address toCreate =
        MAPPER.readValue(fixture("fixtures/domain/address/valid/nullLegacyId.json"), Address.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    // System.out.println(response.readEntity(String.class));
    assertThat(response.getStatus(), is(equalTo(204)));

  }

  @Test
  public void testMissingLegacyIdSuccess() throws Exception {
    Address toCreate = MAPPER
        .readValue(fixture("fixtures/domain/address/valid/missingLegacyId.json"), Address.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    // System.out.println(response.readEntity(String.class));
    assertThat(response.getStatus(), is(equalTo(204)));

  }

  @Test
  public void testLegacyIdTooLongFail() throws Exception {
    Address toCreate = MAPPER
        .readValue(fixture("fixtures/domain/address/invalid/legacyIdTooLong.json"), Address.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("addressId size must be between 0 and 10"),
        is(greaterThanOrEqualTo(0)));

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
