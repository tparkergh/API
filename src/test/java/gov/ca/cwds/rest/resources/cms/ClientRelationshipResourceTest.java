package gov.ca.cwds.rest.resources.cms;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import gov.ca.cwds.rest.api.domain.cms.ClientRelationship;
import gov.ca.cwds.rest.resources.ResourceDelegate;

import org.junit.Before;
import org.junit.Test;

public class ClientRelationshipResourceTest {

  private ClientRelationshipResource resource;
  private ClientRelationship clientRelationship;
  private ResourceDelegate service;

  @Before
  public void setup() {
    service = mock(ResourceDelegate.class);
    resource = new ClientRelationshipResource(service);
  }

  @Test
  public void callClientRelationshipService() throws Exception {
    resource.create(clientRelationship);
    verify(service).create(clientRelationship);
  }
}
