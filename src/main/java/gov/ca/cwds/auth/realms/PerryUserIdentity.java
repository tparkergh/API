package gov.ca.cwds.auth.realms;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Custom Implementation for additional security attributes in JWT token
 * 
 * @author CWDS API Team
 *
 */
public class PerryUserIdentity extends PerryAccount {

  @JsonProperty
  private String staffId;


  public String getStaffId() {
    return staffId;
  }

  public void setStaffId(String staffId) {
    this.staffId = staffId;
  }
}
