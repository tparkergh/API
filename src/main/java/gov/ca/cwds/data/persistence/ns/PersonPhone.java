package gov.ca.cwds.data.persistence.ns;

import java.io.Serializable;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import gov.ca.cwds.data.ns.NsPersistentObject;

/**
 * {@link NsPersistentObject} representing a PersonAddress
 * 
 * @author CWDS API Team
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "person_phone")
@AssociationOverrides({
    @AssociationOverride(name = "personPhoneId.person",
        joinColumns = @JoinColumn(name = "person_id")),
    @AssociationOverride(name = "personPhoneId.phoneNumber",
        joinColumns = @JoinColumn(name = "phone_number_id"))})
public class PersonPhone extends NsPersistentObject implements Serializable {

  @EmbeddedId
  private PersonPhoneId personPhoneId = new PersonPhoneId();

  /**
   * Default constructor
   * 
   * Required for Hibernate
   */
  public PersonPhone() {
    super();
  }

  /**
   * @param person parent person
   * @param phoneNumber parent phoneNumber
   */
  public PersonPhone(Person person, PhoneNumber phoneNumber) {
    super(null, null);
    personPhoneId.setPerson(person);
    personPhoneId.setPhoneNumber(phoneNumber);
  }

  @Override
  public PersonPhoneId getPrimaryKey() {
    return personPhoneId;
  }

  @SuppressWarnings("unused")
  private void setPk(PersonPhoneId pk) {
    this.personPhoneId = pk;
  }

  /**
   * @param person - The person
   */
  public void setPerson(Person person) {
    getPrimaryKey().setPerson(person);
  }

  /**
   * @param phoneNumber - The phoneNumber
   */
  public void setPhoneNumber(PhoneNumber phoneNumber) {
    getPrimaryKey().setPhoneNumber(phoneNumber);
  }

  /**
   * @return the person
   */
  @JsonIgnore
  public Person getPerson() {
    return personPhoneId.getPerson();
  }

  /**
   * @return the address
   */
  public PhoneNumber getPhoneNumber() {
    return personPhoneId.getPhoneNumber();
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((personPhoneId == null) ? 0 : personPhoneId.hashCode());
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
    PersonPhone other = (PersonPhone) obj;
    if (personPhoneId == null) {
      if (other.personPhoneId != null)
        return false;
    } else if (!personPhoneId.equals(other.personPhoneId))
      return false;
    return true;
  }

}
