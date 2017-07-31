package gov.ca.cwds.rest.util.jni;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class CmsPKCompressorTest {

  private static final String TEST_BASE = "/jni/pk/";

  private static final String ZIP_B64_3 = TEST_BASE + "third.b64";
  private static final String ZIP_HEX_3 = TEST_BASE + "third.hex";
  private static final String ZIP_DOC_3 = TEST_BASE + "third.doc";

  @Rule
  public TemporaryFolder tempFolder = new TemporaryFolder();

  File tempInputFile;
  File tempOutputFile;

  @Before
  public void setup() throws Exception {
    tempInputFile = tempFolder.newFile("tempInput.txt");
    tempOutputFile = tempFolder.newFile("tempOutput.txt");
  }

  @Test
  public void type() throws Exception {
    assertThat(CmsPKCompressor.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    CmsPKCompressor target = new CmsPKCompressor();
    assertThat(target, notNullValue());
  }

  @Test
  public void decompressFile_Args__String__String() throws Exception {
    CmsPKCompressor target = new CmsPKCompressor();
    String inputFileName = tempInputFile.getAbsolutePath();
    String outputFileName = tempOutputFile.getAbsolutePath();
    target.decompressFile(inputFileName, outputFileName);
  }

  @Test
  public void decompressFile_Args__String__String_T__IOException() throws Exception {
    CmsPKCompressor target = new CmsPKCompressor();
    String inputFileName = null;
    String outputFileName = null;
    try {
      target.decompressFile(inputFileName, outputFileName);
      fail("Expected exception was not thrown!");
    } catch (IOException e) {
    }
  }

  @Test
  public void decompressBytes_Args__byteArray() throws Exception {
    CmsPKCompressor target = new CmsPKCompressor();
    byte[] bytes = new byte[] {};
    byte[] actual = target.decompressBytes(bytes);
    byte[] expected = new byte[0];
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void decompressBytes_Args__byteArray_T__IOException() throws Exception {
    CmsPKCompressor target = new CmsPKCompressor();
    byte[] bytes = null;
    try {
      target.decompressBytes(bytes);
      fail("Expected exception was not thrown!");
    } catch (IOException e) {
    }
  }

  @Test
  public void decompressStream_Args__InputStream() throws Exception {
    CmsPKCompressor target = new CmsPKCompressor();
    InputStream input = mock(InputStream.class);
    byte[] actual = target.decompressStream(input);
    byte[] expected = new byte[0];
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void decompressStream_Args__InputStream_T__IOException() throws Exception {
    CmsPKCompressor target = new CmsPKCompressor();
    InputStream input = mock(InputStream.class);
    when(input.read()).thenThrow(new IOException("boom"));
    when(input.available()).thenThrow(new IOException("boom"));
    when(input.read(any(byte[].class))).thenThrow(new IOException("boom"));
    when(input.read(any(byte[].class), any(Integer.class), any(Integer.class)))
        .thenThrow(new IOException("boom"));

    try {
      target.decompressStream(input);
      fail("Expected exception was not thrown!");
    } catch (IOException e) {
    }
  }

  @Test
  @Ignore
  public void decompressBase64_Args__String() throws Exception {
    CmsPKCompressor target = new CmsPKCompressor();

    final String base64Doc = PKCompressionTest.class.getResource(ZIP_B64_3).getPath();
    final String good = PKCompressionTest.class.getResource(ZIP_DOC_3).getPath();
    byte[] actual = target.decompressBase64(base64Doc);

    final String chkTgt = CWDSCompressionUtils.checksum(actual);
    final String chkFirst = CWDSCompressionUtils.checksum(new File(good));

    assertTrue("Base64 decompression failed", chkTgt.equals(chkFirst));
  }

  @Test
  public void decompressBase64_Args__String_T__IOException() throws Exception {
    CmsPKCompressor target = new CmsPKCompressor();
    String base64Doc = null;
    try {
      target.decompressBase64(base64Doc);
      fail("Expected exception was not thrown!");
    } catch (IOException e) {
    }

  }

  @Test
  public void decompressHex_Args__String() throws Exception {
    CmsPKCompressor target = new CmsPKCompressor();
    String hex = "ABCDEF10";
    byte[] actual = target.decompressHex(hex);
    byte[] expected = {125, 111, -120, 22, 125, 111, -120, 16};
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void decompressHex_Args__String_T__IOException() throws Exception {
    CmsPKCompressor target = new CmsPKCompressor();
    String hex = null;
    try {
      target.decompressHex(hex);
      fail("Expected exception was not thrown!");
    } catch (IOException e) {
    }
  }

  @Test
  public void compressFile_Args__String__String() throws Exception {
    CmsPKCompressor target = new CmsPKCompressor();
    String inputFileName = tempInputFile.getAbsolutePath();
    String outputFileName = tempOutputFile.getAbsolutePath();
    target.compressFile(inputFileName, outputFileName);
  }

  @Test
  public void compressFile_Args__String__String_T__IOException() throws Exception {
    CmsPKCompressor target = new CmsPKCompressor();
    String inputFileName = null;
    String outputFileName = null;
    try {
      target.compressFile(inputFileName, outputFileName);
      fail("Expected exception was not thrown!");
    } catch (IOException e) {
    }
  }

  @Test
  public void compressBytes_Args__byteArray() throws Exception {
    CmsPKCompressor target = new CmsPKCompressor();
    byte[] bytes = new byte[] {104, 101, 108, 108, 111, 32, 119, 111, 114, 108, 100};
    byte[] actual = target.compressBytes(bytes);
    byte[] expected = new byte[] {-53, 72, -51, -55, -55, 87, 40, -49, 47, -54, 73, 1, 0};
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void compressBytes_Args__byteArray_T__IOException() throws Exception {
    CmsPKCompressor target = new CmsPKCompressor();
    byte[] bytes = null;
    try {
      target.compressBytes(bytes);
      fail("Expected exception was not thrown!");
    } catch (IOException e) {
    }
  }

}
