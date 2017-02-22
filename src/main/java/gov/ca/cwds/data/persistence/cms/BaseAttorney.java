package gov.ca.cwds.data.persistence.cms;

import java.beans.Transient;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import gov.ca.cwds.data.CmsSystemCodeDeserializer;
import gov.ca.cwds.data.ReadablePhone;
import gov.ca.cwds.data.SystemCodeSerializer;
import gov.ca.cwds.data.std.ApiMultiplePhonesAware;
import gov.ca.cwds.data.std.ApiPersonAware;
import gov.ca.cwds.data.std.ApiPhoneAware;

@MappedSuperclass
public abstract class BaseAttorney extends CmsPersistentObject
    implements ApiPersonAware, ApiMultiplePhonesAware {

  private static final Logger LOGGER = LoggerFactory.getLogger(BaseAttorney.class);

  /**
   * Base serialization value. Increment by version.
   */
  protected static final long serialVersionUID = 1L;

  @Column(name = "ARCASS_IND")
  protected String archiveAssociationIndicator;

  @Column(name = "BUSNSS_NM")
  protected String businessName;

  @Column(name = "CITY_NM")
  protected String cityName;

  @Column(name = "CWATRY_IND")
  protected String cwsAttorneyIndicator;

  @Column(name = "EMAIL_ADDR")
  protected String emailAddress;

  @Type(type = "date")
  @Column(name = "END_DT")
  protected Date endDate;

  @Column(name = "FAX_NO")
  protected BigDecimal faxNumber;

  @Column(name = "FIRST_NM")
  protected String firstName;

  @SystemCodeSerializer(logical = true, description = true)
  @JsonDeserialize(using = CmsSystemCodeDeserializer.class)
  @Type(type = "short")
  @Column(name = "GVR_ENTC")
  protected Short governmentEntityType;

  @Id
  @Column(name = "IDENTIFIER", length = CMS_ID_LEN)
  protected String id;

  @SystemCodeSerializer(logical = true, description = true)
  @JsonDeserialize(using = CmsSystemCodeDeserializer.class)
  @Type(type = "short")
  @Column(name = "LANG_TPC")
  protected Short languageType;

  @Column(name = "LAST_NM")
  protected String lastName;

  @Type(type = "integer")
  @Column(name = "MSG_EXT_NO")
  protected Integer messagePhoneExtensionNumber;

  @Column(name = "MSG_TEL_NO")
  protected BigDecimal messagePhoneNumber;

  @Column(name = "MID_INI_NM")
  protected String middleInitialName;

  @Column(name = "NMPRFX_DSC")
  protected String namePrefixDescription;

  @Column(name = "POSTIL_DSC")
  protected String positionTitleDescription;

  @Type(type = "integer")
  @Column(name = "PRM_EXT_NO")
  protected Integer primaryPhoneExtensionNumber;

  @Column(name = "PRM_TEL_NO")
  protected BigDecimal primaryPhoneNumber;

  @SystemCodeSerializer(logical = true, description = true)
  @JsonDeserialize(using = CmsSystemCodeDeserializer.class)
  @Type(type = "short")
  @Column(name = "STATE_C")
  protected Short stateCodeType;

  @Column(name = "STREET_NM")
  protected String streetName;

  @Column(name = "STREET_NO")
  protected String streetNumber;

  @Column(name = "SUFX_TLDSC")
  protected String suffixTitleDescription;

  @Type(type = "integer")
  @Column(name = "ZIP_NO")
  protected Integer zipNumber;

  @Type(type = "short")
  @Column(name = "ZIP_SFX_NO")
  protected Short zipSuffixNumber;

  public BaseAttorney() {
    super();
  }

  public BaseAttorney(String lastUpdatedId) {
    super(lastUpdatedId);
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.data.persistence.PersistentObject#getPrimaryKey()
   */
  @JsonIgnore
  @Override
  public String getPrimaryKey() {
    return getId();
  }

  /**
   * @return the archiveAssociationIndicator
   */
  public String getArchiveAssociationIndicator() {
    return StringUtils.trimToEmpty(archiveAssociationIndicator);
  }

  /**
   * @return the businessName
   */
  public String getBusinessName() {
    return StringUtils.trimToEmpty(businessName);
  }

  /**
   * @return the cityName
   */
  public String getCityName() {
    return StringUtils.trimToEmpty(cityName);
  }

  /**
   * @return the cwsAttorneyIndicator
   */
  public String getCwsAttorneyIndicator() {
    return StringUtils.trimToEmpty(cwsAttorneyIndicator);
  }

  /**
   * @return the emailAddress
   */
  public String getEmailAddress() {
    return StringUtils.trimToEmpty(emailAddress);
  }

  /**
   * @return the endDate
   */
  public Date getEndDate() {
    return endDate;
  }

  /**
   * @return the faxNumber
   */
  public BigDecimal getFaxNumber() {
    return faxNumber;
  }

  /**
   * @return the firstName
   */
  @Override
  public String getFirstName() {
    return StringUtils.trimToEmpty(firstName);
  }

  /**
   * @return the governmentEntityType
   */
  public Short getGovernmentEntityType() {
    return governmentEntityType;
  }

  /**
   * @return the id
   */
  public String getId() {
    return StringUtils.trimToEmpty(id);
  }

  /**
   * @return the languageType
   */
  public Short getLanguageType() {
    return languageType;
  }

  /**
   * @return the lastName
   */
  @Override
  public String getLastName() {
    return StringUtils.trimToEmpty(lastName);
  }

  /**
   * @return the messagePhoneExtensionNumber
   */
  public Integer getMessagePhoneExtensionNumber() {
    return messagePhoneExtensionNumber;
  }

  /**
   * @return the messagePhoneNumber
   */
  public BigDecimal getMessagePhoneNumber() {
    return messagePhoneNumber;
  }

  /**
   * @return the middleInitialName
   */
  public String getMiddleInitialName() {
    return StringUtils.trimToEmpty(middleInitialName);
  }

  /**
   * @return the namePrefixDescription
   */
  public String getNamePrefixDescription() {
    return StringUtils.trimToEmpty(namePrefixDescription);
  }

  /**
   * @return the positionTitleDescription
   */
  public String getPositionTitleDescription() {
    return StringUtils.trimToEmpty(positionTitleDescription);
  }

  /**
   * @return the primaryPhoneExtensionNumber
   */
  public Integer getPrimaryPhoneExtensionNumber() {
    return primaryPhoneExtensionNumber;
  }

  /**
   * @return the primaryPhoneNumber
   */
  public BigDecimal getPrimaryPhoneNumber() {
    return primaryPhoneNumber;
  }

  /**
   * @return the stateCodeType
   */
  public Short getStateCodeType() {
    return stateCodeType;
  }

  /**
   * @return the streetName
   */
  public String getStreetName() {
    return StringUtils.trimToEmpty(streetName);
  }

  /**
   * @return the streetNumber
   */
  public String getStreetNumber() {
    return StringUtils.trimToEmpty(streetNumber);
  }

  /**
   * @return the suffixTitleDescription
   */
  public String getSuffixTitleDescription() {
    return StringUtils.trimToEmpty(suffixTitleDescription);
  }

  /**
   * @return the zipNumber
   */
  public Integer getZipNumber() {
    return zipNumber;
  }

  /**
   * @return the zipSuffixNumber
   */
  public Short getZipSuffixNumber() {
    return zipSuffixNumber;
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.lang.Object#hashCode()
   */
  @Override
  public final int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this, false);
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public final boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj, false);
  }

  @JsonIgnore
  @Override
  @Transient
  public String getMiddleName() {
    return this.getMiddleInitialName();
  }

  @JsonIgnore
  @Override
  @Transient
  public String getGender() {
    return null;
  }

  @JsonIgnore
  @Override
  public Date getBirthDate() {
    return null;
  }

  @JsonIgnore
  @Override
  @Transient
  public String getSsn() {
    return null;
  }

  @JsonIgnore
  @Override
  @Transient
  public String getNameSuffix() {
    return null;
  }

  // =======================
  // IMultiplePhonesAware:
  // =======================

  @JsonIgnore
  @Override
  @Transient
  public ApiPhoneAware[] getPhones() {
    List<ApiPhoneAware> phones = new ArrayList<>();
    if (this.primaryPhoneNumber != null && !BigDecimal.ZERO.equals(this.primaryPhoneNumber)) {
      phones.add(new ReadablePhone(this.primaryPhoneNumber.toPlainString(),
          this.primaryPhoneExtensionNumber != null ? this.primaryPhoneExtensionNumber.toString()
              : null,
          null));
    }

    if (this.messagePhoneNumber != null && !BigDecimal.ZERO.equals(this.messagePhoneNumber)) {
      LOGGER.debug("add message phone");
      phones
          .add(new ReadablePhone(
              this.messagePhoneNumber.toPlainString(), this.messagePhoneExtensionNumber != null
                  ? this.messagePhoneExtensionNumber.toString() : null,
              ApiPhoneAware.PhoneType.Cell));
    }

    return phones.toArray(new ApiPhoneAware[0]);
  }

}
