package gov.ca.cwds.data;

/**
 * Interface defines naming standard methods for persistence classes that represent Address. Allows
 * DAO and service classes to operate on Address-aware objects without knowledge of their
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
   * Setter for (first) street address
   * 
   * @param streetAddress (first) street address
   */
  void setStreetAddress(String streetAddress);

  /**
   * Getter for city
   * 
   * @return city
   */
  String getCity();

  /**
   * Setter for city
   * 
   * @param city city
   */
  void setCity(String city);

  /**
   * Getter for state, 2-char code
   * 
   * @return state
   */
  String getState();

  /**
   * Setter for state, 2-char code.
   * 
   * @param state state
   */
  void setState(String state);

  /**
   * Getter for 5-digit zip
   * 
   * @return zip
   */
  String getZip();

  /**
   * Setter for 5-digit zip
   * 
   * @param zip zip code
   */
  void setZip(String zip);

}
