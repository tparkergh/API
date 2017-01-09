package gov.ca.cwds.data;

import java.util.Date;

/**
 * Interface defines naming standard methods for persistence classes that represent persons. Allows
 * DAO and service classes to operate on person-aware objects without knowledge of their
 * implementation.
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

  /**
   * Setter for first name
   * 
   * @param firstName first name
   */
  void setFirstName(String firstName);

  /**
   * Setter for middle name
   * 
   * @param middleName middle name
   */
  void setMiddleName(String middleName);

  /**
   * Setter for last name
   * 
   * @param lastName last name
   */
  void setLastName(String lastName);

  /**
   * Setter for gender
   * 
   * @param gender gender
   */
  void setGender(String gender);

  /**
   * Setter for birth date
   * 
   * @param birthDate birth date
   */
  void setBirthDate(Date birthDate);

  /**
   * Setter for ssn
   * 
   * @param ssn ssn
   */
  void setSsn(String ssn);

  /**
   * Setter for nameSuffix
   * 
   * @param nameSuffix name suffix
   */
  void setNameSuffix(String nameSuffix);

}
