package gov.ca.cwds.data.persistence.ns;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import gov.ca.cwds.data.ns.NsPersistentObject;

@SuppressWarnings("serial")
@Entity
@Table(name = "person_address")
public class PersonAddress extends NsPersistentObject {

  @ManyToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "person_id")
  private Person person;

  @OneToMany
  @Column(name = "address_id")
  private Address address;

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
    this.person = person;
    this.address = address;
  }

  @Override
  public Person getPrimaryKey() {
    return getPerson();
  }

  /**
   * @return the person
   */
  public Person getPerson() {
    return person;
  }

  /**
   * @return the address
   */
  public Address address() {
    return address;
  }

}
