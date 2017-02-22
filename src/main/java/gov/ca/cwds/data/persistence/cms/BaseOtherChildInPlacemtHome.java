package gov.ca.cwds.data.persistence.cms;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnore;

import gov.ca.cwds.data.std.ApiPersonAware;

@MappedSuperclass
public abstract class BaseOtherChildInPlacemtHome extends CmsPersistentObject
    implements ApiPersonAware {

  /**
  * 
  */
  private static final long serialVersionUID = 1L;

  @Column(name = "YR_INC_AMT")
  protected BigDecimal annualUnearnedIncomeAmount;

  @Type(type = "date")
  @Column(name = "BIRTH_DT")
  protected Date birthDate;

  @Column(name = "FKPLC_HM_T")
  protected String fkplcHmT;

  @Column(name = "GENDER_CD")
  protected String genderCode;

  @Id
  @Column(name = "IDENTIFIER", length = CMS_ID_LEN)
  protected String id;

  @Column(name = "OTHCHLD_NM")
  protected String name;

  public BaseOtherChildInPlacemtHome() {
    super();
  }

  public BaseOtherChildInPlacemtHome(String lastUpdatedId) {
    super(lastUpdatedId);
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

}
