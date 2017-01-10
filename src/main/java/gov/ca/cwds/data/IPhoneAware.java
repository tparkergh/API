package gov.ca.cwds.data;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * Interface defines naming standard methods for persistence classes that represent phone numbers.
 * Allows DAO and service classes to operate on phone-aware objects efficiently, without possessing
 * knowledge of internal implementation details.
 * 
 * @author CWDS API Team
 */
public interface IPhoneAware {

  /**
   * Common phone types.
   * 
   * @author CWDS API Team
   */
  @JsonFormat(shape = JsonFormat.Shape.OBJECT)
  public enum PhoneType {
    Cell, Work, Home, Other
  }

  /**
   * Getter for concatenated phone number.
   * 
   * @return phone number
   */
  String getPhoneNumber();

  /**
   * Getter for phone number extension.
   * 
   * @return phone number extension
   */
  String getPhoneNumberExtension();

  /**
   * Getter for phone number type.
   * 
   * @return phone number type
   */
  PhoneType getPhoneType();

}
