package gov.ca.cwds.rest.util;

import java.security.SecureRandom;

/**
 * A holder class for generating ids. A real implementation is in the works. See user story
 * #128886979
 * 
 * @author CWDS API Team
 */
public class IdGenerator {
  static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
  static SecureRandom rnd = new SecureRandom();

  public static String randomString(int len) {
    StringBuilder sb = new StringBuilder(len);
    for (int i = 0; i < len; i++)
      sb.append(AB.charAt(rnd.nextInt(AB.length())));
    return sb.toString();
  }
}
