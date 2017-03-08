package gov.ca.cwds.data.persistence.cms;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import gov.ca.cwds.data.persistence.PersistentObject;

/**
 * {@link PersistentObject} representing a Client Address.
 * 
 * @author CWDS API Team
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "CL_ADDRT")
@JsonPropertyOrder(alphabetic = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public final class ReplicatedClientAddress extends BaseClientAddress {

  @OneToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "FKADDRS_T", referencedColumnName = "IDENTIFIER", insertable = false,
      updatable = false)
  protected Address address;

  /**
   * Default constructor
   * 
   * Required for Hibernate
   */
  public ReplicatedClientAddress() {
    super();
  }

  public Address getAddress() {
    return address;
  }

  public void setAddress(Address address) {
    this.address = address;
  }

}
