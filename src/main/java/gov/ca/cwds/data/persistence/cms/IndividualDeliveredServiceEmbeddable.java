package gov.ca.cwds.data.persistence.cms;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import gov.ca.cwds.data.persistence.PersistentObject;

/**
 * {@link PersistentObject} representing an Individual Delivered Service Embeddable
 * 
 * <p>
 * IndividualDeliveredServiceEmbeddable is the primaryKey representation of
 * IndividualDeliveredServiceEmbeddable and making the three columns(deliveredServiceId,
 * deliveredToIndividualCode and deliveredToIndividualId) as composite keys, as the primary can be
 * repeatable.
 * <p>
 * 
 * @author CWDS API Team
 * 
 */
@Embeddable
public class IndividualDeliveredServiceEmbeddable implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  protected static final int CMS_ID_LEN = CmsPersistentObject.CMS_ID_LEN;

  @Column(name = "FKDL_SVC_T", length = CMS_ID_LEN)
  private String deliveredServiceId;

  @Column(name = "DEL_IDV_CD")
  private String deliveredToIndividualCode;

  @Column(name = "DEL_IDV_ID", length = CMS_ID_LEN)
  private String deliveredToIndividualId;

  /**
   * Default constructor
   * 
   * Required for Hibernate
   */
  public IndividualDeliveredServiceEmbeddable() {
    // no-opt
  }

  /**
   * @param deliveredServiceId
   * @param deliveredToIndividualCode
   * @param deliveredToIndividualId
   */
  public IndividualDeliveredServiceEmbeddable(String deliveredServiceId,
      String deliveredToIndividualCode, String deliveredToIndividualId) {
    super();
    this.deliveredServiceId = deliveredServiceId;
    this.deliveredToIndividualCode = deliveredToIndividualCode;
    this.deliveredToIndividualId = deliveredToIndividualId;
  }

  /**
   * @return the deliveredServiceId
   */
  public String getDeliveredServiceId() {
    return deliveredServiceId;
  }

  /**
   * @return the deliveredToIndividualCode
   */
  public String getDeliveredToIndividualCode() {
    return deliveredToIndividualCode;
  }

  /**
   * @return the deliveredToIndividualId
   */
  public String getDeliveredToIndividualId() {
    return deliveredToIndividualId;
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.lang.Object#hashCode()
   */
  @Override
  public final int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this, false);
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public final boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj, false);
  }

}
