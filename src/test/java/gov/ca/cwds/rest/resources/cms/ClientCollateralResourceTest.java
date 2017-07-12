package gov.ca.cwds.rest.resources.cms;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import gov.ca.cwds.rest.api.domain.cms.ClientCollateral;
import gov.ca.cwds.rest.resources.ResourceDelegate;

import org.junit.Before;
import org.junit.Test;

public class ClientCollateralResourceTest {

  private ClientCollateralResource resource;
  private ClientCollateral clientCollateral;
  private ResourceDelegate service;

  @Before
  public void setup() {
    service = mock(ResourceDelegate.class);
    resource = new ClientCollateralResource(service);
  }

  @Test
  public void callClientCollateralService() throws Exception {
    resource.create(clientCollateral);
    verify(service).create(clientCollateral);
  }
}
