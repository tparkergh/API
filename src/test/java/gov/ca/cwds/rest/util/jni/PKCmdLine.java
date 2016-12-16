package gov.ca.cwds.rest.util.jni;

import java.io.File;
import java.io.FileReader;

import org.flywaydb.core.internal.util.FileCopyUtils;

/**
 * Command line tool to inflate/deflate LZW-compressed documents.
 * 
 * @author CWDS API Team
 */
public class PKCmdLine {

  /**
   * Compress (deflate) a CMS PKWare archive and writes resulting decompressed document to given
   * output file.
   * 
   * <p>
   * EXAMPLE USAGE:
   * </p>
   * 
   * <pre>
   * {@code -d 6916351513091620_CWDST___00007.pk from_java_pk.doc}
   * </pre>
   *
   * <pre>
   * {@code -c from_java_pk.doc something.pk}
   * </pre>
   *
   * <pre>
   * {@code -d something.pk again.doc}
   * </pre>
   *
   * @param args Command line arguments
   */
  public static void main(String[] args) {
    try {
      CmsPKCompressor inst = new CmsPKCompressor();

      String mode = args[0];
      if ("-d".equals(mode)) { // Decompress
        inst.decompressFile(args[1], args[2]);
      } else if ("-h".equals(mode)) { // hex
        final String hex = FileCopyUtils.copyToString(new FileReader(new File(args[1]))).trim();
        System.out.println("hex len=" + hex.length());
        final byte[] bytes = inst.decompressHex(hex);
        System.out.println("bytes len = " + bytes.length);
      } else if ("-b".equals(mode)) { // Base64
        final String b64 = FileCopyUtils.copyToString(new FileReader(new File(args[1]))).trim();
        System.out.println("b64 len=" + b64.length());
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
