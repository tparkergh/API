package gov.ca.cwds.rest.api.domain;

public enum LimitedAccessType {
  NONE("N", "None"),
  SEALED("R", "Sealed"),
  SENSITIVE("S", "Sensitive");

  private final String value;
  private final String description;

  private LimitedAccessType(String value, String description) {
    this.value = value;
    this.description = description;
  }

  public String getValue() {
    return value;
  }

  public String getDescription() {
    return description;
  }
}
