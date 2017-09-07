package gov.ca.cwds.rest.resources;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import javax.ws.rs.core.MediaType;

import org.hamcrest.junit.ExpectedException;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.squarespace.jersey2.guice.JerseyGuiceUtils;

import gov.ca.cwds.rest.api.domain.IntakeLovEntry;
import gov.ca.cwds.rest.api.domain.IntakeLovResponse;
import gov.ca.cwds.rest.core.Api;
import gov.ca.cwds.rest.resources.cms.JerseyGuiceRule;
import gov.ca.cwds.rest.services.IntakeLovService;
import io.dropwizard.testing.junit.ResourceTestRule;

public class IntakeLovResourceTest {

  private static final String ROOT_RESOURCE = "/" + Api.RESOURCE_INTAKE_LOV;

  @After
  public void ensureServiceLocatorPopulated() {
    JerseyGuiceUtils.reset();
  }

  @ClassRule
  public static JerseyGuiceRule rule = new JerseyGuiceRule();

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @SuppressWarnings("unchecked")
  private static SimpleResourceDelegate<String, IntakeLovEntry, IntakeLovResponse, IntakeLovService> resourceDelegate =
      mock(SimpleResourceDelegate.class);

  @ClassRule
  public final static ResourceTestRule inMemoryResource =
      ResourceTestRule.builder().addResource(new IntakeLovResource(resourceDelegate)).build();

  @Before
  public void setup() throws Exception {
    MockitoAnnotations.initMocks(this);
    Mockito.reset(resourceDelegate);
  }

  @Test
  public void createDelegatesToResourceDelegate() throws Exception {
    inMemoryResource.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
        .get();
    verify(resourceDelegate).handle(any());
  }

  @Test
  public void type() throws Exception {
    assertThat(IntakeLovResource.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    IntakeLovResource target = new IntakeLovResource(resourceDelegate);
    assertThat(target, notNullValue());
  }

}
