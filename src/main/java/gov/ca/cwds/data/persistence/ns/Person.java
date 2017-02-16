package gov.ca.cwds.data.persistence.ns;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
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
@SuppressWarnings("serial")
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

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "person_address_id")
  private Address address;

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
   * @param address The address of this person
   */
  public Person(Long id, String firstName, String lastName, String gender, Date dateOfBirth,
      String ssn, Address address) {
    super();
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.gender = gender;
    this.dateOfBirth = dateOfBirth;
    this.ssn = ssn;
    this.address = address;
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
    this.address = new Address(person.getAddress(), null, null);
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
  public Address getAddress() {
    return address;
  }

  // ================
  // IPersonAware
  // ================

  // @Override
  public String getMiddleName() {
    return null;
  }

  // @Override
  public Date getBirthDate() {
    return this.getDateOfBirth();
  }

  // @Override
  public String getNameSuffix() {
    return null;
  }

  // @Override
  public String getStreetAddress() {
    return this.getAddress() != null && this.getAddress().getStreetAddress() != null
        ? this.getAddress().getStreetAddress() : null;
  }

  // @Override
  public String getCity() {
    return this.getAddress() != null && this.getAddress().getCity() != null
        ? this.getAddress().getCity() : null;
  }

  // @Override
  public String getState() {
    return this.getAddress() != null && this.getAddress().getState() != null
        ? this.getAddress().getState() : null;
  }

  // @Override
  public String getZip() {
    return this.getAddress() != null && this.getAddress().getZip() != null
        ? this.getAddress().getZip().toString() : null;
  }

  // @Override
  public String getCounty() {
    return null;
  }

}
