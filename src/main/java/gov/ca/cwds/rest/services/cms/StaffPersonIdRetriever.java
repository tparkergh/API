package gov.ca.cwds.rest.services.cms;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import gov.ca.cwds.auth.realms.PerryUserIdentity;

/**
 * @author CWDS API Team
 *
 */
public class StaffPersonIdRetriever {

  private static final String DEFAULT_STAFF_ID = "0X5";
  private static final String DEFAULT_USER_ID = "CWDST";

  StaffPersonIdRetriever() {}

  /**
   * Retrieves the Staff Person Id of the current user. Defaults to a hard-coded value if
   * authorization information is not passed in or if StaffId is not included as part of the
   * security token
   * 
   * @return the last updated id for persistence, this is the Staff Person Id of the current user
   */
  public String getStaffPersonId() {

    return getPerryUserIdentity().getStaffId();
  }

  /**
   * @return the perry user
   */
  public static PerryUserIdentity getPerryUserIdentity() {

    PerryUserIdentity userIdentity = null;

    Subject currentUser = SecurityUtils.getSubject();
    if (currentUser.getPrincipals() != null) {
      @SuppressWarnings("rawtypes")
      List principals = currentUser.getPrincipals().asList();

      if (principals.size() > 1 && principals.get(1) instanceof PerryUserIdentity) {
        PerryUserIdentity currentUserInfo = (PerryUserIdentity) principals.get(1);
        String staffPersonId = currentUserInfo.getStaffId();
        if (!StringUtils.isBlank(staffPersonId)) {
          userIdentity = currentUserInfo;
        }
      }
    }

    if (userIdentity == null) {
      String localDEvprop = System.getenv("LOCAL_DEV");
      if (StringUtils.isNoneBlank(localDEvprop) && "true".equals(localDEvprop)) {
        userIdentity = new PerryUserIdentity();
        userIdentity.setStaffId(DEFAULT_STAFF_ID);
        userIdentity.setUser(DEFAULT_USER_ID);
      }
    }
    return userIdentity;
  }

}
