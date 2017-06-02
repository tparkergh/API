package gov.ca.cwds.rest.util.jni;

import gov.ca.cwds.rest.util.jni.KeyJNI.KeyDetail;

/**
 * Command line tool to call the native CWDS key generation library.
 * 
 * <h3>Command line arguments:</h3>
 * <h4>Compose/generate a key:</h4> <blockquote>
 * 
 * <pre>
 * {@code java -cp bin gov.ca.cwds.rest.util.jni.KeyCmdLine -c 0X5}.
 * </pre>
 * 
 * </blockquote>
 * 
 * <h4>Decompose a key:</h4> <blockquote>
 * 
 * <pre>
 * {@code java -cp bin gov.ca.cwds.rest.util.jni.KeyCmdLine -d OpHh4Kr0X5}.
 * </pre>
 * 
 * </blockquote>
 * 
 * <h4>JVM arguments (both cases):</h4> <blockquote>
 * 
 * <pre>
 * {@code -ea -Dcwds.jni.force=Y -Djava.library.path=.:lib/}.
 * </pre>
 * 
 * </blockquote>
 * 
 * @author CWDS API Team
 */
public final class KeyCmdLine {

  /**
   * Main method. See class javadoc for details.
   * 
   * @param args command line
   */
  public static void main(String[] args) {
    KeyJNI inst = new KeyJNI();

    if (args[0].startsWith("-d")) { // Decompose key.
      KeyDetail kd = new KeyDetail();
      inst.decomposeKey(args[1], kd);
      System.out.println("Java:\nkey=" + kd.key + "\nstaffId=" + kd.staffId + "\nUITimestamp="
          + kd.UITimestamp + "\nPTimestamp=" + kd.PTimestamp);
    } else if (args[0].startsWith("-c")) { // Compose key.
      System.out.println("Java: Call JNI generateKey ... ");
      final String key = inst.generateKey(args[1]);
      System.out.println("Generated key=" + key);
    }
  }

}
