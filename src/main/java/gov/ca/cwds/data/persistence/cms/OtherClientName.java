package gov.ca.cwds.data.persistence.cms;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;
import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.data.persistence.EmbeddableCompositeKey2;
import gov.ca.cwds.data.persistence.PersistentObject;


/**
 * {@link PersistentObject} representing an OtherClientName
 * 
 * @author CWDS API Team
 */
@SuppressWarnings("serial")
@NamedQueries({
    @NamedQuery(name = "gov.ca.cwds.data.persistence.cms.OtherClientName.findAll",
        query = "FROM OtherClientName"),
    @NamedQuery(name = "gov.ca.cwds.data.persistence.cms.OtherClientName.findAllUpdatedAfter",
        query = "FROM OtherClientName WHERE lastUpdatedTime > :after")})
@Entity
@Table(name = "OCL_NM_T")
public class OtherClientName extends CmsPersistentObject {

  @AttributeOverrides({
      @AttributeOverride(name = "id1", column = @Column(name = "FKCLIENT_T", length = CMS_ID_LEN)),
      @AttributeOverride(name = "id2", column = @Column(name = "THIRD_ID", length = CMS_ID_LEN))})
  @EmbeddedId
  private EmbeddableCompositeKey2 id;

  @Column(name = "FIRST_NM")
  private String firstName;

  @Column(name = "LAST_NM")
  private String lastName;

  @Column(name = "MIDDLE_NM")
  private String middleName;

  @Column(name = "NMPRFX_DSC")
  private String namePrefixDescription;

  @Type(type = "short")
  @Column(name = "NAME_TPC")
  private Short nameType;

  @Column(name = "SUFX_TLDSC")
  private String suffixTitleDescription;

  /**
   * Default constructor
   * 
   * Required for Hibernate
   */
  public OtherClientName() {
    super();
    this.id = new EmbeddableCompositeKey2();
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
   * @param thirdId "third" id, generated value (staff + timestamp) for unique identification
   */
  public OtherClientName(String clientId, String firstName, String lastName, String middleName,
      String namePrefixDescription, Short nameType, String suffixTitleDescription, String thirdId) {
    super();
    this.id = new EmbeddableCompositeKey2(clientId, thirdId);

    this.firstName = firstName;
    this.lastName = lastName;
    this.middleName = middleName;
    this.namePrefixDescription = namePrefixDescription;
    this.nameType = nameType;
    this.suffixTitleDescription = suffixTitleDescription;
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.data.persistence.PersistentObject#getPrimaryKey()
   */
  @Override
  public EmbeddableCompositeKey2 getPrimaryKey() {
    return this.id;
  }

  /**
   * @return the firstName
   */
  public String getFirstName() {
    return StringUtils.trimToEmpty(firstName);
  }

  /**
   * @return the lastName
   */
  public String getLastName() {
    return StringUtils.trimToEmpty(lastName);
  }

  /**
   * @return the middleName
   */
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
   * Delegate accessor: get the client id through the composite primary key.
   * 
   * @return the clientId
   */
  @JsonProperty(value = "clientId")
  public String getClientId() {
    return StringUtils.trimToEmpty(id.getId1());
  }

  /**
   * Delegate accessor: get the third id through the composite primary key.
   * 
   * @return the "thirdId"
   */
  @JsonProperty(value = "thirdId")
  public String getThirdId() {
    return StringUtils.trimToEmpty(id.getId2());
  }

  /**
   * Delegate accessor: set the client id through the composite primary key.
   * 
   * @param clientId the clientId
   */
  @JsonProperty(value = "clientId")
  public void setClientId(String clientId) {
    id.setId1(clientId);
  }

  /**
   * Delegate accessor: set the third id through the composite primary key.
   * 
   * @param thirdId the "thirdId"
   */
  @JsonProperty(value = "thirdId")
  public void setThirdId(String thirdId) {
    id.setId2(thirdId);
  }

  @Override
  public final int hashCode() {
    final int prime = 31;
    int ret = 1;
    ret = prime * ret + ((id == null || id.getId1() == null) ? 0 : id.getId1().hashCode());
    ret = prime * ret + ((id == null || id.getId2() == null) ? 0 : id.getId2().hashCode());
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

    if (super.getLastUpdatedId() == null) {
      if (o.getLastUpdatedId() != null) {
        return false;
      }
    } else if (!super.getLastUpdatedId().equals(o.getLastUpdatedId())) {
      return false;
    }
    if (super.getLastUpdatedTime() == null) {
      if (o.getLastUpdatedTime() != null) {
        return false;
      }
    } else if (!super.getLastUpdatedTime().equals(o.getLastUpdatedTime())) {
      return false;
    }
    return true;
  }

}
