package gov.ca.cwds.rest.api.persistence.cms;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;
import org.hibernate.annotations.Type;

import gov.ca.cwds.rest.api.persistence.PersistentObject;

/**
 * {@link PersistentObject} representing a EducationProviderContact
 * 
 * @author CWDS API Team
 */
@NamedQueries({
    @NamedQuery(name = "gov.ca.cwds.rest.api.persistence.cms.EducationProviderContact.findAll",
        query = "FROM EducationProviderContact"),
    @NamedQuery(
        name = "gov.ca.cwds.rest.api.persistence.cms.EducationProviderContact.findAllUpdatedAfter",
        query = "FROM EducationProviderContact WHERE lastUpdatedTime > :after")})
@Entity
@Table(name = "EDPRVCNT")

public class EducationProviderContact extends CmsPersistentObject {
  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  @Column(name = "DOE_IND")
  private String departmentOfEducationIndicator;

  @Column(name = "EMAILADR")
  private String emailAddress;

  @Column(name = "FAX_NO")
  private BigDecimal faxNumber;

  @Column(name = "FIRST_NME")
  private String firstName;

  @Column(name = "FKED_PVDRT")
  private String fKeyEducationProvider;

  @Id
  @Column(name = "IDENTIFIER")
  private String id;

  @Column(name = "LAST_NME")
  private String lastName;

  @Column(name = "MIDDLE_NM")
  private String middleName;

  @Column(name = "NM_PREFIX")
  private String namePrefixExtension;

  @Type(type = "integer")
  @Column(name = "PH_EXTNO")
  private Integer phoneExtensionNumber;

  @Column(name = "PH_NUMBR")
  private BigDecimal phoneNumber;

  @Column(name = "PRICNTIND")
  private String primaryContactIndicator;

  @Column(name = "SUFFX_TITL")
  private String suffixTitleDescription;

  @Column(name = "TITLDESC")
  private String titleDescription;


  /**
   * Default constructor
   * 
   * Required for Hibernate
   */
  public EducationProviderContact() {
    super();
  }

  public EducationProviderContact(String departmentOfEducationIndicator, String emailAddress,
      BigDecimal faxNumber, String firstName, String fKeyEducationProvider, String id,
      String lastName, String middleName, String namePrefixExtension, Integer phoneExtension,
      BigDecimal phoneNumber, String primaryContactIndicator, String suffixTitleDescription,
      String titleDescription) {

    this.departmentOfEducationIndicator = departmentOfEducationIndicator;
    this.emailAddress = emailAddress;
    this.faxNumber = faxNumber;
    this.firstName = firstName;
    this.fKeyEducationProvider = fKeyEducationProvider;
    this.id = id;
    this.lastName = lastName;
    this.middleName = middleName;
    this.namePrefixExtension = namePrefixExtension;
    this.phoneExtensionNumber = phoneExtension;
    this.phoneNumber = phoneNumber;
    this.primaryContactIndicator = primaryContactIndicator;
    this.suffixTitleDescription = suffixTitleDescription;
    this.titleDescription = titleDescription;
  }

  public String getdepartmentOfEducationIndicator() {
    return departmentOfEducationIndicator;
  }

  public String getemailAddress() {
    return emailAddress;
  }

