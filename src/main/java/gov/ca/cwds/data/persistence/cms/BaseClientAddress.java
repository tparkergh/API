package gov.ca.cwds.data.persistence.cms;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import gov.ca.cwds.data.CmsSystemCodeDeserializer;
import gov.ca.cwds.data.SystemCodeSerializer;

@MappedSuperclass
public abstract class BaseClientAddress extends CmsPersistentObject {

  /**
   * Base serialization version. Increment by class version.
   */
  private static final long serialVersionUID = 1L;

  private static final Logger LOGGER = LoggerFactory.getLogger(BaseClientAddress.class);

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

  public BaseClientAddress() {
    super();
  }

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

  @Override
  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this, false);
  }

  @Override
  public boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj, false);
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

}
