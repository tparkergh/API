package gov.ca.cwds.rest.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Masks the Social Security Number to the last 4 digits
 * 
 * @author CWDS API Team
 *
 */
public class MaskString {

  private static final Logger LOGGER = LoggerFactory.getLogger(SmartyStreet.class);
  private static final String ssnWithHyphen = "^(?!000)[0-9]{3}-(?!00)[0-9]{2}-(?!0000)[0-9]{4}$";
  private static final String ssnWithOutHyphen = "^(?!000)[0-9]{3}(?!00)[0-9]{2}(?!0000)[0-9]{4}$";
  private static final Pattern pattern1 = Pattern.compile(ssnWithHyphen);
  private static final Pattern pattern2 = Pattern.compile(ssnWithOutHyphen);

  public String maskSsn(String ssn) {
    Integer length;
    Integer startPos;
    String returnSsn;

    if (StringUtils.isBlank(ssn)) {
      returnSsn = "";
      return returnSsn;
    }

    String actualString = ssn.trim();

    Matcher matcher1 = pattern1.matcher(actualString);
    Matcher matcher2 = pattern2.matcher(actualString);

    length = actualString.length();

    startPos = length - 4;

    if (matcher1.matches() || matcher2.matches()) {
      returnSsn = actualString.substring(startPos, length);
    } else {
      returnSsn = "";
      LOGGER.error("Invalid SSN format or SSN is blank");
    }

    return returnSsn;
  }

}
