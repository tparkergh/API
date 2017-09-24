package gov.ca.cwds.data.persistence.cms;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.mock;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.junit.Test;

public class CmsDocumentTest {
  private final static DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
  private final static DateFormat tf = new SimpleDateFormat("HH:mm:ss");

  private String id = "0131351421120020*JONESMF 00004";
  private Short segmentCount = 1234;
  private Long docLength = (long) 1000;
  private String docAuth = "RAMESHA";
  private String docServ = "D7706001";
  private String docDate = "2007-01-31";
  private String docTime = "19:59:07";
  private String docName = "1234";
  private String compressionMethod = "CWSCMP01";
  private String baseBlob = "string";

  /*
   * Constructor test
   */
  @Test
  public void emtpyConstructorIsNotNull() throws Exception {
    assertThat(CmsDocument.class.newInstance(), is(notNullValue()));
  }

  @Test
  public void domainCmsDocumentConstructorTest() throws Exception {
    gov.ca.cwds.rest.api.domain.cms.CmsDocument domain =
        new gov.ca.cwds.rest.api.domain.cms.CmsDocument(id, segmentCount, docLength, docAuth,
            docServ, docDate, docTime, docName, compressionMethod, baseBlob);
    CmsDocument persistent = new CmsDocument(domain);
    assertThat(persistent.getId(), is(equalTo(id)));
    assertThat(persistent.getSegmentCount(), is(equalTo(segmentCount)));
    assertThat(persistent.getDocAuth(), is(equalTo(docAuth)));
    assertThat(persistent.getDocServ(), is(equalTo(docServ)));
    assertThat(persistent.getDocDate(), is(equalTo(df.parse(docDate))));
    assertThat(persistent.getDocTime(), is(equalTo(tf.parse(docTime))));
    assertThat(persistent.getDocName(), is(equalTo(docName)));
    assertThat(persistent.getCompressionMethod(), is(equalTo(compressionMethod)));
  }

  @Test
  public void persistentCmsDocumentContructorTest() throws Exception {
    CmsDocument persistent = new CmsDocument(id, segmentCount, docLength, docAuth, docServ,
        df.parse(docDate), tf.parse(docTime), docName, compressionMethod);
    assertThat(persistent.getId(), is(equalTo(id)));
    assertThat(persistent.getSegmentCount(), is(equalTo(segmentCount)));
    assertThat(persistent.getDocAuth(), is(equalTo(docAuth)));
    assertThat(persistent.getDocServ(), is(equalTo(docServ)));
    assertThat(persistent.getDocDate(), is(equalTo(df.parse(docDate))));
    assertThat(persistent.getDocTime(), is(equalTo(tf.parse(docTime))));
    assertThat(persistent.getDocName(), is(equalTo(docName)));
    assertThat(persistent.getCompressionMethod(), is(equalTo(compressionMethod)));
  }

  @Test
  public void type() throws Exception {
    assertThat(CmsDocument.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    CmsDocument target = new CmsDocument();
    assertThat(target, notNullValue());
  }

  @Test
  public void getPrimaryKey_A$() throws Exception {
    CmsDocument target = new CmsDocument();
    Serializable actual = target.getPrimaryKey();

    Serializable expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void addBlobSegment_A$CmsDocumentBlobSegment() throws Exception {
    CmsDocument target = new CmsDocument();
    CmsDocumentBlobSegment blobSegment = mock(CmsDocumentBlobSegment.class);
    target.addBlobSegment(blobSegment);
  }

}
