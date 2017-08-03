package gov.ca.cwds.rest.filters;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import gov.ca.cwds.auth.realms.PerryUserIdentity;
import gov.ca.cwds.data.std.ApiObjectIdentity;

/**
 * Common information carrier for all requests. Includes the request start time stamp and user
 * information. Each request is separated by thread local.
 * 
 * @author CWDS API Team
 */
class RequestExecutionContextImpl extends ApiObjectIdentity implements RequestExecutionContext {

  /**
   * Default serialization.
   */
  private static final long serialVersionUID = 1L;

  /**
   * Context parameters
   */
  private Map<Parameter, Object> contextParameters = new HashMap<>();

  /**
   * Private constructor
   * 
   * @param userIdentity User identity
   */
  private RequestExecutionContextImpl(PerryUserIdentity userIdentity) {
    put(Parameter.REQUEST_START_TIME, new Date());
    put(Parameter.USER_IDENTITY, userIdentity);
  }

  /**
   * Store request execution parameter
   * 
   * @param parameter Parameter
   * @param value Parameter value
   */
  @Override
  public void put(Parameter parameter, Object value) {
    contextParameters.put(parameter, value);
  }

  /**
   * Retrieve request execution parameter
   * 
   * @param parameter Parameter
   * @return The parameter value
   */
  @Override
  public Object get(Parameter parameter) {
    return contextParameters.get(parameter);
  }

  /**
   * Get user id if stored.
   * 
   * @return The user id
   */
  @Override
  public String getUserId() {
    String userId = null;
    PerryUserIdentity userIdentity = (PerryUserIdentity) get(Parameter.USER_IDENTITY);
    if (userIdentity != null) {
      userId = userIdentity.getUser();
    }
    return userId;
  }

  /**
   * Get request start time if stored
   * 
   * @return The request start time
   */
  @Override
  public Date getRequestStartTime() {
    return (Date) get(Parameter.REQUEST_START_TIME);
  }

  /**
   * Servlet filter marks the start of a web request. This method is only accessible by the filters
   * package.
   * 
   */
  static void startRequest() {
    PerryUserIdentity userIdentity = new PerryUserIdentity();
    userIdentity.setUser(DEFAULT_USER_ID);

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

    RequestExecutionContextRegistry.register(new RequestExecutionContextImpl(userIdentity));
  }

  /**
   * Perform cleanup after request completion
   */
  static void stopRequest() {
    RequestExecutionContextRegistry.remove();
  }
}
