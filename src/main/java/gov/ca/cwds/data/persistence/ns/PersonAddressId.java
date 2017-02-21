package gov.ca.cwds.data.persistence.ns;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

import gov.ca.cwds.data.ns.NsPersistentObject;

/**
 * @author CWDS API Team
 * 
 *         This class is the refernce to {@link gov.ca.cwds.data.persistence.ns.PersonAddress}
 *
 */
@Embeddable
public class PersonAddressId extends NsPersistentObject implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  /**
   * constructor
   */
  public PersonAddressId() {
    super(null, null);
  }

  @ManyToOne
  private Person person; // NOSONAR

  @ManyToOne
  private Address address; // NOSONAR


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
   * @return the address
   */
  public Address getAddress() {
    return address;
  }

  /**
   * @param address - The address
   */
  public void setAddress(Address address) {
    this.address = address;
  }

  @Override
  public Serializable getPrimaryKey() {
    return null;
  }

}
