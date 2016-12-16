package gov.ca.cwds.logging;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.MDC;

import com.google.inject.Inject;

import gov.ca.cwds.inject.AuditLogger;

public class AuditLoggerImpl implements gov.ca.cwds.logging.AuditLogger {
  private final Logger LOGGER;

  public static final String REMOTE_ADDRESS = "remoteAddress";
  public static final String USER_ID = "userid";
  public static final String SESSION_ID = "sessionid";
  public static final String REQUEST_ID = "requestid";
  public static final String UNIQUE_ID = "uniqueId";


  /**
   * Constructor
   * 
   * @param logger The logger
   */
  @Inject
  public AuditLoggerImpl(@AuditLogger Logger logger) {
    super();
    LOGGER = logger;
  }


  /*
   * (non-Javadoc)
   * 
   * @see gov.ca.cwds.logging.AuditLogger#audit(java.lang.String, java.lang.String)
   */
  @Override
  public void audit(String data) {
    LOGGER.info(data);
  }


  /*
   * (non-Javadoc)
   * 
   * @see gov.ca.cwds.logging.AuditLogger#buildMDC(java.lang.String, java.lang.String,
   * java.lang.String, java.lang.String)
   */
  @Override
  public String buildMDC(String remoteAddress, String userid, String sessionId, String requestId) {
    MDC.put(REMOTE_ADDRESS, remoteAddress);
    MDC.put(USER_ID, userid);
    MDC.put(SESSION_ID, sessionId);
    MDC.put(REQUEST_ID, requestId);
    String uniqueId = UUID.randomUUID().toString();
    MDC.put(UNIQUE_ID, uniqueId);

    return uniqueId;
  }

  /*
   * (non-Javadoc)
   * 
   * @see gov.ca.cwds.logging.AuditLogger#teardownMDC()
   */
  @Override
  public void teardownMDC() {
    MDC.clear();
  }



}

