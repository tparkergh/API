package gov.ca.cwds.data.persistence.cms;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import gov.ca.cwds.data.persistence.PersistentObject;

/**
 * {@link PersistentObject} representing a Client Address.
 * 
 * @author CWDS API Team
 */
@Entity
@Table(name = "CL_ADDRT")
@JsonPropertyOrder(alphabetic = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public final class ClientAddress extends BaseClientAddress {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  // @OneToOne(fetch = FetchType.LAZY)
  // @JoinColumns({@JoinColumn(name = "IDENTIFIER", referencedColumnName = "FKADDRS_T")})
  // private Address address;

  /**
   * Default constructor
   * 
   * Required for Hibernate
   */
  public ClientAddress() {
    super();
  }

  // public Address getAddress() {
  // return address;
  // }
  //
  // public void setAddress(Address address) {
  // this.address = address;
  // }

}
