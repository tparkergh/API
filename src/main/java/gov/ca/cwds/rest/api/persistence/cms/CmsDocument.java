package gov.ca.cwds.rest.api.persistence.cms;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import gov.ca.cwds.rest.api.domain.DomainObject;
import gov.ca.cwds.rest.api.persistence.PersistentObject;

/**
 * {@link PersistentObject} represents a record in TSCNTRLT.
 * 
 * @author CWDS API Team
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "TSCNTRLT")
public class CmsDocument extends CmsPersistentObject {

  @Id
  @Column(name = "DOC_HANDLE", length = 30)
  private String id;

  @Type(type = "short")
  @Column(name = "DOC_SEGS")
  private Short segmentCount;

  @Type(type = "long")
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

  @OneToMany(fetch = FetchType.EAGER)
  @JoinColumn(name = "DOC_HANDLE", nullable = false)
  @OrderBy("DOC_HANDLE, DOC_SEGSEQ")
  private Set<CmsDocumentBlobSegment> blobSegments = new LinkedHashSet<>();

  /**
   * Default constructor
   * 
   * Required for Hibernate.
   */
  public CmsDocument() {
    super();
  }

  /**
   * Convenience constructor.
   * 
   * @param id unique document handle
   * @param segmentCount number of blob segments
   * @param docLength total bytes in all blob segments
   * @param docAuth document "auth"
   * @param docServ document "server"
   * @param docDate creation date
   * @param docTime creation time
   * @param docName document name (e.g., "word.doc")
   * @param compressionMethod type of compression, 01 (LZW) or 02 (PK)
   */
  public CmsDocument(String id, Short segmentCount, Long docLength, String docAuth, String docServ,
      Date docDate, Date docTime, String docName, String compressionMethod) {
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

  /**
   * Pseudo copy constructor. Build a persistence document from a domain document.
   * 
   * <p>
   * This constructor doesn't populate blob segments directly from a base64-encoded, decompressed
   * document because that translation requires compression, which doesn't belong here.
   * </p>
   * 
   * @param cmsDocument domain (JSON) document object
   */
  public CmsDocument(gov.ca.cwds.rest.api.domain.cms.CmsDocument cmsDocument) {
    super();
    this.id = cmsDocument.getId();
    this.docAuth = cmsDocument.getDocAuth();
    this.docServ = cmsDocument.getDocServ();
    this.docName = cmsDocument.getDocName();
    this.segmentCount = cmsDocument.getSegmentCount();
    this.docDate = DomainObject.uncookDateString(cmsDocument.getDocDate());
    this.docLength = cmsDocument.getDocLength();
    this.docTime = DomainObject.uncookTimeString(cmsDocument.getDocTime());
    this.compressionMethod = cmsDocument.getCompressionMethod();

  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.api.persistence.PersistentObject#getPrimaryKey()
   */
  @Override
  public Serializable getPrimaryKey() {
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

  public Set<CmsDocumentBlobSegment> getBlobSegments() {
    return blobSegments;
  }

  public void addBlobSegment(CmsDocumentBlobSegment blobSegment) {
    this.blobSegments.add(blobSegment);
  }

  public void setBlobSegments(Set<CmsDocumentBlobSegment> blobSegments) {
    this.blobSegments = blobSegments;
  }

}
