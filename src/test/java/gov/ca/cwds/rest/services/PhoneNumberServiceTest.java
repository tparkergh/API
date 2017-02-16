package gov.ca.cwds.rest.services;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.apache.commons.lang3.NotImplementedException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import gov.ca.cwds.data.ns.PhoneNumberDao;
import gov.ca.cwds.rest.api.domain.PhoneNumber;
import gov.ca.cwds.rest.api.domain.PostedPhoneNumber;
import gov.ca.cwds.rest.services.junit.template.ServiceTestTemplate;

/**
 * @author CWDS API Team
 *
 */
@SuppressWarnings("unused")
public class PhoneNumberServiceTest implements ServiceTestTemplate {

  private PhoneNumberService phoneNumberService;

  private PhoneNumberDao phoneNumberDao;

  @SuppressWarnings("javadoc")
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Override
  @Before
  public void setup() throws Exception {
    phoneNumberDao = mock(PhoneNumberDao.class);
    phoneNumberService = new PhoneNumberService(phoneNumberDao);
  }

  @Override
  public void testFindThrowsAssertionError() throws Exception {
    thrown.expect(AssertionError.class);
    try {
      phoneNumberService.find("nonLong");
    } catch (AssertionError e) {
      assertEquals("Expected AssertionError", e.getMessage());
    }


  }

  @Test
  @Override
  public void testFindReturnsCorrectEntity() throws Exception {
    when(phoneNumberDao.find(new Long(1)))
        .thenReturn(new gov.ca.cwds.data.persistence.ns.PhoneNumber(1L, "408 987-6543", "Home"));

    PhoneNumber expected = new PhoneNumber("408 987-6543", "Home");

    PhoneNumber found = phoneNumberService.find(new Long(1));

    assertThat(found, is(expected));

  }

  @Test
  @Override
  public void testFindReturnsNullWhenNotFound() throws Exception {
    when(phoneNumberDao.find(new Long(-1))).thenReturn(null);
    PhoneNumber found = phoneNumberService.find(new Long(-1));

    assertThat(found, is(nullValue()));

  }

  @Override
  public void testFindThrowsNotImplementedException() throws Exception {

  }

  @Test
  @Override
  public void testDeleteThrowsAssertionError() throws Exception {
    thrown.expect(AssertionError.class);
    try {
      phoneNumberService.delete("nonLong");
    } catch (AssertionError e) {
      assertEquals("Expected AssertionError", e.getMessage());
    }

  }

  @Override
  public void testDeleteDelegatesToCrudsService() throws Exception {

  }

  @Test
  @Override
  public void testDeleteReturnsNullWhenNotFound() throws Exception {
    thrown.expect(NotImplementedException.class);
    phoneNumberService.delete(new Long(1));

  }

  @Override
  public void testDeleteThrowsNotImplementedException() throws Exception {

  }

  @Override
  public void testDeleteReturnsClass() throws Exception {

  }

  @Test
  @Override
  public void testUpdateThrowsAssertionError() throws Exception {
    thrown.expect(AssertionError.class);
    try {
      phoneNumberService.update(null, new PhoneNumber("408 987-6543", "Home"));
    } catch (AssertionError e) {
      assertEquals("Expected AssertionError", e.getMessage());
    }

  }

  @Test
  @Override
  public void testUpdateReturnsDomain() throws Exception {
    thrown.expect(NotImplementedException.class);

    phoneNumberService.update(1L, new PhoneNumber("408 987-6543", "Home"));

  }

  @Override
  public void testUpdateReturnsCorrectEntity() throws Exception {

  }

  @Override
  public void testUpdateThrowsServiceException() throws Exception {

  }

  @Override
  public void testUpdateThrowsNotImplementedException() throws Exception {

  }

  @Override
  public void testCreateThrowsAssertionError() throws Exception {
    thrown.expect(AssertionError.class);
    try {
      PostedPhoneNumber postedPhoneNumber = phoneNumberService.create(null);
    } catch (AssertionError e) {
      assertEquals("Expected AssertionError", e.getMessage());
    }

  }

  @Test
  @Override
  public void testCreateReturnsPostedClass() throws Exception {
    gov.ca.cwds.data.persistence.ns.PhoneNumber toCreate =
        new gov.ca.cwds.data.persistence.ns.PhoneNumber(1L, "408 987-6543", "Home");
    PhoneNumber request = new PhoneNumber(toCreate);

    when(phoneNumberDao.create(any(gov.ca.cwds.data.persistence.ns.PhoneNumber.class)))
        .thenReturn(toCreate);

    PostedPhoneNumber postedPhoneNumber = phoneNumberService.create(request);
    assertThat(postedPhoneNumber.getClass(), is(PostedPhoneNumber.class));

  }

  @Test
  @Override
  public void testCreateReturnsCorrectEntity() throws Exception {
    gov.ca.cwds.data.persistence.ns.PhoneNumber toCreate =
        new gov.ca.cwds.data.persistence.ns.PhoneNumber(10L, "408 987-6543", "Home");
    PhoneNumber request = new PhoneNumber(toCreate);

    when(phoneNumberDao.create(any(gov.ca.cwds.data.persistence.ns.PhoneNumber.class)))
        .thenReturn(toCreate);

    PostedPhoneNumber expected = new PostedPhoneNumber(10, "408 987-6543", "Home");
    PostedPhoneNumber returned = phoneNumberService.create(request);

    assertThat(returned, is(expected));

  }

  @Test
  @Override
  public void testCreateBlankIDError() throws Exception {
    thrown.expect(AssertionError.class);
    try {
      PostedPhoneNumber postedPhoneNumber = phoneNumberService.create(null);
    } catch (AssertionError e) {
      assertEquals("Expected AssertionError", e.getMessage());
    }

  }

  @Override
  public void testCreateNullIDError() throws Exception {

  }

  @Override
  public void testCreateEmptyIDError() throws Exception {

  }

  @Override
  public void testCreateThrowsNotImplementedException() throws Exception {

  }

  @SuppressWarnings("javadoc")
  @Test
  public void testCreateExistsError() throws Exception {

    gov.ca.cwds.data.persistence.ns.PhoneNumber toCreate =
        new gov.ca.cwds.data.persistence.ns.PhoneNumber((long) 1, "408 987-6543", "Home");
    PhoneNumber request = new PhoneNumber(toCreate);

    when(phoneNumberDao.create(any(gov.ca.cwds.data.persistence.ns.PhoneNumber.class)))
        .thenReturn(toCreate);

    PostedPhoneNumber expected = new PostedPhoneNumber(1, "408 987-6543", "Home");
    PostedPhoneNumber returned = phoneNumberService.create(request);

    assertThat(returned, is(expected));

  }

}
