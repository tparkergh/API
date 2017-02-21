package gov.ca.cwds.data.persistence.ns;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

import gov.ca.cwds.data.ns.NsPersistentObject;

/**
 * @author CWDS API Team
 * 
 *         This class is the refernce to {@link gov.ca.cwds.data.persistence.ns.PersonPhone}
 *
 */
@Embeddable
public class PersonPhoneId extends NsPersistentObject implements Serializable {

  /**
   * constructor
   */
  private static final long serialVersionUID = 1L;

  /**
   * constructor
   */
  public PersonPhoneId() {
    super(null, null);
  }

  @ManyToOne
  private Person person; // NOSONAR

  @ManyToOne
  private PhoneNumber phoneNumber; // NOSONAR


  /**
   * @return the person
   */
  public Person getPerson() {
    return person;
  }

  /**
   * @param person - The person
   */
  public void setPerson(Person person) {
    this.person = person;
  }

  /**
   * @return the phoneNumber
   */
  public PhoneNumber getPhoneNumber() {
    return phoneNumber;
  }

  /**
   * @param phoneNumber - The phoneNumber
   */
  public void setPhoneNumber(PhoneNumber phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  @Override
  public Serializable getPrimaryKey() {
    return null;
  }

}
