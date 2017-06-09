package gov.ca.cwds.data.persistence.cms;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * {@link CmsPersistentObject} Class representing an DrmsDocument.
 * 
 * @author CWDS API Team
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "DRMSDOCT")
@JsonPropertyOrder(alphabetic = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class DrmsDocument extends CmsPersistentObject {

  @Id
  @Column(name = "IDENTIFIER", length = CMS_ID_LEN)
  private String id;

  @Type(type = "timestamp")
  @Column(name = "CREATN_TS")
  private Date creationTimeStamp;

  @Column(name = "FKDOCTMPLT")
  private String drmsDocumentTemplateId;

  @Column(name = "FKFPSTFPRT")
  private String fingerprintStaffPerson;

  @Column(name = "FKSTFPERST")
  private String staffPersonId;

  @Column(name = "DOCHNDL_NM")
  private String handleName;

  /**
   * 
   */
  public DrmsDocument() {
    super();
  }

  /**
   * @param id
   * @param creationTimeStamp
   * @param drmsDocumentTemplateId
   * @param fingerprintStaffPerson
   * @param staffPersonId
   * @param handleName
   */
  public DrmsDocument(String id, Date creationTimeStamp, String drmsDocumentTemplateId,
      String fingerprintStaffPerson, String staffPersonId, String handleName) {
    super();
    this.id = id;
    this.creationTimeStamp = creationTimeStamp;
    this.drmsDocumentTemplateId = drmsDocumentTemplateId;
    this.fingerprintStaffPerson = fingerprintStaffPerson;
    this.staffPersonId = staffPersonId;
    this.handleName = handleName;
  }

  /**
   * Constructor using domain
   * 
   * @param id
   * @param persisedDrmsDocument
   * @param lastUpdatedId
   */
  public DrmsDocument(String id, gov.ca.cwds.rest.api.domain.cms.DrmsDocument persisedDrmsDocument,
      String lastUpdatedId) {
    super(lastUpdatedId);
    this.id = id;
    this.creationTimeStamp = persisedDrmsDocument.getCreationTimeStamp();
    this.drmsDocumentTemplateId = persisedDrmsDocument.getDrmsDocumentTemplateId();
    this.fingerprintStaffPerson = persisedDrmsDocument.getFingerprintStaffPerson();
    this.staffPersonId = persisedDrmsDocument.getStaffPersonId();
    this.handleName = persisedDrmsDocument.getHandleName();
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
   * @return the id
   */
  public String getId() {
    return id;
  }

  /**
   * @return the creationTimeStamp
   */
  public Date getCreationTimeStamp() {
    return creationTimeStamp;
  }

  /**
   * @return the drmsDocumentTemplateId
   */
  public String getDrmsDocumentTemplateId() {
    return drmsDocumentTemplateId;
  }

  /**
   * @return the fingerprintStaffPerson
   */
  public String getFingerprintStaffPerson() {
    return fingerprintStaffPerson;
  }

  /**
   * @return the staffPersonId
   */
  public String getStaffPersonId() {
    return staffPersonId;
  }

  /**
   * @return the handleName
   */
  public String getHandleName() {
    return handleName;
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
