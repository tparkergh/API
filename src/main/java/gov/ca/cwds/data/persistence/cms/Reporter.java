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
import javax.validation.constraints.NotNull;

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
import gov.ca.cwds.data.IAddressAware;
import gov.ca.cwds.data.IMultiplePhonesAware;
import gov.ca.cwds.data.IPersonAware;
import gov.ca.cwds.data.IPhoneAware;
import gov.ca.cwds.data.ReadablePhone;
import gov.ca.cwds.data.SystemCodeSerializer;
import gov.ca.cwds.data.ns.NsPersistentObject;
import gov.ca.cwds.rest.api.domain.DomainChef;

/**
 * {@link NsPersistentObject} representing a Reporter
 * 
 * @author CWDS API Team
 */
@NamedQueries({
    @NamedQuery(name = "gov.ca.cwds.data.persistence.cms.Reporter.findAll",
        query = "FROM Reporter"),
    @NamedQuery(name = "gov.ca.cwds.data.persistence.cms.Reporter.findAllUpdatedAfter",
        query = "FROM Reporter WHERE lastUpdatedTime > :after")})
@NamedNativeQueries({@NamedNativeQuery(
    name = "gov.ca.cwds.data.persistence.cms.Reporter.findAllByBucket",
    query = "select z.RPTR_BDGNO, z.RPTR_CTYNM, z.COL_RELC, z.CMM_MTHC, z.CNFWVR_IND, "
        + "z.FDBACK_DOC, z.RPTR_EMPNM, z.FEEDBCK_DT, z.FB_RQR_IND, z.RPTR_FSTNM, "
        + "z.RPTR_LSTNM, z.MNRPTR_IND, z.MSG_EXT_NO, z.MSG_TEL_NO, z.MID_INI_NM, "
        + "z.NMPRFX_DSC, z.PRM_TEL_NO, z.PRM_EXT_NO, z.STATE_C, z.RPTR_ST_NM, "
        + "z.RPTR_ST_NO, z.SUFX_TLDSC, z.RPTR_ZIPNO, z.LST_UPD_ID, z.LST_UPD_TS, "
        + "z.FKREFERL_T, z.FKLAW_ENFT, z.ZIP_SFX_NO, z.CNTY_SPFCD "
        + "from ( select mod(y.rn, :total_buckets) + 1 as bucket, y.* "
        + "from ( select row_number() over (order by 1) as rn, x.* "
        + "from ( select c.* from cwsint.REPTR_T c "
        + ") x ) y ) z where z.bucket = :bucket_num for read only",
    resultClass = Reporter.class)})
