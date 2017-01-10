package gov.ca.cwds.data;

/**
 * Interface defines naming standard methods for persistence classes that represent an Address.
 * Allows DAO and service classes to operate on Address-aware objects without knowledge of their
 * implementation.
 * 
 * @author CWDS API Team
 */
public interface IAddressAware {

  /**
   * Getter for (first) street address
   * 
   * @return street address
   */
  String getStreetAddress();

  /**
   * Getter for city
   * 
   * @return city
   */
  String getCity();

  /**
   * Getter for state, 2-char code
   * 
   * @return state
   */
  String getState();

  /**
   * Getter for 5-digit zip
   * 
   * @return zip
   */
  String getZip();

  /**
   * Getter for county
   * 
   * @return county
   */
  String getCounty();

}
