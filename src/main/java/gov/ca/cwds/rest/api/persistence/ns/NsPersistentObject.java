package gov.ca.cwds.rest.api.persistence.ns;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.Type;

import gov.ca.cwds.rest.api.persistence.PersistentObject;

/**
 * Base class for objects in the NS persistence layer.
 * 
 * <p>
 * Type P stands for the primary key type, which must extend interface {@link Serializable}.
 * </p>
 * 
 * @author CWDS API Team
 */
@MappedSuperclass
public abstract class NsPersistentObject implements PersistentObject {
  protected static final String TIMESTAMP_FORMAT = "yyyy-MM-dd-HH.mm.ss.SSS";

  @Column(name = "update_user_id")
  private Long lastUpdatedId;

  @Type(type = "timestamp")
  @Column(name = "update_datetime")
  private Date lastUpdatedTime;


  /**
   * Default constructor
   * 
   * Required for Hibernate
   */
  protected NsPersistentObject() {

  }

  /**
   * Constructor
   * 
   * @param lastUpdatedId the id of the last person to update this object
   */
  protected NsPersistentObject(Long lastUpdatedId) {
    this.lastUpdatedId = lastUpdatedId;
    this.lastUpdatedTime = new Date();
  }

  /**
   * @return the lastUpdatedId
   */
  public Long getLastUpdatedId() {
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
