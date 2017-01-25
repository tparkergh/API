package gov.ca.cwds.rest.resources.cms;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

import org.apache.http.HttpStatus;
import org.hamcrest.junit.ExpectedException;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mockito;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;

import gov.ca.cwds.rest.api.domain.es.AutoCompletePersonRequest;
import gov.ca.cwds.rest.api.domain.es.AutoCompletePersonResponse;
import gov.ca.cwds.rest.core.Api;
import gov.ca.cwds.rest.resources.AutoCompletePersonResource;
import gov.ca.cwds.rest.resources.ServiceBackedResourceDelegate;
import gov.ca.cwds.rest.resources.SimpleResourceDelegate;
import gov.ca.cwds.rest.services.es.AutoCompletePersonService;
import io.dropwizard.jackson.Jackson;
import io.dropwizard.testing.junit.ResourceTestRule;

/**
 * NOTE : The CWDS API Team has taken the pattern of delegating Resource functions to
 * {@link ServiceBackedResourceDelegate}. As such the tests in here reflect that assumption.
 * 
 * @author CWDS API Team
 */
public class AutoCompleteResourceTest {

  private static final String FOUND_RESOURCE = "/" + Api.RESOURCE_AUTOCOMPLETE;

  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  private static final AutoCompletePersonService svc = mock(AutoCompletePersonService.class);

  private static final SimpleResourceDelegate<String, AutoCompletePersonRequest, AutoCompletePersonResponse, AutoCompletePersonService> delegate =
      new SimpleResourceDelegate<>(svc);

  private final static SimpleResourceDelegate<String, AutoCompletePersonRequest, AutoCompletePersonResponse, AutoCompletePersonService> resourceDelegate =
      mock(delegate.getClass());

  @ClassRule
  public final static ResourceTestRule inMemoryResource = ResourceTestRule.builder()
      .addResource(new AutoCompletePersonResource(resourceDelegate)).build();

  @Before
  public void setup() throws Exception {
    Mockito.reset(resourceDelegate);
  }

  /*
   * GET Tests
   */
  @Test
  public void testSearch_good() throws Exception {
    final AutoCompletePersonResponse actual =
        inMemoryResource.client().target(FOUND_RESOURCE + "?search_term=john").request()
            .get(AutoCompletePersonResponse.class);
    verify(resourceDelegate).handle(any());
  }

  @Test
  public void testSearch_blank() throws Exception {
    final AutoCompletePersonResponse actual = inMemoryResource.client()
        .target(FOUND_RESOURCE + "?search_term=").request().get(AutoCompletePersonResponse.class);
    verify(resourceDelegate).handle(any());
  }

  @Test(expected = UnrecognizedPropertyException.class)
  public void testSearch_invalid() throws Exception {
    AutoCompletePersonRequest serialized =
        MAPPER.readValue(fixture("fixtures/domain/elasticsearch/Intake/es_person.json"),
            AutoCompletePersonRequest.class);

    final int status = inMemoryResource.client().target(FOUND_RESOURCE).request()
        .accept(MediaType.APPLICATION_JSON)
        .post(Entity.entity(serialized, MediaType.APPLICATION_JSON)).getStatus();
    assertThat(status, is(HttpStatus.SC_OK));
  }

}
