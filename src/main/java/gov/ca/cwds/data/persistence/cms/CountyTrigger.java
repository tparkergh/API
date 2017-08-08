package gov.ca.cwds.data.persistence.cms;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import gov.ca.cwds.data.persistence.PersistentObject;

/**
 * {@link PersistentObject} Class representing an CountyTrigger.
 * 
 * @author CWDS API Team
 */
@Entity
@Table(name = "CNTYTRGT")
@JsonPropertyOrder(alphabetic = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CountyTrigger implements PersistentObject, Serializable {

  /**
   * Serialization Version
   */
  private static final long serialVersionUID = 1L;

  protected static final int CMS_ID_LEN = CmsPersistentObject.CMS_ID_LEN;

  @Id
  private CountyTriggerEmbeddable countyTriggerEmbeddable;

  @Type(type = "timestamp")
  @Column(name = "INT_REG_TS")
  private Date integratorTimeStamp;

  /**
   * Default Constructor
   */
  public CountyTrigger() {
    super();
  }

  /**
   * @param logicId - logicId
   * @param countyOwnership0 - countyOwnership0
   * @param countyOwnershipT - countyOwnershipT
   * @param integratorEntity - integratorEntity
   * @param integratorTimeStamp - integratorTimeStamp
   */
  public CountyTrigger(String logicId, String countyOwnership0, String countyOwnershipT,
      String integratorEntity, Date integratorTimeStamp) {
    super();
    this.countyTriggerEmbeddable =
        new CountyTriggerEmbeddable(logicId, countyOwnership0, countyOwnershipT, integratorEntity);
    this.integratorTimeStamp = integratorTimeStamp;
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.data.persistence.PersistentObject#getPrimaryKey()
   */
  @Override
  public CountyTriggerEmbeddable getPrimaryKey() {
    return getCountyTriggerEmbeddable();
  }

  /**
   * @return the countyTriggerEmbeddable
   */
  public CountyTriggerEmbeddable getCountyTriggerEmbeddable() {
    return countyTriggerEmbeddable;
  }

  /**
   * @return the integratorTimeStamp
   */
  public Date getIntegratorTimeStamp() {
    return integratorTimeStamp;
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
