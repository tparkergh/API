package gov.ca.cwds.data.persistence.cms;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import gov.ca.cwds.rest.api.domain.DomainChef;

/**
 * {@link CmsPersistentObject} Class representing an Tickle.
 * 
 * @author CWDS API Team
 */
@Entity
@Table(name = "TICKLE_T")
@JsonPropertyOrder(alphabetic = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Tickle extends CmsPersistentObject {

  protected static final String DATE_FORMAT = "yyyy-MM-dd";

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "IDENTIFIER", length = CMS_ID_LEN)
  private String id;

  @Column(name = "AFTBY_CRID")
  private String affectedByCaseOrReferralId;

  @Column(name = "AFCTD_BYCD")
  private String affectedByCode;

  @Column(name = "AFTBY_OTID")
  private String affectedByOtherId;

  @Column(name = "AFCTD_THID")
  private String affectedByThirdId;

  @Type(type = "date")
  @Column(name = "DUE_DT")
  private Date dueDate;

  @Column(name = "NOTE_TXT")
  private String noteText;

  @Column(name = "TKL_MSGC")
  private Short tickleMessageType;

  /**
   * Default constructor
   * 
   * Required for Hibernate
   */
  public Tickle() {
    super();
  }

  /**
   * Constructor
   * 
   * @param id primary key
   * @param affectedByCaseOrReferralId affected case or referral id
   * @param affectedByCode affected by code
   * @param affectedByOtherId affected by other id
   * @param affectedByThirdId affected by third id
   * @param dueDate due date
   * @param noteText note text
   * @param tickleMessageType tickle message type
   */
  public Tickle(String id, String affectedByCaseOrReferralId, String affectedByCode,
      String affectedByOtherId, String affectedByThirdId, Date dueDate, String noteText,
      Short tickleMessageType) {
    super();
    this.id = id;
    this.affectedByCaseOrReferralId = affectedByCaseOrReferralId;
    this.affectedByCode = affectedByCode;
    this.affectedByOtherId = affectedByOtherId;
    this.affectedByThirdId = affectedByThirdId;
    this.dueDate = dueDate;
    this.noteText = noteText;
    this.tickleMessageType = tickleMessageType;
  }

  /**
   * Constructor using domain
   * 
   * @param id The id
   * @param domainTickle The domain object to construct this object from
   * @param lastUpdatedId The id of the last person to update this object
   */
  public Tickle(String id, gov.ca.cwds.rest.api.domain.cms.Tickle domainTickle,
      String lastUpdatedId) {
    super(lastUpdatedId);
    this.id = id;
    this.affectedByCaseOrReferralId = domainTickle.getAffectedByCaseOrReferralId();
    this.affectedByCode = domainTickle.getAffectedByCode();
    this.affectedByOtherId = domainTickle.getAffectedByOtherId();
    this.affectedByThirdId = domainTickle.getAffectedByThirdId();
    this.dueDate = DomainChef.uncookDateString(domainTickle.getDueDate());
    this.noteText = domainTickle.getNoteText();
    this.tickleMessageType = domainTickle.getTickleMessageType();
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
   * @return the affectedByCaseOrReferralId
   */
  public String getAffectedByCaseOrReferralId() {
    return affectedByCaseOrReferralId;
  }

  /**
   * @return the affectedByCode
   */
  public String getAffectedByCode() {
    return affectedByCode;
  }

  /**
   * @return the affectedByOtherId
   */
  public String getAffectedByOtherId() {
    return affectedByOtherId;
  }

  /**
   * @return the affectedByThirdId
   */
  public String getAffectedByThirdId() {
    return affectedByThirdId;
  }

  /**
   * @return the dueDate
   */
  public Date getDueDate() {
    return dueDate;
  }

  /**
   * @return the noteText
   */
  public String getNoteText() {
    return noteText;
  }

  /**
   * @return the tickleMessageType
   */
  public Short getTickleMessageType() {
    return tickleMessageType;
  }

}
