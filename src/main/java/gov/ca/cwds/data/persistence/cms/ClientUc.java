package gov.ca.cwds.data.persistence.cms;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import gov.ca.cwds.data.ns.NsPersistentObject;

/**
 * {@link NsPersistentObject} representing a Client Uppercase
 * 
 * @author CWDS API Team
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "CLINT_UC")
@JsonPropertyOrder(alphabetic = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ClientUc extends CmsPersistentObject {

  @Id
  @Column(name = "PKTBL_ID", length = CMS_ID_LEN)
  private String pktableId;

  @Column(name = "SRCTBL_CD")
  private String sourceTableCode;

  @Column(name = "COM_FST_NM")
  private String commonFirstName;

  @Column(name = "COM_LST_NM")
  private String commonLastName;

  @Column(name = "COM_MID_NM")
  private String commonMiddleName;

  /**
   * Default constructor
   * 
   * Required for Hibernate
   */
  public ClientUc() {
    super();
  }

  /**
   * @param pktableId The primaryKeyTableId
   * @param sourceTableCode The sourceTableCode
   * @param commonFirstName The commonFirstName
   * @param commonLastName The commonLastName
   * @param commonMiddleName The commonMiddleName
   */
  public ClientUc(String pktableId, String sourceTableCode, String commonFirstName,
      String commonLastName, String commonMiddleName) {
    super();
    this.pktableId = pktableId;
    this.sourceTableCode = sourceTableCode;
    this.commonFirstName = commonFirstName;
    this.commonLastName = commonLastName;
    this.commonMiddleName = commonMiddleName;
  }

  /**
   * @param clientUc -- client uppercase
   * @param lastUpdateId -- last updated id
   */
  public ClientUc(gov.ca.cwds.rest.api.domain.cms.ClientUc clientUc, String lastUpdateId) {
    super(lastUpdateId);

    this.pktableId = clientUc.getPktableId();
    this.sourceTableCode = clientUc.getSourceTableCode();
    this.commonFirstName = clientUc.getCommonFirstName();
    this.commonLastName = clientUc.getCommonLastName();
    this.commonMiddleName = clientUc.getCommonMiddleName();
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.data.persistence.PersistentObject#getPrimaryKey()
   */
  @Override
  public String getPrimaryKey() {
    return getPktableId();
  }

  /**
   * @return the pktableId
   */
  public String getPktableId() {
    return pktableId;
  }

  /**
   * @return the sourceTableCode
   */
  public String getSourceTableCode() {
    return sourceTableCode;
  }

  /**
   * @return the commonFirstName
   */
  public String getCommonFirstName() {
    return commonFirstName;
  }

  /**
   * @return the commonLastName
   */
  public String getCommonLastName() {
    return commonLastName;
  }

  /**
   * @return the commonMiddleName
   */
  public String getCommonMiddleName() {
    return commonMiddleName;
  }



  /**
   * @param pktableId the pktableId to set
   */
  public void setPktableId(String pktableId) {
    this.pktableId = pktableId;
  }

  /**
   * @param sourceTableCode the sourceTableCode to set
   */
  public void setSourceTableCode(String sourceTableCode) {
    this.sourceTableCode = sourceTableCode;
  }

  /**
   * @param commonFirstName the commonFirstName to set
   */
  public void setCommonFirstName(String commonFirstName) {
    this.commonFirstName = commonFirstName;
  }

  /**
   * @param commonLastName the commonLastName to set
   */
  public void setCommonLastName(String commonLastName) {
    this.commonLastName = commonLastName;
  }

  /**
   * @param commonMiddleName the commonMiddleName to set
   */
  public void setCommonMiddleName(String commonMiddleName) {
    this.commonMiddleName = commonMiddleName;
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
