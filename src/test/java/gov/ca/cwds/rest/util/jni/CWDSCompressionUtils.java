package gov.ca.cwds.rest.util.jni;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.xml.bind.DatatypeConverter;

/**
 * Miscellaneous utilities to test compression components.
 * 
 * @author CWDS API Team
 */
public class CWDSCompressionUtils {

  /**
   * Track memory to hunt memory leaks and record overall memory consumption.
   * 
   * @return free memory in MB
   */
  public static long calcMemory() {
    Runtime runtime = Runtime.getRuntime();
    long maxMemory = runtime.maxMemory();
    long allocatedMemory = runtime.totalMemory();
    long freeMemory = runtime.freeMemory();
    return (freeMemory + (maxMemory - allocatedMemory)) / 1024L;
  }

  /**
   * Compare files when testing compression components.
   * 
   * @param iss input stream to run checksum on
   * @return hex checksum string of file bytes
   * @throws IOException if file not found or accessible
   * @throws NoSuchAlgorithmException if SHA1 algorithm not available
   */
  public static String checksum(InputStream iss) throws IOException, NoSuchAlgorithmException {
    MessageDigest md = MessageDigest.getInstance("SHA1");
    try {
      byte[] dataBytes = new byte[1024];
      int nread = 0;

      while ((nread = iss.read(dataBytes)) != -1) {
        md.update(dataBytes, 0, nread);
      }
    } catch (IOException e) {
      e.printStackTrace();
      throw e;
    } finally {
      iss.close();
    }

    return DatatypeConverter.printHexBinary(md.digest());
  }

  /**
   * Convenience overload. Compare files when testing compression components.
   * 
   * @param bytes byte array to checksum
   * @return hex checksum string of file bytes
   * @throws IOException if file not found or accessible
   * @throws NoSuchAlgorithmException if SHA1 algorithm not available
   */
  public static String checksum(final byte[] bytes) throws IOException, NoSuchAlgorithmException {
    return CWDSCompressionUtils.checksum(new ByteArrayInputStream(bytes));
  }

  /**
   * Convenience overload. Compare files when testing compression components.
   * 
   * @param str String to checksum
   * @return hex checksum string of file bytes
   * @throws IOException if file not found or accessible
   * @throws NoSuchAlgorithmException if SHA1 algorithm not available
   */
  public static String checksum(final String str) throws IOException, NoSuchAlgorithmException {
    return CWDSCompressionUtils.checksum(new ByteArrayInputStream(str.getBytes()));
  }

  /**
   * Convenience overload. Compare files when testing compression components.
   * 
   * @param file file name of file to run checksum on
   * @return hex checksum string of file bytes
   * @throws IOException if file not found or accessible
   * @throws NoSuchAlgorithmException if SHA1 algorithm not available
   */
  public static String checksum(File file) throws IOException, NoSuchAlgorithmException {
    return checksum(new FileInputStream(file));
  }

}
