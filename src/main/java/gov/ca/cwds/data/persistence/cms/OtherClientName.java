package gov.ca.cwds.data.persistence.cms;

import java.beans.Transient;
import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.hibernate.annotations.NamedNativeQueries;
import org.hibernate.annotations.NamedNativeQuery;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;
import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import gov.ca.cwds.data.CmsSystemCodeDeserializer;
import gov.ca.cwds.data.SystemCodeSerializer;
import gov.ca.cwds.data.persistence.PersistentObject;
import gov.ca.cwds.data.std.ApiPersonAware;


/**
 * {@link PersistentObject} representing an OtherClientName.
 * 
 * @author CWDS API Team
 */
@SuppressWarnings("serial")
@NamedQueries({@NamedQuery(name = "gov.ca.cwds.data.persistence.cms.OtherClientName.findAll",
    query = "FROM OtherClientName WHERE clientId IN (SELECT id FROM Client WHERE sensitivityIndicator = 'N' AND soc158SealedClientIndicator = 'N')"),})
@NamedNativeQueries({
    @NamedNativeQuery(name = "gov.ca.cwds.data.persistence.cms.OtherClientName.findAllUpdatedAfter",
        query = "select x.THIRD_ID, x.FIRST_NM, x.LAST_NM, x.MIDDLE_NM, x.NMPRFX_DSC, "
            + "x.NAME_TPC, x.SUFX_TLDSC, x.LST_UPD_ID, x.LST_UPD_TS, x.FKCLIENT_T "
            + "from {h-schema}OCL_NM_T x WHERE x.IBMSNAP_LOGMARKER >= :after for read only",
        resultClass = OtherClientName.class),
    @NamedNativeQuery(
        name = "gov.ca.cwds.data.persistence.cms.OtherClientName.findPartitionedBuckets",
        query = "select z.THIRD_ID, z.FIRST_NM, z.LAST_NM, z.MIDDLE_NM, z.NMPRFX_DSC, "
            + "z.NAME_TPC, z.SUFX_TLDSC, z.LST_UPD_ID, z.LST_UPD_TS, z.FKCLIENT_T "
            + "from ( select mod(y.rn, CAST(:total_buckets AS INTEGER)) + 1 as bucket, y.* "
            + "from ( select row_number() over (order by 1) as rn, x.* "
            + "from ( select c.* from {h-schema}OCL_NM_T c "
            + "WHERE c.FKCLIENT_T >= :min_id AND c.FKCLIENT_T < :max_id AND THIRD_ID < '9999999999' "
            + ") x ) y ) z where z.bucket = :bucket_num for read only",
        resultClass = OtherClientName.class)})
@Entity
@Table(name = "OCL_NM_T")
@JsonPropertyOrder(alphabetic = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class OtherClientName extends CmsPersistentObject implements ApiPersonAware {

  @Column(name = "FIRST_NM")
  private String firstName;

  // TODO: correct unit test and enable the composite key on FKCLIENT_T and THIRD_ID.
  // @Id
  @Column(name = "FKCLIENT_T", length = CMS_ID_LEN)
  private String clientId;

  @Column(name = "LAST_NM")
  private String lastName;

  @Column(name = "MIDDLE_NM")
  private String middleName;

  @Column(name = "NMPRFX_DSC")
  private String namePrefixDescription;

  @SystemCodeSerializer(logical = true, description = true)
  @JsonDeserialize(using = CmsSystemCodeDeserializer.class)
  @Type(type = "short")
  @Column(name = "NAME_TPC")
  private Short nameType;

  @Column(name = "SUFX_TLDSC")
  private String suffixTitleDescription;

  @Id
  @Column(name = "THIRD_ID", length = CMS_ID_LEN)
  private String thirdId;

  /**
   * Default constructor
   * 
   * Required for Hibernate
   */
  public OtherClientName() {
    super();
  }

  /**
   * Construct from String inputs.
   * 
   * @param clientId the client id
   * @param firstName first name
   * @param lastName last name
   * @param middleName middle name
   * @param namePrefixDescription name prefix description, if any
   * @param nameType name type
   * @param suffixTitleDescription suffix title description, if any
   * @param thirdId "third" id, generated value (time stamp + staffId) for unique identification
   */
  public OtherClientName(String clientId, String firstName, String lastName, String middleName,
      String namePrefixDescription, Short nameType, String suffixTitleDescription, String thirdId) {
    super();

    this.firstName = firstName;
    this.clientId = clientId;
    this.lastName = lastName;
    this.middleName = middleName;
    this.namePrefixDescription = namePrefixDescription;
    this.nameType = nameType;
    this.suffixTitleDescription = suffixTitleDescription;
    this.thirdId = thirdId;
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.data.persistence.PersistentObject#getPrimaryKey()
   */
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

  @Override
  public final int hashCode() {
    final int prime = 31;
    int ret = 1;
    ret = prime * ret + ((thirdId == null) ? 0 : thirdId.hashCode());
    ret = prime * ret + ((clientId == null) ? 0 : clientId.hashCode());
    ret = prime * ret + ((firstName == null) ? 0 : firstName.hashCode());
    ret = prime * ret + ((lastName == null) ? 0 : lastName.hashCode());
    ret = prime * ret + ((middleName == null) ? 0 : middleName.hashCode());
    ret = prime * ret + ((namePrefixDescription == null) ? 0 : namePrefixDescription.hashCode());
    ret = prime * ret + ((nameType == null) ? 0 : nameType.hashCode());
    ret = prime * ret + ((suffixTitleDescription == null) ? 0 : suffixTitleDescription.hashCode());
    ret = prime * ret
        + ((super.getLastUpdatedId() == null) ? 0 : super.getLastUpdatedId().hashCode());
    ret = prime * ret
        + ((super.getLastUpdatedTime() == null) ? 0 : super.getLastUpdatedTime().hashCode());

    return ret;
  }

  @Override
  public final boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (!(obj instanceof OtherClientName)) {
      return false;
    }
    OtherClientName o = (OtherClientName) obj;

    // Reduce cognitive complexity -- at the expense of slightly slower performance.
    if (!EqualsBuilder.reflectionEquals(this, o, false))
      return false;

    return true;
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
