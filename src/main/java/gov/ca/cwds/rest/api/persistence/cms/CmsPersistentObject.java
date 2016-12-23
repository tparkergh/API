package gov.ca.cwds.rest.api.persistence.cms;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.Type;

import gov.ca.cwds.rest.api.persistence.PersistentObject;

/**
 * Base class for objects in the legacy persistence layer.
 * 
 * @author CWDS API Team
 */
@MappedSuperclass
public abstract class CmsPersistentObject implements PersistentObject {

  /**
   * Baseline serialization version.
   */
  private static final long serialVersionUID = 1L;

  /**
   * Standard timestamp format for legacy DB2 tables.
   */
  protected static final String TIMESTAMP_FORMAT = "yyyy-MM-dd-HH.mm.ss.SSS";

  @Column(name = "\"LST_UPD_ID\"")
  private String lastUpdatedId;

  @Type(type = "timestamp")
  @Column(name = "\"LST_UPD_TS\"")
  private Date lastUpdatedTime;

  /**
   * Default constructor.
   * 
   * Required for some framework calls.
   */
  protected CmsPersistentObject() {}

  /**
   * Constructor.
   * 
   * @param lastUpdatedId the id of the last person to update this object
   */
  protected CmsPersistentObject(String lastUpdatedId) {
    this.lastUpdatedId = lastUpdatedId;
    this.lastUpdatedTime = new Date();
  }

  /**
   * @return the lastUpdatedId
   */
  public String getLastUpdatedId() {
    return lastUpdatedId;
  }

  /**
   * @return the lastUpdatedTime
   */
  public Date getLastUpdatedTime() {
    return lastUpdatedTime;
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.api.persistence.PersistentObject#getPrimaryKey()
   */
  @Override
  public abstract Serializable getPrimaryKey();
}
