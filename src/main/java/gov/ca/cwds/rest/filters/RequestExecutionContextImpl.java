package gov.ca.cwds.rest.filters;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import gov.ca.cwds.auth.realms.PerryUserIdentity;

/**
 * Common information carrier for all requests. Includes the request start time stamp and user
 * information. Each request is separated by thread local.
 * 
 * @author CWDS API Team
 */
class RequestExecutionContextImpl implements RequestExecutionContext {

  private static final String DEFAULT_STAFF_ID = "0X5";
  private static final String DEFAULT_USER_ID = "CWDST";

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
    put(Parameter.SEQUENCE_EXTERNAL_TABLE, Integer.valueOf(0));
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
   * Get staff id if stored.
   * 
   * @return The staff id
   */
  @Override
  public String getStaffId() {
    String staffId = null;
    PerryUserIdentity userIdentity = (PerryUserIdentity) get(Parameter.USER_IDENTITY);
    if (userIdentity != null) {
      staffId = userIdentity.getStaffId();
    }
    return staffId;
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
    userIdentity.setStaffId(DEFAULT_STAFF_ID);
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

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE, true);
  }

  @Override
  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this, false);
  }

  @Override
  public boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj, false);
  }
}
