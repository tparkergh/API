package gov.ca.cwds.data.persistence.ns;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

/**
 * @author CWDS API Team
 * 
 *         This class is the refernce to {@link gov.ca.cwds.data.persistence.ns.PersonAddress}
 *
 */
@Embeddable
public class PersonAddressId implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  /**
   * Default constructor
   */
  public PersonAddressId() {
    super();
  }

  @ManyToOne(cascade = CascadeType.ALL)
  private Person person; // NOSONAR

  @ManyToOne(cascade = CascadeType.ALL)
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

  @SuppressWarnings("javadoc")
  public Serializable getPrimaryKey() {
    return null;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((address == null) ? 0 : address.hashCode());
    result = prime * result + ((person == null) ? 0 : person.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    PersonAddressId other = (PersonAddressId) obj;
    if (address == null) {
      if (other.address != null)
        return false;
    } else if (!address.equals(other.address))
      return false;
    if (person == null) {
      if (other.person != null)
        return false;
    } else if (!person.equals(other.person))
      return false;
    return true;
  }

}
