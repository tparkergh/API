package gov.ca.cwds.rest.util.jni;

/**
 * Utility class, not a test. Command line tool to inflate/deflate LZW-compressed documents.
 * 
 * @author CWDS API Team
 */
public class LZWCmdLine {

  // Test Driver
  public static void main(String[] args) {
    LZWEncoder inst = new LZWEncoder();

    if (args[0].startsWith("-d")) { // De-compress target file.
      final long startingMemory = CWDSCompressionUtils.calcMemory();
      inst.fileCopyUncompress(args[1], args[2]);
      System.out.println("used memory = " + (CWDSCompressionUtils.calcMemory() - startingMemory));
    } else { // Compress target file.
      final long startingMemory = CWDSCompressionUtils.calcMemory();
      inst.fileCopyCompress(args[1], args[2]);
      System.out.println("used memory = " + (CWDSCompressionUtils.calcMemory() - startingMemory));
    }
  }

}
