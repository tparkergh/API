package gov.ca.cwds.data.persistence.cms;

import java.beans.Transient;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.ColumnTransformer;
import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import gov.ca.cwds.data.CmsSystemCodeDeserializer;
import gov.ca.cwds.data.ReadablePhone;
import gov.ca.cwds.data.SystemCodeSerializer;
import gov.ca.cwds.data.std.ApiAddressAware;
import gov.ca.cwds.data.std.ApiMultiplePhonesAware;
import gov.ca.cwds.data.std.ApiPhoneAware;

@MappedSuperclass
public abstract class BaseAddress extends CmsPersistentObject
    implements ApiAddressAware, ApiMultiplePhonesAware {

  /**
   * Base serialization version. Increment by class version.
   */
  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "IDENTIFIER", nullable = false, length = CMS_ID_LEN)
  protected String id;

  @Column(name = "CITY_NM", nullable = false)
  @ColumnTransformer(read = "trim(CITY_NM)")
  protected String city;

  @Column(name = "EMRG_TELNO", nullable = false)
  protected BigDecimal emergencyNumber;

  @Type(type = "integer")
  @Column(name = "EMRG_EXTNO", nullable = false)
  protected Integer emergencyExtension;

  @Column(name = "FRG_ADRT_B", nullable = false)
  protected String frgAdrtB;

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
  @ColumnTransformer(read = "trim(HEADER_ADR)")
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
  @ColumnTransformer(read = "trim(STREET_NM)")
  protected String streetName;

  @Column(name = "STREET_NO", nullable = false)
  @ColumnTransformer(read = "trim(STREET_NO)")
  protected String streetNumber;

  // TODO: legacy database stores zip code as an Integer.
  // ApiAddressAware interface requires a String type
  @Column(name = "ZIP_NO", nullable = false)
  @ColumnTransformer(read = "trim(ZIP_NO)")
  protected String zip;

  @Column(name = "ADDR_DSC", nullable = false)
  @ColumnTransformer(read = "trim(ADDR_DSC)")
  protected String addressDescription;

  @Type(type = "short")
  @Column(name = "ZIP_SFX_NO", nullable = false)
  protected Short zip4;

  @Column(name = "POSTDIR_CD", nullable = false)
  @ColumnTransformer(read = "trim(POSTDIR_CD)")
  protected String postDirCd;

  @Column(name = "PREDIR_CD", nullable = false)
  @ColumnTransformer(read = "trim(PREDIR_CD)")
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
  @ColumnTransformer(read = "trim(UNIT_NO)")
  protected String unitNumber;

  /**
   * Default constructor.
   */
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

  @Override
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

  @JsonIgnore
  @Override
  public String getState() {
    return this.state != null ? this.state.toString() : null;
  }

  public Short getStateCd() {
    return state;
  }

  public void setStateCd(Short state) {
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

  @Override
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

  public Short getZip4() {
    return zip4;
  }

  public void setZip4(Short zip4) {
    this.zip4 = zip4;
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

  public String getFrgAdrtB() {
    return frgAdrtB;
  }

  public void setFrgAdrtB(String frgAdrtB) {
    this.frgAdrtB = frgAdrtB;
  }

  public void setState(Short state) {
    this.state = state;
  }

  // =======================
  // ApiMultiplePhonesAware:
  // =======================

  @JsonIgnore
  @Override
  @Transient
  public ApiPhoneAware[] getPhones() {
    List<ApiPhoneAware> phones = new ArrayList<>();
    if (this.primaryNumber != null && !BigDecimal.ZERO.equals(this.primaryNumber)) {
      phones.add(new ReadablePhone(null, this.primaryNumber.toPlainString(),
          this.primaryExtension != null ? this.primaryExtension.toString() : null, null));
    }

    if (this.messageNumber != null && !BigDecimal.ZERO.equals(this.messageNumber)) {
      phones.add(new ReadablePhone(null, this.messageNumber.toPlainString(),
          this.messageExtension != null ? this.messageExtension.toString() : null,
          ApiPhoneAware.PhoneType.Cell));
    }

    if (this.emergencyNumber != null && !BigDecimal.ZERO.equals(this.emergencyNumber)) {
      phones.add(new ReadablePhone(null, this.emergencyNumber.toPlainString(),
          this.emergencyNumber != null ? this.emergencyNumber.toString() : null,
          ApiPhoneAware.PhoneType.Other));
    }

    return phones.toArray(new ApiPhoneAware[0]);
  }

  @Override
  public String getStreetAddress() {
    StringBuilder buf = new StringBuilder();
    buf.append(this.streetNumber).append(" ").append(this.streetName);
    if (StringUtils.isNotBlank(this.unitNumber)) {
      buf.append(" ").append(this.unitNumber);
    }
    return buf.toString();
  }

  @Override
  public String getCounty() {
    return null;
  }

  @JsonIgnore
  @Override
  public String getAddressId() {
    return this.id;
  }

}
