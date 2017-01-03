package gov.ca.cwds.data.persistence.cms;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PersistenceException;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import gov.ca.cwds.rest.api.ApiException;
import gov.ca.cwds.rest.api.domain.DomainChef;

/**
 * {@link CmsPersistentObject} representing a StaffPerson.
 * 
 * <p>
 * Note that a staff identifer is a base 62, char(3), not the usual char(10).
 * </p>
 * 
 * @author CWDS API Team
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "STFPERST")
public class StaffPerson extends CmsPersistentObject {

  @Id
  @Column(name = "IDENTIFIER", length = 3)
  private String id;

  @Type(type = "date")
  @Column(name = "END_DT")
  private Date endDate;

  @Column(name = "FIRST_NM", length = 20, nullable = false)
  private String firstName;

  @Column(name = "JOB_TL_DSC")
  private String jobTitle;

  @Column(name = "LAST_NM", length = 25, nullable = false)
  private String lastName;

  @Column(name = "MID_INI_NM")
  private String middleInitial;

  @Column(name = "NMPRFX_DSC")
  private String namePrefix;

  @Column(name = "PHONE_NO")
  private BigDecimal phoneNumber;

  @Type(type = "integer")
  @Column(name = "TEL_EXT_NO")
  private Integer phoneExt;

  @Type(type = "date")
  @Column(name = "START_DT")
  private Date startDate;

  @Column(name = "SUFX_TLDSC")
  private String nameSuffix;

  @Column(name = "TLCMTR_IND")
  private String telecommuterIndicator;

  @Column(name = "FKCWS_OFFT")
  private String cwsOffice;

  @Column(name = "AVLOC_DSC")
  private String availabilityAndLocationDescription;

  @Column(name = "SSRS_WKRID")
  private String ssrsLicensingWorkerId;

  @Column(name = "CNTY_SPFCD")
  private String countyCode;

  @Column(name = "DTYWKR_IND")
  private String dutyWorkerIndicator;

  @Column(name = "FKCWSADDRT")
  private String cwsOfficeAddress;

  @Column(name = "EMAIL_ADDR")
  private String emailAddress;

  /**
   * Default constructor
   * 
   * Required for Hibernate
   */
  public StaffPerson() {
    super();
  }

  /**
   * Constructor
   * 
   * @param id The id
   * @param endDate The endDate
   * @param firstName The firstName
   * @param jobTitle The jobTitle
   * @param lastName The lastName
   * @param middleInitial The middleInitial
   * @param namePrefix The namePrefix
   * @param phoneNumber The phoneNumber
   * @param phoneExt The phoneExt
   * @param startDate The startDate
   * @param nameSuffix The nameSuffix
   * @param telecommuterIndicator The telecommuterIndicator
   * @param cwsOffice The cwsoffic
   * @param availabilityAndLocationDescription The availabilityAndLocationDescription
   * @param ssrsLicensingWorkerId The ssrsLicensingWorkerId
   * @param countyCode The countyCode
   * @param dutyWorkerIndicator The dutyWorkerIndicator
   * @param cwsOfficeAddress The cwsOfficeAddress
   * @param emailAddress The emailAddress
   */
  public StaffPerson(String id, Date endDate, String firstName, String jobTitle, String lastName,
      String middleInitial, String namePrefix, BigDecimal phoneNumber, Integer phoneExt,
      Date startDate, String nameSuffix, String telecommuterIndicator, String cwsOffice,
      String availabilityAndLocationDescription, String ssrsLicensingWorkerId, String countyCode,
      String dutyWorkerIndicator, String cwsOfficeAddress, String emailAddress) {
    super();
    this.id = id;
    this.endDate = endDate;
    this.firstName = firstName;
    this.jobTitle = jobTitle;
    this.lastName = lastName;
    this.middleInitial = middleInitial;
    this.namePrefix = namePrefix;
    this.phoneNumber = phoneNumber;
    this.phoneExt = phoneExt;
    this.startDate = startDate;
    this.nameSuffix = nameSuffix;
    this.telecommuterIndicator = telecommuterIndicator;
    this.cwsOffice = cwsOffice;
    this.availabilityAndLocationDescription = availabilityAndLocationDescription;
    this.ssrsLicensingWorkerId = ssrsLicensingWorkerId;
    this.countyCode = countyCode;
    this.dutyWorkerIndicator = dutyWorkerIndicator;
    this.cwsOfficeAddress = cwsOfficeAddress;
    this.emailAddress = emailAddress;
  }

  /**
   * Constructor
   * 
   * @param id The id
   * @param staffPerson The domain object to construct this object from
   * @param lastUpdatedId the id of the last person to update this object
   */
  public StaffPerson(String id, gov.ca.cwds.rest.api.domain.cms.StaffPerson staffPerson,
      String lastUpdatedId) {
    super(lastUpdatedId);

    try {
      this.id = id;
      this.endDate = DomainChef.uncookDateString(staffPerson.getEndDate());
      this.firstName = staffPerson.getFirstName();
      this.jobTitle = staffPerson.getJobTitle();
      this.lastName = staffPerson.getLastName();
      this.middleInitial = staffPerson.getMiddleInitial();
      this.namePrefix = staffPerson.getNamePrefix();
      this.phoneNumber = staffPerson.getPhoneNumber();
      this.phoneExt = staffPerson.getPhoneExt();
      this.startDate = DomainChef.uncookDateString(staffPerson.getStartDate());
      this.nameSuffix = staffPerson.getNameSuffix();
      this.telecommuterIndicator = DomainChef.cookBoolean(staffPerson.getTelecommuterIndicator());
      this.cwsOffice = staffPerson.getCwsOffice();
      this.availabilityAndLocationDescription = staffPerson.getAvailabilityAndLocationDescription();
      this.ssrsLicensingWorkerId = staffPerson.getSsrsLicensingWorkerId();
      this.countyCode = staffPerson.getCountyCode();
      this.dutyWorkerIndicator = DomainChef.cookBoolean(staffPerson.getDutyWorkerIndicator());
      this.cwsOfficeAddress = staffPerson.getCwsOfficeAddress();
      this.emailAddress = staffPerson.getEmailAddress();
    } catch (ApiException e) {
      throw new PersistenceException(e);
    }
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.data.persistence.PersistentObject#getPrimaryKey()
   */
  @Override
  public String getPrimaryKey() {
    return getId();
  }

  /**
   * @return the id
   */
  public String getId() {
    return id;
  }

  /**
   * @return the endDate
   */
  public Date getEndDate() {
    return endDate;
  }

  /**
   * @return the firstName
   */
  public String getFirstName() {
    return firstName;
  }

  /**
   * @return the jobTitle
   */
  public String getJobTitle() {
    return jobTitle;
  }

  /**
   * @return the lastName
   */
  public String getLastName() {
    return lastName;
  }

  /**
   * @return the middleInitial
   */
  public String getMiddleInitial() {
    return middleInitial;
  }

  /**
   * @return the namePrefix
   */
  public String getNamePrefix() {
    return namePrefix;
  }

  /**
   * @return the phoneNumber
   */
  public BigDecimal getPhoneNumber() {
    return phoneNumber;
  }

  /**
   * @return the phoneExt
   */
  public Integer getPhoneExt() {
    return phoneExt;
  }

  /**
   * @return the startDate
   */
  public Date getStartDate() {
    return startDate;
  }

  /**
   * @return the nameSuffix
   */
  public String getNameSuffix() {
    return nameSuffix;
  }

  /**
   * @return the telecommuterIndicator
   */
  public String getTelecommuterIndicator() {
    return telecommuterIndicator;
  }

  /**
   * @return the cwsOffice
   */
  public String getCwsOffice() {
    return cwsOffice;
  }

  /**
   * @return the availabilityAndLocationDescription
   */
  public String getAvailabilityAndLocationDescription() {
    return availabilityAndLocationDescription;
  }

  /**
   * @return the ssrsLicensingWorkerId
   */
  public String getSsrsLicensingWorkerId() {
    return ssrsLicensingWorkerId;
  }

  /**
   * @return the countyCode
   */
  public String getCountyCode() {
    return countyCode;
  }

  /**
   * @return the dutyWorkerIndicator
   */
  public String getDutyWorkerIndicator() {
    return dutyWorkerIndicator;
  }

  /**
   * @return the cwsOfficeAddress
   */
  public String getCwsOfficeAddress() {
    return cwsOfficeAddress;
  }

  /**
   * @return the emailAddress
   */
  public String getEmailAddress() {
    return emailAddress;
  }

}
