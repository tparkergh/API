package gov.ca.cwds.rest.services.cms;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import gov.ca.cwds.data.cms.ClientUcDao;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.cms.ClientUc;
import gov.ca.cwds.rest.services.ServiceException;
import gov.ca.cwds.rest.services.junit.template.ServiceTestTemplate;
import io.dropwizard.jackson.Jackson;

import javax.persistence.EntityNotFoundException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author CWDS API Team
 *
 */
public class ClientUcServiceTest implements ServiceTestTemplate {
  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();
  private ClientUcService clientUcService;
  private ClientUcDao clientUcDao;
  private StaffPersonIdRetriever staffPersonIdRetriever;

  @SuppressWarnings("javadoc")
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Override
  @Before
  public void setup() throws Exception {
    clientUcDao = mock(ClientUcDao.class);
    staffPersonIdRetriever = mock(StaffPersonIdRetriever.class);
    clientUcService = new ClientUcService(clientUcDao, staffPersonIdRetriever);
  }

  // find test
  @Override
  @Test
  public void testFindThrowsAssertionError() {
    // expect string type for primary key test
    thrown.expect(AssertionError.class);
    try {
      clientUcService.find(1);
    } catch (AssertionError e) {
      assertEquals("Expeceted AssertionError", e.getMessage());
    }
  }

  @Override
  @Test
  public void testFindReturnsCorrectEntity() throws Exception {
    String id = "KFXgqgo0JG";
    ClientUc expected =
        MAPPER.readValue(fixture("fixtures/domain/legacy/ClientUc/valid/valid.json"),
            ClientUc.class);
    gov.ca.cwds.data.persistence.cms.ClientUc clientUc =
        new gov.ca.cwds.data.persistence.cms.ClientUc(expected, "q27");

    when(clientUcDao.find(id)).thenReturn(clientUc);
    ClientUc found = clientUcService.find(id);
    assertThat(found, is(expected));
  }

  @Override
  @Test
  public void testFindReturnsNullWhenNotFound() throws Exception {
    Response found = clientUcService.find("ABC1234567");
    assertThat(found, is(nullValue()));
  }

  @Override
  @Test
  // delete test
  public void testDeleteThrowsAssertionError() throws Exception {
    // expect string type for primary key test
    thrown.expect(AssertionError.class);
    try {
      clientUcService.delete(123);
    } catch (AssertionError e) {
      assertEquals("Expected AssertionError", e.getMessage());
    }
  }

  @Override
  @Test
  public void testDeleteDelegatesToCrudsService() {
    clientUcService.delete("ABC2345678");
    verify(clientUcDao, times(1)).delete("ABC2345678");
  }

  @Override
  @Test
  public void testDeleteReturnsNullWhenNotFound() throws Exception {
    Response found = clientUcService.delete("ABC1234567");
    assertThat(found, is(nullValue()));
  }

  @Override
  public void testDeleteThrowsNotImplementedException() throws Exception {
    // delete is implemented

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
      clientUcService.update("ABC1234567", null);
    } catch (AssertionError e) {
      assertEquals("Expected AssertionError", e.getMessage());
    }
  }

  @Override
  @Test
  public void testUpdateReturnsCorrectEntity() throws Exception {
    String id = "AaoDyiJq27";
    ClientUc expected =
        MAPPER.readValue(fixture("fixtures/domain/legacy/ClientUc/valid/valid.json"),
            ClientUc.class);

    gov.ca.cwds.data.persistence.cms.ClientUc clientUc =
        new gov.ca.cwds.data.persistence.cms.ClientUc(expected, "q27");

    when(clientUcDao.find("ABC1234567")).thenReturn(clientUc);
    when(clientUcDao.update(any())).thenReturn(clientUc);

    Object retval = clientUcService.update("ABC1234567", expected);
    assertThat(retval.getClass(), is(ClientUc.class));
  }

  @SuppressWarnings("javadoc")
  @Test
  public void testUpdateThrowsExceptionWhenNotFound() throws Exception {
    try {
      ClientUc clientUcRquest =
          MAPPER.readValue(fixture("fixtures/domain/legacy/ClientUc/valid/valid.json"),
              ClientUc.class);

      when(clientUcDao.update(any())).thenThrow(EntityNotFoundException.class);

      clientUcService.update("ZZZZZZZZZZ", clientUcRquest);
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

  @Override
  public void testCreateReturnsPostedClass() throws Exception {

  }

  @SuppressWarnings("javadoc")
  public void testCreateReturnsNonNull() throws Exception {}

  @Override
  public void testCreateReturnsCorrectEntity() throws Exception {}

  @Override
  @Test
  public void testCreateNullIDError() throws Exception {
    try {
      ClientUc clientUcDomain =
          MAPPER.readValue(fixture("fixtures/domain/legacy/ClientUc/valid/valid.json"),
              ClientUc.class);
      gov.ca.cwds.data.persistence.cms.ClientUc toCreate =
          new gov.ca.cwds.data.persistence.cms.ClientUc(clientUcDomain, "q27");

      when(clientUcDao.create(any(gov.ca.cwds.data.persistence.cms.ClientUc.class))).thenReturn(
          toCreate);

      ClientUc expected = new ClientUc(toCreate);
    } catch (ServiceException e) {
      assertEquals("Client ID cannot be blank", e.getMessage());
    }

  }

  @Override
  @Test
  public void testCreateBlankIDError() throws Exception {
    try {
      ClientUc clientUcDomain =
          MAPPER.readValue(fixture("fixtures/domain/legacy/ClientUc/valid/valid.json"),
              ClientUc.class);
      gov.ca.cwds.data.persistence.cms.ClientUc toCreate =
          new gov.ca.cwds.data.persistence.cms.ClientUc(clientUcDomain, "q27");

      when(clientUcDao.create(any(gov.ca.cwds.data.persistence.cms.ClientUc.class))).thenReturn(
          toCreate);

      ClientUc expected = new ClientUc(toCreate);
    } catch (ServiceException e) {
      assertEquals("LongText ID cannot be blank", e.getMessage());
    }

  }

  /*
   * Test for checking the new Client Id generated and lenght is 10
   */
  @SuppressWarnings("javadoc")
  public void createReturnsGeneratedId() throws Exception {

  }

  @Override
  public void testCreateThrowsAssertionError() throws Exception {

  }

  @Override
  public void testCreateEmptyIDError() throws Exception {

  }

  @Override
  public void testCreateThrowsNotImplementedException() throws Exception {

  }

  @Override
  public void testFindThrowsNotImplementedException() throws Exception {

  }

}
