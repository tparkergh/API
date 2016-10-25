package gov.ca.cwds.rest.api.persistence.cms;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import gov.ca.cwds.rest.api.persistence.PersistentObject;

/**
 * {@link PersistentObject} representing a record in TSCNTRLT.
 * 
 * @author CWDS API Team
 */
@Entity
@Table(name = "TSCNTRLT")
public class DocumentControl extends CmsPersistentObject {

  @Id
  @Column(name = "DOC_HANDLE")
  private String id;

  @Type(type = "integer")
  @Column(name = "DOC_SEGS")
  private Short segmentCount;

  @Type(type = "integer")
  @Column(name = "DOC_LEN")
  private Long docLength;

  @Column(name = "DOC_AUTH")
  private String docAuth;

  @Column(name = "DOC_SERV")
  private String docServ;

  @Type(type = "date")
  @Column(name = "DOC_DATE")
  private Date docDate;

  @Type(type = "time")
  @Column(name = "DOC_TIME")
  private Date docTime;

  @Column(name = "DOC_NAME")
  private String docName;

  @Column(name = "CMPRS_PRG")
  private String compressionMethod;

  /**
   * Default constructor
   * 
   * Required for Hibernate
   */
  public DocumentControl() {
    super();
  }

  public DocumentControl(String id, Short segmentCount, Long docLength, String docAuth,
      String docServ, Date docDate, Date docTime, String docName, String compressionMethod) {
    super();
    this.id = id;
    this.docAuth = docAuth;
    this.docServ = docServ;
    this.docName = docName;
    this.segmentCount = segmentCount;
    this.docDate = docDate;
    this.docLength = docLength;
    this.docTime = docTime;
    this.compressionMethod = compressionMethod;
  }

  /*
   * (non-Javadoc)
   * 
   * @see gov.ca.cwds.rest.api.persistence.PersistentObject#getPrimaryKey()
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

  public Short getSegmentCount() {
    return segmentCount;
  }

  public void setSegmentCount(Short segmentCount) {
    this.segmentCount = segmentCount;
  }

  public Long getDocLength() {
    return docLength;
  }

  public void setDocLength(Long docLength) {
    this.docLength = docLength;
  }

  public String getDocAuth() {
    return docAuth;
  }

  public void setDocAuth(String docAuth) {
    this.docAuth = docAuth;
  }

  public String getDocServ() {
    return docServ;
  }

  public void setDocServ(String docServ) {
    this.docServ = docServ;
  }

  public Date getDocDate() {
    return docDate;
  }

  public void setDocDate(Date docDate) {
    this.docDate = docDate;
  }

  public Date getDocTime() {
    return docTime;
  }

  public void setDocTime(Date docTime) {
    this.docTime = docTime;
  }

  public String getDocName() {
    return docName;
  }

  public void setDocName(String docName) {
    this.docName = docName;
  }

  public String getCompressionMethod() {
    return compressionMethod;
  }

  public void setCompressionMethod(String compressionMethod) {
    this.compressionMethod = compressionMethod;
  }

  public void setId(String id) {
    this.id = id;
  }

}
