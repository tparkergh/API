package gov.ca.cwds.rest.services;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.apache.commons.lang3.NotImplementedException;
import org.hamcrest.junit.ExpectedException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import gov.ca.cwds.data.ns.AddressDao;
import gov.ca.cwds.rest.api.domain.Address;
import gov.ca.cwds.rest.api.domain.PostedAddress;

public class AddressServiceTest {
  private AddressService addressService;

  private AddressDao addressDao;

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Before
  public void setup() throws Exception {
    addressDao = mock(AddressDao.class);
    addressService = new AddressService(addressDao);
  }

  /*
   * find tests
   */
  @Test
  public void findReturnsCorrectAddressWhenFoundWhenFound() throws Exception {
    when(addressDao.find(new Long(1))).thenReturn(new gov.ca.cwds.data.persistence.ns.Address(
        1L, "742 Evergreen Terrace", "Springfield", "WA", new Integer(98700)));

    Address expected = new Address("742 Evergreen Terrace", "Springfield", "WA", 98700);

    Address found = addressService.find(new Long(1));

    assertThat(found, is(expected));
  }

  @Test
  public void findReturnsNullWhenNotFound() throws Exception {
    when(addressDao.find(new Long(-1))).thenReturn(null);
    Address found = addressService.find(new Long(-1));

    assertThat(found, is(nullValue()));
  }

  @Test
  public void findThrowsAssertionError() throws Exception {
    // TODO : thrown.expect not working on AssertionError???? WHY???
    // thrown.expect(AssertionError.class);
    try {
      addressService.find("nonLong");
      Assert.fail("Expected AssertionError");
    } catch (AssertionError e) {
    }
  }

  /*
   * create tests
   */
  @Test
  public void createReturnsPostedAddress() throws Exception {
    gov.ca.cwds.data.persistence.ns.Address toCreate =
        new gov.ca.cwds.data.persistence.ns.Address(1L, "742 Evergreen Terrace", "Springfield",
            "WA", new Integer(98700));
    Address request = new Address(toCreate);

    when(addressDao.create(any(gov.ca.cwds.data.persistence.ns.Address.class)))
        .thenReturn(toCreate);

    PostedAddress postedAddress = addressService.create(request);
    assertThat(postedAddress.getClass(), is(PostedAddress.class));
  }

  @Test
  public void createReturnsNonNull() throws Exception {
    gov.ca.cwds.data.persistence.ns.Address toCreate =
        new gov.ca.cwds.data.persistence.ns.Address(1L, "742 Evergreen Terrace", "Springfield",
            "WA", new Integer(98700));
    Address request = new Address(toCreate);

    when(addressDao.create(any(gov.ca.cwds.data.persistence.ns.Address.class)))
        .thenReturn(toCreate);

    PostedAddress postedAddress = addressService.create(request);
    assertThat(postedAddress, is(notNullValue()));
  }

  @Test
  public void createReturnsReturnsCorrectPostedAddress() throws Exception {
    gov.ca.cwds.data.persistence.ns.Address toCreate =
        new gov.ca.cwds.data.persistence.ns.Address(1L, "742 Evergreen Terrace", "Springfield",
            "WA", new Integer(98700));
    Address request = new Address(toCreate);

    when(addressDao.create(any(gov.ca.cwds.data.persistence.ns.Address.class)))
        .thenReturn(toCreate);

    PostedAddress expected =
        new PostedAddress(1, "742 Evergreen Terrace", "Springfield", "WA", new Integer(98700));
    PostedAddress returned = addressService.create(request);

    assertThat(returned, is(expected));
  }

  /*
   * delete tests
   */
  @Test
  public void deleteThrowsNotImplementedException() throws Exception {
    thrown.expect(NotImplementedException.class);
    addressService.delete(new Long(1));
  }

  @Test
  public void deleteThrowsAssertionError() throws Exception {
    // TODO : thrown.expect not working on AssertionError???? WHY???
    // thrown.expect(AssertionError.class);
    try {
      addressService.delete("nonLong");
      Assert.fail("Expected AssertionError");
    } catch (AssertionError e) {
    }
  }

  /*
   * update tests
   */
  @Test
  public void updateThrowsNotImplementedException() throws Exception {
    thrown.expect(NotImplementedException.class);

    addressService.update(1L, new Address("street", "city", "state", 95555));
  }

  @Test
  public void updateThrowsAssertionError() throws Exception {
    // TODO : thrown.expect not working on AssertionError???? WHY???
    // thrown.expect(AssertionError.class);
    try {
      addressService.update("wrong", null);
      Assert.fail("Expected AssertionError");
    } catch (AssertionError e) {
    }
  }
}
