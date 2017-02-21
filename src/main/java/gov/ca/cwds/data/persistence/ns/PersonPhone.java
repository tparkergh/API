package gov.ca.cwds.data.persistence.ns;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import gov.ca.cwds.data.persistence.PersistentObject;

/**
 * @author CWS-NS2
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "person_phone")
@AssociationOverrides({
    @AssociationOverride(name = "personPhoneId.person",
        joinColumns = @JoinColumn(name = "person_id")),
    @AssociationOverride(name = "personPhoneId.phoneNumber",
        joinColumns = @JoinColumn(name = "phone_number_id"))})
public class PersonPhone implements PersistentObject {

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
    super();
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
  @Transient
  public Person getPerson() {
    return personPhoneId.getPerson();
  }

  /**
   * @return the address
   */
  @Transient
  public PhoneNumber getPhoneNumber() {
    return personPhoneId.getPhoneNumber();
  }

}
