package gov.ca.cwds.data.persistence.cms;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.validator.constraints.NotEmpty;

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

  @NotEmpty
  @Size(max = 2)
  @Column(name = "LGCID")
  private String logicId;

  @NotEmpty
  @Size(max = 2)
  @Column(name = "FKCNTY_OWN0")
  private String countyOwnership0;

  @NotEmpty
  @Size(max = 8)
  @Column(name = "INTREG_ENT")
  private String integratorEntity;

  /**
   * Default Constructor
   */
  public CountyTrigger() {
    super();
  }

  /**
   * @param countyOwnershipT - countyOwnershipT
   * @param logicId - logicId
   * @param countyOwnership0 - countyOwnership0
   * @param integratorTimeStamp - integratorTimeStamp
   * @param integratorEntity - integratorEntity
   */
  public CountyTrigger(String countyOwnershipT, String logicId, String countyOwnership0,
      Date integratorTimeStamp, String integratorEntity) {
    super();
    this.logicId = logicId;
    this.countyOwnership0 = countyOwnership0;
    this.integratorEntity = integratorEntity;
    this.countyTriggerEmbeddable =
        new CountyTriggerEmbeddable(countyOwnershipT, integratorTimeStamp);
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
   * @return the logicId
   */
  public String getLogicId() {
    return logicId;
  }

  /**
   * @return the countyOwnership0
   */
  public String getCountyOwnership0() {
    return countyOwnership0;
  }

  /**
   * @return the integratorEntity
   */
  public String getIntegratorEntity() {
    return integratorEntity;
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
