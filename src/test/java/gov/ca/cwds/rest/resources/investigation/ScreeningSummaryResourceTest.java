package gov.ca.cwds.rest.resources.investigation;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import gov.ca.cwds.data.cms.TestSystemCodeCache;
import gov.ca.cwds.rest.api.domain.investigation.ScreeningSummary;
import gov.ca.cwds.rest.resources.ServiceBackedResourceDelegate;
import gov.ca.cwds.rest.resources.TypedResourceDelegate;
import gov.ca.cwds.rest.resources.cms.JerseyGuiceRule;
import io.dropwizard.testing.junit.ResourceTestRule;

import javax.ws.rs.core.MediaType;

import org.hamcrest.junit.ExpectedException;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import com.squarespace.jersey2.guice.JerseyGuiceUtils;

/****
 * NOTE:The CWDS API Team has taken the pattern of delegating Resource functions to
 * 
 * {@link ServiceBackedResourceDelegate}. As such the tests in here reflect that assumption.
 *
 * @author CWDS API Team
 */
@SuppressWarnings("javadoc")
public class ScreeningSummaryResourceTest {


  private static final String ROOT_RESOURCE = "/investigations/";

  @After
  public void ensureServiceLocatorPopulated() {
    JerseyGuiceUtils.reset();
  }

  @ClassRule
  public static JerseyGuiceRule rule = new JerseyGuiceRule();

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @SuppressWarnings("unchecked")
  private final static TypedResourceDelegate<String, ScreeningSummary> typedResourceDelegate =
      mock(TypedResourceDelegate.class);

  @ClassRule
  public final static ResourceTestRule inMemoryResource = ResourceTestRule.builder()
      .addResource(new ScreeningSummaryResource(typedResourceDelegate)).build();
  /*
   * Load system code cache
   */
  TestSystemCodeCache testSystemCodeCache = new TestSystemCodeCache();

  @Before
  public void initMocks() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void findDelegatesToResourceDelegate() throws Exception {

    inMemoryResource.client().target(ROOT_RESOURCE + "1/screening").request()
        .accept(MediaType.APPLICATION_JSON).get().getStatus();
    verify(typedResourceDelegate, atLeastOnce()).get("1");
  }

}
