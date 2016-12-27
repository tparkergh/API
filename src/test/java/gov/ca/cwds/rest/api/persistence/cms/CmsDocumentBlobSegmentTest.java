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

  private String docHandle = "0131351421120020*JONESMF 00004";
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
  public void testEmtpyConstructorIsNotNull() throws Exception {
    assertThat(CmsDocumentBlobSegment.class.newInstance(), is(notNullValue()));
  }

  @Test
  public void testPersistentCtor() throws Exception {
    CmsDocumentBlobSegment blob = new CmsDocumentBlobSegment(docHandle, segmentSequence, docBlob);
    assertThat(blob.getDocBlob(), is(equalTo(docBlob)));
    assertThat(blob.getSegmentSequence(), is(equalTo(segmentSequence)));
    assertThat(blob.getDocHandle(), is(equalTo(docHandle)));
  }

  @Test
  public void testColumnConstraintsGood() throws Exception {
    CmsDocumentBlobSegment blob = new CmsDocumentBlobSegment(docHandle, segmentSequence, docBlob);
    Set<ConstraintViolation<CmsDocumentBlobSegment>> violations = validator.validate(blob);
    assertEquals(0, violations.size());
  }

  // ====================
  // DOC_HANDLE
  // ====================

  @Test
  public void testConstraintDocHandlePattern() throws Exception {
    CmsDocumentBlobSegment blob =
        new CmsDocumentBlobSegment("123456789012345678901234567890", segmentSequence, docBlob);
    Set<ConstraintViolation<CmsDocumentBlobSegment>> violations = validator.validate(blob);

    assertEquals(1, violations.size());
    assertEquals("invalid DOC_HANDLE", violations.iterator().next().getMessage());
  }

  @Test
  public void testConstraintDocHandleNull() throws Exception {
    CmsDocumentBlobSegment blob = new CmsDocumentBlobSegment(null, segmentSequence, docBlob);
    Set<ConstraintViolation<CmsDocumentBlobSegment>> violations = validator.validate(blob);

    assertEquals(1, violations.size());
    assertEquals("may not be null", violations.iterator().next().getMessage());
  }

  @Test
  public void testConstraintDocHandleSize() throws Exception {
    CmsDocumentBlobSegment blob =
        new CmsDocumentBlobSegment("0131351421120020*JONEM 00004", segmentSequence, docBlob);
    Set<ConstraintViolation<CmsDocumentBlobSegment>> violations = validator.validate(blob);

    assertEquals(1, violations.size());
    assertEquals("size must be between 30 and 30", violations.iterator().next().getMessage());
  }

  // ====================
  // DOC_SEGMENT
  // ====================

  @Test
  public void testConstraintDocSegmentNull() throws Exception {
    CmsDocumentBlobSegment blob = new CmsDocumentBlobSegment(docHandle, null, docBlob);
    Set<ConstraintViolation<CmsDocumentBlobSegment>> violations = validator.validate(blob);

    assertEquals(1, violations.size());
    assertEquals("may not be null", violations.iterator().next().getMessage());
  }

  @Test
  public void testConstraintDocSegmentSize() throws Exception {
    CmsDocumentBlobSegment blob = new CmsDocumentBlobSegment(docHandle, "002", docBlob);
    Set<ConstraintViolation<CmsDocumentBlobSegment>> violations = validator.validate(blob);

    assertEquals(2, violations.size());
    // assertEquals("size must be between 4 and 4", violations.iterator().next().getMessage());
  }

  @Test
  public void testConstraintDocSegmentPattern() throws Exception {
    CmsDocumentBlobSegment blob = new CmsDocumentBlobSegment(docHandle, "abcd", docBlob);
    Set<ConstraintViolation<CmsDocumentBlobSegment>> violations = validator.validate(blob);

    assertEquals(1, violations.size());
    // assertEquals("invalid DOC_HANDLE", violations.iterator().next().getMessage());
  }

  // ====================
  // BLOB
  // ====================

  @Test
  public void testConstraintBlobNull() throws Exception {
    CmsDocumentBlobSegment blob = new CmsDocumentBlobSegment(docHandle, segmentSequence, null);
    Set<ConstraintViolation<CmsDocumentBlobSegment>> violations = validator.validate(blob);

    assertEquals(1, violations.size());
    assertEquals("may not be null", violations.iterator().next().getMessage());
  }

  @Test
  public void testConstraintBlobMinSize() throws Exception {
    CmsDocumentBlobSegment blob = new CmsDocumentBlobSegment(docHandle, segmentSequence, "");
    Set<ConstraintViolation<CmsDocumentBlobSegment>> violations = validator.validate(blob);

    assertEquals(1, violations.size());
    assertEquals("size must be between 1 and 4000", violations.iterator().next().getMessage());
  }

  @Test
  public void testConstraintBlobMaxSize() throws Exception {
    StringBuilder buf = new StringBuilder();
    final String alphabet = "abcefghijklmnopqrstuvwxyz";
    for (int i = 0; i < 1000; i++) {
      buf.append(alphabet);
    }

    final String theBlob = buf.toString();
    CmsDocumentBlobSegment blob = new CmsDocumentBlobSegment(docHandle, segmentSequence, theBlob);
    Set<ConstraintViolation<CmsDocumentBlobSegment>> violations = validator.validate(blob);

    assertEquals(1, violations.size());
    assertEquals("size must be between 1 and 4000", violations.iterator().next().getMessage());
  }

  @Test
  public void equalsHashCodeWork() {
    EqualsVerifier.forClass(CmsDocumentBlobSegment.class).suppress(Warning.NONFINAL_FIELDS)
        .verify();
  }

  @Test
  public void testGetPrimaryKeyGood() throws Exception {
    VarargPrimaryKey pk = new VarargPrimaryKey(docHandle, segmentSequence);
    CmsDocumentBlobSegment blob = new CmsDocumentBlobSegment(docHandle, segmentSequence, docBlob);
    assertThat(blob.getPrimaryKey().toString(), is(equalTo(pk.toString())));
  }
}
