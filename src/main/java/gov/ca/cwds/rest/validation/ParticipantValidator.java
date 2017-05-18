package gov.ca.cwds.rest.validation;

import java.util.HashSet;
import java.util.Set;

import gov.ca.cwds.rest.api.domain.Participant;
import gov.ca.cwds.rest.api.domain.ScreeningToReferral;
import gov.ca.cwds.rest.services.ServiceException;

/**
 * @author CWDS API Team
 *
 */
public class ParticipantValidator {
  private static final String PERPETRATOR_ROLE = "Perpetrator";
  private static final String MANDATED_REPORTER_ROLE = "Mandated Reporter";
  private static final String NON_MANDATED_REPORTER_ROLE = "Non-mandated Reporter";
  private static final String ANONYMOUS_REPORTER_ROLE = "Anonymous Reporter";
  private static final String VICTIM_ROLE = "Victim";
  private static final String SELF_REPORTED_ROLE = "Self Reported";

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
    if (reporterCount != 1) {
      return false;
    }
    // R - 00851 Reporter Creation
    // one and only one victim is allowed on a referral
    if (victimCount != 1) {
      return false;
    }

    return true;
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
