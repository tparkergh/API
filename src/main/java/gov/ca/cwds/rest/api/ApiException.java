package gov.ca.cwds.rest.api;

@SuppressWarnings("serial")
public class ApiException extends RuntimeException {

  public ApiException() {
    super();
  }

  public ApiException(String message) {
    super(message);
  }

  public ApiException(Throwable cause) {
    super(cause);
  }

  public ApiException(String message, Throwable cause) {
    super(message, cause);
  }

  public ApiException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

}
