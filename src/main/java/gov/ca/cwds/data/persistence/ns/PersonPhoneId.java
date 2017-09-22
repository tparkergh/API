package gov.ca.cwds.data.persistence.ns;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author CWDS API Team
 * 
 *         This class is the refernce to {@link gov.ca.cwds.data.persistence.ns.PersonPhone}
 *
 */
@Embeddable
public class PersonPhoneId implements Serializable {

  /**
   * constructor
   */
  private static final long serialVersionUID = 1L;

  @ManyToOne
  private Person person;

  @ManyToOne
  private PhoneNumber phoneNumber;

  /**
   * constructor
   */
  public PersonPhoneId() {
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
