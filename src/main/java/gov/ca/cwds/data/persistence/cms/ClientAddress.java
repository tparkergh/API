package gov.ca.cwds.data.persistence.cms;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import gov.ca.cwds.data.persistence.PersistentObject;

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
   */
  public ClientAddress(String id, Short addressType, String bkInmtId, Date effEndDt,
      Date effStartDt, String fkAddress, String fkClient, String homelessInd, String fkReferral) {
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
