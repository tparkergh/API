package gov.ca.cwds.data.persistence.contact;

import gov.ca.cwds.data.persistence.cms.CmsPersistentObject;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.NamedQuery;
import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * {@link CmsPersistentObject} Class representing an IndividualDeliveredService.
 * 
 * @author CWDS API Team
 */
@NamedQuery(
    name = "gov.ca.cwds.data.persistence.contact.IndividualDeliveredServiceEntity.findAllForDeliveredService",
    query = "FROM IndividualDeliveredServiceEntity WHERE deliveredServiceId = :deliveredServiceId")
@Entity
@Table(name = "IDV_SVCT")
@JsonPropertyOrder(alphabetic = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class IndividualDeliveredServiceEntity extends CmsPersistentObject {

  /**
   * Default.
   */
  private static final long serialVersionUID = 1L;

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

  @Column(name = "FKDL_SVC_T", updatable = false, insertable = false,
      length = CmsPersistentObject.CMS_ID_LEN)
  private String deliveredServiceId;

  @Column(name = "DEL_IDV_CD", updatable = false, insertable = false)
  private String deliveredToIndividualCode;

  @Column(name = "DEL_IDV_ID", updatable = false, insertable = false,
      length = CmsPersistentObject.CMS_ID_LEN)
  private String deliveredToIndividualId;



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
    this.deliveredServiceId = deliveredServiceId;
    this.deliveredToIndividualCode = deliveredToIndividualCode;
    this.deliveredToIndividualId = deliveredToIndividualId;
    this.individualDeliveredServiceEmbeddable =
        new IndividualDeliveredServiceEmbeddable(deliveredServiceId, deliveredToIndividualCode,
            deliveredToIndividualId);

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
