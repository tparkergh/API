package gov.ca.cwds.rest.api.domain;

import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import gov.ca.cwds.rest.api.ApiException;

// TODO : RDB move this to DomainChef.
public abstract class DomainObject {

  /**
   * Common date format for domain classes.
   */
  public static final String DATE_FORMAT = "yyyy-MM-dd";

  /**
   * Common timestamp format for domain classes.
   */
  public static final String TIMESTAMP_FORMAT = "yyyy-MM-dd-HH.mm.ss.SSS";

  /**
   * Common time format for domain classes.
   */
  public static final String TIME_FORMAT = "HH:mm:ss";

  protected static final String ZIP_ALL_ZEROES = "00000";
  protected static final Pattern ZIPCODE_PATTERN = Pattern.compile("0*([1-9]*)");

  public DomainObject() {}

  @Override
  public abstract boolean equals(Object obj);

  public static String cookBoolean(Boolean uncookedBoolean) {
    if (uncookedBoolean != null) {
      return Boolean.TRUE.equals(uncookedBoolean) ? "Y" : "N";
    }
    return null;
  }

  public static Boolean uncookBooleanString(String cookedBoolean) {
    if ("N".equalsIgnoreCase(cookedBoolean)) {
      return Boolean.FALSE;
    }
    if ("Y".equalsIgnoreCase(cookedBoolean)) {
      return Boolean.TRUE;
    } else if (StringUtils.trimToNull(cookedBoolean) == null) {
      return null;
    }
    throw new ApiException(new ParseException("Unable to generate boolean", 0));
  }

  public static String cookDate(Date date) {
    if (date != null) {
      DateFormat df = new SimpleDateFormat(DATE_FORMAT);
      return df.format(date);
    }
    return null;
  }

  public static String cookTimestamp(Date date) {
    if (date != null) {
      DateFormat df = new SimpleDateFormat(TIMESTAMP_FORMAT);
      return df.format(date);
    }
    return null;
  }

  public static String cookTime(Date date) {
    if (date != null) {
      DateFormat df = new SimpleDateFormat(TIME_FORMAT);
      return df.format(date);
    }
    return null;
  }

  public static Date uncookDateString(String date) {
    if (date != null) {
      try {
        DateFormat df = new SimpleDateFormat(DATE_FORMAT);
        return df.parse(date);
      } catch (Exception e) {
        throw new ApiException(e);
      }
    }
    return null;
  }

  public static Date uncookTimestampString(String timestamp) {
    if (timestamp != null) {
      try {
        DateFormat df = new SimpleDateFormat(TIMESTAMP_FORMAT);
        return df.parse(timestamp);
      } catch (Exception e) {
        throw new ApiException(e);
      }
    }
    return null;
  }

  public static Date uncookTimeString(String timestamp) {
    if (timestamp != null) {
      try {
        DateFormat df = new SimpleDateFormat(TIME_FORMAT);
        return df.parse(timestamp);
      } catch (Exception e) {
        throw new ApiException(e);
      }
    }
    return null;
  }

  public static String cookZipcodeNumber(Integer zipcodeNumber) {
    String zipcode = "";
    if (zipcodeNumber != null && zipcodeNumber > 0) {
      String draft = (ZIP_ALL_ZEROES + zipcodeNumber.toString());
      zipcode = draft.substring(draft.length() - 5, draft.length());
    }
    return zipcode;
  }

  public static Integer uncookZipcodeString(String zipcode) {
    if (StringUtils.isBlank(zipcode)) {
      return new Integer(0);
    }
    Matcher matcher = ZIPCODE_PATTERN.matcher(zipcode);
    if (matcher.matches()) {
      try {
        return Integer.parseInt(matcher.group(1));
      } catch (NumberFormatException e) {
        throw new ApiException(
            MessageFormat.format("Unable to convert zipcode to Integer - {1}", zipcode), e);
      }
    } else {
      throw new ApiException(MessageFormat.format("Unable to uncook zipcode string {1}", zipcode));
    }
  }
}
