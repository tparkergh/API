package gov.ca.cwds.data.persistence.cms;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * {@link CmsPersistentObject} representing a Address.
 * 
 * @author CWDS API Team
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "ADDRS_T")
@JsonPropertyOrder(alphabetic = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public final class Address extends BaseAddress {

  @OneToOne(fetch = FetchType.EAGER, mappedBy = "address")
  private ReplicatedClientAddress clientAddress;

  public Address() {
    super();
  }

  public ReplicatedClientAddress getClientAddress() {
    return clientAddress;
  }

  public void setClientAddress(ReplicatedClientAddress clientAddress) {
    this.clientAddress = clientAddress;
  }

}
