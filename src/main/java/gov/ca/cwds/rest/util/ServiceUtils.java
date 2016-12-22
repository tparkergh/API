package gov.ca.cwds.rest.util;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import gov.ca.cwds.rest.services.ServiceException;

public class ServiceUtils {

  public static Map<String, String> extractKeyValuePairs(Serializable nameValuePairsSerializable) {
    Map<String, String> keyValuePairs = new HashMap<String, String>();
    if (!(nameValuePairsSerializable instanceof String)) {

      throw new ServiceException("Unable to read nameValuePairs as string");
    }
    try {
      String primaryKeyString = (String) nameValuePairsSerializable;

      for (String keyValueString : primaryKeyString.split(",")) {
        String[] keyValuePair = keyValueString.split("=");
        keyValuePairs.put(keyValuePair[0].trim(), keyValuePair[1].trim());
      }
      return keyValuePairs;
    } catch (Throwable e) {
      throw new ServiceException("Problem parsing name value pairs", e);
    }
  }

}
