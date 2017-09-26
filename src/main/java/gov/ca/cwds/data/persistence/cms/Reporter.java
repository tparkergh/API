package gov.ca.cwds.data.persistence.cms;

import java.beans.Transient;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.NamedNativeQueries;
import org.hibernate.annotations.NamedNativeQuery;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import gov.ca.cwds.data.ReadablePhone;
import gov.ca.cwds.data.ns.NsPersistentObject;
import gov.ca.cwds.data.std.ApiPhoneAware;
import gov.ca.cwds.rest.api.domain.DomainChef;

/**
 * {@link NsPersistentObject} representing a Reporter.
 * 
 * @author CWDS API Team
 */
@NamedQueries({
    @NamedQuery(name = "gov.ca.cwds.data.persistence.cms.Reporter.findAll",
        query = "FROM Reporter WHERE confidentialWaiverIndicator = 'Y' AND referralId IN "
            + "(SELECT id FROM Referral WHERE limitedAccessCode = 'N')"),
    @NamedQuery(name = "gov.ca.cwds.data.persistence.cms.Reporter.findAllUpdatedAfterOLD",
        query = "FROM Reporter WHERE lastUpdatedTime > :after AND confidentialWaiverIndicator = 'Y' AND referralId IN "
            + "(SELECT id FROM Referral WHERE limitedAccessCode = 'N')")})
@NamedNativeQueries({@NamedNativeQuery(
    name = "gov.ca.cwds.data.persistence.cms.Reporter.findPartitionedBuckets",
    query = "select trim(z.RPTR_BDGNO) as RPTR_BDGNO, trim(z.RPTR_CTYNM) as RPTR_CTYNM, "
        + "z.COL_RELC, z.CMM_MTHC, z.CNFWVR_IND, z.FDBACK_DOC, z.RPTR_EMPNM, "
        + "z.FEEDBCK_DT, z.FB_RQR_IND, z.RPTR_FSTNM, trim(z.RPTR_LSTNM) as RPTR_LSTNM, "
        + "z.MNRPTR_IND, z.MSG_EXT_NO, z.MSG_TEL_NO, trim(z.MID_INI_NM) as MID_INI_NM, "
        + "trim(z.NMPRFX_DSC) as NMPRFX_DSC, z.PRM_TEL_NO, z.PRM_EXT_NO, z.STATE_C, "
        + "trim(z.RPTR_ST_NM) as RPTR_ST_NM, trim(z.RPTR_ST_NO) as RPTR_ST_NO, "
        + "trim(z.SUFX_TLDSC) as SUFX_TLDSC, z.RPTR_ZIPNO, z.LST_UPD_ID, z.LST_UPD_TS, "
        + "z.FKREFERL_T, z.FKLAW_ENFT, z.ZIP_SFX_NO, z.CNTY_SPFCD "
        + "from ( select mod(y.rn, CAST(:total_buckets AS INTEGER)) + 1 as bucket, y.* "
        + "from ( select row_number() over (order by 1) as rn, x.* "
        + "from ( select c.* from {h-schema}REPTR_T c "
        + "WHERE c.FKREFERL_T >= :min_id and c.FKREFERL_T < :max_id "
        + ") x ) y ) z where z.bucket = :bucket_num for read only",
    resultClass = Reporter.class)})
