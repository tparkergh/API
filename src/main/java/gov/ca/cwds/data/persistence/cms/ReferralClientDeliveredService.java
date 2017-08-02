package gov.ca.cwds.data.persistence.cms;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * {@link CmsPersistentObject} Class representing an ReferralClientDeliveredService.
 * 
 * @author CWDS API Team
 */
@Entity
@Table(name = "RFDLSVCT")
@JsonPropertyOrder(alphabetic = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReferralClientDeliveredService extends CmsPersistentObject {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  @Id
  private ReferralClientDeliveredServiceEmbeddable referralClientDeliveredServiceEmbeddable;

  @Column(name = "CNTY_SPFCD")
  private String countySpecificCode;

  /**
   * Default constructor
   * 
   * Required for Hibernate
   */
  public ReferralClientDeliveredService() {
    super();
  }

  /**
   * @param deliveredServiceId - deliveredServiceId
   * @param referralId - referralId
   * @param clientId - clientId
   * @param countySpecificCode - countySpecificCode
   */
  public ReferralClientDeliveredService(String deliveredServiceId, String referralId,
      String clientId, String countySpecificCode) {
    super();
    this.countySpecificCode = countySpecificCode;
    this.referralClientDeliveredServiceEmbeddable =
        new ReferralClientDeliveredServiceEmbeddable(deliveredServiceId, referralId, clientId);
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.data.persistence.PersistentObject#getPrimaryKey()
   */
  @Override
  public ReferralClientDeliveredServiceEmbeddable getPrimaryKey() {
    return getReferralClientDeliveredServiceEmbeddable();
  }


  /**
   * @return the referralClientDeliveredServiceEmbeddable
   */
  public ReferralClientDeliveredServiceEmbeddable getReferralClientDeliveredServiceEmbeddable() {
    return referralClientDeliveredServiceEmbeddable;
  }

  /**
   * @return the countySpecificCode
   */
  public String getCountySpecificCode() {
    return countySpecificCode;
  }

}
