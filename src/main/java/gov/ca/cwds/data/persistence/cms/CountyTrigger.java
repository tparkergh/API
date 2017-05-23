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
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import gov.ca.cwds.data.persistence.PersistentObject;

/**
 * Class representing an CountyTrigger.
 * 
 * @author CWDS API Team
 */
@Entity
@Table(name = "CNTYTRGT")
@JsonPropertyOrder(alphabetic = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CountyTrigger implements PersistentObject, Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  protected static final int CMS_ID_LEN = 10;

  @Id
  @Column(name = "FKCNTY_OWNT", length = CMS_ID_LEN)
  private String countyOwnershipT;

  @NotEmpty
  @Size(max = 2)
  @Column(name = "LGCID")
  private String logicId;

  @NotEmpty
  @Size(max = 2)
  @Column(name = "FKCNTY_OWN0")
  private String countyOwnership0;

  @Type(type = "timestamp")
  @Column(name = "INT_REG_TS")
  private Date integratorTimeStamp;

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
    this.countyOwnershipT = countyOwnershipT;
    this.logicId = logicId;
    this.countyOwnership0 = countyOwnership0;
    this.integratorTimeStamp = new Date();
    this.integratorEntity = integratorEntity;
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.data.persistence.PersistentObject#getPrimaryKey()
   */
  @Override
  public String getPrimaryKey() {
    return getCountyOwnershipT();
  }

  /**
   * @return the countyOwnershipT
   */
  public String getCountyOwnershipT() {
    return countyOwnershipT;
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
   * @return the integratorTimeStamp
   */
  public Date getIntegratorTimeStamp() {
    return integratorTimeStamp;
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
