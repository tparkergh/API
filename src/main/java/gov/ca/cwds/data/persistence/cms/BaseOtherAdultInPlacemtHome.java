package gov.ca.cwds.data.persistence.cms;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnore;

import gov.ca.cwds.data.std.ApiPersonAware;

@MappedSuperclass
public abstract class BaseOtherAdultInPlacemtHome extends CmsPersistentObject
    implements ApiPersonAware {

  /**
   * Base serialization version. Increment by class version.
   */
  private static final long serialVersionUID = 1L;

  @Type(type = "date")
  @Column(name = "BIRTH_DT")
  protected Date birthDate;

  @Column(name = "COMNT_DSC")
  protected String commentDescription;

  @Type(type = "date")
  @Column(name = "END_DT")
  protected Date endDate;

  @Column(name = "FKPLC_HM_T", length = CMS_ID_LEN)
  protected String fkplcHmT;

  @Column(name = "GENDER_CD")
  protected String genderCode;

  @Id
  @Column(name = "IDENTIFIER", length = CMS_ID_LEN)
  protected String id;

  @Type(type = "date")
  @Column(name = "IDENTFD_DT")
  protected Date identifiedDate;

  @Column(name = "OTH_ADLTNM")
  protected String name;

  @Column(name = "OTH_ADL_CD")
  protected String otherAdultCode;

  @Column(name = "PASSBC_CD")
  protected String passedBackgroundCheckCode;

  @Column(name = "RESOST_IND")
  protected String residedOutOfStateIndicator;

  @Type(type = "date")
  @Column(name = "START_DT")
  protected Date startDate;

  public BaseOtherAdultInPlacemtHome() {
    super();
  }

  public BaseOtherAdultInPlacemtHome(String lastUpdatedId) {
    super(lastUpdatedId);
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
   * @return the birthDate
   */
  @Override
  public Date getBirthDate() {
    return birthDate;
  }

  /**
   * @return the commentDescription
   */
  public String getCommentDescription() {
    return StringUtils.trimToEmpty(commentDescription);
  }

  /**
   * @return the endDate
   */
  public Date getEndDate() {
    return endDate;
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
   * @return the identifiedDate
   */
  public Date getIdentifiedDate() {
    return identifiedDate;
  }

  /**
   * @return the name
   */
  public String getName() {
    return StringUtils.trimToEmpty(name);
  }

  /**
   * @return the otherAdultCode
   */
  public String getOtherAdultCode() {
    return StringUtils.trimToEmpty(otherAdultCode);
  }

  /**
   * @return the passedBackgroundCheckCode
   */
  public String getPassedBackgroundCheckCode() {
    return StringUtils.trimToEmpty(passedBackgroundCheckCode);
  }

  /**
   * @return the residedOutOfStateIndicator
   */
  public String getResidedOutOfStateIndicator() {
    return StringUtils.trimToEmpty(residedOutOfStateIndicator);
  }

  /**
   * @return the startDate
   */
  public Date getStartDate() {
    return startDate;
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

}
