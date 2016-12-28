package gov.ca.cwds.rest.api;


/**
 * Base RuntimeException of CWDS API exceptions.
 * 
 * <p>
 * Indicates that an API operation has encountered a possible yet unexpected condition, rather than
 * a generic Java exception.
 * </p>
 * 
 * @author CWDS API Team
 */
@SuppressWarnings("serial")
public class ApiException extends RuntimeException {

  /**
   * Construct a generic API exception.
   */
  public ApiException() {
    super();
  }

  /**
   * Construct with a displayable message.
   * 
   * @param message message to display
   */
  public ApiException(String message) {
    super(message);
  }

  /**
   * Construct with a Throwable to wrap and rethrow.
   * 
   * @param cause Throwable to rethrow
   */
  public ApiException(Throwable cause) {
    super(cause);
  }

  /**
   * Construct with a displayable message and Throwable to wrap and rethrow.
   * 
   * @param message message to display
   * @param cause Throwable to rethrow
   */
  public ApiException(String message, Throwable cause) {
    super(message, cause);
  }

  public ApiException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

}