@SuppressWarnings("serial")
@Entity
@Table(name = "REPTR_T")
@JsonPropertyOrder(alphabetic = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Reporter extends CmsPersistentObject
    implements IPersonAware, IAddressAware, IMultiplePhonesAware {

  private static final Logger LOGGER = LoggerFactory.getLogger(Reporter.class);

  @Id
  @NotNull
  @Column(name = "FKREFERL_T", length = CMS_ID_LEN)
  private String referralId;

  @Column(name = "RPTR_BDGNO")
  private String badgeNumber;

  @Column(name = "RPTR_CTYNM")
  private String cityName;

  @SystemCodeSerializer(logical = true, description = true)
  @JsonDeserialize(using = CmsSystemCodeDeserializer.class)
  @Type(type = "short")
  @Column(name = "COL_RELC")
  private Short colltrClientRptrReltnshpType;

  @SystemCodeSerializer(logical = true, description = true)
  @JsonDeserialize(using = CmsSystemCodeDeserializer.class)
  @Type(type = "short")
  @Column(name = "CMM_MTHC")
  private Short communicationMethodType;

  @Column(name = "CNFWVR_IND")
  private String confidentialWaiverIndicator;

  @Column(name = "FDBACK_DOC")
  private String drmsMandatedRprtrFeedback;

  @Column(name = "RPTR_EMPNM")
  private String employerName;

  @Type(type = "date")
  @Column(name = "FEEDBCK_DT")
  private Date feedbackDate;

  @Column(name = "FB_RQR_IND")
  private String feedbackRequiredIndicator;

  @Column(name = "RPTR_FSTNM")
  private String firstName;

  @Column(name = "RPTR_LSTNM")
  private String lastName;

  @NotNull
  @Column(name = "MNRPTR_IND")
  private String mandatedReporterIndicator;

  @Type(type = "integer")
  @Column(name = "MSG_EXT_NO")
  private Integer messagePhoneExtensionNumber;

  @Column(name = "MSG_TEL_NO")
  private BigDecimal messagePhoneNumber;

  @Column(name = "MID_INI_NM")
  private String middleInitialName;

  @Column(name = "NMPRFX_DSC")
  private String namePrefixDescription;

  @Column(name = "PRM_TEL_NO")
  private BigDecimal primaryPhoneNumber;

  @Type(type = "integer")
  @Column(name = "PRM_EXT_NO")
  private Integer primaryPhoneExtensionNumber;

  @SystemCodeSerializer(logical = true, description = true)
  @JsonDeserialize(using = CmsSystemCodeDeserializer.class)
  @Type(type = "short")
  @Column(name = "STATE_C")
  private Short stateCodeType;

  @Column(name = "RPTR_ST_NM")
  private String streetName;

  @Column(name = "RPTR_ST_NO")
  private String streetNumber;

  @Column(name = "SUFX_TLDSC")
  private String suffixTitleDescription;

  @Type(type = "integer")
  @Column(name = "RPTR_ZIPNO")
  private Integer zipNumber;

  @Column(name = "FKLAW_ENFT", length = 10)
  private String lawEnforcementId;

  @Type(type = "short")
  @Column(name = "ZIP_SFX_NO")
  private Short zipSuffixNumber;

  @Column(name = "CNTY_SPFCD")
  private String countySpecificCode;

  /**
   * Default constructor
   * 
   * Required for Hibernate
   */
  public Reporter() {
    super();
  }

  /**
   * @param referralId = Identifier
   * @param badgeNumber = badge number
   * @param cityName = city
   * @param colltrClientRptrReltnshpType - client/reporter relationship type
   * @param communicationMethodType - communication method type
   * @param confidentialWaiverIndicator - confidential waiver indicator
   * @param drmsMandatedRprtrFeedback = drms mandated reporter
   * @param employerName - name
   * @param feedbackDate - date
   * @param feedbackRequiredIndicator - feedback
   * @param firstName = first name
   * @param lastName - last name
   * @param mandatedReporterIndicator - mandated reporter
   * @param messagePhoneExtensionNumber - phone extension
   * @param messagePhoneNumber - phone number
   * @param middleInitialName - middle initial
   * @param namePrefixDescription - prefix
   * @param primaryPhoneNumber - phone number
   * @param primaryPhoneExtensionNumber - extension
   * @param stateCodeType - state code
   * @param streetName - street
   * @param streetNumber - street number
   * @param suffixTitleDescription - suffix
   * @param zipNumber - zip
   * @param lawEnforcementId - law enforcement id
   * @param zipSuffixNumber - suffix
   * @param countySpecificCode - county code
   */
  public Reporter(String referralId, String badgeNumber, String cityName,
      Short colltrClientRptrReltnshpType, Short communicationMethodType,
      String confidentialWaiverIndicator, String drmsMandatedRprtrFeedback, String employerName,
      Date feedbackDate, String feedbackRequiredIndicator, String firstName, String lastName,
      String mandatedReporterIndicator, Integer messagePhoneExtensionNumber,
      BigDecimal messagePhoneNumber, String middleInitialName, String namePrefixDescription,
      BigDecimal primaryPhoneNumber, Integer primaryPhoneExtensionNumber, Short stateCodeType,
      String streetName, String streetNumber, String suffixTitleDescription, Integer zipNumber,
      String lawEnforcementId, Short zipSuffixNumber, String countySpecificCode) {
    super();
    this.referralId = referralId;
    this.badgeNumber = badgeNumber;
    this.cityName = cityName;
    this.colltrClientRptrReltnshpType = colltrClientRptrReltnshpType;
    this.communicationMethodType = communicationMethodType;
    this.confidentialWaiverIndicator = confidentialWaiverIndicator;
    this.drmsMandatedRprtrFeedback = drmsMandatedRprtrFeedback;
    this.employerName = employerName;
    this.feedbackDate = feedbackDate;
    this.feedbackRequiredIndicator = feedbackRequiredIndicator;
    this.firstName = firstName;
    this.lastName = lastName;
    this.mandatedReporterIndicator = mandatedReporterIndicator;
    this.messagePhoneExtensionNumber = messagePhoneExtensionNumber;
    this.messagePhoneNumber = messagePhoneNumber;
    this.middleInitialName = middleInitialName;
    this.namePrefixDescription = namePrefixDescription;
    this.primaryPhoneNumber = primaryPhoneNumber;
    this.primaryPhoneExtensionNumber = primaryPhoneExtensionNumber;
    this.stateCodeType = stateCodeType;
    this.streetName = streetName;
    this.streetNumber = streetNumber;
    this.suffixTitleDescription = suffixTitleDescription;
    this.zipNumber = zipNumber;
    this.lawEnforcementId = lawEnforcementId;
    this.zipSuffixNumber = zipSuffixNumber;
    this.countySpecificCode = countySpecificCode;
  }

  /**
   * Constructor
   * 
   * @param reporter The domain object to construct this object from
   * @param lastUpdatedId the id of the last person to update this object
   */
  public Reporter(gov.ca.cwds.rest.api.domain.cms.Reporter reporter, String lastUpdatedId) {
    super(lastUpdatedId);

    this.referralId = reporter.getReferralId();
    this.badgeNumber = reporter.getBadgeNumber();
    this.cityName = reporter.getCityName();
    this.colltrClientRptrReltnshpType = reporter.getColltrClientRptrReltnshpType();
    this.communicationMethodType = reporter.getCommunicationMethodType();
    this.confidentialWaiverIndicator =
        DomainChef.cookBoolean(reporter.getConfidentialWaiverIndicator());
    this.drmsMandatedRprtrFeedback = reporter.getDrmsMandatedRprtrFeedback();
    this.employerName = reporter.getEmployerName();
    this.feedbackDate = DomainChef.uncookDateString(reporter.getFeedbackDate());
    this.feedbackRequiredIndicator =
        DomainChef.cookBoolean(reporter.getFeedbackRequiredIndicator());
    this.firstName = reporter.getFirstName();
    this.lastName = reporter.getLastName();
    this.mandatedReporterIndicator =
        DomainChef.cookBoolean(reporter.getMandatedReporterIndicator());
    this.messagePhoneExtensionNumber = reporter.getMessagePhoneExtensionNumber();
    this.messagePhoneNumber = reporter.getMessagePhoneNumber();
    this.middleInitialName = reporter.getMiddleInitialName();
    this.namePrefixDescription = reporter.getNamePrefixDescription();
    this.primaryPhoneNumber = reporter.getPrimaryPhoneNumber();
    this.primaryPhoneExtensionNumber = reporter.getPrimaryPhoneExtensionNumber();
    this.stateCodeType = reporter.getStateCodeType();
    this.streetName = reporter.getStreetName();
    this.streetNumber = reporter.getStreetNumber();
    this.suffixTitleDescription = reporter.getSuffixTitleDescription();
    this.zipNumber = DomainChef.uncookZipcodeString(reporter.getZipcode());
    this.lawEnforcementId = reporter.getLawEnforcementId();
    this.zipSuffixNumber = reporter.getZipSuffixNumber();
    this.countySpecificCode = reporter.getCountySpecificCode();
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.data.persistence.PersistentObject#getPrimaryKey()
   */
  @Override
  public String getPrimaryKey() {
    return getReferralId();
  }

  /**
   * @return the referralId
   */
  public String getReferralId() {
    return referralId;
  }

  /**
   * @return the badgeNumber
   */
  public String getBadgeNumber() {
    return badgeNumber;
  }

  /**
   * @return the cityName
   */
  public String getCityName() {
    return cityName;
  }

  /**
   * @return the colltrClientRptrReltnshpType
   */
  public Short getColltrClientRptrReltnshpType() {
    return colltrClientRptrReltnshpType;
  }

  /**
   * @return the communicationMethodType
   */
  public Short getCommunicationMethodType() {
    return communicationMethodType;
  }

  /**
   * @return the confidentialWaiverIndicator
   */
  public String getConfidentialWaiverIndicator() {
    return confidentialWaiverIndicator;
  }

  /**
   * @return the drmsMandatedRprtrFeedback
   */
  public String getDrmsMandatedRprtrFeedback() {
    return drmsMandatedRprtrFeedback;
  }

  /**
   * @return the employerName
   */
  public String getEmployerName() {
    return employerName;
  }

  /**
   * @return the feedbackDate
   */
  public Date getFeedbackDate() {
    return feedbackDate;
  }

  /**
   * @return the feedbackRequiredIndicator
   */
  public String getFeedbackRequiredIndicator() {
    return feedbackRequiredIndicator;
  }

  /**
   * @return the firstName
   */
  @Override
  public String getFirstName() {
    return firstName;
  }

  /**
   * @return the lastName
   */
  @Override
  public String getLastName() {
    return lastName;
  }

  /**
   * @return the mandatedReporterIndicator
   */
  public String getMandatedReporterIndicator() {
    return mandatedReporterIndicator;
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
    return middleInitialName;
  }

  /**
   * @return the namePrefixDescription
   */
  public String getNamePrefixDescription() {
    return namePrefixDescription;
  }

  /**
   * @return the primaryPhoneNumber
   */
  public BigDecimal getPrimaryPhoneNumber() {
    return primaryPhoneNumber;
  }

  /**
   * @return the primaryPhoneExtensionNumber
   */
  public Integer getPrimaryPhoneExtensionNumber() {
    return primaryPhoneExtensionNumber;
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
    return streetName;
  }

  /**
   * @return the streetNumber
   */
  public String getStreetNumber() {
    return streetNumber;
  }

  /**
   * @return the suffixTitleDescription
   */
  public String getSuffixTitleDescription() {
    return suffixTitleDescription;
  }

  /**
   * @return the zipNumber
   */
  public Integer getZipNumber() {
    return zipNumber;
  }

  /**
   * @return the lawEnforcementId
   */
  public String getLawEnforcementId() {
    return lawEnforcementId;
  }

  /**
   * @return the zipSuffixNumber
   */
  public Short getZipSuffixNumber() {
    return zipSuffixNumber;
  }

  /**
   * @return the countySpecificCode
   */
  public String getCountySpecificCode() {
    return countySpecificCode;
  }

  // ==================
  // IPersonAware:
  // ==================

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
    // Does not apply.
    return null;
  }

  @JsonIgnore
  @Override
  @Transient
  public Date getBirthDate() {
    // Does not apply.
    return null;
  }

  @JsonIgnore
  @Override
  @Transient
  public String getSsn() {
    // Does not apply.
    return null;
  }

  @JsonIgnore
  @Override
  @Transient
  public String getNameSuffix() {
    return this.suffixTitleDescription;
  }

  // ==================
  // IAddressAware:
  // ==================

  @JsonIgnore
  @Override
  @Transient
  public String getStreetAddress() {
    return this.streetNumber + " " + this.streetName;
  }

  @JsonIgnore
  @Override
  @Transient
  public String getCity() {
    return this.cityName;
  }

  @JsonIgnore
  @Override
  @Transient
  public String getState() {
    return this.stateCodeType != null ? this.stateCodeType.toString() : null;
  }

  @JsonIgnore
  @Override
  @Transient
  public String getZip() {
    StringBuilder buf = new StringBuilder();

    if (this.zipNumber != null) {
      buf.append(zipNumber);
    }

    return buf.toString();
  }

  @Override
  public String getCounty() {
    return this.countySpecificCode;
  }

  // =======================
  // IMultiplePhonesAware:
  // =======================

  @Override
  public IPhoneAware[] getPhones() {

    List<IPhoneAware> phones = new ArrayList<>();
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
              IPhoneAware.PhoneType.Cell));
    }

    return phones.toArray(new IPhoneAware[0]);
  }

}
