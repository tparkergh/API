package gov.ca.cwds.rest.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Masks the Social Security Number to the last 4 digits.
 * 
 * @author CWDS API Team
 */
public class MaskString {

  private static final Logger LOGGER = LoggerFactory.getLogger(SmartyStreet.class);
  private static final String SSN_WITH_HYPHEN = "^(?!000)[0-9]{3}-(?!00)[0-9]{2}-(?!0000)[0-9]{4}$";
  private static final String SSN_WITHOUT_HYPHEN =
      "^(?!000)[0-9]{3}(?!00)[0-9]{2}(?!0000)[0-9]{4}$";
  private static final Pattern patternHyphen = Pattern.compile(SSN_WITH_HYPHEN);
  private static final Pattern patternNoHyphen = Pattern.compile(SSN_WITHOUT_HYPHEN);

  /**
   * @param ssn incoming ssn
   * @return return validation
   */
  public String maskSsn(String ssn) {
    String returnSsn;

    if (StringUtils.isBlank(ssn)) {
      returnSsn = "";
      return returnSsn;
    }

    final String actualString = ssn.trim();
    Matcher matcherHyphen = patternHyphen.matcher(actualString);
    Matcher matcherNoHyphen = patternNoHyphen.matcher(actualString);

    if (matcherHyphen.matches() || matcherNoHyphen.matches()) {
      final int length = actualString.length();
      final int startPos = length - 4;
      returnSsn = actualString.substring(startPos, length);
    } else {
      returnSsn = "";
      LOGGER.error("Invalid SSN format or SSN is blank");
    }

    return returnSsn;
  }

}
