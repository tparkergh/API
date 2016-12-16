package gov.ca.cwds.rest.util.jni;

import gov.ca.cwds.rest.util.jni.KeyJNI.KeyDetail;

/**
 * Command line tool to call native CWDS key generation library.
 * 
 * @author CWDS API Team
 */
public class KeyCmdLine {

  // Test Driver
  public static void main(String[] args) {
    KeyJNI inst = new KeyJNI();

    if (args[0].startsWith("-d")) {
      // ===================
      // DECOMPOSE KEY:
      // ===================

      final long startingMemory = CWDSCompressionUtils.calcMemory();
      KeyDetail kd = new KeyDetail();
      inst.decomposeKey(args[1], kd);
      System.out.println("Java: key=" + kd.key + ", staffId=" + kd.staffId + ", UITimestamp="
          + kd.UITimestamp + ", PTimestamp=" + kd.PTimestamp);
      System.out.println("used memory = " + (CWDSCompressionUtils.calcMemory() - startingMemory));
    } else {
      // ===================
      // GENERATE KEY:
      // ===================

      final long startingMemory = CWDSCompressionUtils.calcMemory();
      System.out.println("Java: Call JNI generateKey ... ");
      final String key = inst.generateKey(args[1]);
      System.out.println("Java: key=" + key);
      System.out.println("used memory = " + (CWDSCompressionUtils.calcMemory() - startingMemory));
    }
  }

}
