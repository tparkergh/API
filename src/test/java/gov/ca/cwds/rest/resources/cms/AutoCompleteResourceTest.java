package gov.ca.cwds.rest.resources.cms;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

import org.apache.http.HttpStatus;
import org.hamcrest.junit.ExpectedException;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import gov.ca.cwds.rest.api.domain.es.AutoCompletePersonRequest;
import gov.ca.cwds.rest.api.domain.es.AutoCompletePersonResponse;
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

  private static final String ROOT_RESOURCE = "/autocomplete/";
  private static final String FOUND_RESOURCE = "/autocomplete/search_person";

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
  public void setup() throws Exception {}

  /*
   * POST Tests
   */
  @Test
  public void createValidatesEntity() throws Exception {
    AutoCompletePersonRequest serialized = MAPPER.readValue(
        fixture("fixtures/domain/elasticsearch/Intake/person_autocomplete_good.json"),
        AutoCompletePersonRequest.class);

    int status = inMemoryResource.client().target(FOUND_RESOURCE).request()
        .accept(MediaType.APPLICATION_JSON)
        .post(Entity.entity(serialized, MediaType.APPLICATION_JSON)).getStatus();
    assertThat(status, is(HttpStatus.SC_OK));
  }


}
