package gov.ca.cwds.rest.api.domain.auth;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.dropwizard.jackson.Jackson;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

public class StaffUnitAuthorityTest {

  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

  private String unitAuthorityType = "Unitwide Read";
  private String assignedUnit = "ABC123";
  private String county = "Sacramento";


  /*
   * Constructor Tests
   */

  @Test
  public void jsonCreatorConstructorTest() throws Exception {

    StaffUnitAuthority domain = new StaffUnitAuthority("Unitwide Read", "ABC123", "Sacramento");

    assertThat(domain.getUnitAuthorityType(), is(equalTo(unitAuthorityType)));
    assertThat(domain.getAssignedUnit(), is(equalTo(assignedUnit)));
    assertThat(domain.getCounty(), is(equalTo(county)));
  }

  @Test
  public void serializesToJSON() throws Exception {
    final String expected = MAPPER.writeValueAsString(
        MAPPER.readValue(fixture("fixtures/domain/auth/StaffUnitAuthority/valid/valid.json"),
            StaffUnitAuthority.class));

    assertThat(MAPPER.writeValueAsString(validStaffUnitAuthority()), is(equalTo(expected)));
  }

  @Test
  public void deserializesFromJSON() throws Exception {
    assertThat(MAPPER.readValue(fixture("fixtures/domain/auth/StaffUnitAuthority/valid/valid.json"),
        StaffUnitAuthority.class), is(equalTo(validStaffUnitAuthority())));
  }

  @Test
  public void equalsHashCodeWork() {
    EqualsVerifier.forClass(UserAuthorization.class).suppress(Warning.NONFINAL_FIELDS).verify();
  }

  /*
   * Utils
   */
  private StaffUnitAuthority validStaffUnitAuthority() {
    return new StaffUnitAuthority("Unitwide Read", "ABC123", "Sacramento");
  }
}
