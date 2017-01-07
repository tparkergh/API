package gov.ca.cwds.rest.resources.cms;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import javax.ws.rs.core.Response;

import org.hamcrest.junit.ExpectedException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import gov.ca.cwds.rest.api.domain.cms.CmsDocument;
import gov.ca.cwds.rest.resources.ServiceBackedResourceDelegate;
import gov.ca.cwds.rest.resources.TypedResourceDelegate;
import gov.ca.cwds.rest.services.cms.CmsDocumentService;

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

  @Mock
  private CmsDocumentService service;

  @Mock
  private gov.ca.cwds.data.persistence.cms.CmsDocument persDoc;

  @Mock
  private CmsDocument domainDoc;

  @Mock
  private TypedResourceDelegate<String, CmsDocument> resourceDelegate;

  @InjectMocks
  @Spy
  private CmsDocumentResource target; // "Class Under Test"

  @Before
  public void initMocks() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void type() throws Exception {
    assertThat(CmsDocumentResource.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    assertThat(target, notNullValue());
  }

  @Test
  public void get_A$String() throws Exception {
    final String id = "1234";
    // when(persDoc.equals(any())).thenReturn(true);
    when(resourceDelegate.get(any())).thenReturn(null);
    Response actual = target.get(id);
    Response expected = null;
    assertTrue(actual == null);
  }

}
