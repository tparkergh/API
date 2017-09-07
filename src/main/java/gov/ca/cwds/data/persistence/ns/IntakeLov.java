package gov.ca.cwds.data.persistence.ns;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnTransformer;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

import gov.ca.cwds.data.ns.NsPersistentObject;
import gov.ca.cwds.data.persistence.PersistentObject;

/**
 * {@link NsPersistentObject} representing a Person.
 * 
 * @author CWDS API Team
 */
@SuppressWarnings("serial")
@NamedQueries({@NamedQuery(name = "gov.ca.cwds.data.persistence.ns.IntakeLov.findAll",
    query = "FROM IntakeLov WHERE intakeCode IS NOT NULL AND intakeCode != '' ORDER BY intakeType, INTAKE_CODE")})
@Entity
@Table(name = "VW_INTAKE_LOV")
public class IntakeLov implements PersistentObject {

  @Id
  @Column(name = "LG_SYS_ID")
  private Long legacySystemCodeId;

  @Column(name = "LG_META")
  @ColumnTransformer(read = "trim(LG_META)")
  private String legacyMeta;

  @Column(name = "LG_SHRT_DSC")
  @ColumnTransformer(read = "trim(LG_SHRT_DSC)")
  private String legacyShortDescription;

  @Column(name = "LG_LOG_ID")
  private String legacyLogicalCode;

  @Column(name = "USE_LOG_ID")
  private boolean useLogical;

  @Column(name = "LG_CAT_ID")
  private String legacyCategoryId;

  @Column(name = "LG_OTH_CD")
  private String legacyOtherCode;

  @Column(name = "LG_LNG_DSC")
  @ColumnTransformer(read = "trim(LG_LNG_DSC)")
  private String legacyLongDescription;

  @Id
  @Column(name = "INTAKE_TYPE")
  @ColumnTransformer(read = "trim(INTAKE_TYPE)")
  private String intakeType;

  @Id
  @Column(name = "INTAKE_CODE")
  @ColumnTransformer(read = "trim(INTAKE_CODE)")
  private String intakeCode;

  @Column(name = "INTAKE_DISPLAY")
  @ColumnTransformer(read = "trim(INTAKE_DISPLAY)")
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
   * @param legacyLogicalCode legacy logical code
   * @param useLogical legacy inactive flag
   * @param legacyCategoryId legacy category id
   * @param legacyOtherCode legacy other code
   * @param legacyLongDescription legacy long description
   * @param intakeType Intake type category
   * @param intakeCode Intake LOV code
   * @param intakeDisplay Intake display string
   */
  public IntakeLov(String legacyMeta, String legacyShortDescription, String legacyLogicalCode,
      boolean useLogical, String legacyCategoryId, String legacyOtherCode,
      String legacyLongDescription, String intakeType, String intakeCode, String intakeDisplay) {
    super();
    this.legacyMeta = legacyMeta;
    this.legacyShortDescription = legacyShortDescription;
    this.legacyLogicalCode = legacyLogicalCode;
    this.useLogical = useLogical;
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

  public String getLegacyLogicalCode() {
    return legacyLogicalCode;
  }

  public void setLegacyLogicalCode(String legacyLogicalId) {
    this.legacyLogicalCode = legacyLogicalId;
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

  public boolean isUseLogical() {
    return useLogical;
  }

  public void setUseLogical(boolean useLogical) {
    this.useLogical = useLogical;
  }

}
