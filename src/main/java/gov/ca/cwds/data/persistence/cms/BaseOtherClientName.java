package gov.ca.cwds.data.persistence.cms;

import java.beans.Transient;
import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import gov.ca.cwds.data.CmsSystemCodeDeserializer;
import gov.ca.cwds.data.SystemCodeSerializer;
import gov.ca.cwds.data.std.ApiPersonAware;

@MappedSuperclass
public abstract class BaseOtherClientName extends CmsPersistentObject implements ApiPersonAware {

  @Column(name = "FIRST_NM")
  protected String firstName;

  @Column(name = "FKCLIENT_T", length = CMS_ID_LEN)
  protected String clientId;

  @Column(name = "LAST_NM")
  protected String lastName;

  @Column(name = "MIDDLE_NM")
  protected String middleName;

  @Column(name = "NMPRFX_DSC")
  protected String namePrefixDescription;

  @SystemCodeSerializer(logical = true, description = true)
  @JsonDeserialize(using = CmsSystemCodeDeserializer.class)
  @Type(type = "short")
  @Column(name = "NAME_TPC")
  protected Short nameType;

  @Column(name = "SUFX_TLDSC")
  protected String suffixTitleDescription;

  @Id
  @Column(name = "THIRD_ID", length = CMS_ID_LEN)
  protected String thirdId;

  public BaseOtherClientName() {
    super();
  }

  public BaseOtherClientName(String lastUpdatedId) {
    super(lastUpdatedId);
  }

  /**
   * @return the firstName
   */
  @Override
  public String getFirstName() {
    return StringUtils.trimToEmpty(firstName);
  }

  /**
   * @return the lastName
   */
  @Override
  public String getLastName() {
    return StringUtils.trimToEmpty(lastName);
  }

  /**
   * @return the middleName
   */
  @Override
  public String getMiddleName() {
    return StringUtils.trimToEmpty(middleName);
  }

  /**
   * @return the namePrefixDescription
   */
  public String getNamePrefixDescription() {
    return StringUtils.trimToEmpty(namePrefixDescription);
  }

  /**
   * @return the nameType
   */
  public Short getNameType() {
    return nameType;
  }

  /**
   * @return the suffixTitleDescription
   */
  public String getSuffixTitleDescription() {
    return StringUtils.trimToEmpty(suffixTitleDescription);
  }

  /**
   * @return the clientId
   */
  @JsonProperty(value = "clientId")
  public String getClientId() {
    return StringUtils.trimToEmpty(clientId);
  }

  /**
   * @return the "thirdId"
   */
  @JsonProperty(value = "thirdId")
  public String getThirdId() {
    return StringUtils.trimToEmpty(thirdId);
  }

  /**
   * 
   * @param clientId the clientId
   */
  @JsonProperty(value = "clientId")
  public void setClientId(String clientId) {
    this.clientId = clientId;
  }

  /**
   * 
   * @param thirdId the "thirdId"
   */
  @JsonProperty(value = "thirdId")
  public void setThirdId(String thirdId) {
    this.thirdId = thirdId;
  }

  // ==================
  // IPersonAware:
  // ==================

  @JsonIgnore
  @Override
  @Transient
  public String getGender() {
    // Does not apply
    return null;
  }

  @JsonIgnore
  @Override
  @Transient
  public Date getBirthDate() {
    // Does not apply
    return null;
  }

  @JsonIgnore
  @Override
  @Transient
  public String getSsn() {
    // Does not apply
    return null;
  }

  @JsonIgnore
  @Override
  @Transient
  public String getNameSuffix() {
    return this.suffixTitleDescription;
  }

  @Override
  public String toString() {
    return "OtherClientName [id=" + clientId + ", firstName=" + firstName + ", lastName=" + lastName
        + ", middleName=" + middleName + ", namePrefixDescription=" + namePrefixDescription
        + ", nameType=" + nameType + ", suffixTitleDescription=" + suffixTitleDescription
        + ", thirdId=" + thirdId + "]";
  }

  @Override
  public Serializable getPrimaryKey() {
    return this.thirdId;
  }

}
