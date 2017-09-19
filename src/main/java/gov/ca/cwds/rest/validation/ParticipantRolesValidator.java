package gov.ca.cwds.rest.validation;

import java.util.Collection;
import java.util.Set;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import gov.ca.cwds.rest.api.domain.Participant;

/**
 * Validates that the {@code SystemCodeId.property} of a given bean must be a valid CMS system code
 * id for its system code category, {@code SystemCodeId.category}.
 * 
 * @author CWDS API Team
 */
public class ParticipantRolesValidator
    implements ConstraintValidator<ValidParticipantRoles, Collection<Participant>> {

  private static final String MANDATED_REPORTER_ROLE = "Mandated Reporter";
  private static final String NON_MANDATED_REPORTER_ROLE = "Non-mandated Reporter";
  private static final String ANONYMOUS_REPORTER_ROLE = "Anonymous Reporter";
  private static final String VICTIM_ROLE = "Victim";
  private static final String SELF_REPORTED_ROLE = "Self Reported";

  @Override
  public void initialize(ValidParticipantRoles anno) {}

  @Override
  public boolean isValid(final Collection<Participant> participants,
      ConstraintValidatorContext context) {
    boolean valid = true;

    int reporterCount = 0;
    int victimCount = 0;

    if (participants != null) {
      for (Participant participant : participants) {
        if (hasReporterRole(participant)) {
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
      valid = false;
    }

    // R - 00851 Reporter Creation
    // at least one victim is required for a referral
    if (victimCount < 1) {
      valid = false;
    }

    return valid;
  }

  private static Boolean hasReporterRole(Participant participant) {
    Set<String> roles = participant.getRoles();
    Boolean hasReporterRole = Boolean.FALSE;

    if (roles != null) {
      hasReporterRole =
          roles.contains(ANONYMOUS_REPORTER_ROLE) || roles.contains(MANDATED_REPORTER_ROLE)
              || roles.contains(NON_MANDATED_REPORTER_ROLE) || roles.contains(SELF_REPORTED_ROLE);
    }
    return hasReporterRole;
  }

  private static Boolean hasVictimRole(Participant participant) {
    Set<String> roles = participant.getRoles();
    return roles != null && roles.contains(VICTIM_ROLE);
  }

}
