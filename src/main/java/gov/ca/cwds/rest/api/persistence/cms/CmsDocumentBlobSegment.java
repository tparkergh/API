package gov.ca.cwds.rest.api.persistence.cms;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.annotations.ColumnTransformer;

import gov.ca.cwds.rest.api.persistence.PersistentObject;
import gov.ca.cwds.rest.api.persistence.TypedPersistentObject;

/**
 * {@link PersistentObject} represents a record in TSBLOBT.
 * 
 * <p>
 * Note that this entity class does not extend {@link CmsPersistentObject}, because table TSBLOBT
 * lacks the last update timestamp and last update user id fields.
 * </p>
 * 
 * @author CWDS API Team
 */
@Entity
@Table(name = "TSBLOBT")
public class CmsDocumentBlobSegment implements TypedPersistentObject<VarargPrimaryKey> {

  private static final long serialVersionUID = -6101861394294752291L;

  @Id
  @Column(name = "DOC_HANDLE", length = 30)
  @NotNull
  @Size(min = 30, max = 30)
  @Pattern(regexp = "\\d{16}[*][A-Z0-9]+\\s*\\d{5}", message = "invalid DOC_HANDLE")
  private String docHandle;

  @Id
  @Column(name = "DOC_SEGSEQ", length = 4)
  @NotNull
  @Size(min = 4, max = 4)
  @Pattern(regexp = "\\d{4}")
  private String segmentSequence;

  @NotNull
  @Size(min = 1, max = 4000)
  @ColumnTransformer(read = "blob(DOC_BLOB)")
  private String docBlob;

  /**
   * Default constructor
   * 
   * Required for Hibernate.
   */
  public CmsDocumentBlobSegment() {
    super();
  }

  /**
   * Convenience constructor.
   * 
   * @param docHandle document identifier
   * @param segmentSequence blob segment sequence
   * @param docBlob hexadecimal blob segment data
   */
  public CmsDocumentBlobSegment(String docHandle, String segmentSequence, String docBlob) {
    super();
    this.docHandle = docHandle;
    this.segmentSequence = segmentSequence;
    this.docBlob = docBlob;
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.api.persistence.PersistentObject#getPrimaryKey()
   */
  @Override
  public VarargPrimaryKey getPrimaryKey() {
    return new VarargPrimaryKey(this.getDocHandle(), this.getSegmentSequence());
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.lang.Object#hashCode()
   */
  @Override
  public final int hashCode() {
    final int prime = 31;
    int result = 1;

    result = prime * result + ((docHandle == null) ? 0 : docHandle.hashCode());
    result = prime * result + ((segmentSequence == null) ? 0 : segmentSequence.hashCode());

    // 1) NOT part of unique key, 2) potentially large and waste of processing to compute.
    // 3) if you got this far, well ... ;)
    result = prime * result + ((docBlob == null) ? 0 : docBlob.hashCode());

    return result;
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public final boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (!(obj instanceof CmsDocumentBlobSegment)) {
      return false;
    }
    CmsDocumentBlobSegment other = (CmsDocumentBlobSegment) obj;

    if (docHandle == null) {
      if (other.docHandle != null) {
        return false;
      }
    } else if (!docHandle.equals(other.docHandle)) {
      return false;
    }
    if (segmentSequence == null) {
      if (other.segmentSequence != null) {
        return false;
      }
    } else if (!segmentSequence.equals(other.segmentSequence)) {
      return false;
    }

    // 1) NOT part of unique key, 2) potentially large and waste of processing to compute.
    // 3) if you got this far, well ... ;)
    if (docBlob == null) {
      if (other.docBlob != null) {
        return false;
      }
    } else if (!docBlob.equals(other.docBlob)) {
      return false;
    }

    return true;
  }

  // ==================
  // ACCESSORS:
  // ==================

  /**
   * Blob segment sequence is a 4-digit, zero left-padded string, starting with "0001".
   * 
   * @return segment number
   */
  public String getSegmentSequence() {
    return segmentSequence;
  }

  /**
   * Sets the the segment's sequence number. See {@link #getSegmentSequence()} for details.
   * 
   * @param segmentSequence segment's sequence number
   * @see #getSegmentSequence()
   */
  public void setSegmentSequence(String segmentSequence) {
    this.segmentSequence = segmentSequence;
  }

  /**
   * Hex string of this segment's binary data.
   * 
   * @return hex string of segment data
   */
  public String getDocBlob() {
    return docBlob;
  }

  /**
   * Sets the hexadecimal binary data for this segment, max size 4k.
   * 
   * @param docBlob hex of binary, compressed data for this segment
   */
  public void setDocBlob(String docBlob) {
    this.docBlob = docBlob;
  }

  /**
   * Gets the document handle.
   * 
   * @return the document's id, doc_handle.
   */
  public String getDocHandle() {
    return docHandle;
  }

  /**
   * Sets the document's id, the document handle.
   * 
   * @param docHandle 30-character identifier.
   */
  public void setDocHandle(String docHandle) {
    this.docHandle = docHandle;
  }

}
