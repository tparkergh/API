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

import gov.ca.cwds.data.ns.AddressDao;
import gov.ca.cwds.rest.api.domain.Address;
import gov.ca.cwds.rest.api.domain.PostedAddress;
import gov.ca.cwds.rest.services.junit.template.ServiceTestTemplate;

/**
 * @author CWDS API Team
 *
 */
public class AddressServiceTest implements ServiceTestTemplate {
  private AddressService addressService;

  private AddressDao addressDao;

  @SuppressWarnings("javadoc")
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Override
  @Before
  public void setup() throws Exception {
    addressDao = mock(AddressDao.class);
    addressService = new AddressService(addressDao);
  }

  /*
   * find tests
   */
  @Override
  @Test
  public void testEntityFindThrowsAssertionError() throws Exception {
    thrown.expect(AssertionError.class);
    try {
      addressService.find("nonLong");
    } catch (AssertionError e) {
      assertEquals("Expected AssertionError", e.getMessage());
    }

  }

  @Override
  @Test
  public void testEntityFindReturnsCorrectEntity() throws Exception {
    when(addressDao.find(new Long(1))).thenReturn(new gov.ca.cwds.data.persistence.ns.Address(1L,
        "742 Evergreen Terrace", "Springfield", "WA", new Integer(98700)));

    Address expected = new Address("742 Evergreen Terrace", "Springfield", "WA", 98700);

    Address found = addressService.find(new Long(1));

    assertThat(found, is(expected));
  }

  @Override
  @Test
  public void testEntityFindReturnsNullWhenNotFound() throws Exception {
    when(addressDao.find(new Long(-1))).thenReturn(null);
    Address found = addressService.find(new Long(-1));

    assertThat(found, is(nullValue()));
  }


  @Override
  public void testEntityFindThrowsNotImplementedException() throws Exception {

  }

  /*
   * create tests
   */
  @Override
  @Test
  public void testEntityCreateThrowsAssertionError() throws Exception {
    thrown.expect(AssertionError.class);
    try {
      PostedAddress postedAddress = addressService.create(null);
    } catch (AssertionError e) {
      assertEquals("Expected AssertionError", e.getMessage());
    }
  }

  @Override
  @Test
  public void testEntityCreateReturnsPostedClass() throws Exception {
    gov.ca.cwds.data.persistence.ns.Address toCreate = new gov.ca.cwds.data.persistence.ns.Address(
        1L, "742 Evergreen Terrace", "Springfield", "WA", new Integer(98700));
    Address request = new Address(toCreate);

    when(addressDao.create(any(gov.ca.cwds.data.persistence.ns.Address.class)))
        .thenReturn(toCreate);

    PostedAddress postedAddress = addressService.create(request);
    assertThat(postedAddress.getClass(), is(PostedAddress.class));
  }

  @Override
  @Test
  public void testEntityCreateReturnsCorrectEntity() throws Exception {
    gov.ca.cwds.data.persistence.ns.Address toCreate = new gov.ca.cwds.data.persistence.ns.Address(
        10L, "742 Evergreen Terrace", "Springfield", "WA", new Integer(98700));
    Address request = new Address(toCreate);

    when(addressDao.create(any(gov.ca.cwds.data.persistence.ns.Address.class)))
        .thenReturn(toCreate);

    PostedAddress expected =
        new PostedAddress(10, "742 Evergreen Terrace", "Springfield", "WA", new Integer(98700));
    PostedAddress returned = addressService.create(request);

    assertThat(returned, is(expected));
  }

  @Override
  @Test
  public void testEntityCreateNullIDError() throws Exception {
    thrown.expect(AssertionError.class);
    try {
      PostedAddress postedAddress = addressService.create(null);
    } catch (AssertionError e) {
      assertEquals("Expected AssertionError", e.getMessage());
    }
  }

  @Override
  public void testEntityCreateBlankIDError() throws Exception {

  }

  @Override
  public void testEntityCreateEmptyError() throws Exception {

  }

  @SuppressWarnings("javadoc")
  @Test
  public void testEntityCreateExistsError() throws Exception {

    gov.ca.cwds.data.persistence.ns.Address toCreate = new gov.ca.cwds.data.persistence.ns.Address(
        (long) 1, "742 Evergreen Terrace", "Springfield", "WA", new Integer(98700));
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
  @Override
  @Test
  public void testEntityDeleteThrowsAssertionError() throws Exception {
    thrown.expect(AssertionError.class);
    try {
      addressService.delete("nonLong");
    } catch (AssertionError e) {
      assertEquals("Expected AssertionError", e.getMessage());
    }
  }

  @Override
  @Test
  public void testDeleteThrowsNotImplementedException() throws Exception {
    thrown.expect(NotImplementedException.class);
    addressService.delete(new Long(1));
  }

  @Override
  public void testEntityDeleteDelegatesToCrudsService() throws Exception {

  }

  @Override
  public void testEntityDeleteReturnsNullWhenNotFound() throws Exception {

  }

  /*
   * update tests
   */
  @Override
  @Test
  public void testEntityUpdateThrowsAssertionError() throws Exception {
    thrown.expect(AssertionError.class);
    try {
      addressService.update(null, new Address("street", "city", "state", 95555));
    } catch (AssertionError e) {
      assertEquals("Expected AssertionError", e.getMessage());
    }
  }

  @Override
  @Test
  public void testEntityUpdateThrowsNotImplementedException() throws Exception {
    thrown.expect(NotImplementedException.class);

    addressService.update(1L, new Address("street", "city", "state", 95555));
  }

  @Override
  public void testEntityUpdateReturnsPersistent() throws Exception {

  }

  @Override
  public void testEntityUpdateReturnsCorrectEntity() throws Exception {

  }

  @Override
  public void testEntityCreateThrowsNotImplementedException() throws Exception {
    // TODO Auto-generated method stub

  }
}
