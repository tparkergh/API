package gov.ca.cwds.data.persistence.cms;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import gov.ca.cwds.rest.api.domain.SystemCodeCategoryId;
import gov.ca.cwds.rest.validation.ValidSystemCodeId;
import io.dropwizard.validation.OneOf;

/**
 * {@link CmsPersistentObject} Class representing an ClientScpEthnicity.
 * 
 * @author CWDS API Team
 */
@Entity
@Table(name = "CLSCP_ET")
@JsonPropertyOrder(alphabetic = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ClientScpEthnicity extends CmsPersistentObject {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "IDENTIFIER", length = CMS_ID_LEN)
  private String id;

  @OneOf(value = {"C", "S"}, ignoreCase = false, ignoreWhitespace = true)
  @Column(name = "ESTBLSH_CD")
  private String establishedForCode;

  @Column(name = "ESTBLSH_ID")
  private String establishedId;

  @ValidSystemCodeId(required = false, category = SystemCodeCategoryId.ETHNICITY)
  @Column(name = "ETHNCTYC")
  private Short ethnicity;

  /**
   * Default constructor
   * 
   * Required for Hibernate
   */
  public ClientScpEthnicity() {
    super();
  }

  /**
   * Constructor
   * 
   * @param id primary key
   * @param establishedForCode established For Code
   * @param establishedId established Id
   * @param ethnicity ethnicity
   * @param lastUpdatedId lastUpdatedId
   * @param lastUpdatedTime lastUpdatedTime
   */
  public ClientScpEthnicity(String id, String establishedForCode, String establishedId,
      Short ethnicity, String lastUpdatedId, Date lastUpdatedTime) {
    super(lastUpdatedId, lastUpdatedTime);
    this.id = id;
    this.establishedForCode = establishedForCode;
    this.establishedId = establishedId;
    this.ethnicity = ethnicity;
  }

  /**
   * Constructor using domain
   * 
   * @param id The primary key
   * @param domainClientScpEthnicity The domain object to construct this object from
   * @param lastUpdatedId The id of the last person to update this object
   * @param lastUpdatedTime The time when this object is last updated
   */
  public ClientScpEthnicity(String id,
      gov.ca.cwds.rest.api.domain.cms.ClientScpEthnicity domainClientScpEthnicity,
      String lastUpdatedId, Date lastUpdatedTime) {
    super(lastUpdatedId, lastUpdatedTime);
    this.id = id;
    this.establishedForCode = domainClientScpEthnicity.getEstablishedForCode();
    this.establishedId = domainClientScpEthnicity.getEstablishedId();
    this.ethnicity = domainClientScpEthnicity.getEthnicity();
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.data.persistence.PersistentObject#getPrimaryKey()
   */
  @Override
  public String getPrimaryKey() {
    return getId();
  }

  /**
   * @return the id
   */
  public String getId() {
    return id;
  }

  /**
   * @return the establishedForCode
   */
  public String getEstablishedForCode() {
    return establishedForCode;
  }

  /**
   * @return the establishedId
   */
  public String getEstablishedId() {
    return establishedId;
  }

  /**
   * @return the ethnicity
   */
  public Short getEthnicity() {
    return ethnicity;
  }

}
