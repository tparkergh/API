package gov.ca.cwds.data.persistence.ns;

import gov.ca.cwds.data.ns.NsPersistentObject;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * {@link NsPersistentObject} representing an Address
 * 
 * @author CWDS API Team
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "hotline_contact_participant")
public class Participant extends NsPersistentObject {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE,
      generator = "seq_hotline_contact_participant_id")
  @SequenceGenerator(name = "seq_hotline_contact_participant_id",
      sequenceName = "seq_hotline_contact_participant_id", allocationSize = 50)
  @Column(name = "hotline_contact_participant_id")
  private Long hotlineContactParticipantId;

  @Column(name = "hotline_contact_id")
  private long screeningId;

  @Column(name = "person_id")
  private long personId;

  @Column(name = "hotline_contact_participant_type")
  private String hotelineContactParticipantType;

  @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  @JoinColumn(name = "hotline_contact_id", nullable = false, insertable = false, updatable = false)
  private Screening screening;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "person_id", insertable = false, updatable = false)
  private Person person;



  /**
   * @param person - personId
   * @param screening - screeningId
   */
  public Participant(long person, long screening) {
    this.personId = person;
    this.screeningId = screening;
  }


  /**
   * @param participant The domain object to construct this object from
   * @param lastUpdatedId the id of the last person to update this object
   * @param createUserId the id of the person created the record
   */
  public Participant(gov.ca.cwds.rest.api.domain.Participant participant, String lastUpdatedId,
      String createUserId) {
    super(lastUpdatedId, createUserId);
    this.personId = participant.getPersonId();
    this.screeningId = participant.getScreeningId();
  }

  /**
   * Default constructor
   * 
   * Required for Hibernate
   */
  public Participant() {
    super();
  }


  @Override
  public Long getPrimaryKey() {
    return getId();
  }

  /**
   * @return the id
   */
  public Long getId() {
    return hotlineContactParticipantId;
  }

  /**
   * @return the personId
   */
  public long getPersonId() {
    return personId;
  }

  /**
   * @return the screeningId
   */
  public long getHotlineContactId() {
    return screeningId;
  }

  /**
   * @return the hotelineContactParticipantType
   */
  public String getHotelineContactParticipantType() {
    return hotelineContactParticipantType;
  }

  /**
   * @return the person
   */
  public Person getPerson() {
    return person;
  }


}
