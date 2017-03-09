package gov.ca.cwds.data.persistence.cms;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
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
@SuppressWarnings("serial")
@Entity
@Table(name = "CL_ADDRT")
@JsonPropertyOrder(alphabetic = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public final class ReplicatedClientAddress extends BaseClientAddress {

  @OneToMany(fetch = FetchType.EAGER)
  @JoinColumn(name = "IDENTIFIER", referencedColumnName = "FKADDRS_T", insertable = false,
      updatable = false, unique = false)
  protected Set<ReplicatedAddress> addresses = new LinkedHashSet<>();

  /**
   * Default constructor
   * 
   * Required for Hibernate
   */
  public ReplicatedClientAddress() {
    super();
  }

  public Set<ReplicatedAddress> getAddresses() {
    return addresses;
  }

  public void setAddresses(Set<ReplicatedAddress> addresses) {
    if (addresses != null) {
      this.addresses = addresses;
    } else {
      addresses = new LinkedHashSet<>();
    }
  }

  public void addAddress(ReplicatedAddress address) {
    if (address != null) {
      addresses.add(address);
    }
  }

  // @Override
  // public int hashCode() {
  // return HashCodeBuilder.reflectionHashCode(this, false);
  // }
  //
  // @Override
  // public boolean equals(Object obj) {
  // return EqualsBuilder.reflectionEquals(this, obj, false);
  // }

}
