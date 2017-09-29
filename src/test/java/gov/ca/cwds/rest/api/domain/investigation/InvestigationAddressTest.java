package gov.ca.cwds.rest.api.domain.investigation;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.joda.time.DateTime;
import org.junit.Test;

import gov.ca.cwds.fixture.investigation.InvestigationAddressEntityBuilder;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

@SuppressWarnings("javadoc")
public class InvestigationAddressTest {

  private String streetAddress = "741 Evergreen Ct";
  private String city = "Springfield";
  private Short state = 1828;
  private String zip = "95757";
  private Short type = 32;
  private DateTime now = new DateTime("2010-10-01T15:26:42.000-0700");
  private String id = "1234567ABC";
  private String tableName = "ADDRS_T";
  private CmsRecordDescriptor cmsRecordDescriptor =
      new CmsRecordDescriptor(id, "111-222-333-4444", now, tableName, "Address");

  @Test
  public void testEmptyConstructorSuccess() {
    InvestigationAddress investigationAddress = new InvestigationAddress();
    assertNotNull(investigationAddress);
  }

  @Test
  public void testDomainConstructorSuccess() {
    InvestigationAddress investigationAddress =
        new InvestigationAddress(cmsRecordDescriptor, streetAddress, city, state, zip, type);
    assertThat(cmsRecordDescriptor, is(equalTo(investigationAddress.getCmsRecordDescriptor())));
    assertThat(streetAddress, is(equalTo(investigationAddress.getStreetAddress())));
    assertThat(city, is(equalTo(investigationAddress.getCity())));
    assertThat(state, is(equalTo(investigationAddress.getState())));
    assertThat(type, is(equalTo(investigationAddress.getType())));

  }

  @Test
  public void shouldCompareEqualsToObjectWithSameValues() {
    InvestigationAddress investigationAddress = new InvestigationAddressEntityBuilder().build();
    InvestigationAddress otherInvestigationAddress =
        new InvestigationAddressEntityBuilder().build();
    assertEquals(investigationAddress, otherInvestigationAddress);

  }

  @Test
  public void shouldCompareNotEqualsToObjectWithDifferentValues() {
    InvestigationAddress investigationAddress = new InvestigationAddressEntityBuilder().build();
    InvestigationAddress otherInvestigationAddress =
        new InvestigationAddressEntityBuilder().setCity("Fresno").build();
    assertThat(investigationAddress, is(not(equals(otherInvestigationAddress))));
  }

  @Test
  public void equalsHashCodeWork() {
    EqualsVerifier.forClass(Relationship.class).suppress(Warning.NONFINAL_FIELDS).verify();
  }
}
