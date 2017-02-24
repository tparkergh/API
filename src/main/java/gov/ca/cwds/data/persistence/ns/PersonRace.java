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
 * @author CWS-NS2
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "person_race")
@AssociationOverrides({
    @AssociationOverride(name = "personRaceId.person",
        joinColumns = @JoinColumn(name = "person_id")),
    @AssociationOverride(name = "personRaceId.race", joinColumns = @JoinColumn(name = "race_id"))})
public class PersonRace extends NsPersistentObject implements Serializable {

  @EmbeddedId
  private PersonRaceId personRaceId = new PersonRaceId();

  /**
   * Default constructor
   * 
   * Required for Hibernate
   */
  public PersonRace() {
    super();
  }

  /**
   * @param person parent person
   * @param race parent race
   */
  public PersonRace(Person person, Race race) {
    super(null, null);
    personRaceId.setPerson(person);
    personRaceId.setRace(race);
  }

  @Override
  public PersonRaceId getPrimaryKey() {
    return personRaceId;
  }

  @SuppressWarnings("unused")
  private void setPk(PersonRaceId pk) {
    this.personRaceId = pk;
  }

  /**
   * @param person - The person
   */
  public void setPerson(Person person) {
    getPrimaryKey().setPerson(person);
  }

  /**
   * @param race - The race
   */
  public void setRace(Race race) {
    getPrimaryKey().setRace(race);
  }

  /**
   * @return the person
   */
  @JsonIgnore
  public Person getPerson() {
    return personRaceId.getPerson();
  }

  /**
   * @return the race
   */
  public Race getRace() {
    return personRaceId.getRace();
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((personRaceId == null) ? 0 : personRaceId.hashCode());
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
    PersonRace other = (PersonRace) obj;
    if (personRaceId == null) {
      if (other.personRaceId != null)
        return false;
    } else if (!personRaceId.equals(other.personRaceId))
      return false;
    return true;
  }

}
