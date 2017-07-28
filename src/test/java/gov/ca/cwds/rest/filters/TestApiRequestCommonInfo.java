package gov.ca.cwds.rest.filters;

/**
 * Used for unit tests only.
 */
public class TestApiRequestCommonInfo {

  /**
   * @param racf the racf ID
   */
  public TestApiRequestCommonInfo(String racf) {
    ApiRequestCommonInfo.startRequest(racf);
  }

}
