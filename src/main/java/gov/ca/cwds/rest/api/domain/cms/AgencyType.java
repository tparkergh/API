package gov.ca.cwds.rest.api.domain.cms;

public enum AgencyType {

  /**
   * Government Organizations
   */
  BUREAU_OF_INDIAN_AFFAIRS((short) 1127, "Bureau of Indian Affairs"),

  COMMUNITY_CARE_LICENSING((short) 1128, "Community Care Licensing"),

  COUNTY_LICENSING((short) 2524, "County Licensing"),

  COURT((short) 1129, "Court"),

  DEPARTMENT_OF_INTERIOR((short) 1130, "Department of Interior"),

  DEPARTMENT_OF_JUSTICE((short) 1131, "Department of Justice"),

  DEPARTMENT_OF_MENTAL_HEALTH((short) 1132, "Department of Mental Health"),

  DISTRICT_ATTORNEY((short) 1133, "District Attorney"),

  ELIGIBILITY_OFFICE((short) 5316, "Eligibility Office"),

  HEALTH_DEPARTMENT((short) 1134, "Health Department"),

  PRISON((short) 1135, "Prison"),

  PROBATION((short) 2525, "Probation"),

  PUBLIC_DEFENDER_OFFICE((short) 1136, "Public Defender Office"),

  /**
   * Law Enforcement Agencies
   */
  LAW_ENFORCEMENT(null, "Law Enforcement");

  private final Short id;
  private final String description;

  private AgencyType(Short id, String description) {
    this.id = id;
    this.description = description;
  }

  public Short getId() {
    return id;
  }

  public String getDescription() {
    return description;
  }

  public static AgencyType getById(Short id) {
    for (AgencyType agencyType : values()) {
      if (agencyType.getId().equals(id)) {
        return agencyType;
      }
    }
    return null;
  }

  public static AgencyType getByName(String name) {
    return valueOf(name);
  }
}
