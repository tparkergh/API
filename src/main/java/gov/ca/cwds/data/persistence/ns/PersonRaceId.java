package gov.ca.cwds.data.persistence.ns;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

/**
 * @author CWDS API Team
 * 
 *         This class is the refernce to {@link gov.ca.cwds.data.persistence.ns.PersonRace}
 *
 */
@Embeddable
public class PersonRaceId implements Serializable {

  /**
   * Base serialization value. Increment by version
   */
  private static final long serialVersionUID = 1L;

  /**
   * constructor
   */
  public PersonRaceId() {
    super();
  }

  @ManyToOne
  private Person person; // NOSONAR

  @ManyToOne
  private Race race; // NOSONAR


  /**
   * @return the person
   */
  public Person getPerson() {
    return person;
  }

  /**
   * @param person - The person
   */
  public void setPerson(Person person) {
    this.person = person;
  }

  /**
   * @return the race
   */
  public Race getRace() {
    return race;
  }

  /**
   * @param race - The race
   */
  public void setRace(Race race) {
    this.race = race;
  }

  @SuppressWarnings("javadoc")
  public Serializable getPrimaryKey() {
    return null;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((race == null) ? 0 : race.hashCode());
    result = prime * result + ((person == null) ? 0 : person.hashCode());
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
    PersonRaceId other = (PersonRaceId) obj;
    if (race == null) {
      if (other.race != null)
        return false;
    } else if (!race.equals(other.race))
      return false;
    if (person == null) {
      if (other.person != null)
        return false;
    } else if (!person.equals(other.person))
      return false;
    return true;
  }

}
