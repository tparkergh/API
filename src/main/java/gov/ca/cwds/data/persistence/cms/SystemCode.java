package gov.ca.cwds.data.persistence.cms;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.NamedQuery;
import org.hibernate.annotations.Type;

import gov.ca.cwds.data.persistence.PersistentObject;


/**
 * {@link PersistentObject} representing a SystemCode
 * 
 * @author CWDS API Team
 */
@NamedQuery(name = "gov.ca.cwds.data.persistence.cms.SystemCode.findAll",
    query = "FROM SystemCode  WHERE inactiveIndicator = 'N' AND  foreignKeyMetaTable = :metaTable")
@Entity
@Table(schema = "CWSINT", name = "SYS_CD_C")
public class SystemCode extends CmsPersistentObject {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  @Type(type = "short")
  @Column(name = "SYS_ID")
  private Short systemCode;

  @Type(type = "short")
  @Column(name = "CATEGORYID")
  private Short categoryId;

  @Column(name = "INACTV_IND")
  private String inactiveIndicator;

  @Column(name = "OTHER_CD")
  private String otherCd;

  @Column(name = "SHORT_DSC")
  private String shortDescription;

  @Type(type = "short")
  @Column(name = "LGC_ID")
  private Short logicalId;

  @Column(name = "THIRD_ID")
  private String thridId;

  @Column(name = "FKS_META_T")
  private String foreignKeyMetaTable;

  @Column(name = "LONG_DSC")
  private String longDescription;

  /**
   * Default constructor
   * 
   * Required for Hibernate
   */
  public SystemCode() {
    super();
  }

  /**
   * @param systemCode - The System Code
   * @param categoryId - the category id
   * @param inactiveIndicator - inactive indicator
   * @param otherCd - other CD
   * @param shortDescription - the short description
   * @param logicalId - the logical Id
   * @param thirdId - third Id
   * @param foreignKeyMetaTable - - foreign key to system meta table (S_META_T)
   * @param longDescription - long description
   */
  public SystemCode(Short systemCode, Short categoryId, String inactiveIndicator, String otherCd,
      String shortDescription, Short logicalId, String thirdId, String foreignKeyMetaTable,
      String longDescription) {
    super();
    this.systemCode = systemCode;
    this.categoryId = categoryId;
    this.inactiveIndicator = inactiveIndicator;
    this.otherCd = otherCd;
    this.shortDescription = shortDescription;
    this.logicalId = logicalId;
    this.thridId = thirdId;
    this.foreignKeyMetaTable = foreignKeyMetaTable;
    this.longDescription = longDescription;
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.data.persistence.PersistentObject#getPrimaryKey()
   */
  @Override
  public Short getPrimaryKey() {
    return getSystemCode();
  }

  /**
   * @return serialVersionUID
   */
  public static long getSerialversionuid() {
    return serialVersionUID;
  }

  /**
   * @return system code (unique)
   */
  public Short getSystemCode() {
    return systemCode;
  }

  /**
   * @return category ID
   */
  public Short getCategoryId() {
    return categoryId;
  }

  /**
   * @return inactive indicator
   */
  public String getInactiveIndicator() {
    return inactiveIndicator;
  }

  /**
   * @return other CD
   */
  public String getOtherCd() {
    return otherCd;
  }

  /**
   * @return short description
   */
  public String getShortDescription() {
    return shortDescription;
  }

  /**
   * @return logical ID
   */
  public Short getLogicalId() {
    return logicalId;
  }

  /**
   * @return third ID (not used in legacy DB)
   */
  public String getThridId() {
    return thridId;
  }

  /**
   * @return foreign key to the System Meta Table
   */
  public String getForeignKeyMetaTable() {
    return foreignKeyMetaTable;
  }

  /**
   * @return long description
   */
  public String getLongDescription() {
    return longDescription;
  }

  @Override
  public final int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this, false);
  }

  @Override
  public final boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj, false);
  }

}
