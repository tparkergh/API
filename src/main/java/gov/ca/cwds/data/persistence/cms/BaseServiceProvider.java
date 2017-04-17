package gov.ca.cwds.data.persistence.cms;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import gov.ca.cwds.data.CmsSystemCodeDeserializer;
import gov.ca.cwds.data.SystemCodeSerializer;
import gov.ca.cwds.data.std.ApiAddressAware;
import gov.ca.cwds.data.std.ApiPersonAware;
import gov.ca.cwds.data.std.ApiPhoneAware;

@MappedSuperclass
public abstract class BaseServiceProvider extends CmsPersistentObject
    implements ApiPersonAware, ApiAddressAware, ApiPhoneAware {

  /**
   * Base serialization value. Increment by class version.
   */
  private static final long serialVersionUID = 1L;

  @Column(name = "AGENCY_NM", length = 35, nullable = false)
  protected String agencyName;

  @Column(name = "ARCASS_IND", length = 1, nullable = false)
  protected String archiveAssociationIndicator;

  @Column(name = "CITY_NM", length = 20, nullable = false)
  protected String cityName;

  @Column(name = "EMAIL_ADDR", length = 50)
  protected String emailAddress;

  @Column(name = "FAX_NO")
  protected BigDecimal faxNumber;

  @Column(name = "FIRST_NM", length = 20, nullable = false)
  protected String firstName;

  @Id
  @Column(name = "IDENTIFIER", length = CMS_ID_LEN)
  protected String id;

  @Column(name = "LAST_NM", length = 20, nullable = false)
  protected String lastName;

  @Column(name = "NMPRFX_DSC", length = 6, nullable = false)
  protected String namePrefixDescription;

  @Type(type = "integer")
  @Column(name = "TEL_EXT_NO", nullable = false)
  protected Integer phoneExtensionNumber;

  @Column(name = "PHONE_NO")
  protected BigDecimal phoneNumber;

  @Column(name = "PSTITL_DSC", length = 30, nullable = false)
  protected String positionTitleDescription;

  @Type(type = "short")
  @Column(name = "SVCPVDRC", nullable = false)
  protected Short serviceProviderType;

  @SystemCodeSerializer(logical = true, description = true)
  @JsonDeserialize(using = CmsSystemCodeDeserializer.class)
  @Type(type = "short")
  @Column(name = "STATE_C", nullable = false)
  protected Short stateCodeType;

  @Column(name = "STREET_NM", length = 40, nullable = false)
  protected String streetName;

  @Column(name = "STREET_NO", length = 10, nullable = false)
  protected String streetNumber;

  @Column(name = "SUFX_TLDSC", length = 4, nullable = false)
  protected String suffixTitleDescription;

  @Type(type = "integer")
  @Column(name = "ZIP_NM", nullable = false)
  protected Integer zipNumber;

  @Type(type = "short")
  @Column(name = "ZIP_SFX_NO", nullable = false)
  protected Short zipSuffixNumber;

  public BaseServiceProvider() {
    super();
  }

  public BaseServiceProvider(String lastUpdatedId) {
    super(lastUpdatedId);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getPrimaryKey() {
    return getId();
  }

  /**
   * @return the agencyName
   */
  public String getAgencyName() {
    return StringUtils.trimToEmpty(agencyName);
  }

  /**
   * @return the archiveAssociationIndicator
   */
  public String getArchiveAssociationIndicator() {
    return StringUtils.trimToEmpty(archiveAssociationIndicator);
  }

  /**
   * @return the cityName
   */
  public String getCityName() {
    return StringUtils.trimToEmpty(cityName);
  }

  /**
   * @return the emailAddress
   */
  public String getEmailAddress() {
    return StringUtils.trimToEmpty(emailAddress);
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
   * @return the id
   */
  public String getId() {
    return StringUtils.trimToEmpty(id);
  }

  /**
   * @return the lastName
   */
  @Override
  public String getLastName() {
    return StringUtils.trimToEmpty(lastName);
  }

  /**
   * @return the namePrefixDescription
   */
  public String getNamePrefixDescription() {
    return StringUtils.trimToEmpty(namePrefixDescription);
  }

  /**
   * @return the phoneExtensionNumber
   */
  public Integer getPhoneExtensionNumber() {
    return phoneExtensionNumber;
  }

  /**
   * @return the phoneNumber
   */
  public BigDecimal getPhoneNumberAsDecimal() {
    return phoneNumber;
  }

  /**
   * @return the phoneNumber
   */
  @Override
  public String getPhoneNumber() {
    return phoneNumber != null ? phoneNumber.toPlainString() : null;
  }

  /**
   * @return the positionTitleDescription
   */
  public String getPositionTitleDescription() {
    return StringUtils.trimToEmpty(positionTitleDescription);
  }

  /**
   * @return the serviceProviderType
   */
  public Short getServiceProviderType() {
    return serviceProviderType;
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
  @Override
  public String getStreetName() {
    return StringUtils.trimToEmpty(streetName);
  }

  /**
   * @return the streetNumber
   */
  @Override
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

  @JsonIgnore
  @Override
  public String getStreetAddress() {
    return this.getStreetNumber() + " " + this.getStreetName();
  }

  @JsonIgnore
  @Override
  public String getCity() {
    return this.getCityName();
  }

  @JsonIgnore
  @Override
  public String getState() {
    return this.getStateCodeType() != null ? this.getStateCodeType().toString() : null;
  }

  @JsonIgnore
  @Override
  public String getZip() {
    StringBuilder buf = new StringBuilder();

    if (this.getZipNumber() != null) {
      buf.append(getZipNumber());
    }
    if (this.getZipSuffixNumber() != null) {
      buf.append('-').append(getZipSuffixNumber());
    }

    return buf.toString();
  }

  @JsonIgnore
  @Override
  public String getCounty() {
    return null;
  }

  @JsonIgnore
  @Override
  public String getMiddleName() {
    return null;
  }

  @JsonIgnore
  @Override
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
  public String getSsn() {
    return null;
  }

  @JsonIgnore
  @Override
  public String getNameSuffix() {
    return null;
  }

  @JsonIgnore
  @Override
  public String getPhoneNumberExtension() {
    return this.getPhoneExtensionNumber() != null ? this.getPhoneExtensionNumber().toString()
        : null;
  }

  @JsonIgnore
  @Override
  public PhoneType getPhoneType() {
    return null;
  }

  @Override
  @JsonIgnore
  public String getPhoneId() {
    return null;
  }

  @Override
  public String getAddressId() {
    return null;
  }

  @Override
  public Short getStateCd() {
    return this.stateCodeType;
  }

}
