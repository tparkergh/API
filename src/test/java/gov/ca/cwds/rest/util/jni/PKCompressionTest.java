package gov.ca.cwds.rest.util.jni;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileReader;

// import org.apache.commons.compress.utils.IOUtils;
import org.flywaydb.core.internal.util.FileCopyUtils;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * JUnit tests for PK compression.
 * 
 * @author CWDS API Team
 */
public class PKCompressionTest {

  private static final Logger LOGGER = LoggerFactory.getLogger(PKCompressionTest.class);

  private static final String TEST_BASE = "/jni/pk/";
  private static final String ZIP_PK_1 = TEST_BASE + "first.pk";
  private static final String ZIP_DOC_1 = TEST_BASE + "first.doc";

  private static final String ZIP_B64_3 = TEST_BASE + "third.b64";
  private static final String ZIP_HEX_3 = TEST_BASE + "third.hex";
  private static final String ZIP_DOC_3 = TEST_BASE + "third.doc";

  private CmsPKCompressor inst;

  @Before
  public void setUpBeforeTest() throws Exception {
    this.inst = new CmsPKCompressor();
  }

  // ===================
  // DECOMPRESS:
  // ===================

  @Test
  @Ignore
  public void testDecompressFileToFile1() {
    try {
      final String src = PKCompressionTest.class.getResource(ZIP_PK_1).getPath();
      final String good = PKCompressionTest.class.getResource(ZIP_DOC_1).getPath();

      File tgt = File.createTempFile("tgt", ".pk");
      tgt.deleteOnExit();

      inst.decompressFile(src, tgt.getAbsolutePath());

      final String chkTgt = CWDSCompressionUtils.checksum(tgt);
      final String chkFirst = CWDSCompressionUtils.checksum(new File(good));

      assertTrue("PK decompression failed", chkTgt.equals(chkFirst));
    } catch (Exception e) {
      fail("Exception: " + e.getMessage());
    }
  }

  @Test
  public void testDecompressInputStream1() {
    // try {
    // final String src = PKCompressionTest.class.getResource(ZIP_PK_1).getPath();
    // final String good = PKCompressionTest.class.getResource(ZIP_DOC_1).getPath();
    //
    // final byte[] bytes = inst.decompressStream(
    // new ByteArrayInputStream(IOUtils.toByteArray(new FileInputStream(new File(src)))));
    //
    // final String chkTgt = CWDSCompressionUtils.checksum(bytes);
    // final String chkFirst = CWDSCompressionUtils.checksum(new File(good));
    //
    // assertTrue("PK decompression failed", chkTgt.equals(chkFirst));
    // } catch (Exception e) {
    // fail("Exception: " + e.getMessage());
    // }
  }

  @Test
  @Ignore
  public void testDecompressBase64Encoded3() {
    try {
      final String src = PKCompressionTest.class.getResource(ZIP_B64_3).getPath();
      final String good = PKCompressionTest.class.getResource(ZIP_DOC_3).getPath();

      final String b64 = FileCopyUtils.copyToString(new FileReader(new File(src))).trim();
      LOGGER.debug("b64 len=" + b64.length());

      final byte[] bytes = inst.decompressBase64(b64);
      LOGGER.debug("bytes len=" + bytes.length);

      final String chkTgt = CWDSCompressionUtils.checksum(bytes);
      final String chkFirst = CWDSCompressionUtils.checksum(new File(good));

      assertTrue("PK decompression failed", chkTgt.equals(chkFirst));
    } catch (Exception e) {
      fail("Exception: " + e.getMessage());
    }
  }

  @Test
  @Ignore
  public void testDecompressBase64Hex3() {
    try {
      final String src = PKCompressionTest.class.getResource(ZIP_HEX_3).getPath();
      final String good = PKCompressionTest.class.getResource(ZIP_DOC_3).getPath();

      final String hex = FileCopyUtils.copyToString(new FileReader(new File(src))).trim();
      LOGGER.debug("hex len=" + hex.length());

      final byte[] bytes = inst.decompressHex(hex);
      LOGGER.debug("bytes len=" + bytes.length);

      final String chkTgt = CWDSCompressionUtils.checksum(bytes);
      final String chkFirst = CWDSCompressionUtils.checksum(new File(good));

      assertTrue("PK decompression failed", chkTgt.equals(chkFirst));
    } catch (Exception e) {
      fail("Exception: " + e.getMessage());
    }
  }

  // ===================
  // COMPRESS:
  // ===================

  @Test
  @Ignore
  public void testCompressFile1() {
    try {
      final String src = PKCompressionTest.class.getResource(ZIP_DOC_1).getPath();
      final String good = PKCompressionTest.class.getResource(ZIP_PK_1).getPath();

      File tgt = File.createTempFile("tgt", ".doc");
      tgt.deleteOnExit();

      inst.compressFile(src, tgt.getAbsolutePath());

      final String chkTgt = CWDSCompressionUtils.checksum(tgt);
      final String chkFirst = CWDSCompressionUtils.checksum(new File(good));

      assertTrue("PK compression failed", chkTgt.equals(chkFirst));
    } catch (Exception e) {
      fail("Exception: " + e.getMessage());
    }
  }

  @Test
  public void testCompressBytes1() {
    // try {
    // final String src = PKCompressionTest.class.getResource(ZIP_DOC_1).getPath();
    // final String good = PKCompressionTest.class.getResource(ZIP_PK_1).getPath();
    //
    // final byte[] bytes =
    // inst.compressBytes(IOUtils.toByteArray(new FileInputStream(new File(src))));
    //
    // final String chkTgt = CWDSCompressionUtils.checksum(bytes);
    // final String chkFirst = CWDSCompressionUtils.checksum(new File(good));
    //
    // assertTrue("PK compression failed", chkTgt.equals(chkFirst));
    // } catch (Exception e) {
    // fail("Exception: " + e.getMessage());
    // }
  }

}
