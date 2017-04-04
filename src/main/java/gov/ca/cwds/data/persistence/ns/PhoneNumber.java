package gov.ca.cwds.data.persistence.ns;

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

import org.hibernate.annotations.NamedQuery;

import gov.ca.cwds.data.ns.NsPersistentObject;

/**
 * {@link NsPersistentObject} representing an PhoneNumber
 * 
 * @author CWDS API Team
 */

@NamedQuery(name = "gov.ca.cwds.rest.api.persistence.ns.PhoneNumber.findAll",
    query = "FROM PhoneNumber")
@NamedQuery(name = "gov.ca.cwds.rest.api.persistence.ns.PhoneNumber.findAllUpdatedAfter",
    query = "FROM PhoneNumber WHERE lastUpdatedTime > :after")

@SuppressWarnings("serial")
@Entity
@Table(name = "phone_number")
public class PhoneNumber extends NsPersistentObject {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_phone_number")
  @SequenceGenerator(name = "seq_phone_number", sequenceName = "seq_phone_number",
      allocationSize = 50)
  @Column(name = "phone_number_id")
  private Long id;

  @Column(name = "phone_number_value")
  private String number;

  @Column(name = "phone_type_id")
  private String type;

  @OneToMany(fetch = FetchType.EAGER, mappedBy = "personPhoneId.phoneNumber")
  private Set<PersonPhone> personPhone = new HashSet<>();

  @SuppressWarnings("javadoc")
  public PhoneNumber() {
    super();
  }

  /**
   * @param id - unique id
   * @param number - the phone number
   * @param type - the phone number type
   */
  public PhoneNumber(Long id, String number, String type) {
    super();
    this.id = id;
    this.number = number;
    this.type = type;
  }

  @SuppressWarnings("javadoc")
  public PhoneNumber(gov.ca.cwds.rest.api.domain.PhoneNumber domain, String lastUpdatedId,
      String createUserId) {
    super(lastUpdatedId, createUserId);
    this.number = domain.getPhoneNumber();
    this.type = domain.getPhoneType();
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
   * @return the phone number
   */
  public String getNumber() {
    return number;
  }

  /**
   * @return the phone number type
   */
  public String getType() {
    return type;
  }

  /**
   * @return the personPhone
   */
  public Set<PersonPhone> getPersonPhone() {
    return personPhone;
  }

}
