package gov.ca.cwds.data.persistence.cms;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * {@link CmsPersistentObject} Class representing an ContactPartyDeliveredService.
 * 
 * @author CWDS API Team
 */
@Entity
@Table(name = "CPTY_SVT")
@JsonPropertyOrder(alphabetic = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ContactPartyDeliveredService extends CmsPersistentObject {

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
  private String deliveryServiceId;

  /**
   * Default constructor
   * 
   * Required for Hibernate
   */
  public ContactPartyDeliveredService() {
    super();
  }

  /**
   * @param thirdId
   * @param contactPartyType
   * @param countySpecificCode
   * @param deliveryServiceId
   */
  public ContactPartyDeliveredService(String thirdId, Short contactPartyType,
      String countySpecificCode, String deliveryServiceId) {
    super();
    this.thirdId = thirdId;
    this.contactPartyType = contactPartyType;
    this.countySpecificCode = countySpecificCode;
    this.deliveryServiceId = deliveryServiceId;
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
  public String getDeliveryServiceId() {
    return deliveryServiceId;
  }

}
