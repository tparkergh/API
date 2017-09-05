package gov.ca.cwds.data.persistence.cms;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * {@link CmsPersistentObject} representing a Law Enforcement.
 * 
 * @author CWDS API Team
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "LAW_ENFT")
@JsonPropertyOrder(alphabetic = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class LawEnforcement extends CmsPersistentObject {

  @Column(name = "ARCASS_IND")
  private String archiveAssociationInd;

  @Column(name = "CITY_NM", nullable = false)
  private String cityName;

  @Type(type = "integer")
  @Column(name = "CNT_EXT_NO")
  private Integer contactPhoneExtensionNumber;

  @Column(name = "CNT_PERSNM")
  private String contactPersonName;

  @Column(name = "CNT_TEL_NO")
  private BigDecimal contactPhoneNumber;

  @Column(name = "CNTITL_DSC")
  private String contactPositionTitleDescription;

  @Column(name = "EMAIL_ADDR")
  private String emailAddress;

  @Column(name = "FAX_NO")
  private BigDecimal faxNumber;

  @Type(type = "short")
  @Column(name = "GVR_ENTC")
  private Short governmentEntityType;

  @Id
  @Column(name = "IDENTIFIER", nullable = false, length = CMS_ID_LEN)
  private String id;

  @Column(name = "LAW_ENF_NM", nullable = false)
  private String lawEnforcementName;

  @Type(type = "short")
  @Column(name = "STATE_C", nullable = false)
  private Short referenceNumber;

  @Column(name = "STATION_NM")
  private String stationName;

  @Column(name = "STREET_NM")
  private String streetName;

  @Column(name = "STREET_NO")
  private String streetNumber;

  @Type(type = "integer")
  @Column(name = "ZIP_NO", nullable = false)
  private Integer zipNumber;

  @Type(type = "short")
  @Column(name = "ZIP_SFX_NO")
  private Short zipSuffixNumber;

  /**
   * Default constructor
   */
  public LawEnforcement() {

  }

  /**
   * @param archiveAssociationInd the archive Association Ind
   * @param cityName the city Name
   * @param contactPhoneExtensionNumber the contact Phone Extension Number
   * @param contactPersonName the contactPersonName
   * @param contactPhoneNumber the contactPhoneNumber
   * @param contactPositionTitleDescription the contactPositionTitleDescription
   * @param emailAddress the emailAddress
   * @param faxNumber the faxNumber
   * @param governmentEntityType the governmentEntityType
   * @param id the identifier
   * @param lawEnforcementName the law Enforcement Name
   * @param referenceNumber the reference Number
   * @param stationName the station Name
   * @param streetName the street Name
   * @param streetNumber the street Number
   * @param zipNumber the zip Number
   * @param zipSuffixNumber the zip Suffix Number
   */
  public LawEnforcement(String archiveAssociationInd, String cityName,
      Integer contactPhoneExtensionNumber, String contactPersonName, BigDecimal contactPhoneNumber,
      String contactPositionTitleDescription, String emailAddress, BigDecimal faxNumber,
      Short governmentEntityType, String id, String lawEnforcementName, Short referenceNumber,
      String stationName, String streetName, String streetNumber, Integer zipNumber,
      Short zipSuffixNumber) {
    super();
    this.archiveAssociationInd = archiveAssociationInd;
    this.cityName = cityName;
    this.contactPhoneExtensionNumber = contactPhoneExtensionNumber;
    this.contactPersonName = contactPersonName;
    this.contactPhoneNumber = contactPhoneNumber;
    this.contactPositionTitleDescription = contactPositionTitleDescription;
    this.emailAddress = emailAddress;
    this.faxNumber = faxNumber;
    this.governmentEntityType = governmentEntityType;
    this.id = id;
    this.lawEnforcementName = lawEnforcementName;
    this.referenceNumber = referenceNumber;
    this.stationName = stationName;
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
   * @return the contactPhoneExtensionNumber
   */
  public Integer getContactPhoneExtensionNumber() {
    return contactPhoneExtensionNumber;
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
   * @return the governmentEntityType
   */
  public Short getGovernmentEntityType() {
    return governmentEntityType;
  }

  /**
   * @return the id
   */
  public String getId() {
    return id;
  }

  /**
   * @return the lawEnforcementName
   */
  public String getLawEnforcementName() {
    return lawEnforcementName;
  }

  /**
   * @return the referenceNumber
   */
  public Short getReferenceNumber() {
    return referenceNumber;
  }

  /**
   * @return the stationName
   */
  public String getStationName() {
    return stationName;
  }

  /**
   * @return the streetName
   */
  public String getStreetName() {
    return streetName;
  }

  /**
   * @return the streetName
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
   * @return the zipSuffixNumber
   */
  public Short getZipSuffixNumber() {
    return zipSuffixNumber;
  }

}
