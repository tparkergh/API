package gov.ca.cwds.data.persistence.cms;

import java.beans.Transient;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.NamedNativeQueries;
import org.hibernate.annotations.NamedNativeQuery;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;
import org.hibernate.annotations.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import gov.ca.cwds.data.CmsSystemCodeDeserializer;
import gov.ca.cwds.data.ReadablePhone;
import gov.ca.cwds.data.SystemCodeSerializer;
import gov.ca.cwds.data.persistence.PersistentObject;
import gov.ca.cwds.data.std.ApiMultiplePhonesAware;
import gov.ca.cwds.data.std.ApiPersonAware;
import gov.ca.cwds.data.std.ApiPhoneAware;

/**
 * {@link PersistentObject} representing a Attorney
 * 
 * @author CWDS API Team
 */
@NamedQueries({
    @NamedQuery(name = "gov.ca.cwds.data.persistence.cms.Attorney.findAll",
        query = "FROM Attorney"),
    @NamedQuery(name = "gov.ca.cwds.data.persistence.cms.Attorney.findAllUpdatedAfter",
        query = "FROM Attorney WHERE lastUpdatedTime > :after")})
@NamedNativeQueries({@NamedNativeQuery(
    name = "gov.ca.cwds.data.persistence.cms.Attorney.findAllByBucket",
    query = "select z.IDENTIFIER, z.CITY_NM, z.CWATRY_IND, z.FAX_NO, z.FIRST_NM, "
        + "z.GVR_ENTC, z.LANG_TPC, z.LAST_NM, z.MSG_EXT_NO, z.MSG_TEL_NO, "
        + "z.MID_INI_NM, z.NMPRFX_DSC, z.POSTIL_DSC, z.PRM_EXT_NO, z.PRM_TEL_NO, "
        + "z.STATE_C, z.STREET_NM, z.STREET_NO, z.SUFX_TLDSC, z.ZIP_NO, z.LST_UPD_ID, "
        + "z.LST_UPD_TS, z.BUSNSS_NM, z.ZIP_SFX_NO, z.END_DT, z.ARCASS_IND, z.EMAIL_ADDR "
        + "from ( select mod(y.rn, CAST(:total_buckets AS INTEGER)) + 1 as bucket, y.* "
        + "from ( select row_number() over (order by 1) as rn, x.* "
        + "from ( select c.* from {h-schema}ATTRNY_T c "
        + ") x ) y ) z where z.bucket = :bucket_num for read only",
    resultClass = Attorney.class)})
