package gov.ca.cwds.data.persistence.ns;

import java.io.Serializable;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import gov.ca.cwds.data.ns.NsPersistentObject;

/**
 * {@link NsPersistentObject} representing a PersonEthnicity
 * 
 * @author CWDS API Team
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "person_ethnicity")
@AssociationOverrides({
    @AssociationOverride(name = "personEthnicityId.person",
        joinColumns = @JoinColumn(name = "person_id")),
    @AssociationOverride(name = "personEthnicityId.ethnicity",
        joinColumns = @JoinColumn(name = "ethnicity_id"))})
public class PersonEthnicity extends NsPersistentObject implements Serializable {

  @EmbeddedId
  private PersonEthnicityId personEthnicityId = new PersonEthnicityId();

  /**
   * Default constructor
   * 
   * Required for Hibernate
   */
  public PersonEthnicity() {
    super();
  }

  /**
   * @param person - The person
   * @param ethnicity - The Ethnicity
   */
  public PersonEthnicity(Person person, Ethnicity ethnicity) {
    super(null, null);
    personEthnicityId.setPerson(person);
    personEthnicityId.setEthnicity(ethnicity);
  }

  @Override
  public PersonEthnicityId getPrimaryKey() {
    return personEthnicityId;
  }

  @SuppressWarnings("unused")
  private void setPk(PersonEthnicityId pk) {
    this.personEthnicityId = pk;
  }

  /**
   * @param person - The person
   */
  public void setPerson(Person person) {
    getPrimaryKey().setPerson(person);
  }

  /**
   * @param ethnicity - The Ethnicity
   */
  public void setEthnicity(Ethnicity ethnicity) {
    getPrimaryKey().setEthnicity(ethnicity);
  }

  /**
   * @return the person
   */
  @JsonIgnore
  public Person getPerson() {
    return personEthnicityId.getPerson();
  }

  /**
   * @return the address
   */
  public Ethnicity getEthnicity() {
    return personEthnicityId.getEthnicity();
  }

  @Override
  public int hashCode() {
    final int PRIME = 31;
    int result = 1;
    result = PRIME * result + ((personEthnicityId == null) ? 0 : personEthnicityId.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    PersonEthnicity other = (PersonEthnicity) obj;
    if (personEthnicityId == null) {
      if (other.personEthnicityId != null)
        return false;
    } else if (!personEthnicityId.equals(other.personEthnicityId))
      return false;
    return true;
  }

}
