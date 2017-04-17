package gov.ca.cwds.data.persistence.cms;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.Type;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import gov.ca.cwds.data.CmsSystemCodeDeserializer;
import gov.ca.cwds.data.SystemCodeSerializer;

@MappedSuperclass
public abstract class BaseClientAddress extends CmsPersistentObject {

  /**
   * Base serialization version. Increment by class version.
   */
  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "IDENTIFIER", nullable = false, length = CMS_ID_LEN)
  protected String id;

  @SystemCodeSerializer(logical = true, description = true)
  @JsonDeserialize(using = CmsSystemCodeDeserializer.class)
  @Type(type = "short")
  @Column(name = "ADDR_TPC", nullable = false)
  protected Short addressType;

  @Column(name = "BK_INMT_ID", nullable = false, length = CMS_ID_LEN)
  protected String bkInmtId;

  @Type(type = "date")
  @Column(name = "EFF_END_DT", nullable = true)
  protected Date effEndDt;

  @Type(type = "date")
  @Column(name = "EFF_STRTDT", nullable = true)
  protected Date effStartDt;

  @Column(name = "FKADDRS_T", nullable = false, length = CMS_ID_LEN)
  protected String fkAddress;

  @Column(name = "FKCLIENT_T", nullable = false, length = CMS_ID_LEN)
  protected String fkClient;

  @Column(name = "HOMLES_IND", nullable = false)
  protected String homelessInd;

  @Column(name = "FKREFERL_T", nullable = true, length = CMS_ID_LEN)
  protected String fkReferral;

  /**
   * Default constructor.
   */
  public BaseClientAddress() {
    super();
  }

  /**
   * Parent constructor.
   * 
   * @param lastUpdatedId staff id who last updated this record
   */
  public BaseClientAddress(String lastUpdatedId) {
    super(lastUpdatedId);
  }

  // ==================
  // IDENTIFIERS:
  // ==================

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.data.persistence.PersistentObject#getPrimaryKey()
   */
  @Override
  public String getPrimaryKey() {
    return getId();
  }

  // ==================
  // ACCESSORS:
  // ==================

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Short getAddressType() {
    return addressType;
  }

  public void setAddressType(Short addressType) {
    this.addressType = addressType;
  }

  public String getBkInmtId() {
    return bkInmtId;
  }

  public void setBkInmtId(String bkInmtId) {
    this.bkInmtId = bkInmtId;
  }

  public String getFkAddress() {
    return fkAddress;
  }

  public void setFkAddress(String fkAddress) {
    this.fkAddress = fkAddress;
  }

  public String getFkClient() {
    return fkClient;
  }

  public void setFkClient(String fkClient) {
    this.fkClient = fkClient;
  }

  public String getHomelessInd() {
    return homelessInd;
  }

  public void setHomelessInd(String homelessInd) {
    this.homelessInd = homelessInd;
  }

  public String getFkReferral() {
    return fkReferral;
  }

  public void setFkReferral(String fkReferral) {
    this.fkReferral = fkReferral;
  }

  public Date getEffEndDt() {
    return effEndDt;
  }

  public void setEffEndDt(Date effEndDt) {
    this.effEndDt = effEndDt;
  }

  public Date getEffStartDt() {
    return effStartDt;
  }

  public void setEffStartDt(Date effStartDt) {
    this.effStartDt = effStartDt;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((addressType == null) ? 0 : addressType.hashCode());
    result = prime * result + ((bkInmtId == null) ? 0 : bkInmtId.hashCode());
    result = prime * result + ((effEndDt == null) ? 0 : effEndDt.hashCode());
    result = prime * result + ((effStartDt == null) ? 0 : effStartDt.hashCode());
    result = prime * result + ((fkAddress == null) ? 0 : fkAddress.hashCode());
    result = prime * result + ((fkClient == null) ? 0 : fkClient.hashCode());
    result = prime * result + ((fkReferral == null) ? 0 : fkReferral.hashCode());
    result = prime * result + ((homelessInd == null) ? 0 : homelessInd.hashCode());
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    BaseClientAddress other = (BaseClientAddress) obj;
    if (addressType == null) {
      if (other.addressType != null)
        return false;
    } else if (!addressType.equals(other.addressType))
      return false;
    if (bkInmtId == null) {
      if (other.bkInmtId != null)
        return false;
    } else if (!bkInmtId.equals(other.bkInmtId))
      return false;
    if (effEndDt == null) {
      if (other.effEndDt != null)
        return false;
    } else if (!effEndDt.equals(other.effEndDt))
      return false;
    if (effStartDt == null) {
      if (other.effStartDt != null)
        return false;
    } else if (!effStartDt.equals(other.effStartDt))
      return false;
    if (fkAddress == null) {
      if (other.fkAddress != null)
        return false;
    } else if (!fkAddress.equals(other.fkAddress))
      return false;
    if (fkClient == null) {
      if (other.fkClient != null)
        return false;
    } else if (!fkClient.equals(other.fkClient))
      return false;
    if (fkReferral == null) {
      if (other.fkReferral != null)
        return false;
    } else if (!fkReferral.equals(other.fkReferral))
      return false;
    if (homelessInd == null) {
      if (other.homelessInd != null)
        return false;
    } else if (!homelessInd.equals(other.homelessInd))
      return false;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    return true;
  }

}
