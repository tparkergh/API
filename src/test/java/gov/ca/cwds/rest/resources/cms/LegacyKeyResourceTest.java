package gov.ca.cwds.rest.resources.cms;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import javax.ws.rs.BadRequestException;

import org.hamcrest.junit.ExpectedException;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mockito;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.squarespace.jersey2.guice.JerseyGuiceUtils;

import gov.ca.cwds.rest.api.domain.cms.LegacyKeyRequest;
import gov.ca.cwds.rest.api.domain.cms.LegacyKeyResponse;
import gov.ca.cwds.rest.core.Api;
import gov.ca.cwds.rest.resources.ServiceBackedResourceDelegate;
import gov.ca.cwds.rest.resources.SimpleResourceDelegate;
import gov.ca.cwds.rest.services.cms.LegacyKeyService;
import io.dropwizard.jackson.Jackson;
import io.dropwizard.testing.junit.ResourceTestRule;

/**
 * NOTE : The CWDS API Team has taken the pattern of delegating Resource functions to
 * {@link ServiceBackedResourceDelegate}. As such the tests in here reflect that assumption.
 * 
 * @author CWDS API Team
 */
@SuppressWarnings("javadoc")
public class LegacyKeyResourceTest {

  private static final String FOUND_RESOURCE = "/" + Api.RESOURCE_CMS_UI_IDENTIFIER;

  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  private static final LegacyKeyService svc = mock(LegacyKeyService.class);

  private static final SimpleResourceDelegate<String, LegacyKeyRequest, LegacyKeyResponse, LegacyKeyService> delegate =
      new SimpleResourceDelegate<>(svc);

  private final static SimpleResourceDelegate<String, LegacyKeyRequest, LegacyKeyResponse, LegacyKeyService> resourceDelegate =
      mock(delegate.getClass());

  @After
  public void ensureServiceLocatorPopulated() {
    JerseyGuiceUtils.reset();
  }

  @ClassRule
  public static JerseyGuiceRule rule = new JerseyGuiceRule();

  @ClassRule
  public final static ResourceTestRule inMemoryResource =
      ResourceTestRule.builder().addResource(new LegacyKeyResource(resourceDelegate)).build();

  @Before
  public void setup() throws Exception {
    Mockito.reset(resourceDelegate);
  }

  /*
   * GET Tests
   */
  @Test
  public void testSearch_good() throws Exception {
    final LegacyKeyResponse actual = inMemoryResource.client()
        .target(FOUND_RESOURCE + "?key=H1ONj7M0J3").request().get(LegacyKeyResponse.class);
    verify(resourceDelegate).handle(any());
  }

  @Test(expected = BadRequestException.class)
  public void testSearch_blank() throws Exception {
    final LegacyKeyResponse actual = inMemoryResource.client().target(FOUND_RESOURCE + "?key=")
        .request().get(LegacyKeyResponse.class);
    verify(resourceDelegate).handle(any());
  }

  @Test(expected = BadRequestException.class)
  public void testSearch_short() throws Exception {
    final LegacyKeyResponse actual = inMemoryResource.client().target(FOUND_RESOURCE + "?key=asf")
        .request().get(LegacyKeyResponse.class);
    verify(resourceDelegate).handle(any());
  }

  @Test(expected = BadRequestException.class)
  public void testSearch_long() throws Exception {
    final LegacyKeyResponse actual = inMemoryResource.client()
        .target(FOUND_RESOURCE + "?key=asf38383kdhslakdnv").request().get(LegacyKeyResponse.class);
    verify(resourceDelegate).handle(any());
  }

  @Test(expected = BadRequestException.class)
  public void testSearch_bad_pattern() throws Exception {
    final LegacyKeyResponse actual =
        inMemoryResource.client().target(FOUND_RESOURCE + "?key=as_f3_8383_kdhslakdnv").request()
            .get(LegacyKeyResponse.class);
    verify(resourceDelegate).handle(any());
  }

}
