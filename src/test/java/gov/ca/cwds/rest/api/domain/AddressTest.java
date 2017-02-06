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
public class AddressTest {

  private String street_name = "123 Main";
  private String city = "Sacramento";
  private String state = "CA";
  private Integer zip = 95757;

  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

  /*
   * Serialization and de-serialization
   */
  @Test
  public void serializesToJSON() throws Exception {
    String expected = MAPPER.writeValueAsString(new Address("123 Main", "Sacramento", "CA", 95757));

    String serialized = MAPPER.writeValueAsString(
        MAPPER.readValue(fixture("fixtures/domain/address/valid/valid.json"), Address.class));

    assertThat(serialized, is(expected));
  }

  @Test
  public void deserializesFromJSON() throws Exception {
    Address expected = new Address("123 Main", "Sacramento", "CA", 95757);
    Address serialized =
        MAPPER.readValue(fixture("fixtures/domain/address/valid/valid.json"), Address.class);
    assertThat(serialized, is(expected));
  }

  @Test
  public void equalsHashCodeWork() throws Exception {
    EqualsVerifier.forClass(Address.class).suppress(Warning.NONFINAL_FIELDS).verify();
  }

  @Test
  public void persistentObjectConstructorTest() throws Exception {
    Address domain = this.validAddress();

    gov.ca.cwds.data.persistence.ns.Address persistent =
        new gov.ca.cwds.data.persistence.ns.Address(domain, "12345", "12345");

    Address totest = new Address(persistent);
    assertThat(totest.getCity(), is(equalTo(persistent.getCity())));
    assertThat(totest.getState(), is(equalTo(persistent.getState())));
    assertThat(totest.getStreet_address(), is(equalTo(persistent.getStreetAddress())));
    assertThat(totest.getZip(), is(equalTo(persistent.getZip())));
  }

  @Test
  public void jsonCreatorConstructorTest() throws Exception {
    Address domain = new Address(street_name, city, state, zip);

    assertThat(domain.getCity(), is(equalTo(city)));
    assertThat(domain.getState(), is(equalTo(state)));
    assertThat(domain.getStreet_address(), is(equalTo(street_name)));
    assertThat(domain.getZip(), is(equalTo(zip)));
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
