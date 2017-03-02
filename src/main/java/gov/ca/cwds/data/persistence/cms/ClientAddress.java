package gov.ca.cwds.data.persistence.cms;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToMany;
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

  @OneToMany(fetch = FetchType.EAGER)
  @JoinColumns({@JoinColumn(name = "IDENTIFIER", referencedColumnName = "FKADDRS_T")})
  private Set<Address> addresses = new LinkedHashSet<>();

  /**
   * Default constructor
   * 
   * Required for Hibernate
   */
  public ClientAddress() {
    super();
  }

  public Set<Address> getAddresses() {
    return addresses;
  }

  public void setAddresses(Set<Address> addresses) {
    this.addresses = addresses;
  }

  public void addAddress(final Address address) {
    this.addresses.add(address);
  }

}
