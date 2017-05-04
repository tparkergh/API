package gov.ca.cwds.data.persistence.cms;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PersistenceException;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import gov.ca.cwds.data.persistence.PersistentObject;
import gov.ca.cwds.rest.api.ApiException;
import gov.ca.cwds.rest.api.domain.DomainChef;

/**
 * {@link PersistentObject} representing a Client Address
 * 
 * @author CWDS API Team
 */
@NamedQueries({
    @NamedQuery(name = "gov.ca.cwds.data.persistence.cms.ClientAddress.findAll",
        query = "FROM ClientAddress"),
    @NamedQuery(name = "gov.ca.cwds.data.persistence.cms.ClientAddress.findAllUpdatedAfter",
        query = "FROM ClientAddress WHERE lastUpdatedTime > :after")})
@Entity
@Table(name = "CL_ADDRT")
@JsonPropertyOrder(alphabetic = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ClientAddress extends BaseClientAddress {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "FKADDRS_T", insertable = false, updatable = false)
  private Address addresses;


  /**
   * Default constructor
   * 
   * Required for Hibernate
   */
  public ClientAddress() {
    super();
  }


  /**
   * @param id The identifier
   * @param addressType The addressType
   * @param bkInmtId The bkInmtId
   * @param effEndDt The effEndDt
   * @param effStartDt The effStartDt
   * @param fkAddress The fkAddress
   * @param fkClient The fkClient
   * @param homelessInd The homelessInd
   * @param fkReferral The fkReferral
   * @param addresses The addresses
   */
  public ClientAddress(String id, Short addressType, String bkInmtId, Date effEndDt,
      Date effStartDt, String fkAddress, String fkClient, String homelessInd, String fkReferral,
      Address addresses) {
    super();
    this.id = id;
    this.addressType = addressType;
    this.bkInmtId = bkInmtId;
    this.effEndDt = effEndDt;
    this.effStartDt = effStartDt;
    this.fkAddress = fkAddress;
    this.fkClient = fkClient;
    this.homelessInd = homelessInd;
    this.fkReferral = fkReferral;
    this.addresses = addresses;
  }


  /**
   * @param id - ClientAddress Id
   * @param clientAddress - domain ClientAddress object
   * @param lastUpdateId - staff Id
   */
  public ClientAddress(String id, gov.ca.cwds.rest.api.domain.cms.ClientAddress clientAddress,
      String lastUpdateId) {
    super(lastUpdateId);
    try {
      this.id = id;

      this.addressType = clientAddress.getAddressType();
      this.bkInmtId = clientAddress.getBookingOrInmateId();
      this.effEndDt = DomainChef.uncookDateString(clientAddress.getEffectiveEndDate());
      this.effStartDt = DomainChef.uncookDateString(clientAddress.getEffectiveStartDate());
      this.fkAddress = clientAddress.getAddressId();
      this.fkClient = clientAddress.getClientId();
      this.homelessInd = clientAddress.getHomelessInd();
      this.fkReferral = clientAddress.getReferralId();
    } catch (ApiException e) {
      throw new PersistenceException(e);
    }

  }

  /**
   * @return the address
   */
  public Address getAddresses() {
    return addresses;
  }


  /**
   * {@inheritDoc}
   * 
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this, false);
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj, false);
  }

}
