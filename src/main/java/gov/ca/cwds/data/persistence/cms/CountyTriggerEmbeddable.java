package gov.ca.cwds.data.persistence.cms;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.Type;

/**
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

  @Column(name = "FKCNTY_OWNT", length = CMS_ID_LEN)
  private String countyOwnershipT;

  @Type(type = "timestamp")
  @Column(name = "INT_REG_TS")
  private Date integratorTimeStamp;

  /**
   * Default constructor
   * 
   * Required for Hibernate
   */
  public CountyTriggerEmbeddable() {
    // no opt
  }

  /**
   * @param countyOwnershipT - countyOwnershipT
   * @param integratorTimeStamp - intergratorTimeStamp
   */
  public CountyTriggerEmbeddable(String countyOwnershipT, Date integratorTimeStamp) {
    super();
    this.countyOwnershipT = countyOwnershipT;
    this.integratorTimeStamp = new Date();
  }

  /**
   * @return the countyOwnershipT
   */
  public String getCountyOwnershipT() {
    return countyOwnershipT;
  }

  /**
   * @return the integratorTimeStamp
   */
  public Date getIntegratorTimeStamp() {
    return integratorTimeStamp;
  }

  /**
   * @param integratorTimeStamp - integratorTimeStamp
   */
  public void setIntegratorTimeStamp(Date integratorTimeStamp) {
    this.integratorTimeStamp = integratorTimeStamp;
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
