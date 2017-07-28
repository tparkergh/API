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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import gov.ca.cwds.data.ns.AddressDao;
import gov.ca.cwds.rest.api.domain.Address;
import gov.ca.cwds.rest.api.domain.PostedAddress;
import gov.ca.cwds.rest.services.junit.template.ServiceTestTemplate;
import io.dropwizard.jackson.Jackson;

/**
 * @author CWDS API Team
 *
 */
public class AddressServiceTest implements ServiceTestTemplate {
  private AddressService addressService;

  private AddressDao addressDao;
  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

  @SuppressWarnings("javadoc")
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Override
  @Before
  public void setup() throws Exception {
    addressDao = mock(AddressDao.class);
    addressService = new AddressService(addressDao);
    MAPPER.configure(SerializationFeature.INDENT_OUTPUT, true);
  }

  /*
   * find tests
   */
  @Override
  @Test
  public void testFindThrowsAssertionError() throws Exception {
    thrown.expect(AssertionError.class);
    try {
      addressService.find("nonLong");
    } catch (AssertionError e) {
      assertEquals("Expected AssertionError", e.getMessage());
    }

  }

  @Override
  @Test
  public void testFindReturnsCorrectEntity() throws Exception {
    when(addressDao.find(new Long(1))).thenReturn(new gov.ca.cwds.data.persistence.ns.Address(1L,
        "742 Evergreen Terrace", "Springfield", "WA", new Integer(98700), 32));

    Address expected = new Address("", "", "742 Evergreen Terrace", "Springfield", "WA", 98700, 32);

    Address found = addressService.find(new Long(1));

    assertThat(found, is(expected));
  }

  @Override
  @Test
  public void testFindReturnsNullWhenNotFound() throws Exception {
    when(addressDao.find(new Long(-1))).thenReturn(null);
    Address found = addressService.find(new Long(-1));

    assertThat(found, is(nullValue()));
  }


  @Override
  public void testFindThrowsNotImplementedException() throws Exception {

  }

  /*
   * create tests
   */
  @Override
  @Test
  public void testCreateThrowsAssertionError() throws Exception {
    thrown.expect(AssertionError.class);
    try {
      @SuppressWarnings("unused")
      PostedAddress postedAddress = addressService.create(null);
    } catch (AssertionError e) {
      assertEquals("Expected AssertionError", e.getMessage());
    }
  }

  @Override
  @Test
  public void testCreateReturnsPostedClass() throws Exception {
    gov.ca.cwds.data.persistence.ns.Address toCreate = new gov.ca.cwds.data.persistence.ns.Address(
        1L, "742 Evergreen Terrace", "Springfield", "WA", new Integer(98700), 32);
    Address request = new Address(toCreate);

    when(addressDao.create(any(gov.ca.cwds.data.persistence.ns.Address.class)))
        .thenReturn(toCreate);

    PostedAddress postedAddress = addressService.create(request);
    assertThat(postedAddress.getClass(), is(PostedAddress.class));
  }

  @Override
  @Test
  public void testCreateReturnsCorrectEntity() throws Exception {
    gov.ca.cwds.data.persistence.ns.Address toCreate = new gov.ca.cwds.data.persistence.ns.Address(
        10L, "742 Evergreen Terrace", "Springfield", "WA", new Integer(98700), 32);
    Address request = new Address(toCreate);

    when(addressDao.create(any(gov.ca.cwds.data.persistence.ns.Address.class)))
        .thenReturn(toCreate);

    PostedAddress expected = new PostedAddress(10, "", "", "742 Evergreen Terrace", "Springfield",
        "WA", new Integer(98700), 32);
    PostedAddress returned = addressService.create(request);

    assertThat(returned, is(expected));
  }

  @Override
  @Test
  public void testCreateNullIDError() throws Exception {
    thrown.expect(AssertionError.class);
    try {
      @SuppressWarnings("unused")
      PostedAddress postedAddress = addressService.create(null);
    } catch (AssertionError e) {
      assertEquals("Expected AssertionError", e.getMessage());
    }
  }

  @Override
  public void testCreateEmptyIDError() throws Exception {

  }

  @Override
  public void testCreateThrowsNotImplementedException() throws Exception {

  }

  @Override
  public void testCreateBlankIDError() throws Exception {

  }

  @SuppressWarnings("javadoc")
  @Test
  public void testCreateExistsError() throws Exception {

    gov.ca.cwds.data.persistence.ns.Address toCreate = new gov.ca.cwds.data.persistence.ns.Address(
        (long) 1, "742 Evergreen Terrace", "Springfield", "WA", new Integer(98700), 32);
    Address request = new Address(toCreate);

    when(addressDao.create(any(gov.ca.cwds.data.persistence.ns.Address.class)))
        .thenReturn(toCreate);

    PostedAddress expected = new PostedAddress(1, "", "", "742 Evergreen Terrace", "Springfield",
        "WA", new Integer(98700), 32);

    PostedAddress returned = addressService.create(request);

    // String e = MAPPER.writeValueAsString(expected);
    // System.out.println(e);
    // String r = MAPPER.writeValueAsString(returned);
    // System.out.println(r);

    assertThat(returned, is(expected));

  }

  /*
   * delete tests
   */
  @Override
  @Test
  public void testDeleteThrowsAssertionError() throws Exception {
    thrown.expect(AssertionError.class);
    try {
      addressService.delete("nonLong");
    } catch (AssertionError e) {
      assertEquals("Expected AssertionError", e.getMessage());
    }
  }

  @Override
  public void testDeleteReturnsClass() throws Exception {

  }

  @Override
  @Test
  public void testDeleteThrowsNotImplementedException() throws Exception {
    thrown.expect(NotImplementedException.class);
    addressService.delete(new Long(1));
  }

  @Override
  public void testDeleteDelegatesToCrudsService() throws Exception {

  }

  @Override
  public void testDeleteReturnsNullWhenNotFound() throws Exception {

  }

  /*
   * update tests
   */
  @Override
  @Test
  public void testUpdateThrowsAssertionError() throws Exception {
    thrown.expect(AssertionError.class);
    try {
      addressService.update(null, new Address("", "", "street", "city", "state", 95555, 32));
    } catch (AssertionError e) {
      assertEquals("Expected AssertionError", e.getMessage());
    }
  }

  @Override
  @Test
  public void testUpdateThrowsNotImplementedException() throws Exception {
    thrown.expect(NotImplementedException.class);

    addressService.update(1L, new Address("", "", "street", "city", "state", 95555, 32));
  }

  @Override
  public void testUpdateReturnsDomain() throws Exception {

  }

  @Override
  public void testUpdateReturnsCorrectEntity() throws Exception {

  }

  @Override
  public void testUpdateThrowsServiceException() throws Exception {

  }

}
