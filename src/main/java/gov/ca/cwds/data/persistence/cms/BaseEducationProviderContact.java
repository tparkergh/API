package gov.ca.cwds.data.persistence.cms;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnore;

import gov.ca.cwds.data.std.ApiPersonAware;
import gov.ca.cwds.data.std.ApiPhoneAware;

@MappedSuperclass
public abstract class BaseEducationProviderContact extends CmsPersistentObject
    implements ApiPersonAware, ApiPhoneAware {

  /**
   * Base serialization value. Increment by class version.
   */
  private static final long serialVersionUID = 1L;

  @Column(name = "DOE_IND")
  protected String departmentOfEducationIndicator;

  @Column(name = "EMAILADR")
  protected String emailAddress;

  @Column(name = "FAX_NO")
  protected BigDecimal faxNumber;

  @Column(name = "FIRST_NME")
  protected String firstName;

  @Column(name = "FKED_PVDRT")
  protected String fKeyEducationProvider;

  @Id
  @Column(name = "IDENTIFIER", length = CMS_ID_LEN)
  protected String id;

  @Column(name = "LAST_NME")
  protected String lastName;

  @Column(name = "MIDDLE_NM")
  protected String middleName;

  @Column(name = "NM_PREFIX")
  protected String namePrefixDescription;

  @Type(type = "integer")
  @Column(name = "PH_EXTNO")
  protected Integer phoneExtensionNumber;

  @Column(name = "PH_NUMBR")
  protected BigDecimal phoneNumber;

  @Column(name = "PRICNTIND")
  protected String primaryContactIndicator;

  @Column(name = "SUFFX_TITL")
  protected String suffixTitleDescription;

  @Column(name = "TITLDESC")
  protected String titleDescription;

  /**
   * @return serialVersionUID
   */
  public static long getSerialversionuid() {
    return serialVersionUID;
  }

  public BaseEducationProviderContact() {
    super();
  }

  /**
   * @return departmentOfEducationIndicator
   */
  public String getdepartmentOfEducationIndicator() {
    return departmentOfEducationIndicator;
  }

  /**
   * @return emailAddress
   */
  public String getEmailAddress() {
    return emailAddress;
  }

  /**
   * @return faxNumber
   */
  public BigDecimal getFaxNumber() {
    return faxNumber;
  }

  /**
   * @return firstName
   */
  @Override
  public String getFirstName() {
    return firstName;
  }

  /**
   * @return fKeyEducationProvider
   */
  public String getfKeyEducationProvider() {
    return fKeyEducationProvider;
  }

  /**
   * @return id
   */
  public String getId() {
    return id;
  }

  /**
   * @return lastName
   */
  @Override
  public String getLastName() {
    return lastName;
  }

  /**
   * @return middleName
   */
  @Override
  public String getMiddleName() {
    return middleName;
  }

  /**
   * @return namePrefixDescription
   */
  public String getNamePrefixDescription() {
    return namePrefixDescription;
  }

  /**
   * @return phoneExtensionNumber
   */
  public Integer getPhoneExtensionNumber() {
    return phoneExtensionNumber;
  }

  /**
   * @return phoneNumber
   */
  public BigDecimal getPhoneNumberAsDecimal() {
    return phoneNumber;
  }

  /**
   * @return phoneNumber
   */
  @Override
  public String getPhoneNumber() {
    return phoneNumber != null ? this.phoneNumber.toPlainString() : null;
  }

  /**
   * @return primaryContactIndicator
   */
  public String getPrimaryContactIndicator() {
    return primaryContactIndicator;
  }

  /**
   * @return suffixTitleDescription
   */
  public String getSuffixTitleDescription() {
    return suffixTitleDescription;
  }

  /**
   * @return titleDescription
   */
  public String getTitleDescription() {
    return titleDescription;
  }

  public BaseEducationProviderContact(String lastUpdatedId) {
    super(lastUpdatedId);
  }

  @Override
  public String getPrimaryKey() {
    return getId();
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
    return this.getSuffixTitleDescription();
  }

  @Override
  public String getPhoneNumberExtension() {
    return null;
  }

  @Override
  public PhoneType getPhoneType() {
    return null;
  }

  @Override
  public String getPhoneId() {
    return null;
  }

}
