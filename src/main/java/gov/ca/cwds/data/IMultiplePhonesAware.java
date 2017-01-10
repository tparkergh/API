package gov.ca.cwds.data;

/**
 * Represents an object capable of holding multiple phone numbers.
 * 
 * @author CWDS API Team
 */
public interface IMultiplePhonesAware {

  /**
   * Get all phones available on this object.
   * 
   * @return array of phones
   */
  IPhoneAware[] getPhones();

}
