package gov.ca.cwds.rest.resources.cms;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import javax.ws.rs.core.MediaType;

import org.hamcrest.junit.ExpectedException;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import gov.ca.cwds.rest.api.domain.cms.CmsDocument;
import gov.ca.cwds.rest.resources.ServiceBackedResourceDelegate;
import gov.ca.cwds.rest.resources.TypedResourceDelegate;
import io.dropwizard.testing.junit.ResourceTestRule;

/**
 * NOTE : The CWDS API Team has taken the pattern of delegating Resource functions to
 * {@link ServiceBackedResourceDelegate}. As such the tests in here reflect that assumption.
 * 
 * @author CWDS API Team
 */
public class CmsDocumentResourceTest {

  private static final String FOUND_RESOURCE = "/cmsdocument/abc";

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  private final static TypedResourceDelegate<String, CmsDocument> resourceDelegate =
      mock(TypedResourceDelegate.class);

  @ClassRule
  public final static ResourceTestRule inMemoryResource =
      ResourceTestRule.builder().addResource(new CmsDocumentResource(resourceDelegate)).build();

  @Before
  public void setup() throws Exception {}

  /**
   * Get Tests
   * 
   * @throws Exception required for test compilation
   */
  @Test
  public void getDelegatesToResourceDelegate() throws Exception {
    inMemoryResource.client().target(FOUND_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
        .get();
    verify(resourceDelegate).get("abc");
  }

  /**
   * Create Tests
   * <p>
   * <strong>NOT YET IMPLEMENTED</strong>
   * </p>
   */
  // @Test
  // public void createValidatesEntity() throws Exception {
  // CmsDocument serialized =
  // MAPPER.readValue(fixture("fixtures/domain/cms/CmsDocument/invalid/invalid.json"),
  // CmsDocument.class);
  //
  // int status =
  // inMemoryResource.client().target(ROOT_RESOURCE).request()
  // .accept(MediaType.APPLICATION_JSON)
  // .post(Entity.entity(serialized, MediaType.APPLICATION_JSON)).getStatus();
  // assertThat(status, is(422));
  // }

}
