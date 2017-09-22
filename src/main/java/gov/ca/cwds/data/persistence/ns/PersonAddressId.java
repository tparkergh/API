package gov.ca.cwds.data.persistence.ns;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author CWDS API Team
 * 
 *         This class is the refernce to {@link gov.ca.cwds.data.persistence.ns.PersonAddress}
 *
 */
@Embeddable
public class PersonAddressId implements Serializable {

  /**
   * Base serialization value. Increment by version
   */
  private static final long serialVersionUID = 1L;

  @JsonIgnore
  @ManyToOne(cascade = CascadeType.ALL)
  private Person person;

  @ManyToOne(cascade = CascadeType.ALL)
  private Address address;

  /**
   * Default constructor
   */
  public PersonAddressId() {
    super();
  }

  /**
   * @return the person
   */
  @JsonIgnore
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
