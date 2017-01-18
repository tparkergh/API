package gov.ca.cwds.rest.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

/**
 * @author CWDS API Team
 *
 */
public class MaskString {

  public String maskSsn(String ssn) {
    Integer length;
    Integer startPos;
    String returnSsn;


    if (StringUtils.isBlank(ssn)) {
      returnSsn = "";
      return returnSsn;
    }

    String actualString = ssn.trim();

    String ssnWithHyphen = "^(?!000)[0-9]{3}-(?!00)[0-9]{2}-(?!0000)[0-9]{4}$";
    String ssnWithOutHyphen = "^(?!000)[0-9]{3}(?!00)[0-9]{2}(?!0000)[0-9]{4}$";

    Pattern pattern1 = Pattern.compile(ssnWithHyphen);
    Pattern pattern2 = Pattern.compile(ssnWithOutHyphen);

    Matcher matcher1 = pattern1.matcher(actualString);
    Matcher matcher2 = pattern2.matcher(actualString);

    length = actualString.length();

    startPos = length - 4;

    if (matcher1.matches() || matcher2.matches()) {
      returnSsn = actualString.substring(startPos, length);
    } else {
      returnSsn = "";
    }

    return returnSsn;
  }

}
