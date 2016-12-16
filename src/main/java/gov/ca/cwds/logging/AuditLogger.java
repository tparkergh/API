package gov.ca.cwds.logging;

/**
 * Interface for audit logger
 * 
 * @author CWDS API Team
 */
public interface AuditLogger {

  /**
   * Writes given message to the audit log with the given event type
   * 
   * @param data The data to audit
   *
   */
  public void audit(String data);

  /**
   * Setup the Mapped Diagnostic Context
   * 
   * @param remoteAddress The remote address
   * @param userid The user id
   * @param sessionId The session id
   * @param requestId The request id
   * 
   * @return A unique id for this request
   */
  public String buildMDC(String remoteAddress, String userid, String sessionId, String requestId);

  public void teardownMDC();

}
