package gov.ca.cwds.data.persistence.cms;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * {@link CmsPersistentObject} representing a Government Organization.
 * 
 * @author CWDS API Team
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "GV_ORG_T")
@JsonPropertyOrder(alphabetic = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@NamedQueries({@NamedQuery(name = "gov.ca.cwds.data.persistence.cms.GovernmentOrganization.findAll",
    query = "FROM GovernmentOrganization ORDER BY governmentOrganizationType")})
public class GovernmentOrganization extends CmsPersistentObject {

  @Id
  @Column(name = "IDENTIFIER", nullable = false, length = CMS_ID_LEN)
  private String id;

  @Column(name = "ARCASS_IND")
  private String archiveAssociationInd;

  @Column(name = "CITY_NM")
  private String cityName;

  @Column(name = "CNT_PERSNM")
  private String contactPersonName;

  @Column(name = "CNT_TEL_NO")
  private BigDecimal contactPhoneNumber;

  @Column(name = "CNT_EXT_NO")
  private Integer contactPhoneExtNumber;

  @Column(name = "CNTITL_DSC")
  private String contactPositionTitleDescription;

  @Column(name = "EMAIL_ADDR")
  private String emailAddress;

  @Column(name = "FAX_NO")
  private BigDecimal faxNumber;

  @Column(name = "FEDERL_IND")
  private String federalInd;

  @Column(name = "GVR_ENTC")
  private Short governmentEntityType;

  @Column(name = "GVR_ORGC")
  private Short governmentOrganizationType;

  @Column(name = "GVN_ORG_NM")
  private String governmentOrganizationName;

  @Column(name = "GV_STATE_C")
  private Short stateCodeType;

  @Column(name = "STREET_NM")
  private String streetName;

  @Column(name = "STREET_NO")
  private String streetNumber;

  @Column(name = "ZIP_NO")
  private Integer zipNumber;

  @Column(name = "ZIP_SFX_NO")
  private Short zipSuffixNumber;

  /**
   * Default constructor
   */
  public GovernmentOrganization() {
    super();
  }

  /**
   * @param id the identifier
   * @param archiveAssociationInd the archive Association Ind
   * @param cityName the city Name
   * @param contactPersonName the contact Person Name
   * @param contactPhoneNumber the contact Phone Number
   * @param contactPhoneExtNumber the contact Phone Ext Number
   * @param contactPositionTitleDescription the contact Position Title Description
   * @param emailAddress the email Address
   * @param faxNumber the fax Number
   * @param federalInd the federal Ind
   * @param governmentEntityType the government Entity Type
   * @param governmentOrganizationType the government Organization Type
   * @param governmentOrganizationName the government Organization Name
   * @param stateCodeType the state Code Type
   * @param streetName the street Name
   * @param streetNumber the street Number
   * @param zipNumber the zip Number
   * @param zipSuffixNumber the zip Suffix Number
   */
  public GovernmentOrganization(String id, String archiveAssociationInd, String cityName,
      String contactPersonName, BigDecimal contactPhoneNumber, Integer contactPhoneExtNumber,
      String contactPositionTitleDescription, String emailAddress, BigDecimal faxNumber,
      String federalInd, Short governmentEntityType, Short governmentOrganizationType,
      String governmentOrganizationName, Short stateCodeType, String streetName,
      String streetNumber, Integer zipNumber, Short zipSuffixNumber) {
    super();
    this.id = id;
    this.archiveAssociationInd = archiveAssociationInd;
    this.cityName = cityName;
    this.contactPersonName = contactPersonName;
    this.contactPhoneNumber = contactPhoneNumber;
    this.contactPhoneExtNumber = contactPhoneExtNumber;
    this.contactPositionTitleDescription = contactPositionTitleDescription;
    this.emailAddress = emailAddress;
    this.faxNumber = faxNumber;
    this.federalInd = federalInd;
    this.governmentEntityType = governmentEntityType;
    this.governmentOrganizationType = governmentOrganizationType;
    this.governmentOrganizationName = governmentOrganizationName;
    this.stateCodeType = stateCodeType;
    this.streetName = streetName;
    this.streetNumber = streetNumber;
    this.zipNumber = zipNumber;
    this.zipSuffixNumber = zipSuffixNumber;
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
   * @return the archiveAssociationInd
   */
  public String getArchiveAssociationInd() {
    return archiveAssociationInd;
  }

  /**
   * @return the cityName
   */
  public String getCityName() {
    return cityName;
  }

  /**
   * @return the contactPersonName
   */
  public String getContactPersonName() {
    return contactPersonName;
  }

  /**
   * @return the contactPhoneNumber
   */
  public BigDecimal getContactPhoneNumber() {
    return contactPhoneNumber;
  }

  /**
   * @return the contactPhoneExtNumber
   */
  public Integer getContactPhoneExtNumber() {
    return contactPhoneExtNumber;
  }

  /**
   * @return the contactPositionTitleDescription
   */
  public String getContactPositionTitleDescription() {
    return contactPositionTitleDescription;
  }

  /**
   * @return the emailAddress
   */
  public String getEmailAddress() {
    return emailAddress;
  }

  /**
   * @return the faxNumber
   */
  public BigDecimal getFaxNumber() {
    return faxNumber;
  }

  /**
   * @return the federalInd
   */
  public String getFederalInd() {
    return federalInd;
  }

  /**
   * @return teh governmentEntityType
   */
  public Short getGovernmentEntityType() {
    return governmentEntityType;
  }

  /**
   * @return the governmentOrganizationType
   */
  public Short getGovernmentOrganizationType() {
    return governmentOrganizationType;
  }

  /**
   * @return the governmentOrganizationName
   */
  public String getGovernmentOrganizationName() {
    return governmentOrganizationName;
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
   * @return the zipNumber
   */
  public Integer getZipNumber() {
    return zipNumber;
  }

  /**
   * @return the zipSuffxNumber
   */
  public Short getZipSuffixNumber() {
    return zipSuffixNumber;
  }

}
