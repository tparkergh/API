package gov.ca.cwds.rest.services.cms;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.persistence.EntityNotFoundException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.fasterxml.jackson.databind.ObjectMapper;

import gov.ca.cwds.data.cms.ClientDao;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.cms.Client;
import gov.ca.cwds.rest.api.domain.cms.PostedClient;
import gov.ca.cwds.rest.services.ServiceException;
import gov.ca.cwds.rest.services.junit.template.ServiceTestTemplate;
import io.dropwizard.jackson.Jackson;

/**
 * @author CWDS API Team
 *
 */
public class ClientServiceTest implements ServiceTestTemplate {
  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();
  private ClientService clientService;
  private ClientDao clientDao;

  @SuppressWarnings("javadoc")
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Override
  @Before
  public void setup() throws Exception {
    clientDao = mock(ClientDao.class);
    clientService = new ClientService(clientDao);
  }

  // find test
  @Override
  @Test
  public void testFindThrowsAssertionError() {
    // expect string type for primary key test
    thrown.expect(AssertionError.class);
    try {
      clientService.find(1);
    } catch (AssertionError e) {
      assertEquals("Expeceted AssertionError", e.getMessage());
    }
  }

  @Override
  @Test
  public void testFindReturnsCorrectEntity() throws Exception {
    String id = "AaiU7IW0Rt";
    Client expected = MAPPER
        .readValue(fixture("fixtures/domain/legacy/Client/valid/serviceValid.json"), Client.class);
    gov.ca.cwds.data.persistence.cms.Client client =
        new gov.ca.cwds.data.persistence.cms.Client(id, expected, "04Z");

    when(clientDao.find(id)).thenReturn(client);
    Client found = clientService.find(id);
    assertThat(found, is(expected));
  }

  @Override
  @Test
  public void testFindReturnsNullWhenNotFound() throws Exception {
    Response found = clientService.find("ABC1234567");
    assertThat(found, is(nullValue()));
  }

  @Override
  @Test
  // delete test
  public void testDeleteThrowsAssertionError() throws Exception {
    // expect string type for primary key test
    thrown.expect(AssertionError.class);
    try {
      clientService.delete(123);
    } catch (AssertionError e) {
      assertEquals("Expected AssertionError", e.getMessage());
    }
  }

  @Override
  @Test
  public void testDeleteDelegatesToCrudsService() {
    clientService.delete("ABC2345678");
    verify(clientDao, times(1)).delete("ABC2345678");
  }

  @Override
  @Test
  public void testDeleteReturnsNullWhenNotFound() throws Exception {
    Response found = clientService.delete("ABC1234567");
    assertThat(found, is(nullValue()));
  }


  @Override
  public void testDeleteReturnsClass() throws Exception {

  }

  // update test
  @Override
  @Test
  public void testUpdateThrowsAssertionError() throws Exception {
    // expected string type for primary key test
    thrown.expect(AssertionError.class);
    try {
      clientService.update("ABC1234567", null);
    } catch (AssertionError e) {
      assertEquals("Expected AssertionError", e.getMessage());
    }
  }

  @Override
  @Test
  public void testUpdateReturnsCorrectEntity() throws Exception {
    String id = "Aaeae9r0F4";
    Client expected = MAPPER
        .readValue(fixture("fixtures/domain/legacy/Client/valid/serviceValid.json"), Client.class);

    gov.ca.cwds.data.persistence.cms.Client client =
        new gov.ca.cwds.data.persistence.cms.Client(id, expected, "ABC");

    when(clientDao.find("ABC1234567")).thenReturn(client);
    when(clientDao.update(any())).thenReturn(client);

    Object retval = clientService.update("ABC1234567", expected);
    assertThat(retval.getClass(), is(Client.class));
  }

  @SuppressWarnings("javadoc")
  @Test
  public void testUpdateThrowsExceptionWhenNotFound() throws Exception {
    try {
      Client clientRequest = MAPPER.readValue(
          fixture("fixtures/domain/legacy/Client/valid/serviceValid.json"), Client.class);

      when(clientDao.update(any())).thenThrow(EntityNotFoundException.class);

      clientService.update("ZZZZZZZZZZ", clientRequest);
    } catch (Exception e) {
      assertEquals(e.getClass(), ServiceException.class);
    }
  }

