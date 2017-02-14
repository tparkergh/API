package gov.ca.cwds.data.persistence.cms;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.NamedNativeQueries;
import org.hibernate.annotations.NamedNativeQuery;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;
import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import gov.ca.cwds.data.IPersonAware;
import gov.ca.cwds.data.persistence.PersistentObject;


/**
 * {@link PersistentObject} representing a OtherChildInPlacemtHome
 * 
 * @author CWDS API Team
 */
@NamedQueries({
    @NamedQuery(name = "gov.ca.cwds.data.persistence.cms.OtherChildInPlacemtHome.findAll",
        query = "FROM OtherChildInPlacemtHome"),
    @NamedQuery(
        name = "gov.ca.cwds.data.persistence.cms.OtherChildInPlacemtHome.findAllUpdatedAfter",
        query = "FROM OtherChildInPlacemtHome WHERE lastUpdatedTime > :after")})
@NamedNativeQueries({@NamedNativeQuery(
    name = "gov.ca.cwds.data.persistence.cms.OtherChildInPlacemtHome.findAllByBucket",
    query = "select z.IDENTIFIER, z.BIRTH_DT, z.GENDER_CD, z.OTHCHLD_NM, "
        + "z.LST_UPD_ID, z.LST_UPD_TS, z.FKPLC_HM_T, z.YR_INC_AMT "
        + "from ( select mod(y.rn, CAST(:total_buckets AS INTEGER)) + 1 as bucket, y.* "
        + "from ( select row_number() over (order by 1) as rn, x.* "
        + "from ( select c.* from {h-schema}OTH_KIDT c "
        + ") x ) y ) z where z.bucket = :bucket_num for read only",
    resultClass = OtherChildInPlacemtHome.class)})
@Entity
@Table(name = "OTH_KIDT")
@JsonPropertyOrder(alphabetic = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class OtherChildInPlacemtHome extends CmsPersistentObject implements IPersonAware {

  /**
  * 
  */
  private static final long serialVersionUID = 1L;

  @Column(name = "YR_INC_AMT")
  private BigDecimal annualUnearnedIncomeAmount;

  @Type(type = "date")
  @Column(name = "BIRTH_DT")
  private Date birthDate;

  @Column(name = "FKPLC_HM_T")
  private String fkplcHmT;

  @Column(name = "GENDER_CD")
  private String genderCode;

  @Id
  @Column(name = "IDENTIFIER", length = CMS_ID_LEN)
  private String id;

  @Column(name = "OTHCHLD_NM")
  private String name;

  /**
   * Default constructor
   * 
   * Required for Hibernate
   */
  public OtherChildInPlacemtHome() {
    super();
  }

  /**
   * @param annualUnearnedIncomeAmount The annualUnearnedIncomeAmount
   * @param birthDate The birthDate
   * @param fkplcHmT The fkplcHmT
   * @param genderCode The genderCode
   * @param id The id
   * @param name The name
   */
  public OtherChildInPlacemtHome(BigDecimal annualUnearnedIncomeAmount, Date birthDate,
      String fkplcHmT, String genderCode, String id, String name) {
    super();
    this.annualUnearnedIncomeAmount = annualUnearnedIncomeAmount;
    this.birthDate = birthDate;
    this.fkplcHmT = fkplcHmT;
    this.genderCode = genderCode;
    this.id = id;
    this.name = name;
  }


  @JsonIgnore
  @Override
  public String getFirstName() {
    return null;
  }

  @JsonIgnore
  @Override
  public String getMiddleName() {
    return null;
  }

  @JsonIgnore
  @Override
  public String getLastName() {
    return this.getName();
  }

  @JsonIgnore
  @Override
  public String getGender() {
    return this.getGenderCode();
  }

  @JsonIgnore
  @Override
  public String getSsn() {
    return null;
  }

  @JsonIgnore
  @Override
  public String getNameSuffix() {
    return null;
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
   * @return the annualUnearnedIncomeAmount
   */
  public BigDecimal getAnnualUnearnedIncomeAmount() {
    return annualUnearnedIncomeAmount;
  }

  /**
   * @return the birthDate
   */
  @Override
  public Date getBirthDate() {
    return birthDate;
  }

  /**
   * @return the fkplcHmT
   */
  public String getFkplcHmT() {
    return StringUtils.trimToEmpty(fkplcHmT);
  }

  /**
   * @return the genderCode
   */
  public String getGenderCode() {
    return StringUtils.trimToEmpty(genderCode);
  }

  /**
   * @return the id
   */
  public String getId() {
    return StringUtils.trimToEmpty(id);
  }

  /**
   * @return the name
   */
  public String getName() {
    return StringUtils.trimToEmpty(name);
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
