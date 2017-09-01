package gov.ca.cwds.data.persistence.ns;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import gov.ca.cwds.data.ns.NsPersistentObject;

/**
 * {@link NsPersistentObject} representing a Person.
 * 
 * @author CWDS API Team
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "intake_codes")
public class IntakeLovCode extends NsPersistentObject {

  @Column(name = "LG_SYS_ID")
  private Long id;

  @Column(name = "LG_META")
  private String legacyMeta;

  @Column(name = "LG_SHRT_DSC")
  private String legacyShortDescription;

  @Column(name = "LG_LOG_ID")
  private String legacyLogicalId;

  @Column(name = "LG_INACTIVE")
  private String legacyInactive;

  @Column(name = "LG_CAT_ID")
  private String legacyCategoryId;

  @Column(name = "LG_OTH_CD")
  private String legacyOtherCode;

  @Column(name = "LG_LNG_DSC")
  private String legacyLongDescription;

  @Column(name = "INTAKE_TYPE")
  private String intakeType;

  @Column(name = "INTAKE_CODE")
  private String intakeCode;

  @Column(name = "INTAKE_DISPLAY")
  private String intakeDisplay;

  /**
   * Default constructor
   * 
   * Required for Hibernate
   */
  public IntakeLovCode() {
    super();
  }

  /**
   * Constructor
   * 
   * @param reference The reference
   */
  public IntakeLovCode(String reference) {
    this.legacyMeta = reference;
  }

  /**
   * Constructor
   * 
   * @param reference The reference
   * @param endedAt The endedAt date
   * @param incidentCounty The incident county
   * @param incidentDate The incident date
   * @param locationType The location type
   * @param communicationMethod The communication method
   * @param name The name of the screening
   * @param responseTime The response time
   * @param screeningDecision The screening decision
   * @param startedAt The started at date
   */
  public IntakeLovCode(String reference, String endedAt, String incidentCounty, String incidentDate,
      String locationType, String communicationMethod, String name, String responseTime,
      String screeningDecision, String startedAt) {
    super();
    this.legacyMeta = reference;
    this.legacyShortDescription = endedAt;
    this.legacyLogicalId = incidentCounty;
    this.legacyInactive = incidentDate;
    this.legacyCategoryId = locationType;
    this.legacyOtherCode = communicationMethod;
    this.legacyLongDescription = name;
    this.intakeType = responseTime;
    this.intakeCode = screeningDecision;
    this.intakeDisplay = startedAt;
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.data.persistence.PersistentObject#getPrimaryKey()
   */
  @Override
  public Long getPrimaryKey() {
    return getId();
  }

  /**
   * @return the id
   */
  public Long getId() {
    return id;
  }

  public String getLegacyMeta() {
    return legacyMeta;
  }

  public void setLegacyMeta(String legacyMeta) {
    this.legacyMeta = legacyMeta;
  }

  public String getLegacyShortDescription() {
    return legacyShortDescription;
  }

  public void setLegacyShortDescription(String legacyShortDescription) {
    this.legacyShortDescription = legacyShortDescription;
  }

  public String getLegacyLogicalId() {
    return legacyLogicalId;
  }

  public void setLegacyLogicalId(String legacyLogicalId) {
    this.legacyLogicalId = legacyLogicalId;
  }

  public String getLegacyInactive() {
    return legacyInactive;
  }

  public void setLegacyInactive(String legacyInactive) {
    this.legacyInactive = legacyInactive;
  }

  public String getLegacyCategoryId() {
    return legacyCategoryId;
  }

  public void setLegacyCategoryId(String legacyCategoryId) {
    this.legacyCategoryId = legacyCategoryId;
  }

  public String getLegacyOtherCode() {
    return legacyOtherCode;
  }

  public void setLegacyOtherCode(String legacyOtherCode) {
    this.legacyOtherCode = legacyOtherCode;
  }

  public String getLegacyLongDescription() {
    return legacyLongDescription;
  }

  public void setLegacyLongDescription(String legacyLongDescription) {
    this.legacyLongDescription = legacyLongDescription;
  }

  public String getIntakeType() {
    return intakeType;
  }

  public void setIntakeType(String intakeType) {
    this.intakeType = intakeType;
  }

  public String getIntakeCode() {
    return intakeCode;
  }

  public void setIntakeCode(String intakeCode) {
    this.intakeCode = intakeCode;
  }

  public String getIntakeDisplay() {
    return intakeDisplay;
  }

  public void setIntakeDisplay(String intakeDisplay) {
    this.intakeDisplay = intakeDisplay;
  }

  public void setId(Long id) {
    this.id = id;
  }

}
