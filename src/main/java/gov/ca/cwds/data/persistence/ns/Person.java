package gov.ca.cwds.data.persistence.ns;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;
import org.hibernate.annotations.Type;

import gov.ca.cwds.data.ns.NsPersistentObject;
import gov.ca.cwds.rest.api.domain.DomainChef;

/**
 * {@link NsPersistentObject} representing a Person.
 * 
 * @author CWDS API Team
 */
@SuppressWarnings({"serial", "javadoc"})
@NamedQueries({
    @NamedQuery(name = "gov.ca.cwds.rest.api.persistence.ns.Person.findAll", query = "FROM Person"),
    @NamedQuery(name = "gov.ca.cwds.rest.api.persistence.ns.Person.findAllUpdatedAfter",
        query = "FROM Person WHERE lastUpdatedTime > :after")})
@Entity
@Table(name = "person")
public class Person extends NsPersistentObject
// implements IPersonAware, IAddressAware
{

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_person_id")
  @SequenceGenerator(name = "seq_person_id", sequenceName = "seq_person_id", allocationSize = 50)
  @Column(name = "person_id")
  private Long id;

  @Column(name = "first_name")
  private String firstName;

  @Column(name = "last_name")
  private String lastName;

  @Column(name = "gender")
  private String gender;

  @Column(name = "date_of_birth")
  @Type(type = "date")
  private Date dateOfBirth;

  @Column(name = "ssn")
  private String ssn;

  @OneToMany(fetch = FetchType.EAGER, mappedBy = "personAddressId.person")
  private Set<PersonAddress> personAddress = new HashSet<>();

  @OneToMany(fetch = FetchType.EAGER, mappedBy = "personPhoneId.person")
  private Set<PersonPhone> personPhone = new HashSet<>();


  /**
   * Default constructor
   * 
   * Required for Hibernate
   */
  public Person() {
    super();
  }

  /**
   * Construct from all fields.
   * 
   * @param id The identifier of this row
   * @param firstName The first name
   * @param lastName The last name
   * @param gender The gender
   * @param dateOfBirth The date op birth
   * @param ssn The SSN
   * @param personAddress The address of this person
   * @param personPhone The phoneNumber of this person
   */
  public Person(Long id, String firstName, String lastName, String gender, Date dateOfBirth,
      String ssn, Set<PersonAddress> personAddress, Set<PersonPhone> personPhone) {
    super();
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.gender = gender;
    this.dateOfBirth = dateOfBirth;
    this.ssn = ssn;
    this.personAddress = personAddress;
    this.personPhone = personPhone;
  }


  /**
   * @param personAddress - The personAddress
   */
  public void addPersonAddress(PersonAddress personAddress) {
    this.personAddress.add(personAddress);
  }

  /**
   * @param personPhone - The personPhone
   */
  public void addPersonPhone(PersonPhone personPhone) {
    this.personPhone.add(personPhone);
  }

  /**
   * Constructor
   * 
   * @param person The domain object to construct this object from
   * @param lastUpdatedId the id of the last person to update this object
   * @param createUserId the id of the person created the record
   */
  public Person(gov.ca.cwds.rest.api.domain.Person person, String lastUpdatedId,
      String createUserId) {
    super(lastUpdatedId, createUserId);
    this.firstName = person.getFirstName();
    this.lastName = person.getLastName();
    this.gender = person.getGender();
    this.dateOfBirth = DomainChef.uncookDateString(person.getBirthDate());
    this.ssn = person.getSsn();
    Set<gov.ca.cwds.rest.api.domain.Address> address = person.getAddress();
    if (address != null && !address.isEmpty()) {
      for (gov.ca.cwds.rest.api.domain.Address addresses : address) {
        this.addPersonAddress(
            new PersonAddress(this, new Address(addresses, lastUpdatedId, createUserId)));
      }
    }
    Set<gov.ca.cwds.rest.api.domain.PhoneNumber> phoneNumber = person.getPhoneNumber();
    if (phoneNumber != null && !phoneNumber.isEmpty()) {
      for (gov.ca.cwds.rest.api.domain.PhoneNumber phoneNumbers : phoneNumber) {
        this.addPersonPhone(
            new PersonPhone(this, new PhoneNumber(phoneNumbers, lastUpdatedId, createUserId)));
      }
    }

  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.data.persistence.PersistentObject#getPrimaryKey()
   */
  @Override
  public Long getPrimaryKey() {
    return getId();
  }

  /**
   * @return the id
   */
  public Long getId() {
    return id;
  }

  /**
   * @return the firstName
   */
  // @Override
  public String getFirstName() {
    return firstName;
  }

  /**
   * @return the lastName
   */
  // @Override
  public String getLastName() {
    return lastName;
  }

  /**
   * @return the gender
   */
  // @Override
  public String getGender() {
    return gender;
  }

  /**
   * @return the dateOfBirth
   */
  public Date getDateOfBirth() {
    return dateOfBirth;
  }

  /**
   * @return the ssn
   */
  // @Override
  public String getSsn() {
    return ssn;
  }

  /**
   * @return the address
   */
  public Set<PersonAddress> getPersonAddress() {
    return personAddress;
  }

  /**
   * @return the Phone
   */
  public Set<PersonPhone> getPersonPhone() {
    return personPhone;
  }

  public void setPersonAddress(Set<PersonAddress> personAddress) {
    this.personAddress = personAddress;
  }

  public void setPersonPhone(Set<PersonPhone> personPhone) {
    this.personPhone = personPhone;
  }

  // ================
  // IPersonAware
  // ================


}
