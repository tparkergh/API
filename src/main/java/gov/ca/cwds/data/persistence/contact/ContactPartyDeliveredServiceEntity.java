package gov.ca.cwds.data.persistence.contact;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import gov.ca.cwds.data.persistence.cms.CmsPersistentObject;

/**
 * {@link CmsPersistentObject} Class representing an ContactPartyDeliveredService.
 * 
 * @author CWDS API Team
 */
@Entity
@Table(name = "CPTY_SVT")
@JsonPropertyOrder(alphabetic = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ContactPartyDeliveredServiceEntity extends CmsPersistentObject {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "THIRD_ID", length = CMS_ID_LEN)
  private String thirdId;

  @Type(type = "short")
  @Column(name = "CNT_PRTC")
  private Short contactPartyType;

  @Column(name = "CNTY_SPFCD")
  private String countySpecificCode;

  @Column(name = "FKDL_SVC_T")
  private String deliveredServiceId;

  /**
   * Default constructor
   * 
   * Required for Hibernate
   */
  public ContactPartyDeliveredServiceEntity() {
    super();
  }

  /**
   * @param thirdId - thirdId
   * @param contactPartyType - contactPartyType
   * @param countySpecificCode - countySpecificCode
   * @param deliveredServiceId - deliveredServiceId
   */
  public ContactPartyDeliveredServiceEntity(String thirdId, Short contactPartyType,
      String countySpecificCode, String deliveredServiceId) {
    super();
    this.thirdId = thirdId;
    this.contactPartyType = contactPartyType;
    this.countySpecificCode = countySpecificCode;
    this.deliveredServiceId = deliveredServiceId;
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.data.persistence.PersistentObject#getPrimaryKey()
   */
  @Override
  public String getPrimaryKey() {
    return getThirdId();
  }

  /**
   * @return the thirdId
   */
  public String getThirdId() {
    return thirdId;
  }

  /**
   * @return the contactPartyType
   */
  public Short getContactPartyType() {
    return contactPartyType;
  }

  /**
   * @return the countySpecificCode
   */
  public String getCountySpecificCode() {
    return countySpecificCode;
  }

  /**
   * @return the deliveryServiceId
   */
  public String getDeliveredServiceId() {
    return deliveredServiceId;
  }

}
