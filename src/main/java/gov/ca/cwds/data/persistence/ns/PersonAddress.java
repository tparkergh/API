package gov.ca.cwds.data.persistence.ns;

import java.io.Serializable;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.fasterxml.jackson.annotation.JsonIgnore;

import gov.ca.cwds.data.ns.NsPersistentObject;

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
public class PersonAddress extends NsPersistentObject implements Serializable {

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
    super(null, null);
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
  @JsonIgnore
  public Person getPerson() {
    return personAddressId.getPerson();
  }

  /**
   * @return the address
   */
  public Address getAddress() {
    return personAddressId.getAddress();
  }

  /**
   * {@inheritDoc}
   *
   * @see java.lang.Object#hashCode()
   */
  @Override
  public final int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this, false);
  }

  /**
   * {@inheritDoc}
   *
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public final boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj, false);
  }

}
