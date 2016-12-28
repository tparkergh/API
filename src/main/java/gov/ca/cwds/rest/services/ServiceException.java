package gov.ca.cwds.rest.services;

/**
 * Base exception class for CWDS API service exceptions.
 * 
 * <p>
 * Indicates that an API operation has encountered a possible yet unexpected condition, rather than
 * a generic Java exception.
 * </p>
 * 
 * @author CWDS API Team
 */
@SuppressWarnings("serial")
public class ServiceException extends RuntimeException {

  /**
   * Construct an API service exception.
   */
  public ServiceException() {
    super();
  }

  /**
   * Construct with a displayable message.
   * 
   * @param message message to display
   */
  public ServiceException(String message) {
    super(message);
  }

  /**
   * Construct with a Throwable to wrap and rethrow.
   * 
   * @param cause Throwable to rethrow
   */
  public ServiceException(Throwable cause) {
    super(cause);
  }

  /**
   * Construct with a displayable message and Throwable to wrap and rethrow.
   * 
   * @param message message to display
   * @param cause Throwable to rethrow
   */
  public ServiceException(String message, Throwable cause) {
    super(message, cause);
  }

  /**
   * Construct and control stack traces.
   * 
   * @param message message to display
   * @param cause Throwable to rethrow
   * @param enableSuppression if the JVM can suppress this exception
   * @param writableStackTrace if security permits, the JVM may log the stack trace
   */
  public ServiceException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

}
