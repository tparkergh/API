package gov.ca.cwds.rest.services.cms;

import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

import com.fasterxml.jackson.databind.ObjectMapper;

import gov.ca.cwds.data.cms.ClientDao;
import io.dropwizard.jackson.Jackson;

/**
 * @author CWDS API Team
 *
 */
public class ClientServiceTest {

  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();
  private ClientService clientService;
  private ClientDao clientDao;

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Before
  public void setup() throws Exception {
    clientDao = mock(ClientDao.class);
    clientService = new ClientService(clientDao);
  }

  // @Test
  // public void createThrowsNotImplementedException() throws Exception {
  // thrown.expect(NotImplementedException.class);
  // Client clientDomain =
  // MAPPER.readValue(fixture("fixtures/domain/cms/Client/valid/valid.json"), Client.class);
  // clientService.create(clientDomain);
  // }
  //
  // @Test
  // public void updateThrowsNotImplementedException() throws Exception {
  // thrown.expect(NotImplementedException.class);
  // Client clientDomain =
  // MAPPER.readValue(fixture("fixtures/domain/cms/Client/valid/valid.json"), Client.class);
  // clientService.update("testkey", clientDomain);
  // }
  //
  // @Test
  // public void deleteThrowsNotImplementedException() throws Exception {
  // thrown.expect(NotImplementedException.class);
  // clientService.delete("testkey");
  // }

}
