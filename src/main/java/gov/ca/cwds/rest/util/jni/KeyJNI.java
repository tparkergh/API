package gov.ca.cwds.rest.util.jni;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.ca.cwds.data.persistence.cms.StaffPerson;

//
// http://docs.oracle.com/javase/8/docs/technotes/guides/jni/spec/functions.html
//

// COMPILE JAVA:
// javac gov/ca/cwds/rest/util/jni/KeyJNI.java

// GENERATE C HEADERS:
// javah -jni gov.ca.cwds.rest.util.jni.KeyJNI

// JAVA EXECUTE: OS X:
// java -Djava.library.path=.:/usr/local/lib/ gov.ca.cwds.rest.util.jni.KeyJNI

/**
 * Calls native CWDS key generation library via JNI.
 *
 * <p>
 * <strong>WARNING: DO NOT CHANGE METHOD SIGNATURES!</strong> Any signature change necessitates
 * regeneration of JNI headers and recompilation of shared libraries!
 * </p>
 * 
 * <p>
 * You break it, you buy it. :-)
 * </p>
 * 
 * <h2>Steps to build and run</h2>
 * 
 * @author CWDS API Team
 */
public final class KeyJNI {

  private static final Logger LOGGER = LoggerFactory.getLogger(KeyJNI.class);

  private static final boolean IS_CLASS_LOADED = loadLibs();

  /**
   * Load native library at runtime, when the classloader loads this class. Native libraries follow
   * the naming convention of the host operating system:
   * 
   * <p>
   * <ul>
   * <li>Windows: KeyJNI.dll</li>
   * <li>OS X: libKeyJNI.dylib</li>
   * <li>LinuxlibKeyJNI.so</li>
   * </ul>
   * </p>
   * 
   * @return true = native libraries load correctly
   */
  private static final boolean loadLibs() {
    LOGGER.info("user.dir={}", System.getProperty("user.dir"));
    LOGGER.info("java.library.path={}", System.getProperty("java.library.path"));

    final boolean forceLoad = "Y".equalsIgnoreCase(System.getProperty("cwds.jni.force", "N"));
    LOGGER.info("cwds.jni.force={}", forceLoad);

    boolean retval = false;

    try {
      System.loadLibrary("KeyJNI");
      retval = true;
      LOGGER.info("KeyJNI library loaded successfully");
    } catch (UnsatisfiedLinkError e) {
      retval = false;
      LOGGER.error("KeyJNI library failed to load!", e);
    }

    if (!retval && forceLoad) { // NOSONAR
      retval = true;
    }

    return retval;
  }

  /**
   * Utility struct class stores details of CWDS key decomposition.
   * 
   * <p>
   * Intentionally oversimplified structure for C++ results. Must match function signature exported
   * in shared library. <strong>DO NOT CHANGE!</strong>
   * </p>
   */
  @SuppressWarnings("javadoc")
  public static final class KeyDetail {
    public String key; // NOSONAR
    public String staffId; // NOSONAR
    public String UITimestamp; // NOSONAR
    public String PTimestamp; // NOSONAR
  }

  /**
   * Tells whether the JVM successfully loaded the shared library for the target platform.
   * 
   * @return whether the JNI shared library loaded successfully
   */
  public static final boolean isClassloaded() {
    return IS_CLASS_LOADED;
  }

  /**
   * Generates a unique key for use within CWDS, derived from a staff person id.
   * 
   * @param staffId the {@link StaffPerson}
   * @return The generated key
   */
  public native String generateKey(String staffId);

  /**
   * Decomposes a CMS, char(10), base-62 key.
   * 
   * @param key the 10 character key
   * @param kd the key detail
   */
  public native void decomposeKey(String key, KeyDetail kd);

}
