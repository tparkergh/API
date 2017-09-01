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
@Table(name = "intake_raw_codes")
public class IntakeLov extends NsPersistentObject {

  @Column(name = "LG_SYS_ID")
  private Long legacySystemCodeId;

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
  public IntakeLov() {
    super();
  }

  /**
   * Constructor
   * 
   * @param reference The reference
   */
  public IntakeLov(String reference) {
    this.legacyMeta = reference;
  }

  /**
   * Constructor.
   * 
   * @param legacyMeta legacy "meta" category
   * @param legacyShortDescription legacy short description
   * @param legacyLogicalId legacy logical id
   * @param legacyInactive legacy inactive flag
   * @param legacyCategoryId legacy category id
   * @param legacyOtherCode legacy other code
   * @param legacyLongDescription legacy long description
   * @param intakeType Intake type category
   * @param intakeCode Intake LOV code
   * @param intakeDisplay Intake display string
   */
  public IntakeLov(String legacyMeta, String legacyShortDescription, String legacyLogicalId,
      String legacyInactive, String legacyCategoryId, String legacyOtherCode,
      String legacyLongDescription, String intakeType, String intakeCode, String intakeDisplay) {
    super();
    this.legacyMeta = legacyMeta;
    this.legacyShortDescription = legacyShortDescription;
    this.legacyLogicalId = legacyLogicalId;
    this.legacyInactive = legacyInactive;
    this.legacyCategoryId = legacyCategoryId;
    this.legacyOtherCode = legacyOtherCode;
    this.legacyLongDescription = legacyLongDescription;
    this.intakeType = intakeType;
    this.intakeCode = intakeCode;
    this.intakeDisplay = intakeDisplay;
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.data.persistence.PersistentObject#getPrimaryKey()
   */
  @Override
  public Long getPrimaryKey() {
    return getLegacySystemCodeId();
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

  public Long getLegacySystemCodeId() {
    return legacySystemCodeId;
  }

  public void setLegacySystemCodeId(Long legacySystemCodeId) {
    this.legacySystemCodeId = legacySystemCodeId;
  }

}
