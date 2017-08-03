package gov.ca.cwds.data.persistence.cms;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * {@link CmsPersistentObject} Class representing an IndividualDeliveredService.
 * 
 * @author CWDS API Team
 */
@Entity
@Table(name = "IDV_SVCT")
@JsonPropertyOrder(alphabetic = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class IndividualDeliveredServiceEntity extends CmsPersistentObject {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  protected static final String DATE_FORMAT = "yyyy-MM-dd";

  @Id
  private IndividualDeliveredServiceEmbeddable individualDeliveredServiceEmbeddable;

  @Column(name = "CNTY_SPFCD")
  private String countySpecificCode;

  @Type(type = "date")
  @Column(name = "END_DT")
  private Date endDate;

  @Column(name = "SVC_CNTC")
  private Short serviceContactType;

  @Type(type = "date")
  @Column(name = "START_DT")
  private Date startDate;

  /**
   * Default constructor
   * 
   * Required for Hibernate
   */
  public IndividualDeliveredServiceEntity() {
    super();
  }

  /**
   * @param deliveredServiceId The deliveredServiceId
   * @param deliveredToIndividualCode The deliveredToIndividualCode
   * @param deliveredToIndividualId The deliveredToIndividualId
   * @param countySpecificCode The countySpecificCode
   * @param endDate The endDate
   * @param serviceContactType The serviceContactType
   * @param startDate The startDate
   */
  public IndividualDeliveredServiceEntity(String deliveredServiceId,
      String deliveredToIndividualCode, String deliveredToIndividualId, String countySpecificCode,
      Date endDate, Short serviceContactType, Date startDate) {
    super();
    this.countySpecificCode = countySpecificCode;
    this.endDate = endDate;
    this.serviceContactType = serviceContactType;
    this.startDate = startDate;
    this.individualDeliveredServiceEmbeddable = new IndividualDeliveredServiceEmbeddable(
        deliveredServiceId, deliveredToIndividualCode, deliveredToIndividualId);
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.data.persistence.PersistentObject#getPrimaryKey()
   */
  @Override
  public IndividualDeliveredServiceEmbeddable getPrimaryKey() {
    return getIndividualDeliveredServiceEmbeddable();
  }

  /**
   * @return the individualDeliveredServiceEmbeddable
   */
  public IndividualDeliveredServiceEmbeddable getIndividualDeliveredServiceEmbeddable() {
    return individualDeliveredServiceEmbeddable;
  }

  /**
   * @return the countySpecificCode
   */
  public String getCountySpecificCode() {
    return countySpecificCode;
  }

  /**
   * @return the endDate
   */
  public Date getEndDate() {
    return endDate;
  }

  /**
   * @return the serviceContactType
   */
  public Short getServiceContactType() {
    return serviceContactType;
  }

  /**
   * @return the startDate
   */
  public Date getStartDate() {
    return startDate;
  }

}
