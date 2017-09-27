package gov.ca.cwds.rest.validation;

import gov.ca.cwds.rest.api.domain.Role;
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

  @Override
  public void initialize(ValidParticipantRoles anno) {
    //Nothing to initialize
  }

  @Override
  public boolean isValid(final Collection<Participant> participants,
      ConstraintValidatorContext context) {
    boolean valid = true;

    int reporterCount = 0;
    int victimCount = 0;

    if (participants != null) {
      for (Participant participant : participants) {
        if(!ParticipantValidator.hasValidRoles(participant)){
          return false;
        }

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
        roles.contains(Role.ANONYMOUS_REPORTER_ROLE.getType()) ||
        roles.contains(Role .MANDATED_REPORTER_ROLE.getType()) ||
        roles.contains(Role.NON_MANDATED_REPORTER_ROLE.getType()) ||
        roles.contains(Role.SELF_REPORTED_ROLE.getType());
    }
    return hasReporterRole;
  }

  private static Boolean hasVictimRole(Participant participant) {
    Set<String> roles = participant.getRoles();
    return roles != null && roles.contains(Role.VICTIM_ROLE.getType());
  }

}