  @Override
  public void testUpdateReturnsDomain() throws Exception {

  }

  @Override
  public void testUpdateThrowsServiceException() throws Exception {

  }

  @Override
  public void testUpdateThrowsNotImplementedException() throws Exception {

  }

  // create test
  @Override
  @Test
  public void testCreateReturnsPostedClass() throws Exception {
    String id = "Aaeae9r0F4";
    Client clientDomain = MAPPER
        .readValue(fixture("fixtures/domain/legacy/Client/valid/serviceValid.json"), Client.class);
    gov.ca.cwds.data.persistence.cms.Client toCreate =
        new gov.ca.cwds.data.persistence.cms.Client(id, clientDomain, "ABC");

    Client request = new Client(toCreate);
    when(clientDao.create(any(gov.ca.cwds.data.persistence.cms.Client.class))).thenReturn(toCreate);

    Response response = clientService.create(request);
    assertThat(response.getClass(), is(PostedClient.class));
  }

  @SuppressWarnings("javadoc")
  @Test
  public void testCreateReturnsNonNull() throws Exception {
    String id = "Aaeae9r0F4";
    Client clientDomain = MAPPER
        .readValue(fixture("fixtures/domain/legacy/Client/valid/serviceValid.json"), Client.class);
    gov.ca.cwds.data.persistence.cms.Client toCreate =
        new gov.ca.cwds.data.persistence.cms.Client(id, clientDomain, "ABC");

    Client request = new Client(toCreate);
    when(clientDao.create(any(gov.ca.cwds.data.persistence.cms.Client.class))).thenReturn(toCreate);

    PostedClient postedClient = clientService.create(request);
    assertThat(postedClient, is(notNullValue()));
  }

  @Override
  @Test
  public void testCreateReturnsCorrectEntity() throws Exception {
    String id = "Aaeae9r0F4";
    Client clientDomain = MAPPER
        .readValue(fixture("fixtures/domain/legacy/Client/valid/serviceValid.json"), Client.class);
    gov.ca.cwds.data.persistence.cms.Client toCreate =
        new gov.ca.cwds.data.persistence.cms.Client(id, clientDomain, "ABC");

    Client request = new Client(toCreate);
    when(clientDao.create(any(gov.ca.cwds.data.persistence.cms.Client.class))).thenReturn(toCreate);

    PostedClient expected = new PostedClient(toCreate);
    PostedClient returned = clientService.create(request);
    assertThat(returned, is(expected));
  }

  @Override
  @Test
  public void testCreateNullIDError() throws Exception {
    try {
      Client clientDomain = MAPPER.readValue(
          fixture("fixtures/domain/legacy/Client/valid/serviceValid.json"), Client.class);
      gov.ca.cwds.data.persistence.cms.Client toCreate =
          new gov.ca.cwds.data.persistence.cms.Client(null, clientDomain, "ABC");

      when(clientDao.create(any(gov.ca.cwds.data.persistence.cms.Client.class)))
          .thenReturn(toCreate);

    } catch (ServiceException e) {
      assertEquals("Client ID cannot be empty", e.getMessage());
    }

  }

  @Override
  @Test
  public void testCreateEmptyIDError() throws Exception {

    try {
      Client clientDomain = MAPPER.readValue(
          fixture("fixtures/domain/legacy/Client/valid/serviceValid.json"), Client.class);
      gov.ca.cwds.data.persistence.cms.Client toCreate =
          new gov.ca.cwds.data.persistence.cms.Client("    ", clientDomain, "ABC");

      when(clientDao.create(any(gov.ca.cwds.data.persistence.cms.Client.class)))
          .thenReturn(toCreate);

    } catch (ServiceException e) {
      assertEquals("Client ID cannot be empty", e.getMessage());
    }


  }

  @Override
  public void testCreateBlankIDError() throws Exception {

  }

  @Override
  public void testCreateThrowsAssertionError() throws Exception {

  }



  @Override
  public void testCreateThrowsNotImplementedException() throws Exception {

  }

  @Override
  public void testFindThrowsNotImplementedException() throws Exception {

  }

  @Override
  public void testDeleteThrowsNotImplementedException() throws Exception {

  }

}
