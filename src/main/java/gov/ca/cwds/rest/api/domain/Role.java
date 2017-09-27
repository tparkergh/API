package gov.ca.cwds.rest.api.domain;

import org.apache.commons.lang3.EnumUtils;

public enum Role {
  PERPETRATOR_ROLE           ("Perpetrator"),
  VICTIM_ROLE                ("Victim"),
  MANDATED_REPORTER_ROLE     ("Mandated Reporter"),
  NON_MANDATED_REPORTER_ROLE ("Non-mandated Reporter"),
  ANONYMOUS_REPORTER_ROLE    ("Anonymous Reporter"),
  SELF_REPORTED_ROLE         ("Self Reported");

  private final String type;

  Role(String type){
    this.type = type;
  }

  public static boolean hasRole(String value){
    if (value == null) {
      return false;
    }
    for(Role role : values()){
      if (role.type.equals(value)){
        return true;
      }
    }
    return false;
  }

  public String getType(){
    return type;
  }
}
