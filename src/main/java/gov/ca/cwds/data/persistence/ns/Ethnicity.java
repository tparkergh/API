package gov.ca.cwds.data.persistence.ns;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.NamedQuery;

import gov.ca.cwds.data.ns.NsPersistentObject;

/**
 * {@link NsPersistentObject} representing an Ethnicity
 *
 * @author CWDS API Team
 */
@NamedQuery(name = "gov.ca.cwds.rest.api.persistence.ns.Ethnicity.findAll",
    query = "FROM Ethnicity")
@NamedQuery(name = "gov.ca.cwds.rest.api.persistence.ns.Ethnicity.findAllUpdatedAfter",
    query = "FROM Ethnicity WHERE lastUpdatedTime > :after")

@SuppressWarnings("serial")
@Entity
@Table(name = "ethnicity")
public class Ethnicity extends NsPersistentObject {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_ethnicity")
  @SequenceGenerator(name = "seq_ethnicity", sequenceName = "seq_ethnicity", allocationSize = 50)
  @Column(name = "ethnicity_id")
  private Long id;

  @Column(name = "ethnicity_type_id")
  private String ethnicityType;

  @Column(name = "sub_ethnicity_id")
  private String subEthnicity;

  @OneToMany(fetch = FetchType.EAGER, mappedBy = "personEthnicityId.ethnicity")
  private Set<PersonEthnicity> personEthnicity = new HashSet<>();

  @SuppressWarnings("javadoc")
  public Ethnicity() {
    super();
  }

  /**
   * Constructor
   * 
   * @param id - unique id
   * @param ethnicityType - ethinicity
   * @param subEthnicity - subethinicity
   */
  public Ethnicity(Long id, String ethnicityType, String subEthnicity) {
    super();
    this.id = id;
    this.ethnicityType = ethnicityType;
    this.subEthnicity = subEthnicity;
  }


  /**
   * @param ethnicity The domain object to construct this object from
   * @param lastUpdatedId the id of the last person to update this object
   * @param createUserId the id of the person created the record
   */
  public Ethnicity(gov.ca.cwds.rest.api.domain.Ethnicity ethnicity, String lastUpdatedId,
      String createUserId) {
    super(lastUpdatedId, createUserId);
    this.ethnicityType = ethnicity.getEthnicityType();
    this.subEthnicity = ethnicity.getSubEthnicity();
  }

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
   * @return the ethnicityType
   */
  public String getEthnicityType() {
    return ethnicityType;
  }

  /**
   * @return the subEthnicity
   */
  public String getSubEthnicity() {
    return subEthnicity;
  }

  /**
   * @return the personEthnicity
   */
  public Set<PersonEthnicity> getPersonEthnicity() {
    return personEthnicity;
  }

}
