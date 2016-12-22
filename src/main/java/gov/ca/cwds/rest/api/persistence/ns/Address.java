package gov.ca.cwds.rest.api.persistence.ns;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * {@link NsPersistentObject} representing an Address
 * 
 * @author CWDS API Team
 */
@Entity
@Table(name = "address")
public class Address extends NsPersistentObject {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_address_id")
  @SequenceGenerator(name = "seq_address_id", sequenceName = "seq_address_id", allocationSize = 50)
  @Column(name = "address_id")
  private Long id;

  @Column(name = "street_address")
  private String streetAddress;

  @Column(name = "city")
  private String city;

  @Column(name = "state")
  private String state;

  @Column(name = "zip")
  private Integer zip;

  /**
   * Default constructor
   * 
   * Required for Hibernate
   */
  public Address() {
    super();
  }

  /**
   * Constructor
   * 
   * @param id The id
   * @param streetAddress The street address
   * @param city The city
   * @param state The state
   * @param zip The zip
   */
  public Address(Long id, String streetAddress, String city, String state, Integer zip) {
    super();
    this.id = id;
    this.streetAddress = streetAddress;
    this.city = city;
    this.state = state;
    this.zip = zip;
  }

  /**
   * Constructor
   * 
   * @param address The domain object to construct this object from
   * @param lastUpdatedId the id of the last person to update this object
   */
  public Address(gov.ca.cwds.rest.api.domain.Address address, Long lastUpdatedId) {
    super(lastUpdatedId);
    this.streetAddress = address.getStreet_address();
    this.city = address.getCity();
    this.state = address.getState();
    this.zip = address.getZip();
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.api.persistence.PersistentObject#getPrimaryKey()
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
   * @return the streetAddress
   */
  public String getStreetAddress() {
    return streetAddress;
  }

  /**
   * @return the city
   */
  public String getCity() {
    return city;
  }

  /**
   * @return the state
   */
  public String getState() {
    return state;
  }

  /**
   * @return the zip
   */
  public Integer getZip() {
    return zip;
  }

}
