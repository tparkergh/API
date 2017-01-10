package gov.ca.cwds.data;

import java.util.Date;

/**
 * Interface defines naming standard methods for persistence classes that represent persons. Allows
 * DAO and service classes to operate on person-aware objects without knowledge of their
 * implementation.
 * 
 * <p>
 * Writable. Provides "setter" methods for all fields.
 * </p>
 * 
 * @author CWDS API Team
 */
public interface IPersonAwareWritable extends IPersonAware {

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
