package gov.ca.cwds.rest.api.persistence.cms;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

public class CmsDocumentBlobSegmentTest {

  private String docHandle = "doc1handle";
  private String segmentSequence = "10";
  private String docBlob = "test document blob";

  /*
   * Constructor test
   */
  @Test
  public void emtpyConstructorIsNotNull() throws Exception {
    assertThat(CmsDocumentBlobSegment.class.newInstance(), is(notNullValue()));
  }

  @Test
  public void persistenttContructorTest() throws Exception {

    CmsDocumentBlobSegment blob = new CmsDocumentBlobSegment(docHandle, segmentSequence, docBlob);
    assertThat(blob.getDocBlob(), is(equalTo(docBlob)));
    assertThat(blob.getSegmentSequence(), is(equalTo(segmentSequence)));
    assertThat(blob.getDocHandle(), is(equalTo(docHandle)));
  }

  @Test
  public void equalsHashCodeWork() {
    EqualsVerifier.forClass(CmsDocumentBlobSegment.class).suppress(Warning.NONFINAL_FIELDS)
        .verify();
  }

  @Test
  public void successGetPrimaryKey() throws Exception {
    VarargPrimaryKey pk = new VarargPrimaryKey(docHandle, segmentSequence);
    CmsDocumentBlobSegment blob = new CmsDocumentBlobSegment(docHandle, segmentSequence, docBlob);
    assertThat(blob.getPrimaryKey().toString(), is(equalTo(pk.toString())));
  }
}
