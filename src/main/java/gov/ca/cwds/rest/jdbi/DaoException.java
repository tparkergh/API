package gov.ca.cwds.rest.jdbi;

import gov.ca.cwds.rest.api.ApiException;

/**
 * Base RuntimeException of API DAO exceptions.
 * 
 * <p>
 * Indicates that an API persistence operation has encountered a possible yet unexpected condition,
 * rather than a generic Java exception.
 * </p>
 * 
 * @author CWDS API Team
 */
@SuppressWarnings("serial")
public class DaoException extends ApiException {

  /**
   * Construct a generic DAO exception.
   */
  public DaoException() {
    super();
  }

  /**
   * Construct with a displayable message.
   * 
   * @param message message to display
   */
  public DaoException(String message) {
    super(message);
  }

  /**
   * Construct with a Throwable to wrap and rethrow.
   * 
   * @param cause Throwable to rethrow
   */
  public DaoException(Throwable cause) {
    super(cause);
  }

  /**
   * Construct with a displayable message and Throwable to wrap and rethrow.
   * 
   * @param message message to display
   * @param cause Throwable to rethrow
   */
  public DaoException(String message, Throwable cause) {
    super(message, cause);
  }

  public DaoException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

}
