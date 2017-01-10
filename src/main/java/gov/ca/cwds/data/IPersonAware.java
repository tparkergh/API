package gov.ca.cwds.data;

import java.util.Date;

/**
 * Interface defines naming standard methods for persistence classes that represent persons. Allows
 * DAO and service classes to operate on person-aware objects efficiently, without possessing
 * knowledge of internal implementation details.
 * 
 * @author CWDS API Team
 */
public interface IPersonAware {

  /**
   * Getter for first name
   * 
   * @return first name
   */
  String getFirstName();

  /**
   * Getter for middle name
   * 
   * @return middle name
   */
  String getMiddleName();

  /**
   * Getter for last name
   * 
   * @return last name
   */
  String getLastName();

  /**
   * Getter for gender
   * 
   * @return gender
   */
  String getGender();

  /**
   * Getter for birth date
   * 
   * @return birth date
   */
  Date getBirthDate();

  /**
   * Getter for ssn
   * 
   * @return ssn
   */
  String getSsn();

  /**
   * Getter for name suffix
   * 
   * @return name suffix
   */
  String getNameSuffix();

}