@SuppressWarnings("serial")
@Entity
@Table(name = "REPTR_T")
@JsonPropertyOrder(alphabetic = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Reporter extends BaseReporter {

  /**
   * #147241489: referential integrity check.
   * <p>
   * Doesn't actually load the data. Just checks the existence of the parent referral,
   * lawEnforcement and reporterDrmsDocument record.
   * </p>
   */
  @OneToOne(optional = false)
  @JoinColumn(name = "FKREFERL_T", nullable = false, updatable = false, insertable = false)
  private Referral referral;

  @ManyToOne(optional = true)
  @JoinColumn(name = "FKLAW_ENFT", nullable = true, updatable = false, insertable = false)
  private LawEnforcement lawEnforcement;

  @ManyToOne(optional = true)
  @JoinColumn(name = "FDBACK_DOC", nullable = true, updatable = false, insertable = false)
  private DrmsDocument drmsDocument;

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
   * @param drmsMandatedRprtrFeedback = DRMS mandated reporter
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
    init(reporter);
  }

  /**
   * Constructor
   * 
   * @param reporter The domain object to construct this object from
   * @param lastUpdatedId the id of the last person to update this object
   * @param lastUpdatedTime the time of last person to update this object
   */
  public Reporter(gov.ca.cwds.rest.api.domain.cms.Reporter reporter, String lastUpdatedId,
      Date lastUpdatedTime) {
    super(lastUpdatedId, lastUpdatedTime);
    init(reporter);
  }

  /**
   * @param id the id
   * @param reporter - reporter
   */
  private void init(gov.ca.cwds.rest.api.domain.cms.Reporter reporter) {
    this.referralId = reporter.getReferralId();
    this.badgeNumber = reporter.getBadgeNumber();
    this.cityName = reporter.getCityName();
    this.colltrClientRptrReltnshpType = reporter.getColltrClientRptrReltnshpType();
    this.communicationMethodType = reporter.getCommunicationMethodType();
    this.confidentialWaiverIndicator =
        DomainChef.cookBoolean(reporter.getConfidentialWaiverIndicator());
    this.drmsMandatedRprtrFeedback = StringUtils.isBlank(reporter.getDrmsMandatedRprtrFeedback())
        ? null : reporter.getDrmsMandatedRprtrFeedback();
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
    this.middleInitialName =
        StringUtils.isBlank(reporter.getMiddleInitialName()) ? "" : reporter.getMiddleInitialName();
    this.namePrefixDescription = StringUtils.isBlank(reporter.getNamePrefixDescription()) ? ""
        : reporter.getNamePrefixDescription();
    this.primaryPhoneNumber = reporter.getPrimaryPhoneNumber();
    this.primaryPhoneExtensionNumber = reporter.getPrimaryPhoneExtensionNumber();
    this.stateCodeType = reporter.getStateCodeType();
    this.streetName = StringUtils.isBlank(reporter.getStreetName()) ? "" : reporter.getStreetName();
    this.streetNumber =
        StringUtils.isBlank(reporter.getStreetNumber()) ? "" : reporter.getStreetNumber();
    this.suffixTitleDescription = StringUtils.isBlank(reporter.getSuffixTitleDescription()) ? ""
        : reporter.getSuffixTitleDescription();
    this.zipNumber = DomainChef.uncookZipcodeString(reporter.getZipcode());
    this.lawEnforcementId =
        StringUtils.isBlank(reporter.getLawEnforcementId()) ? null : reporter.getLawEnforcementId();
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
  @Override
  public String getReferralId() {
    return referralId;
  }

  /**
   * @return the badgeNumber
   */
  @Override
  public String getBadgeNumber() {
    return StringUtils.trimToEmpty(badgeNumber);
  }

  /**
   * @return the cityName
   */
  @Override
  public String getCityName() {
    return StringUtils.trimToEmpty(cityName);
  }

  /**
   * @return the colltrClientRptrReltnshpType
   */
  @Override
  public Short getColltrClientRptrReltnshpType() {
    return colltrClientRptrReltnshpType;
  }

  /**
   * @return the communicationMethodType
   */
  @Override
  public Short getCommunicationMethodType() {
    return communicationMethodType;
  }

  /**
   * @return the confidentialWaiverIndicator
   */
  @Override
  public String getConfidentialWaiverIndicator() {
    return confidentialWaiverIndicator;
  }

  /**
   * @return the drmsMandatedRprtrFeedback
   */
  @Override
  public String getDrmsMandatedRprtrFeedback() {
    return StringUtils.trimToEmpty(drmsMandatedRprtrFeedback);
  }

  /**
   * @return the employerName
   */
  @Override
  public String getEmployerName() {
    return StringUtils.trimToEmpty(employerName);
  }

  /**
   * @return the feedbackDate
   */
  @Override
  public Date getFeedbackDate() {
    return feedbackDate;
  }

  /**
   * @return the feedbackRequiredIndicator
   */
  @Override
  public String getFeedbackRequiredIndicator() {
    return feedbackRequiredIndicator;
  }

  /**
   * @return the firstName
   */
  @Override
  public String getFirstName() {
    return StringUtils.trimToEmpty(firstName);
  }

  /**
   * @return the lastName
   */
  @Override
  public String getLastName() {
    return StringUtils.trimToEmpty(lastName);
  }

  /**
   * @return the mandatedReporterIndicator
   */
  @Override
  public String getMandatedReporterIndicator() {
    return mandatedReporterIndicator;
  }

  /**
   * @return the messagePhoneExtensionNumber
   */
  @Override
  public Integer getMessagePhoneExtensionNumber() {
    return messagePhoneExtensionNumber;
  }

  /**
   * @return the messagePhoneNumber
   */
  @Override
  public BigDecimal getMessagePhoneNumber() {
    return messagePhoneNumber;
  }

  /**
   * @return the middleInitialName
   */
  @Override
  public String getMiddleInitialName() {
    return StringUtils.trimToEmpty(middleInitialName);
  }

  /**
   * @return the namePrefixDescription
   */
  @Override
  public String getNamePrefixDescription() {
    return StringUtils.trimToEmpty(namePrefixDescription);
  }

  /**
   * @return the primaryPhoneNumber
   */
  @Override
  public BigDecimal getPrimaryPhoneNumber() {
    return primaryPhoneNumber;
  }

  /**
   * @return the primaryPhoneExtensionNumber
   */
  @Override
  public Integer getPrimaryPhoneExtensionNumber() {
    return primaryPhoneExtensionNumber;
  }

  /**
   * @return the stateCodeType
   */
  @Override
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
  @Override
  public String getSuffixTitleDescription() {
    return StringUtils.trimToEmpty(suffixTitleDescription);
  }

  /**
   * @return the zipNumber
   */
  @Override
  public Integer getZipNumber() {
    return zipNumber;
  }

  /**
   * @return the lawEnforcementId
   */
  @Override
  public String getLawEnforcementId() {
    return lawEnforcementId;
  }

  /**
   * @return the zipSuffixNumber
   */
  @Override
  public Short getZipSuffixNumber() {
    return zipSuffixNumber;
  }

  /**
   * @return the countySpecificCode
   */
  @Override
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
    return StringUtils.trimToEmpty(this.suffixTitleDescription);
  }

  // ==================
  // IAddressAware:
  // ==================

  @JsonIgnore
  @Override
  @Transient
  public String getStreetAddress() {
    return StringUtils.trimToEmpty(this.streetNumber) + " "
        + StringUtils.trimToEmpty(this.streetName);
  }

  @JsonIgnore
  @Override
  @Transient
  public String getCity() {
    return StringUtils.trimToEmpty(this.cityName);
  }

  @JsonIgnore
  @Override
  @Transient
  public String getState() {
    return this.getStateCodeType() != null ? this.getStateCodeType().toString() : null;
  }

  @JsonIgnore
  @Override
  @Transient
  public String getZip() {
    StringBuilder buf = new StringBuilder();

    if (this.zipNumber != null) {
      buf.append(zipNumber);
    }
    if (this.getZipSuffixNumber() != null) {
      buf.append('-').append(getZipSuffixNumber());
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
  public ApiPhoneAware[] getPhones() {
    List<ApiPhoneAware> phones = new ArrayList<>();
    if (this.primaryPhoneNumber != null && !BigDecimal.ZERO.equals(this.primaryPhoneNumber)) {
      phones.add(new ReadablePhone(null, this.primaryPhoneNumber.toPlainString(),
          this.primaryPhoneExtensionNumber != null ? this.primaryPhoneExtensionNumber.toString()
              : null,
          null));
    }

    if (this.messagePhoneNumber != null && !BigDecimal.ZERO.equals(this.messagePhoneNumber)) {
      phones
          .add(new ReadablePhone(null,
              this.messagePhoneNumber.toPlainString(), this.messagePhoneExtensionNumber != null
                  ? this.messagePhoneExtensionNumber.toString() : null,
              ApiPhoneAware.PhoneType.Cell));
    }

    return phones.toArray(new ApiPhoneAware[0]);
  }

}
