package gov.ca.cwds.rest.filters;

/**
 * Used for unit tests only.
 */
public class TestRequestExecutionContext {

  public TestRequestExecutionContext() {
    RequestExecutionContextImpl.startRequest();
  }
}
