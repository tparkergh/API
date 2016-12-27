package gov.ca.cwds.rest.api.persistence.cms;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertEquals;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.BeforeClass;
import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

public class CmsDocumentBlobSegmentTest {

  private static Validator validator;

  private String docHandle = "doc1handle";
  private String segmentSequence = "0001";
  private String docBlob = "test document blob";

  @BeforeClass
  public static void setUp() {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
  }

  /*
   * Constructor test
   */
  @Test
  public void emtpyConstructorIsNotNull() throws Exception {
    assertThat(CmsDocumentBlobSegment.class.newInstance(), is(notNullValue()));
  }

  @Test
  public void persistentCtorTest() throws Exception {
    CmsDocumentBlobSegment blob = new CmsDocumentBlobSegment(docHandle, segmentSequence, docBlob);
    assertThat(blob.getDocBlob(), is(equalTo(docBlob)));
    assertThat(blob.getSegmentSequence(), is(equalTo(segmentSequence)));
    assertThat(blob.getDocHandle(), is(equalTo(docHandle)));
  }

  @Test
  public void testColumnConstraintsDocHandle() throws Exception {
    CmsDocumentBlobSegment blob = new CmsDocumentBlobSegment(docHandle, segmentSequence, docBlob);
    Set<ConstraintViolation<CmsDocumentBlobSegment>> violations = validator.validate(blob);

    assertEquals(1, violations.size());
    assertEquals("size must be between 30 and 30", violations.iterator().next().getMessage());
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
