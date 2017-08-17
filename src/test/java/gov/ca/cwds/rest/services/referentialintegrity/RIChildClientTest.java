package gov.ca.cwds.rest.services.referentialintegrity;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.data.cms.ClientDao;
import gov.ca.cwds.fixture.ChildClientResourceBuilder;
import gov.ca.cwds.fixture.ClientResourceBuilder;
import gov.ca.cwds.rest.api.domain.cms.ChildClient;
import gov.ca.cwds.rest.api.domain.cms.Client;
import gov.ca.cwds.rest.validation.ReferentialIntegrityException;

/**
 * @author CWDS API Team
 *
 */
@SuppressWarnings("javadoc")
public class RIChildClientTest {

  private ClientDao clientDao;

  @Test
  public void type() throws Exception {
    assertThat(RIChildClient.class, notNullValue());
  }

  @Before
  public void setup() {
    clientDao = mock(ClientDao.class);
  }

  @Test
  public void instantiation() throws Exception {
    RIChildClient riChildClient = new RIChildClient(clientDao);
    assertThat(riChildClient, notNullValue());
  }

  /*
   * Test for test the referential Integrity Exception
   */
  @Test(expected = ReferentialIntegrityException.class)
  public void riCheckForReferentialIntegrityException() throws Exception {
    ChildClient childClientDomain = new ChildClientResourceBuilder().buildChildClient();
    gov.ca.cwds.data.persistence.cms.ChildClient childClient =
        new gov.ca.cwds.data.persistence.cms.ChildClient("ABC1234567", childClientDomain, "0X5");

    when(clientDao.find(any(String.class))).thenReturn(null);
    RIChildClient rIChildClient = new RIChildClient(clientDao);
    rIChildClient.apply(childClient);
  }

  @Test
  public void riCheckPassWhenVictimClientFound() throws Exception {
    ChildClient childClientDomain = new ChildClientResourceBuilder().buildChildClient();
    gov.ca.cwds.data.persistence.cms.ChildClient childClient =
        new gov.ca.cwds.data.persistence.cms.ChildClient("ABC1234567", childClientDomain, "0X5");

    Client clientDomain = new ClientResourceBuilder().build();
    gov.ca.cwds.data.persistence.cms.Client client =
        new gov.ca.cwds.data.persistence.cms.Client("ABC1234567", clientDomain, "0X5");

    when(clientDao.find(any(String.class))).thenReturn(client);
    RIChildClient rIChildClient = new RIChildClient(clientDao);
    Boolean result = rIChildClient.apply(childClient);
    assertThat(result, is(equalTo(true)));
  }

}
