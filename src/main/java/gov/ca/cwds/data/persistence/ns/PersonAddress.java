package gov.ca.cwds.data.persistence.ns;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import gov.ca.cwds.data.ns.NsPersistentObject;
import gov.ca.cwds.data.persistence.PersistentObject;

/**
 * {@link NsPersistentObject} representing a PersonAddress
 * 
 * @author CWDS API Team
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "person_address")
@AssociationOverrides({
    @AssociationOverride(name = "personAddressId.person",
        joinColumns = @JoinColumn(name = "person_id")),
    @AssociationOverride(name = "personAddressId.address",
        joinColumns = @JoinColumn(name = "address_id"))})
public class PersonAddress implements PersistentObject {

  @EmbeddedId
  private PersonAddressId personAddressId = new PersonAddressId();

  /**
   * Default constructor
   * 
   * Required for Hibernate
   */
  public PersonAddress() {
    super();
  }

  /**
   * @param person parent person
   * @param address linked address
   */
  public PersonAddress(Person person, Address address) {
    super();
    personAddressId.setPerson(person);
    personAddressId.setAddress(address);
  }

  @Override
  public PersonAddressId getPrimaryKey() {
    return personAddressId;
  }

  @SuppressWarnings("unused")
  private void setPk(PersonAddressId pk) {
    this.personAddressId = pk;
  }

  /**
   * @param person - The person
   */
  public void setPerson(Person person) {
    getPrimaryKey().setPerson(person);
  }

  /**
   * @param address - The address
   */
  public void setAddress(Address address) {
    getPrimaryKey().setAddress(address);
  }

  /**
   * @return the person
   */

  @Transient
  public Person getPerson() {
    return personAddressId.getPerson();
  }

  /**
   * @return the address
   */
  @Transient
  public Address getAddress() {
    return personAddressId.getAddress();
  }

}