  public BigDecimal getFaxNumber() {
    return faxNumber;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getfKeyEducationProvider() {
    return fKeyEducationProvider;
  }

  public String getId() {
    return id;
  }

  public String getLastName() {
    return lastName;
  }


  public String getMiddleName() {
    return middleName;
  }

  public String getNamePrefixExtension() {
    return namePrefixExtension;
  }

  public Integer getPhoneExtensionNumber() {
    return phoneExtensionNumber;
  }

  public BigDecimal getPhoneNumber() {
    return phoneNumber;
  }

  public String getPrimaryContactIndicator() {
    return primaryContactIndicator;
  }


  public String getSuffixTitleDescription() {
    return suffixTitleDescription;
  }

  public String getTitleDescription() {
    return titleDescription;
  }

  public static long getSerialversionuid() {
    return serialVersionUID;
  }

  @Override
  public String getPrimaryKey() {
    return getId();
  }

  @Override
  public final int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((departmentOfEducationIndicator == null) ? 0
        : departmentOfEducationIndicator.hashCode());
    result = prime * result + ((emailAddress == null) ? 0 : emailAddress.hashCode());
    result =
        prime * result + ((fKeyEducationProvider == null) ? 0 : fKeyEducationProvider.hashCode());
    result = prime * result + ((faxNumber == null) ? 0 : faxNumber.hashCode());
    result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
    result = prime * result + ((middleName == null) ? 0 : middleName.hashCode());
    result = prime * result + ((namePrefixExtension == null) ? 0 : namePrefixExtension.hashCode());
    result =
        prime * result + ((phoneExtensionNumber == null) ? 0 : phoneExtensionNumber.hashCode());
    result = prime * result + ((phoneNumber == null) ? 0 : phoneNumber.hashCode());
    result = prime * result
        + ((primaryContactIndicator == null) ? 0 : primaryContactIndicator.hashCode());
    result =
        prime * result + ((suffixTitleDescription == null) ? 0 : suffixTitleDescription.hashCode());
    result = prime * result + ((titleDescription == null) ? 0 : titleDescription.hashCode());
    result = prime * result
        + ((super.getLastUpdatedId() == null) ? 0 : super.getLastUpdatedId().hashCode());
    result = prime * result
        + ((super.getLastUpdatedTime() == null) ? 0 : super.getLastUpdatedTime().hashCode());
    return result;
  }

  @Override
  public final boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (!(obj instanceof EducationProviderContact)) {
      return false;
    }
    EducationProviderContact other = (EducationProviderContact) obj;
    if (departmentOfEducationIndicator == null) {
      if (other.departmentOfEducationIndicator != null)
        return false;
    } else if (!departmentOfEducationIndicator.equals(other.departmentOfEducationIndicator))
      return false;
    if (emailAddress == null) {
      if (other.emailAddress != null)
        return false;
    } else if (!emailAddress.equals(other.emailAddress))
      return false;
    if (fKeyEducationProvider == null) {
      if (other.fKeyEducationProvider != null)
        return false;
    } else if (!fKeyEducationProvider.equals(other.fKeyEducationProvider))
      return false;
    if (faxNumber == null) {
      if (other.faxNumber != null)
        return false;
    } else if (!faxNumber.equals(other.faxNumber))
      return false;
    if (firstName == null) {
      if (other.firstName != null)
        return false;
    } else if (!firstName.equals(other.firstName))
      return false;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    if (lastName == null) {
      if (other.lastName != null)
        return false;
    } else if (!lastName.equals(other.lastName))
      return false;
    if (middleName == null) {
      if (other.middleName != null)
        return false;
    } else if (!middleName.equals(other.middleName))
      return false;
    if (namePrefixExtension == null) {
      if (other.namePrefixExtension != null)
        return false;
    } else if (!namePrefixExtension.equals(other.namePrefixExtension))
      return false;
    if (phoneExtensionNumber == null) {
      if (other.phoneExtensionNumber != null)
        return false;
    } else if (!phoneExtensionNumber.equals(other.phoneExtensionNumber))
      return false;
    if (phoneNumber == null) {
      if (other.phoneNumber != null)
        return false;
    } else if (!phoneNumber.equals(other.phoneNumber))
      return false;
    if (primaryContactIndicator == null) {
      if (other.primaryContactIndicator != null)
        return false;
    } else if (!primaryContactIndicator.equals(other.primaryContactIndicator))
      return false;
    if (suffixTitleDescription == null) {
      if (other.suffixTitleDescription != null)
        return false;
    } else if (!suffixTitleDescription.equals(other.suffixTitleDescription))
      return false;
    if (titleDescription == null) {
      if (other.titleDescription != null)
        return false;
    } else if (!titleDescription.equals(other.titleDescription))
      return false;
    if (super.getLastUpdatedId() == null) {
      if (other.getLastUpdatedId() != null) {
        return false;
      }
    } else if (!super.getLastUpdatedId().equals(other.getLastUpdatedId())) {
      return false;
    }
    if (super.getLastUpdatedTime() == null) {
      if (other.getLastUpdatedTime() != null) {
        return false;
      }
    } else if (!super.getLastUpdatedTime().equals(other.getLastUpdatedTime())) {
      return false;
    }
    return true;
  }

}
