package gov.ca.cwds.rest.resources;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;

public class StaffPersonResourceTest {
  private StaffPersonResource resource;
  private ResourceDelegate service;

  @Before
  public void setup() {
    service = mock(ResourceDelegate.class);
    resource = new StaffPersonResource(service);
  }

  @Test
  public void callStaffPersonService() throws Exception {
    resource.get("abc");
    verify(service).get("abc");
  }
}
