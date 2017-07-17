package gov.ca.cwds.rest.services.cms;

import gov.ca.cwds.auth.realms.PerryUserIdentity;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

/**
 * @author CWDS API Team
 *
 */
public class StaffPersonIdRetriever {


  StaffPersonIdRetriever() {}

  /**
   * Retrieves the Staff Person Id of the current user. Defaults to a hard-coded value if
   * authorization information is not passed in or if StaffId is not included as part of the
   * security token
   * 
   * @return the last updated id for persistence, this is the Staff Person Id of the current user
   */
  public String getStaffPersonId() {
    String staffPersonId = "0X5";
    Subject currentUser = SecurityUtils.getSubject();
    if (currentUser.getPrincipals() != null) {
      @SuppressWarnings("rawtypes")
      List principals = currentUser.getPrincipals().asList();
      if (principals.size() > 1 && principals.get(1) instanceof PerryUserIdentity) {
        PerryUserIdentity currentUserInfo = (PerryUserIdentity) principals.get(1);
        staffPersonId =
            currentUserInfo.getStaffId() != null ? currentUserInfo.getStaffId() : staffPersonId;
      }
    }
    return staffPersonId;
  }

}
