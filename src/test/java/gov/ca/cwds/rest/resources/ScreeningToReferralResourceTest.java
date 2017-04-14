package gov.ca.cwds.rest.resources;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.rest.api.domain.ScreeningToReferral;

public class ScreeningToReferralResourceTest {

  private ScreeningToReferralResource resource;
  private ScreeningToReferral referral;
  private ResourceDelegate service;

  @Before
  public void setup() {
    service = mock(ResourceDelegate.class);
    resource = new ScreeningToReferralResource(service);
  }

  @Test
  public void callScreeningToReferralService() throws Exception {
    resource.create(referral);
    verify(service).create(referral);
  }
}
