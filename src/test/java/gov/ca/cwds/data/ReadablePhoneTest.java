package gov.ca.cwds.data;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import gov.ca.cwds.data.std.ApiPhoneAware.PhoneType;
import io.dropwizard.jackson.Jackson;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;



/**
 * @author CWDS API Team
 *
 */
@SuppressWarnings("javadoc")
public class ReadablePhoneTest {

  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

  private final String phoneNumber = "408 690-1234";
  private final String phoneNumberExtension = "987";
  private final PhoneType pt = PhoneType.Cell;

  @Test
  public void testConstructor() {
    ReadablePhone rp = new ReadablePhone(null, phoneNumber, phoneNumberExtension, pt);
    assertThat(rp.getPhoneNumber(), is(equalTo(phoneNumber)));
    assertThat(rp.getPhoneNumberExtension(), is(equalTo(phoneNumberExtension)));
    assertThat(rp.getPhoneType(), is(equalTo(pt)));
  }

  @Test
  public void serializesToJSON() throws Exception {
    final String expected = MAPPER.writeValueAsString(MAPPER
        .readValue(fixture("fixtures/data/ReadablePhone/valid/valid.json"), ReadablePhone.class));

    assertThat(MAPPER.writeValueAsString(validReadablePhone()), is(equalTo(expected)));
  }

  @Test
  public void deserializesFromJSON() throws Exception {
    ReadablePhone rp = validReadablePhone();

    assertThat(MAPPER.readValue(fixture("fixtures/data/ReadablePhone/valid/valid.json"),
        ReadablePhone.class), is(equalTo(rp)));
  }

  @Test
  public void equalsHashCodeWork() {
    EqualsVerifier.forClass(ReadablePhone.class).suppress(Warning.NONFINAL_FIELDS).verify();
  }

  private ReadablePhone validReadablePhone() {
    return new ReadablePhone(null, "408 690-1234", "987", PhoneType.Cell);
  }
}
