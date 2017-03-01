package gov.ca.cwds.data.persistence.cms;

import java.math.BigDecimal;

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
public abstract class BaseAddress extends CmsPersistentObject {

  /**
   * Base serialization version. Increment by class version.
   */
  private static final long serialVersionUID = 1L;

  private static final Logger LOGGER = LoggerFactory.getLogger(BaseAddress.class);

  @Id
  @Column(name = "IDENTIFIER", nullable = false, length = CMS_ID_LEN)
  protected String id;

  @Column(name = "CITY_NM", nullable = false)
  protected String city;

  @Column(name = "EMRG_TELNO", nullable = false)
  protected BigDecimal emergencyNumber;

  @Type(type = "integer")
  @Column(name = "EMRG_EXTNO", nullable = false)
  protected Integer emergencyExtension;

  @Column(name = "FRG_ADRT_B", nullable = false)
  protected String FRG_ADRT_B;

  @SystemCodeSerializer(logical = true, description = true)
  @JsonDeserialize(using = CmsSystemCodeDeserializer.class)
  @Type(type = "short")
  @Column(name = "GVR_ENTC", nullable = false)
  protected Short governmentEntityCd;

  @Column(name = "MSG_TEL_NO", nullable = false)
  protected BigDecimal messageNumber;

  @Type(type = "integer")
  @Column(name = "MSG_EXT_NO", nullable = false)
  protected Integer messageExtension;

  @Column(name = "HEADER_ADR", nullable = false)
  protected String headerAddress;

  @Column(name = "PRM_TEL_NO", nullable = false)
  protected BigDecimal primaryNumber;

  @Type(type = "integer")
  @Column(name = "PRM_EXT_NO", nullable = false)
  protected Integer primaryExtension;

  @SystemCodeSerializer(logical = true, description = true)
  @JsonDeserialize(using = CmsSystemCodeDeserializer.class)
  @Type(type = "short")
  @Column(name = "STATE_C", nullable = false)
  protected Short state;

  @Column(name = "STREET_NM", nullable = false)
  protected String streetName;

  @Column(name = "STREET_NO", nullable = false)
  protected String streetNumber;

  @Column(name = "ZIP_NO", nullable = false)
  protected String zip;

  @Column(name = "ADDR_DSC", nullable = false)
  protected String addressDescription;

  @Type(type = "short")
  @Column(name = "ZIP_SFX_NO", nullable = false)
  protected Short zip5;

  @Column(name = "POSTDIR_CD", nullable = false)
  protected String postDirCd;

  @Column(name = "PREDIR_CD", nullable = false)
  protected String preDirCd;

  @SystemCodeSerializer(logical = true, description = true)
  @JsonDeserialize(using = CmsSystemCodeDeserializer.class)
  @Type(type = "short")
  @Column(name = "ST_SFX_C", nullable = false)
  protected Short streetSuffixCd;

  @SystemCodeSerializer(logical = true, description = true)
  @JsonDeserialize(using = CmsSystemCodeDeserializer.class)
  @Type(type = "short")
  @Column(name = "UNT_DSGC", nullable = false)
  protected Short unitDesignationCd;

  @Column(name = "UNIT_NO", nullable = false)
  protected String unitNumber;

  public BaseAddress() {
    super();
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

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public BigDecimal getEmergencyNumber() {
    return emergencyNumber;
  }

  public void setEmergencyNumber(BigDecimal emergencyNumber) {
    this.emergencyNumber = emergencyNumber;
  }

  public Integer getEmergencyExtension() {
    return emergencyExtension;
  }

  public void setEmergencyExtension(Integer emergencyExtension) {
    this.emergencyExtension = emergencyExtension;
  }

  public String getFRG_ADRT_B() {
    return FRG_ADRT_B;
  }

  public void setFRG_ADRT_B(String fRG_ADRT_B) {
    FRG_ADRT_B = fRG_ADRT_B;
  }

  public BigDecimal getMessageNumber() {
    return messageNumber;
  }

  public void setMessageNumber(BigDecimal messageNumber) {
    this.messageNumber = messageNumber;
  }

  public Integer getMessageExtension() {
    return messageExtension;
  }

  public void setMessageExtension(Integer messageExtension) {
    this.messageExtension = messageExtension;
  }

  public String getHeaderAddress() {
    return headerAddress;
  }

  public void setHeaderAddress(String headerAddress) {
    this.headerAddress = headerAddress;
  }

  public BigDecimal getPrimaryNumber() {
    return primaryNumber;
  }

  public void setPrimaryNumber(BigDecimal primaryNumber) {
    this.primaryNumber = primaryNumber;
  }

  public Integer getPrimaryExtension() {
    return primaryExtension;
  }

  public void setPrimaryExtension(Integer primaryExtension) {
    this.primaryExtension = primaryExtension;
  }

  public Short getState() {
    return state;
  }

  public void setState(Short state) {
    this.state = state;
  }

  public String getStreetName() {
    return streetName;
  }

  public void setStreetName(String streetName) {
    this.streetName = streetName;
  }

  public String getStreetNumber() {
    return streetNumber;
  }

  public void setStreetNumber(String streetNumber) {
    this.streetNumber = streetNumber;
  }

  public String getZip() {
    return zip;
  }

  public void setZip(String zip) {
    this.zip = zip;
  }

  public String getAddressDescription() {
    return addressDescription;
  }

  public void setAddressDescription(String addressDescription) {
    this.addressDescription = addressDescription;
  }

  public Short getZip5() {
    return zip5;
  }

  public void setZip5(Short zip5) {
    this.zip5 = zip5;
  }

  public String getPostDirCd() {
    return postDirCd;
  }

  public void setPostDirCd(String postDirCd) {
    this.postDirCd = postDirCd;
  }

  public String getPreDirCd() {
    return preDirCd;
  }

  public void setPreDirCd(String preDirCd) {
    this.preDirCd = preDirCd;
  }

  public Short getStreetSuffixCd() {
    return streetSuffixCd;
  }

  public void setStreetSuffixCd(Short streetSuffixCd) {
    this.streetSuffixCd = streetSuffixCd;
  }

  public Short getUnitDesignationCd() {
    return unitDesignationCd;
  }

  public void setUnitDesignationCd(Short unitDesignationCd) {
    this.unitDesignationCd = unitDesignationCd;
  }

  public String getUnitNumber() {
    return unitNumber;
  }

  public void setUnitNumber(String unitNumber) {
    this.unitNumber = unitNumber;
  }

  public Short getGovernmentEntityCd() {
    return governmentEntityCd;
  }

  public void setGovernmentEntityCd(Short governmentEntityCd) {
    this.governmentEntityCd = governmentEntityCd;
  }

}
