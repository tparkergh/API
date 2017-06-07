package gov.ca.cwds.rest.validation;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.ca.cwds.rest.api.domain.Participant;
import gov.ca.cwds.rest.api.domain.ScreeningToReferral;
import gov.ca.cwds.rest.api.domain.error.ErrorMessage;
import gov.ca.cwds.rest.services.ServiceException;

/**
 * @author CWDS API Team
 *
 */
public class ParticipantValidator {

  private static final Logger LOGGER = LoggerFactory.getLogger(ParticipantValidator.class);

  private static final String PERPETRATOR_ROLE = "Perpetrator";
  private static final String MANDATED_REPORTER_ROLE = "Mandated Reporter";
  private static final String NON_MANDATED_REPORTER_ROLE = "Non-mandated Reporter";
  private static final String ANONYMOUS_REPORTER_ROLE = "Anonymous Reporter";
  private static final String VICTIM_ROLE = "Victim";
  private static final String SELF_REPORTED_ROLE = "Self Reported";

  private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
  private static final DateFormat timeFormat = new SimpleDateFormat("HH:MM:SS");
  private static final DateFormat dateTimeFormat =
      new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);

  /**
   * CWS/CMS Referral must have on reporter
   */
  public static final int ALLOWED_NUMBER_OF_REPORTERS = 1;
  /**
   * CWS/CMS Referral must have at least one victim
   */
  public static final int MINIMUM_NUMBER_OF_VICTIMS = 1;
  @SuppressWarnings("javadoc")
  public static final boolean INVALID_PARTICIPANTS = false;
  @SuppressWarnings("javadoc")
  public static final boolean VALID_PARTICIPANTS = true;

  /**
   * default constructor
   */
  private ParticipantValidator() {
    // Default, no-op.

  }

  /**
   * @param scr - ScreeningToReferral object
   * @return Boolean - has valid participants
   * @throws ServiceException - throw and errors
   */
  public static Boolean hasValidParticipants(ScreeningToReferral scr) throws ServiceException {

    int reporterCount = 0;
    int victimCount = 0;

    Set<Participant> participants = scr.getParticipants();
    if (participants != null) {
      for (Participant participant : participants) {
        if (isReporterType(participant)) {
          reporterCount++;
        }
        if (hasVictimRole(participant)) {
          victimCount++;
        }
      }
    }
    // R - 00851 Reporter Creation
    // R - 00836 Self Rep Ind Limit
    // only one reporter is allowed on a referral
    if (reporterCount != ALLOWED_NUMBER_OF_REPORTERS) {
      return INVALID_PARTICIPANTS;
    }
    // R - 00851 Reporter Creation
    // one victim is required for a referral
    if (victimCount < MINIMUM_NUMBER_OF_VICTIMS) {
      return INVALID_PARTICIPANTS;
    }

    return VALID_PARTICIPANTS;
  }


  /**
   * @param str - ScreeningToReferral object
   * @return Boolean - is an anonymous reporter
   * @throws ServiceException - throw any exception
   */
  // is there an anonymous reporter participant on this screening to referral?
  public static Boolean anonymousReporter(ScreeningToReferral str) throws ServiceException {
    Set<Participant> participants;
    participants = str.getParticipants();
    if (participants != null) {
      for (Participant incomingParticipant : participants) {
        Set<String> roles = new HashSet<>(incomingParticipant.getRoles());
        if (roles != null && roles.contains(ANONYMOUS_REPORTER_ROLE)) {

          return true;
        }
      }
    }

    return false;
  }

  /**
   * @param participant - Participant
   * @return - Boolean true if Participant has reporter type role
   * @throws ServiceException - throw all Exceptions
   */
  public static Boolean isReporterType(Participant participant) throws ServiceException {

    Set<String> roles = participant.getRoles();
    if (roles != null) {
      if (roles.contains(ANONYMOUS_REPORTER_ROLE)) {
        return true;
      }
      if (roles.contains(MANDATED_REPORTER_ROLE)) {
        return true;
      }
      if (roles.contains(NON_MANDATED_REPORTER_ROLE)) {
        return true;
      }
      if (roles.contains(SELF_REPORTED_ROLE)) {
        return true;
      }
      if (roles.contains(VICTIM_ROLE) && roles.contains(NON_MANDATED_REPORTER_ROLE)) {
        return true;
      }
    }
    return false;
  }

  /**
   * @param participant - Participant
   * @return - Boolean true if Participant has perpetrator role
   * @throws ServiceException - throws all Exceptions
   */
  public static Boolean isPerpetrator(Participant participant) throws ServiceException {
    Set<String> roles = participant.getRoles();
    if (roles != null && roles.contains(PERPETRATOR_ROLE)) {

      return true;
    }
    return false;
  }

  /**
   * @param participant - Participant
   * @return Boolean - true if victim role found in participant
   * @throws ServiceException - throw any exception
   */
  public static Boolean hasVictimRole(Participant participant) throws ServiceException {
    Set<String> roles = participant.getRoles();
    if (roles != null && roles.contains(VICTIM_ROLE)) {

      return true;
    }
    return false;
  }

  /**
   * @param participant - Participant
   * @return Boolean - true if mandated reporter role found in participant
   * @throws ServiceException on arbitrary error
   */
  public static Boolean hasMandatedReporterRole(Participant participant) throws ServiceException {
    Set<String> roles = participant.getRoles();
    if (roles != null && roles.contains(MANDATED_REPORTER_ROLE)) {
      return true;
    }
    return false;
  }

  /**
   * @param participants - Participants
   * @return Boolean - true if mandated reporter role found in any of the participant
   * @throws ServiceException on arbitrary error
   */
  public static Boolean hasMandatedReporterRole(Set<Participant> participants)
      throws ServiceException {
    boolean mandatedRepoter = false;
    for (Participant p : participants) {
      mandatedRepoter = hasMandatedReporterRole(p);
      if (mandatedRepoter) {
        break;
      }
    }
    return mandatedRepoter;
  }

  /**
   * 
   * @param screeningToReferral - screeningToReferral
   * @param messages - logError messages
   * @return - timeStarted
   */
  public static String extractStartTime(ScreeningToReferral screeningToReferral,
      Set<ErrorMessage> messages) {
    String timeStarted = null;
    try {
      Date dateTime = dateTimeFormat.parse(screeningToReferral.getStartedAt());
      timeStarted = timeFormat.format(dateTime);
    } catch (ParseException e) {
      String message = " parsing Start Date/Time ";
      logError(message, e, messages);
    }
    return timeStarted;
  }

  /**
   * 
   * @param screeningToReferral - screeningToReferral
   * @param messages - logError messages
   * @return dateStarted
   */
  public static String extractStartDate(ScreeningToReferral screeningToReferral,
      Set<ErrorMessage> messages) {
    String dateStarted = null;
    try {
      Date dateTime = dateTimeFormat.parse(screeningToReferral.getStartedAt());
      dateStarted = dateFormat.format(dateTime);
    } catch (ParseException e) {
      String message = " parsing Start Date/Time ";
      logError(message, e, messages);
    }
    return dateStarted;
  }

  private static void logError(String message, Exception exception, Set<ErrorMessage> messages) {
    messages.add(new ErrorMessage(ErrorMessage.ErrorType.VALIDATION, message, ""));
    LOGGER.error(message, exception.getMessage());
  }

  /**
   * @param participant - Participant object
   * @return Boolean - has valid roles
   * @throws ServiceException - throw any exception
   */
  // check for incompatiable roles for this participant
  public static Boolean hasValidRoles(Participant participant) throws ServiceException {

    Set<String> roles = participant.getRoles();
    if (roles != null) {
      // R - 00831
      if (roles.contains(ANONYMOUS_REPORTER_ROLE) && roles.contains(SELF_REPORTED_ROLE)) {
        return false;
      }
      if (roles.contains(ANONYMOUS_REPORTER_ROLE) && roles.contains(VICTIM_ROLE)) {
        return false;
      }
      if (roles.contains(ANONYMOUS_REPORTER_ROLE) && (roles.contains(MANDATED_REPORTER_ROLE)
          || roles.contains(NON_MANDATED_REPORTER_ROLE))) {
        return false;
      }
      if (roles.contains(VICTIM_ROLE) && roles.contains(PERPETRATOR_ROLE)) {
        return false;
      }
      if (roles.contains(MANDATED_REPORTER_ROLE) && roles.contains(NON_MANDATED_REPORTER_ROLE)) {
        return false;
      }
    }
    return true;
  }

  // check for self-reported participant
  /**
   * @param participant - Participant object
   * @return Boolean - is a self reported participant
   * @throws ServiceException - throw any exception
   */
  public static Boolean selfReported(Participant participant) throws ServiceException {
    Set<String> roles = participant.getRoles();
    if (roles != null) {
      if (roles.contains(VICTIM_ROLE) && roles.contains(NON_MANDATED_REPORTER_ROLE)) {
        return true;
      }
      if (roles.contains(SELF_REPORTED_ROLE)) {
        return true;
      }
    }
    return false;
  }

  /**
   * @param str - ScreeningToReferral object
   * @param victimPersonId - Person Id of victim
   * @return - True if participant has a role of Victim
   * @throws ServiceException - throw any exception
   */
  public static Boolean isVictimParticipant(ScreeningToReferral str, long victimPersonId)
      throws ServiceException {

    if (str.getParticipants() != null) {
      Set<Participant> participants = str.getParticipants();
      for (Participant participant : participants) {
        if (participant.getId() == victimPersonId && hasVictimRole(participant)) {

          return true;
        }
      }
    }

    return false;
  }

  /**
   * @param str - ScreeningToReferral object
   * @param perpetratorPersonId - Person Id of perpetrator
   * @return - True if participant has a role of Perpetrator
   * @throws ServiceException - throw and exception
   */
  public static Boolean isPerpetratorParticipant(ScreeningToReferral str, long perpetratorPersonId)
      throws ServiceException {

    if (str.getParticipants() != null) {
      Set<Participant> participants = str.getParticipants();
      for (Participant participant : participants) {
        if (participant.getId() == perpetratorPersonId && (isPerpetrator(participant))) {

          return true;

        }
      }
    }
    return false;
  }

  /**
   * @param role - String role
   * @return - Boolean true if mandated or non mandated
   * 
   *         Do Not include Anonymous Reporter (special case of reporter)
   */
  public static Boolean roleIsReporterType(String role) {
    if (role != null && (role.equalsIgnoreCase(MANDATED_REPORTER_ROLE)
        || role.equalsIgnoreCase(NON_MANDATED_REPORTER_ROLE))) {
      return true;

    }

    return false;
  }

  /**
   * @param role - String role
   * @return - Boolean true if Victim
   */
  public static Boolean roleIsVictim(String role) {
    if (role != null && role.equalsIgnoreCase(VICTIM_ROLE)) {
      return true;
    }
    return false;
  }

  /**
   * @param role - String role
   * @return - Boolean true if perpetrator
   */
  public static Boolean roleIsPerpetrator(String role) {
    if (role != null && role.equalsIgnoreCase(PERPETRATOR_ROLE)) {
      return true;
    }
    return false;
  }

  /**
   * @param role - String role
   * @return - Boolean true if anonymouse reporter
   */
  public static Boolean roleIsAnonymousReporter(String role) {
    if (role != null && role.equalsIgnoreCase(ANONYMOUS_REPORTER_ROLE)) {

      return true;

    }
    return false;
  }

  /**
   * @param role - String role
   * @return - Boolean true if mandated reporter
   */
  public static Boolean roleIsMandatedReporter(String role) {
    if (role != null && role.equalsIgnoreCase(MANDATED_REPORTER_ROLE)) {
      return true;
    }
    return false;
  }
}
