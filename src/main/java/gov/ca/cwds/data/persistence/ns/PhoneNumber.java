package gov.ca.cwds.data.persistence.ns;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

import gov.ca.cwds.data.ns.NsPersistentObject;

/**
 * {@link NsPersistentObject} representing an Address
 * 
 * @author CWDS API Team
 */
@NamedQueries({
    @NamedQuery(name = "gov.ca.cwds.rest.api.persistence.ns.PhoneNumber.findAll",
        query = "FROM Address"),
    @NamedQuery(name = "gov.ca.cwds.rest.api.persistence.ns.PhoneNumber.findAllUpdatedAfter",
        query = "FROM Address WHERE lastUpdatedTime > :after")})
@SuppressWarnings("serial")
@Entity
@Table(name = "phone_number")
public class PhoneNumber extends NsPersistentObject {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_phone_number_id")
  @SequenceGenerator(name = "seq_phone_number_id", sequenceName = "seq_phone_number_id",
      allocationSize = 50)
  @Column(name = "phone_number_id")
  private Long id;

  @Column(name = "phone_number_value")
  private String number;

  @Column(name = "phone_type_id")
  private String type;

  /**
   * Default constructor
   * 
   * Required for Hibernate
   */
  public PhoneNumber() {

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

}
