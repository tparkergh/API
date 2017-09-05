package gov.ca.cwds.helper;

import java.util.Random;

public class CmsIdGenerator {
  private final String alphabet = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZancdefghijklmnopqrstuvwxyz";
  Random r = new Random();

  public String generate(){
    StringBuffer id = new StringBuffer();

    for (int i = 0; i < 10; i++) {
      id.append(alphabet.charAt(r.nextInt(alphabet.length())));
    }
    return id.toString();
  }
}
