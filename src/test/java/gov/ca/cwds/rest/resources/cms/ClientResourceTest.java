package gov.ca.cwds.rest.resources.cms;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.hamcrest.junit.ExpectedException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import gov.ca.cwds.rest.api.domain.cms.Client;
import gov.ca.cwds.rest.resources.ServiceBackedResourceDelegate;
import gov.ca.cwds.rest.resources.TypedResourceDelegate;
import gov.ca.cwds.rest.services.cms.ClientService;

/**
 * NOTE : The CWDS API Team has taken the pattern of delegating Resource functions to
 * {@link ServiceBackedResourceDelegate}. As such the tests in here reflect that assumption.
 * 
 * @author CWDS API Team
 */
public class ClientResourceTest {

  private static final String FOUND_RESOURCE = "/_client/abc";

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Mock
  private ClientService service;

  @Mock
  // private gov.ca.cwds.data.persistence.cms.Client persClient;
  private gov.ca.cwds.data.std.ApiMultipleAddressesAware persClient;


  @Mock
  private Client domainClient;

  @Mock
  private TypedResourceDelegate<String, Client> resourceDelegate;

  @InjectMocks
  @Spy
  private ClientResource target; // "Class Under Test"

  @Before
  public void initMocks() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void type() throws Exception {
    assertThat(ClientResource.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    assertThat(target, notNullValue());
  }


}
