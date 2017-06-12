package gov.ca.cwds.rest.services.cms;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import gov.ca.cwds.rest.api.domain.cms.LegacyKeyRequest;
import gov.ca.cwds.rest.api.domain.cms.LegacyKeyResponse;

public class LegacyKeyServiceTest {

  @Test
  public void type() throws Exception {
    assertThat(LegacyKeyService.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    LegacyKeyService target = new LegacyKeyService();
    assertThat(target, notNullValue());
  }

  @Test
  public void handleRequest_Args__LegacyKeyRequest() throws Exception {
    LegacyKeyService target = new LegacyKeyService();
    LegacyKeyRequest req = new LegacyKeyRequest("HfsDjZT0AB");
    LegacyKeyResponse actual = target.handleRequest(req);
    LegacyKeyResponse expected = new LegacyKeyResponse("1003-9666-4662-7000631");
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void handleFind_Args__String() throws Exception {
    LegacyKeyService target = new LegacyKeyService();
    String key = "HaRIdrG00h";
    LegacyKeyResponse actual = target.handleFind(key);
    LegacyKeyResponse expected = new LegacyKeyResponse("0998-9881-9107-4000043");
    assertThat(actual, is(equalTo(expected)));
  }

}
