package gov.ca.cwds.data.persistence.ns;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import gov.ca.cwds.data.ns.NsPersistentObject;

/**
 * {@link NsPersistentObject} representing a Person.
 * 
 * @author CWDS API Team
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "hotline_contact")
public class Screening extends NsPersistentObject {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_hotline_contact_id")
  @SequenceGenerator(name = "seq_hotline_contact_id", sequenceName = "seq_hotline_contact_id",
      allocationSize = 50)
  @Column(name = "hotline_contact_id")
  private Long id;

  @Column(name = "hotline_contact_reference")
  private String reference;

  @Column(name = "screening_end_datetime")
  @Type(type = "date")
  private Date endedAt;

  @Column(name = "hotline_contact_county")
  private String incidentCounty;

  @Column(name = "incident_datetime")
  @Type(type = "date")
  private Date incidentDate;

  @Column(name = "location_type")
  private String locationType;

  @Column(name = "hotline_communication_method")
  private String communicationMethod;

  @Column(name = "hotline_contact_name")
  private String name;

  @Column(name = "response_time")
  private String responseTime;

  @Column(name = "screening_result")
  private String screeningDecision;

  @Column(name = "screening_start_datetime")
  @Type(type = "date")
  private Date startedAt;

  @Column(name = "screening_report_narrative")
  private String narrative;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "contact_address_id")
  private Address contactAddress;

  @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "screening")
  private Set<Participant> participants = new HashSet<>(0);

  /**
   * Default constructor
   * 
   * Required for Hibernate
   */
  public Screening() {
    super();
  }

  /**
   * Constructor
   * 
   * @param reference The reference
   */
  public Screening(String reference) {
    this.reference = reference;
  }

  /**
   * Constructor
   * 
   * @param reference The reference
   * @param endedAt The endedAt date
   * @param incidentCounty The incident county
   * @param incidentDate The incident date
   * @param locationType The location type
   * @param communicationMethod The communication method
   * @param name The name of the screening
   * @param responseTime The response time
   * @param screeningDecision The screening decision
   * @param startedAt The started at date
   * @param narrative The narrative
   * @param contactAddress The contact address
   * @param participants The list of participants
   */
  public Screening(String reference, Date endedAt, String incidentCounty, Date incidentDate,
      String locationType, String communicationMethod, String name, String responseTime,
      String screeningDecision, Date startedAt, String narrative, Address contactAddress,
      Set<Participant> participants) {
    super();
    this.reference = reference;
    this.endedAt = endedAt;
    this.incidentCounty = incidentCounty;
    this.incidentDate = incidentDate;
    this.locationType = locationType;
    this.communicationMethod = communicationMethod;
    this.name = name;
    this.responseTime = responseTime;
    this.screeningDecision = screeningDecision;
    this.startedAt = startedAt;
    this.narrative = narrative;
    this.contactAddress = contactAddress;

    if (participants != null && !participants.isEmpty()) {
      this.participants.addAll(participants);
    }
  }

  /**
   * Constructor
   * 
   * @param id The id
   * @param screening The screening
   * @param address The address
   * @param participants The set of participants
   * @param lastUpdatedId the id of the last person to update this object
   * @param createUserId the id of the person created the record
   */
  // public Screening(Long id, gov.ca.cwds.rest.api.domain.Screening screening, Address address,
  // Set<Participant> participants, String lastUpdatedId, String createUserId) {
  // super(lastUpdatedId, createUserId);
  //
  // this.id = id;
  // this.reference = screening.getReference();
  // this.endedAt = DomainChef.uncookDateString(screening.getEndedAt());
  // this.incidentCounty = screening.getIncidentCounty();
  // this.incidentDate = DomainChef.uncookDateString(screening.getIncidentDate());
  // this.locationType = screening.getLocationType();
  // this.communicationMethod = screening.getCommunicationMethod();
  // this.name = screening.getName();
  // this.responseTime = screening.getResponseTime();
  // this.screeningDecision = screening.getScreeningDecision();
  // this.startedAt = DomainChef.uncookDateString(screening.getStartedAt());
  // this.narrative = screening.getNarrative();
  // this.contactAddress = address;
  // if (participants != null) {
  // this.participants.addAll(participants);
  // }
  // }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.data.persistence.PersistentObject#getPrimaryKey()
   */
  @Override
  public Long getPrimaryKey() {
    return getId();
  }

  /**
   * @return the id
   */
  public Long getId() {
    return id;
  }

  /**
   * @return the reference
   */
  public String getReference() {
    return reference;
  }

  /**
   * @return the endedAt
   */
  public Date getEndedAt() {
    return endedAt;
  }

  /**
   * @return the incidentCounty
   */
  public String getIncidentCounty() {
    return incidentCounty;
  }

  /**
   * @return the incidentDate
   */
  public Date getIncidentDate() {
    return incidentDate;
  }

  /**
   * @return the locationType
   */
  public String getLocationType() {
    return locationType;
  }

  /**
   * @return the communicationMethod
   */
  public String getCommunicationMethod() {
    return communicationMethod;
  }

  /**
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * @return the responseTime
   */
  public String getResponseTime() {
    return responseTime;
  }

  /**
   * @return the screeningDecision
   */
  public String getScreeningDecision() {
    return screeningDecision;
  }

  /**
   * @return the startedAt
   */
  public Date getStartedAt() {
    return startedAt;
  }

  /**
   * @return the narrative
   */
  public String getNarrative() {
    return narrative;
  }

  /**
   * @return the contactAddress
   */
  public Address getContactAddress() {
    return contactAddress;
  }

  /**
   * @return the participants
   */
  public Set<Participant> getParticipants() {
    return participants;
  }

}
