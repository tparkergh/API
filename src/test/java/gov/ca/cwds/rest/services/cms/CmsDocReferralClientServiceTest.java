package gov.ca.cwds.rest.services.cms;

import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.fasterxml.jackson.databind.ObjectMapper;

import gov.ca.cwds.data.cms.CmsDocumentDao;
import gov.ca.cwds.rest.util.jni.LZWEncoder;
import io.dropwizard.jackson.Jackson;

public class CmsDocReferralClientServiceTest {

  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Mock
  private LZWEncoder lzw;

  @Mock
  private SessionFactory sf;

  @InjectMocks
  private CmsDocumentDao cmsDocumentDao;

  @InjectMocks
  private CmsDocumentDao cmsDocReferralClientDao;

  @InjectMocks
  private CmsDocReferralClientService svc;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void createThrowsNotImplementedException() throws Exception {
    // thrown.expect(NotImplementedException.class);
    // CmsDocument cmsDocumentDomain = MAPPER
    // .readValue(fixture("fixtures/domain/cms/CmsDocument/valid/valid.json"), CmsDocument.class);
    // svc.create(cmsDocumentDomain);
  }

  @Test
  public void updateThrowsNotImplementedException() throws Exception {
    // thrown.expect(NotImplementedException.class);
    // CmsDocument cmsDocumentDomain = MAPPER
    // .readValue(fixture("fixtures/domain/cms/CmsDocument/valid/valid.json"), CmsDocument.class);
    // svc.update("testkey", cmsDocumentDomain);
  }

  @Test
  public void deleteThrowsNotImplementedException() throws Exception {
    // thrown.expect(NotImplementedException.class);
    // svc.delete("testkey");
  }

  // DRS: CmsDocument constructor doesn't populate blob segments directly from a base64-encoded,
  // decompressed document because that translation requires compression, which doesn't belong
  // there.;)

  // TODO: found.base64Blob is not set in persistent CmsDocument constructor
  // @Test
  // public void findReturnsCorrectCmsDocumentWhenFound() throws Exception {
  // CmsDocument expected = MAPPER
  // .readValue(fixture("fixtures/domain/cms/CmsDocument/valid/valid.json"), CmsDocument.class);
  //
  // gov.ca.cwds.rest.api.persistence.cms.CmsDocument cmsDocument =
  // new gov.ca.cwds.rest.api.persistence.cms.CmsDocument(expected);
  //
  // when(cmsDocumentDao.find("0131351421120020*JONESMF 00004")).thenReturn(cmsDocument);
  //
  // CmsDocument found = cmsDocumentService.find("0131351421120020*JONESMF 00004");
  //
  // assertThat(found, is(expected));
  // }

}
