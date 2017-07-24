package gov.ca.cwds.rest.business.reminders;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author CWS-NS2
 *
 */
public class ReminderHelper {

  private static final short REFERRAL_RESPONSE_TYPE_10_DAYS = (short) 1518;
  private static final short REFERRAL_RESPONSE_TYPE_3_DAYS = (short) 1516;
  private static final short REFERRAL_RESPONSE_TYPE_5_DAYS = (short) 1517;
  private static final short REFERRAL_RESPONSE_TYPE_IMMEDIATE = (short) 1520;

  private static final Logger LOGGER = LoggerFactory.getLogger(ReminderHelper.class);

  static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

  /**
   * 
   */
  private ReminderHelper() {
    // no-opt
  }

  /**
   * @param dateOfBirth - dateOfBirth
   * @param postedScreeningToReferral - postedScreeningToReferral
   * @param referral - referral
   * @param client - client
   * @param years - years
   * @return the ageDiffernce
   */
  public static int checkForAgeDiffernce(String dateOfBirth) {
    int years = 0;
    try {
      /*
       * check for the age differnce by using the Java Calender
       */
      Date dob = dateFormat.parse(dateOfBirth);
      Calendar present = Calendar.getInstance();
      Calendar past = Calendar.getInstance();
      past.setTime(dob);

      while (past.before(present)) {
        past.add(Calendar.YEAR, 1);
        if (past.before(present)) {
          years++;
        }
      }

    } catch (ParseException e) {
      LOGGER.error("Error While parsing the dateOfBirth");
    }
    return years;
  }


  /**
   * Mapped all the Referaal Response Type Code
   * 
   * @return the number of days the dueDate to set
   */
  public static Map<Short, Integer> getMap() {
    Map<Short, Integer> valueMap = new HashMap<>();
    valueMap.put(REFERRAL_RESPONSE_TYPE_10_DAYS, 10);
    valueMap.put(REFERRAL_RESPONSE_TYPE_3_DAYS, 3);
    valueMap.put(REFERRAL_RESPONSE_TYPE_5_DAYS, 5);
    valueMap.put(REFERRAL_RESPONSE_TYPE_IMMEDIATE, 1);
    return valueMap;
  }

}
