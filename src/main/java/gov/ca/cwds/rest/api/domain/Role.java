package gov.ca.cwds.rest.api.domain;

/**
 * @author CWDS API Team
 *
 */
public enum Role {

  /**
   * Participant roles
   */
  PERPETRATOR_ROLE("Perpetrator"),

  VICTIM_ROLE("Victim"),

  MANDATED_REPORTER_ROLE("Mandated Reporter"),

  NON_MANDATED_REPORTER_ROLE("Non-mandated Reporter"),

  ANONYMOUS_REPORTER_ROLE("Anonymous Reporter"),

  SELF_REPORTED_ROLE("Self Reported");

  private final String type;

  Role(String type) {
    this.type = type;
  }

  /**
   * @param value - role vale
   * @return the roles
   */
  public static boolean hasRole(String value) {
    if (value == null) {
      return false;
    }
    for (Role role : values()) {
      if (role.type.equals(value)) {
        return true;
      }
    }
    return false;
  }

  /**
   * @return the type
   */
  public String getType() {
    return type;
  }
}
