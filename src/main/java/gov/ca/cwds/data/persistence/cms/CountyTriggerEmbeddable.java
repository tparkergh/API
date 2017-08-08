package gov.ca.cwds.data.persistence.cms;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.validator.constraints.NotEmpty;

import gov.ca.cwds.data.persistence.PersistentObject;

/**
 * {@link PersistentObject} representing an County Trigger Embeddable
 * 
 * <p>
 * CountyTriggerEmbeddable is the primaryKey representation of countyTrigger and making the two
 * columns(logicId, countyOwnership0, countyOwnership0 and integratorEntity) as composite keys in
 * the trigger table values, as the primary can be repeatable.
 * <p>
 * 
 * @author CWDS API Team
 * 
 */
@Embeddable
public class CountyTriggerEmbeddable implements Serializable {

  /**
   * Serialization Version
   */
  private static final long serialVersionUID = 1L;

  protected static final int CMS_ID_LEN = CmsPersistentObject.CMS_ID_LEN;

  @NotEmpty
  @Size(max = 2)
  @Column(name = "LGCID")
  private String logicId;

  @NotEmpty
  @Size(max = 2)
  @Column(name = "FKCNTY_OWN0")
  private String countyOwnership0;

  @Column(name = "FKCNTY_OWNT", length = CMS_ID_LEN)
  private String countyOwnershipT;

  @NotEmpty
  @Size(max = 8)
  @Column(name = "INTREG_ENT")
  private String integratorEntity;

  /**
   * Default constructor
   * 
   * Required for Hibernate
   */
  public CountyTriggerEmbeddable() {
    // no opt
  }

  /**
   * @param logicId - logicId
   * @param countyOwnership0 - countyOwnership0
   * @param countyOwnershipT - countyOwnershipT
   * @param integratorEntity - integratorEntity
   */
  public CountyTriggerEmbeddable(String logicId, String countyOwnership0, String countyOwnershipT,
      String integratorEntity) {
    super();
    this.logicId = logicId;
    this.countyOwnership0 = countyOwnership0;
    this.countyOwnershipT = countyOwnershipT;
    this.integratorEntity = integratorEntity;
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
   * @return the countyOwnershipT
   */
  public String getCountyOwnershipT() {
    return countyOwnershipT;
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