@Entity
@Table(name = "ATTRNY_T")
@JsonPropertyOrder(alphabetic = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Attorney extends CmsPersistentObject implements ApiPersonAware, ApiMultiplePhonesAware {

  private static final Logger LOGGER = LoggerFactory.getLogger(Attorney.class);

  /**
   * Base serialization value. Increment by version.
   */
  private static final long serialVersionUID = 1L;

  @Column(name = "ARCASS_IND")
  private String archiveAssociationIndicator;

  @Column(name = "BUSNSS_NM")
  private String businessName;

  @Column(name = "CITY_NM")
  private String cityName;

  @Column(name = "CWATRY_IND")
  private String cwsAttorneyIndicator;

  @Column(name = "EMAIL_ADDR")
  private String emailAddress;

  @Type(type = "date")
  @Column(name = "END_DT")
  private Date endDate;

  @Column(name = "FAX_NO")
  private BigDecimal faxNumber;

  @Column(name = "FIRST_NM")
  private String firstName;

  @SystemCodeSerializer(logical = true, description = true)
  @JsonDeserialize(using = CmsSystemCodeDeserializer.class)
  @Type(type = "short")
  @Column(name = "GVR_ENTC")
  private Short governmentEntityType;

  @Id
  @Column(name = "IDENTIFIER", length = CMS_ID_LEN)
  private String id;

  @SystemCodeSerializer(logical = true, description = true)
  @JsonDeserialize(using = CmsSystemCodeDeserializer.class)
  @Type(type = "short")
  @Column(name = "LANG_TPC")
  private Short languageType;

  @Column(name = "LAST_NM")
  private String lastName;

  @Type(type = "integer")
  @Column(name = "MSG_EXT_NO")
  private Integer messagePhoneExtensionNumber;

  @Column(name = "MSG_TEL_NO")
  private BigDecimal messagePhoneNumber;

  @Column(name = "MID_INI_NM")
  private String middleInitialName;

  @Column(name = "NMPRFX_DSC")
  private String namePrefixDescription;

  @Column(name = "POSTIL_DSC")
  private String positionTitleDescription;

  @Type(type = "integer")
  @Column(name = "PRM_EXT_NO")
  private Integer primaryPhoneExtensionNumber;

  @Column(name = "PRM_TEL_NO")
  private BigDecimal primaryPhoneNumber;

  @SystemCodeSerializer(logical = true, description = true)
  @JsonDeserialize(using = CmsSystemCodeDeserializer.class)
  @Type(type = "short")
  @Column(name = "STATE_C")
  private Short stateCodeType;

  @Column(name = "STREET_NM")
  private String streetName;

  @Column(name = "STREET_NO")
  private String streetNumber;

  @Column(name = "SUFX_TLDSC")
  private String suffixTitleDescription;

  @Type(type = "integer")
  @Column(name = "ZIP_NO")
  private Integer zipNumber;

  @Type(type = "short")
  @Column(name = "ZIP_SFX_NO")
  private Short zipSuffixNumber;

  /**
   * Default constructor
   * 
   * Required for Hibernate
   */
  public Attorney() {
    super();
  }

  /**
   * Construct from all fields.
   * 
   * @param archiveAssociationIndicator The archiveAssociationIndicator
   * @param businessName The businessName
   * @param cityName The cityName
   * @param cwsAttorneyIndicator The cwsAttorneyIndicator
   * @param emailAddress The cwsAttorneyIndicator
   * @param endDate The endDate
   * @param faxNumber The faxNumber
   * @param firstName The firstName
   * @param governmentEntityType The governmentEntityType
   * @param id The id
   * @param languageType The languageType
   * @param lastName The lastName
   * @param messagePhoneExtensionNumber The messagePhoneExtensionNumber
   * @param messagePhoneNumber The messagePhoneNumber
   * @param middleInitialName The middleInitialName
   * @param namePrefixDescription The namePrefixDescription
   * @param positionTitleDescription The positionTitleDescription
   * @param primaryPhoneExtensionNumber The primaryPhoneExtensionNumber
   * @param primaryPhoneNumber The primaryPhoneNumber
   * @param stateCodeType The stateCodeType
   * @param streetName The streetName
   * @param streetNumber The streetNumber
   * @param suffixTitleDescription The suffixTitleDescription
   * @param zipNumber The zipNumber
   * @param zipSuffixNumber The zipSuffixNumber
   */
  public Attorney(String archiveAssociationIndicator, String businessName, String cityName,
      String cwsAttorneyIndicator, String emailAddress, Date endDate, BigDecimal faxNumber,
      String firstName, Short governmentEntityType, String id, Short languageType, String lastName,
      Integer messagePhoneExtensionNumber, BigDecimal messagePhoneNumber, String middleInitialName,
      String namePrefixDescription, String positionTitleDescription,
      Integer primaryPhoneExtensionNumber, BigDecimal primaryPhoneNumber, Short stateCodeType,
      String streetName, String streetNumber, String suffixTitleDescription, Integer zipNumber,
      Short zipSuffixNumber) {
    super();
    this.archiveAssociationIndicator = archiveAssociationIndicator;
    this.businessName = businessName;
    this.cityName = cityName;
    this.cwsAttorneyIndicator = cwsAttorneyIndicator;
    this.emailAddress = emailAddress;
    this.endDate = endDate;
    this.faxNumber = faxNumber;
    this.firstName = firstName;
    this.governmentEntityType = governmentEntityType;
    this.id = id;
    this.languageType = languageType;
    this.lastName = lastName;
    this.messagePhoneExtensionNumber = messagePhoneExtensionNumber;
    this.messagePhoneNumber = messagePhoneNumber;
    this.middleInitialName = middleInitialName;
    this.namePrefixDescription = namePrefixDescription;
    this.positionTitleDescription = positionTitleDescription;
    this.primaryPhoneExtensionNumber = primaryPhoneExtensionNumber;
    this.primaryPhoneNumber = primaryPhoneNumber;
    this.stateCodeType = stateCodeType;
    this.streetName = streetName;
    this.streetNumber = streetNumber;
    this.suffixTitleDescription = suffixTitleDescription;
    this.zipNumber = zipNumber;
    this.zipSuffixNumber = zipSuffixNumber;
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
