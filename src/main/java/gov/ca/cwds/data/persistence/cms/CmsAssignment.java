package gov.ca.cwds.data.persistence.cms;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import gov.ca.cwds.data.persistence.PersistentObject;

/**
 * {@link PersistentObject} representing a collateral individual who is a substitute care provider.
 * 
 * @author CWDS API Team
 */
@Entity
@DiscriminatorValue("S")
@JsonPropertyOrder(alphabetic = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CmsAssignment extends CmsPersistentObject {

  private static final long serialVersionUID = 1L;

  @Column(name = "ESTBLSH_ID")
  protected String establishedForId;

  /**
   * Default constructor. Required for Hibernate.
   */
  public CmsAssignment() {
    super();
  }

  @Override
  public Serializable getPrimaryKey() {
    return null;
  }

}
