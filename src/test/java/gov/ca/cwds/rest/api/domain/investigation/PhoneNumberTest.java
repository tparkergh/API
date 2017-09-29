package gov.ca.cwds.rest.api.domain.investigation;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;

import org.joda.time.DateTime;
import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

@SuppressWarnings("javadoc")
public class PhoneNumberTest {

  private BigDecimal phone = new BigDecimal(4345677);
  private BigDecimal phone1 = new BigDecimal(3345789);
  private Integer phoneExtension = 1123;
  private Short phoneType = 1111;
  private DateTime dateTime = new DateTime("2010-10-01T15:26:42.000-0700");
  private CmsRecordDescriptor cmsRecordDescriptor =
      new CmsRecordDescriptor("1234567ABC", "111-222-333-4444333", dateTime, "CLIENT_T", "Client");

  @Test
  public void testEmptyConstructorSuccess() {
    PhoneNumber phoneNumber = new PhoneNumber();
    assertNotNull(phoneNumber);
  }

  @Test
  public void testDomainConstructorSuccess() {
    PhoneNumber phoneNumber =
        new PhoneNumber(phone, phoneExtension, phoneType, cmsRecordDescriptor);
    assertThat(phoneNumber.getPhoneNumber(), is(equalTo(phone)));
    assertThat(phoneNumber.getPhoneExtension(), is(equalTo(phoneExtension)));
    assertThat(phoneNumber.getPhoneType(), is(equalTo(phoneType)));
    assertThat(phoneNumber.getCmsRecordDescriptor(), is(equalTo(cmsRecordDescriptor)));
  }

  @Test
  public void shouldCompareEqualsToObjectWithSameValues() {
    PhoneNumber phoneNumber =
        new PhoneNumber(phone, phoneExtension, phoneType, cmsRecordDescriptor);
    PhoneNumber phoneNumber1 =
        new PhoneNumber(phone, phoneExtension, phoneType, cmsRecordDescriptor);
    assertEquals(phoneNumber, phoneNumber1);
  }

  @Test
  public void shouldCompareNotEqualsToObjectWithDifferentValues() {
    PhoneNumber phoneNumber =
        new PhoneNumber(phone, phoneExtension, phoneType, cmsRecordDescriptor);
    PhoneNumber phoneNumber1 =
        new PhoneNumber(phone1, phoneExtension, phoneType, cmsRecordDescriptor);
    assertThat(phone, is(not(equals(phoneNumber1))));
  }

  @Test
  public void equalsHashCodeWork() {
    EqualsVerifier.forClass(PhoneNumber.class).suppress(Warning.NONFINAL_FIELDS).verify();
  }

}
